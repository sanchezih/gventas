package controllers;

import core.JAbstractController;
import core.JAbstractModelBD;
import modelbd.Bitacora;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ferz Código Lite - https://codigolite.com
 */
public class CBitacora extends JAbstractController implements Serializable{

    @Override
    public ArrayList getRegistros() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Bitacora> getRegistros(Object[] cvl) {
        return getRegistros(new String[]{},new String[]{"idusuario"}, cvl) ;
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

    @Override
    public boolean guardarRegistro(JAbstractModelBD mdl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int actualizarRegistro(JAbstractModelBD mdl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Bitacora> getRegistros(String[] campos, String[] columnaId, Object[] id) {
        
        
        ArrayList<Bitacora> rgs = new ArrayList<Bitacora>();        
        try {
            
            if(id != null)
            { 
               this.numRegistros = this.getNumeroRegistros(Bitacora.nt, Bitacora.COLUMNA_USER, Bitacora.COLUMNA_USER, id);
            }else
            {
               this.numRegistros = this.getNumeroRegistros(Bitacora.nt, Bitacora.COLUMNA_USER);               
               if(rs.isClosed())
               {
                   System.out.println("resultset esta cerrado...");
               }
            }
            rs = this.getRegistros(Bitacora.nt,campos,columnaId,id);
            if(this.numRegistros<finalPag)
           {
              finalPag =  this.numRegistros;
           }
            if(finalPag>inicioPag)
            {
                inicioPag -= (finalPag-inicioPag)-1;
            }
            Bitacora bt = new Bitacora();
            while(rs.next())
            {
                bt.setIdbitacora(rs.getLong(1));
                bt.setIdusuario(rs.getInt(2));
                bt.setOs(rs.getString(3));
                bt.setArquitectura(rs.getString(4));
                bt.setVersion(rs.getString(5));
                bt.setUsuario(rs.getString(6));
                bt.setUltimaActividad(rs.getLong(7));
                bt.setFechaActividad(rs.getLong(8));
                rgs.add(bt);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rgs;
    }
    
}
