package modelbd;

import core.JAbstractModelBD;
import java.io.Serializable;
import java.sql.ResultSet;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class Proveedor extends JAbstractModelBD implements Serializable,IModel{
    
    private static final long serialVersionUID = 1L; 
    public static final String nt = "gv_proveedor";
    public static final String campoActivo = "activo";
    public static final String CAMPO_RUC = "ruc";
    public static final String CAMPO_NOMBRE = "razon_social";
    public static final String CAMPO_TELEFONO = "telefono";
    
    
    private Integer idproveedor;   
    private String ruc;   
    private String razonSocial;
    private String direccion;
    private String ciudad;   
    private String telefono;    
    private String nextel;    
    private String movil;   
    private String fax;   
    private String ctaBancaria;   
    private String nomContacto;    
    private String email;   
    private String rubro;    
    private String productos;
    private Integer activo;

    public Proveedor() {
        initBasic();
    }

    public Proveedor(Integer idproveedor) {
        this.idproveedor = idproveedor;
        initBasic();
    }

    public Proveedor(Integer idproveedor, String razonSocial) {
        this.idproveedor = idproveedor;
        this.razonSocial = razonSocial;
        initBasic();
    }
    public Proveedor(Integer idproveedor,String ruc, String razonSocial) {
        this.idproveedor = idproveedor;
        this.ruc=ruc;
        this.razonSocial = razonSocial;
        initBasic();
    }
    
    private void initBasic()
    {
        this.setNombreTabla(nt);
        this.setCampoClavePrimaria("idproveedor");
        this.setCampoExistencial("activo");
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(Integer idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNextel() {
        return nextel;
    }

    public void setNextel(String nextel) {
        this.nextel = nextel;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCtaBancaria() {
        return ctaBancaria;
    }

    public void setCtaBancaria(String ctaBancaria) {
        this.ctaBancaria = ctaBancaria;
    }

    public String getNomContacto() {
        return nomContacto;
    }

    public void setNomContacto(String nomContacto) {
        this.nomContacto = nomContacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproveedor != null ? idproveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {   
        
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.idproveedor == null && other.idproveedor != null) || (this.idproveedor != null && !this.idproveedor.equals(other.idproveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.razonSocial;
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
