package joao.io.projetoRestCarrinho.util;

public class Constantes {
	
	public interface Status{
		public static final int CODIGO_SUCESSO = 0;
		public static final int CODIGO_ERRO = -1;
	}
	
	public interface Url{
		public static final String URL_PRODUTO = "/produto";
		public static final String URL_CARRINHO = "/carrinho";
		public static final String URL_LIMPAR_CARRINHO = "/carrinho/limpar";
	}
}
