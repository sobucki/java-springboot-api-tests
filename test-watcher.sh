#!/bin/bash

echo "Iniciando modo de hot reload para testes..."
echo "Os testes serão executados automaticamente quando houver alterações nos arquivos."
echo "Pressione Ctrl+C para sair."

# Executa o Maven com o Spring Boot DevTools ativado para hot reload
./mvnw spring-boot:test-run -Dspring-boot.run.profiles=test 