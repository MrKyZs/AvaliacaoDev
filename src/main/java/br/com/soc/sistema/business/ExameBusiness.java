package br.com.soc.sistema.business;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.exames.ExameDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.filter.ExameFilter;
import br.com.soc.sistema.infra.FiltroBusca;
import br.com.soc.sistema.vo.ExameVo;

public class ExameBusiness {

	private static final String FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO = "Foi informado um caracter no lugar de um numero";
	private ExameDao dao;
	
	public ExameBusiness() {
		this.dao = new ExameDao();
	}
	
	public List<ExameVo> trazerTodosOsExames(){
		return dao.findAllExames();
	}	
	
	public void salvarExame(ExameVo exameVo) {
		try {
			if(exameVo.getNome().isEmpty())
				throw new IllegalArgumentException("Nome nao pode ser em branco");
			
			dao.insertExame(exameVo);
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a inclusao do registro");
		}
		
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
	
	
	public String checkErros(ExameFilter filter){
		
		String errosFound = null;
		
		switch(filter.getOpcoesCombo()) {
			case NOME:
				if(isNumero(filter.getValorBusca())) {
					errosFound = "A busca por nomes nao deve possuir numeros";
				}
				break;
			case ID:
				if(!isNumero(filter.getValorBusca())) {
					errosFound = "A busca por ID so deve possuir numeros";
				}
				break;
		}
		return errosFound;
	}
	
	
	public List<ExameVo> filtrarExames(ExameFilter filter){
		List<ExameVo> exames = new ArrayList<>();
		
		switch (filter.getOpcoesCombo()) {
			case ID:
				Integer codigo = Integer.parseInt(filter.getValorBusca());
				exames.add(dao.findByCodigo(codigo));
			break;
			case NOME:
				exames.addAll(dao.findAllByNome(filter.getValorBusca()));
			break;
		}
		
		return exames;
	}
	
	public ExameVo buscarExamePor(String codigo) {
		try {
			Integer cod = Integer.parseInt(codigo);
			return dao.findByCodigo(cod);
		}catch (NumberFormatException e) {
			throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
		}
	}

	public void editarExame(ExameVo exameVo) {
		try {
			if(exameVo.getNome().isEmpty()) {
				throw new IllegalArgumentException("Nome nao pode ser em branco");
			}
			
			dao.updateExame(exameVo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("Nao foi possivel realizar a edição do registro");
		}
	}
	
	public boolean exameRealizado(String id) {
		return dao.exameRealizado(id);
	}
	
	public void excluirExame(String id) {
		dao.deleteExame(id);
	}

}
