package main;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Scanner;

public class Report {

	/**
	 * getVar() prompts the user to input which of the variables they want to use in the operation.
	 * If the input is not in the list of known variables, it tells the user it was not found and asks again.
	 * If there are 3 invalid inputs in a row, the function returns -1,
  	 * which terminates the program. (lines 182-184 & 186-188)
	 * 
	 * @param myObj
	 * @param topLine
	 * @param varNum
	 * @return
	 */

	public static int getVar(Scanner myObj, ArrayList<String> topLine, int varNum) {
		int tryC = 0;
		while (true) {
			System.out.println("Enter Variable " + varNum + ".");
			String in = myObj.nextLine();
			for (int i = 0; i < topLine.size(); i++) {
				if (in.equals(topLine.get(i)) || in.equals("NA")) {
					return i;
				}
			}
			if (tryC <= 3) {
				System.out.println("Variable not detected. Try Again.");
				tryC++;
			} else {
				System.out.println("Too many attempts. Program Quitting Automatically.");
				return -1;
			}
		}
	}

	/**
	 * PrintTable() takes TT (ArrayList of ArrayLists of booleans) 
  	 * as well as topLine (ArrayList of Strings) and formats the data
	 * into a table, printing T for true, and F for false.
	 * 
	 * @param TT
	 * @param topLine
	 * @param n
	 */

	public static void printTable(ArrayList<ArrayList<Boolean>> TT, ArrayList<String> topLine, int n) {
		// Determines length of the entire top line as each element is printed.
		// NOTE: print is used, not println(), this is for formatting purposes.
		int len = 0;
		for (int i = 0; i < topLine.size(); i++) {
			System.out.print("| " + topLine.get(i) + " ");
			if (i >= n) {
				String str = (i - n + 1) + " ";
				System.out.print(str);
				len += str.length();
			}
			len += topLine.get(i).length() + 3;
		}
		System.out.print("|\n|");
		len--;
		// Prints dashes to separate each row.
		for (int j = 0; j < len; j++) {
			System.out.print("-");
		}
		System.out.print("|\n");
		// 1st for loop: iterates through every row.
		// 2nd for loop: iterates through every column.
		String a;
		for (int i = 0; i < Math.pow(2, n); i++) {
			for (int j = 0; j < topLine.size(); j++) {
				// Converts booleans to strings for printing.
				if (TT.get(j).get(i)) {
					a = "T";
				} else {
					a = "F";
				}
				// Prints each element in every row, adding spaces based on the length of the column label.
				System.out.print("|");
				int opLen = topLine.get(j).length() + 1;
				if (j >= n) {
					opLen += ((j - n + 1) + " ").length();
				}
				// For loops are used to adjust the number of spaces.
				for (int k = 0; k < opLen / 2; k++) {
					System.out.print(" ");
				}
				System.out.print(a);
				for (int k = 0; k < opLen / 2 + opLen % 2; k++) {
					System.out.print(" ");
				}
			}
			// Prints dashes to separate each row as demonstrated in the top line. (lines 66-68)
			System.out.print("|\n|");
			for (int j = 0; j < len; j++) {
				System.out.print("-");
			}
			System.out.print("|\n");
		}
	}

	public static void main(String args[]) {
		// Gets the number of variables as a command line argument.
		if (args.length != 1) {
			System.err.println("ERROR: expecting a single integer argument");
			System.exit(1);
		}

		Integer n = null;
		try {
			n = Integer.parseInt(args[0]);
		} catch (NumberFormatException nfe) {
			System.err.println("ERROR: expecting a single integer argument");
			System.exit(1);
		}
		// Defines main ArrayLists (V is a temporary ArrayList).
		ArrayList<ArrayList<Boolean>> TT = new ArrayList<>();
		ArrayList<Boolean> V;
		// Iterates through each variable, generating all combinations of T and F for each column.
		for (int i = 0; i < n; i++) {
			V = new ArrayList<>();
			/**
			 * The outer for loop runs 2^i times. To explain, the first variable, if there are
			 * 3, will be TTTT | FFFF, getting split only once. The second will be TT | FF |
			 * TT | FF, splitting twice, first in half, then in four. 3rd will be T F T F T
			 * F T F, splitting 4 times. The equation for the number of splits is 2^i.
			 */

			for (int j = 0; j < Math.pow(2, i); j++) {
				/**
				 * Each inner loops adds T or F to the list the appropriate number of times. for
				 * the 1st variable it adds T 4 times, then F 4 times. for the 2nd it adds T
				 * twice, then F twice. The outer loops runs twice, resulting in TT FF TT FF.
				 * The equation to determine this number is 2^(n-i-1).
				 */
				for (int k = 0; k < Math.pow(2, n - i - 1); k++) {
					V.add(true);
				}
				for (int k = 0; k < Math.pow(2, n - i - 1); k++) {
					V.add(false);
				}
			}
			// Adds the temporary ArrayList to the main table.
			TT.add(V);
		}
		// Creates a Scanner to read user input.
		Scanner myObj = new Scanner(System.in);
		// Creates an ArrayList to hold the reference label for each column.
		ArrayList<String> topLine = new ArrayList<>();
		// Reads in the name of each variable and adds said name to the reference list.
		for (int i = 1; i <= n; i++) {
			System.out.println("Enter Var. " + i + " Name");
			topLine.add(myObj.nextLine());
		}
		// Creates a list of all possible operators. (I think this can be done in 1 line)
		ArrayList<String> op = new ArrayList<>();
		op.add("-");
		op.add("v");
		op.add("^");
		op.add("xo");
		op.add("->");
		op.add("<->");
		op.add("=");
		int var1I;
		int var2I;
		int opI;
		int opC = 0;
		// Creates a list of the top line names that will be printed.
		// (the way to reference previous operations is different than the printed output)
		ArrayList<String> topLinePrint = new ArrayList<>();
		for (int i = 0; i < topLine.size(); i++) {
			topLinePrint.add(topLine.get(i));
		}
		// This loop does each operation until the user ends the program.
		while (true) {
			// see getVar() line 20
			var1I = getVar(myObj, topLine, 1);
			if (var1I == -1) {
				System.exit(0);
			}
			var2I = getVar(myObj, topLine, 2);
			if (var2I == -1) {
				System.exit(0);
			}
			opI = -1;
			// asks the user for the operator. ( similar to getVar() )
			while (true) {
				System.out.println("Enter Operator.");
				String in = myObj.nextLine();
				// Checks if the input is a valid operator.
				for (int i = 0; i < 7; i++) {
					if (in.equals(op.get(i))) {
						opI = i;
					}
				}
				if (opI >= 0) {
					break;
				}
				System.out.println("Variable not detected. Try Again.");
			}
			V = new ArrayList<>();
			for (int i = 0; i < Math.pow(2, n); i++) {
				// Propositional logic operations.
				if (opI == 0) {
					V.add(!(TT.get(var1I).get(i)));
				} else if (opI == 1) {
					V.add(TT.get(var1I).get(i) || TT.get(var2I).get(i));
				} else if (opI == 2) {
					V.add(TT.get(var1I).get(i) && TT.get(var2I).get(i));
				} else if (opI == 3) {
					V.add(TT.get(var1I).get(i) || TT.get(var2I).get(i));
					if (TT.get(var1I).get(i) && TT.get(var1I).get(i)) {
						V.set(i, false);
					}
				} else if (opI == 4) {
					V.add(true);
					if (TT.get(var1I).get(i) && !TT.get(var2I).get(i)) {
						V.set(i, false);
					}
				} else if (opI == 5){
					V.add(false);
					boolean a = TT.get(var1I).get(i);
					boolean b = TT.get(var2I).get(i);
					if (a && b || !a && !b) {
						V.set(i, true);
					}
				} else {
					V.add(TT.get(var1I).get(i) == TT.get(var2I).get(i));
				}
			}
			opC++;
			TT.add(V);
			// Formats the operation for the column's label in the top line.
			String first;
			String last;
			if (opI == 0) {
				if (topLinePrint.get(var1I).length() > 2) {
					topLinePrint.add("-(" + topLinePrint.get(var1I) + ")");
				} else {
					topLinePrint.add("-" + topLinePrint.get(var1I));
				}
			} else {
				if (topLinePrint.get(var1I).length() > 2) {
					first = "(" + topLinePrint.get(var1I) + ")";
				} else {
					first = topLinePrint.get(var1I);
				}
				if (topLinePrint.get(var2I).length() > 2) {
					last = "(" + topLinePrint.get(var2I) + ")";
				} else {
					last = topLinePrint.get(var2I);
				}
				topLinePrint.add(first + op.get(opI) + last);
			}
			// Creates the name used to reference the new column in future operations.
			topLine.add("op" + opC);
			System.out.println(
					"Operation added to column " + topLinePrint.get(n + opC - 1) + ". (access using op" + opC + ")\n");
			// See printTable() line 42
			printTable(TT, topLinePrint, n);
			// Asks the user if they are done adding to the table.
			System.out.println("End of table? (y/n)");
			String end = myObj.nextLine();
			if (end.equals("y")) {
				break;
			}
		}
		// Closes the Scanner to stop searching for input.
		myObj.close();
	}
}
