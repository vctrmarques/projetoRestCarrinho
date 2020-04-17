package joao.io.projetoRestCarrinho.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RespostaCarrinho extends Resposta{
	
	@JsonInclude(Include.NON_NULL)
	private Double valorTotal;
	
	@JsonInclude(Include.NON_NULL)
	private Integer qtdTotal;
	
	@JsonInclude(Include.NON_NULL)
	private Double qtdTotalComDescontoCupom;
	
	@JsonInclude(Include.NON_NULL)
	private Integer cupomDesconto;

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Integer getQtdTotal() {
		return qtdTotal;
	}

	public void setQtdTotal(Integer qtdTotal) {
		this.qtdTotal = qtdTotal;
	}

	public Integer getCupomDesconto() {
		return cupomDesconto;
	}

	public void setCupomDesconto(Integer cupomDesconto) {
		this.cupomDesconto = cupomDesconto;
	}

	public Double getQtdTotalComDescontoCupom() {
		return qtdTotalComDescontoCupom;
	}

	public void setQtdTotalComDescontoCupom(Double qtdTotalComDescontoCupom) {
		this.qtdTotalComDescontoCupom = qtdTotalComDescontoCupom;
	}
	
}
