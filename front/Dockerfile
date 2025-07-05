# Etapa de build
FROM node:20 AS builder

ARG PUBLIC_API_BASE_URL

ENV PUBLIC_API_BASE_URL=$PUBLIC_API_BASE_URL

WORKDIR /app
COPY . .
RUN npm ci
RUN npm run build

# Etapa de produção
FROM node:20-alpine
WORKDIR /app
COPY --from=builder /app/build ./build
COPY --from=builder /app/package.json .
COPY --from=builder /app/package-lock.json .
RUN npm ci --omit=dev

EXPOSE 3000
CMD ["node", "build"]
