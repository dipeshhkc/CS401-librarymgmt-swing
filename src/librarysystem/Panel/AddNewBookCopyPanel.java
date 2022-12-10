package librarysystem.Panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import librarysystem.LibWindow;

public class AddNewBookCopyPanel extends JFrame  implements LibWindow{

	private static final long serialVersionUID = 5538744765326735797L;
	
	private boolean isInitialized = false;
	private JPanel mainPanel;
	private JFrame parentFrame;
	private JTextField txtISBN;
	private JTextField txtTitle;
	private JTextField txtAuthor;
	private JTextField txtCheckoutLeng;
	private JTextField txtCopies;
	
	ControllerInterface ci = new SystemController();
	public static final AddNewBookCopyPanel INSTANCE = new AddNewBookCopyPanel();

	public AddNewBookCopyPanel() {
		init();		
	}
	
	public void init() {
		
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		//mainPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		//--north-----------------------------------------------------------------
		JPanel northPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) northPanel.getLayout();
		flowLayout.setVgap(30);
		JLabel northLabel = new JLabel("Add New Book Copy");
		northLabel.setFont(new Font("Fira Code Retina", Font.BOLD, 20));
		northPanel.add(northLabel);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		

		//--middle-----------------------------------------------------------------
		JPanel middlePanel = new JPanel();
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		middlePanel.setLayout(new BorderLayout(0, 0));	

		//-------------list--------------------------------
		JPanel listPanel = new JPanel();
		middlePanel.add(listPanel,BorderLayout.NORTH);
		listPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		txtISBN 	= getLblAndTxt(listPanel, "Book ISBN", true);
		txtTitle 	= getLblAndTxt(listPanel, "Title",false);
		txtAuthor 	= getLblAndTxt(listPanel, "Authors",false);
		txtCheckoutLeng = getLblAndTxt(listPanel, "Max Checkout Length",false);
		txtCopies 	= getLblAndTxt(listPanel, "Current Copies",false);
		
		JLabel label = new JLabel("");
		listPanel.add(label);
		
		//-------------Search-------------------
		JPanel AddPanel = new JPanel();
		listPanel.add(AddPanel);
		
		JButton btnSearch = new JButton("Find Book");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Book b = ci.getBook(txtISBN.getText());
					updateBook(b);	
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(parentFrame,e1.getMessage());
					return;
				}

			}
		});
		
		AddPanel.add(btnSearch);
		
		//-------------Add-------------------
		JButton btnAddCopy = new JButton("Add Copy");
		btnAddCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Book b = ci.addNewBookCopy(txtISBN.getText());
					updateBook(b);	
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(parentFrame,e1.getMessage());
					return;
				}
			}
		});
		
		AddPanel.add(btnAddCopy);
		
	}
	
	private void updateBook(Book b) {
		txtTitle.setText(" " + b.getTitle());
		txtCheckoutLeng.setText(" " + b.getMaxCheckoutLength());
		List<String> strAuth = new ArrayList<>();
		for(Author au: b.getAuthors()) {
			strAuth.add(au.getFirstName() + " " + au.getLastName());
		}
		txtAuthor.setText(" " + strAuth.toString());
		txtCopies.setText(" " + b.getNumCopies());		
	}
	
	private JTextField getLblAndTxt(JPanel parentPanel, String strLbl, boolean bEnable) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		parentPanel.add(panel);
		
		JPanel lblPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) lblPanel.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(30);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.add(lblPanel);
		JLabel label = new JLabel(strLbl);
		lblPanel.add(label);
		label.setEnabled(bEnable);;
		
		JPanel txtPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) txtPanel.getLayout();
		flowLayout_1.setHgap(5);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel.add(txtPanel);
		JTextField txtField = new JTextField();
		txtPanel.add(txtField);
		txtField.setColumns(20);
		txtField.setEnabled(bEnable);
		
		return txtField;
	}
	
	public JPanel getMainPanel(JFrame _parentFrame) {
		parentFrame = _parentFrame;
		return mainPanel;
	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return  isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		// TODO Auto-generated method stub
		isInitialized = val;
		
	}
	
	
}
