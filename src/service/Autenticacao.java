package service;

import org.mindrot.jbcrypt.BCrypt;
import DAO.UsuarioDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Autenticacao {

    private final UsuarioDAO usuarioDAO;
    private static final Logger logger = Logger.getLogger(Autenticacao.class.getName());

    public Autenticacao() {
        this(new UsuarioDAO());
    }

    public Autenticacao(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public boolean autenticarUsuario(int idUsuario, String senhaFornecida) {
        try {
            String senhaArmazenada = usuarioDAO.buscarSenhaPorUsuario(idUsuario); // Recupera a senha (hash)

            if (senhaArmazenada != null) {
                return BCrypt.checkpw(senhaFornecida, senhaArmazenada);
            }
        } catch (Exception e) {  
            logger.log(Level.SEVERE, "Erro ao autenticar o usuário com ID: " + idUsuario, e);
        }
        return false; // Caso a senha não seja encontrada ou ocorra erro
    }
}
