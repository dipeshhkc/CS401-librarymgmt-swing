package librarysystem.Panel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.ControllerInterface;
import business.SystemController;
import librarysystem.LibWindow;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddNewBookCopyPanel extends JFrame  implements LibWindow{

	private static final long serialVersionUID = 5538744765326735797L;
	
	private boolean isInitialized = false;
	private JPanel mainPanel;
	private JFrame parentFrame;
	private JTextField txtISBN;
	private JTextField txtCopies;
	
	ControllerInterface ci = new SystemController();
	public static final AddNewBookCopyPanel INSTANCE = new AddNewBookCopyPanel();

	public AddNewBookCopyPanel() {
		init();		
	}
	
	public void init() {
		
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		//--north-----------------------------------------------------------------
		JPanel northPanel = new JPanel();
		mainPanel.add(northPanel);
		
		//--middle-----------------------------------------------------------------
		JPanel middlePanel = new JPanel();
		mainPanel.add(middlePanel);
		middlePanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		//-------------ISBN-------------------
		JPanel ISBNpanel = new JPanel();
		middlePanel.add(ISBNpanel);
		
		JLabel lblISBNLabel = new JLabel("Book ISBN");
		lblISBNLabel.setFont(new Font("Gulim", Font.PLAIN, 13));
		ISBNpanel.add(lblISBNLabel);
		
		txtISBN = new JTextField();
		txtISBN.setFont(new Font("Gulim", Font.PLAIN, 13));
		ISBNpanel.add(txtISBN);
		txtISBN.setColumns(10);
		
		//-------------copy number---------------
		JPanel Copypanel = new JPanel();
		middlePanel.add(Copypanel);
		
		JLabel lblCopyNumLabel = new JLabel("Current Copies");
		lblCopyNumLabel.setFont(new Font("Gulim", Font.PLAIN, 13));
		Copypanel.add(lblCopyNumLabel);
		
		txtCopies = new JTextField();
		txtCopies.setEnabled(false);
		txtCopies.setEditable(false);
		txtCopies.setFont(new Font("Gulim", Font.PLAIN, 13));
		Copypanel.add(txtCopies);
		txtCopies.setColumns(5);
		
		//-------------Add-------------------
		JPanel AddPanel = new JPanel();
		middlePanel.add(AddPanel);
		
		JButton btnAddCopy = new JButton("Add Copy");
		btnAddCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ci.addNewBookCopy(txtISBN.getText());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(parentFrame,e1.getMessage());
					return;
				}
				
				int iNum = ci.getBookCopiesCount(txtISBN.getText());
				txtCopies.setText("" + iNum);
			}
		});
		
		btnAddCopy.setFont(new Font("Gulim", Font.PLAIN, 13));
		AddPanel.add(btnAddCopy);
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
