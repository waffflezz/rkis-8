package ru.sfu.waffflezz.rkis8.utils;

import static ru.sfu.waffflezz.rkis8.utils.Logger.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.sfu.waffflezz.rkis8.models.Vessel;
import ru.sfu.waffflezz.rkis8.services.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Client implements CommandLineRunner {

  private final ClientService clientService;
  List<Integer> ids = new ArrayList<>();
  List<Float> width = new ArrayList<>();

  @Autowired
  public Client(ClientService clientService) {
    this.clientService = clientService;
  }

  public void showAll() {
    Mono<List<Vessel>> vesselMono = clientService.getAll();
    vesselMono
        .doOnSuccess(vessels -> log("Все записи:\n" + vessels.stream()
            .map(vessel -> {
              ids.add(vessel.getId());
              width.add(vessel.getWidth());
              return vessel.toString();
            })
            .collect(Collectors.joining("\n")), "Client"))
        .subscribeOn(Schedulers.boundedElastic())
        .block();
  }

  public void showOne(int id) {
    Mono<Vessel> vesselMono = clientService.getById(id);
    vesselMono
        .doOnSuccess(vessel -> log("Запись с id = " + id + ": \n" + vessel, "Client"))
        .subscribeOn(Schedulers.boundedElastic())
        .block();
  }

  public void showFilterByWidth(Float width) {
    Mono<List<Vessel>> vesselMono = clientService.filterByWidth(width);
    vesselMono
        .doOnSuccess(vessels -> log("Записи с шириной меньше или равной " + width + " :\n" + vessels.stream()
            .map(Object::toString)
            .collect(Collectors.joining("\n")), "Client"))
        .subscribeOn(Schedulers.boundedElastic())
        .block();
  }

  public void addVessel(String name, String color, String material,
      Float width, Float depth, Float price, Integer quantity) {
    Vessel vessel = new Vessel(
        name,
        color,
        material,
        width,
        depth,
        price,
        quantity
    );
    log("Добавляем посуду: \n" + vessel, "Client");
    clientService.create(vessel);
  }

  public void deleteAll() {
    log("Удаляем все записи", "Client");
    clientService.deleteAll();
  }

  public void updateVessel(int id, String name, String color, String material,
      Float width, Float depth, Float price, Integer quantity) {
    Vessel vessel = new Vessel(
        name,
        color,
        material,
        width,
        depth,
        price,
        quantity
    );
    log("Обновляем посуду с id = " + id + " на \n" + vessel, "Client");
    clientService.update(id, vessel);
  }

  public void deleteVessel(int id) {
    log("Удаляем посуду с id = " + id, "Client");
    clientService.delete(id);
  }

  @Override
  public void run(String... args) {
    String run = System.getProperty("allow.run");

    if (!(run == null || "true".equals(run))) {
      return;
    }
    log("--------------- CLIENT START ---------------", "Client");

    addVessel("Name_1", "Color_1", "Material_1", 111f, 111f, 111f, 100);
    addVessel("Name_2", "Color_2", "Material_2", 222f, 222f, 222f, 200);
    showAll();
    showFilterByWidth(150f);
    showOne(ids.get(0));
    updateVessel(ids.get(0), "Name_3", "Color_3", "Material_3", 333f, 333f, 333f, 300);
    showAll();
    deleteVessel(ids.get(1));
    showAll();
    deleteAll();
    log("-------------- CLIENT FINISH ---------------", "Client");
  }
}
