# Desafio de Impostos

Você foi designado para construir uma API REST capaz de calcular o preço tarifado de um produto de seguros com base no preço base informado.
O cálculo deve ocorrer sempre que um produto for criado ou alterado com base em sua tributação.
Para isto é necessário considerar as informações abaixo.
Todo produto deve ter os atributos:
- ID
- Nome
- Categoria
- Preço Base
- Preço Tarifado

**Exemplo de Requisição**

```json
{
    "nome": "Seguro de Vida Individual",
    "categoria": "VIDA",
    "preco_base": 100.00
}
```

**Exemplo de Resposta**

```json
{
    "id": "8cfb5eb2-fd93-4322-bb74-c82f27c95a47",
    "nome": "Seguro de Vida Individual",
    "categoria": "VIDA",
    "preco_base": 100.00,
    "preco_tarifado": 106.00
}
```

Após o cálculo é necessário salvar ou atualizar o produto em uma base de dados de sua preferência (SQL ou NoSQL, podendo ser inclusive um banco de dados em memória como H2 ou HSQLDB).
O preço tarifado deve ser calculado pela API e ignorado caso seja enviado através da requisição, ou seja, caso o corpo da requisição contenha o campo preço tarifado, o mesmo deve ser ignorado.
Os produtos de seguros podem pertencer as seguintes categorias:
- VIDA
- AUTO
- VIAGEM
- RESIDENCIAL
- PATRIMONIAL
Os impostos devem ser aplicados da seguinte forma:

| **Categoria** | **Imposto sobre Operação Finaneira (IOF)** | **Programa de Integração Social (PIS)** | **Contribuição para o Financiamento da Seguridade Social (COFINS)** |
|---------------|--------------------------------------------|-----------------------------------------|----------------------------------------------------------------|
| VIDA          | 1%                                         | 2%                                      | 3%                                                             |
| AUTO          | 5.5%                                       | 4%                                      | 1%                                                             |
| VIAGEM        | 2%                                         | 4%                                      | 1%                                                             |
| RESIDENCIAL   | 4%                                         | 2.5%                                    | 3%                                                             |
| PATRIMONIAL   | 5%                                         | 3%                                      | 2%                                                             |


**Fórmula**
Preço Tarifado = Preço Base + (Preço Base x IOF) + (Preço Base x PIS) + (Preço Base x COFINS)

**Exemplos**
Seguro de Vida com preço informado de **R$ 100.00**
**Preço tarifado**: 100.00 + (100.00 x 0.01) + (100.00 x 0.02) + (100.00 x 0.03) = R$ 106,00

Seguro Auto com preço informado de **R$ 50.00**
**Preço tarifado**: 50.00 + (50.00 x 0.0055) + (50.00 x 0.04) + (50.00 x 0.01) = R$ 106,00

### Pontos que daremos mais atenção
- Testes de unidade e integração
- Arquitetura utilizada
- Abstração, acoplamento, extensibilidade e coesão
- Profundidade na utilização de Design Patterns
- Clean Clode
- SOLID
- Documentação da Solução no README.md
- Observabilidade

### Pontos que não iremos avaliar
- Dockerfile
- Scripts CI/CD
- Collections do Postman, Insomnia ou qualquer outra ferramenta para execução

### Sobre a documentação
Nesta etapa do processo seletivo queremos entneder as decisões por trás do código, portanto é fundamental que o README.md tenha algumas informações referentes a sua solução.
Algumas dicas do que esperamos ver são:
- Instruções básicas de como executar o projeto
- Detalhes sobre a solução, gostaríamos de saber qual foi o seu racional nas decisões
- Caso algo não esteja claro e você precisou assumir alguma premissa, quais foram e o que te motivou a tomar essas decisões;

### Como esperamos receber sua solução
Esta etapa é elimintória, e por isso esperamos que o código reflita essa importância.
Se tiver algum imprevisto, dúvida ou problema, por favor entre em contato com a gente, estamos aqui para ajudar.
Atualmente trabalhamos com a stack Java/Spring, porém você pode utilizar a tecnologia de sua preferência.
Para candidatos do externos nos envie o link de um repositório público com a sua solução e para candidatos internos o projeto em formato .zip
