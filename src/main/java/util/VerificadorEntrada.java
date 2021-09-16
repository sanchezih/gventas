package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class VerificadorEntrada extends InputVerifier{
    
    public final static int EMAIL  = 1;
    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN = 
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";   
    
    private int longitudCadena = 0;
    private boolean esObligatorio = false;
    private int tipoValidacion;
    
    public VerificadorEntrada() {
        tipoValidacion = EMAIL;
    }
    public VerificadorEntrada(boolean esObligatorio) {
        tipoValidacion = EMAIL;
        this.esObligatorio = esObligatorio;
    }

    public VerificadorEntrada(int tipoValidacion) {
        this.tipoValidacion = tipoValidacion;
    }    

    public VerificadorEntrada(int longitudCadena, int tipoValidacion) {
        this.longitudCadena = longitudCadena;
        this.tipoValidacion = tipoValidacion;
    }

    @Override
    public boolean verify(JComponent input) {
        JTextComponent cmp = (JTextComponent)input;
        String texto = cmp.getText();
        if(!esObligatorio && texto.isEmpty())
        {
            return true;
        }
        if(!texto.isEmpty())
        {
            switch(tipoValidacion)
            {
               case EMAIL:
                   if(longitudCadena == 0 || texto.length()<longitudCadena)
                   {
                       if(esValido(texto)&&this.esObligatorio)
                       {
                           JOptionPane.showMessageDialog(cmp.getParent(), "Este campo es Obligatorio");
                           return true;
                       }else{
                           if(!esValido(texto))
                           {
                             cmp.setText("");
                             JOptionPane.showMessageDialog(cmp.getParent(), "Email no Valido");
                           }
                            if(!esObligatorio)
                           {
                               return true;
                           }
                       }
                   }
            }
        }
        
        
        return false;
    }
    
    private boolean esValido(String string)
    {
        switch(tipoValidacion)
        {
            case EMAIL:
                pattern = Pattern.compile(EMAIL_PATTERN);
                matcher = pattern.matcher(string);
		return matcher.matches();
            default:
                return false;
        }
    }
    
    
}
