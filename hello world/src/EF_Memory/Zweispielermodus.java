package EF_Memory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.awt.Color;

public class Zweispielermodus extends JFrame implements ActionListener {

	private static EF_Memory MGame;
	private static Memory_Card[] CardButton = new Memory_Card[64];
	private static int[] MemoryContentArray = new int[64];
	private JButton StartButton, ResetButton, ExitButton;
	private int Status = 0;
	private int ToDo = 32;
	private int TriesCounter = 1;
	private int FirstButton, SecondButton;
	private int MatchesPlayer1 = 0;
	private int MatchesPlayer2 = 0;
	private JLabel Spielerzug, AnzahlPaare1, AnzahlPaare2;
	public static String winner, loser;
	public static int MatchesWinner, MatchesLoser;
	public static Boolean Gewinn_Zweispieler = false;

	public class Memory_Card extends JButton {

		public Memory_Card(int Spalte, int Zeile) {
			// Anpassungen an den Karten

			this.setSize(75, 75);
			this.setEnabled(false);
			this.setLocation(70 + Spalte * 90, 70 + Zeile * 90);
			this.setIcon(new ImageIcon("KSO.jpg"));

		}

		public void PlayButton(int ButtonValue) {
			// Bilder auf den Karten mit Zahlenwert

			this.setBackground(null);
			this.setIcon(new ImageIcon(Integer.toString(ButtonValue) + ".jpg"));

		}

		public void NoMatch() {
			// Wenn die Karten nicht übereinstimmen

			this.setBackground(null);
			this.setIcon(new ImageIcon("KSO.jpg"));

		}
	}

	public Zweispielermodus() {
		// Erstellung des Spielbretts

		super("KSO-Memory");

		int Zeile, Spalte;

		JPanel Platte = new JPanel();
		Platte.setLayout(null);

		JLabel Label = new JLabel();
		Label.add(Platte);

		for (int i = 0; i < 64; i++) {
			// Fügt die Karten ein

			Zeile = (int) i / 8;
			Spalte = i % 8;

			CardButton[i] = new Memory_Card(Spalte, Zeile);
			CardButton[i].addActionListener(this);

			Platte.add(CardButton[i]);
		}

		Spielerzug = new JLabel(); // Zeigt an, welcher Spieler am Zug ist
		Spielerzug.setText(Menu.Spieler1 + " ist am Zug");
		Spielerzug.setSize(150, 30);
		Spielerzug.setLocation(33, 20);
		Platte.add(Spielerzug);

		AnzahlPaare1 = new JLabel(); // Zeigt an, wie viel Paare der erste Spieler hat
		AnzahlPaare1.setSize(150, 30);
		AnzahlPaare1.setLocation(200, 20);
		AnzahlPaare1.setText(Menu.Spieler1 + ": " + Integer.toString(MatchesPlayer1) + " Paare");
		Platte.add(AnzahlPaare1);

		AnzahlPaare2 = new JLabel(); // Zeigt an, wie viel Paare der zweite Spieler hat
		AnzahlPaare2.setSize(150, 30);
		AnzahlPaare2.setLocation(400, 20);
		AnzahlPaare2.setText(Menu.Spieler2 + ": " + Integer.toString(MatchesPlayer2) + " Paare");
		Platte.add(AnzahlPaare2);

		ResetButton = new JButton(); // Anpassungen am Neustartbutton
		ResetButton.setSize(150, 30);
		ResetButton.setLocation(820, 50);
		ResetButton.setText("Neu starten");
		ResetButton.addActionListener(this);
		ResetButton.setEnabled(false);
		Platte.add(ResetButton);

		StartButton = new JButton(); // Anpassungen am Startbutton
		StartButton.setSize(150, 30);
		StartButton.setLocation(820, 100);
		StartButton.setText("Spiel starten");
		StartButton.addActionListener(this);
		Platte.add(StartButton);

		ExitButton = new JButton(); // Anpassungen am Beenden-Button
		ExitButton.setSize(150, 30);
		ExitButton.setLocation(820, 150);
		ExitButton.setText("Spiel beenden");
		ExitButton.addActionListener(this);
		Platte.add(ExitButton);

		setContentPane(Platte); // Anpassungen am Spielbrett
		setSize(1000, 825);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void mixCards() {
		// Methode fürs Mischen der Karten, Zahlenpaare werden den Karten zugeordnet

		boolean FreePlace;
		int RandomPlace;
		for (int i = 0; i < 64; i++) {
			// Jeder Karte wird anfangs die Zahl -1 zugeordnet

			MemoryContentArray[i] = -1;
		}
		for (int j = 0; j < 32; j++) {
			FreePlace = false;
			while (!FreePlace) {
				// Suche nach einer freien Karte

				RandomPlace = (int) (Math.random() * 64);

				if (MemoryContentArray[RandomPlace] == -1) {
					// Einer zufälligen leeren Karte wird die momentane Zahl
					// "j" zugeordnet

					MemoryContentArray[RandomPlace] = j;
					FreePlace = true;
				}
			}

			FreePlace = false;

			while (!FreePlace) { // Suche nach einer anderen freien Karte

				RandomPlace = (int) (Math.random() * 64);

				if (MemoryContentArray[RandomPlace] == -1) { // Einer anderen zufäligen Karte wird die momentane Zahl
																// "j" zugeordnet
					MemoryContentArray[RandomPlace] = j;
					FreePlace = true;
				}
			}
		}
	}

	private void StartGame() {
		// Methode zum Start des Spiels

		mixCards();

		for (int i = 0; i < 64; i++) {
			// Alle Karten werden aktiviert

			CardButton[i].setEnabled(true);
			CardButton[i].setIcon(new ImageIcon("KSO.jpg"));
			CardButton[i].setText(null);

		}

		ResetButton.setEnabled(true);
		StartButton.setEnabled(false);
	}

	private void ResetGame() {
		// Methode für den Neustart des Spiels

		for (int i = 0; i < 64; i++) {
			// Alle Karten werden deaktiviert und die Zahl -1 wird zugeordnet

			CardButton[i].setEnabled(false);
			MemoryContentArray[i] = -1;
			CardButton[i].setBackground(null);
		}

		// Zurücksetzung und Anpassung der Variablen Buttons

		ResetButton.setEnabled(false);
		StartButton.setEnabled(true);

		Status = 0;
		MatchesPlayer1 = 0;
		MatchesPlayer2 = 0;
		AnzahlPaare1.setText(Menu.Spieler1 + ": " + Integer.toString(MatchesPlayer1) + " Paare");
		AnzahlPaare2.setText(Menu.Spieler2 + ": " + Integer.toString(MatchesPlayer2) + " Paare");
		TriesCounter = 0;
		Spielerzug.setText(Menu.Spieler1 + " ist am Zug");
		ToDo = 32;

	}

	public void actionPerformed(ActionEvent Action) {
		// ActionListener nimmt Betätigung der Buttons war

		Object Source = Action.getSource();

		if (Source == ExitButton) {
			System.exit(0);
		} // Beenden-Button wird gedrückt
		if (Source == ResetButton) {
			ResetGame();
		} // Neustartbutton wird gedrückt

		switch (Status) {

		case 0: {
			// Noch keine Karte wurde aufgedeckt

			if (Source == StartButton) {
				StartGame();
			} // Neustartbutton wird gedrückt

			for (int i = 0; i < 64; i++) {
				// Bei Betätigung einer Karte wird der Startbutton deaktiviert und der Inhalt
				// wird angezeigt

				if (Source == CardButton[i]) {
					CardButton[i].PlayButton(MemoryContentArray[i]);
					FirstButton = i;
					StartButton.setEnabled(false);
					Status = 1;
				}
			}
			break;
		}

		case 1: {
			// Noch keine zweite Karte wurde aufgedeckt

			if (Source == ExitButton) {
				System.exit(0);
			} // Beenden-Button wird gedrückt
			if (Source == ResetButton) {
				ResetGame();
			} // Neustartbutton wird gedrückt

			for (int j = 0; j < 64; j++) {

				if (Source == CardButton[j] && j != FirstButton) {
					// Wenn eine neue Karte aufgedeckt wird, wird der Inhalt angezeigt

					CardButton[j].PlayButton(MemoryContentArray[j]);
					SecondButton = j;
					Status = 2;
				}
			}
			break;
		}

		case 2: {
			// Überprüfung der Karten

			if (Source == CardButton[FirstButton] || Source == CardButton[SecondButton]) {
				// Wenn eine der beiden Karten erneut gedrückt wird, werden sie umgedreht

				CardButton[FirstButton].NoMatch();
				CardButton[SecondButton].NoMatch();

				if (MemoryContentArray[FirstButton] == MemoryContentArray[SecondButton]) {
					// Beim Finden eines Paars werden die Karten deaktiviert

					CardButton[FirstButton].setIcon(null);
					CardButton[SecondButton].setIcon(null);

					CardButton[FirstButton].setText("X");
					CardButton[SecondButton].setText("X");

					CardButton[FirstButton].setEnabled(false);
					CardButton[SecondButton].setEnabled(false);

					if (TriesCounter % 2 == 1) { // Bei einer ungeraden Zahl hat Spieler1 ein weiteres Paar gefunden und
													// ist noch einmal dran

						MatchesPlayer1++;
						Spielerzug.setText(Menu.Spieler1 + " ist am Zug");

						if (MatchesPlayer1 == 1) { // Anzeige der Anzahl Paare von Spieler1

							AnzahlPaare1.setText(Menu.Spieler1 + ": " + Integer.toString(MatchesPlayer1) + " Paar");

						}

						else {

							AnzahlPaare1.setText(Menu.Spieler1 + ": " + Integer.toString(MatchesPlayer1) + " Paare");
						}
					}

					else { // Bei einer geraden Zahl hat Spieler2 ein weiteres Paar gefunden und ist noch
							// einmal dran

						MatchesPlayer2++;
						Spielerzug.setText(Menu.Spieler2 + " ist am Zug");

						if (MatchesPlayer2 == 1) { // Anzeige der Anzahl Paare von Spieler2

							AnzahlPaare2.setText(Menu.Spieler2 + ": " + Integer.toString(MatchesPlayer2) + " Paar");

						}

						else {

							AnzahlPaare2.setText(Menu.Spieler2 + ": " + Integer.toString(MatchesPlayer2) + " Paare");

						}

					}

					ToDo--;
					if (ToDo == 0) {
						// Wird ausgeführt, wenn alle Paare gefunden worden sind

						Gewinn_Zweispieler = true; // Damit wird die Information überliefert, dass der Zweispielermodus
													// fertig gespielt wurde

						if (MatchesPlayer1 > MatchesPlayer2) {
							// Name und Anzahl Paare des Gewinners und Verlierers werden fesgelegt

							winner = Menu.Spieler1;
							loser = Menu.Spieler2;
							MatchesWinner = MatchesPlayer1;
							MatchesLoser = MatchesPlayer2;

						}

						if (MatchesPlayer2 > MatchesPlayer1) {

							winner = Menu.Spieler2;
							loser = Menu.Spieler1;
							MatchesWinner = MatchesPlayer2;
							MatchesLoser = MatchesPlayer1;

						}

						if (MatchesPlayer2 == MatchesPlayer1) { // Bei der gleichen Anzahl Paare ist es Unentschieden

							winner = "both";
						}

						new Gewinnerbildschirm();

					}
				}

				else {
					// Falls die Karten nicht identisch ist, ist je nach gerader oder ungerader Zahl
					// der Anzahl Versuche der nächste Spieler dran

					TriesCounter++;

					if (TriesCounter % 2 == 1) {

						Spielerzug.setText(Menu.Spieler1 + " ist am Zug");
					}

					else {

						Spielerzug.setText(Menu.Spieler2 + " ist am Zug");

					}
				}

				Status = 0;
			}

		}
			break;
		}
	}
}
