package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sistema.Navegador;
import conectividade.Main;
import sistema.Validador;
import entidade.Cargo;
	
public class CargosEditar extends JPanel {
	
	Cargo currentOffice;
	JLabel labelTitle, labelOffice;
	JTextField fieldOffice;
	JButton buttonSubmit;
	Main connected = new Main();
	public CargosEditar(Cargo office) {
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
	 	Validador validate = new Validador();
		validate.office(fieldOffice.getText());
		
		
		try {
			int result =  connected.executeUpdate("UPDATE cargos set nome_cargo='"+fieldOffice.getText()+"' WHERE id="+currentOffice.getId()+"");
			
			JOptionPane.showMessageDialog(null, "Cargo atualizado com sucesso!");
		
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar o Cargo.");
			Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}

}




















