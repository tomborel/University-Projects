/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographyassignment;

/**
 *
 * @author thomasborel
 */
public class GUI extends javax.swing.JFrame {

    Encoder encoder = new Encoder();
    Decoder decoder = new Decoder();
    
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        encodeButton = new javax.swing.JButton();
        encodeField = new javax.swing.JTextField();
        generateButton = new javax.swing.JButton();
        generateField = new javax.swing.JTextField();
        luckyDipButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputArea = new javax.swing.JTextArea();
        decodeButton = new javax.swing.JButton();
        decodeTextField = new javax.swing.JTextField();
        luckyDipTextField = new javax.swing.JTextField();
        generationsTextField = new javax.swing.JTextField();
        luckyDipGenerationsTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("BCH Code Utilities");

        encodeButton.setText("Encode");
        encodeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                encodeButtonMouseClicked(evt);
            }
        });

        generateButton.setText("Generate Codes");
        generateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                generateButtonMouseClicked(evt);
            }
        });

        luckyDipButton.setText("Lucky Dip");
        luckyDipButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                luckyDipButtonMouseClicked(evt);
            }
        });

        outputArea.setColumns(20);
        outputArea.setRows(5);
        jScrollPane1.setViewportView(outputArea);

        decodeButton.setText("Decode");
        decodeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                decodeButtonMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jLabel1)
                        .add(168, 168, 168))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, luckyDipButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, encodeButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, generateButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                    .add(decodeButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(decodeTextField)
                                    .add(layout.createSequentialGroup()
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                            .add(org.jdesktop.layout.GroupLayout.LEADING, luckyDipTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                            .add(org.jdesktop.layout.GroupLayout.LEADING, generateField))
                                        .add(18, 18, 18)
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(luckyDipGenerationsTextField)
                                            .add(generationsTextField)))
                                    .add(encodeField))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))))
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 315, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 149, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(encodeButton)
                        .add(18, 18, 18)
                        .add(generateButton))
                    .add(layout.createSequentialGroup()
                        .add(encodeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(generateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(generationsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(luckyDipButton)
                    .add(luckyDipTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(luckyDipGenerationsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(decodeButton)
                    .add(decodeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 216, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void encodeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encodeButtonMouseClicked
        outputArea.setText(encoder.encode(encodeField.getText()));
    }//GEN-LAST:event_encodeButtonMouseClicked

    private void generateButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generateButtonMouseClicked
        
        if(generateField.getText().isEmpty())
        {
            outputArea.setText("Please enter a number to start from");
            return;
        }
        
        if(generationsTextField.getText().isEmpty())
        {
            outputArea.setText("Please enter number of generations");
            return;
        }
            
        String text = encoder.generateNextUsable(generateField.getText().toString(), Integer.parseInt(generationsTextField.getText())).toString();
        
        text = text.replaceAll("\\[", "");
        text = text.replaceAll("\\]", "");
        
        outputArea.setText(" " + text.replaceAll(",", "\n"));
    }//GEN-LAST:event_generateButtonMouseClicked

    private void luckyDipButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_luckyDipButtonMouseClicked
        
        if(luckyDipTextField.getText().isEmpty())
        {
            outputArea.setText("Please enter a number to start from");
            return;
        }
        
        if(luckyDipGenerationsTextField.getText().isEmpty())
        {
            outputArea.setText("Please enter number of generations");
            return;
        }
        
        encoder.setPalindromes("");
        encoder.setPalindromeCount(0);
        
        encoder.generateNextUsable(luckyDipTextField.getText().toString(), Integer.parseInt(luckyDipGenerationsTextField.getText()));
        
        outputArea.setText(encoder.getPalindromes() + "\n Count: " + encoder.getPalindromeCount());
    }//GEN-LAST:event_luckyDipButtonMouseClicked

    private void decodeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_decodeButtonMouseClicked
        if(decodeTextField.getText().isEmpty())
            outputArea.setText("Enter Code");
        
        outputArea.setText(decoder.decode(decodeTextField.getText()));
    }//GEN-LAST:event_decodeButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton decodeButton;
    private javax.swing.JTextField decodeTextField;
    private javax.swing.JButton encodeButton;
    private javax.swing.JTextField encodeField;
    private javax.swing.JButton generateButton;
    private javax.swing.JTextField generateField;
    private javax.swing.JTextField generationsTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton luckyDipButton;
    private javax.swing.JTextField luckyDipGenerationsTextField;
    private javax.swing.JTextField luckyDipTextField;
    private javax.swing.JTextArea outputArea;
    // End of variables declaration//GEN-END:variables
}
