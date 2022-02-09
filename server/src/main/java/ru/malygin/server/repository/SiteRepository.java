package ru.malygin.server.repository;

import org.springframework.data.repository.CrudRepository;
import ru.malygin.server.model.entity.core.Site;

public interface SiteRepository extends CrudRepository<Site, Long> {
    boolean existsByPath(String path);
    boolean existsByName(String name);
}
