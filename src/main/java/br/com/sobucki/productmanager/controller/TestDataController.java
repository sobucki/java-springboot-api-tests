package br.com.sobucki.productmanager.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.service.TestDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador para operações de geração de dados de teste.
 * Esse controlador só deve ser usado em ambiente de desenvolvimento.
 */
@RestController
@RequestMapping("/api/test-data")
@Tag(name = "Dados de Teste", description = "Endpoints para gerenciamento de dados de teste")
@Profile({"dev", "test", "local"})
public class TestDataController {

    private final TestDataService testDataService;

    public TestDataController(TestDataService testDataService) {
        this.testDataService = testDataService;
    }
    
    @GetMapping("/status")
    @Operation(summary = "Verifica o status do serviço de dados de teste", 
               description = "Endpoint simples para verificar se o controlador de dados de teste está disponível")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Serviço de dados de teste está ativo");
    }

    @PostMapping("/products/{quantity}")
    @Operation(summary = "Gera produtos com nomes e preços realistas", 
               description = "Cria a quantidade especificada de produtos com nomes e preços que parecem reais")
    public ResponseEntity<List<Product>> generateProducts(
            @PathVariable int quantity) {
        List<Product> products = testDataService.generateRealisticProducts(quantity);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/products/price-range/{quantity}")
    @Operation(summary = "Gera produtos com preço em um intervalo específico", 
               description = "Cria a quantidade especificada de produtos com preços dentro do intervalo definido")
    public ResponseEntity<List<Product>> generateProductsWithPriceRange(
            @PathVariable int quantity,
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<Product> products = testDataService.generateProductsWithPriceRange(quantity, minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/products")
    @Operation(summary = "Remove todos os produtos", 
               description = "Limpa todos os produtos do banco de dados")
    public ResponseEntity<Void> clearAllProducts() {
        testDataService.clearAllProducts();
        return ResponseEntity.noContent().build();
    }
} 