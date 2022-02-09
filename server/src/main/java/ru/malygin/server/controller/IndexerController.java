package ru.malygin.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.malygin.server.exception.*;
import ru.malygin.server.model.dto.CrawlerSettingsDto;
import ru.malygin.server.model.dto.IndexerSettingsDto;
import ru.malygin.server.model.dto.transfer.CrawlerSettingsViews;
import ru.malygin.server.model.dto.transfer.IndexerSettingsViews;
import ru.malygin.server.service.IndexerActionService;
import ru.malygin.server.service.IndexerService;

@RestController
@RequestMapping("/api/v1/indexer")
public class IndexerController {

    private final IndexerService indexerService;
    private final IndexerActionService indexerActionService;

    public IndexerController(IndexerService indexerService, IndexerActionService indexerActionService) {
        this.indexerService = indexerService;
        this.indexerActionService = indexerActionService;
    }

    @GetMapping
    @JsonView({IndexerSettingsViews.IdNamePreset.class})
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) Boolean preset) {
        return ResponseEntity.ok(IndexerSettingsDto.fromListIndexerSettings(indexerService.findAll(preset)));
    }

    @GetMapping("{id}")
    @JsonView({IndexerSettingsViews.FullView.class})
    public ResponseEntity<?> findOne(
            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(IndexerSettingsDto.fromIndexerSettings(indexerService.findById(id)));
        } catch (IndexerSettingsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    @JsonView({IndexerSettingsViews.FullView.class})
    public ResponseEntity<?> save(
            @Validated(IndexerSettingsViews.New.class) @RequestBody IndexerSettingsDto isd) {
        try {
            return ResponseEntity.ok(IndexerSettingsDto.fromIndexerSettings(indexerService.save(isd.toIndexerSettings())));
        } catch (IndexerSettingsAlreadyExistsException | IndexerSettingsWrongFormatException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @JsonView({IndexerSettingsViews.FullView.class})
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Validated(IndexerSettingsViews.Update.class) @RequestBody IndexerSettingsDto isd) {
        try {
            return ResponseEntity.ok(IndexerSettingsDto.fromIndexerSettings(indexerService.update(id, isd.toIndexerSettings())));
        } catch (IndexerSettingsNotFoundException | IndexerSettingsAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(indexerService.delete(id));
        } catch (IndexerSettingsNotFoundException | IndexerSettingsCannotBeRemovedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/start/{id}")
    public ResponseEntity<?> start(
            @PathVariable Long id) {
        try {
            indexerActionService.start(id);
            return ResponseEntity.ok("Start indexing for id: " + id);
        } catch (SiteNotFoundException | SiteNotSavedException | IndexingHasAlreadyStartedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
