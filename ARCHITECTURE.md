# Arquitetura Hexagonal — Transfer Service

## Estrutura de Módulos

```
domain ← application ← infrastructure
```

| Módulo | Responsabilidade |
|---|---|
| `domain` | Entidades e regras de negócio puras (sem dependências externas) |
| `application` | Casos de uso, ports de entrada e saída |
| `infrastructure` | Controllers REST, persistência MongoDB, Kafka, mappers |

---

## Fluxo de uma Requisição

### 1. Entrada — Adaptador REST (infrastructure)

```
HTTP POST /api/v1/accounts
HTTP POST /api/v1/transfers
```

`AccountController` e `TransferController` recebem a requisição e delegam para os **ports de entrada**. Nunca importam o service diretamente.

---

### 2. Port de Entrada — Contratos (application)

Interfaces que definem **o que** a aplicação sabe fazer:

| Interface | Operação |
|---|---|
| `CreateAccountUseCase` | Criar uma conta |
| `GetBalanceUseCase` | Consultar saldo |
| `ExecuteTransferUseCase` | Executar transferência |
| `GetTransferUseCase` | Consultar transferência |

---

### 3. Application Service — Lógica de Negócio (application)

Implementam os ports de entrada e orquestram o fluxo:

#### `AccountService`
1. Gera `UUID` para a nova conta
2. Cria `Account` com saldo zero
3. Persiste via `SaveAccountPort`
4. Retorna `AccountResponse`

#### `TransferService`
1. Busca conta origem → `LoadAccountPort`
2. Busca conta destino → `LoadAccountPort`
3. Valida saldo disponível
4. Debita origem, credita destino → `SaveAccountPort`
5. Salva a transferência → `SaveTransferPort`
6. Publica evento de domínio → `PublishTransferEventPort`

---

### 4. Port de Saída — Contratos de Infraestrutura (application)

Interfaces que o `Application Service` usa sem saber a implementação:

| Interface | Responsabilidade |
|---|---|
| `LoadAccountPort` | Buscar conta por ID |
| `SaveAccountPort` | Persistir conta |
| `LoadTransferPort` | Buscar transferência por ID |
| `SaveTransferPort` | Persistir transferência |
| `PublishTransferEventPort` | Publicar evento de domínio |

---

### 5. Adaptadores de Saída — Implementações Reais (infrastructure)

| Adaptador | Implementa | Tecnologia |
|---|---|---|
| `AccountPersistenceAdapter` | `LoadAccountPort` + `SaveAccountPort` | MongoDB |
| `TransferPersistenceAdapter` | `LoadTransferPort` + `SaveTransferPort` | MongoDB |
| `TransferEventPublisherAdapter` | `PublishTransferEventPort` | Kafka |
| `AuditPersistenceAdapter` | — | MongoDB |

Cada adaptador usa um **Mapper** (`AccountMapper`, `TransferMapper`, `AuditMapper`) para converter entre objetos de domínio e documentos MongoDB.

---

### 6. Diagrama Completo

```
HTTP Request
     │
     ▼
┌─────────────────────────────────────────┐
│  infrastructure — Adaptador de ENTRADA  │
│  AccountController / TransferController │
└────────────────┬────────────────────────┘
                 │ chama interface (port de entrada)
                 ▼
┌─────────────────────────────────────────┐
│  application — PORT de entrada          │
│  CreateAccountUseCase                   │
│  ExecuteTransferUseCase                 │
└────────────────┬────────────────────────┘
                 │ implementado por
                 ▼
┌─────────────────────────────────────────┐
│  application — Lógica de negócio        │
│  AccountService / TransferService       │
└────────────────┬────────────────────────┘
                 │ chama interfaces (ports de saída)
                 ▼
┌─────────────────────────────────────────┐
│  application — PORT de saída            │
│  LoadAccountPort / SaveAccountPort      │
│  SaveTransferPort / PublishEventPort    │
└────────────────┬────────────────────────┘
                 │ implementado por
                 ▼
┌─────────────────────────────────────────┐
│  infrastructure — Adaptador de SAÍDA    │
│  AccountPersistenceAdapter              │
│  TransferPersistenceAdapter             │
│  TransferEventPublisherAdapter          │
└────────────────┬────────────────────────┘
                 │
                 ▼
          MongoDB / Kafka
```

---

## Domínio (domain)

As entidades de domínio **não dependem de nada** — sem Spring, sem MongoDB, sem anotações de framework:

| Classe | Descrição |
|---|---|
| `Account` | Agregado raiz de conta bancária |
| `Transfer` | Agregado raiz de transferência |
| `Money` | Value Object de valor monetário |
| `AccountId` | Value Object de identidade de conta |
| `TransferId` | Value Object de identidade de transferência |
| `AuditLog` | Registro de auditoria de eventos |

---

## Testes

| Tipo | Localização | Estratégia |
|---|---|---|
| Integração (`ApiIntegrationTest`) | `infrastructure/src/test` | Sobe Spring Boot com `InMemoryAccountStore` / `InMemoryTransferStore` substituindo os adaptadores reais |
| Unitário (`AccountServiceTest`) | `application/src/test` | Testa `AccountService` com mocks dos ports de saída |

A substituição dos adaptadores reais por implementações em memória nos testes é exatamente o benefício central da Arquitetura Hexagonal: **trocar a infraestrutura sem tocar na lógica de negócio**.
