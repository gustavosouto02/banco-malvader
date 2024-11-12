package model;

public class ContaPoupanca extends Conta {
    private double taxaRendimento;

    // Construtor da ContaPoupanca
    public ContaPoupanca(int numeroConta, String agenciaConta, Cliente cliente, double saldo, double taxaRendimento) {
        super(numeroConta, agenciaConta, cliente, saldo);  // Chama o construtor da classe Conta
        if (taxaRendimento < 0 || taxaRendimento > 1) {
            throw new IllegalArgumentException("A taxa de rendimento deve ser entre 0 e 1.");
        }
        this.taxaRendimento = taxaRendimento;
    }

    // Método para calcular o rendimento da conta poupança
    public double calcularRendimento() {
        return getSaldo() * taxaRendimento;  // saldo herdado da classe Conta
    }

    // Getters e Setters
    public double getTaxaRendimento() {
        return taxaRendimento;
    }

    public void setTaxaRendimento(double taxaRendimento) {
        if (taxaRendimento < 0 || taxaRendimento > 1) {
            throw new IllegalArgumentException("A taxa de rendimento deve ser entre 0 e 1.");
        }
        this.taxaRendimento = taxaRendimento;
    }
}
