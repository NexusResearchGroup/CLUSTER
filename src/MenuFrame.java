import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.net.*;
import javax.swing.text.PlainDocument;
import javax.print.DocFlavor.URL;
import javax.swing.*;

/*
 *@author Arthur Huang Feb 13, 2011
 *@Affiliation: Dept. of Civil Eng, U of MN 
 */

/**
 * 
 */

/**
 * @author arthurhuang
 *
 */
	class MenuFrame extends JFrame implements  ActionListener
	{
		
	   //public static final int WIDTH = 300;
	   //public static final int HEIGHT = 200;
		public Frame fAttributeEdit; 
		Demonstration demo;
		Dimension screensize = getToolkit().getScreenSize();
		URL helpurl = null;
		URL url = null;
		MenuBar mbar = new MenuBar();
		FramePanel panel = new FramePanel();
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints constraints=new GridBagConstraints();
		Label id, attribute1, attribute2;
		AboutClusterWindow clusterWindowshow;

	   //not public void 
	   public MenuFrame(Demonstration demo) 
	   {		   
		   
		   //System.out.println("print here2..");
		   //addWindowListener((WindowListener) this);
		    //url = getCodeBase();
		   this.demo = demo;
		    setTitle("Clustered Locations of Urban Services, Transport,and Economic Resources (CLUSTER)");
			setSize ((int)(1.0*screensize.width), (int)(0.99*screensize.height));
		   
			MenuBar mbar = new MenuBar();

			add(panel);
		    setMenuBar(mbar);
		   
			Menu cluster = new Menu("CLUSTER 1.0");
			Menu help=new Menu("Help");
			
			MenuItem quit,about,instruction;
			cluster.add(about = new MenuItem("About CLUSTER 1.0"));
			cluster.add(quit = new MenuItem("Quit"));
			help.add(instruction = new MenuItem("Instructions"));
			//help.add(about=new MenuItem("About Song1.0"));

			mbar.add(cluster);
			mbar.add (help);
		    
			
			about.addActionListener(this);
			quit.addActionListener(this);
			instruction.addActionListener(this);
 
	   }
	  
		public void actionPerformed( ActionEvent ae)  {
			String arg = (String) ae.getActionCommand();
			Object obj = ae.getSource();
			
			if(arg=="Quit"){
		           dispose();
		           addWindowListener(new WindowAdapter(){
		   		    public void windowClosing (WindowEvent e)
		   		    {
		   				System.exit(0);
		   			}
		   		});
			}
			if (arg == "About CLUSTER 1.0") {
				System.out.println("about cluster 1.0");
						//" ©2011: the Nexus Research Group at the University of Minnesota. " + "\n");
				//JLabel content2 = new JLabel("\n It is used to illustrate retail location choice on a  \n " + "\n");
				clusterWindowshow = new AboutClusterWindow();
				clusterWindowshow.setVisible(true);
				
				clusterWindowshow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				
			}
			
			/*
		    if (arg == "Instructions")
		    {
		    	demo.readLink();
		    	System.out.println("instruction");
		    }  */
			
		}
	   
	   
	}