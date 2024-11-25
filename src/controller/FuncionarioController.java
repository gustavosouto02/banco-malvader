package controller;

import java.sql.SQLException;

import DAO.FuncionarioDAO;
import model.Funcionario;

public class FuncionarioController {

    private final FuncionarioDAO funcionarioDAO;
    // Construtor que recebe BancoController e FuncionarioDAO
    public FuncionarioController(BancoController bancoController, FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

    // Método para buscar funcionário por ID
    public Funcionario buscarFuncionarioPorId(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser um número positivo.");
        }

        Funcionario funcionario = funcionarioDAO.buscarFuncionarioPorId(id);
        if (funcionario == null) {
            System.out.println("Nenhum funcionário encontrado para o ID: " + id);
        }
        return funcionario;
    }

    // Método para cadastrar um novo funcionário
    public void cadastrarFuncionario(Funcionario funcionario) throws SQLException {
        if (funcionario == null) {
            System.out.println("Funcionário não pode ser nulo.");
            return;
        }

        if (funcionario.getNome().isEmpty()) {
            System.out.println("Nome do funcionário não pode ser vazio.");
            return;
        }

        try {
            funcionarioDAO.salvarFuncionario(funcionario);
            System.out.println("Funcionário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro inesperado ao cadastrar funcionário: " + e.getMessage());
        }
    }

    // Método para alterar os dados de um funcionário
    public void alterarFuncionario(Funcionario funcionario) throws SQLException {
        if (funcionario == null || funcionario.getId() <= 0) {
            System.out.println("Dados inválidos para atualização de funcionário.");
            return;
        }

        try {
            funcionarioDAO.atualizarFuncionario(funcionario);
            System.out.println("Funcionário atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro inesperado ao atualizar funcionário: " + e.getMessage());
        }
    }
}
