package bg.kirilov.timer.calculator;

/**
 * Calculator class which calculates secondsPassed * moneyRate
 *
 * @author Leni Kirilov
 * @date 4/13/2014
 */
public class MoneyPerSecondCalculator {

    /**
     * The multiplication of numberOfPeople by payRate.
     */
    private double peoplePay;

    public MoneyPerSecondCalculator(int peopleCount, double payRatePerHourPerson) {
        this.peoplePay = peopleCount * payRatePerHourPerson;
    }

    public double calculate(long secondsPassed) {
        return (peoplePay * secondsPassed) / (TimeConstants.SECONDS_IN_AN_HOUR * 1.0);
    }
}
