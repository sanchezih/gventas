package modelbd;

import controllers.CMoneda;
import controllers.CProveedor;
import controllers.CUsuario;
import core.JAbstractModelBD;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class Compra extends JAbstractModelBD implements Serializable,IModel{
    private static final long serialVersionUID = 1L;
   
    public static final String nt = "gv_compra";
    public static final String COLUMNA_PK = "idcompra";
    public static final String COLUMNA_ACTIVO = "activo";
    
    public static final String[] TIPOSDOC = {"FACTURA","BOLETA","GUIA","NOTA DE PEDIDO","DUA"};
    public static final String[] ESTADOS = {"CANCELADO","PENDIENTE"};
    
    public static final String FULL_CAMPOS = "idcompra,tipodoc,numdoc,idproveedor,idusuario,"
                                            + "idmoneda,estado,numitems,observaciones,subtotal,"
                                            + "igv,total,fecha,activo";
    
    public static final String CAMPOS_NO_ID = "tipodoc,numdoc,idproveedor,idusuario,"
                                            + "idmoneda,estado,numitems,observaciones,subtotal,"
                                            + "igv,total,fecha,activo";
    
    public static final String CAMPOS_OBLIGATORIOS = "idcompra,tipodoc,numdoc,idproveedor,idusuario,"
                                                    + "idmoneda,estado,numitems,subtotal,"
                                                    + "total,fecha";
    
    private Integer id;
    private String tipoDocumento;
    private String numeroDocumento;
    private Proveedor idProveedor;
    private Usuario idUsuario;
    private Moneda idMoneda;
    private String estado;
    private Integer numeroItems;
    private String observaciones;
    private Double subtotla;
    private Integer igv;
    private Double total;
    private Date fecha;
    private Integer activo=1;
    private ArrayList<DetalleCompra> dc;
    
     public Compra() {
         initBasic();
    }

    public Compra(Integer id, String tipoDocumento, String numeroDocumento, Proveedor idProveedor, Usuario idUsuario, Moneda idMoneda, String estado, Integer numeroItems, String observaciones, Double subtotla, Integer igv, Double total, Date fecha, Integer activo) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.idProveedor = idProveedor;
        this.idUsuario = idUsuario;
        this.idMoneda = idMoneda;
        this.estado = estado;
        this.numeroItems = numeroItems;
        this.observaciones = observaciones;
        this.subtotla = subtotla;
        this.igv = igv;
        this.total = total;
        this.fecha = fecha;
        this.activo = activo;
        initBasic();
    }

    public Compra(String tipoDocumento, String numeroDocumento, Proveedor idProveedor, Usuario idUsuario, Moneda idMoneda, String estado, Integer numeroItems, String observaciones, Double subtotla, Integer igv, Double total, Date fecha, Integer activo) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.idProveedor = idProveedor;
        this.idUsuario = idUsuario;
        this.idMoneda = idMoneda;
        this.estado = estado;
        this.numeroItems = numeroItems;
        this.observaciones = observaciones;
        this.subtotla = subtotla;
        this.igv = igv;
        this.total = total;
        this.fecha = fecha;
        this.activo = activo;
        initBasic();
    }

    public Compra(String tipoDocumento, String numeroDocumento, Proveedor idProveedor, Usuario idUsuario, Moneda idMoneda, String estado, Integer numeroItems, Double subtotla, Double total, Date fecha) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.idProveedor = idProveedor;
        this.idUsuario = idUsuario;
        this.idMoneda = idMoneda;
        this.estado = estado;
        this.numeroItems = numeroItems;
        this.subtotla = subtotla;
        this.total = total;
        this.fecha = fecha;
        initBasic();
    }
    private void initBasic()
    {
        this.setNombreTabla(nt);
        this.setCampoClavePrimaria(COLUMNA_PK);
        this.setCampoExistencial(COLUMNA_ACTIVO);
    }

    public ArrayList<DetalleCompra> getDetalleCompra() {
        return dc;
    }

    public void setDetalleCompra(ArrayList<DetalleCompra> dc) {
        this.dc = dc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Proveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Proveedor idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Moneda getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Moneda idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getNumeroItems() {
        return numeroItems;
    }

    public void setNumeroItems(Integer numeroItems) {
        this.numeroItems = numeroItems;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getSubtotla() {
        return subtotla;
    }

    public void setSubtotla(Double subtotla) {
        this.subtotla = subtotla;
    }

    public Integer getIgv() {
        return igv;
    }

    public void setIgv(Integer igv) {
        this.igv = igv;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Compra other = (Compra) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Compra{" + "tipoDocumento=" + tipoDocumento + ", numeroDocumento=" + numeroDocumento + '}';
    }
    
    @Override
    public Object clone()
    {
        try
        {
            return super.clone();
        }catch (CloneNotSupportedException e)
        {
          return null;
        }
    }

    @Override
    public JAbstractModelBD llenarObjeto(ResultSet rs) { 
        Compra us=null;
        try {           
                   CProveedor cntp = new CProveedor(); 
                   us = new Compra();
                   us.setPrimaryKey(rs.getInt(1));                
                   us.setId(rs.getInt(1));
                   us.setTipoDocumento(rs.getString(2));
                   us.setNumeroDocumento(rs.getString(3));
                   us.setIdProveedor(cntp.getRegistroPorPk(rs.getInt(4)));
                   us.setIdUsuario(new CUsuario().getRegistroPorPk(rs.getInt(5)));
                   us.setIdMoneda(new CMoneda().getRegistroPorPk(rs.getInt(6)));
                   us.setEstado(rs.getString(7));
                   us.setNumeroItems(rs.getInt(8));
                   us.setObservaciones(rs.getString(9));
                   us.setSubtotla(rs.getDouble(10));
                   us.setIgv(rs.getInt(11));
                   us.setTotal(rs.getDouble(12));
                   us.setFecha(rs.getDate(13));
                   us.setActivo(rs.getInt(14));                 
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
        
        return us;
    }

    @Override
    public Object[] llenarValores() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
