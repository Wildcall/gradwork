package ru.malygin.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.malygin.server.exception.indexer.IndexingHasAlreadyStartedException;
import ru.malygin.server.exception.site.SiteNotFoundException;
import ru.malygin.server.exception.site.SiteNotSavedException;
import ru.malygin.server.model.entity.CrawlerStatistics;
import ru.malygin.server.model.entity.core.Site;
import ru.malygin.server.model.entity.core.SiteStatus;
import ru.malygin.server.utility.Crawler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

@Service
@Slf4j
public class CrawlerActionService {

    private final Map<Long, ForkJoinPool> crawlerMap;
    private final SiteService siteService;
    private final StatisticsService statService;

    public CrawlerActionService(SiteService siteService,
                                StatisticsService statService,
                                ErrorService errorService,
                                PageService pageService) {
        this.siteService = siteService;
        this.statService = statService;
        crawlerMap = new HashMap<>();
        Crawler.init(pageService, errorService);
    }

    public void start(Long id) throws SiteNotFoundException, IndexingHasAlreadyStartedException, SiteNotSavedException {
        Site site = siteService.findById(id);

        if (site.getStatus().equals(SiteStatus.SAVING) | site.getStatus().equals(SiteStatus.INDEXING))
            throw new IndexingHasAlreadyStartedException("Saving or indexing for site " + site.getPath() + " has already started");


        log.info("Start saving for site - " + site.getPath());
        siteService.update(id, SiteStatus.SAVING);

        ForkJoinPool forkJoinPool = new ForkJoinPool(site.getCrawler().getParallelism());
        crawlerMap.put(id, forkJoinPool);

        CrawlerStatistics cs = statService.startCrawlerStat(site);

        forkJoinPool.invoke(new Crawler(site, null, cs));

        statService.stopCrawlerStat(cs);

        crawlerMap.remove(id, forkJoinPool);

        if (forkJoinPool.isShutdown()) {
            log.info("Stop for site - " + site.getPath());
            siteService.update(id, SiteStatus.FAILED);
        } else {
            log.info("All task done for site - " + site.getPath());
            siteService.update(id, SiteStatus.SAVED);
        }
    }

    public void stop(Long id) {
        ForkJoinPool forkJoinPool = crawlerMap.get(id);
        if (forkJoinPool != null){
            forkJoinPool.shutdownNow();
            crawlerMap.remove(id, forkJoinPool);
        }
    }
}
