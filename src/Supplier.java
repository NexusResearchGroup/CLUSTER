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
public class Supplier {
	
	private double price;
	private double location;
		
	public Supplier()
	{
		
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
}
