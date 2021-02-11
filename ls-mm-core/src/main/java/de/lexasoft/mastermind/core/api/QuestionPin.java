package de.lexasoft.mastermind.core.api;

import de.lexasoft.common.model.Range;
import de.lexasoft.common.model.RangeValidator;
import de.lexasoft.common.model.ValueObject;

/**
 * This pin represents a color to guess.
 * 
 * @author Axel
 *
 */
@ValueObject
public class QuestionPin extends Pin {

	protected QuestionPin(NrOfColors nrOfColors, PinColor color) {
		super(color);
		if (!RangeValidator.of(Range.of(0, nrOfColors.getValue() - 1)).validate(color.value())) {
			throw new IllegalArgumentException(
			    String.format("Color %s not valid. Must be between 0 and %s", color, nrOfColors));
		}
	}

	/**
	 * Fluent API for creation of question pin.
	 * 
	 * @param nrOfColors
	 * @param color
	 * @return QuestionPin value object
	 */
	public static QuestionPin of(NrOfColors nrOfColors, PinColor color) {
		return new QuestionPin(nrOfColors, color);
	}
}
