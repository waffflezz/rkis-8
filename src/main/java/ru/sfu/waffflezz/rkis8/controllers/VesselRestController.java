package ru.sfu.waffflezz.rkis8.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sfu.waffflezz.rkis8.models.Vessel;
import ru.sfu.waffflezz.rkis8.services.VesselService;

import java.util.List;

@RestController
@RequestMapping("/vessels/api")
public class VesselRestController {

  private final VesselService service;

  @Autowired
  public VesselRestController(VesselService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<Vessel>> getAll(
      @RequestParam(name = "width", required = false) Float width) {
    if (width != null) {
      return new ResponseEntity<>(service.filterByWidth(width), HttpStatus.OK);
    }
    return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Vessel> show(@PathVariable("id") int id) {
    return new ResponseEntity<>(service.findOne(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<HttpStatus> create(@RequestBody @Valid Vessel vessel) {
    service.save(vessel);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<HttpStatus> update(@RequestBody @Valid Vessel vessel,
      @PathVariable("id") int id) {
    service.update(id, vessel);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
    service.delete(id);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<HttpStatus> deleteAll() {
    service.deleteAll();
    return ResponseEntity.ok(HttpStatus.OK);
  }
}
