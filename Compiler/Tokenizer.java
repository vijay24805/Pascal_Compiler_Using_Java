

import java.io.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
//import compiler.scanner.ScannerPointer;





public class Tokenizer {

	
	
	
	static boolean check_array(int ptr)
	{
		
		
		
		return true;
	}
	
	static void gettoken(ArrayList<Character> c) {
		String curtoken = "";
		final int TK_KEYWORD = 1;
		final int TK_IDENTIFIER = 2;
		final int TK_DIGIT = 3;
		
		
		int curtok;
		ArrayList<ScannerPointer> tokens = new ArrayList<ScannerPointer>();
		int linenumber=0;
	
		
		for (int i = 0; i < c.size();) {
			String curvalue = "";
			
			// LETTERS- KEYWORDS AND IDENTIFIERS
			if (Character.isLetter(c.get(i)) || c.get(i) == 95) {

				while ((c.get(i) != 32) && (c.get(i) != 59) && (c.get(i) != 40) && (c.get(i) != 10) &&(c.get(i) != 46) && (c.get(i)!=41) && !(c.get(i)>=42 && c.get(i)<=47)
						&& c.get(i)!=91 && c.get(i)!=93 && c.get(i)!=61 && c.get(i)!=58) {
					curvalue = curvalue + c.get(i);
					
					

					i++;
					
					// cur.add(j, curvalue);
				}
				////System.out.println(curvalue);
				if(curvalue.equalsIgnoreCase("for"))
				{
				curtoken = "TK_FOR";
				
				}
				else if(curvalue.equalsIgnoreCase("TO"))
				{
				curtoken = "TK_TO";
				
				}
				else if(curvalue.equalsIgnoreCase("program"))
				{
					curtoken ="TK_PROGRAM";
				}
				else if(curvalue.equalsIgnoreCase("var"))
				{
					curtoken="TK_DECLARATION";
				}
				else if(curvalue.equalsIgnoreCase("integer"))
				{
					curtoken="TK_INT";
				}
				else if(curvalue.equalsIgnoreCase("begin"))
				{
					curtoken="TK_BEGIN";
				}
				else if(curvalue.equalsIgnoreCase("end"))
				{
					curtoken="TK_END";
				}
				else if(curvalue.equalsIgnoreCase("repeat"))
				{
					curtoken="TK_REPEAT";
				}
				else if(curvalue.equalsIgnoreCase("until"))
				{
					curtoken="TK_UNTIL";
				}
				else if(curvalue.equalsIgnoreCase("while"))
				{
					curtoken="TK_WHILE";
				}
				else if(curvalue.equalsIgnoreCase("do"))
				{
					curtoken="TK_DO";
				}
				else if(curvalue.equalsIgnoreCase("writeln"))
				{
					curtoken="TK_WRITELN";
				}
				else if(curvalue.equalsIgnoreCase("if"))
				{
					curtoken ="TK_IF";
				}
				else if(curvalue.equalsIgnoreCase("then"))
				{
					curtoken ="TK_THEN";
				}
				else if(curvalue.equalsIgnoreCase("else"))
				{
					curtoken ="TK_ELSE";
				}
				else if(curvalue.equalsIgnoreCase("array"))
				{
					curtoken ="TK_ARRAY";
				}
				else if(curvalue.equalsIgnoreCase("of"))
				{
					curtoken ="TK_OF";
				}
				else if(curvalue.equalsIgnoreCase("case"))
				{
					curtoken ="TK_CASE";
				}
				else if(curvalue.equalsIgnoreCase("real"))
				{
					curtoken ="TK_REAL";
				}
				else if(curvalue.equalsIgnoreCase("char"))
				{
					curtoken ="TK_CHAR";
				}
				else
				{
					curtoken ="TK_VAR";
				}
				
				ScannerPointer s=new ScannerPointer(curvalue, curtoken, 0 ,0.0f);
				tokens.add(s);
				
				
				
				
				
			} 
			// DIGITS
			else if (Character.isDigit(c.get(i))) {

				int val=0;
				float realval=0.0f;
				while((c.get(i)>=48 && c.get(i)<=57) && (c.get(i+1)!=46) && (c.get(i+2)!=46) )
				{	/*
					int check=Character.digit(c.get(i), 10);
					////System.out.println("check---"+check);
					val=(char)((val*10)+check);
					*/
				
				////System.out.println("val"+val);
				curtoken = "TK_INTEGER";
				curvalue = curvalue + c.get(i);
				val=Integer.parseInt(curvalue);
				////System.out.println(curvalue);
				i++;
				}
				
				
				
				if((c.get(i)>=48 && c.get(i)<=57) && (c.get(i+1)!=46) || (c.get(i+2)!=46))
				{
														
				//System.out.println("Entering to check real-------");
				while((c.get(i)>=48 && c.get(i)<=57) || ((c.get(i)==46) && c.get(i+1)!=46))
				{
				
				curtoken = "TK_REAL";
				curvalue = curvalue + c.get(i);	
				//System.out.println(curvalue);
				i++;
				}
				
				//realval=Double.parseDouble(curvalue);
				realval=Float.parseFloat(curvalue);
				}
				
				
				
					while((c.get(i)>=48 && c.get(i)<=57) && (c.get(i+1)!=46) && (c.get(i+2)!=46))
					{
						
					curtoken = "TK_INTEGER";
					curvalue = curvalue + c.get(i);
					val=Integer.parseInt(curvalue);
					//i=i+4;
					i++;
					}
				 	
					
				
				// To store the lower bound of an array
				if((c.get(i)>=48 && c.get(i)<=57))
				{
				   while((c.get(i)>=48 && c.get(i)<=57) && ((c.get(i+1)!=93) || (c.get(i+1)!=46))  )
					{
					 ////System.out.println("enters");
					curtoken = "TK_INTEGER";
					curvalue = curvalue + c.get(i);
					val=Integer.parseInt(curvalue);
					
					i++;
					////System.out.println("--->" +val + " " +  i);
					}
					
				}
				
				ScannerPointer s=new ScannerPointer(curvalue, curtoken,val,realval);
				tokens.add(s);
				

			} 
			// SPACE
			else if (c.get(i) >= 0 && c.get(i) <= 32) {
				////System.out.println("its a space");
				i++;
				////System.out.println(i);

			}
			// STRINGS
			else if(c.get(i) == 39)
			{
				//'bill'
				i++;
				while(c.get(i)!= 39)
				{
					curvalue = curvalue + c.get(i);
					curtoken="TK_STRING";
					i++;	
				}
				i++;
				//System.out.println(curvalue);
				//System.out.println(curtoken);
				
				ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
				tokens.add(s);
			}
			
			// STORING COMMENTS (*......*) and {.....}
			else if((c.get(i) == 40 && c.get(i+1)== 42) || (c.get(i) == 123) )
			{
				if((c.get(i) == 40 && c.get(i+1)== 42))
				{
				i=i+2;
				while(c.get(i) !=42 && c.get(i+1) !=41)
				{
					//curvalue = curvalue + c.get(i);
					//curtoken="TK_COMMENTS";
					i++;	 
				}
				////System.out.println(curvalue);
				////System.out.println(curtoken);
				i=i+2;
				
				}
				else if(c.get(i)== 123)
				{
					i=i+1;
					while(c.get(i) !=125)
					{
						//curvalue = curvalue + c.get(i);
						//curtoken="TK_COMMENTS";
						i++;		
					}
					////System.out.println(curvalue);
					////System.out.println(curtoken);
					i++;	
				}
				//ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0);
				//tokens.add(s);
			}
			//to store-- (
			else if(c.get(i)==40 && c.get(i)!=42)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_LEFTPRTH";
				i++;
				ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
				tokens.add(s);
			}
			// to store-- )
			else if(c.get(i)== 41)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_RIGHTPRTH";
				i++;
				ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
				tokens.add(s);
			}
			//to store :   or := assignments
			else if((c.get(i)==58))
			{
			  if(c.get(i)==58 && c.get(i+1)==61)
			  {
				  curvalue = curvalue + c.get(i)+c.get(i+1);
				  i=i+2;
				  curtoken="TK_ASSIGN";
			  }
			  else
			  {
				  curvalue = curvalue +c.get(i);
				  curtoken="TK_COLON";
				  i++;
			  }
			  
			  ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
				tokens.add(s);
			  
			}
			
			// to store--- ;
			else if(c.get(i)==59)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_SEMICOLON";
					i++;
					ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
					tokens.add(s);
			}
			
			// to store -- all special characters except [] = {} symbol
			else if(c.get(i)==42)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_MUL";
					i++;
					ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
					tokens.add(s);
			}
			else if(c.get(i)==43)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_ADD";
					i++;
					ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
					tokens.add(s);
			}
			else if(c.get(i)==45)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_SUB";
					i++;
					ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
					tokens.add(s);
			}
			
			else if(c.get(i)==44)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_COMMA";
					i++;
					ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
					tokens.add(s);
			}
			else if(c.get(i)==47)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_DIV";
					i++;
					ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
					tokens.add(s);
			}
			else if(c.get(i)==46)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_STOP";
					i++;
					ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
					tokens.add(s);
			}
			
			
			// to store [ and ]
			else if(c.get(i)== 91)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_OpenBracket";
					i++;
					ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
					tokens.add(s);
			}
			
			else if(c.get(i)==93)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_CloseBracket";
					i++;
					ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
					tokens.add(s);
			}
			
			
			else if(c.get(i)== 46 || c.get(i+1)==46)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_SpecialCharacters";
					i=i+2;
					ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
					tokens.add(s);
			}
			
			
			// to store =
			else if(c.get(i)==61)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_EQUAL";
					i++;
					ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
					tokens.add(s);
			}
			
			// to store <
			else if(c.get(i)==60)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_LESSTHAN";
					i++;
					ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
					tokens.add(s);
			}
			
			
			// to store >
			else if(c.get(i)==62)
			{
				curvalue=curvalue+c.get(i);
				curtoken="TK_GREATERTHAN";
					i++;
					ScannerPointer s=new ScannerPointer(curvalue, curtoken,0,0.0f);
					tokens.add(s);
			}
			
			//DIFFERENT TOKEN
			else
			{
				////System.out.println("differnt token");
				i++;
			}
			}
		
		
		
		PrintsToken t=new PrintsToken();
		//t.printtoken(tokens);
		Parser parsing = new Parser();
		parsing.parsing(tokens);

		}
	
		

	public static void main(String[] args) {
		//System.out.println("Reading from text file");
		// Name of the file
		
		String fileName;
		
       fileName = args[0];
      // fileName=fileName;
		
		//System.out.println(fileName);   
		
		try {


			// Create object of FileReader
			FileReader inputFile = new FileReader(fileName);

			// Instantiate the BufferedReader Class
			BufferedReader bufferReader = new BufferedReader(inputFile);

			

			int readvalue;
			
			ArrayList<Character> scanp = new ArrayList<Character>();
			
			// Reading the complete file and Storing each character
			while ((readvalue = bufferReader.read()) != -1) {

				scanp.add((char) readvalue);
				
			}
		
			
			//System.out.println(scanp);

			//Main tokenization loop starts until EOF
			do {
				//get token type and value , it has an function inside to call printtoken() method
				gettoken(scanp);
				

			} while (readvalue != -1);

			
			
			// Close the buffer reader
			bufferReader.close();
		} catch (Exception e) {
			System.out.println("COMPILER ERROR ,CHECK THE SYNTAX PLEASE");
			System.out.println("Error:-"
					+ e.getMessage() );
		}

	}

}
