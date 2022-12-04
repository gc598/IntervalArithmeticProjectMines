package plotting;

import java.util.function.Function;

public class ContinuousFunctions {

	/**
	 * library of continuous functions, using the type Function<Double,Double>
	*/
	
	//constants
	public static final Function<Double,Double> ID = Function.identity();
	public static final Function<Double,Double> EXP = (Double x) -> Math.exp(x);
	public static final Function<Double,Double> LOG = (Double x) -> Math.log(x);
	public static final Function<Double,Double> COS = (Double x) -> Math.cos(x);
	public static final Function<Double,Double> SIN = (Double x) -> Math.sin(x);
	public static final Function<Double,Double> TAN = (Double x) -> Math.tan(x);
	public static final Function<Double,Double> ASIN = (Double x) -> Math.asin(x);
	public static final Function<Double,Double> ACOS = (Double x) -> Math.acos(x);
	public static final Function<Double,Double> ATAN = (Double x) -> Math.atan(x);
	public static final Function<Double,Double> COSH = (Double x) -> Math.cosh(x);
	public static final Function<Double,Double> SINH = (Double x) -> Math.sinh(x);
	public static final Function<Double,Double> TANH = (Double x) -> Math.tanh(x);
	public static final Function<Double,Double> ASINH = (Double x) -> Math.log(x+Math.sqrt(Math.pow(x,2)+1));
	public static final Function<Double,Double> ACOSH = (Double x) -> Math.log(x+Math.sqrt(Math.pow(x,2)-1));
	public static final Function<Double,Double> ATANH = (Double x) -> 0.5*Math.log((1+x)/(1-x));
	public static final Function<Double,Double> SQRT = (Double x) -> Math.sqrt(x);
	public static final Function<Double,Double> INV = (Double x) -> {
		try {
			return 1/x;
		}
		catch (ArithmeticException e) {
			return Double.NaN;
		}
	};
	public static final Function<Double,Double> COTANH = (Double x) -> 1.0/Math.tanh(x);
	public static final Function<Double,Double> COTAN = (Double x) -> Math.tan(x);
	
	/**
	 * constructing methods
	 */
	
	/**
	 * 
	 * @param a
	 * @return the constant function a
	 */
	public static Function<Double,Double> constant(double a) {
		return (Double x) -> a;
	}
	
	/**
	 * 
	 * @param f
	 * @param g
	 * @return the function f+g
	 */
	public static Function<Double,Double> add(Function<Double,Double> f,Function<Double,Double> g){
		return (Double x) -> f.apply(x)+g.apply(x);
	}
	
	/**
	 * 
	 * @param f
	 * @param g
	 * @return the function f*d
	 */
	public static Function<Double,Double> multiply(Function<Double,Double> f,Function<Double,Double> g){
		return (Double x) -> f.apply(x)*g.apply(x);
	}
	
	/**
	 * 
	 * @param f
	 * @param g
	 * @return the function f-d
	 */
	public static Function<Double,Double> substract(Function<Double,Double> f,Function<Double,Double> g){
		return (Double x) -> f.apply(x)-g.apply(x);
	}
	
	/**
	 * 
	 * @param f
	 * @param g
	 * @return the function f/g
	 */
	public static Function<Double,Double> divide(Function<Double,Double> f,Function<Double,Double> g){
			return (Double x) -> {
				try {
					return f.apply(x)/g.apply(x);
				}
				catch (ArithmeticException e) {
					return Double.NaN;
				}
			};
	}
	
	/**
	 * 
	 * @param f
	 * @param g
	 * @return the function f^g
	 */
	public static Function<Double,Double> pow(Function<Double,Double> f,Function<Double,Double> g){
		return (Double x) -> Math.pow(f.apply(x), g.apply(x));
	}
	


}
