package modelbd;

import core.JAbstractModelBD;
import java.io.Serializable;
import java.sql.ResultSet;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class Cliente extends JAbstractModelBD implements Serializable,IModel {
    
    private static final long serialVersionUID = 1L;
    public static final String nt = "gv_cliente";
    
    private Long idc;
    private String ruc;    
    private String dni;   
    private String nombre;   
    private String apellidos;    
    private String tipo;   
    private String email;   
    private String telefono;   
    private String movil;    
    private String nextel;   
    private String fax;    
    private long fecReg;

    public Cliente() {
    }

    public Cliente(Long idc) {
        this.idc = idc;
    }

    public Cliente(Long idc, String nombre, String apellidos, long fecReg) {
        this.idc = idc;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fecReg = fecReg;
    }

    public Long getIdc() {
        return idc;
    }

    public void setIdc(Long idc) {
        this.idc = idc;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getNextel() {
        return nextel;
    }

    public void setNextel(String nextel) {
        this.nextel = nextel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public long getFecReg() {
        return fecReg;
    }

    public void setFecReg(long fecReg) {
        this.fecReg = fecReg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idc != null ? idc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idc == null && other.idc != null) || (this.idc != null && !this.idc.equals(other.idc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.GvCliente[ idc=" + idc + " ]";
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
