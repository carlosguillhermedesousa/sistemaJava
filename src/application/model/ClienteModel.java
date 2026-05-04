package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import application.conexao;
import javafx.scene.control.Alert;

public class ClienteModel {
	    
	    private int id;
	    private String nomeRazao;
	    private String fantasia;
	    private String cpfCgc;
	    private String rgIe;
	    private String telefone;
	    private String email;
	    private String dataNasc;
	    private String sexo;   // 0: Masculino, 1: Feminino
	    private String tipo;   // 0: Física, 1: Jurídica
	    private String status; // 0: Inativo, 1: Ativo

	    // Construtor
	    public ClienteModel(int id, String nomeRazao, String fantasia, String cpfCgc, String rgIe,
	                   String telefone, String email, String dataNasc, String sexo, String tipo, String status) {
	        this.id = id;
	        this.nomeRazao = nomeRazao;
	        this.fantasia = fantasia;
	        this.cpfCgc = cpfCgc;
	        this.rgIe = rgIe;
	        this.telefone = telefone;
	        this.email = email;
	        this.dataNasc = dataNasc;
	        this.sexo = sexo;
	        this.tipo = tipo;
	        this.status = status;
	    }
	    
	    //GETTERS
	    public int getId() { return id; }
	    public String getNomeRazao() { return nomeRazao; }
	    public String getFantasia() { return fantasia;}
	    public String getCpfCgc() { return cpfCgc; }
	    public String getRgIe() { return rgIe; }
	    public String getTelefone() { return telefone; }
	    public String getEmail() { return email; }
	    public String getDataNasc() { return dataNasc; }
	    public String getSexo() { return sexo; }
	    public String getStatus() { return status; }
	    public String getTipo() { return tipo; }
	    //SETTERS
	    public void setId(int id) { this.id = id; }
	    public void setNomeRazao(String nomeRazao) { this.nomeRazao = nomeRazao; }
	    public void setFantasia(String fantasia) { this.fantasia = fantasia; }
	    public void setCpfCgc(String cpfCgc) { this.cpfCgc = cpfCgc; }
	    public void setRgIe(String rgIe) { this.rgIe = rgIe; }  
	    public void setTelefone(String telefone) { this.telefone = telefone;}
	    public void setEmail(String email) { this.email = email; }
		public void setDataNasc(String dataNasc) { this.dataNasc = dataNasc; }
		public void setSexo(String sexo) { this.sexo = sexo;} 
	    public void setTipo(String tipo) { this.tipo = tipo; }
	    public void setStatus(String status) { this.status = status; }

	    public void Salvar() {
	        try (Connection conn = conexao.getConnection();
	             PreparedStatement insert = conn.prepareStatement(
	                     "INSERT INTO cliente (nome_razao, fantasia, cpf_cgc, "
	                     + "rg_ie, telefone, email, data_nasc, sexo, tipo, status) "
	                     + "VALUES (?,?,?,?,?,?,?,?,?,?)");
	             PreparedStatement update = conn.prepareStatement(
	                     "UPDATE cliente SET nome_razao=?, fantasia=?, cpf_cgc=?, "
	                     + "rg_ie=?, telefone=?, email=?, data_nasc=?, sexo=?, "
	                     + "tipo=?, status=? WHERE id=?")) {

	            if (this.id > 0) {
	                update.setString(1, this.nomeRazao);
	                update.setString(2, this.fantasia);
	                update.setString(3, this.cpfCgc);
	                update.setString(4, this.rgIe);
	                update.setString(5, this.telefone);
	                update.setString(6, this.email);
	                update.setString(7, this.dataNasc);
	                update.setString(8, this.sexo.equals("Masculino") ? "0":"1");
	                update.setString(9, this.tipo.equals("Pessoa Física") ? "0":"1");
	                update.setString(10, this.status.equals("Ativo") ? "1":"0");
	                update.setInt(11, this.id);
	                update.executeUpdate();

	                Alert msg = new Alert(Alert.AlertType.INFORMATION);
	                msg.setContentText("Cliente Alterado!");
	                msg.showAndWait();
	            } else {
	                insert.setString(1, this.nomeRazao);
	                insert.setString(2, this.fantasia);
	                insert.setString(3, this.cpfCgc);
	                insert.setString(4, this.rgIe);
	                insert.setString(5, this.telefone);
	                insert.setString(6, this.email);
	                insert.setString(7, this.dataNasc);
	                insert.setString(8, this.sexo.equals("Masculino") ? "0":"1");
	                insert.setString(9, this.tipo.equals("Pessoa Física") ? "0":"1");
	                insert.setString(10, this.status.equals("Ativo") ? "1":"0");
	                insert.executeUpdate();

	                Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
	                msg.setContentText("Cliente Cadastrado!");
	                msg.showAndWait();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // Método Buscar
	    public void Buscar(String valor) {
	        try (Connection conn = conexao.getConnection();
	             PreparedStatement consulta = conn.prepareStatement(
	            		 "SELECT id, IFNULL(nome_razao, 'Sem Nome/Razão') AS nome_razao, IFNULL(fantasia, 'Sem Fantasia') AS fantasia, "
	     	             		+ "IFNULL(cpf_cgc, 'Sem CPF/CNPJ') AS cpf_cgc, IFNULL(rg_ie, 'Sem RG/IE') AS rg_ie, IFNULL(telefone, 'Sem Telefone') AS telefone, "
	     	             		+ "IFNULL(email, 'Sem Email') AS email, DATE_FORMAT(data_nasc, '%d/%m/%Y') AS data_nasc,  "
	     	             		+ "CASE   WHEN sexo = 0 THEN 'Masculino' WHEN sexo = 1 THEN 'Feminino' ELSE 'Masculino' END AS sexo, "
	     	             		+ "CASE WHEN tipo = 0 THEN 'Pessoa Física' WHEN tipo = 1 THEN 'Pessoa Jurídica'  ELSE 'Pessoa Física' END AS tipo, "
	     	             		+ "CASE   WHEN status = 0 THEN 'Inativo' WHEN status = 1 THEN 'Ativo' ELSE 'Inativo' END AS status FROM cliente WHERE nome_razao LIKE ? OR fantasia LIKE ? OR cpf_cgc LIKE ? OR email like ?")) {

	            consulta.setString(1, "%" + valor + "%");
	            consulta.setString(2, "%" + valor + "%");
	            consulta.setString(3, "%" + valor + "%");
	            consulta.setString(4, "%" + valor + "%");

	            ResultSet rs = consulta.executeQuery();
	            if (rs.next()) {
	                this.id = rs.getInt("id");
	                this.nomeRazao = rs.getString("nome_razao");
	                this.fantasia = rs.getString("fantasia");
	                this.cpfCgc = rs.getString("cpf_cgc");
	                this.rgIe = rs.getString("rg_ie");
	                this.telefone = rs.getString("telefone");
	                this.email = rs.getString("email");
	                this.dataNasc = rs.getString("data_nasc");
	                this.sexo = rs.getString("sexo");
	                this.tipo = rs.getString("tipo");
	                this.status = rs.getString("status");
	            } else {
	                Alert msg = new Alert(Alert.AlertType.ERROR);
	                msg.setContentText("Cliente não encontrado!");
	                msg.showAndWait();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // Método Excluir
	    public void Excluir() {
	        try (Connection conn = conexao.getConnection();
	             PreparedStatement consulta = conn.prepareStatement("DELETE FROM cliente WHERE id=?")) {

	            if (this.id > 0) {
	                consulta.setInt(1, this.id);
	                consulta.executeUpdate();

	                Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
	                msg.setContentText("Cliente Excluído!");
	                msg.showAndWait();
	            } else {
	                Alert msg = new Alert(Alert.AlertType.ERROR);
	                msg.setContentText("Cliente não localizado!");
	                msg.showAndWait();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // Método ListarUsuarios
	    public List<ClienteModel> ListarUsuarios(String valor) {
	        List<ClienteModel> clientes = new ArrayList<>();
	        try (Connection conn = conexao.getConnection();
	             PreparedStatement consulta = conn.prepareStatement("SELECT id, IFNULL(nome_razao, 'Sem Nome/Razão') AS nome_razao, IFNULL(fantasia, 'Sem Fantasia') AS fantasia, "
	             		+ "IFNULL(cpf_cgc, 'Sem CPF/CNPJ') AS cpf_cgc, IFNULL(rg_ie, 'Sem RG/IE') AS rg_ie, IFNULL(telefone, 'Sem Telefone') AS telefone, "
	             		+ "IFNULL(email, 'Sem Email') AS email, DATE_FORMAT(data_nasc, '%d/%m/%Y') AS data_nasc,  "
	             		+ "CASE   WHEN sexo = 0 THEN 'Masculino' WHEN sexo = 1 THEN 'Feminino' ELSE 'Masculino' END AS sexo, "
	             		+ "CASE WHEN tipo = 0 THEN 'Pessoa Física' WHEN tipo = 1 THEN 'Pessoa Jurídica'  ELSE 'Pessoa Física' END AS tipo, "
	             		+ "CASE   WHEN status = 0 THEN 'Inativo' WHEN status = 1 THEN 'Ativo' ELSE 'Inativo' END AS status FROM cliente");
	             PreparedStatement consultaWhere = conn.prepareStatement(
	                     "SELECT     id, IFNULL(nome_razao, 'Sem Nome/Razão') AS nome_razao, IFNULL(fantasia, 'Sem Fantasia') AS fantasia, "
	                     + "    IFNULL(cpf_cgc, 'Sem CPF/CNPJ') AS cpf_cgc, IFNULL(rg_ie, 'Sem RG/IE') AS rg_ie, IFNULL(telefone, 'Sem Telefone') AS telefone, "
	                     + "    IFNULL(email, 'Sem Email') AS email, DATE_FORMAT(data_nasc, '%d/%m/%Y') AS data_nasc, "
	                     + "    CASE   WHEN sexo = 0 THEN 'Masculino' WHEN sexo = 1 THEN 'Feminino' ELSE 'Masculino' END AS sexo,\r\n"
	                     + "    CASE WHEN tipo = 0 THEN 'Pessoa Física' WHEN tipo = 1 THEN 'Pessoa Jurídica'  ELSE 'Pessoa Física' END AS tipo, "
	                     + "    CASE   WHEN status = 0 THEN 'Inativo' WHEN status = 1 THEN 'Ativo' ELSE 'Inativo' END AS status FROM cliente WHERE nome_razao LIKE ? OR fantasia LIKE ? OR cpf_cgc LIKE ?")) {

	            ResultSet rs;
	            if (valor == null) {
	                rs = consulta.executeQuery();
	            } else {
	                consultaWhere.setString(1, "%" + valor + "%");
	                consultaWhere.setString(2, "%" + valor + "%");
	                consultaWhere.setString(3, "%" + valor + "%");
	                rs = consultaWhere.executeQuery();
	            }

	            while (rs.next()) {
	                ClienteModel c = new ClienteModel(
	                        rs.getInt("id"),
	                        rs.getString("nome_razao"),
	                        rs.getString("fantasia"),
	                        rs.getString("cpf_cgc"),
	                        rs.getString("rg_ie"),
	                        rs.getString("telefone"),
	                        rs.getString("email"),
	                        rs.getString("data_nasc"),
	                        rs.getString("sexo"),
	                        rs.getString("tipo"),
	                        rs.getString("status")
	                );
	                clientes.add(c);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return clientes;
	    }
	    
}
