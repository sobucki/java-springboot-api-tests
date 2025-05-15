package br.com.sobucki.productmanager.util;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;

import br.com.sobucki.productmanager.model.Product;

/**
 * Fábrica de dados realistas para produtos.
 * Usa o JavaFaker para gerar dados que parecem reais com suporte ao português
 * do Brasil.
 */
public class ProductDataFactory {

    private static final Random RANDOM = new Random();
    private static final Faker FAKER = new Faker(new Locale("pt", "BR"));

    /**
     * Gera um produto com dados realistas usando JavaFaker.
     * 
     * @return o produto gerado
     */
    public static Product createRealisticProduct() {
        Product product = new Product();

        String productName = generateProductName();

        product.setName(productName);
        product.setDescription(generateDescription(productName));
        product.setPrice(generatePrice());

        return product;
    }

    /**
     * Gera um nome de produto realista.
     * 
     * @return um nome de produto que parece real
     */
    private static String generateProductName() {
        // Seleciona aleatoriamente um dos vários formatos possíveis para dar variedade
        int nameFormat = RANDOM.nextInt(5);

        switch (nameFormat) {
            case 0:
                // Formato: "Samsung Galaxy S21"
                return FAKER.company().name() + " " + FAKER.commerce().productName();

            case 1:
                // Formato: "iPhone Pro 12 Max"
                return FAKER.commerce().productName() + " " + FAKER.app().name();

            case 2:
                // Formato: "TV LED 55 polegadas 4K"
                String material = FAKER.commerce().material();
                String product = FAKER.commerce().productName();
                int size = RANDOM.nextInt(100) + 10;
                return product + " " + material + " " + size + getUnitFor(product);

            case 3:
                // Formato: "Fritadeira Elétrica Air Fryer"
                return FAKER.commerce().productName() + " " + FAKER.commerce().material() + " " +
                        FAKER.app().name();

            default:
                // Formato: "Mesa de Jantar Moderna com 6 Cadeiras"
                return FAKER.commerce().productName() + " " + FAKER.commerce().color() + " " +
                        FAKER.commerce().promotionCode();
        }
    }

    /**
     * Retorna uma unidade apropriada para um tipo de produto.
     * 
     * @param productType o tipo de produto
     * @return uma unidade de medida adequada
     */
    private static String getUnitFor(String productType) {
        productType = productType.toLowerCase();

        if (productType.contains("tv") || productType.contains("monitor")) {
            return " polegadas";
        } else if (productType.contains("computador") || productType.contains("notebook")) {
            return "GB";
        } else if (productType.contains("mesa") || productType.contains("sofá")) {
            return " lugares";
        } else {
            return " " + FAKER.commerce().productName().split(" ")[0];
        }
    }

    /**
     * Gera uma descrição realista para o produto.
     * 
     * @param productName o nome do produto
     * @return uma descrição elaborada
     */
    private static String generateDescription(String productName) {
        String department = FAKER.commerce().department();
        String material = FAKER.commerce().material();
        String color = FAKER.commerce().color();

        StringBuilder description = new StringBuilder();
        description.append(productName)
                .append(" - ")
                .append(FAKER.commerce().promotionCode())
                .append(". ");

        description.append("Produto de alta qualidade da categoria ")
                .append(department)
                .append(", feito em ")
                .append(material)
                .append(" na cor ")
                .append(color)
                .append(". ");

        description.append(FAKER.company().bs())
                .append(". ")
                .append(FAKER.company().catchPhrase())
                .append(".");

        return description.toString();
    }

    /**
     * Gera um preço realista para um produto.
     * 
     * @return um valor de preço que parece real
     */
    private static BigDecimal generatePrice() {
        // Gera preços entre R$ 10 e R$ 5000
        double basePrice = 10 + (RANDOM.nextDouble() * 4990);

        // Arredonda para criar preços que pareçam reais (199,90 em vez de 203,47)
        int intValue = (int) basePrice;

        if (RANDOM.nextInt(10) < 7) { // 70% de chance de preços terminados em ,99 ou ,90
            // Preços terminados em ,99 ou ,90
            intValue = (intValue / 10) * 10;
            return new BigDecimal(intValue + (RANDOM.nextBoolean() ? 9.99 : 9.90))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            // Preços "redondos" como 100, 200, 500
            intValue = (intValue / 100) * 100;
            return new BigDecimal(intValue)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }
}