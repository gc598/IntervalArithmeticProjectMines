package plotting;

import java.util.function.Function;

public class ContinuousFunctionDrawer extends ContinuousFunctionPlotter{

	/**
	 * This class provides a getY method for the Plotter class, using a functional 
	 * Function<Double,Double> object
	 * @author gabriel
	 */
	
	//continuous function to plot
	protected Function<Double,Double> function;
	
	public ContinuousFunctionDrawer() {
		super();
		this.function = Function.identity();
	}
	
	
	public ContinuousFunctionDrawer(Function<Double, Double> function) {
		super();
		this.function = function;
	}

	@Override
	public double getY(double x) {
		return function.apply(x);
	}

	@Override
	public String getName() {
		return "functionName";
	}

}
