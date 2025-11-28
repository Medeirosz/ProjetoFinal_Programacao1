# Sistema de Gerenciamento de Jogos

Projeto Final da disciplina de Programação 1 - Sistema para gerenciamento de biblioteca de jogos digitais desenvolvido com JavaFX e banco de dados H2.

## Índice

- [Descrição](#descrição)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Requisitos do Sistema](#requisitos-do-sistema)
- [Instalação](#instalação)
- [Execução](#execução)
- [Execução de Testes](#execução-de-testes)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Funcionalidades Implementadas](#funcionalidades-implementadas)
- [Observações Técnicas](#observações-técnicas)

## Descrição

Este projeto consiste em uma aplicação desktop desenvolvida em Java utilizando a biblioteca JavaFX para construção da interface gráfica. O sistema implementa operações CRUD (Create, Read, Update, Delete) para gerenciamento de uma coleção de jogos digitais, com persistência de dados realizada através do banco de dados embarcado H2.

## Tecnologias Utilizadas

- Java 17 ou superior
- JavaFX 21 - Framework para interface gráfica
- Apache Maven - Gerenciamento de dependências e build
- H2 Database - Sistema de gerenciamento de banco de dados relacional embarcado
- JUnit 5 - Framework para testes unitários

## Requisitos do Sistema

### Software Necessário

- Java Development Kit (JDK) versão 17 ou superior
- Apache Maven versão 3.8 ou superior
- Git (para clonagem do repositório)

### Verificação das Instalações

Execute os seguintes comandos no terminal para verificar as versões instaladas:

```bash
java -version
mvn -version
```

## Instalação

### Passo 1: Clonagem do Repositório

```bash
git clone https://github.com/Medeirosz/ProjetoFinal_Programacao1.git
cd ProjetoFinal_Programacao1
```

### Passo 2: Instalação de Dependências

```bash
mvn clean install
```

Este comando irá baixar todas as dependências necessárias e compilar o projeto.

## Execução

### Método 1: Execução via Maven

```bash
mvn javafx:run
```

### Método 2: Execução via IDE

1. Importe o projeto como um projeto Maven
2. Aguarde a sincronização e download das dependências
3. Localize e execute a classe principal `App.java`

### Método 3: Geração e Execução de JAR

```bash
mvn clean package
java -jar target/projetofinal-1.0-SNAPSHOT.jar
```

## Execução de Testes

### Execução Completa da Suíte de Testes

```bash
mvn test
```

### Geração de Relatório de Cobertura de Código

```bash
mvn clean test jacoco:report
```

O relatório HTML será gerado no diretório: `target/site/jacoco/index.html`

### Execução de Classe de Teste Específica

```bash
mvn test -Dtest=NomeDaClasse
```

### Execução de Método de Teste Específico

```bash
mvn test -Dtest=NomeDaClasse#nomeDoMetodo
```

### Execução de Testes com Modo Verbose

```bash
mvn test -X
```

## Estrutura do Projeto

```
ProjetoFinal_Programacao1/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/seupacote/
│   │   │       ├── controller/    # Controladores MVC
│   │   │       ├── model/         # Entidades de domínio
│   │   │       ├── dao/           # Data Access Objects
│   │   │       ├── service/       # Camada de serviços
│   │   │       └── App.java       # Classe principal
│   │   └── resources/
│   │       ├── fxml/              # Definições de interface
│   │       └── css/               # Folhas de estilo
│   └── test/
│       └── java/                  # Classes de teste
├── pom.xml                        # Configuração Maven
└── README.md                      # Documentação
```

## Funcionalidades Implementadas

- Cadastro de novos jogos no sistema
- Listagem completa dos jogos cadastrados
- Funcionalidade de busca e aplicação de filtros
- Edição de informações de jogos existentes
- Remoção de jogos do sistema
- Persistência automática em banco de dados relacional
- Interface gráfica responsiva

## Observações Técnicas

### Compatibilidade de Plataforma

Este projeto foi desenvolvido e testado em sistema macOS com arquitetura ARM (Apple Silicon M1/M2/M3). Devido às especificidades das dependências do JavaFX relacionadas à arquitetura do processador, usuários de outras plataformas podem necessitar realizar ajustes no arquivo de configuração Maven.

#### Configuração para Diferentes Sistemas Operacionais

Caso você esteja executando o projeto em Windows, Linux ou macOS com processador Intel, será necessário modificar o parâmetro `classifier` das dependências JavaFX no arquivo `pom.xml`.

Localize as dependências do JavaFX no arquivo `pom.xml` e altere o valor do elemento `<classifier>` conforme a arquitetura do seu sistema:

```xml
<!-- Configuração atual (macOS ARM - Apple Silicon) -->
<classifier>mac-aarch64</classifier>

<!-- Windows (x86-64) -->
<classifier>win</classifier>

<!-- macOS Intel (x86-64) -->
<classifier>mac</classifier>

<!-- Linux (x86-64) -->
<classifier>linux</classifier>
```

Exemplo de dependência completa:

```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>21</version>
    <classifier>win</classifier> <!-- Modificar conforme plataforma -->
</dependency>
```

Após a modificação, execute novamente o comando de instalação:

```bash
mvn clean install
```

### Banco de Dados

O sistema utiliza o banco de dados H2 em modo embarcado. A base de dados é criada automaticamente na primeira execução da aplicação e os arquivos de dados são armazenados localmente, garantindo persistência entre diferentes sessões de uso.

### Resolução de Problemas Comuns

**Erro: "JavaFX runtime components are missing"**

Solução: Verifique se as dependências do JavaFX estão configuradas corretamente para sua plataforma no arquivo `pom.xml`. Execute novamente `mvn clean install` após as correções.

**Erros de Compilação**

Solução: Confirme que a versão do Java instalada é 17 ou superior através do comando `java -version`. Verifique também se a variável de ambiente `JAVA_HOME` está configurada corretamente.

**Falhas na Execução de Testes**

Solução: Execute `mvn clean test` para remover artefatos de compilações anteriores. Verifique se não há outras instâncias da aplicação em execução que possam estar bloqueando o acesso ao banco de dados H2.

**Problemas com Maven**

Solução: Limpe o repositório local do Maven e tente novamente:

```bash
mvn dependency:purge-local-repository
mvn clean install
```

## Autor

Desenvolvido por Medeiros como projeto acadêmico para a disciplina de Programação 1.

## Licença

Este projeto possui fins exclusivamente educacionais e acadêmicos.

---

Última atualização: Novembro de 2025
