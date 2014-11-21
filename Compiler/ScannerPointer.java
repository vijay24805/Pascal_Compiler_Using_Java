

public class ScannerPointer {

	
	
	
	String value;
	String type;
	int numericvalue;
	float realvalue;

	

	

	public float getRealvalue() {
		return realvalue;
	}

	public void setRealvalue(float realvalue) {
		this.realvalue = realvalue;
	}

	public int getNumericvalue() {
		return numericvalue;
	}

	public void setNumericvalue(int numericvalue) {
		this.numericvalue = numericvalue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	ScannerPointer(String value,String type,int numericvalue,float realvalue)
	{
		this.value=value;
		this.type=type;
		this.numericvalue=numericvalue;
		this.realvalue=realvalue;
	}
	
	
	
}
