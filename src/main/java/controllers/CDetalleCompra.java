/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import core.Ex;
import core.JAbstractController;
import core.JAbstractModelBD;
import modelbd.Compra;
import modelbd.DetalleCompra;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class CDetalleCompra extends JAbstractController{

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

     public ArrayList<DetalleCompra> getRegistros(String[] columnas,Object[] cvl) {
        return this.getRegistros(new String[]{}, columnas,cvl);
    }
     
     public ArrayList<DetalleCompra> getRegistrosPorCompra(Integer pkCompra) {
        return this.getRegistros(new String[]{}, new String[]{"idcompra"},new Object[]{pkCompra});
    }
    
    @Override
    public boolean guardarRegistro(JAbstractModelBD mdl) {
        DetalleCompra usr = (DetalleCompra)mdl;
        int gravado = 0;
        String campos = DetalleCompra.CAMPOS_NO_ID;
        
        Object[] valores = {usr.getIdcompra().getPrimaryKey(),
                            usr.getIdProducto().getPrimaryKey(),
                            usr.getCantidad(),
                            usr.getImporte(),
                            usr.getDescuento(),
                            usr.getActivo()};
       
           gravado = this.agregarRegistroPs(DetalleCompra.nt, this.stringToArray(campos, ","), valores);
       
        if(gravado==1)
        {          
            usr.getIdProducto();
        }
        return false;
    }
    
    private Integer getUltimoPk()
    {
        ResultSet rsTmp = this.getUltimoRegistro(DetalleCompra.nt, "iddetallec");
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
        DetalleCompra usr = (DetalleCompra)mdl;
        int gravado = 0;
        String campos = DetalleCompra.CAMPOS_NO_ID;
        
        Object[] valores = {usr.getIdcompra().getPrimaryKey(),
                            usr.getIdProducto().getPrimaryKey(),
                            usr.getCantidad(),
                            usr.getImporte(),
                            usr.getDescuento(),
                            usr.getActivo(),
                            usr.getPrimaryKey()};
       
           gravado = this.actualizarRegistroPs(DetalleCompra.nt, this.adjuntarSimbolo(campos, ",", "?")+Ex.WHERE+DetalleCompra.COLUMNA_PK+" = ? ", valores);
               
        return gravado;
    }

    @Override
    public ArrayList getRegistros(String[] campos, String[] columnaId, Object[] id) {
       ArrayList<DetalleCompra> rgs = new ArrayList<DetalleCompra>();        
        try {
            
            if(id != null)
            { 
               this.numRegistros = this.getNumeroRegistros(DetalleCompra.nt, DetalleCompra.COLUMNA_ACTIVO, DetalleCompra.COLUMNA_ACTIVO, id);
            }else
            {
               this.numRegistros = this.getNumeroRegistros(DetalleCompra.nt, DetalleCompra.COLUMNA_ACTIVO);               
               if(rs.isClosed())
               {
                   System.out.println("resultset esta cerrado...");
               }
            }
            rs = this.getRegistros(DetalleCompra.nt,campos,columnaId,id);
            if(this.numRegistros<finalPag)
           {
              finalPag =  this.numRegistros;
           }
            if(finalPag>inicioPag)
            {
                inicioPag -= (finalPag-inicioPag)-1;
            }
            DetalleCompra us = null;
            CCompra controllerCompra = null;
            while(rs.next())
            {
                   controllerCompra = new CCompra();
                   us = new DetalleCompra();
                   us.setPrimaryKey(rs.getInt(1));                
                   us.setId(rs.getInt(1));
                   us.setIdcompra(controllerCompra.getRegistroPorPk(rs.getInt(2)));
                   us.setIdProducto(new CProducto().getRegistro(rs.getInt(3)));
                   us.setCantidad(rs.getInt(4));
                   us.setImporte(rs.getDouble(5));
                   us.setDescuento(rs.getDouble(6));                  
                 rgs.add(us);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rgs;
    }
    
    public boolean existeProductoEnCompra(Integer idCompra,Integer idProducto)
    {
        return this.existeDosPk(DetalleCompra.nt, "idcompra", idCompra, "idproducto", idProducto);
    }
    
}
