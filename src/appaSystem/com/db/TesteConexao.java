package appaSystem.com.db;

public class TesteConexao {

    public String getStatus(){
        Conexao con = new Conexao();
        DadosConexao dcon = new DadosConexao();
        con.conecta(dcon.getUrl(), dcon.getDriver(), dcon.getUsuario(), dcon.getSenha());

        return con.statusConexaoStr();
    }
    
}
