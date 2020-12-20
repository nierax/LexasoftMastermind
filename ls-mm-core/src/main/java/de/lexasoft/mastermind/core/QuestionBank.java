/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one line of pins for question.
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
  List<Pin> getPins() {
    return pins;
  }

  void setPinValue(int position, Integer value) {
    getPin(position).setValue(value);
  }

  /**
   * Sets the values of the pins to the entries in the given list.
   * 
   * @param pinValues The values of the pins.
   */
  void setPinValues(List<Integer> pinValues) {
    for (int i = 0; i < pinValues.size(); i++) {
      setPinValue(i, pinValues.get(i));
    }
  }

  /**
   * @param position
   * @return Pin at the given position
   */
  public Pin getPin(int position) {
    return pins.get(position);
  }

}
