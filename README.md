# Rest-Api-Project
Small Rest API project in Java Language.

## Tecnologies

- Spring Boot;
- PostgresSQL;
- Flyway;
- Swagger;
- Docker.

### Dados

- Link Swagger: http://localhost:8080/swagger-ui/index.html

### Executar a aplicação

- Verificar se a versão utilizada é a 17. Comando: ```java -version```.
- Build. Executar: ```mvn clean package```.
- Rodando aplicação. Executar: ```mvn spring-boot:run```. 

### Executar aplicação via docker

- Verificar se a versão utilizada é a 17. Comando: ```java -version```.
- Build. Executar: ```mvn clean package```.
- Desabilitar o postgresql na máquina: ```systemctl stop postgresql```.
- Rodando aplicação. Executar: ```docker-compose up```.
- Verificar status do postgresql: ```systemctl status postgresql```.
- Reiniciar o postgresql: ```systemctl start postgresql```.

### Comandos úteis docker

- Listar contâiners: ```docker ps -a```.
- Listar imagens: ```docker images -a```.
- Remover contâiner: ```docker rm ID```.
- Remover imagem: ```docker rmi ID```.

### Observações

- Com o mapeamento das portas: ```35432:5432``` não é necessário desabilitar a execução do Postgres.
