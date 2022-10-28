package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import sistema.Navegador;

public class Login extends JPanel {
	
	JLabel labelUser;
	JTextField fieldUser;
	JLabel labelPassword;
	JPasswordField fieldPassword;
	JButton buttonLogin;
	
	public Login() {
		createComponents();
		createEvents();
	}
	
	private void createComponents() {
		setLayout(null);
		
		JLabel labelTitle = new JLabel("Seja Bem vindo ao Sistema OOP Company!", JLabel.CENTER);
		labelTitle.setFont(new Font(labelTitle.getFont() .getName(), Font.PLAIN, 18));
		
		labelUser = new JLabel("Usuario", JLabel.LEFT);
		fieldUser = new JTextField();
		labelPassword = new JLabel("Senha", JLabel.LEFT);
		fieldPassword = new JPasswordField();
		buttonLogin = new JButton("Entrar");
		
		labelTitle.setBounds(20, 100, 600, 40);
		labelUser.setBounds(250, 220, 200, 20);
		fieldUser.setBounds(250, 240, 200, 40);
		labelPassword.setBounds(250, 280, 200, 20);
		fieldPassword.setBounds(250, 300, 200, 40);
		buttonLogin.setBounds(250, 350, 200, 40);
		
		add(labelTitle);
		add(labelUser);
		add(fieldUser);
		add(labelPassword);
		add(fieldPassword);
		add(buttonLogin);
		
		setVisible(true);
		
	}
	
	private void createEvents() {
		buttonLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fieldUser.getText().equals("admin") && new String(fieldPassword.getPassword()).equals("admin")){
                    Navegador.inicio();
                } else {
                    JOptionPane.showMessageDialog(null, "Acesso n�o permitido");
                }
			}
		});
	}
	
	

}
