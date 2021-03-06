package bg.kirilov.timer.calculator;

import bg.kirilov.timer.util.TimeConstants;

/**
 * Calculator class which calculates secondsPassed * moneyRate
 *
 * @author Leni Kirilov
 * @since 4/13/2014
 */
public class MoneyPerSecondCalculator implements Calculator {

    /**
     * The multiplication of numberOfPeople by payRate.
     */
    private double peoplePay;

    public MoneyPerSecondCalculator(int peopleCount, double payRatePerHourPerson) {
        this.peoplePay = peopleCount * payRatePerHourPerson;
    }

    @Override
    public double calculate(long secondsPassed) {
        return (peoplePay * secondsPassed) / (TimeConstants.SECONDS_IN_AN_HOUR);
    }
}
