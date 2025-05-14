package br.com.sobucki.productmanager.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.sobucki.productmanager.config.BigDecimalDeserializer;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {

  private UUID id;

  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotBlank(message = "Description is mandatory")
  private String description;

  @NotNull(message = "Price is mandatory")
  @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
  @Digits(integer = 38, fraction = 2)
  @JsonDeserialize(using = BigDecimalDeserializer.class)
  private BigDecimal price;

  public ProductDTO() {
    // Construtor vazio necessário para JPA
  }

  public ProductDTO(UUID id, String name, String description, BigDecimal price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

}
