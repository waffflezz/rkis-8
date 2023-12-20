package ru.sfu.waffflezz.rkis8.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.sfu.waffflezz.rkis8.models.Vessel;

import java.util.List;

@Service
public class ClientService {

  private final WebClient webClient;

  @Autowired
  public ClientService(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<List<Vessel>> getAll() {
    return webClient.get()
        .uri("/vessels/api")
        .retrieve()
        .bodyToFlux(Vessel.class)
        .collectList();
  }

  public Mono<Vessel> getById(int id) {
    return webClient.get()
        .uri("/vessels/api/" + id)
        .retrieve().bodyToMono(Vessel.class);
  }

  public Mono<List<Vessel>> filterByWidth(Float width) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder.path("/vessels/api")
            .queryParam("width", width)
            .build())
        .retrieve()
        .bodyToFlux(Vessel.class)
        .collectList();
  }

  public void create(Vessel vessel) {
    webClient.post()
        .uri("/vessels/api")
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(vessel), Vessel.class)
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }

  public void update(int id, Vessel vessel) {
    webClient.put()
        .uri("/vessels/api/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(vessel))
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }

  public void delete(int id) {
    webClient.delete()
        .uri("/vessels/api/{id}", id)
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }

  public void deleteAll() {
    webClient.delete()
        .uri("/vessels/api")
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }
}
