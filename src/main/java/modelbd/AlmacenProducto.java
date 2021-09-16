package modelbd;

import controllers.CAlmacen;
import controllers.CProducto;
import core.CriterioSQL;
import core.JAbstractModelBD;
import managerbd.BaseConexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class AlmacenProducto extends JAbstractModelBD implements Serializable,IModel{
    private static final long serialVersionUID = 1L;
    public static final String nt = "gv_almaceproduct";
    public static final String COLUMNA_PK = "idpa";
    public static final String COLUMNA_ACTIVO = "activo";
    public static final String[] TIPO_AGREGAR ={"VENTA","COMPRA","TRANSLADO","REGISTRO"};
    
    public static final String FULL_CAMPOS = "idpa,idalmacen, idproducto, cantidad_actual, cantidad_inicial, tipo_manipulacion, fecha, activo";
    public static final String NO_FULL_CAMPOS = "idalmacen, idproducto, cantidad_actual, cantidad_inicial, tipo_manipulacion, fecha, activo";
    
    private Integer idpa;
    private Almacen almacen;
    private Producto producto;
    private Integer cantidadActual;
    private Integer cantidadInicial;
    private String tipoManipulacion;
    private Date fecha;
    private Integer activo=1;

    public AlmacenProducto(Integer idpa, Almacen almacen, Producto producto, Integer cantidadActual, Integer cantidadInicial, String tipoManipulacion, Date fecha, Integer activo) {
        this.idpa = idpa;
        this.almacen = almacen;
        this.producto = producto;
        this.cantidadActual = cantidadActual;
        this.cantidadInicial = cantidadInicial;
        this.tipoManipulacion = tipoManipulacion;
        this.fecha = fecha;
        this.activo = activo;
        initBasic();
    }

    public AlmacenProducto(Almacen almacen, Producto producto, Integer cantidadInicial, String tipoManipulacion, Date fecha, Integer activo) {
        this.almacen = almacen;
        this.producto = producto;
        this.cantidadInicial = cantidadInicial;
        this.tipoManipulacion = tipoManipulacion;
        this.fecha = fecha;
        this.activo = activo;
        initBasic();
    }

    public AlmacenProducto() {
        initBasic();
    }
    
    private void initBasic()
    {
        this.setNombreTabla(nt);
        this.setCampoClavePrimaria(COLUMNA_PK);
        this.setCampoExistencial(COLUMNA_ACTIVO);
    }

    public Integer getIdpa() {
        return idpa;
    }

    public void setIdpa(Integer idpa) {
        this.idpa = idpa;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Integer cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public Integer getCantidadInicial() {
        return cantidadInicial;
    }

    public void setCantidadInicial(Integer cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

    public String getTipoManipulacion() {
        return tipoManipulacion;
    }

    public void setTipoManipulacion(String tipoManipulacion) {
        this.tipoManipulacion = tipoManipulacion;
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
    
    public void sumarCantidad(Integer cantidad)
    {
        this.cantidadActual += cantidad;
    }

    public void restarCantidad(Integer cantidad)
    {
         this.cantidadActual -= cantidad;
    }
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.idpa != null ? this.idpa.hashCode() : 0);
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
        final AlmacenProducto other = (AlmacenProducto) obj;
        if (this.idpa != other.idpa && (this.idpa == null || !this.idpa.equals(other.idpa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return almacen.getNombre() + " : " + producto.getNombre();
    }
    
    public JAbstractModelBD llenarObjeto(ResultSet rs)
    {
        AlmacenProducto sm = null;
        CAlmacen controllerAlmacen = new CAlmacen();
        CProducto controllerProducto = new CProducto();
        try {
                 sm = new AlmacenProducto();
                 sm.setPrimaryKey(rs.getInt(1));
                 sm.setAlmacen((Almacen)controllerAlmacen.buscarRegistro(rs.getInt(2)));
                 controllerProducto.setNumPaginador(0, 50);
                 sm.setProducto(controllerProducto.getRegistro(rs.getInt(3)));
                 sm.setCantidadActual(rs.getInt(4));
                 sm.setCantidadInicial(rs.getInt(5));
                 sm.setTipoManipulacion(rs.getString(6));
                 sm.setFecha(rs.getDate(7));
                 sm.setActivo(rs.getInt(8));  
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sm;
    }

    @Override
    public Object[] llenarValores() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
