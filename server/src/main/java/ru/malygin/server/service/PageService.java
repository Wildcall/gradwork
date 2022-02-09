package ru.malygin.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.malygin.server.model.entity.core.Page;
import ru.malygin.server.model.entity.core.Site;
import ru.malygin.server.repository.PageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PageService {

    private final PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
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

    public Page update(Page oldPage, Page newPage) {
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

    public List<Page> findAllBySiteAndHasIndexAndBlacklist(Site site, Boolean hasIndex, Boolean blacklist) {
        return pageRepository.findAllBySiteAndHasIndexAndBlacklist(site, hasIndex, blacklist);
    }

    public List<Page> findAllById(List<Long> pageIdList) {
        return (List<Page>) pageRepository.findAllById(pageIdList);
    }
}
