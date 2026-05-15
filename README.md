# bank-transfer-service

Projeto backend para simular um sistema de transferências bancárias.

Tecnologias
- Java 17
- Spring Boot 3
- Maven
- Spring Kafka
- MongoDB
- SpringDoc OpenAPI

Arquitetura
- Estilo: Hexagonal (Ports & Adapters), organizando por feature/context (DDD-inspired).
- Base package: `src/main/java/com/bank/transfer_service`.
- Features: `account`, `transfer`, `audit`, `shared`, `config`.
- Cada feature contém: `api`, `application`, `domain`, `infrastructure`.

Objetivos do sistema
- Criação de contas
- Consulta de saldo
- Execução de transferências entre contas
- Publicação de eventos de transferência via Kafka
- Persistência de logs de auditoria no MongoDB

Estrutura de pacotes (resumo)

- `config` — configuração de Kafka, MongoDB, Web e OpenAPI.
- `shared` — VOs (`Money`, `AccountId`, `TransferId`), exceções e API comum (`ApiResponse`).
- `account` — endpoints, casos de uso (`CreateAccountUseCase`, `GetBalanceUseCase`), domínio (`Account`), persistência (Mongo adapters).
- `transfer` — endpoints, casos de uso (`ExecuteTransferUseCase`, `GetTransferUseCase`), domínio (`Transfer`, eventos), publicação Kafka (publisher adapter), persistência.
- `audit` — consumidor Kafka (`TransferEventConsumer`), serviços de gravação de `AuditLog` em MongoDB, endpoints de consulta.

Principais componentes e classes sugeridas
- DTOs: `CreateAccountRequest`, `AccountResponse`, `BalanceResponse`, `TransferRequest`, `TransferResponse`, `AuditLogResponse`.
- Controllers: `AccountController`, `TransferController`, `AuditController`.
- UseCases / Ports: inbound (`*UseCase`), outbound (`*Port`) para persistência e publicação.
- Services (application): `AccountService`, `TransferService`, `AuditService`.
- Persistence adapters: `AccountPersistenceAdapter`, `TransferPersistenceAdapter`, `AuditPersistenceAdapter`.
- Kafka adapters: `TransferEventPublisherAdapter`, `TransferEventConsumer`.
- Entities / Documents: `AccountDocument`, `TransferDocument`, `AuditLogDocument`.

Regras de dependência
- `domain` é puro (sem dependências de Spring ou infra).
- `application` depende apenas de `domain` e `port` interfaces.
- `api` (controllers) dependem apenas de portas inbound.
- `infrastructure` implementa portas outbound e depende de adaptadores (MongoDB, Kafka).

Como rodar (desenvolvimento)
1. Ajustar `application.properties` com conexões de MongoDB e Kafka.
2. Executar com Maven:

```bash
./mvnw spring-boot:run
```

3. Documentação OpenAPI disponível em `/swagger-ui.html` (quando configurado).

Próximos passos sugeridos
- Implementar mapeadores e adapters (Mongo, Kafka).
- Implementar lógica de domínio (débito/crédito atômico) em `TransferService`.
- Adicionar testes unitários e de integração (embedded Mongo, `spring-kafka-test`).
- Configurar CI e verificação estática (SpotBugs, Checkstyle).

Contato
- Arquitetura proposta por: equipe técnica

---
Arquivo gerado automaticamente: `README.md` — editar conforme necessário.
