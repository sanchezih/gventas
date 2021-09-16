package modelgui;

import core.JAbstractController;
import core.JAbstractModelBD;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public abstract class ModeloTabla<M extends JAbstractModelBD> extends AbstractTableModel {
	protected ArrayList<M> registros;
	protected String[] nombreColumnas;
	protected JAbstractController cc;

	
	public int getRowCount() {
		return registros.size();
	}


	public int getColumnCount() {
		return nombreColumnas.length;
	}

	@Override
	public String getColumnName(int column) {
		return nombreColumnas[column];
	}

	public void borrarFila(int fila) {
		registros.remove(fila);
		cc.borrarRegistro(registros.get(fila));
		fireTableRowsDeleted(fila, fila);
	}

	public void quitarFila(int fila) {
		registros.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}

	public JAbstractModelBD getFila(int fila) {
		return registros.get(fila);
	}

}
