package modelgui;

import core.CriterioSQL;
import core.JAbstractModelBD;
import core.Paginador;
import modelbd.Compra;
import util.Helper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Código Lite
 */
public class ModeloTablaCompra extends ModeloTabla{

    private Compra compras;
    private Paginador paginador;
    private CriterioSQL criterio;
    public ModeloTablaCompra() {
         compras = new Compra();
         paginador = new Paginador();
         paginador.setLimite(3);
         paginador.setTipoObjeto(new Compra());         
        this.nombreColumnas = new String[]{"Encargado","Tipo Documento","Nro. Documento","Items",
                                            "SubTotal","Total","Fecha","Estado","Observacion"};
        registros = (ArrayList<JAbstractModelBD>)paginador.getItem();
    }
    
    public ModeloTablaCompra(Paginador paginador){
        this.nombreColumnas = new String[]{"Encargado","Tipo Documento","Nro. Documento","Items",
                                            "SubTotal","Total","Fecha","Estado","Observacion"};
        registros = (ArrayList<JAbstractModelBD>)paginador.getItem();
    }
    
     public ModeloTablaCompra(List<JAbstractModelBD> compras){
        this.nombreColumnas = new String[]{"Encargado","Tipo Documento","Nro. Documento","Items",
                                            "SubTotal","Total","Fecha","Estado","Observacion"};
        registros = (ArrayList<JAbstractModelBD>)compras;
    }
     
     public Paginador getPaginador()
     {
         return paginador;
     }

    
 
    public Object getValueAt(int rowIndex, int columnIndex) {
        Compra compra = (Compra)registros.get(rowIndex);
        switch(columnIndex)
        {
            case 0: return compra.getIdUsuario().getNombre();
            case 1: return compra.getTipoDocumento();
            case 2: return compra.getNumeroDocumento();
            case 3: return compra.getNumeroItems();
            case 4: return compra.getSubtotla();
            case 5: return compra.getTotal();
            case 6: return Helper.getFechaFormateada(compra.getFecha(), Helper.DIA_MES_ANIO);
            case 7: return compra.getEstado();
            case 8: return compra.getObservaciones();
            default: return null;
        }
    }
    
}
