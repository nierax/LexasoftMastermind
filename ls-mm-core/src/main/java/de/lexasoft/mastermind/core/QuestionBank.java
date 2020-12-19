/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one line of pins, both as question and answer.
 * 
 * @author Axel
 */
public class QuestionBank {

  /**
   * Minimum number of pins.
   */
  public static final int MINIMUM_NUMBER_OF_PINS = 4;

  /**
   * Minimum number of colors.
   */
  public static final int MINIMUM_NUMBER_OF_COLORS = 6;

  private List<Pin> pins;

  /**
   * Creates a bank of pins with the given number of pins, each with a range of
   * the given number of colors.
   * 
   * @param nrOfPins   Number of pins in the bank.
   * @param nrOfColors Range of colors, every pin can represent.
   */
  public QuestionBank(Integer nrOfPins, Integer nrOfColors) {
    if (nrOfPins < MINIMUM_NUMBER_OF_PINS) {
      throw new IllegalArgumentException(String.format("Minimum number of pins is %s ", MINIMUM_NUMBER_OF_PINS));
    }
    if (nrOfColors < MINIMUM_NUMBER_OF_COLORS) {
      throw new IllegalArgumentException(String.format("Minimum number of colors is %s ", MINIMUM_NUMBER_OF_COLORS));
    }
    pins = new ArrayList<>();
    for (int i = 0; i < nrOfPins; i++) {
      pins.add(new Pin(nrOfColors));
    }
  }

  /**
   * @return List of the pins in the bank.
   */
  public List<Pin> getPins() {
    return pins;
  }

  /**
   * @param position
   * @return Pin at the given position
   */
  public Pin getPin(int position) {
    return pins.get(position);
  }

}
