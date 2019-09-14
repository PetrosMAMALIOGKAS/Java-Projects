

import java.util.InputMismatchException;
import java.util.Scanner;
public class ProjectNfa031 {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Nombre de coureurs...:");
		int numCoureurs = input.nextInt();
		int selection = 0;
		int dossard = 0;
		int counter = 1;
		boolean flag = true; 
		String[]  listCoureurs = creeTab(numCoureurs);
		int[]  classement = creeTabVide(numCoureurs);
		int[]  disqualifie = creeTabVide(numCoureurs);
		int[]  abandoner = creeTabVide(numCoureurs);
		int[]  resterEnCourse = creeTabVide(numCoureurs);
		for (int i = 0; i < resterEnCourse.length; i++) {
			resterEnCourse[i] = i + 1;
		}
		while (flag) {
			
			afficheMenu();
			System.out.println("Votre choix...:");
			selection = input.nextInt();
			switch (selection) {
				
				case 1 :
					clearScr();
					counter = 1;
					System.out.println("#####   Classement provisoire   #####");
					if (classement[0] == -1) {
						System.out.println();
						System.out.println("Pas de courreur arrivé!!!!");
					}else {
						System.out.println();
						for (int i = 0; i < classement.length; i++) {
							if (classement[i] != -1) {
								System.out.println(counter + ". " + classement[i] + " " + listCoureurs[classement[i]-1]);
								counter++;
							}
						}
					}
					System.out.println();
					System.out.println("###  Abandons  ###");
					counter = 0;
					for (int i = 0; i < abandoner.length; i++) {
						if (abandoner[i] != -1) {
							System.out.println("coureur numero " + abandoner[i] + " " + listCoureurs[abandoner[i]-1]);
							counter++;
						}
					}
					if (counter == 0) {
						System.out.println("Zero abandons");
					}
					System.out.println();
					counter = 0;
					System.out.println("###  disqualifiés  ###");
					for (int i = 0; i < disqualifie.length; i++) {
						if (disqualifie[i] != -1) {
							System.out.println("coureur numero " + disqualifie[i] + " " + listCoureurs[disqualifie[i]-1]);
							counter++;
						}
					}
					if (counter == 0) {
						System.out.println("Zero disqulifies");
					}
					
					System.out.println("###  Reste  en course  ###");
					counter = 0;
					for (int i = 0; i < resterEnCourse.length; i++) {
						if (resterEnCourse[i] != -1) {
							System.out.println("coureur numero " + resterEnCourse[i] + " " + listCoureurs[resterEnCourse[i]-1]);
							counter++;
						}
					}
					if (counter == 0) {
						System.out.println("Personne reste");
					}
					
					
					
					System.out.println();
					System.out.println("########################################");
			
					break;
					
				case 2 :
					clearScr();
					System.out.println("####  Liste de Participants  ######");
					afficheTab(listCoureurs);
					System.out.println();
					break;
					
				case 3 :
					System.out.println("########################################");
					System.out.println("Entrez le numero de dossard arrivee....: ");
					dossard = input.nextInt();
					while (checkDonne(dossard, numCoureurs)) {
						System.out.println(dossard + " paricipe pas a la course");
						System.out.println("Entrez le numero de dossard....: ");
						dossard = input.nextInt();
					}
					if (checkStatus(classement, dossard)) {
						System.out.println("le courreur " + dossard +" (" + listCoureurs[dossard -1] + ") est deja arrive");
						System.out.println();
					} else if (checkStatus(abandoner, dossard)) {
								System.out.println("le courreur " + dossard +" (" + listCoureurs[dossard -1] + ") est deja abandoner la course");
								System.out.println();
							} else if (checkStatus(disqualifie, dossard)) {
										System.out.println("le courreur " + dossard +" (" + listCoureurs[dossard   -1] + ") est disqualifier");
										System.out.println();
									} else { 
											if (classement[0] == -1) {
												classement[0] = dossard;
												for (int i = 0; i < resterEnCourse.length; i++) {
													if (resterEnCourse[i] == dossard) {
														resterEnCourse[i] = -1;
													}
												}
											} else {
												classement[dernierArrive(classement) + 1] = dossard;
												for (int i = 0; i < resterEnCourse.length; i++) {
													if (resterEnCourse[i] == dossard) {
														resterEnCourse[i] = -1;
													}
												}
											}
									}
					break;
					
				case 4 : 
					System.out.println("########################################");
					System.out.println("Entrez le numero de dossard abandone....: ");
					dossard = input.nextInt();
					while (checkDonne(dossard, numCoureurs)) {
						System.out.println(dossard + " paricipe pas a la course");
						System.out.println("Entrez le numero de dossard....: ");
						dossard = input.nextInt();
					}
					if (checkStatus(classement, dossard)) {
						System.out.println("le courreur " + dossard +" (" + listCoureurs[dossard -1] + ") est deja arrive");
					}  else if (checkStatus(abandoner, dossard)) {
								System.out.println("le courreur " + dossard +" (" + listCoureurs[dossard -1] + ") est deja abandoner la course");
								System.out.println();
							} else if (checkStatus(disqualifie, dossard)) {
										System.out.println("le courreur " + dossard +" (" + listCoureurs[dossard -1] + ") est disqualifier");
										System.out.println();
									} else if (abandoner[0] == -1) {
											 abandoner[0] = dossard;
											 for (int i = 0; i < resterEnCourse.length; i++) {
													if (resterEnCourse[i] == dossard) {
														resterEnCourse[i] = -1;
													}
											}
										   } else {
												abandoner[dernierArrive(abandoner) + 1] = dossard;
												for (int i = 0; i < resterEnCourse.length; i++) {
													if (resterEnCourse[i] == dossard) {
														resterEnCourse[i] = -1;
													}
												}
										   }
					break;
					
				case 5 : 
					System.out.println("########################################");
					System.out.println("Entrez le numero de dossard disqualifie....: ");
					dossard = input.nextInt();
					while (checkDonne(dossard, numCoureurs)) {
						System.out.println(dossard + " paricipe pas a la course");
						System.out.println("Entrez le numero de dossard....: ");
						dossard = input.nextInt();
					}
					if (checkStatus(classement, dossard)) {
						System.out.println("le courreur " + dossard +" (" + listCoureurs[dossard -1] + ") est deja arrive mais il est disqualifie par le jury");
						for (int i = 0; i < classement.length; i++) {
							if (classement[i] == dossard) {
								for (int k = i; k < classement.length; k++) {
									try {
										classement[k] = classement[k + 1];
									} catch (ArrayIndexOutOfBoundsException e) {
										
								    }
								}
							}
						}
						disqualifie[dernierArrive(disqualifie)] = dossard;
					}  else if (checkStatus(abandoner, dossard)) {
								System.out.println("le courreur " + dossard +" (" + listCoureurs[dossard -1] + ") est deja abandoner la course");
								System.out.println();
							} else if (checkStatus(disqualifie, dossard)) {
										System.out.println("le courreur " + dossard +" (" + listCoureurs[dossard -1] + ") est  deja disqualifier");
										System.out.println();
									} else if (disqualifie[0] == -1) {
											   disqualifie[0] = dossard;
											   for (int i = 0; i < resterEnCourse.length; i++) {
													if (resterEnCourse[i] == dossard) {
														resterEnCourse[i] = -1;
													}
												}
											} else {
												disqualifie[dernierArrive(disqualifie) + 1] = dossard;
												for (int i = 0; i < resterEnCourse.length; i++) {
													if (resterEnCourse[i] == dossard) {
														resterEnCourse[i] = -1;
													}
											}
											}
											
					break;
					
				case 6 :
					flag = false;
					break;
				
				default :
					System.out.println(selection + " ce n'est pas un choix valid");
					System.out.println();
					break;
			}
		}
	}
	
	public static String[] creeTab(int num) {
		Scanner input = new Scanner(System.in);
		String[]  tab  = new String[num];
			for (int i = 0; i < tab.length; i++) {
				System.out.println("Donnez-moi le nom du courreur numero " + (i+1) + "....: ");
				tab[i] = input.nextLine();
		}
		return tab;
	}
	
	public static int[] creeTabVide(int num) {
		int[]  tab  = new int[num];
			for (int i = 0; i < tab.length; i++) {
				
				tab[i] = -1;
		}
		return tab;
	}
	
	public static void afficheMenu() {
		System.out.println("###########     M E N U     ############");
		System.out.println("1- pour afficher le classement provisoire");
		System.out.println("2- pour afficher les participants");
		System.out.println("3- pour enregistrer une arrivee");
		System.out.println("4- pour enregistrer un abandon");
		System.out.println("5- pour enregistrer une disqualification");
		System.out.println("6- pour quitter");
	}
	
	public static void afficheTab(String[] tab) {
		for (int i = 0; i < tab.length; i++) {
			System.out.println("       coureur " + (i+1) + "...:" + tab[i]);
		}
	}
	
	public static int dernierArrive(int[] classement) {
		int dernier = 0;
		for (int i = 0; i < classement.length; i++ ) {
			if (classement[i] != -1) {
				dernier = i;
			}
		}
		return dernier;
	}
		
	public static boolean checkStatus(int[] classement, int dossard) {
		int counter = 0;
		for (int i = 0; i < classement.length; i++ ) {
			if (classement[i] == dossard) {
				counter++;
			}
		}
		if (counter == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean checkDonne(int dossard, int numCoureurs) {
		if (dossard > numCoureurs || dossard < 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void clearScr() {
		for (int i = 0; i < 30; i++ ) {
			System.out.println();
		}
	}
}
