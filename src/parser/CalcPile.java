// ----------------------------------------------
//    Algorithmique et programmation en Java
// Ecole Nationale Supérieure des Mines de PARIS
//      Cours d'informatique -  1ère année
// ----------------------------------------------
//                Exercice sur les piles
// Simulation d'une calculette en notation polonaise inverse
// ----------------------------------------------
package parser;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner; // pour les saisies
import java.util.function.Function;

import intervals.Functions;
import intervals.Interval;
import intervals.IntervalException;
import plotting.ContinuousFunctions;

public class CalcPile {
  
	/**
	 * defines methods to convert a postfix expression, obtained via the function.txt file,
	 *  to Function<Double,Double> and Functions 
	 * java 8 objects, as defined in the intervals package, and that can be used to
	 * be plotted on a graph, as provided by the plotting package
	 * 
	 * NOTE THE FOLLOWING WHENEVER ADDING FUNCTIONS:
	 * 
	 * when adding a new operation (unitary or binary), always add it to the following constant arrays, and to
	 * the functions effectuerOperation, and effectuerOperationFunctions (binary) or effectuerOperationUni and
	 * effectuerOperationUniFunctions (unitatry)
	 * @author gabriel
	 */
	
	
  public static final String[] ops = {"*","+","-","/"};

  
  /**
   * 
   * @param function, current stack of functions (interval version)
   * @param contFunction current stack of functions (continuous version)
   * @param op String representing the binary operation being carried out over literal expressions and/or numbers
   * @throws PileVideException
   * @throws IntervalException
   */
  public static void effectuerOperationUniFunctions(Pile<Functions> function,Pile<Function<Double,Double>>
  contFunction,String op) 
  throws PileVideException,IntervalException {
	  Functions f = function.depiler();
	  Function<Double,Double> fCont = contFunction.depiler();
	  Functions res = new Functions(Functions.ID);
	  Function<Double,Double> resCont = ContinuousFunctions.ID;
	  switch(op){
	  case "-":
		  res = Functions.opposite(f);
		  resCont = (Double d) -> -fCont.apply(d);
		  break;
	  case "log":
		  res = Functions.comp(Functions.LOG, f);
		  resCont = ContinuousFunctions.LOG.compose(fCont);
		  break;
	  case "exp":
		  res = Functions.comp(Functions.EXP, f);
		  resCont = ContinuousFunctions.EXP.compose(fCont);
		  break;
	  case "cos":
		  res = Functions.comp(Functions.COS,f);
		  resCont = ContinuousFunctions.COS.compose(fCont);
		  break;
	  case "sin":
		  res = Functions.comp(Functions.SIN, f);
		  resCont = ContinuousFunctions.SIN.compose(fCont);
		  break;
	  case "tan":
		  res = Functions.comp(Functions.TAN, f);
		  resCont = ContinuousFunctions.TAN.compose(fCont);
		  break;
	  case "cosh":
		  res = Functions.comp(Functions.COSH, f);
		  resCont = ContinuousFunctions.COSH.compose(fCont);
		  break;
	  case "sinh":
		  res = Functions.comp(Functions.SINH, f);
		  resCont = ContinuousFunctions.SINH.compose(fCont);
		  break;
	  case "tanh":
		  res = Functions.comp(Functions.TANH, f);
		  resCont = ContinuousFunctions.TANH.compose(fCont);
		  break;
	  case "sqrt":
		  res = Functions.comp(Functions.SQRT, f);
		  resCont = ContinuousFunctions.SQRT.compose(fCont);
		  break;
	  case "arctan":
	  case "atan":
		  res = Functions.comp(Functions.ATAN, f);
		  resCont = ContinuousFunctions.ATAN.compose(fCont);
		  break;
	  case "arcos":
	  case "acos":
		  res = Functions.comp(Functions.ACOS, f);
		  resCont = ContinuousFunctions.ACOS.compose(fCont);
		  break;
	  case "arcsin":
	  case "asin":
		  res = Functions.comp(Functions.ASIN, f);
		  resCont = ContinuousFunctions.ASIN.compose(fCont);
		  break;
	  case "asinh":
	  case "arcsinh":
		  res = Functions.comp(Functions.ASINH, f);
		  resCont = ContinuousFunctions.ASINH.compose(fCont);
		  break;
	  case "acosh":
	  case "arcosh":
		  res = Functions.comp(Functions.ACOSH, f);
		  resCont = ContinuousFunctions.ACOSH.compose(fCont);
		  break;
	  case "atanh":
	  case "arctanh":
		  res = Functions.comp(Functions.ATANH, f);
		  resCont = ContinuousFunctions.ATANH.compose(fCont);
		  break;
	  case "cotan":
	  case "ctg":
		  res = Functions.comp(Functions.COTAN, f);
		  resCont = ContinuousFunctions.COTAN.compose(fCont);
		  break;	  
	  case "cotanh":
			  res = Functions.comp(Functions.COTANH, f);
			  resCont = ContinuousFunctions.COTANH.compose(fCont);
			  break;
	  default:
		  System.out.println("unitary expression does not exist");
		  function.empiler(f);
		  contFunction.empiler(fCont);
		  break;
	  }
	  function.empiler(res);
	  contFunction.empiler(resCont);
  }
  
  
  public static boolean effectuerOperationFunctions(Pile<Functions> function,
		  Pile<Function<Double,Double>> contFunction,String op) 
		  throws PileVideException,IntervalException{
	  Functions f = function.depiler();
	  Functions g = function.depiler();
	  Function<Double,Double> fCont = contFunction.depiler();
	  Function<Double,Double> gCont = contFunction.depiler();
	  Functions res;
	  Function<Double,Double> resCont;
	  switch(op) {
	    case "+":
	        res = Functions.add(f, g);
	        resCont = ContinuousFunctions.add(fCont, gCont);
	        break;
	      case "-":
	        res = Functions.substract(g, f);
	        resCont = ContinuousFunctions.substract(gCont, fCont);
	        break;
	      case "*":
	      	res = Functions.multiply(f,g);
	      	resCont = ContinuousFunctions.multiply(fCont, gCont);
	      	break;
	      case "/":
	    	res = Functions.divide(g, f);
	    	resCont = ContinuousFunctions.divide(gCont, fCont);
	      	break;
	      default:
	        System.out.println("Erreur : Opérateur inconnu...");
	        // On rempile les opérandes avant de sortir de la fonction :
	        function.empiler(g);
	        function.empiler(f);
	        contFunction.empiler(gCont);
	        contFunction.empiler(fCont);
	        return false;
	      }
	      // On empile le résultat et on affiche
	      function.empiler(res);
	      contFunction.empiler(resCont);
	      return true;	  
	 
	  }

  public static void effectuerOperationPow(Pile<Functions> function,Pile<Function<Double,Double>> contFunction) 
		  throws PileVideException,IntervalException {
	  Functions pow = function.depiler();
	  Functions f = function.depiler();
	  Function<Double,Double> contPow = contFunction.depiler();
	  Function<Double,Double> fCont = contFunction.depiler();
	  double d = pow.apply(new Interval(0)).getLow();
	  int n = (int)(d);
	  if(n!=d)
		  throw new IntervalException("power operation impossible");
	  function.empiler(Functions.pow(f, n));
	  contFunction.empiler(ContinuousFunctions.pow(fCont, contPow));
  }
  
  
  public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
  
  
  
  public static Object[] parseFunctionFile(File file) throws 
  PileVideException,IntervalException, FileNotFoundException{
	  
	  Scanner sc = new Scanner(file);
	  Pile<Functions> function = new Pile<Functions>(); //stack of interval valued functions
	  // stack of classical double valued functions
	  Pile<Function<Double,Double>> contFunction = new Pile<Function<Double,Double>>();
	  Object[] res = new Object[2];
	  double next = 0.0;
	        do {
	        	//reads every number, x letter, and operator in the file
	          if (sc.hasNextDouble()) { 
	            //If a number is read, then push the appropriate constant function onto the stack
	        	next = sc.nextDouble();
	            function.empiler(Functions.constant(next));
	            contFunction.empiler(ContinuousFunctions.constant(next));
	            
	          } else {
	        	  // if neither a number nor x, the it must be an operator
	        	String s = sc.next();   //reads the next word
	            switch (s) {
	            case "x":
	            	function.empiler(Functions.ID);
	            	contFunction.empiler(ContinuousFunctions.ID);
	            	break;
	            case "-":
	            	if(function.renvoyerHauteur()==2) {
		                effectuerOperationFunctions(function,contFunction,s);
	            	}
	            	else if(function.renvoyerHauteur()==1) {
		                  effectuerOperationUniFunctions(function,contFunction, s);
	            	}
	            	else {
	            		System.out.println("wrong use of the - operator");
	            	}
	            	break;
	            case "+":
	            case "*":
	            case "/":
	              // checks whether the stack contains two operands
	              if (function.renvoyerHauteur() < 2) {
	            	  System.out.println("cannot apply binary operator to two operands");
	              } else {
	                effectuerOperationFunctions(function,contFunction,s);
	              }
	              break;
	            case "^":
		              if (function.renvoyerHauteur() < 2) {
			                System.out.println("misses an operand to apply the power "+ "binary operator");
			              } else {
			            	  effectuerOperationPow(function,contFunction);
			              }
	            	break;
	            default:
	              if(Arrays.asList(Functions.functions).contains(s)){
	                if(function.renvoyerHauteur()<1)
	                  System.out.println("number, or x expected");
	                else{
	                  effectuerOperationUniFunctions(function,contFunction, s);
	                }
	              }
	              else
	                System.out.printf("Error: unknown operator", s);
	            } // end of Switch
	          } //end of situation where we are not dealing with a number
	        } while (sc.hasNext());
	        sc.close();
	        if(function.renvoyerHauteur()==1) {
	        	res[0] = function.depiler();
	        	res[1] = contFunction.depiler();
	        	return res;
	        }	
	        else
	        	throw new PileVideException();
  }
  
 
  
  

  public static void main(String[] args) throws PileVideException, IOException {


  
  }

}
