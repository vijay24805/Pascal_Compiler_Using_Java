

public class StackElement {

	String tokenvalues;
	int numerics;
	public String getTokenvalues() {
		return tokenvalues;
	}
	public void setTokenvalues(String tokenvalues) {
		this.tokenvalues = tokenvalues;
	}
	public int getNumerics() {
		return numerics;
	}
	public void setNumerics(int numerics) {
		this.numerics = numerics;
	}
	
	
	public StackElement(String tokenvalues,int numerics)
	{
		this.tokenvalues=tokenvalues;
		this.numerics=numerics;
	}
	
	
}
