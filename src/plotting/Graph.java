package plotting;

import plotting.Plotter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * <p/>
 * Graph.java<br />
 * Renders graphs using the Java 2D API
 * </p>
 * <p/>
 * All customizable settings are defined in PlotSettings.java
 * </p>
 * <p/>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation;
 * provided that any use properly credits the author.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details at http://www.gnu.org
 * </p>
 *
 * @author Olly Oechsle
 */

public class Graph {

    public static final String VERSION = "Java Plot 1.1";
    
    /**
     * colors to plot the functions
     */
    public ArrayList<Color> functionColors;
    
    /**
     * A graph may plot as many functions as it wants
     * These may all be of different types.
     */
    public ArrayList<Plotter> functions;

    /**
     * The area and general settings of the graph are all defined
     * by a PlotArea object.
     */
    
    /**
     * the names of the functions, to be printed in the top right corner, using the appropriate color
     */
    public ArrayList<String> functionNames;
    
    public PlotSettings plotSettings;

    /**
     * Initialises the graph with the plot settings to use.
     */
    public Graph(PlotSettings p) {
        this.functions = new ArrayList<Plotter>(Math.min(p.getMaxNbFunctions(),p.possibleColors.length));
        this.functionColors = new ArrayList<Color>(Math.min(p.getMaxNbFunctions(),p.possibleColors.length));
        this.functionNames = new ArrayList<String>(Math.min(p.getMaxNbFunctions(),p.possibleColors.length));
        this.plotSettings = p;
    }

    protected double plotRangeX, plotRangeY;

    /**
     * How many pixels are there available to use in the graph?
     * This is the size of the image minus the border size.
     */
    protected int chartWidth, chartHeight;

    protected double unitsPerPixelX, unitsPerPixelY;

    /**
     * Draws the graph using a graphics object.
     *
     * @param g      The graphics context on which to draw
     * @param width  The width to make the graph
     * @param height The height to make the graph
     */
    public void draw(Graphics g, int width, int height) {

        
    	/**
         * Draw the title
         */
    	
        if (plotSettings.title != null) {
            g.setColor(plotSettings.fontColor);
            // ensure the border top can accommodate the title
            if (plotSettings.marginTop < g.getFontMetrics().getHeight() + 20) {
                plotSettings.marginTop = g.getFontMetrics().getHeight() + 20;
            }
            int titleXPosition = (width / 2) - ((g.getFontMetrics().stringWidth(plotSettings.title)) / 2);
            g.drawString(plotSettings.title, titleXPosition, 10 + g.getFontMetrics().getHeight());
        }
        

        /**
         * Calculate the plot range
         */

        plotRangeX = Math.abs(plotSettings.maxX - plotSettings.minX);
        plotRangeY = Math.abs(plotSettings.maxY - plotSettings.minY);

        /*
           First we need to know how many pixels there are across the panel
           And we can divide that number between the range that we've been assigned.
        */

        chartWidth = width - (plotSettings.marginLeft + plotSettings.marginRight);
        chartHeight = height - (plotSettings.marginTop + plotSettings.marginBottom);

        /*
           Calculate the number of units per pixel
        */

        unitsPerPixelX = plotRangeX / chartWidth;
        unitsPerPixelY = plotRangeY / chartHeight;

        /**
         * Set the background colour
         */
        g.setColor(plotSettings.backgroundColor);
        g.fillRect(plotSettings.marginLeft, plotSettings.marginTop, chartWidth - 1, chartHeight - 1);


        /**
         * Draw X Axis Notches
         */
        double firstGridXLocation = ((int) (plotSettings.getMinX() / plotSettings.getGridSpacingX())) * plotSettings.getGridSpacingX();

        for (double px = firstGridXLocation; px <= plotSettings.getMaxX(); px += plotSettings.getGridSpacingX()) {

            if (px < plotSettings.getMinX()) continue;

            // find the position of each point and draw a line
            int plotX = getPlotX(px);

            int plotY = plotSettings.marginTop + chartHeight;

            // vertical grid lines
            if (plotSettings.verticalGridVisible) {
                g.setColor(plotSettings.gridColor);
                g.drawLine(plotX, plotSettings.marginTop, plotX, plotY);
            }

            // and draw a notch on the X axis.
            g.setColor(plotSettings.axisColor);
            g.drawLine(plotX, plotY, plotX, plotY + plotSettings.notchLength);

            // work out the value at this point and draw
            String value;
            int labelXPosition;

            
            value = plotSettings.numberFormatter.format(px);
            labelXPosition = plotX - (g.getFontMetrics().stringWidth(value)) / 2;


            // draw the value underneath the notch
            g.setColor(plotSettings.fontColor);
            g.drawString(value, labelXPosition, 
            		plotY + plotSettings.notchLength + 
            		g.getFontMetrics().getHeight() - 1 + plotSettings.notchGap);

        }

        /**
         * Draw Y Axis Notches and Labels
         */
        double firstGridYLocation = ((int) (plotSettings.getMinY() / plotSettings.getGridSpacingY())) * plotSettings.getGridSpacingY();

        for (double py = firstGridYLocation; py <= plotSettings.getMaxY(); py += plotSettings.getGridSpacingY()) {

            if (py < plotSettings.getMinY()) continue;

            // find the position of each point and draw a line
            int plotX = plotSettings.marginLeft;

            int plotY = getPlotY(py);

            // horizontal gridColor lines
            if (plotSettings.horizontalGridVisible) {
                g.setColor(plotSettings.gridColor);
                g.drawLine(plotSettings.marginLeft, plotY, plotSettings.marginLeft + chartWidth - 1, plotY);
            }

            // draw a notch on the Y axis
            g.setColor(plotSettings.axisColor);
            g.drawLine(plotX, plotY, plotX - plotSettings.notchLength, plotY);

            // work out the value at this point and draw
            String value = plotSettings.numberFormatter.format(py);

            // work out how wide this string is
            int textXOffset = (g.getFontMetrics().stringWidth(value));

            g.setColor(plotSettings.fontColor);
            g.drawString(value, plotX - plotSettings.notchLength - textXOffset - plotSettings.notchGap, plotY + (g.getFontMetrics().getHeight() / 2) - 1);

        }

        /**
         * Draw a box around the whole graph to delimit the Axes
         */
        g.setColor(plotSettings.axisColor);
        g.drawRect(plotSettings.marginLeft, plotSettings.marginTop, chartWidth, chartHeight);

        /**
         * Draw the horizontal and vertical axes that go through the point at 0,0.
         */
        int yEqualsZero = getPlotY(0) + 0;
        if (0 > plotSettings.getMinY() && 0 < plotSettings.getMaxY())
            g.drawLine(plotSettings.marginLeft, yEqualsZero, plotSettings.marginLeft + chartWidth - 1, yEqualsZero);

        int xEqualsZero = getPlotX(0) + 0;
        if (0 > plotSettings.getMinX() && 0 < plotSettings.getMaxX())
            g.drawLine(xEqualsZero, plotSettings.marginTop, xEqualsZero, plotSettings.marginTop + chartHeight);

        
        
        /**
         * And finally - draw the results of the function onto the chart.
         */
        for (int i = 0; i < functions.size(); i++) {
        	
        	String name = functionNames.get(i);
        	Plotter function = functions.get(i);
            g.setColor(functionColors.get(i));
            function.plot(this, g, chartWidth, chartHeight);
            g.drawString(name, chartWidth-3-name.length(),2*plotSettings.marginTop+8*i);

        }
       
        
        
    }

    /**
     * Uses the numeric value of Y (as returned by a function) and
     * figures out which pixel on screen this relates to.
     */
    public int getPlotY(double y) {

        /**
         * Convert Y into pixel coordinates again
         */
        int pixelY = ((int) ((y - plotSettings.minY) / unitsPerPixelY));

        /**
         * We also need to flip the Y axis because Y is counted from the top
         * and not the bottom. Add the various borders
         */
        return ((chartHeight - pixelY) + plotSettings.marginTop);

    }

    /**
     * Uses the numeric value of X, and figures out which pixel on the screen
     * this relates to.
     */
    public int getPlotX(double x) {
        return (int) (((x - plotSettings.minX) / unitsPerPixelX) + plotSettings.marginLeft);
    }

    /**
     * Takes a numeric distance and calculates how many actual pixels high that is.
     */
    public double getActualHeight(double height) {
        return height / unitsPerPixelY;
    }

    /**
     * Takes a numeric distance and calculates how many actual pixels wide that is.
     */
    public double getActualWidth(double width) {
        return width / unitsPerPixelX;
    }

    /**
     * Takes a set number of actual pixels on the screen (in the Y direction)
     * And returns how long they are, if plotted on the graph.
     */
    public double getPlotHeight(double height) {
        return height * unitsPerPixelY;
    }

    /**
     * Takes a set number of actual pixels on the screen (in the X direction)
     * And returns how long they are, if plotted on the graph.
     */
    public double getPlotWidth(double width) {
        return width * unitsPerPixelX;
    }

    public double getActualX(int pixelX) {
        return plotSettings.minX + (pixelX * unitsPerPixelX);
    }
    
    /**
     * adds a function to the printing list, and also adds a new color to print this function with
     * if there a already a number of functions exceeding the limit set in PlotSettings, the oldes one will
     * be erased, and so will its color
     * @param f
     */
    public void addFunctionPlotter(Plotter f,String name) {
    	int currentNbFunctions = functions.size();
    	if(currentNbFunctions>=plotSettings.getMaxNbFunctions()-1) {
    		functions.remove(0);
    		functionColors.remove(0);
    		functionNames.remove(0);
    	}
		functions.add(f);
		functionColors.add(plotSettings.possibleColors[plotSettings.getNextColorIndex()]);
		functionNames.add(name);
    }

    /**
     * Plots a line between two sets of values.
     *
     * @param g  Graphics context upon which to write
     * @param x1 First point X
     * @param y1 First point Y
     * @param x2 Second point X
     * @param y2 Second point Y
     */
    public void drawLine(Graphics g, double x1, double y1, double x2, double y2) {
        g.drawLine(getPlotX(x1), getPlotY(y1), getPlotX(x2), getPlotY(y2));
    }
    
    
    /**
     * used to plot Interval valued functions
     * @param g Graphics context upon which to write
     * @param x1 x-coordinate of the bottom left corner
     * @param x2 x-coordinate of the top right corner
     * @param y1 y-coordinate of the bottom left corner
     * @param y2 y-coordinate of the top right corner
     */
    public void drawRectangle(Graphics g, double x1, double x2, double y1, double y2) {
    	//g.drawRect(getPlotX(x1), getPlotY(y2), getPlotX(x2-x1), getPlotY(y2-y1));
    	int xLow = getPlotX(x1), xUp = getPlotX(x2), yLow = getPlotY(y2), yUp = getPlotY(y1);
    	//System.out.println("x: "+new Interval(xLow,xUp)+"  y: "+new Interval(yLow,yUp));
    	//g.fillRect(xLow, yUp, Math.abs(xUp-xLow), Math.abs(yUp-yLow));
    	//g.drawRect(xLow, yUp, xUp-xLow, yUp-yLow);
    	
    	//g.setColor(Color.BLACK);
    	g.drawLine(xLow,yLow,xLow,yUp);
    	g.drawLine(xLow, yUp, xUp, yUp);
    	g.drawLine(xUp, yUp, xUp, yLow);
    	g.drawLine(xUp, yLow, xLow, yLow);
    	
    	
    }

    

  

    /**
     * Returns the graph as an image so that it can be saved.
     */
    public BufferedImage getImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(plotSettings.backgroundColor);
        g.fillRect(0, 0, width, height);
        draw(g, width, height);
        return image;
    }

}