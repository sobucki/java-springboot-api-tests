package br.com.sobucki.productmanager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
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
}
