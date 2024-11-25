package View;

import javax.swing.*;

import DAO.FuncionarioDAO;
import controller.BancoController;
import controller.FuncionarioController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalView extends JFrame {

    private static final long serialVersionUID = 1L;

    public MenuPrincipalView() {
        setTitle("Menu Principal");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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
                // Exibe o menu do funcionário
                new MenuFuncionarioView(new BancoController(), new FuncionarioController(new BancoController(), new FuncionarioDAO())).setVisible(true);
                dispose(); // Fecha a tela atual
            }
        });

        JButton clienteButton = new JButton("Cliente");
        clienteButton.setBounds(170, 80, 150, 25);
        panel.add(clienteButton);

        clienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Exibe o menu do cliente
                new MenuClienteView().setVisible(true);
                dispose(); // Fecha a tela atual
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
