package ru.malygin.server.service;

import org.springframework.stereotype.Service;
import ru.malygin.server.exception.error.ErrorNotFoundException;
import ru.malygin.server.exception.site.SiteNotFoundException;
import ru.malygin.server.model.entity.core.Error;
import ru.malygin.server.repository.ErrorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ErrorService {

    private final ErrorRepository errorRepository;
    private final SiteService siteService;

    public ErrorService(ErrorRepository errorRepository,
                        SiteService siteService) {
        this.errorRepository = errorRepository;
        this.siteService = siteService;
    }

    public Error save(Error error) {
        error.setErrorTime(LocalDateTime.now());
        return errorRepository.save(error);
    }

    public List<Error> find(Long siteId) throws SiteNotFoundException {
        if (siteId == null)
            return (List<Error>) errorRepository.findAll();
        return findBySite(siteId);
    }

    public Error findById(Long id) throws ErrorNotFoundException {
        return errorRepository.findById(id)
                .orElseThrow(() -> new ErrorNotFoundException("Error with id: " + id  + " not found"));
    }

    private List<Error> findBySite(Long siteId) throws SiteNotFoundException {
        return errorRepository.findBySite(siteService.findById(siteId));
    }

    public void deleteBySite(Long siteId) throws SiteNotFoundException {
        errorRepository.deleteBySite(siteService.findById(siteId));
    }
}
