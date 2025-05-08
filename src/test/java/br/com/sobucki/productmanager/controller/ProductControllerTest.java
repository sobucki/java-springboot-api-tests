package br.com.sobucki.productmanager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.service.ProductService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@DisplayName("Testes do Controlador de Produtos")
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ProductService productService;

  @Test
  @DisplayName("Should return a product by ID")
  void shouldReturnProductById() throws Exception {

    Product product = new Product(1L, "Tênis", "Tênis Nike", "199.90");
    when(productService.getProductById(1L)).thenReturn(product);

    ObjectMapper mapper = new ObjectMapper();

    String expectedJson = """
        {
          "id": 1,
          "name": "Tênis",
          "price": "199.90",
          "description": "Tênis Nike"
        }
        """;

    MvcResult result = mockMvc.perform(get("/api/products/1"))
        .andExpect(status().isOk())
        .andReturn();

    String actualJson = result.getResponse().getContentAsString();

    JsonNode expected = mapper.readTree(expectedJson);
    JsonNode actual = mapper.readTree(actualJson);

    assertEquals(expected, actual);

  }

  @Test
  @DisplayName("Should return 404 when product not found")
  void shouldReturn404WhenProductNotFound() throws Exception {

    when(productService.getProductById(1L)).thenReturn(null);

    ObjectMapper mapper = new ObjectMapper();

    MvcResult result = mockMvc.perform(get("/api/products/1"))
        .andExpect(status().isNotFound())
        .andReturn();
    String actualJson = result.getResponse().getContentAsString();
    JsonNode expected = mapper.readTree("{\"error\":\"Product not found\"}");
    JsonNode actual = mapper.readTree(actualJson);
    assertEquals(expected, actual);
  }

}
