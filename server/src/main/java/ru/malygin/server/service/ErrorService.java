package ru.malygin.server.service;

import org.springframework.stereotype.Service;
import ru.malygin.server.model.entity.core.Error;
import ru.malygin.server.model.entity.core.Site;
import ru.malygin.server.repository.ErrorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ErrorService {

    private final ErrorRepository errorRepository;

    public ErrorService(ErrorRepository errorRepository) {
        this.errorRepository = errorRepository;
    }

    public Error save(Error error) {
        error.setErrorTime(LocalDateTime.now());
        return errorRepository.save(error);
    }

    public List<Error> findAllBySite(Site site){
        return errorRepository.findBySite(site);
    }

    public List<Error> findAll() {
        return (List<Error>) errorRepository.findAll();
    }

    public void deleteAllBySite(Site site) {
        errorRepository.deleteBySite(site);
    }
}
