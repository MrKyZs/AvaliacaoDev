package br.com.soc.sistema.business;

import java.sql.Date;
import java.util.List;

import br.com.soc.sistema.dao.examesfuncionarios.ExamesFuncionariosDAO;
import br.com.soc.sistema.filter.ExamesFuncionariosFilter;
import br.com.soc.sistema.infra.FiltroBusca;
import br.com.soc.sistema.vo.ExamesFuncionariosVo;

public class ExamesFuncionarioBusiness {

	ExamesFuncionariosDAO daoExameFuncionario = new ExamesFuncionariosDAO();
	ExamesFuncionariosFilter filtroExamesFuncionarios = new ExamesFuncionariosFilter();

	public List<ExamesFuncionariosVo> mostrarFuncionarios() {
		return daoExameFuncionario.getAllExameFuncionarios();
	}
	
	public void inserirExamesFuncionarios(ExamesFuncionariosVo examesFuncionarios) {
		daoExameFuncionario.inserirExameFuncionario(examesFuncionarios);
	}
	
	public ExamesFuncionariosVo getExamesFuncionariosById(String id) {
		return daoExameFuncionario.getExameFuncionarioById(id);
	}
	
	public void updateExamesFuncionarios(ExamesFuncionariosVo examesFuncionarios) {
		daoExameFuncionario.updateExameFuncionario(examesFuncionarios);
	}
	
	public void deleteExamesFuncionarios(String id) {
		daoExameFuncionario.deleteExameFuncionario(id);
	}
	
	public List<ExamesFuncionariosVo> getResultFilter(FiltroBusca filtroBusca){
		return filtroExamesFuncionarios.selectFilter(filtroBusca);
	}
	
	public Date changeToDateType(String date) {
		return Date.valueOf(date);
	}
	
	public String changeToStringType(Date date) {
		return String.valueOf(date);
	}
}
