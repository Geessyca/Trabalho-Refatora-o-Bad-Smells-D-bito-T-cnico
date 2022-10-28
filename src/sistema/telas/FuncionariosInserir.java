package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import conectividade.Main;
import sistema.Navegador;
import sistema.Validador;
import entidade.Cargo;
import entidade.Funcionario;

public class FuncionariosInserir extends JPanel {
	
	JLabel labelTitle, labelName, labelLastName, labelBirthDate, labelEmail, labelOffice, labelSalary;
	JTextField fieldFirstName, fieldLastName, fieldEmail;
	JFormattedTextField fieldBirthDate, fieldSalary;
	JComboBox<Cargo> boxWithOffice;
	JButton buttonSubmit;
	MaskFormatter mkSalario;
	Main connected = new Main();
	public FuncionariosInserir() {
		createComponents();
		createEvents();
		Navegador.enableMenu();
	}
	
	
	private void createComponents() {
		setLayout(null);
		
		labelTitle =  new JLabel("Cadastro de Funcionario", JLabel.CENTER);
		labelTitle.setFont(new Font(labelTitle.getFont().getName(), Font.PLAIN, 20));
		labelName =  new JLabel("Nome:", JLabel.LEFT);
		fieldFirstName = new JTextField();
		labelLastName = new  JLabel("Sobrenome:", JLabel.LEFT);
		fieldLastName = new JTextField();
		labelBirthDate = new JLabel("Data de Nascimento", JLabel.LEFT);
		fieldBirthDate = new JFormattedTextField();
		try {
			MaskFormatter dateMask= new MaskFormatter("##/##/####");
			dateMask.install(fieldBirthDate);
		}catch (ParseException ex) {
			Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
		}
		labelEmail = new JLabel("E-mail:", JLabel.LEFT);
		fieldEmail = new JTextField();
		labelOffice = new JLabel("Cargo:", JLabel.LEFT);
		boxWithOffice = new JComboBox();
		labelSalary = new JLabel("Sal�rio", JLabel.LEFT);
		DecimalFormat formatter = new DecimalFormat("###0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
		fieldSalary = new JFormattedTextField(formatter);
		fieldSalary.setValue(0.00);
		buttonSubmit = new JButton("Adicionar");
		
		labelTitle.setBounds(20, 20, 660, 40);
		labelName.setBounds(150, 80, 400, 20);
		fieldFirstName.setBounds(150, 100, 400, 40);
		labelLastName.setBounds(150, 140, 400, 20);
		fieldLastName.setBounds(150, 160, 400, 40);
		labelBirthDate.setBounds(150, 200, 400, 20);
		fieldBirthDate.setBounds(150, 220, 400, 40);
		labelEmail.setBounds(150,260, 400, 20);
		fieldEmail.setBounds(150,280, 400, 40);
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
		
		loadEmployee();
		
		setVisible(true);
	}
	
	private void createEvents() {
		buttonSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Funcionario novoFuncionario = new Funcionario();
				novoFuncionario.setFirstName(fieldFirstName.getText());
				novoFuncionario.setLastName(fieldLastName.getText());
				novoFuncionario.setBirthDate(fieldBirthDate.getText());
				novoFuncionario.setEmail(fieldEmail.getText());
				Cargo officeSelecionado = (Cargo) boxWithOffice.getSelectedItem();
				if(officeSelecionado != null) novoFuncionario.setOffice(officeSelecionado.getId());
				
				novoFuncionario.setSalary(Double.valueOf(fieldSalary.getText().replace(",", ".")));
				insertEmployee(novoFuncionario);
				}
		});
	}
	
	private void loadEmployee() {
		
		
		try {
			ResultSet results = connected.executeQuery("SELECT * from cargos order by nome_cargo asc");
			boxWithOffice.removeAll();
			
			while (results.next()) {
				Cargo office = new Cargo();
				office.setId(results.getInt("id"));
				office.setName(results.getString("nome_cargo"));
				boxWithOffice.addItem(office);
			}
			boxWithOffice.updateUI();
			
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao carregar os cargos");
			Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);	
		}
	}
	
	private void insertEmployee(Funcionario novoFuncionario) {
		Validador validate = new Validador();
		validate.fullName(fieldFirstName.getText(), fieldLastName.getText());
		validate.salary(fieldSalary.getText());
		validate.email(fieldEmail.getText());
        
        try{  

            PreparedStatement preparedStatement = connected.prepareStatement("INSERT INTO funcionarios (nome,sobrenome,data_nascimento,email,cargo,salario) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, novoFuncionario.getFirstName());
            preparedStatement.setString(2, novoFuncionario.getLastName());
            preparedStatement.setString(3, novoFuncionario.getBirthDate());
            preparedStatement.setString(4, novoFuncionario.getEmail());
            if(novoFuncionario.getOffice() > 0){
                preparedStatement.setInt(5, novoFuncionario.getOffice());
            }else{
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }
            preparedStatement.setString(6, Double.toString(novoFuncionario.getSalary()));
            preparedStatement.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Funcionario adicionado com sucesso!");
            Navegador.inicio();
            
        
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o Funcionario.");
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }

}
