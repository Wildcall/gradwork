package ru.malygin.server.service;

import org.springframework.stereotype.Service;
import ru.malygin.server.model.entity.core.LIndex;
import ru.malygin.server.model.entity.core.Page;
import ru.malygin.server.repository.LIndexRepository;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class LIndexService {

    private final LIndexRepository lIndexRepository;

    public LIndexService(LIndexRepository lIndexRepository) {
        this.lIndexRepository = lIndexRepository;
    }

    /**
     * Сохраняет список индексов в БД
     * @param collect список индексов
     */
    public void saveAll(Set<LIndex> collect) {
        lIndexRepository.saveAll(collect);
    }

    /**
     * Поиск всех индексов для страницы
     * @param page страница
     * @return List< LIndex >
     */
    public List<LIndex> findAllByPage(Page page) {
        return lIndexRepository.findAllByPage(page.getId());
    }

    /**
     * Возвращает количество индексов в БД для леммы с заданным id
     * @param id леммы
     * @return int кол-во индексов
     */
    public int countByLemmaId(Long id) {
        return lIndexRepository.countByLemma(id);
    }

    /**
     * Удаление списка индексов из БД
     * @param existLIndices список индексов
     */
    public void delete(List<LIndex> existLIndices) {
        lIndexRepository.deleteAll(existLIndices);
    }

    /**
     * Выполняет запрос к БД, выполняя поиск по списку лемм.
     * Возвращает id страницы на которой найдена лемма и релевантность данной страницы.
     * Сортировка по убыванию релевантности.
     * @param allByLemmas список лемм
     * @return Map< Long, Double >
     */
    public Map<Long, Double> search(List<Long> allByLemmas) {
        Map<Long, Double> queryResult = new LinkedHashMap<>();
        lIndexRepository.search(allByLemmas, allByLemmas.size()).forEach(o -> {
            queryResult.put(((BigInteger) o[0]).longValue(), ((Double) o[1]));
        });
        return queryResult;
    }
}
