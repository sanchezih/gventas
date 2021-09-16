package ventanas.paneles;

import controllers.CAlmacen;
import controllers.CEncargadoAlmacen;
import controllers.CUsuario;
import modelbd.Almacen;
import modelbd.EncargadoAlmacen;
import modelbd.Usuario;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class EncargadoAlmacenPanel extends javax.swing.JPanel {

    public final static int ALMACEN = 1;
    public final static int USUARIO = 2;
    private int tipo;
    private String tituloBorde;
    private String tituloBorde2;
    private boolean opcion = false;
    
    
    private Usuario usuario;
    private Almacen almacen;
    private DefaultListModel asignados = new DefaultListModel();
    private DefaultListModel dlm = new DefaultListModel();
    private int indiceAlmacenPorDefecto;
    private JOptionPane op;
    /**
     * Creates new form EncargadoAlmacenPanel
     */
    public EncargadoAlmacenPanel(Object model,int tipo) {
        initComponents();
        switch(tipo)
        {
            case ALMACEN:
                break;
            case USUARIO:
                break;
        }
    }

    public EncargadoAlmacenPanel(Usuario usuario) {
        this.tipo = USUARIO;
        tituloBorde = "Almacenes Disponibles";
        tituloBorde2 = "Almacenes Asignados";
        initComponents();
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel(EncargadoAlmacen.TIPOS_CARGOS); 
        this.cmbTipo.setModel(dcbm);
        this.usuario = usuario;
        if(this.usuario != null)
        {
           this.lbNombre.setText(usuario.getLogin()); 
        }else{
            this.lbNombre.setText("Usuario test"); 
        }
        initDatosAlmacen();
        
    }
    
    public EncargadoAlmacenPanel(Almacen almacen) {
        this.tipo = ALMACEN;
        tituloBorde = "Usuarios Disponibles";
        tituloBorde2 = "Usuarios Asignados";
        initComponents();
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel(EncargadoAlmacen.TIPOS_CARGOS); 
        this.cmbTipo.setModel(dcbm);
        this.almacen = almacen;
        if(this.almacen != null)
        {
           this.lbNombre.setText(almacen.getNombre()); 
        }else{
            this.lbNombre.setText("Almacen test"); 
        }
        initDatosUsuario();
    }
    
    private void initDatosAlmacen()
    {
        CAlmacen controllerAlmacen = new CAlmacen();
        ArrayList<Almacen> registros = controllerAlmacen.getRegistros(new Object[]{1});        
        for(Almacen al:registros)
        {
            dlm.addElement(al);
        }
        this.listaMuestra.setModel(dlm);
        listaExistentes.setModel(asignados);
    }
    
    private void initDatosUsuario()
    {
        CUsuario controllerUsuario = new CUsuario();
        ArrayList<Usuario> registros = controllerUsuario.getRegistros(new Object[]{1});        
        for(Usuario al:registros)
        {
            dlm.addElement(al);
        }
        this.listaMuestra.setModel(dlm);
        listaExistentes.setModel(asignados);
    }

    public boolean EsAceptado() {
        return opcion;
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
    
    public void guardar()
    {
        /**
         * verificar que el usuario no se asigne al mismo almacen
         */
        if(tipo==ALMACEN)
        {
            asignarEncargadoAlmacen();
        }else if(tipo == USUARIO)
        {
            asignaAlmacenEncargado();
        }
    }
    
    private void asignarEncargadoAlmacen()
    {
        CEncargadoAlmacen cea = new CEncargadoAlmacen();
        EncargadoAlmacen ea = new EncargadoAlmacen();
        for(int i=0;i<asignados.size();i++)
        {
            Usuario usr = (Usuario)asignados.get(i);
            ea.setAlmacen(almacen);
            ea.setUsuario(usr);
            ea.setTipoCargo(this.cmbTipo.getSelectedItem().toString());
            cea.guardarRegistro(ea);
        }
    }
    
    public void desabilitarCancelar()
    {
        this.bntCancelar.setEnabled(false);
    }
    
    private void asignaAlmacenEncargado()
    {
        CEncargadoAlmacen cea = new CEncargadoAlmacen();
        EncargadoAlmacen ea = new EncargadoAlmacen();
        for(int i=0;i<asignados.size();i++)
        {
            Almacen alm = (Almacen)asignados.get(i);
            ea.setAlmacen(alm);
            ea.setUsuario(usuario);
            ea.setTipoCargo(this.cmbTipo.getSelectedItem().toString());
            cea.guardarRegistro(ea);
        }
    }
    
    public void getAlmacenPordefecto()
    {
        if(!asignados.isEmpty())
        {
            asignados.get(this.indiceAlmacenPorDefecto);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelImage1 = new elaprendiz.gui.panel.PanelImage();
        jPanel1 = new javax.swing.JPanel();
        lbNombre = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbTipo = new elaprendiz.gui.comboBox.ComboBoxRect();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaMuestra = new elaprendiz.gui.list.ListSelector();
        jPanel4 = new javax.swing.JPanel();
        bntEstablecer = new elaprendiz.gui.button.ButtonRect();
        bntEliminar = new elaprendiz.gui.button.ButtonRect();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaExistentes = new elaprendiz.gui.list.ListSelector();
        jPanel6 = new javax.swing.JPanel();
        bntAceptar = new elaprendiz.gui.button.ButtonRect();
        bntCancelar = new elaprendiz.gui.button.ButtonRect();

        setLayout(new java.awt.BorderLayout());

        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/fondoazulceleste.jpg"))); // NOI18N
        panelImage1.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lbNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbNombre.setText("Usuario desconocido");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 12, 30);
        jPanel1.add(lbNombre, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Tipo de Encargado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 12, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        cmbTipo.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 4, 12, 6);
        jPanel1.add(cmbTipo, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Usuario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 12, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        panelImage1.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, this.tituloBorde, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14),java.awt.Color.white));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.BorderLayout());

        listaMuestra.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listaMuestra.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaMuestraValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listaMuestra);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3);

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.PAGE_AXIS));

        bntEstablecer.setBackground(new java.awt.Color(51, 153, 255));
        bntEstablecer.setText("Establecer");
        bntEstablecer.setEnabled(false);
        bntEstablecer.setMaximumSize(new java.awt.Dimension(108, 25));
        bntEstablecer.setPreferredSize(new java.awt.Dimension(108, 25));
        bntEstablecer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntEstablecerActionPerformed(evt);
            }
        });
        jPanel4.add(bntEstablecer);

        bntEliminar.setBackground(new java.awt.Color(51, 153, 255));
        bntEliminar.setText("Eliminar");
        bntEliminar.setEnabled(false);
        bntEliminar.setMaximumSize(new java.awt.Dimension(108, 25));
        bntEliminar.setPreferredSize(new java.awt.Dimension(108, 25));
        bntEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntEliminarActionPerformed(evt);
            }
        });
        jPanel4.add(bntEliminar);

        jPanel2.add(jPanel4);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, this.tituloBorde2, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14), java.awt.Color.white));
        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.BorderLayout());

        listaExistentes.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listaExistentes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaExistentes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaExistentesValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listaExistentes);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5);

        panelImage1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel6.setOpaque(false);

        bntAceptar.setBackground(new java.awt.Color(51, 153, 255));
        bntAceptar.setText("Aceptar");
        bntAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAceptarActionPerformed(evt);
            }
        });
        jPanel6.add(bntAceptar);

        bntCancelar.setBackground(new java.awt.Color(51, 153, 255));
        bntCancelar.setText("Cancelar");
        bntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCancelarActionPerformed(evt);
            }
        });
        jPanel6.add(bntCancelar);

        panelImage1.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        add(panelImage1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void listaMuestraValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaMuestraValueChanged
        if(listaMuestra.getSelectedValue() != null)
        {
            this.bntEstablecer.setEnabled(true);
        }
    }//GEN-LAST:event_listaMuestraValueChanged

    private void listaExistentesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaExistentesValueChanged
        if(listaExistentes.getModel().getSize() > 0)
        {
            if(listaExistentes.getSelectedValue() != null)
            {
                this.indiceAlmacenPorDefecto = listaExistentes.getSelectedIndex();
                this.bntEliminar.setEnabled(true);
            }
        }
        
    }//GEN-LAST:event_listaExistentesValueChanged

    private void bntEstablecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntEstablecerActionPerformed
        if(listaMuestra.getSelectedValue() != null)
        {
            //establece el tipo de cargo en el almace por el usuario
            if(tipo == ALMACEN)
            {
                //asignaAlmacenEncargado();
                Usuario tmp = (Usuario)listaMuestra.getSelectedValue();
                tmp.setTipoCargoAlmacen(this.cmbTipo.getSelectedItem().toString());
                asignados.addElement(tmp);
                dlm.removeElement(tmp);
                
            }else{
                 asignados.addElement(listaMuestra.getSelectedValue());
                dlm.removeElement(listaMuestra.getSelectedValue());
            }
            this.bntEliminar.setEnabled(true);
            if(dlm.isEmpty())
            {
                this.bntEstablecer.setEnabled(false);
            }
        }
    }//GEN-LAST:event_bntEstablecerActionPerformed

    private void bntEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntEliminarActionPerformed
        if(listaExistentes.getModel().getSize() > 0)
        {
            if(listaExistentes.getSelectedValue() != null)
            {
                dlm.addElement(listaExistentes.getSelectedValue());
                asignados.removeElement(listaExistentes.getSelectedValue());
                this.bntEstablecer.setEnabled(true);
                if(asignados.isEmpty())
                {
                    this.bntEliminar.setEnabled(false);
                }
            }
        }
    }//GEN-LAST:event_bntEliminarActionPerformed

    private void bntAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAceptarActionPerformed
        getOptionPane();
        if(!asignados.isEmpty())
        {
            this.opcion = true;
            op.setValue(1);
        }else{
            JOptionPane.showMessageDialog(null, "La lista "+tituloBorde2+" no debe estar vacio.", tituloBorde, WIDTH);
        }
    }//GEN-LAST:event_bntAceptarActionPerformed

    private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCancelarActionPerformed
       getOptionPane();
       op.setValue(1);
    }//GEN-LAST:event_bntCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private elaprendiz.gui.button.ButtonRect bntAceptar;
    private elaprendiz.gui.button.ButtonRect bntCancelar;
    private elaprendiz.gui.button.ButtonRect bntEliminar;
    private elaprendiz.gui.button.ButtonRect bntEstablecer;
    private elaprendiz.gui.comboBox.ComboBoxRect cmbTipo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbNombre;
    private elaprendiz.gui.list.ListSelector listaExistentes;
    private elaprendiz.gui.list.ListSelector listaMuestra;
    private elaprendiz.gui.panel.PanelImage panelImage1;
    // End of variables declaration//GEN-END:variables
}
