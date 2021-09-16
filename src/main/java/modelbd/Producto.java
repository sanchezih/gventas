package modelbd;

import core.DatoArchivo;
import core.JAbstractModelBD;
import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class Producto extends JAbstractModelBD implements Serializable,IModel,Cloneable{
    private static final long serialVersionUID = 1L;
   
    public static final String nt = "gv_producto";
    public static final String COLUMNA_PK = "idproducto";
    public static final String COLUMNA_ACTIVO = "activo";
    
    public static final String[] tipoProducto = {"Producto Terminado","Componente","Insumo"};
    public static final String[] UNIDADES = {"CAJA","UNIDAD","MILLAR","CARTUCHO","PAQUETE"};
    
    public static final String FULL_CAMPOS = "idproducto, cod_barras, codigo, cod_del_fabricante, nombre, costo,"+ 
                                             "precioalmayor, precioalmenor, utilidad, aplica_igv, stock_min, tipo,"+
                                             "idmoneda, imagen, idclase, idmarca, idmodelo,ubicacion,unidad_principal, peso, activo";
    
    
    public static final String CAMPOS_NOID_NOIMAGE = "cod_barras, codigo, cod_del_fabricante, nombre, costo,"+ 
                                             "precioalmayor, precioalmenor, utilidad, aplica_igv, stock_min, tipo,"+
                                             "idmoneda, idclase, idmarca, idmodelo, ubicacion,unidad_principal,peso, activo";
    
    
    public static final String FULL_CAMPOS_NOIMAGEN = "idproducto, cod_barras, codigo, cod_del_fabricante, nombre, costo,"+ 
                                             "precioalmayor, precioalmenor, utilidad, aplica_igv, stock_min, tipo,"+
                                             "idmoneda,idclase, idmarca, idmodelo, ubicacion,unidad_principal,peso, activo";
    
    public static final String CAMPOS_NO_ID = "cod_barras, codigo, cod_del_fabricante, nombre, costo,"+ 
                                             "precioalmayor, precioalmenor, utilidad, aplica_igv, stock_min, tipo,"+
                                             "idmoneda, imagen, idclase, idmarca, idmodelo, ubicacion,unidad_principal,peso, activo";
    
    public static final String CAMPOS_OBLIGATORIOS = "idproducto,	cod_barras,	codigo,nombre, costo,"+ 
                                                     "precioalmayor, precioalmenor, utilidad, stock_min, tipo,idmoneda";
    
    private Integer idProducto;
    private String codigoBarras;
    private String codigo;
    private String codigoDelFabricante;
    private String nombre;
    private Double costo;
    private Double precioAlMayor;
    private Double precioAlMenor;
    private Integer utilidad;
    private Integer aplicaIGV;
    private Integer stockMinimo;
    private String tipo;
    private Integer idMoneda;
    private FileInputStream imagen; 
    private Integer idClase;
    private Integer idMarca;
    private Integer idModelo;
    private String ubicacion;
    private String unidadPrincipal;
    private Double peso;
    private Integer activo = 1;    
    private DatoArchivo dat;

    public Producto(Integer idProducto, String codigoBarras, String codigo, String nombre, Double costo, Double precioAlMayor, Double precioAlMenor, Integer utilidad, Integer stockMinimo, String tipo,Integer idMoneda) {
        this.idProducto = idProducto;
        this.codigoBarras = codigoBarras;
        this.codigo = codigo;
        this.nombre = nombre;
        this.costo = costo;
        this.precioAlMayor = precioAlMayor;
        this.precioAlMenor = precioAlMenor;
        this.utilidad = utilidad;
        this.stockMinimo = stockMinimo;
        this.tipo = tipo;
        this.idMoneda = idMoneda;
        initBasic();
    }

    public Producto(String codigoBarras, String codigo, String codigoDelFabricante, String nombre, Double costo, Double precioAlMayor, Double precioAlMenor, Integer utilidad, Integer aplicaIGV, Integer stockMinimo, String tipo, Integer idMoneda, FileInputStream imagen, Integer idClase, Integer idMarca, Integer idModelo, String ubicacion, String unidadPrincipal, Double peso) {
        this.codigoBarras = codigoBarras;
        this.codigo = codigo;
        this.codigoDelFabricante = codigoDelFabricante;
        this.nombre = nombre;
        this.costo = costo;
        this.precioAlMayor = precioAlMayor;
        this.precioAlMenor = precioAlMenor;
        this.utilidad = utilidad;
        this.aplicaIGV = aplicaIGV;
        this.stockMinimo = stockMinimo;
        this.tipo = tipo;
        this.idMoneda = idMoneda;
        this.imagen = imagen;
        this.idClase = idClase;
        this.idMarca = idMarca;
        this.idModelo = idModelo;
        this.ubicacion = ubicacion;
        this.unidadPrincipal = unidadPrincipal;
        this.peso = peso;
        initBasic();
    }

    public Producto(String codigoBarras, String codigo, String codigoDelFabricante, String nombre, Double costo, Double precioAlMayor, Double precioAlMenor, Integer utilidad, Integer aplicaIGV, Integer stockMinimo, String tipo, Integer idMoneda, Integer idClase, Integer idMarca, Integer idModelo, String ubicacion, String unidadPrincipal, Double peso) {
        this.codigoBarras = codigoBarras;
        this.codigo = codigo;
        this.codigoDelFabricante = codigoDelFabricante;
        this.nombre = nombre;
        this.costo = costo;
        this.precioAlMayor = precioAlMayor;
        this.precioAlMenor = precioAlMenor;
        this.utilidad = utilidad;
        this.aplicaIGV = aplicaIGV;
        this.stockMinimo = stockMinimo;
        this.tipo = tipo;
        this.idMoneda = idMoneda;
        this.idClase = idClase;
        this.idMarca = idMarca;
        this.idModelo = idModelo;
        this.ubicacion = ubicacion;
        this.unidadPrincipal = unidadPrincipal;
        this.peso = peso;
        initBasic();
    }
    
    
    
    

    public Producto() {
        initBasic();
    }
    
    private void initBasic()
    {
        this.setNombreTabla(nt);
        this.setCampoClavePrimaria(COLUMNA_PK);
        this.setCampoExistencial(COLUMNA_ACTIVO);
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUnidadPrincipal() {
        return unidadPrincipal;
    }

    public void setUnidadPrincipal(String unidadPrincipal) {
        this.unidadPrincipal = unidadPrincipal;
    }

    
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoDelFabricante() {
        return codigoDelFabricante;
    }

    public void setCodigoDelFabricante(String codigoDelFabricante) {
        this.codigoDelFabricante = codigoDelFabricante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getPrecioAlMayor() {
        return precioAlMayor;
    }

    public void setPrecioAlMayor(Double precioAlMayor) {
        this.precioAlMayor = precioAlMayor;
    }

    public Double getPrecioAlMenor() {
        return precioAlMenor;
    }

    public void setPrecioAlMenor(Double precioAlMenor) {
        this.precioAlMenor = precioAlMenor;
    }

    public Integer getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(Integer utilidad) {
        this.utilidad = utilidad;
    }

    public Integer getAplicaIGV() {
        return aplicaIGV;
    }

    public void setAplicaIGV(Integer aplicaIGV) {
        this.aplicaIGV = aplicaIGV;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Integer idMoneda) {
        this.idMoneda = idMoneda;
    }

    public FileInputStream getImagen() {
        return imagen;
    }

    public void setImagen(FileInputStream imagen) {
        this.imagen = imagen;
    }
    
    public void setImagenDA(FileInputStream foto,int longitud)
    {
        dat = new DatoArchivo(foto,longitud);
        this.imagen = foto;
    }
    
     public void setImagenDA(DatoArchivo dat)
    {
        this.dat = dat;
        this.imagen = dat.getFis();
    }
     
     public DatoArchivo getImagen(String s)
    {
        return dat;
    }

    public Integer getIdClase() {
        return idClase;
    }

    public void setIdClase(Integer idClase) {
        this.idClase = idClase;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
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
        hash = 29 * hash + (this.idProducto != null ? this.idProducto.hashCode() : 0);
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
        final Producto other = (Producto) obj;
        if (this.idProducto != other.idProducto && (this.idProducto == null || !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        System.out.println("este di= "+this.idProducto +" = "+other.idProducto);
        return true;
    }

    @Override
    public String toString() {
        return nombre + " - " + costo;
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
        Producto us = null; 
        try {     
                   us = new Producto();
                   us.setPrimaryKey(rs.getInt(1)); 
                   us.setIdProducto(rs.getInt(1));
                   us.setCodigoBarras(rs.getString(2));
                   us.setCodigo(rs.getString(3));
                   us.setCodigoDelFabricante(rs.getString(4));
                   us.setNombre(rs.getString(5));
                   us.setCosto(rs.getDouble(6));
                   us.setPrecioAlMayor(rs.getDouble(7));
                   us.setPrecioAlMenor(rs.getDouble(8));
                   us.setUtilidad(rs.getInt(9));
                   us.setAplicaIGV(rs.getInt(10));
                   us.setStockMinimo(rs.getInt(11));
                   us.setTipo(rs.getString(12));
                   us.setIdMoneda(rs.getInt(13));
                   //us.setImagen(rs.getBinaryStream(14));
                   us.setIdClase(rs.getInt(15));
                   us.setIdMarca(rs.getInt(16));
                   us.setIdModelo(rs.getInt(17));
                   us.setUbicacion(rs.getString(18));
                   us.setUnidadPrincipal(rs.getString(19));                   
                   us.setPeso(rs.getDouble(20));
                   us.setActivo(rs.getInt(21));                
                 
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return us;
    }

    @Override
    public Object[] llenarValores() {
       
        String campos = Producto.CAMPOS_NOID_NOIMAGE;        
        Object[] valores = {
                   this.getCodigoBarras(), this.getCodigo(),
                   this.getCodigoDelFabricante(),
                   this.getNombre(),
                   this.getCosto(),
                   this.getPrecioAlMayor(),
                   this.getPrecioAlMenor(),
                   this.getUtilidad(),
                   this.getAplicaIGV(),
                   this.getStockMinimo(),
                   this.getTipo(),
                   this.getIdMoneda(),                   
                   this.getIdClase(),
                   this.getIdMarca(),
                   this.getIdModelo(),
                   this.getUbicacion(),
                   this.getUnidadPrincipal(),
                   this.getPeso(),
                   this.getActivo(), 
                   this.getPrimaryKey()
                   };
              
           if(this.getImagen(null) != null)
            {
                campos = Producto.CAMPOS_NO_ID;
                 valores = new Object[]{this.getCodigoBarras(), this.getCodigo(),
                   this.getCodigoDelFabricante(),
                   this.getNombre(),
                   this.getCosto(),
                   this.getPrecioAlMayor(),
                   this.getPrecioAlMenor(),
                   this.getUtilidad(),
                   this.getAplicaIGV(),
                   this.getStockMinimo(),
                   this.getTipo(),
                   this.getIdMoneda(),
                   this.getImagen(null),
                   this.getIdClase(),
                   this.getIdMarca(),
                   this.getIdModelo(),
                   this.getUbicacion(),
                   this.getUnidadPrincipal(),
                   this.getPeso(),
                   this.getActivo(),
                   this.getPrimaryKey()
                   };
            } 
           
           return valores;
    }
    
}
