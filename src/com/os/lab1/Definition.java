package linker;

public class Definition {
	private String symbol;
	private Integer location;
	private boolean used;
	
	public Definition(String symbol, Integer location){
		this.symbol = symbol;
		this.location = location;
	}
	
	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getSymbol() {
		return symbol;
	}

	public Integer getLocation() {
		return location;
	}
	
	@Override
	public String toString(){
		return this.symbol + "=" + this.location;
	}
	
}
