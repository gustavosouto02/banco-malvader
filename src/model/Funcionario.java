package model;

public class Funcionario extends Usuario {
    private String codigoFuncionario;
    private String cargo;
    private String senha;

    @Override 
    public boolean login(String senha){
        return this.senha.equals(senha);
    }

    @Override
    public void logout(){
        System.out.println("Funcionário " + nome + "deslogado. ");

    }

    @Override
    public String consultarDados(){
        return "Nome: " + nome + ", Cargo;" + cargo;
    }

    public void abrirConta(Conta conta){
        //implementação para abrir
    }

    public void encerrarConta(Conta conta) {
        // Implementação para encerrar 
    }

    public Conta consultarDadosConta(int numeroConta) {
        return new Conta(); 
    }

    public Cliente consultarDadosCliente(int idCliente) {
        return new Cliente(); 
    }

    public void alterarDadosConta(Conta conta) {
        // Implementação para alterar dados da conta
    }

    public void alterarDadosCliente(Cliente cliente) {
        // Implementação para alterar dados do cliente
    }

    public void cadastrarFuncionario(Funcionario funcionario) {
        // Implementação para cadastrar novo funcionário
    }

    public void gerarRelatorioMovimentacao() {
        // Implementação para gerar relatórios
    }
}
