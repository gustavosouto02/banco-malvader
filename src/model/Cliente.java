package model;

public class Cliente extends Usuario{
	private String senha;
	
	
	@Override
    public boolean login(String senha){
        return this.senha.equals(senha);
    }

    @Override
    public void logout() {
        System.out.println("Cliente " + nome + " deslogado.");
    }
    
    @Override
    public String consultarDados() {
        return "Nome: " + nome + ", CPF: " + cpf;
    }

    
    public double consultarSaldo() {
        return 1000.0;  // saldo fictício
    }

    public void depositar(double valor){
        System.out.println("Depositando " + valor + " na conta.");
    }

    public boolean sacar(double valor){
        return valor <= 1000.0;  // true se valor <= saldo
    }

    public String consultarExtrato(){
        return "Extrato da conta";
    }

    public double consultarLimite(){
        return 500.0;  // limite fictício
    }
}
