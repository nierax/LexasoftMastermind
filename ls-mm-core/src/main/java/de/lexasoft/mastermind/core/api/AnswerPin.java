/**
 * 
 */
package de.lexasoft.mastermind.core.api;

import de.lexasoft.common.model.Range;
import de.lexasoft.common.model.RangeValidator;
import de.lexasoft.common.model.ValueObject;

/**
 * This represents a black or white answer pin.
 * 
 * @author nierax
 */
@ValueObject
public class AnswerPin extends Pin {

	/**
	 * @param color The color of the pin. Allowed are 0 (white) or 1 (black)
	 */
	private AnswerPin(PinColor color) {
		super(color);
		if (!RangeValidator.of(Range.of(0, 1)).validate(color.value())) {
			throw new IllegalArgumentException(String.format("Color %s not valid. Must be either 0 or 1", color));
		}
	}

	/**
	 * Fluent API for answer pin value object
	 * 
	 * @param color
	 * @return New answer pin value object
	 */
	public static AnswerPin of(PinColor color) {
		return new AnswerPin(color);
	}

}
