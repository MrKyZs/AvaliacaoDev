package br.com.soc.sistema.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.soc.sistema.dao.gerarrelatorio.GerarRelatorioDAO;
import br.com.soc.sistema.vo.ExamesFuncionarioVo;

public class GerarRelatorioBusiness {

	private GerarRelatorioDAO gerarRelatorioDAO = new GerarRelatorioDAO();
	private static final String pathArquivo = "C:\\Users\\guima\\OneDrive\\Área de Trabalho\\relatorioExamesFuncionarioByData.xlsx";

	
	public Date changeToDateType(String date) {
		return Date.valueOf(date);
	}
	
	public String changeToStringType(Date date) {
		return String.valueOf(date);
	}
	
	public List<ExamesFuncionarioVo> getRegistrosRelatorioByDate(Date dataInicial, Date dataFinal){
		return gerarRelatorioDAO.selectRegistrosByDate(dataInicial, dataFinal);
	}
	
	public void createRelatorio(List<ExamesFuncionarioVo> listaGerarRelatorio) {		
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet planilha = wb.createSheet("relatorioExamesFuncionarioByData");
		
		CreationHelper createHelper = wb.getCreationHelper();
		XSSFCellStyle dateCellStyle = wb.createCellStyle();
	    dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
		
		XSSFRow linhaCabecalho = planilha.createRow(0);
		linhaCabecalho.createCell(0).setCellValue("ID");
		linhaCabecalho.createCell(1).setCellValue("Data Exame");
		linhaCabecalho.createCell(2).setCellValue("ID Funcionario");
		linhaCabecalho.createCell(3).setCellValue("Nome Funcionario");
		linhaCabecalho.createCell(4).setCellValue("ID Exame");
		linhaCabecalho.createCell(5).setCellValue("Nome Exame");
		
		int numeroLinha = 1;
		for(ExamesFuncionarioVo examesFuncionarios :  listaGerarRelatorio) {
			XSSFRow linhaConteudo = planilha.createRow(numeroLinha++);
			XSSFCell dataCelular = linhaConteudo.createCell(1);
			
			linhaConteudo.createCell(0).setCellValue(examesFuncionarios.getRowid());
			
			dataCelular.setCellValue(examesFuncionarios.getDataExame());
			dataCelular.setCellStyle(dateCellStyle);
			
			linhaConteudo.createCell(2).setCellValue(examesFuncionarios.getFuncionario().getRowid());
			linhaConteudo.createCell(3).setCellValue(examesFuncionarios.getFuncionario().getNome());
			linhaConteudo.createCell(4).setCellValue(examesFuncionarios.getExame().getRowid());
			linhaConteudo.createCell(5).setCellValue(examesFuncionarios.getExame().getNome());
		}
	    for (int i = 0; i <= 5; i++) {
	    	planilha.autoSizeColumn(i);
	    }		
		try {
			FileOutputStream out = new FileOutputStream(new File(pathArquivo));
			wb.write(out);
			out.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
