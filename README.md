Pascal_Compiler_Using_Java
==========================


------------------------------------------------------------

Instruction to run the Compiler

Requirements :- Java JDK 1.5 and above.

------------------------------------------------------------


1)Please download the zip file-CompilerConstruction.zip attached with the email.

2)Inside the zip file is a folder named Compiler. Copy the folder-Compiler in your drive.

3)Compiler folder consist of following files:

 a)input.pas  -      Pascal file.
 
 b)Tokenizer.java - Main java class.

 c)And other class files used by main class.
 

4)Open the command prompt and reach till the path where you have saved the folder-Compiler.

Command:- cd/....{location path}..../Compiler

5) Compile the Main Java class using the command:-

        javac Tokenizer.java
	--------------------

6) Run the Compiler with the pascal file as input arguments using the command:

        java Tokenizer input.pas
	------------------------

7)Any changes made in the Pascal file input.pas requires to compile and run the program again(follow step 5 and 6).

8)End.

