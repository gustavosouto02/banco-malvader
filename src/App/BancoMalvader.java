package App;

import controller.ClienteController;
import controller.ContaController;
import controller.FuncionarioController;
import View.MenuClienteView;
import View.MenuFuncionarioView;
import java.util.Scanner;

public class BancoMalvader {
    private String nome = "Banco Malvader";
    private Scanner scanner = new Scanner(System.in);
    private ContaController contaController = new ContaController();
    private ClienteController clienteController = new ClienteController();
    private FuncionarioController funcionarioController = new FuncionarioController();

    public static void main(String[] args) {
        BancoMalvader banco = new BancoMalvader();
        banco.iniciarSistema();
    }

    public void iniciarSistema() {
        System.out.println("Bem-vindo ao " + nome);
        while (true) {
            exibirMenuPrincipal();
            int opcao = getOpcao();

            switch (opcao) {
                case 1:
                    autenticarFuncionario();
                    break;
                case 2:
                    autenticarCliente();
                    break;
                case 3:
                    System.out.println("Saindo do Sistema...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public void exibirMenuPrincipal() {
        System.out.println("\nMenu Principal:");
        System.out.println("1. Funcionário");
        System.out.println("2. Cliente");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public void autenticarFuncionario() {
        System.out.print("Digite a senha do funcionário: ");
        String senha = scanner.nextLine();

        if ("funcionario123".equals(senha)) {
            new MenuFuncionarioView(funcionarioController).exibirMenu();
        } else {
            System.out.println("Senha incorreta.");
        }
    }

    public void autenticarCliente() {
        System.out.print("Digite a senha do cliente: ");
        String senha = scanner.nextLine();

        if ("cliente123".equals(senha)) {
            new MenuClienteView(clienteController).exibirMenu();
        } else {
            System.out.println("Senha incorreta.");
        }
    }

    public int getOpcao() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                scanner.nextLine();
            }
        }
    }
}
