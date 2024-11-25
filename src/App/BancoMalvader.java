package App;

import javax.swing.*;
import controller.ClienteController;
import controller.ContaController;
import controller.FuncionarioController;
import service.Autenticacao;
import View.LoginView;
import View.MenuClienteView;
import View.MenuFuncionarioView;

import java.sql.SQLException;

public class BancoMalvader {

    private FuncionarioController funcionarioController;
    private Autenticacao autenticacaoService;

    public BancoMalvader(ContaController contaController, ClienteController clienteController, FuncionarioController funcionarioController, Autenticacao autenticacaoService) {
        this.funcionarioController = funcionarioController;
        this.autenticacaoService = autenticacaoService;
    }

    public static void main(String[] args) throws SQLException {
        // Criando o BancoMalvader com a injeção de dependências correta
        BancoMalvader banco = new BancoMalvader(
            new ContaController(),
            new ClienteController(),
            new FuncionarioController(null, null),
            new Autenticacao() // O construtor de Autenticacao agora utiliza a instância de UsuarioDAO sem necessidade de passar null
        );
        banco.iniciarSistema();
    }

    public void iniciarSistema() throws SQLException {
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
    }


    public void autenticarUsuario(String tipoUsuario) throws SQLException {
        try {
            String cpf = JOptionPane.showInputDialog("Digite o CPF do " + tipoUsuario + ":");
            String senha = JOptionPane.showInputDialog("Digite a senha do " + tipoUsuario + ":");

            if (autenticacaoService.autenticarUsuario(cpf, senha)) {
                if (tipoUsuario.equals("funcionário")) {
                    new MenuFuncionarioView(null, funcionarioController).setVisible(true);
                } else {
                    new MenuClienteView().setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, tipoUsuario + " não encontrado ou senha incorreta.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "CPF inválido. Verifique o formato.");
        }
    }
}
