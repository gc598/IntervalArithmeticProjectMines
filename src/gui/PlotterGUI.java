package gui;

import plotting.ContinuousFunctionDrawer;
import plotting.Graph;
import plotting.IntervalFunctionDrawer;
import plotting.PlotSettings;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import intervals.Functions;
import intervals.IntervalException;
import parser.CalcPile;
import parser.PileVideException;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Function;

/**
 * this class is used to create the main frame of the application. It defines the layout of 
 * the components, along with the buttons used to save the graph, position the graph, or type in 
 * functions
 * @author Olly Oechsle
 * @author gabriel
 *
 */

public class PlotterGUI extends JFrame implements ActionListener, SettingsUpdateListener {

	private static final long serialVersionUID = 3603738954951899424L;
	protected JButton save, exit, update;
    protected JTextField minX, minY, maxX, maxY,function;
    protected JComboBox<String> choicesBox;
    protected GraphPanel graphPanel;

    
    public PlotterGUI() {

        //create the graph
        PlotSettings p = new PlotSettings(-2, 2, -1, 1);
        Graph graph = new Graph(p);
        
        // add the panel to the middle of the BorderLayout, it will fill the window.
        graphPanel = new InteractiveGraphPanel(this,graph);
    	
        save = new JButton("Save");
        save.addActionListener(this);

        exit = new JButton("Exit");
        exit.addActionListener(this);
        
        
        
        String[] choices = {"interval mode","continuous mode"};
        choicesBox= new JComboBox<String>(choices);
        choicesBox.addActionListener(this);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        minX = new JTextField(String.valueOf(graph.plotSettings.getMinX()));
        minY = new JTextField(String.valueOf(graph.plotSettings.getMinY()));
        maxX = new JTextField(String.valueOf(graph.plotSettings.getMaxX()));
        maxY = new JTextField(String.valueOf(graph.plotSettings.getMaxY()));
        function = new JTextField("                      ");
        function.addActionListener(this);
        
        toolbar.add(choicesBox);
        toolbar.add(new JLabel("function:"));
        toolbar.add(function);
        toolbar.add(new JLabel("X: "));
        toolbar.add(minX);
        toolbar.add(new JLabel("-"));
        toolbar.add(maxX);
        toolbar.add(new JLabel(", Y:"));
        toolbar.add(minY);
        toolbar.add(new JLabel("-"));
        toolbar.add(maxY);

        update = new JButton("Update");
        update.addActionListener(this);
        toolbar.add(update);

        toolbar.add(save);
        toolbar.add(exit);
        

        // Make sure Java Exits when the close button is clicked
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                graphPanel.setRunning(false);
            	System.exit(0);
            }
        }
        );

        
        // add the components to the frame
        
        Container c = getContentPane();
        c.add(toolbar, BorderLayout.SOUTH);
        c.add(graphPanel, BorderLayout.CENTER);
        

        // default size of the window, the Graph Panel will be slightly smaller.
        setSize(640, 480);      

        // window title
        setTitle("interval-real function plotter");
        
        // show the Window
        setVisible(true);
        
        //start the refining thread
        Thread refiner = new Thread(graphPanel);
        refiner.setPriority(Thread.MIN_PRIORITY);
        refiner.start();
       
    }


    public void graphUpdated(PlotSettings settings) {
        minX.setText(String.valueOf(settings.getMinX()));
        minY.setText(String.valueOf(settings.getMinY()));
        maxX.setText(String.valueOf(settings.getMaxX()));
        maxY.setText(String.valueOf(settings.getMaxY()));
        PlotSettings p = graphPanel.graph.plotSettings;
        //redefines the spacing between the graph notches, to match the new scale
        p.setGridSpacingX(p.getRangeX()/8);
        p.setGridSpacingY(p.getRangeY()/8);
    }
    

    public void actionPerformed(ActionEvent e){

        if (e.getSource() == update) {
            Graph g = graphPanel.getGraph();
            g.plotSettings.setMinX(Double.parseDouble(minX.getText()));
            g.plotSettings.setMaxX(Double.parseDouble(maxX.getText()));
            g.plotSettings.setMinY(Double.parseDouble(minY.getText()));
            g.plotSettings.setMaxY(Double.parseDouble(maxY.getText()));
            graphPanel.repaint();
        }

        // Saves an image of the graph to disk.
        if (e.getSource() == save) {
            JFileChooser filechooser = new JFileChooser(System.getProperty("user.home"));
            filechooser.setDialogTitle("Save Graph Image");
            filechooser.setSelectedFile(new File(filechooser.getCurrentDirectory(), "graph.png"));
            filechooser.setFileFilter(new FileFilter() {
                public boolean accept(File f) {
                    String extension = f.getName().substring(f.getName().lastIndexOf('.') + 1).toLowerCase();
                    if (f.isDirectory()) return true;
                    if (extension.equals("bmp")) return true;
                    if (extension.equals("jpg")) return true;
                    if (extension.equals("png")) return true;
                    return false;
                }

                public String getDescription() {
                    return "Image Files: jpg, png, bmp";
                }
            });
            int action = filechooser.showSaveDialog(this);
            if (action == JFileChooser.APPROVE_OPTION) {
                File f = filechooser.getSelectedFile();
                try {
                    String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                    javax.imageio.ImageIO.write(graphPanel.getImage(), extension, f);
                } catch (IOException err) {
                    JOptionPane.showMessageDialog(this, "Could not save image: " + err.getMessage());
                }
            }
        }
        if(e.getSource()==function) {
        	
        	graphPanel.setRunning(false);
        	graphPanel.graph.plotSettings.setPlottingStep(PlotSettings.DEFAULT_PLOTTING_STEP);
        	
        	File file = new File("function.txt");
        	try {
        		//first parse the string from infix to postfix, then write it into a text file
				PrintWriter pw = new PrintWriter(file);
				String funcString = function.getText();
				String postFixFuncString = parser.InfixToPostfix.infixToPostfix(funcString);
				pw.print(postFixFuncString);
				pw.close();
				
				// parse the postfix string to Functional objects, such as Function<Double,Double>
				// and Functions
				Object[] toPrint = CalcPile.parseFunctionFile(file);
				Functions intervalToPrint = (Functions)toPrint[0];
				@SuppressWarnings("unchecked")
				Function<Double,Double> continuousToPrint = (Function<Double,Double>)toPrint[1];
				intervalToPrint.setDesc(funcString);
				IntervalFunctionDrawer intervalFunction = new IntervalFunctionDrawer(intervalToPrint);
				ContinuousFunctionDrawer continuousFunction = new ContinuousFunctionDrawer(continuousToPrint);
				if(choicesBox.getSelectedItem().equals("interval mode"))
					graphPanel.getGraph().addFunctionPlotter(intervalFunction,funcString);
				else
					graphPanel.getGraph().addFunctionPlotter(continuousFunction,funcString);
				
				graphPanel.repaint();
				
        	}
        	catch(PileVideException ex) {
        		System.out.println("invalid expression");
        		//ex.printStackTrace();
        	}
        	catch(FileNotFoundException ex) {
        		System.out.println("File not found");
        	}
        	catch(IntervalException ex) {
        		System.out.println("function is undefined");
        	}
        	
        	graphPanel.setRunning(true);
        }

        // Exits the program.
        if (e.getSource() == exit) {
            System.exit(0);
        }
        
    }
    


}
