package sistema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import entidade.Cargo;
import sistema.telas.CargosEditar;
import sistema.telas.CargosConsultar;
import sistema.telas.CargosInserir;
import entidade.Funcionario;
import sistema.telas.FuncionariosConsultar;
import sistema.telas.FuncionariosInserir;
import sistema.telas.FuncionariosEditar;
import sistema.telas.Inicio;
import sistema.telas.Login;

public class Navegador {
	public static JPanel screen;

	private static boolean menuBuilt;
	private static boolean menuAble;
	private static JMenuBar menuBar;
	private static JMenu menuFile, menuEmployees, menuOffice;
	private static JMenuItem menuInternExit, menuInternEmployeessConsult, menuInternEmployeessRegister, menuInternOfficeConsult;
	private static JMenuItem menuInternOfficeRegister;
	
	private static void infoPageDefault(String title) {
		Sistema.frame.setTitle("Funcionários OOP Company - "+title);
		Navegador.uptadeScreen();	
	}
	
	public static void login() {
		screen = new Login();
		infoPageDefault("Login");
	}
	
	public static void inicio() {
		screen = new Inicio();
		infoPageDefault("Inicio");
	}
	
	public static void officesRegister() {
		screen = new CargosInserir();
		infoPageDefault("Register Cargos");		
	}
	
	public static void officesConsult() {
		screen = new CargosConsultar();
		infoPageDefault("Consult Cargos");
	}
	
	public static void officesEdit(Cargo office) {
		screen = new CargosEditar(office);
		infoPageDefault("Editar Cargos");
	}
	
	 public static void employeesRegister(){
		 screen = new FuncionariosInserir();
	 	infoPageDefault("Register funcionarios");
	 }
	    
	 public static void employeesConsult(){
	     screen = new FuncionariosConsultar();
	 	infoPageDefault("Consult funcionarios");
	 }

	 public static void employeesEdit(Funcionario employee){
	     screen = new FuncionariosEditar(employee);         
	 	infoPageDefault("Editar funcionarios");
	    }
	 
	
	private static void uptadeScreen() {
		Sistema.frame.getContentPane().removeAll();
		screen.setVisible(true);
		Sistema.frame.add(screen);
		Sistema.frame.setVisible(true);
	}
	
	private static void buildMenu() {
		if(!menuBuilt) {
			menuBuilt = true;
			
			menuBar = new JMenuBar();
			
			menuFile = new JMenu("Arquivo");
			menuBar.add(menuFile);
			menuInternExit = new JMenuItem("Sair");
			menuFile.add(menuInternExit);
			
			menuEmployees = new JMenu ("Funcionários");
			menuBar.add(menuEmployees);
			menuInternEmployeessRegister = new JMenuItem("Cadastro");
			menuEmployees.add(menuInternEmployeessRegister);
			menuInternEmployeessConsult = new JMenuItem("Consulta");
			menuEmployees.add(menuInternEmployeessConsult);
			
			menuOffice = new JMenu("Cargos");
			menuBar.add(menuOffice);
			menuInternOfficeRegister = new JMenuItem("Cadastro");
			menuOffice.add(menuInternOfficeRegister);
			menuInternOfficeConsult = new JMenuItem("Consulta");
			menuOffice.add(menuInternOfficeConsult);
			
			createEventsMenu();
		}
	}
	
	public static void enableMenu() {
		if(!menuBuilt) buildMenu();
		if(!menuAble) {
			menuAble = true;
			Sistema.frame.setJMenuBar(menuBar);
		}
	}
	
	public static void disableMenu() {
		if(menuAble) {
			menuAble = false;
			Sistema.frame.setJMenuBar(null);
		}
	}
	
	private static void createEventsMenu() {
		menuInternExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		menuInternEmployeessRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				employeesRegister();
			}
		});
		menuInternEmployeessConsult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				employeesConsult();
			
			}
		});
		
		menuInternOfficeRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				officesRegister();
			}
		});
		
		menuInternOfficeConsult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				officesConsult();
			}
		});
		
		
	}
	
}
