/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelbd;

import core.JAbstractModelBD;
import java.io.Serializable;
import java.sql.ResultSet;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class DetalleCompra extends JAbstractModelBD implements Serializable,IModel,Cloneable{
    private static final long serialVersionUID = 1L;
   
    public static final String nt = "gv_detalleCompra";
    public static final String COLUMNA_PK = "iddetallec";
    public static final String COLUMNA_ACTIVO = "activo";    
    
    
    public static final String FULL_CAMPOS = "iddetallec,idcompra,idproducto,"
                                            + "cantidad,importe,descuento,activo";
    
    public static final String CAMPOS_NO_ID = "idcompra,idproducto,"
                                            + "cantidad,importe,descuento,activo";
    
    public static final String CAMPOS_OBLIGATORIOS = "idcompra,idproducto,"
                                                    + "cantidad,importe,activo";
    
    private Integer id;
    private Compra idcompra;
    private Producto idProducto;
    private Integer cantidad;
    private Double importe;
    private Double descuento;
    private Integer activo=1;

    public DetalleCompra() {
        initBasic();
    }
    
    public DetalleCompra(Integer id, Compra idcompra, Producto idProducto, Integer cantidad, Double importe, Double descuento, Integer activo) {
        this.id = id;
        this.idcompra = idcompra;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.importe = importe;
        this.descuento = descuento;
        this.activo = activo;
        initBasic();
    }

    public DetalleCompra(Compra idcompra, Producto idProducto, Integer cantidad, Double importe, Double descuento, Integer activo) {
        this.idcompra = idcompra;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.importe = importe;
        this.descuento = descuento;
        this.activo = activo;
        initBasic();
    }

    public DetalleCompra(Compra idcompra, Producto idProducto, Integer cantidad, Double importe) {
        this.idcompra = idcompra;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.importe = importe;
        initBasic();
    }
    
     private void initBasic()
    {
        this.setNombreTabla(nt);
        this.setCampoClavePrimaria(COLUMNA_PK);
        this.setCampoExistencial(COLUMNA_ACTIVO);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Compra getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(Compra idcompra) {
        this.idcompra = idcompra;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double  getDescuento() {
        return descuento;
    }

    public void setDescuento(Double  descuento) {
        this.descuento = descuento;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final DetalleCompra other = (DetalleCompra) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DetalleCompra{" + "idcompra=" + idcompra + ", idProducto=" + idProducto + ", cantidad=" + cantidad + '}';
    }

    @Override
    public Object clone()
    {
        try
        {
            DetalleCompra clon = (DetalleCompra)super.clone();
            clon.idProducto = (Producto)this.idProducto.clone();
            return clon;
        }catch (CloneNotSupportedException e)
        {
          return null;
        }
    }

    @Override
    public JAbstractModelBD llenarObjeto(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] llenarValores() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
