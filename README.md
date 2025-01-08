<p align="center" style='margin-top: 20px;'>
  <strong>📋 TodoList API - Java Spring Boot</strong>
</p>

## 🚀 Projeto

Desenvolvimento de uma aplicação back-end em Java 17 com Spring Boot, focada em:

- Gerenciamento de tarefas e usuários
- Segurança com autenticação básica (Basic Auth)
- Deploy automatizado no Render
- Banco de dados em memória (H2)

## 💡 Tecnologias e Ferramentas Utilizadas:

- `java 17`
- `maven`
- `Spring Boot`
- `API Rest`
- `Lombok`
- `H2 Database Engine`
- `Docker`

## 📦 Funcionalidades Implementadas:

- Cadastro e gerenciamento de usuários
- Autenticação básica (Basic Auth)
- Gerenciamento de tarefas com prioridade e data de vencimento
- Tratamento de exceções
- Deploy automatizado no Render

## 📡 Rotas da API:

### 📌 Usuários:

• Criar Usuário:

- POST - http://localhost:8080/users
- Body:

```
{
	"name": "Gabriel",
	"username": "gk",
	"email": "gabriel@teste.com",
	"password": "1234"
}
```

### 📌 Tarefas: (Requer Basic Auth: username e password)

• Listar Tarefas:

- GET - http://localhost:8080/tasks

• Criar Tarefa:

- POST - http://localhost:8080/tasks
- Body:

```
{
  "title": "Titulo task",
  "description": "Descrição da tarefa",
  "priority": "1",
  "startAt": "2025-01-08T11:00:00",
  "endAt": "2025-01-09T11:00:00"
}
```

• Editar Tarefa:

- PUT - http://localhost:8080/tasks
- Body:

```
{
  "title": "Titulo task - edit",
  "description": "Descricao edit",
  "priority": 2
}
```

## 📦 Executando o Projeto localmente:

```
# Clone o repositório
git clone https://github.com/seu-usuario/todolist-api.git
cd todolist-api

# Compilar o projeto
mvn clean install

# Executar com Spring Boot
mvn spring-boot:run

# Ou executar o .jar gerado
java -jar target/todolist-1.0.0.jar
```

## 📦 Executando o Projeto com Docker:

```
# Clone o repositório
git clone https://github.com/seu-usuario/todolist-api.git
cd todolist-api

# Compilar o projeto
mvn clean install

# Construir e rodar com Docker Compose
docker-compose up --build
```

## 🌐 Deploy (Hospedado no Render):

https://minicurso-springboot-rocketseat.onrender.com
