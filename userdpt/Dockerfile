# Use a imagem oficial do Maven como base para compilar o projeto
FROM maven:3.8.4-openjdk-17 AS builder

# Copie o arquivo pom.xml para um diretório temporário
COPY pom.xml /tmp/

# Copie o resto do código fonte para o diretório temporário
COPY src /tmp/src/

# Defina o diretório de trabalho como o diretório temporário
WORKDIR /tmp/

# Compile o projeto com Maven
RUN mvn clean package -DskipTests=true

# Use a imagem oficial do OpenJDK 17 como base
FROM openjdk:17-jdk-alpine

# Copie o arquivo JAR do seu projeto Spring Boot para dentro do container
COPY --from=builder /tmp/target/userdpt-0.0.1-SNAPSHOT.jar /app/userdpt-0.0.1-SNAPSHOT.jar

# Defina o diretório de trabalho como /app
WORKDIR /app

# Comando para executar o aplicativo quando o container iniciar
CMD ["java", "-jar", "userdpt-0.0.1-SNAPSHOT.jar"]	
	

