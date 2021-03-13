# Projeto de teste MAPS

## Introdução

Esse projeto foi desenvolvido utilizando [Quarkus](https://quarkus.io/), um framework Kubernetes
nativo para a OpenJDK e GraalVM desenvolvido com as melhores bibliotecas e padrões Java.

## Ferramentas

### Quarkus

Conforme trecho inicial da descrição oficial do projeto (disponível [aqui](https://www.redhat.com/pt-br/topics/cloud-native-apps/what-is-quarkus)):

_Quarkus é um framework Java nativo em Kubernetes e de stack completo que foi desenvolvido para máquinas virtuais Java (JVMs) e compilação nativa. Ele otimiza essa linguagem especificamente para containers, fazendo com que essa tecnologia seja uma plataforma eficaz para ambientes serverless, de nuvem e Kubernetes._

_O Quarkus foi desenvolvido para funcionar com os padrões, frameworks e bibliotecas Java mais utilizados, como o Eclipse MicroProfile e o Spring (demonstrados em conjunto nesta apresentação do Red Hat Summit 2020), além do Apache Kafka, RESTEasy (JAX-RS), Hibernate ORM (JPA), Infinispan, Camel e muitos outros.__

### Maven

Todo projeto utiliza a estrutura e o ciclo de desenvolvimento definidos pelo Maven, em conjunto com 
as ferramentas disponibilizadas pelo Quarkus. Todo processo é autmoatizado desde o desenvolvimento
até a criação da imagem à ser docker.

### OpenAPI

Toda a definição da estrutura dos endpoints estão disponíveis através do arquivo de configuração da
[openapi](openapi), localizado na raiz do projeto e que pode ser acessado também durante a execução 
na URL [host]/openapi

### Swagger

Junto com a aplicação está disponível uma interface Swagger na URL [host]/swagger-ui.

### JMeter/OpenAPI Generator

O plano de testes JMeter foi criado utilizando o [OpenAPI Generator](https://openapi-generator.tech/),
ele contém os valores padrão para testes de carga em APIs REST e contempla todos os endpoints definidos
no arquivo criado pelo projeto. O plano de testes está disponível na raiz do projeto no arquivo [DefaultAPpi](DefaultApi.jmx),
as variáveis para a execução podem ser definidas no arquivo csv [DefaultApi.csv](DefaultApi.csv).

## Desenvolvimento

### Estrutura

A estrutura de pacotes do projeto está definida da seguinte forma

```src
├── main
│   ├── docker                             # Arquivos para a criação de imagem nativa 
│   │   
│   ├── java
│   │   └── com
│   │       └── github
│   │           └── joelbars
│   │               └── maps
│   │                   ├── converter      # Conversores de tipos de dado JAXRS
│   │                   ├── exception      # Exceções e mapeamento de execeções
│   │                   ├── model          # Classes de domínio/modelo
│   │                   ├── repository     # Repositório/Serviços
│   │                   ├── resource       # Endpoints
│   │                   └── serialization  # Classes DTO 
│   └── resources
│       ├── application.properties         # Arquivo principal de configuração 
│       ├── import.sql                     # Arquivo de impotação de dados inicial para a execução
│       └── META-INF
│           └── resources
│               └── index.html             # Página inicial de acesso estática
└── test
    ├── java
    │   └── com
    │       └── github
    │           └── joelbars
    │               └── maps
    │                   ├── resource
    │                   └── service
    └── resources
        ├── application.properties         # Arquivo de configuração de testes
        └── import.sql                     # Arquivo de impotação de dados inicial para os testes
```

O acesso à dados é feito utilizando o padrão de respositórios com o framework Hibernate Panache, com
ele é possivel abstrair uma série de comandos e padrões já existentes no mercado. Devido à essa abstração
os serviços residem nas mesmas classes dos repositórios criando uma camada Repositório/Servico reduzindo
_bypass_ desnecessário.

Foram criadas classes genéricas aproveitando o polimorfismo e reduzindo o trabalho repetitivo de criação de
CRUDs e outras estruturas comuns.

### Execução

O desevolvmento utilizando ocorre utilizando hot-reload através do comando:

```./mvnw compile quarkus:dev```

Todas as alterações são compiladas automaticamente e o servidor realiza o hot-deploy na próxima requisição
sem a necessidade de executar todo o processo novamente reduzindo o tempo de desenvolvimento/testes.

## Testes

*_Devido a problemas de tempo os testes unitários foram ignorados nesse release após as alterações para o nível 2,
os testes criados não puderam ser migrados_

Por esse motivo a tag `<skipTests>true</skipTests>` foi adicionada ao arquivo de configuração do maven. 

## Docker

O projeto conta com um plugin para a criação de uma imagem docker que pode ser executada de maneira nativa
utilizando GraalVM, para sua execução:

``./mvnw clean package -Dquarkus.container-image.build=true``

*_O quarkus possui suporte a criação de imagens Docker (padrão para esse projeto), Jib e S2I_.