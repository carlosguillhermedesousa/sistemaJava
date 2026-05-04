package application.controller;

import java.text.DecimalFormat;
import java.util.List;

import application.model.ProdutoModel;
import application.model.UsuarioModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class CadastroUsuarioController {

    @FXML private Button btnBuscar,btnExcluir,btnSalvar;
    @FXML private ToggleGroup cliTipo;
    @FXML private TableColumn<UsuarioModel, Integer> colID;
    @FXML private TableColumn<UsuarioModel, String> colNome;
    @FXML private TableColumn<UsuarioModel, String> colTipo;
    @FXML private TableColumn<UsuarioModel, String> colUsuario;
    @FXML private TableColumn<ProdutoModel, String> colSenha;
    @FXML private TableView<UsuarioModel> tabUsuarios;
    @FXML private TextField txtBuscar;
    @FXML private TextField txtID;
    @FXML private TextField txtNome;
    @FXML private PasswordField txtSenha;
    @FXML private TextField txtUsuario;
    private ObservableList<UsuarioModel> listaUsuario;
    RadioButton tipoUsuario ;
    
  //CRIANDO O OBJETO
    UsuarioModel usuario= new UsuarioModel(0, null, null, null, null);
    //METODO PARA SALVAR O CADASTRO DO PRODUTO
    public void Salvar() {
    	//VERIFICA SE TEM ALGUM CAMPO VAZIO
    	if(txtNome.getText().isEmpty() || txtSenha.getText().isEmpty() || txtUsuario.getText().isEmpty() ) {
    		//ARMAZENA CAMPO QUE ESTÁ VAZIO
    		String erro="";
    		if(txtNome.getText().isEmpty()) {erro=erro+"\nCampo Nome em Branco!";}
    		if(txtSenha.getText().isEmpty()) {erro=erro+"\nCampo Senha em Branco!";}
    		if(txtUsuario.getText().isEmpty()) {erro=erro+"\nCampo Usuário em Branco!";}
    		//EXIBE UMA MENSAGEM PARA PREENCHER OS CAMPOS
    		Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
			mensagem.setContentText("Preenchaos campos:"+erro);
			mensagem.showAndWait();
    	} else {
    		tipoUsuario = (RadioButton) cliTipo.getSelectedToggle();
    		//INCLUIR AS INFORMAÇÕES NO OBJETO PRODUTO
        	usuario.setNome(txtNome.getText());
        	usuario.setUsuario(txtUsuario.getText());
        	usuario.setSenha(txtSenha.getText());
        	usuario.setTipo(tipoUsuario.getText());
        	
    		//SALVA PRODUTOS NO BANCO DE DADOS
    		usuario.Salvar();

			//LIMPA OS CAMPOS
        	txtNome.setText("");
        	txtUsuario.setText("");
        	txtSenha.setText("");
        	
    	}    	
    	ListarUsuariosTab(null);

    }
    //METODO PARA BUSCAR O CADASTRO DO PRODUTO
    public void Pesquisar() {
    	//VERIFICA SE TEM TEXTO NO CAMPO DE BUSCA
    	//PARA PESQUISAR DE ACORDO COM O TEXTO DIGITADO
    	if(!txtBuscar.getText().isEmpty()) {
    		
    		//executa o metodo de buscar
    		usuario.Buscar(txtBuscar.getText());
    		ListarUsuariosTab(txtBuscar.getText());
    		//informar os Valores nos campos do formulario
    		txtID.setText(String.format("%06d", usuario.getId()));//Usa o Get Para buscar informação
    		txtNome.setText(usuario.getNome()); 
    		txtUsuario.setText(usuario.getUsuario());
    		txtSenha.setText(usuario.getSenha());
    		selTipoUser();
    	} else { // SENÃO BUSCA TODOS OS PRODUTOS
    		//BUSCA TODOS OS PRODUTOS
    		ListarUsuariosTab(null);
    	}
    }
    //METODO PARA EXCLUIR O CADASTRO
    public void Excluir() {
    	usuario.Excluir();
    	txtNome.setText("");
		txtUsuario.setText("");
		txtSenha.setText("");
		ListarUsuariosTab(null);
    }
    //O METODO INITIALIZE VINCULA DIRETAMENTE O CONTROLE COM O FXML
    //A PALAVRA FXML É RESERVADA DO JAVAFX PARA REALIZAR A INTERAÇÃO
    @FXML
    public void initialize() {
    	//ATRIBUI O TIPO DE INFORMAÇÃO DOS GETTERS DA MODEL EX.: return this.id;
    	colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
    	colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    	colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
    	colSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
    	
    	//colocar formato de "000000" na tabela de produtos
    	colID.setCellFactory(c -> new TableCell<UsuarioModel, Integer>() {
    	    @Override
    	    protected void updateItem(Integer id, boolean empty) {
    	        super.updateItem(id, empty);
    	        setText(empty ? null : String.format("%06d", id));
    	    }
    	});
    	
    	
    	ListarUsuariosTab(null);
    	//Tecla enter no campo de pesquisa
    	txtBuscar.setOnAction(e->{btnBuscar.fire();}); // executa o click do butão buscar
    	
    	//Seleciona item na tabela de produtos 
    	tabUsuarios.getSelectionModel().selectedItemProperty().addListener(
		(obs, selecao , novaSelecao) ->{
			if (novaSelecao != null) {
				// Atualizar Model
				usuario=novaSelecao;
				//Atualizar Campos
				txtID.setText(String.format("%06d", usuario.getId()));
				txtNome.setText(usuario.getNome());
				txtUsuario.setText(usuario.getUsuario());
				txtSenha.setText(usuario.getSenha());
				selTipoUser();
				
			}
		});
    	
    	btnSalvar.setOnAction(e->{Salvar();});
    	btnExcluir.setOnAction(e->{Excluir();});
    	btnBuscar.setOnAction(e->{Pesquisar();});
    	
    }
    
    public void ListarUsuariosTab(String Valor) {
    	List <UsuarioModel> usuarios = usuario.ListarUsuarios(Valor);
    	listaUsuario=FXCollections.observableArrayList(usuarios);
    	tabUsuarios.setItems(listaUsuario);
    	
    }
    public void selTipoUser(){
		int tipoUser=0;
		switch (usuario.getTipo()){
				case "Administrador":
					tipoUser=0;
					break;
				case "Gerente":
					tipoUser=1;
					break;
				case "Vendedor":
					tipoUser=2;
					break;
				case "Estoquista":
					tipoUser=3;
				break;
		default:
			tipoUser=0;				
		}
		cliTipo.selectToggle(cliTipo.getToggles().get(tipoUser));
	}
}
