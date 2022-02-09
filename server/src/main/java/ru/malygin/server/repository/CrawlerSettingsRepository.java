package ru.malygin.server.repository;

import org.springframework.data.repository.CrudRepository;
import ru.malygin.server.model.entity.CrawlerSettings;

import java.util.List;
import java.util.Optional;

public interface CrawlerSettingsRepository extends CrudRepository<CrawlerSettings, Long> {
    Optional<CrawlerSettings> findByPresetName(String name);
    boolean existsByPresetName(String name);
    List<CrawlerSettings> findAllByPreset(Boolean preset);
}
