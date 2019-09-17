/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho1;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author todesco
 */
public class Tela extends javax.swing.JFrame {

    static Connection conexao;
    
    /**
     * Creates new form Tela
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     * @throws java.sql.SQLException
     */
    public Tela() throws IOException, FileNotFoundException, SQLException {
        initComponents();
        
        // Seta tamanho da tela ao abrir ela
        this.setTamanhoTela();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGetDimensoes = new javax.swing.JButton();
        cbMitigar = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnGetDimensoes.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnGetDimensoes.setText("Clique aqui!");
        btnGetDimensoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetDimensoesActionPerformed(evt);
            }
        });

        cbMitigar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbMitigar.setText("Mitigar");
        cbMitigar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMitigarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(btnGetDimensoes, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cbMitigar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addComponent(btnGetDimensoes, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(cbMitigar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGetDimensoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetDimensoesActionPerformed
        // TODO add your handling code here:
        
        try {
            // Busca as dimensões atuais da tela
            Dimension tamanho = this.getSize();
            
            // Salva elas separadas
            int largura = (int) tamanho.getWidth();
            int altura = (int) tamanho.getHeight();

            // Se o usuário quiser mitigar o problema, o sistema salva as informações no BD
            if(cbMitigar.isSelected()){
                
                String sql = "UPDATE DIMENSOES SET largura = " + largura + ", altura = " + altura + "";
                System.out.println(sql);

                Statement statement = Tela.conexao.createStatement();
                int retorno = statement.executeUpdate(sql);
                
                System.out.println("Salvou = " + retorno);
            }
            else if(!cbMitigar.isSelected()){
                // Cria arquivo
                File arquivo = new File("C:/dimensoes.txt");
                arquivo.createNewFile();

                // Cria escritor de arquivo
                FileWriter fw = new FileWriter(arquivo);
                BufferedWriter bw = new BufferedWriter(fw);

                // Escreve no arquivo
                bw.write(largura + ";" + altura);

                // Mostra o que gravou no arquivo
                System.out.println(largura + ";" + altura);

                // Fecha o escritor de arquivo
                bw.close();
                fw.close();
            }

            // Fecha o sistema
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGetDimensoesActionPerformed

    private void cbMitigarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMitigarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMitigarActionPerformed

    private void setTamanhoTela() throws FileNotFoundException, IOException, SQLException{

        // Reconhece o arquivo
        File arquivo = new File("C:/dimensoes.txt");

        // Verifica se o arquivo existe. Se não existir, busca informações do BD
        if(!arquivo.exists()){
            
            String sql = "SELECT * FROM DIMENSOES";
            System.out.println(sql);

            Statement statement = Tela.conexao.createStatement();
            ResultSet retorno = statement.executeQuery(sql);

            while (retorno.next()) {
                // Define largura e altura
                int largura = Integer.parseInt(retorno.getString("largura"));
                int altura = Integer.parseInt(retorno.getString("altura"));
                
                // Mostra o que será setado no tamanho da tela
                System.out.println(largura + ";" + altura);
                
                // Seta largura e altura na tela
                this.setSize(largura, altura);
            }
        }
        else if(arquivo.exists()){
            // Le o arquivo
            FileReader ler = new FileReader("C:/dimensoes.txt");
            BufferedReader reader = new BufferedReader(ler);  

            // Variável que armazenará a linha
            String linha;

            // Lê a linha
            while((linha = (String) reader.readLine()) != null){

                // Mostra o que leu no arquivo
                System.out.println(linha);

                // Splita o texto para buscar largura e altura separadamente
                String[] dados = linha.split(";");

                // Define largura e altura
                int largura = Integer.parseInt(dados[0]);
                int altura = Integer.parseInt(dados[1]);

                // Seta largura e altura na tela
                this.setSize(largura, altura);
            }
        }
    }
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        // Conecta com o banco de dados
        Conexao con = new Conexao();

        Tela.conexao = con.conecta();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Tela().setVisible(true);
            } catch (IOException | SQLException ex) {
                Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGetDimensoes;
    private javax.swing.JCheckBox cbMitigar;
    // End of variables declaration//GEN-END:variables
}
