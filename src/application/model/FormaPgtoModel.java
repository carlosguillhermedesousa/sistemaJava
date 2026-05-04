package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import application.conexao;
import javafx.scene.control.Alert;

public class FormaPgtoModel {

	private int id;
	private String descricao;
	private String tipo;
	
	public FormaPgtoModel(int id,String descricao,String tipo){
		this.id=id;
		this.descricao=descricao;
		this.tipo=tipo;
	}
	
	public int getID() { return this.id;	}
	public String getDescricao() { return this.descricao; }
	public String getTipo() { return this.tipo;	}
	
	public void setID(int id) { this.id=id;	}
	public void setDescricao(String descricao) { this.descricao=descricao; }
	public void setTipo(String tipo) { this.tipo=tipo; }
	
    // Salvar (Insert ou Update)
    public void Salvar() {
        int vlrTipo = 0;
        switch (this.tipo) {
            case "Dinheiro": vlrTipo = 0; break;
            case "Cartão Débito": vlrTipo = 1; break;
            case "Cartão Crédito": vlrTipo = 2; break;
            case "Boleto": vlrTipo = 3; break;
            case "Pix": vlrTipo = 4; break;
            case "Transferência": vlrTipo = 5; break;
            default: vlrTipo = 6; // Outro
        }

        try (Connection conn = conexao.getConnection();
             PreparedStatement insert = conn.prepareStatement("INSERT INTO formaPgto (descricao, tipo) VALUES (?, ?)");
             PreparedStatement update = conn.prepareStatement("UPDATE formaPgto SET descricao=?, tipo=? WHERE id=?")) {

            if (this.id > 0) {
                update.setString(1, this.descricao);
                update.setInt(2, vlrTipo);
                update.setInt(3, this.id);
                update.executeUpdate();

                Alert mensagem = new Alert(Alert.AlertType.INFORMATION);
                mensagem.setContentText("Forma de Pagamento Alterada!");
                mensagem.showAndWait();
            } else {
                insert.setString(1, this.descricao);
                insert.setInt(2, vlrTipo);
                insert.executeUpdate();

                Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
                mensagem.setContentText("Forma de Pagamento Cadastrada!");
                mensagem.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Buscar por descrição ou tipo
    public void Buscar(String valor) {
        try (Connection conn = conexao.getConnection();
             PreparedStatement consulta = conn.prepareStatement(
                     "SELECT id, descricao, " +
                     "(CASE tipo WHEN 0 THEN 'Dinheiro' WHEN 1 THEN 'Cartão Débito' WHEN 2 THEN 'Cartão Crédito' " +
                     "WHEN 3 THEN 'Boleto' WHEN 4 THEN 'Pix' WHEN 5 THEN 'Transferência' ELSE 'Outro' END) as tipo " +
                     "FROM formaPgto WHERE descricao LIKE ? OR " +
                     "(CASE tipo WHEN 0 THEN 'Dinheiro' WHEN 1 THEN 'Cartão Débito' WHEN 2 THEN 'Cartão Crédito' " +
                     "WHEN 3 THEN 'Boleto' WHEN 4 THEN 'Pix' WHEN 5 THEN 'Transferência' ELSE 'Outro' END) LIKE ?")) {

            consulta.setString(1, "%" + valor + "%");
            consulta.setString(2, "%" + valor + "%");
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                this.id = resultado.getInt("id");
                this.descricao = resultado.getString("descricao");
                this.tipo = resultado.getString("tipo");
            } else {
                Alert mensagem = new Alert(Alert.AlertType.ERROR);
                mensagem.setContentText("Forma de Pagamento não encontrada!");
                mensagem.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Excluir
    public void Excluir() {
        try (Connection conn = conexao.getConnection();
             PreparedStatement consulta = conn.prepareStatement("DELETE FROM formaPgto WHERE id=?")) {

            if (this.id > 0) {
                consulta.setInt(1, this.id);
                consulta.executeUpdate();

                Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
                mensagem.setContentText("Forma de Pagamento Excluída!");
                mensagem.showAndWait();
            } else {
                Alert mensagem = new Alert(Alert.AlertType.ERROR);
                mensagem.setContentText("Forma de Pagamento não localizada!");
                mensagem.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listar todas ou filtradas
    public List<FormaPgtoModel> ListarFormas(String valor) {
        List<FormaPgtoModel> formas = new ArrayList<>();
        try (Connection conn = conexao.getConnection();
             PreparedStatement consulta = conn.prepareStatement(
                     "SELECT id, descricao, " +
                     "(CASE tipo WHEN 0 THEN 'Dinheiro' WHEN 1 THEN 'Cartão Débito' WHEN 2 THEN 'Cartão Crédito' " +
                     "WHEN 3 THEN 'Boleto' WHEN 4 THEN 'Pix' WHEN 5 THEN 'Transferência' ELSE 'Outro' END) as tipo " +
                     "FROM formaPgto");
             PreparedStatement consultaWhere = conn.prepareStatement(
                     "SELECT id, descricao, " +
                     "(CASE tipo WHEN 0 THEN 'Dinheiro' WHEN 1 THEN 'Cartão Débito' WHEN 2 THEN 'Cartão Crédito' " +
                     "WHEN 3 THEN 'Boleto' WHEN 4 THEN 'Pix' WHEN 5 THEN 'Transferência' ELSE 'Outro' END) as tipo " +
                     "FROM formaPgto WHERE descricao LIKE ? OR " +
                     "(CASE tipo WHEN 0 THEN 'Dinheiro' WHEN 1 THEN 'Cartão Débito' WHEN 2 THEN 'Cartão Crédito' " +
                     "WHEN 3 THEN 'Boleto' WHEN 4 THEN 'Pix' WHEN 5 THEN 'Transferência' ELSE 'Outro' END) LIKE ?")) {

            ResultSet resultado;
            if (valor == null) {
                resultado = consulta.executeQuery();
            } else {
                consultaWhere.setString(1, "%" + valor + "%");
                consultaWhere.setString(2, "%" + valor + "%");
                resultado = consultaWhere.executeQuery();
            }

            while (resultado.next()) {
                FormaPgtoModel f = new FormaPgtoModel(
                        resultado.getInt("id"),
                        resultado.getString("descricao"),
                        resultado.getString("tipo")
                );
                formas.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formas;
    }
	
}
