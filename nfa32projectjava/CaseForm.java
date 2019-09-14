import java.util.ArrayList;



public class CaseForm extends CaseVar implements CaseAffiche {
	CaseAffiche c1,c2;

	
	public CaseForm(String place , String valeur, CaseVar c1, CaseVar c2) {  // tout  les deux sont references
		super(place, valeur);
		this.c1 = c1;
		this.c2 = c2;
	}
	
	public CaseForm(String place , String valeur) {    //  two les deux sont numerique
		super(place, valeur);
	
	}
	
	private ArrayList<String> getNombres(Memoire m) {
		ArrayList<String> tab = new ArrayList<>();
		ArrayList<String> tab1 = new ArrayList<>();
		tab1 = Proj.analyseParenthese(Proj.analyseFormule(valeur));
		
		if (c1 == null && c2 == null) {
			 tab.add(0, tab1.get(1));
			 tab.add(1, tab1.get(3));
		} else if (c1 != null && c2 != null) {
			tab.add(0, c1.calcule(m));
			tab.add(1, c2.calcule(m));
		} else if (c1 == null) {
			tab.add(0, tab1.get(1));
			tab.add(1, c2.calcule(m));
		} else {
			tab.add(0, c1.calcule(m));
			tab.add(1, tab1.get(3));
		}
		
		return tab;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s", getValeur());
	}
	
	


	@Override
	public String calcule(Memoire m) {
		String s = Proj.quelOperation(getValeur());
		ArrayList<String> tab = getNombres(m);
		double n1 = Double.parseDouble(tab.get(0));
		double n2 = Double.parseDouble(tab.get(1));
		double tot = 0;
		if (s.equals("PLUS")) {
			tot = n1 + n2;
		} else if (s.equals("MOINS")) {
			tot = n1 - n2;
		} else if (s.equals("FOIS")) {
			tot = n1 * n2;
		} else {
			tot = n1 / n2;
		}
		String t = String.valueOf(tot);
		return t;
	}	
}
