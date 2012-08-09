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
public class CustomerRetailerDeal {
	
	ArrayList <Double> retailerNumAtOnePlace = new ArrayList();
	ArrayList <Double> locDistr = new ArrayList();
	ArrayList <Double> supplierDistance = new ArrayList();
	SelectLocation sl;
	
	public CustomerRetailerDeal(ArrayList <Customer> customers, ArrayList <Retailer> retailers, ArrayList <Supplier> suppliers)
	{
		/**
		 * calculate location distribution
		 */
		getNumAtOnePlace(retailers);

		/**
		 *  step 1: retailer selection location
		 *  step 2: customer start to patronize retailers
		 *  step 3: compute profits
		 */
		sl = new SelectLocation(customers, retailers, suppliers);
	 
		System.out.println("no problem in selecting location.");
		makeDeal(customers, retailers, suppliers);
		System.out.println("no problem in making deal.");
		computeProfit(retailers, suppliers);
		System.out.println("no problem in copmuting profits.");
	}
	
	public void getNumAtOnePlace(ArrayList <Retailer> retailers)
	{
		retailerNumAtOnePlace.clear();
		locDistr.clear();

		for (int i = 0; i < retailers.size(); i++)
		{
			if (locDistr.contains(retailers.get(i).getLocation()))
			{
				for (int j = 0; j < locDistr.size(); j++)
			    {
					if (locDistr.get(j).equals(retailers.get(i).getLocation()))
						retailerNumAtOnePlace.set(j, (retailerNumAtOnePlace.get(j)) + 1);
			    }
			}
			else
			{
				locDistr.add(retailers.get(i).getLocation());
				retailerNumAtOnePlace.add(1.0);
			}
		}
	}
	
	//
	public void makeDeal(ArrayList <Customer> customers, ArrayList <Retailer> retailers, ArrayList <Supplier> suppliers) 
	{
		// customers start to choose retailers
		// update retailers profits 
		for (int i = 0; i < customers.size() ; i++)
		{
			customers.get(i).patronizeRetailer(retailers);
			
			//retailer update sales
			retailers.get(customers.get(i).getSelectedRetailerA()).updateSales(customers.get(i).getDemandA());
		}
		
		//System.out.println("Making deal succeeds!");
		/**
		 * retailers look for the nearest supplier
		 * and update the distance to supplier
		 */
		for (int j = 0; j < retailers.size(); j++)
			retailers.get(j).setDistance2Supplier(sl.getSupplierDistance(retailers, suppliers, j));
			//patronizeSupplier(retailers, suppliers, j);
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
	
	
	
	
	public void computeProfit(ArrayList <Retailer> retailers, ArrayList <Supplier> suppliers)
	{
		/**
		 * @content calculate the revenue for each retailer  
		 * @output update the revenue for each retailer  
		 */
		
		for (int i = 0 ; i < retailers.size(); i++)
		{
			retailers.get(i).calculateProfit(suppliers.get(0).getPrice());
			retailers.get(i).PriceHistory.add(retailers.get(i).getPrice());
			retailers.get(i).ProfitHistory.add(retailers.get(i).getProfit());
			retailers.get(i).ExpectedProfitHistory.add(retailers.get(i).getExpectProfit());
			retailers.get(i).SalesHistory.add(retailers.get(i).getSales());
			retailers.get(i).LocationHistory.add(retailers.get(i).getLocation());
			//clear sales record
			retailers.get(i).setSales(0);
			//retailers.get(i).LocationProfit.clear();
			//retailers.get(i).locCandidates.clear();
		}
	}

}
