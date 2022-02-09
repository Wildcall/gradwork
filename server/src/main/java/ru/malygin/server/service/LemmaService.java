package ru.malygin.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.malygin.server.model.entity.IndexerStatistics;
import ru.malygin.server.model.entity.core.LIndex;
import ru.malygin.server.model.entity.core.Lemma;
import ru.malygin.server.model.entity.core.Page;
import ru.malygin.server.model.entity.core.Site;
import ru.malygin.server.repository.LemmaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LemmaService {

    private final LemmaRepository lemmaRepository;
    private final IndexService indexService;

    public LemmaService(LemmaRepository lemmaRepository, IndexService indexService) {
        this.lemmaRepository = lemmaRepository;
        this.indexService = indexService;
    }

    public synchronized void saveAll(Map<String, Double> lemmasMap,
                                     Page page,
                                     Site site,
                                     IndexerStatistics is) {
        Set<Lemma> lemmaSet = lemmasMap
                .keySet()
                .stream()
                .map((lemma) -> new Lemma(lemma, 1, site, is))
                .collect(Collectors.toSet());

        List<Lemma> savingLemmas = new ArrayList<>();
        List<Lemma> existLemmas = lemmaRepository.findBySite(site.getId());

        existLemmas.forEach(existLemma -> {
            if (lemmaSet.contains(existLemma)) {
                savingLemmas.add(existLemma);
                lemmaSet.remove(existLemma);
            }
        });

        savingLemmas.addAll((List<Lemma>) lemmaRepository.saveAll(lemmaSet));

        indexService.saveAll(savingLemmas.stream()
                .map(lemma -> new LIndex(lemmasMap.get(lemma.getLemma()), page, lemma, is))
                .collect(Collectors.toSet()));
    }

    public List<Long> findAllByLemmasAndSite(List<String> lemmas, Site site) {
        return lemmaRepository.findAllByLemmaInAndSite(lemmas, site).stream().map(Lemma::getId).collect(Collectors.toList());
    }

    public List<Lemma> findAllBySite(Site site) {
        return lemmaRepository.findBySite(site.getId());
    }
}
