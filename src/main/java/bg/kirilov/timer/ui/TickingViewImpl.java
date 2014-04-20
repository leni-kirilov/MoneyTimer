package bg.kirilov.timer.ui;

import bg.kirilov.timer.presenter.CalculatingView;
import bg.kirilov.timer.presenter.TickingPresenter;
import bg.kirilov.timer.util.Formatters;

import javax.swing.*;

/**
 * JPanel that contains a clock that measures the time and calculates
 * the amount that a meeting costs a number of participants due to a
 * pay rate per hour. Not connected with any currency.<br>
 * <br>
 * The input is provided in two text fields and it's verified when the START
 * button is clicked. When a session is ended - the data in the text fields is
 * reset.<br>
 * <br>
 * TickingViewImpl contains means to pauseThread the clock and display results page when
 * wanted by the user. The execution of the clock creates and starts a new thread.<br>
 * <br>
 * All operations are thread-safe since they are never meant to be executed by
 * more than one CalculatingThread-clock.
 *
 * @author Leni Kirilov
 * @version 2014.04
 * @since 2010-February
 */
public class TickingViewImpl extends javax.swing.JPanel implements CalculatingView, TickingView {

    private JPanel mainPanel;
    private JLabel amountLabel;
    private JLabel amountNameLabel;

    private JLabel numberParticipantsLabel;
    private JTextField numberParticipantsTextField;

    private JButton pauseButton;
    private JButton startButton;

    private JLabel payRateLabel;
    private JTextField payRateTextField;

    private JLabel clockLabel;
    private JLabel timerNameLabel;

    //TODO TEMP variable for saver and easier refactoring. Remove when refactoring is done
    public TickingPresenter presenter;

    public TickingViewImpl() {
        initComponents();
        pauseButton.setEnabled(false);
    }

    public void setPresenter(TickingPresenter presenter) {
        this.presenter = presenter;
    }

    public void startClock() {
        pauseButton.setEnabled(true);
        startButton.setText("STOP");
        setInput(false);
    }

    //TODO try to create an ENUM with the states of the panel (in the presenter of course) and try to reduce the boolean variables

    public void stopClock() {
        resumeClock();
        pauseButton.setEnabled(false);
        startButton.setText("START");
    }

    public void pauseClock() {
        pauseButton.setText("RESUME");
    }

    public void resumeClock() {
        pauseButton.setText("PAUSE");
    }

    public boolean askIfWantToAbort() {
        return ask("Are you sure you want to abort the current counting?", "Abort ?");
    }

    public boolean askIfWantReport() {
        return ask("Do you want to see report for this session?", "Report ?");
    }

    private boolean ask(String msg, String title) {
        int result = JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION);
        return (result == JOptionPane.YES_OPTION) ? true : false;
    }

    public void setPayRate(double payRate) {
        String text = Formatters.getNumberFormatter().format(payRate);
        payRateTextField.setText(text);
    }

    public void setNumberPeople(int number) {
        numberParticipantsTextField.setText(String.valueOf(number));
    }

    public void resetClock() {
        clockLabel.setText("00:00:00");
        amountLabel.setText("0.00");
        setInput(true);
    }

    public void showReport(String report) {
        JOptionPane.showMessageDialog(this, report, "This is your result", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setClock(String formattedClock) {
        clockLabel.setText(formattedClock);
    }

    @Override
    public void setAmount(String formattedAmount) {
        amountLabel.setText(formattedAmount);
    }

    //TODO rework this generated UI layout
    @SuppressWarnings("unchecked")
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

        //TODO think about splitting into 2 buttons - start and stop
        startButton.setText("START");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presenter.startStopButtonPressed(
                        numberParticipantsTextField.getText().trim(),
                        payRateTextField.getText().trim()
                );
            }
        });

        pauseButton.setText("PAUSE");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presenter.pauseButtonActionPerformed();
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
    }

    @Override
    public void showInvalidInputMessage(String invalidInputMessage) {
        JOptionPane.showMessageDialog(this,
                invalidInputMessage,
                "Incorrect input",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Turns on/off the input text fields.
     *
     * @param enabled if true, input fields are enabled
     */
    private void setInput(boolean enabled) {
        payRateTextField.setEnabled(enabled);
        numberParticipantsTextField.setEnabled(enabled);
    }
}
