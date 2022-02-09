package ru.malygin.server.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.malygin.server.model.dto.transfer.IndexerSettingsViews;
import ru.malygin.server.model.entity.IndexerSettings;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexerSettingsDto {

    @Null(groups = {IndexerSettingsViews.New.class, IndexerSettingsViews.Update.class})
    @JsonView(IndexerSettingsViews.IdNamePreset.class)
    private Long id;

    @NotNull(groups = {IndexerSettingsViews.New.class, IndexerSettingsViews.Update.class})
    @JsonView(IndexerSettingsViews.IdNamePreset.class)
    private Boolean preset;

    @NotNull(groups = {IndexerSettingsViews.New.class, IndexerSettingsViews.Update.class})
    @NotBlank(groups = {IndexerSettingsViews.New.class, IndexerSettingsViews.Update.class})
    @JsonView(IndexerSettingsViews.IdNamePreset.class)
    private String presetName;

    @NotNull(groups = {IndexerSettingsViews.New.class, IndexerSettingsViews.Update.class})
    @NotBlank(groups = {IndexerSettingsViews.New.class, IndexerSettingsViews.Update.class})
    @JsonView(IndexerSettingsViews.FullView.class)
    private String description;

    @NotNull(groups = {IndexerSettingsViews.New.class, IndexerSettingsViews.Update.class})
    @NotEmpty(groups = {IndexerSettingsViews.New.class, IndexerSettingsViews.Update.class})
    @JsonView(IndexerSettingsViews.FullView.class)
    private Map<String, Double> selectorWeight;


    public static IndexerSettingsDto fromIndexerSettings(IndexerSettings is) {
        if (is == null) {
            return null;
        }
        IndexerSettingsDto isd = new IndexerSettingsDto();
        isd.setId(is.getId());
        isd.setPreset(is.getPreset());
        isd.setPresetName(is.getPresetName());
        isd.setDescription(is.getDescription());
        isd.setSelectorWeight(is.getSelectorWeight());

        return isd;
    }

    public IndexerSettings toIndexerSettings() {
        IndexerSettings is = new IndexerSettings();
        is.setPreset(preset);
        is.setPresetName(presetName);
        is.setDescription(description);
        is.setSelectorWeight(selectorWeight);

        return is;
    }

    public static List<IndexerSettingsDto> fromListIndexerSettings(List<IndexerSettings> lis) {
        if (lis == null) {
            return Collections.emptyList();
        }
        return lis.stream().map(IndexerSettingsDto::fromIndexerSettings).collect(Collectors.toList());
    }
}
