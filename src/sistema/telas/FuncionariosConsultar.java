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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import conectividade.Main;
import sistema.Validador;
import sistema.Navegador;
import entidade.Funcionario;

public class FuncionariosConsultar extends JPanel {
    
    Funcionario currentEmployee;
    JLabel labelTitle, labelEmployee;
    JTextField fieldEmployee;
    JButton buttonSearch, buttonEdit, buttonDelete;
    DefaultListModel<Funcionario> employeeListModel = new DefaultListModel();
    JList<Funcionario> employeeList;
     	Main connected = new Main();       
    public FuncionariosConsultar(){
        createComponents();
        createEvents();
        Navegador.enableMenu();
    }

    private void createComponents() {
        setLayout(null);
        
        labelTitle = new JLabel("Consulta de Funcionarios", JLabel.CENTER);
        labelTitle.setFont(new Font(labelTitle.getFont().getName(), Font.PLAIN, 20));      
        labelEmployee = new JLabel("Nome do employee", JLabel.LEFT);
        fieldEmployee = new JTextField();
        buttonSearch = new JButton("Pesquisar Funcionario");
        buttonEdit = new JButton("Editar Funcionario");
        buttonEdit.setEnabled(false);
        buttonDelete = new JButton("Excluir Funcionario");
        buttonDelete.setEnabled(false);
        employeeListModel = new DefaultListModel();
        employeeList = new JList();
        employeeList.setModel(employeeListModel);
        employeeList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        
        labelTitle.setBounds(20, 20, 660, 40);
        labelEmployee.setBounds(150, 120, 400, 20);
        fieldEmployee.setBounds(150, 140, 400, 40);
        buttonSearch.setBounds(560, 140, 130, 40); 
        employeeList.setBounds(150, 200, 400, 240);
        buttonEdit.setBounds(560, 360, 130, 40); 
        buttonDelete.setBounds(560, 400, 130, 40);
        
        add(labelTitle);
        add(labelEmployee);
        add(fieldEmployee);
        add(employeeList);
        add(buttonSearch);
        add(buttonEdit);
        add(buttonDelete);
        
        setVisible(true);
    }

    private void createEvents() {
        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmployee(fieldEmployee.getText());
            }
        });
        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navegador.employeesEdit(currentEmployee);
            }
        });
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });
        employeeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                currentEmployee = employeeList.getSelectedValue();
                if(currentEmployee == null){
                    buttonEdit.setEnabled(false);
                    buttonDelete.setEnabled(false);
                }else{
                    buttonEdit.setEnabled(true);
                    buttonDelete.setEnabled(true);
                }
            }
        });
    }

    private void searchEmployee(String nome) {        
        
        try {
        	ResultSet results = connected.executeQuery("SELECT * FROM funcionarios WHERE nome like '%"+nome+"%' order by nome ASC");
            
            employeeListModel.clear();

            while (results.next()) {                
                Funcionario employee = new Funcionario();
                employee.setId(results.getInt("id"));
                employee.setFirstName(results.getString("nome"));
                employee.setLastName(results.getString("sobrenome"));
                employee.setBirthDate(results.getString("data_nascimento"));
                employee.setEmail(results.getString("email"));
                if(results.getString("cargo") != null) employee.setOffice(Integer.parseInt(results.getString("cargo")));
                employee.setSalary(Double.parseDouble(results.getString("salario")));
                
                employeeListModel.addElement(employee);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar funcion�rios.");
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteEmployee() {
        String pergunta = "Deseja realmente excluir o Funcionario "+currentEmployee.getFirstName()+"?";
        int confirmacao = JOptionPane.showConfirmDialog(null, pergunta, "Excluir", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){
            try {
            	int result = connected.executeUpdate("DELETE FROM funcionarios WHERE id="+currentEmployee.getId()+"");            

                JOptionPane.showMessageDialog(null, "Funcionario deletado com sucesso!");
                Navegador.inicio();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir Funcionario.");
                Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}