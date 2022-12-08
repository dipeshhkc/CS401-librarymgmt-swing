package librarysystem.Panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import business.ControllerInterface;
import business.SystemController;
import librarysystem.LibrarySystem;

public class AddNewBookPanel {

	private AddNewBookPanel() {
	}

	private static JPanel panel;
	private static JTextField isbn;
	private static JTextField title;
	private static JTextField firstName;
	private static JTextField lastName;
	private static JTextField street;
	private static JTextField city;
	private static JTextField state;
	private static JTextField zip;
	private static JTextField maxCheckoutLength;
	private static ControllerInterface ci = new SystemController();

	public static Component getNewBookPanel(JFrame frame) {
		return getPanel(frame);
	}

	private static JPanel getPanel(JFrame frame) {
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frame.setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1163, 700);
		panel.setLayout(null);

		JLabel lblAddNewMember = new JLabel("Add New Book");
		lblAddNewMember.setFont(new Font("Fira Code", Font.BOLD, 18));
		lblAddNewMember.setForeground(Color.DARK_GRAY);
		lblAddNewMember.setBounds(51, 0, 170, 30);
		panel.add(lblAddNewMember);

		JPanel bookPanel = new JPanel();
		bookPanel.setBounds(51, 46, 807, 161);
		panel.add(bookPanel);
		bookPanel.setLayout(null);

		isbn = new JTextField();
		isbn.setBounds(12, 30, 141, 39);
		bookPanel.add(isbn);
		isbn.setColumns(10);

		JLabel lblId_1 = new JLabel("ISBN");
		lblId_1.setBounds(15, 12, 138, 15);
		bookPanel.add(lblId_1);
		lblId_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));

		title = new JTextField();
		title.setBounds(186, 30, 282, 39);
		bookPanel.add(title);
		title.setColumns(10);

		JLabel lblId_1_1 = new JLabel("Title");
		lblId_1_1.setBounds(189, 12, 143, 15);
		bookPanel.add(lblId_1_1);
		lblId_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));

		maxCheckoutLength = new JTextField();
		maxCheckoutLength.setColumns(10);
		maxCheckoutLength.setBounds(519, 30, 220, 39);
		bookPanel.add(maxCheckoutLength);

		JLabel lblId_1_2 = new JLabel("Max Checkout Length");
		lblId_1_2.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_2.setBounds(522, 12, 217, 15);
		bookPanel.add(lblId_1_2);

		JLabel lblId_1_4 = new JLabel("Authors");
		lblId_1_4.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_4.setBounds(15, 82, 138, 15);
		bookPanel.add(lblId_1_4);

		JButton authorAdd = new JButton("Add ");

		authorAdd.setForeground(Color.WHITE);
		authorAdd.setBackground(new Color(75, 0, 130));
		authorAdd.setBounds(12, 99, 76, 25);
		bookPanel.add(authorAdd);

		JButton btnAdd = new JButton("Add Book");

		btnAdd.addActionListener(e -> {

			JOptionPane.showMessageDialog(frame, "Successful Addition of new Book");
		});
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(SystemColor.desktop);
		btnAdd.setBounds(984, 166, 102, 41);
		panel.add(btnAdd);

		JButton btnCancel = new JButton("Clear");

		btnCancel.addActionListener(e -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBackground(Color.LIGHT_GRAY);
		btnCancel.setBounds(870, 166, 102, 41);
		panel.add(btnCancel);

		JPanel authorPanel = new JPanel();
		authorPanel.setBackground(Color.WHITE);
		authorPanel.setBounds(50, 231, 1101, 315);
		panel.add(authorPanel);
		authorPanel.setLayout(null);

		authorAdd.addActionListener(e -> {
			authorPanel.setVisible(true);

		});

		JScrollPane scrollView = new JScrollPane();
		scrollView.setBounds(0, 12, 807, 266);
		authorPanel.add(scrollView);
		scrollView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollView.setLayout(null);

		firstName = new JTextField();
		firstName.setColumns(10);
		firstName.setBounds(12, 30, 282, 39);
		scrollView.add(firstName);

		JLabel lblId_1_3 = new JLabel("FirstName");
		lblId_1_3.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_3.setBounds(15, 12, 254, 15);
		scrollView.add(lblId_1_3);

		lastName = new JTextField();
		lastName.setColumns(10);
		lastName.setBounds(306, 30, 282, 39);
		scrollView.add(lastName);

		JLabel lblId_1_1_2 = new JLabel("LastName");
		lblId_1_1_2.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_1_2.setBounds(309, 12, 143, 15);
		scrollView.add(lblId_1_1_2);

		JTextArea bio = new JTextArea();
		bio.setBounds(15, 169, 437, 72);
		scrollView.add(bio);

		street = new JTextField();
		street.setColumns(10);
		street.setBounds(12, 99, 211, 39);
		scrollView.add(street);

		JLabel lblStreet_2 = new JLabel("Street");
		lblStreet_2.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblStreet_2.setBounds(15, 81, 208, 15);
		scrollView.add(lblStreet_2);

		city = new JTextField();
		city.setColumns(10);
		city.setBounds(241, 99, 200, 39);
		scrollView.add(city);

		JLabel lblId_1_2_2 = new JLabel("City");
		lblId_1_2_2.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_2_2.setBounds(244, 81, 197, 15);
		scrollView.add(lblId_1_2_2);

		state = new JTextField();
		state.setColumns(10);
		state.setBounds(454, 99, 174, 39);
		scrollView.add(state);

		JLabel lblId_1_1_1_1 = new JLabel("State");
		lblId_1_1_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_1_1_1.setBounds(457, 81, 171, 15);
		scrollView.add(lblId_1_1_1_1);

		zip = new JTextField();
		zip.setColumns(10);
		zip.setBounds(646, 99, 135, 39);
		scrollView.add(zip);

		JLabel lblId_1_2_1_1 = new JLabel("Zip");
		lblId_1_2_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_2_1_1.setBounds(649, 81, 254, 15);
		scrollView.add(lblId_1_2_1_1);

		JLabel lblStreet_1_1 = new JLabel("Bio");
		lblStreet_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblStreet_1_1.setBounds(20, 153, 208, 15);
		scrollView.add(lblStreet_1_1);

		JButton btnCancel_1 = new JButton("Cancel");
		btnCancel_1.setBounds(829, 226, 102, 41);
		authorPanel.add(btnCancel_1);
		btnCancel_1.setForeground(Color.WHITE);
		btnCancel_1.setBackground(Color.LIGHT_GRAY);

		JButton btnAddAuthor = new JButton("Add Author");
		btnAddAuthor.setBounds(943, 226, 119, 41);
		authorPanel.add(btnAddAuthor);
		btnAddAuthor.setForeground(Color.WHITE);
		btnAddAuthor.setBackground(SystemColor.desktop);

		authorPanel.setVisible(false);

		return panel;

	}

	private static void clearInputField() {
		isbn.setText("");
		title.setText("");
		lastName.setText("");
		maxCheckoutLength.setText("");
		city.setText("");
		state.setText("");
		zip.setText("");
	}

}
