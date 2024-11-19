package App;

import javax.swing.*;
import controller.ClienteController;
import controller.ContaController;
import controller.FuncionarioController;
import View.MenuClienteView;
import View.MenuFuncionarioView;

import java.sql.SQLException;

public class BancoMalvader {

    private static final String SENHA_FUNCIONARIO = "funcionario123";
    private static final String SENHA_CLIENTE = "cliente123";
    private String nome = "Banco Malvader";
    private FuncionarioController funcionarioController;

    // Construtor com injeção de dependência
    public BancoMalvader(ContaController contaController, ClienteController clienteController, FuncionarioController funcionarioController) {
        this.funcionarioController = funcionarioController;
    }

    public static void main(String[] args) throws SQLException {
        // Injeção de dependências
        BancoMalvader banco = new BancoMalvader(
            new ContaController(),
            new ClienteController(),
            new FuncionarioController()
        );
        banco.iniciarSistema();
    }

    // Responsável por iniciar o sistema
    public void iniciarSistema() {
        JFrame frame = new JFrame(nome);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Menu principal em uma janela gráfica
        String[] options = {"Funcionário", "Cliente", "Sair"};
        int choice = JOptionPane.showOptionDialog(frame, "Escolha uma opção", "Menu Principal", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                autenticarFuncionario();
                break;
            case 1:
                autenticarCliente();
                break;
            case 2:
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    // Autentica um funcionário
    public void autenticarFuncionario() {
        String senha = JOptionPane.showInputDialog("Digite a senha do funcionário:");

        if (SENHA_FUNCIONARIO.equals(senha)) {
            new MenuFuncionarioView(funcionarioController).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Senha incorreta.");
        }
    }

    // Autentica um cliente
    public void autenticarCliente() {
        String senha = JOptionPane.showInputDialog("Digite a senha do cliente:");

        if (SENHA_CLIENTE.equals(senha)) {
            new MenuClienteView().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Senha incorreta.");
        }
    }
}
