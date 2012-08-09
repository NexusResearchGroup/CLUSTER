import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

/*
 *@author Arthur Huang Feb 13, 2011
 *@Affiliation: Dept. of Civil Eng, U of MN 
 */
/**
 * @author arthurhuang
 *
 */
	class FramePanel extends JPanel 
	{

		public static final int  MESSAGE_X = 75;
		public static final int MESSAGE_Y = 100;
		private JTextField hourfield;
		private JTextField minuteField;
		ButtonPanel bp = new ButtonPanel();
		Button a = new Button();
		//DrawA da = new DrawA(bp.ce);
		
		
		public FramePanel()
		{
			setLayout(new BorderLayout());
			//define the size of menuframe according to the screen size
			//DrawArea da = new DrawArea(bp.ce);
			//draw the supplier situation
			add("West", bp);
			add("Center", bp.da);
			//add( a, "south");
			//addWindowListener(this);
			
		}
		
	}