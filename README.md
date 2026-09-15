# Trabalho Estrutura — API de clientes

Replica os endpoints e os atributos de Cliente do projeto desenvolvido em aula.

## Executar

Requisito: JDK 8 (configure JAVA_HOME para apontar para o JDK 8). Na primeira execução, é necessária internet para baixar as dependências.

Windows:
```powershell
.\mvnw.cmd spring-boot:run
```
Linux/macOS:
```sh
sh mvnw spring-boot:run
```

A API estará em http://localhost:8080. Configure o PostgreSQL conforme abaixo. Para encerrar, pressione Ctrl+C.

## Endpoints da aula

| Método | Caminho | Ação |
| --- | --- | --- |
| POST | /clientes/salvar-cliente | Cadastrar |
| GET | /clientes/listar-clientes | Listar |
| GET | /clientes/buscar-cliente/{id} | Buscar por ID |
| PUT | /clientes/atualizar-cliente/{id} | Atualizar, preservando o ID da URL |
| DELETE | /clientes/deletar-cliente/{id} | Excluir |

Corpo para cadastrar ou atualizar:
```json
{
  "nome": "Maria Silva",
  "tipoPessoa": "PF",
  "cpfCnpj": "12345678901",
  "telefone": "44999999999",
  "email": "maria@example.com",
  "logradouro": "Rua Brasil",
  "numero": "100",
  "bairro": "Centro",
  "cep": "87500000",
  "cidade": null
}
```

A entidade mantém `id`, `nome`, `tipoPessoa`, `cpfCnpj`, `telefone`, `email`, `logradouro`, `numero`, `bairro`, `cep` e `cidade`. Os valores de tipoPessoa são PF e PJ. Nome é obrigatório e cpfCnpj é único, conforme a aula. Cidade é opcional; para associar uma cidade, ela deve existir no banco e ser enviada como `{"id": 1}`. As entidades Cidade e Estado também mantêm os atributos originais.

O código usa Spring Boot 2.7.18 e javax.persistence, como no projeto da aula, para compatibilidade com Java 8. Mantém a estrutura Controller → Service → Repository e os caminhos apresentados em aula.

## Testar

```powershell
.\mvnw.cmd test
```

## Repositório para entrega

https://github.com/ArieLindo993/Trabalho-Estrutura



## PostgreSQL

Crie a database no PostgreSQL local (porta 5432):
```sql
CREATE DATABASE "Trabalho-Estrutura";
```

Defina DB_PASSWORD com a senha do PostgreSQL e, se necessário, DB_USERNAME (padrão: postgres). No IntelliJ, essas variáveis podem ser definidas na configuração de execução. Como alternativa, crie `application-local.properties` na raiz do projeto:
```properties
spring.datasource.password=SUA_SENHA
```

Esse arquivo é ignorado pelo Git. Execute a aplicação a partir da raiz do projeto. O Hibernate criará automaticamente cliente, cidade e estado no esquema public da database Trabalho-Estrutura. O H2 é utilizado nos testes dos endpoints.