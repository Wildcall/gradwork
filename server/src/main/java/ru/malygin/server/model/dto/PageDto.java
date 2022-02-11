package ru.malygin.server.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.malygin.server.model.dto.transfer.PageViews;
import ru.malygin.server.model.entity.core.Page;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageDto {

    @NotNull(groups = {PageViews.Update.class})
    @JsonView(PageViews.IdPathBlHi.class)
    private Long id;

    @JsonView(PageViews.IdPathBlHi.class)
    private String path;

    @NotNull(groups = {PageViews.Update.class})
    @JsonView(PageViews.IdPathBlHi.class)
    private boolean hasIndex;

    @NotNull(groups = {PageViews.Update.class})
    @JsonView(PageViews.IdPathBlHi.class)
    private boolean blacklist;

    @JsonView(PageViews.FullView.class)
    private int code;

    @JsonView(PageViews.FullView.class)
    private LocalDateTime lastUpdate;

    public static PageDto fromPage(Page page) {
        if (page == null)
            return null;
        PageDto pageDto = new PageDto();
        pageDto.setId(page.getId());
        pageDto.setPath(page.getPath());
        pageDto.setHasIndex(page.isHasIndex());
        pageDto.setBlacklist(page.isBlacklist());
        pageDto.setCode(page.getCode());
        pageDto.setLastUpdate(page.getLastUpdate());

        return pageDto;
    }

    public Page toPage() {
        Page page = new Page();
        page.setId(id);
        page.setBlacklist(blacklist);
        return page;
    }

    public static List<PageDto> fromListPage(List<Page> pages) {
        return pages == null
                ? Collections.emptyList()
                : pages.stream().map(PageDto::fromPage).collect(Collectors.toList());
    }
}
