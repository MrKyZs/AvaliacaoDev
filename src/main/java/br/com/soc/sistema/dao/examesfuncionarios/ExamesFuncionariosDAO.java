package br.com.soc.sistema.dao.examesfuncionarios;

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
import br.com.soc.sistema.vo.ExamesFuncionariosVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class ExamesFuncionariosDAO {

	MysqlDAO connectionSQL = new MysqlDAO();

	public List<ExamesFuncionariosVo> getAllExameFuncionarios(){
		StringBuilder query = new StringBuilder("SELECT ef.rowid, ef.dataExame, ef.cd_funcionario, f.nm_funcionario, ef.cd_exame, e.nm_exame ");
		query.append("FROM exame_funcionarios ef ");
		query.append("JOIN funcionario f ON ef.cd_funcionario = f.rowid ");
		query.append("JOIN exame e ON ef.cd_exame = e.rowid");
		
		List<ExamesFuncionariosVo> listExamesFuncionarios = new ArrayList<>();
		
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
		return listExamesFuncionarios;
	}

	public void inserirExameFuncionario(ExamesFuncionariosVo examesFuncionarios) {
		
		StringBuilder querySelectExamesFuncionarios = new StringBuilder("SELECT COUNT(*) AS examesIguais FROM exame_funcionarios WHERE dataExame = ? AND cd_funcionario = ? AND cd_exame = ?");
		StringBuilder queryInsert = new StringBuilder("INSERT INTO exame_funcionarios (dataExame, cd_funcionario, cd_exame) VALUES (?, ?, ?)");
		
		int examesIguais = -1;
		
		try(Connection con = connectionSQL.criarConexao()){
			con.setAutoCommit(false);
			try(PreparedStatement statement = con.prepareStatement(querySelectExamesFuncionarios.toString())){
				statement.setDate(1, examesFuncionarios.getDataExame());
				statement.setString(2, examesFuncionarios.getFuncionario().getRowid());
				statement.setString(3, examesFuncionarios.getExame().getRowid());
				try(ResultSet rs = statement.executeQuery()){
					while(rs.next()) {
						examesIguais = rs.getInt("examesIguais");
					}
					if(examesIguais != 0) {
						throw new BusinessException("Esse exame já foi cadastrado para este funcionario");
					}
					else {
						try(PreparedStatement stm = con.prepareStatement(queryInsert.toString())){
							stm.setDate(1, examesFuncionarios.getDataExame());
							stm.setString(2, examesFuncionarios.getFuncionario().getRowid());
							stm.setString(3, examesFuncionarios.getExame().getRowid());
							stm.executeUpdate();
						}
						catch(SQLException e) {
							con.rollback();
							e.printStackTrace();
						}
					}
				}
			}
			con.commit();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public ExamesFuncionariosVo getExameFuncionarioById(String id) {
		StringBuilder query = new StringBuilder("SELECT ef.rowid, ef.dataExame, ef.cd_funcionario, f.nm_funcionario, ef.cd_exame, e.nm_exame ");
		query.append("FROM exame_funcionarios ef ");
		query.append("JOIN funcionario f ON ef.cd_funcionario = f.rowid ");
		query.append("JOIN exame e ON ef.cd_exame = e.rowid ");
		query.append("WHERE ef.rowid = ?");
		
		ExamesFuncionariosVo examesFuncionario = null;
		
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
						
						examesFuncionario = new ExamesFuncionariosVo(
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
	
	public void updateExameFuncionario(ExamesFuncionariosVo examesFuncionarios) {
		StringBuilder query = new StringBuilder("UPDATE exame_funcionarios SET dataExame = ?, cd_funcionario = ?, cd_exame = ? WHERE rowid = ?");
	
		try(Connection con = connectionSQL.criarConexao()){
			System.out.println("CHEGUEI CONEXAO");
			con.setAutoCommit(false);
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
				System.out.println("CHEGUEI CRIAR QUERY");
				stm.setDate(1, examesFuncionarios.getDataExame());
				stm.setString(2, examesFuncionarios.getFuncionario().getRowid());
				stm.setString(3, examesFuncionarios.getExame().getRowid());
				stm.setString(4, examesFuncionarios.getRowid());
				stm.executeUpdate();
				System.out.println("COMMITANDO");
				con.commit();
			}
			catch(SQLException e) {
				System.out.println("CHEGUEI ERRO");
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

	public List<ExamesFuncionariosVo> getExameFuncionarioByNome(String idFuncionario){
		StringBuilder query = new StringBuilder("SELECT ef.rowid, ef.dataExame, ef.cd_funcionario, f.nm_funcionario, ef.cd_exame, e.nm_exame ");
		query.append("FROM exame_funcionarios ef ");
		query.append("JOIN funcionario f ON ef.cd_funcionario = f.rowid ");
		query.append("JOIN exame e ON ef.cd_exame = e.rowid ");
		query.append("WHERE lower(f.nm_funcionario) LIKE CONCAT('%', LOWER(?), '%')");	
		
		List<ExamesFuncionariosVo> listExamesFuncionarios = new ArrayList<>();

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
		return listExamesFuncionarios;
	}

	public List<ExamesFuncionariosVo> getExameFuncionarioByExame(String idExame){
		StringBuilder query = new StringBuilder("SELECT ef.rowid, ef.dataExame, ef.cd_funcionario, f.nm_funcionario, ef.cd_exame, e.nm_exame ");
		query.append("FROM exame_funcionarios ef ");
		query.append("JOIN funcionario f ON ef.cd_funcionario = f.rowid ");
		query.append("JOIN exame e ON ef.cd_exame = e.rowid ");
		query.append("WHERE lower(e.nm_exame) LIKE CONCAT('%', LOWER(?), '%')");	
		
		List<ExamesFuncionariosVo> listExamesFuncionarios = new ArrayList<>();

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
		return listExamesFuncionarios;
	}
}
