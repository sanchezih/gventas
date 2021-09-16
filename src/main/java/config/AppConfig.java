package config;

import controllers.CEncargadoAlmacen;
import controllers.CMoneda;
import controllers.CUsuario;
import modelbd.Almacen;
import modelbd.EncargadoAlmacen;
import modelbd.Moneda;
import modelbd.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import org.w3c.dom.Document;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class AppConfig implements Serializable {

	private static Usuario usuario;
	private static Almacen almacen;
	private static Moneda moneda;
	private static ArrayList<Almacen> almacenes;

	public static enum Estado {

		ERROR, NO_EXISTE, ERROR_CLAVE, ACCESO_OK
	};

	public AppConfig() // throws ParserConfigurationException
	{
//        DocumentBuilderFactory dbc = DocumentBuilderFactory.newInstance();
//        DocumentBuilder db = dbc.newDocumentBuilder();
//        Document doc = db.newDocument();

	}

	public static Estado configUsuario(String login, String clave) {
		if (usuario == null) {
			return initUsuario(login, clave);
		} else {
			return Estado.ERROR;
		}
	}

	public static Usuario getUsuario() {
		return usuario;
	}

	public static void setUsuario(Usuario usuario) {
		AppConfig.usuario = usuario;
	}

	public static Almacen getAlmacen() {
		return almacen;
	}

	public static void setAlmacen(Almacen almacen) {
		AppConfig.almacen = almacen;
	}

	public static Moneda getMoneda() {
		return moneda;
	}

	public static void setMoneda(Moneda moneda) {
		AppConfig.moneda = moneda;
	}

	public static ArrayList<Almacen> getAlmacenes() {
		return almacenes;
	}

	public static void setAlmacenes(ArrayList<Almacen> almacenes) {
		AppConfig.almacenes = almacenes;
	}

	private static Estado initUsuario(String login, String clave) {
		CUsuario cuser = new CUsuario();
		usuario = new Usuario();
		usuario = cuser.getLoginUser(login);
		if (usuario == null) {
			return Estado.NO_EXISTE;
		} else {
			if (clave.equals(usuario.desencriptarPass())) {
				cuser.grabarEnBitacora(usuario);
				if (moneda == null) {
					CMoneda controllerMoneda = new CMoneda();
					controllerMoneda.getRegistros();
					moneda = controllerMoneda.getPredeterminado();
				}

				if (almacen == null) {
					CEncargadoAlmacen controllerEa = new CEncargadoAlmacen();
					ArrayList<EncargadoAlmacen> rEa = controllerEa.getRegistrosPorEncargado(usuario.getPrimaryKey());
					if (!rEa.isEmpty()) {
						almacenes = new ArrayList<Almacen>();
						almacen = rEa.get(0).getAlmacen();
						for (EncargadoAlmacen eat : rEa) {
							almacenes.add(eat.getAlmacen());
						}
					}
				}
				return Estado.ACCESO_OK;
			} else {
				return Estado.ERROR_CLAVE;
			}
		}
	}

}
