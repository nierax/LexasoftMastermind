package de.lexasoft.mastermind.core.api;

import de.lexasoft.common.model.SimpleType;
import de.lexasoft.common.model.ValueObject;

/**
 * Represents a single pin on the board.
 * <p>
 * A pin can have a value in a range from 0 to a maximum value, which depends on
 * the games difficulty. Attention: 0-based!
 * 
 * @author Axel
 */
@ValueObject
public class Pin extends SimpleType<PinColor> {

	Pin(PinColor color) {
		super(color);
	}

	/**
	 * For better readability.
	 * 
	 * @return The color of this pin.
	 */
	public PinColor color() {
		return value();
	}

	/**
	 * Compares its own value against the value of the other pin.
	 * <p>
	 * Can be done several times, pin is not marked as counted.
	 * 
	 * @param otherPin The pin to compare with.
	 * @return True, if pins have the same value, false otherwise.
	 */
	public boolean comparePin(Pin otherPin) {
		return equals(otherPin);
	}

	/**
	 * Pins are equal, if their colors are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Pin)) {
			return false;
		}
		return color().equals(((Pin) obj).color());
	}

	@Override
	public String toString() {
		return color().toString();
	}

	/**
	 * Fluent API to create new Pin with the given color
	 * 
	 * @param color to use
	 * @return New pin value object.
	 */
	public static Pin of(PinColor color) {
		return new Pin(color);
	}
}
