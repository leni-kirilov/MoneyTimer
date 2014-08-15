package bg.kirilov.timer.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Class for building and displaying a report
 *
 * @author Leni Kirilov
 * @since 4/14/2014
 */
public class MoneyReport extends Component {

    private StringBuilder report;

    public void buildReport(int peopleCount, double payRate, String timePassed, String totalAmount) {
        report = new StringBuilder("Result of session:\n");
        report.append("--------\n")
                .append("Number of participants: ").append(peopleCount).append("\n")
                .append("Pay rate per hour: ").append(payRate).append("\n")
                .append("--------\n")
                .append("Total time (HH:MM:ss) : ").append(timePassed).append("\n")
                .append("Total cost: ").append(totalAmount);
    }

    String getReport() {
        return report.toString();
    }

    public void show(Component parent) {
        JOptionPane.showMessageDialog(parent, getReport(), "This is your repost`", JOptionPane.INFORMATION_MESSAGE);
    }
}
