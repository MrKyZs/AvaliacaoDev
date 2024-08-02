package br.com.soc.sistema.business;

import java.util.List;

import br.com.soc.sistema.dao.funcionarios.FuncionarioDAO;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionarioBusiness {
	
	FuncionarioDAO daoFuncionario = new FuncionarioDAO();
	
	public void inserirFuncionario(FuncionarioVo funcionarioVo) {
		try {
			if(funcionarioVo.getNome().isEmpty())
				throw new IllegalArgumentException("Nome nao pode ser em branco");
			
			daoFuncionario.insertFuncionario(funcionarioVo);
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a inclusao do registro");
		}
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
}
