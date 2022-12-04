package plotting;

import intervals.Functions;
import intervals.Interval;

public class IntervalFunctionDrawer extends IntervalFunctionPlotter {

	/**
	 * this class extends IntervalFunctionPlotter, and gives it a real interval valued function to plot
	 @author gabriel
	 */
	
	//function to plot, with type Functions
	protected Functions function;
	
	/**
	 * constructors
	 */
	
	public IntervalFunctionDrawer() {
		this.function = Functions.ID;
	}
	
	public IntervalFunctionDrawer(Functions f) {
		this.function = f;
	}
	
	@Override
	public Interval getIm(Interval I) {
		// simply applies the function to get its interval result
		return function.apply(I);
	}

	//TODO
	@Override
	public String getName() {
		return "functionName";
	}

}
