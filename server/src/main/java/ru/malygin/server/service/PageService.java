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

    public Page save(Page page) {
        Page existPage = pageRepository.findByPath(page.getPath()).orElse(null);

        if (existPage == null) {
            existPage = page;
            existPage.setHasIndex(false);
            existPage.setLastUpdate(LocalDateTime.now());
            existPage.setBlacklist(false);
            existPage = pageRepository.save(existPage);
        } else {
            existPage = update(existPage, page);
        }
        return existPage;
    }

    public List<Page> findAllBySiteAndHasIndexAndBlacklist(Site site, Boolean hasIndex, Boolean blacklist) {
        return pageRepository.findAllBySiteAndHasIndexAndBlacklist(site, hasIndex, blacklist);
    }

    public List<Page> findAllById(List<Long> pageIdList) {
        return (List<Page>) pageRepository.findAllById(pageIdList);
    }

    public List<Page> find(Long siteId) throws SiteNotFoundException {
        if (siteId== null)
            return (List<Page>) pageRepository.findAll();
        return pageRepository.findAllBySite(siteService.findById(siteId));
    }

    public Page findById(Long id) throws PageNotFoundException {
        return pageRepository.findById(id)
                .orElseThrow(() -> new PageNotFoundException("Page with id " + id + " not found"));
    }

    public List<Page> update(List<Page> pages) {
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

    public List<Page> deleteBySite(Long siteId) throws SiteNotFoundException {
        Site site = siteService.findById(siteId);
        pageRepository.deleteBySite(site);
        return pageRepository.findAllBySite(site);
    }

    private Page update(Page oldPage, Page newPage) {
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
