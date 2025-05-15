package br.com.sobucki.productmanager.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sobucki.productmanager.model.Product;
import br.com.sobucki.productmanager.repository.product.ProductRepository;
import br.com.sobucki.productmanager.util.FakeDataGenerator;
import br.com.sobucki.productmanager.util.ProductDataFactory;

/**
 * Serviço para geração e persistência de dados de teste.
 */
@Service
@Profile({ "dev", "test", "local" })
public class TestDataService {

    private final ProductRepository productRepository;

    public TestDataService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Gera e salva produtos aleatórios no banco de dados.
     *
     * @param quantity quantidade de produtos a serem gerados
     * @return lista de produtos gerados e persistidos
     */
    @Transactional
    public List<Product> generateProducts(int quantity) {
        List<Product> products = FakeDataGenerator.generate(Product.class, quantity);

        // Garantir que todos os IDs estão nulos para evitar conflitos
        products.forEach(product -> product.setId(null));

        return productRepository.saveAll(products);
    }

    /**
     * Gera e salva produtos com nomes e preços realistas no banco de dados.
     *
     * @param quantity quantidade de produtos a serem gerados
     * @return lista de produtos gerados e persistidos
     */
    @Transactional
    public List<Product> generateRealisticProducts(int quantity) {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            products.add(ProductDataFactory.createRealisticProduct());
        }

        return productRepository.saveAll(products);
    }

    /**
     * Gera e salva produtos aleatórios no banco de dados com preço dentro de um
     * intervalo específico.
     *
     * @param quantity quantidade de produtos a serem gerados
     * @param minPrice preço mínimo
     * @param maxPrice preço máximo
     * @return lista de produtos gerados e persistidos
     */
    @Transactional
    public List<Product> generateProductsWithPriceRange(int quantity, BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            products.add(FakeDataGenerator.generateProductWithPriceRange(minPrice, maxPrice));
        }

        // Garantir que todos os IDs estão nulos para evitar conflitos
        products.forEach(product -> product.setId(null));

        return productRepository.saveAll(products);
    }

    /**
     * Gera produtos aleatórios sem salvá-los no banco de dados.
     *
     * @param quantity quantidade de produtos a serem gerados
     * @return lista de produtos gerados
     */
    public List<Product> generateProductsWithoutSaving(int quantity) {
        return FakeDataGenerator.generate(Product.class, quantity);
    }

    /**
     * Gera produtos com nomes realistas sem salvá-los no banco de dados.
     *
     * @param quantity quantidade de produtos a serem gerados
     * @return lista de produtos gerados
     */
    public List<Product> generateRealisticProductsWithoutSaving(int quantity) {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            products.add(ProductDataFactory.createRealisticProduct());
        }

        return products;
    }

    /**
     * Limpa todos os produtos do banco de dados.
     */
    @Transactional
    public void clearAllProducts() {
        productRepository.deleteAll();
    }
}