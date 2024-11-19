package controller;

import model.Conta;
import model.Funcionario;
import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;
import util.DataManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.ConnectionFactory;

public class BancoController {

    private List<Conta> contas;
    private List<Funcionario> funcionarios;

    public BancoController() {
        this.contas = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        carregarDados();  
    }

    public void carregarDados() {
        contas = DataManager.carregarContas("contas.dat");
        funcionarios = DataManager.carregarFuncionarios("funcionarios.dat");
        System.out.println("Dados carregados com sucesso!");
    }

    private void salvarDados() {
        DataManager.salvarContas(contas, "contas.dat");
        DataManager.salvarFuncionarios(funcionarios, "funcionarios.dat");
        System.out.println("Dados salvos com sucesso!");
    }

    // Métodos do menu do funcionário
    public void abrirConta(Conta novaConta) {
        contas.add(novaConta);
        salvarDados();  
        System.out.println("Conta aberta com sucesso.");
    }

    public void encerrarConta(int numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta() == numeroConta) {
                contas.remove(conta);
                salvarDados();  
                System.out.println("Conta encerrada com sucesso.");
                return;
            }
        }
        System.out.println("Conta não encontrada.");
    }

    public void consultarDados() {
        // Lógica para consultar dados de contas ou funcionários
        System.out.println("Consultando dados de contas:");
        for (Conta conta : contas) {
            System.out.println(conta);
        }
        System.out.println("Consultando dados de funcionários:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario);
        }
    }

    public void alterarDados(int numeroConta, String novoDado) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta() == numeroConta) {
                // Exemplo de alteração: modificar o nome do cliente
                conta.getCliente().setNome(novoDado); 
                salvarDados();  
                System.out.println("Dados alterados com sucesso.");
                return;
            }
        }
        System.out.println("Conta não encontrada.");
    }

    public void cadastrarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
        salvarDados();  
        System.out.println("Funcionário cadastrado com sucesso.");
    }

    public void gerarRelatorios() {
        // Lógica para gerar relatórios
        System.out.println("Gerando relatórios...");
        for (Conta conta : contas) {
            System.out.println(conta);
        }
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario);
        }
    }

    // Métodos do menu do cliente
    public double consultarSaldo(int numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta() == numeroConta) {
                return conta.consultarSaldo();
            }
        }
        return -1;  
    }

    public void realizarDeposito(int numeroConta, double valor) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta() == numeroConta) {
                try {
                    conta.depositar(valor);
                    salvarDados(); 
                    System.out.println("Depósito realizado com sucesso.");
                } catch (ValorInvalidoException e) {
                    System.out.println(e.getMessage());
                }
                return;
            }
        }
        System.out.println("Conta não encontrada.");
    }

    public void realizarSaque(int numeroConta, double valor) {
        for (Conta conta : contas) {
            if (conta.getNumeroConta() == numeroConta) {
                try {
                    conta.sacar(valor);
                    salvarDados();  
                    System.out.println("Saque realizado com sucesso.");
                } catch (SaldoInsuficienteException e) {
                    System.out.println(e.getMessage());
                }
                return;
            }
        }
        System.out.println("Conta não encontrada.");
    }

    public List<Conta> getContas() {
        return contas;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public boolean isCpfCadastrado(String cpfCliente) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE cpf = ?";  // Consulta SQL
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpfCliente);  // Define o CPF na consulta

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);  
                    return count > 0;  // Se count > 0, o CPF já está cadastrado
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); 
        }
        return false; 
    }
}
