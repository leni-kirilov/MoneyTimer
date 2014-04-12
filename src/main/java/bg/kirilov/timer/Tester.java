package bg.kirilov.timer;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;

/**
 * Tester class that demonstrates easy usage of the TickingPanel.
 * In order to be sure that the closing of the window is wanted,
 * the WindowListener is added with redefined functionality.
 *
 * @author Leni Kirilov
 * @version 2010-February
 */
public final class Tester {

    public static void main(String[] args) {
        JOptionPane.setDefaultLocale(Locale.ENGLISH);
        JFrame frame = new JFrame();
        final TickingPanel panel = new TickingPanel();
        frame.add(panel);

        frame.setTitle("Timer");
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowListener() {

            public void windowOpened(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
                panel.exit(e);
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }
        });

        //this is a test comment
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
