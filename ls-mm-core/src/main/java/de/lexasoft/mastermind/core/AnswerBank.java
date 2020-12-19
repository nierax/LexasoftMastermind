/**
 * 
 */
package de.lexasoft.mastermind.core;

/**
 * Represents the bank for answers with only white and black pins.
 * 
 * @author Axel
 */
public class AnswerBank extends AbstractBank {

  /**
   * Creates the answer bank with the given number of pins. The number of colors
   * is set to 2, because there is only black and white.
   * 
   * @param nrOfPins
   */
  public AnswerBank(Integer nrOfPins) {
    super(nrOfPins, 2);
  }

  @Override
  protected Integer getMinimumNrOfPins() {
    return QuestionBank.MINIMUM_NUMBER_OF_PINS;
  }

  @Override
  protected Integer getMinimumNrOfColors() {
    return 2;
  }

}
