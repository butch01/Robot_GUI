package test;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HistoryScrollPane extends JPanel {
	
	
	private JLabel label = new JLabel("test");
	private JTextArea textArea;
	private JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	public String getHitoryPanelText()
	{
		return textArea.getText();
	}
	
	public JTextArea getTextArea()
	{
		return textArea;
	}
	
//	public JScrollPane getScrollPane()
//	{
//		return historySP;
//	}
//	
	
	
	// Constructor
	public HistoryScrollPane()
	{
		setLayout(null);
		label.setBounds(0,0,30,30);
		textArea = new JTextArea(new String(), 10,30);
		
		scrollPane.setBounds(0,40,400,110);
		scrollPane.add(textArea);
		scrollPane.setViewportView(textArea);
		
		this.add(label);
		this.add(scrollPane);
//		this.setViewportView(label);
//		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
//		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	

}
