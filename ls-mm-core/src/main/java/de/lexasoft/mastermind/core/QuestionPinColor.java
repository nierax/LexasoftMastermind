/**
 * 
 */
package de.lexasoft.mastermind.core;

import de.lexasoft.common.model.RangeValidator;

/**
 * Represents the colors, that can be guessed.
 * 
 * @author Axel
 */
public class QuestionPinColor extends PinColor {

  /**
   * QuestionPinColor needs the number of colors in the game and the value, it
   * should represent.
   * 
   * @param nrOfColors The number of colors in this game
   * @param value      The value, representing the color.
   */
  public QuestionPinColor(NrOfColors nrOfColors, Integer value) {
    super(new RangeValidator<>(0, nrOfColors.getValue() - 1), value);
  }

}
