package ru.malygin.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.malygin.server.configuration.IndexerConfiguration;
import ru.malygin.server.exception.IndexerSettingsAlreadyExistsException;
import ru.malygin.server.exception.IndexerSettingsCannotBeRemovedException;
import ru.malygin.server.exception.IndexerSettingsNotFoundException;
import ru.malygin.server.exception.IndexerSettingsWrongFormatException;
import ru.malygin.server.model.entity.IndexerSettings;
import ru.malygin.server.repository.IndexerSettingsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class IndexerService {

    private final IndexerSettingsRepository isr;
    private final IndexerConfiguration indexerConf;

    public IndexerService(IndexerSettingsRepository isr,
                          IndexerConfiguration indexerConf) {
        this.isr = isr;
        this.indexerConf = indexerConf;
    }

    public IndexerSettings save(IndexerSettings is) throws IndexerSettingsAlreadyExistsException, IndexerSettingsWrongFormatException {
        existByPresetName(is.getPresetName());
        return isr.save(is);
    }

    public IndexerSettings update(Long id,
                                  IndexerSettings is) throws IndexerSettingsNotFoundException, IndexerSettingsAlreadyExistsException {
        IndexerSettings eis = findById(id);
        boolean saveFlag = false;

        if (!eis.getPresetName().equals(is.getPresetName())) {
            existByPresetName(is.getPresetName());
            eis.setPresetName(is.getPresetName());
            saveFlag = true;
        }

        if (!eis.getPreset().equals(is.getPreset())) {
            eis.setPreset(is.getPreset());
            saveFlag = true;
        }

        if (!eis.getDescription().equals(is.getDescription())) {
            eis.setDescription(is.getDescription());
            saveFlag = true;
        }

        if (!eis.getSelectorWeight().equals(is.getSelectorWeight())) {
            eis.setSelectorWeight(is.getSelectorWeight());
            saveFlag = true;
        }

        return saveFlag ? isr.save(eis) : eis;
    }

    public Object delete(Long id) throws IndexerSettingsNotFoundException, IndexerSettingsCannotBeRemovedException {
        IndexerSettings is = findById(id);
        if (!is.getSites().isEmpty()) {
            throw new IndexerSettingsCannotBeRemovedException("Indexer settings with id " + id +
                    " cannot be removed. Reason: there are sites with current settings");
        }
        isr.delete(is);
        return id;
    }

    public List<IndexerSettings> findAll(Boolean preset) {
        if (preset != null)
            return isr.findAllByPreset(preset);
        return (List<IndexerSettings>) isr.findAll();
    }

    public IndexerSettings findById(Long id) throws IndexerSettingsNotFoundException {
        return isr.findById(id)
                .orElseThrow(() -> new IndexerSettingsNotFoundException("Indexer preset with id: " + id + " not found"));
    }

    public IndexerSettings getDefault() {
        Optional<IndexerSettings> eis = isr.findByPresetName("default");

        if (eis.isPresent())
            return eis.get();

        try {
            save(indexerConf.getDefault());
        } catch (IndexerSettingsWrongFormatException | IndexerSettingsAlreadyExistsException e) {
            e.printStackTrace();
        }

        return isr.findByPresetName("default").get();
    }

    private void existByPresetName(String name) throws IndexerSettingsAlreadyExistsException {
        if (isr.existsByPresetName(name))
            throw new IndexerSettingsAlreadyExistsException("Indexer preset with name: " + name + " already has existed");
    }

}
