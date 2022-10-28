package sytem.screen;

import javax.swing.JLabel;
import javax.swing.JPanel;

import sytem.Browser;

public class Start extends JPanel{
	JLabel labelTitle;

	public Start() {
		createComponents();
		Browser.enableMenu();
	}
	
	private void createComponents() {
		setLayout(null);
		
		labelTitle = new JLabel("Escolha uma opção no menu superior", JLabel.CENTER);
		labelTitle.setBounds(20, 100, 660, 40);
		
		add(labelTitle);
		
		setVisible(true);
	}
	

}
