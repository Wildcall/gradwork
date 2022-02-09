package ru.malygin.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/page")
public class PageController {

    @GetMapping()
    public ResponseEntity<?> getAllPage() {
        // todo implement me
        return ResponseEntity.ok("Not implemented yet. All pages should be here...");
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(
            @PathVariable Long id) {
        // todo implement me
        return ResponseEntity.ok("Not implemented yet. Pages with id - {" + id + "} should be here...");
    }

    @PutMapping
    public ResponseEntity<?> changeAll(
    ) {
        // todo implement me (add dao for pages with id, blacklist, has index)
        return ResponseEntity.ok("Not implemented yet. Pages with id in lists should be changes...");
    }

}
