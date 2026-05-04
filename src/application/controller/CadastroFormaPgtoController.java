package application.controller;

import java.util.List;

import application.model.FormaPgtoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class CadastroFormaPgtoController {

    @FXML private Button btnExcluir;
    @FXML private Button btnBuscar;
    @FXML private Button btnSalvar;
    @FXML private TableColumn<FormaPgtoModel, String> colDescricao;
    @FXML private TableColumn<FormaPgtoModel, Integer> colID;
    @FXML private TableColumn<FormaPgtoModel, String> colTipo;
    @FXML private TableView<FormaPgtoModel> tabForma;
    @FXML private ToggleGroup tipoForma;
    @FXML private TextField txtBuscar;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtID;

    private ObservableList<FormaPgtoModel> listaForma;
    RadioButton tipoSelecionado;

    // Criando objeto
    FormaPgtoModel forma = new FormaPgtoModel(0, null, null);

    // Método para salvar
    public void Salvar() {
        if (txtDescricao.getText().isEmpty() || tipoForma.getSelectedToggle() == null) {
            String erro = "";
            if (txtDescricao.getText().isEmpty()) erro += "\nCampo Descrição em Branco!";
            if (tipoForma.getSelectedToggle() == null) erro += "\nSelecione o Tipo!";
            Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
            mensagem.setContentText("Preencha os campos:" + erro);
            mensagem.showAndWait();
        } else {
            tipoSelecionado = (RadioButton) tipoForma.getSelectedToggle();
            forma.setDescricao(txtDescricao.getText());
            forma.setTipo(tipoSelecionado.getText());

            forma.Salvar();

            txtDescricao.setText("");
            txtID.setText("");
        }
        ListarFormasTab(null);
    }

    // Método para pesquisar
    public void Pesquisar() {
        if (!txtBuscar.getText().isEmpty()) {
            forma.Buscar(txtBuscar.getText());
            ListarFormasTab(txtBuscar.getText());

            txtID.setText(String.format("%06d", forma.getID()));
            txtDescricao.setText(forma.getDescricao());
            selTipoForma();
        } else {
            ListarFormasTab(null);
        }
    }

    // Método para excluir
    public void Excluir() {
        forma.Excluir();
        txtDescricao.setText("");
        txtID.setText("");
        ListarFormasTab(null);
    }

    @FXML
    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        colID.setCellFactory(c -> new TableCell<FormaPgtoModel, Integer>() {
            @Override
            protected void updateItem(Integer id, boolean empty) {
                super.updateItem(id, empty);
                setText(empty ? null : String.format("%06d", id));
            }
        });

        ListarFormasTab(null);

        txtBuscar.setOnAction(e -> btnBuscar.fire());

        tabForma.getSelectionModel().selectedItemProperty().addListener(
            (obs, selecao, novaSelecao) -> {
                if (novaSelecao != null) {
                    forma = novaSelecao;
                    txtID.setText(String.format("%06d", forma.getID()));
                    txtDescricao.setText(forma.getDescricao());
                    selTipoForma();
                }
            });

        btnSalvar.setOnAction(e -> Salvar());
        btnExcluir.setOnAction(e -> Excluir());
        btnBuscar.setOnAction(e -> Pesquisar());
    }

    public void ListarFormasTab(String valor) {
        List<FormaPgtoModel> formas = forma.ListarFormas(valor);
        listaForma = FXCollections.observableArrayList(formas);
        tabForma.setItems(listaForma);
    }

    public void selTipoForma() {
        int tipoIndex = 0;
        switch (forma.getTipo()) {
            case "Dinheiro": tipoIndex = 0; break;
            case "Cartão Débito": tipoIndex = 1; break;
            case "Cartão Crédito": tipoIndex = 2; break;
            case "Boleto": tipoIndex = 3; break;
            case "Pix": tipoIndex = 4; break;
            case "Transferência": tipoIndex = 5; break;
            default: tipoIndex = 0;
        }
        tipoForma.selectToggle(tipoForma.getToggles().get(tipoIndex));
    }
}
