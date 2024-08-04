package br.com.soc.sistema.business;

import java.util.List;

import br.com.soc.sistema.dao.funcionarios.FuncionarioDAO;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.filter.FuncionarioFilter;
import br.com.soc.sistema.infra.FiltroBusca;
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionarioBusiness {
	
	FuncionarioDAO daoFuncionario = new FuncionarioDAO();
	FuncionarioFilter filtroFuncionario = new FuncionarioFilter();
	
	public void inserirFuncionario(FuncionarioVo funcionarioVo) {
		daoFuncionario.insertFuncionario(funcionarioVo);
	}
	
	public List<FuncionarioVo> mostrarFuncionarios() {
		return daoFuncionario.selectAllFuncionarios();
	}
	
	public FuncionarioVo getFuncionarioId(String id) {
		
		FuncionarioVo funcionarioVo = daoFuncionario.selectById(id);  
		
		return funcionarioVo;
	}

	public void editarFuncionario(FuncionarioVo funcionarioVo) {
		
		daoFuncionario.updateFuncionario(funcionarioVo);	
	}

	public void excluirFuncionario(String id) {
		daoFuncionario.deleteFuncionario(id);
	}
	
	public List<FuncionarioVo> mostrarResultadoFiltro(FiltroBusca filtroBusca){
		return filtroFuncionario.selectFilter(filtroBusca);
	}
}
