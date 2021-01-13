/**
 * 
 */
package de.lexasoft.mastermind.core.api;

/**
 * This represents a black or white answer pin.
 * 
 * @author Axel
 */
public class AnswerPin extends Pin {

  /**
   * @param color The color of the pin. Allowed are 0 (white) or 1 (black)
   */
  public AnswerPin(PinColor color) {
    super((PinColor value) -> {
      return (value.getValue() == 0) || (value.getValue() == 1);
    }, color);
  }

}
