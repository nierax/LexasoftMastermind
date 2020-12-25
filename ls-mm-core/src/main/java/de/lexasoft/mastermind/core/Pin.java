package de.lexasoft.mastermind.core;

import de.lexasoft.common.model.RangeValidator;
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
    super(new RangeValidator<Integer>(0, nrOfColors - 1));
    counted = false;
  }

  /**
   * This constructor allows to create the pin and set a value at once.
   * <p>
   * First of all thought to be used in test scenarios, that's why it is kept
   * package protected.
   * 
   * @param nrOfColors
   * @param value
   */
  Pin(Integer nrOfColors, Integer value) {
    this(nrOfColors);
    setValue(value);
  }

  /**
   * @return Number of colors, this pin can range.
   */
  public Integer getNrOfColors() {
    return ((RangeValidator<Integer>) getValidator()).getMax() + 1;
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
