package ru.malygin.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.malygin.server.configuration.IndexerConfiguration;
import ru.malygin.server.exception.indexer.IndexerSettingsAlreadyExistsException;
import ru.malygin.server.exception.indexer.IndexerSettingsCannotBeRemovedException;
import ru.malygin.server.exception.indexer.IndexerSettingsNotFoundException;
import ru.malygin.server.model.entity.IndexerSettings;
import ru.malygin.server.repository.IndexerSettingsRepository;

import java.util.List;

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

    /**
     * Сохраняет настройки алгоритма индексации в бд
     * @param is настройки
     * @return IndexerSettings после сохранения в бд с присвоенным id
     * @throws IndexerSettingsAlreadyExistsException если имя шаблона уже используется
     */
    public IndexerSettings save(IndexerSettings is) throws IndexerSettingsAlreadyExistsException {
        existByPresetName(is.getPresetName());
        return isr.save(is);
    }

    /**
     * Обновляет настройки алгоритма индексации с заданным id, на новые
     * @param id настроек для обновления
     * @param is новые настройки
     * @return IndexerSettings после сохранения в бд с присвоенным id
     * @throws IndexerSettingsNotFoundException если настройки с таким id не существуют
     * @throws IndexerSettingsAlreadyExistsException если имя шаблона уже используется
     */
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

    /**
     * Удаляет настройки алгоритма индексации с заданным id из бд
     * @param id настроек
     * @return Long id удаленной настройки
     * @throws IndexerSettingsNotFoundException если настройки с таким id не существуют
     * @throws IndexerSettingsCannotBeRemovedException если настройки с таким id используется
     */
    public Long delete(Long id) throws IndexerSettingsNotFoundException, IndexerSettingsCannotBeRemovedException {
        IndexerSettings is = findById(id);
        if (!is.getSites().isEmpty()) {
            throw new IndexerSettingsCannotBeRemovedException("Indexer settings with id " + id +
                    " cannot be removed. Reason: there are sites with current settings");
        }
        isr.delete(is);
        return id;
    }

    /**
     * Поиск всех настроек алгоритма индексации с маркировкой шаблон
     * @param preset шаблон Boolean
     * @return List< IndexerSettings >
     */
    public List<IndexerSettings> findAll(Boolean preset) {
        if (preset != null)
            return isr.findAllByPreset(preset);
        return (List<IndexerSettings>) isr.findAll();
    }

    /**
     * Поиск настроек алгоритма индексации с заданным id
     * @param id настроек
     * @return IndexerSettings
     * @throws IndexerSettingsNotFoundException  если настройки с таким id не существуют
     */
    public IndexerSettings findById(Long id) throws IndexerSettingsNotFoundException {
        return isr.findById(id)
                .orElseThrow(() -> new IndexerSettingsNotFoundException("Indexer preset with id: " + id + " not found"));
    }

    /**
     * Возвращает default настройки алгоритма индексации из базы данных.
     * Если их нет, делает запись в бд предустановленных настроек из indexer.properties
     * @return IndexerSettings
     */
    public IndexerSettings getDefault() {
        return isr.findByPresetName("default").orElse(isr.save(indexerConf.getDefault()));
    }

    private void existByPresetName(String name) throws IndexerSettingsAlreadyExistsException {
        if (isr.existsByPresetName(name))
            throw new IndexerSettingsAlreadyExistsException("Indexer preset with name: " + name + " already has existed");
    }

}
