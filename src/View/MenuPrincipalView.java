package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalView extends JFrame {

    private static final long serialVersionUID = 1L;

    public MenuPrincipalView() {
        setTitle("Menu Principal");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton funcionarioButton = new JButton("Funcionário");
        funcionarioButton.setBounds(10, 80, 150, 25);
        panel.add(funcionarioButton);

        funcionarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MenuFuncionarioView(null).setVisible(true); // Abre o Menu do Funcionário
                dispose();  // Fecha o Menu Principal
            }
        });

        JButton clienteButton = new JButton("Cliente");
        clienteButton.setBounds(170, 80, 150, 25);
        panel.add(clienteButton);

        clienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MenuClienteView().setVisible(true); // Abre o Menu do Cliente
                dispose();  // Fecha o Menu Principal
            }
        });

        JButton sairButton = new JButton("Sair");
        sairButton.setBounds(10, 120, 150, 25);
        panel.add(sairButton);

        sairButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Fecha o aplicativo
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipalView frame = new MenuPrincipalView();
            frame.setVisible(true);
        });
    }
}
