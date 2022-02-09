package ru.malygin.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.malygin.server.model.entity.core.Error;
import ru.malygin.server.model.entity.core.Site;

import java.util.List;

public interface ErrorRepository extends CrudRepository<Error, Long> {
    List<Error> findBySite(Site site);
    @Transactional
    void deleteBySite(Site site);
}
