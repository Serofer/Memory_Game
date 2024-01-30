package EF_Memory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.awt.Color;
import java.awt.Container;

public class EF_Memory extends JFrame implements ActionListener {

	private static JPanel Platte;
	private static EF_Memory MGame;
	private static Memory_Card[] CardButton = new Memory_Card[64];
	private static int[] MemoryContentArray = new int[64];
	private Boolean[] cardCompleted;
	private JButton StartButton, ResetButton, ExitButton;
	private JLabel Counter, ShowTimeLeft, Pairs;
	private int Status = 0;
	public static int TriesCounter = 0;
	private int FirstButton, SecondButton;
	public long StartTime, GameMin, GameSec, GameTime, TimeLeft, BonusTime;
	public static String TimeString, TimeLeftString = "4 min";
	private int Bonus = 0, k = 64;
	Boolean BonusView = false;
	private int ToDo = 32;
	private int Matches = 0;

	public class Memory_Card extends JButton {

		public Memory_Card(int Spalte, int Zeile) {
			// Anpassungen an den Karten

			this.setSize(75, 75);
			this.setEnabled(false);
			this.setLocation(70 + Spalte * 90, 70 + Zeile * 90);// Position der Karten
			this.setIcon(new ImageIcon("KSO.jpg"));

		}

		public void PlayButton(int ButtonValue) {
			// Bilder auf den Karten mit Zahlerwert

			this.setBackground(null);
			this.setIcon(new ImageIcon(Integer.toString(ButtonValue) + ".jpg"));

		}

		public void NoMatch() {
			// Wenn die Karten nicht übereinstimmen

			this.setBackground(null);
			this.setIcon(new ImageIcon("KSO.jpg"));

		}
	}

	public EF_Memory() {
		// Erstellung des Spielbretts

		super("KSO-Memory");

		if (Schwierigkeit.difficulty == 1) {
			// Bei Auswahl des einfachen Modus' gibt es nur 36 Karten

			k = 36;

			ToDo = 18;
		}

		int Zeile, Spalte;

		Platte = new JPanel();
		Platte.setLayout(null);

		JLabel Label = new JLabel();
		Label.add(Platte);

		for (int i = 0; i < k; i++) {
			// Fügt die Karten ein

			Zeile = (int) (i / (Math.sqrt((double) k)));
			Spalte = (int) (i % (Math.sqrt((double) k)));
			CardButton[i] = new Memory_Card(Spalte, Zeile);
			CardButton[i].addActionListener(this);

			Platte.add(CardButton[i]);
		}
		cardCompleted = new Boolean[k]; // jede Karte ist am Anfang nicht gefunden
		for (int i = 0; i < k; i++) {
			cardCompleted[i] = false;
		}

		if (Schwierigkeit.difficulty == 3) {
			// Anzeige der restllichen Zeit

			ShowTimeLeft = new JLabel();
			ShowTimeLeft.setSize(300, 30);
			ShowTimeLeft.setLocation(500, 20);
			ShowTimeLeft.setText("Restliche Zeit: " + TimeLeftString);
			Platte.add(ShowTimeLeft);
		}

		Pairs = new JLabel(); // Zeigt die Anzahlpaare an
		Pairs.setSize(300, 30);
		Pairs.setLocation(200, 20);
		Pairs.setText("Anzahl Paare: " + Matches);
		Platte.add(Pairs);

		Counter = new JLabel(); // Zählt die Anzahl Versuche
		Counter.setSize(150, 30);
		Counter.setLocation(33, 20);
		Counter.setText("Zähler: " + Integer.toString(TriesCounter));
		Platte.add(Counter);

		ResetButton = new JButton(); // Anpassungen am Neustartbutton
		ResetButton.setSize(150, 30);
		ResetButton.setText("Neu starten");
		ResetButton.addActionListener(this);
		ResetButton.setEnabled(false);
		Platte.add(ResetButton);

		StartButton = new JButton(); // Anpassungen am Startbutton
		StartButton.setSize(150, 30);
		StartButton.setText("Spiel starten");
		StartButton.addActionListener(this);
		Platte.add(StartButton);

		ExitButton = new JButton(); // Anpassungen am Beenden-Button
		ExitButton.setSize(150, 30);
		ExitButton.setText("Spiel beenden");
		ExitButton.addActionListener(this);
		Platte.add(ExitButton);

		setContentPane(Platte);

		if (Schwierigkeit.difficulty == 1) {
			// Positionen der Buttons und Grösse des Spielbretts je nach Schwierigkeit

			ResetButton.setLocation(650, 50);
			StartButton.setLocation(650, 100);
			ExitButton.setLocation(650, 150);
			setSize(825, 700);
		}

		else {
			ResetButton.setLocation(820, 50);
			StartButton.setLocation(820, 100);
			ExitButton.setLocation(820, 150);
			setSize(1000, 825);
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void mixCards() {
		// Methode fürs Mischen der Karten, Zahlenpaare werden den Karten zugeordnet

		boolean FreePlace;
		int RandomPlace;
		for (int i = 0; i < k; i++) {
			// Jeder Karte wird anfangs die Zahl -1 zugeordnet

			MemoryContentArray[i] = -1;
		}
		for (int j = 0; j < k / 2; j++) {
			FreePlace = false;
			while (!FreePlace) {
				// Suche nach einer freien Karte

				RandomPlace = (int) (Math.random() * k);

				if (MemoryContentArray[RandomPlace] == -1) {
					// Einer zufälligen leeren Karte wird die momentane Zahl
					// "j" zugeordnet

					MemoryContentArray[RandomPlace] = j;
					FreePlace = true;
				}
			}
			FreePlace = false;
			while (!FreePlace) {
				// Suche nach einer anderen freien Karte

				RandomPlace = (int) (Math.random() * k);

				if (MemoryContentArray[RandomPlace] == -1) {
					// Einer anderen zufäligen Karte wird die momentane Zahl
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

		for (int i = 0; i < k; i++) {
			// Alle Karten werden aktiviert

			CardButton[i].setEnabled(true);
			CardButton[i].setIcon(new ImageIcon("KSO.jpg"));
			CardButton[i].setText(null);

		}

		StartTime = System.currentTimeMillis();
		ResetButton.setEnabled(true);
		StartButton.setEnabled(false);

		if (Schwierigkeit.difficulty == 3) {

			Thread TimeLimit = new Thread(new Runnable() {
				// Läuft ab dem Starten des Spiels ohne den Rest zu beeinflussen und zeigt
				// restliche Zeit stetig an

				@Override
				public void run() {

					while (true) {

						GameTime = System.currentTimeMillis() - StartTime;

						TimeLeft = 240000 - GameTime;
						GameSec = (TimeLeft / 1000);
						GameMin = GameSec / 60;
						GameSec = GameSec % 60;
						TimeLeftString = "Restliche Zeit: " + Long.toString(GameMin) + " min  " + Long.toString(GameSec)
								+ " s";
						ShowTimeLeft.setText(TimeLeftString);

						if (TimeLeft == 0) {
							// Bei Überschreitung des Zeitlimits im schweren Modus wird eine Meldung
							// angezeigt und man hat das Spiel verloren

							JOptionPane.showMessageDialog(null,
									"Du hast das Zeitlimit von 4 Minuten überschritten und verloren :(. Du hast "
											+ Matches + " Paare gesammelt.");
							System.exit(0);

						}

					}

				}

			});
			TimeLimit.start();
		}
	}

	private void ResetGame() {
		// Methode für den Neustart des Spiels

		for (int i = 0; i < k; i++) {
			// Alle Karten werden deaktiviert und die Zahl -1 wird zugeordnet

			CardButton[i].setEnabled(false);
			MemoryContentArray[i] = -1;

			CardButton[i].setBackground(null);
		}

		// Zurücksetzung der Variablen

		ResetButton.setEnabled(false);
		StartButton.setEnabled(true);
		Status = 0;
		TriesCounter = 0;
		ToDo = k / 2;
		Matches = 0;
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

			for (int i = 0; i < k; i++) {
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

			for (int j = 0; j < k; j++) {

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

				TriesCounter++;

				Counter.setText("Zähler: " + Integer.toString(TriesCounter));

				if (MemoryContentArray[FirstButton] == MemoryContentArray[SecondButton]) {
					// Beim Finden eines Paars werden die Karten deaktiviert

					CardButton[FirstButton].setIcon(null);
					CardButton[SecondButton].setIcon(null);

					CardButton[FirstButton].setText("X");
					CardButton[SecondButton].setText("X");

					CardButton[FirstButton].setEnabled(false);
					CardButton[SecondButton].setEnabled(false);

					cardCompleted[FirstButton] = true; // die beiden gefundenen Karten werden als gefunden
														// gekennzeichnet
					cardCompleted[SecondButton] = true;

					Matches++;
					Pairs.setText("Anzahl Paare: " + Matches);

					if (TriesCounter <= 10) {
						// Wenn man weniger als zehn Versuche hat und ein Paar findet, wird die
						// Bonusvariable erhöht

						Bonus++;
					}

					if (Bonus == 2) {
						// Wird ausgeführt, enn man zwei Paare nacheinander innerhalb der ersten zehn
						// Züge findet

						Bonus = 0;
						BonusTime = System.currentTimeMillis();
						Thread bonusview = new Thread(new Runnable() {
							// Parallele Ausführung vom Code, ohne den Rest anzuhalten

							@Override
							public void run() {

								while (true) {

									if (System.currentTimeMillis() - BonusTime >= 5000) {
										// wenn 5 Sekunden vorbei sind, werden alle Karten zurückgedreht, ausser sie
										// bereits wurden gefunden

										for (int i = 0; i < k; i++) {

											if (cardCompleted[i])
												continue;

											CardButton[i].NoMatch();
											CardButton[i].setEnabled(true);
										}
										return; // While Loop wird beendet
									}

									else {

										for (int i = 0; i < k; i++) {
											// Alle ausser die gefundenen Paare werden aufgedeckt

											if (cardCompleted[i])
												continue;

											CardButton[i].setEnabled(true);
											CardButton[i].setIcon(
													new ImageIcon(Integer.toString(MemoryContentArray[i]) + ".jpg"));// Alle
																														// Bilder
																														// anzeigen
										}

									}
								}

							}
						});

						bonusview.start(); // Thread wird gestartet

					}

					ToDo--;
				}

				else { // Wenn zwei nicht identische Karten aufgedeckt wurden, wird die Bonusvariable
						// zurückgesetzt

					Bonus = 0;
				}

				if (ToDo == 0) { // Wird ausgeführt, wenn alle Paare gefunden worden sind

					GameTime = System.currentTimeMillis() - StartTime;
					GameSec = (GameTime / 1000);
					GameMin = GameSec / 60;
					GameSec = GameSec % 60;

					TimeString = " " + Long.toString(GameMin) + " min  " + Long.toString(GameSec) + " s";

					new Gewinnerbildschirm();

				}

				Status = 0;
			}

		}
			break;
		}
	}
}
