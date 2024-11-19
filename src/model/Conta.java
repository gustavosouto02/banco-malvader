package model;

import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;

import java.io.Serializable;

public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;

    private int numeroConta;
    private String numeroAgencia;
    private double saldo;
    private Cliente cliente;

    // Construtor da classe Conta
    public Conta(int numeroConta, String numeroAgencia, Cliente cliente, double saldo) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (saldo < 0) {
            throw new IllegalArgumentException("Saldo inicial não pode ser negativo.");
        }
        this.numeroConta = numeroConta;
        this.numeroAgencia = numeroAgencia;
        this.saldo = saldo;
        this.cliente = cliente;
    }


    // Método para depositar valor na conta
    public void depositar(double valor) throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("Valor de depósito deve ser positivo.");
        }
        saldo += valor;
    }

    // Método para sacar valor da conta
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor <= saldo) {
            saldo -= valor;
        } else {
            throw new SaldoInsuficienteException("Saldo insuficiente para o saque.");
        }
    }

    // Método para consultar o saldo da conta
    public double consultarSaldo() {
        return saldo;
    }

    // Getters e Setters
    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNumeroAgencia() {
        return numeroAgencia;
    }

    public void setNumeroAgencia(String numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Sobrescrevendo o método toString para exibir informações da conta
    @Override
    public String toString() {
        return "Conta [numeroConta=" + numeroConta + ", numeroAgencia=" + numeroAgencia + 
               ", saldo=" + saldo + ", cliente=" + (cliente != null ? cliente.getNome() : "Sem nome") + "]";
    }
}
