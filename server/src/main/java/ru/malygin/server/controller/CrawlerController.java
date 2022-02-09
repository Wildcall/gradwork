package ru.malygin.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.malygin.server.exception.*;
import ru.malygin.server.model.dto.CrawlerSettingsDto;
import ru.malygin.server.model.dto.transfer.CrawlerSettingsViews;
import ru.malygin.server.service.CrawlerActionService;
import ru.malygin.server.service.CrawlerService;

@RestController
@RequestMapping("/api/v1/crawler")
public class CrawlerController {

    private final CrawlerService crawlerService;
    private final CrawlerActionService crawlerActionService;

    public CrawlerController(CrawlerService crawlerService, CrawlerActionService crawlerActionService) {
        this.crawlerService = crawlerService;
        this.crawlerActionService = crawlerActionService;
    }

    @GetMapping
    @JsonView({CrawlerSettingsViews.IdNamePreset.class})
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) Boolean preset) {
        return ResponseEntity.ok(CrawlerSettingsDto.fromListCrawlerSettings(crawlerService.findAll(preset)));
    }

    @GetMapping("{id}")
    @JsonView({CrawlerSettingsViews.FullView.class})
    public ResponseEntity<?> findOne(
            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(CrawlerSettingsDto.fromCrawlerSettings(crawlerService.findById(id)));
        } catch (CrawlerSettingsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    @JsonView({CrawlerSettingsViews.FullView.class})
    public ResponseEntity<?> save(
            @Validated(CrawlerSettingsViews.New.class) @RequestBody CrawlerSettingsDto csd) {
        try {
            return ResponseEntity.ok(CrawlerSettingsDto.fromCrawlerSettings(crawlerService.save(csd.toCrawlerSettings())));
        } catch (CrawlerSettingsAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @JsonView({CrawlerSettingsViews.FullView.class})
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Validated(CrawlerSettingsViews.Update.class) @RequestBody CrawlerSettingsDto csd) {
        try {
            return ResponseEntity.ok(CrawlerSettingsDto.fromCrawlerSettings(crawlerService.update(id, csd.toCrawlerSettings())));
        } catch (CrawlerSettingsNotFoundException | CrawlerSettingsAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(crawlerService.delete(id));
        } catch (CrawlerSettingsNotFoundException | CrawlerSettingsCannotBeRemovedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/start/{id}")
    public ResponseEntity<?> start(
            @PathVariable Long id) {
        try {
            crawlerActionService.start(id);
            return ResponseEntity.ok("Start saving for id: " + id);
        } catch (SiteNotFoundException | IndexingHasAlreadyStartedException | SiteNotSavedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/stop/{id}")
    public ResponseEntity<?> stop(
            @PathVariable Long id) {
        crawlerActionService.stop(id);
        return ResponseEntity.ok("Stop saving for id: " + id);
    }
}
