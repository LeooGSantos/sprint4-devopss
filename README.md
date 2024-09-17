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
* Banco de dados (ex: MySQL, PostgreSQL)

**Passos:**

1. **Clone o Repositório:**
   ```bash
   git clone https://github.com/ErikTeixeira/java_challenge_.git

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



## Diagramas

**Diagrama da entity da Sprint:**
    ![entity.png](documentacao/entity.png)
**Diagrama repository da Sprint:**
    ![repository.png](documentacao/repository.png)
**Diagrama request e response da Sprint:**
    ![request-response.png](documentacao/request-response.png)
**Diagrama service da Sprint:**
    ![service.png](documentacao/service.png)
**Diagrama da controller da Sprint:**
    ![controller.png](documentacao/controller.png)
**Diagrama da Sprint:**
    ![diagrama6.png](documentacao/diagrama6.png)


## Vídeo da Proposta Tecnológica

**Link da Sprint 3:**
    * https://youtu.be/lV95820MN6k
**Link da Sprint 2:**
    * https://youtu.be/JiGy_ohObWo

**Link da Sprint 1:**
    * https://youtu.be/0c4opnXL8fU



## Descrição do Problema e Solução

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

