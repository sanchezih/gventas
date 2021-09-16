package modelgui;

import controllers.CDetalleCompra;
import modelbd.Compra;
import modelbd.DetalleCompra;
import modelbd.Producto;
import ventanas.ICompra;
import ventanas.paneles.PanelAccion;
import java.util.ArrayList;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class ModeloTablaDetalleCompra extends ModeloTabla{

    private Class[] tipoColumnas;
    private Double subTotal = 0.0;
    private Integer numItems = 0;  
    private CDetalleCompra controllerDetalle;
    private ArrayList<DetalleCompra> ProductosEliminados; 
    public ModeloTablaDetalleCompra() {
        this.registros = new ArrayList<DetalleCompra>();
        DetalleCompra dc = new DetalleCompra();
        dc.setIdProducto(new Producto());
        this.registros.add(dc);
        this.nombreColumnas = new String[]{"Producto Seleccionado",
                               "Cantidad","Precio","Descuento %","Importe","Accion"};
        tipoColumnas = new Class[]{String.class,Integer.class,Double.class,Double.class,
                                   Double.class,PanelAccion.class};
    }

    public ModeloTablaDetalleCompra(Compra cpr) {
        controllerDetalle = new CDetalleCompra();
        this.registros = controllerDetalle.getRegistrosPorCompra(cpr.getId());
        this.nombreColumnas = new String[]{"Producto Seleccionado",
                               "Cantidad","Precio","Descuento %","Importe","Accion"};
        tipoColumnas = new Class[]{String.class,Integer.class,Double.class,Double.class,
                                   Double.class,PanelAccion.class};
    }
    
    //metodo que guarda de forma temporal productos de una compra
    //pero solo guarda cuando un compra es actulizada
    public void setCopiaEliminados(DetalleCompra detalleCompra)
    {
        if(ProductosEliminados == null)
        {
            ProductosEliminados = new ArrayList();            
        }
        ProductosEliminados.add(detalleCompra);
    }
    
    public void quitarProductoDeEliminados(Producto prd)
    {
        if(this.ProductosEliminados !=null)
        {
            for(Object obj:this.ProductosEliminados)
            {
                DetalleCompra dc = (DetalleCompra)obj;
                if(dc.getIdProducto().getPrimaryKey() != null)
                {
                    if(dc.getIdProducto().equals(prd))
                    {
                        ProductosEliminados.remove(dc);
                        break;                   
                    }
                }
            }
            if(ProductosEliminados.isEmpty())
               ProductosEliminados = null;   
        } 
    }

    public ArrayList<DetalleCompra> getProductosEliminados()
    {
        return ProductosEliminados;
    }
    

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return tipoColumnas[columnIndex];
    }
    
    

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        DetalleCompra dc = (DetalleCompra)this.registros.get(rowIndex);
        if(dc.getIdProducto().getPrimaryKey() != null)
        {
            if(columnIndex > 0 && columnIndex!=2)
            {
                return true;
            }
        }else if(dc.getIdProducto().getPrimaryKey() == null)
        {
            if(columnIndex == this.nombreColumnas.length-1)
            {
                return true;
            }
        }
        return false;
    }

    public void setValueAt(ICompra compra,Object aValue, int rowIndex, int columnIndex)
    {
        this.setValueAt(aValue, rowIndex, columnIndex);
        compra.setTotales();
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //super.setValueAt(aValue, rowIndex, columnIndex);
        DetalleCompra dc = (DetalleCompra)this.registros.get(rowIndex);
        if(dc.getIdProducto().getPrimaryKey() != null)
        {
             switch(columnIndex)
            {
                case 1:((DetalleCompra)registros.get(rowIndex)).setCantidad(Integer.parseInt(aValue.toString()));
                        //Double imprt = Integer.parseInt(aValue.toString()) * ((DetalleCompra)registros.get(rowIndex)).getIdProducto().getPrecioAlMenor();
                       //((DetalleCompra)registros.get(rowIndex)).setImporte(imprt);
                    break;
                case 3:
                    if(aValue == null)
                    {
                        aValue = new Double(0.00);
                    }
                    ((DetalleCompra)registros.get(rowIndex)).setDescuento((Double)aValue);
                    Double stotal = ((DetalleCompra)registros.get(rowIndex)).getIdProducto().getCosto()*((DetalleCompra)registros.get(rowIndex)).getCantidad();                    
                    double desc = (((Double)aValue)/100)*stotal;
                    desc = stotal-desc;
                    ((DetalleCompra)registros.get(rowIndex)).setImporte(desc);                    
                    break;
                case 4:                    
                    double descs = (((DetalleCompra)registros.get(rowIndex)).getDescuento()/100)*((Double)aValue);
                    descs = ((Double)aValue)-descs;
                    ((DetalleCompra)registros.get(rowIndex)).setImporte(descs);                     
                    break;
            }
              Double tmpSt = 0.0;
                for(Object obj:this.registros)
                {
                    DetalleCompra dc2 = (DetalleCompra)obj;
                    if(dc2.getIdProducto().getPrimaryKey() != null)
                    {
                        Double stotal = dc2.getIdProducto().getCosto()*dc2.getCantidad(); 
                        double desc = (dc2.getDescuento()/100)*stotal;
                        desc = stotal-desc;
                        tmpSt += desc;                        
                    }
                }
                this.subTotal = tmpSt;
             this.fireTableCellUpdated(rowIndex, columnIndex);  
        }
    }
    
    
    
    public void agregar(Producto prd)
    {
        numItems=0;
        DetalleCompra dc = new DetalleCompra();
        dc.setIdProducto(prd);       
        this.registros.add(dc);
        Double tmpSt = 0.0;
        for(Object obj:this.registros)
        {
            DetalleCompra dc2 = (DetalleCompra)obj;
            if(dc2.getIdProducto().getPrimaryKey() != null)
            {
                Double stotal = dc2.getIdProducto().getCosto()*dc2.getCantidad(); 
                double desc = (dc2.getDescuento()/100)*stotal;
                desc = stotal-desc;
                tmpSt += stotal-desc;
                numItems++;
            }
        }
        this.subTotal = tmpSt;
        this.fireTableRowsInserted(this.registros.size(), this.registros.size());
    }
    
    public void remplazarProducto(Producto prd,int index)
    {
        DetalleCompra dc = new DetalleCompra();
        dc.setIdProducto(prd); 
        registros.remove(index);
        this.agregar(prd);               
    }
    
    @Override
    public void quitarFila(int fila)
    {
        registros.remove(fila);
        numItems=0;
        Double tmpSt = 0.0;
        for(Object obj:this.registros)
        {
            DetalleCompra dc2 = (DetalleCompra)obj;
            if(dc2.getIdProducto().getPrimaryKey() != null)
            {
                Double stotal = dc2.getIdProducto().getCosto()*dc2.getCantidad(); 
                double desc = (dc2.getDescuento()/100)*stotal;
                desc = stotal-desc;
                tmpSt += desc;
                numItems++;
            }
        }
        this.subTotal = tmpSt;
        fireTableRowsDeleted(fila, fila);
    }
    
    public boolean existe(Producto prd)
    {
        for(Object obj:this.registros)
        {
            DetalleCompra dc = (DetalleCompra)obj;
            if(dc.getIdProducto().getPrimaryKey() != null)
            {
                if(dc.getIdProducto().equals(prd))
                {
                    return true;                   
                }
            }
        }
        return false;
    }
    
    public Double getSubTotal()
    {
        Double tmpSt = 0.0;
        for(Object obj:this.registros)
        {
            DetalleCompra dc2 = (DetalleCompra)obj;
            if(dc2.getIdProducto().getPrimaryKey() != null)
            {
                Double stotal = dc2.getIdProducto().getCosto()*dc2.getCantidad(); 
                double desc = (dc2.getDescuento()/100)*stotal;
                desc = stotal-desc;
                tmpSt += desc;
            }
        }
        this.subTotal = tmpSt;
        return this.subTotal;
    }
    
    public void contarItems()
    {
        numItems = 0;
        for(Object obj:this.registros)
        {            
            DetalleCompra dc2 = (DetalleCompra)obj;
            if(dc2.getIdProducto().getPrimaryKey() != null)
            {                
                numItems++;
            }
        }
    }
    
    public Integer getNumItems()
    {
        return numItems;
    }

   
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex)
        {
            case 0:return ((DetalleCompra)registros.get(rowIndex)).getIdProducto().getNombre();
            case 1:return ((DetalleCompra)registros.get(rowIndex)).getCantidad();
            case 2:return ((DetalleCompra)registros.get(rowIndex)).getIdProducto().getCosto();
            case 3:return ((DetalleCompra)registros.get(rowIndex)).getDescuento();
            case 4:return ((DetalleCompra)registros.get(rowIndex)).getImporte();
            case 5:return "Agregar/Eliminar";
            default:return null;    
        }
    }
    
    public void limpiar()
    {
        registros.clear();
        DetalleCompra dc = new DetalleCompra();
        dc.setIdProducto(new Producto());
        registros.add(dc);
    }
    
    public ArrayList<DetalleCompra> getDetallesCompra()
    {
        ArrayList<DetalleCompra> dc = new ArrayList<DetalleCompra>();
        for(Object obj:this.registros)
        {            
            DetalleCompra dc2 = (DetalleCompra)obj;
            if(dc2.getIdProducto().getPrimaryKey() != null)
            {                
                dc.add(dc2);
            }
        }
        return dc;
    }
}
