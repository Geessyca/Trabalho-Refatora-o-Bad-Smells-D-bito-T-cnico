package sytem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Validator {
	public void fullName(String fieldFirstName,String fieldLastName) {
		if(fieldFirstName.length()<=2 || fieldLastName.length()<=2) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha o nome/sobrenome corretamente");
			return;	
		}
	}
	public void office(String fieldOffice) {
		if(fieldOffice.length()<=2) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha o cargo corretamente");
			return;	
		}
	}
	public void salary(String fieldSalary) {
		if(Double.parseDouble(fieldSalary.replace(",", "."))<=100) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha o salário corretamente");
			return;
		}
	}
	public void email(String fieldEmail) {
		Boolean emailValidado = false;
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	    Pattern p = Pattern.compile(ePattern);
	    Matcher m = p.matcher(fieldEmail);
	    emailValidado = m.matches();
	    
	    if(!emailValidado){
	        JOptionPane.showMessageDialog(null, "Por favor, preencha o email corretamente.");
	        return;
	    }
	}

}
