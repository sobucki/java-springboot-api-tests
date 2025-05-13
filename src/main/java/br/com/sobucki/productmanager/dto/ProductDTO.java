package br.com.sobucki.productmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {

  private Long id;

  @NotBlank(message = "Name is mandatory")
  private String name;
  private String description;
  private String price;

  public ProductDTO() {
    // Construtor vazio necessário para JPA
  }

  public ProductDTO(Long id, String name, String description, String price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

}
