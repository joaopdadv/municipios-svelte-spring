#!/bin/bash

clear

./scripts/stop.sh

export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh" # Carrega o nvm

# Inicia o backend
echo "Iniciando o backend..."
(
  cd ./back || exit
  ./mvnw spring-boot:run &
)

echo "Iniciando o frontend..."
(
  cd ./front || exit
  nvm use 20
  npm run dev &
)

echo "Servidores iniciados. Backend e frontend estão rodando."
