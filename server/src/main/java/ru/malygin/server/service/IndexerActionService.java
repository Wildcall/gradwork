package ru.malygin.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.malygin.server.exception.IndexingHasAlreadyStartedException;
import ru.malygin.server.exception.SiteNotFoundException;
import ru.malygin.server.exception.SiteNotSavedException;
import ru.malygin.server.model.entity.IndexerSettings;
import ru.malygin.server.model.entity.IndexerStatistics;
import ru.malygin.server.model.entity.core.Page;
import ru.malygin.server.model.entity.core.Site;
import ru.malygin.server.model.entity.core.SiteStatus;
import ru.malygin.server.utility.Indexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;

@Service
@Slf4j
public class IndexerActionService {

    private final SiteService siteService;
    private final StatisticsService statService;
    private final PageService pageService;
    private final IndexService indexService;
    private final LemmaService lemmaService;

    public IndexerActionService(SiteService siteService,
                                StatisticsService statService,
                                PageService pageService,
                                LemmaService lemmaService,
                                IndexService indexService) {
        this.siteService = siteService;
        this.pageService = pageService;
        this.statService = statService;
        this.indexService = indexService;
        this.lemmaService = lemmaService;
        Indexer.init(lemmaService, indexService);
    }


    public void start(Long id) throws SiteNotFoundException, SiteNotSavedException, IndexingHasAlreadyStartedException {
        Site site = siteService.findById(id);

        if (!site.getStatus().equals(SiteStatus.SAVED) && !site.getStatus().equals(SiteStatus.INDEXED)) {
            if (site.getStatus().equals(SiteStatus.INDEXING))
                throw new IndexingHasAlreadyStartedException("Indexing for site " + site.getPath() + " has already started");
            throw new SiteNotSavedException("Site " + site.getPath() + " not saved in local data base");
        }

        List<Page> pagesList = pageService.findAllBySiteAndHasIndexAndBlacklist(site, false, false);

        if (!pagesList.isEmpty()) {
            siteService.update(id, SiteStatus.INDEXING);
            IndexerStatistics indexerStatistics = statService.startIndexerStat(site);
            IndexerSettings indexerSettings = site.getIndexer();
            Map<String, Double> selectorsWeight = new HashMap<>(indexerSettings.getSelectorWeight());

            ForkJoinTask.invokeAll(pagesList
                            .stream()
                            .map(page -> new Indexer(site, page, indexerStatistics, selectorsWeight))
                            .toList());

            updateLemmaFreq(site);

            statService.stopIndexerStat(indexerStatistics);
            siteService.update(id, SiteStatus.INDEXED);
        }
    }

    private void updateLemmaFreq(Site site) {
        lemmaService.findAllBySite(site).forEach(lemma -> {
            lemma.setFrequency(indexService.countByLemmaId(lemma.getId()));
        });
    }
}
