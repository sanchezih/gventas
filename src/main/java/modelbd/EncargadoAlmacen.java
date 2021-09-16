package modelbd;

import core.JAbstractModelBD;
import java.io.Serializable;
import java.sql.ResultSet;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class EncargadoAlmacen extends JAbstractModelBD implements Serializable,IModel{
    
    public static final String nt = "gv_encargadoalmacen";
    public static final String COLUMNA_PK = "idEA";
    public static final String COLUMNA_ACTIVO = "activo";
    public static final String FULL_NOM_CAMPOS = "idEA,idusuario,idalmacen,tipocargo,activo";
    public static final String CAMPOS_NO_ID = "idusuario,idalmacen,tipocargo,activo";
    public static final String[] TIPOS_CARGOS = {"Lider","Empleado"};
    
    private Integer id;
    private Usuario usuario;
    private Almacen almacen;
    private String tipoCargo;
    private Integer activo = 1;

    public EncargadoAlmacen(Integer id, Usuario usuario, Almacen almacen, String tipoCargo, Integer activo) {
        this.id = id;
        this.usuario = usuario;
        this.almacen = almacen;
        this.tipoCargo = tipoCargo;
        this.activo = activo;
        initBasic();
    }
    public EncargadoAlmacen(Usuario usuario, Almacen almacen, String tipoCargo, Integer activo) {
        this.usuario = usuario;
        this.almacen = almacen;
        this.tipoCargo = tipoCargo;
        this.activo = activo;
        initBasic();
    }

    public EncargadoAlmacen(Usuario usuario, Almacen almacen, String tipoCargo) {
        this.usuario = usuario;
        this.almacen = almacen;
        this.tipoCargo = tipoCargo;
        initBasic();
    }

    public EncargadoAlmacen() {
        initBasic();
    }
    
     private void initBasic()
    {
        this.setNombreTabla(nt);
        this.setCampoClavePrimaria(COLUMNA_PK);
        this.setCampoExistencial(COLUMNA_ACTIVO);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public String getTipoCargo() {
        return tipoCargo;
    }

    public void setTipoCargo(String tipoCargo) {
        this.tipoCargo = tipoCargo;
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
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final EncargadoAlmacen other = (EncargadoAlmacen) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
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
