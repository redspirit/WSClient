/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsclient;

import javax.swing.JOptionPane;

/**
 *
 * @author Алексей
 */
public class AuthFrame extends javax.swing.JFrame {

    private AuthFrame.EventHandler listener;

    private String MD5(String md5) {
       try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
              sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
           }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }    
    
    /**
     * Creates new form AuthFrame
     */
    public AuthFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textLogin = new javax.swing.JTextField();
        textPassword = new javax.swing.JPasswordField();
        btnEnter = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setTitle("Авторизация");
        setResizable(false);

        jLabel1.setText("Логин:");

        jLabel2.setText("Пароль:");

        textLogin.setText("admin");

        textPassword.setText("leosoft");

        btnEnter.setText("Войти");
        btnEnter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEnterMouseClicked(evt);
            }
        });

        btnCancel.setText("Отмена");
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEnter)
                        .addGap(53, 53, 53)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textLogin)
                            .addComponent(textPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnter)
                    .addComponent(btnCancel))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        this.setVisible(false);
        if(this.listener != null)
            this.listener.doCancel();
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnEnterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEnterMouseClicked
        
        String login = textLogin.getText();
        String pass = new String(textPassword.getPassword());
        
        if(login.equals("")){
            JOptionPane.showMessageDialog(null , "Введите свой логин");
            return;
        }
        
        if(pass.equals("")){
            JOptionPane.showMessageDialog(null , "Введите пароль");
            return;
        }
                
        if(this.listener != null)
            this.listener.doEnter(login, MD5(pass));
    }//GEN-LAST:event_btnEnterMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
    }
    
    public void setLogin(String login){
        textLogin.setText(login);
    }

    public void setPassword(String pass){
        textPassword.setText(pass);
    }

    public void clearFields(){
        textLogin.setText("");
        textPassword.setText("");
    }
    
    
    public void setListener (EventHandler listener){
        this.listener = listener;
    }
    
    public static interface EventHandler {
        public void doEnter(String login, String password);
        public void doCancel();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEnter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField textLogin;
    private javax.swing.JPasswordField textPassword;
    // End of variables declaration//GEN-END:variables
}
