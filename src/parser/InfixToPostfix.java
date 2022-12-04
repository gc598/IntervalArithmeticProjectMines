package parser;

import java.util.Arrays;

import intervals.Functions;

/**
 * defines methods to convert an infix expression to a postfix one
 * @author gabriel
 *
 */
public class InfixToPostfix {
	
	/**
	 * 
	 * @param input infix string to translate to postfix
	 * @return postfix version of the input
	 * @throws PileVideException 
	 */
	public static String infixToPostfix(String input) throws PileVideException {
		Pile<String> stack = new Pile<String>();
		String[] ex = infixToArray(input);
		String output = "";
		
		
		for(int i=0;i<ex.length;i++) {
			 String s = ex[i];
			 switch (s) {
	            // call processOper with precedence 1 for + and - operator
			 	case "+": 
	            case "-":
	            	try {
	            		String sPrec = ex[i-1];
	            		//if the preceding character is ( or empty, then - should be 
	            		//interpreted as a unitary operator
	            		if(sPrec.equals("("))
	            			throw new IndexOutOfBoundsException();
	            		output = processOper(s, 1,stack,output); 
	            	}
	            	catch (IndexOutOfBoundsException e) {
	            		stack.empiler(s);
	            	}
	               break; 
	            // call to processOper with precedence 2 for / and *
	            case "*": 
	            case "/":
	               output = processOper(s, 2,stack,output); 
	               break; 
	            case "^":
	            	//call to processOper with precedence 3 for powers
	            	output = processOper(s,3,stack,output);
	            	break;
	            case "(": 
	               stack.empiler(s);
	               break;
	            case ")": 
	               output = processParen(s,stack,output); 
	               break;
	            default: 
	               if(Arrays.asList(Functions.functions).contains(s)) {
	            	   stack.empiler(s);
	               }
	               else {
		               output = output + " "+s; 
		               break;
	               }
	         }
        }
	    while (!stack.estVide()) {
	        output = output + " "+stack.depiler();
	    }
		return output;
	}
	
	private static String processOper(String currentOper,int prec,Pile<String> stack,String currentEx) 
	throws PileVideException{
		  String output = currentEx;
	      while (!stack.estVide()) {
	          String opTop = stack.depiler();
	          if (opTop.equals("(")) {
	             stack.empiler(opTop);
	             break;
	          } else {
	             int prec2;
	             if (opTop.equals("+") || opTop.equals("-"))
	             	prec2 = 1;
	             else if(opTop.equals("*") || opTop.equals("/"))
	             	prec2 = 2;
	             else
	             	prec2 = 3;
	             if (prec2 < prec) { 
	                stack.empiler(opTop);
	                break;
	             } 
	             else output = output + " "+opTop;
	          }
	       }
	       stack.empiler(currentOper);		
	       return output;
	}
	
	private static String processParen(String currentOper,Pile<String> stack,String currentEx) 
			throws PileVideException {
		  String output = currentEx;
	      while (!stack.estVide()) {
	          String s = stack.depiler();
	          if (s.equals("(")) 
	        	  break; 
	          else 
	        	  output = output + " "+ s;
	       }
	      return output;
	}
	
	/**
	 * 
	 * @param input infix string to translate to an array containing the algebraic elements
	 * @return an array containing the algebraic elements of the infix inpus string, e.g. 
	 * 2*exp(x+3.1) -> [2 , * , exp , ( , x , + , 3.1 , ) ]
	 */
	public static String[] infixToArray(String input) {
		String ex = input;
		String[] exArr;
		
		//add spaces around each algebraic element
		ex = ex.replaceAll("\\("," \\( ");
		ex = ex.replaceAll("\\)"," \\) ");
		ex = ex.replaceAll("\\+"," \\+ ");
		ex = ex.replaceAll("\\-"," \\- ");
		ex = ex.replaceAll("\\*"," \\* ");
		ex = ex.replaceAll("/"," / ");
		ex = ex.replaceAll("\\^"," \\^ ");
	
		//replaces . by , to match the French format
		ex = ex.replaceAll("\\.", "\\,");
		
		//split String ex using spaces as a separator
		exArr = ex.split("[\\s]+");
		return exArr;
	}

	
	public static void main(String[] args) throws PileVideException {
	      String input = "x^2-x^3";
	      String output = infixToPostfix(input);
	      System.out.println(output);
	      

	}
	

}
