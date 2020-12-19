/**
 * 
 */
package de.lexasoft.mastermind.core;

/**
 * This bank is used for questions about the color combination to guess.
 * 
 * @author Axel
 */
public class QuestionBank extends AbstractBank {

  /**
   * Minimum number of pins.
   */
  public static final int MINIMUM_NUMBER_OF_PINS = 4;

  /**
   * Minimum number of colors.
   */
  public static final int MINIMUM_NUMBER_OF_COLORS = 6;

  /**
   * @param nrOfPins
   * @param nrOfColors
   */
  public QuestionBank(Integer nrOfPins, Integer nrOfColors) {
    super(nrOfPins, nrOfColors);
  }

  @Override
  protected Integer getMinimumNrOfPins() {
    return MINIMUM_NUMBER_OF_PINS;
  }

  @Override
  protected Integer getMinimumNrOfColors() {
    return MINIMUM_NUMBER_OF_COLORS;
  }

}
