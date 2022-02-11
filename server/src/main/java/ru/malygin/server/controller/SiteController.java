package ru.malygin.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.malygin.server.exception.crawler.CrawlerDefaultSettingsNotFoundException;
import ru.malygin.server.exception.crawler.CrawlerSettingsNotFoundException;
import ru.malygin.server.exception.indexer.IndexerSettingsNotFoundException;
import ru.malygin.server.exception.site.SiteAlreadyExistsException;
import ru.malygin.server.exception.site.SiteNotFoundException;
import ru.malygin.server.model.dto.SiteDto;
import ru.malygin.server.model.dto.transfer.SiteViews;
import ru.malygin.server.model.entity.core.SiteStatus;
import ru.malygin.server.service.SiteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/site")
public class SiteController {

    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @GetMapping
    @JsonView({SiteViews.IdPathNameStatus.class})
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(SiteDto.fromListSite(siteService.findAll()));
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatusList() {
        return ResponseEntity.ok(SiteStatus.values());
    }

    @GetMapping("{id}")
    @JsonView({SiteViews.FullView.class})
    public ResponseEntity<?> findById(
            @PathVariable Long id) {
        try {
            SiteDto response = SiteDto.fromSite(siteService.findById(id));
            return ResponseEntity.ok(response);
        } catch (SiteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    @JsonView(SiteViews.FullView.class)
    public ResponseEntity<?> save(
            @Validated(SiteViews.New.class) @RequestBody SiteDto siteDto) {
        try {
            return ResponseEntity.ok(SiteDto.fromSite(siteService.save(siteDto.toSite())));
        } catch (SiteAlreadyExistsException | CrawlerDefaultSettingsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @JsonView(SiteViews.FullView.class)
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Validated(SiteViews.Update.class) @RequestBody SiteDto siteDto) {
        try {
            return ResponseEntity.ok(SiteDto.fromSite(siteService.update(id, siteDto.toSite())));
        } catch (SiteAlreadyExistsException | SiteNotFoundException | CrawlerSettingsNotFoundException | IndexerSettingsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(siteService.delete(id));
        } catch (SiteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
