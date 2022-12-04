package intervals;

/**
 * class used mainly to manage possible errors due to the use of intervals, 
 * as defined by the Interval class
 * @author gabriel
 *
 */

public class IntervalException extends Exception {


	private static final long serialVersionUID = 1L;
	private String message;

	public IntervalException(String message) {
		super();
		this.message = message;
	}
	
	public String toString() {
		return "IntervalException: "+message;
	}
}
