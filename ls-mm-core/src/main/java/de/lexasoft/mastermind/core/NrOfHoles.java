/**
 * 
 */
package de.lexasoft.mastermind.core;

import de.lexasoft.common.model.MinimumValidator;
import de.lexasoft.common.model.Value;

/**
 * Represents the number of Colors in the game.
 * <p>
 * Validates, that this number is not below the minimum number of colors
 * 
 * @author Axel
 */
public class NrOfHoles extends Value<Integer> {

  /**
   * Minimum number of colors.
   */
  public static final int MINIMUM_NUMBER_OF_PINS = 4;

  /**
   * @param value
   */
  public NrOfHoles(Integer value) {
    super(new MinimumValidator<>(MINIMUM_NUMBER_OF_PINS), value);
  }

}
