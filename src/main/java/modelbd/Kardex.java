package modelbd;

import core.JAbstractModelBD;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class Kardex extends JAbstractModelBD implements Serializable,IModel{
    
    
    private static final long serialVersionUID = 1L;
    public static final String nt = "gv_kardex";
    public static final String COLUMNA_PK = "idkardex";
    public static final String COLUMNA_ACTIVO = "activo";   
    
    public static final String FULL_NOM_CAMPOS="idkardex,idProductos, fecha, documento, nro_documento, entrada,"+
	"salida, precio, valor, stock_actual, valorTotal, activo";
    public static final String NO_FULL_NOM_CAMPOS = "idProductos, fecha, documento, nro_documento, entrada,"+
	"salida, precio, valor, stock_actual, valorTotal, activo";
        
    public static final String APERTURA = "APERTURA";
    public static final String VENTA = "VENTA";
    public static final String COMPRA = "COMPRA";
    public static final String COMPRA_ACTUALIZACION = "COMPRA_ACTUALIZACION";
    public static final String VENTA_ACTUALIZACION = "VENTA_ACTUALIZACION";
    public static final String PRODUCTO_ELIMINADO_COMPRA = "PRODUCTO_ELIMINADO_COMPRA";
    
    
    private Integer idKardex;
    private Producto producto;
    private Date Fecha;
    private String documento;
    private String numeroDeDocumento;
    private Integer entrada;
    private Integer salida;
    private Double precio;
    private Double valor;
    private Integer StockActual;
    private Double valorTotal;
    private Integer activo = 1;

    public Kardex(Integer idKardex, Producto producto, Date Fecha, String documento, String numeroDeDocumento,Integer entrada, Integer salida, Double precio, Double valor, Integer StockActual, Double valorTotal, Integer activo) {
        this.idKardex = idKardex;
        this.producto = producto;
        this.Fecha = Fecha;
        this.documento = documento;
        this.numeroDeDocumento = numeroDeDocumento;
        this.entrada = entrada;
        this.salida = salida;
        this.precio = precio;
        this.valor = valor;
        this.StockActual = StockActual;
        this.valorTotal = valorTotal;
        this.activo = activo;
        initBasic();
    }
    
    public Kardex(Producto producto, Date Fecha, String documento, String numeroDeDocumento,Integer entrada, Integer salida, Double precio, Double valor, Integer StockActual, Double valorTotal) {
        this.producto = producto;
        this.Fecha = Fecha;
        this.documento = documento;
        this.numeroDeDocumento = numeroDeDocumento;
        this.entrada = entrada;
        this.salida = salida;
        this.precio = precio;
        this.valor = valor;
        this.StockActual = StockActual;
        this.valorTotal = valorTotal;
        initBasic();
       
    }
    
    private void initBasic()
    {
        this.setNombreTabla(nt);
        this.setCampoClavePrimaria(COLUMNA_PK);
        this.setCampoExistencial("activo");
    }

    public Kardex() {
        initBasic();
    }

    public Integer getIdKardex() {
        return idKardex;
    }

    public void setIdKardex(Integer idKardex) {
        this.idKardex = idKardex;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNumeroDeDocumento() {
        return numeroDeDocumento;
    }

    public void setNumeroDeDocumento(String numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
    }

    public Integer getEntrada() {
        return entrada;
    }

    public void setEntrada(Integer entrada) {
        this.entrada = entrada;
    }

    public Integer getSalida() {
        return salida;
    }

    public void setSalida(Integer salida) {
        this.salida = salida;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getStockActual() {
        return StockActual;
    }

    public void setStockActual(Integer StockActual) {
        this.StockActual = StockActual;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.idKardex != null ? this.idKardex.hashCode() : 0);
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
        final Kardex other = (Kardex) obj;
        if (this.idKardex != other.idKardex && (this.idKardex == null || !this.idKardex.equals(other.idKardex))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kardex{" + "Fecha=" + Fecha + ", documento=" + documento + '}';
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
