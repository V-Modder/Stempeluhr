package org.stempeluhr.modules.main_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.stempeluhr.modules.common.Constants;
import org.stempeluhr.modules.common.ModuleFrame;
import org.stempeluhr.modules.going.GoingPanel;

public class MainFrame extends ModuleFrame {

	private static final long serialVersionUID = 1L;
	private JLayeredPane contentPane;
	private JLabel btnLeave;

	private GoingPanel goingPanel;

	private String state = ""; // true = coming
	private String stdtext = "<html><body><div style='text-align: center;color:gray'><span style='font-size:40;'><bold>Zeiterfassung</bold></span><br><br>Terminal 1</div></body></html>";

	public static java.util.Timer timer1;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(Constants.CommandMousePressed)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			MouseEvent evt = (MouseEvent) e.getSource();
			if (evt.getSource() == this.btnLeave) {
				this.openGoingPanel();
			}
		}
	}

	private void openGoingPanel() {
		this.goingPanel.showPanel();
	}

	/**
	 * Create the frame.
	 * 
	 * @throws UnknownHostException
	 */
	public MainFrame() throws UnknownHostException {
		setBackground(Color.BLACK);
		// boolean test = this.benutzerRepository.testConnection();
		// if (!test) {
		// JOptionPane.showMessageDialog(null, "Es konnte keine Verbindung
		// zu\nFetish-Design hergestellt werden!" + "\n\nVerbindung
		// überprüfen\n\nTestConnection() = " + test, "Achtung", JOptionPane.OK_OPTION);
		// System.exit(0);
		// }

		final InetAddress ip = InetAddress.getLocalHost();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// JOptionPane.showMessageDialog(null, ip.getHostName());
		if (ip.getHostName().contains("terminal")) {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.setUndecorated(true); // Ohne Steuerung
		}
		this.setBounds(0, 0, 692, 320);
		final JLabel lbl_method = new JLabel("");

		changeJLabel(lbl_method, stdtext);
		lbl_method.setForeground(Color.WHITE);
		lbl_method.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane = new JLayeredPane();
		contentPane.setBackground(Color.BLACK);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JLabel btnCome = new JLabel("");
		btnCome.setBackground(null);
		btnCome.setIcon(new ImageIcon(MainFrame.class.getResource("login-128.png")));

		// btnCome.addActionListener(new ActionListener() {
		btnCome.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent arg0) {
				state = "Come";
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				changeJLabel(lbl_method,
						"<html><body><div style='text-align: center;'><span style='font-size:40'><bold>KOMMEN</bold></span><br><br>Bitte Chip</div></body></html>");
			}

		});
		btnCome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCome.setPreferredSize(new Dimension(150, 40));
		contentPane.add(btnCome, BorderLayout.WEST);

		final JLabel lblNewLabel = new JLabel("Loading...");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(lblNewLabel.getFont().deriveFont(22f));
		lblNewLabel.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(7, 0, 0, 0, Color.decode("#ff80fe")),
				new EmptyBorder(0, 0, 30, 0)));
		lblNewLabel.setOpaque(true);
		new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd.MM.yyyy HH:mm:ss");
				lblNewLabel.setText(sdf.format(new Date()));
			}
		}).start();
		contentPane.add(lblNewLabel, BorderLayout.NORTH);

		this.btnLeave = new JLabel("");
		this.btnLeave.setIcon(new ImageIcon(MainFrame.class.getResource("logout-128.png")));
		this.btnLeave.addMouseListener(this);
		// new MouseAdapter() {
		// public void mousePressed(MouseEvent arg0) {
		// state = "Leave";
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		//
		// // changeJLabel(lbl_method, "<html><body><div style='text-align:
		// center;'><span
		// // style='font-size:40'><bold>GEHEN</bold></span><br><br>Bitte
		// // Chip</div></body></html>");
		//
		// }
		// });
		this.btnLeave.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.btnLeave.setPreferredSize(new Dimension(150, 40));
		contentPane.add(this.btnLeave, BorderLayout.EAST);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// @Override
		// public void keyPressed(KeyEvent e) {
		// if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		//
		// if (state == "Come") // Kommen
		// {
		// Benutzer userID =
		// MainFrame.this.benutzerRepository.getBy(Long.parseLong(textField.getText()));
		// if (userID == null) {
		// changeJLabel(lbl_method, "<html><body><div style='text-align: center;'><span
		// style='font-size:20;color:red'>Achtung<br>Benutzer nicht
		// gefunden</span></div></body></html>");
		// textField.setText("");
		// return;
		// }
		// Boolean iscome = false;//
		// MainFrame.this.zeitRepository.iscomeyet(userID.getId());
		// if (iscome == true) {
		// changeJLabel(lbl_method, "<html><body><div style='text-align: center;'><span
		// style='font-size:20;color:red'>Achtung<br>Es wurde falsch gebucht.<br>Bitte
		// erst GEHEN!</span></div></body></html>");
		// textField.setText("");
		// return;
		// }
		// Zeit startTime = new Zeit();
		// startTime.setUserid(userID.getId());
		// startTime.setStartTime(new Date());
		// MainFrame.this.zeitRepository.save(startTime); // 0003733748
		// textField.setText("");
		// changeJLabel(lbl_method, "Viel Spaß beim Arbeiten");
		// if (timer.isRunning()) {
		// timer.stop();
		// changeJLabel(lbl_method, "<html><body><div style='text-align: center;'><span
		// style='font-size:20'>Viel Spass beim Arbeiten<br><br>" +
		// userID.getFirstname() + " " + userID.getLastname() +
		// "</span></div></body></html>");
		// timer.start();
		// }
		// } else if (state == "Leave") // Gehen
		// {
		// Benutzer userID =
		// MainFrame.this.benutzerRepository.getBy(Long.parseLong(textField.getText()));
		// if(userID == null) {
		// changeJLabel(lbl_method, "<html><body><div style='text-align: center;'><span
		// style='font-size:20;color:red'>Achtung<br>Benutzer nicht
		// gefunden</span></div></body></html>");
		// textField.setText("");
		// return;
		// }
		// Zeit endTime =
		// MainFrame.this.zeitRepository.getStartedTimeBy(Long.valueOf(userID.getUserid()));
		// if (endTime == null) {
		// // JOptionPane.showMessageDialog(null, "Benutzer nicht gefunden", "Achtung",
		// // JOptionPane.OK_OPTION);
		// changeJLabel(lbl_method, "<html><body><div style='text-align: center;'><span
		// style='font-size:20;color:red'>Achtung<br>Es wurde falsch gebucht.<br>Bitte
		// erst Kommen!</span></div></body></html>");
		// textField.setText("");
		// return;
		// }
		// endTime.setEndTime(new Date());
		// endTime.setTotalTime(DateHelper.getDifferenceInHours(endTime.getStartTime(),
		// endTime.getEndTime()));
		// // Solved error by using update insted of save :-(, gn8
		// MainFrame.this.zeitRepository.update(endTime); // meine ChipId: 0133016489
		// textField.setText("");
		// if (timer.isRunning()) {
		// timer.stop();
		// String workingTime = "";
		// double hours = (double) endTime.getTotalTime();
		// int hour = (int) hours;
		// double minute = hours - (int) (hours);
		// minute = Math.round(minute * 60);
		// if (hour > 0) {
		// workingTime += (int) hour + "h ";
		// }
		// workingTime += (int) minute + "min";
		// changeJLabel(lbl_method, "<html><body><div style='text-align: center;'><span
		// style='font-size:20'>Wir wünschen einen<br> schönen Feierabend<br><br>" +
		// userID.getFirstname() + " " + userID.getLastname() + "<br>Gerabeitete Zeit:"
		// + workingTime + "</span></div></body></html>");
		// timer.start();
		// textField.setText("");
		// }
		//
		// } else {
		// changeJLabel(lbl_method, "<html><body><div style='text-align: center;'><span
		// style='font-size:20;color:red'>Bitte zuerst <br>Kommen oder Gehen
		// auswählen!!<br>Bitte erst Kommen!</span></div></body></html>");
		// textField.setText("");
		// timer.start();
		// }

		// }
		// }
		// });

		// panel.add(lbl_method);
		this.goingPanel = new GoingPanel();
		this.goingPanel.setBounds(0, 0, 692, 320);
		this.contentPane.add(this.goingPanel, 0);
		this.contentPane.setComponentZOrder(this.goingPanel, 0);

		JLabel img_footer = new JLabel("");
		img_footer.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null,
						"IP-ADRESSE: " + ip.getHostAddress() + "\nHost-Name: " + ip.getHostName(), "Details",
						JOptionPane.OK_CANCEL_OPTION);
			}

		});
		img_footer.setBackground(Color.BLACK);
		img_footer.setOpaque(true);
		img_footer.setBorder(new EmptyBorder(10, 0, 0, 0));// top,left,bottom,right
		contentPane.add(img_footer, BorderLayout.SOUTH);
		img_footer.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("Logo_Transparent_1.png"))
				.getImage().getScaledInstance(610, 80, Image.SCALE_SMOOTH)));
		this.setTitle("Stempel-Uhr");
		this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("icon175x175.png")).getImage());
	}

	private void changeJLabel(final JLabel label, final String text) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				label.setText(text);
				repaint();
			}
		});
	}

}
