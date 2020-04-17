package joao.io.projetoRestCarrinho.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import joao.io.projetoRestCarrinho.util.Constantes;

public class Resposta {
	
	private Integer codigo;
	
	@JsonInclude(Include.NON_NULL)
	private String mensagem;
	
	@JsonInclude(Include.NON_NULL)
	private Object resposta;
	
	public Resposta() {
		this.codigo = Constantes.Status.CODIGO_SUCESSO;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Object getResposta() {
		return resposta;
	}

	public void setResposta(Object resposta) {
		this.resposta = resposta;
	}


}
