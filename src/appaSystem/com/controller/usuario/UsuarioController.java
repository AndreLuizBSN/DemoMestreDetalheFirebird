package appaSystem.com.controller.usuario;

import appaSystem.com.model.usuario.Usuario;
import appaSystem.com.model.usuario.UsuarioModel;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    
    private List<Usuario> usuarios;
    
    public UsuarioController(){
        usuarios = new ArrayList<>();
    }
    
    public List<Usuario> consulta(String condicao){
        
        UsuarioModel um = new UsuarioModel();
        return um.consultar(condicao);
        
    }
    
}
