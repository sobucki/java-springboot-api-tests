#!/bin/bash

echo "Iniciando ambiente de desenvolvimento..."

# Verifica se o Docker está rodando
if ! docker info > /dev/null 2>&1; then
  echo "Erro: Docker não está rodando. Por favor, inicie o Docker e tente novamente."
  exit 1
fi

# Inicia o banco de dados com Docker Compose
echo "Iniciando banco de dados PostgreSQL..."
docker-compose up -d postgres

# Espera o banco de dados iniciar completamente (5 segundos)
echo "Aguardando o banco de dados iniciar..."
sleep 5

# Executa a aplicação Spring Boot com hot-reload ativado
echo "Iniciando a aplicação Spring Boot..."
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.jvmArguments="-XX:TieredStopAtLevel=1 -Dspring.devtools.restart.enabled=true" 