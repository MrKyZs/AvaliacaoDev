package br.com.soc.sistema.action.funcionarios;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.business.FuncionariosBusiness;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.infra.FiltroBusca;
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionariosAction extends Action{
	private FuncionarioVo funcionarioVo = new FuncionarioVo();
	private FuncionariosBusiness business = new FuncionariosBusiness();
	private List<FuncionarioVo> listFuncionarios = new ArrayList<>();
	private FiltroBusca filtroBusca;

	public String todos() {

		listFuncionarios = business.mostrarFuncionarios();
		
		return SUCCESS;
	}
	
	public String exibirNovo() {
		return INPUT;
	}
	
	public String novo() {
		if(funcionarioVo.getNome() == "") {
			addFieldError("campoNome", "Favor preencher o campo nome");
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
		
		if(funcionarioVo.getNome() == "") {
			funcionarioVo = business.getFuncionarioId(funcionarioVo.getRowid());
			addFieldError("campoNome", "Favor preencher o campo nome");
			return "editar";
		}
		
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
		
		if(listFuncionarios.isEmpty() || listFuncionarios.get(0) == null) {
			return REDIRECT;
		}
		
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
