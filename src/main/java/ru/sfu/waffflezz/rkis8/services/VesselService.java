package ru.sfu.waffflezz.rkis8.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sfu.waffflezz.rkis8.models.Vessel;
import ru.sfu.waffflezz.rkis8.repositories.VesselRepository;

/**
 * Класс представляет сервис для обработки операций с посудой.
 * Он предоставляет методы для поиска, создания, обновления и удаления посуды, а также для
 * фильтрации посуды по её ширине.
 */
@Service
@Transactional(readOnly = true)
public class VesselService {

  /**
   * Внедрение зависимости репозитория посуды в сервис
   */
  private final VesselRepository vesselRepository;

  @Autowired
  public VesselService(VesselRepository vesselRepository) {
    this.vesselRepository = vesselRepository;
  }

  /**
   * Метод выполняет поиск и возвращает список всей посуды, хранящейся в базе данных.
   *
   * @return Список всей посуды.
   */
  public List<Vessel> findAll() {
    return vesselRepository.findAll();
  }

  /**
   * Метод выполняет поиск посдуы по её id.
   *
   * @param id  Уникальный идентификатор посуды.
   * @return Сущность посуды, если найдена, или null, если посуда не существует.
   */
  public Vessel findOne(int id) {
    return vesselRepository.findById(id).orElse(null);
  }

  /**
   * Метод сохраняет новую посуду в базе данных.
   *
   * @param vessel  Сущность посдуы для сохранения.
   */
  @Transactional
  public void save(Vessel vessel) {
    vesselRepository.save(vessel);
  }

  /**
   * Метод обновляет существующую посуду в базе данных по его уникальному ID.
   *
   * @param id      Уникальный идентификатор посуды, которую нужно обновить.
   * @param vessel  Обновленная посуда.
   */
  @Transactional
  public void update(int id, Vessel vessel) {
    vessel.setId(id);
    vesselRepository.save(vessel);
  }

  /**
   * Метод удаляет посуду из базы данных по её id.
   *
   * @param id  Уникальный идентификатор посуду, которую нужно удалить.
   */
  @Transactional
  public void delete(int id) {
    vesselRepository.deleteById(id);
  }

  /**
   * Метод выполняет фильтрацию посуды по ширине, возвращая список посуды, ширина которой
   * меньше или равна заданному значению.
   *
   * @param maxWidth  Максимальная ширина, по которой выполняется фильтрация суден.
   * @return Список суден, удовлетворяющих заданной ширине.
   */
  public List<Vessel> filterByWidth(float maxWidth) {
    return vesselRepository.findByWidthLessThanEqual(maxWidth);
  }

  @Transactional
  public void deleteAll() {
    vesselRepository.deleteAll();
  }
}
