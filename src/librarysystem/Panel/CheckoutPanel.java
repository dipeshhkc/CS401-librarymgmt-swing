package librarysystem.Panel;

import javax.swing.JFrame;

import librarysystem.LibWindow;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import business.BookCopy;
import business.ControllerInterface;
import business.SystemController;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CheckoutPanel extends JFrame implements LibWindow {
	private static final long serialVersionUID = 7598971230240576685L;
	private boolean isInitialized = false;
	private JTextField txtMemberId;
	private JTextField txtISBN;
	
	private JPanel mainPanel;
	
	ControllerInterface ci = new SystemController();
	public static final CheckoutPanel INSTANCE = new CheckoutPanel();
	
	public CheckoutPanel() {
		init();		
	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		// TODO Auto-generated method stub
		isInitialized = val;
	}
	
	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	@Override
	public void init() {
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		JPanel middlePanel = new JPanel();
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		middlePanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		//-------------member-------------------
		JPanel memberPanel = new JPanel();
		middlePanel.add(memberPanel);
		
		JLabel lblmemLabel = new JLabel("Member Id");
		memberPanel.add(lblmemLabel);
		
		txtMemberId = new JTextField();
		memberPanel.add(txtMemberId);
		txtMemberId.setColumns(10);
		
		//-------------ISBN-------------------
		JPanel ISBNpanel = new JPanel();
		middlePanel.add(ISBNpanel);
		
		JLabel lblISBNLabel = new JLabel("ISBN");
		ISBNpanel.add(lblISBNLabel);
		
		txtISBN = new JTextField();
		ISBNpanel.add(txtISBN);
		txtISBN.setColumns(10);
		
		JPanel checkoutPanel = new JPanel();
		middlePanel.add(checkoutPanel);
		
		//-----------check out Button-----------------
		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BookCopy bc = ci.checkIfBookCopyAvailable(txtMemberId.getText(), txtISBN.getText());
				}catch  (Exception e1) {
					
					JOptionPane.showMessageDialog(this,e1.getMessage());
				}
			}
		});
		checkoutPanel.add(btnCheckout);
		
	}

}
