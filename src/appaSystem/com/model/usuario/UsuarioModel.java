package appaSystem.com.model.usuario;

import appaSystem.com.db.Conexao;
import appaSystem.com.db.DadosConexao;
import appaSystem.com.kernel.Idioma;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioModel {

    Conexao con = new Conexao();
    DadosConexao dcon = new DadosConexao();

    public List<Usuario> consultar(String condicao){
        
        List<Usuario> usuarios = new ArrayList<>();

        Usuario usuario = new Usuario();

        //Carregar o cabeçalho
        con.conecta(dcon.getUrl(), dcon.getDriver(), dcon.getUsuario(), dcon.getSenha());
        String sql = "SELECT * FROM USUARIO";

        if(!condicao.equals("") || !condicao.isEmpty()){
            sql += " WHERE "+condicao;
        }
        
        
        con.executeConsulta(sql);

        try{
            while(con.rs.next()){

                usuario = new Usuario();
                
                usuario.setId(Integer.parseInt(con.rs.getString("ID")));
                usuario.setNome(con.rs.getString("NOME"));
                usuario.setLogin(con.rs.getString("LOGIN"));
                usuario.setSenha(con.rs.getString("SENHA"));
                usuario.setIdioma(Idioma.valueOf(con.rs.getString("IDIOMA")));
                
                usuarios.add(usuario);

            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados!\n"+ex);
        }

        con.desconecta();

        return usuarios;

    }
    
}
