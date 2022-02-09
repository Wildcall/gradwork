package ru.malygin.server.service;

import org.springframework.stereotype.Service;
import ru.malygin.server.exception.PageStatisticsNotFoundException;
import ru.malygin.server.exception.SiteNotFoundException;
import ru.malygin.server.model.entity.CrawlerStatistics;
import ru.malygin.server.model.entity.IndexerStatistics;
import ru.malygin.server.model.entity.core.Site;
import ru.malygin.server.repository.CrawlerStatRepository;
import ru.malygin.server.repository.IndexerStatRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatisticsService {

    private final CrawlerStatRepository csRepository;
    private final IndexerStatRepository isRepository;

    public StatisticsService(CrawlerStatRepository csRepository,
                             IndexerStatRepository isRepository) {
        this.csRepository = csRepository;
        this.isRepository = isRepository;
    }

    //=================================================================//

    public CrawlerStatistics startCrawlerStat(Site site) {
        CrawlerStatistics cs = new CrawlerStatistics();
        cs.setSite(site);
        cs.setStartingTime(LocalDateTime.now());
        cs.setEndingTime(LocalDateTime.now());
        return saveCrawlerStat(cs);
    }

    public CrawlerStatistics stopCrawlerStat(CrawlerStatistics cs) {
        cs.setEndingTime(LocalDateTime.now());
        return saveCrawlerStat(cs);
    }

    public List<CrawlerStatistics> findCrawlerStat(Site site) throws SiteNotFoundException {
        return site != null ? findAllCrawlerStatBySite(site) : findAllCrawlerStat();
    }

    public CrawlerStatistics findCrawlerStatById(Long id) throws PageStatisticsNotFoundException {
        return csRepository.findById(id)
                .orElseThrow(() ->
                        new PageStatisticsNotFoundException("Page statistics with id: " + id + " not found"));
    }

    private List<CrawlerStatistics> findAllCrawlerStat() {
        return (List<CrawlerStatistics>) csRepository.findAll();
    }

    private List<CrawlerStatistics> findAllCrawlerStatBySite(Site site) {
        return csRepository.findAllBySite(site);
    }

    private CrawlerStatistics saveCrawlerStat(CrawlerStatistics cs) {
        return csRepository.save(cs);
    }

    //=================================================================//

    public IndexerStatistics startIndexerStat(Site site) {
        IndexerStatistics is = new IndexerStatistics();
        is.setSite(site);
        is.setStartingTime(LocalDateTime.now());
        is.setEndingTime(LocalDateTime.now());
        return saveIndexerStat(is);
    }

    public IndexerStatistics stopIndexerStat(IndexerStatistics is) {
        is.setEndingTime(LocalDateTime.now());
        return saveIndexerStat(is);
    }

    private IndexerStatistics saveIndexerStat(IndexerStatistics is) {
        return isRepository.save(is);
    }

    public void deleteAllIndexerStatBySite(Site site) {
        isRepository.deleteAllBySite(site);
    }
}
