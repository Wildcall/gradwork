package ru.malygin.server.repository;

import org.springframework.data.repository.CrudRepository;
import ru.malygin.server.model.entity.IndexerSettings;

import java.util.List;
import java.util.Optional;

public interface IndexerSettingsRepository extends CrudRepository<IndexerSettings, Long> {
    Optional<IndexerSettings> findByPresetName(String name);
    boolean existsByPresetName(String name);
    List<IndexerSettings> findAllByPreset(Boolean preset);
}
