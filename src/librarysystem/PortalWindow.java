package librarysystem;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.SwingConstants;

import business.LoginException;
import business.SystemController;
import dataaccess.Auth;
import dataaccess.User;

public class PortalWindow extends JFrame implements LibWindow {

	private static final long serialVersionUID = 3842471112999905561L;
	
	private boolean isInitialized = false;
	private String username = "";
	private Auth auth = Auth.BOTH;
	
	private JLabel lblWecome;
	private JButton btnLogout;
	JList<String> funcList;
	JPanel cards;
	
	private boolean[] enabledFlags;
	
	public static String[] funcItems = {	
			"Member", 
			"   Create new members",
			"   Edit member",
			"Book",
			"   Add new books",
			"   Add new copies", 
			"   Check out"};
	
	private static int FUNC_MEMBER = 0;
	private static int FUNC_CREATE_NEW_MEM = 1;
	private static int FUNC_EDIT_MEM = 2;
	private static int FUNC_BOOK = 3;
	private static int FUNC_ADD_NEW_BOOK = 4;
	private static int FUNC_ADD_NEW_COPY = 5;
	private static int FUNC_CHECKOUT = 6;
	
	public static final PortalWindow INSTANCE = new PortalWindow();
	
	public PortalWindow() {
		init(username, auth);
	}
	
	@Override
	public boolean isInitialized() {
		return isInitialized;
	}
	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	@Override
	public void init() {
		
	}
		
	public void setUser(String name, Auth au) {
		username = name;
		auth = au;
		lblWecome.setText("Welcome " + username);
		
		for(int i = 0; i < enabledFlags.length; i++)
			enabledFlags[i] = false;
		
		if(au == Auth.ADMIN || au == Auth.BOTH) {
			enabledFlags[FUNC_MEMBER] = true;
			enabledFlags[FUNC_CREATE_NEW_MEM] = true;
			enabledFlags[FUNC_EDIT_MEM] = true;
			
			enabledFlags[FUNC_BOOK] = true;
			enabledFlags[FUNC_ADD_NEW_BOOK] = true;
			enabledFlags[FUNC_ADD_NEW_COPY] = true;
		}
		if (au == Auth.LIBRARIAN || au == Auth.BOTH) {
			enabledFlags[FUNC_BOOK] = true;
			enabledFlags[FUNC_CHECKOUT] = true;
		}
	}
	
	private void init(String name, Auth au) {  
		username = name;
		auth = au;
		
		enabledFlags = new boolean[funcItems.length];
		
		//[left] create List of functionalities		
		funcList = new JList<String>(funcItems);	
		funcList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		funcList.setSelectionModel(new DisabledItemSelectionModel());
		funcList.setCellRenderer(new DisabledItemListCellRenderer());
	
		//[right] create panels
		createPanels();
		
		//[middle] create split
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, funcList, cards);
		splitPane.setDividerLocation(150);
		add(splitPane, BorderLayout.CENTER);
		
		setSize(660,500);
		setUser(username, au);
		isInitialized = true;
	 
	}
	
	private void createPanels() {
		//-----top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
        
        lblWecome = new JLabel("Welcome " + username);
		topPanel.add(lblWecome);
		
        btnLogout = new JButton("Logout");
		addLogoutButtonListener(btnLogout);
		topPanel.add(btnLogout);
		add(topPanel, BorderLayout.NORTH);
		
		//----middle Panel
		
		
		//------------------------------------
        // add more functional Panel
        //-----------------------------------
		 JPanel middleP0 = new JPanel();
		 JLabel l0 = new JLabel(funcItems[FUNC_CREATE_NEW_MEM]);
		 middleP0.add(l0);		 
		 
		 JPanel middleP1 = new JPanel();
		 JLabel l1 = new JLabel(funcItems[FUNC_EDIT_MEM]);
		 middleP1.add(l1);
		
		 JPanel middleP2 = new JPanel();
		 JLabel l2 = new JLabel(funcItems[FUNC_ADD_NEW_BOOK]);
		 middleP2.add(l2);		
		 
		 JPanel middleP3 = new JPanel();
		 JLabel l3 = new JLabel(funcItems[FUNC_ADD_NEW_COPY]);
		 middleP3.add(l3);	
		 
		 JPanel middleP4 = new JPanel();
		 JLabel l4 = new JLabel(funcItems[FUNC_CHECKOUT]);
		 middleP4.add(l4);	
		
		//------------------------
		//getContentPane().add(mainPanel);
	    
        cards = new JPanel(new CardLayout());

		cards.add(middleP0, funcItems[FUNC_CREATE_NEW_MEM]);
		cards.add(middleP1, funcItems[FUNC_EDIT_MEM]);
		cards.add(middleP2, funcItems[FUNC_ADD_NEW_BOOK]);
		cards.add(middleP3, funcItems[FUNC_ADD_NEW_COPY]);
		cards.add(middleP4, funcItems[FUNC_CHECKOUT]);
		
		//connect JList elements to CardLayout panels
		funcList.addListSelectionListener(event -> {
			String value = funcList.getSelectedValue().toString();
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, value);
		});
	}
	
	private void addLogoutButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.reset();
			LoginWindow.INSTANCE.setVisible(true);
			
		});
	}
	
	//--------------------------------------------------------
	/*private class DisabledItemListCellRenderer extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component comp = super.getListCellRendererComponent(list, value, index, false, false);
            //JComponent jc = (JComponent) comp;
            if (enabledFlags[index]) {
                if (isSelected & cellHasFocus) {
                    comp.setForeground(Color.black);
                    comp.setBackground(Color.red);
                } else {
                    comp.setForeground(Color.blue);
                }
                if (!isSelected) {
                    if ((value.toString()).trim().equals("yellow")) {
                        comp.setForeground(Color.orange);
                        comp.setBackground(Color.magenta);
                    }
                }
                return comp;
            }
            comp.setEnabled(false);
            return comp;
        }
    }*/
	
	private class DisabledItemListCellRenderer extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            if (enabledFlags[index]) {
            	return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
          
            Component comp = super.getListCellRendererComponent(list, value, index, false, false);
            comp.setEnabled(false);
            return comp;
        }
    }
	
	 private class DisabledItemSelectionModel extends DefaultListSelectionModel {

	        private static final long serialVersionUID = 1L;

	        @Override
	        public void setSelectionInterval(int index0, int index1) {
	            if (enabledFlags[index0]) {
	                super.setSelectionInterval(index0, index0);
	            } else {
	                /*
	                 * The previously selected index is before this one,
	                 * so walk forward to find the next selectable item.
	                 */
	                if (getAnchorSelectionIndex() < index0) {
	                    for (int i = index0; i < enabledFlags.length; i++) {
	                        if (enabledFlags[i]) {
	                            super.setSelectionInterval(i, i);
	                            return;
	                        }
	                    }
	                } /*
	                 * Otherwise, walk backward to find the next selectable item.
	                 */ else {
	                    for (int i = index0; i >= 0; i--) {
	                        if (enabledFlags[i]) {
	                            super.setSelectionInterval(i, i);
	                            return;
	                        }
	                    }
	                }
	            }
	        }
	    }
	
}
