

public class CaseAlpha extends Case implements CaseAffiche{
	
	public CaseAlpha(String place, String valeur) {
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
