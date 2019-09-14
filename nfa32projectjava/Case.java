

public class Case {
	String valeur;
	String place;

	
	public Case(String place, String valeur) {
		this.place = place;
		this.valeur  = valeur;
	}
	
	public String getValeur() {
		return valeur;
	}
	
	public void setValeur(String s) {
		this.valeur = s;
	}
	
	public String getplace() {
		
		return place;
		
	}
	
	@Override
	public String toString() {
		return String.format("s", getValeur());
	}
	
}
