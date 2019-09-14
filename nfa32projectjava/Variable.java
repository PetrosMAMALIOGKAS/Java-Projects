

public class Variable implements Expression{
	String nom;
	
	public Variable(String nom) {
		this.nom = nom;
	}
	@Override
	public String calcule(Memoire m) {
		
		return m.getValue(nom);
	}

}
	