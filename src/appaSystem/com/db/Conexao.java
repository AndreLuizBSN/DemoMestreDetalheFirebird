package appaSystem.com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexao {

    private Connection conexao;
    private Statement st;
    private boolean statusCon;
    public ResultSet rs;
    
    //Metodo de conexão com o banco de dados
    public void conecta(String url, String driver, String usuario, String senha){
        
        this.statusCon = false;
        
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
            this.statusCon = true;
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar o Driver de conexão com o Banco!\n"+ex);
        } catch (SQLException ex2){
            JOptionPane.showMessageDialog(null, "Erro no conexão com o Banco!\n"+ex2);
        }
        
    }
    
    //Metodo de desconexão com o banco de dados
    public void desconecta(){
        
        try{
            conexao.close();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao desconectar o banco de dados!\n"+ex);
        }
        
    }
    
    //Executar a atualização dos dados
    public void executeAtualizacao(String sql){
     
        try {
            st = conexao.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar SQL "+sql+"! Erro: \n"+ex);
        }
        
    }
    
    //Executar consulta de banco de dados
    public void executeConsulta(String sql){
        
        try {
            st = conexao.createStatement();
            this.rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar SQL "+sql+"! Erro: \n"+ex);
        }
    }
    
    public boolean statusConexao(){
        
        return this.statusCon;
        
    }
    
    public String statusConexaoStr(){
        
        if(statusConexao()){
            return "Conexão OK";
        }else{
            return "Erro na Conexão";
        }
        
    }
    
}
