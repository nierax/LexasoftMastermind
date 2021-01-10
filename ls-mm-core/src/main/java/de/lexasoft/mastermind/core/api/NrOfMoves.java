package de.lexasoft.mastermind.core.api;

import de.lexasoft.common.model.MinimumValidator;
import de.lexasoft.common.model.Validator;
import de.lexasoft.common.model.Value;

/**
 * Represents the maximum number of moves allowed in the game.
 * 
 * @author Axel
 */
public class NrOfMoves extends Value<Integer> {

  /**
   * Minimum number of moves.
   */
  public static final Validator<Integer> VALIDATOR = new MinimumValidator<>(6);

  /**
   * 
   * @param number
   */
  public NrOfMoves(Integer value) {
    super(VALIDATOR, value);
  }
}
