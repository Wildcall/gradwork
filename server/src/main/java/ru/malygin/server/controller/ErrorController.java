package ru.malygin.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.malygin.server.exception.error.ErrorNotFoundException;
import ru.malygin.server.exception.site.SiteNotFoundException;
import ru.malygin.server.model.dto.ErrorDto;
import ru.malygin.server.model.dto.transfer.ErrorViews;
import ru.malygin.server.service.ErrorService;

@RestController
@RequestMapping("/api/v1/error")
public class ErrorController {

    private final ErrorService errorService;

    public ErrorController(ErrorService errorService) {
        this.errorService = errorService;
    }

    @GetMapping
    @JsonView({ErrorViews.FullView.class})
    public ResponseEntity<?> find(
            @RequestParam(required = false) Long siteId) {
        try {
            return ResponseEntity.ok(ErrorDto.fromListError(errorService.find(siteId)));
        } catch (SiteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    @JsonView({ErrorViews.FullView.class})
    public ResponseEntity<?> findById(
            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(ErrorDto.fromError(errorService.findById(id)));
        } catch (ErrorNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBySite(
            @RequestParam Long siteId) {
        try {
            errorService.deleteBySite(siteId);
            return ResponseEntity.ok(siteId);
        } catch (SiteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
