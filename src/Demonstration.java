/*
 *@author Arthur Huang Feb 13, 2011
 *@Affiliation: Dept. of Civil Eng, U of MN 
 */

/**
 *  
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;

import java.io.*;
import java.applet.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*; 
/**
 * @author arthurhuang
 *
 */
public class Demonstration  extends JApplet {
	
	public void init()
	{
		//super.init();
		URL codeBase = this.getCodeBase();
		WindowDestroyer windowKiller=new WindowDestroyer();
		long startTime = System.currentTimeMillis();
		 MenuFrame frame = new MenuFrame (this);
   	     //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	     frame.setVisible(true);
	     //updateArrayList(frame, parameters);

		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//System.out.println("print here1..");
		//frame.setVisible(true);
		//inititiate variables and parameters
		//ChainEffects ce = new ChainEffects(ArrayList parameters);  //must be put into arraylist..

		System.out.println("The end of the program!!");
		long endTime = System.currentTimeMillis();
		System.out.println("Runing time is " + (double) ((endTime - startTime)/1000) + " seconds.");

	}
	
	public void actionPerformed( ActionEvent ae) throws IOException  {
		String arg = (String) ae.getActionCommand();
		Object obj = ae.getSource();
		if (arg == "Instructions")
		{
		 try {	 
			
	         getAppletContext().showDocument
	           (new URL(getCodeBase()+"www.google.com"),"_blank");
	         
			 /* 
			    URL yahoo = new URL("http://www.yahoo.com/");
			    URLConnection yahooConnection = yahoo.openConnection();
			    yahooConnection.connect();  */

	         
		 }  catch (MalformedURLException e){
		      System.out.println(e.getMessage());
		    }
		}
	}
	
	/*
	public static void main (String [] args)  throws IOException
	{
		WindowDestroyer windowKiller=new WindowDestroyer();
		long startTime = System.currentTimeMillis();
		 MenuFrame frame = new MenuFrame ();
   	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	     frame.setVisible(true);
	     //updateArrayList(frame, parameters);

		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//System.out.println("print here1..");
		//frame.setVisible(true);
		//inititiate variables and parameters
		//ChainEffects ce = new ChainEffects(ArrayList parameters);  //must be put into arraylist..

		System.out.println("The end of the program!!");
		long endTime = System.currentTimeMillis();
		System.out.println("Runing time is " + (double) ((endTime - startTime)/1000) + " seconds.");
	}  */
	
	/*
	public static void updateArrayList(MenuFrame frame, ArrayList parameters)
	{
		parameters.clear();
   	    
   	    for (int i = 0; i < frame.panel.bp.parameters.size(); i++ )
   	    {
   	    	parameters.add(frame.panel.bp.parameters.get(i));
   	    }
   	 
	}    */
	
    /*
	public static void updateArraylist(MenuFrame frame, ArrayList parameters)
	{
		parameters.add(frame.panel.bp.retailerA.value());   //panel
		
	}  */
}
	
	   

