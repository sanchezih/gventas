package modelbd;
/**
 *
 * @author Ferz Código Lite - https://codigolite.com
 */
public class Moneda extends SimpleModelo{
    public static final String TABLA = "gv_moneda";
    public static final String PK_COLUMNA = "idm";
    private String simbolo;
    private int predeterminado;
    public Moneda() {        
        this.setCampoClavePrimaria(PK_COLUMNA);
        this.setNombreTabla(TABLA);
    }
    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    @Override
    public String toString() {
        return simbolo + " - "+this.nombre;
    }

    public int getPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(int predeterminado) {
        this.predeterminado = predeterminado;
    }
    
    public boolean getPredeterminadoBool()
    {
        if(predeterminado == 1)
        {
            return true;
        }else
        {
            return false;
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Moneda other = (Moneda) obj;
        if ((this.primaryKey == null) ? (other.primaryKey  != null) : !this.primaryKey .equals(other.primaryKey)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.primaryKey  != null ? this.primaryKey.hashCode() : 0);
        return hash;
    }
    
    
    
}
