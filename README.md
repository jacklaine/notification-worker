# Inventory Service

Serviço responsável por consumir e persistir alerta de estoque.

## 📋 Visão Geral

- **Linguagem**: Java 21
- **Framework**: Quarkus 3.34.1
- **Banco de Dados**: PostgreSQL 18
- **Message Broker**: Apache Kafka (SmallRye Reactive Messaging)
- **Build**: Maven

---

## 🚀 Como Subir a Infraestrutura

### Pré-requisitos

- Docker e Docker Compose instalados
- Git

### Subir os Serviços com Docker Compose

A infraestrutura local é composta por PostgreSQL. Kafka é provido pelo docker-compose do ecossistema (order-service).

```bash
# Subir todos os serviços em background
docker-compose up -d

# Visualizar logs em tempo real
docker-compose logs -f

# Parar todos os serviços
docker-compose down

# Parar e remover volumes (apaga dados)
docker-compose down -v
```

### Verificar Status dos Serviços

```bash
# Listar containers em execução
docker-compose ps

# Verificar saúde do PostgreSQL
docker-compose logs postgres | grep healthcheck
```

### Serviços Disponíveis

| Serviço | Container | Porta | Credenciais |
|---------|-----------|-------|-------------|
| **PostgreSQL** | pg-inventory | 5445 | `user: pg-worker` / `pass: 0706` |

---

## 🏃 Como Executar o Serviço

### Pré-requisitos

- Java 21 JDK instalado
- Maven 3.8+
- Infraestrutura em execução (Docker Compose)
- Kafka acessível em `localhost:9092`

### Build do Projeto

```bash
# Compilar o projeto
./mvnw clean package

# Compilar sem executar testes
./mvnw clean package -DskipTests
```

### Executar o Serviço

```bash
# Modo dev (hot reload)
./mvnw quarkus:dev

# Ou após build
java -jar target/quarkus-app/quarkus-run.jar
```

O serviço estará disponível em **http://localhost:4646**

> **Dev UI** disponível em modo dev em http://localhost:4646/q/dev/

---

## 🏗️ Decisões Arquiteturais

### 1. Arquitetura Hexagonal (Ports & Adapters)

**Decisão**: Implementar uma arquitetura hexagonal para desacoplar domínio de infraestrutura.

**Trade-offs**:
- ✅ **Vantagens**:
  - Fácil testes unitários (domínio sem dependências externas)
  - Migração de tecnologias sem alterar domínio (trocar BD, trocar Kafka)
  - Independência entre camadas
  - Alta coesão e baixo acoplamento

- ❌ **Desvantagens**:
  - Curva de aprendizado mais acentuada
  - Mais arquivos para mudanças simples

---

### 2. Event-Driven Architecture (Comunicação Assíncrona)

**Decisão**: Usar publicação de eventos no Kafka para comunicação entre serviços. O inventory-service consome `OrderCreated` e publica `OrderConfirmed`/`OrderRejected` e `LowStockAlert`.

**Trade-offs**:
- ✅ **Vantagens**:
  - Desacoplamento entre serviços
  - Escalabilidade horizontal
  - Auditoria de eventos
  - Permitir múltiplos subscribers

- ❌ **Desvantagens**:
  - Eventual consistency (não absoluta)
  - Complexidade de debugging
  - Necessidade de compensating transactions
  - Overhead de infraestrutura

---

### 3. Java 21 + Quarkus 3.34

**Decisão**: Usar Java 21 (LTS) com Quarkus para startup rápido e baixo consumo de memória.

**Trade-offs**:
- ✅ **Vantagens**:
  - LTS com suporte até 2029 (Java 21)
  - Startup em milissegundos (dev mode)
  - Hot reload nativo
  - Possibilidade de compilação nativa (GraalVM)

- ❌ **Desvantagens**:
  - Ecossistema menor que Spring Boot
  - Algumas bibliotecas podem não ter extensão Quarkus

---

### 4. Hibernate ORM Panache + Flyway para Persistência

**Decisão**: Usar ORM com Panache e versionamento de schema com Flyway.

**Trade-offs**:
- ✅ **Vantagens**:
  - Código de repositório simplificado (Panache)
  - Migrações versionadas e auditáveis

- ❌ **Desvantagens**:
  - Performance inferior vs SQLs nativos

---