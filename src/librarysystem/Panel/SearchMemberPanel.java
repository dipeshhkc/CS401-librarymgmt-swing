package librarysystem.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import business.CheckoutRecordEntry;
import business.ControllerInterface;
import business.SystemController;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class SearchMemberPanel extends JFrame{

	private static final long serialVersionUID = -9123601970468408273L;

	ControllerInterface ci = new SystemController();
	
	public static final SearchMemberPanel INSTANCE = new SearchMemberPanel();
	
	private JPanel mainPanel;
	private JTextField txtMemberId;
	private JTable tblRecord;
	private DefaultTableModel model;
	private JFrame parentFrame;
	
	public JPanel getMainPanel(JFrame _parentFrame) {
		parentFrame = _parentFrame;
		return mainPanel;
	}
	
	SearchMemberPanel(){
		
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		//-----------top-------------------------
		JPanel topPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) topPanel.getLayout();
		flowLayout.setVgap(30);
		JLabel AllIDsLabel = new JLabel("Search Member");
		AllIDsLabel.setFont(new Font("Fira Code Retina", Font.BOLD, 20));
		topPanel.add(AllIDsLabel);	
		mainPanel.add(topPanel, BorderLayout.NORTH);
		
		//--middle-----------------------------------------------------------------
		JPanel middlePanel = new JPanel();
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		middlePanel.setLayout(new BorderLayout(0, 0));	

		//-------------middle North--------------------------------
		JPanel middleNorth = new JPanel();
		middlePanel.add(middleNorth,BorderLayout.NORTH);
		//listPanel.setLayout(new GridLayout(0, 1, 0, 0));
		middleNorth.setLayout(new BorderLayout(0, 50));	
		
		createMiddleNorth(middleNorth);
		
		//-------------West--------------------------------	
		JPanel middleWest = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) middleWest.getLayout();
		flowLayout_1.setHgap(50);
		middlePanel.add(middleWest, BorderLayout.WEST);
		
		//-------------East--------------------------------	
		JPanel eastWest = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) eastWest.getLayout();
		flowLayout_2.setHgap(50);
		middlePanel.add(eastWest, BorderLayout.EAST);

		//-------------center--------------------------------		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 154, 582, 287);
		middlePanel.add(scrollPane,BorderLayout.CENTER);
		
		tblRecord = new JTable();
		model = new DefaultTableModel();
		String[] column = {"ISBN","Book Name","Copy number", "Checkout Date", "Due Date"};

		model.setColumnIdentifiers(column);
		tblRecord.setModel(model);
		scrollPane.setViewportView(tblRecord);
	
	}
	
	
	public void init() {
		
	}
	
	private void createMiddleNorth(JPanel parentPanel) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 0, 50));
		parentPanel.add(panel);
		
		JPanel lblPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) lblPanel.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(30);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.add(lblPanel);
		JLabel label = new JLabel("Member Id");
		lblPanel.add(label);
		
		JPanel txtPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) txtPanel.getLayout();
		flowLayout_1.setHgap(5);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel.add(txtPanel);
		txtMemberId = new JTextField();
		txtPanel.add(txtMemberId);
		txtMemberId.setColumns(10);
		
		JButton btnSearchButton = new JButton("Search");
		btnSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<CheckoutRecordEntry> list = null;
				try {
					list = ci.getCheckoutRecordByMemberId(txtMemberId.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				int iRowCount = model.getRowCount();
				for(int i = 0; i < iRowCount; i++)
					model.removeRow(0);
				
				if(list == null)
					return;
				
				for(CheckoutRecordEntry entry: list) {
					String[] row = new String[5];
					row[0] = entry.getbCopy().getBook().getIsbn();
					row[1] = entry.getbCopy().getBook().getTitle();
					row[2] = " " + entry.getbCopy().getCopyNum();
					row[3] = " " + entry.getCheckoutDate();
					row[4] = " " + entry.getDueDate();
					model.addRow(row);
				}
			    
			}
		});
		txtPanel.add(btnSearchButton);


	}
}
