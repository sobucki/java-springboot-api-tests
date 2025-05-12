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