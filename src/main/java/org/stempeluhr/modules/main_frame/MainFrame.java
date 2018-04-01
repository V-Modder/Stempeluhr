package org.stempeluhr.modules.main_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
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
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.stempeluhr.modules.coming.ComingController;
import org.stempeluhr.modules.common.ActionPanel;
import org.stempeluhr.modules.common.Constants;
import org.stempeluhr.modules.common.ModuleFrame;
import org.stempeluhr.modules.going.GoingController;
import org.stempeluhr.repository.ConnectionTestRepository;

public class MainFrame extends ModuleFrame {

	private static final long serialVersionUID = 1L;

	private JLayeredPane contentPane;
	private JLabel btnCome;
	private JLabel btnLeave;

	private ActionPanel goingPanel;
	private ActionPanel comingPanel;
	private GoingController goingController;
	private ComingController comingController;

	private String stdtext = "<html><body><div style='text-align: center;color:gray'><span style='font-size:40;'><bold>Zeiterfassung</bold></span><br><br>Terminal 1</div></body></html>";

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
			} else if (evt.getSource() == this.btnCome) {
				this.openComingPanel();
			}
		}
	}

	private void openGoingPanel() {
		this.goingPanel.showPanel();
	}

	private void openComingPanel() {
		this.comingPanel.showPanel();
	}

	public MainFrame() throws UnknownHostException {
		setBackground(Color.BLACK);
		ConnectionTestRepository connectionTestRepository = new ConnectionTestRepository();
		boolean test = connectionTestRepository.testDbConnection();
		// if (!test) {
		// JOptionPane.showMessageDialog(null, "Es konnte keine Verbindung
		// zu\nFetish-Design hergestellt werden!" + "\n\nVerbindung
		// überprüfen\n\nTestConnection() = " + test, "Achtung", JOptionPane.OK_OPTION);
		// System.exit(0);
		// }

		final InetAddress ip = InetAddress.getLocalHost();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// JOptionPane.showMessageDialog(null, ip.getHostName());
		if (!Constants.isDebug) {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.setUndecorated(true); // Ohne Steuerung
		}
		this.setBounds(0, 0, 692, 320);
		final JLabel lbl_method = new JLabel("");

		this.comingController = new ComingController();
		this.goingController = new GoingController();

		changeJLabel(lbl_method, stdtext);
		lbl_method.setForeground(Color.WHITE);
		lbl_method.setFont(new Font("Tahoma", Font.PLAIN, 24));
		this.contentPane = new JLayeredPane();
		this.contentPane.setBackground(Color.BLACK);
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(contentPane);
		this.btnCome = new JLabel("");
		this.btnCome.setBackground(null);
		this.btnCome.setIcon(new ImageIcon(MainFrame.class.getResource("login-128.png")));
		this.btnCome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.btnCome.setPreferredSize(new Dimension(150, 40));
		this.btnCome.addMouseListener(this);
		this.contentPane.add(btnCome, BorderLayout.WEST);

		this.btnLeave = new JLabel("");
		this.btnLeave.setBackground(null);
		this.btnLeave.setIcon(new ImageIcon(MainFrame.class.getResource("logout-128.png")));
		this.btnLeave.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.btnLeave.setPreferredSize(new Dimension(150, 40));
		this.btnLeave.addMouseListener(this);
		contentPane.add(this.btnLeave, BorderLayout.EAST);

		final JLabel lblTime = new JLabel("Loading...");
		lblTime.setForeground(Color.WHITE);
		lblTime.setBackground(Color.BLACK);
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(lblTime.getFont().deriveFont(22f));
		lblTime.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(7, 0, 0, 0, Color.decode("#ff80fe")), new EmptyBorder(0, 0, 30, 0)));
		lblTime.setOpaque(true);
		new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd.MM.yyyy HH:mm:ss");
				lblTime.setText(sdf.format(new Date()));
			}
		}).start();
		this.contentPane.add(lblTime, BorderLayout.NORTH);

		this.comingPanel = new ActionPanel();
		this.comingPanel.setBounds(0, 0, 692, 320);
		this.comingPanel.setTitel("Kommen");
		this.comingPanel.setController(this.comingController);
		this.contentPane.add(this.comingPanel, -1);

		this.goingPanel = new ActionPanel();
		this.goingPanel.setBounds(0, 0, 692, 320);
		this.goingPanel.setTitel("Gehen");
		this.goingPanel.setController(this.goingController);
		this.contentPane.add(this.goingPanel, -1);

		JLabel img_footer = new JLabel("");
		img_footer.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "IP-ADRESSE: " + ip.getHostAddress() + "\nHost-Name: " + ip.getHostName(), "Details", JOptionPane.OK_CANCEL_OPTION);
			}

		});
		img_footer.setBackground(Color.BLACK);
		img_footer.setOpaque(true);
		img_footer.setBorder(new EmptyBorder(10, 0, 0, 0));// top,left,bottom,right
		contentPane.add(img_footer, BorderLayout.SOUTH);
		img_footer.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("Logo_Transparent_1.png")).getImage().getScaledInstance(610, 80, Image.SCALE_SMOOTH)));
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
