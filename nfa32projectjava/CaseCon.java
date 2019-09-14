
public class CaseCon extends CaseVar{

	public CaseCon(String place, String valeur) {
		super(place, valeur);
	}
	
	public String toString() {
		return String.format("%s", getValeur());
	}
}
