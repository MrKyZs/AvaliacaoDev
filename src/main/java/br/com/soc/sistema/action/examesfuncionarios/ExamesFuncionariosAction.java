package br.com.soc.sistema.action.examesfuncionarios;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.business.ExameBusiness;
import br.com.soc.sistema.business.ExamesFuncionarioBusiness;
import br.com.soc.sistema.business.FuncionarioBusiness;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.infra.FiltroBusca;
import br.com.soc.sistema.vo.ExameVo;
import br.com.soc.sistema.vo.ExamesFuncionariosVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class ExamesFuncionariosAction extends Action{

	private ExamesFuncionariosVo examesFuncionarioVo = new ExamesFuncionariosVo();
	private ExamesFuncionarioBusiness business = new ExamesFuncionarioBusiness();
	private List<ExamesFuncionariosVo> listExamesFuncionarios = new ArrayList<>();
	private FiltroBusca filtroBusca;
	private String dataExameString;
	private Date dataExameDate;
	private FuncionarioBusiness businessFuncionario = new FuncionarioBusiness();
	private List<FuncionarioVo> listFuncionarios = new ArrayList<>();
	
	private List<ExameVo> listExames = new ArrayList<>();
	private ExameBusiness businessExames = new ExameBusiness();
	
	public String todos() {

		listExamesFuncionarios = business.mostrarFuncionarios();
		
		return SUCCESS;
	}
	
	public String exibirNovo() {
		listFuncionarios = businessFuncionario.mostrarFuncionarios();
		listExames = businessExames.trazerTodosOsExames();
		
		return INPUT;
	}
	
	public String novo() {
		if(dataExameString == "") {
			listFuncionarios = businessFuncionario.mostrarFuncionarios();
			listExames = businessExames.trazerTodosOsExames();
			addFieldError("campoData", "Favor preencher a data");
			
			return INPUT;
		}
		
		dataExameDate = business.changeToDateType(dataExameString);
		examesFuncionarioVo.setDataExame(dataExameDate);
		business.inserirExamesFuncionarios(examesFuncionarioVo);

		return REDIRECT;
	}
	
	public String editar() {
		
		examesFuncionarioVo = business.getExamesFuncionariosById(examesFuncionarioVo.getRowid());
		dataExameString = business.changeToStringType(examesFuncionarioVo.getDataExame());
	
		listFuncionarios = businessFuncionario.mostrarFuncionarios();
		listExames = businessExames.trazerTodosOsExames();
		
		return "editar";
	}
	
	public String salvar() {
		
		if(dataExameString == "") {
			
			examesFuncionarioVo = business.getExamesFuncionariosById(examesFuncionarioVo.getRowid());
			dataExameString = business.changeToStringType(examesFuncionarioVo.getDataExame());
			
			listFuncionarios = businessFuncionario.mostrarFuncionarios();
			listExames = businessExames.trazerTodosOsExames();
			addFieldError("campoData", "O campo data deve possuir uma data valida");

			return "editar";
		}
		
		dataExameDate = business.changeToDateType(dataExameString);
		examesFuncionarioVo.setDataExame(dataExameDate);
				
		business.updateExamesFuncionarios(examesFuncionarioVo);
		
		return REDIRECT;
	}
	
	public String excluir() {
		
		business.deleteExamesFuncionarios(examesFuncionarioVo.getRowid());
		
		return REDIRECT;
	}
	
	public String filtrar() {
		if(filtroBusca.getTipoFiltro() == "" || filtroBusca.getConteudo() == "") {
			return REDIRECT;
		}

		listExamesFuncionarios = business.getResultFilter(filtroBusca);
		
		if(listExamesFuncionarios.isEmpty()) {
			return REDIRECT;
		}
		
		return SUCCESS;
	}
	
	public ExamesFuncionariosVo getExamesFuncionarioVo() {
		return examesFuncionarioVo;
	}
	public void setExamesFuncionarioVo(ExamesFuncionariosVo examesFuncionarioVo) {
		this.examesFuncionarioVo = examesFuncionarioVo;
	}
	public List<ExamesFuncionariosVo> getListExamesFuncionarios() {
		return listExamesFuncionarios;
	}
	public void setListExamesFuncionarios(List<ExamesFuncionariosVo> listExamesFuncionarios) {
		this.listExamesFuncionarios = listExamesFuncionarios;
	}
	public List<FuncionarioVo> getListFuncionarios() {
		return listFuncionarios;
	}
	public void setListFuncionarios(List<FuncionarioVo> listFuncionarios) {
		this.listFuncionarios = listFuncionarios;
	}
	public List<ExameVo> getListExames() {
		return listExames;
	}
	public void setListExames(List<ExameVo> listExames) {
		this.listExames = listExames;
	}
	public String getDataExameString() {
		return dataExameString;
	}
	public void setDataExameString(String dataExameString) {
		this.dataExameString = dataExameString;
	}
	public Date getDataExameDate() {
		return dataExameDate;
	}
	public void setDataExameDate(Date dataExameDate) {
		this.dataExameDate = dataExameDate;
	}
	public FiltroBusca getFiltroBusca() {
		return filtroBusca;
	}
	public void setFiltroBusca(FiltroBusca filtroBusca) {
		this.filtroBusca = filtroBusca;
	}
}
