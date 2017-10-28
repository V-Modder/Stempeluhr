package org.stempeluhr;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		System.out.println("Starte Terminal 1");
		Runnable guiCreator = new Runnable() {
			public void run() {
				// Erstellt das Swing-Fenster
				final JFrame fenster = new JFrame("Hallo Welt mit Swing");
				// Swing anweisen, das Programm zu beenden, wenn das Fenster
				// geschlossen wird
				JPanel panel = new JPanel(new GridBagLayout());
				GridBagConstraints grid = new GridBagConstraints();
				grid.fill = GridBagConstraints.HORIZONTAL;
				grid.anchor = GridBagConstraints.NORTH;
				grid.weighty = 1;

				fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Fügt den "Hallo Welt"-Text hinzu
				JButton btn = new JButton();
				btn.setText("EXIT");
				btn.setSize(100, 40);
				btn.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						fenster.dispatchEvent(new WindowEvent(fenster, WindowEvent.WINDOW_CLOSING));
					}
				});
				grid.gridx = 1;
				grid.gridy = 0;
				panel.add(btn, grid);
				// Kommen Button
				JButton btn_come = new JButton();
				btn_come.setText("Kommen");
				btn_come.setSize(100, 40);
				btn_come.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						App connServer = new App();
						connServer.dbConnect_come(
								"jdbc:sqlserver://hauptserver\\FETISHDESIGN;databaseName=Zeiterfassung", "sa",
								"Basti1990");
					}
				});
				//
				grid.gridx = 3;
				grid.gridy = 1;
				panel.add(btn_come, grid);
				//

				grid.gridx = 2;
				grid.gridy = 0;
				// panel.add(label, grid);
				fenster.add(panel);
				grid.gridx = 2;
				grid.gridy = 2;
				// Textfeld wird erstellt
				// Text und Spaltenanzahl werden dabei direkt gesetzt
				JTextField tfName = new JTextField("", 15);
				// Schriftfarbe wird gesetzt
				tfName.setForeground(Color.BLUE);
				// Hintergrundfarbe wird gesetzt
				tfName.setBackground(Color.YELLOW);
				// Textfeld wird unserem Panel hinzugefügt
				panel.add(tfName, grid);
				// Zeigt das Fenster an
				fenster.setExtendedState(JFrame.MAXIMIZED_BOTH);
				fenster.setUndecorated(true);
				// fenster.setSize(300, 200);
				fenster.setVisible(true);
			}
		};

		// Führe den obigen Quellcode im Event-Dispatch-Thread aus
		SwingUtilities.invokeLater(guiCreator);

	}

	public void dbConnect_come(String db_connect_string, String db_userid, String db_password) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
			System.out.println("connected: Trage Kommen Zeit ein!");
			Statement statement = conn.createStatement();
			String queryString = "select * from Benutzer";
			ResultSet rs = statement.executeQuery(queryString);
			while (rs.next()) {
				System.out.println(rs.getString(5));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
