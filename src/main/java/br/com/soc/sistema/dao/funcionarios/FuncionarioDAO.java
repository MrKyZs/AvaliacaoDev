package br.com.soc.sistema.dao.funcionarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.MysqlDAO;
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionarioDAO {

	MysqlDAO connectionSQL = new MysqlDAO();
	
	public List<FuncionarioVo> selectAllFuncionarios() {
			StringBuilder query = new StringBuilder("SELECT rowid id, nm_funcionario nome FROM funcionario");
			
			List<FuncionarioVo> listFuncionarios = new ArrayList<>();
			
			try(Connection con = connectionSQL.criarConexao()){
				try(PreparedStatement stm = con.prepareStatement(query.toString())){
					try(ResultSet rs = stm.executeQuery()){
						while(rs.next()) {
							FuncionarioVo funcionario = new FuncionarioVo(
									rs.getString("id"),
									rs.getString("nome")
									);
							listFuncionarios.add(funcionario);
						}
						return listFuncionarios;
					}
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}	
			return listFuncionarios;			
	}
	
	public FuncionarioVo selectById(String id) {
		
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_funcionario nome FROM funcionario WHERE rowid = ?");
		FuncionarioVo funcionarioVo = null;
		
		try(Connection con = connectionSQL.criarConexao()){
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
				stm.setString(1, id);
				try(ResultSet rs = stm.executeQuery()){
					while(rs.next()) {
						funcionarioVo = new FuncionarioVo(
								rs.getString("id"),
								rs.getString("nome")
								);
					}
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return funcionarioVo;		
	}

	public List<FuncionarioVo> selectByNome(String nome) {
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_funcionario nome FROM funcionario WHERE lower(nm_funcionario) LIKE CONCAT('%', LOWER(?), '%'); ");
		List<FuncionarioVo> listFuncionarios = new ArrayList<>();

		try(Connection con = connectionSQL.criarConexao()){
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
				stm.setString(1, nome);
				try(ResultSet rs = stm.executeQuery()){
					while(rs.next()) {
						FuncionarioVo funcionario = new FuncionarioVo(
								rs.getString("id"),
								rs.getString("nome")
								);
						listFuncionarios.add(funcionario);
					}
					return listFuncionarios;
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void insertFuncionario(FuncionarioVo funcionarioVo){
		StringBuilder query = new StringBuilder("INSERT INTO funcionario (nm_funcionario) values (?)");
		try(Connection con = connectionSQL.criarConexao()){
			con.setAutoCommit(false);
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
				stm.setString(1, funcionarioVo.getNome());
				stm.executeUpdate();
				con.commit();
			}
			catch (SQLException e) {
				con.rollback();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateFuncionario(FuncionarioVo funcionarioVo) {
		
		StringBuilder query = new StringBuilder("UPDATE funcionario SET nm_funcionario = ? WHERE rowid = ?");
		
		try(Connection con = connectionSQL.criarConexao()){
			con.setAutoCommit(false);
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
				stm.setString(1, funcionarioVo.getNome());
				stm.setString(2, funcionarioVo.getRowid());
				stm.executeUpdate();
				con.commit();
			}
			catch(SQLException e) {
				con.rollback();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void deleteFuncionario(String id) {
		
		StringBuilder query = new StringBuilder("DELETE FROM funcionario WHERE rowid = ?");
		
		try(Connection con = connectionSQL.criarConexao()){
			con.setAutoCommit(false);
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
				stm.setString(1, id);
				stm.executeUpdate();
				con.commit();
			}
			catch(SQLException e) {
				con.rollback();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
}
