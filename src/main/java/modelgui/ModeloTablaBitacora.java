package modelgui;

import controllers.CBitacora;
import modelbd.Bitacora;
import util.Helper;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Ferz Código Lite - https://codigolite.com
 */
public class ModeloTablaBitacora extends ModeloTabla{    
    private CBitacora cbt;
    private String[] ncolumnas = {"Sistema","Arquitectura","Version","Usuario Sistema","Ultimo Ingreso","Ingreso"};

    public ModeloTablaBitacora(Integer id) {
        this.nombreColumnas = ncolumnas;
        cbt = new CBitacora();
        registros = cbt.getRegistros(new Object[]{id});
        System.out.println(registros.size());
    }

   
    public Object getValueAt(int rowIndex, int columnIndex) {
       switch(columnIndex)
       {
           case 0:return ((Bitacora)registros.get(rowIndex)).getOs();
           case 1:return ((Bitacora)registros.get(rowIndex)).getArquitectura();    
           case 2:return ((Bitacora)registros.get(rowIndex)).getVersion();
           case 3:return ((Bitacora)registros.get(rowIndex)).getUsuario();
           case 4:return Helper.getFechaFormateada(new Date(((Bitacora)registros.get(rowIndex)).getUltimaActividad()), Helper.ANIO_MES_DIA_B);    
           case 5:return Helper.getFechaFormateada(new Date(((Bitacora)registros.get(rowIndex)).getFechaActividad()), Helper.ANIO_MES_DIA_B);    
           default:return null;
       }
    }
    
}
