package modelbd;

/**
 *
 * @author Ferz Código Lite - https://codigolite.com
 */
public class Almacen extends SimpleModelo{
    public static final String TABLA = "gv_almacen";
    public static final String PK_COLUMNA = "ida";
    private String direccion;
    private Integer encargado;

    public Almacen() {       
        this.setCampoClavePrimaria(PK_COLUMNA);
        this.setNombreTabla(TABLA);
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getEncargado() {
        return encargado;
    }

    public void setEncargado(Integer encargado) {
        this.encargado = encargado;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Almacen other = (Almacen) obj;
        if ((this.primaryKey == null) ? (other.primaryKey != null) : !this.primaryKey.equals(other.primaryKey)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.primaryKey != null ? this.primaryKey.hashCode() : 0);
        return hash;
    }
    
    
    
}
