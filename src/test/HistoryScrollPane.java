package test;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HistoryScrollPane extends JScrollPane {
	
	
	
	private JTextArea textArea;
//	private JScrollPane historySP = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
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
		
		textArea = new JTextArea(new String(), 10,36);
		
		this.add(textArea);
		this.setViewportView(textArea);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	

}
