package sytem.screen;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import database.DatabaseConnection;
import entity.Office;
import sytem.Browser;
import sytem.Validator;
import entity.Employee;

public class EmployeeEdit extends JPanel {
	 Employee currentEmployee;
	 JLabel labelTitle, labelName, labelLastName, labelBirthDate, labelEmail, labelOffice, labelSalary;
	 JTextField fieldFirstName, fieldLastName, fieldEmail;
	 JFormattedTextField fieldBirthDate, fieldSalary;
	 JComboBox<Office> boxWithOffice;
	 JButton buttonSubmit;  
		DatabaseConnection connected = new DatabaseConnection();
	 public EmployeeEdit(Employee employee){
		 currentEmployee = employee;
	     createComponents();
	     createEvents();
	     Browser.enableMenu();
	  }

	  private void createComponents() {
	     setLayout(null);
	     String textoLabel = "Editar Funcionario "+currentEmployee.getFirstName()+" "+currentEmployee.getLastName();
	     labelTitle = new JLabel(textoLabel, JLabel.CENTER);
	     labelTitle.setFont(new Font(labelTitle.getFont().getName(), Font.PLAIN, 20));      
	     labelName = new JLabel("Nome:", JLabel.LEFT);
	     fieldFirstName = new JTextField(currentEmployee.getFirstName());     
	     labelLastName = new JLabel("Sobrenome:", JLabel.LEFT); 
	     fieldLastName = new JTextField(currentEmployee.getLastName());     
	     labelBirthDate = new JLabel("Data de Nascimento:", JLabel.LEFT);
	     fieldBirthDate = new JFormattedTextField(currentEmployee.getBirthDate());
	     try {
	            MaskFormatter dateMask= new MaskFormatter("##/##/####");
	            dateMask.install(fieldBirthDate);
	        } catch (ParseException ex) {
	            Logger.getLogger(EmployeeInsert.class.getName()).log(Level.SEVERE, null, ex);
	        }
	     labelEmail = new JLabel("E-mail:", JLabel.LEFT);
	     fieldEmail = new JTextField(currentEmployee.getEmail());     
	     labelOffice = new JLabel("Cargo:", JLabel.LEFT);
	     boxWithOffice = new JComboBox();     
	     labelSalary = new JLabel("Sal�rio:", JLabel.LEFT);
	     DecimalFormat formatter = new DecimalFormat("###0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
	     fieldSalary = new JFormattedTextField(formatter);
	     fieldSalary.setValue(currentEmployee.getSalary());
	     buttonSubmit = new JButton("Salvar");
	        
	     
	    labelTitle.setBounds(20, 20, 660, 40);
	    labelName.setBounds(150, 80, 400, 20);
	    fieldFirstName.setBounds(150, 100, 400, 40);
	    labelLastName.setBounds(150, 140, 400, 20);
	    fieldLastName.setBounds(150, 160, 400, 40);
	    labelBirthDate.setBounds(150, 200, 400, 20);
	    fieldBirthDate.setBounds(150, 220, 400, 40);
	    labelEmail.setBounds(150, 260, 400, 20);
	    fieldEmail.setBounds(150, 280, 400, 40);
	    labelOffice.setBounds(150, 320, 400, 20);
	    boxWithOffice.setBounds(150, 340, 400, 40);
	    labelSalary.setBounds(150, 380, 400, 20);
	    fieldSalary.setBounds(150, 400, 400, 40);
	    buttonSubmit.setBounds(560, 400, 130, 40); 
	        
	    add(labelTitle);
	    add(labelName);
	    add(fieldFirstName);
	    add(labelLastName);
	    add(fieldLastName);
	    add(labelBirthDate);
	    add(fieldBirthDate);
	    add(labelEmail);
	    add(fieldEmail);
	    add(labelOffice);
	    add(boxWithOffice);
	    add(labelSalary);
	    add(fieldSalary);
	    add(buttonSubmit);
	        
	    loadOffice(currentEmployee.getOffice());
	       
	    setVisible(true);
	    
	  }

	  private void createEvents() {
	    buttonSubmit.addActionListener(new ActionListener() {
	    @Override
	    	public void actionPerformed(ActionEvent e) {
	    		currentEmployee.setFirstName(fieldFirstName.getText());
	            currentEmployee.setLastName(fieldLastName.getText());
	            currentEmployee.setBirthDate(fieldBirthDate.getText());
	            currentEmployee.setEmail(fieldEmail.getText());
	            Office officeSelecionado = (Office) boxWithOffice.getSelectedItem();
	            if(officeSelecionado != null) currentEmployee.setOffice(officeSelecionado.getId());
	            currentEmployee.setSalary(Double.valueOf(fieldSalary.getText().replace(",", ".")));
	                
	            updateEmployee();
	                        
	         }
	     });
	   }

	   private void loadOffice(int currentOffice) {        
	        
	       try {
		       ResultSet results = connected.executeQuery("SELECT * from cargos order by nome_cargo asc");
	           
	           boxWithOffice.removeAll();
	           
	           while (results.next()) {
	        	   Office office = new Office();
	               office.setId(results.getInt("id"));
	               office.setName(results.getString("nome_cargo"));
	               boxWithOffice.addItem(office);
	                
	               if(currentOffice == office.getId()) boxWithOffice.setSelectedItem(office);
	            }
	            
	           boxWithOffice.updateUI();
	            
	            
	        } catch (SQLException ex) {
	        	JOptionPane.showMessageDialog(null, "Ocorreu um erro ao carregar os cargos.");
	            Logger.getLogger(EmployeeInsert.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

	    private void updateEmployee() {
	        
	        Validator validate = new Validator();
		validate.fullName(fieldFirstName.getText(), fieldLastName.getText());
		validate.salary(fieldSalary.getText());
		validate.email(fieldEmail.getText());
	        
	        try {
	            String template = "UPDATE funcionarios set nome=?, sobrenome=?, data_nascimento=?, email=?, cargo=?, salario=?";
	            template = template+" WHERE id="+currentEmployee.getId();
	            PreparedStatement preparedStatement = connected.prepareStatement(template);
	            preparedStatement.setString(1, fieldFirstName.getText());
	            preparedStatement.setString(2, fieldLastName.getText());
	            preparedStatement.setString(3, fieldBirthDate.getText());
	            preparedStatement.setString(4, fieldEmail.getText());
	            Office officeSelecionado = (Office) boxWithOffice.getSelectedItem();
	            if(officeSelecionado != null){
	                preparedStatement.setInt(5, officeSelecionado.getId());
	            }else{
	                preparedStatement.setNull(5, java.sql.Types.INTEGER);
	            }
	            preparedStatement.setString(6, fieldSalary.getText().replace(",", "."));
	            preparedStatement.executeUpdate();
	            
	            JOptionPane.showMessageDialog(null, "Funcionario atualizado com sucesso!");
	            Browser.inicio();
	           	            
	        } catch (SQLException ex) {
	            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar o Funcionario.");
	            Logger.getLogger(EmployeeInsert.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

}
