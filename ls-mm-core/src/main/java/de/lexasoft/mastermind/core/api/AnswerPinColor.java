/**
 * 
 */
package de.lexasoft.mastermind.core.api;

import de.lexasoft.common.model.RangeValidator;

/**
 * Represents the colors, the pins in an answer bank can have.
 * 
 * @author Axel
 */
public class AnswerPinColor extends PinColor {

  /**
   * AnswerPinColor can just represent two colors (black and white)
   * 
   * @param value The value, representing the color.
   */
  public AnswerPinColor(Integer value) {
    super(new RangeValidator<>(0, 1), value);
  }

}
