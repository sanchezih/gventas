package modelgui;

import controllers.CSimpleModelo;
import modelbd.SimpleModelo;

/**
 *
 * @author Ferz Código Lite - https://codigolite.com
 */
public class ModeloTablaSimpleModelo extends ModeloTabla{

    
    public static int TODOS = -1;
    public static int ACTIVOS = 1;
    public static int NO_ACTIVOS = 0;    
    String[] columnas = {"Nombre","Activo"};
    
    public ModeloTablaSimpleModelo(int tipoTabla) {
        cc = new CSimpleModelo(tipoTabla);
        this.nombreColumnas = columnas;       
        registros = ((CSimpleModelo)cc).getRegistros(null);
    }
    
    public ModeloTablaSimpleModelo(int tipoTabla,int opcion) {
        cc = new CSimpleModelo(tipoTabla);
        this.nombreColumnas = columnas;       
        registros = ((CSimpleModelo)cc).getRegistros(new Object[]{new Integer(opcion)});
    }
    
    public ModeloTablaSimpleModelo(int tipoTabla,String[] campo,Object[] valor) {
        cc = new CSimpleModelo(tipoTabla);
        this.nombreColumnas = columnas;       
        registros = ((CSimpleModelo)cc).getRegistros(campo,valor);
    }
    
    public ModeloTablaSimpleModelo(int tipoTabla,int inicio,int finalPag) {
        cc = new CSimpleModelo(tipoTabla);
        this.nombreColumnas = columnas;
        cc.setNumPaginador(inicio, finalPag);
        registros = ((CSimpleModelo)cc).getRegistros(null);
    }
    public ModeloTablaSimpleModelo(int tipoTabla,int opcion,int inicio,int finalPag) {
        cc = new CSimpleModelo(tipoTabla);
        this.nombreColumnas = columnas;
        cc.setNumPaginador(inicio, finalPag);
        switch(opcion)
        {
            case 0: 
            case 1:
                registros = ((CSimpleModelo)cc).getRegistros(new Object[]{new Integer(opcion)});
                break;
            default:
                registros = ((CSimpleModelo)cc).getRegistros(null);
        }
        
    }
    
    public int getCantidadRegistros()
    {
        return cc.getCantidadRegistros();
    }
    

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex)
        {
            case 0: return ((SimpleModelo)registros.get(rowIndex)).getNombre();
            case 1: return ((SimpleModelo)registros.get(rowIndex)).getActivoString(); 
            default:return null;    
        }
    }
    
}
