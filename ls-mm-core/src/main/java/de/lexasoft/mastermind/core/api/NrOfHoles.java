/**
 * 
 */
package de.lexasoft.mastermind.core.api;

import de.lexasoft.common.model.MinimumValidator;
import de.lexasoft.common.model.ScalarType;
import de.lexasoft.common.model.Validator;
import de.lexasoft.common.model.ValueObject;

/**
 * Represents the number of Colors in the game.
 * <p>
 * Validates, that this number is not below the minimum number of colors
 * 
 * @author nierax
 */
@ValueObject
public final class NrOfHoles extends ScalarType<Integer> {

	/**
	 * Minimum number of holes.
	 */
	public static final Validator<Integer> VALIDATOR = new MinimumValidator<>(4);

	/**
	 * Creates the object with the given number of holes.
	 * <p>
	 * Number of holes must not be below 4, otherwise an IllegalArgumentException
	 * will be thrown.
	 * 
	 * @param nrOfHoles Number of holes, at least 4.
	 */
	private NrOfHoles(Integer nrOfHoles) {
		super(nrOfHoles);
		if (!VALIDATOR.validate(nrOfHoles)) {
			throw new IllegalArgumentException("Number of holes must at least be 4.");
		}
	}

	public static NrOfHoles of(Integer nrOfHoles) {
		return new NrOfHoles(nrOfHoles);
	}

}
