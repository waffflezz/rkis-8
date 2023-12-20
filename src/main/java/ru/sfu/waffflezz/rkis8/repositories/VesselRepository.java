package ru.sfu.waffflezz.rkis8.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.waffflezz.rkis8.models.Vessel;

/**
 * Интерфейс представляет собой репозиторий для работы посудой.
 * Он расширяет JpaRepository и предоставляет методы для выполнения операций с посудой в базе данных.
 */
@Repository
public interface VesselRepository extends JpaRepository<Vessel, Integer> {
  /**
   * Метод выполняет поиск посуды в базе данных,
   * ширина которых меньше или равна заданному значению "width".
   *
   * @param width  Значение ширины, по которому выполняется фильтрация посуды.
   * @return Список посуды, соответствующих заданной ширине или менее.
   */
  List<Vessel> findByWidthLessThanEqual(float width);
}
