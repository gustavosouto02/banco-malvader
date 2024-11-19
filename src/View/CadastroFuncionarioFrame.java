package View;

import model.Endereco;
import model.Funcionario;
import controller.FuncionarioController;

import javax.swing.*;
import java.awt.*;

public class CadastroFuncionarioFrame extends JFrame implements CadastroFuncionarioView {
   
	private static final long serialVersionUID = 1L;

	private FuncionarioController funcionarioController;

    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField enderecoField;
    private JTextField cargoField;
    private JPasswordField senhaField;

    public CadastroFuncionarioFrame(FuncionarioController funcionarioController) {
        this.funcionarioController = funcionarioController;

        setTitle("Cadastro de Funcionário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        add(panel);
        placeComponents(panel);
    }

    @Override
    public void exibirCadastroFuncionario() {
        setVisible(true);
    }

    @Override
    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    @Override
    public void limparCampos() {
        nomeField.setText("");
        cpfField.setText("");
        enderecoField.setText("");
        cargoField.setText("");
        senhaField.setText("");
    }

    @Override
    public Funcionario obterDadosFuncionario() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String enderecoStr = enderecoField.getText(); // Get address from field
        String cargo = cargoField.getText();
        String senha = new String(senhaField.getPassword());

        // Create the Endereco object
        Endereco endereco = new Endereco("CEP_EXEMPLO", enderecoStr, 123, "Bairro", "Cidade", "Estado");

        // Return a Funcionario object with the Endereco
        return new Funcionario(0, nome, cpf, null, null, endereco, cargo, senha, senha); // Passing Endereco here
    }

    private void placeComponents(JPanel panel) {
        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField(20);
        panel.add(nomeField);

        panel.add(new JLabel("CPF:"));
        cpfField = new JTextField(20);
        panel.add(cpfField);

        panel.add(new JLabel("Endereço:"));
        enderecoField = new JTextField(20);
        panel.add(enderecoField);

        panel.add(new JLabel("Cargo:"));
        cargoField = new JTextField(20);
        panel.add(cargoField);

        panel.add(new JLabel("Senha:"));
        senhaField = new JPasswordField(20);
        panel.add(senhaField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(e -> {
            Funcionario funcionario = obterDadosFuncionario();
            funcionarioController.cadastrarFuncionario(funcionario);
            limparCampos();
            exibirMensagem("Funcionário cadastrado com sucesso!");
        });
        panel.add(cadastrarButton);
    }
}
