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
    @FXML private TableView<VendaFormaModel> tabFormaVenda;
    @FXML private TableView<VendaItensModel> tabItens;
    
    @FXML private TableView<ClienteModel> tabClientes;
    @FXML private TableColumn<ClienteModel, String> colIDCliente;
    @FXML private TableColumn<ClienteModel, String> colNomeCliente;
    @FXML private TableColumn<ClienteModel, String> colFantasiaCliente;
    @FXML private TableColumn<ClienteModel, String> colCGC;
    
    
    @FXML private TableView<UsuarioModel> tabVendedor;
    
    @FXML private TableView<FormaPgtoModel> tabFormaPgto;
    @FXML private TableColumn<FormaPgtoModel, Integer> colInsereFormaID;
    @FXML private TableColumn<FormaPgtoModel, String> colInsereFormaDesc;
    @FXML private TableColumn<FormaPgtoModel, String> colInsereFormaTipo;
    
    
    @FXML private TableView<ProdutoModel> tabInserirProduto;
    @FXML private TextField txtDescontoItem,txtItem,txtProduto,
    						txtValorForma,txtValorItem,txtValorTotalItem;
    
    private ObservableList<ClienteModel> listaClientes;//lista para selecionar o Cliente
    private ObservableList<FormaPgtoModel> listaForma;//lista para selecionar a forma de pgto
    private ObservableList<UsuarioModel> listaVendedor;// lista para selecionar o vendedor
    private ObservableList<ProdutoModel> listaProduto;// lista para selecionar o produto
    private ObservableList<VendaFormaModel> listaFormaVenda= FXCollections.observableArrayList();// formas de pagamento inserido no pedido
    private ObservableList<VendaItensModel> listaItensVenda = FXCollections.observableArrayList();
    
    ClienteModel cliente = new ClienteModel(0, null, null, null, null, null, null, null, null, null, null);
    FormaPgtoModel formaPgto = new FormaPgtoModel(0, null, null);
    UsuarioModel vendedor = new UsuarioModel(0, null, null, null, null);
    VendaModel venda = new VendaModel(0, 0, null, 0, null, null, null);
    ProdutoModel produto = new ProdutoModel(0, null, null, null, null, 0, 0, 0);
    
    private int idProdutoSelecionado = 0;
    
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
    	//forma pgto seleciona
    	colInsereFormaID.setCellValueFactory(new PropertyValueFactory<>("ID"));
    	colInsereFormaDesc.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
    	colInsereFormaTipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));

    	
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
    	/*
    	btnInserirItem.setOnAction(e->{
    		txtProduto.clear();
    		txtProduto.requestFocus();
    	});*/
    	
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
    	
    	//Vincula a lista à tabela de itens
    	tabItens.setItems(listaItensVenda);
    	
    	//Adicione eventos para recalcular on-the-fly se o usuário digitar qtd ou desconto
    	txtQtdItem.setOnKeyReleased(e -> calcularTotalItem());
    	txtDescontoItem.setOnKeyReleased(e -> calcularTotalItem());
    	
    	//ação do botão Inserir Item
    	btnInserirItem.setOnAction(e -> {
    	    if(txtProduto.getText().isEmpty() || txtItem.getText().isEmpty()) {
    	        return; // Impede inserção vazia
    	    }

    	    // Pega os dados dos campos
    	    int sequencialItem = listaItensVenda.size() + 1; // Gera o número sequencial (1, 2, 3...)
    	    int idProduto = idProdutoSelecionado;
    	    String nomeProduto = txtProduto.getText();
    	    int qtd = Integer.parseInt(txtQtdItem.getText());
    	    String valorUn = txtValorItem.getText();
    	    String valorTotal = txtValorTotalItem.getText();

    	    /* * ATENÇÃO: A forma de instanciar abaixo depende de como o seu construtor 
    	     * da classe VendaItensModel está feito. Se ele não tiver construtor vazio, 
    	     * você deverá preencher via construtor: new VendaItensModel(sequencialItem, ...)
    	     */
    	    VendaItensModel novoItem = new VendaItensModel();
    	    novoItem.setItem(sequencialItem);
    	    novoItem.setIdProduto(idProduto);
    	    novoItem.setNomeProduto(nomeProduto);
    	    novoItem.setQtdItem(qtd);
    	    novoItem.setValorUn(valorUn);
    	    novoItem.setValorTotalItem(valorTotal);
    	    // Defina setCodBarras ou outros campos caso seja necessário na sua model

    	    // Adiciona na tabela
    	    listaItensVenda.add(novoItem);

    	    // Limpa os campos para o próximo item
    	    txtProduto.clear();
    	    txtItem.clear();
    	    txtQtdItem.clear();
    	    txtValorItem.clear();
    	    txtDescontoItem.clear();
    	    txtValorTotalItem.clear();
    	    
    	    // Devolve o foco
    	    txtProduto.requestFocus();

    	    // Atualiza as Labels gerais da venda
    	    atualizarTotais();
    	});
    	
    	// Evento de Duplo Clique na tabela de produtos
    	tabInserirProduto.setOnMouseClicked(event -> {
    	    if (event.getClickCount() == 2) {
    	        selecionarProduto();
    	        txtQtdItem.requestFocus(); // Já joga o cursor para o campo de quantidade
    	    }
    	});

    	// Evento de apertar ENTER na tabela de produtos
    	tabInserirProduto.setOnKeyPressed(event -> {
    	    if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
    	        selecionarProduto();
    	        txtQtdItem.requestFocus(); // Já joga o cursor para o campo de quantidade
    	    }
    	});
    	
    	// Ao apertar ENTER no campo de Quantidade
    	txtQtdItem.setOnKeyPressed(event -> {
    	    if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
    	        // Se quiser que vá para o desconto antes de inserir, use a linha abaixo:
    	        txtDescontoItem.requestFocus(); 
    	        
    	        // Se quiser que o ENTER na quantidade já insira o item direto, comente a linha de cima e descomente a de baixo:
    	        // btnInserirItem.fire(); 
    	    }
    	});

    	// Ao apertar ENTER no campo de Desconto, insere o item na tabela da venda
    	txtDescontoItem.setOnKeyPressed(event -> {
    	    if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
    	        btnInserirItem.fire();
    	    }
    	});
    	
    	// Vincula a lista de pagamentos à tabela
    	tabFormaVenda.setItems(listaFormaVenda);

    	// Habilita o botão Excluir apenas se um item estiver selecionado
    	tabItens.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
    	    btnExcluirItem.setDisable(newSel == null);
    	});

    	// Ação de Excluir Item
    	btnExcluirItem.setOnAction(e -> {
    	    VendaItensModel selecionado = tabItens.getSelectionModel().getSelectedItem();
    	    if (selecionado != null) {
    	        listaItensVenda.remove(selecionado);
    	        reorganizarSequencialItens(); // Arruma a numeração (1, 2, 3...)
    	        atualizarTotais();
    	    }
    	});

    	// Duplo clique na tabela de itens (Edição)
    	tabItens.setOnMouseClicked(event -> {
    	    if (event.getClickCount() == 2) {
    	        VendaItensModel selecionado = tabItens.getSelectionModel().getSelectedItem();
    	        if (selecionado != null) {
    	            // Devolve os dados para os campos
    	            idProdutoSelecionado = selecionado.getIdProduto();
    	            txtProduto.setText(selecionado.getNomeProduto());
    	            txtItem.setText(String.valueOf(selecionado.getItem()));
    	            txtQtdItem.setText(String.valueOf(selecionado.getQtdItem()));
    	            txtValorItem.setText(selecionado.getValorUn());
    	            
    	            // Tira o item da tabela para o usuário reinserir (Editar)
    	            listaItensVenda.remove(selecionado);
    	            reorganizarSequencialItens();
    	            atualizarTotais();
    	            
    	            // Joga o cursor pra quantidade
    	            txtQtdItem.requestFocus();
    	        }
    	    }
    	});
    	
    	// --- BOTÃO FECHAR VENDA ---
    	btnFecharVenda.setOnAction(e -> {
    	    if (listaItensVenda.isEmpty()) {
    	        new Alert(Alert.AlertType.WARNING, "A lista de itens está vazia!").show();
    	        return;
    	    }
    	    painelItens.setDisable(true); // Bloqueia edição de itens
    	    txtNomeForma.setDisable(false);
    	    txtNomeForma.requestFocus();
    	    
    	    // Sugere o valor total líquido no campo de valor da forma
    	    double valorLiquido = extrairDouble(lblValorLiquido.getText());
    	    txtValorForma.setText(String.format(java.util.Locale.US, "%.2f", valorLiquido));
    	});
    	
    	// --- PESQUISA DE FORMA DE PAGAMENTO (Igual Cliente/Vendedor) ---
    	txtNomeForma.setOnKeyReleased(e -> {
    	    if (!txtNomeForma.getText().isEmpty()) {
    	        tabFormaPgto.setVisible(true);
    	        ListarFormaTab(txtNomeForma.getText());
    	    } else {
    	        tabFormaPgto.setVisible(false);
    	    }
    	});
    	
    	// Ao apertar ENTER no campo de nome, seleciona o primeiro da tabela
    	txtNomeForma.setOnAction(e -> {
    	    if (!listaForma.isEmpty()) {
    	        tabFormaPgto.getSelectionModel().selectFirst();
    	        selecionarForma();
    	    }
    	});
    	
    	// Eventos na Tabela de Pesquisa (tabFormaPgto)
    	tabFormaPgto.setOnKeyPressed(event -> {
    	    if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
    	        selecionarForma();
    	    }
    	});

    	tabFormaPgto.setOnMouseClicked(event -> {
    	    if (event.getClickCount() == 2) {
    	        selecionarForma();
    	    }
    	});

    	// --- BOTÃO INSERIR FORMA NA TABELA DE PAGAMENTOS ---
    	btnInserirForma.setOnAction(e -> {
    	    if (txtCodForma.getText().isEmpty() || txtValorForma.getText().isEmpty()) {
    	        new Alert(Alert.AlertType.WARNING, "Selecione uma forma e informe o valor!").show();
    	        return;
    	    }

    	    VendaFormaModel novaF = new VendaFormaModel();
    	    novaF.setForma(Integer.parseInt(txtCodForma.getText()));
    	    // Se sua model VendaFormaModel tiver o campo de nome, preencha-o aqui:
    	    // novaF.setNomeForma(txtNomeForma.getText()); 
    	    novaF.setValorTotal(txtValorForma.getText());
    	    
    	    // Calcula desconto se houver (Regra de Negócio dos 5%)
    	    double vlrLiquidoOriginal = extrairDouble(lblValorLiquido.getText());
    	    novaF.setValorBruto(String.valueOf(vlrLiquidoOriginal));

    	    listaFormaVenda.add(novaF);

    	    // Limpa campos de pagamento
    	    txtCodForma.clear();
    	    txtNomeForma.clear();
    	    txtValorForma.clear();
    	    
    	    atualizarTotaisPagamento();
    	});
    	
    	// Ação de Inserir Forma de Pagamento
    	btnInserirForma.setOnAction(e -> {
    	    if (txtCodForma.getText().isEmpty() || txtValorForma.getText().isEmpty()) {
    	        return;
    	    }

    	    try {
    	        double valorPago = Double.parseDouble(txtValorForma.getText().replace(",", "."));
    	        
    	        VendaFormaModel novaForma = new VendaFormaModel();
    	        novaForma.setForma(Integer.parseInt(txtCodForma.getText())); // ID da forma (ex: 1-Dinheiro, 2-Cartão)
    	        // Defina a descrição da forma caso sua model tenha esse campo (ex: novaForma.setDescricaoForma(txtNomeForma.getText());)
    	        novaForma.setValorTotal(String.valueOf(valorPago));
    	        
    	        listaFormaVenda.add(novaForma);
    	        
    	        txtCodForma.clear();
    	        txtNomeForma.clear();
    	        txtValorForma.clear();
    	        
    	        atualizarTotaisPagamento();
    	        
    	    } catch (NumberFormatException ex) {
    	        Alert alerta = new Alert(Alert.AlertType.ERROR, "Valor de pagamento inválido.");
    	        alerta.show();
    	    }
    	});
    	
    	btnFinalizar.setOnAction(e -> {
    	    // 1. Validação final de segurança
    	    if (listaItensVenda.isEmpty() || listaFormaVenda.isEmpty()) {
    	        new Alert(Alert.AlertType.ERROR, "A venda precisa ter itens e pagamentos para ser finalizada!").show();
    	        return;
    	    }

    	    // 2. Chama o método que faz o INSERT no banco e atualiza o estoque
    	    boolean sucesso = gravarVendaNoBanco();

    	    if (sucesso) {
    	        // Exibe o "Cupom" (Resumo da Venda)
    	        Alert alertaSucesso = new Alert(Alert.AlertType.INFORMATION);
    	        alertaSucesso.setTitle("Venda Finalizada");
    	        alertaSucesso.setHeaderText("Sucesso!");
    	        alertaSucesso.setContentText("A venda foi gravada e o estoque atualizado com sucesso.");
    	        alertaSucesso.showAndWait(); // Espera o usuário dar OK

    	        // 3. Limpa a tela inteira para a próxima venda
    	        resetarTelaVenda();
    	    } else {
    	        new Alert(Alert.AlertType.ERROR, "Erro ao gravar a venda no banco de dados.").show();
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
        FormaPgtoModel selecionada = tabFormaPgto.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            txtCodForma.setText(String.valueOf(selecionada.getID()));
            txtNomeForma.setText(selecionada.getDescricao());
            txtValorForma.setDisable(false);
            btnInserirForma.setDisable(false);
            tabFormaPgto.setVisible(false);
            txtValorForma.requestFocus(); // Foca no valor para o usuário confirmar/alterar
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
        	// Gera o sequencial baseado em quantos itens já estão na tabela da venda
            int sequencialItem = listaItensVenda.size() + 1;
            idProdutoSelecionado = produtoSelecionado.getID();
            // Preenche os campos de texto com os dados do produto
            txtProduto.setText(String.valueOf(produtoSelecionado.getNome())); 
            txtItem.setText(String.valueOf(sequencialItem));
            txtValorItem.setText(String.valueOf(produtoSelecionado.getPreco()));
            
            // Define os valores padrão
            txtQtdItem.setText("1");
            txtDescontoItem.setText("0");
            
            // Calcula o total e esconde a tabela de pesquisa
            calcularTotalItem();
            tabInserirProduto.setVisible(false);
            btnInserirItem.setDisable(false);
        }
    }
    
 // Calcula o valor total do item atual preenchido nos TextFields
    private void calcularTotalItem() {
        try {
            int qtd = txtQtdItem.getText().isEmpty() ? 1 : Integer.parseInt(txtQtdItem.getText());
            double valorUn = txtValorItem.getText().isEmpty() ? 0.0 : Double.parseDouble(txtValorItem.getText().replace(",", "."));
            double desconto = txtDescontoItem.getText().isEmpty() ? 0.0 : Double.parseDouble(txtDescontoItem.getText().replace(",", "."));

            double total = (qtd * valorUn) - desconto;
            txtValorTotalItem.setText(String.format(java.util.Locale.US, "%.2f", total));
        } catch (NumberFormatException e) {
            txtValorTotalItem.setText("0.00");
        }
    }

    // Varre a tabela de itens e atualiza as Labels de total da venda
    private void atualizarTotais() {
        double valorBrutoTotal = 0.0;
        double valorLiquidoTotal = 0.0;

        for (VendaItensModel item : listaItensVenda) {
            double qtd = item.getQtdItem(); // Assumindo que retorna int ou double
            // Convertendo as Strings da model de volta para Double
            double vlrUn = Double.parseDouble(item.getValorUn().replace(",", "."));
            double vlrTot = Double.parseDouble(item.getValorTotalItem().replace(",", "."));

            valorBrutoTotal += (qtd * vlrUn);
            valorLiquidoTotal += vlrTot;
        }

        double descontoTotal = valorBrutoTotal - valorLiquidoTotal;

        lblVlrBruto.setText(String.format("R$ %.2f", valorBrutoTotal));
        lblDesconto.setText(String.format("R$ %.2f", descontoTotal));
        lblValorLiquido.setText(String.format("R$ %.2f", valorLiquidoTotal));
    }
    
 // Recalcula o número sequencial dos itens após uma exclusão
    private void reorganizarSequencialItens() {
        int seq = 1;
        for (VendaItensModel item : listaItensVenda) {
            item.setItem(seq);
            seq++;
        }
        tabItens.refresh(); // Atualiza a visualização na tabela
    }

    // Gerencia as formas de pagamento, calcula troco e libera a finalização
    /*private void atualizarTotaisPagamento() {
        double totalLiquidoDaVenda = 0.0;
        try {
            // Pega o valor líquido calculado na aba de itens. Ex: "R$ 150.00" -> 150.00
            String strLiquido = lblValorLiquido.getText().replace("R$", "").trim().replace(",", ".");
            totalLiquidoDaVenda = Double.parseDouble(strLiquido);
        } catch (Exception e) {}

        double totalPago = 0.0;
        boolean temDinheiro = false;

        // Soma os pagamentos já inseridos
        for (VendaFormaModel f : listaFormaVenda) {
            totalPago += Double.parseDouble(f.getValorTotal().replace(",", "."));
            // Verifica se a forma é dinheiro (supondo que o ID 1 seja Dinheiro)
            if (f.getForma() == 1) { 
                temDinheiro = true;
            }
        }

        // Lógica para finalizar e troco
        if (totalPago >= totalLiquidoDaVenda) {
            btnFinalizar.setDisable(false); // Libera o F4 / Botão Finalizar
            txtNomeForma.setDisable(true);  // Bloqueia inserir mais pagamentos
            
            if (temDinheiro) {
                double troco = totalPago - totalLiquidoDaVenda;
                Alert alertaTroco = new Alert(Alert.AlertType.INFORMATION);
                alertaTroco.setTitle("Pagamento Concluído");
                alertaTroco.setHeaderText("Venda Pronta para Finalizar");
                alertaTroco.setContentText(String.format("Troco a devolver: R$ %.2f", troco));
                alertaTroco.show();
            }
        } else {
            // Se ainda falta pagar, sugere o restante no campo de valor
            double faltaPagar = totalLiquidoDaVenda - totalPago;
            txtValorForma.setText(String.format(java.util.Locale.US, "%.2f", faltaPagar));
            btnFinalizar.setDisable(true);
        }
    }*/
    private void atualizarTotaisPagamento() {
        double totalVenda = extrairDouble(lblValorLiquido.getText());
        double totalPago = 0.0;

        for (VendaFormaModel f : listaFormaVenda) {
            totalPago += Double.parseDouble(f.getValorTotal().replace(",", "."));
        }

        if (totalPago >= totalVenda) {
            double troco = totalPago - totalVenda;
            if (troco > 0) {
                new Alert(Alert.AlertType.INFORMATION, String.format("Troco: R$ %.2f", troco)).show();
            }
            btnFinalizar.setDisable(false); // Habilita o botão de concluir venda
            btnInserirForma.setDisable(true);
        } else {
            double falta = totalVenda - totalPago;
            txtValorForma.setText(String.format(java.util.Locale.US, "%.2f", falta));
            btnFinalizar.setDisable(true);
        }
    }

    // Método utilitário para limpar as strings de R$ e converter para double com segurança
    private double extrairDouble(String valorLabel) {
        try {
            return Double.parseDouble(valorLabel.replace("R$", "").replace(".", "").replace(",", ".").trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    private boolean gravarVendaNoBanco() {
        try {
            /* ==========================================================
             * 1. GRAVAR A VENDA (CABEÇALHO)
             * ========================================================== */
            // Aqui você pega os dados gerais (Cliente, Vendedor, Totais)
            int idCliente = Integer.parseInt(txtCodCliente.getText());
            int idVendedor = Integer.parseInt(txtCodVendedor.getText());
            double vlrTotal = extrairDouble(lblValorLiquido.getText());
            
            // Exemplo de como seria a chamada para a sua classe DAO:
            // VendaDAO dao = new VendaDAO();
            // int idNovaVenda = dao.inserirVendaRetornandoId(idCliente, idVendedor, vlrTotal);
            
            int idNovaVenda = 1; // SUBSTITUA PELA VARIÁVEL ACIMA (O ID GERADO PELO BANCO)

            /* ==========================================================
             * 2. GRAVAR OS ITENS E BAIXAR ESTOQUE (RN01)
             * ========================================================== */
            for (VendaItensModel item : listaItensVenda) {
                // Insere o item atrelado ao ID da venda
                // dao.inserirItemVenda(idNovaVenda, item);
                
                // RN01: Baixa automática de estoque
                // produtoDAO.diminuirEstoque(item.getIdProduto(), item.getQtdItem());
            }

            /* ==========================================================
             * 3. GRAVAR AS FORMAS DE PAGAMENTO
             * ========================================================== */
            for (VendaFormaModel forma : listaFormaVenda) {
                // Insere o pagamento atrelado ao ID da venda
                // dao.inserirFormaPagamentoVenda(idNovaVenda, forma);
            }

            return true; // Retorna true se tudo der certo
        } catch (Exception ex) {
            System.out.println("Erro ao finalizar venda: " + ex.getMessage());
            return false;
        }
    }

    private void resetarTelaVenda() {
        // 1. Limpa as Listas das Tabelas
        listaItensVenda.clear();
        listaFormaVenda.clear();
        
        // 2. Limpa os TextFields Principais
        txtCodCliente.clear();
        txtNomeCliente.clear();
        txtCodVendedor.clear();
        txtNomeVendedor.clear();
        
        // 3. Limpa os TextFields de Itens
        txtProduto.clear();
        txtItem.clear();
        txtQtdItem.clear();
        txtValorItem.clear();
        txtDescontoItem.clear();
        txtValorTotalItem.clear();
        
        // 4. Limpa os TextFields de Pagamento
        txtCodForma.clear();
        txtNomeForma.clear();
        txtValorForma.clear();
        
        // 5. Zera as Labels de Totais
        lblVlrBruto.setText("R$ 0.00");
        lblDesconto.setText("R$ 0.00");
        lblValorLiquido.setText("R$ 0.00");
        
        // 6. Reseta o estado dos painéis e botões
        painelItens.setDisable(true);
        txtNomeForma.setDisable(true);
        btnFinalizar.setDisable(true);
        btnInserirForma.setDisable(true);
        btnInserirCliente.setDisable(true);
        btnInserirVendedor.setDisable(true);
        btnNovaVenda.setDisable(false); // Libera o F2 novamente
        
        // 7. Joga o foco para iniciar novamente
        btnNovaVenda.requestFocus();
    } 
    
}

