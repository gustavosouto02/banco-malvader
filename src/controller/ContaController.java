package controller;

import DAO.ContaDAO;
import model.Conta;

public class ContaController {
    private ContaDAO contaDAO;

    public ContaController() {
        this.contaDAO = new ContaDAO(); // Instância para interagir com o banco
    }

    public void abrirConta(Conta novaConta) {
        if (novaConta == null) {
            System.out.println("Conta inválida. Não foi possível abrir a conta.");
            return;
        }

        if (novaConta.getSaldo() < 0) {
            System.out.println("O saldo inicial não pode ser negativo.");
            return;
        }

        contaDAO.salvarConta(novaConta);
        System.out.println("Conta aberta com sucesso. Número da conta: " + novaConta.getNumeroConta());
    }

    public void encerrarConta(int contaId) {
        // Verifica se a conta existe antes de tentar excluí-la
        Conta conta = contaDAO.buscarContaPorNumero(String.valueOf(contaId));
        
        if (conta == null) {
            System.out.println("Conta não encontrada. Não é possível encerrar a conta.");
            return;
        }

        // Validação de possíveis pendências antes de encerrar a conta (exemplo de saldo devedor)
        if (conta.getSaldo() < 0) {
            System.out.println("A conta possui saldo devedor. Não é possível encerrá-la.");
            return;
        }

        contaDAO.deletarConta(contaId);
        System.out.println("Conta encerrada com sucesso. Número da conta: " + contaId);
    }
}
