package br.com.soc.sistema.action.exame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.soc.sistema.business.ExameBusiness;
import br.com.soc.sistema.filter.ExameFilter;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.infra.OpcoesComboBuscarExames;
import br.com.soc.sistema.vo.ExameVo;

public class ExameAction extends Action {
	private List<ExameVo> exames = new ArrayList<>();
	private ExameBusiness business = new ExameBusiness();
	private ExameFilter filtrar = new ExameFilter();
	private ExameVo exameVo = new ExameVo();
	private String errosFound;
	private boolean exameExcluir;

	public String todos() {
		exames.addAll(business.trazerTodosOsExames());	

		return SUCCESS;
	}
	
	public String filtrar() {
		if(filtrar.isNullOpcoesCombo()) {
			return REDIRECT;
		}
		
		errosFound = business.checkErros(filtrar);
		
		if(errosFound == null) {
			exames = business.filtrarExames(filtrar);
			if(exames.isEmpty()) {
				return REDIRECT;
			}		
		}
		else {
			return SUCCESS;
		}

		
		exames = business.filtrarExames(filtrar);
		
		return SUCCESS;
	}
	
	public String exibirNovo() {
		return INPUT;
	}
	
	public String novo() {
		if(exameVo.getNome() == "") {
			addFieldError("campoNome", "Favor preencher o campo nome");
			return INPUT;
		}
		
		business.salvarExame(exameVo);
		
		return REDIRECT;
	}
	
	public String editar() {

		exameVo = business.buscarExamePor(exameVo.getRowid());		

		return "editar";
	}
	
	public String salvar() {

		if(exameVo.getNome() == "") {
			exameVo = business.buscarExamePor(exameVo.getRowid());		
			addFieldError("campoNome", "Favor preencher o campo nome");
			return "editar";
		}
		
		business.editarExame(exameVo);
		return REDIRECT;
	}
	
	public String excluir(){

		if(business.exameRealizado(exameVo.getRowid())){
			business.excluirExame(exameVo.getRowid());
		}
		else {
			errosFound = "O exame escolhido possui agendamento com algum funcionario";
			return SUCCESS;
		}
		
		return REDIRECT;
	}
	
	public List<OpcoesComboBuscarExames> getListaOpcoesCombo(){
		return Arrays.asList(OpcoesComboBuscarExames.values());
	}
	
	public List<ExameVo> getExames() {
		return exames;
	}

	public void setExames(List<ExameVo> exames) {
		this.exames = exames;
	}

	public ExameFilter getFiltrar() {
		return filtrar;
	}

	public void setFiltrar(ExameFilter filtrar) {
		this.filtrar = filtrar;
	}

	public ExameVo getExameVo() {
		return exameVo;
	}

	public void setExameVo(ExameVo exameVo) {
		this.exameVo = exameVo;
	}
	public String getErrosFound() {
		return errosFound;
	}
	public void setErrosFound(String errosFound) {
		this.errosFound = errosFound;
	}
	public boolean isExameExcluir() {
		return exameExcluir;
	}
	public void setExameExcluir(boolean exameExcluir) {
		this.exameExcluir = exameExcluir;
	}
}
