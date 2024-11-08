package model;

public class Conta {
	 	protected int numeroConta;
	    protected String agenciaConta;
	    protected double saldo;
	    protected Cliente cliente;

	    public void depositar(double valor){
	        saldo += valor; //atualiza o saldo
	    }

	    public boolean sacar(double valor){
	        if (valor <= saldo){
	            saldo -= valor;
	            return true;
	        }
	        return false;
	    }

	    public double consultarSaldo(){
	        return saldo;
	    }
}
