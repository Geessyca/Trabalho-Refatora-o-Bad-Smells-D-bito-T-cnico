package conectividade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	private static Connection connected() {
		final String servidor = "jdbc:mysql://localhost:3306/sistema_de_funcionarios?useTimezone=true&serverTimezone=UTC&useSSL=false";
		final String username = "gessyca";
		final String password = "G23C03l20m00";
		
		Connection connection = null;
		
		try{
			connection = DriverManager.getConnection(servidor, username, password);
		} catch(SQLException erro){
			System.out.println(erro.getMessage());
		}	
		
		return connection;
	}
	public static ResultSet executeQuery(String query) throws SQLException{
		Connection connection = connected();
		
		Statement statement = null;
		
		ResultSet result = null;
		
		try{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			result = statement.executeQuery(query);
			return result;
		} catch(SQLException erro){
			System.out.println(erro.getMessage());
		}	
		connection.close();
		return result;
	}
	
	public static int executeUpdate(String template) throws SQLException{
		Connection connection = connected();
		Statement statement = null;
		int result = 0;
		
		try{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			result = statement.executeUpdate(template);
			
		} catch(SQLException erro){
			System.out.println(erro.getMessage());
		}	
		connection.close();
		return result;
	}
	
	public static PreparedStatement prepareStatement(String template) throws SQLException{
		Connection connection = connected();
		
		PreparedStatement statement = null;
		
		try{
			statement = connection.prepareStatement(template);
			
		} catch(SQLException erro){
			System.out.println(erro.getMessage());
		}	
		connection.close();
		return statement;
	}
}

