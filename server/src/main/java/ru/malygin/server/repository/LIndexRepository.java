package ru.malygin.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.malygin.server.model.entity.core.LIndex;
import ru.malygin.server.model.entity.core.Lemma;

import java.util.List;

public interface LIndexRepository extends CrudRepository<LIndex, Long> {
    @Query("select i from LIndex i where i.page.id = ?1")
    List<LIndex> findAllByPage(Long id);

    @Query("select count(i) from LIndex i where i.lemma.id = ?1")
    int countByLemma(Long id);

    @Query(value = "select pageId, lemmaRank from (select page_id as pageId, count(page_id) as pageCount, sum(_rank) as lemmaRank from _lindex where lemma_id in (:lemmasId) group by page_id) as tmp where pageCount = (:lemmasCount) order by lemmaRank desc", nativeQuery = true)
    List<Object[]> search(List<Long> lemmasId, int lemmasCount);

    @Transactional
    void deleteAllByLemmaIn(List<Lemma> lemmas);
}
