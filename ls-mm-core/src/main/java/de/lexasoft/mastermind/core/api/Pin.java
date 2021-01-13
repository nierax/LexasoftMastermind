package de.lexasoft.mastermind.core.api;

import de.lexasoft.common.model.Validator;
import de.lexasoft.common.model.Value;

/**
 * Represents a single pin on the board.
 * <p>
 * A pin can have a value in a range from 0 to a maximum value, which depends on
 * the games difficulty. Attention: 0-based!
 * 
 * @author Axel
 */
public class Pin extends Value<PinColor> {

  /**
   * 
   * @param validator
   * @param color
   */
  public Pin(Validator<PinColor> validator, PinColor color) {
    super(validator, color);
  }

  /**
   * For better readability.
   * 
   * @return The color of this pin.
   */
  public PinColor getColor() {
    return getValue();
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
    return equals(otherPin);
  }

  /**
   * Pins are equal, if their colors are equal.
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Pin)) {
      return false;
    }
    return getColor().equals(((Pin) obj).getColor());
  }

  @Override
  public String toString() {
    return getColor().toString();
  }

}
