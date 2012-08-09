import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class AboutClusterWindow extends JFrame {
	
	public AboutClusterWindow()
	{
		setTitle("About CLUSTER 1.0");
		setSize(700, 100);
		Container myContentFrame = getContentPane();
		JLabel content = new JLabel("\n " + "CLUSTER 1.0 is developed by the NEXUS Research Group at the University of Minnesota."
				+  "\n"  + " ©2011   ");
		
		myContentFrame.add(content);
		//content.setText("CLUSTER 1.0, developed by the NEXUS Research Group at the University of Minnessota, is based on the model in Huang and Levinson (2010). " +
		 //"It is used to illustrate retail location choice on a supply chain consisting of consumers, retailers and suppliers.");
		
	}

}
