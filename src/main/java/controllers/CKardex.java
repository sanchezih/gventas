package controllers;

import core.Ex;
import core.JAbstractController;
import core.JAbstractModelBD;
import modelbd.Kardex;
import util.Helper;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class CKardex extends JAbstractController{

    @Override
    public ArrayList getRegistros() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList getRegistros(Object[] cvl) {
        throw new UnsupportedOperationException("Not supported yet.");
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

     public JAbstractModelBD buscarRegistro(Object[] cvl) {
        ArrayList<Kardex> registros = this.getRegistros(new String[]{}, new String[]{"documento","idProductos"}, cvl);
        if(registros.size()>0)
        {
            return registros.get(0);
        }
        return null;
    }
    @Override
    public boolean guardarRegistro(JAbstractModelBD mdl) {
        Kardex usr = (Kardex )mdl;
        int gravado = 0;
        String campos = Kardex.NO_FULL_NOM_CAMPOS;
        
        Object[] valores = {usr.getProducto().getPrimaryKey(),
                   Helper.getFechaFormateada(usr.getFecha(), Helper.ANIO_MES_DIA),
                   usr.getDocumento(),
                   usr.getNumeroDeDocumento(),
                   usr.getEntrada(),
                   usr.getSalida(),
                   usr.getPrecio(),
                   usr.getValor(),
                   usr.getStockActual(),
                   usr.getValorTotal(),
                   usr.getActivo(),
                   };
       
           gravado = this.agregarRegistroPs(Kardex.nt, this.stringToArray(campos, ","), valores);
       
        if(gravado==1)
        {       
            return true;
        }
        return false;
    }

    @Override
    public int actualizarRegistro(JAbstractModelBD mdl) {
        Kardex usr = (Kardex )mdl;
        int gravado = 0;
        String campos = Kardex.NO_FULL_NOM_CAMPOS;
        
        Object[] valores = {usr.getProducto().getPrimaryKey(),
                   Helper.getFechaFormateada(usr.getFecha(), Helper.ANIO_MES_DIA),
                   usr.getDocumento(),
                   usr.getNumeroDeDocumento(),
                   usr.getEntrada(),
                   usr.getSalida(),
                   usr.getPrecio(),
                   usr.getValor(),
                   usr.getStockActual(),
                   usr.getValorTotal(),
                   usr.getActivo(),
                   usr.getPrimaryKey()
                   };
       
           gravado = this.actualizarRegistroPs(Kardex.nt, this.adjuntarSimbolo(campos, ",", "?")+Ex.WHERE+Kardex.COLUMNA_PK+" = ? ", valores);
       
        return gravado;
    }

    @Override
    public ArrayList getRegistros(String[] campos, String[] columnaId, Object[] id) {
        ArrayList<Kardex> rgs = new ArrayList<Kardex>();        
        try {
            
            if(id != null)
            { 
               this.numRegistros = this.getNumeroRegistros(Kardex.nt, Kardex.COLUMNA_ACTIVO, Kardex.COLUMNA_ACTIVO, id);
            }else
            {
               this.numRegistros = this.getNumeroRegistros(Kardex.nt, Kardex.COLUMNA_ACTIVO);               
               if(rs.isClosed())
               {
                   System.out.println("resultset esta cerrado...");
               }
            }
            rs = this.getRegistros(Kardex.nt,campos,columnaId,id);
            if(this.numRegistros<finalPag)
           {
              finalPag =  this.numRegistros;
           }
            if(finalPag>inicioPag)
            {
                inicioPag -= (finalPag-inicioPag)-1;
            }
            Kardex us = null;
            CProducto cpr = new CProducto();
            while(rs.next())
            {
                   us = new Kardex();
                   us.setPrimaryKey(rs.getInt(1));   
                   us.setProducto(cpr.getRegistro(rs.getInt(2)));
                   us.setFecha(rs.getDate(3));
                   us.setDocumento(rs.getString(4));
                   us.setNumeroDeDocumento(rs.getString(5));
                   us.setEntrada(rs.getInt(6));
                   us.setSalida(rs.getInt(7));
                   us.setPrecio(rs.getDouble(8));
                   us.setValor(rs.getDouble(9));
                   us.setStockActual(rs.getInt(10));
                   us.setValorTotal(rs.getDouble(11));
                   us.setActivo(rs.getInt(12));
                 rgs.add(us);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rgs;
    }
    
}
