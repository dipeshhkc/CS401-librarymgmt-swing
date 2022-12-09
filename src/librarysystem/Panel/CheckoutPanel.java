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
import java.awt.Font;
import java.awt.FlowLayout;

public class CheckoutPanel extends JFrame implements LibWindow {
	private static final long serialVersionUID = 7598971230240576685L;
	private boolean isInitialized = false;
	private JTextField txtMemberId;
	private JTextField txtISBN;
	
	private JPanel mainPanel;
	private JFrame parentFrame;
	
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
	
	public JPanel getMainPanel(JFrame _parentFrame) {
		parentFrame = _parentFrame;
		return mainPanel;
	}
	
	@Override
	public void init() {
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		//mainPanel.setLayout(new GridLayout(3, 1, 0, 0));
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		//--north-----------------------------------------------------------------
		JPanel northPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) northPanel.getLayout();
		flowLayout.setVgap(30);
		JLabel northLabel = new JLabel("Checkout Book");
		northLabel.setFont(new Font("Fira Code Retina", Font.BOLD, 20));
		northPanel.add(northLabel);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		
		//--middle-----------------------------------------------------------------
		JPanel middlePanel = new JPanel();
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		middlePanel.setLayout(new BorderLayout(0, 0));
		
		//-------------list-------------------------------------------
		JPanel listPanel = new JPanel();
		middlePanel.add(listPanel, BorderLayout.NORTH);
		listPanel.setLayout(new GridLayout(0, 1, 0, 0));
		//-------------member-------------------
		txtMemberId	= getLblAndTxt(listPanel, "Member Id", true);
		txtISBN 	=  getLblAndTxt(listPanel, "Book ISBN", true);
		
		JLabel label = new JLabel("");
		listPanel.add(label);
		
		JPanel checkoutPanel = new JPanel();
		listPanel.add(checkoutPanel);
		//-----------check out Button-----------------
		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.setFont(new Font("Gulim", Font.PLAIN, 13));
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BookCopy bc = ci.checkIfBookCopyAvailable(txtMemberId.getText(), txtISBN.getText());
					JOptionPane.showMessageDialog(parentFrame,bc.getBook().getTitle() + 
							//" [ISBN: " + bc.getBook().getIsbn() + "]" +
							" [copy number: " + bc.getCopyNum() + "] is checked out successfully" );
					
				}catch  (Exception e1) {
					
					JOptionPane.showMessageDialog(parentFrame,e1.getMessage());
					return;
				}
				
				
			}
		});
		checkoutPanel.add(btnCheckout,BorderLayout.CENTER);
		
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

}
