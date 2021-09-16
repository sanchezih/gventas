package ventanas;

import controllers.CAlmacen;
import controllers.CAlmacenProducto;
import controllers.CMoneda;
import controllers.CProducto;
import controllers.CSimpleModelo;
import core.DatoArchivo;
import modelbd.Almacen;
import modelbd.AlmacenProducto;
import modelbd.Moneda;
import modelbd.Producto;
import modelbd.SimpleModelo;
import modelgui.ModeloTablaApertura;
import util.ECampos;
import util.Thumbnail;
import util.filechooser.FiltraExtensionArchivos;
import util.filechooser.PanelVistaPrevia;
import util.filechooser.VistaArchivos;
import util.text.FormatoDecimal;
import ventanas.buscar.BuscarProducto;
import ventanas.paneles.PanelAperturaDeInventario;
import ventanas.paneles.PanelListaProductosAlmacen;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class IProducto extends javax.swing.JInternalFrame {

    /**
     * Creates new form IProducto
     */
    private CProducto controllerProducto;
    private ArrayList<Producto> productos;
    private CMoneda cmoneda;
    private CAlmacen calmacen;
    private CSimpleModelo csm;
    private FileInputStream imagen;
    private Producto product; // producto seleccinado de la base de datos
    private DatoArchivo dat;
    private boolean esActualizacion = false;
    private ImageIcon foto = null; // foto del producto 
    private int desplazamiento = 0;
    private int innerOffSet = 0;  //puntero lista
    private int totalProductos = 0;
    ListIterator<Producto> listaIterador = null;
    
    public IProducto() {
        productos = new ArrayList();        
        initComponents();
        iniciarDatos();
    }

    private void iniciarDatos()
    {
        controllerProducto = new CProducto();
        productos = controllerProducto.getRegistros(new Object[]{1}); 
        listaIterador = productos.listIterator();
        totalProductos = controllerProducto.getTotalRegistros();
        if(totalProductos == 0 || totalProductos == 1)
        {
            habilitarBotonesPaginador(false);
        } 
            
        this.tblProductosAlmacen.setModel(new ModeloTablaApertura());
        
        if(listaIterador.hasNext())
        {            
            inicializarProducto(listaIterador.next());
        }
        
         this.setSize(new Dimension(this.getWidth(),648));
         calmacen = new CAlmacen();
         cmoneda = new CMoneda();
        ArrayList<Moneda> monedas = cmoneda.getRegistros(new Object[]{1});
        DefaultComboBoxModel dlmoneda = new DefaultComboBoxModel();
        for(Moneda mn: monedas)
        {
           dlmoneda.addElement(mn); 
        }
        this.cmbTipoMoneda.setModel(dlmoneda);        
        
        
        
        csm = new CSimpleModelo(CSimpleModelo.OCLASE);
        ArrayList<SimpleModelo> registros1 = csm.getRegistros(new Object[]{1});
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for(SimpleModelo sm:registros1)
        {
           dcbm.addElement(sm); 
        }
        this.cmbClase.setModel(dcbm);
        
        csm = new CSimpleModelo(CSimpleModelo.OMARCA);
        ArrayList<SimpleModelo> registros2 = csm.getRegistros(new Object[]{1});
        DefaultComboBoxModel dcbm2 = new DefaultComboBoxModel();
        for(SimpleModelo sm:registros2)
        {
           dcbm2.addElement(sm); 
        }
        this.cmbMarca.setModel(dcbm2);
        
         csm = new CSimpleModelo(CSimpleModelo.OMODELO);
        ArrayList<SimpleModelo> registros3 = csm.getRegistros(new Object[]{1});
        DefaultComboBoxModel dcbm3 = new DefaultComboBoxModel();
        for(SimpleModelo sm:registros3)
        {
           dcbm3.addElement(sm); 
        }
        this.cmbModelo.setModel(dcbm3);
    }
    
    public void inicializarProducto(int pos)
    {
            product = productos.get(pos);            
            this.setValoresCampos(product);
            this.tblProductosAlmacen.setModel(new ModeloTablaApertura(product));
            foto = controllerProducto.getFoto(product.getPrimaryKey());
            if(foto != null)
            {
                this.pnlImagen.setIcon(foto);
            }else{
                this.pnlImagen.setIcon(new ImageIcon(this.getClass().getResource("/resources/noimage_es.png")));
            }    
            this.pnlImagen.updateUI();
    }
    
     public void inicializarProducto(Producto pos)
    {
            product = pos;            
            this.setValoresCampos(product);
            this.tblProductosAlmacen.setModel(new ModeloTablaApertura(product));
            foto = controllerProducto.getFoto(product.getPrimaryKey());
            if(foto != null)
            {
                this.pnlImagen.setIcon(foto);
            }else{
                this.pnlImagen.setIcon(new ImageIcon(this.getClass().getResource("/resources/noimage_es.png")));
            }  
            this.pnlImagen.updateUI();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnlContenedor = new elaprendiz.gui.panel.PanelImage();
        pnlPaginador = new javax.swing.JPanel();
        bntPrimero = new elaprendiz.gui.button.ButtonRect();
        bntAnterior = new elaprendiz.gui.button.ButtonRect();
        bntBuscar = new elaprendiz.gui.button.ButtonRect();
        bntSiguiente = new elaprendiz.gui.button.ButtonRect();
        bntUltimo = new elaprendiz.gui.button.ButtonRect();
        pnlCodes = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new elaprendiz.gui.textField.TextField();
        txtCodigoBarras = new elaprendiz.gui.textField.TextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbClase = new elaprendiz.gui.comboBox.ComboBoxRectIcon();
        cmbMarca = new elaprendiz.gui.comboBox.ComboBoxRectIcon();
        cmbModelo = new elaprendiz.gui.comboBox.ComboBoxRectIcon();
        jLabel17 = new javax.swing.JLabel();
        txtCodeFabricante = new elaprendiz.gui.textField.TextField();
        pnlDetalles = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaNombre = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtUbicacion = new elaprendiz.gui.textField.TextField();
        ftxtStockMinimo = new javax.swing.JFormattedTextField();
        ftxtPeso = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        bntSelecFoto = new elaprendiz.gui.button.ButtonRect();
        pnlImagen = new elaprendiz.gui.panel.PanelImage();
        jPanel5 = new javax.swing.JPanel();
        rbProductoTerminado = new javax.swing.JRadioButton();
        rbCompomente = new javax.swing.JRadioButton();
        rbInsumo = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        cmbUnidadPrincipal = new elaprendiz.gui.comboBox.ComboBoxRect();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProductosAlmacen = new javax.swing.JTable();
        pnlCantidades1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        ftxtUtilidad = new javax.swing.JFormattedTextField();
        cmbTipoMoneda = new elaprendiz.gui.comboBox.ComboBoxRectIcon();
        chbAplicarIGV = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        pnlPrecios = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        ftxtCosto = new javax.swing.JFormattedTextField();
        ftxtPrecioAlMayor = new javax.swing.JFormattedTextField();
        ftxtPrecioAlMenor = new javax.swing.JFormattedTextField();
        pnlBotones = new javax.swing.JPanel();
        bntNuevo = new elaprendiz.gui.button.ButtonRect();
        bntModificar = new elaprendiz.gui.button.ButtonRect();
        bntGuardar = new elaprendiz.gui.button.ButtonRect();
        bntEliminar = new elaprendiz.gui.button.ButtonRect();
        bntCancelar = new elaprendiz.gui.button.ButtonRect();
        bntSalir = new elaprendiz.gui.button.ButtonRect();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setTitle("Registro de Productos");
        setPreferredSize(new java.awt.Dimension(768, 648));
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

        pnlContenedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/fondoazulceleste.jpg"))); // NOI18N
        pnlContenedor.setLayout(new javax.swing.BoxLayout(pnlContenedor, javax.swing.BoxLayout.PAGE_AXIS));

        pnlPaginador.setBackground(new java.awt.Color(0, 153, 204));

        bntPrimero.setBackground(new java.awt.Color(102, 204, 0));
        bntPrimero.setText("<<");
        bntPrimero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntPrimeroActionPerformed(evt);
            }
        });
        pnlPaginador.add(bntPrimero);

        bntAnterior.setBackground(new java.awt.Color(102, 204, 0));
        bntAnterior.setText("<");
        bntAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAnteriorActionPerformed(evt);
            }
        });
        pnlPaginador.add(bntAnterior);

        bntBuscar.setBackground(new java.awt.Color(102, 204, 0));
        bntBuscar.setText("Buscar");
        bntBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntBuscarActionPerformed(evt);
            }
        });
        pnlPaginador.add(bntBuscar);

        bntSiguiente.setBackground(new java.awt.Color(102, 204, 0));
        bntSiguiente.setText(">");
        bntSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSiguienteActionPerformed(evt);
            }
        });
        pnlPaginador.add(bntSiguiente);

        bntUltimo.setBackground(new java.awt.Color(102, 204, 0));
        bntUltimo.setText(">>");
        bntUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntUltimoActionPerformed(evt);
            }
        });
        pnlPaginador.add(bntUltimo);

        pnlContenedor.add(pnlPaginador);

        pnlCodes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlCodes.setOpaque(false);
        pnlCodes.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Código:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 5);
        pnlCodes.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Código de Barras:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 5);
        pnlCodes.add(jLabel2, gridBagConstraints);

        txtCodigo.setEditable(false);
        txtCodigo.setName("code"); // NOI18N
        txtCodigo.setPreferredSize(new java.awt.Dimension(180, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 176);
        pnlCodes.add(txtCodigo, gridBagConstraints);

        txtCodigoBarras.setEditable(false);
        txtCodigoBarras.setName("codebar"); // NOI18N
        txtCodigoBarras.setPreferredSize(new java.awt.Dimension(180, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 176);
        pnlCodes.add(txtCodigoBarras, gridBagConstraints);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 100));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Clase:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 5);
        jPanel3.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Marca:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 5);
        jPanel3.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Modelo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 5);
        jPanel3.add(jLabel5, gridBagConstraints);

        cmbClase.setPreferredSize(new java.awt.Dimension(180, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 0);
        jPanel3.add(cmbClase, gridBagConstraints);

        cmbMarca.setPreferredSize(new java.awt.Dimension(180, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        jPanel3.add(cmbMarca, gridBagConstraints);

        cmbModelo.setPreferredSize(new java.awt.Dimension(180, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        jPanel3.add(cmbModelo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        pnlCodes.add(jPanel3, gridBagConstraints);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Cod. Fabricante:");
        jLabel17.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 5);
        pnlCodes.add(jLabel17, gridBagConstraints);

        txtCodeFabricante.setEditable(false);
        txtCodeFabricante.setPreferredSize(new java.awt.Dimension(180, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 176);
        pnlCodes.add(txtCodeFabricante, gridBagConstraints);

        pnlContenedor.add(pnlCodes);

        pnlDetalles.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlDetalles.setOpaque(false);
        pnlDetalles.setLayout(new java.awt.GridBagLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Descripción:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 0, 0);
        pnlDetalles.add(jLabel6, gridBagConstraints);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(350, 50));

        areaNombre.setEditable(false);
        areaNombre.setColumns(20);
        areaNombre.setLineWrap(true);
        areaNombre.setRows(5);
        areaNombre.setName("nombre"); // NOI18N
        jScrollPane1.setViewportView(areaNombre);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        pnlDetalles.add(jScrollPane1, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Stock Min:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 3);
        pnlDetalles.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Ubicación:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 3);
        pnlDetalles.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Peso:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 3);
        pnlDetalles.add(jLabel9, gridBagConstraints);

        txtUbicacion.setEditable(false);
        txtUbicacion.setPreferredSize(new java.awt.Dimension(50, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 5);
        pnlDetalles.add(txtUbicacion, gridBagConstraints);

        ftxtStockMinimo.setEditable(false);
        ftxtStockMinimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ftxtStockMinimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ftxtStockMinimo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ftxtStockMinimo.setName("stockMinimo"); // NOI18N
        ftxtStockMinimo.setPreferredSize(new java.awt.Dimension(50, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 5);
        pnlDetalles.add(ftxtStockMinimo, gridBagConstraints);

        ftxtPeso.setEditable(false);
        ftxtPeso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#,##0.00",true))));
        ftxtPeso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ftxtPeso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ftxtPeso.setPreferredSize(new java.awt.Dimension(50, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 5);
        pnlDetalles.add(ftxtPeso, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "FOTO", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 350));
        jPanel4.setLayout(new java.awt.BorderLayout());

        bntSelecFoto.setBackground(new java.awt.Color(51, 153, 255));
        bntSelecFoto.setText("Seleccionar Imagen");
        bntSelecFoto.setEnabled(false);
        bntSelecFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSelecFotoActionPerformed(evt);
            }
        });
        jPanel4.add(bntSelecFoto, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout pnlImagenLayout = new javax.swing.GroupLayout(pnlImagen);
        pnlImagen.setLayout(pnlImagenLayout);
        pnlImagenLayout.setHorizontalGroup(
            pnlImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        pnlImagenLayout.setVerticalGroup(
            pnlImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        jPanel4.add(pnlImagen, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 13;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        pnlDetalles.add(jPanel4, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipo de Producto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(200, 130));
        jPanel5.setLayout(new java.awt.GridLayout(3, 1));

        buttonGroup1.add(rbProductoTerminado);
        rbProductoTerminado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbProductoTerminado.setSelected(true);
        rbProductoTerminado.setText("Producto Terminado");
        rbProductoTerminado.setContentAreaFilled(false);
        rbProductoTerminado.setEnabled(false);
        rbProductoTerminado.setName("productoterminado"); // NOI18N
        jPanel5.add(rbProductoTerminado);

        buttonGroup1.add(rbCompomente);
        rbCompomente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbCompomente.setText("Componente");
        rbCompomente.setEnabled(false);
        rbCompomente.setName("componente"); // NOI18N
        rbCompomente.setOpaque(false);
        jPanel5.add(rbCompomente);

        buttonGroup1.add(rbInsumo);
        rbInsumo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbInsumo.setText("Insumo");
        rbInsumo.setEnabled(false);
        rbInsumo.setName("insumo"); // NOI18N
        rbInsumo.setOpaque(false);
        jPanel5.add(rbInsumo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        pnlDetalles.add(jPanel5, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Unidad Principal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 3);
        pnlDetalles.add(jLabel10, gridBagConstraints);

        cmbUnidadPrincipal.setModel(new DefaultComboBoxModel(Producto.UNIDADES));
        cmbUnidadPrincipal.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        pnlDetalles.add(cmbUnidadPrincipal, gridBagConstraints);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(220, 130));

        tblProductosAlmacen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Almacen", "Cantidad"
            }
        ));
        jScrollPane2.setViewportView(tblProductosAlmacen);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        pnlDetalles.add(jScrollPane2, gridBagConstraints);

        pnlContenedor.add(pnlDetalles);

        pnlCantidades1.setOpaque(false);
        pnlCantidades1.setLayout(new java.awt.GridBagLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 204, 204));
        jLabel11.setText("Utilidad %:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(4, 3, 4, 2);
        pnlCantidades1.add(jLabel11, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 204, 204));
        jLabel12.setText("Tipo de Moneda:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 4, 2);
        pnlCantidades1.add(jLabel12, gridBagConstraints);

        ftxtUtilidad.setEditable(false);
        ftxtUtilidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ftxtUtilidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ftxtUtilidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ftxtUtilidad.setName("utilidad"); // NOI18N
        ftxtUtilidad.setPreferredSize(new java.awt.Dimension(80, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 56);
        pnlCantidades1.add(ftxtUtilidad, gridBagConstraints);

        cmbTipoMoneda.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmbTipoMoneda.setName("idmoneda"); // NOI18N
        cmbTipoMoneda.setPreferredSize(new java.awt.Dimension(120, 21));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 3, 4, 4);
        pnlCantidades1.add(cmbTipoMoneda, gridBagConstraints);

        chbAplicarIGV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chbAplicarIGV.setForeground(new java.awt.Color(204, 204, 204));
        chbAplicarIGV.setSelected(true);
        chbAplicarIGV.setText("Aplicar");
        chbAplicarIGV.setEnabled(false);
        chbAplicarIGV.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 0);
        pnlCantidades1.add(chbAplicarIGV, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 204, 204));
        jLabel16.setText("IGV/IVA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 4, 2);
        pnlCantidades1.add(jLabel16, gridBagConstraints);

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jFormattedTextField1.setName("igv"); // NOI18N
        jFormattedTextField1.setPreferredSize(new java.awt.Dimension(50, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 1);
        pnlCantidades1.add(jFormattedTextField1, gridBagConstraints);

        pnlContenedor.add(pnlCantidades1);

        pnlPrecios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        pnlPrecios.setOpaque(false);
        pnlPrecios.setLayout(new java.awt.GridBagLayout());

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 204, 204));
        jLabel13.setText("Costo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 3);
        pnlPrecios.add(jLabel13, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 204, 204));
        jLabel14.setText("Precio Al Por Menor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 11, 4, 3);
        pnlPrecios.add(jLabel14, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 204, 204));
        jLabel15.setText("Precio Al Por Mayor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 11, 4, 3);
        pnlPrecios.add(jLabel15, gridBagConstraints);

        ftxtCosto.setEditable(false);
        ftxtCosto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#,##0.00",true))));
        ftxtCosto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ftxtCosto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ftxtCosto.setName("costo"); // NOI18N
        ftxtCosto.setPreferredSize(new java.awt.Dimension(80, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        pnlPrecios.add(ftxtCosto, gridBagConstraints);

        ftxtPrecioAlMayor.setEditable(false);
        ftxtPrecioAlMayor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#,##0.00",true))));
        ftxtPrecioAlMayor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ftxtPrecioAlMayor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ftxtPrecioAlMayor.setName("precioalmayor"); // NOI18N
        ftxtPrecioAlMayor.setPreferredSize(new java.awt.Dimension(80, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        pnlPrecios.add(ftxtPrecioAlMayor, gridBagConstraints);

        ftxtPrecioAlMenor.setEditable(false);
        ftxtPrecioAlMenor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new FormatoDecimal("#,##0.00",true))));
        ftxtPrecioAlMenor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ftxtPrecioAlMenor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ftxtPrecioAlMenor.setName("precioalmenor"); // NOI18N
        ftxtPrecioAlMenor.setPreferredSize(new java.awt.Dimension(80, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        pnlPrecios.add(ftxtPrecioAlMenor, gridBagConstraints);

        pnlContenedor.add(pnlPrecios);

        pnlBotones.setOpaque(false);

        bntNuevo.setBackground(new java.awt.Color(51, 153, 255));
        bntNuevo.setMnemonic(KeyEvent.VK_N);
        bntNuevo.setText("Nuevo");
        bntNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntNuevoActionPerformed(evt);
            }
        });
        pnlBotones.add(bntNuevo);

        bntModificar.setBackground(new java.awt.Color(51, 153, 255));
        bntModificar.setText("Modificar");
        bntModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntModificarActionPerformed(evt);
            }
        });
        pnlBotones.add(bntModificar);

        bntGuardar.setBackground(new java.awt.Color(51, 153, 255));
        bntGuardar.setText("Guardar");
        bntGuardar.setEnabled(false);
        bntGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntGuardarActionPerformed(evt);
            }
        });
        pnlBotones.add(bntGuardar);

        bntEliminar.setBackground(new java.awt.Color(51, 153, 255));
        bntEliminar.setText("Eliminar");
        bntEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntEliminarActionPerformed(evt);
            }
        });
        pnlBotones.add(bntEliminar);

        bntCancelar.setBackground(new java.awt.Color(51, 153, 255));
        bntCancelar.setText("Cancelar");
        bntCancelar.setEnabled(false);
        bntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCancelarActionPerformed(evt);
            }
        });
        pnlBotones.add(bntCancelar);

        bntSalir.setBackground(new java.awt.Color(51, 153, 255));
        bntSalir.setText("Salir");
        bntSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSalirActionPerformed(evt);
            }
        });
        pnlBotones.add(bntSalir);

        pnlContenedor.add(pnlBotones);

        getContentPane().add(pnlContenedor, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntSelecFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSelecFotoActionPerformed
        JFileChooser se = new JFileChooser();
        se.setFileSelectionMode(JFileChooser.FILES_ONLY);
        se.setMultiSelectionEnabled(false);
        se.setAccessory(new PanelVistaPrevia(se));
        se.setFileFilter(new FiltraExtensionArchivos());
        se.setFileView(new VistaArchivos());
        int estado = se.showOpenDialog(null);
        if(estado == JFileChooser.APPROVE_OPTION)
        {
            try {
                Thumbnail tn;
                this.imagen = new FileInputStream(se.getSelectedFile());               
                Image icono = ImageIO.read(imagen).getScaledInstance(pnlImagen.getWidth(), pnlImagen.getHeight(), Image.SCALE_AREA_AVERAGING);
                tn = new Thumbnail(icono,System.getProperty("user.dir")+"/"+se.getSelectedFile().getName());
                imagen = tn.generarThumbnail();
                this.pnlImagen.setIcon(new ImageIcon(System.getProperty("user.dir")+"/"+se.getSelectedFile().getName()));
                this.pnlImagen.updateUI();                
                dat = new DatoArchivo(imagen,(int)se.getSelectedFile().length());
                System.out.println(dat.getLongitud());//8
            } catch (IOException ex) {
                Logger.getLogger(IProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_bntSelecFotoActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        cerrarVentana();
    }//GEN-LAST:event_formInternalFrameClosing

    private void bntSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSalirActionPerformed
        cerrarVentana();
    }//GEN-LAST:event_bntSalirActionPerformed

    private void bntNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntNuevoActionPerformed
        habilitarCampos(true,true);
        ECampos.buscarBotones(this.pnlBotones, false, new Component[]{bntGuardar,bntCancelar,bntSalir});
         esActualizacion = false;
         this.tblProductosAlmacen.setModel(new ModeloTablaApertura());
         this.pnlImagen.setIcon(new ImageIcon(this.getClass().getResource("/resources/noimage_es.png")));
    }//GEN-LAST:event_bntNuevoActionPerformed

    private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCancelarActionPerformed
       habilitarCampos(false,true);
       ECampos.buscarBotones(this.pnlBotones, true, new Component[]{bntGuardar,bntCancelar});
       esActualizacion = false;
       this.tblProductosAlmacen.setModel(new ModeloTablaApertura(product));
       this.setValoresCampos(product);
       if(foto != null)
        {
            this.pnlImagen.setIcon(foto);
        }
    }//GEN-LAST:event_bntCancelarActionPerformed

    private void bntModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntModificarActionPerformed
       habilitarCampos(true,false);
        ECampos.buscarBotones(this.pnlBotones, false, new Component[]{bntGuardar,bntCancelar,bntSalir});
        esActualizacion = true;
    }//GEN-LAST:event_bntModificarActionPerformed

    private void bntGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntGuardarActionPerformed

        if(this.txtCodigo.getText().isEmpty())
        {
            JOptionPane.showInternalMessageDialog(this, "Ingrese un codigo para el producto",
                    "No deje campos vacios", JOptionPane.ERROR_MESSAGE);
            txtCodigo.requestFocus();
            return;
        }
        
        if(this.areaNombre.getText().isEmpty())
        {
            JOptionPane.showInternalMessageDialog(this, "Ingrese una descripcion para el Producto",
                    "No deje campos vacios", JOptionPane.ERROR_MESSAGE);
            areaNombre.requestFocus();
            return;
        }
        
        if(this.cmbTipoMoneda.getSelectedItem() == null)
        {
            JOptionPane.showInternalMessageDialog(this, "Seleccione un Tipo de Moneda",
                    "No deje campos vacios", JOptionPane.ERROR_MESSAGE);
            cmbTipoMoneda.requestFocus();
            return;
        }
        if(!esActualizacion)
        {
            if(controllerProducto.existeCodigo(txtCodigo.getText()))
            {
                 JOptionPane.showInternalMessageDialog(this, "El Codigo: "+txtCodigo.getText(),
                    ". Ya ha sido asignado a otro Producto.\n"
                         + "Porfavor Ingrese Otro Codigo", JOptionPane.ERROR_MESSAGE);
                txtCodigo.requestFocus();
                return;
            }
            
            if(!this.txtCodigoBarras.getText().isEmpty())
            {
                if(controllerProducto.existeCodigoBarras(txtCodigoBarras.getText()))
                {
                     JOptionPane.showInternalMessageDialog(this, "El Codigo de Barras: "+txtCodigoBarras.getText(),
                    ". Ya ha sido asignado a otro Producto.\n"
                         + "Porfavor Ingrese Otro Codigo de Barras.\n"
                             + "si desea no ingrese ningun codigo de barras", JOptionPane.ERROR_MESSAGE);
                    txtCodigo.requestFocus();
                    return;
                }
            }
            guardarProducto(false);
        }else{
            
            if(controllerProducto.existeCodigo(txtCodigo.getText(),product.getPrimaryKey()))
            {
                 JOptionPane.showInternalMessageDialog(this, "El Codigo: "+txtCodigo.getText(),
                    ". Ya ha sido asignado a otro Producto.\n"
                         + "Porfavor Ingrese Otro Codigo", JOptionPane.ERROR_MESSAGE);
                txtCodigo.requestFocus();
                return;
            }
            
            if(!this.txtCodigoBarras.getText().isEmpty())
            {
                if(controllerProducto.existeCodigoBarras(txtCodigoBarras.getText(),product.getPrimaryKey()))
                {
                     JOptionPane.showInternalMessageDialog(this, "El Codigo de Barras: "+txtCodigoBarras.getText(),
                    ". Ya ha sido asignado a otro Producto.\n"
                         + "Porfavor Ingrese Otro Codigo de Barras.\n"
                             + "si desea no ingrese ningun codigo de barras", JOptionPane.ERROR_MESSAGE);
                    txtCodigo.requestFocus();
                    return;
                }
            }
            actualizarProductos(false);
        }
       
    }//GEN-LAST:event_bntGuardarActionPerformed

    private void bntEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntEliminarActionPerformed
        //mostrar almacenes que tienen el producto que va a ser eliminado
        PanelListaProductosAlmacen plpa = new PanelListaProductosAlmacen(product);
         int op=0;
        int nu = JOptionPane.showInternalConfirmDialog(this, (plpa.total!=null?plpa:"¿Realmente desea Eliminar\n producto "+product.getNombre()+
             "Con codigo: "+product.getCodigo())
             ,"¿Realmente Desea Eliminar Este Producto?", JOptionPane.YES_NO_CANCEL_OPTION);
         if(nu == JOptionPane.OK_OPTION)
         {         
            CAlmacenProducto cap = new CAlmacenProducto();
            ArrayList<AlmacenProducto> datos = plpa.getDatos();
            for(AlmacenProducto alm:datos)
            {
                cap.eliminacionReal(alm);
            }
            op = this.controllerProducto.eliminacionReal(product);
         }
         if(op > 0)
         {
             JOptionPane.showInternalMessageDialog(this, "Producto Eliminado Correctamente", "Productos Borrado", JOptionPane.INFORMATION_MESSAGE);
         }
    }//GEN-LAST:event_bntEliminarActionPerformed

    private void bntPrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntPrimeroActionPerformed
       controllerProducto.setNumPaginador(0, 50);
       productos = controllerProducto.getRegistros(new Object[]{1});       
       if(productos.isEmpty())
       {    
           this.innerOffSet = 0;
           this.desplazamiento = 0;
           inicializarProducto(productos.get(this.innerOffSet));
       }
       bntPrimero.setEnabled(false);
       bntAnterior.setEnabled(false);
       bntSiguiente.setEnabled(true);
       bntUltimo.setEnabled(true);
    }//GEN-LAST:event_bntPrimeroActionPerformed

    private void bntAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAnteriorActionPerformed
        if(this.innerOffSet == 0)
        {
             this.desplazamiento -= productos.size();
                if(this.desplazamiento<0)
                {
                   this.desplazamiento = 0; 
                }
                controllerProducto.setNumPaginador(this.desplazamiento, 50);                
                ArrayList<Producto> tmp = controllerProducto.getRegistros(new Object[]{1});
                if(tmp.isEmpty())
                {
                  bntAnterior.setEnabled(false);
                  bntPrimero.setEnabled(false);
                }else{
                    productos = tmp;
                    if(this.desplazamiento > 0)
                    {
                       this.innerOffSet = productos.size()-1; 
                    }  
                    
                    if(productos.indexOf(product)==0)
                    {
                        bntAnterior.setEnabled(false);
                        bntPrimero.setEnabled(false);
                    }
                }
                inicializarProducto(productos.get(innerOffSet));
        }else{  
            this.innerOffSet--;
            inicializarProducto(productos.get(innerOffSet));
        }
         bntSiguiente.setEnabled(true);
         bntUltimo.setEnabled(true);
    }//GEN-LAST:event_bntAnteriorActionPerformed

    private void bntSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSiguienteActionPerformed
        System.out.println("indiceeee:\t "+productos.indexOf(innerOffSet));
        if(this.innerOffSet == productos.size()-1)
        {
           this.desplazamiento +=productos.size();
            controllerProducto.setNumPaginador(this.desplazamiento, 50);            
            ArrayList<Producto> tmp = controllerProducto.getRegistros(new Object[]{1});
            if(tmp.isEmpty())
            {                    
                bntSiguiente.setEnabled(false);
                bntUltimo.setEnabled(false);               
            }else{  
                    productos = tmp;
                    innerOffSet = 0;
                    inicializarProducto(productos.get(innerOffSet));
            }
            
        }else{  
            this.innerOffSet++;
            inicializarProducto(productos.get(innerOffSet));
        }
        bntAnterior.setEnabled(true);
        bntPrimero.setEnabled(true);
    }//GEN-LAST:event_bntSiguienteActionPerformed

    private void bntUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntUltimoActionPerformed
       getMostrarUltimoProductos();
       System.out.println("total producto ultim: "+productos.size());
    }//GEN-LAST:event_bntUltimoActionPerformed

    private void bntBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntBuscarActionPerformed
       BuscarProducto pvc = new BuscarProducto();              
       JOptionPane.showInternalOptionDialog(this, pvc, "Buscar Producto: ",JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE, null, new Object[]{pvc.getLbAviso()},null);
       if(pvc.getProducto() != null)
       {
           this.inicializarProducto(pvc.getProducto());
       }
    }//GEN-LAST:event_bntBuscarActionPerformed

    
    private void getMostrarUltimoProductos()
    {             
       int npag = this.totalProductos-50;
       if(npag<0)
       {
           npag=0;
       }
       controllerProducto.setNumPaginador(npag, 50);
       productos = controllerProducto.getRegistros(new Object[]{1});      
      
       if(!productos.isEmpty())
       {        
           this.innerOffSet = productos.size()-1;
           inicializarProducto(productos.get(this.innerOffSet));           
       }
       bntUltimo.setEnabled(false);
       bntSiguiente.setEnabled(false);
       bntAnterior.setEnabled(true);
       bntPrimero.setEnabled(true);
    }
    private void cerrarVentana()
    {
         int nu = JOptionPane.showInternalConfirmDialog(this, "Todos los datos que no se ha guardado "
             + "se perderan.\n"
             + "¿Desea Cerrar esta ventana?", "Cerrar ventana", JOptionPane.YES_NO_CANCEL_OPTION);
         if(nu == JOptionPane.OK_OPTION)
         { 
            iniciarDatos(); 
            this.dispose();
         }
    }
    
    private void guardarProducto(boolean conImagen)
    {
        Producto prd = new Producto();
        setValoresProducto(prd);
        
        //this.setUsuario(pvc.getSeleccionado());
        boolean gr = controllerProducto.guardarRegistro(prd);
        if(gr)
        {
            this.calmacen.setNumPaginador(0, 50);
            ArrayList<Almacen> registros = calmacen.getRegistros();
            CAlmacenProducto cap = new CAlmacenProducto();
            AlmacenProducto ap = null;
            controllerProducto.setNumPaginador(0, 50);
            prd = controllerProducto.getRegistroPorCodigo(prd.getCodigo());
            //registra este producto en todos los almacenes
            for(Almacen al: registros)
            {
                ap = new AlmacenProducto();
                ap.setAlmacen(al);
                ap.setProducto(prd);
                ap.setCantidadActual(0);
                ap.setCantidadInicial(0);
                ap.setFecha(Calendar.getInstance().getTime());
                ap.setTipoManipulacion(AlmacenProducto.TIPO_AGREGAR[3]);
                if(al.getActivo() == 0)
                {
                    ap.setActivo(0);
                }
                cap.guardarRegistro(ap);
                System.out.println("Contaodoo");
            }
            
            PanelAperturaDeInventario pvc = new PanelAperturaDeInventario(prd);
            JLabel aviso = new JLabel();
            aviso.setVisible(false);
            JOptionPane.showInternalOptionDialog(this, pvc, "Registro Guardado. ¿Desea Ingresar Cantidades?",JOptionPane.OK_CANCEL_OPTION,
                                                JOptionPane.QUESTION_MESSAGE, null, new Object[]{aviso},null);
            if(pvc.isAceptado())
            {
                int grabarDatos = pvc.grabarDatos();
                if(grabarDatos>0)
                {
                    JOptionPane.showInternalMessageDialog(this, "Cantidades registradas correctamente", "Cantidades Registradas",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            JOptionPane.showInternalMessageDialog(this, "Producto Registrado Correctamente", "Producto Registrado",
                            JOptionPane.INFORMATION_MESSAGE);            
                  
            totalProductos = controllerProducto.getTotalRegistros();
            habilitarCampos(false,true);
            getMostrarUltimoProductos();            
            ECampos.buscarBotones(this.pnlBotones, true, new Component[]{bntGuardar,bntCancelar});
       }
    }
    
    public void actualizarProductos(boolean conImagen)
    {
       if(product!= null)
       {
         setValoresProducto(product); 
         int gr = controllerProducto.actualizarRegistro(product);
         if(gr != 0)
         {
             PanelAperturaDeInventario pvc = new PanelAperturaDeInventario(product);
            JLabel aviso = new JLabel();
            pvc.setEsActualizacion();
            aviso.setVisible(false);
            JOptionPane.showInternalOptionDialog(this, pvc, "Registro Actualizado. ¿Desea Modifcar las Cantidades existentes en los Almacenes?",JOptionPane.OK_CANCEL_OPTION,
                                                JOptionPane.QUESTION_MESSAGE, null, new Object[]{aviso},null);
            if(pvc.isAceptado())
            {
                int grabarDatos = pvc.grabarDatos();
                if(grabarDatos>0)
                {
                    JOptionPane.showInternalMessageDialog(this, "Cantidades Actualizadas correctamente", "Cantidades Actualizadas",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                JOptionPane.showInternalMessageDialog(this, "Producto Actualizado Correctamente", "Producto Actualizado",
                            JOptionPane.INFORMATION_MESSAGE);
                
            }
            
            habilitarCampos(false,true);
            ECampos.buscarBotones(this.pnlBotones, true, new Component[]{bntGuardar,bntCancelar});
            this.setValoresCampos(product);
            this.esActualizacion = false;       
         }
       }        
    }
    
    public void setValoresProducto(Producto prd)
    {
        prd.setCodigo(this.txtCodigo.getText());
        prd.setCodigoBarras(this.txtCodigoBarras.getText());
        prd.setCodigoDelFabricante(this.txtCodeFabricante.getText());
        prd.setNombre(this.areaNombre.getText());
        prd.setCosto(Double.parseDouble(this.ftxtCosto.getValue().toString()));
        prd.setPrecioAlMayor(Double.parseDouble(this.ftxtPrecioAlMayor.getValue().toString()));
        prd.setPrecioAlMenor(Double.parseDouble(this.ftxtPrecioAlMenor.getValue().toString()));
        prd.setUtilidad(new Integer(this.ftxtUtilidad.getValue().toString()));
        prd.setAplicaIGV(1);
        if(!this.chbAplicarIGV.isSelected())
        {
            prd.setAplicaIGV(0);
        }
        prd.setStockMinimo(new Integer(this.ftxtStockMinimo.getValue().toString()));
        Enumeration<AbstractButton> elements = buttonGroup1.getElements();
        while(elements.hasMoreElements())
        {
            AbstractButton nextElement = elements.nextElement();
            if(nextElement.isSelected())
            {
                prd.setTipo(nextElement.getText());
                System.out.println(nextElement.getText());
                break;
            }
        }
        
        if(this.cmbTipoMoneda.getSelectedItem() != null)
        {
            prd.setIdMoneda(((Moneda)cmbTipoMoneda.getSelectedItem()).getPrimaryKey());
        }
        
        if(imagen != null)
        {
            prd.setImagenDA(dat);
        }
              
        
        if(this.cmbClase.getSelectedIndex() != -1)
        {
            if(this.cmbClase.getSelectedIndex() > 0)
            {
                prd.setIdClase(((SimpleModelo)cmbClase.getSelectedItem()).getPrimaryKey());
            }
        }
        
        if(this.cmbMarca.getSelectedIndex() != -1)
        {
            if(this.cmbMarca.getSelectedIndex() > 0)
            {
                prd.setIdMarca(((SimpleModelo)cmbMarca.getSelectedItem()).getPrimaryKey());
            }
        }
        
        if(this.cmbModelo.getSelectedIndex() != -1)
        {
            if(this.cmbModelo.getSelectedIndex() > 0)
            {
                prd.setIdModelo(((SimpleModelo)cmbModelo.getSelectedItem()).getPrimaryKey());
            }
        }
        
        prd.setUbicacion(this.txtUbicacion.getText());
        prd.setUnidadPrincipal(this.cmbUnidadPrincipal.getSelectedItem().toString());
        prd.setPeso(new Double(this.ftxtPeso.getValue().toString()));
    }
    
    public void habilitarCampos(boolean opcion,boolean limpiar)
    {
        ECampos.setEditableTexto(this.pnlCodes, opcion, null, limpiar, "");
        ECampos.setEditableTexto(this.pnlDetalles, opcion, null, limpiar, "");
        ECampos.setEditableTexto(this.pnlPrecios, opcion, null, limpiar, "");
        ECampos.setEditableTexto(this.pnlCantidades1, opcion, null, limpiar, "");
        Enumeration<AbstractButton> elements = buttonGroup1.getElements();
        while(elements.hasMoreElements())
        {
            elements.nextElement().setEnabled(opcion);
        }
        chbAplicarIGV.setEnabled(opcion);
        ECampos.buscarBotones(this.pnlBotones, opcion, null); 
        this.bntSelecFoto.setEnabled(opcion);
    }
    
    private void setValoresCampos(Producto prd)
    {
       txtCodigo.setText(prd.getCodigo());
       txtCodigoBarras.setText(prd.getCodigoBarras());
       txtCodeFabricante.setText(prd.getCodigoDelFabricante());
       areaNombre.setText(prd.getNombre());
       ftxtCosto.setValue(prd.getCosto());
       ftxtPrecioAlMayor.setValue(prd.getPrecioAlMayor());
       ftxtPrecioAlMenor.setValue(prd.getPrecioAlMenor());
       ftxtUtilidad.setValue(prd.getUtilidad());
       if(prd.getAplicaIGV()==1)
       {
           chbAplicarIGV.setSelected(true);
       }   
       ftxtStockMinimo.setValue(prd.getStockMinimo());
       Enumeration<AbstractButton> elements = buttonGroup1.getElements();
        while(elements.hasMoreElements())
        {
            AbstractButton nextElement = elements.nextElement();
            if(nextElement.getText().equals(prd.getTipo()))
            {
                nextElement.setSelected(true);
                break;
            }
        }
        int ic = cmbTipoMoneda.getItemCount();
        for(int i=0;i<ic;i++)
        {
            Integer primaryKey = ((Moneda)cmbTipoMoneda.getItemAt(i)).getPrimaryKey();
            if(primaryKey == prd.getIdMoneda())
            {
               cmbTipoMoneda.setSelectedIndex(i);
               break;
            }
        }        
        
        setValuesCombos(cmbClase,prd.getIdClase());
        setValuesCombos(cmbMarca,prd.getIdMarca());
        setValuesCombos(cmbModelo,prd.getIdModelo());
        txtUbicacion.setText(prd.getUbicacion());
        cmbUnidadPrincipal.setSelectedItem(prd.getUnidadPrincipal());
        ftxtPeso.setValue(prd.getPeso());
       
    }
    
    private void setValuesCombos(JComboBox cb,Integer id)
    {
       int ic = cb.getItemCount(); 
        for(int i=0;i<ic;i++)
        {
            Integer primaryKey = ((SimpleModelo)cb.getItemAt(i)).getPrimaryKey();
            if(primaryKey == id)
            {
               cb.setSelectedIndex(i);
               break;
            }
        }
    }
    
    public void habilitarBotonesPaginador(boolean opcion)
    {
      ECampos.buscarBotones(this.pnlPaginador, opcion, null);  
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaNombre;
    private elaprendiz.gui.button.ButtonRect bntAnterior;
    private elaprendiz.gui.button.ButtonRect bntBuscar;
    private elaprendiz.gui.button.ButtonRect bntCancelar;
    private elaprendiz.gui.button.ButtonRect bntEliminar;
    private elaprendiz.gui.button.ButtonRect bntGuardar;
    private elaprendiz.gui.button.ButtonRect bntModificar;
    private elaprendiz.gui.button.ButtonRect bntNuevo;
    private elaprendiz.gui.button.ButtonRect bntPrimero;
    private elaprendiz.gui.button.ButtonRect bntSalir;
    private elaprendiz.gui.button.ButtonRect bntSelecFoto;
    private elaprendiz.gui.button.ButtonRect bntSiguiente;
    private elaprendiz.gui.button.ButtonRect bntUltimo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chbAplicarIGV;
    private elaprendiz.gui.comboBox.ComboBoxRectIcon cmbClase;
    private elaprendiz.gui.comboBox.ComboBoxRectIcon cmbMarca;
    private elaprendiz.gui.comboBox.ComboBoxRectIcon cmbModelo;
    private elaprendiz.gui.comboBox.ComboBoxRectIcon cmbTipoMoneda;
    private elaprendiz.gui.comboBox.ComboBoxRect cmbUnidadPrincipal;
    private javax.swing.JFormattedTextField ftxtCosto;
    private javax.swing.JFormattedTextField ftxtPeso;
    private javax.swing.JFormattedTextField ftxtPrecioAlMayor;
    private javax.swing.JFormattedTextField ftxtPrecioAlMenor;
    private javax.swing.JFormattedTextField ftxtStockMinimo;
    private javax.swing.JFormattedTextField ftxtUtilidad;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlCantidades1;
    private javax.swing.JPanel pnlCodes;
    private elaprendiz.gui.panel.PanelImage pnlContenedor;
    private javax.swing.JPanel pnlDetalles;
    private elaprendiz.gui.panel.PanelImage pnlImagen;
    private javax.swing.JPanel pnlPaginador;
    private javax.swing.JPanel pnlPrecios;
    private javax.swing.JRadioButton rbCompomente;
    private javax.swing.JRadioButton rbInsumo;
    private javax.swing.JRadioButton rbProductoTerminado;
    private javax.swing.JTable tblProductosAlmacen;
    private elaprendiz.gui.textField.TextField txtCodeFabricante;
    private elaprendiz.gui.textField.TextField txtCodigo;
    private elaprendiz.gui.textField.TextField txtCodigoBarras;
    private elaprendiz.gui.textField.TextField txtUbicacion;
    // End of variables declaration//GEN-END:variables
}
