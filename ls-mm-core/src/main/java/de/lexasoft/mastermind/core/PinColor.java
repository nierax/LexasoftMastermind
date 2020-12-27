/**
 * 
 */
package de.lexasoft.mastermind.core;

import de.lexasoft.common.model.Validator;
import de.lexasoft.common.model.Value;

/**
 * Represents the color of a pin.
 * 
 * @author Axel
 *
 */
public class PinColor extends Value<Integer> {

  /**
   * Color requires a validator and an value.
   * 
   * @param validator
   * @param value
   */
  public PinColor(Validator<Integer> validator, Integer value) {
    super(validator, value);
  }

  /**
   * Not allowed to set a new value of color after constructor.
   */
  @Override
  public Integer setValue(Integer value) {
    throw new MasterMindValidationException("Color must not change value.");
  }

  /**
   * Not allowed to unset value of color.
   */
  @Override
  public void unsetValue() {
    throw new MasterMindValidationException("Color must not be removed.");
  }

}
