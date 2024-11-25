package model;

import java.io.Serializable;
import java.time.LocalDate;
import exception.SaldoInsuficienteException;

public class ContaCorrente extends Conta implements Serializable {
    private static final long serialVersionUID = 1L;
    private double limite;
    private LocalDate dataVencimento;

    // Construtor da ContaCorrente
    public ContaCorrente(String numeroConta, String agencia, Cliente cliente, double saldo, double limite, LocalDate dataVencimento) {
        // Passando o id_cliente da classe Cliente para o construtor de Conta
        super(numeroConta, agencia, saldo, "Corrente", cliente.getId_cliente());
        this.limite = limite;
        this.dataVencimento = dataVencimento;
    }

    // Construtor vazio para inicialização
    public ContaCorrente() {
        super();
    }

    // Método para consultar o limite
    public double consultarLimite() {
        return limite;
    }

    // Permitir saque até o limite da conta corrente
    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor <= getSaldo()) {
            super.sacar(valor);
        } else if (valor <= getSaldo() + limite) {
            double diferenca = valor - getSaldo();
            super.sacar(getSaldo());
            limite -= diferenca;
        } else {
            throw new SaldoInsuficienteException("Saldo insuficiente, inclusive o limite.");
        }
    }

    // Redefinir o limite da conta
    public void redefinirLimite(double novoLimite) {
        if (novoLimite >= 0) {
            this.limite = novoLimite;
        } else {
            throw new IllegalArgumentException("O limite deve ser positivo.");
        }
    }

    // Getters e Setters
    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    // Representação de uma conta corrente para exibição
    @Override
    public String toString() {
        return String.format("Conta Corrente [ID: %d, Número: %s, Agência: %s, Saldo: %.2f, Limite: %.2f, Vencimento: %s]",
                getId_conta(), getNumeroConta(), getAgencia(), getSaldo(), limite, dataVencimento);
    }
}
