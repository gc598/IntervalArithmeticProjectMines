package plotting;

import java.awt.*;
import java.text.Format;
import java.text.DecimalFormat;
import java.io.Serializable;


public class PlotSettings implements Serializable {


	private static final long serialVersionUID = 3316032123821845391L;

	/**
	 * maximum number of functions to be displayed on the graph
	 */
	protected final int maxNbFunctions = 8;
	
	/**
	 * array of possible colors for the functions
	 */
	protected final Color[] possibleColors = {
			Color.BLUE,Color.RED,Color.GREEN,Color.DARK_GRAY,Color.MAGENTA,Color.ORANGE,Color.YELLOW,
			Color.CYAN,Color.GRAY,Color.PINK
			};
	/**
	 * index of the current color to use
	 */
	protected int currentColorIndex = 0;
    /**
     * Area Parameters
     */
    protected double minX = -5, maxX = 5, minY = -5, maxY = 5;

    /**
     * Margin
     */
    protected int marginTop = 10, marginBottom = 50, marginLeft = 50, marginRight = 20;

    /**
     * The color of the axes
     */
    protected Color axisColor = Color.BLACK;

    /**
     * The color of line graphs
     */
    protected Color plotColor = Color.BLACK;

    /**
     * The color of the background
     */
    protected Color backgroundColor = Color.WHITE;

    /**
     * The color of the grid
     */
    protected Color gridColor = Color.LIGHT_GRAY;

    /**
     * The font color (title and labels)
     */
    protected Color fontColor = Color.BLACK;

    /**
     * The length (in pixels) of each notch on the horizontal and vertical axes
     */
    protected int notchLength = 4;

    /**
     * The distance in pixels between the end of a notch and the corresponding label.
     * Increase this value to move text further away from the notch.
     */
    protected int notchGap = 4;

    /**
     * Display the horizontal grid?
     */
    protected boolean horizontalGridVisible = true;

    /**
     * Display the vertical grid?
     */
    protected boolean verticalGridVisible = true;

    /**
     * Formats the numbers displayed beneath each notch.
     */
    protected Format numberFormatter = new DecimalFormat("0.00");

    /**
     * How many notches in the X direction
     */
    protected double gridSpacingX = 0.25;

    /**
     * How many notches in the Y direction
     */
    protected double gridSpacingY = 0.25;

    /**
     * The title of the graph
     */
    protected String title = null;
    
    /**
     * the number of pixel for which we compute the values of the function we plot
     */
    public final static int DEFAULT_PLOTTING_STEP = 10;
    protected int plottingStep = DEFAULT_PLOTTING_STEP;

    public PlotSettings() {
        // use defaults.
    }

    public PlotSettings(double xMin, double xMax, double yMin, double yMax) {
       this.minX = xMin;
       this.maxX = xMax;
       this.minY = yMin;
       this.maxY = yMax;
    }
    
    /**
     * 
     * @return the next color index for the function to plot, setting it back to zero if all the colors have already
     * been used
     */
    public int getNextColorIndex() {
    	int tmp = currentColorIndex;
    	currentColorIndex =  currentColorIndex==possibleColors.length-1?0:currentColorIndex+1;
    	return tmp;
    }
    
    /**
     * gets the max number of functions to display on the graph
     */
    public int getMaxNbFunctions() {
    	return maxNbFunctions;
    }

    /**
     * Gets the minimum X value for plotting
     */
    public double getMinX() {
        return minX;
    }

    /**
     * Sets the minimum X value for plotting.
     */
    public void setMinX(double minX) {
        this.minX = minX;
    }

    /**
     * Gets the maximum X value for plotting.
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * Sets the maximum X value for plotting.
     */
    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    /**
     * Gets the minimum Y value for plotting
     */
    public double getMinY() {
        return minY;
    }

    /**
     * Sets the minimum Y value for plotting
     */
    public void setMinY(double minY) {
        this.minY = minY;
    }

    /**
     * Gets the maximum Y value for plotting
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * Sets the maximum Y value for plotting
     */
    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public double getRangeX() {
        return maxX - minX;
    }

    public double getRangeY() {
        return maxY - minY;
    }

    /**
     * Gets the top margin
     */
    public int getMarginTop() {
        return marginTop;
    }

    /**
     * Sets the top margin.
     */
    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public Color getAxisColor() {
        return axisColor;
    }

    public void setAxisColor(Color axisColor) {
        this.axisColor = axisColor;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getPlotColor() {
        return plotColor;
    }

    public void setPlotColor(Color plotColor) {
        this.plotColor = plotColor;
    }

    public Color getGridColor() {
        return gridColor;
    }

    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    public int getNotchLength() {
        return notchLength;
    }

    public void setNotchLength(int notchLength) {
        this.notchLength = notchLength;
    }

    public int getNotchGap() {
        return notchGap;
    }

    public void setNotchGap(int notchGap) {
        this.notchGap = notchGap;
    }

    public boolean isHorizontalGridVisible() {
        return horizontalGridVisible;
    }

    public void setHorizontalGridVisible(boolean horizontalGridVisible) {
        this.horizontalGridVisible = horizontalGridVisible;
    }

    public boolean isVerticalGridVisible() {
        return verticalGridVisible;
    }

    public void setVerticalGridVisible(boolean verticalGridVisible) {
        this.verticalGridVisible = verticalGridVisible;
    }

    public Format getNumberFormatter() {
        return numberFormatter;
    }

    public void setNumberFormatter(Format numberFormatter) {
        this.numberFormatter = numberFormatter;
    }

    public double getGridSpacingX() {
        return gridSpacingX;
    }

    public void setGridSpacingX(double gridSpacingX) {
        this.gridSpacingX = gridSpacingX;
    }

    public double getGridSpacingY() {
        return gridSpacingY;
    }

    public void setGridSpacingY(double gridSpacingY) {
        this.gridSpacingY = gridSpacingY;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public int getPlottingStep() {
    	return plottingStep;
    }
    
    public void setPlottingStep(int step) {
    	plottingStep = step;
    }
    
    

}
