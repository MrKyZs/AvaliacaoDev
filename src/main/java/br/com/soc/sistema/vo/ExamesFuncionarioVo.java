package br.com.soc.sistema.vo;

import java.sql.Date;

public class ExamesFuncionarioVo {

	
	private String rowid;
	private Date dataExame;
	private FuncionarioVo funcionario;
	private ExameVo exame;
	
	public ExamesFuncionarioVo() {
		
	}
	
	public ExamesFuncionarioVo(String rowid, Date dataExame, FuncionarioVo funcionario, ExameVo exame) {
		this.rowid = rowid;
		this.dataExame = dataExame;
		this.funcionario = funcionario;
		this.exame = exame;
	}
	
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	public Date getDataExame() {
		return dataExame;
	}
	public void setDataExame(Date dataExame) {
		this.dataExame = dataExame;
	}
	public FuncionarioVo getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(FuncionarioVo funcionario) {
		this.funcionario = funcionario;
	}
	public ExameVo getExame() {
		return exame;
	}
	public void setExame(ExameVo exame) {
		this.exame = exame;
	}
	
}
