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
	
public class OfficeEdit extends JPanel {
	
	Office currentOffice;
	JLabel labelTitle, labelOffice;
	JTextField fieldOffice;
	JButton buttonSubmit;
	DatabaseConnection connected = new DatabaseConnection();
	public OfficeEdit(Office office) {
		currentOffice = office;
		createComponents();
		createEvents();
	}
	
	private void createComponents() {
		setLayout(null);
		
		labelTitle = new JLabel("Editar de Cargo", JLabel.CENTER);
		labelTitle.setFont(new Font(labelTitle.getFont().getName(), Font.PLAIN, 20));
		labelOffice = new JLabel("Nome do Cargo", JLabel.LEFT);
		fieldOffice = new JTextField(currentOffice.getName());
		buttonSubmit = new JButton("Salvar");
		
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
				currentOffice.setName(fieldOffice.getText());
				updateOffice();
			}
		});
	}
	
	private void updateOffice( ) {
	 	Validator validate = new Validator();
		validate.office(fieldOffice.getText());
		
		
		try {
			int result =  connected.executeUpdate("UPDATE cargos set nome_cargo='"+fieldOffice.getText()+"' WHERE id="+currentOffice.getId()+"");
			
			JOptionPane.showMessageDialog(null, "Cargo atualizado com sucesso!");
		
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar o Cargo.");
			Logger.getLogger(OfficeInsert.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}

}




















