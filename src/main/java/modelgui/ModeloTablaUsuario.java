package modelgui;

import controllers.CUsuario;
import core.CriterioSQL;
import core.JAbstractModelBD;
import modelbd.Usuario;
import util.Helper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ferz Código Lite - https://codigolite.com
 */
public class ModeloTablaUsuario extends ModeloTabla{
    private CUsuario  cUser;
    private String[] nColumnas = {"Dni","Codigo","Login","Nombre","Apellidos","Telefono","Cargo","Fecha Registro"};
    public static int tipoconsulta = -1;
    public static final int POR_TIPO_CARGO = 1;
    public static final int POR_DNI = 2;
    public static final int POR_CODIGO = 3;
    public static final int POR_LOGIN = 4;
    public ModeloTablaUsuario() {
        cUser = new CUsuario();
        this.nombreColumnas = nColumnas;
        registros = cUser.getRegistros();
        
    }
    
    
   public ModeloTablaUsuario(int opcion,int inicio,int finalPag) {
    cUser = new CUsuario();
    this.nombreColumnas = nColumnas;
    cUser.setNumPaginador(inicio, finalPag);
    switch(opcion)
    {
        case 0: 
        case 1:
            CriterioSQL criterio = new CriterioSQL();
            criterio.addCondicion(Usuario.COLUMNA_ACTIVO, opcion);
            List<JAbstractModelBD> encuentraTodos = new Usuario().encuentraTodos(criterio);
            registros = (ArrayList<JAbstractModelBD>)encuentraTodos;
            //registros = cUser.getRegistros(new Object[]{new Integer(opcion)});
            break;
        default:
            registros = cUser.getRegistros(null);
    }
        
    }

    public ModeloTablaUsuario(int opcion,String valor) {
        cUser = new CUsuario();
        this.nombreColumnas = nColumnas;
        switch(opcion)
        {
            case POR_TIPO_CARGO:
                registros = cUser.getRegistrosPorTipoCargo(valor);
                break;
            case  POR_DNI:
                registros = cUser.getRegistrosDni(valor);
                break;
            case  POR_CODIGO:
                registros = cUser.getRegistrosCodigo(valor);
                break;
            case  POR_LOGIN:
                registros = cUser.getRegistrosLogin(valor);
                break;
        }        
    }
    
    
    public ModeloTablaUsuario(int inicioPag,int finalPag,String valor) {
        cUser = new CUsuario();
        this.nombreColumnas = nColumnas;
       cUser.setNumPaginador(inicioPag, finalPag);
       registros = cUser.getRegistrosPorTipoCargo(valor); 
    }

    public ModeloTablaUsuario(long fec) {
        cUser = new CUsuario();
        this.nombreColumnas = nColumnas;
        registros = cUser.getRegistrosFecha(fec);
    }
    
    public ModeloTablaUsuario(long inicio,long fin) {
        cUser = new CUsuario();
        this.nombreColumnas = nColumnas;
        registros = cUser.getRegistrosFecha(inicio,fin);
    }
    
    public ModeloTablaUsuario(int inicioPag,int finalPag,long inicio,long fin) {
        cUser = new CUsuario();
        this.nombreColumnas = nColumnas;
        cUser.setNumPaginador(inicioPag, finalPag);
        registros = cUser.getRegistrosFecha(inicio,fin);
    }
    
    public ModeloTablaUsuario(int inicioPag,int finalPag,long inicio) {
        cUser = new CUsuario();
        this.nombreColumnas = nColumnas;
        cUser.setNumPaginador(inicioPag, finalPag);
        registros = cUser.getRegistrosFecha(inicio);
    }

    public ModeloTablaUsuario(int opcion) {
        cUser = new CUsuario();
        this.nombreColumnas = nColumnas;       
        registros = cUser.getRegistros(new Object[]{new Integer(opcion)});
    }

    public ModeloTablaUsuario(CriterioSQL criterio) {
        
    }
    
    
    
    public int getCantidadRegistros()
    {
        return cUser.getCantidadRegistros();
    }
    

  
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex)
        {
            case 0: return ((Usuario)registros.get(rowIndex)).getDni();
            case 1: return ((Usuario)registros.get(rowIndex)).getCodigo();
            case 2: return ((Usuario)registros.get(rowIndex)).getLogin();
            case 3: return ((Usuario)registros.get(rowIndex)).getNombre();
            case 4: return ((Usuario)registros.get(rowIndex)).getApellidos();
            case 5: return ((Usuario)registros.get(rowIndex)).getFono();
            case 6: return ((Usuario)registros.get(rowIndex)).getTipoCargo();
            case 7: return Helper.getFechaFormateada(new Date(((Usuario)registros.get(rowIndex)).getFecRegistro()),Helper.DIA_MES_ANIO);    
            default: return null;   
        }
    }
    
}
