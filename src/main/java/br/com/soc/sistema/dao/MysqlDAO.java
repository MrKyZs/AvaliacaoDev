package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDAO {

	private String path = "jdbc:mysql://localhost:3306/projetoage?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = System.getenv("db_password");
	
	public Connection criarConexao() throws SQLException {
		
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(path, user, password);	
			return conn;
		}
		catch(Exception e) {
			System.out.println("erro");
			e.printStackTrace();
		}

		return null;
	
	}
}
