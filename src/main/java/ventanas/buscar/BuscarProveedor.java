/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas.buscar;

import controllers.CProveedor;
import core.JAbstractModelBD;
import modelgui.ModeloTablaProveedor;
import util.FiltraEntrada;
import util.Helper;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Ferz Código Lite - https://codigolite.com
 */
public class BuscarProveedor extends javax.swing.JPanel {

    /**
     * Creates new form BuscarProveedor
     */
    JOptionPane op;
    int opcion=-1;
    private ModeloTablaProveedor mtp;
    private CProveedor cp;
    
    public BuscarProveedor(int opcion) {
        initComponents();
        lbAviso.setVisible(false);
        this.opcion = opcion;
        switch(opcion)
        {
            case 0:
            case 1:
                mtp = new ModeloTablaProveedor(opcion);
                break;
            default:                
                mtp = new ModeloTablaProveedor();
        }
        tblProveedores.setModel(mtp);
        Helper.ajustarAnchoColumnas(tblProveedores);
        Helper.setFiltraEntrada(txtDato.getDocument(), FiltraEntrada.SOLO_NUMEROS, 50, false);
    }
    public BuscarProveedor() {
        initComponents();
        lbAviso.setVisible(false);
        mtp = new ModeloTablaProveedor();
        tblProveedores.setModel(mtp);
        Helper.ajustarAnchoColumnas(tblProveedores);
        Helper.setFiltraEntrada(txtDato.getDocument(), FiltraEntrada.SOLO_NUMEROS, 50, false);
    }
    
    private void getOptionPane()
    {
        if(op != null)
        {
            return;
        }
        Component pdr =this.getParent(); 
        while(pdr.getParent() != null)
        {
            if(pdr instanceof JOptionPane)
            {
                op = (JOptionPane)pdr;
                break;
            }            
            pdr = pdr.getParent();
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        lbAviso = new elaprendiz.gui.label.LabelRect();
        panelImage1 = new elaprendiz.gui.panel.PanelImage();
        panelCurves1 = new elaprendiz.gui.panel.PanelCurves();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDato = new elaprendiz.gui.textField.TextField();
        rbRuc = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        rbTelefono = new javax.swing.JRadioButton();
        buttonRect1 = new elaprendiz.gui.button.ButtonRect();
        buttonRect2 = new elaprendiz.gui.button.ButtonRect();
        jPanel2 = new javax.swing.JPanel();
        bntAceptar = new elaprendiz.gui.button.ButtonRect();
        bntCancelar = new elaprendiz.gui.button.ButtonRect();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProveedores = new javax.swing.JTable();

        lbAviso.setBackground(new java.awt.Color(255, 0, 51));
        lbAviso.setForeground(new java.awt.Color(255, 255, 0));
        lbAviso.setText("Debe Seleccionar un Proveedor");
        lbAviso.setPreferredSize(new java.awt.Dimension(250, 17));

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(600, 373));
        setLayout(new java.awt.BorderLayout());

        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/fondoazulceleste.jpg"))); // NOI18N
        panelImage1.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Buscar Por:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        txtDato.setPreferredSize(new java.awt.Dimension(250, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(txtDato, gridBagConstraints);

        rbRuc.setBackground(new java.awt.Color(51, 153, 255));
        buttonGroup1.add(rbRuc);
        rbRuc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbRuc.setForeground(new java.awt.Color(255, 255, 255));
        rbRuc.setSelected(true);
        rbRuc.setText("RUC");
        rbRuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbRucActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 1, 12);
        jPanel1.add(rbRuc, gridBagConstraints);

        rbNombre.setBackground(new java.awt.Color(51, 153, 255));
        buttonGroup1.add(rbNombre);
        rbNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbNombre.setForeground(new java.awt.Color(255, 255, 255));
        rbNombre.setText("Nombre");
        rbNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNombreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(6, 14, 1, 16);
        jPanel1.add(rbNombre, gridBagConstraints);

        rbTelefono.setBackground(new java.awt.Color(51, 153, 255));
        buttonGroup1.add(rbTelefono);
        rbTelefono.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbTelefono.setForeground(new java.awt.Color(255, 255, 255));
        rbTelefono.setText("Telefono");
        rbTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTelefonoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 1, 6);
        jPanel1.add(rbTelefono, gridBagConstraints);

        buttonRect1.setBackground(new java.awt.Color(51, 153, 255));
        buttonRect1.setText("Buscar");
        buttonRect1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRect1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel1.add(buttonRect1, gridBagConstraints);

        buttonRect2.setBackground(new java.awt.Color(51, 153, 255));
        buttonRect2.setText("Mostrar Todo");
        buttonRect2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRect2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 8, 1, 0);
        jPanel1.add(buttonRect2, gridBagConstraints);

        panelCurves1.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);

        bntAceptar.setBackground(new java.awt.Color(51, 153, 255));
        bntAceptar.setText("Seleccionar");
        bntAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAceptarActionPerformed(evt);
            }
        });
        jPanel2.add(bntAceptar);

        bntCancelar.setBackground(new java.awt.Color(51, 153, 255));
        bntCancelar.setText("Cancelar");
        bntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(bntCancelar);

        panelCurves1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setOpaque(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(363, 250));

        tblProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProveedores.setOpaque(false);
        jScrollPane1.setViewportView(tblProveedores);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panelCurves1.add(jPanel3, java.awt.BorderLayout.CENTER);

        panelImage1.add(panelCurves1, java.awt.BorderLayout.CENTER);

        add(panelImage1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCancelarActionPerformed
        getOptionPane();
        op.setValue(1);
    }//GEN-LAST:event_bntCancelarActionPerformed

    private void rbRucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbRucActionPerformed
        txtDato.setText("");
        Helper.setFiltraEntrada(txtDato.getDocument(), FiltraEntrada.SOLO_NUMEROS, 50, false);
    }//GEN-LAST:event_rbRucActionPerformed

    private void rbNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNombreActionPerformed
        txtDato.setText("");
        Helper.setFiltraEntrada(txtDato.getDocument(), FiltraEntrada.SOLO_LETRAS, 150, true);
    }//GEN-LAST:event_rbNombreActionPerformed

    private void rbTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTelefonoActionPerformed
        txtDato.setText("");
        Helper.setFiltraEntrada(txtDato.getDocument(), FiltraEntrada.SOLO_NUMEROS, 15, false);
    }//GEN-LAST:event_rbTelefonoActionPerformed

    private void buttonRect1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRect1ActionPerformed
        if(!txtDato.getText().isEmpty())
        {
            String columnas[] = null;
            Object valores[] = null;               
            
            valores = new Object[]{txtDato.getText()}; 
            if(this.rbRuc.isSelected())
            {
               columnas = new String[]{ModeloTablaProveedor.cmpRuc()};
                
               if(opcion != -1)
                {
                    columnas = new String[]{ModeloTablaProveedor.cmpActivo(),
                                            ModeloTablaProveedor.cmpRuc()};
                    valores = new Object[]{opcion,txtDato.getText()};
                } 
              
            }else if(this.rbNombre.isSelected())
            {
                columnas = new String[]{ModeloTablaProveedor.cmpRazonSocial()};               
               if(opcion != -1)
                {
                    columnas = new String[]{ModeloTablaProveedor.cmpActivo(),
                                            ModeloTablaProveedor.cmpRazonSocial()};
                    valores = new Object[]{opcion,txtDato.getText()};
                } 
             
            }else if(this.rbTelefono.isSelected())
            {
              columnas = new String[]{ModeloTablaProveedor.cmpTelefono()};               
               if(opcion != -1)
                {
                    columnas = new String[]{ModeloTablaProveedor.cmpActivo(),
                                            ModeloTablaProveedor.cmpTelefono()};
                    valores = new Object[]{opcion,txtDato.getText()};
                } 
              
            }
            this.mtp = new ModeloTablaProveedor(columnas,valores);
              this.tblProveedores.setModel(mtp);
        }else{
            switch(opcion)
            {
                case 0:
                case 1:
                    mtp = new ModeloTablaProveedor(opcion);
                    break;
                default:
                    mtp = new ModeloTablaProveedor();
            }
            
           tblProveedores.setModel(mtp);
        }
    }//GEN-LAST:event_buttonRect1ActionPerformed

    private void buttonRect2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRect2ActionPerformed
        switch(opcion)
        {
            case 0:
            case 1:
                mtp = new ModeloTablaProveedor(opcion);
                break;
            default:                
                mtp = new ModeloTablaProveedor();
        }
        tblProveedores.setModel(mtp);
    }//GEN-LAST:event_buttonRect2ActionPerformed

    private void bntAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAceptarActionPerformed
        getOptionPane();
        if(this.tblProveedores.getSelectedRow() != -1)
        {
            cp = new CProveedor();
            cp.setProveedor(mtp.getFila(tblProveedores.getSelectedRow()));
            op.setValue(1);
        }else{
            lbAviso.setVisible(true);
        }
        
    }//GEN-LAST:event_bntAceptarActionPerformed

    public JAbstractModelBD getProveedor()
    {
        if(cp!=null)
        {
            return (JAbstractModelBD)cp.getProveedor();
        }
        return null;
    }
    
    public JLabel getAviso()
    {
        return lbAviso;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private elaprendiz.gui.button.ButtonRect bntAceptar;
    private elaprendiz.gui.button.ButtonRect bntCancelar;
    private javax.swing.ButtonGroup buttonGroup1;
    private elaprendiz.gui.button.ButtonRect buttonRect1;
    private elaprendiz.gui.button.ButtonRect buttonRect2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private elaprendiz.gui.label.LabelRect lbAviso;
    private elaprendiz.gui.panel.PanelCurves panelCurves1;
    private elaprendiz.gui.panel.PanelImage panelImage1;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JRadioButton rbRuc;
    private javax.swing.JRadioButton rbTelefono;
    private javax.swing.JTable tblProveedores;
    private elaprendiz.gui.textField.TextField txtDato;
    // End of variables declaration//GEN-END:variables

     
}
