package ru.malygin.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.malygin.server.exception.site.SiteNotFoundException;
import ru.malygin.server.service.SearchService;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam Long siteId,
            @RequestParam String query) {
        try {
            return ResponseEntity.ok(searchService.search(siteId, query));
        } catch (SiteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
