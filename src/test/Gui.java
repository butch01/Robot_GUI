package test;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JSeparator;
//import org.apache.commons.codec.binary.Hex;



public class Gui extends JFrame implements ChangeListener {


	/*
	 * adds baud rates as entries to 
	 */
	private void addAvailableBaudRatesToCombo()
	{
		comboBaud.addItem(9600);
		comboBaud.addItem(56000);
		comboBaud.addItem(57600);
		comboBaud.addItem(115200);
		comboBaud.addItem(256000);
		comboBaud.setSelectedItem(57600);
	}
	
	private JPanel contentPane;
	
	// this Vector holds all Sliderpanel Vectors
	private Vector<Vector<SliderPanel>> vServoGroupSliders = new Vector<Vector<SliderPanel>>();
	private int numberOfServoGroups=2;
//	private Vector<SliderPanel> vSlider = new Vector<SliderPanel>(); // holds all sliders

	// this vector holds all hashmap vectors
	private Vector<HashMap<String, Integer>> vhmSlider = new Vector<HashMap<String, Integer>>();	// holds mapping of sliders (name, vectorId)
	// private HashMap<String, Integer> hmSlider = new HashMap<String,Integer>(); // holds mapping of sliders (name, vectorId)
	
	
	
	
	private Vector<JTextField> vMessageBytes = new Vector<JTextField>(); // holds all textfields for entries
	private JComboBox<String> comboComPorts = new JComboBox<String>();
	private JComboBox<Integer> comboBaud = new JComboBox<Integer>();
	private JButton btnConnectCom = new JButton("connect");
	private JButton btnCpyToHist = new JButton("to History");
//	private JButton btnGenerate = new JButton("generate");
	private JButton btnSend = new JButton("send");
	private JCheckBox chkBoxAutoSend = new JCheckBox("AutoSend");
	private JTextArea jtaHistory = new JTextArea(new String(), 10,36);
	private JTextArea jtaHistoryJson = new JTextArea(new String(), 10,36);
	private JScrollPane historySP = new JScrollPane(jtaHistory, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private JScrollPane historySPJson = new JScrollPane(jtaHistoryJson, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	private JLabel labelTime = new JLabel("TimeOffset (ms)");
	private JTextField timeField = new JTextField("500");
	
	private boolean isPortOpened=false;
	
	private byte[] myMessageBytes = new byte[12];
	private String currentMessageJson = new String("");
	
	Communicator comm = new Communicator();
	private JTable table;
	
	private void buildMessage()
	{
//		for (int i=0; i< vSlider.size(); i++)
//		{
//			myMessageBytes[i]=(byte) vSlider.get(i).getValue();
//		}
	}
	
	
	/**
	 * builds the current message in json format and returns the json string
	 * 
	 * @return message as json
	 */
	private String getMessageJson()
	{
	
		StringBuilder sb = new StringBuilder();
		
		// opening bracket
		sb.append("{");
		
		// adding time for keyframe
		sb.append("\"t\":" + timeField.getText() + ",");
		
		// iterate over all servo groups
		for (int servoGroupId=0; servoGroupId < vServoGroupSliders.size(); servoGroupId++)
		{
			sb.append("\"sg" + String.valueOf(servoGroupId) + "\":{");
			for (int i=0; i < vServoGroupSliders.get(servoGroupId).size(); i++ )
			{
				sb.append("\"" + vServoGroupSliders.get(servoGroupId).get(i).getId() + "\":" + String.valueOf(vServoGroupSliders.get(servoGroupId).get(i).getValue()));
				// adding "," if we are not the last entry
				if (i < vServoGroupSliders.get(servoGroupId).size() -1)
				{
					sb.append(",");
				}
			}
			if (servoGroupId < vServoGroupSliders.size() -1)
			{
				sb.append("},");
			}
			else
			{
				sb.append("}");
			}
			
		}
		sb.append("}");
		return sb.toString();
		
	}
	
	private void sendMessage()
	{
		buildMessage();
		updateMessageInTextfield();
		// comm.send(myMessageBytes);
		comm.send(getMessageJson().getBytes());
	}
	
	private void updateMessageInTextfield()
	{
//		String foo = "I am a string";
//		byte[] bytes = foo.getBytes();
//		System.out.println( Hex.encodeHexString( bytes ) );
		
//		System.out.println(Hex.encodeHex(myMessageBytes));
		
		for (int i = 0; i < vMessageBytes.size(); i++)
		{
			vMessageBytes.get(i).setText(String.valueOf(Byte.toUnsignedInt(myMessageBytes[i])));
			
		}
		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * connects a comport
	 * 
	 */
	private boolean callConnectComPort()
	{
		
		boolean rc = comm.connect(comboComPorts.getSelectedItem().toString(), Integer.valueOf(comboBaud.getSelectedItem().toString()));
		if (rc)
		{
			// connect is successfull
			comboBaud.setEnabled(false);
			comboComPorts.setEnabled(false);
			btnConnectCom.setText("close");
			btnSend.setEnabled(true);
			isPortOpened = true;
		}
		return rc;
	};
	
	/**
	 * disconnects a comport
	 * returns true if disconnect was successful
	 */
	private boolean callDisconnectComPort()
	{
		boolean rc = comm.disconnect();
		if (rc)
		{
			comboBaud.setEnabled(true);
			comboComPorts.setEnabled(true);
			btnConnectCom.setText("connect");
			isPortOpened = false;
			btnSend.setEnabled(false);
		}
		
		return rc;
	}

	
	
	
	
	/**
	 * Create the frame.
	 */
	public Gui() {
		
		
		// generating the Subvectors for each ServoGroup
		for (int i = 0; i< numberOfServoGroups; i++)
		{
			vServoGroupSliders.add(new Vector<SliderPanel>());
			vhmSlider.add(new HashMap<String, Integer>());

			
		}
		
		int currentServoGroup=0;
		vServoGroupSliders.get(currentServoGroup).add( new SliderPanel("0", "shoulder L", currentServoGroup));
		vServoGroupSliders.get(currentServoGroup).add( new SliderPanel("1", "shoulder R", currentServoGroup));
		vServoGroupSliders.get(currentServoGroup).add( new SliderPanel("2", "arm up L", currentServoGroup));
		vServoGroupSliders.get(currentServoGroup).add( new SliderPanel("3", "arm up R", currentServoGroup ));
		vServoGroupSliders.get(currentServoGroup).add( new SliderPanel("4", "low arm L", currentServoGroup));
		vServoGroupSliders.get(currentServoGroup).add( new SliderPanel("5", "low arm R",currentServoGroup ));
		
		currentServoGroup=1;
		vServoGroupSliders.get(currentServoGroup).add( new SliderPanel("0", "leg up L", currentServoGroup));
		vServoGroupSliders.get(currentServoGroup).add( new SliderPanel("1", "leg up R", currentServoGroup));
		
		vServoGroupSliders.get(currentServoGroup).add( new SliderPanel("2", "knee L", currentServoGroup));
		vServoGroupSliders.get(currentServoGroup).add( new SliderPanel("3", "knee R", currentServoGroup));
		
		vServoGroupSliders.get(currentServoGroup).add( new SliderPanel("4", "ankle L", currentServoGroup));
		vServoGroupSliders.get(currentServoGroup).add( new SliderPanel("5", "ankle R", currentServoGroup));
		
		
		
		for (int servoGroupId=0; servoGroupId< vServoGroupSliders.size(); servoGroupId++)
		{
			// build hashmap for name resolution of slider in vector
			for (int i=0; i < vServoGroupSliders.get(servoGroupId).size(); i++)
			{
				vhmSlider.get(servoGroupId).put(vServoGroupSliders.get(servoGroupId).get(i).getName(), i);
				// add change listener directly to slider object
				vServoGroupSliders.get(servoGroupId).get(i).getSlider().addChangeListener(this);
			}
		}
			

		// prepare comports
		addAvailableBaudRatesToCombo();
		
		// prepare com list
		String[] comPortsStrings = comm.list();
		for (int i=0; i< comPortsStrings.length; i++)
		{
			comboComPorts.addItem(comPortsStrings[i]);
		}
		
		
		// generate textfield Vector for message Bytes
//		for (int i=0; i< vSlider.size(); i++)
//		{
//			vMessageBytes.add(new JTextField(2));
//		}
		
		btnSend.setEnabled(false);
		
		
		setTitle("GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(20, 20, 1000, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// update the textboxes
		buildMessage();
		updateMessageInTextfield();
		
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
				
			}
		});
		
		
		int row = 0;
		int nextFreeY=0;
		
		// display all slider panels, iterate through the servicegroups in increasing order
		for (int servoGroupId=0; servoGroupId < vServoGroupSliders.size(); servoGroupId++ )
		{
			for (int i=0; i< vServoGroupSliders.get(servoGroupId).size(); i++)
			{
				if (i % 2 == 0)
				{
					row ++;
				}
				nextFreeY= 0 + row *50;
				vServoGroupSliders.get(servoGroupId).get(i).setBounds(10 + i % 2 * 450, nextFreeY, 400, 44);
				contentPane.add(vServoGroupSliders.get(servoGroupId).get(i));
			}
		}
		
		nextFreeY = nextFreeY + 70;
		
		labelTime.setBounds(10, nextFreeY, 100, 30);
		timeField.setBounds(120, nextFreeY +4, 50, 20);
		contentPane.add(timeField);
		
		contentPane.add(labelTime);

		nextFreeY = nextFreeY + 70;
		JLabel labelCom = new JLabel("COM Port");
		labelCom.setBounds(10, nextFreeY, 100, 30);
		
		contentPane.add(labelCom);
		
		comboComPorts.setBounds(100, nextFreeY, 95, 22);
		contentPane.add(comboComPorts);
		
		comboBaud.setBounds(200, nextFreeY, 95, 22);
		contentPane.add(comboBaud);
		btnConnectCom.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (isPortOpened)
				{
					// port is currently open, so disconnect
					callDisconnectComPort();
				}
				else
				{
					// no port open yet, so connect 
					callConnectComPort();
				}
			}
		});
		
		btnConnectCom.setBounds(300, nextFreeY, 95, 22);
		contentPane.add(btnConnectCom);
		
		nextFreeY = nextFreeY + 50;
		JLabel labelMessageCaption = new JLabel("Message Bytes");
		labelMessageCaption.setBounds(10, nextFreeY, 100, 30);
		contentPane.add(labelMessageCaption);
		
		nextFreeY = nextFreeY + 40;
		
		
		for (int i=0; i < vMessageBytes.size(); i++) 
		{
			System.out.println(i);
			vMessageBytes.get(i).setBounds(10 + i * 50, nextFreeY, 30, 20);
			contentPane.add(vMessageBytes.get(i));
		}

		nextFreeY = nextFreeY + 40;
		
		chkBoxAutoSend.setBounds(16, nextFreeY, 97, 23);
		contentPane.add(chkBoxAutoSend);
		
		btnSend.setBounds(119, nextFreeY, 89, 23);
		contentPane.add(btnSend);
		
		nextFreeY = nextFreeY + 40;
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, nextFreeY, getBounds().width -40, 2);
		contentPane.add(separator);
		
		nextFreeY = nextFreeY + 20;
		btnCpyToHist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToHistoryArray();
				addToHistoryJson();
			}
		});
		
		btnCpyToHist.setBounds(10, nextFreeY, 95, 22);
		
		contentPane.add(btnCpyToHist);
		
		historySP.setBounds(150, nextFreeY, 800, 150);
		contentPane.add(historySP);
		
		nextFreeY = nextFreeY + 170;
		historySPJson.setBounds(150, nextFreeY, 800, 150);
		contentPane.add(historySPJson);
		
	}
	
	private void addToHistoryArray ()
	{
//		if (jtaHistory.getText().length() > 0)
//		{
//			jtaHistory.append("\n" );
//		}
//		jtaHistory.append("{" );
//		
//		for (int i=0; i < vSlider.size(); i++ )
//		{
//			jtaHistory.append(String.valueOf(vSlider.get(i).getValue()));
//			if (i < myMessageBytes.length -1)
//			{
//				jtaHistory.append(", ");
//			}
//		}
//		jtaHistory.append("}" );
	}
	
	private void addToHistoryJson ()
	{
		// add newline if it is not the first entry in edit field
		if (jtaHistoryJson.getText().length() > 0)
		{
			jtaHistoryJson.append("\n" );
		}
		// add the json message
		jtaHistoryJson.append(getMessageJson());
		
	}

	/**
	 * updates for a single source
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider src =  (JSlider) e.getSource();

		
//		int id = hmSlider.get(src.getName());
//		int sliderValue = src.getValue();
//		// update byte values
//		myMessageBytes[id] = (byte) sliderValue;
//		// update edit fields
//		vMessageBytes.get(id).setText(String.valueOf(sliderValue));
		
		if (chkBoxAutoSend.isSelected() && comm.isPortOpen())
		{
			sendMessage();
		}
		
	}
}
