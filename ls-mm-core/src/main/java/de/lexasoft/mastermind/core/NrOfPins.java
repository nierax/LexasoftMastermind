/**
 * 
 */
package de.lexasoft.mastermind.core;

import de.lexasoft.common.model.MinimumValue;
import de.lexasoft.common.model.Value;

/**
 * Represents the number of Colors in the game.
 * <p>
 * Validates, that this number is not below the minimum number of colors
 * 
 * @author Axel
 */
public class NrOfPins extends Value<Integer> {

  /**
   * Minimum number of colors.
   */
  public static final int MINIMUM_NUMBER_OF_PINS = 4;

  /**
   * @param value
   */
  public NrOfPins(Integer value) {
    super(new MinimumValue<>(MINIMUM_NUMBER_OF_PINS));
    setValue(value);
  }

  /**
   * Overridden to get a better exception message.
   */
  @Override
  public void setValue(Integer value) {
    try {
      super.setValue(value);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException(String.format("Number of pins must be at least %s", MINIMUM_NUMBER_OF_PINS),
          ex);
    }
  }

}
