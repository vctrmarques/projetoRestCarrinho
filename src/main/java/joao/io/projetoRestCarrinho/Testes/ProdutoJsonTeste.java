package joao.io.projetoRestCarrinho.Testes;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.path.json.JsonPath;
import joao.io.projetoRestCarrinho.model.Produto;

public class ProdutoJsonTeste {

	@Test
	public void consultarTodos() {
		 JsonPath path = given()
	                .header("Accept", "application/json")
	                .get("http://localhost:8081/produto")
	                .andReturn().jsonPath();

	   List<Produto> produtos = path.getList("resposta", Produto.class);
	   
       Assert.assertThat(produtos.get(0).getId(),is(notNullValue()));
	   Assert.assertEquals("relógio analógico dumont masculino - du2036mfn/4p dourado", produtos.get(0).getNome());
	   Assert.assertThat(produtos.get(0).getValor(), is(800.0));
	   
	   Assert.assertThat(produtos.get(1).getId(),is(notNullValue()));
	   Assert.assertEquals("bicicleta aro 29 caloi vulcan com suspensão dianteira - preta", produtos.get(1).getNome());
	   Assert.assertThat(produtos.get(1).getValor(), is(899.0));
	}
}
