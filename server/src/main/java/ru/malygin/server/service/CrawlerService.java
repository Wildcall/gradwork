package ru.malygin.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.malygin.server.configuration.CrawlerConfiguration;
import ru.malygin.server.exception.crawler.CrawlerSettingsAlreadyExistsException;
import ru.malygin.server.exception.crawler.CrawlerSettingsCannotBeRemovedException;
import ru.malygin.server.exception.crawler.CrawlerSettingsNotFoundException;
import ru.malygin.server.model.entity.CrawlerSettings;
import ru.malygin.server.repository.CrawlerSettingsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CrawlerService {

    private final CrawlerSettingsRepository csr;
    private final CrawlerConfiguration crawlerConf;

    public CrawlerService(
            CrawlerSettingsRepository csr,
            CrawlerConfiguration crawlerConf) {
        this.csr = csr;
        this.crawlerConf = crawlerConf;
    }

    /**
     * Сохраняет настройки алгоритма обхода в бд
     * @param cs настройки
     * @return CrawlerSettings после сохранения в бд с присвоенным id
     * @throws CrawlerSettingsAlreadyExistsException если имя шаблона уже используется
     */
    public CrawlerSettings save(CrawlerSettings cs) throws CrawlerSettingsAlreadyExistsException {
        existByPresetName(cs.getPresetName());
        return csr.save(cs);
    }

    /**
     * Обновляет настройки алгоритма обхода с заданным id, на новые
     * @param id настроек для обновления
     * @param cs новые настройки
     * @return CrawlerSettings после сохранения в бд с присвоенным id
     * @throws CrawlerSettingsNotFoundException если настройки с таким id не существуют
     * @throws CrawlerSettingsAlreadyExistsException если имя шаблона уже используется
     */
    public CrawlerSettings update(Long id, CrawlerSettings cs) throws CrawlerSettingsNotFoundException, CrawlerSettingsAlreadyExistsException {
        CrawlerSettings ecs = findById(id);
        boolean saveFlag = false;

        if (!ecs.getPresetName().equals(cs.getPresetName()) && !ecs.getPresetName().equals("default")) {
            existByPresetName(cs.getPresetName());
            ecs.setPresetName(cs.getPresetName());
            saveFlag = true;
        }

        if (!ecs.getPreset().equals(cs.getPreset())) {
            ecs.setPreset(cs.getPreset());
            saveFlag = true;
        }

        if (!ecs.getDescription().equals(cs.getDescription())) {
            ecs.setDescription(cs.getDescription());
            saveFlag = true;
        }

        if (!ecs.getParallelism().equals(cs.getParallelism())){
            ecs.setParallelism(cs.getParallelism());
            saveFlag = true;
        }

        if (!ecs.getTimeout().equals(cs.getTimeout())){
            ecs.setTimeout(cs.getTimeout());
            saveFlag = true;
        }

        if (!ecs.getDelay().equals(cs.getDelay())){
            ecs.setDelay(cs.getDelay());
            saveFlag = true;
        }

        if (!ecs.getReconnect().equals(cs.getReconnect())){
            ecs.setReconnect(cs.getReconnect());
            saveFlag = true;
        }

        if (!ecs.getUserAgent().equals(cs.getUserAgent())){
            ecs.setUserAgent(cs.getUserAgent());
            saveFlag = true;
        }

        if (!ecs.getReferrer().equals(cs.getReferrer())){
            ecs.setReferrer(cs.getReferrer());
            saveFlag = true;
        }

        return saveFlag ? csr.save(ecs) : ecs;
    }

    /**
     * Удаляет настройки алгоритма обхода с заданным id из бд
     * @param id настроек
     * @return Long id удаленной настройки
     * @throws CrawlerSettingsNotFoundException если настройки с таким id не существуют
     * @throws CrawlerSettingsCannotBeRemovedException если настройки с таким id используется или являются шаблоном по умолчанию
     */
    public Long delete(Long id) throws CrawlerSettingsNotFoundException, CrawlerSettingsCannotBeRemovedException {
        CrawlerSettings cs = findById(id);
        if (cs.getPresetName().equals("default")) {
            throw new CrawlerSettingsCannotBeRemovedException("Crawler settings with id " + id +
                    " cannot be removed. Reason: there are default preset");
        }
        if (!cs.getSites().isEmpty()) {
            throw new CrawlerSettingsCannotBeRemovedException("Crawler settings with id " + id +
                    " cannot be removed. Reason: there are sites with current settings");
        }
        csr.delete(cs);
        return id;
    }

    /**
     * Поиск всех настроек алгоритма обхода с маркировкой шаблон
     * @param preset шаблон Boolean
     * @return List< CrawlerSettings >
     */
    public List<CrawlerSettings> findAll(Boolean preset) {
        if (preset != null)
            return csr.findAllByPreset(preset);
        return (List<CrawlerSettings>) csr.findAll();
    }

    /**
     * Поиск настроек алгоритма обхода с заданным id
     * @param id настроек
     * @return CrawlerSettings
     * @throws CrawlerSettingsNotFoundException если настройки с таким id не существуют
     */
    public CrawlerSettings findById(Long id) throws CrawlerSettingsNotFoundException {
        return csr.findById(id)
                .orElseThrow(() -> new CrawlerSettingsNotFoundException("Crawler preset with id: " + id + " not found"));
    }

    /**
     * Возвращает default настройки алгоритма обхода из базы данных.
     * Если их нет, делает запись в бд предустановленных настроек из crawler.properties
     * @return CrawlerSettings
     */
    public CrawlerSettings getDefault() {
        Optional<CrawlerSettings> ecs = csr.findByPresetName("default");

        if (ecs.isPresent())
            return ecs.get();

        try {
            return save(crawlerConf.getDefault());
        } catch (CrawlerSettingsAlreadyExistsException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Создает в БД запись со стандартными настройками
     * @return строка для печати в консоль
     */
    public String init() {
        try {
            save(crawlerConf.getDefault());
            return "Default crawler settings successfully saved";
        } catch (CrawlerSettingsAlreadyExistsException e) {
            return "Default crawler settings already existed";
        }
    }

    private void existByPresetName(String name) throws  CrawlerSettingsAlreadyExistsException {
        if (csr.existsByPresetName(name))
            throw new CrawlerSettingsAlreadyExistsException("Crawler preset with name: " + name + " already has existed");
    }
}
