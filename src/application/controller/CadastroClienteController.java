package application.controller;

import java.time.LocalDate;
import java.util.List;
import java.time.format.DateTimeFormatter;
import application.model.ClienteModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class CadastroClienteController {

    @FXML private Button btnBuscar,btnExcluir,btnSalvar, btnLimpar;
    @FXML private ComboBox<String> cbSexo;
    @FXML private CheckBox cbStatus;
    @FXML private TableView<ClienteModel> tabClientes;
    @FXML private TableColumn<ClienteModel, Integer> colID;
    @FXML private TableColumn<ClienteModel, String> colNome,colFone,colEmail,colCGC,
    												colRG, colDataNasc,colFantasia,
    												colTipo,colStatus,colSexo;
    @FXML private DatePicker dtNascimento;
    @FXML private ToggleGroup pessoa;
    @FXML private TextField txtCGC;
    @FXML private TextField txtEmail;
    @FXML private TextField txtFantasia;
    @FXML private TextField txtFone;
    @FXML private TextField txtID;
    @FXML private TextField txtNome;
    @FXML private TextField txtPesquisar;
    @FXML private TextField txtRG;
    @FXML private AnchorPane pane;
    RadioButton tipoCliente ;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private ObservableList<ClienteModel> listaClientes;
    private ClienteModel cliente = new ClienteModel(0, null, null, null, null, null, null, null, null, null, null);
    
    @FXML
    public void initialize() {
    	cbSexo.getItems().addAll("Masculino","Feminino");
    	cbSexo.getSelectionModel().select(0);
    	cbStatus.setSelected(true);
    	// Configuração das colunas da tabela
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeRazao"));
        colCGC.setCellValueFactory(new PropertyValueFactory<>("cpfCgc"));
        colRG.setCellValueFactory(new PropertyValueFactory<>("rgIe"));
        colFone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDataNasc.setCellValueFactory(new PropertyValueFactory<>("datanasc"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colFantasia.setCellValueFactory(new PropertyValueFactory<>("fantasia"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

      //colocar formato de "000000" na tabela de produtos
    	colID.setCellFactory(c -> new TableCell<ClienteModel, Integer>() {
    	    @Override
    	    protected void updateItem(Integer id, boolean empty) {
    	        super.updateItem(id, empty);
    	        setText(empty ? null : String.format("%06d", id));
    	    }
    	});
        
        
     // Eventos dos botões
        btnSalvar.setOnAction(e -> Salvar());
        btnExcluir.setOnAction(e -> Excluir());
        btnBuscar.setOnAction(e -> Pesquisar());
        btnLimpar.setOnAction(e->Limpar());
     // Enter no campo de pesquisa
        txtPesquisar.setOnAction(e -> btnBuscar.fire());
     // Seleção na tabela
        tabClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cliente = newSel;
                txtID.setText(String.format("%06d", cliente.getId()));
                txtNome.setText(cliente.getNomeRazao());
                txtFantasia.setText(cliente.getFantasia());
                txtCGC.setText(cliente.getCpfCgc());
                txtRG.setText(cliente.getRgIe());
                txtFone.setText(cliente.getTelefone());
                txtEmail.setText(cliente.getEmail());
                dtNascimento.setValue(LocalDate.parse(cliente.getDataNasc(), formatter));
                cbSexo.setValue(cliente.getSexo());
                cbStatus.setSelected(cliente.getStatus().equals("Ativo")? true:false);
                selTipoCli();
                
            }
        });

        ListarClientesTab(null);
    }
    
    public void Salvar() {
        if (txtNome.getText().isEmpty() || txtCGC.getText().isEmpty()) {
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setContentText("Preencha os campos obrigatórios: Nome e CPF/CNPJ!");
            msg.showAndWait();
            return;
        }

        cliente.setNomeRazao(txtNome.getText());
        cliente.setFantasia(txtFantasia.getText());
        cliente.setCpfCgc(txtCGC.getText());
        cliente.setRgIe(txtRG.getText());
        cliente.setTelefone(txtFone.getText());
        cliente.setEmail(txtEmail.getText());
        cliente.setDataNasc(dtNascimento.getValue() != null ? dtNascimento.getValue().toString() : null);
        cliente.setSexo(cbSexo.getValue());
        cliente.setTipo(pessoa.getSelectedToggle().getUserData() != null ? pessoa.getSelectedToggle().getUserData().toString() : "Pessoa Física");
        cliente.setStatus(cbStatus.isSelected() ? "Ativo" : "Inativo");

        cliente.Salvar();

        btnLimpar.fire();

        ListarClientesTab(null);
    }

    public void Pesquisar() {
    	
        if (!txtPesquisar.getText().isEmpty()) {
        	
            cliente.Buscar(txtPesquisar.getText());
            ListarClientesTab(txtPesquisar.getText());
            btnLimpar.fire();
            txtID.setText(String.format("%06d", cliente.getId()));
            txtNome.setText(cliente.getNomeRazao());
            txtFantasia.setText(cliente.getFantasia());
            txtCGC.setText(cliente.getCpfCgc());
            txtRG.setText(cliente.getRgIe());
            txtFone.setText(cliente.getTelefone());
            txtEmail.setText(cliente.getEmail());
            dtNascimento.setValue(LocalDate.parse(cliente.getDataNasc(), formatter));
            cbSexo.setValue(cliente.getSexo());
            cbStatus.setSelected(cliente.getStatus().equals("Ativo"));
        } else {
            ListarClientesTab(null);
        }
    }

    public void Excluir() {
    	btnLimpar.fire();
        cliente.Excluir();
        ListarClientesTab(null);
    }

    public void ListarClientesTab(String valor) {
    	btnLimpar.fire();
        List<ClienteModel> clientes = cliente.ListarUsuarios(valor);
        listaClientes = FXCollections.observableArrayList(clientes);
        tabClientes.setItems(listaClientes);
    }
    
    public void selTipoCli(){
		int tipoCli=0;
		tipoCliente = (RadioButton) pessoa.getSelectedToggle();
		switch (cliente.getTipo()){
				case "Pessoa Física":
					tipoCli=0;
					break;
				case "Pessoa Jurídica":
					tipoCli=1;
					break;
		default:
			tipoCli=0;				
		}
		
		pessoa.selectToggle(pessoa.getToggles().get(tipoCli));
	}
    public void selStatus() {
    	
    	if(cliente.getStatus().equals("Ativo")) { cbStatus.setSelected(true); }
    	else { cbStatus.setSelected(false);}
    }
    public void Limpar() {
    	for (Node node : pane.getChildrenUnmodifiable()) {
    	    if (node instanceof TextField) {
    	        ((TextField) node).clear();
    	    } else if (node instanceof CheckBox) {
    	        ((CheckBox) node).setSelected(false);
    	    } else if (node instanceof ComboBox<?>) {
    	        ((ComboBox<?>) node).getSelectionModel().clearSelection();
    	    } else if (node instanceof RadioButton) {
    	        ((RadioButton) node).setSelected(false);
    	    } else if (node instanceof DatePicker) {
    	        ((DatePicker) node).setValue(null);
    	    }
    	}
    }
}

