package util;

import modelgui.ModeloTablaApertura;
import modelgui.ModeloTablaDetalleCompra;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class CeldaSpinnerEditor extends AbstractCellEditor implements TableCellEditor{

    private JSpinner spinner;    
    private Object valorInicial;
    private Object valorActual;
        

    public CeldaSpinnerEditor() {
        
        spinner = new JSpinner();
        spinner.setFont(new Font("Tahoma", 1, 14));
        spinner.setModel(new SpinnerNumberModel(0, 0, 500, 10));  
        
        ChangeListener listener = new ChangeListener() {
          
        public void stateChanged(ChangeEvent e) {           
            JSpinner temp = (JSpinner)e.getSource(); 
            Integer vi = (Integer)valorInicial;
            Integer nv = (Integer)temp.getValue();
                if(vi != null)
                {
                  int sd = -1;
                  if(nv < vi)
                    {
                        sd = JOptionPane.showConfirmDialog(null, "La cantidad Ingresada es menor "
                                                        + "que la cantidad actual\n"
                                                        + "¿Realmente desea realizar este Cambio?", "Valor inferior al actual",
                                                        JOptionPane.WARNING_MESSAGE); 
                        if(sd == JOptionPane.OK_OPTION)
                        {
                           valorActual = temp.getValue();  
                        }
                    }else{
                        valorActual = temp.getValue();                       
                    }
                }
            }
        };        
        spinner.addChangeListener(listener);       
    }
    
    
  
    public Object getCellEditorValue() {        
        return valorActual;
    }

   
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        valorActual = value;
        spinner.setValue(value);        
        if(valorInicial == null)
        {
            valorInicial = ((ModeloTablaApertura)table.getModel()).getValueAt(row, column);
            
        }
        return spinner;
    }

    
    
}
