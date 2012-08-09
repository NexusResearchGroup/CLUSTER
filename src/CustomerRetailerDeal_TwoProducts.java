import java.util.ArrayList;
import java.util.Collections;

/*
 *@author Arthur Huang May 14, 2008
 *@Affiliation: Dept. of Civil Eng, U of MN 
 */

/**
 * 
 */

/**
 * @author arthurhuang
 *
 */
public class CustomerRetailerDeal_TwoProducts {
	
	//number of retailers of product A staying in different locations
	ArrayList<Double> retailerNumAtOnePlace_A = new ArrayList<Double> ();
	//number of retailers of product B staying in different locations
	ArrayList<Double> retailerNumAtOnePlace_B = new ArrayList<Double> ();
	
	ArrayList<Double> locDistr_A = new ArrayList<Double> ();
	ArrayList<Double> locDistr_B = new ArrayList<Double> ();
	ArrayList <Double> supplierDistance = new ArrayList<Double> ();
	SelectLocation_TwoProducts sl;
	
	public CustomerRetailerDeal_TwoProducts(ArrayList <Customer> customers, ArrayList <Retailer> retailerA, ArrayList <Retailer> retailerB, 
			ArrayList <Supplier> supplierA, ArrayList <Supplier> supplierB)
	{
		/**
		 * calculate location distribution for retailers of product A and retailers of product B
		 */
		getNumAtOnePlace(retailerA, retailerB);

		/**
		 *  step 1: retailer selection location
		 *  step 2: customer start to patronize retailers
		 *  step 3: compute profits
		 */
		sl = new SelectLocation_TwoProducts (customers, retailerA, retailerB, supplierA, supplierB);
	 
		System.out.println("no problem in selecting location.");
		makeDeal(customers, retailerA, retailerB, supplierA, supplierB);
		System.out.println("no problem in making deal.");
		computeProfit(retailerA, retailerB, supplierA, supplierB);
		System.out.println("no problem in copmuting profits.");
	}
	
	public void getNumAtOnePlace(ArrayList <Retailer> retailerA, ArrayList <Retailer> retailerB)
	{  
		/**
		 * @content calculate the number of retailers of product A and product B 
		 */
		retailerNumAtOnePlace_A.clear();
		retailerNumAtOnePlace_B.clear();
		
		locDistr_A.clear();

		for (int i = 0; i < retailerA.size(); i++)
		{
			if (locDistr_A.contains(retailerA.get(i).getLocation()))
			{
				for (int j = 0; j < locDistr_A.size(); j++)
			    {
					if (locDistr_A.get(j).equals(retailerA.get(i).getLocation()))
						retailerNumAtOnePlace_A.set(j, (Double) retailerNumAtOnePlace_A.get(j) + 1);
			    }
			}
			else
			{
				locDistr_A.add(retailerA.get(i).getLocation());
				retailerNumAtOnePlace_A.add(1.0);
			}
		}
		
		locDistr_B.clear();

		for (int i = 0; i < retailerB.size(); i++)
		{
			if (locDistr_B.contains(retailerB.get(i).getLocation()))
			{
				for (int j = 0; j < locDistr_B.size(); j++)
			    {
					if (locDistr_B.get(j).equals(retailerB.get(i).getLocation()))
						retailerNumAtOnePlace_B.set(j, (Double) retailerNumAtOnePlace_B.get(j) + 1);
			    }
			}
			else
			{
				locDistr_B.add(retailerB.get(i).getLocation());
				retailerNumAtOnePlace_B.add(1.0);
			}
		}
	}
	
	
	
	
	public void makeDeal(ArrayList <Customer> customers, ArrayList <Retailer> retailerA, ArrayList <Retailer> retailerB, ArrayList <Supplier> supplierA, ArrayList <Supplier> supplierB) 
	{
		// customers start to choose retailers
		// update retailers profits 
		for (int i = 0; i < customers.size() ; i++)
		{
			//a customer selects a retailer of product A and a retailer of product B to patronize
			customers.get(i).patronizeRetailer(retailerA, retailerB);
			//retailer update sales for retailers of A and B
			retailerA.get(customers.get(i).getSelectedRetailerA()).updateSales(customers.get(i).getDemandA());
			retailerB.get(customers.get(i).getSelectedRetailerB()).updateSales(customers.get(i).getDemandB());
		}
		
		System.out.println("Making deal succeeds!");
		/**
		 * retailers look for the nearest supplier
		 * and update the distance to supplier
		 */
		/*
		for (int j = 0; j < retailerA.size(); j++)
		{
			retailerA.get(j).setDistance2Supplier(sl.getSupplierDistance(retailerA, supplierA, j));
			retailerB.get(j).setDistance2Supplier(sl.getSupplierDistance(retailerB, supplierB, j));
		}  */
		
		for (int j = 0; j < retailerA.size(); j++)
		{
			patronizeSupplier(retailerA, supplierA, j);
		}
		for (int j = 0; j < retailerB.size(); j++)
		{
			patronizeSupplier(retailerB, supplierB, j);
		}
		
		
	}
	
	// retailer find its nearest supplier
	// index:  member number of retailers
	public void patronizeSupplier(ArrayList <Retailer> retailers, ArrayList <Supplier> suppliers, int index)
	{
	    /**
	     * retailers patronize suppliers
	     * update the distance to supplier info
	     */
		supplierDistance.clear();
		for (int i = 0; i < suppliers.size(); i++)
		{
			double arc_1 = Math.abs(retailers.get(index).getLocation() - suppliers.get(i).getLocation());
			double arc_2 = ChainEffects.SCALE - arc_1;
			supplierDistance.add( Math.min(arc_1, arc_2));
		}
		retailers.get(index).setDistance2Supplier(Collections.min(supplierDistance));
		//System.out.println("nearest distance " + Collections.min(supplierDistance));
	}
	
	
	
	
	public void computeProfit(ArrayList <Retailer> retailerA, ArrayList <Retailer> retailerB, ArrayList <Supplier> supplierA, ArrayList <Supplier> supplierB)
	{
		/**
		 * @content calculate the revenue for retailers of product A and retailers of product B
		 * @output update the revenue for each retailer  
		 */
		
		for (int i = 0 ; i < retailerA.size(); i++)
		{
			retailerA.get(i).calculateProfit(supplierA.get(0).getPrice());
			retailerA.get(i).PriceHistory.add(retailerA.get(i).getPrice());
			retailerA.get(i).ProfitHistory.add(retailerA.get(i).getProfit());
			retailerA.get(i).ExpectedProfitHistory.add(retailerA.get(i).getExpectProfit());
			retailerA.get(i).SalesHistory.add(retailerA.get(i).getSales());
			retailerA.get(i).LocationHistory.add(retailerA.get(i).getLocation());
			//clear sales record
			retailerA.get(i).setSales(0);
		}
		
		for (int i = 0 ; i < retailerB.size(); i++)
		{
			retailerB.get(i).calculateProfit(supplierB.get(0).getPrice());
			retailerB.get(i).PriceHistory.add(retailerB.get(i).getPrice());
			retailerB.get(i).ProfitHistory.add(retailerB.get(i).getProfit());
			retailerB.get(i).ExpectedProfitHistory.add(retailerB.get(i).getExpectProfit());
			retailerB.get(i).SalesHistory.add(retailerB.get(i).getSales());
			retailerB.get(i).LocationHistory.add(retailerB.get(i).getLocation());
			//clear sales record
			retailerB.get(i).setSales(0);
		}
		
	}

}
