package model;

public class ContaPoupanca extends Conta {
    private double taxaRendimento;

    public double calcularRendimento(){
        return saldo * taxaRendimento;
    }
}