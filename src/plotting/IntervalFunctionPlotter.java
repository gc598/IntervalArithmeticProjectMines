package plotting;

import java.awt.Graphics;

import intervals.Interval;

public abstract class IntervalFunctionPlotter extends Plotter{

	/**
	 * @param I, interval whose image will be computed and plotted
	 * @return the interval image of the function to plot, for a certain Interval input I
	 * @author gabriel
	 */
	public abstract Interval getIm(Interval I); 
	
	public void plot(Graph graph, Graphics g, int chartWidth, int chartHeight) {
		
		
		/**
         * Record the last point
         */
        double prevX = 0;
		
        /**
         * Flag to make sure the first point is not drawn (there is no previous point to connect the dots to)
         */
        boolean first = true;

        double xRange = graph.plotSettings.getRangeX();

        /**
         * Plot for every pixel going across the chart
         */
        for (int ax = 0; ax < chartWidth; ax+=graph.plotSettings.getPlottingStep()) {

            // figure out what X is
            double x = graph.plotSettings.getMinX() + ((ax / (double) chartWidth) * xRange);

            /**
             * For this interval value of [prevX,x], get the interval image [prevY,y]
             */
            
            Interval I = getIm(new Interval(prevX,x));
            	//System.out.println("x: "+new Interval(prevX,x) + "  y: "+I + " "+Math.PI/2);
            double yLow = I.getLow();
            double yUp = I.getUp();

            /**
             * Draw a rectangle between product of the two intervals
             */
            if (!first && yUp <= graph.plotSettings.getMaxY() && yLow >= graph.plotSettings.getMinY()) 
            	graph.drawRectangle(g, prevX, x, yLow, yUp);
            
            
            prevX = x;
            /**
             * To stop the first point being drawn
             */
            first = false;

        }
	}
}






