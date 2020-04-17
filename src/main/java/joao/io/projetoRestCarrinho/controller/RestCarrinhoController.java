package joao.io.projetoRestCarrinho.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import joao.io.projetoRestCarrinho.exception.CarrinhoException;
import joao.io.projetoRestCarrinho.model.Produto;
import joao.io.projetoRestCarrinho.rest.Resposta;
import joao.io.projetoRestCarrinho.rest.RespostaCarrinho;
import joao.io.projetoRestCarrinho.service.ProdutoService;
import joao.io.projetoRestCarrinho.util.Constantes;

@RestController
public class RestCarrinhoController {

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
    private MessageSource messageSource;
	
	Logger logger = LoggerFactory.getLogger(RestCarrinhoController.class);
	
	private List<Produto> carrinho = new ArrayList<Produto>();
	
	@RequestMapping(path=Constantes.Url.URL_CARRINHO, method = RequestMethod.GET)
	public @ResponseBody RespostaCarrinho consultarTodos() {
		RespostaCarrinho resposta = new RespostaCarrinho();
		if (carrinho.isEmpty()) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(messageSource.getMessage("carrinho.erro.vazio", null, Locale.getDefault()));
		}else {
			resposta.setResposta(carrinho);
			Double valorTotal = new Double(0);
			Integer qtdTotal = new Integer(0);
			Integer cupomDesconto = new Integer(0);
			for (Produto produto : carrinho) {
				if(produto.getQuantidade() >= 10) {
					Double valor = produto.getValor() * produto.getQuantidade();
					Double valorDesconto = produto.getValor() - (0.1 * valor);
					valorTotal = valorTotal + valorDesconto;
					
				}else {
					valorTotal = valorTotal + (produto.getValor() * produto.getQuantidade());
					qtdTotal = qtdTotal + produto.getQuantidade();
				}
				
				// pegara  sempre o maior cupom.
				if(cupomDesconto == 0) {
					cupomDesconto = produto.getCupom();
				}else {
					 if(produto.getCupom() >= cupomDesconto) {
						 cupomDesconto = produto.getCupom();
					}
				}
			}
			
			//Regra de Negócio da seleção Facilit
			if(valorTotal >= 1000 && valorTotal < 5000) {
				valorTotal = valorTotal - (0.05 * valorTotal);
			}else if(valorTotal >= 5000 && valorTotal < 10000) {
				valorTotal = valorTotal - (0.07 * valorTotal);
			}else if(valorTotal >= 10000) {
				valorTotal = valorTotal - (0.1 * valorTotal);
			}
			
            double percentual = cupomDesconto / 100.0;
            
			resposta.setValorTotal(valorTotal);
			resposta.setQtdTotal(qtdTotal);
			resposta.setCupomDesconto(cupomDesconto);
			resposta.setQtdTotalComDescontoCupom(valorTotal - (percentual * valorTotal));
		}		
		logger.info("Consultando todos os produtos no carrinho");
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_CARRINHO + "/{id}", method = RequestMethod.GET)
	public @ResponseBody Resposta consultarProdutoPorId(@PathVariable Long id) {
		Resposta resposta = new Resposta();
		List<Produto> lstProduto = new ArrayList<Produto>();
		for (Produto produto : carrinho) {
			if (produto.getId() == id) {
				lstProduto.add(produto);
			}
		}	
		if(lstProduto.isEmpty()) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(String.format(messageSource.getMessage("produto.erro.produto.naoencontrado", null, Locale.getDefault()), id));
		}else {
			resposta.setResposta(lstProduto);	
		}			 
		logger.info(String.format("Consultando produto %s no carrinho", id));
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_CARRINHO, method = RequestMethod.POST)
	public @ResponseBody Resposta adicionarProduto(@RequestBody Produto produto) {
		Resposta resposta = new Resposta();
		try {
			Long id = produto .getId();			
			if (id == null || id == 0) {
				resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
				resposta.setMensagem(String.format(messageSource.getMessage("campo.obrigatorio", null, Locale.getDefault()), 
						messageSource.getMessage("campo.id", null, Locale.getDefault())));
			}else {
				Produto produtoJaAdicionado = null;
				for (Produto p : carrinho) {
					if (p.getId() == id) {
						produtoJaAdicionado = p;
						break;
					}
				}
				if (produtoJaAdicionado == null) {
					Produto produtoSalvo = produtoService.buscarProdutoPorId(id);
					produtoSalvo.setQuantidade(1);
					produtoSalvo.setValorTotal(produtoSalvo.getValor());
					produtoSalvo.setCupom(0);
					carrinho.add(produtoSalvo);
					resposta.setResposta(carrinho);
				}else {
					resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
					resposta.setMensagem(messageSource.getMessage("carrinho.erro.produto.ja.adicionado", null, Locale.getDefault()));
				}								 
			}			
			logger.info(String.format("Adicionando produto %s no carrinho", produto.getId()));
		}catch(CarrinhoException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_CARRINHO + "/{id}", method = RequestMethod.PUT)
	public @ResponseBody Resposta atualizarQuantidadeProduto(@RequestBody Produto produto, @PathVariable Long id) {
		Resposta resposta = new Resposta();		
		Produto produtoInformado = null;
		for (Produto p : carrinho) {
			if (p.getId() == id) {
				produtoInformado = p;
				break;
			}
		}
		if (produtoInformado == null) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(messageSource.getMessage("carrinho.erro.produto.nao.encontrado.para.atualizacao", null, Locale.getDefault()));
		}else {
			Integer novaQtd = produto.getQuantidade();
			Integer novoCupom = produto.getCupom();
			if (novaQtd == 0) {
				carrinho.remove(produtoInformado);
			}else {
				produtoInformado.setQuantidade(novaQtd);
				produtoInformado.setValorTotal(produtoInformado.getValor() * novaQtd);
				produtoInformado.setCupom(novoCupom);
			}
		}
		logger.info(String.format("Atualizando a quantidade do produto %s no carrinho", id));		
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_CARRINHO + "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Resposta removerProdutoCarrinho(@PathVariable Long id) {
		Resposta resposta = new Resposta();	
		Produto produtoSelecionado = null;
		if (id == null || id == 0) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(String.format(messageSource.getMessage("campo.obrigatorio", null, Locale.getDefault()), 
					messageSource.getMessage("campo.id", null, Locale.getDefault())));
		}else {
			for (Produto p : carrinho) {
				if (p.getId() == id) {
					produtoSelecionado = p;
					break;
				}
			}
			if (produtoSelecionado == null) {
				resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
				resposta.setMensagem(messageSource.getMessage("carrinho.erro.produto.nao.encontrado", null, Locale.getDefault()));
			}else {
				carrinho.remove(produtoSelecionado);
			}
		}
		logger.info(String.format("Removendo produto %s", id));
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_LIMPAR_CARRINHO, method = RequestMethod.GET)
	public @ResponseBody Resposta limparCarrinho() {
		Resposta resposta = new Resposta();
		carrinho.clear();
		logger.info("Carrinho limpado!");
		return resposta;
	}

}
