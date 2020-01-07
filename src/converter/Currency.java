package converter;
public class Currency {
	private String name;
	private double factor;
	private String symbol;
	
	Currency(String name, double factor, String symbol){
		this.name = name;
		this.factor = factor;
		this.symbol = symbol;
	}	
	
	public String getName() {
		return this.name;
	}
	
	public double getFactor() {
		return this.factor;
	}
	
	public String getSymbol() {
		return this.symbol;
	}

}
