package librarysystem.Panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import business.DTO.BookWithPastDueDateDTO;
import business.DTO.BookWithPastDueDateDTO.BookWithPastDueDateInternalDTO;
import resources.ThemeColor;

public class SearchBookPanel {

	private SearchBookPanel() {
	}

	private static JPanel panel;
	private static JTextField searchTextField;
	private static JTable table;
	private static ControllerInterface ci = new SystemController();

	public static Component getNewSearchBookPanel(JFrame frame) {
		return getPanel(frame);
	}

	private static JPanel getPanel(JFrame frame) {
		panel = new JPanel();
		panel.setBounds(100, 100, 892, 593);
		panel.setLayout(null);
		
		

		JPanel searchPanel = new JPanel();
		searchPanel.setBounds(42, 43, 616, 96);
		searchPanel.setBackground(ThemeColor.backgroundColor);
//		searchPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));	
		panel.add(searchPanel);
		searchPanel.setLayout(null);
		
		JLabel AllIDsLabel = new JLabel("Overdue Book List");
		AllIDsLabel.setFont(ThemeColor.titleText);
		AllIDsLabel.setBounds(42, 10, 319, 36);
		panel.add(AllIDsLabel);	

		searchTextField = new JTextField();
		searchTextField.setToolTipText("Search Book with expired duedate");
		searchTextField.setBounds(15, 40, 319, 36);
		searchPanel.add(searchTextField);
		searchTextField.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Roboto Slab", Font.BOLD, 13));
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setBackground(new Color(0, 0, 139));
		btnSearch.setBounds(345, 40, 90, 36);
		searchPanel.add(btnSearch);

		JLabel lblBookIsbn = new JLabel("BOOK ISBN");
		lblBookIsbn.setFont(ThemeColor.formLabel);
		lblBookIsbn.setBounds(15, 15, 130, 15);
		searchPanel.add(lblBookIsbn);

		JPanel listPanel = new JPanel();
		listPanel.setBackground(Color.WHITE);
		listPanel.setBounds(42, 174, 616, 341);
		panel.add(listPanel);
		listPanel.setLayout(null);

//		JLabel lblCheckoutRecords = new JLabel("Book Copy Records With Expired DueDate");
//		lblCheckoutRecords.setFont(new Font("Roboto Slab", Font.BOLD, 16));
//		lblCheckoutRecords.setBounds(33, 35, 319, 21);
//		listPanel.add(lblCheckoutRecords);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSurrendersFocusOnKeystroke(true);

		DefaultTableModel model = new DefaultTableModel();
		
		String[] column = { "Copy Number", "Member", "Due Date" };

		model.setColumnIdentifiers(column);
		
		table.setModel(model);

		table.setBackground(new Color(249, 249, 249));
		table.setBounds(44, 50, 528, 280);
		table.setFont(ThemeColor.normalText);
		listPanel.add(table);

		listPanel.setVisible(false);

		btnSearch.addActionListener(e -> {

			int iRowCount = model.getRowCount();
			for (int i = 0; i < iRowCount; i++)
				model.removeRow(0);

			if (searchTextField.getText().equals("")) {
				listPanel.setVisible(false);
				JOptionPane.showMessageDialog(frame, "Search Item is empty");
			} else {
				try {
					BookWithPastDueDateDTO books = ci.getOverdueBooks(searchTextField.getText());

					if (books.getOverDueLists().size() != 0) {
						
						model.addRow(column);
						for (BookWithPastDueDateInternalDTO entry : books.getOverDueLists()) {
							Object[] row = new Object[3];
							row[0] = entry.getCopyNumber();
							row[1] = entry.getMemberId();
							row[2] = entry.getDueDate();
							model.addRow(row);

						}
						JLabel lblBook = new JLabel("Book :"+ books.getTitle());
						lblBook.setBounds(20, 20, 300, 15);
						lblBook.setFont(ThemeColor.formLabel);
						listPanel.add(lblBook);

						listPanel.setVisible(true);

					} else {
						listPanel.setVisible(false);
						JOptionPane.showMessageDialog(frame, "No Copies with expired DueDate found");
					}
				} catch (LibrarySystemException err) {
					listPanel.setVisible(false);
					JOptionPane.showMessageDialog(frame, err.getMessage());
				}
			}
			;

		});

		return panel;

	}

}
