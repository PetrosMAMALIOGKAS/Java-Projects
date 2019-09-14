package minessweeper;
import java.util.Scanner;
import java.util.Random;


public class MinesSweeper {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int[][] directions = new int[][]{{-1,-1}, {-1, 0}, {-1, 1},  {0,-1}, {0, 1}, {1,-1}, {1, 0}, {1, 1}};
		System.out.println("compien de lignes...;");
		int ligne = input.nextInt();
		System.out.println("Compien de collumns....:");
		int collumn = input.nextInt();
		String[][] board = init(ligne,collumn);
		afficheBoard(board);
		System.out.print(board.length);
		boolean gagne = false, perdu = false;
		while (!gagne && !perdu) {
			System.out.println("quelle ligne...: ");
			int choixLigne = input.nextInt();
			System.out.println("quelle collumn...:");
			int choixCollumn = input.nextInt();
			if (board[choixLigne][choixCollumn].equals("*")) {
				System.out.println("BOOOOOMMM vous avez perdu...");
				System.out.println("merci pour jouer....");
				perdu = true;
			} else if (board[choixLigne][choixCollumn].equals("#") && checkChoix(board, choixLigne, choixCollumn) == 0) {
				board[choixLigne][choixCollumn] = "@";
				for (int i = 0; i < directions.length; i++) {
					try {
						int lig = choixLigne + directions[i][0];
						int col = choixCollumn + directions[i][1];
						board[lig][col] = String.valueOf(checkChoix(board, lig, col));
					} catch (ArrayIndexOutOfBoundsException e) {
						
					}  // catch
				}  // for
				
			} else {
				board[choixLigne][choixCollumn] = String.valueOf(checkChoix(board, choixLigne, choixCollumn));
			}
			
			System.out.println();
			afficheBoard(board);
			if (espacesReste(board) == 0) {
				System.out.println("Vous avez trouvé tout les mines Felicitation.....");
				gagne = true;
			}
		}     // while
		
	}
	
	public static String[][] init(int ligne, int collumn) {
		Random createRandom = new Random();
		Scanner input = new Scanner(System.in);
		String[][] board = new String[ligne][collumn];
		System.out.println("selectez difficulte 1=facile, 2=moyen, 3=dur ....:");
		int difficulte = checkInt(input.nextInt());
		int total = ligne * collumn;
		switch (difficulte) {
			case 1 : difficulte = 20; break;
			case 2 : difficulte = 30; break;
			default : difficulte = 40; break;
		}
		int pourcent =  ((difficulte * total) / 100);
		for (int i = 0; i < ligne; i++) {
			for (int j = 0; j < collumn; j++) {
				board[i][j] = "#";
			}
		}
		int counter = 0;
		while (counter < pourcent) {
			int lig = createRandom.nextInt(ligne);
			int col = createRandom.nextInt(collumn);
			if (board[lig][col].equals("#")) {
				board[lig][col] = "*";
				counter++;
			} 
		}
		return board;
	}
	
	
	public static int checkInt(int num) {
		/*  verifier  
		 *	que le nombre donné est entre 1..3	
		 */
		Scanner input = new Scanner(System.in);
		boolean flag = false;
		while (!flag) {
			if (num < 4 && num > 0) {
				flag = true;
			} else {
				System.out.println("nombre pas correct Donnez un autre...:");
				num = input.nextInt();
			}
		}
		return num;
	}
	
	public static void afficheBoard(String[][] tab){
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[i].length; j++) {
				System.out.print(tab[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static int espacesReste(String[][] tab) {
		/*  Calcule les espaces qui 
		*   reste au jeu pour  determiner  la fin de jeu
		*/
		int sum = 0;
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[i].length; j++) {
				if (tab[i][j].equals("#")) {
					sum++;
				}
			}
			
		} // for i
		return sum;
	}
	
	public static int checkCarre(String[][] tab , int lig, int col) {
		/*  verifier s' il y a un mine sur 
		 *		le carré indiqué
		 */
		if (tab[lig][col].equals("*")) {
			return 1;
		}
		return 0;
	}
	

	
	
	public static int checkChoix(String[][] tab , int lig, int col) {
		/*  compter combien de mines
		 *	il y a autour du carré selecté
		 *	
		 */
		int nombreMines = 0;
		if (lig == 0 && col == 0) {   // le  carré à la position 0,0 
			nombreMines = nombreMines + checkCarre(tab, 1, 0);
			nombreMines = nombreMines + checkCarre(tab, 1, 1);
			nombreMines = nombreMines + checkCarre(tab, 0, 1);
		} else if (tab.length - 1  == lig && col == 0) {  // le  carré à la position   tab.length,0 
			nombreMines = nombreMines + checkCarre(tab, (tab.length - 2), 0);
			nombreMines = nombreMines + checkCarre(tab, (tab.length - 2), 1);
			nombreMines = nombreMines + checkCarre(tab, (tab.length - 1), 1);
		} else if (col == 0) {     // les  carrés aux positions   n,0  sauf le premier et le dernier 
			nombreMines = nombreMines + checkCarre(tab, lig -1, col);
			nombreMines = nombreMines + checkCarre(tab, lig -1, col + 1);
			nombreMines = nombreMines + checkCarre(tab, lig,  col + 1);
			nombreMines = nombreMines + checkCarre(tab, lig + 1, col);
			nombreMines = nombreMines + checkCarre(tab, lig + 1, col + 1);
		} else if (lig == 0 && tab[tab.length - 1].length - 1 == col)  { // le  carré à la position          0, tab.length 	
			nombreMines = nombreMines + checkCarre(tab, lig , col -1);
			nombreMines = nombreMines + checkCarre(tab, lig + 1, col -1);
			nombreMines = nombreMines + checkCarre(tab, lig + 1, col);
		} else if(lig == 0) {      // les  carrés aux positions   0,n  sauf le premier et le dernier 
			nombreMines = nombreMines + checkCarre(tab, lig, col -1);
			nombreMines = nombreMines + checkCarre(tab, lig + 1, col -1);
			nombreMines = nombreMines + checkCarre(tab, lig + 1, col);
			nombreMines = nombreMines + checkCarre(tab, lig, col + 1);
			nombreMines = nombreMines + checkCarre(tab, lig + 1, col + 1);
		} else if (tab.length - 1  == lig  && tab[tab.length - 1].length - 1 == col) {    // le  carré à la position          tab.length, tab.length 
			nombreMines = nombreMines + checkCarre(tab, lig , col -1);
			nombreMines = nombreMines + checkCarre(tab, lig -1 , col -1);
			nombreMines = nombreMines + checkCarre(tab, lig -1 , col);
		}  else if (tab.length - 1  == lig) {    // les  carrés aux positions   tab.length, n  sauf le premier et le dernier 
			nombreMines = nombreMines + checkCarre(tab, lig , col -1);
			nombreMines = nombreMines + checkCarre(tab, lig - 1, col -1);
			nombreMines = nombreMines + checkCarre(tab, lig - 1, col);
			nombreMines = nombreMines + checkCarre(tab, lig - 1, col + 1);
			nombreMines = nombreMines + checkCarre(tab, lig , col + 1);
		} else if (tab[tab.length - 1].length - 1 == col) {   // les  carrés aux positions   n, tab.length  sauf le premier et le dernier 
			nombreMines = nombreMines + checkCarre(tab, lig - 1, col);
			nombreMines = nombreMines + checkCarre(tab, lig - 1, col -1);
			nombreMines = nombreMines + checkCarre(tab, lig , col -1);
			nombreMines = nombreMines + checkCarre(tab, lig + 1, col -1);
			nombreMines = nombreMines + checkCarre(tab, lig + 1, col);
		} else {    
			nombreMines = nombreMines + checkCarre(tab, lig - 1, col -1);
			nombreMines = nombreMines + checkCarre(tab, lig - 1, col);
			nombreMines = nombreMines + checkCarre(tab, lig - 1, col + 1);
			nombreMines = nombreMines + checkCarre(tab, lig, col - 1);
			nombreMines = nombreMines + checkCarre(tab, lig, col + 1);
			nombreMines = nombreMines + checkCarre(tab, lig + 1, col - 1);
			nombreMines = nombreMines + checkCarre(tab, lig + 1, col);
			nombreMines = nombreMines + checkCarre(tab, lig + 1, col + 1);
		}
		return nombreMines;
	}
	

}
