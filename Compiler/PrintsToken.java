

import java.util.ArrayList;

public class PrintsToken {

	void printtoken(ArrayList<ScannerPointer> tokenlist)
	{
		for(int j=0;j<tokenlist.size();j++)
		{
			System.out.println("-----------------------------");
			System.out.println("value is--- "+tokenlist.get(j).getValue());
			System.out.println("Type is---  "+tokenlist.get(j).getType());
			System.out.println("numeric value(if available)"+tokenlist.get(j).getNumericvalue());
			System.out.println("Real value(if available)"+tokenlist.get(j).getRealvalue());
		}
		System.out.println("--------------------------------------------------------------");
	}
	
}
