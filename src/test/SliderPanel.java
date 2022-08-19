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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.SpringLayout;
import javax.swing.JSplitPane;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.SwingConstants;

public class SliderPanel extends JPanel implements ChangeListener {
	
	   
    // my part
	int value=90;
	private JLabel labelSliderValue;
	private String name = new String();
	private JLabel labelName = new JLabel("name");
	private JSlider slider = new JSlider();
	private String jsonId = new String() ;
	private int servoGroupId;
	private JLabel labelServoGroup = new JLabel();
	
	public String getId() {
		return jsonId;
	}

	public void setId(String id) {
		this.jsonId = id;
	}

	public int getValue()
	{
		return value;
	}
	
	public int getServoGroupId()
	{
		return servoGroupId;
	}
	
	
	/**
	 * Create the panel.
	 */
	public SliderPanel(String jsonId, String name, int servoGroupId) {
		
		setLayout(null);
		this.setName(name);
		labelSliderValue = new JLabel("90");
		labelSliderValue.setBounds(150, 0, 27, 44);
		add(labelSliderValue);
		
		slider.setPaintTicks(true);
		slider.setBounds(180, 4, 181, 40);
		slider.addChangeListener(this);
		slider.setName(name);
		
		labelName.setText(this.getName());
		this.jsonId = jsonId ;
		this.servoGroupId = servoGroupId;
		
		add(slider);
//		slider.addChangeListener(new ChangeListener() {
//			public void stateChanged(ChangeEvent e) {
//				value = slider.getValue();
//				labelValue.setText(String.valueOf(slider.getValue()));
////				notifyObservers(name + ":" +String.valueOf(slider.getValue()));
//			}
//		});
		slider.setMajorTickSpacing(45);
		slider.setFont(new Font("Tahoma", Font.PLAIN, 9));
		slider.setPaintLabels(true);
		slider.setValue(90);
		slider.setMaximum(180);
		
		
		labelName.setBounds(0, 15, 83, 14);
		add(labelName);
		
		labelServoGroup.setText("SG: " + String.valueOf(servoGroupId));
		labelServoGroup.setBounds(90, 15, 83, 14);
		add(labelServoGroup);

	}

	@Override
	public void stateChanged(ChangeEvent e) 
	{
		if (e.getSource().toString().startsWith("javax.swing.JSlider"))
		{
			JSlider src = (JSlider) e.getSource();
			// update the label
			labelSliderValue.setText(String.valueOf(src.getValue()));
			value = src.getValue();
		}
	}
	
	/**
	 * 
	 * @return slider object
	 */
	JSlider getSlider()
	{
		return slider;
	}
}
