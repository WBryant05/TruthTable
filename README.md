The report class contains the main method as well as two helper methods,
getVar() and printTable(). in-file comments explain each function.

Usage:
  Begin by entering the names of each variable for future use in your logical operations.
This is where the operation cycle will begin. It will ask you to enter the first variable to operate on,
then the second. If you are going to use a negate, enter "NA" for the second variable.
Previous operations can be referenced for operation by typing "op" then the number immediately after
the operation you are referencing. (op1, op2, etc.) Then enter the operator you are using. 

List of operators: 
  Negate, "-".
  Or, "v".
  And "^".
  Xor, "xo".
  Implication, "->".
  Biconditional, "<->".
  Equals, "=".
  
  At this point the table will print with all previous operations
as well as the new one. It will ask whether or not your table is completed.
Answering "y" will end the program. (data will stay unless program is rerun or IDE is closed)

Notes:
  It is advised to use this software by building up from simplest to most complex.
For example, if you were in the process of calculating (-(P<->Q))=(P<->(-Q)),
You should start with P<->Q, then negate that. On the other side, start with -Q, then P<->(-Q).
Reference both -(P<->Q) and (P<->(-Q)) with the "=" operator to finish.
This should output true for all cases.
