package modelgui;

import controllers.CProveedor;
import modelbd.Proveedor;

/**
 *
 * @author Ferz Código Lite - https://codigolite.com
 */
public class ModeloTablaProveedor extends ModeloTabla{

    String[] columnas = {"Ruc","Proveedor","Direccion","Telefono"};
    public static int TODOS = -1;
    public static int ACTIVOS = 1;
    public static int NO_ACTIVOS = 0;    
    
    
    public ModeloTablaProveedor() {
        cc = new CProveedor();
        this.nombreColumnas = columnas;       
        registros = ((CProveedor)cc).getRegistros(null);
    }
    
    public ModeloTablaProveedor(int opcion) {
        cc = new CProveedor();
        this.nombreColumnas = columnas;       
        registros = ((CProveedor)cc).getRegistros(new Object[]{new Integer(opcion)});
    }
    
    public ModeloTablaProveedor(String[] campo,Object[] valor) {
        cc = new CProveedor();
        this.nombreColumnas = columnas;       
        registros = ((CProveedor)cc).getRegistros(campo,valor);
    }
    
    public ModeloTablaProveedor(int inicio,int finalPag) {
        cc = new CProveedor();
        this.nombreColumnas = columnas;
        cc.setNumPaginador(inicio, finalPag);
        registros = ((CProveedor)cc).getRegistros(null);
    }
    public ModeloTablaProveedor(int opcion,int inicio,int finalPag) {
        cc = new CProveedor();
        this.nombreColumnas = columnas;
        cc.setNumPaginador(inicio, finalPag);
        switch(opcion)
        {
            case 0: 
            case 1:
                registros = ((CProveedor)cc).getRegistros(new Object[]{new Integer(opcion)});
                break;
            default:
                registros = ((CProveedor)cc).getRegistros(null);
        }
        
    }
    
    public int getCantidadRegistros()
    {
        return cc.getCantidadRegistros();
    }
    
    public static String cmpRuc()
    {
        return Proveedor.CAMPO_RUC;
    }
    
    public static String cmpRazonSocial()
    {
        return Proveedor.CAMPO_NOMBRE;
    }
    
    public static String cmpTelefono()
    {
        return Proveedor.CAMPO_TELEFONO;
    }
    
    public static String cmpActivo()
    {
        return Proveedor.campoActivo;
    }
    
    

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }
    
  
    public Object getValueAt(int rowIndex, int columnIndex) {        
        switch(columnIndex)
        {
            case 0: return ((Proveedor)registros.get(rowIndex)).getRuc();
            case 1: return ((Proveedor)registros.get(rowIndex)).getRazonSocial();     
            case 2: return ((Proveedor)registros.get(rowIndex)).getDireccion();  
            case 3: return ((Proveedor)registros.get(rowIndex)).getTelefono();  
            default: return null;   
        }
    }
    
}
