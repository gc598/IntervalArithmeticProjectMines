package plotting;

import plotting.Graph;

import java.awt.*;

public abstract class Plotter {

    public abstract String getName();

    public abstract void plot(Graph p, Graphics g, int chartWidth, int chartHeight);

}
