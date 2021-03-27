package test;
import java.util.HashMap;
import java.util.Vector;

import com.fazecast.jSerialComm.*;

public class Communicator 
{
	private HashMap<String, SerialPort> portsHashMap = new HashMap<String, SerialPort>();
	private String openedPort = null;
	
	
	/**
	 * 
	 * @return - is a port opened by the application?
	 */
	public boolean isPortOpen()
	{
		boolean returnValue=false;
		if (openedPort == null)
		{
			returnValue = false;
		}
		else
		{
			returnValue = portsHashMap.get(openedPort).isOpen();
		}
		return returnValue;
	}
	
	/**
	 * disconnect the currently connected com port
	 * @return
	 */
	public boolean disconnect()
	{
		portsHashMap.get(openedPort).closePort();
		if (portsHashMap.get(openedPort).isOpen())
		{
			// port is still open, close did not work
			return false;
		}
		else
		{
			// successfully closed the port
			openedPort=null;
			return true;
		}
	}
	
	
	
	public Communicator() 
	{
		SerialPort[] ports = SerialPort.getCommPorts();
		
		for (int i=0; i< ports.length; i++)
		{
			portsHashMap.put(ports[i].getSystemPortName(), ports[i]);
		}
	}
		
	
	/**
	 * list all available com ports. returns user friendly names as Vector<String>
	 * @return string vector of available comports
	 */
	public  String[] list()
	{
		return portsHashMap.keySet().toArray(String[]::new);
		
	}
	
	
   
    
    
    
	
	/**
	 * connects to the given port. 
	 * @param comPort
	 * @param baud
	 * @return
	 */
	public boolean connect(String comPort, int baud)
	{
		// implicitly close old port if there is already one open
		if (openedPort != null)
		{
			portsHashMap.get(openedPort).closePort();
			System.out.println("Port " + openedPort + " is already opened. Closing it!");
		}
		
		// opening new port
		portsHashMap.get(comPort).setBaudRate(baud);
		portsHashMap.get(comPort).openPort();
		
		if (portsHashMap.get(comPort).isOpen())
		{
			openedPort=comPort;
		}
		
		return portsHashMap.get(comPort).isOpen();
		
	}
}
