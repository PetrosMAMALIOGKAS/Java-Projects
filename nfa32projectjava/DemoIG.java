

public class DemoIG implements Application{
    int x=0;
    IGTableur inter;
   
    public String getContenu(char col, int lig){
    	Terminal.ecrireStringln("getContenu "+col+lig);
    	if (col=='A')
    		return ""+lig;
    	return "label " +col + lig;
    }		
   
    public void setInterface(IGTableur i){
    	    inter=i;
    }
    
    public void setContenu(char col, int lig, String s) throws ErreurFormule{
    	Terminal.ecrireStringln("setContenu "+col+lig+ ": " + s);
    	
    	x++;
    	if (x==2){
    		throw new ErreurFormule("pas du tout pas du tout");
    	} else if (x==3){
    		inter.modifieCellule('A',5,"56ghgfjdf");
    		inter.modifieCellule('E',5,"toto");
    		inter.modifieCellule('F',5,"97");
    		inter.modifieCellule('F',6,"97.897");
    	}
  }
    
    public static void main(String[] args){
    	DemoIG dtab = new DemoIG();
    	IGTableur igt=new IGTableur(dtab);
    	dtab.setInterface(igt);
    	Terminal.ecrireStringln("tapez entree");
    	Terminal.lireString();
    	igt.fermer();
    }
}
