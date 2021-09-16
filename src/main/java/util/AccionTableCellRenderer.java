/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import ventanas.paneles.PanelAccion;
import java.awt.Component;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class AccionTableCellRenderer implements TableCellRenderer {
	private PanelAccion test;

	public AccionTableCellRenderer(final JInternalFrame ifr) {
		test = new PanelAccion(ifr);
	}

	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		test.setTabla(table);
		return test;
	}

}
