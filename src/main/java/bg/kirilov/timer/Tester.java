package bg.kirilov.timer;

import bg.kirilov.timer.ui.MoneyTimerExitHandler;
import bg.kirilov.timer.ui.TickingPanel;

import javax.swing.*;
import java.util.Locale;

/**
 * Tester class that demonstrates easy usage of the TickingPanel.
 * In order to be sure that the closing of the window is wanted,
 * the WindowListener is added with redefined functionality.
 *
 * @author Leni Kirilov
 * @version 2010-February
 */

//TODO write unit tests for calculations
//TODO set 1.8 JDK and start using 1.8 constructs
public final class Tester {

    public static void main(String[] args) {
        JOptionPane.setDefaultLocale(Locale.ENGLISH);
        JFrame frame = new JFrame();
        final TickingPanel panel = new TickingPanel();
        //TODO may be change the API of the panel. getExitHandler()
        final MoneyTimerExitHandler exitHandler = new MoneyTimerExitHandler(panel);
        frame.add(panel);

        frame.setTitle("Timer");
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(exitHandler);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
