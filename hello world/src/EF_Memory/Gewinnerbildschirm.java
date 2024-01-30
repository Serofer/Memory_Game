package EF_Memory;

import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Gewinnerbildschirm {

	JFrame meinFrame;
	JPanel panel;
	JLabel Label;
	JLabel Image;
	JLabel TimeAndTries;
	JLabel Loser;

	public Gewinnerbildschirm() {

		meinFrame = new JFrame("Gratulation");
		meinFrame.setSize(620, 740);

		panel = new JPanel();
		panel.setLayout(null);

		Label = new JLabel();

		Loser = new JLabel(); // Text für den Verlierer des Spiels beim Zweispielermodus
		Loser.setText(Zweispielermodus.loser + " hat nur " + Zweispielermodus.MatchesLoser + " Paare gesammelt.");
		Loser.setLocation(150, 600);
		Loser.setSize(400, 30);

		if (Zweispielermodus.Gewinn_Zweispieler) {
			// Wird ausgeführt, wenn man den Zweispielermodus gespielt hat

			if (Zweispielermodus.winner == "both") {
				// Wird bei Unentschieden ausgeführt

				Label.setText("Herzliche Gratulation " + Menu.Spieler1 + " und " + Menu.Spieler2
						+ ". Ihr habt beide mit 16 Paaren gewonnen.");
			}

			if (Zweispielermodus.winner == Menu.Spieler2 || Zweispielermodus.winner == Menu.Spieler1) {
				// Zeigt Gewinner mit Anzahl Paaren an und fügt Verlierertext hinzu

				Label.setText(" Herzliche Gratulation, " + Zweispielermodus.winner + " hat mit "
						+ Zweispielermodus.MatchesWinner + " Paaren gewonnen.");

				panel.add(Loser);

			}

		}

		else {
			// Wenn der Einzelspielermodus abgeschlossen wurde

			Label.setText("Du hast Gewonnen. Herzliche Gratulation " + Menu.Name + ".");

			TimeAndTries = new JLabel();
			TimeAndTries.setText("Dafür hast du " + EF_Memory.TimeString + " und " + EF_Memory.TriesCounter
					+ " Versuche gebraucht.");
			TimeAndTries.setLocation(150, 600);
			TimeAndTries.setSize(600, 30);

			panel.add(TimeAndTries);

		}
		// Anpassungen für JFrame, JLabel und Gewinnerbild

		meinFrame = new JFrame("Gratulation");
		meinFrame.setSize(620, 740);

		Image = new JLabel();
		Image.setIcon(new ImageIcon("WinnerScreen.jpg"));

		Label.setLocation(150, 75);
		Label.setSize(500, 30);

		Image.setLocation(150, 150);
		Image.setSize(400, 400);

		panel.add(Label);
		panel.add(Image);

		meinFrame.add(panel);
		meinFrame.setVisible(true);

	}
}
