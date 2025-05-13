package br.com.sobucki.productmanager.integration;

import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void shouldCreateProductAndFindById() {
        // Arrange
        Product product = new Product();
        product.setName("Teste de Integração");
        product.setDescription("Produto para teste end-to-end");
        product.setPrice("99.99");

        // Act - Cria o produto via API
        ResponseEntity<Product> createResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/products",
                product,
                Product.class
        );

        // Assert - Verifica o status da resposta
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        // Obter o produto criado
        Product createdProduct = createResponse.getBody();
        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getId()).isNotNull();

        // Act - Busca o produto via API
        ResponseEntity<Product> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/products/" + createdProduct.getId(),
                Product.class
        );

        // Assert - Verifica se o produto foi encontrado
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getName()).isEqualTo("Teste de Integração");
        
        // Verifica se o produto foi persistido no banco de dados
        Product foundProduct = productRepository.findById(createdProduct.getId());
        assertThat(foundProduct).isNotNull();
    }

    @Test
    public void shouldListAllProducts() {
        // Arrange - Limpa o repositório (deletando produtos um a um)
        List<Product> allProducts = productRepository.findAll();
        for (Product product : allProducts) {
            productRepository.delete(product.getId());
        }
        
        // Adiciona produtos de teste
        Product product1 = new Product();
        product1.setName("Produto 1");
        product1.setDescription("Descrição 1");
        product1.setPrice("10.00");
        
        Product product2 = new Product();
        product2.setName("Produto 2");
        product2.setDescription("Descrição 2");
        product2.setPrice("20.00");
        
        productRepository.save(product1);
        productRepository.save(product2);

        // Act - Busca todos os produtos via API
        ResponseEntity<List> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/products",
                List.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
    }
} 