# Agentic AI for CRUD Operations

## About
A Spring Boot application demonstrating how to integrate Agentic AI with a traditional CRUD-based User Management System.
The project uses:
- `Spring Boot` for REST APIs
- `Spring Data JPA` for persistence
- `Spring AI` for OpenAI-style Tool Calling 
- `Agentic AI` to interpret natural-language prompts
- `Swagger/ OpenAPI` for REST API documentation & playground

## Features
### 1) Traditional REST API endpoints
   - Fetch user(s) `GET`
   - Create user `POST`
   - Delete user `DELETE`
   - Update user `PATCH`
### 2) Agentic AI Support
   - Interpret natural-language instructions through `ChatClient`
   - Perform CRUD operations intelligently via `Tool Calling`
   - Enforce business rules through `Prompt Guarding`
   - Log all actions via `Spring logger`

## Testing Ideas

- Ask AI to create some dummy users.
- Try updating only certain fields (PATCH) of existing users.
- Ask AI to get some summaries or aggregate statistics.
- Force the AI to break rules → ensure it says `Apples & bananas! Ask ChatGPT instead!`.
- Another one:
```
Create a new user named Joe from London with balance 50,000, born on 20 August 1999 
```

## Running the Project
1. Install dependencies
Ensure Maven + JDK 21 are correctly installed.
```
mvn clean install
```
2. Run the application
```
mvn spring-boot:run
```

## Swagger API
Once the application is running, visit:
- Swagger UI (For playground & testing):
http://localhost:8080/swagger-ui/index.html
- OpenAPI Docs (Raw JSON docs):
http://localhost:8080/v3/api-docs

SpringDoc uses `/v3/api-docs` internally even if you never configured it.