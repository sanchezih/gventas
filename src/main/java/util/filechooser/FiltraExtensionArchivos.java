package util.filechooser;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class FiltraExtensionArchivos extends FileFilter{
    
    private String description = "Archivos Permitidos";
    private String[] extensiones;
    private String[] extImg = {"jpg","jpeg","png","gif"};

    public FiltraExtensionArchivos(String extension) {
        this(new String[]{extension});
    }

    
    public FiltraExtensionArchivos(String[] extensiones) {
        this.extensiones = extensiones;
        generarDescripcion();
    }
    
    public FiltraExtensionArchivos(String description,String[] extensiones) {
        this.description = description;
        this.extensiones = extensiones;
        generarDescripcion();
    }

    public FiltraExtensionArchivos() {
        this.description = "Imagenes Permitidas";
        this.extensiones = extImg;
        generarDescripcion();
    }
    
    
    
    private void generarDescripcion()
    {
        for(int i=0;i<extensiones.length;i++)
        {
           description +=", " + extensiones[i];
        }
    }
    

    @Override
    public boolean accept(File f) {
        if(f.isDirectory())
        {
            return true;
        }else{
            for(int i=0;i<extensiones.length;i++)
            {
                if(f.getName().endsWith(extensiones[i]))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return description;
    }
    
}
