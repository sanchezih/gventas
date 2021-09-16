package controllers;

import core.Ex;
import core.JAbstractController;
import core.JAbstractModelBD;
import modelbd.Almacen;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ferz
 */
public class CAlmacen extends JAbstractController implements Serializable {

	Almacen tmp = new Almacen();

	@Override
	public ArrayList getRegistros() {
		return this.getRegistros(new String[] {}, null, null);
	}

	@Override
	public ArrayList getRegistros(Object[] cvl) {
		return this.getRegistros(new String[] {}, cvl != null ? new String[] { Almacen.ACTIVO } : null, cvl);
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
		ArrayList<Almacen> registros = this.getRegistros(new String[] { Almacen.PK_COLUMNA }, new Object[] { cvl });
		return registros.get(0);
	}

	@Override
	public boolean guardarRegistro(JAbstractModelBD mdl) {
		Almacen al = (Almacen) mdl;
		int gravado = 0;
		String campos = "nombre,direccion, encargado";
		Object[] valores = { al.getNombre(), al.getDireccion(), al.getEncargado() };

		gravado = this.agregarRegistroPs(Almacen.TABLA, this.stringToArray(campos, ","), valores);

		if (gravado == 1)
			return true;

		return false;
	}

	@Override
	public int actualizarRegistro(JAbstractModelBD mdl) {
		Almacen al = (Almacen) mdl;
		int gravado = 0;
		String campos = "nombre, direccion, encargado";
		Object[] valores = { al.getNombre(), al.getDireccion(), al.getEncargado(), al.getPrimaryKey() };

		gravado = this.actualizarRegistroPs(Almacen.TABLA,
				this.adjuntarSimbolo(campos, ",", "?") + Ex.WHERE + mdl.getCampoClavePrimaria() + " = ? ", valores);
		System.out.println("grabadoo " + gravado);
		return gravado;
	}

	public int actualizarEstado(JAbstractModelBD mdl) {
		Almacen al = (Almacen) mdl;
		int gravado = 0;
		String campos = "activo," + Almacen.PK_COLUMNA;
		Object[] valores = { al.getActivo(), al.getPrimaryKey() };

		gravado = this.updateRegisterPs(Almacen.TABLA, campos, valores);
		System.out.println("grabadoo " + gravado);
		return gravado;
	}

	public ArrayList<Almacen> getRegistros(String[] columna, Object[] valor) {
		return getRegistros(new String[] {}, columna, valor);
	}

	public ArrayList<Almacen> getAlmacenPorEncargado(Integer idUser) {
		return getRegistros(new String[] { "encargado" }, new Object[] { idUser });
	}

	public Almacen getRegistroPorPk(Integer id) {
		Almacen sm = null;
		try {
			rs = this.selectPorPk(Almacen.TABLA, Almacen.PK_COLUMNA, id);
			while (rs.next()) {
				sm = new Almacen();
				sm.setPrimaryKey(rs.getInt(1));
				sm.setNombre(rs.getString(2));
				sm.setDireccion(rs.getString(3));
				sm.setEncargado(rs.getInt(4));
				sm.setActivo(rs.getInt(5));

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return sm;
	}

	public Almacen getUltimoAlmacen() {
		Almacen sm = null;
		try {
			rs = this.getUltimoRegistro(Almacen.TABLA, Almacen.PK_COLUMNA);
			while (rs.next()) {
				sm = new Almacen();
				sm.setPrimaryKey(rs.getInt(1));
				sm.setNombre(rs.getString(2));
				sm.setDireccion(rs.getString(3));
				sm.setEncargado(rs.getInt(4));
				sm.setActivo(rs.getInt(5));

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return sm;

	}

	@Override
	public ArrayList<Almacen> getRegistros(String[] campos, String[] columnaId, Object[] id) {
		ArrayList<Almacen> rgs = new ArrayList();
		try {

			if (id != null) {
				this.numRegistros = this.getNumeroRegistros(Almacen.TABLA, tmp.getCampoExistencial(),
						tmp.getCampoExistencial(), id);
			} else {
				this.numRegistros = this.getNumeroRegistros(Almacen.TABLA, tmp.getCampoExistencial());
				if (rs.isClosed()) {
					System.out.println("resultset esta cerrado...");
				}
			}
			rs = this.getRegistros(Almacen.TABLA, campos, columnaId, id);
			if (this.numRegistros < finalPag) {
				finalPag = this.numRegistros;
			}
			if (finalPag > inicioPag) {
				inicioPag -= (finalPag - inicioPag) - 1;
			}
			Almacen sm = null;
			while (rs.next()) {
				sm = new Almacen();
				sm.setPrimaryKey(rs.getInt(1));
				sm.setNombre(rs.getString(2));
				sm.setDireccion(rs.getString(3));
				sm.setEncargado(rs.getInt(4));
				sm.setActivo(rs.getInt(5));
				rgs.add(sm);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return rgs;
	}

}
