package br.com.sobucki.productmanager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.sobucki.productmanager.ProductManagerApplication;
import br.com.sobucki.productmanager.dto.ProductDTO;
import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.service.ProductService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ProductManagerApplication.class)
@AutoConfigureMockMvc
@DisplayName("Testes do Controlador de Produtos")
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ProductService productService;

  @Test
  @DisplayName("Should return a product by ID")
  void shouldReturnProductById() throws Exception {
    UUID id = UUID.randomUUID();
    Product product = new Product(id, "Tênis", "Tênis Nike", new BigDecimal("199.90"));
    when(productService.getProductById(id)).thenReturn(product);

    ObjectMapper mapper = new ObjectMapper();

    String expectedJson = """
        {
          "createdDate":null,
          "lastUpdatedDate":null,
          "createdBy":null,
          "updatedBy":null,
          "id": "%s",
        "name": "Tênis",
              "price": 199.90,
              "description": "Tênis Nike"
        }
        """.formatted(
        id);

    MvcResult result = mockMvc.perform(get("/api/products/" + id))
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

    UUID id = UUID.randomUUID();
    when(productService.getProductById(id)).thenReturn(null);

    ObjectMapper mapper = new ObjectMapper();

    MvcResult result = mockMvc.perform(get("/api/products/" + id))
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
    UUID id1 = UUID.randomUUID();
    UUID id2 = UUID.randomUUID();
    ProductDTO product1 = new ProductDTO(id1, "Tênis", "Tênis Nike", new BigDecimal("199.90"));
    ProductDTO product2 = new ProductDTO(id2, "Camisa", "Camisa Adidas", new BigDecimal("99.90"));

    when(productService.getAllProducts()).thenReturn(List.of(product1, product2));

    ObjectMapper mapper = new ObjectMapper();

    String expectedJson = """
        [
          {
            "id": "%s",
            "name": "Tênis",
            "price": 199.90,
            "description": "Tênis Nike"
          },
          {
            "id": "%s",
            "name": "Camisa",
            "price": 99.90,
            "description": "Camisa Adidas"
          }
        ]
        """.formatted(id1, id2);

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
    UUID id = UUID.randomUUID();
    Product product = new Product(id, "Tênis", "Tênis Nike", new BigDecimal("199.90"));
    when(productService.createProduct(any(ProductDTO.class))).thenReturn(product);

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
          "createdDate":null,
          "lastUpdatedDate":null,
          "createdBy":null,
          "updatedBy":null,
          "id": "%s",
          "name": "Tênis",
          "description": "Tênis Nike",
          "price": 199.90
        }
        """.formatted(id);

    MvcResult result = mockMvc.perform(post("/api/products")
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isCreated())
        .andReturn();

    JsonNode expected = mapper.readTree(expectedJson);
    JsonNode actual = mapper.readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should update a product")
  void shouldUpdateProduct() throws Exception {
    UUID id = UUID.randomUUID();
    Product product = new Product(id, "Tênis", "Tênis Nike", new BigDecimal("199.90"));
    when(productService.getProductById(id)).thenReturn(product);
    when(productService.updateProduct(any(UUID.class), any(Product.class))).thenReturn(product);

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
          "createdDate":null,
          "lastUpdatedDate":null,
          "createdBy":null,
          "updatedBy":null,
          "id": "%s",
          "name": "Tênis",
          "description": "Tênis Nike",
          "price": 199.90
        }
        """.formatted(id);

    MvcResult result = mockMvc.perform(put("/api/products/" + id)
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
    UUID id = UUID.randomUUID();
    when(productService.getProductById(id)).thenReturn(null);
    when(productService.updateProduct(any(UUID.class), any(Product.class))).thenReturn(null);

    ObjectMapper mapper = new ObjectMapper();

    String inputJson = """
        {
          "name": "Tênis",
          "description": "Tênis Nike",
          "price": "199.90"
        }
        """;

    MvcResult result = mockMvc.perform(put("/api/products/" + id)
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isNotFound())
        .andReturn();

    JsonNode expected = mapper.readTree("{\"error\":\"Product not found\"}");
    JsonNode actual = mapper.readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should remove a product")
  void shouldRemoveProduct() throws Exception {
    UUID id = UUID.randomUUID();
    Product product = new Product(id, "Tênis", "Tênis Nike", new BigDecimal("199.90"));
    when(productService.getProductById(id)).thenReturn(product);

    mockMvc.perform(delete("/api/products/" + id))
        .andExpect(status().isNoContent());

  }

  @Test
  @DisplayName("Should return 404 when removing a non-existing product")
  void shouldReturn404WhenRemovingNonExistingProduct() throws Exception {
    UUID id = UUID.randomUUID();
    when(productService.getProductById(id)).thenReturn(null);

    MvcResult result = mockMvc.perform(delete("/api/products/" + id))
        .andExpect(status().isNotFound())
        .andReturn();

    JsonNode expected = new ObjectMapper().readTree("{\"error\":\"Product not found\"}");
    JsonNode actual = new ObjectMapper().readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should return 400 when product name is blank")
  void shouldReturn400WhenProductNameIsBlank() throws Exception {
    String inputJson = """
        {
          "name": "",
          "description": "Tênis Nike",
          "price": "199.90"
        }
        """;

    MvcResult result = mockMvc.perform(post("/api/products")
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isBadRequest())
        .andReturn();

    JsonNode expected = new ObjectMapper().readTree("{\"name\":\"Name is mandatory\"}");
    JsonNode actual = new ObjectMapper().readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should return 400 when product description is blank")
  void shouldReturn400WhenProductDescriptionIsBlank() throws Exception {
    String inputJson = """
        {
          "name": "Tênis",
          "description": "",
          "price": "199.90"
        }
        """;

    MvcResult result = mockMvc.perform(post("/api/products")
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isBadRequest())
        .andReturn();

    JsonNode expected = new ObjectMapper().readTree("{\"description\":\"Description is mandatory\"}");
    JsonNode actual = new ObjectMapper().readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  // @Test
  // @DisplayName("Should return 400 when product price is blank")
  // void shouldReturn400WhenProductPriceIsBlank() throws Exception {
  // String inputJson = """
  // {
  // "name": "Tênis",
  // "description": "Tênis Nike",
  // "price": ""
  // }
  // """;

  // MvcResult result = mockMvc.perform(post("/api/products")
  // .contentType("application/json")
  // .content(inputJson))
  // .andExpect(status().isBadRequest())
  // .andReturn();

  // JsonNode expected = new ObjectMapper().readTree("{\"price\":\"Price is not a
  // number\"}");
  // JsonNode actual = new
  // ObjectMapper().readTree(result.getResponse().getContentAsString());

  // assertEquals(expected, actual);
  // }

  @Test
  @DisplayName("Should return 400 when product price is not provided")
  void shouldReturn400WhenProductPriceIsNotProvided() throws Exception {
    String inputJson = """
        {
          "name": "Tênis",
          "description": "Tênis Nike"
        }
        """;

    MvcResult result = mockMvc.perform(post("/api/products")
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isBadRequest())
        .andReturn();

    JsonNode expected = new ObjectMapper().readTree("{\"price\":\"Price is mandatory\"}");
    JsonNode actual = new ObjectMapper().readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should return 400 when price is not a number")
  void shouldReturn400WhenPriceIsNotNumber() throws Exception {
    String inputJson = """
        {
          "name": "Tênis",
          "description": "Tênis Nike",
          "price": "not-a-number"
        }
        """;

    MvcResult result = mockMvc.perform(post("/api/products")
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isBadRequest())
        .andReturn();

    JsonNode expected = new ObjectMapper()
        .readTree("{\"error\":\"Invalid input: 'not-a-number'. Expected a valid number.\"}");
    JsonNode actual = new ObjectMapper().readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should return 400 when price has more than two decimals")
  void shouldReturn400WhenPriceHasTooManyDecimalPlaces() throws Exception {
    String inputJson = """
        {
          "name": "Tênis",
          "description": "Tênis Nike",
          "price": "199.999"
        }
        """;

    MvcResult result = mockMvc.perform(post("/api/products")
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isBadRequest())
        .andReturn();

    JsonNode expected = new ObjectMapper()
        .readTree("{\"price\":\"numeric value out of bounds (<38 digits>.<2 digits> expected)\"}");
    JsonNode actual = new ObjectMapper()
        .readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should return 400 when price is negative")
  void shouldReturn400WhenPriceIsNegative() throws Exception {
    String inputJson = """
        {
          "name": "Tênis",
          "description": "Tênis Nike",
          "price": "-10.00"
        }
        """;

    MvcResult result = mockMvc.perform(post("/api/products")
        .contentType("application/json")
        .content(inputJson))
        .andExpect(status().isBadRequest())
        .andReturn();

    JsonNode expected = new ObjectMapper()
        .readTree("{\"price\":\"Price must be at least 0.01\"}");
    JsonNode actual = new ObjectMapper()
        .readTree(result.getResponse().getContentAsString());

    assertEquals(expected, actual);
  }

}
