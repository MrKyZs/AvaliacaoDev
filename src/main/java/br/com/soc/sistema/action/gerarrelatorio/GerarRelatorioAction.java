package br.com.soc.sistema.action.gerarrelatorio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.business.GerarRelatorioBusiness;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.vo.ExamesFuncionarioVo;

public class GerarRelatorioAction extends Action{

	private String dataInicial;
	private Date dataInicialDate;
	private String dataFinal;
	private Date dataFinalDate;
	private GerarRelatorioBusiness business = new GerarRelatorioBusiness();
	List<ExamesFuncionarioVo> listaGerarRelatorio = new ArrayList<>();
	
	public String preencher() {
		return SUCCESS;
	}
	
	public String gerarExcel() {
				
		if(dataInicial == ""|| dataFinal == "") {
			addFieldError("dataInicial", "Ambas datas devem estar preenchidas");
			return SUCCESS;
		}
		
		dataInicialDate = business.changeToDateType(dataInicial);
		dataFinalDate = business.changeToDateType(dataFinal);
		
		listaGerarRelatorio = business.getRegistrosRelatorioByDate(dataInicialDate, dataFinalDate);
		business.createRelatorio(listaGerarRelatorio);
		
		return REDIRECT;
	}
	
	public String gerarHTML() {
		if(dataInicial == ""|| dataFinal == "") {
			addFieldError("dataInicial", "Ambas datas devem estar preenchidas");
			return SUCCESS;
		}
		
		dataInicialDate = business.changeToDateType(dataInicial);
		dataFinalDate = business.changeToDateType(dataFinal);
		System.out.println("CHEGUEI: " + dataInicialDate);
		listaGerarRelatorio = business.getRegistrosRelatorioByDate(dataInicialDate, dataFinalDate);
		System.out.println("CHEGUEI: " + listaGerarRelatorio);
		
		return "HTML";
		
	}
	
	public String getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataInicialDate() {
		return dataInicialDate;
	}
	public void setDataInicialDate(Date dataInicialDate) {
		this.dataInicialDate = dataInicialDate;
	}
	public String getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}
	public Date getDataFinalDate() {
		return dataFinalDate;
	}
	public void setDataFinalDate(Date dataFinalDate) {
		this.dataFinalDate = dataFinalDate;
	}
	public List<ExamesFuncionarioVo> getListaGerarRelatorio() {
		return listaGerarRelatorio;
	}
	public void setListaGerarRelatorio(List<ExamesFuncionarioVo> listaGerarRelatorio) {
		this.listaGerarRelatorio = listaGerarRelatorio;
	}

	
}
