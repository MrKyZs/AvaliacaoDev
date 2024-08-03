package br.com.soc.sistema.filter;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.funcionarios.FuncionarioDAO;
import br.com.soc.sistema.infra.FiltroBusca;
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionarioFilter {
	
	FuncionarioDAO daoFuncionario = new FuncionarioDAO();

	
	public List<FuncionarioVo> selectFilter(FiltroBusca filtroBusca) {
		
		List<FuncionarioVo> funcionariosFiltrados = new ArrayList<>();
		
		switch(filtroBusca.getTipoFiltro()) {
			case "ID":
				try {
					 Integer.parseInt(filtroBusca.getConteudo());
					 FuncionarioVo funcionario = daoFuncionario.selectById(filtroBusca.getConteudo());
					 funcionariosFiltrados.add(funcionario);
					 return funcionariosFiltrados;
				}
				catch(NumberFormatException e) {
					throw new IllegalArgumentException("O ID deve ser um numero");
				}
				
			case "Nome":
				try {
					 Integer.parseInt(filtroBusca.getConteudo());
					 throw new IllegalArgumentException("O Nome nao deve ser um numero");

				}
				catch(NumberFormatException e) {
					 funcionariosFiltrados = daoFuncionario.selectByNome(filtroBusca.getConteudo());
					 return funcionariosFiltrados;
				}
			
		}
		return null;
	}
	
}
