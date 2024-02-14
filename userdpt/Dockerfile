# Use a imagem oficial do OpenJDK 17 como base
FROM openjdk:17-jdk-alpine

# Copie o arquivo JAR do seu projeto Spring Boot para dentro do container
COPY target/userdpt-0.0.1-SNAPSHOT.jar /app/userdpt-0.0.1-SNAPSHOT.jar

# Defina o diretório de trabalho como /app
WORKDIR /app

# Comando para executar o aplicativo quando o container iniciar
CMD ["java", "-jar", "userdpt-0.0.1-SNAPSHOT.jar"]
	
	

