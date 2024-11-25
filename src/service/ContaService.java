package service;

import java.sql.SQLException;
import java.util.logging.Logger;
import DAO.ContaDAO;
import model.Conta;
import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;

public class ContaService {
    private static final Logger logger = Logger.getLogger(ContaService.class.getName());
    private ContaDAO contaDAO;

    public ContaService() {
        this.contaDAO = new ContaDAO();
    }

    public void abrirConta(Conta novaConta) {
        if (novaConta == null) {
            throw new IllegalArgumentException("Conta inválida. Não foi possível abrir a conta.");
        }

        if (novaConta.getSaldo() < 0) {
            throw new IllegalArgumentException("O saldo inicial não pode ser negativo.");
        }

        try {
            contaDAO.salvarConta(novaConta);
            logger.info("Conta aberta com sucesso para o cliente: " + novaConta.getCliente().getNome());
        } catch (SQLException e) {
            logger.severe("Erro ao abrir conta: " + e.getMessage());
            throw new RuntimeException("Erro ao abrir conta.", e);
        }
    }


    public void encerrarConta(int contaId) throws ValorInvalidoException {
        try {
            Conta conta = contaDAO.buscarContaPorId(contaId);

            if (conta == null) {
                throw new IllegalArgumentException("Conta não encontrada.");
            }

            if (conta.getSaldo() < 0) {
                throw new IllegalStateException("A conta possui saldo devedor. Não é possível encerrá-la.");
            }

            contaDAO.deletarConta(contaId);
            logger.info("Conta com número " + contaId + " encerrada com sucesso.");
        } catch (SQLException e) {
            logger.severe("Erro ao encerrar conta: " + e.getMessage());
            throw new RuntimeException("Erro ao encerrar conta.", e);
        }
    }

    public void realizarSaque(int contaId, double valor) throws SaldoInsuficienteException, ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor de saque deve ser positivo.");
        }

        try {
            Conta conta = contaDAO.buscarContaPorId(contaId);

            if (conta == null) {
                throw new IllegalArgumentException("Conta não encontrada.");
            }

            if (conta.getSaldo() < valor) {
                throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque.");
            }

            conta.sacar(valor);
            contaDAO.atualizarConta(conta);
            logger.info("Saque de " + valor + " realizado com sucesso na conta " + contaId);

        } catch (SQLException e) {
            logger.severe("Erro ao realizar saque: " + e.getMessage());
            throw new RuntimeException("Erro ao realizar saque.", e);
        }
    }

    public void realizarDeposito(int contaId, double valor) throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor de depósito deve ser positivo.");
        }

        try {
            Conta conta = contaDAO.buscarContaPorId(contaId);

            if (conta == null) {
                throw new IllegalArgumentException("Conta não encontrada.");
            }

            conta.depositar(valor);
            contaDAO.atualizarConta(conta);
            logger.info("Depósito de " + valor + " realizado com sucesso na conta " + contaId);

        } catch (SQLException e) {
            logger.severe("Erro ao realizar depósito: " + e.getMessage());
            throw new RuntimeException("Erro ao realizar depósito.", e);
        }
    }
    
    public void atualizarConta(Conta conta) {
        try {
            if (conta == null) {
                throw new IllegalArgumentException("Conta inválida para atualização.");
            }
            contaDAO.atualizarConta(conta);
            logger.info("Conta atualizada com sucesso: " + conta.getId_conta());
        } catch (SQLException e) {
            logger.severe("Erro ao atualizar conta: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar conta.", e);
        }
    }

    public Conta buscarContaPorId(int idConta) {
        try {
            return contaDAO.buscarContaPorId(idConta);
        } catch (SQLException e) {
            logger.severe("Erro ao buscar conta: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar conta.", e);
        }
    }

}
