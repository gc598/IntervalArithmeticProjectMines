package gui;

import plotting.Graph;
import plotting.PlotSettings;

import java.awt.event.*;

/**
 * <p>
 * An improvement to the graph panel, which allows the user to
 * interactively drag the panel around to explore the graph
 * easily.
 * </p>
 *
 * <p>
 * Easy to replace the standard Graph panel, just make sure your
 * GUI implements SettingsUpdateListener (called after the graph panel updates)
 * This allows your GUI to be informed when the plotSettings object changes its values.
 * </p>
 *
 * @author Olly Oechsle
 */
public class InteractiveGraphPanel extends GraphPanel {

	private static final long serialVersionUID = 4778179932443465753L;
	protected int mouseDownX, mouseDownY;
    protected double minX, maxX, minY, maxY;
    protected boolean mouseDown;

    public InteractiveGraphPanel(final SettingsUpdateListener listener,Graph graph) {

    	super(graph);
        addMouseListener(new MouseAdapter() {

        
            public void mousePressed(MouseEvent e) {
            	
            	running=false;
            	
                if (graph != null) {
                    PlotSettings p = graph.plotSettings;
                    mouseDownX = e.getX();
                    mouseDownY = e.getY();
                    minX = p.getMinX();
                    minY = p.getMinY();
                    maxX = p.getMaxX();
                    maxY = p.getMaxY();
                }
                mouseDown = true;
                
            }

            public void mouseReleased(MouseEvent e) {
                mouseDown = false;
                listener.graphUpdated(graph.plotSettings);
                graph.plotSettings.setPlottingStep(PlotSettings.DEFAULT_PLOTTING_STEP);
                running = true;
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent e) {

                if (graph != null) {

                	running = false;
                	
                    PlotSettings p = graph.plotSettings;

                    double movementX = graph.getPlotWidth(e.getX() - mouseDownX);
                    double movementY = graph.getPlotHeight(e.getY() - mouseDownY);

                    p.setMinX(minX-movementX);
                    p.setMaxX(maxX-movementX);
                    p.setMinY(minY+movementY);
                    p.setMaxY(maxY+movementY);
                   
                    graph.plotSettings.setPlottingStep(PlotSettings.DEFAULT_PLOTTING_STEP);
                    repaint();
                    running = true;

                }

            }

        });

        addMouseWheelListener(new MouseWheelListener() {

            public void mouseWheelMoved(MouseWheelEvent e) {
                if (graph != null && !mouseDown)  {
                	
                	running = false;
                	
                    PlotSettings p = graph.plotSettings;

                    double multiplier;

                    if (e.getWheelRotation() < 0)  {
                        // zoom in
                        multiplier = 0.1;
                    } else {
                        // zoom out
                        multiplier = -0.1;
                    }

                    double xDiff = p.getRangeX() * multiplier;
                    double yDiff = p.getRangeY() * multiplier;

                    p.setMinX(p.getMinX() + xDiff);
                    p.setMaxX(p.getMaxX() - xDiff);

                    p.setMinY(p.getMinY() + yDiff);
                    p.setMaxY(p.getMaxY() - yDiff);

                    listener.graphUpdated(graph.plotSettings);
                    graph.plotSettings.setPlottingStep(plotting.PlotSettings.DEFAULT_PLOTTING_STEP);
                    repaint();
                    
                    running = true;
                    
                }
            }

        });

    }

}
