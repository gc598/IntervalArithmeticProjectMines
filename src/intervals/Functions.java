package intervals;
import java.util.function.*;

/**
 * defines interval valued functions, using a functional interface, thanks to the Function object
 * all the functions here defined take one interval as an orgument, and outputs one interval:
 * this is the interval version of real valued, one variable mathematical functions.
 * most usual functions are defined in this class, from trigonometric, to hyperbolic and exponential
 * functions
 * @author gabriel
 *
 */
public class Functions {

	public static final String[] functions = {"-","exp","log","cos","sin","sqrt","tan","cosh",
			"sinh","tanh","acos","asin","atan","acosh","asinh","atanh","arcos","arcsin","arctan",
			"arctanh","arcsinh","arcosh","ctg","cotan","cotanh"};
	private Function<Interval,Interval> func;
	private String desc;
	
	/*
	 * class implementing usual functions, in order to compare the results to the 
	 * to Math java class, which uses classic approximation methods
	 */
	
	/*
	 * constants
	 */
	public static final Functions ID = new Functions(Function.identity(),"x");
	public static final Functions EXP = new Functions((Interval I) -> exp(I),"exp(x)");
	public static final Functions LOG = new Functions((Interval I) -> log(I),"log(x)");
	public static final Functions COS = new Functions((Interval I) -> cos(I),"cos(x)");
	public static final Functions SIN = new Functions((Interval I) -> sin(I),"sin(x)");
	public static final Functions TAN = new Functions((Interval I) -> tan(I),"tan(x)");
	public static final Functions SQRT = new Functions((Interval I)-> sqrt(I),"sqrt(x)");
	public static final Functions INV = new Functions((Interval I) -> {
		try {
			return inv(I);
		} catch (IntervalException e) {
			// if I contains 0, an IntervalException is raised, we return an NaN interval, so the plotter will
			//not plot the result
			return Interval.UNDEFINED;
		}
	},"1/x");
	public static final Functions COSH = new Functions((Interval I) -> 
		Interval.mult(new Interval(0.5,0.5), Interval.add(exp(I), exp(I.neg()))),"cosh(x)");
	public static final Functions SINH = new Functions((Interval I) -> 
		Interval.mult(new Interval(0.5,0.5), Interval.add(exp(I), exp(I.neg()).neg())),"sinh(x)");
	public static final Functions TANH = divide(new Functions(SINH),new Functions(COSH));
	public static final Functions ACOS = new Functions((Interval I) -> acos(I),"acos(x)");
	public static final Functions ASIN = new Functions((Interval I) -> asin(I),"asin(x)");
	public static final Functions ATAN = new Functions((Interval I) -> atan(I),"atan(x)");
	public static final Functions ASINH = new Functions((Interval I) -> asinh(I),"asinh(x)");
	public static final Functions ACOSH = new Functions((Interval I) -> asinh(I),"acosh(x)");
	public static final Functions ATANH = new Functions((Interval I) -> asinh(I),"atanh(x)");
	public static final Functions COTAN = new Functions((Interval I) -> cotan(I),"cotan(x)");
	public static final Functions COTANH = new Functions((Interval I) -> cotanh(I),"cotanh(x)");
	
	/*
	 * constructors
	 */
	
	public Functions(Function<Interval,Interval> func) {
		this.func = func;
		this.desc="";
	}
	
	public Functions(Function<Interval,Interval> func,String desc) {
		this.func = func;
		this.desc=desc;
	}
	
	/*
	 * get, set
	 */
	
	public void setDesc(String d) {
		desc = d;
	}
	
	@Override
	public String toString() {
		return desc;
	}
	

	//copy constructor
	public Functions(Functions f) {
		func = (Interval I) -> f.func.apply(I);
	}

	public Function<Interval, Interval> getFunc() {
		return func;
	}

	/*
	 * constructing methods
	 */

	
	public static Functions comp(Functions f,Functions g) throws IntervalException{
		return new Functions(f.func.compose(g.func));
	}
	
	/**
	 * 
	 * @param a slope of the linear function
	 * @return a linear function of slope a
	 */
	public static Functions multiplication(double a) {
		return new Functions((Interval I)->Interval.mult(I, new Interval(a)));
	}
	
	/**
	 * 
	 * @param a scalar to add to x
	 * @return a function that adds a to its input
	 */
	public static Functions addition(double a) {
		return new Functions((Interval I)->Interval.add(I, new Interval(a)));
	}
	
	/**
	 * 
	 * @param f
	 * @param g
	 * @return the Functions adding f and g
	 */
	public static Functions add(Functions f,Functions g) {
		return new Functions((Interval I) -> Interval.add(f.apply(I),g.apply(I)));
	}
	
	/**
	 * 
	 * @param f
	 * @param g
	 * @return the Functions substracting g from f
	 */
	public static Functions substract(Functions f,Functions g) {
		return new Functions((Interval I)->Interval.substract(f.apply(I), g.apply(I)));
	}
	
	/**
	 * 
	 * @param f
	 * @param g
	 * @return the Functions multiplying f and g
	 */
	public static Functions multiply(Functions f,Functions g) {
		return new Functions( (Interval I) -> Interval.mult(f.apply(I), g.apply(I)));
	}
	
	/**
	 * 
	 * @param f
	 * @param g
	 * @return the Functions dividing f by g
	 */
	public static Functions divide (Functions f,Functions g) {
		return new Functions ( (Interval I) -> {
			try {
				return Interval.div(f.apply(I), g.apply(I));
			} catch (IntervalException e) {
				return new Interval(Double.NaN,Double.NaN);
			}
		});
	}
	/**
	 * 
	 * @param a 
	 * @return a constant function with value a
	 */
	public static Functions constant(double a) {
		return new Functions((Interval I) -> new Interval(a));
	}
	
	public static Functions opposite(Functions f) {
		return new Functions((Interval I) -> f.apply(I).neg());
	}
	
	/**
	 * 
	 * @param g function to raise to the given power
	 * @param n power to raise the interval to
	 * @return a Functions object returning taking a Funcrions as an argument, and raising it to the power n
	 */
	public static Functions pow(Functions f,int n) {
		return new Functions((Interval I) -> Interval.pow(f.apply(I),n));
	}
	
	/**
	 * 
	 * @param a double to substract the interval to
	 * @return a functions taking an interval I as an argument, and returning a - I
	 */
	public static Functions substraction(double a) {
		return new Functions((Interval I)->Interval.substract(new Interval(a),I));
	}
	
	/**
	 * 
	 * @param a number to divide the interval by
	 * @return a Functions object taking an interval I as an argument, and returning a / I
	 */
	public static Functions division(double a) {
		if(a==0)
			return constant(0.0);
		else
			return new Functions((Interval I) -> {
			try {
				return Interval.div(new Interval(a), I);
			} catch (IntervalException e) {
				System.out.println("division by 0");
				return new Interval(0.0);
			}
		});
	}
	
	/*
	 * instance methods and modifiers
	 */
	
	/**
	 * 
	 * @param I interval to apply the function to
	 * @return this.func.apply
	 */
	public Interval apply(Interval I) {
		return func.apply(I);
	}
	
	/**
	 * composes this with fun, the definition interval of the new function being 
	 * the intersection of this.definition and fun.definition
	 * @param fun function to be composed with this.func
	 * @return this composed with fun 
	 */

	public void comp(Functions f) throws IntervalException{
		func = func.compose(f.func);
	}
	
	/*
	 * factory functions implementing usual mathematical functions
	 */
	
	
	protected static Interval exp(Interval I) {
		Interval res = new Interval(Math.exp(I.getLow()),Math.exp(I.getUp()));
		return res;
	}
	
	protected static Interval log(Interval I)  {
			return new Interval(Math.log(I.getLow()),Math.log(I.getUp()));
	}
	
	protected static Interval inv(Interval I) throws IntervalException{
		return I.inv();
	}
	
	protected static Interval sqrt(Interval I) {
		double l = I.getLow(), u = I.getUp();
		if(l<0 && u<0)
			return Interval.UNDEFINED;
		else if(l<0 && u>0)
			return new Interval(0,Math.sqrt(u));
		else
			return new Interval(Math.sqrt(l),Math.sqrt(u));
	}
	

	
	protected static Interval cos(Interval I) {
		double l = I.getLow(),u = I.getUp();
		if(I.getUp()-I.getLow()>=2*Math.PI)
			return new Interval(-1,1);
		double lTrig = l % (2*Math.PI) , uTrig = u % (2*Math.PI);
		if(lTrig<=uTrig) // if both values stand on the same trigonometric circle
			if( (lTrig <=Math.PI && uTrig <=Math.PI) || (lTrig>=Math.PI && uTrig>=Math.PI)) //same side wrt y=PI
				return new Interval(Math.cos(lTrig),Math.cos(uTrig));
			else
				return new Interval(-1.0,Math.max(Math.cos(lTrig), Math.cos(uTrig))); 
		else //uTrig and lTrig don't stand on the same trigonometric circle
			return new Interval(Math.min(Math.cos(lTrig), Math.cos(uTrig)),1.0);
	}
	
	
	
	protected static Interval sin(Interval I) {
		if(I.getUp()-I.getLow()>=2*Math.PI)
			return new Interval(-1,1);
		//values on the same trigonometric circle
		double lTrig = I.getLow()%(2*Math.PI),uTrig = I.getUp()%(2*Math.PI);
		//let's set the zero of the trigonometric circle where pi/2 usually stands, to simplify
		double l = lTrig>=Math.PI/2?lTrig-Math.PI/2:lTrig+3*(Math.PI/2);
		double u = uTrig>=Math.PI/2?uTrig-Math.PI/2:uTrig+3*(Math.PI/2);
		
		if ((l<=Math.PI && u<=Math.PI) || (l>=Math.PI && u>=Math.PI))//if on same side wrt line x = 0
			return new Interval(Math.sin(lTrig),Math.sin(uTrig));
		else
			if( (l<=Math.PI && u>=Math.PI))
				return new Interval(-1.0,Math.max(Math.sin(lTrig), Math.sin(uTrig)));
			else
				return new Interval(Math.min(Math.sin(lTrig), Math.sin(uTrig)),1.0);
		//l>=Math.PI && u<=Math.PI)
	}
	
	protected static Interval tan(Interval I) {
		double low=I.getLow(), up = I.getUp();
		if(up-low >=Math.PI)
			return Interval.UNDEFINED;
		if(up % (Math.PI/2)==0.0)
			return Interval.UNDEFINED;
		if(low%(Math.PI/2)==0.0)
			return Interval.UNDEFINED;
		return new Interval(Math.tan(low),Math.tan(up));
					
	}
	
	protected static Interval asin(Interval I) {
		return new Interval(Math.asin(I.getLow()),Math.asin(I.getUp()));
	}
	
	protected static Interval acos(Interval I) {
		return new Interval(Math.acos(I.getUp()),Math.acos(I.getLow()));
	}
	
	protected static Interval atan(Interval I) {
		return new Interval(Math.atan(I.getLow()),Math.atan(I.getUp()));
	}

	protected static Interval asinh(Interval I) {
		double l = I.getLow(),u = I.getUp();
		return new Interval(Math.log(l+Math.sqrt(Math.pow(l, 2)+1)),Math.log(u+Math.sqrt(Math.pow(u, 2)+1)));
	}
	
	protected static Interval acosh(Interval I) {
		double l = I.getLow(),u = I.getUp();
		return new Interval(Math.log(l+Math.sqrt(Math.pow(l, 2)-1)),Math.log(u+Math.sqrt(Math.pow(u, 2)-1)));
	}
	
	protected static Interval atanh(Interval I) {
		double l = I.getLow(),u = I.getUp();
		try {
			return new Interval(0.5*Math.log((l+1)/(l-1)),0.5*Math.log((u+1)/(u-1)));
		}
		catch(ArithmeticException e) {
			return Interval.UNDEFINED;
		}
	}
	
	protected static Interval cotan(Interval I) {
		double low=I.getLow(), up = I.getUp();
		if(up-low >=Math.PI)
			return Interval.UNDEFINED;
		if(up % (Math.PI)==0.0)
			return Interval.UNDEFINED;
		if(low%(Math.PI)==0.0)
			return Interval.UNDEFINED;
		return new Interval(1.0/Math.tan(up),1.0/Math.tan(low));
	}
	
	protected static Interval cotanh(Interval I) {
		double low = I.getLow(), up=I.getUp();
		if(up>=0 && low<=0)
			return Interval.UNDEFINED;
		else if(low >= 0)
			return new Interval(1.0/Math.tanh(up),1.0/Math.tanh(low));
		else
			return new Interval(1.0/Math.tanh(low),1.0/Math.tanh(up));
	}
}
