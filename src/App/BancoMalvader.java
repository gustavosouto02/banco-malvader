package App;

import java.util.Scanner;

public class BancoMalvader {
	private String nome = "Banco Malvader";
	private Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		BancoMalvader banco = new BancoMalvader();
		banco.inicarSistema();
	}
	
	public void inicarSistema(){
		System.out.println("Bem vindo ao " + nome);
		while (true) {
			exibirMenuPrincipal();
			int opcao = scanner.nextInt();
			scanner.nextLine(); //limpar o buffer
			
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
					System.out.println("Opcao invalida. Tente novamente.");
			}
		}
	}
	
	public void exibirMenuPrincipal() {
		System.out.println("\nMenu Principal:");
        System.out.println("1. Funcionario");
        System.out.println("2. Cliente");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opcao: ");
	}
	
	public void autenticarFuncionario() {
		System.out.println("Digite a senha do funcionario: ");
		String senha = scanner.nextLine();
		
		if("funcionario123".equals(senha)) {
			menuFuncionario();
		}else {
			 System.out.println("Senha incorreta.");
		}
	}
	
	public void autenticarCliente() {
		System.out.println("Digite a senha do cliente: ");
		String senha = scanner.nextLine();
		
		if("cliente123".equals(senha)) {
			 menuCliente();
		}else {
            System.out.println("Senha incorreta.");
        }
	}
	
	public void menuFuncionario() {
		while (true) {
			System.out.println("\nMenu Funcionário:");
            System.out.println("1. Abertura de Conta");
            System.out.println("2. Encerramento de Conta");
            System.out.println("3. Consulta de Dados");
            System.out.println("4. Alteração de Dados");
            System.out.println("5. Cadastro de Funcionários");
            System.out.println("6. Geração de Relatórios");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer
		}
	}
}
