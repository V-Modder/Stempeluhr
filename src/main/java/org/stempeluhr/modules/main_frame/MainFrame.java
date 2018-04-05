package org.stempeluhr.modules.main_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Inet4Address;
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

import org.stempeluhr.modules.coming.ComingController;
import org.stempeluhr.modules.common.ActionPanel;
import org.stempeluhr.modules.common.Constants;
import org.stempeluhr.modules.common.ModuleFrame;
import org.stempeluhr.modules.going.GoingController;
import org.stempeluhr.repository.ConnectionTestRepository;

public class MainFrame extends ModuleFrame {

	private static final long serialVersionUID = 1L;
	private static final String ShowHelpCommand = "show_help_command";

	private JLayeredPane contentPane;
	private JPanel mainPanel;
	private ActionPanel goingPanel;
	private ActionPanel comingPanel;

	private JLabel btnCome;
	private JLabel btnLeave;
	private JLabel lblTime;
	private Timer timer;
	private JLabel img_footer;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.timer) {
			SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd.MM.yyyy HH:mm:ss");
			this.lblTime.setText(sdf.format(new Date()));
			return;
		} else if (e.getActionCommand() != null && e.getActionCommand().equals(Constants.CommandMousePressed)) {
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
		} else if (e.getActionCommand().equals(ShowHelpCommand)) {
			JOptionPane.showMessageDialog(null, "IP-ADRESSE: " + this.getHostIpAddresses(), "Details", JOptionPane.OK_CANCEL_OPTION);
		}
	}

	private String getHostIpAddresses() {
		String ip = "";
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
			String hostname = "";
			for (InetAddress inetAddress : allMyIps) {
				if (inetAddress instanceof Inet4Address) {
					if (!ip.isEmpty()) {
						ip += ",\n";
					}
					ip += inetAddress.getHostAddress();
					hostname = inetAddress.getHostName();
				}
			}
			ip += "\nHost-Name: " + hostname;
		} catch (UnknownHostException e) {
		}

		return ip;
	}

	private void openGoingPanel() {
		this.goingPanel.showPanel();
	}

	private void openComingPanel() {
		this.comingPanel.showPanel();
	}

	public MainFrame() throws UnknownHostException {
		ConnectionTestRepository connectionTestRepository = new ConnectionTestRepository();
		connectionTestRepository.testDbConnection();
		// if (!test) {
		// JOptionPane.showMessageDialog(null, "Es konnte keine Verbindung
		// zu\nFetish-Design hergestellt werden!" + "\n\nVerbindung
		// überprüfen\n\nTestConnection() = " + test, "Achtung", JOptionPane.OK_OPTION);
		// System.exit(0);
		// }

		this.setBackground(Color.BLACK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 610, 320);
		if (!Constants.isDebug) {
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.setUndecorated(true);
			this.setBounds(0, 0, 610, 320);
		}
		this.setTitle("Stempel-Uhr");
		this.setIconImage(new ImageIcon(this.getClass().getResource("icon175x175.png")).getImage());

		this.contentPane = new JLayeredPane();
		this.contentPane.setBackground(Color.BLACK);
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.contentPane.setPreferredSize(new Dimension(610, 320));
		this.setContentPane(contentPane);

		this.mainPanel = new JPanel();
		this.mainPanel.setBackground(Color.BLACK);
		this.mainPanel.setPreferredSize(new Dimension(610, 320));
		this.mainPanel.setBounds(0, 0, 610, 320);
		this.mainPanel.setLayout(new BorderLayout());
		this.contentPane.add(this.mainPanel);
		this.contentPane.setLayer(this.mainPanel, 0);

		this.btnCome = new JLabel("");
		this.btnCome.setBackground(null);
		this.btnCome.setIcon(new ImageIcon(MainFrame.class.getResource("login-128.png")));
		this.btnCome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.btnCome.setPreferredSize(new Dimension(150, 40));
		this.btnCome.addMouseListener(this);
		this.mainPanel.add(btnCome, BorderLayout.WEST);

		this.btnLeave = new JLabel("");
		this.btnLeave.setBackground(null);
		this.btnLeave.setIcon(new ImageIcon(MainFrame.class.getResource("logout-128.png")));
		this.btnLeave.setFont(new Font("Tahoma", Font.PLAIN, 18));
		this.btnLeave.setPreferredSize(new Dimension(150, 40));
		this.btnLeave.addMouseListener(this);
		this.mainPanel.add(this.btnLeave, BorderLayout.EAST);

		this.lblTime = new JLabel("Loading...");
		this.lblTime.setForeground(Color.WHITE);
		this.lblTime.setBackground(Color.BLACK);
		this.lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblTime.setFont(lblTime.getFont().deriveFont(22f));
		this.lblTime.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(7, 0, 0, 0, Color.decode("#ff80fe")), new EmptyBorder(0, 0, 30, 0)));
		this.lblTime.setOpaque(true);
		this.mainPanel.add(lblTime, BorderLayout.NORTH);

		this.timer = new Timer(1000, this);
		this.timer.start();

		this.img_footer = new JLabel("");
		this.img_footer.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				MainFrame.this.actionPerformed(new ActionEvent(this, 1, ShowHelpCommand));
			}
		});
		this.img_footer.setBackground(Color.BLACK);
		this.img_footer.setOpaque(true);
		this.img_footer.setBorder(new EmptyBorder(10, 0, 0, 0));
		this.img_footer.setIcon(this.getScaledImage("Logo_Transparent_1.png", 610, 80));
		this.mainPanel.add(this.img_footer, BorderLayout.SOUTH);

		this.comingPanel = new ActionPanel();
		this.comingPanel.setBounds(0, 0, 610, 320);
		this.comingPanel.setTitel("Kommen");
		this.comingPanel.setController(new ComingController());
		this.contentPane.add(this.comingPanel);
		this.contentPane.setLayer(this.comingPanel, 1);

		this.goingPanel = new ActionPanel();
		this.goingPanel.setBounds(0, 0, 610, 320);
		this.goingPanel.setTitel("Gehen");
		this.goingPanel.setController(new GoingController());
		this.contentPane.add(this.goingPanel);
		this.contentPane.setLayer(this.goingPanel, 2);
	}

	private ImageIcon getScaledImage(String resourceName, int width, int height) {
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(resourceName));
		Image image = imageIcon.getImage();
		Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		return new ImageIcon(newImage);
	}
}
