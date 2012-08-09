import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
 *@author Arthur Huang May 14, 2008
 *@Affiliation: Dept. of Civil Eng, U of MN 
 *@revised: March 14, 2011
 */

/**
 * @name select location
 * @content retailers evaluate all the locations and select 
 */

/**
 * @author arthur huang
 *
 */
public class SelectLocation_TwoProducts {
	
	//retailers' sequence of selecting locations
	ArrayList<Integer> sequence = new ArrayList<Integer>();
	//list for retailer A that stores profits for each location of the circle
	ArrayList<Double> searchLocA = new ArrayList<Double>();
	//list for retailer B that stores profits for each location of the circle
	ArrayList<Double> searchLocB = new ArrayList<Double>();
	ArrayList <Double> supplierDistanceList = new ArrayList();
	Random rand = new Random();
	
	private double distance;
	private double supplierLoc;
	private double locationProfit;
	
	ArrayList <Retailer> totalRetailerList = new ArrayList();
	
	//constructor
	public SelectLocation_TwoProducts (ArrayList <Customer> customers, ArrayList <Retailer> retailerA, ArrayList <Retailer> retailerB, ArrayList<Supplier> supplierA, ArrayList<Supplier> supplierB)
	{
		/**
		 * @para customers 
		 *       retailerA
		 *       retailerB
		 *       supplierA
		 *       supplierB
		 *      
		 */
		/**combine the retialerA list and retailerB list to  one list: totalRetailerList
		 * the first part is the retailerA list; the second part is the retailerB list. */
		totalRetailerList.clear();
		totalRetailerList.addAll(retailerA);
		totalRetailerList.addAll(retailerB);
		//set sequence for totalRetailerList
		setSequence(totalRetailerList);
		//setSequence2(totalRetailerList);
		//setSequence3(retailerA, retailerB);
		//setSequence4(totalRetailerList);
		System.out.println("Sequnce is: " + sequence);
		selectLocation(customers, retailerA, retailerB, supplierA, supplierB);
		
	}
	
	/*set a random sequence for retailers*/
	public void setSequence(ArrayList <Retailer> totalRetailerList)
	{
		
		sequence.clear();
		int random = rand.nextInt(totalRetailerList.size());
		while (sequence.size() < totalRetailerList.size())
		{
			if (!sequence.contains(random))
				sequence.add(random);
			else
				random = rand.nextInt(totalRetailerList.size());
		}
	}
	
	/*set a sequence for retailers where a retailer of one category moves first, then a retailer of the other category moves..*/
	public void setSequence2(ArrayList <Retailer> totalRetailerList)
	{
		sequence.clear();
		int random = rand.nextInt(totalRetailerList.size());
		while (sequence.size() < totalRetailerList.size())
		{
			if (sequence.size() == 0)
				sequence.add(random);
			
			else if (!sequence.contains(random) && 
					 ((sequence.get(sequence.size()-1) < totalRetailerList.size()/2 && random >= totalRetailerList.size()/2) 
					 ||(sequence.get(sequence.size()-1) >= totalRetailerList.size()/2 && random < totalRetailerList.size()/2 )))
			     sequence.add(random);
			else
				random = rand.nextInt(totalRetailerList.size());
		}
	}
	
	/*a sequence of xxxxyyyy, or yyyyyxxxx*/
	public void setSequence3(ArrayList <Retailer> retailerA, ArrayList <Retailer> retailerB)
	{
		sequence.clear();
		ArrayList<Integer> sequence_x = new ArrayList<Integer>(); //stands for sequence of retailer x
		ArrayList<Integer> sequence_y = new ArrayList<Integer>(); //stands for sequence of retailer y
		
		int random = rand.nextInt(retailerA.size());
		while (sequence_x .size() < retailerA.size())
		{
			if (!sequence_x.contains(random))
				sequence_x.add(random);
			else
				random = rand.nextInt(retailerA.size());
		}
		
		random = rand.nextInt(retailerB.size());
		while (sequence_y.size() < retailerB.size())
		{
			if (!sequence_y.contains(random))
				sequence_y.add(random);
			else
				random = rand.nextInt(retailerB.size());
		}
		
		//index = true, meaning retailers of x move first
		//else, retailers of y move first
		boolean index = rand.nextBoolean();
		
		if (index)
		{
			sequence.addAll(sequence_x);
			for (int i = 0; i < sequence_y.size(); i++)
			{
				sequence.add(sequence_y.get(i) + sequence_x.size());
			}
		}
		else
		{
			for (int i = 0; i < sequence_y.size(); i++)
			{
				sequence.add(sequence_y.get(i) + sequence_x.size());
			}
			sequence.addAll(sequence_x);
		}
		
		/*
		sequence.clear();
		int random = rand.nextInt(totalRetailerList.size());
		
		if (random < totalRetailerList.size()/2)
		{
			//if random < totalRetailerList.size()/2), then first add retailer of x first
			while (sequence.size() < totalRetailerList.size()/2)
			{
				if (!sequence.contains(random))
					sequence.add(random);
				else
					random = rand.nextInt(totalRetailerList.size()/2);
			}
			while (sequence.size() < totalRetailerList.size())
			{
				if (!sequence.contains(random))
					sequence.add(random);
				else 
					random = rand.nextInt(totalRetailerList.size());
			}
		}
		else
		{
			while (sequence.size() < totalRetailerList.size()/2)
			{
				if (!sequence.contains(random) && random > totalRetailerList.size()/2)
					sequence.add(random);
				else
					random = rand.nextInt(totalRetailerList.size());
			}
			
			random = rand.nextInt(totalRetailerList.size());
			
			while (sequence.size() < totalRetailerList.size())
			{
				if (!sequence.contains(random))
					sequence.add(random);
				else
					random = rand.nextInt(totalRetailerList.size()/2);
			}
		} */
		
	
	}
	
	public void setSequence4(ArrayList <Retailer> totalRetailerList)
	{
		sequence.clear();
		sequence.add(3);
		sequence.add(8);
		sequence.add(1);
		sequence.add(5);
		sequence.add(4);
		sequence.add(7);
		sequence.add(0);
		sequence.add(9);
		sequence.add(2);
		sequence.add(6);
	
	}
	
	//retailers select location
	public void selectLocation(ArrayList <Customer> customers, ArrayList <Retailer> retailerA, ArrayList <Retailer> retailerB, 
			  ArrayList<Supplier> supplierA, ArrayList<Supplier> supplierB)
	{
		
		for (int i = 0; i < sequence.size(); i++)
			assessLocation(customers, retailerA, retailerB, supplierA, supplierB, sequence.get(i));
		
		//for test
		for (int j = 0; j < retailerA.size(); j++)
			System.out.print("Retailer A's" + j + " location is: " + retailerA.get(j).getLocation() );
		System.out.println();
		
		for (int j = 0; j < retailerB.size(); j++)
			System.out.print("Retailer B 's" + j + " location is: " + retailerB.get(j).getLocation() );
		System.out.println();
	}
	
	/**
	 * @content retailer index look for location
	 * index: retailer member number 
	 */
	public void assessLocation(ArrayList <Customer> customers, ArrayList <Retailer> retailerA, ArrayList <Retailer> retailerB, 
			 ArrayList<Supplier> supplierA, ArrayList<Supplier> supplierB, int index)
	{
		//record different locations that has been recorded 

		//set retailer i's location 
		double profit;
		//for retailers of product A
		if (index <  retailerA.size())
		{
	        /**
			 * search for all locations
			 * j: the location
			 * @contents: if an index is larger than retaierA size, then retailers of product A choose locations
			 *             if an index is no larger than retailerA size,
			 * @assumption: retailer A list and retailer B list have the same numbers
			 * 
			 */
			searchLocA.clear();
			
			for (int j = 0; j < ChainEffects.SCALE; j++)
			{
				double sales = 0;
				retailerA.get(index).setLocation((double) j);
				//update sales for retailers of product A
				for (int k = 0; k < customers.size(); k++)
				{
					sales += getExpectedSalesForRetailerA ( retailerA, retailerB, customers, j, customers.get(k).getLocation());
					//sales += getExpectedSalesForA ( retailerA, retailerB, customers, j, customers.get(k).getLocation());
				}
				profit = sales * (retailerA.get(index).getPrice() - supplierA.get(0).getPrice() - getSupplierDistance(retailerA, supplierA, index) * retailerA.get(index).getu1());
				searchLocA.add(profit);
			}
			
			//System.out.println("Searc Location for A" + searchLocA);
			retailerA.get(index).setLocation(searchLocA.indexOf(Collections.max(searchLocA)));
			retailerA.get(index).setExpectProfit(Collections.max(searchLocA));
			
		}
		//for retailers of product B
		else
		{
			searchLocB.clear();
			
			for (int j = 0; j < ChainEffects.SCALE; j++)
			{
				double sales = 0;
				
				retailerB.get(index-retailerA.size()).setLocation((double) j);
				//update sales for retailers of product B
				for (int k = 0; k < customers.size(); k++)
				{
					sales += getExpectedSalesForRetailerB(retailerA, retailerB, customers, j, customers.get(k).getLocation());
					//sales += getExpectedSalesForB(retailerA, retailerB, customers, j, customers.get(k).getLocation());
				}
				//compute profits 
				profit = sales * (retailerB.get(index-retailerA.size()).getPrice() - supplierB.get(0).getPrice() - getSupplierDistance(retailerB, supplierB, index-retailerA.size()) * retailerB.get(index-retailerA.size()).getu1());
				searchLocB.add(profit);
			}
			//System.out.println("Searc Location for B" + searchLocB);
			retailerB.get(index-retailerA.size()).setLocation(searchLocB.indexOf(Collections.max(searchLocB)));
			retailerB.get(index-retailerA.size()).setExpectProfit(Collections.max(searchLocB));
		}
	}
	
	/**
	 * @return minimum distance from retailer index to suppliers
	 * @content search for the closet distance to suppliers
	 * @para index: member number of retailer
	 *       retailer list
	 *       supplier list
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
       return Collections.min(supplierDistanceList);
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
	/**
	 * @content calculate the expected sales from an individual customers
	 * @date 9/1/2008
	 * @version 2
	 */
	public double getExpectedSalesForA(ArrayList <Retailer> retailerA,  ArrayList <Retailer> retailerB, ArrayList <Customer> customers, double reLoc, double cusLoc)
	{
		double sum_of_utility = 0;
		double retailer_utility = 0;
		double distance = 0;
		double dist2A = 0;
		double dist2B = 0;
		double distA2B = 0;
		boolean zeroDistance = false;
		
		for (int j = 0; j < retailerB.size(); j++)
		{
			dist2A = getMiniDistance(cusLoc, reLoc);
			dist2B = getMiniDistance(cusLoc, retailerB.get(j).getLocation());
			distA2B = getMiniDistance(reLoc, retailerB.get(j).getLocation());
			
			if (dist2A  + dist2B + distA2B == ChainEffects.SCALE)
				distance = ChainEffects.SCALE;
			else
				distance = 2 * Math.max(dist2A, Math.max(dist2B, distA2B));
			
   		    if (distance == 0)
   		    	zeroDistance = true;
   		    else
   		    	retailer_utility += Math.exp(Math.pow(distance, customers.get(0).getBeta()));	
		}
		
		if (zeroDistance)
		{
			int num_at_cusLoc = 0;
			//see how many retailers of x stay at cusLoc
			for (int i = 0; i < retailerA.size(); i ++)
			{
				if (cusLoc == retailerA.get(i).getLocation())
					num_at_cusLoc ++;
			}
			
			return (1/num_at_cusLoc ) * customers.get(0).getDemandA(); 
		}
		
		else
		{   
		 /**
		  * if consumer at cusLoc stay at other pairs of retailers' locale, then consumer does not patronize this retailer
		  * else calcuate the summation of utiltiy 
		  */	
		 //calculate the summation of utility 
		   for (int i = 0; i < retailerA.size(); i++)
		   {
			   for (int j = 0; j < retailerB.size(); j++)
			   {
	    			//consumer's distance to retailer A
	    			 dist2A = getMiniDistance(cusLoc, retailerA.get(i).getLocation());
	    			//consumer's distance to retailer B
	    			 dist2B = getMiniDistance(cusLoc, retailerB.get(j).getLocation());
	    			//distance between retailer A and retailer B 
	    			 distA2B = getMiniDistance( retailerA.get(i).getLocation(), retailerB.get(j).getLocation());
	    			
	    			/**calculate four categories of trip candidates. 
	    			 * trip 1: i --> A --> B --> i or i --> B --> A --> i
	    			 * trip 3: i ---> A ---> B ---> A ---> i or i ---> B ---> A ---> B ---> i
	    			*/
	    			if (dist2A  + dist2B + distA2B == ChainEffects.SCALE)
	    				distance = ChainEffects.SCALE;
	    			else
	    				distance = 2 * Math.max(dist2A, Math.max(dist2B, distA2B));
	    			
	    			//if  there is distance for other retailers equals zero, then consumer does not patronize this retailer  
	       		    if (distance == 0)
	       		    	return 0;
	       		    else
	       		        sum_of_utility +=  Math.exp(Math.pow(distance, customers.get(0).getBeta()));	
			   }
		   }
		   
		   return (retailer_utility /sum_of_utility) * customers.get(0).getDemandA(); 
		}
			
	}
	
	/**
	 * @content calculate the expected sales from an individual customer
	 * @date 8/26/2008
	 * @version 1
	 */
	public double getExpectedSalesForRetailerA(ArrayList <Retailer> retailerA,  ArrayList <Retailer> retailerB, ArrayList <Customer> customers, double reLoc, double cusLoc)
	{
		
		/**
		 * @para cusLoc:  customers' location
		 *       reLoc: retailer A's location
		 *       sum_of_utlility_1: sum of utility for the scenario where distances between retailers and consumers do not equal 0
		 *       retailer_utlility_1:retailer utility for the scenario where distances between retailers and consumers do not equal 0
		 *       sum_of_utlility_2: sum of utility for  the scenario where there exists 0 distance between retailers and consumers
		 *       retailer_utlility_2: retailer utility for  the scenario where there exists 0 distance between retailers and consumers
		 * @step 1. making a list of trips 
		 *       2. finding minimum travel distance for each trip
		 *       3. calculate utility for each trip
		 *       4. cacluate expected sales for retaielr B
		 * @return expected sales amount at customer's location k or a retailer of product A at location i 
		 * 
		 */
	   double sum_of_utility = 0;
	   double retailer_utility = 0;
	   double distance = 0;
	   double dist2A = 0;
	   double dist2B = 0;
	   double distA2B = 0;
	   
	   //caculate retailer utlity
	   for (int j = 0; j < retailerB.size(); j++)
	   {
		   dist2A = getMiniDistance(cusLoc, reLoc);
		   dist2B = getMiniDistance(cusLoc, retailerB.get(j).getLocation());
		   distA2B = getMiniDistance(reLoc, retailerB.get(j).getLocation());
		   
			if (dist2A  + dist2B + distA2B == ChainEffects.SCALE)
				distance = ChainEffects.SCALE;
			else
				distance = 2 * Math.max(dist2A, Math.max(dist2B, distA2B));
			
   		    if (distance == 0)
   		    	distance = 0.25;

   		    
   		   retailer_utility += Math.exp(Math.pow(distance, customers.get(0).getBeta()));	
		   
	   }
	   
	   //calculate the summation of utility 
	   for (int i = 0; i < retailerA.size(); i++)
	   {
		   for (int j = 0; j < retailerB.size(); j++)
		   {
    			//consumer's distance to retailer A
    			 dist2A = getMiniDistance(cusLoc, retailerA.get(i).getLocation());
    			//consumer's distance to retailer B
    			 dist2B = getMiniDistance(cusLoc, retailerB.get(j).getLocation());
    			//distance between retailer A and retailer B 
    			 distA2B = getMiniDistance( retailerA.get(i).getLocation(), retailerB.get(j).getLocation());
    			
    			/**calculate four categories of trip candidates. 
    			 * trip 1: i --> A --> B --> i or i --> B --> A --> i
    			 * trip 3: i ---> A ---> B ---> A ---> i or i ---> B ---> A ---> B ---> i
    			*/
    			if (dist2A  + dist2B + distA2B == ChainEffects.SCALE)
    				distance = ChainEffects.SCALE;
    			else
    				distance = 2 * Math.max(dist2A, Math.max(dist2B, distA2B));
    			
       		    if (distance == 0)
       		    	distance = 0.25;
       		    
       		    sum_of_utility +=  Math.exp(Math.pow(distance, customers.get(0).getBeta()));	
		   }
	   }
	   //assuming customers' demand keeps constant
	   //System.out.println("Locaton selection Probability for retailer A equals: " + retailer_utility /sum_of_utility );
	  return (retailer_utility /sum_of_utility) * customers.get(0).getDemandA(); 
	 
	}
	
	/**
	 * @content calculate the expected sales from an individual customers
	 * @date 9/1/2008
	 * @version 2
	 */
	public double getExpectedSalesForB(ArrayList <Retailer> retailerA,  ArrayList <Retailer> retailerB, ArrayList <Customer> customers, double reLoc, double cusLoc)
	{
		
		/**
		 * @para cusLoc:  customers' location
		 *       reLoc: retailer B's location
         */
		double sum_of_utility = 0;
		double retailer_utility = 0;
		double distance = 0;
		double dist2A = 0;
		double dist2B = 0;
		double distA2B = 0;
		boolean zeroDistance = false;
		//record the member number of retailer A staying at consumer location -- cusLoc
		//ArrayList <Integer> retailerAList = new ArrayList<Integer>();
		//ArrayList <Integer> retailerBList = new ArrayList<Integer>();
	    //ArrayList<Double> retailerUtilityList = new ArrayList<Double>();
		
		for (int i = 0; i < retailerA.size(); i++)
		{
			 dist2B = getMiniDistance(cusLoc, reLoc);
			 dist2A = getMiniDistance(cusLoc, retailerA.get(i).getLocation());
			 distA2B = getMiniDistance(retailerA.get(i).getLocation(), reLoc);
			
			if (dist2A  + dist2B + distA2B == ChainEffects.SCALE)
				distance = ChainEffects.SCALE;
			else
				distance = 2 * Math.max(dist2A, Math.max(dist2B, distA2B));
			
   		    if (distance == 0)
   		    	zeroDistance = true;
   		    else
   		    	retailer_utility += Math.exp(Math.pow(distance, customers.get(0).getBeta()));	
		}
		
		if (zeroDistance)
		{
			int num_at_cusLoc = 0;
			//see how many retailers of x stay at cusLoc
			for (int i = 0; i < retailerB.size(); i ++)
			{
				if (cusLoc == retailerB.get(i).getLocation())
					num_at_cusLoc ++;
			}
			
			return (1/num_at_cusLoc ) * customers.get(0).getDemandB(); 
		}
		
		else
		{   
		 /**
		  * if consumer at cusLoc stay at other pairs of retailers' locality, then consumer does not patronize this retailer
		  * else calculate the summation of utility
		  */	
		 //calculate the summation of utility 
		   for (int i = 0; i < retailerA.size(); i++)
		   {
			   for (int j = 0; j < retailerB.size(); j++)
			   {
	    			//consumer's distance to retailer A
	    			 dist2A = getMiniDistance(cusLoc, retailerA.get(i).getLocation());
	    			//consumer's distance to retailer B
	    			 dist2B = getMiniDistance(cusLoc, retailerB.get(j).getLocation());
	    			//distance between retailer A and retailer B 
	    			 distA2B = getMiniDistance( retailerA.get(i).getLocation(), retailerB.get(j).getLocation());
	    			 
	    			if (dist2A  + dist2B + distA2B == ChainEffects.SCALE)
	    				distance = ChainEffects.SCALE;
	    			else
	    				distance = 2 * Math.max(dist2A, Math.max(dist2B, distA2B));
	    			
	    			//if  there is distance for other retailers equals zero, then consumer does not patronize this retailer  
	       		    if (distance == 0)
	       		    	return 0;
	       		    else
	       		        sum_of_utility +=  Math.exp(Math.pow(distance, customers.get(0).getBeta()));	
			   }
		   }
		   return (retailer_utility /sum_of_utility) * customers.get(0).getDemandB(); 
		}
			
	}
	
	
	/**
	 * @content calculate the expected sales from an individual customer
	 * @date 8/26/2008
	 * @version 1
	 */
	public double getExpectedSalesForRetailerB(ArrayList <Retailer> retailerA,  ArrayList <Retailer> retailerB, ArrayList <Customer> customers, double reLoc, double cusLoc)
	{
		/**
		 * @para cusLoc:  customers' location
		 *       reLoc: retailer B's location

		 * @return expected sales amount at customer's location k or a retailer of product B at location reLoc 
		 * 
		 */
	   double sum_of_utility =0;
	   double retailer_utility =0;
	   double distance = 0;
	   double dist2A = 0;
	   double dist2B = 0;
	   double distA2B = 0;
	   
	   //caculate retailer utlity
	   for (int i = 0; i < retailerA.size(); i++)
	   {
		   dist2B = getMiniDistance(cusLoc, reLoc);
		   dist2A = getMiniDistance(cusLoc, retailerA.get(i).getLocation());
		   distA2B = getMiniDistance(retailerA.get(i).getLocation(), reLoc);
		   
			if (dist2A  + dist2B + distA2B == ChainEffects.SCALE)
				distance = ChainEffects.SCALE;
			else
				distance = 2 * Math.max(dist2A, Math.max(dist2B, distA2B));
			
   		    if (distance == 0)
   		    	distance = 0.25;
   		    
   		    retailer_utility += Math.exp(Math.pow(distance, customers.get(0).getBeta()));	
		   
	   }
	   
	   //calculate the summation of utility 
	   for (int i = 0; i < retailerA.size(); i++)
	   {
		   for (int j = 0; j < retailerB.size(); j++)
		   {
    			//consumer's distance to retailer A
    			 dist2A = getMiniDistance(cusLoc, retailerA.get(i).getLocation());
    			//consumer's distance to retailer B
    			 dist2B = getMiniDistance(cusLoc, retailerB.get(j).getLocation());
    			//distance between retailer A and retailer B 
    			 distA2B = getMiniDistance( retailerA.get(i).getLocation(), retailerB.get(j).getLocation());

    			if (dist2A  + dist2B + distA2B == ChainEffects.SCALE)
    				distance = ChainEffects.SCALE;
    			else
    				distance = 2 * Math.max(dist2A, Math.max(dist2B, distA2B));
    			
       		    if (distance == 0)
       		    	distance = 0.25;
       		    
       		    sum_of_utility +=  Math.exp(Math.pow(distance, customers.get(0).getBeta()));	
		   }
	   }
	   //System.out.println("Location choice for retailer B equals: " +  retailer_utility /sum_of_utility);
	  return (retailer_utility /sum_of_utility)* customers.get(0).getDemandB(); 
	
	}
}
