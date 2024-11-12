package model;

import exception.SaldoInsuficienteException;

public class Conta {
    private int numeroConta;
    private String agenciaConta;
    private double saldo;
    private Cliente cliente;

    // Construtor da classe Conta
    public Conta(int numeroConta, String agenciaConta, Cliente cliente, double saldo) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        this.numeroConta = numeroConta;
        this.agenciaConta = agenciaConta;
        this.saldo = saldo; 
        this.cliente = cliente;
    }

    // Métodos para depositar valores na conta
    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        } else {
            System.out.println("Valor de depósito inválido.");
        }
    }

    // Método para sacar valores da conta com tratamento para saldo insuficiente
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

    public String getAgenciaConta() {
        return agenciaConta;
    }

    public void setAgenciaConta(String agenciaConta) {
        this.agenciaConta = agenciaConta;
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

    // Representação da conta como string
    @Override
    public String toString() {
        return "Conta [numeroConta=" + numeroConta + ", agenciaConta=" + agenciaConta + ", saldo=" + saldo + ", cliente=" + cliente.getNome() + "]";
    }
}
