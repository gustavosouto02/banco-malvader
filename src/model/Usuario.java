package model;

import java.time.LocalDate;

public abstract class Usuario {
    protected int id;
    protected String nome;
    protected String cpf;
    protected LocalDate dataNascimento;
    protected String telefone;
    protected Endereco endereco;

    // Construtor da classe Usuario
    public Usuario(int id, String nome, String cpf, LocalDate dataNascimento, String telefone, Endereco endereco) {
    	this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    // Método de login
    public abstract boolean login(String senha); // Tornado abstrato, pois a senha pode ser específica para cada tipo de usuário

    // Método de logout
    public void logout() {
        System.out.println("Usuário " + nome + " deslogado.");
    }

    // Método para consultar dados do usuário
    public String consultarDados() {
        return nome + " - " + cpf + " - " + telefone;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
