

public class DemoIGS{
    public static void main(String[] args){
	IGTableurSimple igts = new IGTableurSimple();
	igts.modifieCellule('C',3,"Oui");
	igts.modifieCellule('C',4,"12.3589");
	Terminal.lireString();
	igts.modifieCellule('C',3,"Non");
	igts.modifieCellule('E',3,"6785");
	igts.modifieCellule('E',7,"6785.999");
    }
}
