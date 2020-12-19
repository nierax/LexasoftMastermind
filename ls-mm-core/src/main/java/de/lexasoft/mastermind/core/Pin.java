package de.lexasoft.mastermind.core;

import de.lexasoft.common.model.Range;
import de.lexasoft.common.model.Value;

/**
 * Represents a single pin on the board.
 * <p>
 * A pin can have a value in a range from 0 to a maximum value, which depends on
 * the games difficulty. Attention: 0-based!
 * 
 * @author Axel
 */
public class Pin extends Value<Integer> {

  private boolean counted;

  /**
   * Create a pin with number of colors.
   * 
   * @param nrOfColors
   */
  public Pin(Integer nrOfColors) {
    super(new Range<Integer>(0, nrOfColors - 1));
    counted = false;
  }

  /**
   * @return Number of colors, this pin can range.
   */
  public Integer getNrOfColors() {
    return ((Range<Integer>) getValidator()).getMax() + 1;
  }

  /**
   * Compares its own value against the value of the other pin.
   * <p>
   * Can be done several times, pin is not marked as counted.
   * 
   * @param otherPin The pin to compare with.
   * @return True, if pins have the same value, false otherwise.
   */
  public boolean comparePin(Pin otherPin) {
    return getValue().equals(otherPin.getValue());
  }

  /**
   * Compares its own value against the value of the other pin.
   * <p>
   * Pin is being marked as counted, so it won't be counted again.
   * 
   * @param otherPin The pin to compare with.
   * @return True, if pins have the same value and this pin was not answered
   *         before, false otherwise.
   */
  public boolean answerPin(Pin otherPin) {
    if (isCounted()) {
      return false;
    }
    boolean result = comparePin(otherPin);
    if (result) {
      setCounted();
    }
    return result;
  }

  /**
   * 
   * @return True, if the pin has already been counted, false otherwise.
   */
  boolean isCounted() {
    return counted;
  }

  /**
   * Marks the pin counted.
   */
  void setCounted() {
    counted = true;
  }

  /**
   * Removes the counted mark from the pin.
   */
  void resetCounted() {
    counted = false;
  }

}
