package plotting;

import java.util.function.Function;

public class FunctionDrawer extends ContinuousFunctionPlotter {

	/**
	factory class used to define continuous usual functions
	@author gabriel
	*/
	
	//function to plot
	protected Function<Double,Double> function;
	
	/**
	 * constructors
	 */
	
	public FunctionDrawer() {
		this.function = Function.identity();
	}
	
	public FunctionDrawer(Function<Double,Double> f) {
		this.function = f;
	}
	
	@Override
	public double getY(double x) {
		return function.apply(x);
	}

	//TODO
	@Override
	public String getName() {
		return "functionName";
	}

}
