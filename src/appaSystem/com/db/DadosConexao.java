package appaSystem.com.db;

import appaSystem.com.file.ManipulacaoArquivo;
import appaSystem.com.file.ManipulacaoINI;
import java.util.List;

public class DadosConexao {

    private String driver;
    private String url;
    private String usuario;
    private String senha;

    public DadosConexao(){
        
        ManipulacaoArquivo ma = new ManipulacaoArquivo();
        String arquivo = ma.localArquivo("config.ini");
        
        ManipulacaoINI mi = new ManipulacaoINI();
        List<String[]> lista = mi.carregarIni(arquivo);
        
        for (String[] lista1 : lista) {
            
            if(lista1[0].equals("url")){
                this.url = lista1[1];
            }else if(lista1[0].equals("driver")){
                this.driver = lista1[1];
            }else if(lista1[0].equals("usuario")){
                this.usuario = lista1[1];
            }else if(lista1[0].equals("senha")){
                this.senha = lista1[1];
            }
            
        }
        
    }
    
    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }
    
    
    
}
