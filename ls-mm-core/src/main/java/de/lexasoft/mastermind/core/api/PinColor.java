/**
 * 
 */
package de.lexasoft.mastermind.core.api;

import de.lexasoft.common.model.ScalarType;
import de.lexasoft.common.model.ValueObject;

/**
 * Represents the color of a pin.
 * 
 * @author Axel
 *
 */
@ValueObject
public class PinColor extends ScalarType<Integer> {

	private PinColor(Integer value) {
		super(value);
	}

	/**
	 * Colors are equals, if their values are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PinColor)) {
			return false;
		}
		return value().equals(((PinColor) obj).value());
	}

	@Override
	public String toString() {
		return value().toString();
	}

	/**
	 * Create new ValueObject for color with the given color value.
	 * 
	 * @param value Color value to use.
	 * @return ValueObject for PinColor
	 */
	public static PinColor of(Integer value) {
		return new PinColor(value);
	}
}
