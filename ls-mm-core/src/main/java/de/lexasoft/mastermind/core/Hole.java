/**
 * 
 */
package de.lexasoft.mastermind.core;

import de.lexasoft.common.model.Value;
import de.lexasoft.mastermind.core.api.Pin;

/**
 * One hole in the bank, which can hold a pin in it.
 * 
 * @author Axel
 *
 */
public class Hole<T extends Pin> extends Value<T> {

  /**
   * Does this hole hold a pin?
   * 
   * @return True, if a pin is set, false otherwise.
   */
  public boolean holdsAPin() {
    return hasValue();
  }

  /**
   * For better readability
   * 
   * @return Returns the pin or null, if there wasn't given one.
   */
  public T getPin() {
    return getValue();
  }

  /**
   * For better readability
   * 
   * @param pin The pin to set.
   */
  public T setPin(T pin) {
    return setValue(pin);
  }

  /**
   * Removes the pin from the hole.
   */
  public T removePin() {
    return unsetValue();
  }

  @Override
  public String toString() {
    if (!holdsAPin()) {
      return "empty";
    }
    return super.toString();
  }

}
