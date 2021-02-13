/**
 * 
 */
package de.lexasoft.mastermind.core;

import de.lexasoft.mastermind.core.api.Pin;

/**
 * One hole in the bank, which can hold a pin in it.
 * <p>
 * Decided to keep it generic, because we want to differ holes, that hold
 * question pins from those, that hold answer pins for type safety reasons in
 * the api.
 * 
 * @author Axel
 *
 */
public class Hole<T extends Pin> {

	private T pin;

	/**
	 * Does this hole hold a pin?
	 * 
	 * @return True, if a pin is set, false otherwise.
	 */
	public boolean holdsAPin() {
		return pin != null;
	}

	/**
	 * For better readability
	 * 
	 * @return Returns the pin or null, if there wasn't given one.
	 */
	public T pin() {
		return pin;
	}

	/**
	 * For better readability
	 * 
	 * @param pin The pin to set.
	 */
	public T stickPin(T pin) {
		return this.pin = pin;
	}

	/**
	 * Removes the pin from the hole.
	 * 
	 * @return Returns the pin, that was removed or null, if there wasn't set one
	 *         before.
	 */
	public T removePin() {
		T old = this.pin;
		this.pin = null;
		return old;
	}

	@Override
	public String toString() {
		if (!holdsAPin()) {
			return "empty";
		}
		return this.pin.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Hole)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Hole<T> other = (Hole<T>) obj;
		// Both have null => true
		if (!holdsAPin() && !other.holdsAPin()) {
			return true;
		}
		// Just I'm having null => false
		if (!holdsAPin()) {
			return false;
		}
		// Compare by values
		return pin.equals(other.pin);
	}

}
