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
public class NrOfColors extends Value<Integer> {

  /**
   * Minimum number of colors.
   */
  public static final int MINIMUM_NUMBER_OF_COLORS = 6;

  /**
   * @param value
   */
  public NrOfColors(Integer value) {
    super(new MinimumValue<>(MINIMUM_NUMBER_OF_COLORS));
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
      throw new IllegalArgumentException(
          String.format("Number of colors must be at least %s", MINIMUM_NUMBER_OF_COLORS), ex);
    }
  }

}
