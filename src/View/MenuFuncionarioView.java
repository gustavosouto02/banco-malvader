package View;

import javax.swing.*;
import controller.BancoController;
import controller.FuncionarioController;
import model.Endereco;
import model.Funcionario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class MenuFuncionarioView extends JFrame {

    private static final long serialVersionUID = 1L;
    private BancoController bancoController;
	public MenuFuncionarioView(FuncionarioController funcionarioController) {
        setTitle("Menu Funcionário");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);


        JButton aberturaContaButton = new JButton("Abertura de Conta");
        aberturaContaButton.setBounds(10, 30, 200, 25);
        panel.add(aberturaContaButton);

        aberturaContaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abrir a tela de abertura de conta
                AberturaDeContaScreen aberturaDeContaScreen = new AberturaDeContaScreen(bancoController);
                aberturaDeContaScreen.setVisible(true);
            }
        });

        JButton encerramentoContaButton = new JButton("Encerramento de Conta");
        encerramentoContaButton.setBounds(10, 70, 200, 25);
        panel.add(encerramentoContaButton);

        encerramentoContaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numeroConta = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da conta para encerrar:"));
                bancoController.encerrarConta(numeroConta); 
            }
        });

        JButton consultaDadosButton = new JButton("Consulta de Dados");
        consultaDadosButton.setBounds(10, 110, 200, 25);
        panel.add(consultaDadosButton);

        consultaDadosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bancoController.consultarDados();
            }
        });

        JButton alteracaoDadosButton = new JButton("Alteração de Dados");
        alteracaoDadosButton.setBounds(10, 150, 200, 25);
        panel.add(alteracaoDadosButton);

        alteracaoDadosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numeroConta = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da conta para alterar:"));
                String novoNome = JOptionPane.showInputDialog("Digite o novo nome do cliente:");
                bancoController.alterarDados(numeroConta, novoNome);  
            }
        });

        JButton cadastroFuncionarioButton = new JButton("Cadastro de Funcionário");
        cadastroFuncionarioButton.setBounds(10, 190, 200, 25);
        panel.add(cadastroFuncionarioButton);

        cadastroFuncionarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Collecting funcionario details
                String nomeFuncionario = JOptionPane.showInputDialog("Digite o nome do funcionário:");
                String cpfFuncionario = JOptionPane.showInputDialog("Digite o CPF do funcionário:");
                String enderecoFuncionario = JOptionPane.showInputDialog("Digite o endereço do funcionário:");
                String cargo = JOptionPane.showInputDialog("Digite o cargo do funcionário:");
                String senha = JOptionPane.showInputDialog("Digite a senha do funcionário:");

                Endereco endereco = new Endereco("CEP_EXEMPLO", enderecoFuncionario, 123, "Bairro", "Cidade", "Estado");
                
                // Creating Funcionario object
                Funcionario funcionario = new Funcionario(1, nomeFuncionario, cpfFuncionario, LocalDate.now(), 
                        "987654321", endereco, cargo, senha, senha);

                bancoController.cadastrarFuncionario(funcionario);  
            }
        });

        JButton geracaoRelatoriosButton = new JButton("Geração de Relatórios");
        geracaoRelatoriosButton.setBounds(10, 230, 200, 25);
        panel.add(geracaoRelatoriosButton);

        geracaoRelatoriosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bancoController.gerarRelatorios();
            }
        });

        JButton sairButton = new JButton("Sair");
        sairButton.setBounds(10, 270, 200, 25);
        panel.add(sairButton);

        sairButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Close the application
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Aqui, crie o FuncionarioController e passe para o MenuFuncionarioView
            FuncionarioController funcionarioController = new FuncionarioController();
            MenuFuncionarioView frame = new MenuFuncionarioView(funcionarioController);
            frame.setVisible(true);
        });
    }

	public void exibirMenu() {
		setVisible(true);
		
	}
}
