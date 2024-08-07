package br.com.soc.sistema.filter;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.funcionarios.FuncionariosDAO;
import br.com.soc.sistema.infra.FiltroBusca;
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionariosFilter {
	
	FuncionariosDAO daoFuncionario = new FuncionariosDAO();

	public String checkErros(FiltroBusca filtroBusca){
		
		String errosFound = null;
		
		switch(filtroBusca.getTipoFiltro()) {
			case "Nome":
				if(isNumero(filtroBusca.getConteudo())) {
					errosFound = "A busca por nomes nao deve possuir numeros";
				}
				break;
				
			case "ID":
				if(!isNumero(filtroBusca.getConteudo())) {
					errosFound = "A busca por ID so deve possuir numeros";
				}
				break;
		}
		return errosFound;
	}
	
	public List<FuncionarioVo> selectFilter(FiltroBusca filtroBusca) {
		
		List<FuncionarioVo> funcionariosFiltrados = new ArrayList<>();
		
		switch(filtroBusca.getTipoFiltro()) {
			case "ID":
				FuncionarioVo funcionario = daoFuncionario.selectById(filtroBusca.getConteudo());
				funcionariosFiltrados.add(funcionario);
				return funcionariosFiltrados;
			case "Nome":
				funcionariosFiltrados = daoFuncionario.selectByNome(filtroBusca.getConteudo());
				return funcionariosFiltrados;	
		}
		return null;
	}
	
	public boolean isNumero(String conteudo) {
		try {
			Integer.parseInt(conteudo);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
}
