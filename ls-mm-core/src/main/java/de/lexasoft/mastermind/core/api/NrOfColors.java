/**
 * 
 */
package de.lexasoft.mastermind.core.api;

import de.lexasoft.common.model.MinimumValidator;
import de.lexasoft.common.model.ScalarType;
import de.lexasoft.common.model.Validator;

/**
 * Represents the number of Colors in the game.
 * <p>
 * Validates, that this number is not below the minimum number of colors
 * 
 * @author Axel
 */
public final class NrOfColors extends ScalarType<Integer> {

	/**
	 * Minimum number of colors.
	 */
	public static final Validator<Integer> VALIDATOR = MinimumValidator.of(6);

	/**
	 * Creates the object with the given number of colors.
	 * <p>
	 * Number of colors must not be below 6, otherwise an IllegalArgumentException
	 * will be thrown.
	 * 
	 * @param nrOfColors Number of colors, at least 6.
	 */
	private NrOfColors(Integer nrOfColors) {
		super(nrOfColors);
		if (!VALIDATOR.validate(nrOfColors)) {
			throw new IllegalArgumentException("Number of colors must at least be 6.");
		}
	}

	/**
	 * Fluent API for creation of NrOfColors
	 * 
	 * @param nrOfColors Must not be below 6
	 * @return New instance of NrOfColors
	 */
	public static NrOfColors of(Integer nrOfColors) {
		return new NrOfColors(nrOfColors);
	}

}
