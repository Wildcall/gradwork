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

    public CrawlerSettings save(CrawlerSettings cs) throws CrawlerSettingsAlreadyExistsException {
        existByPresetName(cs.getPresetName());
        return csr.save(cs);
    }

    public CrawlerSettings update(Long id, CrawlerSettings cs) throws CrawlerSettingsNotFoundException, CrawlerSettingsAlreadyExistsException {
        CrawlerSettings ecs = findById(id);
        boolean saveFlag = false;

        if (!ecs.getPresetName().equals(cs.getPresetName())) {
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

    public Long delete(Long id) throws CrawlerSettingsNotFoundException, CrawlerSettingsCannotBeRemovedException {
        CrawlerSettings cs = findById(id);
        if (!cs.getSites().isEmpty()) {
            throw new CrawlerSettingsCannotBeRemovedException("Crawler settings with id " + id +
                    " cannot be removed. Reason: there are sites with current settings");
        }
        csr.delete(cs);
        return id;
    }

    public List<CrawlerSettings> findAll(Boolean preset) {
        if (preset != null)
            return csr.findAllByPreset(preset);
        return (List<CrawlerSettings>) csr.findAll();
    }

    public CrawlerSettings findById(Long id) throws CrawlerSettingsNotFoundException {
        return csr.findById(id)
                .orElseThrow(() -> new CrawlerSettingsNotFoundException("Crawler preset with id: " + id + " not found"));
    }

    public CrawlerSettings getDefault() {
        Optional<CrawlerSettings> ecs = csr.findByPresetName("default");

        if (ecs.isPresent())
            return ecs.get();

        try {
            save(crawlerConf.getDefault());
        } catch (CrawlerSettingsAlreadyExistsException e) {
            e.printStackTrace();
        }

        return csr.findByPresetName("default").get();
    }

    private void existByPresetName(String name) throws  CrawlerSettingsAlreadyExistsException {
        if (csr.existsByPresetName(name))
            throw new CrawlerSettingsAlreadyExistsException("Crawler preset with name: " + name + " already has existed");
    }
}
