package ru.malygin.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.malygin.server.model.entity.core.Lemma;
import ru.malygin.server.model.entity.core.Site;

import java.util.List;

public interface LemmaRepository extends CrudRepository<Lemma, Long> {
    @Query("select l from Lemma l where l.site.id = ?1")
    List<Lemma> findBySite(Long siteId);
    List<Lemma> findAllByLemmaInAndSite(@Param("lemmas") List<String> lemmas, Site site);
}
