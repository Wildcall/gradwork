package ru.malygin.server.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.malygin.server.model.dto.transfer.CrawlerStatisticsViews;
import ru.malygin.server.model.entity.CrawlerStatistics;
import ru.malygin.server.model.entity.core.Error;
import ru.malygin.server.model.entity.core.Page;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrawlerStatisticsDto {

    @JsonView(CrawlerStatisticsViews.IdTimeSiteId.class)
    private Long id;

    @JsonView(CrawlerStatisticsViews.IdTimeSiteId.class)
    private LocalDateTime startingTime;

    @JsonView(CrawlerStatisticsViews.IdTimeSiteId.class)
    private LocalDateTime endingTime;

    @JsonView(CrawlerStatisticsViews.IdTimeSiteId.class)
    private Long siteId;

    @JsonView(CrawlerStatisticsViews.FullView.class)
    private List<Long> pages;

    @JsonView(CrawlerStatisticsViews.FullView.class)
    private List<Long> errors;

    /**
     * Преобразует объект CrawlerStatistics в модель отображения CrawlerStatisticsDto
     * @param cs объект статистики
     * @return CrawlerStatisticsDto
     */
    public static CrawlerStatisticsDto fromPageStatistics(CrawlerStatistics cs) {
        if (cs == null) {
            return null;
        }
        CrawlerStatisticsDto psd = new CrawlerStatisticsDto();
        psd.setId(cs.getId());
        psd.setStartingTime(cs.getStartingTime());
        psd.setEndingTime(cs.getStartingTime());
        psd.setSiteId(cs.getSite().getId());
        psd.setPages(cs.getPages().stream().map(Page::getId).toList());
        psd.setErrors(cs.getErrors().stream().map(Error::getId).toList());

        return psd;
    }

    /**
     * Преобразует List< CrawlerStatistics > в List< CrawlerStatisticsDto >
     * @param csl лист статистики
     * @return List< CrawlerStatisticsDto >
     */
    public static List<CrawlerStatisticsDto> fromPageStatisticsList(List<CrawlerStatistics> csl) {
        if (csl == null) {
            return Collections.emptyList();
        }
        return csl.stream().map(CrawlerStatisticsDto::fromPageStatistics).collect(Collectors.toList());
    }
}
