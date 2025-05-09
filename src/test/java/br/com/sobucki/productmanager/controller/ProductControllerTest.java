package br.com.sobucki.productmanager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

  @Test
  @DisplayName("Should list all products")
  void shouldListAllProducts() throws Exception {

    Product product1 = new Product(1L, "Tênis", "Tênis Nike", "199.90");
    Product product2 = new Product(2L, "Camisa", "Camisa Adidas", "99.90");

    when(productService.getAllProducts()).thenReturn(List.of(product1, product2));

    ObjectMapper mapper = new ObjectMapper();

    String expectedJson = """
        [
          {
            "id": 1,
            "name": "Tênis",
            "price": "199.90",
            "description": "Tênis Nike"
          },
          {
            "id": 2,
            "name": "Camisa",
            "price": "99.90",
            "description": "Camisa Adidas"
          }
        ]
        """;

    MvcResult result = mockMvc.perform(get("/api/products"))
        .andExpect(status().isOk())
        .andReturn();

    String actualJson = result.getResponse().getContentAsString();

    JsonNode expected = mapper.readTree(expectedJson);
    JsonNode actual = mapper.readTree(actualJson);

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should add a new product")
  void shouldAddNewProduct() throws Exception {
    Product product = new Product(1L, "Tênis", "Tênis Nike", "199.90");
    when(productService.createProduct(any(Product.class))).thenReturn(product);

    ObjectMapper mapper = new ObjectMapper();

    String inputJson = """
        {
          "name": "Tênis",
          "description": "Tênis Nike",
          "price": "199.90"
        }
        """;

    String expectedJson = """
        {
          "id": 1,
          "name": "Tênis",
          "description": "Tênis Nike",
          "price": "199.90"
        }
        """;

    MvcResult result = mockMvc.perform(post("/api/products")
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isOk()) // ou .isCreated() se você retornar 201
        .andReturn();

    JsonNode expected = mapper.readTree(expectedJson);
    JsonNode actual = mapper.readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should update a product")
  void shouldUpdateProduct() throws Exception {
    Product product = new Product(1L, "Tênis", "Tênis Nike", "199.90");
    when(productService.getProductById(1L)).thenReturn(product);
    when(productService.updateProduct(any(Long.class), any(Product.class))).thenReturn(product);

    ObjectMapper mapper = new ObjectMapper();

    String inputJson = """
        {
          "name": "Tênis",
          "description": "Tênis Nike",
          "price": "199.90"
        }
        """;

    String expectedJson = """
        {
          "id": 1,
          "name": "Tênis",
          "description": "Tênis Nike",
          "price": "199.90"
        }
        """;

    MvcResult result = mockMvc.perform(put("/api/products/1")
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isOk())
        .andReturn();

    JsonNode expected = mapper.readTree(expectedJson);
    JsonNode actual = mapper.readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should return 404 when updating a non-existing product")
  void shouldReturn404WhenUpdatingNonExistingProduct() throws Exception {
    when(productService.getProductById(1L)).thenReturn(null);
    when(productService.updateProduct(any(Long.class), any(Product.class))).thenReturn(null);

    ObjectMapper mapper = new ObjectMapper();

    String inputJson = """
        {
          "name": "Tênis",
          "description": "Tênis Nike",
          "price": "199.90"
        }
        """;

    MvcResult result = mockMvc.perform(put("/api/products/1")
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isNotFound())
        .andReturn();

    JsonNode expected = mapper.readTree("{\"error\":\"Product not found\"}");
    JsonNode actual = mapper.readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  // @Test
  // @DisplayName("Should remove a product")
  // void shouldRemoveProduct() throws Exception {
  // Product product = new Product(1L, "Tênis", "Tênis Nike", "199.90");
  // when(productService.getProductById(1L)).thenReturn(product);

  // mockMvc.perform(delete("/api/products/1"))
  // .andExpect(status().isOk());

  // }

}
