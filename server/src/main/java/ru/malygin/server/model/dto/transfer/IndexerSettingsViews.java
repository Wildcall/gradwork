package ru.malygin.server.model.dto.transfer;

public final class IndexerSettingsViews {
    public interface IdNamePreset {}
    public interface FullView extends IndexerSettingsViews.IdNamePreset {}
    public interface New {}
    public interface Update {}
}
