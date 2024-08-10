package br.com.soc.sistema.business;

import java.sql.Date;
import java.util.List;

import br.com.soc.sistema.dao.examesfuncionario.ExamesFuncionarioDAO;
import br.com.soc.sistema.filter.ExamesFuncionarioFilter;
import br.com.soc.sistema.infra.FiltroBusca;
import br.com.soc.sistema.vo.ExamesFuncionarioVo;

public class ExamesFuncionarioBusiness {

	ExamesFuncionarioDAO daoExameFuncionario = new ExamesFuncionarioDAO();
	ExamesFuncionarioFilter filtroExamesFuncionarios = new ExamesFuncionarioFilter();

	public List<ExamesFuncionarioVo> mostrarFuncionarios() {
		return daoExameFuncionario.getAllExameFuncionarios();
	}
	
	public boolean checarCopias(ExamesFuncionarioVo examesFuncionarios) {
		return daoExameFuncionario.possuiCopia(examesFuncionarios);
	}
	
	public void inserirExamesFuncionarios(ExamesFuncionarioVo examesFuncionarios) {
		daoExameFuncionario.inserirExameFuncionario(examesFuncionarios);
	}
	
	public ExamesFuncionarioVo getExamesFuncionariosById(String id) {
		return daoExameFuncionario.getExameFuncionarioById(id);
	}
	
	public void updateExamesFuncionarios(ExamesFuncionarioVo examesFuncionarios) {
		daoExameFuncionario.updateExameFuncionario(examesFuncionarios);
	}
	
	public void deleteExamesFuncionarios(String id) {
		daoExameFuncionario.deleteExameFuncionario(id);
	}
	
	public String checkErros(FiltroBusca filtroBusca){
		return filtroExamesFuncionarios.checkErros(filtroBusca);
	}
	
	public List<ExamesFuncionarioVo> getResultFilter(FiltroBusca filtroBusca){
		return filtroExamesFuncionarios.selectFilter(filtroBusca);
	}
	
	public Date changeToDateType(String date) {
		return Date.valueOf(date);
	}
	
	public String changeToStringType(Date date) {
		return String.valueOf(date);
	}
}
