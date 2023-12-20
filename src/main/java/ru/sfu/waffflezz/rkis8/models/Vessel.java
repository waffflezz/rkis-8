package ru.sfu.waffflezz.rkis8.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * Класс Vessel представляет собой посуду (тарелку, чашку)
 */
@Entity
@Table(name = "dishes")
public class Vessel {

  /**
   * Идентификатор посуды
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  /**
   * Название посуды
   */
  @Column(name = "name")
  @Size(min = 2, max = 25, message = "len from 2 to 25")
  private String name;

  /**
   * Цвет посуды
   */
  @Column(name = "color")
  @Size(min = 2, max = 25, message = "len from 2 to 25")
  private String color;

  /**
   * Материал посуды
   */
  @Column(name = "material")
  @Size(min = 2, max = 25, message = "len from 2 to 25")
  private String material;

  /**
   * Ширина посуды
   */
  @Column(name = "width")
  @Min(value = 1, message = "so small")
  private float width;

  /**
   * Глубина посуды
   */
  @Column(name = "depth")
  @Min(value = 1, message = "so small")
  private float depth;

  /**
   * Цена посуды
   */
  @Column(name = "price")
  @Min(value = 1, message = "so small")
  private float price;

  /**
   * Количество посуды
   */
  @Column(name = "quantity")
  @Min(value = 0, message = "so small")
  private int quantity;

  public Vessel() {
  }


  public Vessel(String name, String color, String material, float width, float depth, float price,
      int quantity) {
    this.name = name;
    this.color = color;
    this.material = material;
    this.width = width;
    this.depth = depth;
    this.price = price;
    this.quantity = quantity;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getMaterial() {
    return material;
  }

  public void setMaterial(String material) {
    this.material = material;
  }

  public float getWidth() {
    return width;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public float getDepth() {
    return depth;
  }

  public void setDepth(float depth) {
    this.depth = depth;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "---Посуда с id: " + id + "---\n" +
        "Название: " + name + '\n' +
        "Цвет: " + color + '\n' +
        "Материал: " + material + '\n' +
        "Ширина: " + width + " См.\n" +
        "Глубина: " + depth + " См.\n" +
        "Цена: " + price + " Руб.\n" +
        "Количество: " + quantity + "\n";
  }
}
