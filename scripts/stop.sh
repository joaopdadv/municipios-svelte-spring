#!/bin/bash

BACKEND_PID=$(lsof -i:8080 -t -sTCP:LISTEN)

if [ -n "$BACKEND_PID" ]; then
  kill "$BACKEND_PID"
  echo "Backend (processo 'java') na porta 8080 parado."
else
  echo "Nenhum processo 'java' encontrado na porta 8080."
fi

FRONTEND_PID=$(lsof -i:5173 -t -sTCP:LISTEN)

if [ -n "$FRONTEND_PID" ]; then
  kill "$FRONTEND_PID"
  echo "Frontend (processo 'node') na porta 5173 parado."
else
  echo "Nenhum processo 'node' encontrado na porta 5173."
fi
