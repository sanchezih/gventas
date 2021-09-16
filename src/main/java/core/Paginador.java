package core;

import managerbd.BaseConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Código Lite
 */
public class Paginador {
	private int itemsPorPagina = 10;
	private int totalItems = 0;
	private int cantidadesDePaginas = 0;
	private int IndexpaginaActual = 0;
	private int desplazamiento = 0;
	private int limite = itemsPorPagina;
	private CriterioSQL criterio;
	private List<JAbstractModelBD> item;
	private JAbstractModelBD tipoObjeto;
	private boolean ultimo = false;

	public Paginador(CriterioSQL criterio, JAbstractModelBD tipoObjeto) {
		this.criterio = criterio;
		this.tipoObjeto = tipoObjeto;
	}

	public Paginador(JAbstractModelBD tipoObjeto) {
		criterio = new CriterioSQL();
		criterio.setTabla(tipoObjeto.getNombreTabla());
		criterio.setLimit(limite);
		criterio.setDesplazamientos(desplazamiento);
		totalItems = tipoObjeto.contar();
		item = new ArrayList();
		this.tipoObjeto = tipoObjeto;
	}

	public Paginador(CriterioSQL criterio) {
		this.criterio = criterio;
		item = new ArrayList();
		totalItems = tipoObjeto.contar();
	}

	public Paginador() {
		this.criterio = new CriterioSQL();
		item = new ArrayList();
	}

	public List<JAbstractModelBD> siguiente() {
		// primero verificar que item no sea nulo o este vacio
		int tmpContador = desplazamiento + limite;
		if (tmpContador <= this.totalItems)
			criterio.setDesplazamientos(desplazamiento += limite);
		else
			ultimo = true;
		IndexpaginaActual++;
		System.out.println("Index actual= " + IndexpaginaActual);
		System.out.println("Total paginas= " + this.cantidadesDePaginas);
		return cargarRegistros();
	}

	public boolean isUltimo() {
		return ultimo;
	}

	private List<JAbstractModelBD> cargarRegistros() {
		item = new ArrayList();
		try {
			ResultSet rs = BaseConexion.getResultSet(criterio.getConsultaSQL());
			while (rs.next()) {
				this.tipoObjeto = tipoObjeto.llenarObjeto(rs);
				item.add(tipoObjeto);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return item;
	}

	public List<JAbstractModelBD> anterior() { // primero verificar que item no sea nulo o este vacio

		if (desplazamiento > 0) {
			criterio.setDesplazamientos(desplazamiento -= limite);
		}
		IndexpaginaActual--;
		if (IndexpaginaActual < 0)
			IndexpaginaActual = 0;
		return cargarRegistros();
	}

	public JAbstractModelBD getTipoObjeto() {
		return tipoObjeto;
	}

	public void setTipoObjeto(JAbstractModelBD tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	public CriterioSQL getCriterio() {
		return criterio;
	}

	public void setCriterio(CriterioSQL criterio) {
		this.criterio = criterio;
	}

	public List<JAbstractModelBD> getItem() {

		criterio.setTabla(tipoObjeto.getNombreTabla());
		if (totalItems == 0) {
			CriterioSQL criteria = new CriterioSQL();
			criteria.setCondicion(criterio.getCondicion());
			totalItems = tipoObjeto.contar(criteria);
		}

		if (cantidadesDePaginas == 0)
			cantidadesDePaginas = this.getTotalItems() / this.limite;
		if (item.isEmpty()) {
			try {
				ResultSet rs = BaseConexion.getResultSet(criterio.getConsultaSQL());
				while (rs.next()) {
					this.tipoObjeto = tipoObjeto.llenarObjeto(rs);
					item.add(this.tipoObjeto);
				}
				rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return item;
	}

	public int getItemsPorPagina() {
		return itemsPorPagina;
	}

	public void setItemsPorPagina(int itemsPorPagina) {
		this.itemsPorPagina = itemsPorPagina;
	}

	public int getTotalItems() {
		if (totalItems == 0)
			totalItems = tipoObjeto.contar();
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public int getCantidadesDePaginas() {
		cantidadesDePaginas = this.getTotalItems() / this.limite;
		return cantidadesDePaginas;
	}

	public void setCantidadesDePaginas(int cantidadesDePaginas) {
		this.cantidadesDePaginas = cantidadesDePaginas;
	}

	public int getIndexpaginaActual() {
		return IndexpaginaActual;
	}

	public void setIndexpaginaActual(int IndexpaginaActual) {
		this.IndexpaginaActual = IndexpaginaActual;
	}

	public int getDesplazamiento() {
		return desplazamiento;
	}

	public void setDesplazamiento(int desplazamiento) {
		this.desplazamiento = desplazamiento;
		this.criterio.setDesplazamientos(this.desplazamiento);
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
		this.criterio.setLimit(this.limite);
	}
}
