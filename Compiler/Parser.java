

import java.nio.ByteBuffer;
import java.util.ArrayList;


public class Parser {

	Opcodes op=new Opcodes();
	
	
	String savetoken;
	
	int target;
	int i=0;
	String currenttoken;
	String currentvalue;
	// To keep track of code array used during repaet until
	int codepntr=0;
	//counter for for loop
	int target_for=0;
	int for_hole=0;
	//counter_for[0]=0;
	int[] forarry2=new int[4];
	// to check whether semicolon is missin
	int semiclnerror=0;
	int semi=0;
	int leftmiss;
	int numericval;
	float realval;
	int fnd;
	int dp=0;
	int dptemp=0;
	int type=0;
	int typevar=0;
	ArrayList<Byte> Data = new ArrayList<Byte>();
	ArrayList<Byte>  Code=new ArrayList<Byte>();
	ArrayList<String> symTab=new ArrayList<String>();
	
	// Storing int in a byte array
	static byte[] toByteArray(int value) {
	    return new byte[] {
	        (byte) (value >> 24),
	        (byte) (value >> 16),
	        (byte) (value >> 8),
	        (byte) value};
	}
	
	static byte[] toByteArray(char value) {
	    return new byte[] {
	        (byte) (0),
	        (byte) (0),
	        (byte) (value >> 8),
	        (byte) value};
	}
	
	public void update(int savedip , int[] tempcd)
	{
		//byte[] temcd =new byte[4];
		byte[] temcd=Parser.toByteArray(savedip);
		////System.out.println("tempcd value--->" + Code.get(tempcd[3]));
		Code.remove(tempcd[0]);
		Code.add(tempcd[0], temcd[0]);
		Code.remove(tempcd[1]);
		Code.add(tempcd[1], temcd[1]);
		Code.remove(tempcd[2]);
		Code.add(tempcd[2], temcd[2]);
		Code.remove(tempcd[3]);
		Code.add(tempcd[3], temcd[3]);
		//codepntr=codepntr+4;
		//Code.remove(tempcd[0]+)
	
	}
	
	public void update_Case_holes(int codepointer,ArrayList<Integer> trckindex )
	{
		
		int trk=0;
		byte[] temp=Parser.toByteArray(codepointer);
		
		while(trk<trckindex.size()-4)
		{
		
		
			Code.set(trckindex.get(trk),temp[0]);
			Code.set(trckindex.get(trk+1),temp[1]);
			Code.set(trckindex.get(trk+2),temp[2]);
			Code.set(trckindex.get(trk+3),temp[3]);
			trk=trk+4;
			
		
		
		
		}
		
		
		
		
	}
	
	
	
	// Push the converted int->byte[] into Code array.
	public void push(int operand) 
	{
		
		byte[] temp=Parser.toByteArray(operand);
		Code.add(temp[0]);
		Code.add(temp[1]);
		Code.add(temp[2]);
		Code.add(temp[3]);
		codepntr=codepntr+4;
	}
	
	public void pushc(char operand)
	{
		Code.add((byte)operand);
		codepntr++;
	}
	
	
	public void pushi(byte opcode)
	{
		
		Code.add(opcode);
		codepntr++;
		
	}
	
	void E(ArrayList<ScannerPointer> ref,ArrayList<String> symTabb)
	{
		
		
		
		
		
		
		String curop1;
		////System.out.println("Entering E()");
		T(ref,symTabb);
		////System.out.println("current tokken in E() -->"+ currenttoken);
		while(currenttoken.equalsIgnoreCase("TK_ADD") || currenttoken.equalsIgnoreCase("TK_SUB")
			  ||currenttoken.equalsIgnoreCase("TK_EQUAL") )
		{
			////System.out.println("enter because ofplus");
			curop1=currenttoken;
			getnextoken(ref);
			T(ref,symTabb);
			
			
			
		
			
			
			
			if(curop1.equalsIgnoreCase("TK_ADD") && typevar==0)
			{	
				////System.out.println("calling gen1(op plus)");
				gen1(op.ADD);
				if(currenttoken.equalsIgnoreCase("TK_NEXTLINE"))
				{
					getnextoken(ref);
				}
			}
			else if(curop1.equalsIgnoreCase("TK_SUB") && typevar==0)
			{
				////System.out.println("Subtract");
				gen1(op.SUB);
				if(currenttoken.equalsIgnoreCase("TK_NEXTLINE"))
				{
					getnextoken(ref);
				}
			}
			else if(curop1.equalsIgnoreCase("TK_ADD") && typevar==1)
			{
				////System.out.println("Subtract");
				gen1(op.FADD);
				if(currenttoken.equalsIgnoreCase("TK_NEXTLINE"))
				{
					getnextoken(ref);
				}
			}
			else if(curop1.equalsIgnoreCase("TK_SUB") && typevar==1)
			{
				////System.out.println("Subtract");
				gen1(op.FSUB);
				if(currenttoken.equalsIgnoreCase("TK_NEXTLINE"))
				{
					getnextoken(ref);
				}
			}
			else if(curop1.equalsIgnoreCase("TK_NEXTLINE"))
			{
				getnextoken(ref);
				//System.out.println("NEXTLINE--->");
			}
			else
			{
				System.out.println("ERROR");
				
			}
			
				
			
			
		}
		//System.out.println("after parsing variables-->" + currenttoken);
		
	}
	
	void T(ArrayList<ScannerPointer> ref, ArrayList<String> symTabb)
	{
		String curop2;
		//System.out.println("Entering T()");
		F(ref,symTabb);
		//System.out.println("current token in T()--"+currentvalue);
		
		while(currenttoken.equalsIgnoreCase("TK_MUL") || currenttoken.equalsIgnoreCase("TK_DIV") )
		{	
			//System.out.println("inside the tk_times");
			curop2=currenttoken;
			getnextoken(ref);
			F(ref,symTabb);
			
			if(curop2.equalsIgnoreCase("TK_MUL") && typevar==0)
			{	
				//System.out.println("calling gen1(op multipy)");
				gen1(op.MUL);
				if(currenttoken.equalsIgnoreCase("TK_NEXTLINE"))
				{
					getnextoken(ref);
				}
			}
			if(curop2.equalsIgnoreCase("TK_MUL") && typevar==1)
			{	
				//System.out.println("calling gen1(op multipy)");
				gen1(op.FMUL);
				if(currenttoken.equalsIgnoreCase("TK_NEXTLINE"))
				{
					getnextoken(ref);
				}
			}
			else if(curop2.equalsIgnoreCase("TK_DIV") && typevar==0)
			{
				////System.out.println("Subtract");
				gen1(op.DIV);
				if(currenttoken.equalsIgnoreCase("TK_NEXTLINE"))
				{
					getnextoken(ref);
				}
			}
			else if(curop2.equalsIgnoreCase("TK_DIV") && typevar==1)
			{
				////System.out.println("Subtract");
				gen1(op.FDIV);
				if(currenttoken.equalsIgnoreCase("TK_NEXTLINE"))
				{
					getnextoken(ref);
				}
			}
		}
		
	}
	
	void F(ArrayList<ScannerPointer> ref,ArrayList<String> symTabb)
	{
		if(currenttoken.equalsIgnoreCase("TK_INTEGER"))
		{
		typevar=0;
		}
		else if(currenttoken.equalsIgnoreCase("TK_REAL") )
		{
			typevar=1;
			
			
		}
		
		//System.out.println("Entering F()");
		if(currenttoken.equalsIgnoreCase("TK_INTEGER"))
		{
		   //System.out.println("inserting pushi");	 
		   gen1(op.PUSHI);
		   //System.out.println("Inserted value" + currentvalue);
		   gen4(numericval);
		   
		   getnextoken(ref);
		}
		else if(currenttoken.equalsIgnoreCase("TK_REAL"))
		{
			//System.out.println("inserting pushi");	 
			   gen1(op.PUSHI);
			   //System.out.println("Inserted value" + currentvalue);
			   int val;
			   val=Float.floatToIntBits(realval); 
			   gen4(val);
			   
			   getnextoken(ref);
		}
		else if(currenttoken.equalsIgnoreCase("TK_STRING"))
		{
			//System.out.println("inserting pushi");	 
			   gen1(op.PUSHI);
			 //  System.out.println("Inserted value" + currentvalue);
			   int val;
			   val=currentvalue.charAt(0);
			   gen4(val);
			   
			   getnextoken(ref);
		}
		else if(currenttoken.equalsIgnoreCase("TK_VAR"))
		{
			int address;
			gen1(op.PUSH);
			//System.out.println("----------PUSHED------" + currentvalue);
			for(int cnt=0;cnt<symTabb.size();cnt++)
			{
				if(symTabb.get(cnt).equalsIgnoreCase(currentvalue))
				{
					fnd=cnt;
				}
			}
			address=Integer.parseInt(symTab.get(fnd+3));
			//System.out.println("---------addr----" + address);
			gen4(address);
			getnextoken(ref);
			
		}
		else
		{
			//System.out.println("invalid");
			getnextoken(ref);
		}
		
			
		
	}
	
	
	void L(ArrayList<ScannerPointer> ref,ArrayList<String> symTabb)
	{
		String curop1;
		//System.out.println("Entering L()");
		//getnextoken(ref);
		while(currenttoken.equalsIgnoreCase("TK_EQUAL") || currenttoken.equalsIgnoreCase("TK_LESSTHAN")
				|| currenttoken.equalsIgnoreCase("TK_GREATERTHAN"))
		{
			//System.out.println("enter because of EQUAL or LSSTHAN");
			curop1=currenttoken;
			getnextoken(ref);
			F(ref,symTabb);
			
			if(curop1.equalsIgnoreCase("TK_EQUAL"))
			{	
				//System.out.println("calling gen1(op equals)");
				gen1(op.EQUAL);
				if(currenttoken.equalsIgnoreCase("TK_NEXTLINE"))
				{
					getnextoken(ref);
				}
			}
			else if(curop1.equalsIgnoreCase("TK_LESSTHAN"))
			{
				//System.out.println("calling gen1(op LESS THAN)");
				gen1(op.LESSTHAN);
				i--;
				if(currenttoken.equalsIgnoreCase("TK_NEXTLINE"))
				{
					getnextoken(ref);
					////System.out.println("inside nextline");
				}

			}
			else if(curop1.equalsIgnoreCase("TK_LessTEqual"))
			{
				//System.out.println("calling gen1(op less than equal)");
				gen1(op.LessTEqual);
				i--;
				if(currenttoken.equalsIgnoreCase("TK_NEXTLINE"))
				{
					getnextoken(ref);
					////System.out.println("inside nextline");
				}

			}
			else if(curop1.equalsIgnoreCase("TK_GREATERTHAN"))
			{
				//System.out.println("calling gen1(op greater THAN)");
				gen1(op.GREATERTHAN);
				i--;
				if(currenttoken.equalsIgnoreCase("TK_NEXTLINE"))
				{
					getnextoken(ref);
					////System.out.println("inside nextline");
				}

			}
			else if(curop1.equalsIgnoreCase("TK_NEXTLINE"))
			{
				getnextoken(ref);
				//System.out.println("NEXTLINE--->");
			}
			else
			{
				System.out.println("COMPILER ERROR");
			}
		}

	}
		
	
	public void gen1(byte opcode) {
		
		pushi(opcode);
		
	}

	public void gen4(int value) {
		
		push(value);
		
	}
	
	public void gen4(double value)
	{
		
	}
	
	public void genCh(char value) {
		
		pushc(value);
		
	}
	
	public void write_st_with_var(ArrayList<ScannerPointer> tokenpointer)
	{
		gen1(op.Wrtie_with_var);
		
		 //System.out.println("Entering write ---> " + currentvalue);
	    
		 
		    int strlength=0;
			int srtlen=0;
			
			//System.out.println("before brack writeln-->" + currenttoken);
			getnextoken(tokenpointer);
			//System.out.println("after wtite shld be ( " + currenttoken);
			if(currenttoken.equalsIgnoreCase("TK_LEFTPRTH"))
			{
			  getnextoken(tokenpointer);
			  //System.out.println("inside brackets-->" + currentvalue);
				
				if(currenttoken.equalsIgnoreCase("TK_STRING"))
				{
						////System.out.println("writeln String-->" + currentvalue);
					strlength=currentvalue.length();
					gen4(strlength);
					for(srtlen=0;srtlen<strlength;srtlen++)
					{
					////System.out.println("char--->" + currentvalue.charAt(srtlen));
					genCh(currentvalue.charAt(srtlen));
					}
					
					getnextoken(tokenpointer);
					
					if(currenttoken.equalsIgnoreCase("TK_COMMA"))
					{
						getnextoken(tokenpointer);
						
						if(currenttoken.equalsIgnoreCase("TK_VAR"))
						{
							
							variable(tokenpointer);
							
							//System.out.println("after passing var-->"+ currentvalue);
							if(type==0)
							{
							gen1(op.Pop_at);
							}
							else if(type==1)
							{
								//System.out.println("onlu twice");
								gen1(op.pop_at_real);
							}
							else if(type==2)
							{
								//System.out.println("onlu twice");
								gen1(op.pop_at_char);
							}
							
							
							
							if(currenttoken.equalsIgnoreCase("TK_WRITELN"))
							{
								//System.out.println("shld be what" + currentvalue);
								i=i-3;
								getnextoken(tokenpointer);
								//System.out.println("shld be writeln" + currentvalue);
							}
							
							
							if(currenttoken!="TK_END")
							getnextoken(tokenpointer);
						
						}
						
						
						
						
					}
					
				}
						
			}
			else
			{
				System.out.println("COMPILER ERROR");
			}
		 
		
		if(currenttoken!="TK_SEMICOLON")
		{
			System.out.println("---------------------------------");
			System.out.println("COMPILER ERROR-SEMICOLON MISSING IN THE END OF WRITELN");
			System.out.println("-----------------------------------");
		}
		getnextoken(tokenpointer);
		//System.out.println("Current token is " + currentvalue);
		
		
	}
	
	public void write_str(ArrayList<ScannerPointer> tokenpointer) {
		// TODO Auto-generated method stub
		gen1(op.WriteStr);
		int strlength=0;
		int srtlen=0;
		//System.out.println("entering write");
		//i=i+2;
		getnextoken(tokenpointer);
		//System.out.println("before brack writeln-->" + currenttoken);
		if(currenttoken.equalsIgnoreCase("TK_LEFTPRTH"))
		{
		  getnextoken(tokenpointer);
		  
			
			if(currenttoken.equalsIgnoreCase("TK_STRING"))
			{
				//System.out.println("writeln String-->" + currentvalue);
				strlength=currentvalue.length();
				gen4(strlength);
				for(srtlen=0;srtlen<strlength;srtlen++)
				{
				////System.out.println("char--->" + currentvalue.charAt(srtlen));
				genCh(currentvalue.charAt(srtlen));
				}
				
								
				getnextoken(tokenpointer);
				
			}
					
		}
		getnextoken(tokenpointer);
		getnextoken(tokenpointer);
		//System.out.println("inside writeln-->" + currentvalue);
	}
	
	public void variable(ArrayList<ScannerPointer> tokenpntr)
	{
		
		//System.out.println("ENTERING VARIABLE AGAIN" + currentvalue);
		while(currenttoken.equalsIgnoreCase("TK_VAR"))
		{
			
		  if(check_if_arrayvar(tokenpntr))
		  {
			//System.out.println("Pushed variable");
			gen1(op.PUSH);
			for(int cnt=0;cnt<symTab.size();cnt++)
			{
				if(symTab.get(cnt).equalsIgnoreCase(currentvalue))
				{
					fnd=cnt;
				}
			}
			
			if(symTab.get(fnd+2).equalsIgnoreCase("integer"))
			{
				type=0;
				//System.out.println("its integer" + currentvalue);
			}
			else if(symTab.get(fnd+2).equalsIgnoreCase("real"))
			{
				type=1;
				//System.out.println("its real--->" + currentvalue);
			}
			else
			{
				//System.out.println("enter here");
				type=2;
			}
			
			//System.out.println("FOUND--->" + symTab.get(fnd+3));
			int address=Integer.parseInt(symTab.get(fnd+3));
			//System.out.println("ADDRESS--->" + address);
			//System.out.println("pushed variable address");
			gen4(address);
			getnextoken(tokenpntr);
			//System.out.println("Current Value --->" + currentvalue);
			
			// for assignments
			if(currenttoken.equalsIgnoreCase("TK_ASSIGN"))
			{
				getnextoken(tokenpntr);
				while(currenttoken!="TK_SEMICOLON" && currenttoken!="TK_DO")
				{
				////System.out.println("entering to assign with"+ currenttoken);
				if(currenttoken.equalsIgnoreCase("TK_INTEGER"))
				{
				E(tokenpntr,symTab);
				}
				else if(currenttoken.equalsIgnoreCase("TK_REAL"))
				{
					E(tokenpntr,symTab);
				}
				else if(currenttoken.equalsIgnoreCase("TK_STRING"))
				{
					E(tokenpntr,symTab);
				}
				else if(currenttoken.equalsIgnoreCase("TK_VAR"))
				{
					E(tokenpntr,symTab);
				}
				else if(currenttoken.equalsIgnoreCase("TK_TO"))
				{
					//getnextoken(tokenpntr);
					i--;
					break;
				}
				
				}
			    gen1(op.POP);
			    if(currenttoken.equalsIgnoreCase("TK_TO"))
			    {
			    	i=i-3;
					getnextoken(tokenpntr);
					////System.out.println("NEED TO CHECK THIS-->"+ currenttoken);
					target_for=codepntr;
					gen1(op.PUSH);
					for(int cnt=0;cnt<symTab.size();cnt++)
					{
						if(symTab.get(cnt).equalsIgnoreCase(currentvalue))
						{
							fnd=cnt;
						}
					}
					//System.out.println("FOUND--->" + symTab.get(fnd+3));
					int address1=Integer.parseInt(symTab.get(fnd+3));
					//System.out.println("ADDRESS--->" + address);
					//System.out.println("pushed variable address");
					gen4(address1);
					i=i+2;

			    }
			    //System.out.println("after parsing variables-->" + currenttoken);
				getnextoken(tokenpntr);
			}
			// for comparison operators equals
			else if(currenttoken.equalsIgnoreCase("TK_EQUAL"))
			{
				//System.out.println("ENTERING EQQQQUALSSSS");
				//getnextoken(tokenpntr);
				L(tokenpntr,symTab);
				
				//getnextoken(tokenpntr);
				if(currenttoken!="TK_SEMICOLON")
				{
					//System.out.println("NOT EQAL TO SEMICOLON");
				}
				
			}
			else if(currenttoken.equalsIgnoreCase("TK_LESSTHAN"))
			{
				//System.out.println("ENTERING LESS THAN");
				//getnextoken(tokenpntr);
				L(tokenpntr,symTab);
				
				//getnextoken(tokenpntr);
				if(currenttoken!="TK_DO")
				{
					//System.out.println("NOT EQAL TO Do");
				}
				
			}
			else if(currenttoken.equalsIgnoreCase("TK_GREATERTHAN"))
			{
				//System.out.println("ENTERING GREATER THAN");
				//getnextoken(tokenpntr);
				L(tokenpntr,symTab);
				
				//getnextoken(tokenpntr);
				if(currenttoken!="TK_DO")
				{
					//System.out.println("NOT EQAL TO Do");
				}
				
			}
			else
			{
				////System.out.println("BREAKING OFF");
				
			}
			
		  }// new if after the first while
		  else
		  {
		  // parsing the array element
			  
		  //System.out.println("its a array assignment");
		  
		 // i=i+6;
		    //System.out.println("Pushed variable in array" + currentvalue);
			gen1(op.PUSH);
			for(int cnt=0;cnt<symTab.size();cnt++)
			{
				if(symTab.get(cnt).equalsIgnoreCase(currentvalue))
				{
					fnd=cnt;
					break;
				}
			}
			
			fnd=fnd-2;
			int lowerbound;
			int index;
			int lominusind;
			//System.out.println("Lower bound value-->" + symTab.get(fnd));
			lowerbound=Integer.parseInt(symTab.get(fnd));
			fnd=fnd+2;
			i++;
			getnextoken(tokenpntr);
			
			index = Integer.parseInt(currentvalue);
			//System.out.println("index value-->" + index);
			lominusind=index-lowerbound;
			//System.out.println("lowere minus index-->" + lominusind);
			if(lominusind<0)
			{
				System.out.println("COMPILER ERROR");
			}
			else
			{
				
			}
			lominusind++;
			//System.out.println("final lominusind-->" + lominusind);
			//System.out.println("fnd value "+ symTab.get(fnd+3));
			//fnd=fnd+(3*lominusind-lowerbound);
			lominusind=(lominusind*4)-4;
			//System.out.println("lower min index-->" + lominusind);
			fnd=fnd+lominusind;
			//System.out.println("at 16 "+ symTab.get(fnd-1));
			////System.out.println("fnd value "+fnd);
			
			
			//System.out.println("enter to address of array");
			//System.out.println(" array FOUND--->" + symTab.get(fnd+3));
			int address=Integer.parseInt(symTab.get(fnd+3));
			//System.out.println("array ADDRESS--->" + address);
			////System.out.println("pushed variable address");
			gen4(address);
			getnextoken(tokenpntr);
			//System.out.println("after array index--> " + currentvalue);
			
			//i=i++;
			getnextoken(tokenpntr);
			getnextoken(tokenpntr);
			//System.out.println("entering to assign with in array"+ currenttoken);
			while(currenttoken!="TK_SEMICOLON")
			{
				
			//System.out.println("entering  with in array"+ currentvalue);
			if(currenttoken.equalsIgnoreCase("TK_INTEGER"))
			{
				//System.out.println("----------------------------------------------------");
			E(tokenpntr,symTab);
			gen1(op.POP);
			}
			else if(currenttoken.equalsIgnoreCase("TK_VAR"))
			{
				E(tokenpntr,symTab);
				gen1(op.POP);
			}
			else if(currenttoken.equalsIgnoreCase("TK_TO"))
			{
				
				i--;
				break;
			}
			else
			{	
				//System.out.println("does assign anything");
			}
			
			
			}
		    
		    getnextoken(tokenpntr);
			//System.out.println("Current Value --->" + currentvalue);
			
			
		  
		  
		  
		  
		  
		   
		  }
		  
		  
		  
		}
		// Just if u find integer , example in FOR loop
		if(currenttoken.equalsIgnoreCase("TK_INTEGER"))
		{
			F(tokenpntr, symTab);
			i--;
		}
		
		
		
		
	}
	// Condition for repeat .. until
	public void condition(ArrayList<ScannerPointer> tokenpntr)
	{
		// a=2 
		semiclnerror=0;
		getnextoken(tokenpntr);
		//System.out.println("Curret tokken after UNTIL--->" + currenttoken);
		while(currenttoken!="TK_SEMICOLON")
		{
			variable(tokenpntr);
		  //System.out.println("curenttokkk-->" + currenttoken);
		  if(currenttoken!="TK_SEMICOLON")
		  {
			  break;
		  }
		 // getnextoken(tokenpntr);
		  
		}
		//getnextoken(tokenpntr);
		/////////
		//System.out.println("after currenttookk-->" + currenttoken);
		if(currenttoken!="TK_SEMICOLON")
		  {
			  System.out.println("COMPILER ERROR MISSING SEMICOLON");
			 semiclnerror++;
		  }
		  else
		  {
			//System.out.println("CONTINUE");  
			i--;
		  }
		
	}
	
	public void writeln_st(ArrayList<ScannerPointer> tokenpntr)
	{
		if(currenttoken.equalsIgnoreCase("TK_WRITELN"))
		{
			//System.out.println("entering writeln");
			leftmiss=0;
			getnextoken(tokenpntr);   // left parenthesis
		  if(currenttoken.equalsIgnoreCase("TK_LEFTPRTH"))
		  {  
			
			 
			while(currenttoken!="TK_RIGHTPRTH")
			{
				//System.out.println("inside writeln..." + currenttoken);
				getnextoken(tokenpntr);
				if(currenttoken.equalsIgnoreCase("TK_VAR"))
				{
				variable(tokenpntr);
				}
				else
				{
					
				}
				// string , comma , var
				
				
				
				
			}
			getnextoken(tokenpntr);
			//System.out.println("after right )" + currenttoken); // SEMICOLON
			
			gen1(op.WRITE);
			
		  
		  	}
			else
			{
				  System.out.println("COMPILER ERROR-MISSING LEFT PAR");
				  leftmiss++;
			}
		  			
		}
		
	}
	
	
	// Condition for a while....do
		public void condition_for_while(ArrayList<ScannerPointer> tokenpntr)
		{
			while(currenttoken!="TK_DO")
			{
				//System.out.println("inside DO-->" + currenttoken);
				variable(tokenpntr);
				getnextoken(tokenpntr);
				//System.out.println("shld be DO--->" + currenttoken);
			}
			
			//System.out.println("Outside Do-->" + currenttoken);
			getnextoken(tokenpntr);
		}
	
	
	// while .... do
	public void while_st(ArrayList<ScannerPointer> tokenpntr)
	{
		int targwhile;
		int whilesemi=0;
		int hole=0;
		int save_ip;
		int[] whlarry=new int[4];
		//System.out.println("Current token shld be while-->" + currenttoken);
		
	while(currenttoken.equalsIgnoreCase("TK_WHILE"))
	{
		//System.out.println("ENTERING WHILE----");
		while(currenttoken!="TK_SEMICOLON")
		{
			targwhile=codepntr;
			// condition
			condition_for_while(tokenpntr);
			
			
			gen1(op.JFlase);
			hole=codepntr;
			//System.out.println("HOLE IN WHILE-->" + hole + " " + Code.get(hole-1));
			whlarry[0]=codepntr;
			whlarry[1]=codepntr+1;
			whlarry[2]=codepntr+2;                
			whlarry[3]=codepntr+3;
			gen4(0);
			//System.out.println("codepntr at -->" + Code.get(whlarry[3]));
			
			
			//System.out.println("shld be do-->" + currenttoken);
			
			while(currenttoken.equalsIgnoreCase("TK_BEGIN"))
			{
				
				getnextoken(tokenpntr);
				while(currenttoken!="TK_SEMICOLON")
				{
					
				 while(currenttoken!="TK_END")
				 {
					 
					 //System.out.println("Current token after while" + currentvalue);
					 while(currenttoken!="TK_SEMICOLON")
					 {
						// //System.out.println("Current token after while" + currentvalue);
						 if(currenttoken.equalsIgnoreCase("TK_WRITELN") && check_for_diff_write(tokenpntr))
						 {
							 //System.out.println("Entering write");
						 writeln_st(tokenpntr);
						 }
						 else if(currenttoken.equalsIgnoreCase("TK_WRITELN"))
						 {
							 write_st_with_var(tokenpntr);
							 i=i-2;
							 getnextoken(tokenpntr);
							 //System.out.println("Current token after while-->" + currentvalue);
						 }
						 else if(currenttoken.equalsIgnoreCase("TK_VAR"))
						 {
						  variable(tokenpntr);
						  i=i-2;	
						  getnextoken(tokenpntr);
						  //System.out.println("After while variable-->" + currenttoken);
						 }
						 
						 ////System.out.println("expected semicolon--" + currenttoken);
						 
					 }
					 
					 //add Jump to the condition again
					 
					 getnextoken(tokenpntr);
					 //System.out.println("expected is end--" + currenttoken);
				 
				 
				 }
				 gen1(op.JUMP);
				 gen4(targwhile);
				 save_ip=codepntr;
				 codepntr=hole;
				 //System.out.println("value of save_ip--->"+ save_ip + "  " + Code.get(save_ip-1));
				 
				 update(save_ip,whlarry);
				 
				 codepntr=save_ip;
				 
				 getnextoken(tokenpntr);
				 //System.out.println("expected is semi--" + currenttoken);
					
				}
				
				
				
				
				
				
			}
			
			
			
		}	
	
		getnextoken(tokenpntr);
	}
		
	}
	
	
	// repeat .... Until 
	public void repeat_st(ArrayList<ScannerPointer> tokenpntr)
	{
		int endcheck=0;
		while(currenttoken.equalsIgnoreCase("TK_REPEAT"))
		{
			
		while(currenttoken!="TK_UNTIL")
		{
		//System.out.println("***********ENTERING UNTIL***");
		 endcheck=0;	
		getnextoken(tokenpntr);	
		target=codepntr;
		////System.out.println("CODE ARRAY POINTER IS--->" + target + " "+ Code.get(target-1));
		////System.out.println("TOKKKKKKEEE target---->"+ currenttoken);
		if(currenttoken.equalsIgnoreCase("TK_VAR"))
		{
		variable(tokenpntr);
		}
		
		
		
			//System.out.println("entering writeln in repeat");
		if(currenttoken.equalsIgnoreCase("TK_WRITELN") && check_for_diff_write(tokenpntr))
		{
		//	//System.out.println("Write statement-->" + currentvalue);
			writeln_st(tokenpntr);
			getnextoken(tokenpntr);
		//	//System.out.println("after write st-->" + currentvalue);
		}
		else if(currenttoken.equalsIgnoreCase("TK_WRITELN"))
		{
		//	//System.out.println("Entering with variable" + currentvalue);
			write_st_with_var(tokenpntr);
			//System.out.println("after write a"+ currentvalue);
		}
		
		}
		
		//System.out.println("shld be UNtil---->" + currenttoken);
		if(currenttoken!="TK_UNTIL")
		 {
			 System.out.println("Compiler Error -> UNTIL IS MISSING");
			 endcheck++;
			 break;
		 }
		 // break if no until is found before END
		 if(endcheck>0)
		 {
			 System.out.println("Compiler Error -> UNTIL IS MISSING");
			 break;
		 }
		
		// ADD FURTHER CONDITIONS TO WHAT EVER CAN OCCUR HE
		while(currenttoken!="TK_SEMICOLON")
		{
		semi=0;
		condition(tokenpntr);
		// Compiler error if END Occurs before Until
		// again add to skip the condition stuff.
		// i + tr
		
		if(semiclnerror>0)
		{
			semi++;
			break;
		}
		else
		{
			semi=0;
		}
		//getnextoken(tokenpntr);
		getnextoken(tokenpntr);
		}	
		gen1(op.JFlase);
		//System.out.println("TARGETTTTTTT--->" + target + " " + Code.get(target));
		gen4(target);
		
		if(semi>0)
		{
			break;
		}
		else
		{
			
		}
		}
		
		
		getnextoken(tokenpntr);
		
		//System.out.println("inside repeat.. until--->" + currenttoken);
		
	}
	public void condition_if(ArrayList<ScannerPointer> tokenpntr)
	{
		while(currenttoken!="TK_RIGHTPRTH")
		{
			variable(tokenpntr);
			//System.out.println("after variable if cond-->" + currenttoken);
			getnextoken(tokenpntr);
		}
		//System.out.println("after variable if cond-->" + currenttoken);
	}
	
	
	
	public void ifthen_else(ArrayList<ScannerPointer> tokenpntr)
	{
		int[] ifarry=new int[4];
		int[] ifarry2=new int[4];
		int hole=0;
		int save_ip=0;
		//System.out.println("Entering the if block");
		while(currenttoken.equalsIgnoreCase("TK_IF"))
		{
			getnextoken(tokenpntr);
			if(currenttoken.equalsIgnoreCase("TK_LEFTPRTH"))
			{
				//System.out.println("Entering the Leftparth");
				getnextoken(tokenpntr);
				while(currenttoken!="TK_THEN")
				{
					
					//System.out.println("inside if condition-->" + currenttoken);
					condition_if(tokenpntr);
										
					getnextoken(tokenpntr);
					gen1(op.JFlase);
					hole=codepntr;
					//System.out.println("HOLE IN IF COND-->" + hole + " " + Code.get(hole-1));
					ifarry[0]=codepntr;
					ifarry[1]=codepntr+1;
					ifarry[2]=codepntr+2;                
					ifarry[3]=codepntr+3;
					gen4(0);
				
				}
				
				getnextoken(tokenpntr);
				while(currenttoken!="TK_ELSE")
				{
				if(currenttoken.equalsIgnoreCase("TK_WRITELN") && check_for_diff_write(tokenpntr))
				{
					//System.out.println("Inside write ln in If cond");
					writeln_st(tokenpntr);
					//write_str(tokenpntr);
					//write_st_with_var(tokenpntr);
					gen1(op.JUMP);
					ifarry2[0]=codepntr;
					ifarry2[1]=codepntr+1;
					ifarry2[2]=codepntr+2;                
					ifarry2[3]=codepntr+3;
					gen4(0);
					
					//System.out.println("after write in if " + currentvalue);

				}
				else if(currenttoken.equalsIgnoreCase("TK_WRITELN"))
				{
					//System.out.println("Inside write ln in If cond");
					//writeln_st(tokenpntr);
					//write_str(tokenpntr);
					write_st_with_var(tokenpntr);
					gen1(op.JUMP);
					ifarry2[0]=codepntr;
					ifarry2[1]=codepntr+1;
					ifarry2[2]=codepntr+2;                
					ifarry2[3]=codepntr+3;
					gen4(0);
					i--;
					//System.out.println("after write in if " + currentvalue);

				}
				
				else if(currenttoken.equalsIgnoreCase("TK_VAR"))
				{
					
				}
				update(codepntr, ifarry);
				getnextoken(tokenpntr);
				}
				
				if(currenttoken.equalsIgnoreCase("TK_ELSE"))
				{
					getnextoken(tokenpntr);
					//writeln_st(tokenpntr);
					//System.out.println("after else write-->" + currentvalue);
					
					if(currenttoken.equalsIgnoreCase("TK_WRITELN") && check_for_diff_write(tokenpntr))
					{
					
						write_str(tokenpntr);
						//write_st_with_var(tokenpntr);
						//getnextoken(tokenpntr);
					
					}
					else
					{
						write_st_with_var(tokenpntr);
					}
					
					
				}
				update(codepntr, ifarry2);	
				
			}
			else
			{
				System.out.println("Compiler error - Left parenthesis missing");
				break;
			}
			
		}
		
		
	}
	
	public void condition_for(ArrayList<ScannerPointer> tokenpntr)
	{
		
		//System.out.println("Entering for condition-->" + currenttoken);
		while(currenttoken!="TK_DO")
		{
			
			while(currenttoken!="TK_TO")
			{
				//System.out.println("inside To-->" + currenttoken + currentvalue);
				//getnextoken(tokenpntr);	
				
				//System.out.println("at target inside for-->" + Code.get(target-1));
				variable(tokenpntr);
				//System.out.println("after variable");
			}
			
			
			
			getnextoken(tokenpntr);
			//System.out.println("Has to be variable-->" + currenttoken);
		    
			
			variable(tokenpntr);
			
			gen1(op.LessTEqual);
			gen1(op.JFlase);
			forarry2[0]=codepntr;
			forarry2[1]=codepntr+1;
			forarry2[2]=codepntr+2;                
			forarry2[3]=codepntr+3;
			gen4(0);
			getnextoken(tokenpntr);
		}
		//System.out.println("After for condition-->" + currenttoken);
	}
	
	public void for_loop(ArrayList<ScannerPointer> tokenpntr)
	{   
		//counter_for=0;
		while(currenttoken.equalsIgnoreCase("TK_FOR"))
		{
			//System.out.println("inside FOR-->" + currenttoken);
			getnextoken(tokenpntr);
			while(currenttoken!="TK_SEMICOLON")
			{
				//getnextoken(tokenpntr);
				
					condition_for(tokenpntr);
					getnextoken(tokenpntr);
				
				//getnextoken(tokenpntr);
				if(currenttoken.equalsIgnoreCase("TK_BEGIN"))
				{
					getnextoken(tokenpntr);
					while(currenttoken!="TK_END")
					{
						
						//System.out.println("inside FOR before writeln-->" + currenttoken);
						if(currenttoken.equalsIgnoreCase("TK_WRITELN") && check_for_diff_write(tokenpntr))
						{
							//System.out.println("entering --->");
							writeln_st(tokenpntr);
							//write_st_with_var(tokenpntr);
							//System.out.println("after writeln-->" + currentvalue);
														
						}
						else
						{
							//System.out.println("entering with write with var");
							write_st_with_var(tokenpntr);
							i=i-1;
							//System.out.println("after with write with var" + currentvalue);
						}
						
						getnextoken(tokenpntr);
						
					}
					for_hole=i;
					while(currenttoken!="TK_FOR")
					{
						getprevtoken(tokenpntr);
					}
					i=i+2;
					getnextoken(tokenpntr);
					gen1(op.PUSH);
					//System.out.println("current value in for--" + currentvalue);
					
					for(int cnt=0;cnt<symTab.size();cnt++)
					{
						if(symTab.get(cnt).equalsIgnoreCase(currentvalue))
						{
							fnd=cnt;
						}
					}
					
					
					
					
					
					//System.out.println("FOUND for--->" + symTab.get(fnd+3));
					int address1=Integer.parseInt(symTab.get(fnd+3));
					//System.out.println("ADDRESS--->" + address1);
					//System.out.println("pushed variable address");
					gen4(address1);
					getnextoken(tokenpntr);
					////System.out.println("NEED TO CHECK THIS-->"+ currenttoken);
					gen1(op.PUSH);
					gen4(address1);
					
					gen1(op.PUSHI);
					gen4(1);
					gen1(op.ADD);
					gen1(op.POP);
					gen1(op.JUMP);
					gen4(target_for);
					i=for_hole;
					update(codepntr, forarry2);
					getnextoken(tokenpntr);
				}
				else
				{
					break;
				}
			}
			//getnextoken(tokenpntr);
			
			
		}
		
		
	}
	
	void getnextoken(ArrayList<ScannerPointer> token)
	{
		currenttoken=token.get(i).getType();
		currentvalue=token.get(i).getValue();
		numericval=token.get(i).getNumericvalue();
		realval=token.get(i).getRealvalue();
		i++;
		
	}
	
	void getprevtoken(ArrayList<ScannerPointer> token)
	{
		currenttoken=token.get(i).getType();
		currentvalue=token.get(i).getValue();
		numericval=token.get(i).getNumericvalue();
		i--;
		
	}
	
	// To go ahead and see if the variable is an array
	boolean Check_array(ArrayList<ScannerPointer> token)
	{
		//System.out.println("entering chk array-->" + currentvalue);
		i=i+1;
		currenttoken=token.get(i).getType();
		currentvalue=token.get(i).getValue();
		numericval=token.get(i).getNumericvalue();
		//System.out.println("inside Check_array--->" + currentvalue);
		if(currenttoken.equalsIgnoreCase("TK_ARRAY"))
		{
			
		    i=i-2;
		    getnextoken(token);
			return false;
		}
		else
		{
		i=i-2;
		getnextoken(token);
		return true;
		}
	}
	
	// to differentiate between variable and array during assignment
	
	boolean check_if_arrayvar(ArrayList<ScannerPointer> token)
	{
		getnextoken(token);
		if(currenttoken.equalsIgnoreCase("TK_OpenBracket"))
		{
			i=i-2;
			getnextoken(token);
			return false;
		}
		else
		{
			i=i-2;
			getnextoken(token);
			return true;	
		}
		
		
	}
	
	boolean check_for_string(ArrayList<ScannerPointer> token)
	{
		
		i=i+1;
		getnextoken(token);
		
		if(currenttoken.equalsIgnoreCase("TK_STRING"))
		{
			i=i-3;
			getnextoken(token);
			return true;
		}
		else
		{
			i=i-3;
			getnextoken(token);
			return false;	
		}
		
	}
	
	boolean check_for_diff_write(ArrayList<ScannerPointer> token)
	{
		i=i+2;
		getnextoken(token);
		
	//	//System.out.println("to check difference in write-->" + currentvalue + " " + currenttoken);
		if(currenttoken.equalsIgnoreCase("TK_COMMA"))
		{
			i=i-4;
			getnextoken(token);
			////System.out.println("after difference-->" + currentvalue);
			return false;
		}
		else
		{
			i=i-4;
			getnextoken(token);
			return true;	
		}
		
		
	}
	
	public void case_statement(ArrayList<ScannerPointer> tokenpntr)
	{
		byte[] dup=new byte[4];
		ArrayList<Integer> trkhole = new ArrayList<Integer>();
		int[] trackhole = new int[4];
		int trk=0;
		int first=0;
		
		while(currenttoken!="TK_END")
		{
			if(currenttoken.equalsIgnoreCase("TK_CASE"))
			{
				getnextoken(tokenpntr);
			while(currenttoken!="TK_OF")
			{
				
				if(currenttoken.equalsIgnoreCase("TK_LEFTPRTH"))
				{
					
					getnextoken(tokenpntr);
					
					if(currenttoken.equalsIgnoreCase("TK_VAR"))
					{
						
						variable(tokenpntr);
						////System.out.println("TO DUPLICATE" + Code.get(codepntr-1));
						codepntr=codepntr-4;
						dup[0]=Code.get(codepntr);
						dup[1]=Code.get(codepntr+1);
						dup[2]=Code.get(codepntr+2);
						dup[3]=Code.get(codepntr+3);
						codepntr=codepntr+4;
						first++;
						//System.out.println("after case-->" + currentvalue);
						
						
					}
					
					
				}
								
				
				
				//System.out.println("inside-->" + currentvalue);
				getnextoken(tokenpntr);
				//System.out.println("of inside-->" + currentvalue);
				
			}
			  getnextoken(tokenpntr);
			}
			
			//System.out.println("inside case-->" + currentvalue);
			
			while(currenttoken!="TK_SEMICOLON")
			{
				
				if(currenttoken.equalsIgnoreCase("TK_INTEGER"))
				{
					if(first==1)
					{
					//System.out.println("variabless list---" + currentvalue + " " + first);
					variable(tokenpntr);
					gen1(op.EQUAL);
					gen1(op.JFlase);
					trackhole[0]=codepntr;
					trackhole[1]=codepntr+1;
					trackhole[2]=codepntr+2;
					trackhole[3]=codepntr+3;
					gen4(0);
					first++;
					}
					else
					{
						update(codepntr, trackhole);
						/*
						add dup
						*/
						gen1(op.PUSH);
						Code.add(dup[0]);
						Code.add(dup[1]);
						Code.add(dup[2]);
						Code.add(dup[3]);
						codepntr=codepntr+4;
						
						//System.out.println("variabless list---" + currentvalue);
						variable(tokenpntr);
						//System.out.println("pushin equals");
						gen1(op.EQUAL);
						gen1(op.JFlase);
						trackhole[0]=codepntr;
						trackhole[1]=codepntr+1;
						trackhole[2]=codepntr+2;
						trackhole[3]=codepntr+3;
						gen4(0);	
					}
				}
				if(currenttoken.equalsIgnoreCase("TK_WRITELN"))
				{
					
					
					write_str(tokenpntr);
					gen1(op.JUMP);
					
					trkhole.add(codepntr);
					trkhole.add(codepntr+1);
					trkhole.add(codepntr+2);
					trkhole.add(codepntr+3);
					gen4(0);
					
					//System.out.println("after write in case-->" + currentvalue);
					
					if(currenttoken.equalsIgnoreCase("TK_END"))
					{
						i=i-2;
						getnextoken(tokenpntr);
						//System.out.println("altering end-->" + currenttoken);
					}
					
					
					
					//	write_str(tokenpntr);
					
					
				}
				
				////System.out.println("after each" + currentvalue);
				if(currenttoken!="TK_INTEGER" && currenttoken!="TK_SEMICOLON")
				{
				getnextoken(tokenpntr);
				}
				else
				{
					
				}
				
			}
			
			
			
			//System.out.println("after each case-->" + currentvalue);
			getnextoken(tokenpntr);
			//System.out.println("now-->" + currentvalue);
			if(currenttoken.equalsIgnoreCase("TK_END"))
			{
				//System.out.println("entering to remove");
				
				Code.remove(Code.size()-1);
				Code.remove(Code.size()-1);
				Code.remove(Code.size()-1);
				Code.remove(Code.size()-1);
				Code.remove(Code.size()-1);
				
				codepntr=codepntr-5;
			}
						
		}
		
		/*
		update all holes after statement for each case
		*/
		//System.out.println("code element at the end-->" + Code.get(codepntr-1));
		//System.out.println("updating holes now" + currentvalue);
		update_Case_holes(codepntr, trkhole);
		
		
		getnextoken(tokenpntr);
		//System.out.println("after case-->" + currentvalue);
	}
	
	void stackelements(ArrayList<Byte> StackEle)
	{
		for(int i=0;i<StackEle.size();i++)
		{
			//System.out.println("Code array element--->" + StackEle.get(i));
		}
	}
	void parsing(ArrayList<ScannerPointer> tokenpointer)
	{
		
		
		
		getnextoken(tokenpointer);
		if(currenttoken.equalsIgnoreCase("TK_PROGRAM"))
		{
			getnextoken(tokenpointer);
			if(currenttoken.equalsIgnoreCase("TK_VAR"))
			{	
				getnextoken(tokenpointer);
				if(currenttoken.equalsIgnoreCase("TK_SEMICOLON"))
				{
					getnextoken(tokenpointer);
					// if declaration var is found
			        if(currenttoken.equalsIgnoreCase("TK_DECLARATION"))
			        {	
			        	getnextoken(tokenpointer);
			        	int sympntr=0;
			        	int tempcntr;
			        	
			        	// loop through and add variable into symbol table
			        	while(currenttoken.equalsIgnoreCase("TK_VAR"))
			        	{
			        		//System.out.println("current variable-->" + currentvalue);
			        		if(currenttoken.equalsIgnoreCase("TK_VAR") && Check_array(tokenpointer))
			        		{
			        		// entering variable into symbol table for first time
			        		if(sympntr==0)
			        		{
			        		symTab.add(currentvalue);
			        		symTab.add(currenttoken);
			        		//symTab.add(String.valueOf(symTab.get(sympntr).hashCode()));
			        		////System.out.println("hash code--->" + symTab.get(sympntr+2));
			        		sympntr=sympntr+2;
			        		
			        		}//entering value into symTab after first time
			        		
			        		else if(sympntr>0)
			        		{   
			        			// assigning to temp variable used for checking duplicates occuring early in list
			        			tempcntr=sympntr;
			        			//if variable are not distinct- Compiler error
			        			int check=0;
			        			// to check if any duplicate is present example -- 0-4-8...i do check++
			        			while(tempcntr!=0)
			        			{
			        				tempcntr=tempcntr-4;
			        				
			        			if(symTab.get(tempcntr).equalsIgnoreCase(currentvalue))
			        			{
			        				// check increments if any duplicate is found
			        				System.out.println("COMPILER ERROR-Variable names should be different");
			        				check++;
			        				break;
			        			}
			        			else
			        			{
			        				//continue;
			        			}
			        			
			        			
			        			}
			        			//if check is greater than zero , break out bec duplicate found
			        			if(check>0)
			        			{
			        				System.out.println("COMPILER ERROR-->SAME VAR NAMES");
			        				break;
			        			}
			        			// if no duplicates then continue
			        			else if(tempcntr==0)
			        			{
			        				
			        				symTab.add(currentvalue);
					        		symTab.add(currenttoken);
					        		//symTab.add(String.valueOf(symTab.get(sympntr).hashCode()));
					           		sympntr=sympntr+2;
					           		
			        			}
			        			
			        			
			        		}
			        		
			        		//System.out.println("VARIABLE NAME-->" + currentvalue);
			        		    getnextoken(tokenpointer);
			        		   // if variable occur like i,k,j : integer.Where , is TK_SPECIAL
			        		    
			        		    if(currenttoken.equalsIgnoreCase("TK_COMMA"))
			        		    {
			        		    	getnextoken(tokenpointer);
			        		    	sympntr=sympntr+2;
			        		    	// store a hole at the place where type will be filled later.
			        		    	// you dont increment data pntr here bec type is unknown
			        		    	symTab.add("hole");
			        		    	symTab.add("ad");
			        		    	//System.out.println("hole");
			        		    	//System.out.println("address");
			        		    	//System.out.println("SYMPTR LAST->" + sympntr);
			        		    	////System.out.println("toke is-->" + currenttoken);
			        		    	//Increment dat pointer based on size
			        		    	
			        		    	//System.out.println("************************");
			        		    	
			        		    }
			        		    else if(currenttoken.equalsIgnoreCase("TK_COLON"))
			        		    {
			        		    	getnextoken(tokenpointer);
			        		    	// now for i,j,k:integer when u reach at integer , replace all holes
			        		    	// created for i,j with integer value.
			        		    	for(int g=0;g<symTab.size();g++)
			        		    	{
			        		    		while("hole".equalsIgnoreCase(symTab.get(g)))
			        		    		{
			        		    			symTab.remove(g);
			        		    			
			        		    			symTab.add(g, currentvalue);
			        		    			
			        		    			//System.out.println(symTab.get(g));
			        		    			// increment data pnt based on the type
			        		    			if(currenttoken.equalsIgnoreCase("TK_INT") || currenttoken.equalsIgnoreCase("TK_REAL")
			        		    					 || currenttoken.equalsIgnoreCase("TK_CHAR"))
					        		    	{
			        		    				
					        		    		dp=dp+4;
					        		    		symTab.remove(g+1);		
					        		    		symTab.add(g+1,String.valueOf(dp));
					        		    		////System.out.println("---->" + symTab.get(g+1));
					        		    		//System.out.println("Data pointer value is----->"+dp);
					        		    		//dptemp=0;
					        		    		dptemp=dp;
					        		    		while(dptemp!=dp-4)
					        		    		{
					        		    			Data.add((byte)dptemp);
					        		    			dptemp--;	
					        		    		}
					        		    		
					        		    	}
			        		    			
			        		    			break;
			        		    		}
			        		    	}
			        		    	symTab.add(currentvalue);
			        		    	
			        		    	//sympntr=sympntr+2;
			        		    	//System.out.println("TYPE-->" + currentvalue);
			        		    	//System.out.println("SYMPTR LAST->" + sympntr);
			        		    	//increment data pointer based on type.
			        		    	if(currenttoken.equalsIgnoreCase("TK_INT") || currenttoken.equalsIgnoreCase("TK_REAL")
			        		    			 || currenttoken.equalsIgnoreCase("TK_CHAR"))
			        		    	{
			        		    		dp=dp+4;
			        		    		symTab.add(String.valueOf(dp));
			        		    		//System.out.println("Data pointer value is-->"+dp);
			        		    		//dptemp=0;
			        		    		dptemp=dp;
			        		    		while(dptemp!=dp-4)
			        		    		{
			        		    			Data.add((byte)dptemp);
			        		    			dptemp--;
			        		    		}
			        		    	}
			        		    	 
			        		    	
			        		    	sympntr=sympntr+2;
			        		    	//System.out.println("**********************");
			        		    	
			        		    	getnextoken(tokenpointer);
			        		    	if(currenttoken.equalsIgnoreCase("TK_SEMICOLON"))
			        		    	{
			        		    		getnextoken(tokenpointer);
			        		    	}
			        		    	else
			        		    	{
			        		    		System.out.println("Compiler error : semicolon required at the end");
			        		    	}
			        		    }
			        		    
			        		    
			        	   }// if token is TK var  
			        		else
			        		{
			        			//System.out.println("printf array " + currentvalue);
			        			//i=i+10;
			        			String[] locsymbolTab=new String[5];
			        			int lb=3;
			        			int lc=0;
			        			// to maintain the 4 blocks in symbol table
			        			symTab.add("");
			        			symTab.add("");
			        			while(lc!=11)
			        			{
			        				////System.out.println("data pointer value inside array " + dp);
			        				////System.out.println("array token----->" + currentvalue);
			        				if(currenttoken.equalsIgnoreCase("TK_VAR"))
			        				{
			        					////System.out.println("found var-->"+currentvalue);
			        					locsymbolTab[0]=currentvalue;
			        				}
			        				
			        				if(currenttoken.equalsIgnoreCase("TK_ARRAY"))
			        				{
			        					////System.out.println("found array-->"+ currenttoken);
			        					locsymbolTab[1]=currenttoken;
			        				}
			        				
			        				if(currenttoken.equalsIgnoreCase("TK_INT"))
			        				{
			        					////System.out.println("found type-->"+currentvalue);
			        					locsymbolTab[2]=currentvalue;
			        				}
			        				
			        				if(currenttoken.equalsIgnoreCase("TK_INTEGER"))
			        				{
			        				////System.out.println("integer found");
			        				locsymbolTab[lb]=currentvalue;
			        				lb++;
			        				symTab.add(currentvalue);
			        				}
			        				
			        				getnextoken(tokenpointer);
			        				lc++;
			        			}
			        			
			        			int upperbound=Integer.parseInt(locsymbolTab[4]);
			        			int lowerbound=Integer.parseInt(locsymbolTab[3]);
			        			
			        			
			        			for(lc=0;lc <upperbound-lowerbound ; lc++)
			        			{
			        				////System.out.println("------------->no of arrray element " + lc);
			        				symTab.add(locsymbolTab[0]);
			        				symTab.add(locsymbolTab[1]);
			        				symTab.add(locsymbolTab[2]);
			        				dp=dp+4;
		        		    		symTab.add(String.valueOf(dp));
		        		    		
		        		    		//dptemp=0;
		        		    		dptemp=dp;
		        		    		while(dptemp!=dp-4)
		        		    		{
		        		    			Data.add((byte)dptemp);
		        		    			dptemp--;
		        		    		}
			        			}
			        			
			        			//getnextoken(tokenpointer);
			        			//System.out.println("afterarray " + currenttoken);
			        			getnextoken(tokenpointer);
			        		}
			        		    
			        		    
			        	}
			        	for(int f=0;f<symTab.size();f++)
			        	{
			        		//System.out.println("symtab values--->" + symTab.get(f));
			        	}
			        	
			        	////System.out.println("CURRENT TOKEN AFTER DECLARATION" + currenttoken);
			        	//the next token expected is a BEGIN
			        	if(currenttoken.equalsIgnoreCase("TK_BEGIN"))
			        	{
			        		////System.out.println("ENTERING BEGIN----->");
			        		getnextoken(tokenpointer);
			        		
			        		// If its a variable
			        	  while(currenttoken!="TK_END")
			        	  {
			        		

			        		  // if things are variables  
			        		if(currenttoken.equalsIgnoreCase("TK_VAR"))
			        		{
			        		 // //System.out.println("entering variable-->" + currentvalue);
			        		  variable(tokenpointer);
			        		 // //System.out.println("at the end of variable" + currentvalue);
			        		  //getnextoken(tokenpointer);
			        		}
			        		////System.out.println("after variable declaration-->" + currentvalue);
			        		
			        		if(currenttoken.equalsIgnoreCase("TK_WRITELN") && check_for_diff_write(tokenpointer))
			        		{
			        			
			        			write_str(tokenpointer);
			        			//System.out.println("after write_str function-->" + currentvalue);
			        		}
			        		else
			        		{
			        			
			        			//System.out.println("now the token-->" + currenttoken);
			        			write_st_with_var(tokenpointer);
			        			//System.out.println("after write_str_var-->" + currentvalue);
			        			
			        			
			        		}
			        		
			        		
			        		
			        		if(currenttoken.equalsIgnoreCase("TK_REPEAT"))
			        		{
			        		//System.out.println("ENTERING REPEAT-->" + currentvalue);	
			        		repeat_st(tokenpointer);
			        		//System.out.println("AFTER REPEAT-->" + currenttoken);
			        		}
			        		
			        		
			        		// IF its while
			        		if(currenttoken.equalsIgnoreCase("TK_WHILE"))
			        		{
			        			//System.out.println("inside while loop-->" + currentvalue);
			        		    while_st(tokenpointer);
			        		    //System.out.println("End of while loop-->" + currentvalue);
			        		}
			        		
			        		// if..then..else
			        		if(currenttoken.equalsIgnoreCase("TK_IF"))
			        		{
			        		//System.out.println("Inside If statement" + currenttoken);	
			        		ifthen_else(tokenpointer);
			        		//System.out.println("After if statement" + currenttoken);	
			        		}
			        		
			        		if(currenttoken.equalsIgnoreCase("TK_FOR"))
			        		{
			        			//System.out.println("Entering For loop-->" + currentvalue);
			        			for_loop(tokenpointer);
			        			//System.out.println("After For loop-->" + currentvalue);
			        		}
			        		
			        		if(currenttoken.equalsIgnoreCase("TK_CASE"))
			        		{
			        			
			        			case_statement(tokenpointer);
			        			
			        		}
			        		
			        		
			        	//System.out.println("TOKEN AT LAST--->" + currentvalue);
			        		
			        		if(currenttoken.equalsIgnoreCase("TK_SEMICOLON"))
			        		{
			        			getnextoken(tokenpointer);
			        		}
			        		
			        		if(currenttoken.equalsIgnoreCase("TK_STOP"))
			        		{
			        			//System.out.println("ITs tk stop");
			        			i=i-2;
			        			getnextoken(tokenpointer);
			        		}
			        		
			        		
			        		
			        	  }
			        		
			        		if(currenttoken.equalsIgnoreCase("TK_END"))
				        	{
			        			//System.out.println("entering END");
				        		getnextoken(tokenpointer);
				        		if(currenttoken.equalsIgnoreCase("TK_STOP"))
				        		{
				        			
				        		}
				        		else
				        		{
				        			System.out.println("Compiler Error -Stop expected");
				        		}
				        	}
				        	else
				        	{
				        		System.out.println("Compiler err - Missing END STATEMENT");
				        	}
			        		
			        		
			        		
			        	}
			        	else
			        	{
			        	System.out.println("Compiler error -No begin");
			        	}
			        
			        }
			        // Without a var declaration , Just begin and end block
			        else if(currenttoken.equalsIgnoreCase("TK_BEGIN"))
			        {
			        	while(currenttoken!="TK_STOP")
						{
						E(tokenpointer,symTab);
						}
			        }
			        else
			        {
			        	System.out.println("Error-Variable declaration was expected");
			        	
			        }
				}
				else
				{
					System.out.println("Error-Colon was expected");
				}
		
			}
			else
			{
			System.out.println("Error-Expects a program name");	
			}
			
		}
		else
		{
			System.out.println("PROGRAM KEYWORD IS EXPECTED");
		}
		if(currenttoken.equalsIgnoreCase("TK_STOP"))
				{
				gen1(op.STOP);
				
				}
				else
				{
					System.out.println("Compiler Error");
				}
		
		//System.out.println("--------------------------dp size----"+ Data.size());
		/*
		stackelements(Code);
		*/
		//System.out.println("Code array pointer---->" + codepntr + " " + "actual size-->" + Code.size());
		CodeExecution code=new CodeExecution();
		code.run(Code,Data);
		
	}

	
		 
		
		
	}
	
	
	

