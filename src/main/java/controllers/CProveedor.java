package controllers;

import core.Ex;
import core.JAbstractController;
import core.JAbstractModelBD;
import modelbd.Proveedor;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * @author Código Lite - https://codigolite.com
 */
public class CProveedor extends JAbstractController{
    private Proveedor prv;   
    @Override
    public ArrayList<Proveedor> getRegistros(Object[] op) {        
        return this.getRegistros(new String[]{}, op!=null?new String[]{Proveedor.campoActivo}:null,op);
    }
    
    public ArrayList<Proveedor> getRegistros(String[] columna,Object[] valor) {
        return getRegistros(new String[]{},columna,valor);
    }
    
    
    public Proveedor getRegistroPorPk(Integer id)
    {
        try {            
            rs = this.selectPorPk(Proveedor.nt, "idproveedor", id);
            while(rs.next())
            {
                 prv = new Proveedor();
                 prv.setPrimaryKey(rs.getInt(1));
                 prv.setIdproveedor(rs.getInt(1));
                 prv.setRuc(rs.getString(2));
                 prv.setRazonSocial(rs.getString(3));
                 prv.setDireccion(rs.getString(4));
                 prv.setCiudad(rs.getString(5));
                 prv.setTelefono(rs.getString(6));
                 prv.setNextel(rs.getString(7));
                 prv.setMovil(rs.getString(8));
                 prv.setFax(rs.getString(9));
                 prv.setCtaBancaria(rs.getString(10));
                 prv.setNomContacto(rs.getString(11));
                 prv.setEmail(rs.getString(12));
                 prv.setRubro(rs.getString(13));
                 prv.setProductos(rs.getString(14));
                 prv.setActivo(rs.getInt(15));
                 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return prv;
    }
    
    @Override
    public ArrayList<Proveedor> getRegistros(String[] campos, String[] columnaId, Object[] id) {
        
        
        ArrayList<Proveedor> rgs = new ArrayList<Proveedor>();        
        try {
            
            if(id != null)
            { 
               this.numRegistros = this.getNumeroRegistros(Proveedor.nt, Proveedor.campoActivo, Proveedor.campoActivo, id);
            }else
            {
               this.numRegistros = this.getNumeroRegistros(Proveedor.nt, Proveedor.campoActivo);               
               if(rs.isClosed())
               {
                   System.out.println("resultset esta cerrado...");
               }
            }
            rs = this.getRegistros(Proveedor.nt,campos,columnaId,id);
            if(this.numRegistros<finalPag)
           {
              finalPag =  this.numRegistros;
           }
            if(finalPag>inicioPag)
            {
                inicioPag -= (finalPag-inicioPag)-1;
            }
            while(rs.next())
            {
                 prv = new Proveedor();
                 prv.setPrimaryKey(rs.getInt(1));
                 prv.setIdproveedor(rs.getInt(1));
                 prv.setRuc(rs.getString(2));
                 prv.setRazonSocial(rs.getString(3));
                 prv.setDireccion(rs.getString(4));
                 prv.setCiudad(rs.getString(5));
                 prv.setTelefono(rs.getString(6));
                 prv.setNextel(rs.getString(7));
                 prv.setMovil(rs.getString(8));
                 prv.setFax(rs.getString(9));
                 prv.setCtaBancaria(rs.getString(10));
                 prv.setNomContacto(rs.getString(11));
                 prv.setEmail(rs.getString(12));
                 prv.setRubro(rs.getString(13));
                 prv.setProductos(rs.getString(14));
                 prv.setActivo(rs.getInt(15));
                 rgs.add(prv);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rgs;
        
    }

    @Override
    public ArrayList<JAbstractModelBD> getRegistros() {
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

    @Override
    public boolean guardarRegistro(JAbstractModelBD mdl) {
        prv = (Proveedor)mdl;
        int gravado = 0;
        String campos = "ruc, razon_social, direccion, ciudad, telefono, nextel, movil, fax, cta_bancaria, nom_contacto, email, rubro, productos";
        
        Object[] valores = {prv.getRuc(),prv.getRazonSocial(),prv.getDireccion(),
                            prv.getCiudad(),prv.getTelefono(),
                            prv.getNextel(),prv.getMovil(),
                            prv.getFax(),prv.getCtaBancaria(),
                            prv.getNomContacto(),prv.getEmail(),
                            prv.getRubro(),prv.getProductos()};
       
           gravado = this.agregarRegistroPs(prv.getNombreTabla(), this.stringToArray(campos, ","), valores);
       
        if(gravado==1)
            return true;
        
        return false;
    }

    @Override
    public int actualizarRegistro(JAbstractModelBD mdl) {
         prv = (Proveedor)mdl;
        int gravado = 0;
        String campos = "ruc, razon_social, direccion, ciudad, telefono, nextel, movil, fax, cta_bancaria, nom_contacto, email, rubro, productos, activo";
        
        Object[] valores = {prv.getRuc(),prv.getRazonSocial(),prv.getDireccion(),
                            prv.getCiudad(),prv.getTelefono(),
                            prv.getNextel(),prv.getMovil(),
                            prv.getFax(),prv.getCtaBancaria(),
                            prv.getNomContacto(),prv.getEmail(),
                            prv.getRubro(),prv.getProductos(),prv.getActivo(),prv.getPrimaryKey()};
       
        gravado = this.actualizarRegistroPs(prv.getNombreTabla(), this.adjuntarSimbolo(campos, ",", "?")+Ex.WHERE+mdl.getCampoClavePrimaria()+" = ? ", valores);
           
   
        return gravado;
    }

    public Proveedor getProveedor() {
        if(prv == null)
        {
            prv = new Proveedor();
        }
        return prv;
    }

    public void setProveedor(JAbstractModelBD prv) {
        this.prv = (Proveedor)prv;
    }
    
}
