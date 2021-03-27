package test;

import javax.swing.JPanel;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;
import javax.swing.JSplitPane;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.SwingConstants;

public class SliderPanel extends JPanel {

	int value=90;
	private JLabel labelValue;
	String name = new String();
	JLabel labelName = new JLabel("name");
	
	public int getValue()
	{
		return value;
	}
	
	public void setName(String name)
	{
		this.name = name;
		labelName.setText(this.name);
	}
	
	/**
	 * Create the panel.
	 */
	public SliderPanel(String name) {
		
		setLayout(null);
		setName(name);
		labelValue = new JLabel("90");
		labelValue.setBounds(93, 0, 27, 44);
		add(labelValue);
		
		JSlider slider = new JSlider();
		slider.setPaintTicks(true);
		slider.setBounds(130, 4, 181, 40);
		add(slider);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				value = slider.getValue();
				labelValue.setText(String.valueOf(slider.getValue()));	
			}
		});
		slider.setMajorTickSpacing(45);
		slider.setFont(new Font("Tahoma", Font.PLAIN, 9));
		slider.setPaintLabels(true);
		slider.setValue(90);
		slider.setMaximum(180);
		
		
		labelName.setBounds(0, 15, 83, 14);
		add(labelName);

	}
}
