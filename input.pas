program HelloWorld;

var
i,k,j,t:integer;
m:integer;
r:integer;
a:integer;
realval,rl1,rl2,rl3:real;
c:char;
case_number:integer;
n:array [1..10] of integer;


(* Here the main program block starts *)
begin

writeln('--------Program starts here-----------');

i:=6;
t:=3*3;
j:=23;
c:='e';
r:=i+j;
r:=4+j;
r:=j+3;
a:= 1;
item:=1;
n[4]:=11;
n[5]:=12;
rl1:=3.4-2.4;
realval:=3.4 + 52.2;
rl2:=3.4*2.3;
rl3:=4.2/2.0;

writeln('Assignments:');	
writeln('------------');
writeln('realval value is: ' ,realval);
writeln('j value is:',j );
writeln('i value is:',i );
writeln('r value is:',r );
writeln('array n[4] value is:',n[4] );
writeln('a value is:', a );
writeln('t value is:', t );
writeln('char c value is:', c );

writeln(' ');
writeln('Arithmetic Operations:');
writeln('----------------------');
t:=j*3;
writeln('Multiplication t is j * 3 =' , t);
t:=4+9;
writeln('Addition t is 4 + 9 =' , t);
t:=12-6;
writeln('Subtraction t is 12 - 6 =' , t);
t:=8/4;
writeln('Division t is 8/4 =' , t);
writeln('Adding real numbers 3.4+55.2= ' ,realval);
writeln('Subtracting real numbers 3.4-2.4= ' ,rl1);
writeln('Multiply real numbers 3.4*2.3= ' ,rl2);
writeln('Dividing real numbers 4.2/2.0= ' ,rl3);


(* repeat until loop execution *)
writeln(' ');
writeln('Repeat Until Loop- until a equals 5');
writeln('------------------------------------');

repeat
  a := a + 1; 
  writeln('a value is:' , a);
until a=5;

writeln(' ');
writeln('Executing while loop - while a less than 9 do');
writeln('---------------------------------------------');
while  a < 9  do
begin
a:=a+1;
 writeln('a value is:' , a); 
end;

writeln(' ');
writeln('Executing IF Statement');
writeln('----------------------');
writeln(' ');

writeln('Check if "a" is greater than 13');

{ If then else statement }

if( a > 13 ) then
writeln('Yes a is greater. a:' , a);
else
writeln('No a is less. a: ' , a);

writeln('                         ');
writeln('Check if "a" is greater than 2');

if( a > 2 ) then
writeln('Yes a is greater. a:' , a);
else
writeln('No a is less. a:' , a);




writeln(' ');
{For statement}
writeln('Executing FOR Statement:');
writeln('------------------------');
writeln(' ');

writeln('print i from 2 to 8');

for i := 2 to 8 do
begin
writeln('i value:' , i);
end;

writeln(' ');
writeln('array assignment:');
writeln('-----------------');
writeln('array at n[4] is:',n[4]);
writeln('array at n[5] is:',n[5]);


writeln(' ');
writeln('Case statement Execution ');
writeln('-------------------------');
case_number:=3;
writeln('which case: ' ,item);

case (case_number) of
      1 : writeln('First case executed' );
      2 : writeln('Second case executed' );
	  3 : writeln('Third case executed' );
	  4 : writeln('Fourth case executed');
   end;

writeln(' ');
writeln('------end of program----');
writeln(' ');
writeln('------Thank You!!!----');


(* r:=i+j; *)
END.