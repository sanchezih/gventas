package controllers;

import core.Ex;
import core.JAbstractController;
import core.JAbstractModelBD;
import modelbd.Producto;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class CProducto extends JAbstractController{
    
    private Producto prd;
    private InputStream itmp;

    @Override
    public ArrayList getRegistros() {
        return this.getRegistros(new String[]{}, null,null);
    }

    @Override
    public ArrayList getRegistros(Object[] cvl) {
        return this.getRegistros(new String[]{},new String[]{Producto.COLUMNA_ACTIVO},cvl);
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

    public Producto getRegistro(int id)
    {
        ArrayList<Producto> registros = this.getRegistros(new String[]{Producto.COLUMNA_PK},new Object[]{id});
        return registros.get(0);
    }
    
     public Producto getRegistroPorCodigo(String id)
    {
        ArrayList<Producto> registros = this.getRegistros(new String[]{"codigo"},new Object[]{id});
        return registros.get(0);
    }
    
    public ArrayList<Producto> getRegistros(String[] columnas,Object[] cvl) {
        return this.getRegistros(new String[]{}, columnas,cvl);
    }
    @Override
    public boolean guardarRegistro(JAbstractModelBD mdl) {
        Producto usr = (Producto)mdl;
        int gravado = 0;
        String campos = Producto.CAMPOS_NO_ID;
        
        Object[] valores = {usr.getCodigoBarras(), usr.getCodigo(),
                   usr.getCodigoDelFabricante(),
                   usr.getNombre(),
                   usr.getCosto(),
                   usr.getPrecioAlMayor(),
                   usr.getPrecioAlMenor(),
                   usr.getUtilidad(),
                   usr.getAplicaIGV(),
                   usr.getStockMinimo(),
                   usr.getTipo(),
                   usr.getIdMoneda(),
                   usr.getImagen(null),
                   usr.getIdClase(),
                   usr.getIdMarca(),
                   usr.getIdModelo(),
                   usr.getUbicacion(),
                   usr.getUnidadPrincipal(),
                   usr.getPeso(),
                   usr.getActivo(),                   
                   };
              
           gravado = this.agregarRegistroPs(Producto.nt, this.stringToArray(campos, ","), valores);
       
        if(gravado==1)
        {   
            return true;
        }
            
        
        return false;
    }

    @Override
    public int actualizarRegistro(JAbstractModelBD mdl) {
        Producto usr = (Producto)mdl;
        int gravado = 0;
        String campos = Producto.CAMPOS_NOID_NOIMAGE;        
        Object[] valores = {
                   usr.getCodigoBarras(), usr.getCodigo(),
                   usr.getCodigoDelFabricante(),
                   usr.getNombre(),
                   usr.getCosto(),
                   usr.getPrecioAlMayor(),
                   usr.getPrecioAlMenor(),
                   usr.getUtilidad(),
                   usr.getAplicaIGV(),
                   usr.getStockMinimo(),
                   usr.getTipo(),
                   usr.getIdMoneda(),                   
                   usr.getIdClase(),
                   usr.getIdMarca(),
                   usr.getIdModelo(),
                   usr.getUbicacion(),
                   usr.getUnidadPrincipal(),
                   usr.getPeso(),
                   usr.getActivo(), 
                   usr.getPrimaryKey()
                   };
              
           if(usr.getImagen(null) != null)
            {
                campos = Producto.CAMPOS_NO_ID;
                 valores = new Object[]{usr.getCodigoBarras(), usr.getCodigo(),
                   usr.getCodigoDelFabricante(),
                   usr.getNombre(),
                   usr.getCosto(),
                   usr.getPrecioAlMayor(),
                   usr.getPrecioAlMenor(),
                   usr.getUtilidad(),
                   usr.getAplicaIGV(),
                   usr.getStockMinimo(),
                   usr.getTipo(),
                   usr.getIdMoneda(),
                   usr.getImagen(null),
                   usr.getIdClase(),
                   usr.getIdMarca(),
                   usr.getIdModelo(),
                   usr.getUbicacion(),
                   usr.getUnidadPrincipal(),
                   usr.getPeso(),
                   usr.getActivo(),
                   usr.getPrimaryKey()
                   };
            }        
           gravado = this.actualizarRegistroPs(Producto.nt, this.adjuntarSimbolo(campos, ",", "?")+Ex.WHERE+Producto.COLUMNA_PK+" = ? ", valores);
       
        return gravado;
    }
    @Override
    public ArrayList getRegistros(String[] campos, String[] columnaId, Object[] id) {
        ArrayList<Producto> rgs = new ArrayList<Producto>();        
        try {
            
            if(id != null)
            { 
               this.numRegistros = this.getNumeroRegistros(Producto.nt, Producto.COLUMNA_ACTIVO, Producto.COLUMNA_ACTIVO, id);
            }else
            {
               this.numRegistros = this.getNumeroRegistros(Producto.nt, Producto.COLUMNA_ACTIVO);               
               if(rs.isClosed())
               {
                   System.out.println("resultset esta cerrado...");
               }
            }
            rs = this.getRegistros(Producto.nt,campos,columnaId,id);
            if(this.numRegistros<finalPag)
           {
              finalPag =  this.numRegistros;
           }
            if(finalPag>inicioPag)
            {
                inicioPag -= (finalPag-inicioPag)-1;
            }
            Producto us = null;
            while(rs.next())
            {
                   us = new Producto();
                   us.setPrimaryKey(rs.getInt(1)); 
                   us.setIdProducto(rs.getInt(1));
                   us.setCodigoBarras(rs.getString(2));
                   us.setCodigo(rs.getString(3));
                   us.setCodigoDelFabricante(rs.getString(4));
                   us.setNombre(rs.getString(5));
                   us.setCosto(rs.getDouble(6));
                   us.setPrecioAlMayor(rs.getDouble(7));
                   us.setPrecioAlMenor(rs.getDouble(8));
                   us.setUtilidad(rs.getInt(9));
                   us.setAplicaIGV(rs.getInt(10));
                   us.setStockMinimo(rs.getInt(11));
                   us.setTipo(rs.getString(12));
                   us.setIdMoneda(rs.getInt(13));
                   //us.setImagen(rs.getBinaryStream(14));
                   us.setIdClase(rs.getInt(15));
                   us.setIdMarca(rs.getInt(16));
                   us.setIdModelo(rs.getInt(17));
                   us.setUbicacion(rs.getString(18));
                   us.setUnidadPrincipal(rs.getString(19));                   
                   us.setPeso(rs.getDouble(20));
                   us.setActivo(rs.getInt(21));                
                 rgs.add(us);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rgs;
    }
    
    public ImageIcon getFoto(Integer id)
    {
        InputStream archivo = this.getArchivo(Producto.nt, "imagen", Producto.COLUMNA_PK, id); 
        itmp = archivo;
        ImageIcon ii = null;
        if(archivo!= null)
        {
            try {
                 BufferedImage read = ImageIO.read(archivo);                 
                  ii = new ImageIcon(read);
            } catch (IOException ex) {
                Logger.getLogger(CUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch(NullPointerException ex){
                ii = null;
            }
        }
        return ii;        
    }
     public boolean existeCodigo(String codigo)
    {
        return this.existe(Producto.nt, "codigo", codigo);
    }
    
    public boolean existeCodigoBarras(String codigoDeBarras)
    {
        return this.existe(Producto.nt, "cod_barras", codigoDeBarras);
    }
    
     public boolean existeCodigo(String codigo,Integer pk)
    {
        return this.existe(Producto.nt, "codigo", codigo,Producto.COLUMNA_PK,pk);
    }
    
    public boolean existeCodigoBarras(String codigoDeBarras,Integer pk)
    {
        return this.existe(Producto.nt, "cod_barras", codigoDeBarras,Producto.COLUMNA_PK,pk);
    }
    
    public int getTotalRegistros()
    {
        return this.getNumeroRegistros(Producto.nt, Producto.COLUMNA_PK);
    }
    
}
