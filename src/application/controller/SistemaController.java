package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SistemaController {

    @FXML private MenuItem itemClientes;
    @FXML private MenuItem itemProcessaEstoque;
    @FXML private MenuItem itemProdutos;
    @FXML private MenuItem itemUsuarios;
    @FXML private MenuItem itemForma;
    @FXML private MenuItem itemVenda;
    @FXML private MenuItem itemConfiguracao;
    @FXML private MenuItem itemSair;
    private static Stage cadastroProdutoStage,cadastroUsuarioStage,
    cadastroClienteStage, cadastroFormaStage,processaEstoqueStage,
    vendaStage;
    
    @FXML private void initialize() {
    	itemProcessaEstoque.setOnAction(e->{AbrirProcessaEstoque();});
    	itemClientes.setOnAction(e->{AbrirCadastroCliente();});
    	itemForma.setOnAction(e->{AbrirCadastroFormaPgto();});
    	itemUsuarios.setOnAction(e->{AbrirCadastroUsuario();});
    	itemVenda.setOnAction(e->{AbrirVenda();});
    	
    }
    
    public void Sair() {
    	System.exit(0);
    }
    
    public void AbrirCadastroProduto() {
	try {		
		if(cadastroProdutoStage==null || !cadastroProdutoStage.isShowing()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/CadastroProdutos.fxml"));
			cadastroProdutoStage= new Stage();
			cadastroProdutoStage.setScene(new Scene (loader.load()));
			cadastroProdutoStage.setTitle("Sistema - Cadastro de Produtos");
			cadastroProdutoStage.getIcons().add(new Image(getClass().getResourceAsStream("/application/logo.png")));
			cadastroProdutoStage.show(); } 
		else {cadastroProdutoStage.toFront();}
    } catch(Exception e) {
		e.printStackTrace();
	}
    
    }
    
    public void AbrirProcessaEstoque() {
	try {
		if(processaEstoqueStage==null || !processaEstoqueStage.isShowing()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/ProcessarEstoque.fxml"));
			processaEstoqueStage= new Stage();
			processaEstoqueStage.setScene(new Scene (loader.load()));
			processaEstoqueStage.setTitle("Sistema - Processa Estoque");
			processaEstoqueStage.getIcons().add(new Image(getClass().getResourceAsStream("/application/logo.png")));
			processaEstoqueStage.show();}
		else {processaEstoqueStage.toFront();}
    } catch(Exception e) {
		e.printStackTrace();
	}
    }
    
    public void AbrirCadastroCliente() {
    	try {
    		if(cadastroClienteStage==null || !cadastroClienteStage.isShowing()) {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/CadastroCliente.fxml"));
    			cadastroClienteStage= new Stage();
    			cadastroClienteStage.setScene(new Scene (loader.load()));
    			cadastroClienteStage.setTitle("Sistema - Cadastro de Cliente");
    			cadastroClienteStage.getIcons().add(new Image(getClass().getResourceAsStream("/application/logo.png")));
    			cadastroClienteStage.show();} 
    		else {cadastroClienteStage.toFront();}
        } catch(Exception e) {
    		e.printStackTrace();
    	}
        }

    public void AbrirCadastroUsuario() {
    	try {
    		if(cadastroUsuarioStage==null || !cadastroUsuarioStage.isShowing()) {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/CadastroUsuario.fxml"));
    			cadastroUsuarioStage= new Stage();
    			cadastroUsuarioStage.setScene(new Scene (loader.load()));
    			cadastroUsuarioStage.setTitle("Sistema - Cadastro de Usuario");
    			cadastroUsuarioStage.getIcons().add(new Image(getClass().getResourceAsStream("/application/logo.png")));
    			cadastroUsuarioStage.show();}
    		else {cadastroUsuarioStage.toFront();}
        } catch(Exception e) {
    		e.printStackTrace();
    	}
        }
    
    public void AbrirVenda() {
    	try {
    		if(vendaStage==null || !vendaStage.isShowing()) {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Venda.fxml"));
    			vendaStage= new Stage();
    			vendaStage.setScene(new Scene (loader.load()));
    			vendaStage.setTitle("Sistema - Balcão de Venda");
    			vendaStage.getIcons().add(new Image(getClass().getResourceAsStream("/application/logo.png")));
    			vendaStage.show();}
    		else {vendaStage.toFront();}
        } catch(Exception e) {
    		e.printStackTrace();
    	}
        }
    
    public void AbrirCadastroFormaPgto() {
    	try {
    		if(cadastroFormaStage==null || !cadastroFormaStage.isShowing()) {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/FormaPagamento.fxml"));
    			cadastroFormaStage= new Stage();
    			cadastroFormaStage.setScene(new Scene (loader.load()));
    			cadastroFormaStage.setTitle("Sistema - Cadastro de Forma de Pagamento");
    			cadastroFormaStage.getIcons().add(new Image(getClass().getResourceAsStream("/application/logo.png")));
    			cadastroFormaStage.show();}
    		else {cadastroFormaStage.toFront();}
        } catch(Exception e) {
    		e.printStackTrace();
    	}
        }

}
