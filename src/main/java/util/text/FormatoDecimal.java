package util.text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author Código Lite <https://codigolite.com>
 */
public class FormatoDecimal extends DecimalFormat {

	private boolean invertirSimbolos = false;
	private DecimalFormatSymbols dfs;

	public FormatoDecimal(String pattern, boolean invertirSimbolos) {
		this.invertirSimbolos = invertirSimbolos;
		if (this.invertirSimbolos) {
			dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			dfs.setGroupingSeparator(',');
			this.setDecimalFormatSymbols(dfs);
		} else {
			dfs = new DecimalFormatSymbols(Locale.getDefault());
			this.setDecimalFormatSymbols(dfs);
		}
		this.applyPattern(pattern);
	}
}
