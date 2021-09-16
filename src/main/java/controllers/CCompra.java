/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import core.Ex;
import core.JAbstractController;
import core.JAbstractModelBD;
import managerbd.BaseConexion;
import modelbd.Compra;
import util.Helper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class CCompra extends JAbstractController{

    @Override
    public ArrayList<Compra> getRegistros() {
        return this.getRegistros(new String[]{}, null,null);
    }

    @Override
    public ArrayList<Compra> getRegistros(Object[] cvl) {
        return this.getRegistros(new String[]{},new String[]{Compra.COLUMNA_ACTIVO},cvl);
    }
    
    public ArrayList<Compra> getRegistros(String[] columnas,Object[] cvl) {
        return this.getRegistros(new String[]{}, columnas,cvl);
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
    
    public boolean existeDocumento(String numDoc,String tipoDoc)
    {
        sql = "select * from "+Compra.nt+" where numdoc='"+numDoc+"' and tipodoc ='"+tipoDoc+"'";
        boolean encontrado = false;
         try{            
            rs = BaseConexion.getResultSet(sql);
            if(rs.next())
            {
                encontrado = true;
            }
            rs.close();
        }catch(SQLException ex){ex.printStackTrace();}
        
        return encontrado;
    }

    @Override
    public boolean guardarRegistro(JAbstractModelBD mdl) {
        Compra usr = (Compra)mdl;
        int gravado = 0;
        String campos = Compra.CAMPOS_NO_ID;
        
        Object[] valores = {usr.getTipoDocumento(),
                            usr.getNumeroDocumento(),
                            usr.getIdProveedor().getPrimaryKey(),
                            usr.getIdUsuario().getPrimaryKey(),
                            usr.getIdMoneda().getPrimaryKey(),
                            usr.getEstado(),
                            usr.getNumeroItems(),
                            usr.getObservaciones(),
                            usr.getSubtotla(),
                            usr.getIgv(),
                            usr.getTotal(),
                            Helper.getFechaFormateada(usr.getFecha(), Helper.ANIO_MES_DIA),
                            usr.getActivo()};
       
           gravado = this.agregarRegistroPs(Compra.nt, this.stringToArray(campos, ","), valores);
       
        if(gravado==1)
        {    
            usr.setId(getUltimoPk());
            usr.setPrimaryKey(usr.getId());
            return true;
        }
        return false;
    }
    
      private Integer getUltimoPk()
    {
        ResultSet rsTmp = this.getUltimoRegistro(Compra.nt, "idcompra");
        Integer pk=null;
        try{
            while(rsTmp.next())
            {
               pk =  rsTmp.getInt(1);
            }
        }catch(SQLException ex){ex.printStackTrace();}
        return pk;
    }

    @Override
    public int actualizarRegistro(JAbstractModelBD mdl) {
        Compra usr = (Compra)mdl;
        int gravado = 0;
        String campos = Compra.CAMPOS_NO_ID;
        
        Object[] valores = {usr.getTipoDocumento(),
                            usr.getNumeroDocumento(),
                            usr.getIdProveedor().getPrimaryKey(),
                            usr.getIdUsuario().getPrimaryKey(),
                            usr.getIdMoneda().getPrimaryKey(),
                            usr.getEstado(),
                            usr.getNumeroItems(),
                            usr.getObservaciones(),
                            usr.getSubtotla(),
                            usr.getIgv(),
                            usr.getTotal(),
                            Helper.getFechaFormateada(usr.getFecha(), Helper.ANIO_MES_DIA),
                            usr.getActivo(),
                            usr.getPrimaryKey()};
       
           gravado = this.actualizarRegistroPs(Compra.nt, this.adjuntarSimbolo(campos, ",", "?")+Ex.WHERE+Compra.COLUMNA_PK+" = ? ", valores);
               
        return gravado;
    }

    public Compra getRegistroPorPk(Integer id)
    {
         Compra us = null;
        try {
            rs =  this.selectPorPk(Compra.nt, Compra.COLUMNA_PK, id);
            CProveedor cntp = new CProveedor();
            while(rs.next())
            {
                   us = new Compra();
                   us.setPrimaryKey(rs.getInt(1));                
                   us.setId(rs.getInt(1));
                   us.setTipoDocumento(rs.getString(2));
                   us.setNumeroDocumento(rs.getString(3));
                   us.setIdProveedor(cntp.getRegistroPorPk(rs.getInt(4)));
                   us.setIdUsuario(new CUsuario().getRegistroPorPk(rs.getInt(5)));
                   us.setIdMoneda(new CMoneda().getRegistroPorPk(rs.getInt(6)));
                   us.setEstado(rs.getString(7));
                   us.setNumeroItems(rs.getInt(8));
                   us.setObservaciones(rs.getString(9));
                   us.setSubtotla(rs.getDouble(10));
                   us.setIgv(rs.getInt(11));
                   us.setTotal(rs.getDouble(12));
                   us.setFecha(rs.getDate(13));
                   us.setActivo(rs.getInt(14));
                 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return us;
    }
    
    
    @Override
    public ArrayList getRegistros(String[] campos, String[] columnaId, Object[] id) {
         ArrayList<Compra> rgs = new ArrayList<Compra>();        
        try {
            
            if(id != null)
            { 
               this.numRegistros = this.getNumeroRegistros(Compra.nt, Compra.COLUMNA_ACTIVO, Compra.COLUMNA_ACTIVO, id);
            }else
            {
               this.numRegistros = this.getNumeroRegistros(Compra.nt, Compra.COLUMNA_ACTIVO);               
               if(rs.isClosed())
               {
                   System.out.println("resultset esta cerrado...");
               }
            }
            rs = this.getRegistros(Compra.nt,campos,columnaId,id);
            if(this.numRegistros<finalPag)
           {
              finalPag =  this.numRegistros;
           }
            if(finalPag>inicioPag)
            {
                inicioPag -= (finalPag-inicioPag)-1;
            }
            Compra us = null;
            CProveedor cntp = new CProveedor();
            while(rs.next())
            {
                   us = new Compra();
                   us.setPrimaryKey(rs.getInt(1));                
                   us.setId(rs.getInt(1));
                   us.setTipoDocumento(rs.getString(2));
                   us.setNumeroDocumento(rs.getString(3));
                   us.setIdProveedor(cntp.getRegistroPorPk(rs.getInt(4)));
                   us.setIdUsuario(new CUsuario().getRegistroPorPk(rs.getInt(5)));
                   us.setIdMoneda(new CMoneda().getRegistroPorPk(rs.getInt(6)));
                   us.setEstado(rs.getString(7));
                   us.setNumeroItems(rs.getInt(8));
                   us.setObservaciones(rs.getString(9));
                   us.setSubtotla(rs.getDouble(10));
                   us.setIgv(rs.getInt(11));
                   us.setTotal(rs.getDouble(12));
                   us.setFecha(rs.getDate(13));
                   us.setActivo(rs.getInt(14));
                 rgs.add(us);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rgs;
    }
    
}
