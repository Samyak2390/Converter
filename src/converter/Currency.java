package converter;

/**
 * This is the Currency class that is mainly used to get name, factor and symbol of Currency instance.
 * 
 * @author Samyak Maharjan, ID: 77202779
 */
public class Currency {
	private String name;
	private double factor;
	private String symbol;
	
	// constructor to initialize variables.
	Currency(String name, double factor, String symbol){
		this.name = name;
		this.factor = factor;
		this.symbol = symbol;
	}	
	
	// getter to get name of currency
	public String getName() {
		return this.name;
	}
	
	// getter to get factor of currency
	public double getFactor() {
		return this.factor;
	}
	
	// getter to get the symbol of currency
	public String getSymbol() {
		return this.symbol;
	}
	
	// overriding the toString() method 
	public String toString(){ 
		return name+" "+factor+" "+symbol;  
	} 

}
