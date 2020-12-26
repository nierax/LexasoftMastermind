/**
 * 
 */
package de.lexasoft.mastermind.core;

import de.lexasoft.common.model.Value;

/**
 * One hole in the bank, which can hold a pin in it.
 * 
 * @author Axel
 *
 */
public class Hole extends Value<Pin> {

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
  public Pin getPin() {
    return getValue();
  }

  /**
   * For better readability
   * 
   * @param pin The pin to set.
   */
  public Pin setPin(Pin pin) {
    return setValue(pin);
  }
}
