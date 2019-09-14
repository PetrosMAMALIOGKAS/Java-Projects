

import java.awt.event.*;

public class ButtonListener implements ActionListener{
	private IGTableur igt;
	ButtonListener(IGTableur o){
		igt=o;
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("OK"))
			igt.clickOK();
		else
			igt.clickCancel();
	}
}
