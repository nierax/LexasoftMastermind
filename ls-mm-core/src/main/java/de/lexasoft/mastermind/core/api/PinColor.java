/**
 * 
 */
package de.lexasoft.mastermind.core.api;

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
   * Color requires a validator and a value.
   * <p>
   * The validator should be used in derived classes to check the range of the
   * color.
   * 
   * @param validator
   * @param value
   */
  public PinColor(Validator<Integer> validator, Integer value) {
    super(validator, value);
  }

  /**
   * Color requires a value.
   * 
   * @param value
   */
  public PinColor(Integer value) {
    super(value);
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
  public Integer unsetValue() {
    throw new MasterMindValidationException("Color must not be removed.");
  }

  /**
   * Colors are equals, if their values are equal.
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof PinColor)) {
      return false;
    }
    return getValue().equals(((PinColor) obj).getValue());
  }

  @Override
  public String toString() {
    return getValue().toString();
  }

}
