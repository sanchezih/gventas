package modelbd;

import core.JAbstractModelBD;
import java.io.Serializable;
import java.sql.ResultSet;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class Bitacora extends JAbstractModelBD implements Serializable,IModel{
    private static final long serialVersionUID = 1L;
    public static final String nt = "gv_bitacora";
    public static final String COLUMNA_PK = "idbitacora";
    public static final String COLUMNA_USER = "idusuario";
    public static final String COLUMNAS_FULL = "idbitacora,idusuario, OS, arquitectura, verssion, nomCuenta,	ultima_actividad, fecha_actividad";
    public static final String COLUMNAS_NO_ID = "idusuario, OS, arquitectura, verssion, nomCuenta, ultima_actividad, fecha_actividad";
    private Long idbitacora;   
    private int idusuario;  
    private String os;
    private String arquitectura;
    private String version;
    private String usuario; //nombre de usuario en el sitema operativo
    private long ultimaActividad;    
    private long fechaActividad;

    public Bitacora() {
        os = System.getProperty("os.name");
        arquitectura = System.getProperty("os.arch");
        version = System.getProperty("os.version");
        usuario = System.getProperty("user.name");
        initBasic();
    }
    
     private void initBasic()
    {
        this.setNombreTabla(nt);
        this.setCampoClavePrimaria(COLUMNA_PK);        
    }

    public String getArquitectura() {
        return arquitectura;
    }

    public void setArquitectura(String arquitectura) {        
        this.arquitectura = arquitectura;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getFechaActividad() {
        return fechaActividad;
        
    }

    public void setFechaActividad(long fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public Long getIdbitacora() {
        return idbitacora;
    }

    public void setIdbitacora(Long idbitacora) {
        this.idbitacora = idbitacora;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public long getUltimaActividad() {
        return ultimaActividad;
    }

    public void setUltimaActividad(long ultimaActividad) {
        this.ultimaActividad = ultimaActividad;
    }
    
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbitacora != null ? idbitacora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bitacora)) {
            return false;
        }
        Bitacora other = (Bitacora) object;
        if ((this.idbitacora == null && other.idbitacora != null) || (this.idbitacora != null && !this.idbitacora.equals(other.idbitacora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.GvBitacora[ idbitacora=" + idbitacora + " ]";
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
