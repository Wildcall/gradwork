package ru.malygin.server.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ru.malygin.server.exception.SiteNotFoundException;
import ru.malygin.server.model.entity.core.Page;
import ru.malygin.server.model.entity.core.Site;
import ru.malygin.server.model.other.SearchResponse;
import ru.malygin.server.utility.Lemmantisator;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SearchService {

    private final SiteService siteService;
    private final LemmaService lemmaService;
    private final PageService pageService;
    private final IndexService indexService;

    public SearchService(SiteService siteService,
                         LemmaService lemmaService,
                         PageService pageService,
                         IndexService indexService) {
        this.siteService = siteService;
        this.lemmaService = lemmaService;
        this.pageService = pageService;
        this.indexService = indexService;
    }

    public List<SearchResponse> search(Long siteId, String query) throws SiteNotFoundException {
        Site existSite = siteService.findById(siteId);
        List<String> queryList = Lemmantisator.getLemmas(query);
        List<Long> pageIdList = indexService.search(lemmaService.findAllByLemmasAndSite(queryList, existSite)).keySet().stream().toList();
        List<Page> pageList = pageService.findAllById(pageIdList);
        List<SearchResponse> responses = new ArrayList<>();
        pageList.forEach(page -> {
            SearchResponse response = new SearchResponse();
            response.setPath(page.getPath());
            Document document = Jsoup.parse(page.getContent());
            response.setTitle(document.getElementsByTag("title").toString());
            responses.add(response);
        });

        return responses;
    }
}
