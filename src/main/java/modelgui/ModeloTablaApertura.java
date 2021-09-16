package modelgui;

import controllers.CAlmacen;
import controllers.CAlmacenProducto;
import modelbd.Almacen;
import modelbd.AlmacenProducto;
import modelbd.Producto;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class ModeloTablaApertura extends AbstractTableModel{
    private ArrayList<AlmacenProducto> aps = new ArrayList<AlmacenProducto>();
    private String[] nombreColumnas = {"Almacen","Cantidad"};
    private Class[] clases = {Object.class,Integer.class}; 
    private JLabel aviso;
    //private CAlmacen controllerAlmacen;
   // private Almacen almacen;
    private Integer totalProducto = 0;
    
    private CAlmacenProducto controllerAp;
    public ModeloTablaApertura(JLabel aviso) {
       
        controllerAp =  new CAlmacenProducto();
        this.aviso = aviso;
        aps = controllerAp.getRegistros(new Object[]{1});
        setTotal(); 
    }
    
    public ModeloTablaApertura(Producto prd) {       
        controllerAp =  new CAlmacenProducto();        
        aps = controllerAp.getRegistroPorProducto(prd.getPrimaryKey());
        setTotal();
    }

    public ModeloTablaApertura(JLabel aviso,Producto prd) {
        
        controllerAp = new CAlmacenProducto();
        this.aviso = aviso;       
        aps = controllerAp.getRegistroPorProducto(prd.getPrimaryKey());
        setTotal();
      
    }

    public ModeloTablaApertura() {
        setTotal();
    }
    
    private void setTotal()
    {
        if(aps.size()>0)
        {
            for(AlmacenProducto al: aps)
            {
                this.totalProducto += al.getCantidadActual();
            }
            if(aviso != null)
            {
              aviso.setText("Cantidad Tota de Productos: "+this.totalProducto);  
            } 
        }
    }

    public int getRowCount() {
        return aps.size();
    }

 
    public int getColumnCount() {
        return this.nombreColumnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return this.nombreColumnas[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return clases[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0: return false;
            case 1: return true;    
        }
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        aps.get(rowIndex).setCantidadActual(Integer.parseInt(aValue.toString()));
        //aps.get(rowIndex).setCantida(Integer.parseInt(aValue.toString()));
        int ctr = 0;
        for(AlmacenProducto wp: aps)
        {
            ctr += wp.getCantidadActual();
        }
        if(aviso != null)
        {
          aviso.setText("Cantidad Tota de Productos: "+ctr);  
        }        
        totalProducto = ctr;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public Integer getTotalProductos()
    {
        return totalProducto;
    }

  
    public Object getValueAt(int rowIndex, int columnIndex) {
       switch(columnIndex){
           case 0: return aps.get(rowIndex).getAlmacen().getNombre();
           case 1: return aps.get(rowIndex).getCantidadActual()==null?0:aps.get(rowIndex).getCantidadActual();
           default:return null;
       }
    }
    
    public ArrayList<AlmacenProducto> getDatos()
    {        
        return aps;
    }
    
    public class WrapperProductoAlmacen{
        public Almacen almacen;
        public Producto producto;
        public Integer cantida;
        
        public AlmacenProducto almacenProducto;
        
        public boolean existe = false;

        public WrapperProductoAlmacen(Almacen almacen, Producto producto, Integer cantida) {
            this.almacen = almacen;
            this.producto = producto;
            this.cantida = cantida;
        }

        public WrapperProductoAlmacen(Almacen almacen, Producto producto, Integer cantida, boolean existe) {
            this.almacen = almacen;
            this.producto = producto;
            this.cantida = cantida;
            this.existe = existe;
        }

        public WrapperProductoAlmacen(AlmacenProducto almacenProducto) {
            this.almacenProducto = almacenProducto; 
            this.almacen = this.almacenProducto.getAlmacen();
            this.producto = this.almacenProducto.getProducto();
            this.cantida = this.almacenProducto.getCantidadActual();
            this.existe = true;
        }
        public WrapperProductoAlmacen() {
        }

        public Almacen getAlmacen() {
            return almacen;
        }

        public void setAlmacen(Almacen almacen) {
            this.almacen = almacen;
        }

        public Producto getProducto() {
            return producto;
        }

        public void setProducto(Producto producto) {
            this.producto = producto;
        }

        public Integer getCantida() {
            return cantida;
        }

        public void setCantida(Integer cantida) {
            this.cantida = cantida;
        }

        public AlmacenProducto getAlmacenProducto() {
            return almacenProducto;
        }

        public void setAlmacenProducto(AlmacenProducto almacenProducto) {
            this.almacenProducto = almacenProducto;
        }

        public boolean isExiste() {
            return existe;
        }

        public void setExiste(boolean existe) {
            this.existe = existe;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 23 * hash + (this.almacen != null ? this.almacen.hashCode() : 0);
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
            final WrapperProductoAlmacen other = (WrapperProductoAlmacen) obj;
            if (this.almacen != other.almacen && (this.almacen == null || !this.almacen.equals(other.almacen))) {
                return false;
            }
            return true;
        }
    }
    
}
