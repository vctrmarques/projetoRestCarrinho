package joao.io.projetoRestCarrinho.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import joao.io.projetoRestCarrinho.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {
	
	@Query("SELECT p FROM Produto p WHERE p.dataExclusao IS NULL AND p.id = :id")
	public Optional<Produto> findById(@Param("id") Long id);
	
	@Query("SELECT p FROM Produto p WHERE p.dataExclusao IS NULL")
	public List<Produto> buscarTodosProdutos();

}
