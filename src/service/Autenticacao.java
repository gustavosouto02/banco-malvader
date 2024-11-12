package service;

import org.mindrot.jbcrypt.BCrypt;
import DAO.UsuarioDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Autenticacao {

    private UsuarioDAO usuarioDAO;
    private static final Logger logger = Logger.getLogger(Autenticacao.class.getName());

    public Autenticacao() {
        try {
            usuarioDAO = new UsuarioDAO(); // Criação da instância do DAO
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao inicializar UsuarioDAO", e);
            
        }
    }

    // Método para autenticar o usuário
    public boolean autenticarUsuario(int idUsuario, String senhaFornecida) throws SQLException {
        String senhaArmazenada = usuarioDAO.buscarSenhaPorUsuario(idUsuario); // Recupera a senha (hash) do banco

		if (senhaArmazenada != null) {
		    // Valida a senha fornecida usando o hash armazenado
		    return BCrypt.checkpw(senhaFornecida, senhaArmazenada);
		}
        return false; // Caso a senha não seja encontrada ou inválida
    }
}
