package ru.malygin.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.malygin.server.exception.page.PageNotFoundException;
import ru.malygin.server.exception.site.SiteNotFoundException;
import ru.malygin.server.model.dto.PageDto;
import ru.malygin.server.model.dto.transfer.PageViews;
import ru.malygin.server.service.PageService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/page")
public class PageController {

    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping()
    @JsonView({PageViews.IdPathBlHi.class})
    public ResponseEntity<?> find(
            @RequestParam(required = false) Long siteId) {
        try {
            return ResponseEntity.ok(
                    PageDto.fromListPage(
                            pageService.find(siteId)));
        } catch (SiteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    @JsonView({PageViews.FullView.class})
    public ResponseEntity<?> findById(
            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    PageDto.fromPage(
                            pageService.findById(id)));
        } catch (PageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    @JsonView({PageViews.IdPathBlHi.class})
    public ResponseEntity<?> updateAll(
            @Validated(PageViews.Update.class) @RequestBody List<PageDto> pages) {
        return ResponseEntity.ok(
                PageDto.fromListPage(
                        pageService.update(
                                pages.stream().map(PageDto::toPage).collect(Collectors.toList()))));

    }

    @DeleteMapping
    @JsonView({PageViews.IdPathBlHi.class})
    public ResponseEntity<?> deleteBySite(
            @RequestParam Long siteId) {
        try {
            return ResponseEntity.ok(
                    PageDto.fromListPage(
                            pageService.deleteBySite(siteId)));
        } catch (SiteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
