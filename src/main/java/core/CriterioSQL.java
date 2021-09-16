package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author Código Lite
 */
public class CriterioSQL {
	private String select = " * ";
	private int desplazamiento = -1;
	private int limite = -1;
	private boolean distinct = false;
	private String orderBy = "";
	private String alias = "";
	private String tabla;
	private String groupBy = "";
	private String having = "";
	private List valoresAcumulados; // lista que acumulara los valroes de las condiciones

	public boolean updatePreparement = false; // poner a true para gener una setencia
												// sql formateado esclusivamente para
												// con un objeto PreparementStatemen

	private List<Condicion> condicion;

	public Object[] getValores() {
		valoresAcumulados = new ArrayList();
		for (Condicion cnd : condicion) {
			valoresAcumulados.add(cnd.getAbsoluteValue());
		}
		return valoresAcumulados.toArray();
	}

	public CriterioSQL() {
		this.condicion = new ArrayList();
	}

	public List<Condicion> getCondicion() {
		return condicion;
	}

	public CriterioSQL(String tabla) {
		this.tabla = tabla;
		this.condicion = new ArrayList();
	}

	public CriterioSQL(String tabla, String alias) {
		this.tabla = tabla;
		this.alias = alias;
		this.condicion = new ArrayList();
	}

	public boolean isUpdatePreparement() {
		return updatePreparement;
	}

	public void setUpdatePreparement(boolean updatePreparement) {
		this.updatePreparement = updatePreparement;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public void setSelect(String campos) {
		select = campos;
	}

	public void setSelect(String[] campos) {
		String arrayFromtring = this.getStringFromArray(campos);
		this.setSelect(arrayFromtring);
	}

	/**
	 * Agrega un campo que estara incluido en un select
	 * 
	 * @param campo
	 */
	public void addSelect(String campo) {
		if (select.indexOf("*") != -1) {
			select = " " + campo;
		} else {
			select += "," + campo;
		}
	}

	// por defecto se usa el signo '=' y el And para realizar la comparacion
	// solo soporta valores numericos
	public void addCondicion(String campo, Number value) {
		this.addCondicion(campo, value, "=", "and");
	}

	// por defecto se usa el signo '=' y el And para realizar la comparacion
	// solo soporta cadenas
	public void addCondicion(String campo, String value) {
		this.addCompareTexto(campo, value);
	}

	/**
	 * metodo que agrega la clausa where en una sentencia de update por eso debe
	 * debe ser el ultimo metodo en llamar
	 * 
	 * @param campo
	 * @param value
	 * @param operadorlogico
	 */

	public void addCondicionUpdate(String campo, Object value, String operadorlogico) {
		this.addCondicion(campo, value, "=", operadorlogico);
	}

	// por defecto And
	public void addCondicion(String campo, Object value, String operadorComparacion) {
		this.addCondicion(campo, value, operadorComparacion, "and");
	}

	// valores cadenas o enteros
	public void agruparPor(Object[] valores) {

		this.groupBy = " group by " + this.unirCon(valores, ",");
	}

	public void agruparPor(Object valor) {
		this.groupBy = " group by " + (this.esNumero(valor) != null ? valor : "'" + valor + "'");
	}

	// agrupar por campos
	public void agruparPor(String[] campos) {
		this.groupBy = " group by " + this.getStringFromArray(campos);
	}

	public void agruparPor(String campo) {
		this.groupBy = " group by " + campo;
	}

	/**
	 * metodo que genera having en una consulta
	 * 
	 * @param funcion     funcion puede ser sum, avg, count, etc
	 * @param campo       nombre de una columna de la tabla de la base de datos
	 * @param comparacion signo de comparacion puede ser < <= >= !=
	 * @param valor       un valor numerico
	 */
	public void queTengan(String funcion, String campo, String comparacion, Number valor) {
		if (campo == null)
			campo = "*";
		if (campo.isEmpty())
			campo = "*";
		if (funcion != null) {
			if (!funcion.isEmpty()) {
				this.having = " having " + funcion + "(" + campo + ")" + comparacion + valor;
			}
		}

		if (this.having.isEmpty()) {
			this.having = campo + comparacion + valor;
		}

	}

	/**
	 * metodo que genera having en una consulta
	 * 
	 * @param funcion     funcion puede ser sum, avg, count, etc
	 * @param campo       nombre de una columna de la tabla de la base de datos
	 * @param comparacion signo de comparacion puede ser < <= >= !=
	 * @param valor       una cadena
	 */
	public void queTengan(String funcion, String campo, String comparacion, String valor) {
		if (campo == null)
			campo = "*";
		if (campo.isEmpty())
			campo = "*";
		if (funcion != null) {
			if (!funcion.isEmpty()) {
				this.having = " having " + funcion + "(" + campo + ")" + comparacion + "'" + valor + "'";
			}
		}

		if (this.having.isEmpty()) {
			this.having = campo + comparacion + "'" + valor + "'";
		}
	}

	/**
	 * 
	 * @param campo               nombre del campo de una tabla de la base de datos
	 * @param value               valor que se usar para la condicion
	 * @param operadorComparacion pueden ser (=,!=,<= ,>=)
	 * @param operadorBoleano     puede ser and, or, xor
	 */
	public void addCondicion(String campo, Object value, String operadorComparacion, String operadorBoleano) {
		Condicion cond = new Condicion();
		cond.setValor(value);
		cond.setCampo(campo);
		cond.setOperadorLogico(operadorBoleano);
		if (this.updatePreparement) {
			if (operadorBoleano.indexOf("where") != -1)
				cond.setOperadorLogico(operadorBoleano);
			else
				cond.setOperadorLogico(",");
		}
		cond.setOperadorComparacion(operadorComparacion);
		condicion.add(cond);
	}

	public void addCompareTexto(String campo, String value) {
		addCompareTexto(campo, value, "and");
	}

	public void addCompareTexto(String campo, String value, String operadorLogico) {
		Condicion cond = new Condicion();
		cond.setValor(value);
		cond.setCampo(campo);
		cond.setOperadorLogico(operadorLogico);
		cond.setOperadorComparacion("like");
		if (this.updatePreparement) {
			cond.setOperadorComparacion("=");
			if (operadorLogico.indexOf("where") != -1)
				cond.setOperadorLogico(operadorLogico);
			else
				cond.setOperadorLogico(",");
		}
		cond.setComparaTexto(true);
		condicion.add(cond);
	}

	/**
	 * 
	 * @param campo                 campo de la base de datos
	 * @param value                 valor
	 * @param operadorLogico        Operador Logico < > =>
	 * @param operadorDeComparacion and or like
	 */
	public void addCompareTexto(String campo, String value, String operadorLogico, String operadorDeComparacion) {
		Condicion cond = new Condicion();
		cond.setValor(value);
		cond.setCampo(campo);
		cond.setOperadorLogico(operadorLogico);
		cond.setOperadorComparacion(operadorDeComparacion);
		if (this.updatePreparement) {
			cond.setOperadorComparacion("=");
			if (operadorLogico.indexOf("where") != -1)
				cond.setOperadorLogico(operadorLogico);
			else
				cond.setOperadorLogico(",");
		}
		cond.setComparaTexto(true);
		condicion.add(cond);
	}

	public void addCompareTextoEmpiezaCon(String campo, String value, String comodin, String operadorLogico) {
		Condicion cond = new Condicion();
		cond.setValor(value);
		cond.setCampo(campo);
		cond.setOperadorLogico(operadorLogico);
		cond.setOperadorComparacion("like");
		cond.setComodinInicial(comodin);
		cond.setComparaTexto(true);
		cond.setComparaCadenaInicio(true);
		condicion.add(cond);

	}

	public void addCompareTextoTerminaCon(String campo, String value, String comodin, String operadorLogico) {
		Condicion cond = new Condicion();
		cond.setValor(value);
		cond.setCampo(campo);
		cond.setOperadorLogico(operadorLogico);
		cond.setComodinFinal(comodin);
		cond.setOperadorComparacion("like");
		cond.setComparaTexto(true);
		cond.setComparaCadenaFinal(true);
		condicion.add(cond);
	}

	public void addCompareTextoInicioFinal(String campo, String value, String comodinInicial, String comodinFinal) {
		Condicion cond = new Condicion();
		cond.setValor(value);
		cond.setCampo(campo);
		cond.setComodinFinal(comodinFinal);
		cond.setComodinInicial(comodinInicial);
		cond.setOperadorComparacion("like");
		cond.setOperadorLogico("and");
		cond.setComparaTexto(true);
		condicion.add(cond);
	}

	// distinct para no mostrar valores con repetidos
	public void setDistinct(boolean establecer) {
		this.distinct = true;
	}

	// uso del between
	public void addCompareValorBetween(String campo, Object valorInical, Object valorFinal, String operadorBooleano) {
		String value = "" + (this.esNumero(valorInical) != null ? valorInical : "'" + valorInical + "'");
		value += " and " + (this.esNumero(valorFinal) != null ? valorFinal : "'" + valorFinal + "'");
		this.addCondicion(campo, value, "between", operadorBooleano);
	}

	// uso del in
	public void addCompareValorIn(String campo, Object[] valores, String operadorBooleano) {
		String value = "(" + unirCon(valores, ",") + ")";
		this.addCondicion(campo, value, "in", operadorBooleano);
	}

	// order by
	public void setOrden(String campo, String tipoOrden) {
		this.orderBy = " order by " + campo + " " + tipoOrden;
	}

	// limit
	public void setLimit(int limit) {
		this.limite = limit;
	}

	// limit desplazamiento
	public void setDesplazamientos(int offset) {
		this.desplazamiento = offset;
	}

	public void setSQLnativo(String sql) {
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setCondicion(List<Condicion> condicion) {
		this.condicion = condicion;
	}

	public class Condicion {

		private long idObject;
		public String campo;
		public Object valor;
		private Object valorTexto;
		public String operadorComparacion; // =, != ,<=, like

		/**
		 * var operadorBoleano almacena solo un operador para comparacion de texto
		 * especificamente '_' , '%' soloe puede almacenar uno y no mas.
		 */
		public String operadorLogico; // operadores booleanos ( and, or)

		public String comodinInicial = ""; // puede ser (% , _ )
		public String comodinFinal = "";// puede ser (% , _ )

		public boolean comparaCadenaInicio = false;
		public boolean comparaCadenaFinal = false;
		public boolean comparaCadenaInicioFina = false;
		private boolean comparaTexto; // activa comparacion de cadena

		public Condicion() {
			idObject = new Random().nextLong();
		}

		public Condicion(String campo, Object valor, String operadorComparacion, String operadorBoleano) {
			this.campo = campo;
			this.valor = valor;
			this.operadorComparacion = operadorComparacion;
			this.operadorLogico = operadorBoleano;
			idObject = new Random().nextLong();
		}

		public String getCampo() {
			return campo;
		}

		public void setCampo(String campo) {
			this.campo = campo;
		}

		public Object getAbsoluteValue() {
			return valor;
		}

		public Object getValor() {
			if (updatePreparement) {
				return "?";
			}
			return valor;
		}

		public void setValor(Object valor) {
			this.valor = valor;
		}

		public String getOperadorComparacion() {
			return operadorComparacion;
		}

		public void setOperadorComparacion(String operadorComparacion) {
			this.operadorComparacion = operadorComparacion;
		}

		public String getOperadorLogico() {
			return this.operadorLogico;
		}

		public void setOperadorLogico(String operadorBoleano) {
			this.operadorLogico = operadorBoleano;
		}

		public boolean isComparaCadenaInicio() {
			return comparaCadenaInicio;
		}

		public void setComparaCadenaInicio(boolean comparaCadenaInicio) {
			this.comparaCadenaInicio = comparaCadenaInicio;
		}

		public boolean isComparaCadenaFinal() {
			return comparaCadenaFinal;
		}

		public void setComparaCadenaFinal(boolean comparaCadenaFinal) {
			this.comparaCadenaFinal = comparaCadenaFinal;
		}

		public boolean isComparaCadenaInicioFina() {
			return comparaCadenaInicioFina;
		}

		public boolean isComparaTexto() {
			return comparaTexto;
		}

		public void setComparaTexto(boolean comparaTexto) {
			this.comparaTexto = comparaTexto;
		}

		public String getComodinInicial() {
			return comodinInicial;
		}

		public void setComodinInicial(String comodinInicial) {
			this.comodinInicial = comodinInicial;
		}

		public String getComodinFinal() {
			return comodinFinal;
		}

		public void setComodinFinal(String comodinFinal) {
			this.comodinFinal = comodinFinal;
		}

		public String getValorTexto() {
			if (updatePreparement) {
				return "?";
			}
			return "'" + this.comodinInicial + this.valor + this.comodinFinal + "'";
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 53 * hash + (int) (this.idObject ^ (this.idObject >>> 32));
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final Condicion other = (Condicion) obj;
			if (this.idObject != other.idObject) {
				return false;
			}
			return true;
		}

	}

	public String getConsultaSQL() {

		String limite = "";
		if (this.limite > -1) {
			limite += " limit ";
			if (this.desplazamiento > -1) {
				limite += this.desplazamiento + ",";
			}
			limite += this.limite;
		}
		String sql = "select " + this.select + " from " + this.tabla + " " + this.procesaCondicion() + this.groupBy
				+ this.having + this.orderBy + limite;
		System.out.println(sql);
		return sql;
	}

	public String getSQLfromWhere() {

		String limite = "";
		if (this.limite > -1) {
			limite += " limit ";
			if (this.desplazamiento > -1) {
				limite += this.desplazamiento + ",";
			}
			limite += this.limite;
		}
		String sql = this.procesaCondicion() + this.groupBy + this.having + this.orderBy + limite;

		return sql;
	}

	public String getOnlyWhere() {

		String limite = "";
		if (this.limite > -1) {
			limite += " limit ";
			if (this.desplazamiento > -1) {
				limite += this.desplazamiento + ",";
			}
			limite += this.limite;
		}
		String sql = this.procesaCondicion();

		return sql;
	}

	public String getSQLupdate() {

		String sql = "update " + this.tabla + " " + procesaUpdate();
		return sql;
	}

	private String procesaCondicion() {

		String condicionProcesada = " where ";
		if (this.condicion == null)
			return "";
		if (this.condicion.isEmpty())
			return "";
		if (this.condicion.size() == 1) {
			Condicion cod = this.condicion.get(0);
			if (cod.isComparaTexto()) {
				condicionProcesada += cod.getCampo() + " " + cod.getOperadorComparacion() + " " + cod.getValorTexto();
			} else {
				condicionProcesada += cod.getCampo() + " " + cod.getOperadorComparacion() + " " + cod.getValor();
			}
		} else {

			for (Condicion cod : this.condicion) {
				if (condicion.indexOf(cod) == 0) {
					if (cod.isComparaTexto()) {
						condicionProcesada += cod.getCampo() + " " + cod.getOperadorComparacion() + " "
								+ cod.getValorTexto();
					} else {
						condicionProcesada += cod.getCampo() + " " + cod.getOperadorComparacion() + " "
								+ cod.getValor();
					}
				} else {
					if (cod.isComparaTexto()) {
						condicionProcesada += " " + cod.getOperadorLogico() + " " + cod.getCampo() + " "
								+ cod.getOperadorComparacion() + " " + cod.getValorTexto();
					} else {
						condicionProcesada += " " + cod.getOperadorLogico() + " " + cod.getCampo() + " "
								+ cod.getOperadorComparacion() + " " + cod.getValor();
					}
				}

			}
		}

		return condicionProcesada;
	}

	private String procesaUpdate() {

		String condicionProcesada = " set ";
		if (this.condicion == null)
			return "";
		if (this.condicion.isEmpty())
			return "";
		if (this.condicion.size() == 1) {
			Condicion cod = this.condicion.get(0);
			if (cod.isComparaTexto()) {
				condicionProcesada += cod.getCampo() + " = " + cod.getValorTexto();
			} else {
				condicionProcesada += cod.getCampo() + " = " + cod.getValor();
			}
		} else {

			for (Condicion cod : this.condicion) {
				if (condicion.indexOf(cod) == 0) {
					if (cod.isComparaTexto()) {
						condicionProcesada += cod.getCampo() + " = " + cod.getValorTexto();
					} else {
						condicionProcesada += cod.getCampo() + " = " + cod.getValor();
					}
				} else {
					if (cod.isComparaTexto()) {
						condicionProcesada += " " + cod.getOperadorLogico() + " " + cod.getCampo() + " = "
								+ cod.getValorTexto();
					} else {
						condicionProcesada += " " + cod.getOperadorLogico() + " " + cod.getCampo() + " = "
								+ cod.getValor();
					}
				}

			}
		}
		return condicionProcesada;
	}

	/**
	 * Este metodo une los items de un array en una cadena, pero separado por una
	 * coma<br/>
	 * Y ademas verifica el tipo de dato de cada item, si el item no fuera numeric,
	 * entonces<br/>
	 * envuelve este item entre comiilas.
	 * 
	 * @param valores
	 * @param separador caracter por el cual seran separados los items puede ser (,
	 *                  . -)
	 * @return cadena
	 */
	public String unirCon(Object[] valores, String separador) {
		String cad = "";
		Number nb = null;
		if (valores != null) {
			if (valores.length > 0) {
				cad = "";
				if (valores.length == 1) {
					nb = esNumero(valores[0]);
					cad += (nb == null ? "'" + valores[0] + "'" : nb);
					return cad;
				}
				for (int i = 0; i < valores.length; i++) {
					nb = esNumero(valores[i]);
					cad += (i == 0 ? " " : separador) + (nb != null ? nb : "'" + valores[i] + "'");
				}
			}
		}
		return cad;
	}

	/**
	 * Verifica si un objetos es numerico
	 * 
	 * @param valor
	 * @return
	 */
	public Number esNumero(Object valor) {
		Number nb = null;
		if (valor instanceof Number) {
			nb = (Number) valor;
			return nb;
		} else {
			return null;
		}
	}

	/**
	 * Este metodo une los items de una array en una cadena, pero separado por una
	 * coma
	 * 
	 * @param campos array de cadenas
	 * @return cadena
	 */
	public String getStringFromArray(String[] campos) {
		String cad = "";
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

}
