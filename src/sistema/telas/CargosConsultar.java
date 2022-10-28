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
import sistema.Navegador;
import conectividade.Main;
import sistema.Validador;
import entidade.Cargo;

public class CargosConsultar extends JPanel {
    
    Cargo currentOffice;
    JLabel labelTitle, labelOffice;
    JTextField fieldOffice;
    JButton buttonSearch, buttonEdit, buttonDelete;
    DefaultListModel<Cargo> officeListModel = new DefaultListModel();
    JList<Cargo> officeList;
	Main connected = new Main();
    public CargosConsultar(){
        createComponents();
        createEvents();
        Navegador.enableMenu();
    }

    private void createComponents() {
        setLayout(null);
        
        labelTitle = new JLabel("Consulta de Cargos", JLabel.CENTER);
        labelTitle.setFont(new Font(labelTitle.getFont().getName(), Font.PLAIN, 20));      
        labelOffice = new JLabel("Nome do office", JLabel.LEFT);
        fieldOffice = new JTextField();
        buttonSearch = new JButton("Pesquisar Cargo");
        buttonEdit = new JButton("Editar Cargo");
        buttonEdit.setEnabled(false);
        buttonDelete = new JButton("Excluir Cargo");
        buttonDelete.setEnabled(false);
        officeListModel = new DefaultListModel();
        officeList = new JList();
        officeList.setModel(officeListModel);
        officeList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        
        labelTitle.setBounds(20, 20, 660, 40);
        labelOffice.setBounds(100, 120, 400, 20);
        fieldOffice.setBounds(100, 140, 400, 40);
        buttonSearch.setBounds(500, 140, 130, 40); 
        officeList.setBounds(100, 200, 400, 240);
        buttonEdit.setBounds(500, 360, 130, 40); 
        buttonDelete.setBounds(500, 400, 130, 40);
        
        add(labelTitle);
        add(labelOffice);
        add(fieldOffice);
        add(officeList);
        add(buttonSearch);
        add(buttonEdit);
        add(buttonDelete);
        
        setVisible(true);
    }

    private void createEvents() {
        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchOffice(fieldOffice.getText());
            }
        });
        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Navegador.officesEdit(currentOffice);
            }
        });
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOffice();
            }
        });
        officeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                currentOffice = officeList.getSelectedValue();
                if(currentOffice == null){
                    buttonEdit.setEnabled(false);
                    buttonDelete.setEnabled(false);
                }else{
                    buttonEdit.setEnabled(true);
                    buttonDelete.setEnabled(true);
                }
            }
        });
    }

    private void searchOffice(String nome_cargo) {
        
        try {
        	ResultSet results = connected.executeQuery("SELECT * FROM offices WHERE nome_cargo like '%"+nome_cargo+"%'");
            
            officeListModel.clear();

            while (results.next()) {                
                Cargo office = new Cargo();
                office.setId(results.getInt("id"));
                office.setName(results.getString("nome_cargo"));
                
                officeListModel.addElement(office);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar os Cargos.");
            Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteOffice() {
        
        int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o Cargo "+currentOffice.getName()+"?", "Excluir", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){

            
            try {
            	int result = connected.executeUpdate("DELETE FROM cargos WHERE id="+currentOffice.getId()+"");
                officeList.clearSelection();
                JOptionPane.showMessageDialog(null, "Cargo deletado com sucesso!");  
                
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir o Cargo.");
                Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
            }
        }
        
    }
}