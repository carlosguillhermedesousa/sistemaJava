package application.model;

public class VendaFormaModel {

    private int forma; // ID ou código da Forma de Pagamento
    private String valorBruto;
    private String desconto;
    private String valorTotal;

    // Construtor Vazio
    public VendaFormaModel() {
    }

    // Construtor Cheio
    public VendaFormaModel(int forma, String valorBruto, String desconto, String valorTotal) {
        this.forma = forma;
        this.valorBruto = valorBruto;
        this.desconto = desconto;
        this.valorTotal = valorTotal;
    }

    // Getters (Importantes para o PropertyValueFactory do JavaFX)
    public int getForma() { return forma; }
    public String getValorBruto() { return valorBruto; }
    public String getDesconto() { return desconto; }
    public String getValorTotal() { return valorTotal; }

    // Setters
    public void setForma(int forma) { this.forma = forma; }
    public void setValorBruto(String valorBruto) { this.valorBruto = valorBruto; }
    public void setDesconto(String desconto) { this.desconto = desconto; }
    public void setValorTotal(String valorTotal) { this.valorTotal = valorTotal; }
}