# bank-transfer-service

Projeto backend para simular um sistema de transferências bancárias.

Principais tecnologias
- Java 17
- Spring Boot 3
- Maven
- Spring Kafka
- MongoDB
- SpringDoc OpenAPI

Arquitetura
- Estilo: Hexagonal (Ports & Adapters) organizado por feature/context.
- Base package: `src/main/java/com/bank/transfer_service`.

Objetivos
- Criação de contas, consulta de saldo e execução de transferências entre contas.
- Publicação de eventos de transferência via Kafka e persistência de logs de auditoria no MongoDB.

Como rodar (desenvolvimento)
1. Ajuste `application.properties` em `infrastructure/src/main/resources` com as conexões de MongoDB e Kafka.
2. Executar a aplicação:

```bash
./mvnw spring-boot:run
```

3. Rodar testes:

```bash
./mvnw -B test
```

Pontos importantes
- Módulos: `domain`, `application`, `infrastructure` (cada um no layout Maven).  
- `domain` é puro; `application` depende apenas de `domain` e portas; `infrastructure` implementa adaptadores.

Contribuindo
- Abra issues e PRs no repositório remoto `https://github.com/peacevan/bank-transfer-service`.  
- Sugestões: adicionar testes (unitários e de integração), implementar integração Kafka real e melhorar cobertura.

Repositório remoto
- URL: https://github.com/peacevan/bank-transfer-service

Contato
- Arquitetura proposta por: equipe técnica

---
Arquivo atualizado: instruções de execução e testes adicionadas.
