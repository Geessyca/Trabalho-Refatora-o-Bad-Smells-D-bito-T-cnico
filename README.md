<h1 align="center">
  🎓<br>Trabalho Refatoração | Bad Smells | Débito Técnico
</h1>
<div class="tags" id=”conn”><strong>/conectividade/Main.java: </strong>
				<span ng-repeat="tag in tags">
            <a href=""><code class="btn btn-primary btn-xs">Código não refatorado</code></a></span>
  <span ng-repeat="tag in tags">
            <a href=""><code class="btn btn-primary btn-xs">Código refatorado</code></a></span>
			</div>

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

Foi observado nas classes <a href=”#CargosConsultar”>telas/CargosConsultar.java</a>, <a href=”#CargosConsultar”>telas/CargosConsultar.java</a>, <a href=”#CargosEditar”>telas/CargosEditar.java</a>, <a href=”#CargosInserir”>telas/CargosInserir.java</a>, <a href=”#FuncionariosConsultar”>telas/FuncionariosConsultar.java</a>,<a href=”#FuncionariosEditar”>telas/FuncionariosEditar.java</a>,<a href=”#FuncionariosInserir”>telas/FuncionariosInserir.java</a>, havia duplicidade na conexão com o banco de dados, por isso o método anterior foi necessário.

<div class="tags" id=”Cargo”><strong>/entidade/Cargo.java: </strong>
				<span ng-repeat="tag in tags">
            <a href=""><code class="btn btn-primary btn-xs">Código não refatorado</code></a></span>
  <span ng-repeat="tag in tags">
            <a href=""><code class="btn btn-primary btn-xs">Código refatorado</code></a></span>
			</div>

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
