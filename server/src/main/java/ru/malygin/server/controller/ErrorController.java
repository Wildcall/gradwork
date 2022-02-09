package ru.malygin.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.malygin.server.model.dto.ErrorDto;
import ru.malygin.server.model.dto.transfer.ErrorViews;
import ru.malygin.server.service.ErrorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/error")
public class ErrorController {

    private final ErrorService errorService;

    public ErrorController(ErrorService errorService) {
        this.errorService = errorService;
    }

    @GetMapping()
    @JsonView({ErrorViews.FullView.class})
    public ResponseEntity<?> findAll() {
        List<ErrorDto> response = ErrorDto.fromListError(errorService.findAll());
        return ResponseEntity.ok(response);
    }
}
