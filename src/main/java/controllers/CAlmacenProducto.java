package controllers;

import core.Ex;
import core.JAbstractController;
import core.JAbstractModelBD;
import modelbd.Almacen;
import modelbd.AlmacenProducto;
import util.Helper;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class CAlmacenProducto extends JAbstractController implements Serializable{

    @Override
    public ArrayList getRegistros() {
        return this.getRegistros(new String[]{},null, null);
    }

    @Override
    public ArrayList getRegistros(Object[] cvl) {
        return this.getRegistros(new String[]{},new String[]{AlmacenProducto.COLUMNA_ACTIVO}, cvl);
    }

    @Override
    public JAbstractModelBD getRegistro() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JAbstractModelBD getRegistro(JAbstractModelBD mdl, boolean opcion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JAbstractModelBD buscarRegistro(Object cvl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public AlmacenProducto getRegistro(Integer idalm,Integer idprd)
    {
        ArrayList<AlmacenProducto> registros = this.getRegistros(new String[]{"idalmacen","idproducto"},new Object[]{idalm,idprd});
        return registros.size()>0?registros.get(0):null;
    }
    
    public AlmacenProducto getRegistro(Integer idalm)
    {
        ArrayList<AlmacenProducto> registros = this.getRegistros(new String[]{"idalmacen"},new Object[]{idalm});
        return registros.size()>0?registros.get(0):null;
    }
    
    public ArrayList<AlmacenProducto> getRegistroPorProducto(Integer idalm)
    {
        ArrayList<AlmacenProducto> registros = this.getRegistros(new String[]{AlmacenProducto.COLUMNA_ACTIVO,"idproducto"},new Object[]{1,idalm});
        return registros;
    }
    
    public ArrayList<AlmacenProducto> getRegistros(String[] columnas,Object[] cvl) {
        return this.getRegistros(new String[]{}, columnas,cvl);
    }
    
    public int getCantidadProducto(Integer idp)
    {
        Object suma =this.getSuma(AlmacenProducto.nt, "cantidad_actual", "idproducto", idp);
        int cnt = Integer.parseInt(suma.toString());
        return cnt;
    }

    @Override
    public boolean guardarRegistro(JAbstractModelBD mdl) {
        AlmacenProducto ap = (AlmacenProducto)mdl;
        int gravado = 0;
        String campos = AlmacenProducto.NO_FULL_CAMPOS;        
        Object[] valores = {ap.getAlmacen().getPrimaryKey(),
            ap.getProducto().getPrimaryKey(),
            ap.getCantidadActual(),
            ap.getCantidadInicial(),
            ap.getTipoManipulacion(),
            Helper.getFechaFormateada(ap.getFecha(), Helper.ANIO_MES_DIA),
            ap.getActivo()
        };
       
         gravado = this.agregarRegistroPs(AlmacenProducto.nt, this.stringToArray(campos, ","), valores);
       
        if(gravado==1)
            return true;
        
        return false;       
    }

    @Override
    public int actualizarRegistro(JAbstractModelBD mdl) {
        AlmacenProducto ap = (AlmacenProducto)mdl;
        int gravado = 0;
        String campos = AlmacenProducto.NO_FULL_CAMPOS;        
        Object[] valores = {ap.getAlmacen().getPrimaryKey(),
            ap.getProducto().getPrimaryKey(),
            ap.getCantidadActual(),
            ap.getCantidadInicial(),
            ap.getTipoManipulacion(),
            Helper.getFechaFormateada(ap.getFecha(), Helper.ANIO_MES_DIA),
            ap.getActivo(),
            ap.getPrimaryKey()   
        };
        gravado = this.actualizarRegistroPs(AlmacenProducto.nt, this.adjuntarSimbolo(campos, ",", "?")+Ex.WHERE+AlmacenProducto.COLUMNA_PK+" = ? ", valores);
           
   
        return gravado;
    }

    @Override
    public ArrayList getRegistros(String[] campos, String[] columnaId, Object[] id) {
        ArrayList<AlmacenProducto> rgs = new ArrayList();        
        try {
            
            if(id != null)
            { 
               this.numRegistros = this.getNumeroRegistros(AlmacenProducto.nt, AlmacenProducto.COLUMNA_ACTIVO, AlmacenProducto.COLUMNA_ACTIVO, id);
            }else
            {
               this.numRegistros = this.getNumeroRegistros(AlmacenProducto.nt, AlmacenProducto.COLUMNA_ACTIVO);               
               if(rs.isClosed())
               {
                   System.out.println("resultset esta cerrado...");
               }
            }
            rs = this.getRegistros(AlmacenProducto.nt,campos,columnaId,id);
            if(this.numRegistros<finalPag)
           {
              finalPag =  this.numRegistros;
           }
            if(finalPag>inicioPag)
            {
                inicioPag -= (finalPag-inicioPag)-1;
            }
            AlmacenProducto sm = null;
            CAlmacen controllerAlmacen = new CAlmacen();
            CProducto controllerProducto = new CProducto();
            while(rs.next())
            {
                 sm = new AlmacenProducto();
                 sm.setPrimaryKey(rs.getInt(1));
                 sm.setAlmacen((Almacen)controllerAlmacen.buscarRegistro(rs.getInt(2)));
                 controllerProducto.setNumPaginador(0, 50);
                 sm.setProducto(controllerProducto.getRegistro(rs.getInt(3)));
                 sm.setCantidadActual(rs.getInt(4));
                 sm.setCantidadInicial(rs.getInt(5));
                 sm.setTipoManipulacion(rs.getString(6));
                 sm.setFecha(rs.getDate(7));
                 sm.setActivo(rs.getInt(8));
                 rgs.add(sm);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rgs;
    }
    
}
