package ru.malygin.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.malygin.server.model.dto.transfer.CrawlerStatisticsViews;

@RestController
@RequestMapping("/api/v1/stat")
public class StatisticsController {

    @GetMapping
    public ResponseEntity<?> total() {
        // todo implement me
        return ResponseEntity.ok("Not implemented yet. Total statistic should be here...");
    }

    @GetMapping("/crawler")
    @JsonView({CrawlerStatisticsViews.IdTimeSiteId.class})
    public ResponseEntity<?> findAllCrawlerStat() {
        // todo implement me
        return ResponseEntity.ok("Not implemented yet. Statistics for all crawlers should be here...");
    }

    @GetMapping("/crawler/{id}")
    @JsonView({CrawlerStatisticsViews.FullView.class})
    public ResponseEntity<?> findOneCrawlerStat(
            @PathVariable Long id) {
        // todo implement me
        return ResponseEntity.ok("Not implemented yet. Statistics for specific crawler {" + id + "} should be here...");
    }

    @GetMapping("/indexer")
    @JsonView({CrawlerStatisticsViews.IdTimeSiteId.class})
    public ResponseEntity<?> findAllIndexerStat() {
        // todo implement me
        return ResponseEntity.ok("Not implemented yet. Statistics for all indexers should be here...");
    }

    @GetMapping("/indexer/{id}")
    @JsonView({CrawlerStatisticsViews.FullView.class})
    public ResponseEntity<?> findOneIndexerStat(
            @PathVariable Long id) {
        // todo implement me
        return ResponseEntity.ok("Not implemented yet. Statistics for specific indexer {" + id + "} should be here...");
    }
}
