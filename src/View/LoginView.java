package View;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

//import org.mindrot.jbcrypt.BCrypt;

public class LoginView extends JFrame {
	
	public void LoginScreen() {
		setTitle("Banco Malvader - Login");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		add(panel);
		placeComponents(panel);
	}
	
	private void placeComponents(JPanel panel) {
		panel.setLayout(null);
		
		JLabel userLabel = new JLabel ("Usuário:");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);
		
		JTextField userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText);
		
		JLabel passwordLabel = new JLabel("Senha:");
		passwordLabel.setBounds(10, 50, 80, 25);
		panel.add(passwordLabel);
		
		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 50, 165, 25);
		panel.add(passwordText);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(10, 80, 80, 25);
		panel.add(loginButton);
		
		loginButton.addActionListener(e -> {
			JOptionPane.showMessageDialog(null, "Login bem sucedido!");
		});
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			LoginScreen frame = new LoginScreen();
			frame.setVisible(true);
		})
		
		}
	}
/*
	@Override
	public boolean login(String senha) {
	    return BCrypt.checkpw(senha, this.senha);  // Compara a senha informada com o hash armazenado
	    */
	}

