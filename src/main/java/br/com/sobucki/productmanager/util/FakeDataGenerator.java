package br.com.sobucki.productmanager.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.javafaker.Faker;

import br.com.sobucki.productmanager.model.Product;

/**
 * Utilitário para gerar dados falsos para testes usando a biblioteca JavaFaker.
 */
public class FakeDataGenerator {

    private static final Random RANDOM = new Random();
    private static final Faker FAKER = new Faker(new Locale("pt", "BR"));

    /**
     * Gera um único objeto falso do tipo especificado.
     *
     * @param <T>   o tipo de objeto a ser gerado
     * @param clazz a classe do objeto a ser gerado
     * @return um objeto aleatório do tipo especificado
     */
    public static <T> T generate(Class<T> clazz) {
        if (clazz == Product.class) {
            return (T) generateProduct();
        }
        
        throw new UnsupportedOperationException("Geração de " + clazz.getSimpleName() + " não suportada pelo JavaFaker");
    }

    /**
     * Gera uma lista de objetos falsos do tipo especificado.
     *
     * @param <T>      o tipo de objeto a ser gerado
     * @param clazz    a classe do objeto a ser gerado
     * @param quantity a quantidade de objetos a serem gerados
     * @return uma lista de objetos aleatórios do tipo especificado
     */
    public static <T> List<T> generate(Class<T> clazz, int quantity) {
        return Stream.generate(() -> generate(clazz))
                .limit(quantity)
                .collect(Collectors.toList());
    }

    /**
     * Gera uma lista de objetos falsos usando um fornecedor personalizado.
     *
     * @param <T>        o tipo de objeto a ser gerado
     * @param supplier   o fornecedor de objetos
     * @param quantity   a quantidade de objetos a serem gerados
     * @return uma lista de objetos gerados pelo fornecedor
     */
    public static <T> List<T> generate(Supplier<T> supplier, int quantity) {
        List<T> items = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            items.add(supplier.get());
        }
        return items;
    }

    /**
     * Gera um produto com dados aleatórios usando JavaFaker.
     *
     * @return o produto gerado
     */
    private static Product generateProduct() {
        Product product = new Product();
        
        product.setName(FAKER.commerce().productName());
        product.setDescription(FAKER.lorem().paragraph());
        
        // Gera preços entre R$ 10 e R$ 2000
        double price = 10 + (RANDOM.nextDouble() * 1990);
        product.setPrice(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP));
        
        return product;
    }
    
    /**
     * Gera um produto com preço dentro do intervalo especificado.
     *
     * @param minPrice preço mínimo
     * @param maxPrice preço máximo
     * @return um produto com preço dentro do intervalo
     */
    public static Product generateProductWithPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        Product product = generateProduct();
        
        double range = maxPrice.subtract(minPrice).doubleValue();
        double randomPrice = minPrice.doubleValue() + (RANDOM.nextDouble() * range);
        product.setPrice(new BigDecimal(randomPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
        
        return product;
    }
} 