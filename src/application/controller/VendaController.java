package application.controller;

import java.util.List;

import application.model.ClienteModel;
import application.model.FormaPgtoModel;
import application.model.MovimentacaoEstoqueModel;
import application.model.ProdutoModel;
import application.model.UsuarioModel;
import application.model.VendaFormaModel;
import application.model.VendaItensModel;
import application.model.VendaModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class VendaController {

    @FXML private Button btnExcluirItem,btnInserirItem,btnFecharVenda,
    					 btnFinalizar,btnHistorico,btnInserirCliente,
    					 btnInserirForma,btnInserirVendedor,btnNovaVenda;    
    @FXML private TableColumn<VendaItensModel, Integer> colCodBarras;
    @FXML private TableColumn<VendaItensModel, Integer> colID;
    @FXML private TableColumn<VendaItensModel, Integer> colIdProduto;
    @FXML private TableColumn<VendaItensModel, Integer> colItem;
    @FXML private TableColumn<VendaItensModel, String> colNomeProduto;
    @FXML private TableColumn<VendaItensModel, Integer> colQtdItem;
    
    @FXML private TableColumn<VendaFormaModel, String> colDesconto;
    @FXML private TableColumn<VendaFormaModel, Integer> colForma;
    @FXML private TableColumn<VendaFormaModel, String> colValorBruto;
    @FXML private TableColumn<VendaFormaModel, String> colValorTotal;
    @FXML private TableColumn<VendaItensModel, String> colValorTotalItem;
    @FXML private TableColumn<VendaItensModel, String> colValorUn;
    
    @FXML private TableColumn<ProdutoModel, Integer> colInserirProdutoCod;
    @FXML private TableColumn<ProdutoModel, String> colInserirProdutoCodBarras;
    @FXML private TableColumn<ProdutoModel, String> colInserirProdutoNome;
    @FXML private TableColumn<ProdutoModel, Double> colInserirProdutoValor;

    @FXML private TableColumn<UsuarioModel, Integer> colIDVendedor;
    @FXML private TableColumn<UsuarioModel, String> colNomeVendedor;
    
    
    @FXML private Label lblDesconto,lblValorLiquido,lblVlrBruto;
    @FXML private TextField txtNomeCliente,txtNomeForma,txtNomeVendedor,txtQtdItem,
    						txtCodCliente,txtCodForma,txtCodVendedor;
    @FXML private AnchorPane painelVenda,painelItens;
    @FXML private TableView<FormaPgtoModel> tabFormaVenda;
    @FXML private TableView<VendaItensModel> tabItens;
    
    @FXML private TableView<ClienteModel> tabClientes;
    @FXML private TableColumn<ClienteModel, String> colIDCliente;
    @FXML private TableColumn<ClienteModel, String> colNomeCliente;
    @FXML private TableColumn<ClienteModel, String> colFantasiaCliente;
    @FXML private TableColumn<ClienteModel, String> colCGC;
    
    
    @FXML private TableView<UsuarioModel> tabVendedor;
    @FXML private TableView<FormaPgtoModel> tabFormaPgto;
    @FXML private TableView<ProdutoModel> tabInserirProduto;
    @FXML private TextField txtDescontoItem,txtItem,txtProduto,
    						txtValorForma,txtValorItem,txtValorTotalItem;
    
    private ObservableList<ClienteModel> listaClientes;//lista para selecionar o Cliente
    private ObservableList<FormaPgtoModel> listaForma;//lista para selecionar a forma de pgto
    private ObservableList<UsuarioModel> listaVendedor;// lista para selecionar o vendedor
    private ObservableList<ProdutoModel> listaProduto;// lista para selecionar o produto
    private ObservableList<VendaFormaModel> listaFormaVenda;// formas de pagamento inserido no pedido
    
    ClienteModel cliente = new ClienteModel(0, null, null, null, null, null, null, null, null, null, null);
    FormaPgtoModel formaPgto = new FormaPgtoModel(0, null, null);
    UsuarioModel vendedor = new UsuarioModel(0, null, null, null, null);
    VendaModel venda = new VendaModel(0, 0, null, 0, null, null, null);
    ProdutoModel produto = new ProdutoModel(0, null, null, null, null, 0, 0, 0);
    
    @FXML
    public void initialize() {
    	// VendaItensModel
    	colCodBarras.setCellValueFactory(new PropertyValueFactory<>("codBarras"));
    	colID.setCellValueFactory(new PropertyValueFactory<>("id"));
    	colIdProduto.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
    	colItem.setCellValueFactory(new PropertyValueFactory<>("item"));
    	colNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
    	colQtdItem.setCellValueFactory(new PropertyValueFactory<>("qtdItem"));
    	colValorTotalItem.setCellValueFactory(new PropertyValueFactory<>("valorTotalItem"));
    	colValorUn.setCellValueFactory(new PropertyValueFactory<>("valorUn"));

    	// VendaFormaModel
    	colDesconto.setCellValueFactory(new PropertyValueFactory<>("desconto"));
    	colForma.setCellValueFactory(new PropertyValueFactory<>("forma"));
    	colValorBruto.setCellValueFactory(new PropertyValueFactory<>("valorBruto"));
    	colValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
    	
    	//cliente
    	colIDCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
    	colNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nomeRazao"));
    	colFantasiaCliente.setCellValueFactory(new PropertyValueFactory<>("fantasia"));
    	colCGC.setCellValueFactory(new PropertyValueFactory<>("cpfCgc"));
    	
    	//vendedor
    	colIDVendedor.setCellValueFactory(new PropertyValueFactory<>("Id"));
    	colNomeVendedor.setCellValueFactory(new PropertyValueFactory<>("Nome"));
    	
    	//produto
    	colInserirProdutoCod.setCellValueFactory(new PropertyValueFactory<>("ID"));
    	colInserirProdutoCodBarras.setCellValueFactory(new PropertyValueFactory<>("CodBarras"));
    	colInserirProdutoNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
    	colInserirProdutoValor.setCellValueFactory(new PropertyValueFactory<>("Preco"));
    	

    	
    	painelVenda.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
                    Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
                    mensagem.setTitle("Mensagem");
                    mensagem.setHeaderText("Aviso Importante");

                    switch (event.getCode()) {
                        case F2:
                            btnNovaVenda.fire();
                            mensagem.setContentText("Nova venda iniciada!");
                            break;
                        case F3:
                            btnHistorico.fire();
                            mensagem.setContentText("Histórico aberto!");
                            break;
                        case F4:
                            btnFinalizar.fire();
                            mensagem.setContentText("Venda finalizada!");
                            break;
                        case F5:
                            btnInserirCliente.fire();
                            mensagem.setContentText("Cliente inserido!");
                            break;
                        case F6:
                            btnInserirVendedor.fire();
                            mensagem.setContentText("Vendedor inserido!");
                            break;
                        case F7:
                            btnInserirForma.fire();
                            mensagem.setContentText("Forma de pagamento inserida!");
                            break;
                        case F8:
                            btnInserirItem.fire();
                            mensagem.setContentText("Item inserido!");
                            break;
                        case F9:
                            btnExcluirItem.fire();
                            mensagem.setContentText("Item excluído!");
                            break;
                        case F10:
                            btnFecharVenda.fire();
                            mensagem.setContentText("Venda fechada!");
                            break;
                        default:
                            break;
                    }
                });
            }
        });
    	
    	btnNovaVenda.setOnAction(e->{
    		txtNomeCliente.clear();
    		txtNomeCliente.setDisable(!txtNomeCliente.isDisable());
    		txtNomeCliente.requestFocus();
    		btnNovaVenda.setDisable(true);
    	});
    	
    	btnInserirCliente.setOnAction(e->{
    		selecionarCliente();
    		txtNomeCliente.setDisable(!txtNomeCliente.isDisable());
    		txtNomeCliente.requestFocus();
    		btnInserirCliente.setDisable(true);
    		txtNomeVendedor.setDisable(false);
    		txtNomeVendedor.requestFocus();
    		
    	});
    	
    	btnInserirVendedor.setOnAction(e->{
    		selecionarVendedor();
    		txtNomeVendedor.setDisable(!txtNomeVendedor.isDisable());
    		txtNomeVendedor.requestFocus();
    		btnInserirVendedor.setDisable(true);
    		painelItens.setDisable(false);
    		txtProduto.requestFocus();
    	});
    	
    	txtNomeCliente.setOnKeyReleased(e -> {
    	    if (!txtNomeCliente.getText().isEmpty()) {
    	    	btnInserirCliente.setDisable(false);
    	        tabClientes.setVisible(true);
    	        ListarClientesTab(txtNomeCliente.getText());
    	    }
    	});
    	
    	/*txtNomeCliente.focusedProperty().addListener((obs, oldVal, newVal) -> {
    	    if (!newVal) {
    	        // Aqui entra o código quando o campo perde o foco
    	    	tabClientes.getSelectionModel().selectFirst();
        		btnInserirCliente.fire();
    	    }
    	});*/
    	
    	txtNomeCliente.setOnAction(e->{
    		tabClientes.getSelectionModel().selectFirst();
    		btnInserirCliente.fire();
    		
    		});
    	
    	txtNomeVendedor.setOnKeyReleased(e -> {
    	    if (!txtNomeVendedor.getText().isEmpty()) {
    	    	btnInserirVendedor.setDisable(false);
    	        tabVendedor.setVisible(true);
    	        ListarVendedorTab(txtNomeVendedor.getText());
    	    }
    	});
    	/*
    	txtNomeVendedor.focusedProperty().addListener((obs, oldVal, newVal) -> {
    	    if (!newVal) {
    	        // Aqui entra o código quando o campo perde o foco
    	    	tabVendedor.getSelectionModel().selectFirst();
        		btnInserirVendedor.fire();
    	    }
    	});*/
    	
    	txtNomeVendedor.setOnAction(e->{
    		tabVendedor.getSelectionModel().selectFirst();
    		btnInserirVendedor.fire();
    		
    		});
    	
    	txtProduto.setOnKeyReleased(e -> {
    	    if (!txtProduto.getText().isEmpty()) {    	    	
    	    	tabInserirProduto.setVisible(true);
    	    	ListarProdutosTab(txtProduto.getText());
    	    }
    	});
    	
    	txtProduto.setOnAction(e->{
    		txtQtdItem.requestFocus();
    		});
    	
    	txtQtdItem.focusedProperty().addListener((obs, oldVal, newVal) -> {
    	    if (newVal) { 
    	        // Entrou no foco
    	        tabInserirProduto.setVisible(false);
    	    }
    	});
    	
    	btnInserirItem.setOnAction(e->{
    		txtProduto.clear();
    		txtProduto.requestFocus();
    	});
    	
    	tabClientes.setOnKeyPressed(event -> {
    	    if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
    	    	btnInserirCliente.fire();
    	    }
    	});
    	
    	tabClientes.setOnMouseClicked(event -> {
    	    if (event.getClickCount() == 2) {
    	        btnInserirCliente.fire();
    	    }
    	});
    	
    	tabVendedor.setOnKeyPressed(event -> {
    	    if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
    	    	btnInserirVendedor.fire();
    	    }
    	});

    	tabVendedor.setOnMouseClicked(event -> {
    	    if (event.getClickCount() == 2) {
    	    	btnInserirVendedor.fire();
    	    }
    	});
    }
    
    public void ListarClientesTab(String valor) {
        List<ClienteModel> clientes = cliente.ListarUsuarios(valor);
        listaClientes = FXCollections.observableArrayList(clientes);
        tabClientes.setItems(listaClientes);
    }
    
    private void selecionarCliente() {
        ClienteModel clienteSelecionado = tabClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
        	txtCodCliente.setText(String.valueOf(clienteSelecionado.getId()));
        	txtNomeCliente.setText(String.valueOf(clienteSelecionado.getNomeRazao()));
        	tabClientes.setVisible(false);
        	
        }
    }
    
    public void ListarVendedorTab(String valor) {
        List<UsuarioModel> vendedores = vendedor.ListarUsuarios(valor);
        listaVendedor = FXCollections.observableArrayList(vendedores);
        tabVendedor.setItems(listaVendedor);
    }
    
    private void selecionarVendedor() {
        UsuarioModel vendedorSelecionado = tabVendedor.getSelectionModel().getSelectedItem();
        if (vendedorSelecionado != null) {
        	txtCodVendedor.setText(String.valueOf(vendedorSelecionado.getId()));
        	txtNomeVendedor.setText(String.valueOf(vendedorSelecionado.getNome()));
        	tabVendedor.setVisible(false);
        	
        }
    }
    
    public void ListarFormaTab(String valor) {
        List<FormaPgtoModel> forma = formaPgto.ListarFormas(valor);
        listaForma = FXCollections.observableArrayList(forma);
        tabFormaPgto.setItems(listaForma);
    }
    
    private void selecionarForma() {
        FormaPgtoModel formaSelecionado = tabFormaPgto.getSelectionModel().getSelectedItem();
        if (formaSelecionado != null) {
        	txtCodForma.setText(String.valueOf(formaSelecionado.getID()));
        	txtNomeForma.setText(String.valueOf(formaSelecionado.getDescricao()));
        	tabFormaPgto.setVisible(false);
        	
        }
    }
    
    public void ListarProdutosTab(String valor) {
        List<ProdutoModel> produtos = produto.ListarProdutos(valor);
        listaProduto = FXCollections.observableArrayList(produtos);
        tabInserirProduto.setItems(listaProduto);
    }
    
    private void selecionarProduto() {
        ProdutoModel produtoSelecionado = tabInserirProduto.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
        	txtProduto.setText(String.valueOf(produtoSelecionado.getID()));
        	txtItem.setText(String.valueOf(produtoSelecionado.getNome()));
        	tabInserirProduto.setVisible(false);
        	
        }
    }
    
}

