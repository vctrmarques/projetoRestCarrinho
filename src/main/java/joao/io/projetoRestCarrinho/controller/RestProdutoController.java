package joao.io.projetoRestCarrinho.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import joao.io.projetoRestCarrinho.exception.CarrinhoException;
import joao.io.projetoRestCarrinho.model.Produto;
import joao.io.projetoRestCarrinho.rest.Resposta;
import joao.io.projetoRestCarrinho.service.ProdutoService;
import joao.io.projetoRestCarrinho.util.Constantes;

@RestController
public class RestProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	Logger logger = LoggerFactory.getLogger(RestProdutoController.class);
	
	@RequestMapping(path=Constantes.Url.URL_PRODUTO, method = RequestMethod.GET)
	public @ResponseBody Resposta consultarTodos() {
		Resposta resposta = new Resposta();
		try {
			resposta.setResposta(produtoService.buscarTodos());
			logger.info("Consultando todos os produtos");
		}catch(CarrinhoException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_PRODUTO + "/{id}", method = RequestMethod.GET)
	public @ResponseBody Resposta consultarProdutoPorId(@PathVariable Long id) {
		Resposta resposta = new Resposta();
		try {
			Produto produto = produtoService.buscarProdutoPorId(id);
			resposta.setResposta(produto); 
			logger.info(String.format("Consultando produto %s", id));
		}catch(CarrinhoException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_PRODUTO, method = RequestMethod.POST)
	public @ResponseBody Resposta salvarProduto(@RequestBody Produto produto) {
		Resposta resposta = new Resposta();
		try {
			produto.setId(null);
			produtoService.salvarProduto(produto);
			resposta.setResposta(produto); 
			logger.info(String.format("Salvando novo produto %s", produto.getNome()));
		}catch(CarrinhoException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_PRODUTO + "/{id}", method = RequestMethod.PUT)
	public @ResponseBody Resposta atualizarProduto(@RequestBody Produto produtoAtualizado, @PathVariable Long id) {
		Resposta resposta = new Resposta();
		try {
			Produto produtoSalvo = produtoService.buscarProdutoPorId(id);
			produtoAtualizado.setId(produtoSalvo.getId());
			Produto produto = produtoService.salvarProduto(produtoAtualizado);
			resposta.setResposta(produto);
			logger.info(String.format("Atualizando produto %s", id));
		}catch(CarrinhoException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_PRODUTO + "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Resposta removerProduto(@PathVariable Long id) {
		Resposta resposta = new Resposta();
		try {
			produtoService.removerProduto(id);
			logger.info(String.format("Removendo produto %s", id));
		}catch(CarrinhoException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
}
