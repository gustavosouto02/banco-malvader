package model;

import java.io.Serializable;

public class ContaPoupanca extends Conta implements Serializable {
    private static final long serialVersionUID = 1L;
    private double taxaRendimento;

    // Construtor da ContaPoupanca
    public ContaPoupanca(String numeroConta, String agencia, Cliente cliente, double saldo, double taxaRendimento) {
        // Chamando o construtor da classe pai Conta e passando o id_cliente do Cliente
        super(numeroConta, agencia, saldo, "Poupança", cliente.getId_cliente());
        if (taxaRendimento < 0 || taxaRendimento > 1) {
            throw new IllegalArgumentException("A taxa de rendimento deve ser entre 0 e 1.");
        }
        this.taxaRendimento = taxaRendimento;
    }

    // Construtor vazio
    public ContaPoupanca() {
        super();
    }

    // Método para calcular o rendimento da conta poupança
    public double calcularRendimento() {
        return getSaldo() * taxaRendimento; // saldo herdado da classe Conta
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

    // Representação de uma conta poupança para exibição
    @Override
    public String toString() {
        return String.format("Conta Poupança [ID: %d, Número: %s, Agência: %s, Saldo: %.2f, Taxa de Rendimento: %.2f]",
                getId_conta(), getNumeroConta(), getAgencia(), getSaldo(), taxaRendimento);
    }
}
