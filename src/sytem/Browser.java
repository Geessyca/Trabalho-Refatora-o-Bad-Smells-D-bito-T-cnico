package sytem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import entity.Office;
import sytem.screen.OfficeConsult;
import sytem.screen.OfficeEdit;
import sytem.screen.OfficeInsert;
import sytem.screen.EmployeeConsult;
import sytem.screen.EmployeeEdit;
import sytem.screen.EmployeeInsert;
import sytem.screen.Start;
import sytem.screen.Login;
import entity.Employee;

public class Browser {
	public static JPanel screen;

	private static boolean menuBuilt;
	private static boolean menuAble;
	private static JMenuBar menuBar;
	private static JMenu menuFile, menuEmployees, menuOffice;
	private static JMenuItem menuInternExit, menuInternEmployeessConsult, menuInternEmployeessRegister, menuInternOfficeConsult;
	private static JMenuItem menuInternOfficeRegister;
	
	private static void infoPageDefault(String title) {
		Main.frame.setTitle("Funcionários OOP Company - "+title);
		Browser.uptadeScreen();	
	}
	
	public static void login() {
		screen = new Login();
		infoPageDefault("Login");
	}
	
	public static void inicio() {
		screen = new Start();
		infoPageDefault("Inicio");
	}
	
	public static void officesRegister() {
		screen = new OfficeInsert();
		infoPageDefault("Register Cargos");		
	}
	
	public static void officesConsult() {
		screen = new OfficeConsult();
		infoPageDefault("Consult Cargos");
	}
	
	public static void officesEdit(Office office) {
		screen = new OfficeEdit(office);
		infoPageDefault("Editar Cargos");
	}
	
	 public static void employeesRegister(){
		 screen = new EmployeeInsert();
	 	infoPageDefault("Register funcionarios");
	 }
	    
	 public static void employeesConsult(){
	     screen = new EmployeeConsult();
	 	infoPageDefault("Consult funcionarios");
	 }

	 public static void employeesEdit(Employee employee){
	     screen = new EmployeeEdit(employee);         
	 	infoPageDefault("Editar funcionarios");
	    }
	 
	
	private static void uptadeScreen() {
		Main.frame.getContentPane().removeAll();
		screen.setVisible(true);
		Main.frame.add(screen);
		Main.frame.setVisible(true);
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
			Main.frame.setJMenuBar(menuBar);
		}
	}
	
	public static void disableMenu() {
		if(menuAble) {
			menuAble = false;
			Main.frame.setJMenuBar(null);
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
