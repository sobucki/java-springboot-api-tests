package br.com.sobucki.productmanager.controller;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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
  @DisplayName("Deve retornar um produto quando buscar pelo ID")
  void shouldReturnProductById() throws Exception {
    // Arrange
    Product product = new Product(1L, "Tênis", "Tênis Nike", "199.90");
    when(productService.getProductById(1L)).thenReturn(product);

    // Act & Assert
    mockMvc.perform(get("/api/products/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Tênis"))
        .andExpect(jsonPath("$.price").value("199.90"));

    
  }
}
