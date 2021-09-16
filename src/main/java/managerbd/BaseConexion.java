package managerbd;

import core.CriterioSQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class BaseConexion {

	public static Connection cns = null;
	private static ResultSet rs = null;
	private static Statement st = null;
	private static PreparedStatement ps = null;
	private static String host = "192.168.4.172";
	private static String dataBase = "gventas"; // nombre base de datos
	private static String user = "root"; // usuario de la base de datos
	private static String pass = "password1234"; // contraseña de la base de datos
	public static int TODO = 1;
	public static int SOLO_STATEMENT = 2;
	public static int SOLO_RESULTSET = 3;
	public static int SOLO_PREPAREDSTATEMENT = 4;
	public static int CONNECION = 5;

	public static void init() {
		try {
			if (cns == null) {
				Class.forName("com.mysql.jdbc.Driver");
				String url = "jdbc:mysql://" + host + ":3306/" + dataBase;
				cns = DriverManager.getConnection(url, user, pass);
			}
		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Fue imposible conectarse al servidor.", host,
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		} catch (SQLException ex) {
			System.out.println("No se pudo establecer la connecion");
			JOptionPane.showMessageDialog(null,
					"no se pudo conectar con el servidor " + "jdbc:mysql://" + host + ":3306/" + dataBase,
					"Error de Conecion", JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "Fue imposible conectarse al servidor."
					+ "Porfavor siga los pasos para para ejecutar este programa."
					+ "\nPrimero: Inicie su servido de base de datos si no esta iniciado.\n"
					+ "Segundo: En los archivos de este proyecto existe una carpeta\n"
					+ "con el nombre 'basededato' dentro de esta carpeta existe el script sql"
					+ "\npara la base de datos importe este script desde su gestor de base\n"
					+ "de datos(sqlyog,phpmyadmin,etc). si aun si tiene problemas\n"
					+ "debe crear una base de datos con el nombre 'gventas' y usando esta base de datos"
					+ "\nimporte el script indicado anteriormente."
					+ "\nTercero: configura cambie el nombre de usuario y la contraseña\n"
					+ "con el nombre de usuario y contraseña de su servidor de base de datos."
					+ "\n en el archivo 'elaprendiz.managerbd.BaseConexion.java'\n" + "Vuelva a ejecutar este programa",
					host, JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			System.exit(0);
		} catch (NullPointerException ex) {
			System.out.println("se esta pasando un objeto nulo");
			ex.printStackTrace();
		}

	}

	/**
	 * antes de llamar a este metodo, debe llamar al metodo init.
	 *
	 * @return
	 * @throws NullPointerException
	 */
	public static Connection getConexion() throws NullPointerException {
		if (cns == null) {
			init();
		}
		return cns;
	}

	public static ResultSet getResultSet(String sql) {
		if (cns == null) {
			init();
		}
		try {
			rs = getStatement().executeQuery(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return rs;
	}

	public static List<String> getLista(CriterioSQL sql) {
		if (cns == null) {
			init();
		}
		List<String> reg = new ArrayList();
		try {
			rs = getStatement().executeQuery(sql.getConsultaSQL());
			while (rs.next()) {
				reg.add(rs.getString(1));
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return reg;
	}

	public static HashMap getItems(CriterioSQL sql) {
		if (cns == null) {
			init();
		}
		HashMap items = new HashMap();
		List<Object> reg = new ArrayList();
		try {
			rs = getStatement().executeQuery(sql.getConsultaSQL());
			int conter = 0;
			while (rs.next()) {
				reg.add(rs.getString(1));
				reg.add(rs.getInt(2));
				reg.add(rs.getString(3));
				reg.add(rs.getString(4));
				reg.add(rs.getString(5));
				items.put(conter, reg);
				conter++;
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return items;
	}

	public static ArrayList<HashMap> getItemChildrens(String sql) {
		if (cns == null) {
			init();
		}
		HashMap items;
		ArrayList<HashMap> reg = new ArrayList();
		try {
			rs = getStatement().executeQuery(sql);
			while (rs.next()) {
				items = new HashMap();
				items.put("nombre", rs.getString(1));
				items.put("tipo", rs.getInt(2));
				items.put("descripcion", rs.getString(3));
				items.put("bizzrule", rs.getString(4));
				items.put("dato", rs.getObject(5));
				reg.add(items);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return reg;
	}

	public static Statement getStatement() {
		if (cns == null) {
			init();
		}
		try {
			st = cns.createStatement();
		} catch (SQLException ex) {
		}

		return st;
	}

	public static PreparedStatement getPreparedStatement(String sql) {
		if (cns == null) {
			init();
		}

		if (ps == null) {
			try {
				ps = cns.prepareStatement(sql);
			} catch (SQLException ex) {
			}
		} else if (ps != null) {
			try {
				if (ps.isClosed()) {
					ps = cns.prepareStatement(sql);
				}
			} catch (SQLException ex) {
			}
		}
		return ps;
	}

	/**
	 *
	 * @param tipoCierre -
	 *                   TODO,SOLO_STATEMENT,SOLO_RESULTSET,SOLO_PREPAREDSTATEMENT,CONNECION
	 */
	public static void cerrarEnlacesConexion(int tipoCierre) {
		switch (tipoCierre) {
		case 1:
			cerrarConexion();
			cerrarResultSet();
			cerrarStatement();
			cerrarPreparedStatement();
			break;
		case 2:
			cerrarStatement();
			break;
		case 3:
			cerrarResultSet();
			break;
		case 4:
			cerrarPreparedStatement();
			break;
		case 5:
			cerrarConexion();
			break;
		default:
		}
	}

	private static void cerrarResultSet() {
		if (rs != null) {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException es) {
			}
		}
	}

	private static void cerrarStatement() {
		if (st != null) {
			try {
				if (!st.isClosed()) {
					st.close();
				}
			} catch (SQLException es) {
			}
		}
	}

	private static void cerrarPreparedStatement() {
		if (ps != null) {
			try {
				if (!ps.isClosed()) {
					ps.close();
				}
			} catch (SQLException es) {
			}
		}
	}

	private static void cerrarConexion() {
		if (cns != null) {
			try {
				if (!cns.isClosed()) {
					cns.close();
				}
			} catch (SQLException es) {
			}
		}
	}

}
