package ru.malygin.server.utility;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ru.malygin.server.model.entity.CrawlerSettings;
import ru.malygin.server.model.entity.CrawlerStatistics;
import ru.malygin.server.model.entity.core.Error;
import ru.malygin.server.model.entity.core.Page;
import ru.malygin.server.model.entity.core.Site;
import ru.malygin.server.service.ErrorService;
import ru.malygin.server.service.PageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

@Slf4j
public class Crawler extends RecursiveTask<Set<String>> {

    private static PageService pageService;
    private static ErrorService errorService;

    public static void init(PageService pageService,
                            ErrorService errorService) {
        Crawler.pageService = pageService;
        Crawler.errorService = errorService;
    }

    private final Site site;
    private final String path;
    private final CrawlerStatistics crawlerStatistics;
    private final CrawlerSettings crawlerSettings;
    private Document document;
    private int code;
    private String errors;
    private Page page;
    private Set<String> linkSet;

    public Crawler(Site site, String path, CrawlerStatistics crawlerStatistics) {
        this.site = site;
        this.crawlerSettings = site.getCrawler();
        this.path = path == null ? site.getPath() : path;
        this.crawlerStatistics = crawlerStatistics;
    }

    @Override
    protected Set<String> compute() {
        try {
            fetchPath();
            savePage();
            saveErrors();
            getUrls();

            if (!this.linkSet.isEmpty()) {
                List<Crawler> crawlers = new ArrayList<>();
                linkSet.forEach(item -> crawlers.add(new Crawler(site, item, crawlerStatistics)));
                ForkJoinTask.invokeAll(crawlers);
                crawlers.forEach(crawler -> linkSet.addAll(crawler.join()));
            }
            return linkSet;
        } catch (CancellationException e) {
            return null;
        }
    }

    private void fetchPath() {
        Connection.Response response;
        int reconnect = crawlerSettings.getReconnect();

        while (reconnect > 0) {
            try {
                try {
                    response = Jsoup.connect(path)
                            .maxBodySize(0)
                            .userAgent(crawlerSettings.getUserAgent())
                            .referrer(crawlerSettings.getReferrer())
                            .timeout(crawlerSettings.getTimeout())
                            .execute();
                    this.document = response.parse();
                    this.code = response.statusCode();

                    Thread.sleep(crawlerSettings.getDelay());
                    break;
                } catch (IOException e) {
                    if (reconnect == 1) {
                        int tmp = e.getMessage().indexOf("Status=");
                        this.errors = e.getMessage();
                        this.code = tmp > 0 ? Integer.parseInt(e.getMessage().substring(tmp + 7, tmp + 10)) : -1;
                        this.document = null;
                        break;
                    }
                    reconnect--;
                    Thread.sleep(crawlerSettings.getDelay());
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void getUrls() {
        linkSet = new HashSet<>();
        if (this.document != null) {
            Elements links = this.document.select("a[href]");
            links.forEach(link -> {
                String tmp = link.absUrl("href");
                if (!tmp.isEmpty() && tmp.startsWith(path) && !tmp.equals(path) && !tmp.equals(path + "/") && !tmp.contains("#"))
                    linkSet.add(tmp);
            });
        }
    }

    private void savePage() {
        Page page = new Page();
        page.setPath(this.path);
        page.setCode(this.code);
        page.setContent(this.document == null || this.code < 0 ? "" : this.document.toString());
        page.setSite(this.site);
        page.setCrawlerStatistics(this.crawlerStatistics);
        this.page = pageService.save(page);
    }

    private void saveErrors() {
        if (this.errors != null) {
            Error error = new Error();
            error.setText(this.errors);
            error.setSite(this.site);
            error.setPage(this.page);
            error.setCrawlerStatistics(this.crawlerStatistics);
            errorService.save(error);
        }
    }

}
