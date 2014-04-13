package bg.kirilov.timer.ui;

import bg.kirilov.timer.presenter.CalculatingPerSecondThread;
import bg.kirilov.timer.presenter.CalculatingPerSecondView;

import javax.swing.*;
import java.text.NumberFormat;

/**
 * JPanel that contains a clock that measures the time and calculates
 * the amount that a meeting costs a number of participants due to a
 * pay rate per hour. Not connected with any currency.<br>
 * <br>
 * The input is provided in two text fields and it's verified when the START
 * button is clicked. When a session is ended - the data in the text fields is
 * reset.<br>
 * <br>
 * TickingPanel contains means to pause the clock and display results page when
 * wanted by the user. The execution of the clock creates and starts a new thread.<br>
 * <br>
 * All operations are thread-safe since they are never meant to be executed by
 * more than one CalculatingPerSecondThread-clock.
 *
 * @author Leni Kirilov
 * @version 2010-February
 */
public class TickingPanel extends javax.swing.JPanel implements CalculatingPerSecondView {

    /**
     * Used to produce nice formatting of numbers with floating point.<br>
     * 2 characters after the floating point.
     */
    private static NumberFormat formatter;

    static {
        formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
    }

    private boolean clockTicking;
    private int numberPeople;
    private double payRate;
    private CalculatingPerSecondThread tickerThread;

    public TickingPanel() {
        initComponents();
        pauseButton.setEnabled(false);
    }

    public int getNumberPeople() {
        return numberPeople;
    }

    public double getPayRate() {
        return payRate;
    }

    public NumberFormat getFormatter() {
        return formatter;
    }

    public boolean isClockTicking() {
        return clockTicking;
    }

    /**
     * Start the clock ticking. Enables the PAUSE button.
     */
    private void startClock() {
        clockTicking = true;
        pauseButton.setEnabled(true);
        tickerThread = new CalculatingPerSecondThread(this);
        startButton.setText("STOP");
        tickerThread.start();
    }

    /**
     * Stops the clock. Disables the PAUSE button.
     */
    private void stopClock() {
        clockTicking = false;
        pauseButton.setEnabled(false);
        tickerThread.stopThread();
        startButton.setText("START");
    }

    /**
     * Pauses the clock(clock thread). Changes the label of the PAUSE button.
     */
    private void pauseClock() {
        tickerThread.setPaused(true);
        pauseButton.setText("RESUME");
    }

    /**
     * Resumes clock. Notifies the thread to continue execution.
     */
    private void resumeClock() {
        tickerThread.resumeClock();
        clockTicking = true;
        pauseButton.setText("PAUSE");
    }

    //TODO extract input parsing to another class?
    /**
     * Performs various checks on the input. Checks for integer and floating<br>
     * point numbers and to be positive.<br>
     * Checks input, creates MessageDialog to inform when the input is incorrect.<br>
     * If correct input is entered the corresponding labels will be updated.<br>
     *
     * @return - true if the input is correct
     */
    private boolean checkInput() {
        boolean correctInput = false;
        try {
            numberPeople = Integer.parseInt(numberParticipantsTextField.getText());
            payRate = Double.parseDouble(payRateTextField.getText());

            if (payRate <= 0 || numberPeople <= 0) {
                //TODO do not use exceptions for program flow!
                throw new Exception("Positive numbers are required!");
            }

            correctInput = true;
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this,
                    "Incorrect input! Required integer for number of people and float point number for rate.",
                    exc.getMessage(),
                    JOptionPane.WARNING_MESSAGE);
            numberPeople = 0;
            payRate = 0.0d;
        }

        setNumberPeople(getNumberPeople());
        setPayRate(getPayRate());
        return correctInput;
    }

    /**
     * Displays Dialog window to choose from Yes/No if abortion is wanted.
     *
     * @return true if clicked YES on the dialog
     */
    private boolean askIfWantToAbort() {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to abort the current counting?",
                "Abort ?",
                JOptionPane.YES_NO_OPTION);

        return (result == JOptionPane.YES_OPTION) ? true : false;
    }

    /**
     * Displays dialog window to choose Yes/No if results are wanted.
     *
     * @return true if clicked YES on the dialog
     */
    private boolean wantsResults() {
        int result = JOptionPane.showConfirmDialog(this,
                "Do you want to see results for this session?",
                "Results ?",
                JOptionPane.YES_NO_OPTION);

        return (result == JOptionPane.YES_OPTION) ? true : false;
    }

    /**
     * Gathers and formats information regarding the current session.<br>
     * Information regarding the number of participants; pay rates; <br>
     * time for the session and total sum is displayed.<br>
     * <p/>
     * The results are displayed as a message dialog.
     */
    //TODO extract result page into another class
    private void showResultsPage() {
        StringBuilder result = new StringBuilder("Result of session:\n");
        result.append("--------\n");
        result.append("Number of participants: ").append(numberPeople).append("\n");
        result.append("Pay rate per hour: ").append(payRate).append("\n");
        result.append("--------\n");
        result.append("Total time (HH:MM:ss) : ").append(tickerThread.getFinalTime()).append("\n");
        result.append("Total cost: ").append(tickerThread.getFinalAmount());

        JOptionPane.showMessageDialog(this, result.toString(), "This is your result", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Sets pay rate and synchronizes the internals logics with the UI.
     *
     * @param payRate - valid double number
     */
    private void setPayRate(double payRate) {
        String text = getFormatter().format(payRate);
        payRateTextField.setText(text);
        this.payRate = payRate;
    }

    /**
     * Sets numberOfPeople and synchronizes the internals logics with the UI.
     *
     * @param number - valid integer number
     */
    private void setNumberPeople(int number) {
        numberParticipantsTextField.setText(String.valueOf(number));
        numberPeople = number;
    }

    /**
     * Turns off the input text fields.
     */
    private void disableInput() {
        payRateTextField.setEnabled(false);
        numberParticipantsTextField.setEnabled(false);
    }

    /**
     * Turns on the input text fields.
     */
    private void enableInput() {
        payRateTextField.setEnabled(true);
        numberParticipantsTextField.setEnabled(true);
    }

    /**
     * Resets the state of the panel internal logics and UI elements, so that<br>
     * it becomes in the initial state to be used again.<br>
     * Usually used after stopClock().
     */
    private void resetPanel() {
        tickerThread = null;
        setNumberPeople(0);
        setPayRate(0);
        clockLabel.setText("00:00:00");
        amountLabel.setText("0.00");
    }

    //TODO rework UI - split to UI and presenter

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        numberParticipantsLabel = new JLabel();
        payRateLabel = new JLabel();
        numberParticipantsTextField = new javax.swing.JTextField();
        payRateTextField = new javax.swing.JTextField();
        startButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        timerNameLabel = new JLabel();
        amountNameLabel = new JLabel();
        clockLabel = new JLabel();
        amountLabel = new JLabel();

        numberParticipantsLabel.setText("Number of participants: ");

        payRateLabel.setText("Pay rate per hour: ");

        numberParticipantsTextField.setText("0");

        payRateTextField.setText("0.00");

        //TODO think about split to 2 buttons - start and stop
        startButton.setText("START");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        pauseButton.setText("PAUSE");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        timerNameLabel.setText("Timer: ");

        amountNameLabel.setText("Amount: ");

        clockLabel.setText("00:00:00");

        amountLabel.setText("0.00");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                                                                .addGap(23, 23, 23)
                                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                                .addComponent(startButton)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                                                                                .addComponent(pauseButton))
                                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                                .addGap(11, 11, 11)
                                                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                        .addComponent(timerNameLabel)
                                                                                        .addComponent(clockLabel)))))
                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(numberParticipantsLabel)
                                                                        .addComponent(payRateLabel))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                                                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                                                .addGap(10, 10, 10)
                                                                                                .addComponent(amountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                                        .addComponent(amountNameLabel, javax.swing.GroupLayout.Alignment.TRAILING)))
                                                                        .addComponent(payRateTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                                                        .addComponent(numberParticipantsTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))))
                                                .addGap(24, 24, 24))))
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(numberParticipantsLabel)
                                        .addComponent(numberParticipantsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(payRateLabel)
                                        .addComponent(payRateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(startButton)
                                        .addComponent(pauseButton))
                                .addGap(18, 18, 18)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(amountNameLabel)
                                        .addComponent(timerNameLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(amountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(clockLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * The actions that are done when the "START/"STOP" button is clicked.<br>
     * They depend on the current state of the clock - PAUSED/RUNNING/STOPPED.
     *
     * @param evt
     */
    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if (clockTicking) { //if true, the button says STOP
            if (askIfWantToAbort()) {
                if (tickerThread.isPaused()) {
                    tickerThread.dieAtResume();
                    resumeClock();
                }

                enableInput();
                stopClock();
                if (wantsResults()) {
                    showResultsPage();
                }
                resetPanel();
            }
        } else { //if false, the button says START
            if (checkInput()) {
                disableInput();
                startClock();
            }
        }
    }//GEN-LAST:event_startButtonActionPerformed

    /**
     * The actions that are done when the "PAUSE"/"RESUME" button is clicked.<br>
     * They depend on the current state of the clock - RUNNING/PAUSED/STOPPED.
     *
     * @param evt
     */
    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        if (tickerThread.isPaused()) {
            resumeClock();
        } else {
            pauseClock();
        }
    }//GEN-LAST:event_pauseButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel amountLabel;
    private JLabel amountNameLabel;
    private JLabel clockLabel;
    private javax.swing.JPanel mainPanel;
    private JLabel numberParticipantsLabel;
    private javax.swing.JTextField numberParticipantsTextField;
    private javax.swing.JButton pauseButton;
    private JLabel payRateLabel;
    private javax.swing.JTextField payRateTextField;
    private javax.swing.JButton startButton;
    private JLabel timerNameLabel;

    @Override
    public void setClock(String formattedTimer) {
        clockLabel.setText(formattedTimer);
    }

    @Override
    public void setAmount(String formattedAmount) {
        amountLabel.setText(formattedAmount);
    }
}
