/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import ventanas.paneles.PanelAccion;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class CeldaAccionEditor extends AbstractCellEditor implements TableCellEditor{

    private PanelAccion pa;
    public CeldaAccionEditor(JInternalFrame ifr) {
        pa = new PanelAccion(ifr);
        pa.setCeldaEditor(this);
        
    }

   
    public Object getCellEditorValue() {
        return "";
    }

  
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        pa.setTabla(table);
        pa.setIndexFila(row);        
        return pa;
    }
    
    public void lanzarDetencionEdicion()
    {
        this.fireEditingStopped();
    }
    
}
