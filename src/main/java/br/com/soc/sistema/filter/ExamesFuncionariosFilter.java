package br.com.soc.sistema.filter;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.examesfuncionarios.ExamesFuncionariosDAO;
import br.com.soc.sistema.infra.FiltroBusca;
import br.com.soc.sistema.vo.ExamesFuncionariosVo;

public class ExamesFuncionariosFilter {

	ExamesFuncionariosDAO daoExameFuncionario = new ExamesFuncionariosDAO();

	public List<ExamesFuncionariosVo> selectFilter(FiltroBusca filtroBusca) {
		
		List<ExamesFuncionariosVo> examesFuncionariosFiltrados = new ArrayList<>();
		
		switch(filtroBusca.getTipoFiltro()) {
			case "Nome":
				try {
					 Integer.parseInt(filtroBusca.getConteudo());
					 throw new IllegalArgumentException("O Nome nao deve ser um numero");

				}
				catch(NumberFormatException e) {
					 examesFuncionariosFiltrados = daoExameFuncionario.getExameFuncionarioByNome(filtroBusca.getConteudo());
					 return examesFuncionariosFiltrados;
				}
				
			case "Exame":
				try {
					 Integer.parseInt(filtroBusca.getConteudo());
					 throw new IllegalArgumentException("O Exame nao deve ser um numero");

				}
				catch(NumberFormatException e) {
					examesFuncionariosFiltrados = daoExameFuncionario.getExameFuncionarioByExame(filtroBusca.getConteudo());
					 return examesFuncionariosFiltrados;
				}
			
		}
		return null;
	}
}
