import java.util.ArrayList;
import java.util.Random;

/*
 *@author Arthur Huang May 14, 2008
 *@Affiliation: Dept. of Civil Eng, U of MN 
 */
import java.util.*;
/**
 * 
 */

/**
 * @author arthurhuang
 *
 */

public class Customer {
	
	private int demandA; 
	private int demandB;
    private double location;
    
    //distance decay equals = -1
    //public static double beta = -1.0;
    
    private double beta = - 1.0;
    
    /**for the scenario of tow products */
    //private double beta = 0.8;
    // a utility list that records the utility for a retailer to patronize retaielrs at supplier locations
    ArrayList<ArrayList> zeroDist_utilityPackage = new ArrayList<ArrayList>();
    //a utilty list that records the retailer number and the its corresponding utility at a supplier locations
    ArrayList<ArrayList> nonzeroDist_utilityPackage = new ArrayList<ArrayList>();
    
    /**for the scenario of one product */
    //private double beta = 0.8;
    // a utilty list that records the retailer number and the its corresponding utility for one customer
    ArrayList<ArrayList> utilityPackage = new ArrayList<ArrayList>();
    //roulette wheel
    ArrayList<Double> prob_wheel = new ArrayList<Double>();
    // the retailer chosen by the customer
    //private int retailerSelection = 0;
    private int retailerASelection = 0;
    private int retailerBSelection = 0;
    //this record the series number of package that has been selected
   // private int numberInPackage;
    
    //ArrayList <Object> retailerUtilty = new ArrayList <Object>();
	
	//constructor
	public Customer()
	{
	}
	
	public void setBeta(double beta)
	{
		this.beta = beta;
	}
	
	public double getBeta()
	{
		return this.beta;
	}
	
	public void setDemandA(int demand)
	{
		this.demandA = demand;
	}
	
	public double getDemandA()
	{
		return demandA;
	}
	
	public void setDemandB(int demand)
	{
		this.demandB= demand;
	}
	
	public double getDemandB()
	{
		return demandB;
	}
	
	public void setLocation(double location)
	{
		this.location = location;
	}
	
	public double getLocation()
	{
		return location;
	}
	
	public int getSelectedRetailerA ()
	{
		return this.retailerASelection;
	}
	
	public int getSelectedRetailerB ()
	{
		return this.retailerBSelection;
	}
	
	//for one product 
	public void patronizeRetailer(ArrayList<Retailer> retailers)
	{
		/**
		 * select the retailer to patronize,
		 * and patronize it
		 */
		calculateUtiliy(retailers);
		setRouletteWheel_oneProduct();
		selectRetailer();
		
		//System.out.println("Selected retailer is: " + retailerSelection);
		
		//retailer update sales
		//retailers.get(retailerSelection).updateSales(demand);
	}
	
    public void calculateUtiliy(ArrayList<Retailer> retailers)
    {
    	utilityPackage.clear();
    	//retailerUtilty.clear();
    	
    	 for (int j = 0; j < retailers.size(); j++)
		{
    		 double utility;
    		 ArrayList <Object> retailerUtilty = new ArrayList <Object>();
    		 //calculate the weak-arc distance between a customer and retailer 					
    		 double arc_1 = Math.abs(retailers.get(j).getLocation() - this.location);
    		 double arc_2 = ChainEffects.SCALE - Math.abs(retailers.get(j).getLocation() - this.location);
    		 double distance =  Math.min(arc_1, arc_2);
    		 if (distance == 0)
    			 distance = 0.05;
    		 utility = Math.exp(Math.pow(distance, beta));	
			
    		 retailerUtilty.add(j);
    		 retailerUtilty.add(utility);
    		 utilityPackage.add(retailerUtilty);
    		 //retailerUtilty.clear();
		}
    	 //for test
    	//System.out.println("retialer's utility package is: " + utilityPackage);
    }
	
	//select retailer using wheel selection
	public void setRouletteWheel_oneProduct()
	{
    	prob_wheel.clear();
    	double sum = 0;
    	double sum_of_probability = 0;
    	double probability;
    	for (int j = 0; j < utilityPackage.size(); j++)
    		sum += (Double) utilityPackage.get(j).get(1);
    	for (int k = 0; k < utilityPackage.size(); k ++)
    	{
    		probability = ((Double) utilityPackage.get(k).get(1))/sum + sum_of_probability;
    		prob_wheel.add(probability);
    		sum_of_probability += ((Double) utilityPackage.get(k).get(1))/sum;
    	}
    	
    	//System.out.println(prob_wheel);
	}
	
	
	
	public void selectRetailer()
	{
		   double random = Math.random();
		   if (random <= prob_wheel.get(0))
		         retailerASelection = (Integer) utilityPackage.get(0).get(0);
		   else 
		   {
			   for (int l = 1; l < prob_wheel.size(); l++)
			   {
				   if (random > prob_wheel.get(l-1) && random <= prob_wheel.get(l))
				   {
					   retailerASelection = (Integer) utilityPackage.get(l).get(0);
					   break;
				   }
			   }
		   }
	}
	
	/*
    public void updateDemand(ArrayList<Retailer> retailers)
    {
    	//to do: based on selected method, if there is enough products
    	// at retailer's side, customer will buy all that he wants at this 
    	//retailer's side. other wise, use routewheel to select another one 
    	//to buy the rest of his demand.  	
    	
    	this.demand -= retailers.get(retailerSelection).getReal_supply();
 
    } */
	
	
	/**for two products*/
	public void patronizeRetailer(ArrayList<Retailer> retailerA, ArrayList<Retailer> retailerB)
	{
		/**
		 * select the retailer to patronize,
		 * and patronize it
		 */
		calculateUtiliy(retailerA, retailerB);
		setRouletteWheel_twoProducts();
		selectRetailerOfNoneZeroDist();
	}
	
	
	public double getMiniDistance(double pointA, double pointB)
	{
		/**
		 * @para pointA, pointB in the circle
		 * @content find the minimum distance between A and B in the circle
		 * @return minimum distance
		 */
		
		double arc_1 = Math.abs(pointA - pointB);
		double arc_2 = ChainEffects.SCALE - arc_1;
		double distance =   Math.min(arc_1, arc_2);
		return distance;
	}
	
    public void calculateUtiliy(ArrayList<Retailer> retailerA, ArrayList<Retailer> retailerB)
    {
    	/**
    	 * @content calucate utility for each trip
    	 * @content calcualte minimum disance for each trip
    	 *          add it to the utilityPackage 
    	 */
		double sum_of_utility = 0;
		double retailer_utility = 0;
		double distance = 0;
		double utility = 0;
		double dist2A = 0;
		double dist2B = 0;
		double distA2B = 0;
		//boolean zeroDistance = false;
		nonzeroDist_utilityPackage.clear();
		zeroDist_utilityPackage.clear();
    	
    	for (int i = 0; i < retailerA.size(); i++)
    	{
    		/**
    		 * first: find out dis2A, dis2B, disA2B which constitute a circle
    		 * step 1: find minimum distance between A and i, B and i, and A and B
    		 * if the summation of the above variables equals zero, then they are respctively dist2A, dist2B,  and distA2B that we need.
    		 * else if one of the three is the summation of the other
    		 *     then the travel distance equals 2* the distance of this one
    		 */
    		for (int j= 0; j < retailerB.size(); j++)
    		{
    			utility = 0;
    			distance = 0;
    			ArrayList <Object> tripUtility = new ArrayList <Object>();
    			//consumer's location to retailer A
    			dist2A = getMiniDistance(this.location, retailerA.get(i).getLocation());
    			//consumer's locaiton to retailer B
    			dist2B = getMiniDistance(this.location, retailerB.get(j).getLocation());
    			//distance between retailer A and retailer B 
    			distA2B = getMiniDistance( retailerA.get(i).getLocation(), retailerB.get(j).getLocation());
    			
    			if (dist2A  + dist2B + distA2B == ChainEffects.SCALE)
    				distance = ChainEffects.SCALE;
    			else
    				distance = 2 * Math.max(dist2A, Math.max(dist2B, distA2B));
    			
       		    if (distance == 0)
       		    {
       		    	/**
       		    	 * if distance equals zero, then put pairs into the zeroDistance utility package
       		    	 */
       		    	/*
       		    	zeroDistanceRetailers  = true;
           		    tripUtility.add(i);
           		    tripUtility.add(j);
           		    zeroDist_utilityPackage.add(tripUtility); */
       		    	distance = 0.25;
       		    }
       		    
       	
       		    	/**
       		    	 * if total distance does not equal zero, then put into the nonzeroDistance utility list
       		    	 */
       		    utility = Math.exp(Math.pow(distance, beta));	
           		    //trip utility includes: retailer A member No. i, retailer B memeber No. j, and travel dis
           		tripUtility.add(i);
           		tripUtility.add(j);
           		tripUtility.add(utility);
           		nonzeroDist_utilityPackage.add(tripUtility);
 
    		}
    		}
    	}
    	
    	public void setRouletteWheel_twoProducts()
    	{
    		/**
    		 * Building up roulette wheel for utilityPackage for nonzeroDist_utilityPackage
    		 */
        	prob_wheel.clear();
        	double sum = 0;
        	double sum_of_probability = 0;
        	double probability;
        	for (int j = 0; j < nonzeroDist_utilityPackage.size(); j++)
        		sum += (Double) nonzeroDist_utilityPackage.get(j).get(2);
        	for (int k = 0; k < nonzeroDist_utilityPackage.size(); k ++)
        	{
        		probability = ((Double) nonzeroDist_utilityPackage.get(k).get(2))/sum + sum_of_probability;
        		prob_wheel.add(probability);
        		sum_of_probability += ((Double) nonzeroDist_utilityPackage.get(k).get(2))/sum;
        	}
        	
        	//System.out.println(prob_wheel);
    	}
    	
    	public void selectRetailerOfZeroDist()
    	{
    		 Random rand1 = new Random();
    		 int k = rand1.nextInt(zeroDist_utilityPackage.size());
    		 retailerASelection = (Integer) zeroDist_utilityPackage.get(k).get(0);
    		 retailerBSelection = (Integer) zeroDist_utilityPackage.get(k).get(1);
    	}
    	
    	public void selectRetailerOfNoneZeroDist()
    	{
    		/**
    		 * select trips from the utilityPackage
    		 * from the trips select retailer A and retailer B to be patronized 
    		 */
    		   double random = Math.random();
    		   if (random <= prob_wheel.get(0))
    		   {
    		         retailerASelection = (Integer) nonzeroDist_utilityPackage.get(0).get(0);
    		         retailerBSelection = (Integer) nonzeroDist_utilityPackage.get(0).get(1);
    		   }
    		   else 
    		   {
    			   for (int l = 1; l < prob_wheel.size(); l++)
    			   {
    				   if (random > prob_wheel.get(l-1) && random <= prob_wheel.get(l))
    				   {
    					   retailerASelection = (Integer) nonzeroDist_utilityPackage.get(l).get(0);
    					   retailerBSelection = (Integer) nonzeroDist_utilityPackage.get(l).get(1);
    					   break;
    				   }
    			    }
    		   }
    	}

    	


}
