package ru.malygin.server.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.malygin.server.model.dto.transfer.ErrorViews;
import ru.malygin.server.model.entity.core.Error;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDto {

    @JsonView(ErrorViews.ShortView.class)
    private Long id;

    @JsonView(ErrorViews.FullView.class)
    private String text;

    @JsonView(ErrorViews.ShortView.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime errorTime;

    @JsonView(ErrorViews.ShortView.class)
    private String sitePath;

    @JsonView(ErrorViews.FullView.class)
    private String pagePath;

    public static ErrorDto fromError(Error error) {
        if (error == null) {
            return null;
        }
        ErrorDto errorDto = new ErrorDto();
        errorDto.setId(error.getId());
        errorDto.setText(error.getText());
        errorDto.setErrorTime(error.getErrorTime());
        errorDto.setPagePath(error.getPage().getPath());
        errorDto.setSitePath(error.getSite().getPath());

        return errorDto;
    }

    public static List<ErrorDto> fromListError(List<Error> errors) {
        if (errors == null) {
            return Collections.emptyList();
        }
        return errors.stream().map(ErrorDto::fromError).collect(Collectors.toList());
    }
}
