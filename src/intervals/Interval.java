package intervals;


public class Interval {

/**
 * this class defines the interval object, that will be used to implement interval valued 
 * functions. As such, this class implements the usual arithmetic operations, but performs them
 * over intervals (that is: a lower bound an upper bound)
 */
	
	public final static Interval R = new Interval(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
	public final static Interval RPOS = new Interval(0,Double.POSITIVE_INFINITY);
	public static final Interval UNDEFINED = new Interval(Double.NaN,Double.NaN);
	
	/**
	 * CLASS ATTRIBUTES
	 * an upper bound and a lower bound, might take finite or infinite values
	 */
	
	double low,up;

	public Interval(double low, double up){
		double lowTmp = Math.min(low, up);
		double upTmp = Math.max(low, up);
		this.low = lowTmp;
		this.up = upTmp;
	}
	
	/**
	 * generates a real valued number, ie an interval with both bounds equal
	 * @param a
	 */
	public Interval(double a) {
		low = a;
		up=a;
	}
	
	public Interval() {
		this(0.0);
	}

	public double getLow() {
		return low;
	}

	public double getUp() {
		return up;
	}
	
	@Override
	public String toString() {
		return "["+low+","+up+"]";
	}
	
	/**
	 * FACTORY STATIC METHODS AND FUNCTIONS FOR INTERVAL ARITHMETIC OPERATIONS
	 */
	
	/**
	 * 
	 * @param i1
	 * @param i2
	 * @return the sum of the two given intervals using the adition formula:
	 * [l1,u1] + [l2,u2] = [l1+l2,u1+u2]
	 */
	public static Interval add(Interval i1,Interval i2) {
		return new Interval(i1.low+i2.low,i1.up+i2.up);
	}
	
	/**
	 * 
	 * @param i1
	 * @param i2
	 * @return the product of the two given intervals, using the multiplication 
	 * formula for intervals: [x,y] x [a,b] = 
	 * [min (xa,xb,ya,yb) , max(xa,xb,ya,yb) ]
	 */
	
	/**
	 * 
	 * @param i1
	 * @param i2
	 * @return the interval i1-i2 using the formula:
	 * [x,y] - [a,b] = [x,y] + (-[a,b])
	 */
	public static Interval substract(Interval i1,Interval i2) {
		return add(i1,i2.neg());
	}
	
	public static Interval mult(Interval i1,Interval i2) {
		double min = i1.low*i2.low, max = i2.up*i1.up;
		double[] arr1 = {i1.low,i1.up};
		double[] arr2 = {i2.low,i2.up};
		for(int i=0;i<arr1.length;i++)
			for(int j=0;j<arr2.length;j++) {
				double tmp = arr1[i]*arr2[j];
				if(tmp > max)
					max = tmp;
				if(tmp < min)
					min = tmp;
			}
 		return new Interval(min,max);
	}
	
	/**
	 * 
	 * @param i1
	 * @param i2
	 * @return the division of i1 by i2, using the formula: 
	 * [x,y] / [a,b] = [x,y] x (1 / [a,b])
	 * @throws IntervalException
	 */
	
	public static Interval div(Interval i1,Interval i2) throws IntervalException{
		return mult(i1,i2.inv());
	}
	
	/**
	 * 
	 * @param i1
	 * @param i2
	 * @return the union of the two specified intervals, unless they don't 
	 * intersect
	 * @throws IntervalException if the interval don't intersect
	 */
	public static Interval biUnion(Interval i1,Interval i2) 
	throws IntervalException {
		if(i1.up < i2.low || i2.up < i1.low)
			throw new IntervalException("the union of" + 
		i1.toString()+" and "+i2.toString()+" is not an interval");
		return new Interval(Math.min(i1.low, i2.low),Math.max(i1.up, i2.up));
	}
	
	/**
	 * 
	 * @param i1
	 * @param i2
	 * @return the intersection of i1 and i2
	 */
	public static Interval intersect(Interval i1,Interval i2) throws IntervalException {
		double l = Math.max(i1.low, i2.low);
		double up = Math.min(i1.up, i2.up);
		if(up<l)
			throw new IntervalException("empty intersection");
		else
			return new Interval(l,up);
	}
	
	
	/**
	 * FUNCTIONS AND METHODS FOR INTERVAL MANIPULATION
	 */
	
	/**
	 * 
	 * @return the inverse of the current interval, according to the formula:
	 * 1 / [x,y] = 
	 * 				[1/x,1/y] if 0 not in [x,y]
	 * 				[-infinity , 1/x] if y  = 0
	 * 				[1/y, infinity]  if x = 0
	 * 				[-inf,1/x] U [1/y,inf] = [-inf,+inf] = R , otherwise	 
	 */
	
	public Interval inv() throws IntervalException{
		if(low==0.0 && up==low)
			throw new IntervalException("impossible to invert interval [0,0]");
		if(low<0 && up > 0)
			return new Interval(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
		else if(low==0.0 && up>low)
			return new Interval(1.0/up,Double.POSITIVE_INFINITY);
		else if(up==0.0 && low < up)
			return new Interval(Double.NEGATIVE_INFINITY,1.0/low);
		else
			return new Interval(1.0/up,1.0/low);
	}
	
	/**
	 * 
	 * @return the sama result as inv, except fot the case of intervals 
	 * strictly containing 0, where [-inf,1/x] is returned
	 * @throws IntervalException
	 */
	
	public Interval invLeft() throws IntervalException{
		if(low<0 && up >0)
			return new Interval(Double.NEGATIVE_INFINITY,1.0/low);
		else 
			return inv();
	}
	
	/**
	 * 
	 * @return the sama result as inv, except fot the case of intervals 
	 * strictly containing 0, where [1/y,inf] is returned
	 * @throws IntervalException
	 */
	
	public Interval invRight() throws IntervalException{
		if(low<0 && up >0)
			return new Interval(1.0/up,Double.POSITIVE_INFINITY);
		else 
			return inv();
	}
	
	/**
	 * 
	 * @return the opposite of the current interval using the formula:
	 * -[x,y] = [-y,-x]
	 */
	
	/**
	 * 
	 * @param I interval to be raised to the power n
	 * @param n integer power to raise I to
	 * @return I^n
	 */
	protected static Interval pow(Interval I,int n) {
		double low,up;
		if(n%2==1) {
			low = Math.pow(I.getLow(), n);
			up = Math.pow(I.getUp(), n);
		}
		else {
			if(I.getLow()>=0) {
				low = Math.pow(I.getLow(), n);
				up = Math.pow(I.getUp(), n);
			}
			else if(I.getUp()<0) {
				low = Math.pow(I.getUp(),n);
				up = Math.pow(I.getLow(), n);
			}
			else {
				low = 0;
				up = Math.max(Math.pow(I.getLow(), n),Math.pow(I.getUp(), n));
			}
				
		}
		return new Interval(low,up);
	}
	public Interval neg() {
		return new Interval(-up,-low);
	}
	
	/**
	 * 
	 * @param a slope interval
	 * @param b intercept interval
	 * @return apply an affine function to the current interval, given its 
	 * interval valued slope and intercept, using the simple formula:
	 * f(a,b,x) = a * x + b
	 */
	public Interval applyAffineFunction(Interval a,Interval b) {
		return add(mult(a,this),b);
	}
	
	public Interval intersect(Interval i) throws IntervalException{
		return Interval.intersect(this, i);
	}
}
