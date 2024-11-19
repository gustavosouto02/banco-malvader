package View;

import javax.swing.*;
import service.Autenticacao;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField userText;
    private JPasswordField passwordText;
    private Autenticacao autenticacao;
    private static final Logger logger = Logger.getLogger(LoginView.class.getName());

    public LoginView() {
        autenticacao = new Autenticacao();

        setTitle("Banco Malvader - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JButton loginFuncionarioButton = new JButton("Login Funcionário");
        loginFuncionarioButton.setBounds(10, 80, 150, 25);
        panel.add(loginFuncionarioButton);

        loginFuncionarioButton.addActionListener(e -> {
            realizarLogin("funcionario");
        });

        JButton loginClienteButton = new JButton("Login Cliente");
        loginClienteButton.setBounds(170, 80, 150, 25);
        panel.add(loginClienteButton);

        loginClienteButton.addActionListener(e -> {
            realizarLogin("cliente");
        });
    }

    private void realizarLogin(String tipoUsuario) {
        try {
            String userIdText = userText.getText();
            String senhaFornecida = new String(passwordText.getPassword());

            if (userIdText.isEmpty() || senhaFornecida.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
                return;
            }

            int idUsuario;
            try {
                idUsuario = Integer.parseInt(userIdText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID de usuário inválido.");
                return;
            }

            boolean autenticado = autenticacao.autenticarUsuario(idUsuario, senhaFornecida);

            if (autenticado) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                new MenuPrincipalView().setVisible(true); // Redireciona para o Menu Principal
                this.dispose();  // Fecha a janela de login
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.");
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Erro inesperado durante o login", ex);
            JOptionPane.showMessageDialog(this, "Erro inesperado. Tente novamente mais tarde.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView frame = new LoginView();
            frame.setVisible(true);
        });
    }
}
