package ru.malygin.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.malygin.server.model.entity.CrawlerStatistics;
import ru.malygin.server.model.entity.core.Site;

import java.util.List;

public interface CrawlerStatRepository extends CrudRepository<CrawlerStatistics, Long> {
    List<CrawlerStatistics> findAllBySite(Site site);
    @Transactional
    void deleteAllBySite(Site site);
}
