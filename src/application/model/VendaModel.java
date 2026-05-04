package application.model;

public class VendaModel {
	private int Id;
	private int codCliente;
	private String nomeCliente;
	private int codVendedor;
	private String nomeVendedor;
	private String statusVenda;
	private String dataVenda;
	
	// Construtor
    public VendaModel(int Id, int codCliente, String nomeCliente, int codVendedor, String nomeVendedor, String statusVenda, String dataVenda) {
        this.Id = Id;
        this.codCliente = codCliente;
        this.nomeCliente = nomeCliente;
        this.codVendedor = codVendedor;
        this.nomeVendedor = nomeVendedor;
        this.statusVenda = statusVenda;
        this.dataVenda = dataVenda;
    }
	
	// Getters
    public int getId() { return Id; }
    public int getCodCliente() { return codCliente; }
    public String getNomeCliente() { return nomeCliente; }
    public int getCodVendedor() { return codVendedor; }
    public String getNomeVendedor() { return nomeVendedor; }
    public String getStatusVenda() { return statusVenda; }
    public String getDataVenda() { return dataVenda; }

    // Setters
    public void setId(int id) { this.Id = id; }
    public void setCodCliente(int codCliente) { this.codCliente = codCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public void setCodVendedor(int codVendedor) { this.codVendedor = codVendedor; }
    public void setNomeVendedor(String nomeVendedor) { this.nomeVendedor = nomeVendedor; }
    public void setStatusVenda(String statusVenda) { this.statusVenda = statusVenda; }
    public void setDataVenda(String dataVenda) { this.dataVenda = dataVenda;} 

}
