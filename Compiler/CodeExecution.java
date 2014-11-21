

import java.util.ArrayList;

import javax.xml.crypto.Data;
public class CodeExecution {

	Opcodes Opc=new Opcodes();
	// Instruction to be executed , out final stack
	static ArrayList<Byte> InsStack=new ArrayList<Byte>();
	
	static ArrayList<Integer> datapntr=new ArrayList<Integer>();
	int dataptr=0;
	// points to the InsStack.
	static int sp=0;
	// Useed locally in pop() fnction
	static int localdp=0;
	// size of data , to get the actual index to represent in data array.
	static int dpsize=1;
	
	// dp is the actuall index value of Data array. Which hold the data at that place.
	static int dp=0;
	//CodeExecution Codexe=new CodeExecution();
	
	//To convert int into a byte array
	static byte[] toByteArray(int value) {
	    return new byte[] {
	        (byte) (value >> 24),
	        (byte) (value >> 16),
	        (byte) (value >> 8),
	        (byte) value};
	}
	
	// to get back the int from byte array
	static int fromByteArray(byte[] bytes) {
	     return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
	}
	
	public int getAddress(int ipt,ArrayList<Byte> Code)
	{
		byte[] tempbyt=new byte[4];
		tempbyt[0]=Code.get(ipt+1);
		//System.out.println("getaddress-->" + Code.get(ipt+1));
		tempbyt[1]=Code.get(ipt+2);
		//System.out.println("getaddress-->" + Code.get(ipt+2));
		tempbyt[2]=Code.get(ipt+3);
		//System.out.println("getaddress-->" + Code.get(ipt+3));
		tempbyt[3]=Code.get(ipt+4);
		//System.out.println("getaddress-->" + Code.get(ipt+4));
		return tempbyt[0] << 24 | (tempbyt[1] & 0xFF) << 16 | (tempbyt[2] & 0xFF) << 8 | (tempbyt[3] & 0xFF);
	}
	
	//pop int value from Instructionstack when arthmetic op is found.
	static int popInt()
	{
		int val=0;
		byte[] CEE=new byte[4];
		CEE[3]=CodeExecution.InsStack.get(CodeExecution.sp);
		CodeExecution.InsStack.remove(CodeExecution.sp);
		CEE[2]=CodeExecution.InsStack.get(CodeExecution.sp-1);
		CodeExecution.InsStack.remove(CodeExecution.sp-1);
		CEE[1]=CodeExecution.InsStack.get(CodeExecution.sp-2);
		CodeExecution.InsStack.remove(CodeExecution.sp-2);
		CEE[0]=CodeExecution.InsStack.get(CodeExecution.sp-3);
		CodeExecution.InsStack.remove(CodeExecution.sp-3);
		val=CodeExecution.fromByteArray(CEE);
		CodeExecution.sp=CodeExecution.sp-4;
		return val;
	}
	
	//push the value into the Instruction stack.
    static void push(int value)
	{
		byte[] CE=CodeExecution.toByteArray(value);
		
		CodeExecution.InsStack.add(CE[0]);
		CodeExecution.InsStack.add(CE[1]);
		CodeExecution.InsStack.add(CE[2]);
		CodeExecution.InsStack.add(CE[3]);
		
		if(sp==0)
		{
			CodeExecution.sp=CodeExecution.sp+3;	
		}
		else
		{
		CodeExecution.sp=CodeExecution.sp+4;	
		}
		//sp++;
		//System.out.println("Sp after pushi-->" + CodeExecution.sp);
	}
	
    public void pop(ArrayList<Byte> LocalData)
    {
    	//System.out.println("SP at pop() Instr-->" + sp);
    	/* pop last 4 elements which is an integer.
    	Only concerned about the top of the stack which has address.
    	 dp or dataptr-1 points to the end index of the address
    	 assigned dp to localdp to grab the address and put the values in it
    	 datapntr.get(dataptr-1); --> grabs the address added into datapntr list during push
    	 
    	 datapntr.get(dataptr-1); It gives 1-char , 4 - int,real etc.
    	*/
    	if(dataptr==1)
    	{
    		
    	  //localdp=datapntr.get(dataptr-1);	
    		//System.out.println("Entering here when zero------->" + (dataptr-1));
    		localdp=datapntr.get(dataptr-1);
    	}
    	else
    	{
    		//localdp=datapntr.get(dataptr-1);
    		//System.out.println("Entering-------->" + localdp);
    		localdp=datapntr.get(dataptr-1);
    	}
    	//localdp=dp;
    	localdp=localdp-4;
    	//System.out.println("address last index-->" + localdp + " actall dp-->" + dp);
    	//System.out.println("STACK PNTR--->" + sp );
    	sp=sp-3;
    	while(localdp!=datapntr.get(dataptr-1))
    	{
    		//System.out.println("shld not enter" );
    		//System.out.println("data at the adddress-->" + LocalData.get(43) + " "+ localdp);
    		LocalData.remove(localdp);
    		LocalData.add(localdp,InsStack.get(sp));
    		//System.out.println("after adddress-->" + LocalData.get(localdp));
    		sp++;
    		localdp++;
    	}
    	// here sp is one more , so reduce it back because in above while loop
    	// its the size ->datapntr.get(dataptr-1) is 4
    	// spgoes till 4 and breaks out when its 4
    	sp--;
    	//datapntr.remove(datapntr.size()-1);
    	// pop all the elements before the pop()
    	// remove all elements in stack until its zero
    	while(sp!=0)
    	{
    		InsStack.remove(sp);
    		sp--;
    	}
    	// remove last element at zero.
    	InsStack.remove(sp);
    	
    	//System.out.println("SP after pop--> " + sp);
    	
    	
    }
    
    public int pop_at(ArrayList<Byte> LocalData)
    {
    	
    	int val4=0;
    	localdp=0;
		byte[] popat=new byte[4];
		localdp=datapntr.get(dataptr-1);
		//System.out.println("-->" + localdp);
		int trk=0;
		localdp=localdp-4;
		sp=sp-3;
		
		while(localdp!=datapntr.get(dataptr-1))
    	{
    		//System.out.println("data at the adddress-->" + LocalData.get(localdp));
    		popat[trk]= LocalData.get(localdp);
    		
    		//System.out.println("at track-->" + popat[trk] + " " + localdp);
    		trk++;
			//System.out.println("after adddress-->" + LocalData.get(localdp));
    		sp++;
    		localdp++;
    	}
		
		sp--;
    	//datapntr.remove(datapntr.size()-1);
    	// pop all the elements before the pop()
    	// remove all elements in stack until its zero
    	while(sp!=0)
    	{
    		InsStack.remove(sp);
    		sp--;
    	}
    	// remove last element at zero.
    	InsStack.remove(sp);
		val4= CodeExecution.fromByteArray(popat);
		return val4;
    	
    	
    	
    	
    	
    	
    }
    
    public float pop_at_real(ArrayList<Byte> LocalData)
    {
    	
    	float val5=0.0f;
    	int val4=0;
    	localdp=0;
		byte[] popat=new byte[4];
		localdp=datapntr.get(dataptr-1);
		//System.out.println("-->" + localdp);
		int trk=0;
		localdp=localdp-4;
		sp=sp-3;
		
		while(localdp!=datapntr.get(dataptr-1))
    	{
    		//System.out.println("data at the adddress-->" + LocalData.get(localdp));
    		popat[trk]= LocalData.get(localdp);
    		
    		//System.out.println("at track-->" + popat[trk] + " " + localdp);
    		trk++;
			//System.out.println("after adddress-->" + LocalData.get(localdp));
    		sp++;
    		localdp++;
    	}
		
		sp--;
    	//datapntr.remove(datapntr.size()-1);
    	// pop all the elements before the pop()
    	// remove all elements in stack until its zero
    	while(sp!=0)
    	{
    		InsStack.remove(sp);
    		sp--;
    	}
    	// remove last element at zero.
    	InsStack.remove(sp);
		val4= CodeExecution.fromByteArray(popat);
		val5=Float.intBitsToFloat(val4);
		
		return val5;
    	
    	
    	
    	
    	
    	
    }
    
    
    public char pop_at_char(ArrayList<Byte> LocalData)
    {
    	
    	char val5;
    	int val4=0;
    	localdp=0;
		byte[] popat=new byte[4];
		localdp=datapntr.get(dataptr-1);
		//System.out.println("-->" + localdp);
		int trk=0;
		localdp=localdp-4;
		sp=sp-3;
		
		while(localdp!=datapntr.get(dataptr-1))
    	{
    		//System.out.println("data at the adddress-->" + LocalData.get(localdp));
    		popat[trk]= LocalData.get(localdp);
    		
    		//System.out.println("at track-->" + popat[trk] + " " + localdp);
    		trk++;
			//System.out.println("after adddress-->" + LocalData.get(localdp));
    		sp++;
    		localdp++;
    	}
		
		sp--;
    	//datapntr.remove(datapntr.size()-1);
    	// pop all the elements before the pop()
    	// remove all elements in stack until its zero
    	while(sp!=0)
    	{
    		InsStack.remove(sp);
    		sp--;
    	}
    	// remove last element at zero.
    	InsStack.remove(sp);
		val4= CodeExecution.fromByteArray(popat);
		val5=(char)val4;
		
		return val5;
    	
    	
    	
    	
    	
    	
    }
    
    
	public void popBool()
	{
		// Jus to pop true -1 or false -0 on top of stack.IT has 4 bytes
		//System.out.println("STCK PTR-->" + sp); // sp is 3 here
		while(sp!=0)
		{
			CodeExecution.InsStack.remove(InsStack.size()-1);
			sp--;
		}
		CodeExecution.InsStack.remove(InsStack.size()-1); // last one has to be removed.
	}
    
    
	public void run(ArrayList<Byte> Code , ArrayList<Byte> Data)
	{
			
		//System.out.println("----------------");
		
		// points to the code array
		int ip=0;
		
		// To identify whether its an address or value
		int identy=0;
		
		while(ip!=Code.size())
		{
		switch(Code.get(ip))
		{
		
		//PUSH
		case 1:
			//System.out.println("the ins is Push address");
			ip++;
			
			// this gets the size of the variable .Can be different for char , int/real
			//System.out.println("ip --->" + Code.get((ip+3)));
			
			/* For integer */
			// If integer
			// tem var to increment until
			// for integer do until4 as size is 4.
			int tem=0;
			dpsize=Code.get((ip+3));
			dpsize=dpsize-3;
			dp=dpsize-1;
			//System.out.println(dpsize);
			
			//System.out.println("In stack pntr-->" + sp + " " + tem + "dp-->" + dp);
			while(tem!=4)
			{
				//System.out.println("dpsize->" + dpsize + "dp->" + dp + "sp->" + sp);
				// dp gives the idex value.
				CodeExecution.InsStack.add(Data.get(dp));
		//System.out.println("item-->"+ InsStack.indexOf(Data.get(dp)) + " " +  InsStack.get(InsStack.indexOf(Data.get(dp))));
				dpsize++;
				tem++;
				dp++;
				sp++;
				
			}
			// during push add the address into datapntr and to keep track of position use
			// dataptr. remember the dataptr becomes 1 for first push as intially it is zero.
			datapntr.add(dp);
			dataptr++;
			
			/* Increments to keep track of address. Will be used during addition.
			r:=i+j;
			push r
			push i
			push j
			add
			if its an address then last value at datapntr arraylist has to be poped to reach back to r's address 
			*/
			identy++;
			//System.out.println("*********total->" + datapntr + "pointer-->" + dataptr);
			//System.out.println("IDENTITY-------push------------>" + identy);
			
			// When sp is 0 at the beginning , then 0-4 gives 5 bytes , but we are at 0-3
			// 0-3 size if 4 whereas 0-4 is 5 bytes. So when sp=0 we
			// decrease it to reach 3
			if(sp==4)
			{
			sp--;
			}
			else
			{
			//done decrease
				// further address push doesnt require to do sp--;
			}
			//System.out.println("Stack pointe at-->" + CodeExecution.sp);
			ip=ip+4;
			break;
			
		//PUSHI
		case 2:	
			//System.out.println("The ins is PUSHI");
			ip++;
			byte[] temp=new byte[4];
			temp[0]=Code.get(ip);
			temp[1]=Code.get(ip+1);
			temp[2]=Code.get(ip+2);
			temp[3]=Code.get(ip+3);
			int val;
			val=CodeExecution.fromByteArray(temp);
			CodeExecution.push(val);
			ip=ip+4;
			//System.out.println(ip);
			break;
		
		case 18:
			ip++;
			byte[] temp1=new byte[4];
			temp1[0]=Code.get(ip);
			temp1[1]=Code.get(ip+1);
			temp1[2]=Code.get(ip+2);
			temp1[3]=Code.get(ip+3);
			int valr;
			valr=CodeExecution.fromByteArray(temp1);
			
			CodeExecution.push(valr);
			ip=ip+4;
			//System.out.println(ip);
			break;
		
		// POP
		case 3:
			ip++;
			//System.out.println("Instruction is POP");
			pop(Data);
			/*When pop is done the identy is set back to 0 , ex-when i=4; is over .Now for j=3;
			 again the identy starts from zero and increments and then during pop again reset to 0
			 And continues for next statement.
			*/
			identy=0;
			//System.out.println("IDENTITY-------pop------------>" + identy);
			break;
			
		//ADD INTEGER
		case 4:
			//System.out.println("ADD INTEGERS");
			int val1 ,val2;
			val1=CodeExecution.popInt();
			//dp=dp-4;
			//System.out.println("total dataptr-->" + datapntr);
			/*
			 * If its Value it wont enter this condition at all.we only increment identy at push
			 * and not in PUSHI.
			 *If identy is not 1 then there is an address here.
			 *ex push a identy=1
			 *push b identy=2
			 *push c identy=3
			 *add
			 *pop the datapntr arraylist last element to reach until it is identy=1
			*/
			if(identy!=1)
			{
				//System.out.println("ENTERING****111**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			//System.out.println("val1***********>" + val1);
			val2=CodeExecution.popInt();
			//System.out.println("val2*******>" + val2);
			//System.out.println("IDENTITY-------add integer------------>" + identy);
			/*
			 *If identy is not 1 then there is an address here.
			 *ex push a identy=1
			 *push b identy=2
			 *push c identy=3
			 *add
			 *pop the datapntr arraylist last element to reach until it is identy=1
			*/
			if(identy!=1)
			{
				//System.out.println("ENTERING****222**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			val2=val2+val1;
			//System.out.println("Result*****>" + val2);
			CodeExecution.push(val2);
			ip++;
			break;
			
			
		case 17:
			//System.out.println("ADD INTEGERS");
			//int val1 ,val2;
			float f1,f2;
			val1=CodeExecution.popInt();
			f1=Float.intBitsToFloat(val1);
			
			if(identy!=1)
			{
				//System.out.println("ENTERING****111**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			val2=CodeExecution.popInt();
			f2=Float.intBitsToFloat(val2);
			
			if(identy!=1)
			{
				//System.out.println("ENTERING****222**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			//val2=val2+val1;
			//System.out.println("Result*****>" + val2);
			
			float f7;
			f7=f1+f2;
			int tempr;
			
			tempr=Float.floatToIntBits(f7);
			
			CodeExecution.push(tempr);
			ip++;
			break;	
		// multiplication	
		case 20:	
			//System.out.println("ADD INTEGERS");
			int mul1 ,mul2;
			mul1=CodeExecution.popInt();
			//dp=dp-4;
		
			if(identy!=1)
			{
				//System.out.println("ENTERING****111**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			//System.out.println("val1***********>" + val1);
			mul2=CodeExecution.popInt();
			//System.out.println("val2*******>" + val2);
			//System.out.println("IDENTITY-------add integer------------>" + identy);
			/*
			 *If identy is not 1 then there is an address here.
			 *ex push a identy=1
			 *push b identy=2
			 *push c identy=3
			 *add
			 *pop the datapntr arraylist last element to reach until it is identy=1
			*/
			if(identy!=1)
			{
				//System.out.println("ENTERING****222**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			mul2=mul2*mul1;
			//System.out.println("Result*****>" + val2);
			CodeExecution.push(mul2);
			ip++;
			break;

		case 24:
		
			//System.out.println("ADD INTEGERS");
			
			mul1=CodeExecution.popInt();
			//dp=dp-4;
			f1=Float.intBitsToFloat(mul1);
			if(identy!=1)
			{
				//System.out.println("ENTERING****111**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			//System.out.println("val1***********>" + val1);
			mul2=CodeExecution.popInt();
			f2=Float.intBitsToFloat(mul2);
			if(identy!=1)
			{
				//System.out.println("ENTERING****222**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			
			f7=f1*f2;
		
			tempr=Float.floatToIntBits(f7);
			//System.out.println("Result*****>" + val2);
			CodeExecution.push(tempr);
			ip++;
			break;
			
		//DIVISION 	
		case 22:	
			//System.out.println("ADD INTEGERS");
			int div1 ,div2;
			div1=CodeExecution.popInt();
			//dp=dp-4;
		
			if(identy!=1)
			{
				//System.out.println("ENTERING****111**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			//System.out.println("val1***********>" + val1);
			div2=CodeExecution.popInt();
			//System.out.println("val2*******>" + val2);
			//System.out.println("IDENTITY-------add integer------------>" + identy);
			/*
			 *If identy is not 1 then there is an address here.
			 *ex push a identy=1
			 *push b identy=2
			 *push c identy=3
			 *add
			 *pop the datapntr arraylist last element to reach until it is identy=1
			*/
			if(identy!=1)
			{
				//System.out.println("ENTERING****222**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			div2=div2/div1;
			//System.out.println("Result*****>" + val2);
			CodeExecution.push(div2);
			ip++;
			break;
		
			
		case 25:	
			//System.out.println("ADD INTEGERS");
			
			div1=CodeExecution.popInt();
			//dp=dp-4;
			f1=Float.intBitsToFloat(div1);
			if(identy!=1)
			{
				//System.out.println("ENTERING****111**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			//System.out.println("val1***********>" + val1);
			div2=CodeExecution.popInt();
			f2=Float.intBitsToFloat(div2);
			//System.out.println("val2*******>" + val2);
			//System.out.println("IDENTITY-------add integer------------>" + identy);
			/*
			 *If identy is not 1 then there is an address here.
			 *ex push a identy=1
			 *push b identy=2
			 *push c identy=3
			 *add
			 *pop the datapntr arraylist last element to reach until it is identy=1
			*/
			if(identy!=1)
			{
				//System.out.println("ENTERING****222**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			f7=f2/f1;
			
			tempr=Float.floatToIntBits(f7);
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			
			//System.out.println("Result*****>" + val2);
			CodeExecution.push(tempr);
			ip++;
			break;	
			
			
		case 21:	
			//System.out.println("ADD INTEGERS");
			int sub1 ,sub2;
			sub1=CodeExecution.popInt();
			//dp=dp-4;
		
			if(identy!=1)
			{
				//System.out.println("ENTERING****111**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			//System.out.println("val1***********>" + val1);
			sub2=CodeExecution.popInt();
			
			if(identy!=1)
			{
				//System.out.println("ENTERING****222**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			sub2=sub2-sub1;
			//System.out.println("Result*****>" + val2);
			CodeExecution.push(sub2);
			ip++;
			break;
		
			
		case 23:	
			//System.out.println("ADD INTEGERS");
			//int sub1 ,sub2;
			//float f1,f2;
			sub1=CodeExecution.popInt();
			//dp=dp-4;
			f1=Float.intBitsToFloat(sub1);
			if(identy!=1)
			{
				//System.out.println("ENTERING****111**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			//System.out.println("val1***********>" + val1);
			sub2=CodeExecution.popInt();
			f2=Float.intBitsToFloat(sub2);
			if(identy!=1)
			{
				//System.out.println("ENTERING****222**");
				datapntr.remove(datapntr.size()-1);
				--dataptr;
				identy--;
			}
			else
			{
				
			}
			
			f7=f2-f1;
			
			tempr=Float.floatToIntBits(f7);
			//System.out.println("Remove at-->" + dataptr + " " + datapntr);
			
			//System.out.println("Result*****>" + val2);
			CodeExecution.push(tempr);
			ip++;
			break;	
			
			
		//EQUAL COMPARISON	
		case 5:
			
			int localeq1;
			int localeq2;
			//System.out.println("INSTRUCTION IS EQUAL");
			// grab the last 8 bytes whihc has operands to compare.
			//System.out.println("STACK POINTER--->" + sp + " "+ InsStack.get(sp));
			/*
			localeq1=sp-3;
			System.out.println("Current pointer at--->" + InsStack.get(localeq1));
			*/
			localeq1=CodeExecution.popInt();
			//System.out.println("LOCALEQ1---->" + localeq1);
			localeq2=CodeExecution.popInt();
			//System.out.println("LOCALEQ2---->" + localeq2);
			
			if(localeq1==localeq2)
			{
				push(1);
				//System.out.println("STCK PNTR AFTER--->" + sp);
				
				//popBool();
			}
			else
			{
				push(0);
				//System.out.println("STCK PNTR AFTER--->" + sp);
				//popBool();
			}
			
			
			ip++;
			break;
			
			
		// JFalse	
		case 6:	
			//to store tr and false in a 1,0 format
			int bool;
			//System.out.println("AT JFALSE--->" + sp);
			bool=popInt();
			// popint takes care of decrementing the sp value.
			//sometime it decreases one more less when sp=3
			//to make it as zero we add 1 to sp
			sp++;
			//System.out.println("bool value-->" + bool + "stck pntre-->" + sp + " Ip -->" + ip);
			if(bool==1)
			{
				//System.out.println("TRUE");
				// if true jump ahead of the target and continue with usual flow of program.
				ip=ip+5;
				//System.out.println("TRUE-->" + Code.get(ip));
				//ip=Code.get(ip);
			}
			else
			{
				
				
				ip=getAddress(ip,Code);
				//System.out.println("FALSE-->" + Code.get(ip));
				//ip=ip+4;
				
				// if false then assigne the stored target value to the ip so that it loops
				// untilthe condition becomes true. supprots repeat until
				//ip=Code.get(ip);
			}
			
			break;
			
			
		//Less Than
		case 8:
			
			int less1;
			int less2;
			//System.out.println("INSTRUCTION IS LESS THAN");
			// grab the last 8 bytes whihc has operands to compare.
			//System.out.println("STACK POINTER--->" + sp + " "+ InsStack.get(sp));
			/*
			localeq1=sp-3;
			System.out.println("Current pointer at--->" + InsStack.get(localeq1));
			*/
			less1=CodeExecution.popInt();
			//System.out.println("LESS1---->" + less1);
			less2=CodeExecution.popInt();
			//System.out.println("LESS2---->" + less2);
			//System.out.println("next instr-->" + Code.get(ip+1));
			//ip++;
			if(less2<less1)
			{
				push(1);
				//System.out.println("ITS LESS THAN TRUE");
				//System.out.println("STCK PNTR AFTER--->" + sp);
				
				//popBool();
			}
			else
			{
				push(0);
				//System.out.println("ITS LESS THAN FALSE");
				//System.out.println("STCK PNTR AFTER--->" + sp);
				//popBool();
			}
			ip++;
			break;
		// STOP THE EXEC	
		case 10:
			//System.out.println("---STOP--");
			ip++;
			break; 
		
		// WRITELN	
		case 9:
			//System.out.println("Instr to WRITELN");
			//System.out.println("stack pointer in WRITELN-->" + sp);
			int toprint;
			toprint=popInt();
			ip++;
			sp++;
			//System.out.println("stack pointer after WRITELN->" + sp + "code->" + Code.get(ip));
			System.out.println(toprint);
			//ip++;
			break;
			
		// NORMAL JUMP
		case 11:
			//System.out.println("instruction is JUMP");
			ip=getAddress(ip, Code);
			break;
			
		// GREATERTHAN
		case 12:
			int grt1;
			int grt2;
			//System.out.println("INSTRUCTION IS greater THAN");
			// grab the last 8 bytes whihc has operands to compare.
			//System.out.println("STACK POINTER--->" + sp + " "+ InsStack.get(sp));
			/*
			localeq1=sp-3;
			System.out.println("Current pointer at--->" + InsStack.get(localeq1));
			*/
			grt1=CodeExecution.popInt();
			//System.out.println("grt1---->" + grt1);
			grt2=CodeExecution.popInt();
			//System.out.println("grt2---->" + grt2);
			//System.out.println("next instr-->" + Code.get(ip+1));
			//ip++;
			if(grt2>grt1)
			{
				push(1);
				//System.out.println("ITS great THAN TRUE");
				//System.out.println("STCK PNTR AFTER--->" + sp);
				
				//popBool();
			}
			else
			{
				push(0);
				//System.out.println("ITS great THAN FALSE");
				//System.out.println("STCK PNTR AFTER--->" + sp);
				//popBool();
			}
			ip++;
			break;
			
		// Less Than equal  <=	
			
		case 13:
			
			int lst1;
			int lst2;
			//System.out.println("INSTRUCTION IS greater THAN");
			// grab the last 8 bytes whihc has operands to compare.
			//System.out.println("STACK POINTER--->" + sp + " "+ InsStack.get(sp));
			/*
			localeq1=sp-3;
			System.out.println("Current pointer at--->" + InsStack.get(localeq1));
			*/
			lst1=CodeExecution.popInt();
			//System.out.println("lst1---->" + lst1);
			lst2=CodeExecution.popInt();
			//System.out.println("lst2---->" + lst2);
			//System.out.println("next instr-->" + Code.get(ip+1));
			//ip++;
			if(lst2<=lst1)
			{
				push(1);
				//System.out.println("ITS great THAN TRUE");
				//System.out.println("STCK PNTR AFTER--->" + sp);
				
				//popBool();
			}
			else
			{
				push(0);
				//System.out.println("ITS great THAN FALSE");
				//System.out.println("STCK PNTR AFTER--->" + sp);
				//popBool();
			}
			ip++;
			break;
			
		
			// To write string 
			
		case 14:
			
			int loop=1;
			int length=0;
			ip++;
			byte[] wrttemp=new byte[4];
			wrttemp[0]=Code.get(ip);
			wrttemp[1]=Code.get(ip+1);
			wrttemp[2]=Code.get(ip+2);
			wrttemp[3]=Code.get(ip+3);
			ip=ip+4;
			length=CodeExecution.fromByteArray(wrttemp);
			//System.out.println("lenght of the string" + length);
			byte tempch;
			char strch;
			StringBuilder printstr=new StringBuilder();
			
			while(loop<=length)
			{
				//System.out.println("String elements-->" + Code.get(ip));
				tempch=Code.get(ip);
				strch=(char)tempch;
				printstr.append(strch);
				ip++;
				loop++;
			}
			
			System.out.println(printstr);
			
			
			//ip=ip+10;
			
			break;
			
			
		case 15:
			
			int loop1=1;
			int length1=0;
			ip++;
			byte[] wrttemp1=new byte[4];
			wrttemp1[0]=Code.get(ip);
			wrttemp1[1]=Code.get(ip+1);
			wrttemp1[2]=Code.get(ip+2);
			wrttemp1[3]=Code.get(ip+3);
			ip=ip+4;
			length1=CodeExecution.fromByteArray(wrttemp1);
			//System.out.println("lenght of the string" + length);
			byte tempch1;
			char strch1;
			StringBuilder printstr1=new StringBuilder();
			
			while(loop1<=length1)
			{
				//System.out.println("String elements-->" + Code.get(ip));
				tempch=Code.get(ip);
				strch=(char)tempch;
				printstr1.append(strch);
				ip++;
				loop1++;
			}
			
			System.out.print(printstr1);			
		//	System.out.println("");
			//ip=ip+10;
			
			break;
			
		case 16:
			
			
			System.out.println(pop_at(Data));
			ip++;
			break;
		
		case 19:
			
			
			System.out.println(pop_at_real(Data));
			ip++;
			break;	
			
		case 26:
			
			
			System.out.println(pop_at_char(Data));
			ip++;
			break;	
				
		default:
			System.out.println("default" + Code.get(ip));
			ip++;
			break; 
		}
		
		
		}
		
		for(int j=0;j<CodeExecution.InsStack.size();j++)
		{
			System.out.println("Inst pointer--->" + CodeExecution.InsStack.get(j));
		}
		//System.out.println(CodeExecution.InsStack.size());
		/*
		System.out.println("------------MEMORY-----------------------------");
		for(int m=0;m<Data.size();m++)
		{
			System.out.println("MEMORY CONTENT-->"+ Data.get(m));
		}
		/*
		for(int m=0;m<datapntr.size();m++)
		{
			System.out.println("sizeof variables-->"+ datapntr.get(m));
		}
		*/
	}
	
}
