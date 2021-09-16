package ventanas;

import controllers.CProveedor;
import modelgui.ModeloTablaProveedor;
import util.ECampos;
import util.FiltraEntrada;
import util.Helper;
import util.VerificadorEntrada;
import ventanas.buscar.BuscarProveedor;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Ferz
 */
public class IProveedores extends javax.swing.JInternalFrame {

    /**
     * Creates new form IProveedores
     */
    private CProveedor cp;
    private ModeloTablaProveedor mtp;
    private boolean esActualizacion = false;
    private int tipoSeleccion = -1; //-1 todo,1 activos, 0 No activos
    public int finalPag = 6;//cont
    public int tmpFp = finalPag;
    public int inicioPag = 0;
    public int numRegistros = 0;
    
    public IProveedores() {
        initComponents();
        cp = new CProveedor();
        mtp = new ModeloTablaProveedor(inicioPag,finalPag);
        numRegistros = mtp.getCantidadRegistros();
        if(finalPag > numRegistros)
       {
           finalPag = numRegistros;
           bntUltimo.setEnabled(false);
           bntSiguiente.setEnabled(false);
           bntAnterior.setEnabled(true);
            btnPrimero.setEnabled(true);
       }
        tblProveedores.setModel(mtp);
        Helper.ajustarAnchoColumnas(tblProveedores);
        setFiltroTexto();        
        setEventSelectionModel(tblProveedores.getSelectionModel());
    }

    private void setEventSelectionModel(ListSelectionModel lsm)
    {
        ListSelectionListener lsl = new ListSelectionListener(){
         
            public void valueChanged(ListSelectionEvent e) {
                selectionEvent(e);
            }
        };
        
        lsm.addListSelectionListener(lsl);
    }
    
    private void selectionEvent(ListSelectionEvent e)
    {
        if(tblProveedores.getSelectedRow() != -1)
       {
           cp.setProveedor(mtp.getFila(tblProveedores.getSelectedRow()));
           setProveedor();           
       }
    }
    
    private void setProveedor()
    {
           this.txtRuc.setText(cp.getProveedor().getRuc());
           this.txtProveedor.setText(cp.getProveedor().getRazonSocial());
           this.txtCiudad.setText(cp.getProveedor().getCiudad());
           this.txtAProductos.setText(cp.getProveedor().getProductos());
           this.txtContacto.setText(cp.getProveedor().getNomContacto());
           this.txtDireccion.setText(cp.getProveedor().getDireccion());
           this.txtEmail.setText(cp.getProveedor().getEmail());
           this.txtFax.setText(cp.getProveedor().getFax());
           this.txtMovil.setText(cp.getProveedor().getFax());
           this.txtNextel.setText(cp.getProveedor().getNextel());
           this.txtNumCuenta.setText(cp.getProveedor().getCtaBancaria());
           this.txtRubro.setText(cp.getProveedor().getRubro());
           this.txtTelf.setText(cp.getProveedor().getTelefono());
           if(cp.getProveedor().getActivo() == 1)
           {
              chbSetActivo(true); 
           }else
           {
               chbSetActivo(false); 
           }
           this.bntModificar.setEnabled(true);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panelImage1 = new elaprendiz.gui.panel.PanelImage();
        pnlEntradas = new javax.swing.JPanel();
        txtRuc = new elaprendiz.gui.textField.TextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtProveedor = new elaprendiz.gui.textField.TextField();
        txtDireccion = new elaprendiz.gui.textField.TextField();
        txtCiudad = new elaprendiz.gui.textField.TextField();
        txtTelf = new elaprendiz.gui.textField.TextField();
        txtNextel = new elaprendiz.gui.textField.TextField();
        txtMovil = new elaprendiz.gui.textField.TextField();
        txtFax = new elaprendiz.gui.textField.TextField();
        txtNumCuenta = new elaprendiz.gui.textField.TextField();
        txtContacto = new elaprendiz.gui.textField.TextField();
        txtEmail = new elaprendiz.gui.textField.TextField();
        txtRubro = new elaprendiz.gui.textField.TextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAProductos = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        chbEstado = new javax.swing.JCheckBox();
        pnlBuscador = new javax.swing.JPanel();
        btnPrimero = new elaprendiz.gui.button.ButtonRect();
        bntAnterior = new elaprendiz.gui.button.ButtonRect();
        bntBuscar = new elaprendiz.gui.button.ButtonRect();
        bntSiguiente = new elaprendiz.gui.button.ButtonRect();
        bntUltimo = new elaprendiz.gui.button.ButtonRect();
        rbTodos = new javax.swing.JRadioButton();
        rbAtivos = new javax.swing.JRadioButton();
        rbNoActivos = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProveedores = new javax.swing.JTable();
        pnlAcciones = new javax.swing.JPanel();
        bntNuevo = new elaprendiz.gui.button.ButtonRect();
        bntModificar = new elaprendiz.gui.button.ButtonRect();
        bntGuardar = new elaprendiz.gui.button.ButtonRect();
        bntEliminar = new elaprendiz.gui.button.ButtonRect();
        bntCancelar = new elaprendiz.gui.button.ButtonRect();
        bntSalir = new elaprendiz.gui.button.ButtonRect();

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 654, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setTitle("Formulario del Proveedor");
        setPreferredSize(new java.awt.Dimension(900, 600));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/fondoazulceleste.jpg"))); // NOI18N
        panelImage1.setLayout(new javax.swing.BoxLayout(panelImage1, javax.swing.BoxLayout.PAGE_AXIS));

        pnlEntradas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Proveedor", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlEntradas.setOpaque(false);
        pnlEntradas.setLayout(new java.awt.GridBagLayout());

        txtRuc.setEditable(false);
        txtRuc.setBackground(new java.awt.Color(255, 255, 255));
        txtRuc.setDireccionDeSombra(30);
        txtRuc.setDisabledTextColor(new java.awt.Color(255, 102, 102));
        txtRuc.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtRuc.setName("pruc"); // NOI18N
        txtRuc.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        pnlEntradas.add(txtRuc, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("RUC:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 7);
        pnlEntradas.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Proveedor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 7);
        pnlEntradas.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Dirección:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 7);
        pnlEntradas.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Ciudad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 7);
        pnlEntradas.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Teléfono:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 7);
        pnlEntradas.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Nextel:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 7);
        pnlEntradas.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Movil:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 7);
        pnlEntradas.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Fax:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 7);
        pnlEntradas.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Nro. de Cuenta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 7);
        pnlEntradas.add(jLabel9, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Contacto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 7);
        pnlEntradas.add(jLabel10, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Email:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 7);
        pnlEntradas.add(jLabel11, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Rubro:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 7);
        pnlEntradas.add(jLabel12, gridBagConstraints);

        txtProveedor.setEditable(false);
        txtProveedor.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtProveedor.setName("prover"); // NOI18N
        txtProveedor.setPreferredSize(new java.awt.Dimension(320, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 2);
        pnlEntradas.add(txtProveedor, gridBagConstraints);

        txtDireccion.setEditable(false);
        txtDireccion.setDireccionDeSombra(30);
        txtDireccion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtDireccion.setName("pdir"); // NOI18N
        txtDireccion.setPreferredSize(new java.awt.Dimension(320, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 2);
        pnlEntradas.add(txtDireccion, gridBagConstraints);

        txtCiudad.setEditable(false);
        txtCiudad.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtCiudad.setPreferredSize(new java.awt.Dimension(320, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 2);
        pnlEntradas.add(txtCiudad, gridBagConstraints);

        txtTelf.setEditable(false);
        txtTelf.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtTelf.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtTelf.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        pnlEntradas.add(txtTelf, gridBagConstraints);

        txtNextel.setEditable(false);
        txtNextel.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtNextel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNextel.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        pnlEntradas.add(txtNextel, gridBagConstraints);

        txtMovil.setEditable(false);
        txtMovil.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtMovil.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtMovil.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 13;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 2);
        pnlEntradas.add(txtMovil, gridBagConstraints);

        txtFax.setEditable(false);
        txtFax.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtFax.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtFax.setPreferredSize(new java.awt.Dimension(120, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 13;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 2);
        pnlEntradas.add(txtFax, gridBagConstraints);

        txtNumCuenta.setEditable(false);
        txtNumCuenta.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNumCuenta.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        pnlEntradas.add(txtNumCuenta, gridBagConstraints);

        txtContacto.setEditable(false);
        txtContacto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtContacto.setPreferredSize(new java.awt.Dimension(320, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 2);
        pnlEntradas.add(txtContacto, gridBagConstraints);

        txtEmail.setEditable(false);
        txtEmail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtEmail.setInputVerifier(new VerificadorEntrada(125,VerificadorEntrada.EMAIL));
        txtEmail.setPreferredSize(new java.awt.Dimension(320, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 2);
        pnlEntradas.add(txtEmail, gridBagConstraints);

        txtRubro.setEditable(false);
        txtRubro.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtRubro.setPreferredSize(new java.awt.Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        pnlEntradas.add(txtRubro, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Productos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 0);
        pnlEntradas.add(jLabel13, gridBagConstraints);

        jScrollPane2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jScrollPane2.setPreferredSize(new java.awt.Dimension(250, 100));

        txtAProductos.setEditable(false);
        txtAProductos.setColumns(20);
        txtAProductos.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        txtAProductos.setLineWrap(true);
        txtAProductos.setRows(5);
        jScrollPane2.setViewportView(txtAProductos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        pnlEntradas.add(jScrollPane2, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Estado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 7);
        pnlEntradas.add(jLabel14, gridBagConstraints);

        chbEstado.setBackground(new java.awt.Color(104, 204, 0));
        chbEstado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chbEstado.setSelected(true);
        chbEstado.setText("Activo");
        chbEstado.setEnabled(false);
        chbEstado.setPreferredSize(new java.awt.Dimension(100, 25));
        chbEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbEstadoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        pnlEntradas.add(chbEstado, gridBagConstraints);

        panelImage1.add(pnlEntradas);

        pnlBuscador.setOpaque(false);

        btnPrimero.setBackground(new java.awt.Color(102, 204, 0));
        btnPrimero.setText("<<");
        btnPrimero.setEnabled(false);
        btnPrimero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroActionPerformed(evt);
            }
        });
        pnlBuscador.add(btnPrimero);

        bntAnterior.setBackground(new java.awt.Color(102, 204, 0));
        bntAnterior.setText("<");
        bntAnterior.setEnabled(false);
        bntAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAnteriorActionPerformed(evt);
            }
        });
        pnlBuscador.add(bntAnterior);

        bntBuscar.setBackground(new java.awt.Color(102, 204, 0));
        bntBuscar.setText("Buscar");
        bntBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntBuscarActionPerformed(evt);
            }
        });
        pnlBuscador.add(bntBuscar);

        bntSiguiente.setBackground(new java.awt.Color(102, 204, 0));
        bntSiguiente.setText(">");
        bntSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSiguienteActionPerformed(evt);
            }
        });
        pnlBuscador.add(bntSiguiente);

        bntUltimo.setBackground(new java.awt.Color(104, 204, 0));
        bntUltimo.setText(">>");
        bntUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntUltimoActionPerformed(evt);
            }
        });
        pnlBuscador.add(bntUltimo);

        rbTodos.setBackground(new java.awt.Color(51, 153, 255));
        buttonGroup1.add(rbTodos);
        rbTodos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        rbTodos.setForeground(new java.awt.Color(255, 255, 255));
        rbTodos.setSelected(true);
        rbTodos.setText("Todos");
        rbTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTodosActionPerformed(evt);
            }
        });
        pnlBuscador.add(rbTodos);

        rbAtivos.setBackground(new java.awt.Color(51, 153, 255));
        buttonGroup1.add(rbAtivos);
        rbAtivos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        rbAtivos.setForeground(new java.awt.Color(255, 255, 255));
        rbAtivos.setText("Activos");
        rbAtivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAtivosActionPerformed(evt);
            }
        });
        pnlBuscador.add(rbAtivos);

        rbNoActivos.setBackground(new java.awt.Color(51, 153, 255));
        buttonGroup1.add(rbNoActivos);
        rbNoActivos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        rbNoActivos.setForeground(new java.awt.Color(255, 255, 255));
        rbNoActivos.setText("No Activos");
        rbNoActivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNoActivosActionPerformed(evt);
            }
        });
        pnlBuscador.add(rbNoActivos);

        panelImage1.add(pnlBuscador);

        jScrollPane1.setOpaque(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 90));

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
        jScrollPane1.setViewportView(tblProveedores);

        panelImage1.add(jScrollPane1);

        pnlAcciones.setOpaque(false);
        pnlAcciones.setPreferredSize(new java.awt.Dimension(471, 30));
        pnlAcciones.setLayout(new javax.swing.BoxLayout(pnlAcciones, javax.swing.BoxLayout.LINE_AXIS));

        bntNuevo.setBackground(new java.awt.Color(0, 153, 255));
        bntNuevo.setText("Nuevo");
        bntNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntNuevoActionPerformed(evt);
            }
        });
        pnlAcciones.add(bntNuevo);

        bntModificar.setBackground(new java.awt.Color(51, 153, 255));
        bntModificar.setText("Modificar");
        bntModificar.setEnabled(false);
        bntModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntModificarActionPerformed(evt);
            }
        });
        pnlAcciones.add(bntModificar);

        bntGuardar.setBackground(new java.awt.Color(51, 153, 255));
        bntGuardar.setText("Guardar");
        bntGuardar.setEnabled(false);
        bntGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntGuardarActionPerformed(evt);
            }
        });
        pnlAcciones.add(bntGuardar);

        bntEliminar.setBackground(new java.awt.Color(51, 153, 255));
        bntEliminar.setText("Eliminar");
        bntEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntEliminarActionPerformed(evt);
            }
        });
        pnlAcciones.add(bntEliminar);

        bntCancelar.setBackground(new java.awt.Color(51, 153, 255));
        bntCancelar.setText("Cancelar");
        bntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCancelarActionPerformed(evt);
            }
        });
        pnlAcciones.add(bntCancelar);

        bntSalir.setBackground(new java.awt.Color(51, 153, 255));
        bntSalir.setText("Salir");
        bntSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSalirActionPerformed(evt);
            }
        });
        pnlAcciones.add(bntSalir);

        panelImage1.add(pnlAcciones);

        getContentPane().add(panelImage1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntEliminarActionPerformed
        int opcion = JOptionPane.showInternalConfirmDialog(this, "¿Desea Eliminar este proveedor?", "Desea Eliminar", JOptionPane.YES_NO_CANCEL_OPTION);
        if(opcion == JOptionPane.OK_OPTION)
        { 
            //mtp.borrarFila(tblProveedores.getSelectedRow());
            int rs = cp.borrarRegistro(cp.getProveedor());
            if(rs != 0)
            {
                JOptionPane.showMessageDialog(this, "Regisro Eliminado correctamente", "Registro Eliminado", JOptionPane.INFORMATION_MESSAGE);
             mtp = new ModeloTablaProveedor(tipoSeleccion,inicioPag,finalPag);
             this.numRegistros = mtp.getCantidadRegistros();
             tblProveedores.setModel(mtp);
            }
            System.out.println(rs);
        }
    }//GEN-LAST:event_bntEliminarActionPerformed

    private void bntModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntModificarActionPerformed
        ECampos.setEditableTexto(this.pnlEntradas, true, null,false,null);
        //ECampos.buscarBotones(this.pnlBuscador, false, null);
        this.pnlBuscador.setVisible(false);
        bntModificar.setEnabled(false);
        this.bntEliminar.setEnabled(false);
        bntNuevo.setEnabled(false);
        bntGuardar.setEnabled(true);
        esActualizacion = true;
        chbEstado.setEnabled(true);
    }//GEN-LAST:event_bntModificarActionPerformed

    private void bntNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntNuevoActionPerformed
       //ECampos.buscarBotones(this.pnlBuscador, false, null);
       ECampos.setEditableTexto(this.pnlEntradas, true, null,true,"");
       this.bntModificar.setEnabled(false);
       this.bntEliminar.setEnabled(false);
       this.tblProveedores.setEnabled(false);//asas
       bntNuevo.setEnabled(false);
       bntGuardar.setEnabled(true);
       this.tblProveedores.clearSelection();
       esActualizacion = false;
       this.pnlBuscador.setVisible(false);
       
    }//GEN-LAST:event_bntNuevoActionPerformed

    private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCancelarActionPerformed
       //ECampos.buscarBotones(this.pnlBuscador, true, null);
       ECampos.setEditableTexto(this.pnlEntradas, false, null,true,"");
       this.bntModificar.setEnabled(false);
       this.tblProveedores.clearSelection();
       this.bntEliminar.setEnabled(true);
       this.tblProveedores.setEnabled(true);
       bntNuevo.setEnabled(true);
       bntGuardar.setEnabled(false);
       ECampos.esObligatorio(this.pnlEntradas,false);
       System.out.println(txtRuc.getText());
       System.out.println(txtProveedor.getText());
       esActualizacion = false;
       chbEstado.setEnabled(false);
       this.pnlBuscador.setVisible(true);
    }//GEN-LAST:event_bntCancelarActionPerformed

    private void bntGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntGuardarActionPerformed
       if(esActualizacion)
       {
           if(!this.verficarCambios())
            {
                JOptionPane.showMessageDialog(this, "Debe cambiar por lo menos algun valor", "No hubo cambios", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
       }
        boolean correcto = guardarActualizar(); 
        if(!correcto)
        {
            return;
        }
       
       if(!esActualizacion && correcto)
       {        
        ECampos.setEditableTexto(this.pnlEntradas, false, null,true,"");
       }
       if(tipoSeleccion == -1)
        {
            this.mtp = new ModeloTablaProveedor(inicioPag,finalPag);
            tblProveedores.setModel(mtp);
        }else if(tipoSeleccion == 1)
        {
            this.mtp = new ModeloTablaProveedor(tipoSeleccion,inicioPag,finalPag);
            tblProveedores.setModel(mtp);
        }
       this.numRegistros = mtp.getCantidadRegistros();
       //ECampos.buscarBotones(this.pnlBuscador, true, null); 
       this.pnlBuscador.setVisible(true);
       this.bntModificar.setEnabled(true);
        this.bntEliminar.setEnabled(true);
        this.tblProveedores.setEnabled(true);
        bntNuevo.setEnabled(true);
        bntGuardar.setEnabled(false);
        ECampos.esObligatorio(this.pnlEntradas,false);
        System.out.println(txtRuc.getText());
        System.out.println(txtProveedor.getText());
        chbEstado.setEnabled(false);  
       esActualizacion = false;
    }//GEN-LAST:event_bntGuardarActionPerformed

    private void rbTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTodosActionPerformed
       tipoSeleccion = -1;
       inicioPag = 0;  
       finalPag = tmpFp;
       mtp = new ModeloTablaProveedor(tipoSeleccion,inicioPag,finalPag);
       numRegistros = mtp.getCantidadRegistros();
       if(finalPag > numRegistros)
       {
           finalPag = numRegistros;
           bntUltimo.setEnabled(false);
           bntSiguiente.setEnabled(false);
       }else{
             bntUltimo.setEnabled(true);
           bntSiguiente.setEnabled(true);
        }  
       tblProveedores.setModel(mtp);
       chbSetActivo(true);
       ECampos.setEditableTexto(this.pnlEntradas, false, null,true,"");
    }//GEN-LAST:event_rbTodosActionPerformed

    private void rbAtivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAtivosActionPerformed
        tipoSeleccion = 1;
        inicioPag = 0; 
       finalPag = tmpFp;
        mtp = new ModeloTablaProveedor(tipoSeleccion,inicioPag,finalPag);
        numRegistros = mtp.getCantidadRegistros();
        if(finalPag > numRegistros)
       {
           finalPag = numRegistros;
           bntUltimo.setEnabled(false);
           bntSiguiente.setEnabled(false);
       }else{
             bntUltimo.setEnabled(true);
           bntSiguiente.setEnabled(true);
        }
        tblProveedores.setModel(mtp);
        chbSetActivo(true);
        ECampos.setEditableTexto(this.pnlEntradas, false, null,true,"");
    }//GEN-LAST:event_rbAtivosActionPerformed

    private void rbNoActivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNoActivosActionPerformed
        tipoSeleccion = 0;
        inicioPag = 0; 
        finalPag = tmpFp;
        mtp = new ModeloTablaProveedor(tipoSeleccion,inicioPag,finalPag);
        numRegistros = mtp.getCantidadRegistros();
        if(finalPag > numRegistros)
       {
           finalPag = numRegistros;
           bntUltimo.setEnabled(false);
           bntSiguiente.setEnabled(false);
       }else{
             bntUltimo.setEnabled(true);
           bntSiguiente.setEnabled(true);
        }
        tblProveedores.setModel(mtp);
        chbSetActivo(false);
        ECampos.setEditableTexto(this.pnlEntradas, false, null,true,"");
    }//GEN-LAST:event_rbNoActivosActionPerformed

    private void chbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbEstadoActionPerformed
        
      chbSetActivo(chbEstado.isSelected());
        
    }//GEN-LAST:event_chbEstadoActionPerformed

    private void bntSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSiguienteActionPerformed
            inicioPag += finalPag; 
            if(finalPag>=this.numRegistros)
            {
                bntSiguiente.setEnabled(false);
                bntUltimo.setEnabled(false);
                bntAnterior.setEnabled(true);
                btnPrimero.setEnabled(true);
                return;
            }
            if(tipoSeleccion == -1)
            {
                this.mtp = new ModeloTablaProveedor(inicioPag,finalPag);                
            }else if(tipoSeleccion == 1)
            {
                this.mtp = new ModeloTablaProveedor(tipoSeleccion,inicioPag,finalPag);
            }
            tblProveedores.setModel(mtp);
           bntAnterior.setEnabled(true);
           btnPrimero.setEnabled(true);
           if((inicioPag+finalPag)>=this.numRegistros)
            {                
                 bntSiguiente.setEnabled(false);
                 bntUltimo.setEnabled(false);
            }
    }//GEN-LAST:event_bntSiguienteActionPerformed

    private void bntAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAnteriorActionPerformed
            inicioPag -= finalPag;  
            System.out.println(inicioPag+" > "+this.numRegistros);
            if(inicioPag<0)
            {
                inicioPag = 0;
                bntAnterior.setEnabled(false);
                btnPrimero.setEnabled(false);
                bntUltimo.setEnabled(true);
                bntSiguiente.setEnabled(true);
                return;
                
            }
            if(tipoSeleccion == -1)
            {
                this.mtp = new ModeloTablaProveedor(inicioPag,finalPag);                
            }else if(tipoSeleccion == 1)
            {
                this.mtp = new ModeloTablaProveedor(tipoSeleccion,inicioPag,finalPag);
            }
            tblProveedores.setModel(mtp);
            bntUltimo.setEnabled(true);
            bntSiguiente.setEnabled(true);
            btnPrimero.setEnabled(true);
            if((inicioPag-finalPag)<0)
            {
                
                inicioPag = 0;
                bntAnterior.setEnabled(false);
                btnPrimero.setEnabled(false);
            }
    }//GEN-LAST:event_bntAnteriorActionPerformed

    private void bntUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntUltimoActionPerformed
            inicioPag = this.numRegistros - finalPag;
            if(finalPag>=this.numRegistros)
            {
                bntSiguiente.setEnabled(false);
                bntUltimo.setEnabled(false);
                bntAnterior.setEnabled(true);
                btnPrimero.setEnabled(true);
                return;
            }
            if(inicioPag-finalPag<0)
            {
               inicioPag = finalPag; 
            }
            if(tipoSeleccion == -1)
            {
                this.mtp = new ModeloTablaProveedor(inicioPag,finalPag);                
            }else if(tipoSeleccion == 1)
            {
                this.mtp = new ModeloTablaProveedor(tipoSeleccion,inicioPag,finalPag);
            }
            tblProveedores.setModel(mtp);
            bntUltimo.setEnabled(false);
            bntSiguiente.setEnabled(false);
            bntAnterior.setEnabled(true);
            btnPrimero.setEnabled(true);
    }//GEN-LAST:event_bntUltimoActionPerformed

    private void btnPrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroActionPerformed
            inicioPag =0;
            if(tipoSeleccion == -1)
            {
                this.mtp = new ModeloTablaProveedor(inicioPag,finalPag);                
            }else if(tipoSeleccion == 1)
            {
                this.mtp = new ModeloTablaProveedor(tipoSeleccion,inicioPag,finalPag);
            }
            tblProveedores.setModel(mtp);
            btnPrimero.setEnabled(false);
            bntSiguiente.setEnabled(true);
            bntAnterior.setEnabled(false);
            bntUltimo.setEnabled(true);
    }//GEN-LAST:event_btnPrimeroActionPerformed

    private void bntBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntBuscarActionPerformed
       BuscarProveedor pvc = new BuscarProveedor(tipoSeleccion);
       JLabel aviso = pvc.getAviso();
       String msj = (tipoSeleccion==-1?"Mostrando todos los Proveedores existentes":(tipoSeleccion==1?"Mostrando todo los usuarios activos":"Mostrando todo los usuarios No activos"));
        JOptionPane.showInternalOptionDialog(this, pvc, msj,JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE, null, new Object[]{aviso},null);
   
        if(pvc.getProveedor() != null)
        {
           cp.setProveedor(pvc.getProveedor());
           setProveedor();
        }
    }//GEN-LAST:event_bntBuscarActionPerformed

    private void bntSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSalirActionPerformed
       cerrarVentana();
    }//GEN-LAST:event_bntSalirActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        cerrarVentana();
    }//GEN-LAST:event_formInternalFrameClosing

    private void cerrarVentana()
    {
        int opcio = JOptionPane.showInternalConfirmDialog(this, "Perderá todos los datos"
                + " que no hayan sido guardados\n"
                + "¿Desea cerrar esta ventana?", "¿Desea cerrar la ventana?", 
                JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
        if(opcio==JOptionPane.OK_OPTION)
        {
            cp = new CProveedor();
            inicioPag = 0;
            tipoSeleccion = -1;
            mtp = new ModeloTablaProveedor(inicioPag,finalPag);
            numRegistros = mtp.getCantidadRegistros();
            
            ECampos.setEditableTexto(this.pnlEntradas, false, null,true,"");
            this.bntModificar.setEnabled(false);
            this.tblProveedores.clearSelection();
            this.bntEliminar.setEnabled(true);
            this.tblProveedores.setEnabled(true);
            bntNuevo.setEnabled(true);
            bntGuardar.setEnabled(false);
            ECampos.esObligatorio(this.pnlEntradas,false);            
            esActualizacion = false;
            chbEstado.setEnabled(false);
            this.pnlBuscador.setVisible(true);
            this.rbTodos.setSelected(true);
            
            if(finalPag > numRegistros)
            {
                finalPag = numRegistros;
                bntUltimo.setEnabled(false);
                bntSiguiente.setEnabled(false);
                bntAnterior.setEnabled(true);
                 btnPrimero.setEnabled(true);
            }
            this.dispose();
        }
    }
    
    public void chbSetActivo(boolean opcion)
    {
        chbEstado.setSelected(true);
        chbEstado.setText("Activo");
        chbEstado.setBackground(new Color(104,204,0));
        chbEstado.setForeground(Color.BLACK);
        if(!opcion)
        {
            chbEstado.setSelected(false);
            chbEstado.setText("No Activo");
            chbEstado.setBackground(Color.red);
            chbEstado.setForeground(Color.WHITE);
        }
    }
    public void setFiltroTexto()
    {
        Helper.setFiltraEntrada(txtRuc.getDocument(), FiltraEntrada.SOLO_NUMEROS, 15, false);
        Helper.setFiltraEntrada(txtNumCuenta.getDocument(), FiltraEntrada.SOLO_NUMEROS, 30, false);
        Helper.setFiltraEntrada(txtProveedor.getDocument(), FiltraEntrada.NUM_LETRAS, 150, true);
        Helper.setFiltraEntrada(txtDireccion.getDocument(), FiltraEntrada.NUM_LETRAS, 200, true);        
    }
    
    public boolean verficarCambios()
    {
        if(cp.getProveedor() != null)
        {
          if(!txtRuc.getText().equals(cp.getProveedor().getRuc()))
          {              
              return true;
          }else if(!txtProveedor.getText().equals(cp.getProveedor().getRazonSocial()))
          {
              return true;
          }else if(!txtDireccion.getText().equals(cp.getProveedor().getDireccion()))
          {
              return true;
          }else if(!txtCiudad.getText().equals(cp.getProveedor().getCiudad()))
          {
             return true; 
          }else if(!txtTelf.getText().equals(cp.getProveedor().getTelefono()))
          {
             return true; 
          }else if(!txtNextel.getText().equals(cp.getProveedor().getNextel()))
          {
             return true; 
          }else if(!txtMovil.getText().equals(cp.getProveedor().getMovil()))
          {
             return true; 
          }else if(!txtFax.getText().equals(cp.getProveedor().getFax()))
          {
             return true; 
          }else if(!txtNumCuenta.getText().equals(cp.getProveedor().getCtaBancaria()))
          {
             return true;
          }else if(!txtContacto.getText().equals(cp.getProveedor().getNomContacto()))
          {
             return true; 
          }else if(!txtEmail.getText().equals(cp.getProveedor().getEmail()))
          {
             return true; 
          }else if(!txtRubro.getText().equals(cp.getProveedor().getRubro()))
          {
             return true; 
          }else if(!txtAProductos.getText().equals(cp.getProveedor().getProductos()))
          {
             return true; 
          }else if(chbEstado.isSelected() != (cp.getProveedor().getActivo()==1))
          {
              System.out.println(!chbEstado.isSelected() == (cp.getProveedor().getActivo()==0));
              return true;
          }else if(!chbEstado.isSelected() != (cp.getProveedor().getActivo()==0))
          {              
              return true;
          }
        }
        
        return false;
    }
    
    
    private boolean guardarActualizar()
    {
        String msj = "¿Desea registrar nuevo Proveedor?";
        String titulo = "Grabar Proveedor";
        String msj2 = "Proveedor con RUC: "+txtRuc.getText()+" ha sido Guardado correctamente";
        String titulo2 = "Proveedor Nuevo Registrado";
        if(esActualizacion)
        {
            msj = "¿Desea Modificar los Registros de este Proveedor?";
            titulo = "Grabar Modificar datos";
            msj2 = "Proveedor con RUC: "+txtRuc.getText()+" ha sido Actualizado correctamente";
            titulo2 = "Proveedor Actualizado";
        }
        if(ECampos.esObligatorio(this.pnlEntradas,true))
        {
            JOptionPane.showInternalMessageDialog(this, "Los campos en rojo son obligatorios", "Llene los campos obligatorios", JOptionPane.ERROR_MESSAGE);
            return false;
        }         
        int opcion = JOptionPane.showInternalConfirmDialog(this, msj, titulo, JOptionPane.YES_NO_CANCEL_OPTION);
        boolean seguardo = false;
        if(opcion == JOptionPane.OK_OPTION)
        {
         cp.getProveedor().setRuc(this.txtRuc.getText());
         cp.getProveedor().setRazonSocial(txtProveedor.getText());
         cp.getProveedor().setDireccion(txtDireccion.getText());
         cp.getProveedor().setCiudad(txtCiudad.getText());
         cp.getProveedor().setTelefono(txtTelf.getText());
         cp.getProveedor().setNextel(txtNextel.getText());
         cp.getProveedor().setMovil(txtMovil.getText());
         cp.getProveedor().setFax(txtFax.getText());
         cp.getProveedor().setCtaBancaria(txtNumCuenta.getText());
         cp.getProveedor().setNomContacto(txtContacto.getText());
         cp.getProveedor().setEmail(txtEmail.getText());
         cp.getProveedor().setRubro(txtRubro.getText());
         cp.getProveedor().setProductos(txtAProductos.getText());
            
            
            //asas
            
            if(!esActualizacion)
            {
                seguardo = cp.guardarRegistro(cp.getProveedor());
                
            }else
            {
                cp.getProveedor().setActivo(chbEstado.isSelected()?1:0);
                int act = cp.actualizarRegistro(cp.getProveedor());
                if(act != 0)
                {
                   seguardo = true; 
                }
            }
            if(seguardo)
            {
                JOptionPane.showInternalMessageDialog(this, msj2, titulo2, JOptionPane.INFORMATION_MESSAGE);
            }else
            {
                JOptionPane.showInternalMessageDialog(this, "Error: no se pudo guardar.", "Error al guardar el registro", JOptionPane.ERROR_MESSAGE);
            }
            ECampos.esObligatorio(this.pnlEntradas,false);
        }
        
        if(opcion == JOptionPane.OK_OPTION && seguardo)
        {
            return true;
        }        
        return false;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private elaprendiz.gui.button.ButtonRect bntAnterior;
    private elaprendiz.gui.button.ButtonRect bntBuscar;
    private elaprendiz.gui.button.ButtonRect bntCancelar;
    private elaprendiz.gui.button.ButtonRect bntEliminar;
    private elaprendiz.gui.button.ButtonRect bntGuardar;
    private elaprendiz.gui.button.ButtonRect bntModificar;
    private elaprendiz.gui.button.ButtonRect bntNuevo;
    private elaprendiz.gui.button.ButtonRect bntSalir;
    private elaprendiz.gui.button.ButtonRect bntSiguiente;
    private elaprendiz.gui.button.ButtonRect bntUltimo;
    private elaprendiz.gui.button.ButtonRect btnPrimero;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chbEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private elaprendiz.gui.panel.PanelImage panelImage1;
    private javax.swing.JPanel pnlAcciones;
    private javax.swing.JPanel pnlBuscador;
    private javax.swing.JPanel pnlEntradas;
    private javax.swing.JRadioButton rbAtivos;
    private javax.swing.JRadioButton rbNoActivos;
    private javax.swing.JRadioButton rbTodos;
    private javax.swing.JTable tblProveedores;
    private javax.swing.JTextArea txtAProductos;
    private elaprendiz.gui.textField.TextField txtCiudad;
    private elaprendiz.gui.textField.TextField txtContacto;
    private elaprendiz.gui.textField.TextField txtDireccion;
    private elaprendiz.gui.textField.TextField txtEmail;
    private elaprendiz.gui.textField.TextField txtFax;
    private elaprendiz.gui.textField.TextField txtMovil;
    private elaprendiz.gui.textField.TextField txtNextel;
    private elaprendiz.gui.textField.TextField txtNumCuenta;
    private elaprendiz.gui.textField.TextField txtProveedor;
    private elaprendiz.gui.textField.TextField txtRubro;
    private elaprendiz.gui.textField.TextField txtRuc;
    private elaprendiz.gui.textField.TextField txtTelf;
    // End of variables declaration//GEN-END:variables



}
