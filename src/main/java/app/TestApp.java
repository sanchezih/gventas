package app;

import autorizacion.DbAutorizacionAdministrador;
import config.AppConfig;
import modelbd.Usuario;
import ventanas.paneles.PanelAutorizacion;
import javax.swing.JFrame;

/**
 *
 * @author Código Lite
 */
public class TestApp {
	public static void main(String[] arg) {
//        CriterioSQL criterio = new CriterioSQL("gv_usuario");
		// criterio.setUpdatePreparement(true);
		// criterio.setTabla("gv_usuario");
		// criterio.addSelect("dni");
//        criterio.addSelect("sum(login) as suma");
//        criterio.addSelect("nom ");
		// criterio.addSelect("fono");
//        criterio.addCondicion("login", "genius");
//        criterio.addCondicion("ape", "jose");
//        criterio.addCondicion("ape", "jose");
//        criterio.addCondicion("apsae", 14);
//        criterio.setLimit(50);
//        criterio.setDesplazamientos(5);
		// criterio.addCondicionUpdate("id",15, "where");
		// criterio.addCondicion("ape", "jose");
		// criterio.addCompareTexto("ape", "ruiz","or");
		// criterio.addCondicion("edad", 30.25);
		// criterio.setOrden("fecha", "asc");
		// criterio.addCompareValorBetween("precio", 200, 150, "and");
		// criterio.addCompareValorIn("userId", new Object[]{1,2,59,"sol"}, "or");
		// criterio.agruparPor(new String[]{"nombre","ape"});
		// criterio.queTengan("sum", "edad", "=", 15);
		// System.out.println(criterio.getConsultaSQL());
		// System.out.println(criterio.getSQLupdate());
		// System.out.println(criterio.getValores()[3]);

		// Paginador paginador = new Paginador(new Usuario());
//         Paginador paginador = new Paginador();
//         paginador.setTipoObjeto(new Usuario());
//         paginador.setLimite(3);
		// paginador.setDesplazamiento(0);

//        System.out.println(paginador.getTotalItems());
//        System.out.println(paginador.getItem().size());
//        System.out.println("Encontrado primeros");
//        for(JAbstractModelBD  us : paginador.getItem())
//        {
//            Usuario ur = (Usuario)us;
//            System.out.println(ur.getNombre());
//        }
//        
//        System.out.println("Encontrado siguiente");
//        for(JAbstractModelBD  us : paginador.siguiente())
//        {
//            Usuario ur = (Usuario)us;
//            System.out.println(ur.getNombre());
//        }
//        
//        
//        System.out.println("Encontrado siguiente ******");
//        for(JAbstractModelBD  us : paginador.siguiente())
//        {
//            Usuario ur = (Usuario)us;
//            System.out.println(ur.getNombre());
//        }
//        
//        System.out.println("Encontrado anterior");
//        for(JAbstractModelBD  us : paginador.anterior())
//        {
//            Usuario ur = (Usuario)us;
//            System.out.println(ur.getNombre());
//        }

//        CriterioSQL criterio = new CriterioSQL("destt");
//                criterio.addCompareTexto("name", "aoire",null,"=");
//                criterio.addCompareTexto("name", "poar", "=","or");
//                System.out.println(criterio.getConsultaSQL());

		///// codificar adecuadamente | codigo de test
		DbAutorizacionAdministrador auth = new DbAutorizacionAdministrador();
//        auth.clearAll();

		// HashMap permisosPorDefecto =
		// DbAutorizacionAdministrador.getPermisosPorDefecto();
//         Autorizacion vendedor =auth.createRole("vendedor", "Rol vendedor encargado de administra clientes, imprimir"
//                + " documento de ventas y ver stock");
//        Autorizacion tareaVender = auth.createTask("vender", "Tarea Principal de un vendedor");
//        vendedor.addChild("vender");

//        Autorizacion almacenero =auth.createRole("almacenero", "Rol almacenero encargado de registrar productos,mover productos, y actualizar"
//                + " el stock");
//        Autorizacion tareaVender = auth.createTask("almacenar", "Tarea Principal de un almacenero");
//        almacenero.addChild("almacenar");
//        
//        ArrayList<String> operacionesVentas = (ArrayList<String>)permisosPorDefecto.get("almacenero");
//        for(String permisos:operacionesVentas)
//        {
//            if(auth.getAuthItem(permisos) == null)
//                    auth.createOperation(permisos);
//            
//            tareaVender.addChild(permisos);
//        }

//        Autorizacion administrador =auth.createRole("administrador", "Rol almacenero encargado de registrar productos,mover productos, y actualizar"
//                + " el stock");
//        Autorizacion administrar = auth.createTask("administrar", "Tarea Principal de un almacenero");
//        administrador.addChild("administrar");
//        
//        ArrayList<String> operacionesVentas = (ArrayList<String>)permisosPorDefecto.get("administrador");
//        for(String permisos:operacionesVentas)
//        {
//            if(auth.getAuthItem(permisos) == null)
//                    auth.createOperation(permisos);
//            
//            administrar.addChild(permisos);
//        }

///// codificar adecuadamente        

//        Autorizacion authItem = auth.getAuthItem("Administrador");
//        Autorizacion authItem1 = auth.getAuthItem("Tecnico");        
//        auth.createOperation("actualizarCompra");
//        authItem.addChild("actualizarCompra");
//        authItem1.addChild("actualizarCompra");
		// System.out.println(auth.checkAccess("actualizarCompra", "genius", null));
		// auth.assign("Administrador", "genius");
		// auth.addItemChild("Administrador", "BorrarClientes");
		// auth.removeItemChild("Administrador", "BorrarClientes");
		// Autorizacion cr = auth.createRole("Vendedor", "vender productos");
//        ArrayList<AutorizacionAsignado> cr = auth.getAuthAssignments("genius");
//        //cr.addChild("Tecnico");
//        
//        for(AutorizacionAsignado aa: cr)
//        {
//            if(aa.getUserId().equals("genius"))
//            {
//                System.out.println("jahs djfjasd");
//                return;
//            }
//             System.out.println(aa.getItemName());
//        }
		// System.out.println(cr.contains("genius"));
		// cr.contains("genius");
		Usuario usr = new Usuario();
		AppConfig.setUsuario((Usuario) usr.getUltimo(27));
		JFrame vnt = new JFrame("Test Permisos");
		vnt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vnt.add(new PanelAutorizacion());
		vnt.setVisible(true);
		vnt.setSize(420, 380);
	}
}
