package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.SystemController;


public class AllMemberIdsWindow extends JFrame implements LibWindow {
	public static final AllMemberIdsWindow INSTANCE = new AllMemberIdsWindow();
    ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;
	public JPanel getMainPanel() {
		return mainPanel;
	}
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private TextArea textArea;
	
	private AllMemberIdsWindow() {
		init();
	}
	
	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);	
		//mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
		
		setData();
	}
	
	public void defineTopPanel() {
		topPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) topPanel.getLayout();
		flowLayout.setVgap(30);
		JLabel AllIDsLabel = new JLabel("All Member IDs");
		AllIDsLabel.setFont(new Font("Fira Code Retina", Font.BOLD, 20));
		topPanel.add(AllIDsLabel);	
	}
	
	public void defineMiddlePanel() {
		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
		middlePanel.setLayout(fl);
		textArea = new TextArea(8,20);
		middlePanel.add(textArea);
		
	}
	
	public void defineLowerPanel() {
		lowerPanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		lowerPanel.setLayout(fl);
		JButton backButton = new JButton("<== Back to Main");
		addBackButtonListener(backButton);
		lowerPanel.add(backButton);
	}
	
	public void setData() {
		List<String> ids = ci.allMemberIds();
		Collections.sort(ids);
		StringBuilder sb = new StringBuilder();
		for (String s : ids) {
			sb.append(s + "\n");
		}
		setData(sb.toString());
	}
	
	public void setData(String data) {
		textArea.setText(data);
	}
	
	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
		   LibrarySystem.hideAllWindows();
		   LibrarySystem.INSTANCE.setVisible(true);
	    });
	}

	@Override
	public boolean isInitialized() {
		
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
		
	}
	
}


