package ru.malygin.server.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.malygin.server.model.dto.transfer.CrawlerSettingsViews;
import ru.malygin.server.model.entity.CrawlerSettings;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrawlerSettingsDto {

    @Null(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @JsonView(CrawlerSettingsViews.IdNamePreset.class)
    private Long id;

    @NotNull(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @JsonView(CrawlerSettingsViews.IdNamePreset.class)
    private Boolean preset;

    @NotNull(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @NotBlank(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @JsonView(CrawlerSettingsViews.IdNamePreset.class)
    private String presetName;

    @NotNull(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @NotBlank(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @JsonView(CrawlerSettingsViews.FullView.class)
    private String description;

    @Min(value = 1, groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @NotNull(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @JsonView(CrawlerSettingsViews.FullView.class)
    private Integer parallelism;

    @Min(value = 0, groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @NotNull(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @JsonView(CrawlerSettingsViews.FullView.class)
    private Integer timeout;

    @Min(value = 0, groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @NotNull(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @JsonView(CrawlerSettingsViews.FullView.class)
    private Integer delay;

    @Min(value = 1, groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @NotNull(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @JsonView(CrawlerSettingsViews.FullView.class)
    private Integer reconnect;

    @NotNull(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @NotBlank(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @JsonView(CrawlerSettingsViews.FullView.class)
    private String userAgent;

    @NotNull(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @NotBlank(groups = {CrawlerSettingsViews.New.class, CrawlerSettingsViews.Update.class})
    @JsonView(CrawlerSettingsViews.FullView.class)
    private String referrer;

    public static CrawlerSettingsDto fromCrawlerSettings(CrawlerSettings cs) {
        if (cs == null) {
            return null;
        }
        CrawlerSettingsDto csd = new CrawlerSettingsDto();
        csd.setId(cs.getId());
        csd.setPreset(cs.getPreset());
        csd.setPresetName(cs.getPresetName());
        csd.setDescription(cs.getDescription());
        csd.setParallelism(cs.getParallelism());
        csd.setTimeout(cs.getTimeout());
        csd.setDelay(cs.getDelay());
        csd.setReconnect(cs.getReconnect());
        csd.setUserAgent(cs.getUserAgent());
        csd.setReferrer(cs.getReferrer());

        return csd;
    }

    public CrawlerSettings toCrawlerSettings() {
        CrawlerSettings cs = new CrawlerSettings();
        cs.setPreset(preset);
        cs.setPresetName(presetName);
        cs.setDescription(description);
        cs.setParallelism(parallelism);
        cs.setTimeout(timeout);
        cs.setDelay(delay);
        cs.setReconnect(reconnect);
        cs.setUserAgent(userAgent);
        cs.setReferrer(referrer);

        return cs;
    }

    public static List<CrawlerSettingsDto> fromListCrawlerSettings(List<CrawlerSettings> lcs) {
        if (lcs == null) {
            return Collections.emptyList();
        }
        return lcs.stream().map(CrawlerSettingsDto::fromCrawlerSettings).collect(Collectors.toList());
    }
}
