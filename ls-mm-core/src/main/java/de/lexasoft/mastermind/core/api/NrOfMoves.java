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
   * Creates the object with the given number of moves.
   * <p>
   * Number of moves must not be below 6, otherwise an IllegalArgumentException will be thrown.
   * 
   * @param nrOfMoves Number of moves, at least 6.
   */
  public NrOfMoves(Integer nrOfMoves) {
    super(VALIDATOR, nrOfMoves);
  }
}
