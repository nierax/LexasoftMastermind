package de.lexasoft.mastermind.core;

import de.lexasoft.common.model.MinimumValidator;
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
  public static final int MINIMUM_NUMBER_OF_MOVES = 6;

  /**
   * 
   * @param number
   */
  public NrOfMoves(Integer value) {
    super(new MinimumValidator<>(MINIMUM_NUMBER_OF_MOVES), value);
  }
}
