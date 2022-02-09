package ru.malygin.server.service;

import org.springframework.stereotype.Service;
import ru.malygin.server.model.entity.core.LIndex;
import ru.malygin.server.model.entity.core.Page;
import ru.malygin.server.model.entity.core.Lemma;
import ru.malygin.server.repository.LIndexRepository;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class IndexService {

    private final LIndexRepository lIndexRepository;

    public IndexService(LIndexRepository lIndexRepository) {
        this.lIndexRepository = lIndexRepository;
    }

    public void saveAll(Set<LIndex> collect) {
        lIndexRepository.saveAll(collect);
    }

    public List<LIndex> findAllByPage(Page page) {
        return lIndexRepository.findAllByPage(page.getId());
    }

    public int countByLemmaId(Long id) {
        return lIndexRepository.countByLemma(id);
    }

    public void delete(List<LIndex> existLIndices) {
        lIndexRepository.deleteAll(existLIndices);
    }

    public Map<Long, Double> search(List<Long> allByLemmasAndSite) {
        Map<Long, Double> queryResult = new LinkedHashMap<>();
        lIndexRepository.search(allByLemmasAndSite, allByLemmasAndSite.size()).forEach(o -> {
            queryResult.put(((BigInteger) o[0]).longValue(), ((Double) o[1]));
        });
        return queryResult;
    }
}
