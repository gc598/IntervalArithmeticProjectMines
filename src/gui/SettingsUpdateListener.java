package gui;
import plotting.PlotSettings;

/**
 * interface to be implemented by the GraphPanel class, to define the actions to take in case
 * of an update by the user
 * @author Olly Oechsle
 */
public interface SettingsUpdateListener {

    public void graphUpdated(PlotSettings settings);

}
