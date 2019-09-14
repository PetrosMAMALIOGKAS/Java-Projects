

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class IGTableurSimple extends JFrame {
    private JTextField[][] tab = new JTextField[10][10];
    private DecimalFormat df = new DecimalFormat("#######.##"); 
    private JPanel feuille = new JPanel();
    IGTableurSimple(){
	super("Tableur");
 	JLabel[] horlab = new JLabel[10];
	JLabel[] verlab = new JLabel[10];
 	feuille.setLayout(new GridLayout(11,11));
	for (int i=0; i<10; i++){
	    for (int j=0; j<10; j++){
		tab[i][j]= new JTextField(10);
		tab[i][j].setEditable(false);
		tab[i][j].setHorizontalAlignment(JTextField.LEFT);
	    }
	}
	feuille.add(new JLabel(""));
	char c = 'A';
	for (int i=0; i<10; i++,c++){
	    verlab[i]=new JLabel(""+c,JLabel.CENTER);
	    feuille.add(verlab[i]);
	}
	for (int i=0; i<10; i++){
	    horlab[i]=new JLabel("" + i+ "    ",JLabel.RIGHT);
	    feuille.add(horlab[i]);
	    for (int j=0; j<10; j++){
		feuille.add(tab[i][j]);
	    }
	}
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setContentPane(feuille);
        pack();
        setVisible(true);
    }

    public void modifieCellule(char c, int l, String v){
	int co = c-'A';
	String aff;
	try{
	    double d=Double.parseDouble(v);
	    aff=df.format(d) + ' ';
	    tab[l][co].setHorizontalAlignment(JTextField.RIGHT);
	}catch(NumberFormatException e){
	    tab[l][co].setHorizontalAlignment(JTextField.LEFT);
		aff = ' ' + v;
	}
	tab[l][co].setText(aff);
    }
    public void fermer(){
        this.dispose();
    }
}
