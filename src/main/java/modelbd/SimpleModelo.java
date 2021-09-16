package modelbd;

import core.JAbstractModelBD;
import java.io.Serializable;
import java.sql.ResultSet;

/**
 * Esta Clase encapsula propiedades comunes de las tablas: Modelo, Marca, Clase, moneda y almacen
 * @author Ferz Código Lite - https://codigolite.com
 */
public class SimpleModelo extends JAbstractModelBD implements Serializable,IModel {
    
    protected String nombre;
    protected Integer activo = 1;
    public static final String CLASE = "gv_clase";
    public static final String MODELO = "gv_modelo";
    public static final String MARCA = "gv_marca";
    public static final String KEY_CLASE = "idclase";
    public static final String KEY_MODELO = "idmodelo";
    public static final String KEY_MARCA = "idmarca";
    public static final String ACTIVO = "activo";
    
    public static final int OMARCA = 1;
    public static final int OMODELO = 2;
    public static final int OCLASE = 3;
    

    /**
     * 
     * @param opcion OMARCA = 1,OMODELO = 2,OCLASE = 3
     */
    public SimpleModelo(int opcion) {
        this.setCampoExistencial(ACTIVO);
        switch(opcion)
        {
            case OCLASE:
                this.setNombreTabla(CLASE);
                this.setCampoClavePrimaria(KEY_CLASE);                
                break;
            case OMODELO: 
                this.setNombreTabla(MODELO);
                this.setCampoClavePrimaria(KEY_MODELO);      
                break;
           case OMARCA: 
                this.setNombreTabla(MARCA);
                this.setCampoClavePrimaria(KEY_MARCA);      
                break;     
        }
    }

    public SimpleModelo() {
        this.setCampoExistencial(ACTIVO);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }
    
    public String getActivoString() {
        return activo==1?"Activo":"No activado";
    }

    public boolean getActivoBool() {
        return activo==1?true:false;
    }
    
    public void setActivo(boolean activo) {
        if(activo)
        {
           this.activo = 1; 
        }else
        {
          this.activo = 0; 
        }
    }

    @Override
    public String toString() {
        return nombre;
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
