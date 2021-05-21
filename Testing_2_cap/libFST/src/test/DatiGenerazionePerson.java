package test;


import java.util.Calendar;
import java.util.TimeZone;
import java.util.Locale;
import java.util.GregorianCalendar;

/**
 * @author stefa
 *	dati generazione persona
 */
public class DatiGenerazionePerson {
	/**
	 * nome persona
	 */

	private String nome/*=""*/;
	
	/**
	 * sesso persona
	 */

	private String sesso/*=""*/;
	/**
	 * data nascita persona
	 */
	private Calendar dataNasc;
	/**
	 * data esame
	 */
	private transient Calendar dataEsame;

	public String getNome() {
		return nome;
	}

/**
 * @param nome
 */
public void setNome(final String nome) {
	this.nome=nome;
	
}
public String getSesso() {
	return sesso;
}

/**
 * @param sesso
 */
public void setSesso(final String sesso) {
	this.sesso = sesso;
}
	public Calendar getDataNasc() {
		return dataNasc;
	}

	/**
	* @param dn  the dataNasc to set
	*/
	public void setDataNascNC(final Calendar datanascita) {
		this.dataNasc=datanascita;
	}
	public Calendar getDataEsame() {
		return dataEsame;
	}

	public void setDataEsame(final Calendar dataEsame) {
		this.dataEsame = dataEsame;
	}

	/**
	* @param dn  the dataNasc to set
	*/
	public void setDataNasc(final Calendar datanascita) {
		final Calendar dateNow = Calendar.getInstance(TimeZone.getTimeZone(DatiGenerazione.TIMEZONE), Locale.ITALY);
		if (dateNow.after(datanascita)) { //oggetto non creato localmente
			this.dataNasc = datanascita;
		}
	}

	/**
	* @param dataNasc  the dataNasc to set in dd/mm/yyyy
	*/
	public void setDataNasc(final String dataNascString) {
		if (dataNascString.charAt(2) == '/' && dataNascString.charAt(5) == '/') {
			GregorianCalendar data = new GregorianCalendar();
			dataEsame = dataNasc;
			dataEsame.setTime(data.getTime());
			final char[] charDate = dataNascString.toCharArray();
			final String anno = "" + charDate[6] + charDate[7] + charDate[8] + charDate[9]; //stringhe vuote
			final String mese = "" + charDate[3] + charDate[4];
			final String giorno = "" + charDate[0] + charDate[1];
			if (Integer.parseInt(mese) <= 12 && Integer.parseInt(giorno) <= 31) {
				data = new GregorianCalendar(Integer.parseInt(anno), Integer.parseInt(mese) - 1,
						Integer.parseInt(giorno));
				data.setTimeZone(TimeZone.getTimeZone(DatiGenerazione.TIMEZONE));
				if (dataEsame.getTime().after(data.getTime())) { //chain call
					this.dataNasc.setTime(data.getTime());
				}
			}
		}
	}
}