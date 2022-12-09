package librarysystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;

public class LoginWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = -4332346753166095308L;
	ControllerInterface ci = new SystemController();
	public static final LoginWindow INSTANCE = new LoginWindow();

	private boolean isInitialized = false;

	private JPanel mainPanel;

	private JPanel leftFormPanel;
	private JPanel imagePanel;

	private JTextField username;
	private JPasswordField password;

	private Color commonColor = new Color(35, 55, 62);

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	private JTextField messageBar = new JTextField();

	public void clear() {
		messageBar.setText("");
	}

	/* This class is a singleton */
	private LoginWindow() {
		init();
	}

	public void init() {
		mainPanel = new JPanel();
		defineRightHalf();
		defineLeftHalf();

		mainPanel.setBounds(100, 100, 821, 722);
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setLayout(null);

		mainPanel.add(imagePanel);
		mainPanel.add(leftFormPanel);

		getContentPane().add(mainPanel);
		isInitialized(true);
	}

	private void defineLeftHalf() {

		leftFormPanel = new JPanel();
		leftFormPanel.setBackground(new Color(249, 249, 249));
		leftFormPanel.setBounds(0, 0, 391, 673);
		leftFormPanel.setLayout(null);

		JLabel lblLibrayManagementSystem = new JLabel("LIBRARY");
		lblLibrayManagementSystem.setHorizontalAlignment(SwingConstants.CENTER);
		lblLibrayManagementSystem.setForeground(commonColor);
		lblLibrayManagementSystem.setFont(new Font("Roboto Slab", Font.BOLD, 31));
		lblLibrayManagementSystem.setBounds(12, 141, 326, 32);
		leftFormPanel.add(lblLibrayManagementSystem);

		JLabel lblSystem = new JLabel("SYSTEM");
		lblSystem.setHorizontalAlignment(SwingConstants.CENTER);
		lblSystem.setForeground(commonColor);
		lblSystem.setFont(new Font("Roboto Slab", Font.BOLD, 31));
		lblSystem.setBounds(15, 189, 326, 69);
		leftFormPanel.add(lblSystem);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(getClass().getResource("/librarysystem/book.png")));
		lblNewLabel_2.setBounds(131, 39, 101, 90);
		leftFormPanel.add(lblNewLabel_2);

		JLabel lblManagement = new JLabel("MANAGEMENT");
		lblManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblManagement.setForeground(commonColor);
		lblManagement.setFont(new Font("Roboto Slab", Font.BOLD, 30));
		lblManagement.setBounds(22, 161, 326, 55);
		leftFormPanel.add(lblManagement);

		JPanel formWrapperPanel = new JPanel();
		formWrapperPanel.setBackground(SystemColor.text);
		formWrapperPanel.setBounds(29, 316, 319, 234);
		leftFormPanel.add(formWrapperPanel);
		formWrapperPanel.setLayout(null);

		JPanel usernamePanel = new JPanel();
		usernamePanel.setBounds(12, 12, 289, 80);
		formWrapperPanel.add(usernamePanel);
		usernamePanel.setBackground(SystemColor.text);
		usernamePanel.setLayout(null);

		username = new JTextField();
		username.setBackground(new Color(249, 249, 249));
		username.setFont(new Font("Roboto Slab", Font.PLAIN, 16));
		username.setBounds(12, 41, 256, 38);
		usernamePanel.add(username);
		username.setColumns(10);

		JLabel lblEmail = new JLabel("USERNAME");
		lblEmail.setForeground(commonColor);
		lblEmail.setFont(new Font("Roboto Slab", Font.BOLD, 12));
		lblEmail.setBounds(45, 15, 140, 15);
		usernamePanel.add(lblEmail);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(getClass().getResource("/librarysystem/user.png")));
		lblNewLabel_1.setBounds(12, 0, 48, 45);
		usernamePanel.add(lblNewLabel_1);
		lblEmail.setForeground(commonColor);

		JPanel passwordPanel = new JPanel();
		passwordPanel.setBounds(12, 88, 289, 80);
		formWrapperPanel.add(passwordPanel);
		passwordPanel.setBackground(SystemColor.text);
		passwordPanel.setLayout(null);

		password = new JPasswordField();
		password.setBackground(new Color(249, 249, 249));
		password.setFont(new Font("Roboto Slab", Font.PLAIN, 16));
		password.setColumns(10);
		password.setBounds(12, 41, 256, 38);
		passwordPanel.add(password);

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Roboto Slab", Font.BOLD, 12));
		lblPassword.setBounds(45, 15, 140, 15);
		passwordPanel.add(lblPassword);

		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(getClass().getResource("/librarysystem/padlock.png")));
		lblNewLabel_1_1.setBounds(12, 0, 48, 45);
		passwordPanel.add(lblNewLabel_1_1);

		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setBounds(23, 188, 256, 38);
		formWrapperPanel.add(btnLogin);
		btnLogin.setFont(new Font("Roboto Slab", Font.BOLD, 12));
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(commonColor);
		addLoginButtonListener(btnLogin);

	}

	private void defineRightHalf() {
		imagePanel = new JPanel();
		imagePanel.setBackground(Color.WHITE);
		imagePanel.setBounds(390, 12, 419, 673);
		imagePanel.setBounds(390, 12, 419, 673);
		imagePanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/librarysystem/library-500.png")));
//		lblNewLabel.setBounds(0, 0, 433, 672);
		lblNewLabel.setBounds(-26, -24, 433, 672);

		imagePanel.add(lblNewLabel);

	}

	private void addLoginButtonListener(JButton butn) {
		butn.addActionListener(evt -> {

			try {
				ci.login(username.getText(), password.getText());
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, e.getMessage());
				// e.printStackTrace();
				return;
			}

			LibrarySystem.hideAllWindows();
			PortalWindow.INSTANCE.setUser(username.getText(), SystemController.currentAuth);
			Util.centerFrameOnDesktop(PortalWindow.INSTANCE);
			PortalWindow.INSTANCE.setVisible(true);
		});
	}

	public void reset() {
		username.setText("");
		password.setText("");
	}

}
