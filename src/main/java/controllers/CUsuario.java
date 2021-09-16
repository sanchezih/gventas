package controllers;

import core.CriterioSQL;
import core.Ex;
import core.JAbstractController;
import core.JAbstractModelBD;
import managerbd.BaseConexion;
import modelbd.Bitacora;
import modelbd.Usuario;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class CUsuario extends JAbstractController{
    private Usuario us;
    private InputStream itmp;

    public CUsuario(){        
        //BaseConexion.init();
    }
    
    

    @Override
    public ArrayList<Usuario> getRegistros() {
        return this.getRegistros(new String[]{}, null,null);
    }

    @Override
    public ArrayList<Usuario> getRegistros(Object[] cvl) {        
        return this.getRegistros(new String[]{}, cvl!=null?new String[]{Usuario.COLUMNA_ACTIVO}:null,cvl);
    }
    
    public Usuario getLoginUser(String login) {
        ArrayList<Usuario> reg = this.getRegistros(new String[]{},new String[]{"login"},new Object[]{login});
        if(!reg.isEmpty())
        {
            return reg.get(0);
        }
        return null;
    }
    public ArrayList<Usuario> getRegistros(String[] columnas,Object[] cvl) {
        return this.getRegistros(new String[]{}, columnas,cvl);
    }
    
    public ArrayList<Usuario> getRegistrosPorTipoCargo(String cargo) {
        return this.getRegistros(new String[]{}, new String[]{"tipo_cargo"},new Object[]{cargo});
    }
    
    public ArrayList<Usuario> getRegistrosDni(String dni) {
        return this.getRegistros(new String[]{}, new String[]{"dni"},new Object[]{dni});
    }
    
    public ArrayList<Usuario> getRegistrosCodigo(String codigo) {
        return this.getRegistros(new String[]{}, new String[]{"codigo"},new Object[]{codigo});
    }
    
    public ArrayList<Usuario> getRegistrosLogin(String login) {
        return this.getRegistros(new String[]{}, new String[]{"login"},new Object[]{login});
    }
    
    public ArrayList<Usuario> getRegistrosFecha(long valor) {
        return this.getRegistrosMn(new String[]{}, new String[]{"fec_registro"},new Object[]{valor});
    }
    
    public ArrayList<Usuario> getRegistrosFecha(long valor1,long valor) {
        return this.getRegistrosMn(new String[]{}, new String[]{"fec_registro","fec_registro"},new Object[]{valor1,valor});
    }

    @Override
    public Usuario getRegistro() {
        ArrayList<Usuario> registros = this.getRegistros();
        return registros.get(0);
    }
    
    public Usuario getRegitro(String id)
    {
        ArrayList<Usuario> registros = this.getRegistros(new String[]{"dni"},new Object[]{id});
        return registros.get(0);
    }

    
    /**
     * 
     * @param mdl modelo
     * @param opcion si true llenara el modelos con todos los datos, de lo contrario solo obtendra la clave primaria.
     * @return 
     */
    @Override
    public JAbstractModelBD getRegistro(JAbstractModelBD mdl,boolean opcion) {
        us = (Usuario)mdl;
        CriterioSQL criterio = new CriterioSQL(Usuario.nt);
        criterio.setSelect("idu,dni,codigo,login,nombre,apellidos,clave,salt,fono,sexo,tipo_cargo,fec_registro,fec_baja");
        criterio.addCondicion("dni", us.getDni());
        //sql = "select idu,dni,codigo,login,nombre,apellidos,clave,salt,fono,sexo,tipo_cargo,fec_registro,fec_baja from "+us.getNombreTabla()+" where dni = '"+us.getDni()+"'";
        try {
            rs =BaseConexion.getConexion().createStatement().executeQuery(criterio.getConsultaSQL());
            if(rs.next())
            {
                us.setPrimaryKey(rs.getInt(1));
                us.setIdu(rs.getInt(1));
                if(opcion) 
                {
                   us.setDni(rs.getString(2));
                   us.setCodigo(rs.getString(3));
                   us.setLogin(rs.getString(4));
                   us.setNombre(rs.getString(5));
                   us.setApellidos(rs.getString(6));
                   us.setClave(rs.getString(7));
                   us.setSalt(rs.getString(8));
                   us.setFono(rs.getString(9));
                   us.setSexo(rs.getString(10));
                   us.setTipoCargo(rs.getString(11));
                   us.setFecRegistro(rs.getLong(12));
                   us.setFecBaja(rs.getLong(13));
                }
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return us;
    }

    @Override
    public JAbstractModelBD buscarRegistro(Object cvl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean guardarRegistro(JAbstractModelBD mdl) {
      
        Usuario usr = (Usuario)mdl;
        int gravado = 0;
        String campos = Usuario.NO_FULL_NOM_CAMPOS;
        
        Object[] valores = {usr.getDni(), usr.getCodigo(),
                   usr.getLogin(),
                   usr.getNombre(),
                   usr.getApellidos(),
                   usr.getFoto(null),
                   usr.getClave(),
                   usr.getSalt(),
                   usr.getFechaNacimiento(),
                   usr.getFono(),
                   usr.getFono2(),
                   usr.getCelular(),
                   usr.getNextel(),
                   usr.getEmail(),
                   usr.getSexo(null),
                   usr.getTipoCargo(),
                   usr.getDescipcion(),
                   usr.getActivo(),                   
                   usr.getFecRegistro(),
                   usr.getFecBaja()};
       
           gravado = this.agregarRegistroPs(Usuario.nt, this.stringToArray(campos, ","), valores);
       
        if(gravado==1)
        {
            //getRegitro(usr.getDni());
            try{
                this.grabarBitacora(this.getUltimoUsuario());
            }catch(SQLException ex){ex.printStackTrace();}            
            return true;
        }
            
        
        return false;
    }

    @Override
    public int actualizarRegistro(JAbstractModelBD mdl) {
       Usuario usr = (Usuario)mdl;
        int gravado = 0;
        String campos = Usuario.SIN_FOTO_CLAVE;
        
        Object[] valores = {usr.getDni(), usr.getCodigo(),
                   usr.getLogin(),
                   usr.getNombre(),
                   usr.getApellidos(),
                   usr.getFechaNacimiento(),
                   usr.getFono(),
                   usr.getFono2(),
                   usr.getCelular(),
                   usr.getNextel(),
                   usr.getEmail(),
                   usr.getSexo(null),
                   usr.getTipoCargo(),
                   usr.getDescipcion(),
                   usr.getActivo(),                   
                   usr.getFecRegistro(),
                   usr.getFecBaja(),usr.getPrimaryKey()};
        gravado = this.actualizarRegistroPs(Usuario.nt, this.adjuntarSimbolo(campos, ",", "?")+Ex.WHERE+Usuario.COLUMNA_PK+" = ? ", valores);
        return gravado;
    }
    
    public int actualizarConFoto(JAbstractModelBD mdl) {
       Usuario usr = (Usuario)mdl;
        int gravado = 0;
        String campos = Usuario.CON_FOTO;
        
        Object[] valores = {usr.getDni(), usr.getCodigo(),
                   usr.getLogin(),
                   usr.getNombre(),
                   usr.getApellidos(),
                   usr.getFoto(null),
                   usr.getFechaNacimiento(),
                   usr.getFono(),
                   usr.getFono2(),
                   usr.getCelular(),
                   usr.getNextel(),
                   usr.getEmail(),
                   usr.getSexo(null),
                   usr.getTipoCargo(),
                   usr.getDescipcion(),
                   usr.getActivo(),                   
                   usr.getFecRegistro(),
                   usr.getFecBaja(),usr.getPrimaryKey()};
        gravado = this.actualizarRegistroPs(Usuario.nt, this.adjuntarSimbolo(campos, ",", "?")+Ex.WHERE+Usuario.COLUMNA_PK+" = ? ", valores);
           
   
        return gravado;
    }
    
    public int actualizarPass(JAbstractModelBD mdl) {
       Usuario usr = (Usuario)mdl;
        int gravado = 0;
        String campos = "clave,salt";
        
        Object[] valores = {usr.getClave(),usr.getSalt(),usr.getPrimaryKey()};
        gravado = this.actualizarRegistroPs(Usuario.nt, this.adjuntarSimbolo(campos, ",", "?")+Ex.WHERE+Usuario.COLUMNA_PK+" = ? ", valores);
        
        return gravado;
    }
    
    public int grabarEnBitacora(Usuario us)
    {
        try{
            return this.grabarBitacora(us);
        }catch(SQLException ex){ex.printStackTrace();}
        return -1;
    }
    private int grabarBitacora(Usuario us) throws SQLException 
    {
        
            Calendar cr = Calendar.getInstance();
            Bitacora bt = getBitacora(us);
            sql = "INSERT INTO gv_bitacora ("+Bitacora.COLUMNAS_NO_ID+")"+
            " VALUES (?, ?, ?, ?, ?, ?, ?)";
            this.ps = BaseConexion.getConexion().prepareStatement(sql);
            ps.setInt(1,us.getPrimaryKey()); 
            ps.setString(2, System.getProperty("os.name"));
            ps.setString(3, System.getProperty("os.arch"));
            ps.setString(4, System.getProperty("os.version"));
            ps.setString(5, System.getProperty("user.name"));
            if(bt!=null)
            {
                ps.setLong(6, bt.getFechaActividad());
            }else{
                ps.setLong(6, cr.getTime().getTime());
            }
            ps.setLong(7, cr.getTime().getTime());
            return ps.executeUpdate();            
        
    }
    
    private Bitacora getBitacora(Usuario us) 
    {
        try {
            Bitacora bt = new Bitacora();
            sql = "select * from gv_bitacora where idusuario = "+us.getPrimaryKey()+" order by fecha_actividad desc limit 1";
            rs =BaseConexion.getConexion().createStatement().executeQuery(sql);
            if(rs.next())
            {
                bt.setIdbitacora(rs.getLong(1));
                bt.setIdusuario(rs.getInt(2));
                bt.setOs(rs.getString(3));
                bt.setArquitectura(rs.getString(4));
                bt.setVersion(rs.getString(5));
                bt.setUsuario(rs.getString(6));
                bt.setUltimaActividad(rs.getLong(7));
                bt.setFechaActividad(rs.getLong(8));
                return bt;
            }
                
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return null;
    }
    
       
    public Usuario getRegistroPorPk(Integer id)
    {
        try {
            rs =  this.selectPorPk(Usuario.nt, Usuario.COLUMNA_PK, id);
            while(rs.next())
            {
                 us = new Usuario();
                   us.setPrimaryKey(rs.getInt(1));
                   us.setIdu(rs.getInt(1));
                   us.setDni(rs.getString(2));
                   us.setCodigo(rs.getString(3));
                   us.setLogin(rs.getString(4));
                   us.setNombre(rs.getString(5));
                   us.setApellidos(rs.getString(6));
                   us.setClave(rs.getString(8));
                   us.setSalt(rs.getString(9));
                   us.setFechaNacimiento(rs.getInt(10));
                   us.setFono(rs.getString(11));
                   us.setFono2(rs.getString(12));
                   us.setCelular(rs.getString(13));
                   us.setNextel(rs.getString(14));
                   us.setEmail(rs.getString(15));
                   us.setSexo(rs.getString(16));
                   us.setTipoCargo(rs.getString(17));
                   us.setDescipcion(rs.getString(18));
                   us.setActivo(rs.getInt(19));                   
                   us.setFecRegistro(rs.getLong(20));
                   us.setFecBaja(rs.getLong(21));   
                 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return us;
    }

    @Override
    public ArrayList<Usuario> getRegistros(String[] campos, String[] columnaId, Object[] id) {
        ArrayList<Usuario> rgs = new ArrayList<Usuario>();        
        try {
            
            if(id != null)
            { 
               this.numRegistros = this.getNumeroRegistros(Usuario.nt, Usuario.COLUMNA_ACTIVO, Usuario.COLUMNA_ACTIVO, id);
            }else
            {
               this.numRegistros = this.getNumeroRegistros(Usuario.nt, Usuario.COLUMNA_ACTIVO);               
               if(rs.isClosed())
               {
                   System.out.println("resultset esta cerrado...");
               }
            }
            rs = this.getRegistros(Usuario.nt,campos,columnaId,id);
            if(this.numRegistros<finalPag)
           {
              finalPag =  this.numRegistros;
           }
            if(finalPag>inicioPag)
            {
                inicioPag -= (finalPag-inicioPag)-1;
            }
            Usuario us = null;
            while(rs.next())
            {
                   us = new Usuario();
                   us.setPrimaryKey(rs.getInt(1));   
                   us.setIdu(rs.getInt(1));
                   us.setDni(rs.getString(2));
                   us.setCodigo(rs.getString(3));
                   us.setLogin(rs.getString(4));
                   us.setNombre(rs.getString(5));
                   us.setApellidos(rs.getString(6));
                   us.setClave(rs.getString(8));
                   us.setSalt(rs.getString(9));
                   us.setFechaNacimiento(rs.getInt(10));
                   us.setFono(rs.getString(11));
                   us.setFono2(rs.getString(12));
                   us.setCelular(rs.getString(13));
                   us.setNextel(rs.getString(14));
                   us.setEmail(rs.getString(15));
                   us.setSexo(rs.getString(16));
                   us.setTipoCargo(rs.getString(17));
                   us.setDescipcion(rs.getString(18));
                   us.setActivo(rs.getInt(19));                   
                   us.setFecRegistro(rs.getLong(20));
                   us.setFecBaja(rs.getLong(21));                
                 rgs.add(us);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rgs;
    }
    public Usuario getUltimoUsuario()
    {
       Usuario us2 = null;
       rs = this.getUltimoRegistro(Usuario.nt, Usuario.COLUMNA_PK);
       try{
           
            while(rs.next())
            {
                   us2 = new Usuario();
                   us2.setPrimaryKey(rs.getInt(1));   
                   us2.setIdu(rs.getInt(1));
                   us2.setDni(rs.getString(2));
                   us2.setCodigo(rs.getString(3));
                   us2.setLogin(rs.getString(4));
                   us2.setNombre(rs.getString(5));
                   us2.setApellidos(rs.getString(6));
                   us2.setClave(rs.getString(8));
                   us2.setSalt(rs.getString(9));
                   us2.setFechaNacimiento(rs.getInt(10));
                   us2.setFono(rs.getString(11));
                   us2.setFono2(rs.getString(12));
                   us2.setCelular(rs.getString(13));
                   us2.setNextel(rs.getString(14));
                   us2.setEmail(rs.getString(15));
                   us2.setSexo(rs.getString(16));
                   us2.setTipoCargo(rs.getString(17));
                   us2.setDescipcion(rs.getString(18));
                   us2.setActivo(rs.getInt(19));                   
                   us2.setFecRegistro(rs.getLong(20));
                   us2.setFecBaja(rs.getLong(21));               
                 
            }
       }catch(SQLException ex){
           ex.printStackTrace();
       }
       return us2;
    }
    
     public ArrayList<Usuario> getRegistrosMn(String[] campos, String[] columnaId, Object[] id) {
        ArrayList<Usuario> rgs = new ArrayList<Usuario>();        
        try {
            
            if(id != null)
            { 
               this.numRegistros = this.getNumeroRegistros(Usuario.nt, Usuario.COLUMNA_ACTIVO, Usuario.COLUMNA_ACTIVO, id);
            }else
            {
               this.numRegistros = this.getNumeroRegistros(Usuario.nt, Usuario.COLUMNA_ACTIVO);               
               if(rs.isClosed())
               {
                   System.out.println("resultset esta cerrado...");
               }
            }
            rs = this.getRegistrosMn(Usuario.nt,campos,columnaId,id);
            if(this.numRegistros<finalPag)
           {
              finalPag =  this.numRegistros;
           }
            if(finalPag>inicioPag)
            {
                inicioPag -= (finalPag-inicioPag)-1;
            }
            Usuario us = null;
            while(rs.next())
            {
                   us = new Usuario();
                   us.setPrimaryKey(rs.getInt(1));
                   us.setIdu(rs.getInt(1));
                   us.setDni(rs.getString(2));
                   us.setCodigo(rs.getString(3));
                   us.setLogin(rs.getString(4));
                   us.setNombre(rs.getString(5));
                   us.setApellidos(rs.getString(6));
                   us.setClave(rs.getString(8));
                   us.setSalt(rs.getString(9));
                   us.setFechaNacimiento(rs.getInt(10));
                   us.setFono(rs.getString(11));
                   us.setFono2(rs.getString(12));
                   us.setCelular(rs.getString(13));
                   us.setNextel(rs.getString(14));
                   us.setEmail(rs.getString(15));
                   us.setSexo(rs.getString(16));
                   us.setTipoCargo(rs.getString(17));
                   us.setDescipcion(rs.getString(18));
                   us.setActivo(rs.getInt(19));                   
                   us.setFecRegistro(rs.getLong(20));
                   us.setFecBaja(rs.getLong(21));                
                 rgs.add(us);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rgs;
    }
    
    public boolean existeCodigo(String codigo)
    {
        return this.existe(Usuario.nt, "codigo", codigo);
    }
    
    public boolean existeDni(String dni)
    {
        return this.existe(Usuario.nt, "dni", dni);
    }
    
    public boolean existeLogin(String login)
    {
        return this.existe(Usuario.nt, "login", login);
    }
    
    public String getCodigoUsuario()
    {
        return this.getCodigo(Usuario.nt, "codigo");
    }
    
    public ImageIcon getFoto(Integer id)
    {
        InputStream archivo = this.getArchivo(Usuario.nt, "foto", Usuario.COLUMNA_PK, id); 
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
        }
        return ii;        
    }
    public InputStream getDatoFoto()
    {
        
        return this.itmp;
    }
    
    public int getNumeroRegistros()
    {
        return this.getNumeroRegistros(Usuario.nt, Usuario.COLUMNA_PK);
    }
    
    public int activarUsuario(Usuario us)
    {
        return this.eliminacionTemporal(Usuario.nt, Usuario.COLUMNA_ACTIVO, Usuario.COLUMNA_PK, us.getPrimaryKey(), 1);
    }
    
}
