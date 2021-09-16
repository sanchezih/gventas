package util;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class ECampos {    
        
    
    public final static int VERIFICAR=1;
    public final static int LIMPIAR=2;
    public final static int HABILITAR_DESABILITAR=3;
    public final static int HABILITAR_TODO = 4;
    public final static int HABILITAR_POR_NOMBRE = 5; 
    
    
    /**
     * metedo que buscas los botonoes que tiene contiene un contenedor y los habilias o desabihilitas.
     * @param cm contenedor (JPanel o JFrame)
     * @param habilitar boolean true para habilitar, false para lo contrario
     * @param excepcion componentes que seran ignorados: pasar null si no se desea que ningun objeto 
     * sea ignorado
     */
    public static void buscarBotones(Component cm,boolean habilitar,Component[] excepcion)
    {
        if(cm instanceof JPanel)
        {            
           habilitaBotones(((JPanel)cm).getComponents(),habilitar,excepcion); 
        }else{
            habilitaBotones(new Component[]{cm},habilitar,excepcion);
        }
    }
    
    private static void habilitaBotones(Component[] cmps,boolean habilitar,Component[] excepcion)
    {
        
        for(int i=0;i<cmps.length;i++)
        {
            if(cmps[i] instanceof JPanel)
            {            
               habilitaBotones(((JPanel)cmps[i]).getComponents(),habilitar,excepcion);
               continue;
            }
            if(excepcion !=null)
            {
                if(excepcion.length > 0)
                {
                    boolean iguales = false;
                    for(int j=0;j<excepcion.length;j++)
                    {
                        if(cmps[i].equals(excepcion[j]))
                        {
                            iguales = true;
                            break;
                        }
                    }
                    
                    if(iguales)
                    {
                        continue;
                    }
                }
            }
           if(cmps[i] instanceof AbstractButton)
            {
               cmps[i].setEnabled(habilitar);
               if(cmps[i] instanceof JToggleButton)
               {
                   JToggleButton tmp = (JToggleButton)cmps[i];
                   tmp.setSelected(false);
               }
            } 
        }
    }
    
    /**
     * 
     * @param cm componente a poser editable.
     * @param habilitar opcion para habilitar
     * @param excepcion componente que se ignoraran
     * @param limpiar opcion limpiar. true para limpar los campos detexto
     * @param valor valor para establecer cuando se limpie los campso de texto
     */
    public static void setEditableTexto(Component cm,boolean habilitar,Component[] excepcion,boolean limpiar,String valor)
    {
        if(cm instanceof JPanel)
        {            
           habilitarTexto(((JPanel)cm).getComponents(),habilitar,excepcion,limpiar,valor); 
        }else if(cm instanceof JScrollPane){
           habilitarTexto(((JScrollPane)cm).getComponents(),habilitar,excepcion,limpiar,valor);  
        }else if(cm instanceof JViewport){
            habilitarTexto(((JViewport)cm).getComponents(),habilitar,excepcion,limpiar,valor);  
        }        
        else{
            habilitarTexto(new Component[]{cm},habilitar,excepcion,limpiar,valor);
        }
    }
    
    private static void habilitarTexto(Component[] cmps,boolean habilitar,Component[] excepcion,boolean limpiar,String valor)
    {
        for(int i=0;i<cmps.length;i++)
        {
            if(cmps[i] instanceof JDateChooser)
            {
                continue;
            }
            if(cmps[i] instanceof JPanel || cmps[i] instanceof JScrollPane || cmps[i] instanceof JViewport)
            {            
               setEditableTexto(cmps[i],habilitar,excepcion,limpiar,valor);
               continue;
            }
            if(excepcion !=null)
            {
                if(excepcion.length > 0)
                {
                    boolean iguales = false;
                    for(int j=0;j<excepcion.length;j++)
                    {
                        if(cmps[i].equals(excepcion[j]))
                        {
                            iguales = true;
                            break;
                        }
                    }
                    
                    if(iguales)
                    {
                        continue;
                    }
                }
            }
           if(cmps[i] instanceof JTextComponent)
            {
                JTextComponent tmp = (JTextComponent)cmps[i];
                tmp.setEditable(habilitar);
                if(limpiar)
                {
                    if(cmps[i] instanceof JFormattedTextField)
                    {
                        ((JFormattedTextField)cmps[i]).setValue(0);
                        if(cmps[i].getName() != null)
                        {
                            if(cmps[i].getName().equals("igv"))
                            {
                              ((JFormattedTextField)cmps[i]).setValue(18);  
                            }
                        }
                    }else if(cmps[i] instanceof JTextField)
                    {
                        tmp.setText(valor);
                    }
                }
            } 
        }
    }
    
    /**
     * 
     * @param cm contenedor
     * @param opcion marcar componentes encontrados
     * @return 
     */
    public static boolean esObligatorio(Component cm,boolean opcion)
    {
        if(cm instanceof JPanel)
        {            
           return marcarCamposObligatorios(((JPanel)cm).getComponents(),opcion); 
        }else if(cm instanceof JScrollPane){
           return marcarCamposObligatorios(((JScrollPane)cm).getComponents(),opcion);  
        }else if(cm instanceof JViewport){
            return marcarCamposObligatorios(((JViewport)cm).getComponents(),opcion);  
        }        
        else{
            return marcarCamposObligatorios(new Component[]{cm},opcion);
        }        
    }
    
    private static boolean  marcarCamposObligatorios(Component[] cm,boolean opcion)
    {
        boolean existen = false;
        for(int i=0;i<cm.length;i++)
        {
            if(cm[i] instanceof JDateChooser)
            {
                continue;
            }
            if(cm[i] instanceof JPanel || cm[i] instanceof JScrollPane || cm[i] instanceof JViewport)
            {            
               esObligatorio(cm[i],opcion);
               continue;
            }
            if(cm[i].getName() != null)
            {
                if(cm[i] instanceof JTextComponent)
                {
                    if(((JTextComponent)cm[i]).getText().isEmpty())
                    {
                        if(opcion)
                        {
                           cm[i].setBackground(Color.red);
                           cm[i].setForeground(Color.WHITE); 
                        }else{
                            cm[i].setBackground(Color.white);
                           cm[i].setForeground(Color.BLACK); 
                        }
                        
                        existen = true;
                    }else if(!((JTextComponent)cm[i]).getText().isEmpty())
                    {
                         cm[i].setBackground(Color.white);
                         cm[i].setForeground(Color.BLACK); 
                    }
                }else if(cm[i] instanceof JComboBox)
                {
                    if(((JComboBox)cm[i]).getSelectedIndex() == -1)
                    {
                       if(opcion)
                        {
                           cm[i].setBackground(Color.red);
                           cm[i].setForeground(Color.WHITE); 
                        }else{
                            cm[i].setBackground(Color.white);
                           cm[i].setForeground(Color.BLACK); 
                        }
                       existen = true;
                    }else if(((JComboBox)cm[i]).getSelectedIndex() != -1)
                    {
                        if(((JComboBox)cm[i]).getSelectedIndex() == 0)
                        {
                            if(opcion)
                        {
                           cm[i].setBackground(Color.red);
                           cm[i].setForeground(Color.WHITE); 
                        }else{
                            cm[i].setBackground(Color.white);
                           cm[i].setForeground(Color.BLACK); 
                        }
                            existen = true;
                        }
                    }else
                    {
                         cm[i].setBackground(Color.white);
                         cm[i].setForeground(Color.BLACK); 
                    }
                }
            }
        }
        
        return existen;
    }
            
}
