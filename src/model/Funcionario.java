package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Funcionario extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
    private String codigoFuncionario;
    private String cargo;
    private String senha;

    // Construtor
    public Funcionario(int id, String nome, String cpf, LocalDate dataNascimento, String telefone,
            Endereco endereco, String codigoFuncionario, String cargo, String senha) {
		super(id, nome, cpf, dataNascimento, telefone, endereco);
		this.codigoFuncionario = codigoFuncionario;
		this.cargo = cargo;
		this.senha = senha;
}

    // Métodos getters e setters
    public String getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public String getCargo() {
        return cargo;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public boolean login(String senha) {
        return this.senha.equals(senha);
    }

    @Override
    public String consultarDados() {
        return "Nome: " + getNome() + ", Cargo: " + cargo;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "codigoFuncionario='" + codigoFuncionario + '\'' +
                ", cargo='" + cargo + '\'' +
                ", nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                ", endereco=" + getEndereco() +
                '}';
    }
}
