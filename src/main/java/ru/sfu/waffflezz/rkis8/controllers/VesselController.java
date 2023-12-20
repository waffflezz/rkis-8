package ru.sfu.waffflezz.rkis8.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sfu.waffflezz.rkis8.messaging.MessageProducer;
import ru.sfu.waffflezz.rkis8.models.Vessel;
import ru.sfu.waffflezz.rkis8.services.VesselService;

/**
 * Класс представляет собой контроллер, отвечающий за обработку HTTP-запросов. Он обеспечивает
 * взаимодействие с сервисом VesselService и обработку запросов, связанных с посудой, такие как
 * создание, редактирование, удаление и просмотр.
 */
@Controller
@RequestMapping("/vessels")
public class VesselController {

  private final MessageProducer messageProducer;

  /**
   * Внедрение зависимости сервиса посуды в контроллер
   */
  private final VesselService vesselService;

  /**
   * Конструктор контроллера, принимающий сервис суднов в качестве параметра
   */
  @Autowired
  public VesselController(MessageProducer messageProducer, VesselService vesselService) {
    this.vesselService = vesselService;
    this.messageProducer = messageProducer;
  }

  /**
   * Обрабатывает GET-запросы по пути "/vessels". Если указан параметр "width", фильтрует посуду по
   * заданной ширине. В противном случае, возвращает всю посуду.
   *
   * @param width Ширина судна для фильтрации (необязательный параметр).
   * @param model Модель для передачи данных в представление.
   * @return Представление "vessels/index".
   */
  @GetMapping()
  public String index(@RequestParam(name = "width", required = false) Float width, Model model) {
    if (width != null) {
      model.addAttribute("vessels", vesselService.filterByWidth(width));
    } else {
      model.addAttribute("vessels", vesselService.findAll());
    }
    return "vessels/index";
  }

  /**
   * Обрабатывает GET-запросы по пути "/vessels/{id}" для просмотра посуды по её идентификатору.
   *
   * @param id    Идентификатор судна.
   * @param model Модель для передачи данных в представление.
   * @return Представление "vessels/show".
   */
  @GetMapping("/{id}")
  public String show(@PathVariable("id") int id, Model model) {
    model.addAttribute("vessel", vesselService.findOne(id));
    return "vessels/show";
  }

  /**
   * Обрабатывает GET-запросы по пути "/vessels/{id}/edit" для редактирования посуды.
   *
   * @param id    Идентификатор судна, которое нужно редактировать.
   * @param model Модель для передачи данных в представление.
   * @return Представление "vessels/edit".
   */
  @GetMapping("/{id}/edit")
  public String edit(@PathVariable("id") int id, Model model) {
    model.addAttribute("vessel", vesselService.findOne(id));
    return "vessels/edit";
  }

  /**
   * Обрабатывает GET-запросы по пути "/vessels/new" для создания новой посуды.
   *
   * @param vessel Сущность посуды, передаваемая в представление.
   * @return Представление "vessels/new".
   */
  @GetMapping("/new")
  public String newVessel(@ModelAttribute("vessel") Vessel vessel) {
    return "vessels/new";
  }

  /**
   * Обрабатывает POST-запросы по пути "/vessels" для создания новой посуды. Проводит валидацию
   * посуды и сохраняет его в сервисе, если данные верны.
   *
   * @param vessel        Сущность посуды для создания.
   * @param bindingResult Результат валидации посуды.
   * @return Представление "vessels/new" в случае ошибок валидации, иначе перенаправляет на
   * "/vessels".
   */
  @PostMapping()
  public String create(
      @ModelAttribute("vessel") @Valid Vessel vessel,
      BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return "vessels/new";
    }
    messageProducer.sendMessage("Добавлена посуда\n" + vessel);
    vesselService.save(vessel);
    return "redirect:/vessels";
  }

  /**
   * Обрабатывает PATCH-запросы по пути "/vessels/{id}" для обновления существующей посуды. Проводит
   * валидацию посуды и обновляет её в сервисе, если данные верны.
   *
   * @param vessel        Сущность посуды для обновления.
   * @param bindingResult Результат валидации посуды.
   * @param id            Идентификатор посуды, которую нужно обновить.
   * @return Представление "vessels/edit" в случае ошибок валидации, иначе перенаправляет на
   * "/vessels".
   */
  @PatchMapping("/{id}")
  public String update(
      @ModelAttribute("vessel") @Valid Vessel vessel,
      BindingResult bindingResult,
      @PathVariable("id") int id
  ) {
    if (bindingResult.hasErrors()) {
      return "vessels/edit";
    }

    messageProducer.sendMessage("Посуда с id = " + id + " изменена на\n" + vessel);
    vesselService.update(id, vessel);
    return "redirect:/vessels";
  }

  /**
   * Обрабатывает DELETE-запросы по пути "/vessels/{id}" для удаления посуды по её идентификатору.
   *
   * @param id Идентификатор посуды, которую нужно удалить.
   * @return Перенаправляет на "/vessels".
   */
  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    messageProducer.sendMessage("Произошла покупка посуды с id = " + id);
    vesselService.delete(id);
    return "redirect:/vessels";
  }

  @DeleteMapping()
  public String deleteAll() {
    messageProducer.sendMessage("Вся посуда исчезла");
    vesselService.deleteAll();
    return "redirect:/vessels";
  }

  @PatchMapping("/{id}/buy")
  public String buy(@PathVariable("id") int id) {
    messageProducer.sendMessage("Куплена посуда с id = " + id);
    return "redirect:/vessels";
  }
}

