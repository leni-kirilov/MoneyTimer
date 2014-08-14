package bg.kirilov.timer.calculator;

/**
 * Defines classes that calculate value
 *
 * @author Leni Kirilov
 * @since 4/13/2014
 */
public interface Calculator {

    /**
     * Calculates pay
     *
     * @param hoursPassed
     * @return
     */
    double calculate(long hoursPassed);
}
