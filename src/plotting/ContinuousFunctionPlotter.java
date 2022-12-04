package plotting;

import plotting.Plotter;
import plotting.Graph;

import java.awt.*;

/**
 *
 * Graphable Function
 * Basic interface allowing mathematical functions to talk to the Graph renderer.
 * This class defines the way to draw continuous functions, using the usual mathod, that is:
 * drawing a straight line between two consecutive points at each iteration
 @author Olly Oechsle
*/
public abstract class ContinuousFunctionPlotter extends Plotter {

    public abstract double getY(double x);

    public void plot(Graph graph, Graphics g, int chartWidth, int chartHeight) {
    	

        /**
         * Record the last two points. Plotting works by drawing lines between consecutive points
         * This ensures there are no gaps.
         */
        double prevX = 0, prevY = 0;

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
             * For this value of X, get the value of Y (via the abstract method)
             */
            double y = getY(x);

            /**
             * Draw a line between two points
             */
            if (!first && y <= graph.plotSettings.getMaxY() && y >= graph.plotSettings.getMinY()) 
            	graph.drawLine(g, prevX, prevY, x, y);

            /**
             * Remember the last two values
             */
            prevX = x;
            prevY = y;

            /**
             * To stop the first point being drawn
             */
            first = false;

        }
        
    }

    

}
