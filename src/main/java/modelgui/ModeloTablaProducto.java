package modelgui;

import controllers.CProducto;
import modelbd.Producto;
import java.util.ArrayList;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class ModeloTablaProducto extends ModeloTabla{
    
    /**
     *  Indica si este modelo se aplicara a una tabla
     *  que se mostrara en un formulario de busqueda,compra o venta
     *  
     */
    public final static int BUSQUEDA = 1;
    public final static int COMPRA= 2;
    public final static int VENTA = 3;
    
    private final String[] columnasBusqueda = {"Codigo de Barras","Codigo","Nombre",
                                               "Costo"};
    private final String[] columnasCompra = {"Codigo de Barras","Codigo","Nombre",
                                            "P. Al Mayor", "Costo", "Utilidad",
                                            "Stock Min."};
                                                
    private final String[] columnasVenta = {"Nombre","Unidad","P. Al Mayor", "P. Al Menor"
                                            };
    
    private int tipoTabla;
    private CProducto producto;

    public ModeloTablaProducto() {
        producto = new CProducto();
        this.nombreColumnas = columnasBusqueda;
        tipoTabla = BUSQUEDA;
        this.registros = producto.getRegistros(new Object[]{1});
    }
    
    public ModeloTablaProducto(ArrayList<Producto> productos) {       
        this.nombreColumnas = columnasBusqueda;
        tipoTabla = BUSQUEDA;
        this.registros = productos;
    }

    public ModeloTablaProducto(ArrayList<Producto> productos,int tipoTabla) {        
        switch(tipoTabla)
        {
            case COMPRA: this.tipoTabla = COMPRA;
                        this.nombreColumnas = columnasCompra;
                break;
            case VENTA: this.tipoTabla = VENTA;
                        this.nombreColumnas = columnasVenta;
                break; 
            default: this.tipoTabla = BUSQUEDA;
                     this.nombreColumnas = columnasVenta;
        }
        this.registros = productos;
    }
    


    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(tipoTabla)
        {
            case BUSQUEDA:
                 switch(columnIndex)
                 {
                     case 0:return ((Producto)registros.get(rowIndex)).getCodigoBarras();
                     case 1:return ((Producto)registros.get(rowIndex)).getCodigo();
                     case 2:return ((Producto)registros.get(rowIndex)).getNombre();
                     case 3:return ((Producto)registros.get(rowIndex)).getCosto();                     
                 }
                break;
            case COMPRA: 
                switch(columnIndex)
                 {
                     case 0:return ((Producto)registros.get(rowIndex)).getCodigoBarras();
                     case 1:return ((Producto)registros.get(rowIndex)).getCodigo();
                     case 2:return ((Producto)registros.get(rowIndex)).getNombre();
                     case 3:return ((Producto)registros.get(rowIndex)).getPrecioAlMayor();                     
                     case 4:return ((Producto)registros.get(rowIndex)).getCosto();                         
                     case 5:return ((Producto)registros.get(rowIndex)).getUtilidad();                         
                     case 6:return ((Producto)registros.get(rowIndex)).getStockMinimo();                         
                 }
                break;
            case VENTA:
                switch(columnIndex)
                 {
                     case 0:return ((Producto)registros.get(rowIndex)).getNombre();
                     case 1:return ((Producto)registros.get(rowIndex)).getUnidadPrincipal();
                     case 2:return ((Producto)registros.get(rowIndex)).getPrecioAlMayor();
                     case 3:return ((Producto)registros.get(rowIndex)).getPrecioAlMenor();                    
                 }
                break; 
            default: return null;
        }
        
        return null;
    }
    
}
