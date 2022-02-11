package ru.malygin.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.malygin.server.model.entity.core.Page;
import ru.malygin.server.model.entity.core.Site;

import java.util.List;
import java.util.Optional;

public interface PageRepository extends CrudRepository<Page, Long> {
    Optional<Page> findById(Long id);
    Optional<Page> findByPath(String path);
    List<Page> findAllBySiteAndHasIndexAndBlacklist(Site site, Boolean hasIndex, Boolean blacklist);
    List<Page> findAllBySite(Site site);
    @Transactional
    void deleteBySite(Site site);
}
