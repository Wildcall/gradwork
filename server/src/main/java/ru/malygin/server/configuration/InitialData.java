package ru.malygin.server.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.malygin.server.service.CrawlerService;
import ru.malygin.server.service.IndexerService;

@Component
@Slf4j
public class InitialData {

    private final CrawlerService crawlerService;
    private final IndexerService indexerService;

    public InitialData(CrawlerService crawlerService, IndexerService indexerService) {
        this.crawlerService = crawlerService;
        this.indexerService = indexerService;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        log.info(crawlerService.init());
        log.info(indexerService.init());
    }
}
