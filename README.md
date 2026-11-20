# 🍲 Raízes do Nordeste - Backend API

API RESTful desenvolvida para gerenciar o fluxo de pedidos, estoque, pagamentos simulados e autenticação de uma rede de lanchonetes em expansão. Este projeto foi construído como parte da Atividade Prática da disciplina de Projeto Multidisciplinar (Trilha Back-End) da UNINTER.

## 🚀 Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 4.1.1**
* **Spring Data JPA & Hibernate** (Persistência de dados)
* **Spring Security & JWT** (Autenticação e Autorização)
* **Banco de Dados H2** (Arquivo local)
* **Flyway Migration** (Versionamento do banco de dados)
* **Springdoc OpenAPI (Swagger)** (Documentação da API)
* **Lombok** (Produtividade)
* **Postman** (Testes de integração)

## ⚙️ Como Executar o Projeto

### Pré-requisitos
* Java JDK 17 instalado.
* Maven instalado (ou usar o Maven Wrapper incluso).
* Postman ou Insomnia para testar as rotas.

### Passo a Passo
1. Clone o repositório:
   ```bash
   git clone https://github.com/ArturAires8/Projeto-Raizes-do-Nordeste

### 
2. Navegue até a pasta do projeto:
   ``` bash
   cd raizes-nordeste-backend
   
###
3. Execute a aplicação via Maven:
   ``` bash
   mvn spring-boot: rum
   
###
4. O servidor iniciará na porta 8080. O banco de dados H2 será criado automaticamente na pasta /data e populado pelo Flyway e pelo DataSeeder.

----
📚 Documentação da API (Swagger)
A API está totalmente documentada. Com o projeto rodando, acesse:
👉 http://localhost:8080/swagger-ui/index.html

Lá você pode visualizar todos os endpoints, contratos de requisição/resposta e até testar as rotas diretamente pelo navegador.


----
🗄️ Console do Banco de Dados (H2)
Para visualizar as tabelas criadas e os dados inseridos:
👉 Acesse: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:file:./data/raizesdb

User Name: sa

Password: (deixar em branco)

----

🧪 Testes de Integração (Postman)
O repositório inclui o arquivo postman_collection.json na raiz. Para executar os testes:

1 .Importe o arquivo no seu Postman.

2 .A coleção está dividida em: 1. Auth, 2. Pedidos, 3. Pagamentos e 4. Erros.

3 .Fluxo de teste sugerido:

Execute o T01 - Login Valido para obter o Token JWT.

Nas rotas de Pedidos e Pagamentos, configure a aba Authorization -> Bearer Token com o token obtido.

Execute os testes na ordem (T01 ao T10) para validar sucessos e erros.

----
🔐 Segurança e LGPD
O projeto adota práticas essenciais de segurança e privacidade:

Senhas: Armazenadas no banco utilizando hash forte (BCrypt), nunca em texto puro.

Autenticação: Stateless via Token JWT (JSON Web Token).

Autorização: Controle de acesso baseado em perfis (Roles: ADMIN, GERENTE, CLIENTE, etc.).

LGPD: O cadastro exige o registro explícito do consentimentoFidelidade do usuário, garantindo a base legal para o uso dos dados em campanhas.

----
📌 Principais Regras de Negócio Implementadas
Multicanalidade: O pedido exige a identificação do canal de origem (APP, TOTEM, BALCAO, PICKUP, WEB).

Controle de Estoque: O sistema verifica a disponibilidade na unidade antes de fechar o pedido e dá baixa no estoque automaticamente.

Pagamento Mock: Simulação de gateway de pagamento com cenários de aprovação (status muda para PAGO) e recusa (status mantém AGUARDANDO_PAGAMENTO).

Tratamento de Erros: Respostas de erro padronizadas em JSON contendo error, message, timestamp e path.


----
Desenvolvido por Artur - RU: 4997377
