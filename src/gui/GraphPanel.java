package gui;

import plotting.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Wraps around the graph object making it easy to integrate, and adds interactive features, 
 * such as a thread, that automatically refines the function graphs being plotted, by decreasing
 * the plottnig step, defined in PlotSettings, within the plotting package
 * @author gabriel
*/
public class GraphPanel extends JPanel implements Runnable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6782158340792673245L;
	/**
     * The graph object renders charts and graphs.
     */
    protected Graph graph;
    
    //boolean stopping the execution during events
    protected boolean running;
    

    public GraphPanel(Graph graph) {
    	this.graph = graph;
    }
    /**
     * Initializes the panel with a graph object
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
        repaint();
    }

    /**
     * Draws the graph directly onto the JPanel
     */
    public void paintComponent(Graphics g) {
       super.paintComponent(g);
       if (graph != null) 
    	   graph.draw(g, getWidth(), getHeight());
    }

    /**
     * Returns an image of the graph which can be saved to disk.
     */
    public BufferedImage getImage() {
       return graph != null? graph.getImage(getWidth(), getHeight()) : null;
    }

    /**
     * Provides access to the graph object.
     * @return
     */
    public Graph getGraph() {
        return graph;
    }
    
    
    @Override
	public void run() {
    	while(true) {
    		try {
	    		//System.out.println("running: "+running);
	    		Thread.sleep(100);
	    	}
	    	catch(InterruptedException e) {
	    		System.out.println("interrputed");
	    	}
			while(running) {
				//while running, decrease the plottingStep
				int i;
				while((i = graph.plotSettings.getPlottingStep()) >= 0) {
					if(i>1) {
						repaint();
						graph.plotSettings.setPlottingStep(i-1);
					}
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
			}
    	}
	}
    
    public void setRunning(boolean b) {
    	running = b;
    }

    
    public boolean getRunning() {
    	return running;
    }
	

		
	
    

}
