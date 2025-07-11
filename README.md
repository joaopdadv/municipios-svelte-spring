# Cidades Brasileiras – Aplicação sobre Municípios do Brasil

## Desenvolvedores

* **João Pedro de Villa**
* **Bruno Ascari**
* **Vinícius Guilherme Pereira de Lima**

---

## Descrição Geral

Este projeto tem como objetivo desenvolver uma aplicação para consulta e análise de informações sobre municípios brasileiros, com base nos dados do arquivo “2022_IBGE - Municípios”. O projeto foi realizado como parte da disciplina de Projeto e Arquitetura de Software, buscando aplicar os padrões arquiteturais e de projeto discutidos em aula.

A aplicação foi desenvolvida com foco em modularidade, manutenibilidade e clareza estrutural, adotando o padrão arquitetural **MVC (Model-View-Controller)** e os padrões de projeto **Repository** e **DAO** para organização da camada de persistência.

---

## Tecnologias e Ferramentas Utilizadas

| Camada | Tecnologia / Ferramenta |
| :--- | :--- |
| **Front-end** | SvelteKit |
| **Back-end** | Spring Boot (Java 17) |
| **Banco de Dados** | PostgreSQL |
| **ORM** | Hibernate (via Spring Data JPA) |
| **Build Back-end** | Maven |
| **Build Front-end** | npm |
| **Versionamento** | Git / GitHub |

---

## Estrutura e Arquitetura

A aplicação segue o padrão **MVC**, com clara separação entre:

* **Model**: Classes de entidade JPA representando os municípios.
* **View**: Componentes Svelte no front-end para a interface com o usuário.
* **Controller**: Controladores REST do Spring Boot que expõem endpoints para o front-end.

Além disso, a arquitetura contempla:

* **DAO (Data Access Object)**: Camadas de acesso direto ao banco, encapsuladas nas interfaces `JpaRepository`.
* **Repository**: Camada intermediária para a lógica de negócio de persistência.
* **Service**: Camada de lógica de negócio entre o `Controller` e o `Repository`.
* **DTOs (Data Transfer Objects)**: Objetos de transferência para comunicação segura e eficiente entre as camadas e o front-end.

A comunicação entre o front-end e o back-end é realizada por meio de **REST APIs**, utilizando **JSON** como formato de dados.

---

## Funcionalidades da Aplicação

### Funcionalidades Básicas

1.  **Buscar município pelo nome:**
    * Permite ao usuário pesquisar um município e visualizar:
        * Nome
        * Código do Município
        * Estado (UF)
        * Código do Estado
        * População
        * Se é Capital

2.  **Apresentar população total de um estado:**
    * Calcula e exibe a soma das populações de todos os municípios de um estado informado pelo usuário.

3.  **Listar todas as capitais:**
    * Apresenta uma lista de todos os municípios que são capitais estaduais, com suas respectivas informações.

### Funcionalidades com Filtros ou Agregações

1.  **Listar municípios por população:**
    * Acima de um valor informado.
    * Entre dois valores informados.
    * Para cada município listado, exibe: nome, estado (UF) e população.

2.  **Estados cuja capital não é a cidade mais populosa:**
    * O sistema identifica quais estados possuem outra cidade mais populosa que a capital.
    * Para cada caso, exibe a cidade mais populosa do estado e seu número de habitantes.

3.  **Top 10 municípios mais populosos que não são capitais:**
    * Lista os 10 municípios mais populosos que não são capitais.
    * Para cada município, exibe: nome, estado (UF) e população.