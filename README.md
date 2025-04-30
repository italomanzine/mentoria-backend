# MENTORIA BACKEND JAVA

## Visão Geral
Projeto Java Spring Boot que expõe APIs REST para operações CRUD, com tratamento centralizado de exceções e documentação via Swagger.

## Tecnologias Utilizadas
- Java 17
- Spring Boot 3.3.4
- Spring Data JPA
- Flyway Migration
- Spring Boot DevTools
- Swagger UI
- Maven

## Pré-requisitos
- JDK 17 instalado
- Maven 3.6+ ou `mvnw` (wrapper)
- Banco de dados compatível com JDBC (ex: PostgreSQL, MySQL)

## Configuração do Banco de Dados
No arquivo `src/main/resources/application.properties`, defina:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nortix
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
```

## Build e Execução
Clone o repositório e entre na pasta do projeto:
```bash
git clone <url-do-repo>
cd auto-backend-nortix
```

Compile e execute testes:
```bash
./mvnw clean install
```

Inicie a aplicação:
```bash
./mvnw spring-boot:run
# ou
java -jar target/auto-backend-nortix.jar
```

## Testes

### Testes unitários e de integração
```bash
./mvnw test
```

### Testes manuais em Insomnia
Use endpoints com métodos PUT e DELETE.

## Documentação da API
Após iniciar a aplicação, acesse:  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Migrações de Banco
As migrações Flyway ficam em `src/main/resources/db/migration`.  
Basta criar novos scripts no formato:
```text
V<n>__descricao.sql
```

## Estrutura do Projeto
```text
src/
 ?? main/
 ?   ?? java/…          # Código-fonte
 ?   ?? resources/      # Configurações e migrations
 ?? test/
     ?? java/…          # Testes automatizados
```

## Contribuindo
1. Crie uma branch para cada feature/bugfix.
2. Implemente testes e valide com `./mvnw test`.
3. Abra um pull request apontando para a branch `develop`.  
