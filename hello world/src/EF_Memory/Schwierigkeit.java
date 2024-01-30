package EF_Memory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Schwierigkeit implements ActionListener {
	// TODO Auto-generated method stub

	JFrame meinFrame;
	JButton Einfach;
	JButton Mittel;
	JButton Schwer;
	JPanel panel;
	JLabel Label;
	public static int difficulty; // Öffentliche Variable zur Übermittlung des Schwierigkeitsgrads

	public Schwierigkeit() {
		// Anpassungen für den JFrame, JPanel, JLabel und JButtons

		meinFrame = new JFrame(" ");
		meinFrame.setSize(465, 200);

		panel = new JPanel();
		panel.setLayout(null);

		Einfach = new JButton("Einfach");
		Einfach.setLocation(50, 100);
		Einfach.setSize(100, 30);

		Mittel = new JButton("Mittel");
		Mittel.setLocation(175, 100);
		Mittel.setSize(100, 30);

		Schwer = new JButton("Schwer");
		Schwer.setLocation(300, 100);
		Schwer.setSize(100, 30);

		Label = new JLabel("Schwierigkeitsauswahl");
		Label.setLocation(163, 50);
		Label.setSize(150, 30);

		Einfach.addActionListener(this);
		Mittel.addActionListener(this);
		Schwer.addActionListener(this);

		panel.add(Label);
		panel.add(Einfach);
		panel.add(Mittel);
		panel.add(Schwer);

		meinFrame.add(panel);
		meinFrame.setVisible(true);
	}

	public void actionPerformed(ActionEvent selection) {
		// Abhängend von der Betätigung des Buttons wird die Schwierigkeitsgrad-Variable
		// definiert

		if (selection.getSource() == this.Einfach) {

			difficulty = 1;

		}

		if (selection.getSource() == this.Mittel) {

			difficulty = 2;

		}

		else if (selection.getSource() == this.Schwer) {

			difficulty = 3;

		}

		EF_Memory spiel = new EF_Memory();

		meinFrame.dispose();

	}

}
