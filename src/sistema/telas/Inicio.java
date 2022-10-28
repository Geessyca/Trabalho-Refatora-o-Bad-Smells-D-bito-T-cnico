package sistema.telas;

import javax.swing.JLabel;
import javax.swing.JPanel;
import sistema.Navegador;

public class Inicio extends JPanel{
	JLabel labelTitle;

	public Inicio() {
		createComponents();
		Navegador.enableMenu();
	}
	
	private void createComponents() {
		setLayout(null);
		
		labelTitle = new JLabel("Escolha uma opção no menu superior", JLabel.CENTER);
		labelTitle.setBounds(20, 100, 660, 40);
		
		add(labelTitle);
		
		setVisible(true);
	}
	

}
