package br.com.soc.sistema.dao.exames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.soc.sistema.dao.MysqlDAO;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.vo.ExameVo;

public class ExameDao {
	
	MysqlDAO connectionSQL = new MysqlDAO();
	
	public void insertExame(ExameVo exameVo){
		StringBuilder query = new StringBuilder("INSERT INTO exame (nm_exame) values (?)");
		try(
			Connection con = connectionSQL.criarConexao();
			PreparedStatement  ps = con.prepareStatement(query.toString())){
			
			int i=1;
			ps.setString(i++, exameVo.getNome());
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateExame(ExameVo examevo) throws SQLException {
		StringBuilder query = new StringBuilder("UPDATE exame SET nm_exame = ? WHERE rowid = ?");

		try(Connection con = connectionSQL.criarConexao()){
			try(PreparedStatement stm = con.prepareStatement(query.toString())){
				con.setAutoCommit(false);
				stm.setString(1, examevo.getNome());
				stm.setString(2, examevo.getRowid());
				stm.executeUpdate();
			}
			catch(SQLException e) {
				con.rollback();
				e.printStackTrace();
			}
			con.commit();
		}
	}
	
	public boolean exameRealizado(String id) {
		StringBuilder queryChecarExamesFuncionarios = new StringBuilder("SELECT COUNT(cd_exame) AS examesRealizados FROM exame_funcionarios WHERE cd_exame = ?");
		int examesRealizados = -1;

		try(Connection con = connectionSQL.criarConexao()){
			try(PreparedStatement stm = con.prepareStatement(queryChecarExamesFuncionarios.toString())){
				stm.setString(1, id);
				try(ResultSet rs = stm.executeQuery()){
					if(rs.next()) {
						examesRealizados = rs.getInt("examesRealizados");
					}
					if(examesRealizados == 0) {
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
	
	public void deleteExame(String id) {
		StringBuilder queryDeleteExame = new StringBuilder("DELETE FROM exame WHERE rowid = ?");
		
		try(Connection con = connectionSQL.criarConexao()){
			con.setAutoCommit(false);
			try(PreparedStatement stm = con.prepareStatement(queryDeleteExame.toString())){
				stm.setString(1, id);
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
	
	public List<ExameVo> findAllExames(){
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_exame nome FROM exame ORDER BY rowid");
		try(
			Connection con = connectionSQL.criarConexao();
			PreparedStatement  ps = con.prepareStatement(query.toString());
			ResultSet rs = ps.executeQuery()){
			
			ExameVo vo =  null;
			List<ExameVo> exames = new ArrayList<>();
			while (rs.next()) {
				vo = new ExameVo();
				vo.setRowid(rs.getString("id"));
				vo.setNome(rs.getString("nome"));	
				
				exames.add(vo);
			}
			return exames;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Collections.emptyList();
	}
	
	public List<ExameVo> findAllByNome(String nome){
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_exame nome FROM exame ")
								.append("WHERE lower(nm_exame) like lower(?)");
		
		try(Connection con = connectionSQL.criarConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())){
			int i = 1;
			
			ps.setString(i, "%"+nome+"%");
			
			try(ResultSet rs = ps.executeQuery()){
				ExameVo vo =  null;
				List<ExameVo> exames = new ArrayList<>();
				
				while (rs.next()) {
					vo = new ExameVo();
					vo.setRowid(rs.getString("id"));
					vo.setNome(rs.getString("nome"));	
					
					exames.add(vo);
				}
				return exames;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}		
		return Collections.emptyList();
	}
	
	public ExameVo findByCodigo(Integer codigo){
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_exame nome FROM exame ")
								.append("WHERE rowid = ?");
		
		try(Connection con = connectionSQL.criarConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())){
			int i = 1;
			
			ps.setInt(i, codigo);
			
			try(ResultSet rs = ps.executeQuery()){
				ExameVo vo =  null;
				
				while (rs.next()) {
					vo = new ExameVo();
					vo.setRowid(rs.getString("id"));
					vo.setNome(rs.getString("nome"));	
				}
				return vo;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}
}