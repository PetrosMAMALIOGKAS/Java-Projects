

public class CaseVide extends Case implements CaseAffiche {
	
	public CaseVide(String place, String valeur) {
		super(place, valeur);
		
	}
	
	@Override
	public String toString() {
		return String.format("%s", getValeur());
	}
	
	

	@Override
	public String calcule(Memoire m) {
		return m.getValue(place);
	}

}
