package ru.malygin.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.malygin.server.exception.page.PageNotFoundException;
import ru.malygin.server.exception.site.SiteNotFoundException;
import ru.malygin.server.model.entity.core.Page;
import ru.malygin.server.model.entity.core.Site;
import ru.malygin.server.repository.PageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PageService {

    private final PageRepository pageRepository;
    private final SiteService siteService;

    public PageService(PageRepository pageRepository, SiteService siteService) {
        this.pageRepository = pageRepository;
        this.siteService = siteService;
    }

    /**
     * Сохраняет страницу в бд
     * @param page Page
     * @return Page после сохранения в БД с присвоенным id
     */
    public Page save(Page page) {
        Page existPage = pageRepository.findByPath(page.getPath()).orElse(null);

        if (existPage == null) {
            existPage = page;
            existPage.setHasIndex(false);
            existPage.setLastUpdate(LocalDateTime.now());
            existPage.setBlacklist(false);
            existPage = pageRepository.save(existPage);
        } else {
            existPage = updateAll(existPage, page);
        }
        return existPage;
    }

    /**
     * Возвращает список страниц для заданного сайта с полями hasIndex и blacklist
     * @param site Site сайт
     * @param hasIndex Boolean
     * @param blacklist Boolean
     * @return List< Page > список страниц
     */
    public List<Page> findAllBySiteAndHasIndexAndBlacklist(Site site, Boolean hasIndex, Boolean blacklist) {
        return pageRepository.findAllBySiteAndHasIndexAndBlacklist(site, hasIndex, blacklist);
    }

    /**
     * Возвращает список страниц из БД соответсвующий списку id
     * @param pageIdList список id страниц
     * @return List< Page >
     */
    public List<Page> findAllById(List<Long> pageIdList) {
        return (List<Page>) pageRepository.findAllById(pageIdList);
    }

    /**
     * Возвращает список страниц для заданного сайта
     * @param siteId сайта
     * @return List< Page >
     * @throws SiteNotFoundException если сайт с заданным id не найден
     */
    public List<Page> findBySite(Long siteId) throws SiteNotFoundException {
        if (siteId== null)
            return (List<Page>) pageRepository.findAll();
        return pageRepository.findAllBySite(siteService.findById(siteId));
    }

    /**
     * Возвращает страницу из бд с заданным id
     * @param id страницы
     * @return Page
     * @throws PageNotFoundException если страница с заданным id не найдена
     */
    public Page findById(Long id) throws PageNotFoundException {
        return pageRepository.findById(id)
                .orElseThrow(() -> new PageNotFoundException("Page with id " + id + " not found"));
    }

    /**
     * Возвращает список страниц после обновления и сохранения в БД.
     * @param pages список страниц для обновления
     * @return List< Page >
     */
    public List<Page> updateAll(List<Page> pages) {
        for (Page page : pages) {
            try {
                Page existPage = findById(page.getId());
                existPage.setHasIndex(page.isHasIndex());
                existPage.setBlacklist(page.isBlacklist());
                pageRepository.save(existPage);
            } catch (PageNotFoundException ignored) {}
        }
        return (List<Page>) pageRepository.findAllById(pages.stream().map(Page::getId).collect(Collectors.toList()));
    }

    /**
     * Удалить все страницы из БД для заданного сайта
     * @param siteId сайта
     * @return пустой список, если удаление успешно
     * @throws SiteNotFoundException если сайт с заданным id не найден
     */
    public List<Page> deleteBySite(Long siteId) throws SiteNotFoundException {
        Site site = siteService.findById(siteId);
        pageRepository.deleteBySite(site);
        return pageRepository.findAllBySite(site);
    }

    private Page updateAll(Page oldPage, Page newPage) {
        boolean saveFlag = false;

        if (!oldPage.getContent().equals(newPage.getContent())) {
            oldPage.setContent(newPage.getContent());
            oldPage.setHasIndex(false);
            oldPage.setCode(newPage.getCode());
            saveFlag = true;
        }

        if (oldPage.isBlacklist() != newPage.isBlacklist()) {
            oldPage.setBlacklist(newPage.isBlacklist());
            saveFlag = true;
        }

        if (newPage.getCrawlerStatistics() != null && !oldPage.getCrawlerStatistics().equals(newPage.getCrawlerStatistics())) {
            oldPage.setCrawlerStatistics(newPage.getCrawlerStatistics());
            saveFlag = true;
        }

        if (newPage.getIndexerStatistics() != null && !oldPage.getIndexerStatistics().equals(newPage.getIndexerStatistics())) {
            oldPage.setIndexerStatistics(newPage.getIndexerStatistics());
            saveFlag = true;
        }

        if (saveFlag) {
            oldPage.setLastUpdate(LocalDateTime.now());
            return pageRepository.save(oldPage);
        }
        return oldPage;
    }
}
