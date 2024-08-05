package br.com.soc.sistema.dao.gerarrelatorio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.MysqlDAO;
import br.com.soc.sistema.vo.ExameVo;
import br.com.soc.sistema.vo.ExamesFuncionariosVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class GerarRelatorioDAO {

	MysqlDAO connectionSQL = new MysqlDAO();

	public List<ExamesFuncionariosVo> selectRegistrosByDate(Date dataInicial, Date dataFinal){
		
		StringBuilder query = new StringBuilder("SELECT ef.rowid, ef.dataExame, ef.cd_funcionario, f.nm_funcionario, ef.cd_exame, e.nm_exame ");
		query.append("FROM exame_funcionarios ef ");
		query.append("JOIN funcionario f ON ef.cd_funcionario = f.rowid ");
		query.append("JOIN exame e ON ef.cd_exame = e.rowid ");
		query.append("WHERE ef.dataExame BETWEEN ? AND ?");
		
		List<ExamesFuncionariosVo> listExamesFuncionarios = new ArrayList<>();
		
		try(Connection con = connectionSQL.criarConexao()){
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
				stm.setDate(1, dataInicial);
				stm.setDate(2, dataFinal);
				try(ResultSet rs = stm.executeQuery()){
					while(rs.next()) {
						FuncionarioVo funcionario = new FuncionarioVo(
								rs.getString("cd_funcionario"), 
								rs.getString("nm_funcionario")
								);
						
						ExameVo exame= new ExameVo(
								rs.getString("cd_exame"), 
								rs.getString("nm_exame")
								);
						
						ExamesFuncionariosVo examesFuncionario = new ExamesFuncionariosVo(
								rs.getString("rowid"),
								rs.getDate("dataExame"),
								funcionario,
								exame
								);
						listExamesFuncionarios.add(examesFuncionario);
					}
					return listExamesFuncionarios;
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
