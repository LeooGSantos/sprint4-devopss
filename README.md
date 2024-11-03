# TechPeach: Planejamento de Viagens Personalizadas com IA

![image](https://github.com/AlleSilvaa/TechPeach/assets/126684613/9783be37-be88-4a69-9629-dbc7f67624d6)

**TechPeach** é uma plataforma web que permite aos usuários organizar e compartilhar seus locais favoritos em São Paulo. Com uma interface intuitiva e dinâmica, o TechPeach facilita o planejamento de viagens e a descoberta de novos pontos de interesse na cidade.

## Integrantes

* **Allesson Augusto (RM99533):** Compliance & Quality Assurance
* **Cauã Mongs (RM552178):** Advanced Business Development with .NET
* **Erik Teixeira (RM551377):** Disruptive Architectures: IOT, IOB & Generative IA e Java Advanced
* **Guilherme Naoki (RM551456):** Mastering Relational and Non-Relational Database
* **Leonardo Gonçalves (RM98912):** DevOps Tools e Cloud Computing

**Contribuições em Equipe:**
* Mobile App Development: Todo o grupo colaborou.


## Rodando a Aplicação

**Pré-requisitos:**

* Java JDK 8 ou superior
* Maven
* Banco de dados: Oracle SQL Developer 

**Passos:**

1. **Clone o Repositório:**
   ```bash
   git clone https://github.com/LeooGSantos/sprint4-devopss.git

2. **Configure o Banco de Dados:**
    * Crie um banco de dados e atualize as configurações de conexão no arquivo application.properties.

3. **Construa a Aplicação:**
    ```bash
    mvn clean package

4. **Execute a Aplicaçãos:**
    ```bash
   java -jar target/TechPeach.jar

5. **Execute a Aplicaçãos:**
    * A aplicação estará disponível em http://localhost/


Para rodar os comandos acima no Windows, você pode usar o Prompt de Comando ou o PowerShell:

1. Abra o Prompt de Comando ou PowerShell.
2. Navegue até o diretório onde você clonou o repositório.
3. Execute os comandos conforme indicado nos passos acima.

Certifique-se de que o Maven e o Java estão corretamente instalados e configurados no seu PATH do Windows.


## Vídeo da Proposta Tecnológica

**Link da Sprint 4:**



## Configuração das Pipelines definidas na ferramenta Azure DevOps

##Pipeline de Build
A pipeline de Build foi configurada para compilar e empacotar a aplicação utilizando Maven. A configuração da pipeline está descrita abaixo:

trigger:
  main

pool:
  vmImage: ubuntu-latest

variables:
  MAVEN_CACHE_FOLDER: $(Pipeline.Workspace)/.m2/repository
  MAVEN_OPTS: '-Dmaven.repo.local=$(MAVEN_CACHE_FOLDER) -Xmx3072m'

stages:
  - stage: Build
    jobs:
      - job: MavenBuild
        steps:
          - task: JavaToolInstaller@0
            inputs:
              versionSpec: '17'
              jdkArchitectureOption: 'x64'
              jdkSourceOption: 'PreInstalled'

          - task: Cache@2
            displayName: 'Cache Maven packages'
            inputs:
              key: 'maven | "$(Agent.OS)" | pom.xml'
              restoreKeys: 'maven | "$(Agent.OS)"'
              path: $(MAVEN_CACHE_FOLDER)

          - task: Maven@3
            displayName: 'Build com o Maven'
            inputs:
              mavenPomFile: 'pom.xml'
              mavenOptions: $(MAVEN_OPTS)
              javaHomeOption: 'JDKVersion'
              jdkVersionOption: '1.17'
              jdkArchitectureOption: 'x64'
              publishJUnitResults: true
              testResultsFiles: '**/surefire-reports/TEST-*.xml'
              goals: 'package'

          - task: CopyFiles@2
            displayName: 'Copiar JAR para o diretório de staging'
            inputs:
              SourceFolder: '$(System.DefaultWorkingDirectory)/target'
              Contents: '*.jar'
              TargetFolder: $(Build.ArtifactStagingDirectory)

          - task: PublishBuildArtifacts@1
            displayName: 'Publicar artefato: drop'
            inputs:
              PathtoPublish: $(Build.ArtifactStagingDirectory)
              ArtifactName: 'drop'

Pipeline de Build
A pipeline de Build foi configurada para compilar e empacotar a aplicação utilizando Maven. A configuração da pipeline está descrita abaixo:
trigger:
  main

pool:
  vmImage: ubuntu-latest

variables:
  MAVEN_CACHE_FOLDER: $(Pipeline.Workspace)/.m2/repository
  MAVEN_OPTS: '-Dmaven.repo.local=$(MAVEN_CACHE_FOLDER) -Xmx3072m'

stages:
  - stage: Build
    jobs:
      - job: MavenBuild
        steps:
          - task: JavaToolInstaller@0
            inputs:
              versionSpec: '17'
              jdkArchitectureOption: 'x64'
              jdkSourceOption: 'PreInstalled'

          - task: Cache@2
            displayName: 'Cache Maven packages'
            inputs:
              key: 'maven | "$(Agent.OS)" | pom.xml'
              restoreKeys: 'maven | "$(Agent.OS)"'
              path: $(MAVEN_CACHE_FOLDER)

          - task: Maven@3
            displayName: 'Build com o Maven'
            inputs:
              mavenPomFile: 'pom.xml'
              mavenOptions: $(MAVEN_OPTS)
              javaHomeOption: 'JDKVersion'
              jdkVersionOption: '1.17'
              jdkArchitectureOption: 'x64'
              publishJUnitResults: true
              testResultsFiles: '**/surefire-reports/TEST-*.xml'
              goals: 'package'

          - task: CopyFiles@2
            displayName: 'Copiar JAR para o diretório de staging'
            inputs:
              SourceFolder: '$(System.DefaultWorkingDirectory)/target'
              Contents: '*.jar'
              TargetFolder: $(Build.ArtifactStagingDirectory)

          - task: PublishBuildArtifacts@1
            displayName: 'Publicar artefato: drop'
            inputs:
              PathtoPublish: $(Build.ArtifactStagingDirectory)
              ArtifactName: 'drop'
            
## Pipeline de Deploy
A pipeline de Deploy foi configurada para realizar o deploy da aplicação no ambiente DevOps. Esta configuração inclui o uso do RabbitMQ e a execução da aplicação Spring Boot. Veja abaixo a configuração:
stage: Deploy
displayName: 'Deploy da Aplicação'
dependsOn: Build
condition: succeeded()
jobs:
  - deployment: DeployJob
    displayName: 'Deploy para o servidor'
    environment: DevOps-Sprint4
    strategy:
      runOnce:
        deploy:
          steps:
            - task: Docker@2
              displayName: 'Iniciar RabbitMQ'
              inputs:
                containerRegistry: 'LeooGSantoss'
                command: 'run'
                arguments: '-d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management'

            - task: DownloadBuildArtifacts@1
              inputs:
                buildType: 'current'
                downloadType: 'single'
                artifactName: 'drop'
                downloadPath: '$(System.ArtifactsDirectory)'

            - task: Bash@3
              displayName: 'Executar aplicativo Spring Boot'
              inputs:
                targetType: 'inline'
                script: |
                  # Aguardar o RabbitMQ iniciar
                  sleep 30
                  
                  # Executar a aplicação Spring Boot com as variáveis de ambiente
                  java -jar $(System.ArtifactsDirectory)/drop/*.jar \
                    --spring.datasource.url=jdbc:oracle:thin:@$(DB_HOST):$(DB_PORT):$(DB_SERVICE_NAME) \
                    --spring.datasource.username=$(DB_USER) \
                    --spring.datasource.password=$(DB_PASSWORD) \
                    --google.api.key=$(GOOGLE_API_KEY) &

                  # Aguardar a aplicação iniciar
                  sleep 30
## Considerações Finais
A configuração das pipelines foi realizada utilizando a ferramenta Azure DevOps, permitindo a automação do processo de build e deploy da aplicação TechPeach. A estrutura configurada garante uma implementação ágil e eficiente, assegurando que as atualizações na aplicação sejam integradas e disponibilizadas de maneira rápida e eficaz.

### Problema
Planejar viagens pode ser um processo tedioso e desafiador, especialmente para encontrar atividades e locais que se alinhem com as preferências individuais dos viajantes.

### Solução
O TechPeach oferece uma plataforma online onde os usuários podem:
- Cadastrar seus locais favoritos em São Paulo, com informações detalhadas como endereço, horário de funcionamento, fotos e avaliações.
- Organizar os locais em listas personalizadas, facilitando o planejamento de roteiros.
- Compartilhar as listas com amigos e familiares, permitindo a colaboração na organização da viagem.

### Público-Alvo
Viajantes e turistas que buscam uma experiência de viagem personalizada e memorável.

### Benefícios
- **Experiências Autênticas:** Conecta viajantes a atividades e locais que realmente os inspirem.
- **Planejamento Simplificado:** Facilita o processo de planejamento de viagens.
- **Personalização:** Cria itinerários sob medida adaptados às preferências dos usuários.
- **Ofertas Exclusivas:** Oferece ofertas especiais em parceria com estabelecimentos locais.

### Próximos Passos
- Implementar autenticação de usuários com Spring Security.
- Adicionar recursos de internacionalização para suportar múltiplos idiomas.
- Integrar recursos de mensageria para comunicação assíncrona.
- Implementar monitoramento da aplicação com Spring Boot Actuator.
- Explorar a possibilidade de integrar recursos de IA para aprimorar as recomendações de locais (opcional).


Com o TechPeach, os viajantes podem descobrir e reservar atividades personalizadas, tornando suas viagens mais memoráveis e autênticas!

