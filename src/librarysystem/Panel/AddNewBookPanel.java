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
import javax.swing.JTextField;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.LibrarySystemException;
import business.SystemController;
import librarysystem.LibrarySystem;

public class AddNewBookPanel {
	
	private AddNewBookPanel() {
	}

	private static JPanel panel;
	private static JTextField isbn;
	private static JTextField title;
	private static JTextField lastName;
	private static JTextField maxCheckOutLength;
	private static JTextField city;
	private static JTextField state;
	private static JTextField zip;
	private static JTextField cell;

	private static ControllerInterface ci = new SystemController();

	public static Component getNewBookPanel(JFrame frame) {
		return getPanel(frame);
	}

	private static JPanel getPanel(JFrame frame) {
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBackground(Color.WHITE);
		panel.setBounds(100, 100, 870, 468);
		panel.setLayout(null);

		JLabel lblAddNewMember = new JLabel("Add New Member");
		lblAddNewMember.setFont(new Font("Fira Code", Font.BOLD, 18));
		lblAddNewMember.setForeground(Color.DARK_GRAY);
		lblAddNewMember.setBounds(51, 29, 170, 30);
		panel.add(lblAddNewMember);

		id = new JTextField();
		id.setBounds(62, 127, 148, 39);
		panel.add(id);
		id.setColumns(10);

		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId.setBounds(65, 109, 70, 15);
		panel.add(lblId);

		title = new JTextField();
		title.setColumns(10);
		title.setBounds(241, 127, 282, 39);
		panel.add(title);

		JLabel lblId_1 = new JLabel("title");
		lblId_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1.setBounds(244, 109, 254, 15);
		panel.add(lblId_1);

		lastName = new JTextField();
		lastName.setColumns(10);
		lastName.setBounds(560, 127, 282, 39);
		panel.add(lastName);

		JLabel lblId_1_1 = new JLabel("LastName");
		lblId_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_1.setBounds(563, 109, 143, 15);
		panel.add(lblId_1_1);

		maxCheckOutLength = new JTextField();
		maxCheckOutLength.setColumns(10);
		maxCheckOutLength.setBounds(62, 221, 211, 39);
		panel.add(maxCheckOutLength);

		city = new JTextField();
		city.setColumns(10);
		city.setBounds(288, 221, 200, 39);
		panel.add(city);

		JLabel lblId_1_2 = new JLabel("City");
		lblId_1_2.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_2.setBounds(291, 203, 197, 15);
		panel.add(lblId_1_2);

		state = new JTextField();
		state.setColumns(10);
		state.setBounds(503, 221, 174, 39);
		panel.add(state);

		JLabel lblId_1_1_1 = new JLabel("State");
		lblId_1_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_1_1.setBounds(506, 203, 171, 15);
		panel.add(lblId_1_1_1);

		zip = new JTextField();
		zip.setColumns(10);
		zip.setBounds(706, 221, 135, 39);
		panel.add(zip);

		JLabel lblId_1_2_1 = new JLabel("Zip");
		lblId_1_2_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_2_1.setBounds(709, 203, 254, 15);
		panel.add(lblId_1_2_1);

		cell = new JTextField();
		cell.setColumns(10);
		cell.setBounds(62, 301, 232, 39);
		panel.add(cell);

		JLabel lblId_1_1_1_1 = new JLabel("Cell");
		lblId_1_1_1_1.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblId_1_1_1_1.setBounds(65, 283, 229, 15);
		panel.add(lblId_1_1_1_1);

		JLabel lblmaxCheckOutLength = new JLabel("maxCheckOutLength");
		lblmaxCheckOutLength.setFont(new Font("Fira Code Retina", Font.BOLD, 13));
		lblmaxCheckOutLength.setBounds(65, 203, 208, 15);
		panel.add(lblmaxCheckOutLength);

		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBounds(51, 94, 807, 266);
		panel.add(backgroundPanel);
		backgroundPanel.setLayout(null);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(e -> {
			String fN = title.getText();
			String lN = lastName.getText();
			String zipCode = zip.getText();
			String phone = cell.getText();
			if (isbn.getText().equals("") || fN.equals("") || lN.equals("") || maxCheckOutLength.getText().equals("")
					|| city.getText().equals("") || state.getText().equals("") || zipCode.equals("")
					|| cell.getText().equals("")) {
				JOptionPane.showMessageDialog(frame, "Please fill all the fields");
			} else if (!fN.matches("[a-zA-Z]*") || !lN.matches("[a-zA-Z]*")) {
				JOptionPane.showMessageDialog(frame, "Names cannot have numbers");
			} else if (!zipCode.matches("\\d{5}")) {
				JOptionPane.showMessageDialog(frame, "Zip Code should be of 5 digits.");
			} else if (!phone.matches("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$")) {
				JOptionPane.showMessageDialog(frame, "Phone Number is invalid");
			} else {
				Address address = new Address(maxCheckOutLength.getText(), city.getText(), state.getText(), zip.getText());
				LibraryMember member = new LibraryMember(isbn.getText(), title.getText(), lastName.getText(),
						cell.getText(), address);
				try {
					ci.addMember(member);
				} catch (LibrarySystemException err) {
					JOptionPane.showMessageDialog(frame, err.getMessage());
					return;
				}
				clearInputField();
				JOptionPane.showMessageDialog(frame, "Successful Addition of new Member");
			}

		});
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(SystemColor.desktop);
		btnAdd.setBounds(756, 378, 102, 41);
		panel.add(btnAdd);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBackground(Color.LIGHT_GRAY);
		btnCancel.setBounds(642, 378, 102, 41);
		panel.add(btnCancel);

		return panel;

	}

	private static void clearInputField() {
		isbn.setText("");	
		title.setText("");
		lastName.setText("");
		maxCheckOutLength.setText("");
		city.setText("");
		state.setText("");
		zip.setText("");
		cell.setText("");

	}


}
