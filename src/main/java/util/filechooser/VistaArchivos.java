package util.filechooser;

import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileView;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class VistaArchivos extends FileView{
//    private final Icon iconoHtml = new ImageIcon(this.getClass().getResource("html.png"));
    private final Icon iconoImagen = new ImageIcon(this.getClass().getResource("/resources/picture.png"));
//    private final Icon iconoCodigo = new ImageIcon(this.getClass().getResource("page_code.png"));
//    private final Icon iconoScript = new ImageIcon(this.getClass().getResource("script.png"));
//    private final Icon iconoVector = new ImageIcon(this.getClass().getResource("vector.png"));

    @Override
    public String getDescription(File f) {
        String descripcion= "";
        String nombreArchivo = f.getName().toLowerCase();
        if(f.isDirectory())
        {
            return "Directorio";
        }else
        {
            if(nombreArchivo.endsWith("jpg") || nombreArchivo.endsWith("jpeg") ||
                    nombreArchivo.endsWith("png") || nombreArchivo.endsWith("gif"))
            {
                descripcion = "Imagen";
            }
            
            return descripcion;
        }        
    }

    @Override
    public Icon getIcon(File f) {
        
        if(f.isDirectory())
            return null;
        else{
            
            Icon icono = null;
            String nombreArchivo = f.getName().toLowerCase();
          if(nombreArchivo.endsWith("jpg") || nombreArchivo.endsWith("jpeg") ||
             nombreArchivo.endsWith("png") || nombreArchivo.endsWith("gif"))
            {
                icono = this.iconoImagen;
            }
            
            return icono;
        }
        
    }

    
//    public String getName(File f) {
//        
//        if(f.isDirectory())
//        {
//            return f.getName()+" : es un directorio";
//        }else
//        {
//            return f.getName()+" : es un archivo";
//        }        
//    }
    
    
    
}
