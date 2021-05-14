package my.vaadin.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.jooq.types.UInteger;

import com.vaadin.ui.TextField;

public class Person implements Cloneable {
    private UInteger id;
    private String Nome;
    private String Cognome;
    private Date dataNasc;
    private List<String> resultTest=new ArrayList<>();

    public UInteger getId() {
        return id;
    }

    public void setId(UInteger id) {
        this.id = id;
    }

    public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getCognome() {
		return Cognome;
	}

	public void setCognome(String cognome) {
		Cognome = cognome;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date birthDate) {
		this.dataNasc = birthDate;
	}
	
	public List<String> getResultTest() {
		return resultTest;
	}

	public void setResultTest(List<String> resultTest) {
		this.resultTest = resultTest;
	}
   
    @Override
    public int hashCode() {
        return id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Person)) {
            return false;
        }
        Person other = (Person) obj;
        return id == other.id;
    }

   public Person(String nome, String cognome, Date birthDate,UInteger id) {
        super();
        this.id = id;
        this.Nome = nome;
        this.Cognome = cognome;
        this.dataNasc = birthDate;
        this.resultTest.add("Test non effettuato");
    }
   public Person(String nome, String cognome, Date birthDate,UInteger id,List<String> risultato) {
       super();
       this.id = id;
       this.Nome = nome;
       this.Cognome = cognome;
       this.dataNasc = birthDate;
       this.resultTest=risultato;
   }

    @Override
    public String toString() {
        return Nome+" "+Cognome;
    }

    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(
                    "The Person object could not be cloned.", e);
        }
    }

	
}

class controllo{
	
	controllo(){
		
	}
public String ControlloIntero(String valore,boolean intero) {
	try {
		Double.parseDouble(valore);
	}catch(Exception e) {
		return "Non è un numero";
	}
	String verificato="";
	if((valore.contains(".")||valore.contains(","))&&intero) {
		int d=new BigDecimal(valore).setScale(0, RoundingMode.HALF_EVEN).intValue();
		verificato=String.valueOf(d);
		
	}else if((valore.contains(".")||valore.contains(","))&&!intero){
		Double c=Double.valueOf(valore);
		double d=new BigDecimal(c).setScale(1, RoundingMode.HALF_UP).doubleValue();
		verificato=String.valueOf(d);

	}else {
		
				verificato=valore;

	}
	return verificato;
	
}

public String ControlloIntero(TextField txDato,boolean intero,Integer condition) {
	String valore=txDato.getValue();
	try {
		Double.parseDouble(valore);
	}catch(Exception e) {
		return "Non è un numero";
	}
	String verificato="";
	if((valore.contains(".")||valore.contains(","))&&intero) {
		Integer d=new BigDecimal(valore).setScale(0, RoundingMode.HALF_EVEN).intValue();
		if(condition!=null) {
		if(d<condition) {
			txDato.setCaption("<h4><font color=#ff0000>Errore: il valore non può essere minore di "+condition+"</font></h4>");
			txDato.setEnabled(false);
		}else {
			txDato.setCaption(null);
		}
		}
			verificato=String.valueOf(d);
			txDato.setEnabled(true);
		
		
	}else if((valore.contains(".")||valore.contains(","))&&!intero){
		Double c=Double.valueOf(valore);
		Double d=new BigDecimal(c).setScale(1, RoundingMode.HALF_UP).doubleValue();
		if(condition!=null) {
			if(d<condition) {
				txDato.setCaption("<h4><font color=#ff0000>Errore: il valore non può essere minore di "+condition+"</font></h4>");
				txDato.setEnabled(false);
			}else {
				txDato.setCaption(null);
			}
		}
			verificato=String.valueOf(d);
			txDato.setEnabled(true);
			
	}else {
		if(condition!=null) {
			int d=Integer.parseInt(valore);
			if(d<condition) {
				txDato.setCaption("<h4><font color=#ff0000>Errore: il valore non può essere minore di "+condition+"</font></h4>");
				txDato.setEnabled(false);
			}else {
				txDato.setCaption(null);
			}
		}
				verificato=valore;
				
			
	}
	return verificato;
	
}
}