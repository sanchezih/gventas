package modelgui;

import controllers.CAlmacen;
import modelbd.Almacen;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author Ferz
 */
public class ListModelAlmacen extends DefaultListModel{

    private CAlmacen ca;
    public ListModelAlmacen(boolean todos) {
        ca = new CAlmacen();
        ArrayList<Almacen> almacenes = ca.getRegistros(todos?new Integer[]{1}:null);
        
        for(Almacen al:almacenes)
        {
            this.addElement(al);
        }
    }
}
