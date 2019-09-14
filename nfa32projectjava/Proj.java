

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Proj implements Application{
	static Memoire mem = new Memoire();
	Map<String, CaseAffiche> data = new HashMap<>();
	int x=0;
	IGTableur inter;
	int code = 65;  	 //  le unicode de 'A'
	public Proj() {
		for (int i = 0; i < 10; i ++) {    // boucle pour creer les objets  CaseVide  et les cles de HashMap des references
			code = 65 + i;
			for (int j = 0; j < 10; j ++) {
				char codeToChar = (char) code;
				String cle = codeToChar + Integer.toString(j);
				mem.setValue(cle , "");
				data.put(cle , new CaseVide(cle, ""));
			}
		}

	}

	public String getContenu(char col, int lig){
		System.out.println("getContenu "+col+lig);
		return data.get(creeRef(col,lig)).toString();
	}		

	public void setInterface(IGTableur i){
		inter=i;
	}
	public void setContenu(char col, int lig, String s) throws ErreurFormule{
		System.out.println("setContenu "+col+lig+ ": " + s);
		if (s.equals("")) {											// condition de creation d' une Case vide			
			data.put(creeRef(col, lig), new CaseVide(creeRef(col, lig), ""));
			mem.setValue(creeRef(col,lig), s);
		} else if (estString(s)) {                          // condition de creation des CaseAlpha
			data.put(creeRef(col, lig) , new CaseAlpha(creeRef(col, lig), s));
			mem.setValue(creeRef(col,lig), s);
		} else if (estNumerique(s)) {											// condition de creation des CaseNum
			data.put(creeRef(col, lig), new CaseNum(creeRef(col, lig) , s));
			mem.setValue(creeRef(col,lig), s);
		} else if (estFormule(s)) {										// condition de creation des CaseForm
			ArrayList<String> tab = analyseParenthese(analyseFormule(s)); 
			if (estNumerique(tab.get(1)) && estNumerique(tab.get(3))) {      //   tout les deux sont numeriques
				CaseVar c1 = null;
				CaseVar c2 = null;
				data.put(creeRef(col ,lig), new CaseForm(creeRef(col ,lig) , s , c1, c2));
				mem.setValue(creeRef(col,lig), data.get(creeRef(col,lig)).calcule(mem));
			} else if (!estNumerique(tab.get(1)) && !estNumerique(tab.get(3))) {
				checkRefVide(tab.get(1));
				checkRefVide(tab.get(3));
				estRefCorrecte(creeRef(col,lig) ,  tab.get(1));
				estRefCorrecte(creeRef(col,lig) ,  tab.get(3));
				CaseVar c1 = new CaseVar(tab.get(1) , data.get(tab.get(1)).toString());
				CaseVar c2 = new CaseVar(tab.get(3) , data.get(tab.get(3)).toString()); 
				data.put(creeRef(col ,lig), new CaseForm(creeRef(col ,lig) , s, c1, c2 ));
				mem.setValue(creeRef(col,lig), data.get(creeRef(col,lig)).calcule(mem));
			} else if (estNumerique(tab.get(1)) && !estNumerique(tab.get(3))) {
				checkRefVide(tab.get(3));
				CaseVar c1 = null;
				CaseVar c2 = new CaseVar(tab.get(3) , data.get(tab.get(3)).toString());
				data.put(creeRef(col ,lig), new CaseForm(creeRef(col ,lig) , s, c1, c2 ));
				mem.setValue(creeRef(col,lig), data.get(creeRef(col,lig)).calcule(mem));
			} else {
				checkRefVide(tab.get(1));
				CaseVar c1 = new CaseVar(tab.get(1) , data.get(tab.get(1)).toString());
				CaseVar c2 = null;
				data.put(creeRef(col ,lig), new CaseForm(creeRef(col ,lig) , s, c1, c2 ));
				mem.setValue(creeRef(col,lig), data.get(creeRef(col,lig)).calcule(mem));
			}
		}  else {      						 // pas une valeur reconnue
			throw new ErreurFormule("pas coRRECTE");
		}
		
	
			
		for (int i = 0; i < 10; i ++) {	
			for (int j = 0; j < 10; j ++) {
				CaseAffiche  c = data.get(creeRef(numToCol(i), j));
				mem.setValue(creeRef(numToCol(i), j), c.calcule(mem));
				inter.modifieCellule(numToCol(i), j, mem.getValue(creeRef(numToCol(i), j)));	
			}
		}


	}
	
	

	/**
	 * tester si le @param est chaine de characters
	 * 
	 * @param valeur -chaine de characters à tester
	 * @return  true si le premier et le dernier character egals "  et false si non. 
	 */
	public static boolean estString(String valeur) {

		if ( valeur.charAt(0) == '"' && valeur.charAt(valeur.length()-1) == '"') {
			return true;
		}
		return false;
	}


	/**
	 * tester si le @param est numerique
	 *
	 * @param valeur -chaine de characters à tester
	 * @return true si  le valeur de parametre est numerique  Et false si non
	 */
	public static boolean estNumerique(String valeur){  
		try {  
			double d = Double.parseDouble(valeur);  
		}  
		catch(NumberFormatException e) {  
			return false;  
		}  
		return true;  
	}


	/**
	 * tester si le @param commance par =
	 * 
	 * @param valeur  -chaine de characters à tester
	 * @return true si le premier character egals =  et false si non. 
	 * @throws IOException 
	 * @throws ErreurFormule 
	 */
	public static boolean estFormule(String valeur) throws ErreurFormule {
		ArrayList<String> tab = analyseFormule(valeur);
		if (formCheckDehorsParenthese(tab) && formCheckDansParenthese(tab)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * methode d'analyse de donnees 
	 * 
	 * @param st :est le string qu'on entre
	 * @return un Arraylist avec toutes les elements de st
	 */
	public static ArrayList<String> analyseFormule(String st) {
		StreamTokenizer s= new StreamTokenizer(new StringReader(st));
		ArrayList<String>  pin = new ArrayList<>();
		try {
			s.nextToken();
		} catch (IOException e) {
		}
		while(s.ttype != StreamTokenizer.TT_EOF) {
			switch (s.ttype) {
			case StreamTokenizer.TT_NUMBER: 
				pin.add(String.valueOf(s.nval));
				break;
			case StreamTokenizer.TT_WORD:
				pin.add(s.sval);
				break;
			default:
				pin.add(Character.toString((char) s.ttype));
			}
		try {
			s.nextToken();
		} catch (IOException e) {
		}
		}
		return pin;
	}
	
	
	/**
	 * methode pour  faire le test de formule dehors a parenthese 
	 * 
	 * @param op ArrayList des  donnees analyses
	 * @return true si la formule dehors la parenthese est correcte
	 * @throws ErreurFormule si la formule dehors la parenthese n'est pas correcte
	 */
	public static boolean formCheckDehorsParenthese(ArrayList<String> op) throws ErreurFormule {
		if (op.size() >= 7 ) {
			if ((op.get(1).equals("PLUS") || op.get(1).equals("FOIS") || op.get(1).equals("MOINS") || op.get(1).equals("DIV"))
					&& op.get(0).equals("=")
					) {
			return true;
			}
		}
		throw new ErreurFormule("formule pas correcte...." + op.get(0) + op.get(1));
	}
	
	
	/**
	 * Analyse des donnes dans la parenthese
	 * 
	 * @param op est un ArrayList avec les elements des donnees
	 * @return true si toutes les donnees dans la parenthese sont correctes
	 * @throws ErreurFormule si la/les reference(s) sont pas numeriques ou vides  
	 */
	public static boolean formCheckDansParenthese(ArrayList<String> op) throws ErreurFormule {
		int dernierDigi = op.size()-1;
		if (op.get(2).equals("(") 
				&& op.get(4).equals(";") 
				&& op.get(dernierDigi).equals(")")
				&& estRef(op.get(3)) || estNumerique(op.get(3))
				&& estRef(op.get(5)) || estNumerique(op.get(5))
			) {
			if (estRef(op.get(3)) && !estNumerique(mem.getValue(op.get(3))))  {
				throw new ErreurFormule("reference " + op.get(3) + " pas numerique ou vide");
			}
			if (estRef(op.get(5)) && !estNumerique(mem.getValue(op.get(5))))  {
				throw new ErreurFormule("reference " + op.get(5) + " pas numerique ou vide");
			}
			return true;
		} 
		return false;
	}
	
	
	/**
	 * methode qui renvoie l'operation donnee
	 * @param valeur est une chaine de characters donnee
	 * @return une chaine de characters de l' operation 
	 */
	public static String quelOperation(String valeur){
		ArrayList<String> op = analyseFormule(valeur);
		if (op.get(1).equals("PLUS")) {
			return "PLUS";
		} else if (op.get(1).equals("FOIS")) {
			return "FOIS"; 
		} else if (op.get(1).equals("MOINS")) {
			return "MOINS";
		} else {
			return "DIV";
		}
	}
	
	
	
	



	/**
	 * methode pour accepter ou pas l'entre des donnees si on a pas choisi une case aussous a la coloumn
	 * ou une case a droit a la ligne 
	 * @param caseActuel une chaine de character des coordonees de notre case actuele
	 * @param caseRef   une chaine de character des coordonees de la case qu on va fait reference
	 * @return true si la case qu on fait reference est audesous ou a droit 
	 * @throws ErreurFormule  si la case qu on fait reference est audesus ou a gauche
	 */
	public static boolean estRefCorrecte(String caseActuel ,  String caseRef) throws ErreurFormule  {
		char c1 = caseActuel.charAt(0);
		char c2 = caseRef.charAt(0);
		int i1 = Character.getNumericValue(caseActuel.charAt(1));
		int i2 = Character.getNumericValue(caseRef.charAt(1));
		if (c1 > c2) {	
			return true;
		} else if ( (c1 < c2) 
					&& (i1 > i2)) {
			return true;
			} 
		else {
			throw new ErreurFormule(" ref il faut etre audessus ou a gauche de la place actuelle");
		}
	}
	
	/**
	 * methode pour fire le test si la reference est correcte ou pas
	 * @param valeur est une chaine de characters donnee
	 * @return true si (A..J) et (0..9) false sinon
	 */
	public static boolean estRef(String valeur)   {
		
		if (valeur.length() == 1 || valeur.length() > 2) {
			return false;
		}  else if ((Character.getNumericValue(valeur.charAt(0)) > 9 && Character.getNumericValue(valeur.charAt(0)) < 20)
				 &&
				 (Character.getNumericValue(valeur.charAt(1)) > -1 && Character.getNumericValue(valeur.charAt(1)) <= 9)
				) {
				return true;
			}
		
		return false;
		
	}
	
	/**
	 * methode pour feire le test si le case demande est vide ou pas
	 * @param s les coordonees de la case qu'on va tester
	 * @return  true si la case testee n'ete pas vide
	 * @throws ErreurFormule si la case testee ete  vide
	 */
	public  boolean checkRefVide(String s) throws ErreurFormule {
		if (mem.getValue(s).equals("")) {
			throw new ErreurFormule(s + " est vide");
		}
		return true;
	}


	/**
	 * renvoie le contenu de la parenthese
	 * @param tab la formule analysé 
	 * @return la formule sans '=' et l'operation
	 */
	public static ArrayList<String> analyseParenthese(ArrayList<String> tab)  {
		ArrayList<String> tabNew = new ArrayList<>(tab);
		tabNew.remove(0);
		tabNew.remove(0);
		return tabNew;
	}


	/**
	 * methode pour change le nombre vers un char qui intique une colonne 
	 * @param y un integer
	 * @return un char de colonne
	 */
	public static char numToCol(int y) {

		switch (y) {

		case 0 :
			return 'A';
		case 1 :
			return 'B';
		case 2 :
			return 'C';
		case 3 :
			return 'D';
		case 4 :
			return 'E';
		case 5 :
			return 'F';
		case 6 :
			return 'G';
		case 7 :
			return 'H';
		case 8 :
			return 'I';
		case 9 :
			return 'J';
		default :
			return 'X';
		}
	}
	
	/**
	 * cree un Reference a pertir d un integer et un char
	 * @param col  un char A..J
	 * @param lig  un integer 0..9
	 * @return  une chaine des characters de type A9 qui est une reference
	 */
	public static String creeRef(char col , int lig) {
		String ref = col + Integer.toString(lig);
		return ref;
	}



	public static void main(String[] args){
		Proj dtab = new Proj();
		IGTableur igt=new IGTableur(dtab);
		dtab.setInterface(igt);
		System.out.println("tapez entree");
		Terminal.lireString();
		igt.fermer();
	}
}
