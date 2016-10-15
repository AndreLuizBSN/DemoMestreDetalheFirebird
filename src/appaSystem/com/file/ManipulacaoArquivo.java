package appaSystem.com.file;

import java.util.List;

public class ManipulacaoArquivo {

    public String localArquivo(String local){
        String raiz = System.getProperty("user.dir");
        String arquivo = raiz+"\\"+local;
        return arquivo;
    }
    
}
