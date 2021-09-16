package controllers;

import core.Ex;
import core.JAbstractController;
import core.JAbstractModelBD;
import modelbd.EncargadoAlmacen;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class CEncargadoAlmacen extends JAbstractController implements Serializable{

    @Override
    public ArrayList<EncargadoAlmacen> getRegistros() {
        return this.getRegistros(new String[]{}, null, null);
    }

    @Override
    public ArrayList getRegistros(Object[] cvl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public ArrayList<EncargadoAlmacen> getRegistrosPorEncargado(Integer idu)
    {
        return this.getRegistros(new String[]{}, new String[]{"idusuario"}, new Object[]{idu});
    }
    /**
     * 
     * @param valores muestra registro de acuerdo a los campos idusuario e idalmacen.
     * primero idusuario, segundo idAlmacen
     * @return  arraylist de EncargadoAlmacen;
     */
    public ArrayList<EncargadoAlmacen> getRegistroPorEA(Object[] valores)
    {
        return this.getRegistro(new String[]{"idusuario","idalmacen"}, valores);
    }
    private ArrayList<EncargadoAlmacen> getRegistro(String[] campos,Object[] valores)
    {
        return this.getRegistros(new String[]{}, campos, valores);
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
        EncargadoAlmacen al = (EncargadoAlmacen)mdl;
        int gravado = 0;
        String campos = EncargadoAlmacen.CAMPOS_NO_ID;        
        Object[] valores = {al.getUsuario().getPrimaryKey(),
            al.getAlmacen().getPrimaryKey(),
            al.getTipoCargo(),al.getActivo()};
       
         gravado = this.agregarRegistroPs(EncargadoAlmacen.nt, this.stringToArray(campos, ","), valores);
       
        if(gravado==1)
            return true;
        
        return false;
    }

    @Override
    public int actualizarRegistro(JAbstractModelBD mdl) {
        EncargadoAlmacen al = (EncargadoAlmacen)mdl;
        int gravado = 0;
        String campos = EncargadoAlmacen.CAMPOS_NO_ID;         
        Object[] valores = {al.getUsuario().getPrimaryKey(),
            al.getAlmacen().getPrimaryKey(),
            al.getTipoCargo(),al.getActivo(),al.getPrimaryKey()};
        
        gravado = this.actualizarRegistroPs(EncargadoAlmacen.nt, this.adjuntarSimbolo(campos, ",", "?")+Ex.WHERE+mdl.getCampoClavePrimaria()+" = ? ", valores);
         System.out.println("grabadoo "+gravado); 
        return gravado;
    }
    
    public EncargadoAlmacen getRegistroPorPk(Integer id)
    {
        EncargadoAlmacen sm = null;
        try {
            rs =  this.selectPorPk(EncargadoAlmacen.nt, EncargadoAlmacen.COLUMNA_PK, id);
            CUsuario controllerUsuario = null;
            CAlmacen controllerAlmacen = null;
            while(rs.next())
            {                
                 sm = new EncargadoAlmacen();
                 sm.setUsuario(controllerUsuario.getRegistroPorPk(rs.getInt(1)));
                 sm.setAlmacen(controllerAlmacen.getRegistroPorPk(rs.getInt(2)));
                 sm.setTipoCargo(rs.getString(3));                
                 sm.setActivo(rs.getInt(4));
                 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sm;
    }

    @Override
    public ArrayList getRegistros(String[] campos, String[] columnaId, Object[] id) {
        ArrayList<EncargadoAlmacen> rgs = new ArrayList();        
        try {
            
            if(id != null)
            { 
               this.numRegistros = this.getNumeroRegistros(EncargadoAlmacen.nt, EncargadoAlmacen.COLUMNA_ACTIVO, EncargadoAlmacen.COLUMNA_ACTIVO, id);
            }else
            {
               this.numRegistros = this.getNumeroRegistros(EncargadoAlmacen.nt, EncargadoAlmacen.COLUMNA_ACTIVO);               
               if(rs.isClosed())
               {
                   System.out.println("resultset esta cerrado...");
               }
            }
            rs = this.getRegistros(EncargadoAlmacen.nt,campos,columnaId,id);
            if(this.numRegistros<finalPag)
           {
              finalPag =  this.numRegistros;
           }
            if(finalPag>inicioPag)
            {
                inicioPag -= (finalPag-inicioPag)-1;
            }
            EncargadoAlmacen sm = null;
            CUsuario controllerUsuario = null;
            CAlmacen controllerAlmacen = null;
            while(rs.next())
            {
                controllerUsuario = new CUsuario();
                controllerAlmacen = new CAlmacen();
                 sm = new EncargadoAlmacen();  
                 sm.setPrimaryKey(rs.getInt(1));                 
                 sm.setUsuario(controllerUsuario.getRegistroPorPk(rs.getInt(2)));
                 sm.setAlmacen(controllerAlmacen.getRegistroPorPk(rs.getInt(3)));
                 sm.setTipoCargo(rs.getString(4));                
                 sm.setActivo(rs.getInt(5));
                 rgs.add(sm);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rgs;
    }
    
}
