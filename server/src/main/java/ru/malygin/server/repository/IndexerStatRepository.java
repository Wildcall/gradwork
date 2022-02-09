package ru.malygin.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.malygin.server.model.entity.IndexerStatistics;
import ru.malygin.server.model.entity.core.Site;

public interface IndexerStatRepository extends CrudRepository<IndexerStatistics, Long> {
    @Transactional
    void deleteAllBySite(Site site);
}
