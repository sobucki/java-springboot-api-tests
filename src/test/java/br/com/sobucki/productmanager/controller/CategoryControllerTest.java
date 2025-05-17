package br.com.sobucki.productmanager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sobucki.productmanager.ProductManagerApplication;
import br.com.sobucki.productmanager.dto.CategoryDTO;
import br.com.sobucki.productmanager.dto.ProductDTO;
import br.com.sobucki.productmanager.model.Category;
import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.service.CategoryService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ProductManagerApplication.class)
@AutoConfigureMockMvc
@DisplayName("Testes do Controlador de Categoria")
public class CategoryControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private CategoryService categoryService;

  @Test
  @DisplayName("Should list all categories")
  public void shouldListAllCategories() throws Exception {
    UUID id1 = UUID.randomUUID();
    UUID id2 = UUID.randomUUID();
    CategoryDTO product1 = new CategoryDTO(id1, "Acessórios", "Acessórios de moda");
    CategoryDTO product2 = new CategoryDTO(id2, "Vestuário", "Roupas e acessórios");

    when(categoryService.getAllCategories()).thenReturn(List.of(product1, product2));

    ObjectMapper mapper = new ObjectMapper();

    String expectedJson = """
        [
          {
            "id": "%s",
            "name": "Acessórios",
            "description": "Acessórios de moda"
          },
          {
            "id": "%s",
            "name": "Vestuário",
            "description": "Roupas e acessórios"
          }
        ]
        """.formatted(id1, id2);

    MvcResult result = mockMvc.perform(get("/api/categories"))
        .andExpect(status().isOk())
        .andReturn();

    String actualJson = result.getResponse().getContentAsString();

    JsonNode expected = mapper.readTree(expectedJson);
    JsonNode actual = mapper.readTree(actualJson);

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should create a new category")
  public void shouldCreateNewCategory() throws Exception {
    UUID id = UUID.randomUUID();
    CategoryDTO category = new CategoryDTO(id, "Category 1", "Category description 1");
    when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(category);

    ObjectMapper mapper = new ObjectMapper();

    String inputJson = """
        {
         "name": "%s",
          "description": "%s"
        }
        """.formatted(category.getName(), category.getDescription());

    String expectedJson = """
        {
          "id": "%s",
          "name": "%s",
          "description": "%s"
        }
        """.formatted(id, category.getName(), category.getDescription());

    MvcResult result = mockMvc.perform(post("/api/categories")
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isCreated())
        .andReturn();

    JsonNode expected = mapper.readTree(expectedJson);
    JsonNode actual = mapper.readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should return 400 when creating a category with invalid data")
  public void shouldReturn400WhenCreatingCategoryWithInvalidData() throws Exception {
    String inputJson = """
        {
          "name": "",
          "description": ""
        }
        """;

    String expectedJson = """
        {
          "name": "Name is mandatory",
          "description": "Description is mandatory"
        }
        """;

    ObjectMapper mapper = new ObjectMapper();

    MvcResult result = mockMvc.perform(post("/api/categories")
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isBadRequest())
        .andReturn();

    JsonNode expected = mapper.readTree(expectedJson);
    JsonNode actual = mapper.readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should update a category")
  public void shouldUpdateCategory() throws Exception {
    UUID id = UUID.randomUUID();
    CategoryDTO updateTo = new CategoryDTO(id, "Category 2", "New Description");
    CategoryDTO existingCategory = new CategoryDTO(id, "Category 1", "Category Description");
    when(categoryService.getCategoryById(id)).thenReturn(existingCategory);
    when(categoryService.updateCategory(any(UUID.class), any(CategoryDTO.class))).thenReturn(updateTo);

    ObjectMapper mapper = new ObjectMapper();

    String inputJson = """
        {
          "name": "%s",
          "description": "%s"
        }
        """.formatted(updateTo.getName(), updateTo.getDescription());

    String expectedJson = """
        {
          "id": "%s",
          "name": "Category 2",
          "description": "New Description"
        }
        """.formatted(id);

    MvcResult result = mockMvc.perform(put("/api/categories/" + id)
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isOk())
        .andReturn();

    JsonNode expected = mapper.readTree(expectedJson);
    JsonNode actual = mapper.readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should return 404 when updating a category that does not exist")
  void shouldReturn404WhenUpdatingNonExistingCategory() throws Exception {
    UUID id = UUID.randomUUID();
    when(categoryService.getCategoryById(id)).thenReturn(null);
    when(categoryService.updateCategory(any(UUID.class), any(CategoryDTO.class))).thenReturn(null);

    ObjectMapper mapper = new ObjectMapper();

    String inputJson = """
        {
          "name": "Some category",
          "description": "Description of the category"
        }
        """;

    MvcResult result = mockMvc.perform(put("/api/categories/" + id)
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isNotFound())
        .andReturn();

    JsonNode expected = mapper.readTree("{\"error\":\"Category not found\"}");
    JsonNode actual = mapper.readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should delete a category")
  public void shouldDeleteCategory() throws Exception {
    UUID id = UUID.randomUUID();
    CategoryDTO category = new CategoryDTO(id, "Category 1", "Category description 1");
    when(categoryService.getCategoryById(id)).thenReturn(category);

    mockMvc.perform(delete("/api/categories/" + id))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("Should return 404 when deleting a category that does not exist")
  public void shouldReturn404WhenDeletingNonExistingCategory() throws Exception {
    UUID id = UUID.randomUUID();
    when(categoryService.getCategoryById(id)).thenReturn(null);

    mockMvc.perform(delete("/api/categories/" + id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("Category not found"));
  }
}
