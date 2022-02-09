package ru.malygin.server.model.dto.transfer;

public final class CrawlerSettingsViews {
    public interface IdNamePreset {}
    public interface FullView extends CrawlerSettingsViews.IdNamePreset {}
    public interface New {}
    public interface Update {}
}
