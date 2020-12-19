/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the bank for answers with only white and black pins.
 * 
 * @author Axel
 */
public class AnswerBank {

  private List<Pin> pins;

  /**
   * Creates the answer bank with the given number of pins. The number of colors
   * is set to 2, because there is only black and white.
   * 
   * @param nrOfPins
   */
  public AnswerBank(Integer nrOfPins) {
    if (nrOfPins < QuestionBank.MINIMUM_NUMBER_OF_PINS) {
      throw new IllegalArgumentException(String.format("Number of Pins was %s, but must be at least %s.", nrOfPins,
          QuestionBank.MINIMUM_NUMBER_OF_PINS));
    }
    pins = new ArrayList<>();
  }

  /**
   * @return Get all pins in the bank.
   */
  List<Pin> getPins() {
    return pins;
  }

  /**
   * Copies all pins in the given list to it's own list. The original list is not
   * reused.
   * 
   * @param pins The pins to get.
   */
  public void setPins(List<Pin> pins) {
    for (Pin pin : pins) {
      addPin(pin);
    }
  }

  /**
   * Adds the given pin to the bank.
   * 
   * @param pin Pin to be added.
   */
  public void addPin(Pin pin) {
    this.pins.add(pin);
  }

}
