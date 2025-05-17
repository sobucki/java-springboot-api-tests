#!/bin/bash

# Verifica se foi fornecido um argumento
if [ "$#" -eq 0 ]; then
  echo "Iniciando modo de hot reload para todos os testes..."
  TEST_PARAM=""
else
  echo "Iniciando modo de hot reload para a classe de teste: $1"
  TEST_PARAM="-Dtest=$1"
fi

echo "Os testes serão executados automaticamente quando houver alterações nos arquivos."
echo "Pressione Ctrl+C para sair."

# Executa testes inicialmente
./mvnw test $TEST_PARAM

# Função para executar os testes
run_tests() {
  echo -e "\n\n====== Executando testes... ======"
  ./mvnw test $TEST_PARAM
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