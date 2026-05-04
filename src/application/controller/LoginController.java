package application.controller;

import application.model.UsuarioModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginController {

    @FXML private Button btnEntrar;
    @FXML private Button btnSair;
    @FXML private TextField txtSenha;
    @FXML private TextField txtUsuario;
    UsuarioModel usuarioLogin=new UsuarioModel(0, null,null, null, null);
    @FXML 
    public void initialize() {
    	txtUsuario.setOnAction(e->{
    		if (!txtUsuario.getText().equals("")) {
    			txtSenha.requestFocus();
    		} else {
    			Alert alert = new Alert(Alert.AlertType.WARNING);
    			alert.setTitle("Digite um usuário");
    			alert.setHeaderText(null);
    			alert.setContentText("Digite um usuário");
    			alert.showAndWait();
    		}
    	});
    	btnEntrar.setOnAction(e->{
    		//if (usuarioLogin.Login(txtUsuario.getText(),txtSenha.getText())) {
    		// Fecha a tela de login
			btnEntrar.getScene().getWindow().hide();
    		try {
    			Parent root = FXMLLoader.load(getClass().getResource("/application/view/Sistema.fxml"));
    			Stage stage = new Stage();
    			Scene scene = new Scene(root);
    			stage.setScene(scene);
    			stage.setTitle("Sistema - by Carlos Guilherme");
    			stage.getIcons().add(new Image(getClass().getResourceAsStream("/application/logo.png")));
    			stage.centerOnScreen(); // Centraliza antes de abrir
    			stage.setMaximized(true); // Abre tela Maximixada
    			stage.show();
    			
    			// Evento de fechamento do sistema principal
                stage.setOnCloseRequest(event -> {
                    //garante que tudo encerre:
                    Platform.exit();
                    System.exit(0);
                });
    			
    			} catch(Exception ex) { ex.printStackTrace(); }
    		/*}else {
    			Alert alert = new Alert(Alert.AlertType.WARNING);
    			alert.setTitle("Erro ao realizar Login");
    			alert.setHeaderText(null);
    			alert.setContentText("Usuário ou senha incorretos");
    			alert.showAndWait();
    		}*/
    	});
    	btnSair.setOnAction(e->{System.exit(0);});
    	txtSenha.setOnAction(e->{btnEntrar.fire();});
    }

}
