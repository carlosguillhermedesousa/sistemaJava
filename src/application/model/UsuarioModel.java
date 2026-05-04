package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import application.conexao;
import javafx.scene.control.Alert;

public class UsuarioModel {
	private int id;
    private String nome;
    private String tipo;
    private String usuario;
    private String senha;

    // Construtor
    public UsuarioModel(int id, String nome, String tipo, String usuario, String senha) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.usuario = usuario;
        this.senha = senha;
    }

    // Getters  
    public int getId() { return id; }  
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public String getUsuario() { return usuario; }
    public String getSenha() { return senha;}
    
    //Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome;}
    public void setTipo(String tipo) { this.tipo = tipo;}
    public void setUsuario(String usuario) { this.usuario = usuario;}
    public void setSenha(String senha) { this.senha = senha;}

    public boolean  Login(String usuario , String senha) {
    	ResultSet  login;
    	try(Connection conn = conexao.getConnection();
				PreparedStatement consulta = conn.prepareStatement("select * from usuario where usuario = BINARY  ?  and senha = BINARY  ?");
    			){
    		consulta.setString(1,usuario);
			consulta.setString(2,senha);
			login=consulta.executeQuery();
			
			return login.next();	
			
    	}catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public void Salvar() {
    	int vlrTipo=0;
    	switch (this.tipo) {
	    	case "Administrador":  
	    		vlrTipo=0;
	    		break;
	    	case "Gerente":  
	    		vlrTipo=1;
	    		break;
	    	case "Vendedor":  
	    		vlrTipo=2;
	    		break;
	    	case "Estoquista":  
	    		vlrTipo=3;
	    		break;
    		default:    		
    	}
		try(Connection conn = conexao.getConnection();
				PreparedStatement consulta = conn.prepareStatement(" INSERT INTO usuario (nome, usuario, senha,tipo) values (?,?,?,?)");
				PreparedStatement consultaUpdate = 
				conn.prepareStatement("update usuario set nome=?,usuario=?,senha=?, tipo=? where id =?");){
			//VERIFICA SE EXISTE ID
			if(this.id>0) {// SE EXISTIR ALTERA SENÃO CADASTRA		
					consultaUpdate.setString(1,this.nome);
					consultaUpdate.setString(2,this.usuario);
					consultaUpdate.setString(3,this.senha);
					consultaUpdate.setInt(4,vlrTipo);
					consultaUpdate.setInt(5,this.id);
					consultaUpdate.executeUpdate();
					//CRIA MENSAGEM
					Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
					mensagem.setContentText("Usuário Alterado!");
					mensagem.showAndWait();
			} else {
			consulta.setString(1,this.nome);
			consulta.setString(2,this.usuario);
			consulta.setString(3,this.senha);
			consulta.setInt(4,vlrTipo);
			consulta.setInt(5,this.id);
			consulta.executeUpdate();
			
			//CRIA MENSAGEM
			Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
			mensagem.setContentText("Usuário Cadastrado!");
			mensagem.showAndWait();
			}
		}catch(Exception e) { e.printStackTrace();}
	}

	public void Buscar(String Valor) {
		try(Connection conn = conexao.getConnection();
			PreparedStatement consulta = conn.prepareStatement("select id,nome,usuario, (case tipo when 0 then \"administrador\" when 1 then \"gerente\" when 2 then \"vendedor\" when 3 then \"estoquista\" else \"desconhecido\" end ) as tipo  from usuario where nome like ? or usuario like ? or (case tipo when 0 then \"administrador\" when 1 then \"gerente\" when 2 then \"vendedor\" when 3 then \"estoquista\" else \"desconhecido\" end ) like ?");){
			consulta.setString(1,"%"+Valor+"%");
			consulta.setString(2,"%"+Valor+"%");
			consulta.setString(3,"%"+Valor+"%");
			ResultSet resultado= consulta.executeQuery();
			if(resultado.next()) {
				this.id=resultado.getInt("id");
				this.nome=resultado.getString("nome");
				this.usuario=resultado.getString("usuario");
				this.tipo=resultado.getString("tipo");
				
			} else {
				Alert mensagem = new Alert(Alert.AlertType.ERROR);
				mensagem.setContentText("Usuário não encontrado!");
				mensagem.showAndWait();
			}
		} catch(Exception e) { e.printStackTrace();}
	}

	public void Excluir() {
		try (Connection conn = conexao.getConnection();
			 PreparedStatement consulta = 
		conn.prepareStatement("delete from usuario where id=?");){
			//VEREIFICA SE O PRODUTO TEM ID
			if (this.id>0) {
				consulta.setInt(1, this.id);
				consulta.executeUpdate();
				//EXIBE MENSAGEM DE EXCLUÍDO 
				Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
				mensagem.setContentText("Produto Excluído!");
				mensagem.showAndWait();
			} else {
				//EXIBE MENSAGEM NÃO LOCALIZADO 
				Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
				mensagem.setContentText("Usuário Não Localizado!");
				mensagem.showAndWait();
			}
		}catch(Exception e) {e.printStackTrace();}
	}
	
	public List<UsuarioModel> ListarUsuarios(String Valor) {
		List <UsuarioModel> usuarios = new ArrayList<UsuarioModel>();
		try(Connection conn = conexao.getConnection();
			PreparedStatement consulta = conn.prepareStatement("select id,nome, usuario, senha, (case tipo when 0 then \"administrador\" when 1 then \"gerente\" when 2 then \"vendedor\" when 3 then \"estoquista\" else \"desconhecido\" end ) as tipo  from usuario");
			PreparedStatement consultaWhere = conn.prepareStatement("select id,nome, usuario, senha, (case tipo when 0 then \"administrador\" when 1 then \"gerente\" when 2 then \"vendedor\" when 3 then \"estoquista\" else \"desconhecido\" end ) as tipo  from usuario where nome like ? or usuario like ? or (case tipo when 0 then \"administrador\" when 1 then \"gerente\" when 2 then \"vendedor\" when 3 then \"estoquista\" else \"desconhecido\" end ) like ?")){
			ResultSet resultado=null;
			if(Valor == null) {
				 resultado=consulta.executeQuery();
			} else {
				consultaWhere.setString(1, "%"+Valor+"%");
				consultaWhere.setString(2, "%"+Valor+"%");
				consultaWhere.setString(3, "%"+Valor+"%");
				resultado=consultaWhere.executeQuery();
			}
						
			
			while (resultado.next()) {
				UsuarioModel u = new UsuarioModel(
						resultado.getInt("id"),
						resultado.getString("nome"),
						resultado.getString("tipo"),
						resultado.getString("usuario"),
						resultado.getString("senha")
						);
				usuarios.add(u);
			}	
		}catch(Exception e) {e.printStackTrace();}
		return usuarios;
	}
}
