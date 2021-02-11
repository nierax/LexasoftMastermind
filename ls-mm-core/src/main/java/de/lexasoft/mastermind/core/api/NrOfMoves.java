package de.lexasoft.mastermind.core.api;

import de.lexasoft.common.model.MinimumValidator;
import de.lexasoft.common.model.ScalarType;
import de.lexasoft.common.model.Validator;
import de.lexasoft.common.model.ValueObject;

/**
 * Represents the maximum number of moves allowed in the game.
 * 
 * @author nierax
 */
@ValueObject
public class NrOfMoves extends ScalarType<Integer> {

	/**
	 * Minimum number of moves.
	 */
	public static final Validator<Integer> VALIDATOR = new MinimumValidator<>(6);

	/**
	 * Creates the object with the given number of moves.
	 * <p>
	 * Number of moves must not be below 6, otherwise an IllegalArgumentException
	 * will be thrown.
	 * 
	 * @param nrOfMoves Number of moves, at least 6.
	 */
	private NrOfMoves(Integer nrOfMoves) {
		super(nrOfMoves);
		if (!VALIDATOR.validate(nrOfMoves)) {
			throw new IllegalArgumentException("Number of Moves must at least be 6");
		}
	}

	/**
	 * Fluent API for new value object NrOfMoves
	 * 
	 * @param nrOfMoves must at least be 6.
	 * @return New instance of NrOfMoves
	 */
	public static NrOfMoves of(Integer nrOfMoves) {
		return new NrOfMoves(nrOfMoves);
	}
}
