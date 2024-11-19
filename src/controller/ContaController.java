package controller;

import service.ContaService;
import model.Conta;
import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;

public class ContaController {
    private ContaService contaService;

    public ContaController() {
        this.contaService = new ContaService(); 
    }

    // Alterando o nome do método para refletir a abertura da conta (Cadastro)
    public void abrirConta(Conta novaConta) {
        try {
            contaService.abrirConta(novaConta);  // Usando a contaService para abrir a conta
            System.out.println("Conta aberta com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao abrir conta: " + e.getMessage());
        }
    }

    public void encerrarConta(int contaId) {
        try {
            validarContaId(contaId);
            contaService.encerrarConta(contaId);
            System.out.println("Conta encerrada com sucesso.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro ao encerrar conta: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    public void sacar(int contaId, double valor) {
        try {
            validarContaId(contaId);
            validarValor(valor);
            contaService.realizarSaque(contaId, valor);
            System.out.println("Saque realizado com sucesso.");
        } catch (SaldoInsuficienteException e) {
            System.out.println("Erro ao sacar: Saldo insuficiente.");
        } catch (ValorInvalidoException e) {
            System.out.println("Erro ao sacar: Valor inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao sacar: " + e.getMessage());
        }
    }

    public void depositar(int contaId, double valor) {
        try {
            validarContaId(contaId);
            validarValor(valor);
            contaService.realizarDeposito(contaId, valor);
            System.out.println("Depósito realizado com sucesso.");
        } catch (ValorInvalidoException e) {
            System.out.println("Erro ao depositar: Valor inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao depositar: " + e.getMessage());
        }
    }

    private void validarContaId(int contaId) {
        if (contaId <= 0) {
            throw new IllegalArgumentException("ID da conta deve ser um número positivo.");
        }
    }

    private void validarValor(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }
    }
}
