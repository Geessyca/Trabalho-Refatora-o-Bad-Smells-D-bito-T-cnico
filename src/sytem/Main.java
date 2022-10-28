package sytem;

import javax.swing.JFrame;

public class Main {

	public static JFrame frame;
	
	public static void main(String[] args) {
		createComponents();
	}
	
	private static void createComponents() {
		frame = new JFrame("Sistema");
		frame.setSize(700,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		Browser.login();
	}
	
}
