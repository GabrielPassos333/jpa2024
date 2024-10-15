# Etapa de build
FROM ubuntu:latest as build

# Atualiza pacotes e instala JDK e Maven
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

# Copia o projeto para dentro do container
COPY . /build

# Define o diretório de trabalho para o Maven
WORKDIR /build

# Compila e constrói o projeto usando Maven
RUN mvn clean install

# Etapa final - Imagem leve para produção
FROM openjdk:17-jdk-slim

# Expõe a porta 8080 para o serviço
EXPOSE 8080

# Copia o JAR gerado da etapa de build para a imagem final
COPY --from=build /build/target/jpa-1.jar app.jar

# Comando de entrada para rodar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
