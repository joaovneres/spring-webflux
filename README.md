### Projeto: Aprendizado de Programação Reativa com Spring WebFlux e MongoDB

#### Descrição do Projeto
Este projeto tem como objetivo principal aprender e aplicar conceitos de programação reativa utilizando o framework Spring WebFlux em conjunto com o banco de dados MongoDB. A aplicação desenvolvida é uma API reativa que permite realizar operações CRUD (Create, Read, Update, Delete) em uma coleção MongoDB de forma eficiente e não bloqueante.

#### Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.0.1**
- **Spring WebFlux**
- **Spring Data MongoDB Reactive**
- **Lombok**
- **MapStruct**
- **JUnit 5**
- **Reactor Test**

#### Configuração do Projeto

##### Arquivo `build.gradle`
```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.0.1'
    id 'io.spring.dependency-management' version '1.1.6'
}

group = 'br.com.joaovneres'
version = '0.0.1-SNAPSHOT'

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-mongodb-reactive'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-webflux'

    // Lombok
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    implementation "org.projectlombok:lombok-mapstruct-binding:0.2.0"

    // MapStruct
    implementation 'org.mapstruct:mapstruct:1.5.3.Final'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.3.Final'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'io.projectreactor:reactor-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

tasks.named('test') {
    useJUnitPlatform()
}
```

##### Arquivo `application.yml`
```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://${DB_USER:admin}:${DB_PASSWORD}@springwebfluxcluster.kfsszxk.mongodb.net/${DB_NAME:spring-webflux}?retryWrites=true&w=majority&appName=SpringWebfluxCluster
      auto-index-creation: true
  application:
    name: spring-webflux
```

#### Funcionalidades Implementadas
- **Criação de Documentos:** Permite adicionar novos documentos à coleção do MongoDB.
- **Leitura de Documentos:** Permite buscar documentos específicos ou todos os documentos da coleção.
- **Atualização de Documentos:** Permite atualizar informações de documentos existentes.
- **Exclusão de Documentos:** Permite remover documentos da coleção.

#### Estrutura do Projeto
O projeto segue a arquitetura padrão do Spring Boot, organizada da seguinte forma:
- **Controller:** Camada responsável por lidar com as requisições HTTP e direcioná-las para os serviços apropriados.
- **Service:** Camada que contém a lógica de negócios da aplicação.
- **Repository:** Camada responsável pela interação com o banco de dados MongoDB.
- **Model:** Classes que representam os documentos armazenados no MongoDB.

#### Como Executar o Projeto
1. **Clone o repositório:**
   ```sh
   git clone https://github.com/joaovneres/spring-webflux.git
   ```
2. **Navegue até o diretório do projeto:**
   ```sh
   cd spring-webflux
   ```
3. **Configure as variáveis de ambiente:**
   ```sh
   export DB_USER=seu_usuario
   export DB_PASSWORD=sua_senha
   export DB_NAME=nome_do_banco
   ```
4. **Execute a aplicação:**
   ```sh
   ./gradlew bootRun
   ```

#### Testes
Os testes foram implementados utilizando JUnit 5 e Reactor Test para garantir que as operações reativas funcionem corretamente. Para executar os testes, utilize o comando:
```sh
./gradlew test
```

#### Contribuição
Sinta-se à vontade para contribuir com o projeto enviando pull requests ou abrindo issues no repositório do GitHub.

#### Contato
Para mais informações, entre em contato através do email [victorsousa247@gmail.com](mailto:victorsousa247@gmail.com).
