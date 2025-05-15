# Product Manager

Sistema de gerenciamento de produtos com repositório PostgreSQL.

## Requisitos

- Java 17+
- Maven
- Docker
- Docker Compose

## Configuração do Banco de Dados

Para iniciar o banco de dados PostgreSQL usando Docker:

```bash
# Inicia o PostgreSQL em background
docker-compose up -d
```

## Como Executar a Aplicação

```bash
# Compilar o projeto
mvn clean install

# Executar a aplicação
mvn spring-boot:run
```

## Executando os Testes

```bash
# Executar os testes (os testes usam Testcontainers, não é necessário o Docker Compose rodando)
mvn test
```

## Estrutura do Projeto

- `src/main/java/br/com/sobucki/productmanager/model`: Modelos de dados
- `src/main/java/br/com/sobucki/productmanager/repository`: Repositórios para acesso a dados
- `src/main/resources/application.properties`: Configurações da aplicação

## Tecnologias Utilizadas

- Spring Boot
- Spring Data JPA
- PostgreSQL
- Testcontainers para testes de integração
- Docker para containerização 

## Geração de Dados de Teste

Este projeto utiliza a biblioteca [JavaFaker](https://github.com/DiUS/java-faker) para geração de dados de teste realistas, com suporte ao português brasileiro. Essa funcionalidade está disponível apenas em ambientes de desenvolvimento (`dev`, `test` e `local`).

### Endpoints para Geração de Dados

Os seguintes endpoints estão disponíveis para geração de dados de teste:

- `POST /api/test-data/products/{quantity}` - Gera a quantidade especificada de produtos com nomes e preços realistas
- `POST /api/test-data/products/price-range/{quantity}?minPrice=X&maxPrice=Y` - Gera produtos com preços dentro de um intervalo específico
- `DELETE /api/test-data/products` - Remove todos os produtos do banco de dados

### Uso Programático

Para usar a biblioteca programaticamente, utilize a classe `FakeDataGenerator` ou `ProductDataFactory`:

```java
// Gerar um único produto
Product product = FakeDataGenerator.generate(Product.class);

// Gerar uma lista de produtos
List<Product> products = FakeDataGenerator.generate(Product.class, 10);

// Gerar produtos com preço dentro de um intervalo específico
Product product = FakeDataGenerator.generateProductWithPriceRange(
    new BigDecimal("10.00"), new BigDecimal("100.00"));

// Gerar produtos com nomes realistas
Product realisticProduct = ProductDataFactory.createRealisticProduct();
```

### Geração de Produtos Realistas

A classe `ProductDataFactory` gera produtos com nomes, descrições e preços que parecem reais usando a biblioteca [JavaFaker](https://github.com/DiUS/java-faker) com suporte ao português brasileiro:

- Nomes de produtos variados e realistas
- Descrições detalhadas com termos comerciais apropriados
- Preços formatados de forma realista (ex.: R$ 99,90 ou R$ 1.000,00)

Exemplos de produtos gerados:
- "Notebook Premium Dell 8000"
- "Smartphone Samsung Galaxy Ultra"
- "TV LED 55 polegadas Smart 4K"

A biblioteca JavaFaker oferece uma grande variedade de geradores para diferentes tipos de dados, incluindo nomes de empresas, produtos, materiais e muito mais, tudo isso com suporte ao idioma português do Brasil.

Para gerar produtos realistas, use:

```java
// Gerar um único produto realista
Product product = ProductDataFactory.createRealisticProduct();

// Ou através do serviço
List<Product> products = testDataService.generateRealisticProducts(5);
```

Esta opção é ideal quando você precisa de dados que se pareçam com produtos reais para demonstrações e interfaces de usuário.

### Utilização em Testes

O `FakeDataGenerator` pode ser usado em testes unitários e de integração para criar dados de teste rapidamente:

```java
@Test
public void testProductService() {
    // Arrange
    Product product = FakeDataGenerator.generate(Product.class);
    when(productRepository.findById(any())).thenReturn(Optional.of(product));
    
    // Act & Assert
    // ...
}
```

O JavaFaker permite personalização e geração de diversos tipos de dados realistas, como nomes, endereços, produtos e muito mais, tudo com suporte ao idioma português do Brasil. 