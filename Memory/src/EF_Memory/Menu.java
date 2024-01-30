package EF_Memory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Menu implements ActionListener {
	// TODO Auto-generated method stub

	JFrame meinFrame;
	JButton Einzelspieler;
	JButton Zweispieler;
	JPanel panel;
	JLabel Label;
	JLabel Image;

	public static String Spieler1; // Öffentlich zugreifbare Variablen für die Namen der Spieler
	public static String Spieler2;
	public static String Name;

	public Menu() {
		// Anpassungen für die JButtons, das Logo und den JFrame

		meinFrame = new JFrame("KSO-Memory");
		meinFrame.setSize(620, 740);

		panel = new JPanel();
		panel.setLayout(null);

		Einzelspieler = new JButton("Einzelspieler");
		Einzelspieler.setLocation(150, 600);
		Einzelspieler.setSize(150, 50);

		Zweispieler = new JButton("Zweispieler");
		Zweispieler.setLocation(350, 600);
		Zweispieler.setSize(150, 50);

		Label = new JLabel();
		Label.setText("KSO-Memory");
		Label.setLocation(275, 100);
		Label.setSize(100, 30);

		Image = new JLabel();
		Image.setIcon(new ImageIcon("Logo.jpg"));
		Image.setLocation(125, 150);
		Image.setSize(400, 400);

		Einzelspieler.addActionListener(this);
		Zweispieler.addActionListener(this);

		panel.add(Einzelspieler);
		panel.add(Zweispieler);
		panel.add(Label);
		panel.add(Image);

		meinFrame.add(panel);
		meinFrame.setVisible(true);

	}

	public static void main(String[] args) {
		// Menu wird als Main Methode ausgeführt

		new Menu();
	}

	public void actionPerformed(ActionEvent selection) {
		// Reaktionen bei Betätigung der JButtons

		if (selection.getSource() == this.Einzelspieler) {
			// Bei Betätigung des Einzelspielerbuttons kann man seinen Namen eingeben und
			// man gelangt zur Schwierigkeitsauswahl

			Name = JOptionPane.showInputDialog(null, "Bitte gib deinen Namen ein", "Nameneingabe",
					JOptionPane.PLAIN_MESSAGE);

			Schwierigkeit spiel = new Schwierigkeit();

			meinFrame.dispose();

		} else if (selection.getSource() == this.Zweispieler) {
			// Bei Betätigung des Zweispielerbuttons gibt man die Namen der beiden Spieler
			// ein und man gelangt zum Zweispielermodus

			Spieler1 = JOptionPane.showInputDialog(null, "Gib den Namen des ersten Spielers ein",
					"Nameneingabe Spieler 1", JOptionPane.PLAIN_MESSAGE);

			Spieler2 = JOptionPane.showInputDialog(null, "Bitte gib den Namen des zweiten Spielers ein",
					"Nameneingabe Spieler 2", JOptionPane.PLAIN_MESSAGE);

			Zweispielermodus spiel = new Zweispielermodus();

			meinFrame.dispose();

		}

	}

}
