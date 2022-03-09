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

    /**
     * Сохраняет ошибку в бд
     * @param error ошибка
     * @return Error после сохранения в бд с присвоенным id
     */
    public Error save(Error error) {
        error.setErrorTime(LocalDateTime.now());
        return errorRepository.save(error);
    }

    /**
     * Поиск всех ошибок для сайта
     * @param siteId id сайта
     * @return List< Error > лист всех ошибок для сайта
     * @throws SiteNotFoundException если сайт с siteId не найден
     */
    public List<Error> find(Long siteId) throws SiteNotFoundException {
        if (siteId == null)
            return (List<Error>) errorRepository.findAll();
        return findBySite(siteId);
    }

    /**
     * Поиск ошибки с заданным id
     * @param id ошибки
     * @return Error
     * @throws ErrorNotFoundException если ошибка с таким id не найдена
     */
    public Error findById(Long id) throws ErrorNotFoundException {
        return errorRepository.findById(id)
                .orElseThrow(() -> new ErrorNotFoundException("Error with id: " + id  + " not found"));
    }

    /**
     * Удаляет все ошибки для сайта с заданным id
     * @param siteId сайта
     * @throws SiteNotFoundException если сайт с siteId не найден
     */
    public void deleteBySite(Long siteId) throws SiteNotFoundException {
        errorRepository.deleteBySite(siteService.findById(siteId));
    }

    private List<Error> findBySite(Long siteId) throws SiteNotFoundException {
        return errorRepository.findBySite(siteService.findById(siteId));
    }
}
