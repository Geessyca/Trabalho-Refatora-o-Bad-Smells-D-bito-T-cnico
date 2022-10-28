package sytem.screen;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.DatabaseConnection;
import entity.Office;
import sytem.Validator;

public class OfficeInsert extends JPanel {
	
	JLabel labelTitle, labelOffice;
	JTextField fieldOffice;
	JButton buttonSubmit;
	DatabaseConnection connected = new DatabaseConnection();
	public OfficeInsert() {
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
				Office novoCargo = 	new Office();
				novoCargo.setName(fieldOffice.getText());
				insertOffice(novoCargo);
			}
		});
		
	}
	
private void insertOffice(Office novoCargo) {
	 	Validator validate = new Validator();
		validate.office(fieldOffice.getText());
			
		try {
			int result = connected.executeUpdate("INSERT INTO cargos (nome_cargo) VALUES ('"+novoCargo.getName()+"')");
			
			JOptionPane.showMessageDialog(null, "Cargo adicionado com sucesso!");			
		
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o carho.");	
			Logger.getLogger(OfficeInsert.class.getName()).log(Level.SEVERE, null, ex);
			
		}
	}

}