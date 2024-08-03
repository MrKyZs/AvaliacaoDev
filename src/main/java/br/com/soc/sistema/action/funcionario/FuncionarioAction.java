package br.com.soc.sistema.action.funcionario;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.business.FuncionarioBusiness;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.infra.FiltroBusca;
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionarioAction extends Action{
	private FuncionarioVo funcionarioVo = new FuncionarioVo();
	private FuncionarioBusiness business = new FuncionarioBusiness();
	private List<FuncionarioVo> listFuncionarios = new ArrayList<>();
	private FiltroBusca filtroBusca;

	public String todos() {

		listFuncionarios = business.mostrarFuncionarios();
		
		return SUCCESS;
	}
	
	public String novo() {
		if(funcionarioVo.getNome() == null) {
			return INPUT;
		}

		business.inserirFuncionario(funcionarioVo);
		
		return REDIRECT;
	}
	
	public String editar() {
		
		funcionarioVo = business.getFuncionarioId(funcionarioVo.getRowid());
		
		return "editar";
	}
	
	public String salvar() {
		
		business.editarFuncionario(funcionarioVo);
		
		return REDIRECT;
	}
	
	public String excluir() {
		
		business.excluirFuncionario(funcionarioVo.getRowid());
		
		return REDIRECT;
	}
	
	public String filtrar() {
		if(filtroBusca.getTipoFiltro() == "" || filtroBusca.getConteudo() == "") {
			return REDIRECT;
		}

		listFuncionarios = business.mostrarResultadoFiltro(filtroBusca);
		
		return SUCCESS;
	}
	
	public FuncionarioVo getFuncionarioVo() {
		return funcionarioVo;
	}
	public void setFuncionarioVo(FuncionarioVo funcionarioVo) {
		this.funcionarioVo = funcionarioVo;
	}
	public List<FuncionarioVo> getListFuncionarios() {
		return listFuncionarios;
	}
	public void setListFuncionarios(List<FuncionarioVo> listFuncionarios) {
		this.listFuncionarios = listFuncionarios;
	}
	public FiltroBusca getFiltroBusca() {
		return filtroBusca;
	}
	public void setFiltroBusca(FiltroBusca filtroBusca) {
		this.filtroBusca = filtroBusca;
	}


}
