package conectividade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	static String d; 
	static String s;
	static String u;
	static String su;
	
	static Connection c;
	
	static Statement i;
	
	static ResultSet r;
	public static void main(String[] args){
		try{
			d = "com.mysql.jdbc.Driver"; 
			s = "jdbc:mysql://localhost:3306/sistema_de_funcionarios?useTimezone=true&serverTimezone=UTC&useSSL=false";
			u = "gessyca";
			su = "G23C03l20m00";
			c = DriverManager.getConnection(s, u, su);
			
			i = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			r = i.executeQuery("SELECT * FROM funcionarios");
			System.out.println("deu certo");
		} catch(SQLException erro){
			System.out.println(erro.getMessage());
		}		
	}
}

