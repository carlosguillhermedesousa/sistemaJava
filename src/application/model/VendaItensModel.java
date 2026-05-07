package application.model;

public class VendaItensModel {
    
    private int id; // ID do registro no banco (se necessário)
    private int item; // Sequencial do item na venda
    private int idProduto;
    private String codBarras;
    private String nomeProduto;
    private int qtdItem;
    private String valorUn;
    private String valorTotalItem;

    // Construtor Vazio
    public VendaItensModel() {
    }

    // Construtor Cheio
    public VendaItensModel(int id, int item, int idProduto, String codBarras, String nomeProduto, int qtdItem, String valorUn, String valorTotalItem) {
        this.id = id;
        this.item = item;
        this.idProduto = idProduto;
        this.codBarras = codBarras;
        this.nomeProduto = nomeProduto;
        this.qtdItem = qtdItem;
        this.valorUn = valorUn;
        this.valorTotalItem = valorTotalItem;
    }

    // Getters (Importantes para o PropertyValueFactory do JavaFX)
    public int getId() { return id; }
    public int getItem() { return item; }
    public int getIdProduto() { return idProduto; }
    public String getCodBarras() { return codBarras; }
    public String getNomeProduto() { return nomeProduto; }
    public int getQtdItem() { return qtdItem; }
    public String getValorUn() { return valorUn; }
    public String getValorTotalItem() { return valorTotalItem; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setItem(int item) { this.item = item; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }
    public void setCodBarras(String codBarras) { this.codBarras = codBarras; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public void setQtdItem(int qtdItem) { this.qtdItem = qtdItem; }
    public void setValorUn(String valorUn) { this.valorUn = valorUn; }
    public void setValorTotalItem(String valorTotalItem) { this.valorTotalItem = valorTotalItem; }
}