package model;

import exception.SaldoInsuficienteException;

import java.io.Serializable;

import DAO.ClienteDAO;

public class Conta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id_conta; // id_conta gerado pelo banco
    private String numeroConta;
    private String agencia;
    private double saldo;
    private String tipoConta;
    private int id_cliente; // Relação com o cliente
    private ContaCorrente contaCorrente;  // Para armazenar a conta corrente
    private ContaPoupanca contaPoupanca;  // Para armazenar a conta poupança

    // Construtor com todos os parâmetros
    public Conta(String numeroConta, String agencia, double saldo, String tipoConta, int id_cliente) {
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.saldo = saldo;
        this.tipoConta = tipoConta;
        this.id_cliente = id_cliente;
    }

    // Construtor vazio para inicialização
    public Conta() {
    }

    // Getter e Setter para id_conta
    public int getId_conta() {
        return id_conta;
    }

    public void setId_conta(int id_conta) {
        this.id_conta = id_conta;
    }

    // Getter e Setter para numeroConta
    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    // Getter e Setter para agencia
    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    // Getter e Setter para saldo
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // Getter e Setter para tipoConta
    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    // Getter e Setter para id_cliente (relacionamento com Cliente)
    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    // Método para buscar o cliente associado à conta
    public Cliente getCliente() {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.buscarClientePorId(this.id_cliente);
    }

    // Método para exibir informações gerais da conta
    @Override
    public String toString() {
        String tipoDetalhado = tipoConta;
        if (contaCorrente != null) {
            tipoDetalhado = "Conta Corrente (Limite: " + contaCorrente.getLimite() + ")";
        } else if (contaPoupanca != null) {
            tipoDetalhado = "Conta Poupança (Taxa de Rendimento: " + contaPoupanca.getTaxaRendimento() + "%)";
        }
        return String.format("Conta [ID: %d, Número: %s, Agência: %s, Saldo: %.2f, Tipo: %s]",
                id_conta, numeroConta, agencia, saldo, tipoDetalhado);
    }


    public void sacar(double valor) throws SaldoInsuficienteException {
        // Verifica se o valor solicitado é maior que o saldo disponível
        if (valor > saldo) {
            // Para contas correntes, verifica se o limite permite o saque
            if (contaCorrente != null && (saldo + contaCorrente.getLimite()) >= valor) {
                saldo -= valor;  // Permite o saque se o limite da conta corrente for suficiente
            } else {
                throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque.");
            }
        } else {
            saldo -= valor; // Decrementa o saldo da conta com o valor do saque
        }
    }

    public void depositar(double valor) {
        // Verifica se o valor de depósito é válido (não negativo)
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
        }
        saldo += valor; // Incrementa o saldo com o valor do depósito
    }

	public double consultarSaldo() {
		return saldo;
	}

	public void setContaCorrente(ContaCorrente contaCorrente) {
	    if (contaCorrente != null && contaCorrente.getLimite() >= 0) {
	        this.contaCorrente = contaCorrente;
	    } else {
	        throw new IllegalArgumentException("Conta Corrente inválida ou limite negativo.");
	    }
	}

	public void setContaPoupanca(ContaPoupanca contaPoupanca) {
	    if (contaPoupanca != null && contaPoupanca.getTaxaRendimento() >= 0) {
	        this.contaPoupanca = contaPoupanca;
	    } else {
	        throw new IllegalArgumentException("Conta Poupança inválida ou taxa de rendimento negativa.");
	    }
	}


}