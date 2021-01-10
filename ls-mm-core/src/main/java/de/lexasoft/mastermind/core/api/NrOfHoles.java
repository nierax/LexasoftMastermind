/**
 * 
 */
package de.lexasoft.mastermind.core.api;

import de.lexasoft.common.model.MinimumValidator;
import de.lexasoft.common.model.Validator;
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
   * Minimum number of holes.
   */
  public static final Validator<Integer> VALIDATOR = new MinimumValidator<>(4);

  /**
   * @param value
   */
  public NrOfHoles(Integer value) {
    super(VALIDATOR, value);
  }

}
