package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import conectividade.Main;
import sistema.Validador;
import entidade.Cargo;

public class CargosInserir extends JPanel {
	
	JLabel labelTitle, labelOffice;
	JTextField fieldOffice;
	JButton buttonSubmit;
	Main connected = new Main();
	public CargosInserir() {
		createComponents();
		createEvents();
	}
	
	private  void createComponents() {
		setLayout(null);
		
		labelTitle = new JLabel ("Cadastro de Cargo", JLabel.CENTER);
		labelTitle.setFont(new Font(labelTitle.getFont().getName(), Font.PLAIN, 20));
		labelOffice = new JLabel ("Nome do cargo", JLabel.LEFT);
		fieldOffice = new JTextField();
		buttonSubmit = new JButton("Adicionar Cargo");
		
		labelTitle.setBounds(20, 20, 660, 40);
		labelOffice.setBounds(150, 120, 400, 20);
		fieldOffice.setBounds(150, 140, 400, 40);
		buttonSubmit.setBounds(250, 380, 200, 40);
		
		add(labelTitle);
		add(labelOffice);
		add(fieldOffice);
		add(buttonSubmit);
		
		setVisible(true);
	}
	
	private void createEvents() {
		buttonSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Cargo novoCargo = 	new Cargo();
				novoCargo.setName(fieldOffice.getText());
				insertOffice(novoCargo);
			}
		});
		
	}
	
private void insertOffice(Cargo novoCargo) {
	 	Validador validate = new Validador();
		validate.office(fieldOffice.getText());
			
		try {
			int result = connected.executeUpdate("INSERT INTO cargos (nome_cargo) VALUES ('"+novoCargo.getName()+"')");
			
			JOptionPane.showMessageDialog(null, "Cargo adicionado com sucesso!");			
		
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o carho.");	
			Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
			
		}
	}

}