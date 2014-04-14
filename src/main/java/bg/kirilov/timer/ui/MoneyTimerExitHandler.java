package bg.kirilov.timer.ui;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author Leni Kirilov
 * @date 4/13/2014
 */
public class MoneyTimerExitHandler implements WindowListener {

    private TickingViewImpl tickingPanel;

    public MoneyTimerExitHandler(TickingViewImpl tickingPanel) {
        this.tickingPanel = tickingPanel;
    }

    /**
     * Exit method to give option to cancel exit operation.<br>
     * Can be used in WindowListeners to override default close operation.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(tickingPanel,
                "Are you sure you want to close the program?\n"
                        + (tickingPanel.presenter.isClockTicking() ? "No report for the meeting will be generated!" : ""),
                "Exit ?", JOptionPane.YES_NO_OPTION
        );
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }
}
