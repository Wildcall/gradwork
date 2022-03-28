package ru.malygin.server.service;

import org.springframework.stereotype.Service;
import ru.malygin.server.exception.crawler.CrawlerDefaultSettingsNotFoundException;
import ru.malygin.server.exception.crawler.CrawlerSettingsNotFoundException;
import ru.malygin.server.exception.indexer.IndexerSettingsNotFoundException;
import ru.malygin.server.exception.site.SiteAlreadyExistsException;
import ru.malygin.server.exception.site.SiteNotFoundException;
import ru.malygin.server.model.entity.CrawlerSettings;
import ru.malygin.server.model.entity.IndexerSettings;
import ru.malygin.server.model.entity.core.Site;
import ru.malygin.server.model.entity.core.SiteStatus;
import ru.malygin.server.repository.SiteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SiteService {

    private final SiteRepository siteRepository;
    private final CrawlerService csService;
    private final IndexerService isService;
    private final StatisticsService statisticsService;


    public SiteService(SiteRepository siteRepository,
                       CrawlerService csService,
                       IndexerService isService,
                       StatisticsService statisticsService) {
        this.siteRepository = siteRepository;
        this.csService = csService;
        this.isService = isService;
        this.statisticsService = statisticsService;
    }

    /**
     * Сохранение сайта в БД
     * @param site сайт
     * @return Site после сохранения в БД и присвоения id
     * @throws SiteAlreadyExistsException если название сайта уже используется
     */
    public Site save(Site site) throws SiteAlreadyExistsException, CrawlerSettingsNotFoundException {
        existByPath(site.getPath());
        existByName(site.getName());
        site.setStatus(SiteStatus.INORDER);
        site.setStatusTime(LocalDateTime.now());
        site.setCrawler(csService.getDefault());
        site.setIndexer(isService.getDefault());

        return siteRepository.save(site);
    }

    /**
     * Обновляет сайт с заданным id на новый сайт
     * @param id сайта для обновления
     * @param site новый сайт
     * @return Site после сохранения в БД и присвоения id
     * @throws SiteNotFoundException если сайт с заданным id не найден
     * @throws SiteAlreadyExistsException если название сайта уже используется
     */
    public Site update(Long id, Site site) throws SiteNotFoundException, SiteAlreadyExistsException, CrawlerSettingsNotFoundException, IndexerSettingsNotFoundException {
        Site existSite = findById(id);

        if (!existSite.getName().equals(site.getName())) {
            existByName(site.getName());
            existSite.setName(site.getName());
            existSite.setStatusTime(LocalDateTime.now());
        }

        if (site.getCrawler() != null && !existSite.getCrawler().equals(site.getCrawler())) {
            CrawlerSettings cs = csService.findById(site.getCrawler().getId());
            existSite.setCrawler(cs);
        }

        if (site.getIndexer() != null && !existSite.getIndexer().equals(site.getIndexer())) {
            IndexerSettings is = isService.findById(site.getIndexer().getId());
            existSite.setIndexer(is);
        }

        return siteRepository.save(existSite);
    }

    /**
     * Обновляет статус заданного сайта
     * @param id сайта
     * @param status новый статус
     * @return Site после сохранения в БД и присвоения id
     * @throws SiteNotFoundException если сайт с заданным id не найден
     */
    public Site update(Long id, SiteStatus status) throws SiteNotFoundException {
        Site existSite = findById(id);

        if (!existSite.getStatus().equals(status)) {
            existSite.setStatus(status);
            existSite.setStatusTime(LocalDateTime.now());
            return siteRepository.save(existSite);
        }
        return existSite;
    }

    /**
     * Удаляет сайт из БД
     * @param id сайта
     * @return id сайта после удаления
     * @throws SiteNotFoundException если сайт с заданным id не найден
     */
    public Long delete(Long id) throws SiteNotFoundException {
        Site site = findById(id);
        statisticsService.deleteAllIndexerStatBySite(site);
        siteRepository.delete(site);
        return id;
    }

    /**
     * Возвращает список всех сайтов сохраненных в БД
     * @return List< Site >
     */
    public List<Site> findAll() {
        return (List<Site>) siteRepository.findAll();
    }

    /**
     * Возвращает сайт с заданным id
     * @param id сайта
     * @return Site
     * @throws SiteNotFoundException если сайт с заданным id не найден
     */
    public Site findById(Long id) throws SiteNotFoundException {
        return siteRepository.findById(id)
                .orElseThrow(() -> new SiteNotFoundException("Site with id: " + id + " not found"));
    }

    private void existByPath(String path) throws SiteAlreadyExistsException {
        if (siteRepository.existsByPath(path))
            throw new SiteAlreadyExistsException("Site with path: " + path + " already has existed");
    }

    private void existByName(String name) throws SiteAlreadyExistsException {
        if (siteRepository.existsByName(name))
            throw new SiteAlreadyExistsException("Site with name: " + name + " already has existed");
    }
}
