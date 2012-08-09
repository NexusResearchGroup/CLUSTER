import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/*
 *@author Arthur Huang May 14, 2008
 *@Affiliation: Dept. of Civil Eng, U of MN
 *@revised:  March 16, 2011
 */

/**
 * Supply chain model
 * @context a discrete circumsference
 *    consumer: choosing location based on the logit model
 *    retailer: profit maximizing
 *    supplier: unevenly distributed -- only influence the 
 * @goal: modeling the existence of centripetal and centrifugal forces 
 * @conditions: consumers' demands are fixed and the same with each other. 
 * 
 * @update date 1/7/2010  consumer's demand can fluctuate from 10 to 40.
 */

/**
 * @author arthurhuang
 *
 */
public class ChainEffects {
	
	public static int RETAILERS_A = 10;
	public static int RETAILERS_B = 0;
	public static int SUPPLIERS_A = 5;
	public static  int SUPPLIERS_B = 0;
	public static int SCALE = 100;
	public static  int DEMAND_A = 10;
	public static int DEMAND_B = 10;
	public static  int CUSTOMERS = 0; 
	public static int CUSTOMER_ON_CELL = 10;
	
	public static double clusternum = 0;
	public static double clusterdensity = 0;
	
	public double totalClusterDensity = 0;
	
	//public static final int ROUNDS = 500;
	
    ArrayList <Retailer> retailerA = new ArrayList <Retailer> ();
	ArrayList <Retailer> retailerB = new ArrayList <Retailer> ();
	 ArrayList <Supplier> supplierA = new ArrayList <Supplier> ();
	ArrayList <Supplier> supplierB = new ArrayList <Supplier> ();
	ArrayList <Customer> customers  = new ArrayList <Customer> ();
	
	//average profit list for the case of different scenarios for retailers of product A
	ArrayList <Double> avgProfit_A = new ArrayList <Double>();
	
	//average profit list for the case of different scenarios for retailers of product B
	ArrayList <Double> avgProfit_B = new ArrayList <Double>();
	
	ArrayList <ArrayList> historyofLocationStat_A = new ArrayList <ArrayList> ();
	ArrayList <ArrayList> historyofclusterStat_A = new ArrayList <ArrayList> ();
	
	ArrayList <ArrayList> historyofLocationStat_B = new ArrayList <ArrayList> ();
	ArrayList <ArrayList> historyofclusterStat_B = new ArrayList <ArrayList> ();
	
	//total number of locales with retailers
	ArrayList <Double> allRetailLoc =   new  ArrayList <Double> ();
	//number of retailer at these locales
	ArrayList  <Double> allRetailStat = new  ArrayList <Double> ();
	
	
	//number of locales that one cluster covers
	ArrayList <Double> clusterLoc =   new  ArrayList <Double> ();
	ArrayList <Double> numRetailAtCluster =   new  ArrayList <Double> ();
	ArrayList <ArrayList> clusterList =   new  ArrayList <ArrayList> ();

	CustomerRetailerDeal crDeal;
	CustomerRetailerDeal_TwoProducts crDeal2;
	
	/**
	 * @ conructor 1 
	 */
	public ChainEffects() {
		//initiallization
		iniPlayers();
		initializeHomogenePlayer ();
		historyofLocationStat_A.clear();
		historyofclusterStat_A.clear();
		historyofLocationStat_B.clear();
		historyofclusterStat_B.clear();
		
		//makeDeal(ROUNDS);
	}
	
	
	public void makeDeal_oneProduct()
	{
			crDeal = new CustomerRetailerDeal( customers, retailerA, supplierA);
			historyofclusterStat_A.add(crDeal.retailerNumAtOnePlace);
	    	historyofLocationStat_A.add(crDeal.locDistr);
	}
	
	public void makeDeal_twoProduct()
	{
	    crDeal2 = new CustomerRetailerDeal_TwoProducts( customers, retailerA, retailerB, supplierA, supplierB);
    
		historyofclusterStat_A.add(crDeal2.retailerNumAtOnePlace_A);
    	historyofLocationStat_A.add(crDeal2.locDistr_A);
    	
    	historyofclusterStat_B.add(crDeal2.retailerNumAtOnePlace_B);
    	historyofLocationStat_B.add(crDeal2.locDistr_B);
    	
    	//System.out.println("historyofLocationStat_A: " + historyofLocationStat_A);
    	//System.out.println("historyofLocationStat_B" + historyofLocationStat_B);
    	
    	//calculateAvgProfit();
	}
	
	
	public void CalculateClusterStat()
	{
		/**
		 * calculate number of clusters and cluster density at the end
		 */
		
		int temp1 = historyofclusterStat_A.size();
		int temp2 = historyofclusterStat_B.size();
		
		if (temp1 > 0)
		{
			
			//get the newest result
			for (int i = 0; i  < historyofLocationStat_A.get(temp1 - 1).size(); i ++)
			{
				allRetailLoc.add((Double) historyofLocationStat_A.get(temp1 - 1).get(i));
				allRetailStat.add((Double) historyofclusterStat_A.get(temp1 - 1).get(i));
			}
			
			if (temp2 > 0)
			{
				for (int j = 0; j < historyofLocationStat_B.get(temp2 - 1).size(); j ++)
				{
					
					
					if (!allRetailLoc.contains((Double) historyofLocationStat_B.get(temp2 - 1).get(j)))
					{
						/* if allCLustLoc does not include retailer B's locations
						 * add B's location to allclusterLoc */
						allRetailLoc.add((Double) historyofLocationStat_B.get(temp2 - 1).get(j));
						allRetailStat.add((Double) historyofclusterStat_B.get(temp2 - 1).get(j));
					}
					else
					{
						int index = allRetailLoc.indexOf((Double)historyofLocationStat_B.get(temp2 - 1).get(j));
						//add the number of retailer B to all retailerStat
						System.out.println("index " + index);
						System.out.println("allRetailStat.get(index): " + allRetailStat.get(index));
						System.out.println("historyofclusterStat_B.get(temp2 - 1) " + historyofclusterStat_B.get(temp2 - 1).get(j));
						allRetailStat.set(index, allRetailStat.get(index) + (Double) historyofclusterStat_B.get(temp2 - 1).get(j));
					}
							
				}
			}
			
			System.out.println("allRetailLoc: " + allRetailLoc);
			System.out.println("allRetailStat: " + allRetailStat);
			
			//sort allRetaiLoc and allRetailStat
			sortClusterLoc();
			
			System.out.println("sorted retailLoc: " + allRetailLoc);
			System.out.println("sorted retail stat: " + allRetailStat);
			
			ArrayList <Double> temp_cluster = new ArrayList <Double> ();
			double temp_clusterSize = 0;
			
			while (allRetailLoc.size() > 0)
			{	
				if (temp_cluster.size() == 0)
				{
					temp_cluster.add(allRetailLoc.get(0));
					temp_clusterSize += allRetailStat.get(0);
					allRetailLoc.remove(0);
					allRetailStat.remove(0);
					System.out.println("temp_cluster " + temp_cluster);
					System.out.println("temp cluster size: " + temp_clusterSize );
					if (allRetailLoc.size() == 0)
					{
						clusterList.add((ArrayList) temp_cluster.clone());
						numRetailAtCluster.add(temp_clusterSize);
						temp_cluster.clear();
						temp_clusterSize = 0;
					}
					
				}
				else
				{
					if (Math.abs(allRetailLoc.get(0) - temp_cluster.get(temp_cluster.size() - 1)) == 1)
					{
				    	 temp_cluster.add(allRetailLoc.get(0));
				    	 temp_clusterSize += allRetailStat.get(0);
				    	 allRetailLoc.remove(0);
				    	 allRetailStat.remove(0);
					}
					else
					{
						System.out.println("enter here...");
						System.out.println("temp_cluster: " + temp_cluster);
						
						clusterList.add((ArrayList) temp_cluster.clone());
						
						numRetailAtCluster.add(temp_clusterSize);
						System.out.println("cluster list: " + clusterList);
						
						temp_cluster.clear();
						temp_clusterSize = 0;
						System.out.println("temp_cluster"  + temp_cluster);
						System.out.println("temp_clusterSize  " + temp_clusterSize );
					}
				}
				
			}
			
			System.out.println("clusterList is: " + clusterList);
			System.out.println("numRetailAtCluster: " + numRetailAtCluster);
			
			clusternum = clusterList.size();
			
			
			for (int i = 0; i < numRetailAtCluster.size(); i ++)
			{
				totalClusterDensity +=  numRetailAtCluster.get(i)/clusterList.get(i).size();
			}
			
			System.out.println("totalClusterDensity" + totalClusterDensity);
			
			clusterdensity = totalClusterDensity / numRetailAtCluster.size();
			
			
			System.out.println("cluster num: " + clusternum);
			System.out.println("cluster density: " + clusterdensity);
		}
		
	}
	
	public void sortClusterLoc()
	{
		/**sort allRetailLoc and allRetailStat 
		 * from small to large***/
		int n = allRetailLoc.size();
	    for (int i = 0; i < n; i ++)
	    {
	    	for (int j = n - 1; j >  i ; j --)
	    	{
	    		if (allRetailLoc.get(j -1) > allRetailLoc.get(j))
	    		{
	    			/*swap allRetailLoc.get(i - 1) and allRetailLoc.get(i)*/
					double temp = allRetailLoc.get(j - 1);
					allRetailLoc.set(j-1, allRetailLoc.get(j));
					allRetailLoc.set(j, temp);
					
					/**do the same for allRetailStat*/
					temp = allRetailStat.get(j - 1);
					allRetailStat.set(j-1, allRetailStat.get(j));
					allRetailStat.set(j, temp);
	    		}
	    	}
	    }
		
	}
	
	public void calculateAvgProfit()
	{
		 double totalprofitA = 0;
		 double totalprofitB = 0;
		 
		 for (int i = 0; i < retailerA.size(); i ++)
		 { 
			 totalprofitA += retailerA.get(i).ProfitHistory.get(retailerA.get(i).ProfitHistory.size() - 1);
		 }
		 avgProfit_A.add(totalprofitA/retailerA.size());
	
		 for (int i = 0; i < retailerB.size(); i ++)
		 { 
			 totalprofitB += retailerB.get(i).ProfitHistory.get(retailerB.get(i).ProfitHistory.size() - 1);
		 }
		 avgProfit_B.add(totalprofitB/retailerB.size());
		 
		 
	}
	
	/**
	 * @content initalize the set of customers, retailers, and suppliers.
	 */
	public void iniPlayers()
	{
		//initilization of players
		
		//initialize customers set
	    customers.clear();
	    retailerA.clear();
	    retailerB.clear();
	    supplierA.clear();
	    supplierB.clear();
	    
	    CUSTOMERS = SCALE * CUSTOMER_ON_CELL;
	    
		for (int i = 0; i < CUSTOMERS; i ++)
		{
			Customer customer = new Customer(); 
			customers.add(customer);
		}
		
		//initialize retailer set
		for (int i = 0; i < RETAILERS_A; i ++)
		{
			Retailer retailer = new Retailer();
			retailerA.add(retailer);
		}
		
		for (int i = 0; i < RETAILERS_B; i ++)
		{
			Retailer retailer = new Retailer();
			retailerB.add(retailer);
		}
		
		for (int i = 0; i < SUPPLIERS_A; i++ )
		{
			Supplier supplier = new Supplier();
			supplierA.add(supplier);
		}
		
		for (int i = 0; i < SUPPLIERS_B; i++ )
		{
			Supplier supplier = new Supplier();
			supplierB.add(supplier);
		}
	}
	
	public void initializeHomogenePlayer ()
	{
		 Random rand = new Random();
		 
			//ini location for customers
		for (int m = 0 ; m < SCALE; m++)
		{
			for (int i = 0; i < CUSTOMER_ON_CELL; i++)
				customers.get((int) (m * CUSTOMER_ON_CELL+ i)).setLocation(m);
		}
		//init price and location for retailers
		for (int i = 0; i < RETAILERS_A; i ++)
		{
			//retailerA.get(i).setPrice(2.5);
			double loc = (double)rand.nextInt(SCALE);
			retailerA.get(i).setLocation(loc);
			retailerA.get(i).LocationHistory.add(loc);
		}
		
		for (int i = 0; i < RETAILERS_B; i ++)
		{
			//retailerB.get(i).setPrice(2.5);
			 double loc = (double)rand.nextInt(SCALE);
			retailerB.get(i).setLocation(loc);
			retailerB.get(i).LocationHistory.add(loc);
			//System.out.println("retailer " + i + " 's location is:" + retailerB.get(i).LocationHistory);
		}
		
		   /*
		for (int i = 0; i < ce.SUPPLIERS_A; i++)
		{
			//supplierA.get(i).setPrice(1.5);
			ce.supplierA.get(i).setLocation(i * (cellNum.value()/ce.SUPPLIERS_A));
			//System.out.println("suppliers' location " + suppliers.get(i).getLocation());
		}
		
		for (int i = 0; i < ce.SUPPLIERS_B; i++)
		{
			//supplierB.get(i).setPrice(1.5);
			ce.supplierB.get(i).setLocation(i *  (cellNum.value()/ce.SUPPLIERS_A) + offset.value());
			//System.out.println("suppliers' location " + suppliers.get(i).getLocation());
		}  */
		
	}
	
	/*
	public static void main(String[] args) throws IOException
	{

		long startTime = System.currentTimeMillis();
		//ChainEffects ce = new ChainEffects(7, 71, 100);
		/*
		 * sensitivity test: test beta
		 * sensitivity test: test u1
		 */
		//ChainEffects ce = new ChainEffects(15, 10, 10);
	/*
		ChainEffects ce = new ChainEffects(10);
		//ce.ChainEffects2(8, 2, 30);
		//ce.ChainEffects3(8, 2, 30);
		System.out.println("The end of the program!!");
		long endTime = System.currentTimeMillis();
		System.out.println("Runing time is " + (double) ((endTime - startTime)/1000) + " seconds.");
	}  */
   
}
