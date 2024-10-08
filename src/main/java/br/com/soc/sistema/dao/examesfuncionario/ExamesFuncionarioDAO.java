package br.com.soc.sistema.dao.examesfuncionario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.MysqlDAO;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.vo.ExameVo;
import br.com.soc.sistema.vo.ExamesFuncionarioVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class ExamesFuncionarioDAO {

	MysqlDAO connectionSQL = new MysqlDAO();

	public List<ExamesFuncionarioVo> getAllExameFuncionarios(){
		StringBuilder query = new StringBuilder("SELECT ef.rowid, ef.dataExame, ef.cd_funcionario, f.nm_funcionario, ef.cd_exame, e.nm_exame ");
		query.append("FROM exame_funcionarios ef ");
		query.append("JOIN funcionario f ON ef.cd_funcionario = f.rowid ");
		query.append("JOIN exame e ON ef.cd_exame = e.rowid ");
		query.append("ORDER BY ef.rowid");
		
		List<ExamesFuncionarioVo> listExamesFuncionarios = new ArrayList<>();
		
		try(Connection con = connectionSQL.criarConexao()){
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
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
						
						ExamesFuncionarioVo examesFuncionario = new ExamesFuncionarioVo(
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
		return listExamesFuncionarios;
	}
	
	public boolean possuiCopia(ExamesFuncionarioVo examesFuncionarios) {
		StringBuilder querySelectExamesFuncionarios = new StringBuilder("SELECT COUNT(*) AS examesIguais FROM exame_funcionarios WHERE dataExame = ? AND cd_funcionario = ? AND cd_exame = ?");
		
		int examesCadastradosIguais = -1;

		try(Connection con = connectionSQL.criarConexao()){
			try(PreparedStatement stm = con.prepareStatement(querySelectExamesFuncionarios.toString())){
				stm.setDate(1, examesFuncionarios.getDataExame());
				stm.setString(2, examesFuncionarios.getFuncionario().getRowid());
				stm.setString(3, examesFuncionarios.getExame().getRowid());			
				try(ResultSet rs = stm.executeQuery()){
					if(rs.next()) {
						examesCadastradosIguais = rs.getInt("examesIguais");
					}
					if(examesCadastradosIguais == 0) {
						return true;
					}
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	public void inserirExameFuncionario(ExamesFuncionarioVo examesFuncionarios) {
		
		StringBuilder queryInsert = new StringBuilder("INSERT INTO exame_funcionarios (dataExame, cd_funcionario, cd_exame) VALUES (?, ?, ?)");
				
		try(Connection con = connectionSQL.criarConexao()){
			con.setAutoCommit(false);
				try(PreparedStatement stm = con.prepareStatement(queryInsert.toString())){
					stm.setDate(1, examesFuncionarios.getDataExame());
					stm.setString(2, examesFuncionarios.getFuncionario().getRowid());
					stm.setString(3, examesFuncionarios.getExame().getRowid());
					stm.executeUpdate();
					con.commit();
				}
				catch(SQLException e) {
					e.printStackTrace();
					con.rollback();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
	}

	public ExamesFuncionarioVo getExameFuncionarioById(String id) {
		StringBuilder query = new StringBuilder("SELECT ef.rowid, ef.dataExame, ef.cd_funcionario, f.nm_funcionario, ef.cd_exame, e.nm_exame ");
		query.append("FROM exame_funcionarios ef ");
		query.append("JOIN funcionario f ON ef.cd_funcionario = f.rowid ");
		query.append("JOIN exame e ON ef.cd_exame = e.rowid ");
		query.append("WHERE ef.rowid = ? ORDER BY ef.rowid");
		
		ExamesFuncionarioVo examesFuncionario = null;
		
		try(Connection con = connectionSQL.criarConexao()){
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
				stm.setString(1, id);
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
						
						examesFuncionario = new ExamesFuncionarioVo(
								rs.getString("rowid"),
								rs.getDate("dataExame"),
								funcionario,
								exame
								);
					}
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return examesFuncionario;
	}
	
	public void updateExameFuncionario(ExamesFuncionarioVo examesFuncionarios) {
		StringBuilder query = new StringBuilder("UPDATE exame_funcionarios SET dataExame = ?, cd_funcionario = ?, cd_exame = ? WHERE rowid = ?");
	
		try(Connection con = connectionSQL.criarConexao()){
			con.setAutoCommit(false);
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
				stm.setDate(1, examesFuncionarios.getDataExame());
				stm.setString(2, examesFuncionarios.getFuncionario().getRowid());
				stm.setString(3, examesFuncionarios.getExame().getRowid());
				stm.setString(4, examesFuncionarios.getRowid());
				stm.executeUpdate();
				con.commit();
			}
			catch(SQLException e) {
				e.printStackTrace();
				con.rollback();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	
	}
	
	public void deleteExameFuncionario(String id) {
		StringBuilder query = new StringBuilder("DELETE FROM exame_funcionarios where rowid = ?");
		
		try(Connection con = connectionSQL.criarConexao()){
			con.setAutoCommit(false);
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
				stm.setString(1, id);
				stm.executeUpdate();
				con.commit();
			}
			catch(SQLException e) {
				con.rollback();
				e.printStackTrace();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public List<ExamesFuncionarioVo> getExameFuncionarioByNome(String idFuncionario){
		StringBuilder query = new StringBuilder("SELECT ef.rowid, ef.dataExame, ef.cd_funcionario, f.nm_funcionario, ef.cd_exame, e.nm_exame ");
		query.append("FROM exame_funcionarios ef ");
		query.append("JOIN funcionario f ON ef.cd_funcionario = f.rowid ");
		query.append("JOIN exame e ON ef.cd_exame = e.rowid ");
		query.append("WHERE lower(f.nm_funcionario) LIKE CONCAT('%', LOWER(?), '%')");	
		
		List<ExamesFuncionarioVo> listExamesFuncionarios = new ArrayList<>();

		try(Connection con = connectionSQL.criarConexao()){
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
				stm.setString(1, idFuncionario);
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
						
						ExamesFuncionarioVo examesFuncionario = new ExamesFuncionarioVo(
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
		return listExamesFuncionarios;
	}

	public List<ExamesFuncionarioVo> getExameFuncionarioByExame(String idExame){
		StringBuilder query = new StringBuilder("SELECT ef.rowid, ef.dataExame, ef.cd_funcionario, f.nm_funcionario, ef.cd_exame, e.nm_exame ");
		query.append("FROM exame_funcionarios ef ");
		query.append("JOIN funcionario f ON ef.cd_funcionario = f.rowid ");
		query.append("JOIN exame e ON ef.cd_exame = e.rowid ");
		query.append("WHERE lower(e.nm_exame) LIKE CONCAT('%', LOWER(?), '%')");	
		
		List<ExamesFuncionarioVo> listExamesFuncionarios = new ArrayList<>();

		try(Connection con = connectionSQL.criarConexao()){
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
				stm.setString(1, idExame);
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
						
						ExamesFuncionarioVo examesFuncionario = new ExamesFuncionarioVo(
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
		return listExamesFuncionarios;
	}
}
