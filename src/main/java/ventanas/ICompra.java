
package ventanas;

import config.AppConfig;
import controllers.CAlmacenProducto;
import controllers.CCompra;
import controllers.CDetalleCompra;
import controllers.CKardex;
import controllers.CMoneda;
import controllers.CProveedor;
import core.JAbstractModelBD;
import modelbd.AlmacenProducto;
import modelbd.Compra;
import modelbd.DetalleCompra;
import modelbd.Kardex;
import modelbd.Moneda;
import modelbd.Proveedor;
import modelgui.ModeloTablaDetalleCompra;
import modelgui.ModeloTablaProveedor;
import util.AccionTableCellRenderer;
import util.CeldaAccionEditor;
import util.CellEditorSpinnerCompra;
import util.ECampos;
import util.TableCellFormatter;
import util.text.FormatoDecimal;
import ventanas.buscar.BuscarCompra;
import ventanas.buscar.BuscarProveedor;
import ventanas.paneles.PanelAccion;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 *
 * @author Código Lite <https://codigolite.com> aun falta el boton eliminar
 */
public class ICompra extends javax.swing.JInternalFrame {

	/**
	 * Creates new form ICompra
	 */
	private DefaultComboBoxModel mTipoDocumento;
	private DefaultComboBoxModel mMoneda;
	private DefaultComboBoxModel mProveedor;

	private CMoneda controllerMoneda;
	private CProveedor controllerProveedor;
	private CCompra controllerCompra;
	private ModeloTablaDetalleCompra mtdc;

	private ArrayList<Compra> compras = new ArrayList<Compra>();

	private Double subTotal = 0.0;
	private Double igv = 0.0;

	private boolean esActualizacion = false;

	private Proveedor proveedor;
	private Compra compra;

	public ICompra() {
		initComponents();
		controllerCompra = new CCompra();
		getYmostrarCompras();
	}

	// verificar o corregir objeto compra
	private void getYmostrarCompras() {
		controllerCompra = new CCompra();
		compras = controllerCompra.getRegistros();
		controllerMoneda = new CMoneda();
		controllerProveedor = new CProveedor();
		mTipoDocumento = new DefaultComboBoxModel(Compra.TIPOSDOC);
		this.cmbTipoDocumento.setModel(mTipoDocumento);
		mProveedor = new DefaultComboBoxModel(controllerProveedor.getRegistros(new Object[] { 1 }).toArray());
		this.cmbProveedor.setModel(mProveedor);
		mMoneda = new DefaultComboBoxModel(controllerMoneda.getRegistros(new Object[] { 1 }).toArray());
		this.cmbMoneda.setModel(mMoneda);
		if (compras.size() > 0) {
			compra = compras.get(0);
			mtdc = new ModeloTablaDetalleCompra(compras.get(0));
		} else {
			mtdc = new ModeloTablaDetalleCompra();
		}

		this.tblProductos.setModel(mtdc);
		this.tblProductos.setDefaultRenderer(PanelAccion.class, new AccionTableCellRenderer(this));
		TableColumn tc = this.tblProductos.getColumnModel().getColumn(this.tblProductos.getColumnCount() - 1);
		tc.setCellEditor(new CeldaAccionEditor(this));
		setEventTable();
		this.tblProductos.getColumnModel().getColumn(0).setPreferredWidth(400);
		CellEditorSpinnerCompra cnt = new CellEditorSpinnerCompra(1, this);
		this.tblProductos.getColumnModel().getColumn(1).setCellEditor(cnt);
		this.tblProductos.getColumnModel().getColumn(1).setCellRenderer(new TableCellFormatter(null));
		this.tblProductos.getColumnModel().getColumn(2).setCellRenderer(new TableCellFormatter());
		this.tblProductos.getColumnModel().getColumn(3).setCellRenderer(new TableCellFormatter());
		this.tblProductos.getColumnModel().getColumn(3).setCellEditor(new TableCellFormatterEditor());
		this.tblProductos.getColumnModel().getColumn(4).setCellRenderer(new TableCellFormatter());
		// this.tblProductos.getColumnModel().getColumn(2).setCellEditor(new
		// CeldaSpinnerEditor(CeldaSpinnerEditor.GENERIC0,1));
		// corregir actualizacion de importe cuando se ingresa descuento
		this.cmbMoneda.setSelectedItem(AppConfig.getMoneda());
		if (compras.size() > 0) {
			this.setValores(compras.get(0));
		}
	}

	public boolean isEsActualizacion() {
		return esActualizacion;
	}

	private void setEventTable() {
		TableModelListener tml = new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				tableHandlerEvent(e);
			}
		};
		this.tblProductos.getModel().addTableModelListener(tml);

	}

	private void tableHandlerEvent(TableModelEvent e) {
		setTotales();
	}

	public void setTotales() {
		if (this.rbIncluyeIGV.isSelected()) {
			setTotalesConIGV();
		}

		if (this.rbNoIncluyeIgv.isSelected()) {
			setTotalesSinnIGV();
		}

	}

	private void setTotalesConIGV() {
		this.igv = (mtdc.getSubTotal() * 18) / 118;
		this.subTotal = mtdc.getSubTotal() - this.igv;
		this.ftfSubtotal.setValue(this.subTotal);
		this.ftfIgv.setValue(this.igv);
		this.ftfTotal.setValue(mtdc.getSubTotal());
		this.ftfItems.setValue(mtdc.getNumItems());
	}

	private void setTotalesSinnIGV() {
		this.igv = (mtdc.getSubTotal() * 0.18);
		this.subTotal = mtdc.getSubTotal();
		this.ftfSubtotal.setValue(this.subTotal);
		this.ftfIgv.setValue(this.igv);
		this.ftfTotal.setValue(mtdc.getSubTotal() + this.igv);
		this.ftfItems.setValue(mtdc.getNumItems());
	}

	private void guardar() {
		compra = new Compra();
		compra.setIdMoneda((Moneda) this.cmbMoneda.getSelectedItem());
		compra.setIdProveedor(proveedor);
		compra.setIdUsuario(AppConfig.getUsuario());
		compra.setFecha(this.dcFecha.getCalendar().getTime());
		compra.setIgv(18);
		compra.setNumeroDocumento(this.txtNumeroDocumento.getText());
		compra.setNumeroItems((Integer) this.ftfItems.getValue());
		compra.setObservaciones(this.txtObservaciones.getText());
		compra.setSubtotla((Double) this.ftfSubtotal.getValue());
		compra.setTotal((Double) this.ftfTotal.getValue());
		compra.setTipoDocumento(this.cmbTipoDocumento.getSelectedItem().toString());
		compra.setDetalleCompra(mtdc.getDetallesCompra());
		if (this.rbCancelado.isSelected()) {
			compra.setEstado(Compra.ESTADOS[0]);
		} else {
			compra.setEstado(Compra.ESTADOS[1]);
		}
		boolean rp = controllerCompra.guardarRegistro(compra);
		CDetalleCompra crtlDc = new CDetalleCompra();
		if (rp) {
			ArrayList<DetalleCompra> dtcs = compra.getDetalleCompra();
			CAlmacenProducto controllerAp = null;
			CKardex controllerKardex = new CKardex();
			Kardex kardex = null;
			// verificar codigo
			for (DetalleCompra item : dtcs) {
				item.setIdcompra(compra);
				crtlDc.guardarRegistro(item);
				controllerAp = new CAlmacenProducto();
				AlmacenProducto reg = controllerAp.getRegistro(AppConfig.getAlmacen().getPrimaryKey(),
						item.getIdProducto().getPrimaryKey());
				int cnt = 0;
				if (reg != null) {
					cnt = reg.getCantidadActual();
					cnt = cnt + item.getCantidad();
					reg.setCantidadActual(cnt);
					controllerAp.actualizarRegistro(reg);
				}
				kardex = new Kardex();
				kardex.setProducto(item.getIdProducto());
				kardex.setFecha(compra.getFecha());
				kardex.setDocumento(Kardex.COMPRA);
				kardex.setNumeroDeDocumento(compra.getNumeroDocumento());
				kardex.setEntrada(item.getCantidad());
				kardex.setSalida(0);
				kardex.setStockActual(controllerAp.getCantidadProducto(item.getIdProducto().getPrimaryKey()));
				kardex.setPrecio(item.getIdProducto().getCosto());
				kardex.setValor(item.getIdProducto().getCosto());
				kardex.setValorTotal(kardex.getStockActual() * kardex.getValor());
				controllerKardex.guardarRegistro(kardex);
			}

			JOptionPane.showInternalMessageDialog(this, "" + compra.getTipoDocumento() + "grabada.",
					"Documento guardado", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void actualizar() {
		if (compra == null)
			return;
		compra.setIdMoneda((Moneda) this.cmbMoneda.getSelectedItem());
		compra.setIdProveedor(proveedor);
		compra.setIdUsuario(AppConfig.getUsuario());
		compra.setFecha(this.dcFecha.getCalendar().getTime());
		compra.setIgv(18);
		compra.setNumeroDocumento(this.txtNumeroDocumento.getText());
		compra.setNumeroItems((Integer) this.ftfItems.getValue());
		compra.setObservaciones(this.txtObservaciones.getText());
		compra.setSubtotla((Double) this.ftfSubtotal.getValue());
		compra.setTotal((Double) this.ftfTotal.getValue());
		compra.setTipoDocumento(this.cmbTipoDocumento.getSelectedItem().toString());
		compra.setDetalleCompra(mtdc.getDetallesCompra());
		if (this.rbCancelado.isSelected()) {
			compra.setEstado(Compra.ESTADOS[0]);
		} else {
			compra.setEstado(Compra.ESTADOS[1]);
		}
		int v = controllerCompra.actualizarRegistro(compra);
		boolean rp = false;
		if (v != 0)
			rp = true;
		else
			return;// error al actualizar
		CDetalleCompra crtlDc = new CDetalleCompra();
		if (rp) {

			CKardex controllerKardex = new CKardex();
			CAlmacenProducto controllerAp = null;

			// eliminacion de productos quitados de la compra
			ArrayList<DetalleCompra> productosEliminados = mtdc.getProductosEliminados();
			if (productosEliminados != null) {

				Kardex kardexEliminadosDeCompras = null;
				if (!productosEliminados.isEmpty()) {
					for (Iterator<DetalleCompra> it = productosEliminados.iterator(); it.hasNext();) {
						DetalleCompra item = it.next();

						crtlDc.eliminacionReal(item);

						controllerAp = new CAlmacenProducto();
						AlmacenProducto reg = controllerAp.getRegistro(AppConfig.getAlmacen().getPrimaryKey(),
								item.getIdProducto().getPrimaryKey());
						int cnt = 0;
						if (reg != null) {
							cnt = reg.getCantidadActual();
							cnt = cnt - item.getCantidad();
							reg.setCantidadActual(cnt);
							controllerAp.actualizarRegistro(reg);
						}

						kardexEliminadosDeCompras = new Kardex();
						kardexEliminadosDeCompras.setProducto(item.getIdProducto());
						kardexEliminadosDeCompras.setFecha(Calendar.getInstance().getTime());
						kardexEliminadosDeCompras.setDocumento(Kardex.PRODUCTO_ELIMINADO_COMPRA);
						kardexEliminadosDeCompras.setNumeroDeDocumento(compra.getNumeroDocumento());
						kardexEliminadosDeCompras.setEntrada(item.getCantidad());
						kardexEliminadosDeCompras.setSalida(0);
						kardexEliminadosDeCompras
								.setStockActual(controllerAp.getCantidadProducto(item.getIdProducto().getPrimaryKey()));
						kardexEliminadosDeCompras.setPrecio(item.getIdProducto().getCosto());
						kardexEliminadosDeCompras.setValor(item.getIdProducto().getCosto());
						kardexEliminadosDeCompras.setValorTotal(
								kardexEliminadosDeCompras.getStockActual() * kardexEliminadosDeCompras.getValor());
						controllerKardex.guardarRegistro(kardexEliminadosDeCompras);

					}
				}
			}
			// ------------------
			ArrayList<DetalleCompra> dtcs = compra.getDetalleCompra();

			Kardex kardex = null;
			// verificar codigo
			for (DetalleCompra item : dtcs) {
				item.setIdcompra(compra);
				// si exites el producto en detalle de compra actualiza
				// si no existe guardar
				if (crtlDc.existeProductoEnCompra(item.getIdcompra().getPrimaryKey(),
						item.getIdProducto().getPrimaryKey()))
					crtlDc.actualizarRegistro(item);
				else
					crtlDc.guardarRegistro(item);

				controllerAp = new CAlmacenProducto();
				AlmacenProducto reg = controllerAp.getRegistro(AppConfig.getAlmacen().getPrimaryKey(),
						item.getIdProducto().getPrimaryKey());
				int cnt = 0;
				if (reg != null) {
					cnt = reg.getCantidadActual();
					cnt = cnt + item.getCantidad();
					reg.setCantidadActual(cnt);
					controllerAp.actualizarRegistro(reg);
				}
				kardex = new Kardex();
				kardex.setProducto(item.getIdProducto());
				kardex.setFecha(compra.getFecha());
				kardex.setDocumento(Kardex.COMPRA_ACTUALIZACION);
				kardex.setNumeroDeDocumento(compra.getNumeroDocumento());
				kardex.setEntrada(item.getCantidad());
				kardex.setSalida(0);
				kardex.setStockActual(controllerAp.getCantidadProducto(item.getIdProducto().getPrimaryKey()));
				kardex.setPrecio(item.getIdProducto().getCosto());
				kardex.setValor(item.getIdProducto().getCosto());
				kardex.setValorTotal(kardex.getStockActual() * kardex.getValor());
				controllerKardex.guardarRegistro(kardex);
			}

			JOptionPane.showInternalMessageDialog(this, "" + compra.getTipoDocumento() + "Actualizada.",
					"Documento actualizado correctamente", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void setValores(Compra cmp) {
		this.ftfIgv.setValue(cmp.getIgv());
		this.ftfItems.setValue(cmp.getNumeroItems());
		this.ftfSubtotal.setValue(cmp.getSubtotla());
		this.ftfTotal.setValue(cmp.getTotal());
		this.txtNumeroDocumento.setText(cmp.getNumeroDocumento());
		this.dcFecha.setDate(cmp.getFecha());
		this.cmbProveedor.setSelectedItem(cmp.getIdProveedor());
		this.txtRuc.setText(cmp.getIdProveedor().getRuc());
		this.txtDireccion.setText(cmp.getIdProveedor().getDireccion());
		this.txtObservaciones.setText(cmp.getObservaciones());
		this.txtUsuario.setText(AppConfig.getUsuario().toString());
	}

	public void reniciar() {
		ECampos.buscarBotones(this.pnlActionButtons, true, null);
		ECampos.buscarBotones(this.pnlActionButtons, false,
				new Component[] { this.bntNuevo, this.bntModificar, this.bntEliminar, this.bntSalir });
		ECampos.buscarBotones(this.pnlBuscar, false, null);
		ECampos.buscarBotones(this.pnlEstado, false, null);
		ECampos.buscarBotones(this.pnlHerramientas, false, null);
		ECampos.buscarBotones(this.pnlIGV, false, null);

		this.cmbProveedor.setEnabled(false);
		this.bntBuscarProveedor.setEnabled(false);
		this.txtObservaciones.setEditable(false);
		this.tblProductos.setEnabled(false);
		this.txtNumeroDocumento.setEditable(false);
		this.cmbMoneda.setEnabled(false);
		this.ftfCambio.setEditable(false);

		this.ftfItems.setValue(0);
		this.ftfSubtotal.setValue(0.00);
		this.ftfIgv.setValue(0.00);
		this.ftfTotal.setValue(0.00);
		this.txtNumeroDocumento.setText("");
		this.dcFecha.setDate(Calendar.getInstance().getTime());

		// this.mtdc.limpiar();
		this.mtdc = new ModeloTablaDetalleCompra();
		this.tblProductos.setModel(mtdc);
		this.tblProductos.setDefaultRenderer(PanelAccion.class, new AccionTableCellRenderer(this));
		TableColumn tc = this.tblProductos.getColumnModel().getColumn(this.tblProductos.getColumnCount() - 1);
		tc.setCellEditor(new CeldaAccionEditor(this));
		setEventTable();
		this.tblProductos.getColumnModel().getColumn(0).setPreferredWidth(400);
		CellEditorSpinnerCompra cnt = new CellEditorSpinnerCompra(1, this);
		this.tblProductos.getColumnModel().getColumn(1).setCellEditor(cnt);
		this.tblProductos.getColumnModel().getColumn(1).setCellRenderer(new TableCellFormatter(null));
		this.tblProductos.getColumnModel().getColumn(2).setCellRenderer(new TableCellFormatter());
		this.tblProductos.getColumnModel().getColumn(3).setCellRenderer(new TableCellFormatter());
		this.tblProductos.getColumnModel().getColumn(3).setCellEditor(new TableCellFormatterEditor());
		this.tblProductos.getColumnModel().getColumn(4).setCellRenderer(new TableCellFormatter());
		this.txtUsuario.setText("");
		setProveedor(null);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		buttonGroup1 = new javax.swing.ButtonGroup();
		buttonGroup2 = new javax.swing.ButtonGroup();
		panelImage1 = new elaprendiz.gui.panel.PanelImage();
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		cmbTipoDocumento = new elaprendiz.gui.comboBox.ComboBoxRect();
		txtNumeroDocumento = new elaprendiz.gui.textField.TextField();
		dcFecha = new com.toedter.calendar.JDateChooser();
		cmbMoneda = new elaprendiz.gui.comboBox.ComboBoxRect();
		ftfCambio = new javax.swing.JFormattedTextField();
		jPanel6 = new javax.swing.JPanel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		cmbProveedor = new elaprendiz.gui.comboBox.ComboBoxRectIcon();
		txtRuc = new elaprendiz.gui.textField.TextField();
		txtDireccion = new elaprendiz.gui.textField.TextField();
		txtObservaciones = new elaprendiz.gui.textField.TextField();
		txtUsuario = new elaprendiz.gui.textField.TextFieldRectIcon();
		bntBuscarProveedor = new javax.swing.JButton();
		jPanel2 = new javax.swing.JPanel();
		jPanel4 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblProductos = new javax.swing.JTable();
		jPanel7 = new javax.swing.JPanel();
		jPanel8 = new javax.swing.JPanel();
		jLabel11 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		jLabel13 = new javax.swing.JLabel();
		ftfSubtotal = new javax.swing.JFormattedTextField();
		ftfIgv = new javax.swing.JFormattedTextField();
		ftfTotal = new javax.swing.JFormattedTextField();
		jPanel9 = new javax.swing.JPanel();
		pnlHerramientas = new javax.swing.JPanel();
		bntImprimir = new elaprendiz.gui.button.ButtonRect();
		bntImportar = new elaprendiz.gui.button.ButtonRect();
		bntCalculadora = new elaprendiz.gui.button.ButtonRect();
		pnlEstado = new javax.swing.JPanel();
		rbCancelado = new javax.swing.JRadioButton();
		rbPendiente = new javax.swing.JRadioButton();
		pnlIGV = new javax.swing.JPanel();
		rbIncluyeIGV = new javax.swing.JRadioButton();
		rbNoIncluyeIgv = new javax.swing.JRadioButton();
		jPanel12 = new javax.swing.JPanel();
		jLabel14 = new javax.swing.JLabel();
		ftfItems = new javax.swing.JFormattedTextField();
		pnlActionButtons = new javax.swing.JPanel();
		bntNuevo = new elaprendiz.gui.button.ButtonRect();
		bntModificar = new elaprendiz.gui.button.ButtonRect();
		bntGuardar = new elaprendiz.gui.button.ButtonRect();
		bntEliminar = new elaprendiz.gui.button.ButtonRect();
		bntCancelar = new elaprendiz.gui.button.ButtonRect();
		bntSalir = new elaprendiz.gui.button.ButtonRect();
		pnlBuscar = new javax.swing.JPanel();
		bntPrimero = new elaprendiz.gui.button.ButtonRect();
		bntAnterior = new elaprendiz.gui.button.ButtonRect();
		bntBuscar = new elaprendiz.gui.button.ButtonRect();
		bntSiguiente = new elaprendiz.gui.button.ButtonRect();
		bntUltimo = new elaprendiz.gui.button.ButtonRect();

		setClosable(true);
		setIconifiable(true);
		setTitle("Compra");
		setToolTipText("");

		panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/fondoazulceleste.jpg"))); // NOI18N
		panelImage1.setLayout(new javax.swing.BoxLayout(panelImage1, javax.swing.BoxLayout.PAGE_AXIS));

		jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		jPanel1.setOpaque(false);
		jPanel1.setLayout(new java.awt.GridBagLayout());

		jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel1.setText("Documento:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 7, 5);
		jPanel1.add(jLabel1, gridBagConstraints);

		jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel2.setText("Numero:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 7, 5);
		jPanel1.add(jLabel2, gridBagConstraints);

		jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel3.setText("Fecha:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 7, 5);
		jPanel1.add(jLabel3, gridBagConstraints);

		jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel4.setText("Moneda:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 7, 5);
		jPanel1.add(jLabel4, gridBagConstraints);

		jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel5.setText("Cambio:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 7, 5);
		jPanel1.add(jLabel5, gridBagConstraints);

		cmbTipoDocumento.setEnabled(false);
		cmbTipoDocumento.setPreferredSize(new java.awt.Dimension(140, 22));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 7, 0);
		jPanel1.add(cmbTipoDocumento, gridBagConstraints);

		txtNumeroDocumento.setEditable(false);
		txtNumeroDocumento.setPreferredSize(new java.awt.Dimension(120, 22));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 7, 0);
		jPanel1.add(txtNumeroDocumento, gridBagConstraints);

		dcFecha.setDate(Calendar.getInstance().getTime());
		dcFecha.setEnabled(false);
		dcFecha.setPreferredSize(new java.awt.Dimension(120, 22));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 7, 0);
		jPanel1.add(dcFecha, gridBagConstraints);

		cmbMoneda.setEnabled(false);
		cmbMoneda.setPreferredSize(new java.awt.Dimension(80, 22));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 7;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 7, 0);
		jPanel1.add(cmbMoneda, gridBagConstraints);

		ftfCambio.setEditable(false);
		ftfCambio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
				new javax.swing.text.NumberFormatter(new FormatoDecimal("#,##0.00", true))));
		ftfCambio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		ftfCambio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		ftfCambio.setPreferredSize(new java.awt.Dimension(80, 22));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		jPanel1.add(ftfCambio, gridBagConstraints);

		panelImage1.add(jPanel1);

		jPanel6.setOpaque(false);
		jPanel6.setLayout(new java.awt.GridBagLayout());

		jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel6.setText("Proveedor:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
		gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
		jPanel6.add(jLabel6, gridBagConstraints);

		jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel7.setText("Direccion:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
		gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
		jPanel6.add(jLabel7, gridBagConstraints);

		jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel8.setText("Responsable:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
		gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
		jPanel6.add(jLabel8, gridBagConstraints);

		jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel9.setText("Observaciones:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 5);
		jPanel6.add(jLabel9, gridBagConstraints);

		jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel10.setText("RUC:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(4, 5, 0, 4);
		jPanel6.add(jLabel10, gridBagConstraints);

		cmbProveedor.setEnabled(false);
		cmbProveedor.setPreferredSize(new java.awt.Dimension(420, 22));
		cmbProveedor.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cmbProveedorItemStateChanged(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
		gridBagConstraints.insets = new java.awt.Insets(4, 5, 0, 0);
		jPanel6.add(cmbProveedor, gridBagConstraints);

		txtRuc.setEditable(false);
		txtRuc.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
		txtRuc.setPreferredSize(new java.awt.Dimension(120, 21));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
		jPanel6.add(txtRuc, gridBagConstraints);

		txtDireccion.setEditable(false);
		txtDireccion.setPreferredSize(new java.awt.Dimension(615, 22));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 7;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
		gridBagConstraints.insets = new java.awt.Insets(4, 5, 0, 0);
		jPanel6.add(txtDireccion, gridBagConstraints);

		txtObservaciones.setEditable(false);
		txtObservaciones.setPreferredSize(new java.awt.Dimension(450, 22));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
		gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
		jPanel6.add(txtObservaciones, gridBagConstraints);

		txtUsuario.setEditable(false);
		txtUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/user_blue_32.png"))); // NOI18N
		txtUsuario.setPreferredSize(new java.awt.Dimension(210, 22));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
		gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 0);
		jPanel6.add(txtUsuario, gridBagConstraints);

		bntBuscarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/search32.png"))); // NOI18N
		bntBuscarProveedor.setToolTipText("Buscar Proveedor");
		bntBuscarProveedor.setEnabled(false);
		bntBuscarProveedor.setPreferredSize(new java.awt.Dimension(28, 20));
		bntBuscarProveedor
				.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/search32select.png"))); // NOI18N
		bntBuscarProveedor
				.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/search32over.png"))); // NOI18N
		bntBuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntBuscarProveedorActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
		jPanel6.add(bntBuscarProveedor, gridBagConstraints);

		panelImage1.add(jPanel6);

		jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
		jPanel2.setOpaque(false);
		jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

		jPanel4.setLayout(new java.awt.BorderLayout());

		jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 150));

		tblProductos
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		tblProductos.setCellSelectionEnabled(true);
		tblProductos.setEnabled(false);
		tblProductos.setRowHeight(24);
		tblProductos.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				tblProductosFocusLost(evt);
			}
		});
		jScrollPane1.setViewportView(tblProductos);

		jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

		jPanel2.add(jPanel4);

		jPanel7.setOpaque(false);
		jPanel7.setLayout(new java.awt.BorderLayout());

		jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		jPanel8.setLayout(new java.awt.GridBagLayout());

		jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel11.setText("SubTotal:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
		jPanel8.add(jLabel11, gridBagConstraints);

		jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel12.setText("IGV:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
		jPanel8.add(jLabel12, gridBagConstraints);

		jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel13.setText("Total:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
		jPanel8.add(jLabel13, gridBagConstraints);

		ftfSubtotal.setEditable(false);
		ftfSubtotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		ftfSubtotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
				new javax.swing.text.NumberFormatter(new FormatoDecimal("#,##0.00", true))));
		ftfSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		ftfSubtotal.setText("0.0");
		ftfSubtotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		ftfSubtotal.setPreferredSize(new java.awt.Dimension(80, 22));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
		jPanel8.add(ftfSubtotal, gridBagConstraints);

		ftfIgv.setEditable(false);
		ftfIgv.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		ftfIgv.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
				new javax.swing.text.NumberFormatter(new FormatoDecimal("#,##0.00", true))));
		ftfIgv.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		ftfIgv.setText("0.0");
		ftfIgv.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		ftfIgv.setPreferredSize(new java.awt.Dimension(80, 22));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
		jPanel8.add(ftfIgv, gridBagConstraints);

		ftfTotal.setEditable(false);
		ftfTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		ftfTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
				new javax.swing.text.NumberFormatter(new FormatoDecimal("#,##0.00", true))));
		ftfTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		ftfTotal.setText("0.0");
		ftfTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		ftfTotal.setPreferredSize(new java.awt.Dimension(80, 22));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
		jPanel8.add(ftfTotal, gridBagConstraints);

		jPanel7.add(jPanel8, java.awt.BorderLayout.EAST);

		jPanel9.setLayout(new javax.swing.BoxLayout(jPanel9, javax.swing.BoxLayout.LINE_AXIS));

		pnlHerramientas.setBorder(javax.swing.BorderFactory.createTitledBorder("Herramientas"));
		pnlHerramientas.setLayout(new java.awt.GridLayout(3, 0, 0, 2));

		bntImprimir.setBackground(new java.awt.Color(51, 153, 255));
		bntImprimir.setText("Imprimir");
		bntImprimir.setEnabled(false);
		bntImprimir.setPreferredSize(new java.awt.Dimension(115, 25));
		pnlHerramientas.add(bntImprimir);

		bntImportar.setBackground(new java.awt.Color(51, 153, 255));
		bntImportar.setText("Importar");
		bntImportar.setEnabled(false);
		pnlHerramientas.add(bntImportar);

		bntCalculadora.setBackground(new java.awt.Color(51, 153, 255));
		bntCalculadora.setText("Calculadora");
		bntCalculadora.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntCalculadoraActionPerformed(evt);
			}
		});
		pnlHerramientas.add(bntCalculadora);

		jPanel9.add(pnlHerramientas);

		pnlEstado.setBorder(javax.swing.BorderFactory.createTitledBorder("Estado"));
		pnlEstado.setLayout(new javax.swing.BoxLayout(pnlEstado, javax.swing.BoxLayout.PAGE_AXIS));

		buttonGroup1.add(rbCancelado);
		rbCancelado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		rbCancelado.setSelected(true);
		rbCancelado.setText("Cancelado");
		rbCancelado.setEnabled(false);
		pnlEstado.add(rbCancelado);

		buttonGroup1.add(rbPendiente);
		rbPendiente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		rbPendiente.setText("Pendiente de Pago");
		rbPendiente.setEnabled(false);
		pnlEstado.add(rbPendiente);

		jPanel9.add(pnlEstado);

		pnlIGV.setBorder(javax.swing.BorderFactory.createTitledBorder("Incluye IVA/IGV"));
		pnlIGV.setLayout(new javax.swing.BoxLayout(pnlIGV, javax.swing.BoxLayout.PAGE_AXIS));

		buttonGroup2.add(rbIncluyeIGV);
		rbIncluyeIGV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		rbIncluyeIGV.setSelected(true);
		rbIncluyeIGV.setText("Incluye IGV");
		rbIncluyeIGV.setEnabled(false);
		rbIncluyeIGV.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rbIncluyeIGVActionPerformed(evt);
			}
		});
		pnlIGV.add(rbIncluyeIGV);

		buttonGroup2.add(rbNoIncluyeIgv);
		rbNoIncluyeIgv.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		rbNoIncluyeIgv.setText("No Incluye IGV");
		rbNoIncluyeIgv.setEnabled(false);
		rbNoIncluyeIgv.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rbNoIncluyeIgvActionPerformed(evt);
			}
		});
		pnlIGV.add(rbNoIncluyeIgv);

		jPanel9.add(pnlIGV);

		jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles"));
		jPanel12.setPreferredSize(new java.awt.Dimension(80, 88));
		jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel14.setText("Items:");
		jLabel14.setPreferredSize(new java.awt.Dimension(46, 20));
		jPanel12.add(jLabel14);

		ftfItems.setEditable(false);
		ftfItems.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		ftfItems.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
				new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
		ftfItems.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		ftfItems.setPreferredSize(new java.awt.Dimension(120, 20));
		jPanel12.add(ftfItems);

		jPanel9.add(jPanel12);

		jPanel7.add(jPanel9, java.awt.BorderLayout.CENTER);

		jPanel2.add(jPanel7);

		panelImage1.add(jPanel2);

		pnlActionButtons.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 153, 255), 1, true));
		pnlActionButtons.setOpaque(false);

		bntNuevo.setBackground(new java.awt.Color(51, 153, 255));
		bntNuevo.setText("Nuevo");
		bntNuevo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntNuevoActionPerformed(evt);
			}
		});
		pnlActionButtons.add(bntNuevo);

		bntModificar.setBackground(new java.awt.Color(51, 153, 255));
		bntModificar.setText("Modificar");
		bntModificar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntModificarActionPerformed(evt);
			}
		});
		pnlActionButtons.add(bntModificar);

		bntGuardar.setBackground(new java.awt.Color(51, 153, 255));
		bntGuardar.setText("Guardar");
		bntGuardar.setEnabled(false);
		bntGuardar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntGuardarActionPerformed(evt);
			}
		});
		pnlActionButtons.add(bntGuardar);

		bntEliminar.setBackground(new java.awt.Color(51, 153, 255));
		bntEliminar.setText("Eliminar");
		bntEliminar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntEliminarActionPerformed(evt);
			}
		});
		pnlActionButtons.add(bntEliminar);

		bntCancelar.setBackground(new java.awt.Color(51, 153, 255));
		bntCancelar.setText("Cancelar");
		bntCancelar.setEnabled(false);
		bntCancelar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntCancelarActionPerformed(evt);
			}
		});
		pnlActionButtons.add(bntCancelar);

		bntSalir.setBackground(new java.awt.Color(51, 153, 255));
		bntSalir.setText("Salir");
		bntSalir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntSalirActionPerformed(evt);
			}
		});
		pnlActionButtons.add(bntSalir);

		panelImage1.add(pnlActionButtons);

		getContentPane().add(panelImage1, java.awt.BorderLayout.CENTER);

		pnlBuscar.setOpaque(false);

		bntPrimero.setBackground(new java.awt.Color(102, 204, 0));
		bntPrimero.setText("<");
		bntPrimero.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntPrimeroActionPerformed(evt);
			}
		});
		pnlBuscar.add(bntPrimero);

		bntAnterior.setBackground(new java.awt.Color(102, 204, 0));
		bntAnterior.setText("<<");
		bntAnterior.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntAnteriorActionPerformed(evt);
			}
		});
		pnlBuscar.add(bntAnterior);

		bntBuscar.setBackground(new java.awt.Color(102, 204, 0));
		bntBuscar.setText("Buscar");
		bntBuscar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntBuscarActionPerformed(evt);
			}
		});
		pnlBuscar.add(bntBuscar);

		bntSiguiente.setBackground(new java.awt.Color(102, 204, 0));
		bntSiguiente.setText(">>");
		bntSiguiente.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntSiguienteActionPerformed(evt);
			}
		});
		pnlBuscar.add(bntSiguiente);

		bntUltimo.setBackground(new java.awt.Color(102, 204, 0));
		bntUltimo.setText(">");
		bntUltimo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntUltimoActionPerformed(evt);
			}
		});
		pnlBuscar.add(bntUltimo);

		getContentPane().add(pnlBuscar, java.awt.BorderLayout.PAGE_START);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void bntBuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntBuscarProveedorActionPerformed
		BuscarProveedor pvc = new BuscarProveedor(ModeloTablaProveedor.ACTIVOS);
		JLabel aviso = pvc.getAviso();
		String msj = "Lista de Proveedores";
		JOptionPane.showInternalOptionDialog(this, pvc, msj, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, new Object[] { aviso }, null);

		if (pvc.getProveedor() != null) {
			// setProveedor((Proveedor)pvc.getProveedor());
			this.cmbProveedor.setSelectedItem(pvc.getProveedor());
		}
	}// GEN-LAST:event_bntBuscarProveedorActionPerformed

	private void cmbProveedorItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_cmbProveedorItemStateChanged
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			Proveedor prv = (Proveedor) this.cmbProveedor.getSelectedItem();
			this.setProveedor(prv);
		}
	}// GEN-LAST:event_cmbProveedorItemStateChanged

	private void rbIncluyeIGVActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbIncluyeIGVActionPerformed
		setTotalesConIGV();
	}// GEN-LAST:event_rbIncluyeIGVActionPerformed

	private void rbNoIncluyeIgvActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbNoIncluyeIgvActionPerformed
		setTotalesSinnIGV();
	}// GEN-LAST:event_rbNoIncluyeIgvActionPerformed

	private void bntNuevoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntNuevoActionPerformed
		esActualizacion = false;
		ECampos.buscarBotones(this.pnlActionButtons, false, null);
		ECampos.buscarBotones(this.pnlActionButtons, true,
				new Component[] { this.bntNuevo, this.bntModificar, this.bntEliminar });
		ECampos.buscarBotones(this.pnlBuscar, false, null);
		ECampos.buscarBotones(this.pnlEstado, true, null);
		ECampos.buscarBotones(this.pnlHerramientas, true, null);
		ECampos.buscarBotones(this.pnlIGV, true, null);

		this.cmbProveedor.setEnabled(true);
		this.bntBuscarProveedor.setEnabled(true);
		this.txtObservaciones.setEditable(true);
		this.tblProductos.setEnabled(true);
		this.txtNumeroDocumento.setEditable(true);
		this.cmbMoneda.setEnabled(true);
		this.ftfCambio.setEditable(true);
		this.txtUsuario.setText(AppConfig.getUsuario().getNombre());

		if (this.tblProductos.getRowCount() > 0) {
			this.dcFecha.setDate(Calendar.getInstance().getTime());
			this.ftfItems.setValue(0);
			this.ftfSubtotal.setValue(0.00);
			this.ftfIgv.setValue(0.00);
			this.ftfTotal.setValue(0.00);
			this.txtNumeroDocumento.setText("");
			this.mtdc = new ModeloTablaDetalleCompra();
			this.tblProductos.setModel(mtdc);
			this.tblProductos.setDefaultRenderer(PanelAccion.class, new AccionTableCellRenderer(this));
			TableColumn tc = this.tblProductos.getColumnModel().getColumn(this.tblProductos.getColumnCount() - 1);
			tc.setCellEditor(new CeldaAccionEditor(this));
			setEventTable();
			this.tblProductos.getColumnModel().getColumn(0).setPreferredWidth(400);
			CellEditorSpinnerCompra cnt = new CellEditorSpinnerCompra(1, this);
			this.tblProductos.getColumnModel().getColumn(1).setCellEditor(cnt);
			this.tblProductos.getColumnModel().getColumn(1).setCellRenderer(new TableCellFormatter(null));
			this.tblProductos.getColumnModel().getColumn(2).setCellRenderer(new TableCellFormatter());
			this.tblProductos.getColumnModel().getColumn(3).setCellRenderer(new TableCellFormatter());
			this.tblProductos.getColumnModel().getColumn(3).setCellEditor(new TableCellFormatterEditor());
			this.tblProductos.getColumnModel().getColumn(4).setCellRenderer(new TableCellFormatter());
			this.txtUsuario.setText("");
			setProveedor(null);
		}
	}// GEN-LAST:event_bntNuevoActionPerformed

	private void bntEliminarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntEliminarActionPerformed
		if (compra != null) {
			int op = JOptionPane.showInternalConfirmDialog(this,
					"Nro de Documento: " + compra.getNumeroDocumento() + "\n ¿Desea sea elimina esta compra?");
			if (op == JOptionPane.OK_OPTION) {
				ArrayList<DetalleCompra> productosEliminados = mtdc.getDetallesCompra();
				if (productosEliminados != null) {
					CDetalleCompra crtlDc = new CDetalleCompra();
					CAlmacenProducto controllerAp = null;
					CKardex controllerKardex = new CKardex();
					Kardex kardex = null;
					if (!productosEliminados.isEmpty()) {
						Kardex kardexEliminadosDeCompras = null;
						for (Iterator<DetalleCompra> it = productosEliminados.iterator(); it.hasNext();) {
							DetalleCompra item = it.next();

							crtlDc.eliminacionReal(item);

							controllerAp = new CAlmacenProducto();
							AlmacenProducto reg = controllerAp.getRegistro(AppConfig.getAlmacen().getPrimaryKey(),
									item.getIdProducto().getPrimaryKey());
							int cnt = 0;
							if (reg != null) {
								cnt = reg.getCantidadActual();
								cnt = cnt - item.getCantidad();
								reg.setCantidadActual(cnt);
								controllerAp.actualizarRegistro(reg);
							}

							kardexEliminadosDeCompras = new Kardex();
							kardexEliminadosDeCompras.setProducto(item.getIdProducto());
							kardexEliminadosDeCompras.setFecha(Calendar.getInstance().getTime());
							kardexEliminadosDeCompras.setDocumento(Kardex.PRODUCTO_ELIMINADO_COMPRA);
							kardexEliminadosDeCompras.setNumeroDeDocumento(compra.getNumeroDocumento());
							kardexEliminadosDeCompras.setEntrada(item.getCantidad());
							kardexEliminadosDeCompras.setSalida(0);
							kardexEliminadosDeCompras.setStockActual(
									controllerAp.getCantidadProducto(item.getIdProducto().getPrimaryKey()));
							kardexEliminadosDeCompras.setPrecio(item.getIdProducto().getCosto());
							kardexEliminadosDeCompras.setValor(item.getIdProducto().getCosto());
							kardexEliminadosDeCompras.setValorTotal(
									kardexEliminadosDeCompras.getStockActual() * kardexEliminadosDeCompras.getValor());
							controllerKardex.guardarRegistro(kardexEliminadosDeCompras);

						}
					}

					int eliminacionReal = controllerCompra.eliminacionReal(compra);
					if (eliminacionReal > 0) {
						JOptionPane.showInternalMessageDialog(this, "Documento Eliminado Correctamente",
								"Documento Borrado", JOptionPane.INFORMATION_MESSAGE);
						reniciar();
						getYmostrarCompras();
					}

				}
			}
		}

	}// GEN-LAST:event_bntEliminarActionPerformed

	private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntCancelarActionPerformed
		if (esActualizacion) {
			getYmostrarCompras();
			ECampos.buscarBotones(this.pnlActionButtons, true, null);
			ECampos.buscarBotones(this.pnlActionButtons, false,
					new Component[] { this.bntNuevo, this.bntModificar, this.bntEliminar, this.bntSalir });

			ECampos.buscarBotones(this.pnlEstado, false, null);
			ECampos.buscarBotones(this.pnlHerramientas, false, null);
			ECampos.buscarBotones(this.pnlIGV, false, null);

			this.cmbProveedor.setEnabled(false);
			this.bntBuscarProveedor.setEnabled(false);
			this.txtObservaciones.setEditable(false);
			this.tblProductos.setEnabled(false);
			this.txtNumeroDocumento.setEditable(false);
			this.cmbMoneda.setEnabled(false);
			this.ftfCambio.setEditable(false);

			return;
		}
		int op = JOptionPane.showInternalConfirmDialog(this,
				"Se borraran todo los datos ingresados.\n" + "¿Realmente desea cancelar esta Operacion?",
				"Cancelar compra", JOptionPane.YES_NO_CANCEL_OPTION);
		if (op == JOptionPane.OK_OPTION) {
			reniciar();
			ECampos.buscarBotones(this.pnlBuscar, true, null);
		}
	}// GEN-LAST:event_bntCancelarActionPerformed

	private void tblProductosFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_tblProductosFocusLost
		tblProductos.clearSelection();
		setTotalesConIGV();
	}// GEN-LAST:event_tblProductosFocusLost

	private void bntGuardarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntGuardarActionPerformed
		if (this.proveedor == null) {
			JOptionPane.showInternalMessageDialog(this, "Debe seleccionar un proveedor.",
					"Error: No ha seleccionado ningun proveedor", JOptionPane.ERROR_MESSAGE);
			this.cmbProveedor.requestFocus();
			return;
		} else if (mtdc.getNumItems() == 0) {
			JOptionPane.showInternalMessageDialog(this, "Debe seleccionar al menos un Producto.",
					"Error: No ha seleccionado ningun producto", JOptionPane.ERROR_MESSAGE);
			this.tblProductos.addRowSelectionInterval(0, 0);
			return;
		} else if (this.txtNumeroDocumento.getText().isEmpty()) {
			JOptionPane.showInternalMessageDialog(this, "Debe Ingresar el numero del Documento.",
					"Error: No ha asignado un numero a esta compra", JOptionPane.ERROR_MESSAGE);
			this.txtNumeroDocumento.requestFocus();
			return;
		}

		if (!this.esActualizacion) {
			if (controllerCompra.existeDocumento(this.txtNumeroDocumento.getText(),
					this.cmbTipoDocumento.getSelectedItem().toString())) {
				JOptionPane.showInternalMessageDialog(this,
						"El numero de documento ya fue ingresado para otro documento.",
						"Error: Numero de documento duplicado", JOptionPane.ERROR_MESSAGE);
				this.txtNumeroDocumento.requestFocus();
				return;
			}
			int res = JOptionPane.showInternalConfirmDialog(this,
					"¿Realmente desea guardar esta " + this.cmbTipoDocumento.getSelectedItem() + "?",
					this.cmbTipoDocumento.getSelectedItem() + " Nro: " + this.txtNumeroDocumento,
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (res == JOptionPane.OK_OPTION) {
				this.guardar();
				reniciar();
			}

		} else {
			this.actualizar();
		}
	}// GEN-LAST:event_bntGuardarActionPerformed

	private void bntBuscarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntBuscarActionPerformed
		BuscarCompra pvc = new BuscarCompra();
		JLabel aviso = pvc.getAviso();
		String msj = "Lista de Compras";
		JOptionPane.showInternalOptionDialog(this, pvc, msj, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, new Object[] { aviso }, null);

		if (pvc.getCompra() != null) {
			compra = pvc.getCompra();
			setValores(compra);
			// verificar porque no estaba esta linea
			mtdc = new ModeloTablaDetalleCompra(compra);
			this.tblProductos.setModel(mtdc);
			this.tblProductos.setDefaultRenderer(PanelAccion.class, new AccionTableCellRenderer(this));
			TableColumn tc = this.tblProductos.getColumnModel().getColumn(this.tblProductos.getColumnCount() - 1);
			tc.setCellEditor(new CeldaAccionEditor(this));
			// setEventTable();
			this.tblProductos.getColumnModel().getColumn(0).setPreferredWidth(400);
			CellEditorSpinnerCompra cnt = new CellEditorSpinnerCompra(1, this);
			this.tblProductos.getColumnModel().getColumn(1).setCellEditor(cnt);
			this.tblProductos.getColumnModel().getColumn(1).setCellRenderer(new TableCellFormatter(null));
			this.tblProductos.getColumnModel().getColumn(2).setCellRenderer(new TableCellFormatter());
			this.tblProductos.getColumnModel().getColumn(3).setCellRenderer(new TableCellFormatter());
			this.tblProductos.getColumnModel().getColumn(3).setCellEditor(new TableCellFormatterEditor());
			this.tblProductos.getColumnModel().getColumn(4).setCellRenderer(new TableCellFormatter());

			if (compra.getAnterior() != null) {
				bntAnterior.setEnabled(true);
				bntPrimero.setEnabled(true);
			} else {
				bntAnterior.setEnabled(false);
				bntPrimero.setEnabled(false);
			}

			if (compra.getSiguiente() != null) {
				bntSiguiente.setEnabled(true);
				bntUltimo.setEnabled(true);
			} else {
				bntSiguiente.setEnabled(false);
				bntUltimo.setEnabled(false);
			}

		}
	}// GEN-LAST:event_bntBuscarActionPerformed

	private void bntModificarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntModificarActionPerformed

		ECampos.buscarBotones(this.pnlActionButtons, false, null);
		ECampos.buscarBotones(this.pnlActionButtons, true, new Component[] { this.bntNuevo, this.bntEliminar });

		ECampos.buscarBotones(this.pnlBuscar, false, null);
		ECampos.buscarBotones(this.pnlEstado, true, null);
		ECampos.buscarBotones(this.pnlHerramientas, true, null);
		ECampos.buscarBotones(this.pnlIGV, true, null);

		this.cmbProveedor.setEnabled(true);
		this.bntBuscarProveedor.setEnabled(true);
		this.txtObservaciones.setEditable(true);
		this.tblProductos.setEnabled(true);
		this.txtNumeroDocumento.setEditable(true);
		this.cmbMoneda.setEnabled(true);
		this.ftfCambio.setEditable(true);
		esActualizacion = true;
	}// GEN-LAST:event_bntModificarActionPerformed

	private void bntSalirActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntSalirActionPerformed
		int nu = JOptionPane.showInternalConfirmDialog(this,
				"Todos los datos que no se ha guardado " + "se perderan.\n" + "¿Desea Cerrar esta ventana?",
				"Cerrar ventana", JOptionPane.YES_NO_CANCEL_OPTION);
		if (nu == JOptionPane.OK_OPTION) {
			getYmostrarCompras();
			ECampos.buscarBotones(this.pnlActionButtons, true, null);
			ECampos.buscarBotones(this.pnlActionButtons, false,
					new Component[] { this.bntNuevo, this.bntModificar, this.bntEliminar, this.bntSalir });
			ECampos.buscarBotones(this.pnlBuscar, false, null);
			ECampos.buscarBotones(this.pnlEstado, false, null);
			ECampos.buscarBotones(this.pnlHerramientas, false, null);
			ECampos.buscarBotones(this.pnlIGV, false, null);
			this.dispose();
		}
	}// GEN-LAST:event_bntSalirActionPerformed

	private void bntSiguienteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntSiguienteActionPerformed

		bntPrimero.setEnabled(true);
		bntAnterior.setEnabled(true);
		compra = (Compra) this.compra.getSiguiente();
		setValores(compra);
		// verificar porque no estaba esta linea
		mtdc = new ModeloTablaDetalleCompra(compra);
		this.tblProductos.setModel(mtdc);
		this.tblProductos.setDefaultRenderer(PanelAccion.class, new AccionTableCellRenderer(this));
		TableColumn tc = this.tblProductos.getColumnModel().getColumn(this.tblProductos.getColumnCount() - 1);
		tc.setCellEditor(new CeldaAccionEditor(this));
		// setEventTable();
		this.tblProductos.getColumnModel().getColumn(0).setPreferredWidth(400);
		CellEditorSpinnerCompra cnt = new CellEditorSpinnerCompra(1, this);
		this.tblProductos.getColumnModel().getColumn(1).setCellEditor(cnt);
		this.tblProductos.getColumnModel().getColumn(1).setCellRenderer(new TableCellFormatter(null));
		this.tblProductos.getColumnModel().getColumn(2).setCellRenderer(new TableCellFormatter());
		this.tblProductos.getColumnModel().getColumn(3).setCellRenderer(new TableCellFormatter());
		this.tblProductos.getColumnModel().getColumn(3).setCellEditor(new TableCellFormatterEditor());
		this.tblProductos.getColumnModel().getColumn(4).setCellRenderer(new TableCellFormatter());
		if (compra.getSiguiente() == null) {
			bntSiguiente.setEnabled(false);
			bntUltimo.setEnabled(false);
			return;
		}

	}// GEN-LAST:event_bntSiguienteActionPerformed

	private void bntAnteriorActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntAnteriorActionPerformed

		bntUltimo.setEnabled(true);
		bntSiguiente.setEnabled(true);
		compra = (Compra) this.compra.getAnterior();
		setValores(compra);
		// verificar porque no estaba esta linea
		mtdc = new ModeloTablaDetalleCompra(compra);
		this.tblProductos.setModel(mtdc);
		this.tblProductos.setDefaultRenderer(PanelAccion.class, new AccionTableCellRenderer(this));
		TableColumn tc = this.tblProductos.getColumnModel().getColumn(this.tblProductos.getColumnCount() - 1);
		tc.setCellEditor(new CeldaAccionEditor(this));
		// setEventTable();
		this.tblProductos.getColumnModel().getColumn(0).setPreferredWidth(400);
		CellEditorSpinnerCompra cnt = new CellEditorSpinnerCompra(1, this);
		this.tblProductos.getColumnModel().getColumn(1).setCellEditor(cnt);
		this.tblProductos.getColumnModel().getColumn(1).setCellRenderer(new TableCellFormatter(null));
		this.tblProductos.getColumnModel().getColumn(2).setCellRenderer(new TableCellFormatter());
		this.tblProductos.getColumnModel().getColumn(3).setCellRenderer(new TableCellFormatter());
		this.tblProductos.getColumnModel().getColumn(3).setCellEditor(new TableCellFormatterEditor());
		this.tblProductos.getColumnModel().getColumn(4).setCellRenderer(new TableCellFormatter());

		if (compra.getAnterior() == null) {
			bntAnterior.setEnabled(false);
			bntPrimero.setEnabled(false);
			return;
		}

	}// GEN-LAST:event_bntAnteriorActionPerformed

	private void bntUltimoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntUltimoActionPerformed
		if (compra.getUltimo() == null) {
			bntAnterior.setEnabled(true);
			return;
		}
		bntAnterior.setEnabled(true);
		bntPrimero.setEnabled(true);
		bntSiguiente.setEnabled(false);
		bntUltimo.setEnabled(false);
		compra = (Compra) this.compra.getUltimo();
		setValores(compra);
		// verificar porque no estaba esta linea
		mtdc = new ModeloTablaDetalleCompra(compra);
		this.tblProductos.setModel(mtdc);
		this.tblProductos.setDefaultRenderer(PanelAccion.class, new AccionTableCellRenderer(this));
		TableColumn tc = this.tblProductos.getColumnModel().getColumn(this.tblProductos.getColumnCount() - 1);
		tc.setCellEditor(new CeldaAccionEditor(this));
		// setEventTable();
		this.tblProductos.getColumnModel().getColumn(0).setPreferredWidth(400);
		CellEditorSpinnerCompra cnt = new CellEditorSpinnerCompra(1, this);
		this.tblProductos.getColumnModel().getColumn(1).setCellEditor(cnt);
		this.tblProductos.getColumnModel().getColumn(1).setCellRenderer(new TableCellFormatter(null));
		this.tblProductos.getColumnModel().getColumn(2).setCellRenderer(new TableCellFormatter());
		this.tblProductos.getColumnModel().getColumn(3).setCellRenderer(new TableCellFormatter());
		this.tblProductos.getColumnModel().getColumn(3).setCellEditor(new TableCellFormatterEditor());
		this.tblProductos.getColumnModel().getColumn(4).setCellRenderer(new TableCellFormatter());

	}// GEN-LAST:event_bntUltimoActionPerformed

	private void bntPrimeroActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntPrimeroActionPerformed
		if (compra.getPrimero() == null) {
			bntAnterior.setEnabled(false);
			return;
		}
		bntAnterior.setEnabled(false);
		bntSiguiente.setEnabled(true);
		bntUltimo.setEnabled(true);
		bntPrimero.setEnabled(false);
		compra = (Compra) this.compra.getPrimero();
		setValores(compra);
		// verificar porque no estaba esta linea
		mtdc = new ModeloTablaDetalleCompra(compra);
		this.tblProductos.setModel(mtdc);
		this.tblProductos.setDefaultRenderer(PanelAccion.class, new AccionTableCellRenderer(this));
		TableColumn tc = this.tblProductos.getColumnModel().getColumn(this.tblProductos.getColumnCount() - 1);
		tc.setCellEditor(new CeldaAccionEditor(this));
		// setEventTable();
		this.tblProductos.getColumnModel().getColumn(0).setPreferredWidth(400);
		CellEditorSpinnerCompra cnt = new CellEditorSpinnerCompra(1, this);
		this.tblProductos.getColumnModel().getColumn(1).setCellEditor(cnt);
		this.tblProductos.getColumnModel().getColumn(1).setCellRenderer(new TableCellFormatter(null));
		this.tblProductos.getColumnModel().getColumn(2).setCellRenderer(new TableCellFormatter());
		this.tblProductos.getColumnModel().getColumn(3).setCellRenderer(new TableCellFormatter());
		this.tblProductos.getColumnModel().getColumn(3).setCellEditor(new TableCellFormatterEditor());
		this.tblProductos.getColumnModel().getColumn(4).setCellRenderer(new TableCellFormatter());

	}// GEN-LAST:event_bntPrimeroActionPerformed

	private void bntCalculadoraActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntCalculadoraActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_bntCalculadoraActionPerformed

	private void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
		if (this.proveedor != null) {
			this.txtRuc.setText(this.proveedor.getRuc());
			this.txtDireccion.setText(this.proveedor.getDireccion());
		} else {
			this.txtRuc.setText("");
			this.txtDireccion.setText("");
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private elaprendiz.gui.button.ButtonRect bntAnterior;
	private elaprendiz.gui.button.ButtonRect bntBuscar;
	private javax.swing.JButton bntBuscarProveedor;
	private elaprendiz.gui.button.ButtonRect bntCalculadora;
	private elaprendiz.gui.button.ButtonRect bntCancelar;
	private elaprendiz.gui.button.ButtonRect bntEliminar;
	private elaprendiz.gui.button.ButtonRect bntGuardar;
	private elaprendiz.gui.button.ButtonRect bntImportar;
	private elaprendiz.gui.button.ButtonRect bntImprimir;
	private elaprendiz.gui.button.ButtonRect bntModificar;
	private elaprendiz.gui.button.ButtonRect bntNuevo;
	private elaprendiz.gui.button.ButtonRect bntPrimero;
	private elaprendiz.gui.button.ButtonRect bntSalir;
	private elaprendiz.gui.button.ButtonRect bntSiguiente;
	private elaprendiz.gui.button.ButtonRect bntUltimo;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.ButtonGroup buttonGroup2;
	private elaprendiz.gui.comboBox.ComboBoxRect cmbMoneda;
	private elaprendiz.gui.comboBox.ComboBoxRectIcon cmbProveedor;
	private elaprendiz.gui.comboBox.ComboBoxRect cmbTipoDocumento;
	private com.toedter.calendar.JDateChooser dcFecha;
	private javax.swing.JFormattedTextField ftfCambio;
	private javax.swing.JFormattedTextField ftfIgv;
	private javax.swing.JFormattedTextField ftfItems;
	private javax.swing.JFormattedTextField ftfSubtotal;
	private javax.swing.JFormattedTextField ftfTotal;
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
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel12;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JScrollPane jScrollPane1;
	private elaprendiz.gui.panel.PanelImage panelImage1;
	private javax.swing.JPanel pnlActionButtons;
	private javax.swing.JPanel pnlBuscar;
	private javax.swing.JPanel pnlEstado;
	private javax.swing.JPanel pnlHerramientas;
	private javax.swing.JPanel pnlIGV;
	private javax.swing.JRadioButton rbCancelado;
	private javax.swing.JRadioButton rbIncluyeIGV;
	private javax.swing.JRadioButton rbNoIncluyeIgv;
	private javax.swing.JRadioButton rbPendiente;
	private javax.swing.JTable tblProductos;
	private elaprendiz.gui.textField.TextField txtDireccion;
	private elaprendiz.gui.textField.TextField txtNumeroDocumento;
	private elaprendiz.gui.textField.TextField txtObservaciones;
	private elaprendiz.gui.textField.TextField txtRuc;
	private elaprendiz.gui.textField.TextFieldRectIcon txtUsuario;
	// End of variables declaration//GEN-END:variables

	public class TableCellFormatterEditor extends AbstractCellEditor implements TableCellEditor {

		JFormattedTextField ftfcampo;
		private Object valorInicial;
		private Object valorActual;
		private int filaSeleccionada;
		private int columnaSeleccionada;

		public TableCellFormatterEditor() {
			ftfcampo = new JFormattedTextField();
			ftfcampo.setHorizontalAlignment(JTextField.RIGHT);
			ftfcampo.setFont(new Font("Tahoma", 1, 14));
			ftfcampo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
			ftfcampo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
					new javax.swing.text.NumberFormatter(new FormatoDecimal("#,##0.00", true))));
			ftfcampo.addKeyListener(new KeyListener() {

				public void keyTyped(KeyEvent e) {
					// throw new UnsupportedOperationException("Not supported yet.");
				}

				public void keyPressed(KeyEvent e) {
					// throw new UnsupportedOperationException("Not supported yet.");
				}

				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						JFormattedTextField tmp = (JFormattedTextField) e.getSource();
						valorActual = Double.parseDouble(tmp.getValue().toString());
						if (valorActual != null) {
							mtdc.setValueAt(valorActual, filaSeleccionada, columnaSeleccionada);
							// setTotales();
							System.out.println("Valor Actual: " + valorActual);
							Double pr = ((DetalleCompra) mtdc.getFila(filaSeleccionada)).getIdProducto().getCosto();
							mtdc.setValueAt(Double.parseDouble(valorActual.toString()) * pr, filaSeleccionada, 4);
							mtdc.contarItems();
							fireEditingStopped();
						}
					}
				}
			});

			ftfcampo.addFocusListener(new FocusListener() {

				public void focusGained(FocusEvent e) {
					System.out.println("Tienee el focoooo");
				}

				public void focusLost(FocusEvent e) {
					JFormattedTextField tmp = (JFormattedTextField) e.getSource();
					Double vl = null;
					try {
						vl = Double.parseDouble(tmp.getText());
					} catch (NumberFormatException ex) {
						return;
					}

					if (vl instanceof Number) {
						tmp.setValue(vl);

					} else {
						return;
					}

					valorActual = Double.parseDouble(tmp.getValue().toString());
					mtdc.setValueAt(valorActual, filaSeleccionada, columnaSeleccionada);
					// setTotales();
					Double pr = ((DetalleCompra) mtdc.getFila(filaSeleccionada)).getIdProducto().getCosto()
							* ((DetalleCompra) mtdc.getFila(filaSeleccionada)).getCantidad();
					mtdc.setValueAt(pr, filaSeleccionada, 4);
					mtdc.contarItems();
					fireEditingStopped();
				}
			});

		}

		public Object getCellEditorValue() {
			return valorActual;
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			filaSeleccionada = row;
			columnaSeleccionada = column;
			if (value != null) {
				mtdc.setValueAt(value, row, column);
				ftfcampo.setValue(value);
			}
			System.out.println("Tabla tiene foco: " + table.hasFocus());
			return ftfcampo;
		}

	}

}
