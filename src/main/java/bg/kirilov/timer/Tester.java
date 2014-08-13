package bg.kirilov.timer;

import bg.kirilov.timer.presenter.TickingPresenter;
import bg.kirilov.timer.ui.MoneyTimerExitHandler;
import bg.kirilov.timer.ui.TickingViewImpl;

import javax.swing.*;
import java.util.Locale;

/**
 * Tester class that demonstrates easy usage of the TickingViewImpl.
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

        final TickingViewImpl view = new TickingViewImpl();
        final TickingPresenter presenter = new TickingPresenter(view);
        view.setPresenter(presenter);

        //TODO change the API of the panel.getExitHandler()
        final MoneyTimerExitHandler exitHandler = new MoneyTimerExitHandler(view);

        frame.add(view);

        frame.setTitle("Timer");
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(exitHandler);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
