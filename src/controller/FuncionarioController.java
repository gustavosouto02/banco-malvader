package controller;

import DAO.FuncionarioDAO;
import model.Funcionario;

public class FuncionarioController {
    private FuncionarioDAO funcionarioDAO;

    public FuncionarioController(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

    public FuncionarioController() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    public Funcionario buscarFuncionarioPorId(int id) {
        validarId(id);
        Funcionario funcionario = funcionarioDAO.buscarFuncionarioPorId(id);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado para o ID: " + id);
        }
        return funcionario;
    }
    
    public void cadastrarFuncionario(Funcionario funcionario) {
        if (funcionario == null || funcionario.getNome() == null || funcionario.getNome().isEmpty()) {
            System.out.println("Dados inválidos para cadastro de funcionário.");
            return;
        }

        try {
            funcionarioDAO.salvarFuncionario(funcionario);
            System.out.println("Funcionário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    public void alterarFuncionario(Funcionario funcionario) {
        if (funcionario == null || funcionario.getId() <= 0) {
            System.out.println("Dados inválidos para atualização de funcionário.");
            return;
        }

        try {
            funcionarioDAO.atualizarFuncionario(funcionario);
            System.out.println("Funcionário atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar funcionário: " + e.getMessage());
        }
    }

    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser um número positivo.");
        }
    }
}
