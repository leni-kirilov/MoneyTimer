package bg.kirilov.timer.presenter;

import bg.kirilov.timer.calculator.MoneyPerSecondCalculator;
import bg.kirilov.timer.ui.TickingView;
import bg.kirilov.timer.ui.TickingViewImpl;

import javax.swing.*;

/**
 * This handles the business logic and inner state of a TickingView
 *
 * @author Leni Kirilov
 * @date 4/14/2014
 */
public class TickingPresenter {

    private int numberPeople;
    private double payRate;
    private boolean clockTicking;
    private CalculatingPerSecondThread tickerThread;

    private TickingView view;

    public TickingPresenter(TickingView view) {
        this.view = view;
    }

    public void startClock() {
        clockTicking = true;
        MoneyPerSecondCalculator calculator = new MoneyPerSecondCalculator(numberPeople, payRate);
        tickerThread = new CalculatingPerSecondThread(
                (CalculatingPerSecondView) view,
                Formatters.getNumberFormatter(),
                calculator);
        tickerThread.start();

        //update view
        view.startClock();
    }

    public boolean isClockTicking() {
        return clockTicking;
    }

    public void stopClock() {
        clockTicking = false;
        tickerThread.stopThread();

        //updating view
        view.stopClock();
    }

    public void resumeClock() {
        clockTicking = true;
        tickerThread.resumeClock();

        //updating view
        view.resumeClock();
    }

    public void resumeIfPaused() {
        if (tickerThread.isPaused()) {
            tickerThread.dieAtResume();
            resumeClock();
        }
    }

    public void pauseClock() {
        tickerThread.setPaused(true);
        view.pauseClock();
    }

    public void resetClock() {
        tickerThread = null;
        numberPeople = 0;
        payRate = 0.0;

        //update view
        view.resetClock();
    }

    /**
     * Gathers and formats information regarding the current session.<br>
     * Information regarding the number of participants; pay rates; <br>
     * time for the session and total sum is displayed.<br>
     * <p/>
     * The results are displayed as a message dialog.
     *
     * @param tickingView
     */
    //TODO extract result page into another ReportView class
    public void showResultsPage(TickingViewImpl tickingView) {
        StringBuilder result = new StringBuilder("Result of session:\n");
        result.append("--------\n")
                .append("Number of participants: ").append(numberPeople).append("\n")
                .append("Pay rate per hour: ").append(payRate).append("\n")
                .append("--------\n")
                .append("Total time (HH:MM:ss) : ").append(tickerThread.getFinalTime()).append("\n")
                .append("Total cost: ").append(tickerThread.getFinalAmountFormatted());

        JOptionPane.showMessageDialog(tickingView, result.toString(), "This is your result", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * The actions that are done when the "PAUSE"/"RESUME" button is clicked.<br>
     * They depend on the current state of the clock - RUNNING/PAUSED/STOPPED.
     */
    public void pauseButtonActionPerformed() {
        if (tickerThread.isPaused()) {
            resumeClock();
        } else {
            pauseClock();
        }
    }

    /**
     * Performs various checks on the input. Checks for integer and floating<br>
     * point numbers and to be positive.<br>
     * Checks input, creates MessageDialog to inform when the input is incorrect.<br>
     * If correct input is entered the corresponding labels will be updated.<br>
     *
     * @return - true if the input is correct
     */
    public boolean validateInput(String peopleCountRaw, String payRateRaw) {
        boolean correctInput = false;
        try {
            numberPeople = Integer.parseInt(peopleCountRaw);
            payRate = Double.parseDouble(payRateRaw);

            if (payRate <= 0 || numberPeople <= 0) {
                //TODO do not use exceptions for program flow!
                throw new Exception("Positive numbers are required!");
            }

            correctInput = true;
        } catch (Exception exc) {
            //TODO the view should define this dialog
            JOptionPane.showMessageDialog((TickingViewImpl) view,
                    "Incorrect input! Required integer for number of people and float point number for rate.",
                    exc.getMessage(),
                    JOptionPane.WARNING_MESSAGE);
            numberPeople = 0;
            payRate = 0.0d;
        }

        view.setNumberPeople(numberPeople);
        view.setPayRate(payRate);
        return correctInput;
    }
}
