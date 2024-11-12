package model;

import java.time.LocalDate;
import exception.SaldoInsuficienteException;

public class ContaCorrente extends Conta {
    private double limite;
    private LocalDate dataVencimento;

    // Construtor da ContaCorrente
    public ContaCorrente(int numeroConta, String agenciaConta, Cliente cliente, double saldo, double limite, LocalDate dataVencimento) {
        super(numeroConta, agenciaConta, cliente, saldo);  // Chama o construtor da classe pai Conta
        this.limite = limite;
        this.dataVencimento = dataVencimento;
    }

    // Método para consultar o limite
    public double consultarLimite() {
        return limite;
    }

    //permitir saque até o limite da conta corrente
    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor <= getSaldo() + limite) {  // Permite sacar saldo + limite
            super.sacar(valor);  // Chama o método sacar da classe pai (Conta)
        } else {
            throw new SaldoInsuficienteException("Saldo insuficiente, inclusive o limite.");
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
}
