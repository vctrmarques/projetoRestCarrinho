# projetoRestCarrinho
Carrinho de compra através da API Rest:

API Rest Produto

1. Listar itens no produto:
URL: http://localhost:8081/produto
Método: GET

2. Adicionar produto no produto:
URL: http://localhost:8081/produto
Método: POST
Exemplo request:
{
  "nome":"carrinho hot wheels unidade",
  "valor": 10
}

3. Remover produto do produto:
URL: http://localhost:8081/produto/<ID_DO_PRODUTO>
Método: DELETE

4. Atualizar quantidade do produto:
URL: http://localhost:8081/produto/<ID_DO_PRODUTO>
Método: PUT
Exemplo request:
{
	"id": 1
	"nome":"carrinho hot wheels unidade",
	"valor": 10
}

5. Listar um iten especifico no produto:
URL: http://localhost:8081/produto/<ID_DO_PRODUTO>
Método: GET

API Rest Carrinho

1. Listar itens no produto:
URL: http://localhost:8081/carrinho
Método: GET

2. Adicionar produto no carrinho:
URL: http://localhost:8081/carrinho
Método: POST
Exemplo request:
{
  "nome":"carrinho hot wheels unidade",
  "valor": 10
}

3. Remover produto do carrinho:
URL: http://localhost:8081/carrinho/<ID_DO_PRODUTO>
Método: DELETE

4. Atualizar quantidade do carrinho:
URL: http://localhost:8081/carrinho/<ID_DO_PRODUTO>
Método: PUT
Exemplo request:
{
	"id": 1
	"nome":"carrinho hot wheels unidade",
	"valor": 10
}

5. Listar um iten especifico no produto:
URL: http://localhost:8081/carrinho/<ID_DO_PRODUTO>
Método: GET

6. Esvaziar carrinho:

URL: http://localhost:8081/carrinho/limpar
Método: GET

Roteiro básico de instalação
Criar o schema "db_carrinho" na base de dados;
Definir um usuário com acesso de escrita e consulta ao novo schema;
Baixar código fonte:
git clone 
Executar o script SQL que se encontra no diretório "sql";
Editar o arquivo "pom.xml" atualizando as configurações da base de dados;
Executar o seguinte comando onde se encontra o arquivo pom.xml:
mvn clean install
