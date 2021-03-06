package ru.malygin.server.utility;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.malygin.server.model.entity.IndexerStatistics;
import ru.malygin.server.model.entity.core.LIndex;
import ru.malygin.server.model.entity.core.Page;
import ru.malygin.server.model.entity.core.Site;
import ru.malygin.server.service.LIndexService;
import ru.malygin.server.service.LemmaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

@Slf4j
public class Indexer extends RecursiveAction {

    private static LemmaService lemmaService;
    private static LIndexService LIndexService;

    /**
     * Устанавливает сервисы для алгоритма индексации
     * @param lemmaService LemmaService
     * @param LIndexService IndexService
     */
    public static void init(LemmaService lemmaService, LIndexService LIndexService) {
        Indexer.LIndexService = LIndexService;
        Indexer.lemmaService = lemmaService;
    }

    private final Page page;
    private final Site site;
    private final IndexerStatistics indexerStatistics;
    private final Map<String, Double> selectorsWeight;

    /**
     * Создание объекта индексатора для страницы
     * @param site сайт на котором ведется индексация
     * @param page страница на которой ведется индексация
     * @param indexerStatistics объект сохранения статистики
     * @param selectorsWeight весовые коэффициенты для селекторов
     */
    public Indexer(Site site,
                   Page page,
                   IndexerStatistics indexerStatistics,
                   Map<String, Double> selectorsWeight) {
        this.site = site;
        this.page = page;
        this.indexerStatistics = indexerStatistics;
        this.selectorsWeight = selectorsWeight;
    }

    /**
     * Запускает алгоритм нахождения и создания индексов
     */
    @Override
    public void compute() {
        if (page.getCode() == 200) {
            page.setIndexerStatistics(indexerStatistics);
            prepare();
            parse();
            page.setHasIndex(true);
        }
    }

    private void prepare() {
        List<LIndex> existLIndices = LIndexService.findAllByPage(page);
        if (!existLIndices.isEmpty()) {
            LIndexService.delete(existLIndices);
        }
    }

    private void parse() {
        Map<String, Double> result = new HashMap<>();
        Document document = Jsoup.parse(page.getContent());
        selectorsWeight
                .forEach((field, weight) -> Lemmantisator.getLemmasWeight(document.getElementsByTag(field).toString(), weight)
                .forEach((lemma, rank) -> result.compute(lemma, (key, val) -> (val == null) ? rank : val + rank)));
        lemmaService.saveAll(result, page, site, indexerStatistics);
    }
}
