package ru.malygin.server.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.malygin.server.model.dto.transfer.SiteViews;
import ru.malygin.server.model.entity.CrawlerSettings;
import ru.malygin.server.model.entity.IndexerSettings;
import ru.malygin.server.model.entity.core.Site;
import ru.malygin.server.model.entity.core.SiteStatus;
import ru.malygin.server.validator.ValueOfEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteDto {

    @Null(groups = {SiteViews.New.class, SiteViews.Update.class})
    @JsonView(SiteViews.IdPathNameStatus.class)
    private Long id;

    @Null(groups = {SiteViews.New.class})
    @ValueOfEnum(enumClass = SiteStatus.class, groups = {SiteViews.Update.class})
    @JsonView(SiteViews.IdPathNameStatus.class)
    private String status;

    @Null(groups = {SiteViews.New.class, SiteViews.Update.class})
    @JsonView(SiteViews.FullView.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime statusTime;

    @Null(groups = {SiteViews.Update.class})
    @NotNull(groups = {SiteViews.New.class})
    @NotBlank(groups = {SiteViews.New.class})
    @JsonView(SiteViews.IdPathNameStatus.class)
    private String path;

    @NotNull(groups = {SiteViews.New.class, SiteViews.Update.class})
    @NotBlank(groups = {SiteViews.New.class, SiteViews.Update.class})
    @JsonView(SiteViews.IdPathNameStatus.class)
    private String name;

    @NotNull(groups = {SiteViews.Update.class})
    @JsonView(SiteViews.FullView.class)
    private Long crawlerId;

    @NotNull(groups = {SiteViews.Update.class})
    @JsonView(SiteViews.FullView.class)
    private Long indexerId;

    private CrawlerSettings crawler;

    private IndexerSettings indexer;

    public static SiteDto fromSite(Site site) {
        if (site == null)
            return null;
        SiteDto siteDto = new SiteDto();
        siteDto.setId(site.getId());
        siteDto.setStatus(site.getStatus().name());
        siteDto.setStatusTime(site.getStatusTime());
        siteDto.setName(site.getName());
        siteDto.setPath(site.getPath());
        siteDto.setCrawler(site.getCrawler());
        siteDto.setCrawlerId(site.getCrawler().getId());
        siteDto.setIndexerId(site.getIndexer().getId());

        return siteDto;
    }

    public Site toSite() {
        Site site = new Site();
        site.setPath(path);
        site.setName(name);

        crawler = new CrawlerSettings();
        crawler.setId(crawlerId);
        site.setCrawler(crawler);

        indexer = new IndexerSettings();
        indexer.setId(indexerId);
        site.setIndexer(indexer);

        if (status != null)
            site.setStatus(SiteStatus.valueOf(status));
        site.setStatusTime(statusTime);

        return site;
    }

    public static List<SiteDto> fromListSite(List<Site> sites) {
        return sites == null
                ? Collections.emptyList()
                : sites.stream().map(SiteDto::fromSite).collect(Collectors.toList());
    }
}
