package appaSystem.com.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class ManipulacaoINI {

    public List<String[]> carregarIni(String iniFile){
        
        List<String[]> lista = new ArrayList<>();
        String[] valores;
        String propriedadeStr;
        String valorStr;
        
        try { 
            String raiz = System.getProperty("user.dir");

            Properties config = new Properties();
            String arquivo = iniFile;
            
            config.load(new FileInputStream(arquivo));
            Set<String> propriedades = config.stringPropertyNames();//lista as propriedados do INI
            
            for (String propriedade : propriedades) {
            
                propriedadeStr = propriedade;
                valorStr = config.getProperty(propriedade);
                
                lista.add(new String[]{propriedadeStr, valorStr});
                
            }
            
            

        } catch (IOException ex) {
            //Logger.getLogger(ManipulacaoArquivo.class.getName()).log(Level.SEVERE,null, ex);
        }
        
        return lista;
    }
    
}
