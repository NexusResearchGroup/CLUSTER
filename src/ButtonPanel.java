/*
 *@author Arthur Huang Feb 13, 2011
 *@Affiliation: Dept. of Civil Eng, U of MN 
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.awt.EventQueue;

import javax.print.DocFlavor.URL;
import javax.swing.*;

import java.awt.Event.*;
import java.awt.event.MouseEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author arthurhuang
 *
 */
public class ButtonPanel extends JPanel implements ActionListener, WindowListener{

	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints constraints = new GridBagConstraints();
	Label temp;
	Choice whichAttribute = new Choice ();
	Choice scale = new Choice ();
	Frame f;
	public TextArea stat;
	Panel help  = new Panel();
	Button forhelp = new Button();
	//Thread animatorThread;
	//int delay = 100;	
	//int run = 1;
	//Timer timer;
	
	////ScrollPanel alpha =new ScrollPanel(0.5,3,1);
	ScrollPanel beta=new ScrollPanel(0,2.0,1.0);
	ScrollPanel transcostA=new ScrollPanel(0,0.5,0.02);
	ScrollPanel transcostB=new ScrollPanel(0,0.5,0.02);
	
	ScrollPanel2 retailerA =new ScrollPanel2(1,30,5);
	//JScrollBar retailerA =new JScrollBar(JScrollBar.HORIZONTAL,0,1,1,30);
	ScrollPanel2 retailerB =new ScrollPanel2(1,30,5);
	
	ScrollPanel retailprice_A =new ScrollPanel(0,5,2.5);
	ScrollPanel retailprice_B =new ScrollPanel(0,5,1.5);
	
	ScrollPanel3 supplierA =new ScrollPanel3(1,10,5);
	ScrollPanel7 supplierB =new ScrollPanel7(1,10,5);
	//ScrollPanel supplierB =new ScrollPanel(1,15,5);
	ScrollPanel supplyprice_A =new ScrollPanel(0,3,1.5);
	ScrollPanel supplyprice_B =new ScrollPanel(0,3,1.0);
	//ScrollPanel4 customersNum =new ScrollPanel4(1,8000,1000);
	ScrollPanel5 rounds = new ScrollPanel5(1,50,10);
	
	//add Scroll panel 6 and 7
	ScrollPanel6 cellNum = new ScrollPanel6(10,200,100);
	ScrollPanel6 customerOnCell = new ScrollPanel6(1,200,10);

	ScrollPanel8 demandA = new  ScrollPanel8(1,40,20);
	ScrollPanel8 demandB = new  ScrollPanel8(1,40,20);
	
	ScrollPanel9 offset = new  ScrollPanel9(0,50,2);
	
	public String products = "Homogengeous goods: x";
	
	Choice productType = new Choice();
	
	Panel button = new Panel();
	
	ArrayList<Float> parameters = new ArrayList();
	ChainEffects ce ;
	Button restore=new Button("Restore"); 
	int totalRounds = 10;
	Button evolve = new Button("Evolve"); 
	Button statistics = new Button("Statistics"); 
	Label status = new Label("                                                        ");  //= new Label ("                                                 ");
	//("Results after " + (int) rounds.value() + " rounds" ); 
	Label producty= new Label("product y");
	//Label showStatus3=new Label("test problems. " ); 
	
	//Button reset= new Button("Reset");
	public Button first = new Button("<<");
	Label productx = new Label("product x");
	public Button previous = new Button("<");
	public Button last = new Button(">>");
    public Button next = new Button(">");
	DrawArea da  = new DrawArea();
	DrawPanel dp = new DrawPanel();
	MenuBar fmbar;
	boolean frozen = false;
	
	Label round = new Label(  "  Round 0    " , Label.CENTER );

	public ButtonPanel ()
	{
		
		//add( "south", dp);
		
		constraints.weightx =1.0;
		constraints.weighty=1.0;
		
		constraints.anchor=GridBagConstraints.WEST;
		constraints.fill=GridBagConstraints.HORIZONTAL ;
		
		setLayout(gbl);
		help.setLayout(new FlowLayout());		
		constraints.fill=GridBagConstraints.HORIZONTAL ;
		constraints.anchor=GridBagConstraints.WEST;
		
		f = new Frame("Statistics");
		stat = new TextArea("");
		
		//addComponent(3,1,1,1, status);
		
		addComponent(0,1,1,1,temp=new Label("   Types of products"));
		//temp.setFont(new Font("",Font.BOLD,13));
		productType.addItem ("Homogengeous goods: x");
		productType.addItem("Heterogeneous goods: x and y");
		productType.select("Homogengeous goods: x");
		addComponent ( 1, 1, 1, 1, productType); 
		//addComponent(1,22,1,1, showStatus2);
		
		//showStatus2.setVisible(true);
		
		addComponent(0,2,1,1,new Label("  (Consumer) distance scaling parameter"));
		addComponent(1,2,1,1,beta.sb);
		addComponent(2,2,1,1,beta.lvalue);
		beta.lvalue.setAlignment( Label.LEFT ); 
		//addComponent ( 3, 1, 1, 1, temp=new Label("                      "));
		
		//addComponent ( 2, 1, 1, 1, temp=new Label(" "));
		//onstraints.fill=GridBagConstraints.LINE_START;
		//constraints.anchor=GridBagConstraints.CENTER;
		
		addComponent(0,3,1,1,new Label("   Num of cells"));
		addComponent(1,3,1,1,cellNum.sb);
		addComponent(2,3,1,1,cellNum.lvalue);
		cellNum.lvalue.setAlignment( Label.LEFT ); 
		
		addComponent(0,4,1,1,new Label("   Customers on each cell "));
		addComponent(1,4,1,1,customerOnCell.sb);
		addComponent(2,4,1,1,customerOnCell.lvalue);
		customerOnCell.lvalue.setAlignment( Label.LEFT); 
		
		/*
		 * shipping cost..
		addComponent(0,5,1,1,new Label("   Unit shipping cost($) "));
		addComponent(1,5,1,1,transcost.sb);
		addComponent(2,5,1,1,transcost.lvalue);
		transcost.lvalue.setAlignment( Label.LEFT);   
		*/
		
		addComponent(0,5,1,1,temp = new Label("   Number of rounds "));
		//temp.setFont(new Font("",Font.BOLD,13));
		addComponent(1,5,1,1,rounds.sb);
		addComponent(2,5,1,1,rounds.lvalue);
		rounds.lvalue.setAlignment( Label.LEFT ); 
		
		addComponent(1, 6, 1, 1, productx);
		productx.setAlignment(Label.CENTER);
		/*
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e) {
				if (frozen)
				{
					frozen = false;
					da.start = true;
					for (int run = 1; run < rounds.value(); run ++)
						
					{   da.currentRound = run;
						start();
						System.out.println("start animation...");
					}
				}
				else{
					frozen = true;
					stop();
				}
			}
		});  */
		
		//add itemListener
		productType.addItemListener(new ItemListener()
		{
			public void itemStateChanged (ItemEvent ie) {
				
				da.start = false;
				da.repaint();
				//status = new Label ("                                                 ");
				
				if (productType.getSelectedItem().equals("Heterogeneous goods: x and y"))
				{
					/** if two products are chosen*/

					retailprice_B.lvalue.setVisible(true);
					retailprice_B.sb.setEnabled(true);
					supplyprice_B.sb.setEnabled(true);
					supplyprice_B.lvalue.setVisible(true);
					retailerB.sb.setEnabled(true);
					retailerB.lvalue.setVisible(true);
					supplierB.sb.setEnabled(true);
					supplierB.lvalue.setVisible(true);
					demandB.sb.setEnabled(true);
					demandB.lvalue.setVisible(true);
					evolve.setEnabled(true);
					previous.setEnabled(false);
					first.setEnabled(false);
					next.setEnabled(false);
					round.setEnabled(false);
					last.setEnabled(false);
					offset.sb.setEnabled(true);
					transcostB.sb.setEnabled(true);
					transcostB.lvalue.setVisible(true);
					offset.lvalue.setVisible(true);
					da.start = false;
					da.repaint();
				}
				else
				{
					retailprice_B.lvalue.setVisible(false);
					retailprice_B.sb.setEnabled(false);
					supplyprice_B.sb.setEnabled(false);
					supplyprice_B.lvalue.setVisible(false);
					retailerB.sb.setEnabled(false);
					retailerB.lvalue.setVisible(false);
					supplierB.sb.setEnabled(false);
					supplierB.lvalue.setVisible(false);
					transcostB.sb.setEnabled(false);
					transcostB.lvalue.setVisible(false);
					demandB.sb.setEnabled(false);
					demandB.lvalue.setVisible(false);
					evolve.setEnabled(true);
					previous.setEnabled(false);
					first.setEnabled(false);
					next.setEnabled(false);
					round.setEnabled(false);
					last.setEnabled(false);
					offset.sb.setEnabled(false);
					offset.lvalue.setVisible(false);
					da.repaint();
				}
			}
		});
	
		
		addComponent(0,7,1,1,new Label("   Retail price"));
		addComponent(1,7,1,1,retailprice_A.sb);
		addComponent(2,7,1,1,retailprice_A.lvalue);
		retailprice_A.lvalue.setAlignment( Label.LEFT);
		
		addComponent(0,8,1,1,new Label("   Supplier's price"));
		addComponent(1,8,1,1,supplyprice_A.sb);
		addComponent(2,8,1,1,supplyprice_A.lvalue);
		supplyprice_A.lvalue.setAlignment( Label.LEFT );   
	
		addComponent(0,9,1,1,temp=new Label("   Number of retailers"));
		addComponent(1,9,1,1,retailerA.sb);
		addComponent(2,9,1,1, retailerA.lvalue );
		retailerA.lvalue.setAlignment( Label.LEFT );
		
		addComponent(0,10,1,1,new Label("   Retailers' shipping cost($) "));
		addComponent(1,10,1,1,transcostA.sb);
		addComponent(2,10,1,1,transcostA.lvalue);
		transcostA.lvalue.setAlignment( Label.LEFT); 
		
		addComponent(0,11,1,1,new Label("   Number of suppliers"));
		addComponent(1,11,1,1,supplierA.sb);
		addComponent(2,11,1,1,supplierA.lvalue);
		supplierA.lvalue.setAlignment( Label.LEFT ); 
	
		addComponent(0,12,1,1,temp = new Label("   Individuals' demand"));
		//temp.setFont(new Font("",Font.BOLD,13));
		addComponent(1,12,1,1,demandA.sb);
		addComponent(2,12,1,1,demandA.lvalue);
		demandA.lvalue.setAlignment( Label.LEFT ); 
		
		//addComponent(3,14,1,1,reset);
		//addComponent(0,4,1,1,showStatus); 
		//addComponent(0,0,1,1,showStatus2); 
		
		addComponent(1, 13, 1, 1, producty);
		producty.setAlignment(Label.CENTER);
		
		addComponent(0,14,1,1,new Label("   Retail price"));
		addComponent(1,14,1,1,retailprice_B.sb);
	    addComponent(2,14,1,1,retailprice_B.lvalue);
		retailprice_B.lvalue.setAlignment( Label.LEFT ); 
		
		addComponent(0,15,1,1,new Label("   Supplier's price"));
		addComponent(1,15,1,1, supplyprice_B.sb);
		addComponent(2,15,1,1, supplyprice_B.lvalue);
		supplyprice_B.lvalue.setAlignment( Label.LEFT); 
		
	 	addComponent(0,16,1,1,temp=new Label("   Number of retailers"));
		addComponent(1,16,1,1,retailerB.sb);
		addComponent(2,16,1,1,retailerB.lvalue);
		retailerB.lvalue.setAlignment( Label.LEFT ); 
		
		addComponent(0,17,1,1,new Label("   Retailers' shipping cost($) "));
		addComponent(1,17,1,1,transcostB.sb);
		addComponent(2,17,1,1,transcostB.lvalue);
		transcostB.lvalue.setAlignment( Label.LEFT); 
		
		addComponent(0,18,1,1,new Label("   Number of suppliers"));
		addComponent(1,18,1,1,supplierB.sb);
		addComponent(2,18,1,1,supplierB.lvalue);
		supplierB.lvalue.setAlignment( Label.LEFT );  
		
		addComponent(0,19,1,1,temp = new Label("  Supplier y's offset"));
		//temp.setFont(new Font("",Font.BOLD,13));
		addComponent(1,19,1,1,offset.sb);
		addComponent(2,19,1,1,offset.lvalue);
		offset.lvalue.setAlignment( Label.LEFT ); 
		
		addComponent(0,20,1,1,temp = new Label("   Individuals' demand"));
		addComponent(1,20,1,1,demandB.sb);
		addComponent(2,20,1,1,demandB.lvalue);
		demandB.lvalue.setAlignment( Label.LEFT );  
		
		retailerB.sb.setEnabled(false);
		//retailerB.lvalue.setVisible(false);
		supplierB.sb.setEnabled(false);
		//supplierB.lvalue.setVisible(false);
		retailprice_B.sb.setEnabled(false);
		//retailprice_B.lvalue.setVisible(false);
		supplyprice_B.sb.setEnabled(false);
		//supplyprice_B.lvalue.setVisible(false);
		demandB.sb.setEnabled(false);
		//demandB.lvalue.setVisible(false);
		offset.sb.setEnabled(false);
		//offset.lvalue.setVisible(false);
		transcostB.sb.setEnabled(false);
		//add action listener for  to the original value 
		restore.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				
				//alpha.value = 1;
				//alpha.lvalue.setText(Double.toString(1));
				//alpha.sb.setValue((int)Math.round(100*(1-alpha.minvalue)/(alpha.maxvalue-alpha.minvalue)));
				round.setText("   Round 0 ");
				
				if (productType.getSelectedItem().equals("Homogengeous goods: x"))
				{
					//retailprice_B.lvalue.setVisible(false);
					retailprice_B.sb.setEnabled(false);
					supplyprice_B.sb.setEnabled(false);
					//supplyprice_B.lvalue.setVisible(false);
					retailerB.sb.setEnabled(false);
					//retailerB.lvalue.setVisible(false);
					supplierB.sb.setEnabled(false);
					//supplierB.lvalue.setVisible(false);
					demandB.sb.setEnabled(false);
					//demandB.lvalue.setVisible(false);
					offset.sb.setEnabled(false);
					//offset.lvalue.setVisible(false);
					transcostB.setEnabled(false);
				}
				else
				{
					retailprice_B.lvalue.setVisible(true);
					retailprice_B.sb.setEnabled(true);
					supplyprice_B.sb.setEnabled(true);
					supplyprice_B.lvalue.setVisible(true);
					retailerB.sb.setEnabled(true);
					retailerB.lvalue.setVisible(true);
					supplierB.sb.setEnabled(true);
					supplierB.lvalue.setVisible(true);
					demandB.sb.setEnabled(true);
					demandB.lvalue.setVisible(true);
					offset.sb.setEnabled(true);
					offset.lvalue.setVisible(true);
					transcostB.setEnabled(true);
					transcostB.setVisible(true);
				}
	
					
			    beta.value = 1.0;
				beta.lvalue.setText(Double.toString(1.0));
				beta.sb.setValue((int)Math.round(100*(1.0-beta.minvalue)/(beta.maxvalue-beta.minvalue)));
				
				transcostA.value = 0.02;
				transcostA.lvalue.setText(Double.toString(0.02));
				transcostA.sb.setValue((int)Math.round(100*(0.02-transcostA.minvalue)/(transcostA.maxvalue-transcostA.minvalue)));
				
				transcostB.value = 0.02;
				transcostB.lvalue.setText(Double.toString(0.02));
				transcostB.sb.setValue((int)Math.round(100*(0.02-transcostB.minvalue)/(transcostB.maxvalue-transcostA.minvalue)));
		
				
				retailprice_A.value = 2.5;
				retailprice_A.lvalue.setText(Double.toString(2.5));
				retailprice_A.sb.setValue((int)Math.round(100*(2.5-retailprice_A.minvalue)/(retailprice_A.maxvalue-retailprice_A.minvalue))); 
				
				retailprice_B.value = 1.5;
				retailprice_B.lvalue.setText(Double.toString(1.5));
				retailprice_B.sb.setValue((int)Math.round(100*(1.5-retailprice_B.minvalue)/(retailprice_B.maxvalue-retailprice_B.minvalue))); 	
				
				supplyprice_A.value = 1.5;
				supplyprice_A.lvalue.setText(Double.toString(1.5));
				supplyprice_A.sb.setValue((int)Math.round(100*(1.5-supplyprice_A.minvalue)/(supplyprice_A.maxvalue-supplyprice_A.minvalue))); 
				
				supplyprice_B.value = 1.0;
				supplyprice_B.lvalue.setText(Double.toString(1.0));
				supplyprice_B.sb.setValue((int)Math.round(100*(1.0-supplyprice_B.minvalue)/(supplyprice_B.maxvalue-supplyprice_B.minvalue))); 	
				
				supplierA.value = 5;
				supplierA.lvalue.setText(Integer.toString(5));
				supplierA.sb.setValue((int)(5-supplierA.minvalue)); 
				
				supplierB.value = 5;
				supplierB.lvalue.setText(Integer.toString(5));
				supplierB.sb.setValue((int)(5-supplierB.minvalue));
				
				
				offset.value = 2;
				offset.lvalue.setText(Integer.toString(2));
				offset.sb.setValue((int)(2-offset.minvalue));

				
				//supplierA.sb.setValue(5);
				da.num_supplier_A  = (int) supplierA.value;
				da.num_supplier_B  = (int) supplierB.value;
			    
				retailerA.value = 5;
				retailerA.lvalue.setText(Integer.toString(5));
				retailerA.sb.setValue((int)(5-retailerA.minvalue));
				//System.out.println("retailerA sb" + retailerA.sb.value() );
				
				retailerB.value = 5;
				retailerB.lvalue.setText(Integer.toString(5));
				retailerB.sb.setValue((int)(5-retailerB.minvalue));
				
				demandA.value = 20;
				demandA.lvalue.setText(Integer.toString(20));
				demandA.sb.setValue((int)(20-demandA.minvalue));
				//((int) Math.round(100*(20-demandA.minvalue)/(demandA.maxvalue-demandA.minvalue))); 

				demandB.value = 20;
				demandB.lvalue.setText(Integer.toString(20));
				demandB.sb.setValue((int)(20-demandB.minvalue));
				//((int) Math.round(100*(20-demandB.minvalue)/(demandB.maxvalue-demandB.minvalue))); 
				
			    System.out.println("demand b is: " + demandB.sb.getValue());
			    System.out.println("demand a is: " + demandA.sb.getValue());
				
				rounds.value = 10;
				rounds.lvalue.setText(Integer.toString(10));
				rounds.sb.setValue((int)(10-rounds.minvalue));
				
				cellNum.value = 100;
				cellNum.lvalue.setText(Integer.toString(100));
				cellNum.sb.setValue((int)(100 - cellNum.minvalue));
				
				customerOnCell.value = 10;
				customerOnCell.lvalue.setText(Integer.toString(10));
				customerOnCell.sb.setValue((int)(10 - customerOnCell.minvalue));
		
				beta.repaint();
				transcostA.repaint();
				transcostB.repaint();
				retailprice_A.repaint();
				//retailprice_B.repaint();
				supplyprice_A.repaint();
				//supplyprice_B.repaint();
				cellNum.repaint();
				customerOnCell.repaint();
				
				retailerA.repaint();
				//retailerB.repaint();
				supplierA.repaint();
				//supplierB.repaint();
				demandA.repaint();
				rounds.repaint();
				//demandB.repaint();
				//writeVariables();
				/*repaint supplier A**/
			    da.repaint();
				
				evolve.setEnabled(true);				
				da.start = false;
				da.currentRound = 0;
				da.repaint();
				
			}
		});
	
		//if evolve button is licked, then intitalize the parameters and start to work 
		//addComponent(1,15,1,1,evolve);
		
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.CENTER;
		addComponent(1,21,1,1,restore);
		addComponent(0,21,1,1, evolve);
		//button.add(restore);
		//button.add(evolve);
		button.add(first);
		button.add(previous);
		button.add(round);
		button.add(next);
		button.add(last);
		button.add(statistics);
		addComponent(1,22,1,1, button);
		addComponent(1,23,1,1, status);
		
	    
		 
		evolve.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent ae) {
				
			    //status.setText(" ");
			    da.start= false;
			    da.currentRound = 0;
			    da.repaint();
				first.setEnabled(false);
				previous.setEnabled(false);
				round.setText("   Round 0 ");
				next.setEnabled(false);
				last.setEnabled(false); 
				restore.setEnabled(false);
			    statistics.setEnabled(false);
			    status.setText(" ");
		  	    evolve.setEnabled(false);
				
				ce.RETAILERS_A = (int) retailerA.value();
				ce.RETAILERS_B = (int) retailerB.value();
				ce.SUPPLIERS_A = (int) supplierA.value();
				ce.SUPPLIERS_B = (int) supplierB.value();
				ce.DEMAND_A = (int) demandA.value();
				ce.DEMAND_B = (int) demandB.value();
				ce.SCALE = (int) cellNum.value();
				ce.CUSTOMER_ON_CELL  = (int) customerOnCell.value();
				   
				   //initialization...
				   ce = new ChainEffects();
				   
				   for (int i = 0; i < ce.retailerA.size(); i ++)
				   {
					   ce.retailerA.get(i).setu1(transcostA.value());
					   ce.retailerA.get(i).setPrice(retailprice_A.value());
				   }
				   
				   for (int i = 0; i < ce.retailerB.size(); i ++)
				   {
					   ce.retailerB.get(i).setu1(transcostB.value());
					   ce.retailerB.get(i).setPrice(retailprice_B.value());
				   }
				   
				   for (int i = 0; i < ce.supplierA.size(); i ++)
				   {
					   ce.supplierA.get(i).setPrice(supplyprice_A.value());
				   }
				   
				   for (int i = 0; i < ce.supplierB.size(); i ++)
				   {
					   ce.supplierB.get(i).setPrice(supplyprice_B.value());
				   }
				   
				   //update beta for customers
				   for (int i = 0; i < ce.customers.size(); i ++)
				   {
					   ce.customers.get(i).setBeta(- beta.value());
				   }
				   
				   for (int i = 0; i < ce.customers.size(); i ++)
				   {
					   ce.customers.get(i).setDemandA(ce.DEMAND_A);
					   ce.customers.get(i).setDemandB(ce.DEMAND_B);
				   }
				   
					for (int i = 0; i < ce.SUPPLIERS_A; i++)
					{
						//supplierA.get(i).setPrice(1.5);
						ce.supplierA.get(i).setLocation(i * (cellNum.value()/ce.SUPPLIERS_A));
						//System.out.println("suppliers' location " + suppliers.get(i).getLocation());
					}
					
					for (int i = 0; i < ce.SUPPLIERS_B; i++)
					{
						ce.supplierB.get(i).setLocation( (i *  (cellNum.value()/ce.SUPPLIERS_B) + offset.value()) % ce.SCALE);
					}
				
		
				//insert sth here
				if (productType.getSelectedItem().equals("Homogengeous goods: x"))
				{	   
					   //da.currentRound = 0;
					   //round.setText("  Round "+ Integer.toString(da.currentRound) + " " );
					   //check if updates are right
					   System.out.println("retailers: " + ce.RETAILERS_A);
					   System.out.println("suppliers " + ce.SUPPLIERS_A);
					   System.out.println("customers" + ce.CUSTOMERS );
					   System.out.println("demand " + ce.DEMAND_A );
					   System.out.println("transportation cost " + ce.retailerA.get(1).getu1());
					   System.out.println("beta "+ ce.customers.get(1).getBeta());
					   System.out.println("update succeed!");
					   
					   System.out.println("rounds" + (int) rounds.value());
					   
					   totalRounds = (int) rounds.value();
					   
 
		

					   /**
					   for ( run = 1; run <= totalRounds; run ++)
					   {
						   
						   status.setText("Location choice of round  " + run + " ");
						   ce.makeDeal_oneProduct();
						   
						   
						   da.currentRound = run;						   
					       da.start = true;
					      
						   da.validate();
						   da.repaint();
						   
						   //da.revalidate();					   
						   System.out.println("historyofLocStat A:" + ce.historyofLocationStat_A);
					        //System.out.println("current round = " + currentRound);
					       // System.out.println("historyofcLocStat A: " + ce.historyofLocationStat_A.get(currentRound-1).size());
					        System.out.println("ce.historyofclusterStat A" +  ce.historyofclusterStat_A);
					       	
					        System.out.println("historyoLocationStat B:" + ce.historyofLocationStat_B);
					        //System.out.println("historyofclusterStat B: " + ce.historyofLocationStat_B.get(currentRound-1).size());
					        System.out.println("ce.historyofclusterStat B" +  ce.historyofclusterStat_B);
					        
						  
					   }  **/
					   
				       RunAndDraw(); 
					   
				       /*
				       ce.CalculateClusterStat();
				        
					    first.setEnabled(true);
					    previous.setEnabled(true);
					    next.setEnabled(true);
					    last.setEnabled(true);
					    statistics.setEnabled(true);
					    restore.setEnabled(true);
					    evolve.setEnabled(true);

					    System.out.println("succeed..");
						status.setText("Results after " + totalRounds + " rounds          " );*/
					
				}
				else
				{
					/**heterogenous goods*/
					   status.setText(" ");
					   System.out.println("retailers A: " + ce.RETAILERS_A);
					   System.out.println("retailers B: " + ce.RETAILERS_B);
					   System.out.println("suppliers A: " + ce.SUPPLIERS_A);
					   System.out.println("suppliers B:" + ce.SUPPLIERS_B);
					   System.out.println("supplier price A: " + ce.supplierA.get(0).getPrice());
					   System.out.println("supplier price B: " + ce.supplierB.get(0).getPrice());
					   
					   System.out.println("suppliers' locales for A: " );
					   for (int i = 0; i < ce.SUPPLIERS_A; i ++)
					   {
						   System.out.print(ce.supplierA.get(i).getLocation() + " ");
					   }
					   System.out.println(" ");
					   
					   System.out.println("suppliers' locales for B: " );
					   for (int i = 0; i < ce.SUPPLIERS_B; i ++)
					   {
						   System.out.print(ce.supplierB.get(i).getLocation() + " ");
					   }
					   System.out.println(" ");
					   
					   System.out.println("customers" + ce.CUSTOMERS );
					   System.out.println("demand A:" + ce.DEMAND_A );
					   System.out.println("demand B:" + ce.DEMAND_B);
					   System.out.println("transportation cost for retailer A " + ce.retailerA.get(0).getu1());
					   System.out.println("transportation cost for retailer B " + ce.retailerB.get(0).getu1());
					   System.out.println("beta "+ ce.customers.get(0).getBeta());
					   System.out.println("update succeed!");

					   // test whether it succeeds.. 
					   //addComponent(20,18,1,1,showStatus3);
					   // showStatus3.setVisible(true); 
					   System.out.println("rounds" + (int) rounds.value());
					   
					   totalRounds = (int) rounds.value();
					   /*
					   for (int run = 1; run <= totalRounds; run ++)
					   {
						   //draw the runnin process
						  // da.start= true;
						   //da.currentRound = run;
						   
						   status.setText("Location choice of round  " + run + " ");
						   ce.makeDeal_twoProduct();
						   //da.repaint();	  
						   
						   
					   }  
					   
					   ce.calculateAvgProfit();
					 
					   ce.CalculateClusterStat();
					   
				       System.out.println("avgProfit_A" + ce.avgProfit_A);
				       System.out.println("avgProfit_B" + ce.avgProfit_B);

					   System.out.println("succeed..");
						status.setText("Results after " + totalRounds + " rounds          " );  */
					
					   RunAndDraw();
					   	   
				}
			    
		}
		});
		
		
		first.setEnabled(false);
		previous.setEnabled(false);
		next.setEnabled(false);
		last.setEnabled(false);
		statistics.setEnabled(false);

	 
		first.addActionListener(this);
		previous.addActionListener(this);
		next.addActionListener(this);
		last.addActionListener(this);
		statistics.addActionListener(this);

	}
	
	public void RunAndDraw ()
	{

		Thread counter = new Thread(new Counter());
		counter.start();
	}
	
	
	public void actionPerformed ( ActionEvent ae){
		String arg = (String) ae.getActionCommand();
		Object obj = ae.getSource();
		
		
		//to be revised...March 17, 2011
		
		if (obj.equals(statistics))
		{
			 //dispose();
	    
			f=new Frame("Statistics");
			f.addWindowListener(this);
			f.dispose();

			stat=new TextArea("");
			Dimension screensize = getToolkit().getScreenSize();
			//define the size of menuframe according to the screen size
			f.setSize ((int)(0.35*screensize.width),
						  (int)(0.80*screensize.height));
			//define the menu
			fmbar = new MenuBar();
			f.setMenuBar(fmbar);
			Menu file = new Menu("File");

			MenuItem  save,close;
			file.add(save = new MenuItem("Save"));
			file.add(close = new MenuItem("Close"));

			fmbar.add(file);

			save.addActionListener(this);
			close.addActionListener(this);
			stat.setText( "");
			f.setVisible( false);
			stat.setVisible(false);

			String temp="";

			stat.append(new String("\n\n--- Summary of variables---\n\n"));
			stat.append(new String("       Item                    Description/Value\n\n"));
			stat.append(new String(" Types of products: \t" + productType.getSelectedItem() + "\n" ));
			stat.append(new String(" Number of cell: \t" + (int) cellNum.value() + "\n" ));
			stat.append(new String(" Customers on each cell: \t" + (int) customerOnCell.value() + "\n" ));
			stat.append(new String(" Number of rounds: \t" + rounds.value() + "\n" ));
			
			stat.setFont(new Font("Times New Roman",Font.PLAIN|Font.ROMAN_BASELINE |Font.BOLD ,12));
			
			stat.append(new String(" Retailer price of product x: \t" + retailprice_A.value() + "\n" ));
			stat.append(new String(" Supplier price of product x: \t" + supplyprice_A.value() + "\n" ));
			stat.append(new String(" Num of retailers of product x: \t" + retailerA.value() + "\n" ));
			stat.append(new String(" Retailers' shippping cost of product x: \t" + transcostA.value() + "\n" ));
			stat.append(new String(" Num of suppliers of product x: \t" + supplierA.value() + "\n" ));
			stat.append(new String(" Individuals demand on product x: \t" + demandA.value() + "\n" ));
			
			stat.append(new String(" Retailer price of product y: \t" + retailprice_B.value() + "\n" ));
			stat.append(new String(" Supplier price of product y: \t" + supplyprice_B.value() + "\n" ));
			stat.append(new String(" Num of retailers of product y: \t" + retailerB.value() + "\n" ));
			stat.append(new String(" Num of suppliers of product y: \t" + supplierB.value() + "\n" ));
			stat.append(new String(" Retailers' shippping cost of product y: \t" + transcostB.value() + "\n" ));
			
			stat.append(new String(" Individuals demand on product y: \t" + demandB.value() + "\n" ));
			stat.append(new String(" Supplier y's offset: \t" + offset.value() + "\n" ));
			
			stat.append(new String("\n"));

			stat.append(new String("\n\n--- Summary of results---\n\n"));

			stat.append(new String(" Number of clusters: \t" + (int) ce.clusternum + "\n" ));
			stat.append(new String(" Cluster density : \t" +  ce.clusterdensity + "\n" ));
			
			
			stat.setVisible( true);
			f.add(stat,"Center");
			f.setVisible( true);
		} 
		
		if(arg=="Save"){
			FileDialog savefile=new FileDialog(f,"Save Statistics...",FileDialog.SAVE);

			savefile.show();

			FileOutputStream out= null;
			System.out.print(savefile.getFilenameFilter()+"\n");
			System.out.print(savefile.getFile()+"\n");
			File s= new File(savefile.getDirectory(),savefile.getFile()  );

			try{
						out= new FileOutputStream(s);
					}catch(Exception e)
					{
						System.out.println("Unable to open file");
						return;
					}
			PrintStream psOut=new PrintStream(out);
			psOut.print(stat.getText());///
			try{
			out.close();
			}catch(IOException e){
			System.out.println("e");
			}
		}

		if(arg=="Close"){
			f.dispose()  ;
		}
		
		if (arg.equals("<<")) {
			  da.currentRound = 0;
			  da.start = false;
			  da.repaint();
		} 
		else if (arg.equals("<")) 
		{
			if (da.currentRound > 1)
			{
				da.currentRound --;
				da.start = true;
				da.repaint();
			}
			else 
			{
				da.currentRound = 0;
				da.start =false;
				da.repaint();
			}
		}
		
		else if (arg.equals(">"))
		{
			if (da.currentRound < totalRounds)
			{
				da.currentRound ++;
				da.start =  true;
				da.repaint();
			}
			else if (da.currentRound == totalRounds)
			{
				da.currentRound = totalRounds;
				da.start = true;
				da.repaint();
			}
		}
		else if (arg.equals(">>"))
		{
			
			da.currentRound = totalRounds;
			da.start = true;
			da.repaint();   
		
		}
		
		round.setText("   Round "+ Integer.toString(da.currentRound) + "  " );
	}
	
	//define the events related to window
	public void windowClosing(WindowEvent e){
		Object obj = e.getSource();
		if (obj.equals( f))f.dispose() ;
	}

	public void windowOpened(WindowEvent e){
		da.setVisible(true) ;

	}
	
	
	
	public void addComponent(int x, int y, int w, int h, Component c)
	{
		constraints.gridx=x;
		constraints.gridy=y;
		constraints.gridwidth=w;
		constraints.gridheight=h;

		gbl.setConstraints( c,constraints);

		add(c);
	}
	

	
	///scrollPanel is used to define the scroll bars embeded in the variablePanel
	class ScrollPanel extends Panel implements AdjustmentListener{

		public double value;
		double maxvalue;
		double minvalue;
		int x;
		int y;
		Label lvalue=new Label("");
		JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,0,1,0,101);

		public ScrollPanel(double minvalue, double maxvalue,double defaultvalue){

		setLayout(new GridBagLayout());
		value =defaultvalue;
		this.maxvalue =maxvalue;
		this.minvalue=minvalue;
		lvalue=new Label(Double.toString(defaultvalue));
		sb.setValue ((int)Math.round(100*(defaultvalue-minvalue)/(maxvalue-minvalue)));
		sb.addAdjustmentListener( this);
		}
		public void adjustmentValueChanged(AdjustmentEvent ame){
		Object obj=ame.getSource() ;
		int arg=ame.getAdjustmentType() ;
			if(obj.equals(this.sb)){
				if(arg==AdjustmentEvent.TRACK){
					value=minvalue+(maxvalue-minvalue)*(double)sb.getValue()/100.0;
				}
				else if(arg==AdjustmentEvent.UNIT_INCREMENT){
					value+=(double)(maxvalue-minvalue) /100.0;
					}
				else if(arg==AdjustmentEvent.UNIT_DECREMENT){
					value-=(double)(maxvalue-minvalue) /100.0;
					}
				else if(arg==AdjustmentEvent.BLOCK_INCREMENT){
					value+=(double)(maxvalue-minvalue) /10.0;
					}
				else if(arg==AdjustmentEvent.BLOCK_DECREMENT){
					value-=(double)(maxvalue-minvalue) /10.0;
					}
			}

	/////update corresponding vp.variables[]...			
			value=Math.round(value*100.0)/100.0;
			this.repaint() ;
			lvalue.setText(Double.toString( value));
			
			evolve.setEnabled(true);
			
		    //repaint everything
			da.start = false;
			da.currentRound = 0;
			da.repaint();

		}
		
		
		
		public float value(){
			return (float)value;
		}
	}
	
	
	class ScrollPanel2 extends Panel implements AdjustmentListener{

		public int value;
		int maxvalue;
		int minvalue;
		int x;
		int y;
		Label lvalue=new Label("");
		JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,0,1,0,30);
		
		public ScrollPanel2(int minvalue, int maxvalue,int defaultvalue){
		//JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,defaultvalue,1,minvalue, maxvalue);

		setLayout(new GridBagLayout());
		value =defaultvalue;
		this.maxvalue =maxvalue;
		this.minvalue=minvalue;
		lvalue=new Label(Integer.toString(defaultvalue));
		//sb.setValue ((int)Math.round(100*(defaultvalue-minvalue)/(maxvalue-minvalue)));
		sb.setValue(defaultvalue);
		sb.addAdjustmentListener( this);
		}
		public void adjustmentValueChanged(AdjustmentEvent ame){
			Object obj=ame.getSource() ;
			int arg=ame.getAdjustmentType() ;
				if(obj.equals(this.sb)){
					if(arg==AdjustmentEvent.TRACK){
						value=minvalue+(int)sb.getValue();
						//value= (int)sb.getValue();
					}
					else if(arg==AdjustmentEvent.UNIT_INCREMENT){
						value+=1;
						}
					else if(arg==AdjustmentEvent.UNIT_DECREMENT){
						value-=1;
						}
					/*
					else if(arg==AdjustmentEvent.BLOCK_INCREMENT){
						value+=2;
						}
					else if(arg==AdjustmentEvent.BLOCK_DECREMENT){
						value-=2;
						} */
				}
			
			this.repaint();
			lvalue.setText(Integer.toString((int) value));
			
			evolve.setEnabled(true);
			
			System.out.println(value);
			
			
		    //repaint everything
			da.start = false;
			da.currentRound = 0;
			da.repaint();
		}

		//writeVariables();
		
		public int value(){
			return (int)value;
		}
		
		

	}

	class ScrollPanel3 extends Panel implements AdjustmentListener{

		public int value;
		int maxvalue;
		int minvalue;
		int x;
		int y;
		Label lvalue=new Label("");
		JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,0,1,0,10);
		
		public ScrollPanel3(int minvalue, int maxvalue,int defaultvalue){
		//JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,defaultvalue,1,minvalue, maxvalue);

		setLayout(new GridBagLayout());
		value =defaultvalue;
		this.maxvalue =maxvalue;
		this.minvalue=minvalue;
		lvalue=new Label(Integer.toString(defaultvalue));
		//sb.setValue ((int)Math.round(100*(defaultvalue-minvalue)/(maxvalue-minvalue)));
		sb.setValue(defaultvalue);
		sb.addAdjustmentListener( this);
		}
		public void adjustmentValueChanged(AdjustmentEvent ame){
			Object obj=ame.getSource() ;
			int arg=ame.getAdjustmentType() ;
				if(obj.equals(this.sb)){
					if(arg==AdjustmentEvent.TRACK){
						value=minvalue+(int)sb.getValue();
						//value= (int)sb.getValue();
					}
					else if(arg==AdjustmentEvent.UNIT_INCREMENT){
						value+=1;
						}
					else if(arg==AdjustmentEvent.UNIT_DECREMENT){
						value-=1;
						}
					
					/*
					else if(arg==AdjustmentEvent.BLOCK_INCREMENT){
						value+=2;
						}
					else if(arg==AdjustmentEvent.BLOCK_DECREMENT){
						value-=2;
						} */
				}
			
			this.repaint();
			lvalue.setText(Integer.toString((int) value));
			
			writeVariables();
			evolve.setEnabled(true);
			
			/*draw supplier locations */
			//da.num_supplier_A  = (int) value;
		    //repaint everything
			da.start = false;
			da.currentRound = 0;
			da.repaint();
		}

		public int value(){
			return (int) value;
		}
		
		public void writeVariables()
		{
			//has successfully written values
			System.out.println("beta" + beta.value());
			System.out.println("transport cost for retailer A"+ transcostA.value());
			System.out.println("transport cost for retailer B"+ transcostB.value());
			System.out.println("retailprice_A" + retailprice_A.value());
			//System.out.println(retailprice_B.value());
			System.out.println("supplier price A" + supplyprice_A.value());
			//System.out.println(supplyprice_B.value());		
			System.out.println("Number of retailer A" + retailerA.value());
			//System.out.println(retailerB.value());
			System.out.println("Number of supplier A" + supplierA.value());

			System.out.println("Number of customers " + customerOnCell.value());
			//System.out.println(supplierB.value());
			System.out.println("offset: " + offset.value());
		}
	}		
		
	class ScrollPanel7 extends Panel implements AdjustmentListener{

		public int value;
		int maxvalue;
		int minvalue;
		int x;
		int y;
		Label lvalue=new Label("");
		JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,0,1,0,10);
		
		public ScrollPanel7(int minvalue, int maxvalue,int defaultvalue){
		//JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,defaultvalue,1,minvalue, maxvalue);

		setLayout(new GridBagLayout());
		value =defaultvalue;
		this.maxvalue =maxvalue;
		this.minvalue=minvalue;
		lvalue=new Label(Integer.toString(defaultvalue));
		//sb.setValue ((int)Math.round(100*(defaultvalue-minvalue)/(maxvalue-minvalue)));
		sb.setValue(defaultvalue);
		sb.addAdjustmentListener( this);
		}
		public void adjustmentValueChanged(AdjustmentEvent ame){
			Object obj=ame.getSource() ;
			int arg=ame.getAdjustmentType() ;
				if(obj.equals(this.sb)){
					if(arg==AdjustmentEvent.TRACK){
						value=minvalue+(int)sb.getValue();
						//value= (int)sb.getValue();
					}
					else if(arg==AdjustmentEvent.UNIT_INCREMENT){
						value+=1;
						}
					else if(arg==AdjustmentEvent.UNIT_DECREMENT){
						value-=1;
						}
					
					/*
					else if(arg==AdjustmentEvent.BLOCK_INCREMENT){
						value+=2;
						}
					else if(arg==AdjustmentEvent.BLOCK_DECREMENT){
						value-=2;
						}  */
				}
			
			this.repaint();
			lvalue.setText(Integer.toString((int) value));
			
			writeVariables();
			evolve.setEnabled(true);
			
			/*draw supplier locations */
			da.num_supplier_B  = (int) value;
		    //repaint everything
			da.start = false;
			da.currentRound = 0;
			da.repaint();
		}

		public int value(){
			return (int)value;
		}
		
		public void writeVariables()
		{
			//has successfully written values
			System.out.println("beta" + beta.value());
			System.out.println("transport cost for retailer A: "+ transcostA.value());
			System.out.println("transport cost for retailer B: "+ transcostB.value());
			System.out.println("retailprice_A" + retailprice_A.value());
			System.out.println("retailprice_B" + retailprice_B.value());
			//System.out.println(retailprice_B.value());
			System.out.println("supplier price A" + supplyprice_A.value());
			System.out.println("supplier price B" + supplyprice_B.value());	
			System.out.println("Number of retailer A" + retailerA.value());
			System.out.println("Number of retailer B" + retailerB.value());
			//System.out.println(retailerB.value());
			System.out.println("Number of supplier A" + supplierA.value());
			System.out.println("Number of supplier A" + supplierB.value());
			//System.out.println(supplierB.value());
		}
	}
	
	
	class ScrollPanel4 extends Panel implements AdjustmentListener{

		public double value;
		double maxvalue;
		double minvalue;
		int x;
		int y;
		Label lvalue=new Label("");
		JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,0,1,0,7000);
		
		public ScrollPanel4(int minvalue, int maxvalue,int defaultvalue){
		//JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,defaultvalue,1,minvalue, maxvalue);

		setLayout(new GridBagLayout());
		value =defaultvalue;
		this.maxvalue =maxvalue;
		this.minvalue=minvalue;
		lvalue=new Label(Double.toString(defaultvalue));
		//sb.setValue ((int)Math.round(100*(defaultvalue-minvalue)/(maxvalue-minvalue)));
		sb.setValue(defaultvalue);
		sb.addAdjustmentListener( this);
		}
		public void adjustmentValueChanged(AdjustmentEvent ame){
			Object obj=ame.getSource() ;
			int arg=ame.getAdjustmentType() ;
				if(obj.equals(this.sb)){
					if(arg==AdjustmentEvent.TRACK){
						value=minvalue+(int)sb.getValue();
						//value= (int)sb.getValue();
					}
					else if(arg==AdjustmentEvent.UNIT_INCREMENT){
						value+=1;
						}
					else if(arg==AdjustmentEvent.UNIT_DECREMENT){
						value-=1;
						}
					
					/*
					else if(arg==AdjustmentEvent.BLOCK_INCREMENT){
						value+=2;
						}
					else if(arg==AdjustmentEvent.BLOCK_DECREMENT){
						value-=2;
						}  */
				}
			
			this.repaint();
			lvalue.setText(Integer.toString((int) value));
			
			evolve.setEnabled(true);
			
			System.out.println(value);
			
		    //repaint everything
			da.start = false;
			da.currentRound = 0;
			da.repaint();
		}

		//writeVariables();
		
		public float value(){
			return (int)value;
		}
		
	} 
	
	
	
	class ScrollPanel5 extends Panel implements AdjustmentListener{

		public double value;
		double maxvalue;
		double minvalue;
		int x;
		int y;
		Label lvalue=new Label("");
		JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,0,1,0,50);
		
		public ScrollPanel5(int minvalue, int maxvalue,int defaultvalue){
		//JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,defaultvalue,1,minvalue, maxvalue);

		setLayout(new GridBagLayout());
		value =defaultvalue;
		this.maxvalue =maxvalue;
		this.minvalue=minvalue;
		lvalue=new Label(Integer.toString(defaultvalue));
		//sb.setValue ((int)Math.round(100*(defaultvalue-minvalue)/(maxvalue-minvalue)));
		sb.setValue(defaultvalue);
		sb.addAdjustmentListener( this);
		}
		public void adjustmentValueChanged(AdjustmentEvent ame){
			Object obj=ame.getSource() ;
			int arg=ame.getAdjustmentType() ;
				if(obj.equals(this.sb)){
					if(arg==AdjustmentEvent.TRACK){
						value=minvalue+(int)sb.getValue();
						//value= (int)sb.getValue();
					}
					else if(arg==AdjustmentEvent.UNIT_INCREMENT){
						value+=1;
						}
					else if(arg==AdjustmentEvent.UNIT_DECREMENT){
						value-=1;
						}
					
					/*
					else if(arg==AdjustmentEvent.BLOCK_INCREMENT){
						value+=2;
						}
					else if(arg==AdjustmentEvent.BLOCK_DECREMENT){
						value-=2;
						}  */
				}
			
			this.repaint();
			lvalue.setText(Integer.toString((int) value));
			
			writeVariables();
			evolve.setEnabled(true);
			
		    //repaint everything
			da.start = false;
			da.currentRound = 0;
			da.repaint();
			
		}

		
		public float value(){
			return (int)value;
		}
		
		public void writeVariables()
		{
			//has successfully written values
			System.out.println("beta" + beta.value());
			System.out.println("transport cost for retailer A"+ transcostA.value());
			System.out.println("transport cost for retailer B"+ transcostB.value());
			System.out.println("retailprice_A" + retailprice_A.value());
			//System.out.println(retailprice_B.value());
			System.out.println("supplier price A" + supplyprice_A.value());
			//System.out.println(supplyprice_B.value());		
			System.out.println("Number of retailer A" + retailerA.value());
			//System.out.println(retailerB.value());
			System.out.println("Number of supplier A" + supplierA.value());

			System.out.println("Number of customers " + customerOnCell.value());
			//System.out.println(supplierB.value());
			
			System.out.println("Number of rounds" + rounds.value());
		}

	}
	
	
	class ScrollPanel6 extends Panel implements AdjustmentListener{

		public int value;
		int maxvalue;
		int minvalue;
		int x;
		int y;
		Label lvalue=new Label("");
		JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,0,1,0,200);
		
		public ScrollPanel6(int minvalue, int maxvalue,int defaultvalue){
		//JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,defaultvalue,1,minvalue, maxvalue);

		setLayout(new GridBagLayout());
		value =defaultvalue;
		this.maxvalue =maxvalue;
		this.minvalue=minvalue;
		lvalue=new Label(Integer.toString(defaultvalue));
		//sb.setValue ((int)Math.round(100*(defaultvalue-minvalue)/(maxvalue-minvalue)));
		sb.setValue(defaultvalue);
		sb.addAdjustmentListener( this);
		}
		public void adjustmentValueChanged(AdjustmentEvent ame){
			Object obj=ame.getSource() ;
			int arg=ame.getAdjustmentType() ;
				if(obj.equals(this.sb)){
					if(arg==AdjustmentEvent.TRACK){
						value=minvalue+(int)sb.getValue();
						//value= (int)sb.getValue();
					}
					else if(arg==AdjustmentEvent.UNIT_INCREMENT){
						value+=1;
						}
					else if(arg==AdjustmentEvent.UNIT_DECREMENT){
						value-=1;
						}
					
					/*
					else if(arg==AdjustmentEvent.BLOCK_INCREMENT){
						value+=2;
						}
					else if(arg==AdjustmentEvent.BLOCK_DECREMENT){
						value-=2;
						}  */
				}
			
			this.repaint();
			lvalue.setText(Integer.toString((int) value));
			
			writeVariables();
			evolve.setEnabled(true);
			
		    //repaint everything
			da.start = false;
			da.currentRound = 0;
			da.repaint();
			
		}

		
		public int value(){
			return (int) value;
		}
		
		public void writeVariables()
		{
			//has successfully written values
			System.out.println("beta" + beta.value());
			System.out.println("transport cost for retailer A: "+ transcostA.value());
			System.out.println("transport cost for retailer B: "+ transcostB.value());
			System.out.println("retailprice_A" + retailprice_A.value());
			//System.out.println(retailprice_B.value());
			System.out.println("supplier price A" + supplyprice_A.value());
			//System.out.println(supplyprice_B.value());		
			System.out.println("Number of retailer A" + retailerA.value());
			//System.out.println(retailerB.value());
			System.out.println("Number of supplier A" + supplierA.value());

			System.out.println("Number of customers " + cellNum.value());
			//System.out.println(supplierB.value());
			
			System.out.println("Number of rounds" + rounds.value());
		}

	}
	
	class Counter implements Runnable{
		 public void run() { 
			 try {
				 for ( int run = 1; run <= totalRounds; run ++)
				  {
					 da.currentRound = run;	
					 round.setText("   Round "+ Integer.toString(da.currentRound) + "  " );
					 round.setVisible(true);
					 status.setText("Location choice of round  " + run + " ");
					 System.out.println("Location choice of round  " + run + " ");		
					 
					 if (productType.getSelectedItem().equals("Homogengeous goods: x"))
					 {
	   			          ce.makeDeal_oneProduct();
					 }
					 else
					 {
						  ce.makeDeal_twoProduct();
					 }			   
    			     da.start = true;
    			     EventQueue.invokeLater(new Runnable() {
    			    	 public void run() {
    			    	 da.repaint();
    			    	 }
    			     }); Thread.sleep(300);
				  }
			 
			 EventQueue.invokeLater(new Runnable() {
				 public void run() {
					   //ce.calculateAvgProfit();
				       ce.CalculateClusterStat();
					    first.setEnabled(true);
					    previous.setEnabled(true);
					    next.setEnabled(true);
					    last.setEnabled(true);
					    statistics.setEnabled(true);
					    restore.setEnabled(true);
					    round.setVisible(true);
					    evolve.setEnabled(true);

					    System.out.println("succeed..");
						status.setText("Results after " + totalRounds + " rounds          " );
				 }
			 });
		 } catch (InterruptedException ie) {
			 Thread.currentThread().interrupt();
			 }
	   }
	 }
	
	class ScrollPanel8 extends Panel implements AdjustmentListener{

		public double value;
		double maxvalue;
		double minvalue;
		int x;
		int y;
		Label lvalue=new Label("");
		JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,0,1,0,40);
		
		public ScrollPanel8(int minvalue, int maxvalue,int defaultvalue){
		//JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,defaultvalue,1,minvalue, maxvalue);

		setLayout(new GridBagLayout());
		value =defaultvalue;
		this.maxvalue =maxvalue;
		this.minvalue=minvalue;
		lvalue=new Label(Integer.toString(defaultvalue));
		//sb.setValue ((int)Math.round(100*(defaultvalue-minvalue)/(maxvalue-minvalue)));
		sb.setValue(defaultvalue);
		sb.addAdjustmentListener( this);
		}
		public void adjustmentValueChanged(AdjustmentEvent ame){
			Object obj=ame.getSource() ;
			int arg=ame.getAdjustmentType() ;
				if(obj.equals(this.sb)){
					if(arg==AdjustmentEvent.TRACK){
						value=minvalue+(int)sb.getValue();
						//value= (int)sb.getValue();
					}
					else if(arg==AdjustmentEvent.UNIT_INCREMENT){
						value+=1;
						}
					else if(arg==AdjustmentEvent.UNIT_DECREMENT){
						value-=1;
						}
					
					/*
					else if(arg==AdjustmentEvent.BLOCK_INCREMENT){
						value+=2;
						}
					else if(arg==AdjustmentEvent.BLOCK_DECREMENT){
						value-=2;
						} */
				}
			
			this.repaint();
			lvalue.setText(Integer.toString((int) value));
			
			writeVariables();
			evolve.setEnabled(true);
			
		    //repaint everything
			da.start = false;
			da.currentRound = 0;
			da.repaint();
			
		}

		
		public float value(){
			return (int)value;
		}
		
		public void writeVariables()
		{
			//has successfully written values
			System.out.println("beta" + beta.value());
			System.out.println("transport cost for retailer A:"+ transcostA.value());
			System.out.println("transport cost for retailer B:"+ transcostB.value());
			System.out.println("demand A: " + demandA.value());
			System.out.println("retailprice_A" + retailprice_A.value());
			//System.out.println(retailprice_B.value());
			System.out.println("supplier price A" + supplyprice_A.value());
			//System.out.println(supplyprice_B.value());		
			System.out.println("Number of retailer A" + retailerA.value());
			//System.out.println(retailerB.value());
			System.out.println("Number of supplier A" + supplierA.value());

			System.out.println("Number of cells " + cellNum.value());
			
			System.out.println("Number of customers on cells " + customerOnCell.value());
			//System.out.println(supplierB.value());
			
			System.out.println("Number of rounds" + rounds.value());
		}

	}
	
	
	class ScrollPanel9 extends Panel implements AdjustmentListener{

		public int value;
		int maxvalue;
		int minvalue;
		int x;
		int y;
		Label lvalue=new Label("");
		JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,0,1,0,50);
		
		public ScrollPanel9(int minvalue, int maxvalue,int defaultvalue){
		//JScrollBar sb=new JScrollBar(JScrollBar.HORIZONTAL,defaultvalue,1,minvalue, maxvalue);

		setLayout(new GridBagLayout());
		value =defaultvalue;
		this.maxvalue =maxvalue;
		this.minvalue=minvalue;
		lvalue=new Label(Integer.toString(defaultvalue));
		//sb.setValue ((int)Math.round(100*(defaultvalue-minvalue)/(maxvalue-minvalue)));
		sb.setValue(defaultvalue);
		sb.addAdjustmentListener( this);
		}
		public void adjustmentValueChanged(AdjustmentEvent ame){
			Object obj=ame.getSource() ;
			int arg=ame.getAdjustmentType() ;
				if(obj.equals(this.sb)){
					if(arg==AdjustmentEvent.TRACK){
						value=minvalue+(int)sb.getValue();
						//value= (int)sb.getValue();
					}
					else if(arg==AdjustmentEvent.UNIT_INCREMENT){
						value+=1;
						}
					else if(arg==AdjustmentEvent.UNIT_DECREMENT){
						value-=1;
						}
					/*
					else if(arg==AdjustmentEvent.BLOCK_INCREMENT){
						value+=2;
						}
					else if(arg==AdjustmentEvent.BLOCK_DECREMENT){
						value-=2;
						}  */
				}
			
			this.repaint();
			lvalue.setText(Integer.toString((int) value));
			
			writeVariables();
			
			evolve.setEnabled(true);
			
			/*draw supplier locations */
			//da.num_supplier_A  = (int) value;
		    //repaint everything
			da.start = false;
			da.currentRound = 0;
			da.repaint();
		}

		public int value(){
			return (int) value;
		}
		
		public void writeVariables()
		{
			//has successfully written values
			System.out.println("beta" + beta.value());
			System.out.println("transport cost for retailer A: "+ transcostA.value());
			System.out.println("transport cost for retailer B: "+ transcostB.value());
			System.out.println("retailprice_A" + retailprice_A.value());
			//System.out.println(retailprice_B.value());
			System.out.println("supplier price A" + supplyprice_A.value());
			//System.out.println(supplyprice_B.value());		
			System.out.println("Number of retailer A" + retailerA.value());
			//System.out.println(retailerB.value());
			System.out.println("Number of supplier A" + supplierA.value());

			System.out.println("Number of customers " + customerOnCell.value());
			//System.out.println(supplierB.value());
			System.out.println("offset: " + offset.value());
		}
	}		
	
	
	public class DrawArea extends JPanel {
		
		  public boolean start = false; // false means haven't started; true means having started
		  private static final int SIZE = 256;
		  private int a = SIZE / 2;
		  private int b = a;
		  private int r = 4 * SIZE / 5;
		  public int num_supplier_A = 5;
		  public int num_supplier_B = 5;
		  public int currentRound = 1; 
		  //create a list of supplier locations
		  ArrayList supplierLocA = new ArrayList();
		  ArrayList supplierLocB = new ArrayList();
		 

		  public DrawArea()
		  {
			  super(true);
			  this.setPreferredSize(new Dimension(SIZE, SIZE));
		  }
		  
		  protected void paintComponent(Graphics g) {
			   
			    super.paintComponent(g);   
;				 num_supplier_A  = supplierA.value();
				 num_supplier_B = supplierB.value();
		        Graphics2D g2d = (Graphics2D) g;
		        g2d.setRenderingHint(
		            RenderingHints.KEY_ANTIALIASING,
		            RenderingHints.VALUE_ANTIALIAS_ON);
		        g2d.setColor(Color.black);
		        a = getWidth() / 2;
		        b = getHeight() / 2;
		        int m = Math.min(a, b);
		        r =  m / 2;
		        //
		        int r2 = Math.abs(m - r) / 18;
		        //In this program fix the radius of the circle as 15
		        //int r2 = 15;
		        g2d.drawOval(a - r, b - r, 2 * r, 2 * r);
		        
		        //draw a clock
		        for (int i = 0; i < (int) cellNum.value(); i ++)
		        {
		        	double t = 2 * Math.PI * i / cellNum.value();
		        	g2d.drawLine((int) (a + (r - 2* r2 /3)* Math.cos(t)), (int) (b + (r -2* r2/3) * Math.sin(t)), 
		        			(int) (a + r * Math.cos(t)), (int)(b + r * Math.sin(t)));
		        	//String linesize= Double.toString(1+ customerOnCell.value()/100) + "f";
		        	g2d.setStroke(new BasicStroke( 1+ customerOnCell.value()/50));
		        }
		        
	            //g2d.drawLine(arg0, arg1, arg2, arg3)
		        
		        System.out.println("start to repaint the pics.");
		        
		    	if (productType.getSelectedItem().equals("Homogengeous goods: x"))
				{
		    		/** in the case of  homgenous goods*/
			        g2d.setColor(Color.yellow);
			        
			        for (int i = 0; i < num_supplier_A ; i++) {
			            double t = 2 * Math.PI * i / num_supplier_A ;
			            int x = (int) Math.round(a + r * Math.cos(t));
			            int y = (int) Math.round(b + r * Math.sin(t));
			            //g2d.fillOval(x - r2, y - r2, 2*r2, 2*r2);
			            g2d.fillRect(x - r2, y - r2, 2*r2, 2*r2);
			          
			        }
			        
			        System.out.println("End here...");
			        
			        if (start)
			        {
			        	
			        	System.out.print("start to draw step: " + currentRound);
				        
				        supplierLocA.clear();
				        supplierLocB.clear();
				        
				        for (int i = 0; i < ce.supplierA.size(); i ++)
				        {
				        	//add all supplier locations to the list
				        	supplierLocA.add(ce.supplierA.get(i).getLocation());
				        }
				        //System.out.println("ce.historyofclusterStat" + ce.historyofclusterStat.get(currentRound));
				        System.out.println("historyofclusterStat" + ce.historyofLocationStat_A);
				        System.out.println("historyofclusterStat" + ce.historyofLocationStat_A.get(currentRound-1).size());
				        System.out.println("ce.historyofclusterStat" +  ce.historyofclusterStat_A.get(currentRound-1).get(0));
				       		        
				        System.out.println("current round equals: " + currentRound);
				        
				        for (int i = 0; i < ce.historyofLocationStat_A.get(currentRound-1).size(); i ++)
				        {
				        	int j = 0;
				        	
				        	if (supplierLocA.contains(ce.historyofLocationStat_A.get(currentRound-1).get(i)))
				        	{
				     			for (int p = 0; p < (Double) ce.historyofclusterStat_A.get(currentRound-1).get(i); p ++)
			        			{
					        		double t = (Double) (ce.historyofLocationStat_A.get(currentRound-1).get(i)) * 2 * Math.PI / ChainEffects.SCALE;
					        		int x = (int) Math.round(a +  (r + 2* (p+1) * r2) * Math.cos(t));
					        		int y = (int) Math.round(b + (r + 2* (p+1) * r2)* Math.sin(t));
					        		g2d.setColor(Color.yellow);
					        		g2d.fillOval(x - r2, y - r2, 2*r2, 2*r2);
			        			}
				        	}
				        	else 
				        	{
			        			for (int p = 0; p < (Double) ce.historyofclusterStat_A.get(currentRound-1).get(i); p ++)
			        			{
			        				System.out.println("enter the thir levell.");
			        				System.out.println("ce lcoation stat" + ce.historyofLocationStat_A.get(currentRound-1).get(i));
				        		   double t = (Double) (ce.historyofLocationStat_A.get(currentRound-1).get(i))  * 2 * Math.PI / ChainEffects.SCALE;
				        		   int x = (int) Math.round(a +  (r + 2* p * r2) * Math.cos(t));
				        		   int y = (int) Math.round(b + (r + 2* p * r2)* Math.sin(t));
				        		   g2d.setColor(Color.yellow);
				        		   g2d.fillOval(x - r2, y - r2, 2*r2, 2*r2);
			        			}
				        	}
				        }     
			        }	        
				}
		        
		    	else
		    	{
		    		/**in the case of  heterogenous goods*/
		    		
		    		System.out.println("enter here..");
		    		
			        g2d.setColor(Color.yellow);
			        
			        for (int i = 0; i < num_supplier_A ; i++) {
			            double t = 2 * Math.PI * i / num_supplier_A ;
			            int x = (int) Math.round(a + r * Math.cos(t));
			            int y = (int) Math.round(b + r * Math.sin(t));
			            //g2d.fillOval(x - r2, y - r2, 2*r2, 2*r2);
			            g2d.fillRect(x - r2, y - r2, 2*r2, 2*r2);
			        }
		    		
			        g2d.setColor(Color.RED);
			        
			        for (int i = 0; i < num_supplier_B ; i++) {
			            double t = 2 * Math.PI * i / num_supplier_B +  2 * Math.PI * offset.value / cellNum.value();
			            int x = (int) Math.round(a + r * Math.cos(t));
			            int y = (int) Math.round(b + r * Math.sin(t));
			            //g2d.fillOval(x - r2, y - r2, 2*r2, 2*r2);
			            g2d.fillRect(x - r2, y - r2, 2*r2, 2*r2);
		    		
			        }
			        
			        if (start)
			        {
			        	
			        	System.out.print("start to draw");
				        
			        	//g2d.setColor(Color.yellow);
				        
				        supplierLocA.clear();
				        supplierLocB.clear();
				        
				        for (int i = 0; i < ce.supplierA.size(); i ++)
				        {
				        	//add all supplier locations to the list
				        	supplierLocA.add(ce.supplierA.get(i).getLocation());
				        }
				        
				        for (int i = 0; i < ce.supplierB.size(); i ++)
				        {
				        	//add all supplier locations to the list
				        	supplierLocB.add(ce.supplierB.get(i).getLocation());
				        }
				        
				        //System.out.println("ce.historyofclusterStat" + ce.historyofclusterStat.get(currentRound));
				        System.out.println("historyofLocStat A:" + ce.historyofLocationStat_A);
				        //System.out.println("current round = " + currentRound);
				       // System.out.println("historyofcLocStat A: " + ce.historyofLocationStat_A.get(currentRound-1).size());
				        System.out.println("ce.historyofclusterStat A" +  ce.historyofclusterStat_A);
				       	
				        System.out.println("historyoLocationStat B:" + ce.historyofLocationStat_B);
				        //System.out.println("historyofclusterStat B: " + ce.historyofLocationStat_B.get(currentRound-1).size());
				        System.out.println("ce.historyofclusterStat B" +  ce.historyofclusterStat_B);
				        
				        System.out.println("supplierLocA" + supplierLocA);
				        
				        System.out.println("supplierLocB" + supplierLocB);      
				        
				        System.out.println("current round: " + currentRound);
				       
				        for (int i = 0; i < ce.historyofLocationStat_A.get(currentRound-1).size(); i ++)
				        {
				        	
				        	if (ce.historyofLocationStat_B.get(currentRound-1).contains(ce.historyofLocationStat_A.get(currentRound-1).get(i)))
				          	{
				        		/* if retailers B and retailer A co-locate**/
				        		if (supplierLocA.contains(ce.historyofLocationStat_A.get(currentRound-1).get(i)) || 
				        			supplierLocB.contains(ce.historyofLocationStat_A.get(currentRound-1).get(i)))
				        		{
				        			/*if there is a or more suppliers there, draw them next to suppliers*/
				        			/*first draw retailers of x*/
				        			int p = 0;
				        			double temp = (Double) ce.historyofLocationStat_A.get(currentRound-1).get(i);
				        			for (p = 0; p < (Double) ce.historyofclusterStat_A.get(currentRound-1).get(i); p ++)
				        			{
				        				g2d.setColor(Color.yellow);
				        				double t = (Double) (ce.historyofLocationStat_A.get(currentRound-1).get(i)) * 2 * Math.PI / ChainEffects.SCALE;
						        		int x = (int) Math.round(a +  (r + 2* (p+1) * r2) * Math.cos(t));
						        		int y = (int) Math.round(b + (r + 2* (p+1) * r2)* Math.sin(t));
						        		
						        		g2d.fillOval(x - r2, y - r2, 2*r2, 2*r2);
				        			}
				        			
				        			//System.out.println("i equals: " + i);
				        			//System.out.println("current round equals: " + currentRound);
				        			//System.out.println ("ce.historyofclusterStat_B.get(currentRound-1).get(i): " );
				        			//System.out.println(ce.historyofclusterStat_B.get(currentRound-1).get(i));
				        			/*seconly draw retailer of y*/
				        			System.out.println("temp: " + temp);
				        			System.out.println("ce.historyofLocationStat_B.get(currentRound-1).indexOf(temp) " + 
				        					ce.historyofLocationStat_B.get(currentRound-1).indexOf(temp));
				        			System.out.println("ce.historyofclusterStat_B.get(currentRound-1).get temp: " + 
				        					ce.historyofclusterStat_B.get(currentRound-1).get(
				        					ce.historyofLocationStat_B.get(currentRound-1).indexOf(temp)));
				        			for (int q = 0; q < (Double) ce.historyofclusterStat_B.get(currentRound-1).get(
				        					ce.historyofLocationStat_B.get(currentRound-1).indexOf(temp)); q ++)
				        			{
				        				g2d.setColor(Color.RED);
						        		double t = (Double) temp * 2 * Math.PI / ChainEffects.SCALE;
						        		int x = (int) Math.round(a +  (r + 2* (p+q+1) * r2) * Math.cos(t));
						        		int y = (int) Math.round(b + (r + 2* (p+q+1) * r2)* Math.sin(t));
						        		g2d.fillOval(x - r2, y - r2, 2*r2, 2*r2);
				        			}
				        		}
				        		
				        		else
				        		{
				        			//if retailers x and retailers of y stock up but not on suppliers' locationns
				        			int p = 0;
				        			double temp = (Double) ce.historyofLocationStat_A.get(currentRound-1).get(i);
				        			for (p = 0; p < (Double) ce.historyofclusterStat_A.get(currentRound-1).get(i); p ++)
				        			{
				        				g2d.setColor(Color.yellow);
				        				double t = (Double) (ce.historyofLocationStat_A.get(currentRound-1).get(i)) * 2 * Math.PI / ChainEffects.SCALE;
						        		int x = (int) Math.round(a +  (r + 2* p * r2) * Math.cos(t));
						        		int y = (int) Math.round(b + (r + 2* p * r2)* Math.sin(t));
						        		
						        		g2d.fillOval(x - r2, y - r2, 2*r2, 2*r2);
				        			}
				        			
				        			System.out.println("ce.historyofLocationStat_B.get(currentRound-1).indexOf(temp) " + 
				        					ce.historyofLocationStat_B.get(currentRound-1).indexOf(temp));
				        			System.out.println("ce.historyofclusterStat_B.get(currentRound-1).get temp: " + 
				        					ce.historyofclusterStat_B.get(currentRound-1).get(
				        					ce.historyofLocationStat_B.get(currentRound-1).indexOf(temp)));
				        			for (int q = 0; q < (Double) ce.historyofclusterStat_B.get(currentRound-1).get(
				        					ce.historyofLocationStat_B.get(currentRound-1).indexOf(temp)); q ++)
				        			{
				        				g2d.setColor(Color.RED);
						        		double t = (Double) temp * 2 * Math.PI / ChainEffects.SCALE;
						        		int x = (int) Math.round(a +  (r + 2* (p+q) * r2) * Math.cos(t));
						        		int y = (int) Math.round(b + (r + 2* (p+q) * r2)* Math.sin(t));
						        		g2d.fillOval(x - r2, y - r2, 2*r2, 2*r2);
				        			}
				        		}
				        	  }
				        	  
				        	  else
				        	  {
				        		  System.out.println("enter here...");
				        		/**if A and B do not co-locate, draw retailers of x 's locations */
				        		  
					        	if (supplierLocA.contains(ce.historyofLocationStat_A.get(currentRound-1).get(i)) ||
					        	   supplierLocB.contains(ce.historyofLocationStat_A.get(currentRound-1).get(i))	)
					        	{
					        		/*if A colocate with a supplier, make it next to the supplier  with longer radius*/
					        		System.out.println("enter here 2nd");
					     			for (int p = 0; p < (Double) ce.historyofclusterStat_A.get(currentRound-1).get(i); p ++)
				        			{
						        		double t = (Double) (ce.historyofLocationStat_A.get(currentRound-1).get(i)) * 2 * Math.PI / ChainEffects.SCALE;
						        		int x = (int) Math.round(a +  (r + 2* (p+1) * r2) * Math.cos(t));
						        		int y = (int) Math.round(b + (r + 2* (p+1) * r2)* Math.sin(t));
						        		g2d.setColor(Color.yellow);
						        		g2d.fillOval(x - r2, y - r2, 2*r2, 2*r2);
				        			}
					        	}
					        	else 
					        	{
					        		System.out.println("enter here 3rd");
				        			for (int p = 0; p < (Double) ce.historyofclusterStat_A.get(currentRound-1).get(i); p ++)
				        			{
				        				System.out.println("enter the thir level.");
				        				System.out.println("ce lcoation stat" + ce.historyofLocationStat_A.get(currentRound-1).get(i));
					        		   double t = (Double) (ce.historyofLocationStat_A.get(currentRound-1).get(i))  * 2 * Math.PI / ChainEffects.SCALE;
					        		   int x = (int) Math.round(a +  (r + 2* p * r2) * Math.cos(t));
					        		   int y = (int) Math.round(b + (r + 2* p * r2)* Math.sin(t));
					        		   g2d.setColor(Color.yellow);
					        		   g2d.fillOval(x - r2, y - r2, 2*r2, 2*r2);
				        			}
					        	}
				        	}
				        	  
				         }
				        	
					     for (int i = 0; i < ce.historyofLocationStat_B.get(currentRound-1).size(); i ++)
					     {
					        	
					        if (! ce.historyofLocationStat_A.get(currentRound-1).contains(ce.historyofLocationStat_B.get(currentRound-1).get(i)))
					      	{
					        		/* if retailers of B and retailers of A do not co-locate; only draw B
					        		 * if retailer of B and retailer A colocate, it has been drawn in the previous step,
					        		 * don't have to draw retailers B again */
					            if (supplierLocA.contains(ce.historyofLocationStat_B.get(currentRound-1).get(i)) || 
					        	  supplierLocB.contains(ce.historyofLocationStat_B.get(currentRound-1).get(i)))
					             {
					        			/*if there is a or more suppliers there, draw them next to suppliers*/
					        	
					        			for (int p = 0; p < (Double) ce.historyofclusterStat_B.get(currentRound-1).get(i); p ++)
					        			{
							        		double t = (Double) (ce.historyofLocationStat_B.get(currentRound-1).get(i)) * 2 * Math.PI / ChainEffects.SCALE;
							        		int x = (int) Math.round(a +  (r + 2* (p+1) * r2) * Math.cos(t));
							        		int y = (int) Math.round(b + (r + 2* (p+1) * r2)* Math.sin(t));
							        		g2d.setColor(Color.RED);
							        		g2d.fillOval(x - r2, y - r2, 2*r2, 2*r2);
					        			}
					        	 }
					            else
					            {
				        			for (int p = 0; p < (Double) ce.historyofclusterStat_B.get(currentRound-1).get(i); p ++)
				        			{
				        				System.out.println("enter the this level B.");
				        	
				        				System.out.println("ce lcoation stat" + ce.historyofLocationStat_B.get(currentRound-1).get(i));
					        		   double t = (Double) (ce.historyofLocationStat_B.get(currentRound-1).get(i))  * 2 * Math.PI / ChainEffects.SCALE;
					        		   int x = (int) Math.round(a +  (r + 2* p * r2) * Math.cos(t));
					        		   int y = (int) Math.round(b + (r + 2* p * r2)* Math.sin(t));
					        		   g2d.setColor(Color.RED);
					        		   g2d.fillOval(x - r2, y - r2, 2*r2, 2*r2);
				        			}
					            }    	
		
					       }
					        	
					      
				         }     
		    	}
		    }
	 
		  }

		  }
	
	
	public class DrawPanel extends JPanel
	{
		//
		public DrawPanel()
		{
			setLayout(new BorderLayout());
			add(first);
			add(previous);
			//add(round);
			add(next);
			add(last); 
		}
	}


	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

