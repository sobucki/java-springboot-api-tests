package br.com.sobucki.productmanager.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {
  private UUID id;

  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotBlank(message = "Description is mandatory")
  private String description;

  public CategoryDTO() {
  }

  public CategoryDTO(UUID id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
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
}
