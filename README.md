# 🎟️ Coupon Management API

Uma API robusta e performática para o gerenciamento de cupons de desconto, desenvolvida com **Java 21** e **Spring Boot 3.5+**.

Este projeto foi construído sob a filosofia de **Rich Domain Model** (Modelo de Domínio Rico), garantindo que a lógica de negócio esteja encapsulada e protegida, seguindo princípios de **Clean Code** e **Domain-Driven Design (DDD)**.


## 🛠️ Como Executar o Projeto

Você pode rodar a aplicação localmente com Maven ou via Docker.

### Opção 1: Maven

1. **Clone o repositório**:
    ```bash
    git clone https://github.com/jsrrocha/coupon-management-api.git
    ```

2. **Entre na pasta:**:
   ```bash
   cd coupon-management-api
   ```

3. **rode a aplicação**:
   ```bash
   mvn spring-boot:run
   ```

4. **Certifique-se de estar usando o JDK 21**

### Opção 2: Docker (Recomendado)

1. **Construa e suba o container:**:

   ```bash
   docker build -t coupon-api .
   docker run -p 8080:8080 coupon-api
   ```

#### Acesse a documentação Swagger para testar os endpoints:
👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 📖 Documentação de Decisões Técnicas (Wiki)

Para entender profundamente os pilares arquiteturais, padrões de projeto aplicados e as motivações por trás de cada escolha técnica, acesse a documentação completa:

👉 **[Acesse a Wiki de Decisões Técnicas](https://github.com/jsrrocha/coupon-management-api)**


---


## 🏗️ Padrões de Projeto & Arquitetura

O projeto não apenas resolve o problema técnico, mas aplica padrões de mercado para garantir manutenibilidade e escalabilidade:

* **Rich Domain Model:** Domínio autossuficiente que valida seu próprio estado.
* **Static Factory Methods:** Semântica clara na criação e recuperação de objetos.
* **Object Mother Pattern:** Centralização de massas de dados para testes unitários limpos e resilientes.
* **Strategy & Fail-Fast:** Validações sintáticas no DTO (Bean Validation) e semânticas no Domínio.
* **Global Exception Handling (RFC 7807):** Padronização de erros para facilitar o consumo da API.


## 🛡️ Regras de Domínio e Validações

O sistema garante a integridade dos cupons através de validações no construtor do domínio:
- ✅ **Código**: Exatamente 6 caracteres alfanuméricos.
- ✅ **Desconto Mínimo**: Valor base de **0.5**.
- ✅ **Validade**: Bloqueio de datas de expiração anteriores ao dia atual.
- ✅ **Estado**: Implementação de *Soft Delete* (status `DELETED`) para histórico.

## 🚀 Tecnologias Utilizadas

| Tecnologia             | Descrição                                                              |
|:-----------------------|:-----------------------------------------------------------------------|
| **Java 21**            | Uso de Records  e melhorias de performance.                            |
| **Spring Boot 3.5.14** | Framework base para a arquitetura da API.                              |
| **Spring Data JPA**    | Persistência de dados com foco em produtividade.                       |
| **H2 Database**        | Banco de dados em memória para agilidade no desenvolvimento.           |
| **Lombok**             | Redução de boilerplate (Builders com `toBuilder = true` para Records). |
| **MapStruct**          | Mapeamento eficiente entre Entidades e DTOs.                           |
| **JaCoCo**             | Relatórios de cobertura com Quality Gate de 80% configurado no Maven.  |
| **Swagger UI**         | Documentação interativa (OpenAPI 3).                                   |
| **Docker**             | Containerização da aplicação para garantir paridade entre ambientes.   |


## 📊 Qualidade de Código (JaCoCo)

O projeto utiliza o **JaCoCo** integrado ao ciclo de vida do Maven.
O build só será finalizado com sucesso se a cobertura de testes de linha atingir o mínimo de **80%**.

### Como rodar os testes e gerar o relatório:
```bash
mvn clean verify
```

### 📄 Localização do Relatório

`target/site/jacoco/index.html`

---



> 💡 **🚀 Construído para evoluir, não apenas para funcionar. Cada decisão técnica reflete o compromisso com a clareza, a testabilidade e a longevidade do software.**


Desenvolvido com ☕ por **Jessica Salvador Rodrigues da Rocha** 🚀
