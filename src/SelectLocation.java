import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
 *@author Arthur Huang May 14, 2008
 *@Affiliation: Dept. of Civil Eng, U of MN 
 */

/**
 * @name select location
 * @content retailers evaluate all the locations and select 
 */

/**
 * @author arthur huang
 *
 */
public class SelectLocation {
	
	//retailers' sequence of selecting locations
	ArrayList<Integer> sequence = new ArrayList<Integer>();
	ArrayList<Double> searchLoc = new ArrayList<Double>();
	ArrayList <Double> supplierDistanceList = new ArrayList();
	Random rand = new Random();
	
	private double distance;
	private double supplierLoc;
	private double locationProfit;
	
	//constructor
	public SelectLocation(ArrayList <Customer> customers, ArrayList <Retailer> retailers, ArrayList<Supplier> suppliers)
	{
		setSequence(retailers);
		selectLocation(customers, retailers, suppliers);
	}
	
	public void setSequence(ArrayList <Retailer> retailers)
	{
		sequence.clear();
		int random = rand.nextInt(retailers.size());
		while (sequence.size() < retailers.size())
		{
			if (!sequence.contains(random))
				sequence.add(random);
			else
				random = rand.nextInt(retailers.size());
		}
	}
	
	//retailers select location
	public void selectLocation(ArrayList <Customer> customers, ArrayList <Retailer> retailers, ArrayList<Supplier> suppliers)
	{
		
		for (int i = 0; i < sequence.size(); i++)
			assessLocation(customers, retailers, suppliers, sequence.get(i));
		
		//for test
		//for (int j = 0; j < retailers.size(); j++)
			//System.out.println("Retailer's" + j + " location is: " + retailers.get(j).getLocation() );
	}
	
	/**
	 * @content retailer index look for location
	 * index: retailer member number 
	 */
	public void assessLocation(ArrayList <Customer> customers, ArrayList <Retailer> retailers, ArrayList<Supplier> suppliers, int index)
	{
		//record different locations that has been recorded 
		searchLoc.clear();
		//System.out.println("For retailer #: " + index);
		//set retailer i's location 
		for (int j = 0; j < ChainEffects.SCALE; j++)
		{
			/*
			 * search for all locations
			 * j: the locaiton
			 */
			double sales = 0;
			
			retailers.get(index).setLocation((double) j);
			
			for (int k = 0; k < customers.size(); k++)
			{
				//update sales
				sales += getExpectedSales( retailers, customers, j, customers.get(k).getLocation());
			}
			
			//System.out.println( "sales is  " + sales);
			
			double profit = sales * (retailers.get(index).getPrice() - suppliers.get(0).getPrice() - getSupplierDistance(retailers, suppliers,index) * retailers.get(0).getu1());
			searchLoc.add(profit);
		}
		//System.out.println("searchLoc " + searchLoc);
		//identify the expected max profits
		retailers.get(index).setExpectProfit(Collections.max(searchLoc));
		//System.out.println("profit is: " + Collections.max(searchLoc));
		//identify the maximum profit place
		retailers.get(index).setLocation(searchLoc.indexOf(Collections.max(searchLoc)));
		//System.out.println("location is: " + searchLoc.indexOf(Collections.max(searchLoc)));
	}
	
	//i: retailer member number
	/**
	 * @return minimum distance from retailer index to suppliers
	 * @content search for the cloest distance to suppliers
	 * index: member number of retailer
	 */
	public double getSupplierDistance(ArrayList <Retailer> retailers, ArrayList<Supplier> suppliers, int index)
	{ 
		//supplier list
		supplierDistanceList.clear();
		double arc_1 =0;
	    double arc_2 =0;
	    double distance = 0;
		for (int i = 0; i < suppliers.size(); i++)
		{
			arc_1 = Math.abs(retailers.get(index).getLocation() - suppliers.get(i).getLocation());
			arc_2 = ChainEffects.SCALE - arc_1;
			distance = Math.min(arc_1, arc_2);
			supplierDistanceList.add(distance);
		}
		//for test
		//System.out.println("shortest distance to supplier" + Collections.min(supplierDistanceList));	
		//sort the list
       return Collections.min(supplierDistanceList);
	   //find the nearest distance	
       /*
       // find the minimum distance to suppliers
       double temp = supplierDistanceList.get(0);
       
       for (int i = 1; i< supplierDistanceList.size(); i ++)
       {
    	   if (supplierDistanceList.get(i) < temp)
    		   temp = supplierDistanceList.get(i);
       }
       
       return temp; */
	   	
	}
	
	//calculate expected sales amount at each place
	// cusLoc:  customers' location
	// reLoc: retailers' location 
    //return  the expected sales amoutn at customer's locaiton k or retailer at location i 
	/**
	 * @content calculate the expected sales from an individual customer
	 */
	public double getExpectedSales(ArrayList <Retailer> retailers, ArrayList <Customer> customers, double reLoc, double cusLoc)
	{
	   double arc_1 =0;
	   double arc_2 =0;
	   double sum_of_utility =0;
	   double retailer_utility =0;
	   for (int i = 0; i < retailers.size(); i ++)
	   {
		   arc_1 = Math.abs(cusLoc - retailers.get(i).getLocation());
		   arc_2 = ChainEffects.SCALE - arc_1;
		   distance =  Math.min(arc_1, arc_2);
		   //in case distance not equal to 0.05
		   if (distance == 0)
			   distance = 0.05;
		   sum_of_utility += Math.exp(Math.pow(distance, customers.get(0).getBeta() ));
		   
		   if (reLoc == retailers.get(i).getLocation())
			   retailer_utility = Math.exp(Math.pow(distance, customers.get(0).getBeta()));
	   }
	   
	   //System.out.println("retailer utility" + retailer_utility);
	   //System.out.println("Sum of utility is: " + sum_of_utility);
	   //to be revised..
	   return (retailer_utility /sum_of_utility * ChainEffects.DEMAND_A); 
	}
	
	

}
