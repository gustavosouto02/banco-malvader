package View;

import javax.swing.*;
import service.Autenticacao;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField userText;
    private JPasswordField passwordText;
    private Autenticacao autenticacao;
    private static final Logger logger = Logger.getLogger(LoginView.class.getName());

    public LoginView() {
        // Inicializa o objeto Autenticacao
        autenticacao = new Autenticacao();
        
        setTitle("Banco Malvader - Login");
        setSize(400, 200);
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
        
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            realizarLogin();
        });
    }

    private void realizarLogin() {
        try {
            // Recupera os valores inseridos pelo usuário
            int idUsuario = Integer.parseInt(userText.getText());
            String senhaFornecida = new String(passwordText.getPassword());

            // Chama o método autenticarUsuario e armazena o resultado
            boolean autenticado = autenticacao.autenticarUsuario(idUsuario, senhaFornecida);

            // Exibe a mensagem com base no resultado da autenticação
            if (autenticado) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID de usuário inválido.");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao realizar autenticação", ex);
            JOptionPane.showMessageDialog(this, "Erro ao conectar com o banco de dados.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView frame = new LoginView();
            frame.setVisible(true);
        });
    }
}
