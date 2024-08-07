package br.com.soc.sistema.filter;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.examesfuncionario.ExamesFuncionarioDAO;
import br.com.soc.sistema.infra.FiltroBusca;
import br.com.soc.sistema.vo.ExamesFuncionarioVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class ExamesFuncionarioFilter {

	ExamesFuncionarioDAO daoExameFuncionario = new ExamesFuncionarioDAO();

	public String checkErros(FiltroBusca filtroBusca){
		
		String errosFound = null;
		
		switch(filtroBusca.getTipoFiltro()) {
			case "Nome":
				if(isNumero(filtroBusca.getConteudo())) {
					errosFound = "A busca por nomes nao deve possuir numeros";
				}
				break;
								
			case "Exame":
				
				if(isNumero(filtroBusca.getConteudo())) {
					errosFound = "A busca por Exames nao deve possuir numeros";
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
	
	
	public List<ExamesFuncionarioVo> selectFilter(FiltroBusca filtroBusca) {
		
		List<ExamesFuncionarioVo> examesFuncionariosFiltrados = new ArrayList<>();
		
		switch(filtroBusca.getTipoFiltro()) {
			case "Nome":
				examesFuncionariosFiltrados = daoExameFuncionario.getExameFuncionarioByNome(filtroBusca.getConteudo());
				return examesFuncionariosFiltrados;
				
			case "Exame":
				examesFuncionariosFiltrados = daoExameFuncionario.getExameFuncionarioByExame(filtroBusca.getConteudo());
				return examesFuncionariosFiltrados;

			case "ID":
				ExamesFuncionarioVo exameFuncionarioID = daoExameFuncionario.getExameFuncionarioById(filtroBusca.getConteudo());
				examesFuncionariosFiltrados.add(exameFuncionarioID);
				return examesFuncionariosFiltrados;

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
