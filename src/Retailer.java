import java.util.ArrayList;

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
public class Retailer {
	
	private double price = 0;
	private double location = 0;
	//private int relativeDis = 0;
	private double distance2Supplier;
	private double sales = 0;
	//private double importUnitCost;
	private double profit;
	private double expectProfit = 0;
	private int supplierSelection;
	
    //history of sales amount of retailer i at the end of t
    ArrayList<Double> SalesHistory = new ArrayList<Double>();
	//history of price changing of retailer i
    ArrayList<Double> PriceHistory = new ArrayList<Double>();
	//history of location changing of retailer i
    ArrayList<Double> LocationHistory = new ArrayList<Double>();
    //history of actual profits of retailer i
    ArrayList<Double> ProfitHistory = new ArrayList<Double>();
    //history of expected of retailer i
    ArrayList<Double> ExpectedProfitHistory = new ArrayList<Double>();
	
	//unit transportation cost per distance per product
	private double u_1 = 0.02;
	
	
	public void Retailer()
	{
		
	}
	
	public void setu1(double u_1) 
	{
		this.u_1 = u_1;
	}
	
	public double getu1()
	{
		return this.u_1;
	}
	
	public void setLocation(double location)
    {
    	this.location = location;
    }
    
    public double getLocation()
    {
    	return location;
    }
    
    public double getPrice()
    {
    	return price;
    }
    
    public void setPrice(double price)
    {
    	this.price = price;
    }
    
    public void updateSales(double amount)
    {
    	sales = sales + amount;
    }
    public double getSales()
    {
    	return sales;
    }
    
    public void setSales(double sales)
    {
    	this.sales = sales;
    }
    
    public void setProfit(double profit )
    {                               
    	this.profit = profit;
    }
    public double getProfit()
    {
    	return profit;
    }
    
    public int getSupplierSelection()
    {
    	return supplierSelection;
    }
    
    public void setDistance2Supplier(double distance)
    {
    	this.distance2Supplier = distance;
    }
    
    public double getDistance2Supplier()
    {
    	return distance2Supplier;
    }
    
    public void setExpectProfit(double expectProfit)
    {
    	this.expectProfit = expectProfit;
    }
    
    public double getExpectProfit()
    {
    	return expectProfit;
    }
    
    
    public void calculateProfit(double importUnitcost)
    {
    	profit = sales * price - u_1 * distance2Supplier * sales - sales *importUnitcost;
    	//profit = Round(profit_raw, 2);
    }
    
}
