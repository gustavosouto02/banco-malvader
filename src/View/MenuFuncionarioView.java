package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DAO.FuncionarioDAO;
import controller.BancoController;
import controller.FuncionarioController;
import java.sql.SQLException;

public class MenuFuncionarioView extends JFrame {

    private static final long serialVersionUID = 1L;
    private BancoController bancoController;
    private FuncionarioController funcionarioController;

    public MenuFuncionarioView(BancoController bancoController, FuncionarioController funcionarioController) {
        this.bancoController = bancoController;
        this.funcionarioController = funcionarioController;
        setTitle("Menu Funcionário");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        add(panel);
        placeComponents(panel, gbc);
    }

    private void placeComponents(JPanel panel, GridBagConstraints gbc) {
        // Abertura de Conta
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton aberturaContaButton = new JButton("Abertura de Conta");
        panel.add(aberturaContaButton, gbc);

        aberturaContaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AberturaDeContaScreen aberturaDeContaScreen = new AberturaDeContaScreen(bancoController);
                aberturaDeContaScreen.setVisible(true);
            }
        });

        // Encerramento de Conta
        gbc.gridx = 0;
        gbc.gridy = 1;
        JButton encerramentoContaButton = new JButton("Encerramento de Conta");
        panel.add(encerramentoContaButton, gbc);

        encerramentoContaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int numeroConta = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da conta para encerrar:"));
                    bancoController.encerrarConta(String.valueOf(numeroConta));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira um número válido para a conta.");
                }
            }
        });

        // Consulta de Dados
        gbc.gridx = 0;
        gbc.gridy = 2;
        JButton consultaDadosButton = new JButton("Consulta de Dados");
        panel.add(consultaDadosButton, gbc);

        consultaDadosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bancoController.consultarDados();
            }
        });

        // Alteração de Dados
        gbc.gridx = 0;
        gbc.gridy = 3;
        JButton alteracaoDadosButton = new JButton("Alteração de Dados");
        panel.add(alteracaoDadosButton, gbc);

        alteracaoDadosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int numeroConta = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da conta para alterar:"));
                    String novoNome = JOptionPane.showInputDialog("Digite o novo nome do cliente:");
                    bancoController.alterarDados(String.valueOf(numeroConta), novoNome);
                } catch (NumberFormatException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira um número válido para a conta.");
                }
            }
        });

        // Cadastro de Funcionário
        gbc.gridx = 0;
        gbc.gridy = 4;
        JButton cadastroFuncionarioButton = new JButton("Cadastro de Funcionário");
        panel.add(cadastroFuncionarioButton, gbc);

        cadastroFuncionarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastroFuncionarioFrame cadastroFuncionarioFrame = new CadastroFuncionarioFrame("", "", "", "");
                cadastroFuncionarioFrame.setVisible(true);
            }
        });

        // Geração de Relatórios
        gbc.gridx = 0;
        gbc.gridy = 5;
        JButton geracaoRelatoriosButton = new JButton("Geração de Relatórios");
        panel.add(geracaoRelatoriosButton, gbc);

        geracaoRelatoriosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bancoController.gerarRelatorios();
            }
        });

        // Exportar para Excel
        gbc.gridx = 0;
        gbc.gridy = 6;
        JButton exportarExcelButton = new JButton("Exportar para Excel");
        panel.add(exportarExcelButton, gbc);

        exportarExcelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bancoController.exportarRelatorioParaExcel();
            }
        });

        // Sair
        gbc.gridx = 0;
        gbc.gridy = 7;
        JButton sairButton = new JButton("Sair");
        panel.add(sairButton, gbc);

        sairButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha apenas a janela
            }
        });
    }

    // Inicializar a interface
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BancoController bancoController = new BancoController();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            FuncionarioController funcionarioController = new FuncionarioController(bancoController, funcionarioDAO);
            MenuFuncionarioView frame = new MenuFuncionarioView(bancoController, funcionarioController);
            frame.setVisible(true);
        });
    }

    public void exibirMenu() {
        setVisible(true);
    }
}
