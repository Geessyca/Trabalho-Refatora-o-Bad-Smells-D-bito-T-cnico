<h1 align="center">
  🎓<br>Trabalho Refatoração | Bad Smells | Débito Técnico
</h1>
<div class="tags" id="conn"><strong>/conectividade/Main.java</strong></div>

Essa classe tem como função testar a conectividade com o banco de dados local. Ela possuía variáveis globais não inicializadas em sua declaração e mutáveis e toda a nomenclatura da classe era simplória, além da sua função não ser necessária/utilizada no sistema.

-> O primeiro passo foi a remoção das variáveis globais, a iniciação delas na criação e torna imutáveis as variáveis designadas a iniciação do banco de dados - driver, servidor, username, password.

```
public class Main {
	static String d; 
	static String s;
	static String u;
	static String su;
	
	static Connection c;
	
	static Statement i;
	
	static ResultSet r;
	public static void main(String[] args){
		try{
			d = "com.mysql.jdbc.Driver"; 
			s = "jdbc:mysql://localhost:3306/sistema_de_funcionarios?useTimezone=true&serverTimezone=UTC&useSSL=false";
			u = "gessyca";
			su = "G23C03l20m00";
			c = DriverManager.getConnection(s, u, su);
			
			…

```

```
public class Main {
	public static void main(String[] args){
		final String driver = "com.mysql.jdbc.Driver"; 
		final String servidor = "jdbc:mysql://localhost:3306/sistema_de_funcionarios?useTimezone=true&serverTimezone=UTC&useSSL=false";
		final String username = "gessyca";
		final String password = "G23C03l20m00";
		
		Connection connection;
		
		Statement statement;
		
		ResultSet result;
…

```

Posteriormente para gerar uma funcionalidade para essa classe foi adicioado um retorno , um novo parametro e a renomeação dessa função para que agora seja retornado o resultado da execução da query passada

```
…
public static void main(String[] args){
		…
			r = i.executeQuery("SELECT * FROM funcionarios");
			System.out.println("deu certo");
		} catch(SQLException erro){
			System.out.println(erro.getMessage());
		}	
…

```

```
…
public static ResultSet executeQuery(String query){
		…
		result = statement.executeQuery(query);
			return result;
		} catch(SQLException erro){
			System.out.println(erro.getMessage());
		}	
		return result;
	}
	
…

```

Foi observado nas classes <a href="#CargosConsultar">telas/CargosConsultar.java</a>, <a href="#CargosConsultar">telas/CargosConsultar.java</a>, <a href="#CargosEditar">telas/CargosEditar.java</a>, <a href="#CargosInserir">telas/CargosInserir.java</a>, <a href="#FuncionariosConsultar">telas/FuncionariosConsultar.java</a>,<a href="#FuncionariosEditar">telas/FuncionariosEditar.java</a>,<a href="#FuncionariosInserir">telas/FuncionariosInserir.java</a>, havia duplicidade na conexão com o banco de dados, por isso o método anterior foi necessário, assim como os seguintes:

```
	public static int executeUpdate(String template) throws SQLException{
		Connection connection = connected();
		Statement statement = null;
		int result = 0;
		
		try{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			result = statement.executeUpdate(template);
			
		} catch(SQLException erro){
			System.out.println(erro.getMessage());
		}	
		connection.close();
		return result;
	}
	
	public static PreparedStatement prepareStatement(String template) throws SQLException{
		Connection connection = connected();
		
		PreparedStatement statement = null;
		
		try{
			statement = connection.prepareStatement(template);
			
		} catch(SQLException erro){
			System.out.println(erro.getMessage());
		}	
		connection.close();
		return statement;
	}
```

<div class="tags" id="Cargo"><strong>/entidade/Cargo.java</strong></div>

A refatoração dessa classe foi bem simples, ela contou com três pontos somente, a renomeação da nomenclatura, a remoção dos comentários presentes e a remoção da toString() que em nenhum momento era utilizada.

```
public class Cargo {
	 // variável destinado ao id do cargo
    private int id; 
    // variável destinado ao nome do cargo
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String toString() {
        return this.nome;
    }    

}


```

```
public class Cargo {
    private int id; 
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
     
}

```
<div class="tags" id="Funcionario"><strong>/entidade/Funcionario.java</strong></div>

Assim como a refatoração da classe anterior, a renomeação da nomenclatura, a remoção dos comentários presentes e a remoção da toString() que em nenhum momento era utilizada.

```
public class Funcionario {
	//dados funcionario
    private int id; 
    private String nome; 
    private String sobrenome; 
    private String dataNascimento;
    private String email;
    private int cargo; 
    private double salario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
    
    @Override
    public String toString() {
        return this.nome + " " + this.sobrenome;
    }

}


```

```
    private int id; 
    private String firstName; 
    private String lastName; 
    private String birthDate;
    private String email;
    private int office; 
    private double salary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getOffice() {
        return office;
    }

    public void setOffice(int office) {
        this.office = office;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


```

<div class="tags" id="BancoDeDados"><strong>/sistema/BancoDeDados.java</strong></div>

Como passamos realizar o banco de dados centralizados na função <a href="#conn">/conectividade/Main.java</a> essa classe foi removida

<div class="tags" id="Validacao"><strong>/sistema/Validador.java</strong></div>

Como debito tecnico identifiquei a falta de validação em campos criticos do formulario, então foi criado uma classe para tal prezando a não duplicidade

```
public class Validador {
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



```
<div class="tags" id="Navegador"><strong>/sistema/Navegador.java</strong></div>


Na classe Navegador há um feature Envy da classe <a href="Sistema">Sistema</a> o Sistema.tela, ele foi excluído da classe Sistema e adicionado na classe Navegador

```
	Sistema.tela  = new Login();

```

```
public static JPanel screen;
		screen = new Login();

```
E para a duplicação do titulo da tela de da atualização da mesma foi criado o infoPageDefault

```
Sistema.frame.setTitle("Funcionários OOP Company");
		Navegador.atualizarTela();

```

```
private static void infoPageDefault(String title) {
		Sistema.frame.setTitle("Funcionários OOP Company - "+title);
		Navegador.atualizarTela();	
	}

```
Fora esses dois pontos foi renomeado toda a nomeclatura da classe

```
package sistema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import entidade.Cargo;
import sistema.telas.CargosEditar;
import sistema.telas.CargosConsultar;
import sistema.telas.CargosInserir;
import entidade.Funcionario;
import sistema.telas.FuncionariosConsultar;
import sistema.telas.FuncionariosInserir;
import sistema.telas.FuncionariosEditar;
import sistema.telas.Inicio;
import sistema.telas.Login;

public class Navegador {
	private static boolean menuConstruido;
	private static boolean menuHabilitado;
	private static JMenuBar menuBar;
	private static JMenu menuArquivo, menuFuncionarios, menuCargos;
	private static JMenuItem miSair, miFuncionariosConsultar, miFuncionariosCadastrar, miCargosConsultar;
	private static JMenuItem miCargosCadastrar;
	
	public static void login() {
		Sistema.tela = new Login();
		Sistema.frame.setTitle("Funcionários OOP Company");
		Navegador.atualizarTela();	
	}
	
	public static void inicio() {
		Sistema.tela = new Inicio();
		Sistema.frame.setTitle("Funcionários OOP Company");
		Navegador.atualizarTela();
	}
	
	public static void cargosCadastrar() {
		Sistema.tela = new CargosInserir();
		Sistema.frame.setTitle("Funcionários OOP Company - Cadastrar Cargos");
		Navegador.atualizarTela();		
	}
	
	public static void cargosConsultar() {
		Sistema.tela = new CargosConsultar();
		Sistema.frame.setTitle("Funcionários OOP Company - Consultar Cargos");
		Navegador.atualizarTela();	
	}
	
	public static void cargosEditar(Cargo cargo) {
		Sistema.tela = new CargosEditar(cargo);
		Sistema.frame.setTitle("Funcionários OOP Company - Editar Cargos");
		Navegador.atualizarTela();
	}
	
	 public static void funcionariosCadastrar(){
		 Sistema.tela = new FuncionariosInserir();
	     Sistema.frame.setTitle("Funcionarios OOP Company - Cadastrar funcionários");
	     Navegador.atualizarTela();
	 }
	    
	 public static void funcionariosConsultar(){
	     Sistema.tela = new FuncionariosConsultar();
	     Sistema.frame.setTitle("Funcionarios OOP Company - Consultar funcionários");
	     Navegador.atualizarTela();
	 }

	 public static void funcionariosEditar(Funcionario funcionario){
	     Sistema.tela = new FuncionariosEditar(funcionario);  
	     Sistema.frame.setTitle("Funcionários OOP Company - Editar funcionários");           
	     Navegador.atualizarTela();
	    }
	 
	
	private static void atualizarTela() {
		Sistema.frame.getContentPane().removeAll();
		Sistema.tela.setVisible(true);
		Sistema.frame.add(Sistema.tela);
		Sistema.frame.setVisible(true);
	}
	
	private static void construirMenu() {
		if(!menuConstruido) {
			menuConstruido = true;
			
			menuBar = new JMenuBar();
			
			menuArquivo = new JMenu("Arquivo");
			menuBar.add(menuArquivo);
			miSair = new JMenuItem("Sair");
			menuArquivo.add(miSair);
			
			menuFuncionarios = new JMenu ("Funcionários");
			menuBar.add(menuFuncionarios);
			miFuncionariosCadastrar = new JMenuItem("Cadastrar");
			menuFuncionarios.add(miFuncionariosCadastrar);
			miFuncionariosConsultar = new JMenuItem("Consultar");
			menuFuncionarios.add(miFuncionariosConsultar);
			
			menuCargos = new JMenu("Cargos");
			menuBar.add(menuCargos);
			miCargosCadastrar = new JMenuItem("Cadastrar");
			menuCargos.add(miCargosCadastrar);
			miCargosConsultar = new JMenuItem("Consultar");
			menuCargos.add(miCargosConsultar);
			
			criarEventosMenu();
		}
	}
	
	public static void habilitarMenu() {
		if(!menuConstruido) construirMenu();
		if(!menuHabilitado) {
			menuHabilitado = true;
			Sistema.frame.setJMenuBar(menuBar);
		}
	}
	
	public static void desabilitarMenu() {
		if(menuHabilitado) {
			menuHabilitado = false;
			Sistema.frame.setJMenuBar(null);
		}
	}
	
	private static void criarEventosMenu() {
		miSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		miFuncionariosCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				funcionariosCadastrar();
			}
		});
		miFuncionariosConsultar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				funcionariosConsultar();
			
			}
		});
		
		miCargosCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cargosCadastrar();
			}
		});
		
		miCargosConsultar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cargosConsultar();
			}
		});
		
		
	}
	
}


```

```
public class Navegador {
	public static JPanel screen;

	private static boolean menuBuilt;
	private static boolean menuAble;
	private static JMenuBar menuBar;
	private static JMenu menuFile, menuEmployees, menuOffice;
	private static JMenuItem menuInternExit, menuInternEmployeessConsult, menuInternEmployeessRegister, menuInternOfficeConsult;
	private static JMenuItem menuInternOfficeRegister;
	
	private static void infoPageDefault(String title) {
		Sistema.frame.setTitle("Funcionários OOP Company - "+title);
		Navegador.uptadeScreen();	
	}
	
	public static void login() {
		screen = new Login();
		infoPageDefault("Login");
	}
	
	public static void inicio() {
		screen = new Inicio();
		infoPageDefault("Inicio");
	}
	
	public static void officesRegister() {
		screen = new CargosInserir();
		infoPageDefault("Register Cargos");		
	}
	
	public static void officesConsult() {
		screen = new CargosConsultar();
		infoPageDefault("Consult Cargos");
	}
	
	public static void officesEdit(Cargo office) {
		screen = new CargosEditar(office);
		infoPageDefault("Editar Cargos");
	}
	
	 public static void employeesRegister(){
		 screen = new FuncionariosInserir();
	 	infoPageDefault("Register funcionarios");
	 }
	    
	 public static void employeesConsult(){
	     screen = new FuncionariosConsultar();
	 	infoPageDefault("Consult funcionarios");
	 }

	 public static void employeesEdit(Funcionario employee){
	     screen = new FuncionariosEditar(employee);         
	 	infoPageDefault("Editar funcionarios");
	    }
	 
	
	private static void uptadeScreen() {
		Sistema.frame.getContentPane().removeAll();
		screen.setVisible(true);
		Sistema.frame.add(screen);
		Sistema.frame.setVisible(true);
	}
	
	private static void buildMenu() {
		if(!menuBuilt) {
			menuBuilt = true;
			
			menuBar = new JMenuBar();
			
			menuFile = new JMenu("Arquivo");
			menuBar.add(menuFile);
			menuInternExit = new JMenuItem("Sair");
			menuFile.add(menuInternExit);
			
			menuEmployees = new JMenu ("Funcionários");
			menuBar.add(menuEmployees);
			menuInternEmployeessRegister = new JMenuItem("Cadastro");
			menuEmployees.add(menuInternEmployeessRegister);
			menuInternEmployeessConsult = new JMenuItem("Consulta");
			menuEmployees.add(menuInternEmployeessConsult);
			
			menuOffice = new JMenu("Cargos");
			menuBar.add(menuOffice);
			menuInternOfficeRegister = new JMenuItem("Cadastro");
			menuOffice.add(menuInternOfficeRegister);
			menuInternOfficeConsult = new JMenuItem("Consulta");
			menuOffice.add(menuInternOfficeConsult);
			
			createEventsMenu();
		}
	}
	
	public static void enableMenu() {
		if(!menuBuilt) buildMenu();
		if(!menuAble) {
			menuAble = true;
			Sistema.frame.setJMenuBar(menuBar);
		}
	}
	
	public static void disableMenu() {
		if(menuAble) {
			menuAble = false;
			Sistema.frame.setJMenuBar(null);
		}
	}
	
	private static void createEventsMenu() {
		menuInternExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		menuInternEmployeessRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				employeesRegister();
			}
		});
		menuInternEmployeessConsult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				employeesConsult();
			
			}
		});
		
		menuInternOfficeRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				officesRegister();
			}
		});
		
		menuInternOfficeConsult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				officesConsult();
			}
		});
		
		
	}
	
}


```
<div class="tags" id="Sistema"><strong>/sistema/Sistema.java</strong></div>


O JPanel tela, ele foi extraido da classe Sistema e adicionado na classe <a href="#Navegador">Navegador</a> e nomedo a classe criarComponentes()

```
	public class Sistema {

	public static JPanel tela;
	public static JFrame frame;
	
	public static void main(String[] args) {
		criarComponentes();
	}
	
	private static void criarComponentes() {
		frame = new JFrame("Sistema");
		frame.setSize(700,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		Navegador.login();
	}
	
}


```

```
public class Sistema {

	public static JFrame frame;
	
	public static void main(String[] args) {
		createComponents();
	}
	
	private static void createComponents() {
		frame = new JFrame("Sistema");
		frame.setSize(700,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		Navegador.login();
	}
	
}


```

Nas classes a seguir inicialmente foi refatorado toda a nomeclatura da classe, a pois isso foi removido a duplicação da <a href="#conn">conexão no banco</a>

<div class="tags" id="CargosConsultar"><strong>/telas/CargosConsultar.java: </strong> </div>



```
public class CargosConsultar extends JPanel {
    
    Cargo cargoAtual;
    JLabel labelTitulo, labelCargo;
    JTextField campoCargo;
    JButton botaoPesquisar, botaoEditar, botaoExcluir;
    DefaultListModel<Cargo> listasCargosModelo = new DefaultListModel();
    JList<Cargo> listaCargos;
            
    public CargosConsultar(){
        criarComponentes();
        criarEventos();
        Navegador.habilitarMenu();
    }

    private void criarComponentes() {
        setLayout(null);
        
        labelTitulo = new JLabel("Consulta de Cargos", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));      
        labelCargo = new JLabel("Nome do cargo", JLabel.LEFT);
        campoCargo = new JTextField();
        botaoPesquisar = new JButton("Pesquisar Cargo");
        botaoEditar = new JButton("Editar Cargo");
        botaoEditar.setEnabled(false);
        botaoExcluir = new JButton("Excluir Cargo");
        botaoExcluir.setEnabled(false);
        listasCargosModelo = new DefaultListModel();
        listaCargos = new JList();
        listaCargos.setModel(listasCargosModelo);
        listaCargos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelCargo.setBounds(100, 120, 400, 20);
        campoCargo.setBounds(100, 140, 400, 40);
        botaoPesquisar.setBounds(500, 140, 130, 40); 
        listaCargos.setBounds(100, 200, 400, 240);
        botaoEditar.setBounds(500, 360, 130, 40); 
        botaoExcluir.setBounds(500, 400, 130, 40);
        
        add(labelTitulo);
        add(labelCargo);
        add(campoCargo);
        add(listaCargos);
        add(botaoPesquisar);
        add(botaoEditar);
        add(botaoExcluir);
        
        setVisible(true);
    }

    private void criarEventos() {
        botaoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlPesquisarCargos(campoCargo.getText());
            }
        });
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Navegador.cargosEditar(cargoAtual);
            }
        });
        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlDeletarCargo();
            }
        });
        listaCargos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                cargoAtual = listaCargos.getSelectedValue();
                if(cargoAtual == null){
                    botaoEditar.setEnabled(false);
                    botaoExcluir.setEnabled(false);
                }else{
                    botaoEditar.setEnabled(true);
                    botaoExcluir.setEnabled(true);
                }
            }
        });
    }

    private void sqlPesquisarCargos(String nome_cargo) {
        Connection conexao;
        Statement instrucaoSQL;
        ResultSet resultados;
        
        try {
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);             
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultados = instrucaoSQL.executeQuery("SELECT * FROM cargos WHERE nome_cargo like '%"+nome_cargo+"%'");
            
            listasCargosModelo.clear();

            while (resultados.next()) {                
                Cargo cargo = new Cargo();
                cargo.setId(resultados.getInt("id"));
                cargo.setNome(resultados.getString("nome_cargo"));
                
                listasCargosModelo.addElement(cargo);
            }
            
        } catch (SQLException ex) {

			System.out.print(ex);	
        }
    }

    private void sqlDeletarCargo() {
        
        int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o Cargo "+cargoAtual.getNome()+"?", "Excluir", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){
            Connection conexao;
            Statement instrucaoSQL;
            ResultSet resultados;
            
            try {
                conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
                instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                instrucaoSQL.executeUpdate("DELETE FROM cargos WHERE id="+cargoAtual.getId()+"");
                listaCargos.clearSelection();
                JOptionPane.showMessageDialog(null, "Cargo deletado com sucesso!");  
                
            }catch(SQLException ex){

    			System.out.print(ex);	
            }
        }
        
    }
}


```

```
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



```

<div class="tags" id="CargosEditar "><strong>/telas/CargosEditar .java: </strong> </div>

```
public class CargosEditar extends JPanel {
	
	Cargo cargoAtual;
	JLabel labelTitulo, labelCargo;
	JTextField campoCargo;
	JButton botaoGravar;
	
	public CargosEditar(Cargo cargo) {
		cargoAtual = cargo;
		criarComponentes();
		criarEventos();
	}
	
	private void criarComponentes() {
		setLayout(null);
		
		labelTitulo = new JLabel("Editar de Cargo", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
		labelCargo = new JLabel("Nome do Cargo", JLabel.LEFT);
		campoCargo = new JTextField(cargoAtual.getNome());
		botaoGravar = new JButton("Salvar");
		
		labelTitulo.setBounds(20, 20, 660, 40);
		labelCargo.setBounds(150, 120, 400, 20);
		campoCargo.setBounds(150, 140, 400, 40);
		botaoGravar.setBounds(250, 380, 200, 40);
		
		add(labelTitulo);
		add(labelCargo);
		add(campoCargo);
		add(botaoGravar);
		
		setVisible(true);
	}
	
	private void criarEventos() {
		botaoGravar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cargoAtual.setNome(campoCargo.getText());
				sqlAtualizarCargo();
			}
		});
	}
	
	private void sqlAtualizarCargo( ) {

		
		Connection conexao;
		Statement instrucaoSQL;
		ResultSet resultados;
		
		try {
			conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
			instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			instrucaoSQL.executeUpdate("UPDATE cargos set nome_cargo='"+campoCargo.getText()+"' WHERE id="+cargoAtual.getId()+"");
			
			JOptionPane.showMessageDialog(null, "Cargo atualizado com sucesso!");
			
			conexao.close();
		} catch (SQLException ex) {

			System.out.print(ex);	
		}
		
	}

}

```

```
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


```

<div class="tags" id="CargosInserir "><strong>/telas/CargosInserir .java: </strong> </div>

```
public class CargosInserir extends JPanel {
	
	JLabel labelTitulo, labelCargo;
	JTextField campoCargo;
	JButton botaoGravar;
	
	public CargosInserir() {
		criarComponentes();
		criarEventos();
	}
	
	private  void criarComponentes() {
		setLayout(null);
		
		labelTitulo = new JLabel ("Cadastro de Cargo", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
		labelCargo = new JLabel ("Nome do cargo", JLabel.LEFT);
		campoCargo = new JTextField();
		botaoGravar = new JButton("Adicionar Cargo");
		
		labelTitulo.setBounds(20, 20, 660, 40);
		labelCargo.setBounds(150, 120, 400, 20);
		campoCargo.setBounds(150, 140, 400, 40);
		botaoGravar.setBounds(250, 380, 200, 40);
		
		add(labelTitulo);
		add(labelCargo);
		add(campoCargo);
		add(botaoGravar);
		
		setVisible(true);
	}
	
	private void criarEventos() {
		botaoGravar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Cargo novoCargo = 	new Cargo();
				novoCargo.setNome(campoCargo.getText());
				sqlInserirCargo(novoCargo);
			}
		});
		
	}
	
private void sqlInserirCargo(Cargo novoCargo) {
		
		
		Connection conexao;
		Statement instrucaoSQL;
		ResultSet resultados;
		
		try {
			
			conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
			instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			instrucaoSQL.executeUpdate("INSERT INTO cargos (nome_cargo) VALUES ('"+novoCargo.getNome()+"')");
			
			JOptionPane.showMessageDialog(null, "Cargo adicionado com sucesso!");			
		
		} catch (SQLException ex) {

			System.out.print(ex);	
			
		}
	}

}

```

```
public class CargosInserir extends JPanel {
	
	JLabel labelTitle, labelOffice;
	JTextField fieldOffice;
	JButton buttonSubmit;
	Main connected = new Main();
	public CargosInserir() {
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
				Cargo novoCargo = 	new Cargo();
				novoCargo.setName(fieldOffice.getText());
				insertOffice(novoCargo);
			}
		});
		
	}
	
private void insertOffice(Cargo novoCargo) {
	 	Validador validate = new Validador();
		validate.office(fieldOffice.getText());
			
		try {
			int result = connected.executeUpdate("INSERT INTO cargos (nome_cargo) VALUES ('"+novoCargo.getName()+"')");
			
			JOptionPane.showMessageDialog(null, "Cargo adicionado com sucesso!");			
		
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o carho.");	
			Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
			
		}
	}

}

```

Para as classes a seguir foi refatorado a nomeclatura das variaveis, remoção do comentário e  a duplicação da <a href="#conn">conexão no banco</a>

<div class="tags" id="FuncionariosConsultar "><strong>/telas/FuncionariosConsultar .java: </strong> </div>

```
public class FuncionariosConsultar extends JPanel {
    
    Funcionario funcionarioAtual;
    JLabel labelTitulo, labelFuncionario;
    JTextField campoFuncionario;
    JButton botaoPesquisar, botaoEditar, botaoExcluir;
    DefaultListModel<Funcionario> listasFuncionariosModelo = new DefaultListModel();
    JList<Funcionario> listaFuncionarios;
            
    public FuncionariosConsultar(){
        criarComponentes();
        criarEventos();
        Navegador.habilitarMenu();
    }

    private void criarComponentes() {
        setLayout(null);
        
        labelTitulo = new JLabel("Consulta de Funcionarios", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));      
        labelFuncionario = new JLabel("Nome do funcionario", JLabel.LEFT);
        campoFuncionario = new JTextField();
        botaoPesquisar = new JButton("Pesquisar Funcionario");
        botaoEditar = new JButton("Editar Funcionario");
        botaoEditar.setEnabled(false);
        botaoExcluir = new JButton("Excluir Funcionario");
        botaoExcluir.setEnabled(false);
        listasFuncionariosModelo = new DefaultListModel();
        listaFuncionarios = new JList();
        listaFuncionarios.setModel(listasFuncionariosModelo);
        listaFuncionarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelFuncionario.setBounds(150, 120, 400, 20);
        campoFuncionario.setBounds(150, 140, 400, 40);
        botaoPesquisar.setBounds(560, 140, 130, 40); 
        listaFuncionarios.setBounds(150, 200, 400, 240);
        botaoEditar.setBounds(560, 360, 130, 40); 
        botaoExcluir.setBounds(560, 400, 130, 40);
        
        add(labelTitulo);
        add(labelFuncionario);
        add(campoFuncionario);
        add(listaFuncionarios);
        add(botaoPesquisar);
        add(botaoEditar);
        add(botaoExcluir);
        
        setVisible(true);
    }

    private void criarEventos() {
        botaoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlPesquisarFuncionarios(campoFuncionario.getText());
            }
        });
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navegador.funcionariosEditar(funcionarioAtual);
            }
        });
        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlDeletarFuncionario();
            }
        });
        listaFuncionarios.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                funcionarioAtual = listaFuncionarios.getSelectedValue();
                if(funcionarioAtual == null){
                    botaoEditar.setEnabled(false);
                    botaoExcluir.setEnabled(false);
                }else{
                    botaoEditar.setEnabled(true);
                    botaoExcluir.setEnabled(true);
                }
            }
        });
    }

    private void sqlPesquisarFuncionarios(String nome) {        
        // conexão
        Connection conexao;
        // instrucao SQL
        Statement instrucaoSQL;
        // resultados
        ResultSet resultados;
        
        try {
            // conectando ao banco de dados
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
            
            // criando a instrução SQL 
           instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultados = instrucaoSQL.executeQuery("SELECT * FROM funcionarios WHERE nome like '%"+nome+"%' order by nome ASC");
            
            listasFuncionariosModelo.clear();

            while (resultados.next()) {                
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultados.getInt("id"));
                funcionario.setNome(resultados.getString("nome"));
                funcionario.setSobrenome(resultados.getString("sobrenome"));
                funcionario.setDataNascimento(resultados.getString("data_nascimento"));
                funcionario.setEmail(resultados.getString("email"));
                if(resultados.getString("cargo") != null) funcionario.setCargo(Integer.parseInt(resultados.getString("cargo")));
                funcionario.setSalario(Double.parseDouble(resultados.getString("salario")));
                
                listasFuncionariosModelo.addElement(funcionario);
            }
            
        } catch (SQLException ex) {

			System.out.print(ex);	
        }
    }

    private void sqlDeletarFuncionario() {
        String pergunta = "Deseja realmente excluir o Funcionario "+funcionarioAtual.getNome()+"?";
        int confirmacao = JOptionPane.showConfirmDialog(null, pergunta, "Excluir", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){
            // conexão
            Connection conexao;
            // instrucao SQL
            Statement instrucaoSQL;
            // resultados
            ResultSet resultados;

            try {
                // conectando ao banco de dados
                conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);

                // criando a instrução SQL
                instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                instrucaoSQL.executeUpdate("DELETE FROM funcionarios WHERE id="+funcionarioAtual.getId()+"");            

                JOptionPane.showMessageDialog(null, "Funcionario deletado com sucesso!");
                Navegador.inicio();

            } catch (SQLException ex) {

    			System.out.print(ex);	
            }
            
        }
    }
}

```

```
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
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar funcionï¿½rios.");
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

```

<div class="tags" id="FuncionariosEditar "><strong>/telas/FuncionariosEditar .java: </strong> </div>

```
public class FuncionariosEditar extends JPanel {
	 Funcionario funcionarioAtual;
	 JLabel labelTitulo, labelNome, labelSobrenome, labelDataNascimento, labelEmail, labelCargo, labelSalario;
	 JTextField campoNome, campoSobrenome, campoEmail;
	 JFormattedTextField campoDataNascimento, campoSalario;
	 JComboBox<Cargo> comboboxCargo;
	 JButton botaoGravar;  
	            
	 public FuncionariosEditar(Funcionario funcionario){
		 funcionarioAtual = funcionario;
	     criarComponentes();
	     criarEventos();
	     Navegador.habilitarMenu();
	  }

	  private void criarComponentes() {
	     setLayout(null);
	     String textoLabel = "Editar Funcionario "+funcionarioAtual.getNome()+" "+funcionarioAtual.getSobrenome();
	     labelTitulo = new JLabel(textoLabel, JLabel.CENTER);
	     labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));      
	     labelNome = new JLabel("Nome:", JLabel.LEFT);
	     campoNome = new JTextField(funcionarioAtual.getNome());     
	     labelSobrenome = new JLabel("Sobrenome:", JLabel.LEFT); 
	     campoSobrenome = new JTextField(funcionarioAtual.getSobrenome());     
	     labelDataNascimento = new JLabel("Data de Nascimento:", JLabel.LEFT);
	     campoDataNascimento = new JFormattedTextField(funcionarioAtual.getDataNascimento());
	     try {
	            MaskFormatter dateMask= new MaskFormatter("##/##/####");
	            dateMask.install(campoDataNascimento);
	        } catch (ParseException ex) {
	            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
	        }
	     labelEmail = new JLabel("E-mail:", JLabel.LEFT);
	     campoEmail = new JTextField(funcionarioAtual.getEmail());     
	     labelCargo = new JLabel("Cargo:", JLabel.LEFT);
	     comboboxCargo = new JComboBox();     
	     labelSalario = new JLabel("Salário:", JLabel.LEFT);
	     DecimalFormat formatter = new DecimalFormat("###0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
	     campoSalario = new JFormattedTextField(formatter);
	     campoSalario.setValue(funcionarioAtual.getSalario());
	     botaoGravar = new JButton("Salvar");
	        
	     
	    labelTitulo.setBounds(20, 20, 660, 40);
	    labelNome.setBounds(150, 80, 400, 20);
	    campoNome.setBounds(150, 100, 400, 40);
	    labelSobrenome.setBounds(150, 140, 400, 20);
	    campoSobrenome.setBounds(150, 160, 400, 40);
	    labelDataNascimento.setBounds(150, 200, 400, 20);
	    campoDataNascimento.setBounds(150, 220, 400, 40);
	    labelEmail.setBounds(150, 260, 400, 20);
	    campoEmail.setBounds(150, 280, 400, 40);
	    labelCargo.setBounds(150, 320, 400, 20);
	    comboboxCargo.setBounds(150, 340, 400, 40);
	    labelSalario.setBounds(150, 380, 400, 20);
	    campoSalario.setBounds(150, 400, 400, 40);
	    botaoGravar.setBounds(560, 400, 130, 40); 
	        
	    add(labelTitulo);
	    add(labelNome);
	    add(campoNome);
	    add(labelSobrenome);
	    add(campoSobrenome);
	    add(labelDataNascimento);
	    add(campoDataNascimento);
	    add(labelEmail);
	    add(campoEmail);
	    add(labelCargo);
	    add(comboboxCargo);
	    add(labelSalario);
	    add(campoSalario);
	    add(botaoGravar);
	        
	    sqlCarregarCargos(funcionarioAtual.getCargo());
	       
	    setVisible(true);
	    
	  }

	  private void criarEventos() {
	    botaoGravar.addActionListener(new ActionListener() {
	    @Override
	    	public void actionPerformed(ActionEvent e) {
	    		funcionarioAtual.setNome(campoNome.getText());
	            funcionarioAtual.setSobrenome(campoSobrenome.getText());
	            funcionarioAtual.setDataNascimento(campoDataNascimento.getText());
	            funcionarioAtual.setEmail(campoEmail.getText());
	            Cargo cargoSelecionado = (Cargo) comboboxCargo.getSelectedItem();
	            if(cargoSelecionado != null) funcionarioAtual.setCargo(cargoSelecionado.getId());
	            funcionarioAtual.setSalario(Double.valueOf(campoSalario.getText().replace(",", ".")));
	                
	            sqlAtualizarFuncionario();
	                        
	         }
	     });
	   }

	   private void sqlCarregarCargos(int cargoAtual) {        
	        // conexão
		   Connection conexao;
	        // instrucao SQL
	       Statement instrucaoSQL;
	        // resultados
	       ResultSet resultados;
	        
	       try {
	            // conectando ao banco de dados
	    	   conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha); 
	           instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	           resultados = instrucaoSQL.executeQuery("SELECT * from cargos order by nome_cargo asc");
	           
	           comboboxCargo.removeAll();
	           
	           while (resultados.next()) {
	        	   Cargo cargo = new Cargo();
	               cargo.setId(resultados.getInt("id"));
	               cargo.setNome(resultados.getString("nome_cargo"));
	               comboboxCargo.addItem(cargo);
	                
	               if(cargoAtual == cargo.getId()) comboboxCargo.setSelectedItem(cargo);
	            }
	            
	           comboboxCargo.updateUI();
	            
	           conexao.close();
	            
	        } catch (SQLException ex) {

				System.out.print(ex);	
	        }
	    }

	    private void sqlAtualizarFuncionario() {
	        
	        
	        // conexão
	        Connection conexao;
	        // instrucao SQL
	        PreparedStatement instrucaoSQL;
	        // resultados
	        ResultSet resultados;
	        
	        try {
	            // conectando ao banco de dados
	            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
	            
	            String template = "UPDATE funcionarios set nome=?, sobrenome=?, data_nascimento=?, email=?, cargo=?, salario=?";
	            template = template+" WHERE id="+funcionarioAtual.getId();
	            instrucaoSQL = conexao.prepareStatement(template);
	            instrucaoSQL.setString(1, campoNome.getText());
	            instrucaoSQL.setString(2, campoSobrenome.getText());
	            instrucaoSQL.setString(3, campoDataNascimento.getText());
	            instrucaoSQL.setString(4, campoEmail.getText());
	            Cargo cargoSelecionado = (Cargo) comboboxCargo.getSelectedItem();
	            if(cargoSelecionado != null){
	                instrucaoSQL.setInt(5, cargoSelecionado.getId());
	            }else{
	                instrucaoSQL.setNull(5, java.sql.Types.INTEGER);
	            }
	            instrucaoSQL.setString(6, campoSalario.getText().replace(",", "."));
	            instrucaoSQL.executeUpdate();
	            
	            JOptionPane.showMessageDialog(null, "Funcionario atualizado com sucesso!");
	            Navegador.inicio();
	            
	            conexao.close();
	            
	        } catch (SQLException ex) {

				System.out.print(ex);	     }
	    }

}

```

```
public class FuncionariosEditar extends JPanel {
	 Funcionario currentEmployee;
	 JLabel labelTitle, labelName, labelLastName, labelBirthDate, labelEmail, labelOffice, labelSalary;
	 JTextField fieldFirstName, fieldLastName, fieldEmail;
	 JFormattedTextField fieldBirthDate, fieldSalary;
	 JComboBox<Cargo> boxWithOffice;
	 JButton buttonSubmit;  
		Main connected = new Main();
	 public FuncionariosEditar(Funcionario employee){
		 currentEmployee = employee;
	     createComponents();
	     createEvents();
	     Navegador.enableMenu();
	  }

	  private void createComponents() {
	     setLayout(null);
	     String textoLabel = "Editar Funcionario "+currentEmployee.getFirstName()+" "+currentEmployee.getLastName();
	     labelTitle = new JLabel(textoLabel, JLabel.CENTER);
	     labelTitle.setFont(new Font(labelTitle.getFont().getName(), Font.PLAIN, 20));      
	     labelName = new JLabel("Nome:", JLabel.LEFT);
	     fieldFirstName = new JTextField(currentEmployee.getFirstName());     
	     labelLastName = new JLabel("Sobrenome:", JLabel.LEFT); 
	     fieldLastName = new JTextField(currentEmployee.getLastName());     
	     labelBirthDate = new JLabel("Data de Nascimento:", JLabel.LEFT);
	     fieldBirthDate = new JFormattedTextField(currentEmployee.getBirthDate());
	     try {
	            MaskFormatter dateMask= new MaskFormatter("##/##/####");
	            dateMask.install(fieldBirthDate);
	        } catch (ParseException ex) {
	            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
	        }
	     labelEmail = new JLabel("E-mail:", JLabel.LEFT);
	     fieldEmail = new JTextField(currentEmployee.getEmail());     
	     labelOffice = new JLabel("Cargo:", JLabel.LEFT);
	     boxWithOffice = new JComboBox();     
	     labelSalary = new JLabel("Salï¿½rio:", JLabel.LEFT);
	     DecimalFormat formatter = new DecimalFormat("###0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
	     fieldSalary = new JFormattedTextField(formatter);
	     fieldSalary.setValue(currentEmployee.getSalary());
	     buttonSubmit = new JButton("Salvar");
	        
	     
	    labelTitle.setBounds(20, 20, 660, 40);
	    labelName.setBounds(150, 80, 400, 20);
	    fieldFirstName.setBounds(150, 100, 400, 40);
	    labelLastName.setBounds(150, 140, 400, 20);
	    fieldLastName.setBounds(150, 160, 400, 40);
	    labelBirthDate.setBounds(150, 200, 400, 20);
	    fieldBirthDate.setBounds(150, 220, 400, 40);
	    labelEmail.setBounds(150, 260, 400, 20);
	    fieldEmail.setBounds(150, 280, 400, 40);
	    labelOffice.setBounds(150, 320, 400, 20);
	    boxWithOffice.setBounds(150, 340, 400, 40);
	    labelSalary.setBounds(150, 380, 400, 20);
	    fieldSalary.setBounds(150, 400, 400, 40);
	    buttonSubmit.setBounds(560, 400, 130, 40); 
	        
	    add(labelTitle);
	    add(labelName);
	    add(fieldFirstName);
	    add(labelLastName);
	    add(fieldLastName);
	    add(labelBirthDate);
	    add(fieldBirthDate);
	    add(labelEmail);
	    add(fieldEmail);
	    add(labelOffice);
	    add(boxWithOffice);
	    add(labelSalary);
	    add(fieldSalary);
	    add(buttonSubmit);
	        
	    loadOffice(currentEmployee.getOffice());
	       
	    setVisible(true);
	    
	  }

	  private void createEvents() {
	    buttonSubmit.addActionListener(new ActionListener() {
	    @Override
	    	public void actionPerformed(ActionEvent e) {
	    		currentEmployee.setFirstName(fieldFirstName.getText());
	            currentEmployee.setLastName(fieldLastName.getText());
	            currentEmployee.setBirthDate(fieldBirthDate.getText());
	            currentEmployee.setEmail(fieldEmail.getText());
	            Cargo officeSelecionado = (Cargo) boxWithOffice.getSelectedItem();
	            if(officeSelecionado != null) currentEmployee.setOffice(officeSelecionado.getId());
	            currentEmployee.setSalary(Double.valueOf(fieldSalary.getText().replace(",", ".")));
	                
	            updateEmployee();
	                        
	         }
	     });
	   }

	   private void loadOffice(int currentOffice) {        
	        
	       try {
		       ResultSet results = connected.executeQuery("SELECT * from cargos order by nome_cargo asc");
	           
	           boxWithOffice.removeAll();
	           
	           while (results.next()) {
	        	   Cargo office = new Cargo();
	               office.setId(results.getInt("id"));
	               office.setName(results.getString("nome_cargo"));
	               boxWithOffice.addItem(office);
	                
	               if(currentOffice == office.getId()) boxWithOffice.setSelectedItem(office);
	            }
	            
	           boxWithOffice.updateUI();
	            
	            
	        } catch (SQLException ex) {
	        	JOptionPane.showMessageDialog(null, "Ocorreu um erro ao carregar os cargos.");
	            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

	    private void updateEmployee() {
	        
	        Validador validate = new Validador();
		validate.fullName(fieldFirstName.getText(), fieldLastName.getText());
		validate.salary(fieldSalary.getText());
		validate.email(fieldEmail.getText());
	        
	        try {
	            String template = "UPDATE funcionarios set nome=?, sobrenome=?, data_nascimento=?, email=?, cargo=?, salario=?";
	            template = template+" WHERE id="+currentEmployee.getId();
	            PreparedStatement preparedStatement = connected.prepareStatement(template);
	            preparedStatement.setString(1, fieldFirstName.getText());
	            preparedStatement.setString(2, fieldLastName.getText());
	            preparedStatement.setString(3, fieldBirthDate.getText());
	            preparedStatement.setString(4, fieldEmail.getText());
	            Cargo officeSelecionado = (Cargo) boxWithOffice.getSelectedItem();
	            if(officeSelecionado != null){
	                preparedStatement.setInt(5, officeSelecionado.getId());
	            }else{
	                preparedStatement.setNull(5, java.sql.Types.INTEGER);
	            }
	            preparedStatement.setString(6, fieldSalary.getText().replace(",", "."));
	            preparedStatement.executeUpdate();
	            
	            JOptionPane.showMessageDialog(null, "Funcionario atualizado com sucesso!");
	            Navegador.inicio();
	           	            
	        } catch (SQLException ex) {
	            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar o Funcionario.");
	            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

}

```

<div class="tags" id="FuncionariosInserir "><strong>/telas/FuncionariosInserir .java: </strong> </div>

```

```

```

```

<div class="tags" id="FuncionariosInserir "><strong>/telas/FuncionariosInserir .java: </strong> </div>

```
public class FuncionariosInserir extends JPanel {
	
	JLabel labelTitulo, labelNome, labelSobrenome, labelDataNascimento, labelEmail, labelCargo, labelSalario;
	JTextField campoNome, campoSobrenome, campoEmail;
	JFormattedTextField campoDataNascimento, campoSalario;
	JComboBox<Cargo> comboBoxCargo;
	JButton botaoGravar;
	MaskFormatter mkSalario;
	
	public FuncionariosInserir() {
		criarComponentes();
		criarEventos();
		Navegador.habilitarMenu();
	}
	
	
	private void criarComponentes() {
		setLayout(null);
		
		labelTitulo =  new JLabel("Cadastro de Funcionario", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
		labelNome =  new JLabel("Nome:", JLabel.LEFT);
		campoNome = new JTextField();
		labelSobrenome = new  JLabel("Sobrenome:", JLabel.LEFT);
		campoSobrenome = new JTextField();
		labelDataNascimento = new JLabel("Data de Nascimento", JLabel.LEFT);
		campoDataNascimento = new JFormattedTextField();
		try {
			MaskFormatter dateMask= new MaskFormatter("##/##/####");
			dateMask.install(campoDataNascimento);
		}catch (ParseException ex) {
			Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
		}
		labelEmail = new JLabel("E-mail:", JLabel.LEFT);
		campoEmail = new JTextField();
		labelCargo = new JLabel("Cargo:", JLabel.LEFT);
		comboBoxCargo = new JComboBox();
		labelSalario = new JLabel("Salário", JLabel.LEFT);
		DecimalFormat formatter = new DecimalFormat("###0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
		campoSalario = new JFormattedTextField(formatter);
		campoSalario.setValue(0.00);
		botaoGravar = new JButton("Adicionar");
		
		labelTitulo.setBounds(20, 20, 660, 40);
		labelNome.setBounds(150, 80, 400, 20);
		campoNome.setBounds(150, 100, 400, 40);
		labelSobrenome.setBounds(150, 140, 400, 20);
		campoSobrenome.setBounds(150, 160, 400, 40);
		labelDataNascimento.setBounds(150, 200, 400, 20);
		campoDataNascimento.setBounds(150, 220, 400, 40);
		labelEmail.setBounds(150,260, 400, 20);
		campoEmail.setBounds(150,280, 400, 40);
		labelCargo.setBounds(150, 320, 400, 20);
		comboBoxCargo.setBounds(150, 340, 400, 40);
		labelSalario.setBounds(150, 380, 400, 20);
		campoSalario.setBounds(150, 400, 400, 40);
		botaoGravar.setBounds(560, 400, 130, 40);
		
		add(labelTitulo);
		add(labelNome);
		add(campoNome);
		add(labelSobrenome);
		add(campoSobrenome);
		add(labelDataNascimento);
		add(campoDataNascimento);
		add(labelEmail);
		add(campoEmail);
		add(labelCargo);
		add(comboBoxCargo);
		add(labelSalario);
		add(campoSalario);
		add(botaoGravar);
		
		sqlCarregarFuncionario();
		
		setVisible(true);
	}
	
	private void criarEventos() {
		botaoGravar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Funcionario novoFuncionario = new Funcionario();
				novoFuncionario.setNome(campoNome.getText());
				novoFuncionario.setSobrenome(campoSobrenome.getText());
				novoFuncionario.setDataNascimento(campoDataNascimento.getText());
				novoFuncionario.setEmail(campoEmail.getText());
				Cargo cargoSelecionado = (Cargo) comboBoxCargo.getSelectedItem();
				if(cargoSelecionado != null) novoFuncionario.setCargo(cargoSelecionado.getId());
				
				novoFuncionario.setSalario(Double.valueOf(campoSalario.getText().replace(",", ".")));
				sqlInserirFuncionario(novoFuncionario);
				}
		});
	}
	
	private void sqlCarregarFuncionario() {
		
		Connection conexao;
		Statement instrucaoSQL;
		ResultSet resultados;
		
		try {
			conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
			instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultados = instrucaoSQL.executeQuery("SELECT * from cargos order by nome_cargo asc");
			comboBoxCargo.removeAll();
			
			while (resultados.next()) {
				Cargo cargo = new Cargo();
				cargo.setId(resultados.getInt("id"));
				cargo.setNome(resultados.getString("nome_cargo"));
				comboBoxCargo.addItem(cargo);
			}
			comboBoxCargo.updateUI();
			
			conexao.close();
		} catch (SQLException ex) {
			System.out.print(ex);	
		}
	}
	
	private void sqlInserirFuncionario(Funcionario novoFuncionario) {
		
        Connection conexao;
        PreparedStatement instrucaoSQL;
        
        try{
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
            
            String template = "INSERT INTO funcionarios (nome,sobrenome,data_nascimento,email,cargo,salario) VALUES (?,?,?,?,?,?)";
            instrucaoSQL = conexao.prepareStatement(template);
            instrucaoSQL.setString(1, novoFuncionario.getNome());
            instrucaoSQL.setString(2, novoFuncionario.getSobrenome());
            instrucaoSQL.setString(3, novoFuncionario.getDataNascimento());
            instrucaoSQL.setString(4, novoFuncionario.getEmail());
            if(novoFuncionario.getCargo() > 0){
                instrucaoSQL.setInt(5, novoFuncionario.getCargo());
            }else{
                instrucaoSQL.setNull(5, java.sql.Types.INTEGER);
            }
            instrucaoSQL.setString(6, Double.toString(novoFuncionario.getSalario()));
            instrucaoSQL.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Funcionário adicionado com sucesso!");
            Navegador.inicio();
            
            conexao.close();
        
        } catch(SQLException ex){
			System.out.print(ex);	
        }
    }

}

```

```
public class FuncionariosInserir extends JPanel {
	
	JLabel labelTitle, labelName, labelLastName, labelBirthDate, labelEmail, labelOffice, labelSalary;
	JTextField fieldFirstName, fieldLastName, fieldEmail;
	JFormattedTextField fieldBirthDate, fieldSalary;
	JComboBox<Cargo> boxWithOffice;
	JButton buttonSubmit;
	MaskFormatter mkSalario;
	Main connected = new Main();
	public FuncionariosInserir() {
		createComponents();
		createEvents();
		Navegador.enableMenu();
	}
	
	
	private void createComponents() {
		setLayout(null);
		
		labelTitle =  new JLabel("Cadastro de Funcionario", JLabel.CENTER);
		labelTitle.setFont(new Font(labelTitle.getFont().getName(), Font.PLAIN, 20));
		labelName =  new JLabel("Nome:", JLabel.LEFT);
		fieldFirstName = new JTextField();
		labelLastName = new  JLabel("Sobrenome:", JLabel.LEFT);
		fieldLastName = new JTextField();
		labelBirthDate = new JLabel("Data de Nascimento", JLabel.LEFT);
		fieldBirthDate = new JFormattedTextField();
		try {
			MaskFormatter dateMask= new MaskFormatter("##/##/####");
			dateMask.install(fieldBirthDate);
		}catch (ParseException ex) {
			Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
		}
		labelEmail = new JLabel("E-mail:", JLabel.LEFT);
		fieldEmail = new JTextField();
		labelOffice = new JLabel("Cargo:", JLabel.LEFT);
		boxWithOffice = new JComboBox();
		labelSalary = new JLabel("Salï¿½rio", JLabel.LEFT);
		DecimalFormat formatter = new DecimalFormat("###0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
		fieldSalary = new JFormattedTextField(formatter);
		fieldSalary.setValue(0.00);
		buttonSubmit = new JButton("Adicionar");
		
		labelTitle.setBounds(20, 20, 660, 40);
		labelName.setBounds(150, 80, 400, 20);
		fieldFirstName.setBounds(150, 100, 400, 40);
		labelLastName.setBounds(150, 140, 400, 20);
		fieldLastName.setBounds(150, 160, 400, 40);
		labelBirthDate.setBounds(150, 200, 400, 20);
		fieldBirthDate.setBounds(150, 220, 400, 40);
		labelEmail.setBounds(150,260, 400, 20);
		fieldEmail.setBounds(150,280, 400, 40);
		labelOffice.setBounds(150, 320, 400, 20);
		boxWithOffice.setBounds(150, 340, 400, 40);
		labelSalary.setBounds(150, 380, 400, 20);
		fieldSalary.setBounds(150, 400, 400, 40);
		buttonSubmit.setBounds(560, 400, 130, 40);
		
		add(labelTitle);
		add(labelName);
		add(fieldFirstName);
		add(labelLastName);
		add(fieldLastName);
		add(labelBirthDate);
		add(fieldBirthDate);
		add(labelEmail);
		add(fieldEmail);
		add(labelOffice);
		add(boxWithOffice);
		add(labelSalary);
		add(fieldSalary);
		add(buttonSubmit);
		
		loadEmployee();
		
		setVisible(true);
	}
	
	private void createEvents() {
		buttonSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Funcionario novoFuncionario = new Funcionario();
				novoFuncionario.setFirstName(fieldFirstName.getText());
				novoFuncionario.setLastName(fieldLastName.getText());
				novoFuncionario.setBirthDate(fieldBirthDate.getText());
				novoFuncionario.setEmail(fieldEmail.getText());
				Cargo officeSelecionado = (Cargo) boxWithOffice.getSelectedItem();
				if(officeSelecionado != null) novoFuncionario.setOffice(officeSelecionado.getId());
				
				novoFuncionario.setSalary(Double.valueOf(fieldSalary.getText().replace(",", ".")));
				insertEmployee(novoFuncionario);
				}
		});
	}
	
	private void loadEmployee() {
		
		
		try {
			ResultSet results = connected.executeQuery("SELECT * from cargos order by nome_cargo asc");
			boxWithOffice.removeAll();
			
			while (results.next()) {
				Cargo office = new Cargo();
				office.setId(results.getInt("id"));
				office.setName(results.getString("nome_cargo"));
				boxWithOffice.addItem(office);
			}
			boxWithOffice.updateUI();
			
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao carregar os cargos");
			Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);	
		}
	}
	
	private void insertEmployee(Funcionario novoFuncionario) {
		Validador validate = new Validador();
		validate.fullName(fieldFirstName.getText(), fieldLastName.getText());
		validate.salary(fieldSalary.getText());
		validate.email(fieldEmail.getText());
        
        try{  

            PreparedStatement preparedStatement = connected.prepareStatement("INSERT INTO funcionarios (nome,sobrenome,data_nascimento,email,cargo,salario) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, novoFuncionario.getFirstName());
            preparedStatement.setString(2, novoFuncionario.getLastName());
            preparedStatement.setString(3, novoFuncionario.getBirthDate());
            preparedStatement.setString(4, novoFuncionario.getEmail());
            if(novoFuncionario.getOffice() > 0){
                preparedStatement.setInt(5, novoFuncionario.getOffice());
            }else{
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }
            preparedStatement.setString(6, Double.toString(novoFuncionario.getSalary()));
            preparedStatement.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Funcionario adicionado com sucesso!");
            Navegador.inicio();
            
        
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o Funcionario.");
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }

}


```

<div class="tags" id="Inicio"><strong>/telas/Inicio.java: </strong> </div>

Foi removido a classe createEvents() que não era utilizada e renomeada toda a nomeclatura da classe 

```
public class Inicio extends JPanel{
	JLabel labelTitulo;

	public Inicio() {
		criarComponentes();
		Navegador.habilitarMenu();
	}
	
	private void criarComponentes() {
		setLayout(null);
		
		labelTitulo = new JLabel("Escolha uma opção no menu superior", JLabel.CENTER);
		labelTitulo.setBounds(20, 100, 660, 40);
		
		add(labelTitulo);
		
		setVisible(true);
	}
	

}


```

```
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


```
<div class="tags" id="Login"><strong>/telas/Login.java: </strong> </div>

Foi renomeada toda a nomeclatura da classe e criado uma permissão para o login que era um debito tecnico já que no antigo codigo não era validado o mesmo

```
public class Login extends JPanel {
	
	JLabel labelUsuario;
	JTextField campoUsuario;
	JLabel labelSenha;
	JPasswordField campoSenha;
	JButton botaoEntrar;
	
	public Login() {
		criarComponentes();
		criarEventos();
	}
	
	private void criarComponentes() {
		setLayout(null);
		
		JLabel labelTitulo = new JLabel("Seja Bem vindo a OOP Company!", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont() .getName(), Font.PLAIN, 18));
		
		labelUsuario = new JLabel("Usuário", JLabel.LEFT);
		campoUsuario = new JTextField();
		labelSenha = new JLabel("Senha", JLabel.LEFT);
		campoSenha = new JPasswordField();
		botaoEntrar = new JButton("Entrar");
		
		labelTitulo.setBounds(20, 100, 600, 40);
		labelUsuario.setBounds(250, 220, 200, 20);
		campoUsuario.setBounds(250, 240, 200, 40);
		labelSenha.setBounds(250, 280, 200, 20);
		campoSenha.setBounds(250, 300, 200, 40);
		botaoEntrar.setBounds(250, 350, 200, 40);
		
		add(labelTitulo);
		add(labelUsuario);
		add(campoUsuario);
		add(labelSenha);
		add(campoSenha);
		add(botaoEntrar);
		
		setVisible(true);
		
	}
	
	private void criarEventos() {
		botaoEntrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Navegador.inicio();
			}
		});
	}
	
	

}


```

```
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
                    JOptionPane.showMessageDialog(null, "Acesso nao permitido");
                }
			}
		});
	}
	
	


```
