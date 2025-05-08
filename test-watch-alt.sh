#!/bin/bash

echo "Iniciando modo de hot reload para testes (método alternativo)..."
echo "Os testes serão executados automaticamente quando houver alterações nos arquivos."
echo "Pressione Ctrl+C para sair."

# Executa testes inicialmente
./mvnw test

# Função para executar os testes
run_tests() {
  echo -e "\n\n====== Executando testes... ======"
  ./mvnw test
}

# Loop principal para monitorar alterações
while true; do
  # Armazena a data da última modificação
  last_mod=$(find src -type f -name "*.java" -o -name "*.xml" -o -name "*.properties" | xargs stat -f "%m %N" | sort -nr | head -1)
  
  # Aguarda 2 segundos
  sleep 2
  
  # Verifica se houve modificação
  current_mod=$(find src -type f -name "*.java" -o -name "*.xml" -o -name "*.properties" | xargs stat -f "%m %N" | sort -nr | head -1)
  
  if [ "$last_mod" != "$current_mod" ]; then
    run_tests
  fi
done 