
public class CaseVar extends Case implements CaseAffiche {
	
	public CaseVar(String place, String valeur) {
		super(place, valeur);
	}


	@Override
	public String calcule(Memoire m) {
		return m.getValue(place);
	}


}
