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
	private static final String Standardtext = "<html><body><div style='text-align: center;color:gray'><span style='font-size:40;'><bold>Zeiterfassung</bold></span><br><br>Terminal 1</div></body></html>";

	private JLayeredPane contentPane;
	private JLabel btnCome;
	private JLabel btnLeave;
	private JLabel lblTime;
	private Timer timer;
	private JLabel img_footer;
	private JLabel lbl_method;

	private ActionPanel goingPanel;
	private ActionPanel comingPanel;
	private GoingController goingController;
	private ComingController comingController;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.timer) {
			SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd.MM.yyyy HH:mm:ss");
			this.lblTime.setText(sdf.format(new Date()));
		} else if (e.getActionCommand().equals(Constants.CommandMousePressed)) {
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
		setBackground(Color.BLACK);
		ConnectionTestRepository connectionTestRepository = new ConnectionTestRepository();
		connectionTestRepository.testDbConnection();
		// if (!test) {
		// JOptionPane.showMessageDialog(null, "Es konnte keine Verbindung
		// zu\nFetish-Design hergestellt werden!" + "\n\nVerbindung
		// überprüfen\n\nTestConnection() = " + test, "Achtung", JOptionPane.OK_OPTION);
		// System.exit(0);
		// }

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (!Constants.isDebug) {
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.setUndecorated(true);
		}
		this.setBounds(0, 0, 692, 320);

		this.comingController = new ComingController();
		this.goingController = new GoingController();

		this.lbl_method = new JLabel("");
		this.lbl_method.setText(Standardtext);
		this.lbl_method.setForeground(Color.WHITE);
		this.lbl_method.setFont(new Font("Tahoma", Font.PLAIN, 24));
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

		this.lblTime = new JLabel("Loading...");
		this.lblTime.setForeground(Color.WHITE);
		this.lblTime.setBackground(Color.BLACK);
		this.lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblTime.setFont(lblTime.getFont().deriveFont(22f));
		this.lblTime.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(7, 0, 0, 0, Color.decode("#ff80fe")), new EmptyBorder(0, 0, 30, 0)));
		this.lblTime.setOpaque(true);
		this.contentPane.add(lblTime, BorderLayout.NORTH);

		this.timer = new Timer(1000, this);
		this.timer.start();

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

		this.img_footer = new JLabel("");
		this.img_footer.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				MainFrame.this.actionPerformed(new ActionEvent(this, 1, ShowHelpCommand));
			}
		});

		this.img_footer.setBackground(Color.BLACK);
		this.img_footer.setOpaque(true);
		this.img_footer.setBorder(new EmptyBorder(10, 0, 0, 0));// top,left,bottom,right
		this.contentPane.add(this.img_footer, BorderLayout.SOUTH);
		this.img_footer.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("Logo_Transparent_1.png")).getImage().getScaledInstance(610, 80, Image.SCALE_SMOOTH)));
		this.setTitle("Stempel-Uhr");
		this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("icon175x175.png")).getImage());
	}
}
