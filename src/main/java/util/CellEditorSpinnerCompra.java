package util;

import modelbd.DetalleCompra;
import modelgui.ModeloTablaDetalleCompra;
import ventanas.ICompra;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class CellEditorSpinnerCompra extends AbstractCellEditor implements TableCellEditor{

    private JSpinner spinner;
    private JTable tbl;
    private Object valorInicial;
    private Object valorActual;
    private ICompra compra;

    
    private int fila;
    private int columna;
    public CellEditorSpinnerCompra(int sizeDes,ICompra compra) {
        this.compra = compra;
        spinner = new JSpinner();
        spinner.setFont(new Font("Tahoma", 1, 14));
        spinner.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        spinner.setModel(new SpinnerNumberModel(0, 0, 500, sizeDes));  
        ChangeListener listener = new ChangeListener() {
         
        public void stateChanged(ChangeEvent e) {             
            JSpinner temp = (JSpinner)e.getSource();
            ((DefaultEditor)temp.getEditor()).getTextField().setHorizontalAlignment(JTextField.RIGHT);
            Integer vi = (Integer)valorInicial;
            Integer nv = (Integer)temp.getValue();
                if(vi != null)
                {
                   int sd = -1;
                    valorActual = temp.getValue();
                    if(tbl != null)
                    {
                       ModeloTablaDetalleCompra mt =((ModeloTablaDetalleCompra)tbl.getModel());
                       //System.out.println("cambioooooooooo"+valorActual); 
                       Double pr = ((DetalleCompra)mt.getFila(fila)).getIdProducto().getCosto();
                       mt.setValueAt(getCompra(),Double.parseDouble(valorActual.toString())*pr, getFila(), 4);                       
                       mt.contarItems();
                    }
                    
                }
                ((DefaultEditor)temp.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
                fireEditingStopped();
            }            
            
        };        
        
        spinner.addChangeListener(listener);
       
    }
    
    
  
    public Object getCellEditorValue() {
        return valorActual;
    }

  
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        tbl = table;
        fila = row;
        columna = column;
        valorActual = value;
        spinner.setValue(value);        
        if(valorInicial == null)
        {
            valorInicial = ((ModeloTablaDetalleCompra)table.getModel()).getValueAt(row, column);
        }        
        return spinner;
    }
    
    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public ICompra getCompra() {
        return compra;
    }

    public JSpinner getSpinner() {
        return spinner;
    }
    
    
    
}
