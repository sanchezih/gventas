package core;

import managerbd.BaseConexion;
import excepciones.ExceptionValoresNoIgual;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public abstract class JAbstractController<M extends JAbstractModelBD> implements JController {

	public ResultSet rs;
	public PreparedStatement ps;
	public Statement st;
	public String sql;
	public int numRegistros = 0;
	public int finalPag = 50;
	public int inicioPag = 0;

	public int borrarRegistro(M mdl) {
		sql = "UPDATE " + mdl.getNombreTabla() + " SET " + mdl.getCampoExistencial() + " = 0 WHERE "
				+ mdl.getCampoClavePrimaria() + " = " + mdl.getPrimaryKey() + "";
		int opcion = 0;
		try {
			opcion = BaseConexion.getStatement().executeUpdate(sql);
			BaseConexion.cerrarEnlacesConexion(BaseConexion.SOLO_STATEMENT);
		} catch (SQLException ex) {
			Logger.getLogger(JAbstractController.class.getName()).log(Level.SEVERE, null, ex);
		}

		return opcion;
	}

	public void setNumPaginador(int inicio, int fin) {
		finalPag = fin;
		inicioPag = inicio;
	}

	public int getCantidadRegistros() {
		return numRegistros;
	}

	public String getCodigo(String nombreTabla, String nombreColumna) {
		String sql = "SELECT " + nombreColumna + " FROM " + nombreTabla + " ORDER BY " + nombreColumna
				+ " DESC LIMIT 1";
		String codigo = null;
		try {
			rs = BaseConexion.getResultSet(sql);
			while (rs.next()) {
				codigo = rs.getObject(1).toString();
			}
			rs.close();
		} catch (SQLException ex) {
			return null;
		}
		return codigo;
	}

	/**
	 * 
	 * @param nombreTabla       nombre de la Tabla
	 * @param nomColumnaCambiar nombre de la columan de la cual se cambiara el valor
	 * @param nomColumnaId      nombre de la columnas con las claves primarias
	 * @param id                clave primaria
	 * @param nuevoValor        nuevo valor.
	 * @return
	 */
	public int eliminacionTemporal(String nombreTabla, String nomColumnaCambiar, String nomColumnaId, Object id,
			Object nuevoValor) {
		int resultado = 1;

		Number vl = null;
		Number nv = null;
		if (esNumerico(id)) {
			vl = (Number) id;
		} else {
			id = "'" + id + "'";
		}

		if (esNumerico(nuevoValor)) {
			nv = (Number) nuevoValor;
		} else {
			nuevoValor = "'" + nuevoValor + "'";
		}

		String sql = "UPDATE " + nombreTabla + " SET " + nomColumnaCambiar + " = " + (nv == null ? nuevoValor : nv)
				+ " where " + nomColumnaId + "=" + (vl == null ? id : vl) + "";
		try {
			resultado = BaseConexion.getStatement().executeUpdate(sql);
		} catch (SQLException ex) {

			ex.printStackTrace();
		}
		System.out.println(sql);
		return resultado;
	}

	public int eliminacionReal(M mdl) {
		sql = "delete from " + mdl.getNombreTabla() + " where " + mdl.getCampoClavePrimaria() + Ex.IGUAL
				+ mdl.getPrimaryKey();
		int opcion = 0;
		try {
			opcion = BaseConexion.getStatement().executeUpdate(sql);
			BaseConexion.cerrarEnlacesConexion(BaseConexion.SOLO_STATEMENT);
		} catch (SQLException ex) {
			Logger.getLogger(JAbstractController.class.getName()).log(Level.SEVERE, null, ex);
		}

		return opcion;
	}

	/**
	 * 
	 * @param CASE nombre de columna
	 * @param WHEN valor
	 * @param THEN valor de retorno
	 * @param ELSE valor de retorno en caso no se cumpla la condicion.
	 * @return una semi consulta case
	 */
	public String consultaCaseSimple(String CASE, String WHEN, String THEN, String ELSE) {
		String sql2 = "case " + CASE + " when '" + WHEN + "' then '" + THEN + "' else '" + ELSE + "' end as "
				+ "respusta ";
		return sql2;
	}

	/**
	 * 
	 * @param CASE nombre de columna
	 * @param WHEN lista de valores
	 * @param THEN lista de valores de retorno
	 * @param ELSE valor de retorno en caso no se cumpla la condicion.
	 * @return una semi consulta case
	 */
	public String consultaCaseSimple(String CASE, String[] WHEN, String[] THEN, String ELSE) {
		if (WHEN.length != THEN.length) {
			throw new ExceptionValoresNoIgual("La longitud de los Arrays no son iguales");
		}
		String sql2 = "case " + CASE;
		for (int i = 0; i < WHEN.length; i++) {
			sql2 += " when '" + WHEN[i] + "' then '" + THEN[i] + "'";
		}

		sql2 += " else '" + ELSE + "' end as respuesta";

		return sql2;
	}

	/**
	 * 
	 * @param WHEN      nombre columna
	 * @param valor     valor
	 * @param THEN      valor de retorno
	 * @param ELSE      valor de retorno en caso no se cumpla la condicion.
	 * @param condicion condicion mayo o menor. < >
	 * @return una semi consulta case
	 */

	public String consultaCaseSimple(String WHEN, Object valor, String THEN, String ELSE, String condicion) {
		String sql2 = "case when " + WHEN + condicion + valor + " then '" + THEN + "' else '" + ELSE + "' end as "
				+ "respuesta ";
		return sql2;
	}

	/**
	 * 
	 * @param WHEN      nombre columna
	 * @param valor     lista de valores
	 * @param THEN      lista de retornos
	 * @param ELSE      valor de retorno en caso no se cumpla la condicion.
	 * @param cnd       lista de condiciones mayor o menor. < >
	 * @param condicion condicion mayo o menor. < >
	 * @return
	 */
	public String consultaCaseSimple(String WHEN, Object[] valor, String THEN[], String ELSE, String[] cnd,
			String condicion) {
		if ((condicion == null && cnd == null) || (condicion != null && cnd != null)) {
			throw new ExceptionValoresNoIgual("Especifique condicional");
		}

		String sql2 = "case when ";
		Number[] nums = verificarTipoDato(valor);
		for (int i = 0; i < THEN.length; i++) {
			sql2 += WHEN + (condicion != null ? condicion : cnd[i]) + nums[i] + " then '" + THEN[i] + "' ";
		}
		sql2 += " else '" + ELSE + "' end as respusta ";
		return sql2;
	}

	private Number[] verificarTipoDato(Object[] datos) {
		Number[] nums = new Number[datos.length];
		for (int i = 0; i < datos.length; i++) {
			if (datos[i] instanceof Integer) {
				getEnteros(nums, datos);
				break;
			}

			if (datos[i] instanceof Double) {
				getDouble(nums, datos);
				break;
			}

			if (datos[i] instanceof Float) {
				getFloat(nums, datos);
				break;
			}
		}
		return nums;
	}

	private void getEnteros(Number[] nums, Object[] datos) {
		for (int i = 0; i < datos.length; i++) {
			nums[i] = (Integer) datos[i];
		}
	}

	private void getDouble(Number[] nums, Object[] datos) {
		for (int i = 0; i < datos.length; i++) {
			nums[i] = (Double) datos[i];
		}
	}

	private void getFloat(Number[] nums, Object[] datos) {
		for (int i = 0; i < datos.length; i++) {
			nums[i] = (Float) datos[i];
		}
	}

	/**
	 * 
	 * @param nomCampo    nombre de un campo(bd)
	 * @param nullNotNull puede ser (is null)|(is not null)
	 * @param cierto      valor para cuado la condicion devuelva true
	 * @param falso       valor para cuado la condicion devuelva false
	 * @return
	 */
	public String consultaIf(String nomCampo, String nullNotNull, String cierto, String falso) {
		String sql2 = "if(" + nomCampo + nullNotNull + ", '" + cierto + "','" + falso + "') as cnti";
		return sql2;
	}

	/**
	 * si el valor del campo no es null entonces retorna el valor del campo<br/>
	 * en caso contrario retornara el valor alternativo
	 * 
	 * @param nomCampo         campo(bd) a comparar
	 * @param valorAlternativo puede se una cadena vacia
	 * @return
	 */
	public String consultaIfNull(String nomCampo, String valorAlternativo) {
		String sql2 = "ifnull(" + nomCampo + ",'" + valorAlternativo + "')";
		return sql2;
	}

	public String consultaAvg(String nomColumnas, String alias) {
		String sql2 = "avg(" + nomColumnas + ") as " + (alias != null ? (!alias.isEmpty() ? alias : "cavg") : "cavg");
		return sql2;

	}

	public int getNumeroRegistros(String nomTabla, String columnaContar, String columaCod, Object valor) {
		Number vl = null;
		int numReg = 0;
		if (esNumerico(valor)) {
			vl = (Number) valor;
		} else {
			valor = "'" + valor + "'";
		}
		String sql2 = "select count(" + columnaContar + ") as nreg from " + nomTabla
				+ (columaCod != null ? Ex.WHERE + columaCod + Ex.IGUAL + (vl != null ? vl : valor) : "");

		try {
			rs = BaseConexion.getResultSet(sql2);
			while (rs.next()) {
				numReg = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return numReg;
	}

	public int getNumeroRegistros(String nomTabla, String columnaContar) {
		return getNumeroRegistros(nomTabla, columnaContar, null, null);
	}

	/**
	 * obtine el valor maximo o mini de una columna
	 * 
	 * @param nomTabla
	 * @param columna
	 * @param columaCod
	 * @param valor
	 * @param maxMin
	 * @return
	 */
	public Object getMaxMin(String nomTabla, String columna, String columaCod, Object valor, String maxMin) {
		Number vl = null;
		Object max = null;
		if (esNumerico(valor)) {
			vl = (Number) valor;
		} else {
			valor = "'" + valor + "'";
		}

		String sql2 = "select " + maxMin + "(" + columna + ") from " + nomTabla + " "
				+ (columaCod != null ? Ex.WHERE + columaCod + Ex.IGUAL + (vl != null ? vl : valor) : "");

		try {
			rs = BaseConexion.getResultSet(sql2);
			while (rs.next()) {
				max = rs.getObject(1);
			}
			st.close();
		} catch (SQLException ex) {
		}

		return max;
	}

	public Object getMaxMin(String nomTabla, String columna, String maxMin) {
		return getMaxMin(nomTabla, columna, null, null, maxMin);
	}

	public Object getSuma(String nomTabla, String columna, String columaCod, Object valor) {
		Number vl = null;
		Object sum = null;
		if (esNumerico(valor)) {
			vl = (Number) valor;
		} else {
			valor = "'" + valor + "'";
		}

		String sql2 = "select sum(" + columna + ") from " + nomTabla + " "
				+ (columaCod != null ? Ex.WHERE + columaCod + Ex.IGUAL + (vl != null ? vl : valor) : "");
		System.out.println(sql2);
		try {
			rs = BaseConexion.getResultSet(sql2);
			while (rs.next()) {
				sum = rs.getObject(1);
			}
			// st.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return sum;
	}

	public Object getSuma(String nomTabla, String columna) {
		return getSuma(nomTabla, columna, null, null);
	}

	/**
	 * verifica si un Objetc es de tipo numerico
	 * 
	 * @param valor
	 * @return
	 */

	public boolean esNumerico(Object valor) {
		if (valor instanceof Number) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param nombreTabla nombre de la tabla
	 * @param campos      nombres de los campos
	 * @param columnaId   columna id
	 * @param id          valor id
	 * @return
	 */
//    @Override
//    public ResultSet getRegistros(String nombreTabla, String[] campos, String columnaId, Object id) {
//        
//        Number vl = null;        
//        if(esNumerico(id))
//        {
//           vl = (Number)id; 
//        }else
//        {
//            id = "'"+id+"'";
//        }
//        
//        sql = "select "+(campos!=null?(campos.length==0?"* ":generarArrayAString(campos)):"* ")+" from "+
//               nombreTabla+(columnaId!=null?(!columnaId.isEmpty()?Ex.WHERE+columnaId+Ex.IGUAL+(vl==null?id:vl):""):"")+Ex.LIMIT+this.inicioPag+","+this.finalPag;
//        
//         rs = BaseConexion.getResultSet(sql);
//        System.out.println(sql);
//        return rs;
//    }

	public ResultSet getRegistros(String nombreTabla, String[] campos, String[] columnaId, Object[] id) {
		String condiciones = unirColumnasValores(columnaId, id);

		sql = "select " + (campos != null ? (campos.length == 0 ? "* " : generarArrayAString(campos)) : "* ") + " from "
				+ nombreTabla + (condiciones != null ? Ex.WHERE + condiciones : "") + Ex.LIMIT + this.inicioPag + ","
				+ this.finalPag;
		System.out.println(sql);
		rs = BaseConexion.getResultSet(sql);

		return rs;
	}

	public ResultSet getRegistros2(CriterioSQL criterio) {
		System.out.println(criterio.getConsultaSQL());
		rs = BaseConexion.getResultSet(criterio.getConsultaSQL());
		return rs;
	}

	public String unirColumnasValores(String[] columnas, Object[] valores) {
		String cad = null;
		if (columnas == null || valores == null) {
			return null;
		}
		if (columnas != null && valores != null) {
			if (columnas.length > 0 && valores.length > 0) {

				cad = "";
				for (int i = 0; i < columnas.length; i++) {
					Number nb = null;
					Object val = null;
					if (esNumerico(valores[i])) {
						nb = (Number) valores[i];
					} else {
						val = "'" + valores[i] + Ex.CUALQUIERA + "'";
					}
					cad += (i == 0 ? "" : Ex.AND) + columnas[i] + (nb == null ? Ex.LIKE : Ex.IGUAL)
							+ (nb == null ? val : nb) + " ";

				}
			}

		}

		return cad;
	}

	public ResultSet getRegistrosMn(String nombreTabla, String[] campos, String[] columnaId, Object[] id) {
		String condiciones = unirColumnasMayorMenor(columnaId, id);

		sql = "select " + (campos != null ? (campos.length == 0 ? "* " : generarArrayAString(campos)) : "* ") + " from "
				+ nombreTabla + (condiciones != null ? Ex.WHERE + condiciones : "") + Ex.LIMIT + this.inicioPag + ","
				+ this.finalPag;
		System.out.println(sql);
		rs = BaseConexion.getResultSet(sql);

		return rs;
	}

	public String unirColumnasMayorMenor(String[] columnas, Object[] valores) {
		String cad = null;
		if (columnas == null || valores == null) {
			return null;
		}
		if (columnas != null && valores != null) {
			if (columnas.length > 0 && valores.length > 0) {
				cad = "";
				Number nb = null;
				Number nb2 = null;
				Object val = null;
				Object val2 = null;
				if (esNumerico(valores[0])) {
					nb = (Number) valores[0];
				} else {
					val = "'" + valores[0] + Ex.CUALQUIERA + "'";

				}

				if (columnas.length > 1) {
					if (esNumerico(valores[1])) {
						nb2 = (Number) valores[1];
					} else {
						val2 = "'" + valores[1] + Ex.CUALQUIERA + "'";
					}
				}

				if (columnas.length == 1) {
					cad += columnas[0] + (nb == null ? Ex.LIKE : Ex.MAYOR_QUE) + (nb == null ? val : nb);
				} else {
					cad += columnas[0] + (nb == null ? Ex.LIKE : Ex.MAYOR_QUE) + (nb == null ? val : nb) + " " + Ex.AND
							+ columnas[1] + (nb2 == null ? Ex.LIKE : Ex.MENOR_QUE) + (nb2 == null ? val2 : nb2);
				}
			}

		}

		return cad;
	}

	/**
	 * obiente los resgistros de una tabla en base a una condicion
	 * 
	 * @param nombreTabla
	 * @param columnaId
	 * @param id
	 * @return
	 */
	public ResultSet getRegistros(String nombreTabla, String[] columnaId, Object[] id) {
		return getRegistros(nombreTabla, null, columnaId, id);
	}

	/**
	 * obtiene los registros de una tabla, especificando los campos
	 * 
	 * @param nombreTabla
	 * @param campos
	 * @return
	 */
	public ResultSet getRegistros(String nombreTabla, String[] campos) {
		return getRegistros(nombreTabla, campos, null, null);
	}

	/**
	 * obtiene los registros de una tabla
	 * 
	 * @param nombreTabla nombre tabla
	 * @return resulset
	 */
	public ResultSet getRegistrosRs(String nombreTabla) {
		return getRegistros(nombreTabla, null, null, null);
	}

	/**
	 * 
	 * @param nomTabla  nombre tabla
	 * @param cnls      lista de columnas y sus valor ejem: nombre='test'
	 * @param columnaId campo id
	 * @param id        id
	 * @return
	 */

	public int actualizarRegistro(String nomTabla, String[] cnls, String columnaId, Object id) {
		Number vl = null;
		if (esNumerico(id)) {
			vl = (Number) id;
		} else {
			id = "'" + id + "'";
		}

		sql = "update " + nomTabla + " set " + generarArrayAString(cnls) + Ex.WHERE + columnaId + Ex.IGUAL
				+ (vl == null ? id : vl);
		int op = 0;
		try {
			op = BaseConexion.getStatement().executeUpdate(sql);
			BaseConexion.cerrarEnlacesConexion(BaseConexion.SOLO_STATEMENT);
		} catch (SQLException ex) {
		}

		System.out.println(sql);
		return op;
	}

	public int actualizarRegistro(String nomTabla, String cnls, String columnaId, Object id) {
		return actualizarRegistro(nomTabla, new String[] { cnls }, columnaId, id);
	}

	public int agregarRegistro(String nombreTabla, String[] campos, Object[] valores) {

		sql = "insert into " + nombreTabla + "(" + generarArrayAString(campos) + ") values(" + formatearValores(valores)
				+ ")";
		int op = 0;
		try {
			op = BaseConexion.getStatement().executeUpdate(sql);
			BaseConexion.cerrarEnlacesConexion(BaseConexion.SOLO_STATEMENT);
		} catch (SQLException ex) {
		}
		System.out.println(sql);
		return op;
	}

	/**
	 * se debe especificar el valor da la calve primaria
	 * 
	 * @param nomTabla  nombre tabla
	 * @param cnls      lista de columnas y sus valor ejem: nombre= ?
	 * @param columnaId campo id
	 * @param id        id
	 * @return
	 */
	public int actualizarRegistroPs(String nomTabla, String cnls, Object[] valores) {

		sql = "update " + nomTabla + " set " + cnls;
		int op = 0;
		try {
			ps = BaseConexion.getPreparedStatement(sql);
			setValores(ps, valores);
			op = ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		System.out.println(sql);
		return op;
	}

	public int updateRegisterPs(String nomTabla, String cnls, Object[] valores) {

		sql = "update " + nomTabla + " set " + this.generarCadenaUpdatePs(this.stringToArray(cnls, ","));
		int op = 0;
		try {
			ps = BaseConexion.getPreparedStatement(sql);
			setValores(ps, valores);
			op = ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		System.out.println(sql);
		return op;
	}

	/**
	 * Agrega resitros usando un objetos PreparedStatemen
	 * 
	 * @param nombreTabla
	 * @param campos
	 * @param valores
	 * @return
	 */
	public int agregarRegistroPs(String nombreTabla, String[] campos, Object[] valores) {

		sql = "insert into " + nombreTabla + "(" + generarArrayAString(campos) + ") values("
				+ formatearValores(valores.length) + ")";
		System.out.println(sql);
		int op = 0;
		try {
			ps = BaseConexion.getPreparedStatement(sql);
			setValores(ps, valores);
			op = ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return op;
	}

	public ResultSet getUltimoRegistro(String table, String column) {
		sql = "select * from " + table + " order by " + column + " desc limit 0,1";
		rs = BaseConexion.getResultSet(sql);
		return rs;
	}

	/**
	 * establece valores a un objeto PreparedStatement para luego ser ejecutado
	 * 
	 * @param ps
	 * @param valores
	 */
	private void setValores(PreparedStatement ps, Object[] valores) {
		try {
			for (int i = 0; i < valores.length; i++) {
				if (getInt(valores[i]) != null) {
					ps.setInt(i + 1, getInt(valores[i]));
				} else if (getDouble(valores[i]) != null) {
					ps.setDouble(i + 1, getDouble(valores[i]));
				} else if (getFloat(valores[i]) != null) {
					ps.setFloat(i + 1, getFloat(valores[i]));
				} else if (valores[i] instanceof DatoArchivo) {
					DatoArchivo da = (DatoArchivo) valores[i];
					ps.setBinaryStream(i + 1, da.getFis(), da.getLongitud());
				} else {
					if (valores[i] != null) {
						ps.setString(i + 1, valores[i].toString());
					} else {
						ps.setString(i + 1, null);
					}

				}
			}
		} catch (SQLException ex) {
		}
	}

	private Integer getInt(Object valor) {
		Integer vl = null;
		if (valor instanceof Integer) {
			vl = (Integer) valor;
		}
		return vl;
	}

	private Double getDouble(Object valor) {
		Double vl = null;
		if (valor instanceof Double) {
			vl = (Double) valor;
		}
		return vl;
	}

	private Float getFloat(Object valor) {
		Float vl = null;
		if (valor instanceof Float) {
			vl = (Float) valor;
		}
		return vl;
	}

	/**
	 * Verifica si un objetos es numerico
	 * 
	 * @param valor
	 * @param obj   no se usa para nadaa
	 * @return
	 */
	public Number esNumerico(Object valor, Object obj) {
		Number nb = null;
		if (valor instanceof Number) {
			nb = (Number) valor;
			return nb;
		} else {
			return null;
		}
	}

	/**
	 * Este metodo genera una cadena con simbolos "?" separada por comas <br/>
	 * 
	 * @param num cantidad de repeticiones *
	 * @return
	 */
	public String formatearValores(int num) {
		String cad = "";
		if (num > 0) {
			cad = "";
			for (int i = 0; i < num; i++) {
				cad += (i == 0 ? " " : ", ") + "?";
			}
		}
		return cad;
	}

	/**
	 * Este metodo une los items de un array en una cadena, pero separado por una
	 * coma<br/>
	 * Y ademas verifica el tipo de dato de cada item, si el item no fuera numeric,
	 * entonces<br/>
	 * envuelve este item entre comiilas.
	 * 
	 * @param valores
	 * @return cadena
	 */
	public String formatearValores(Object[] valores) {
		String cad = "* ";
		Number nb = null;
		if (valores != null) {
			if (valores.length > 0) {
				cad = "";
				if (valores.length == 1) {
					nb = esNumerico(valores[0], null);
					cad += (nb == null ? "'" + valores[0] + "'" : nb);
					return cad;
				}
				for (int i = 0; i < valores.length; i++) {
					nb = esNumerico(valores[i], valores[i]);
					cad += (i == 0 ? " " : ", ") + (nb != null ? nb : "'" + valores[i] + "'");
				}
			}
		}
		return cad;
	}

	/**
	 * Este metodo une los items de una array en una cadena, pero separado por una
	 * coma
	 * 
	 * @param campos array de cadenas
	 * @return cadena
	 */
	public String generarArrayAString(String[] campos) {
		String cad = "* ";
		if (campos != null) {
			if (campos.length > 0) {
				cad = "";
				if (campos.length == 1) {
					return cad + campos[0];
				}
				for (int i = 0; i < campos.length; i++) {
					cad += (i == 0 ? " " : ", ") + campos[i];
				}
			}
		}
		return cad;
	}

	/**
	 * 
	 * @param cad       cadena
	 * @param separador simbolo para dividir la cadena
	 * @return devuelve array de cadenas
	 */
	public String[] stringToArray(String cad, String separador) {
		return cad.split(separador);
	}

	/**
	 * 
	 * @param cad       cadena
	 * @param separador simbolo separador, utilizado para partir la cadena
	 * @param simbolo   simbolo que asigna a cada item que se genere de la cadena
	 * @return cadena: (nome = ?)
	 */
	public String adjuntarSimbolo(String cad, String separador, String simbolo) {
		String[] campos = stringToArray(cad, separador);
		String ncad = "";
		for (int i = 0; i < campos.length; i++) {
			ncad += (i == 0 ? " " : ", ") + campos[i] + Ex.IGUAL + simbolo;
		}

		return ncad;
	}

	/**
	 * 
	 * @param cad       cadena de actualizacion
	 * @param separador simbolo separador, utilizado para partir la cadena
	 * @param simbolo   simbolo que asigna a cada item que se genere de la cadena
	 * @return cadena: (nome = ?)
	 */
	public String generarCadenaUpdatePs(String[] campos) {

		String ncad = "";
		if (campos.length == 0 || campos.length == 1) {
			throw new ExceptionValoresNoIgual("Error en la cantidad de campos");
		}
		if (campos.length == 2) {
			return campos[0] + Ex.IGUAL + "? " + Ex.WHERE + campos[1] + Ex.IGUAL + "?";
		}
		for (int i = 0; i < campos.length; i++) {
			if (i == campos.length - 1) {
				ncad += Ex.WHERE + campos[i] + Ex.IGUAL + "?";
				continue;
			}
			ncad += (i == 0 ? " " : ", ") + campos[i] + Ex.IGUAL + "?";
		}

		return ncad;
	}

	public boolean existe(String nomTabla, String nomCampo, Object value) {
		Number nb = esNumerico(value, null);
		sql = "select * from " + nomTabla + Ex.WHERE + nomCampo + Ex.IGUAL + (nb != null ? nb : "'" + value + "'");
		boolean encontrado = false;
		try {
			rs = BaseConexion.getResultSet(sql);
			if (rs.next()) {
				encontrado = true;
			}
			rs.close();
		} catch (SQLException ex) {
		}

		return encontrado;
	}

	public boolean existe(String nomTabla, String nomCampo, Object value, String cmp, Object exepcion) {
		Number nb = esNumerico(value, null);
		Number exp = esNumerico(exepcion, null);
		sql = "select * from " + nomTabla + Ex.WHERE + nomCampo + Ex.IGUAL + (nb != null ? nb : "'" + value + "'")
				+ Ex.AND + cmp + Ex.DIFERENTE + (exp != null ? exp : "'" + exepcion + "'");
		boolean encontrado = false;
		try {
			rs = BaseConexion.getResultSet(sql);
			if (rs.next()) {
				encontrado = true;
			}
			rs.close();
		} catch (SQLException ex) {
		}

		return encontrado;
	}

	// compara si existe un registro con dos valores que se indica
	public boolean existeDosPk(String nomTabla, String nomCampo, Object value, String cmp, Object exepcion) {
		Number nb = esNumerico(value, null);
		Number exp = esNumerico(exepcion, null);
		sql = "select * from " + nomTabla + Ex.WHERE + nomCampo + Ex.IGUAL + (nb != null ? nb : "'" + value + "'")
				+ Ex.AND + cmp + Ex.IGUAL + (exp != null ? exp : "'" + exepcion + "'");
		boolean encontrado = false;
		try {
			rs = BaseConexion.getResultSet(sql);
			if (rs.next()) {
				encontrado = true;
			}
			rs.close();
		} catch (SQLException ex) {
		}

		return encontrado;
	}

	public InputStream getArchivo(String nomTabla, String campoArchivo, String campoCondicional, Object valor) {
		Number nb = esNumerico(valor, null);
		sql = "select " + campoArchivo + " from " + nomTabla + Ex.WHERE + campoCondicional + Ex.IGUAL
				+ (nb != null ? nb : "'" + valor + "'");
		InputStream bs = null;
		try {
			rs = BaseConexion.getResultSet(sql);
			if (rs.next()) {
				bs = rs.getBinaryStream(1);
			}
			rs.close();
		} catch (SQLException ex) {
		}
		return bs;
	}

	private ResultSet getRegistroPk(String nombreTabla, String[] campos, String columnaid, Integer id) {
		sql = "select " + (campos != null ? (campos.length == 0 ? "* " : generarArrayAString(campos)) : "* ") + " from "
				+ nombreTabla + Ex.WHERE + columnaid + Ex.IGUAL + id;

		rs = BaseConexion.getResultSet(sql);
		return rs;
	}

	public ResultSet selectPorPk(String nombreTabla, String columnaid, Integer id) {
		return this.getRegistroPk(nombreTabla, null, columnaid, id);
	}

	public abstract ArrayList<M> getRegistros();

	public abstract ArrayList<M> getRegistros(Object[] cvl);

	public abstract M getRegistro();

	public abstract M getRegistro(JAbstractModelBD mdl, boolean opcion);

	public abstract M buscarRegistro(Object cvl);

	public abstract boolean guardarRegistro(M mdl);

	public abstract int actualizarRegistro(M mdl);

	public abstract ArrayList<M> getRegistros(String[] campos, String[] columnaId, Object[] id);

}
