package View;

import javax.swing.*;
import controller.BancoController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuClienteView extends JFrame {

    private static final long serialVersionUID = 1L;
    private BancoController bancoController;

    public MenuClienteView() {
        bancoController = new BancoController();

        setTitle("Menu Cliente");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    public void exibirMenu() {
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton saldoButton = new JButton("Saldo");
        saldoButton.setBounds(10, 30, 200, 25);
        panel.add(saldoButton);

        saldoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String contaStr = JOptionPane.showInputDialog("Digite o número da conta:");
                int numeroConta = Integer.parseInt(contaStr);
                double saldo = bancoController.consultarSaldo(numeroConta);
                if (saldo >= 0) {
                    JOptionPane.showMessageDialog(null, "Saldo: " + saldo);
                } else {
                    JOptionPane.showMessageDialog(null, "Conta não encontrada.");
                }
            }
        });

        JButton depositoButton = new JButton("Depósito");
        depositoButton.setBounds(10, 70, 200, 25);
        panel.add(depositoButton);

        depositoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String contaStr = JOptionPane.showInputDialog("Digite o número da conta:");
                int numeroConta = Integer.parseInt(contaStr);
                String valorStr = JOptionPane.showInputDialog("Digite o valor do depósito:");
                double valor = Double.parseDouble(valorStr);
                bancoController.realizarDeposito(numeroConta, valor);
            }
        });

        JButton saqueButton = new JButton("Saque");
        saqueButton.setBounds(10, 110, 200, 25);
        panel.add(saqueButton);

        saqueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String contaStr = JOptionPane.showInputDialog("Digite o número da conta:");
                int numeroConta = Integer.parseInt(contaStr);
                String valorStr = JOptionPane.showInputDialog("Digite o valor do saque:");
                double valor = Double.parseDouble(valorStr);
                bancoController.realizarSaque(numeroConta, valor);
            }
        });

        JButton extratoButton = new JButton("Extrato");
        extratoButton.setBounds(10, 150, 200, 25);
        panel.add(extratoButton);

        extratoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Adicione a lógica para exibir o extrato
                JOptionPane.showMessageDialog(null, "Exibindo extrato...");
            }
        });

        JButton sairButton = new JButton("Sair");
        sairButton.setBounds(10, 230, 200, 25);
        panel.add(sairButton);

        sairButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Fecha a aplicação
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuClienteView frame = new MenuClienteView();
            frame.setVisible(true);
        });
    }
}
