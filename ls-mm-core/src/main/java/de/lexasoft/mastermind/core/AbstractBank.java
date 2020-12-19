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
public abstract class AbstractBank {

  private List<Pin> pins;

  /**
   * Creates a bank of pins with the given number of pins, each with a range of
   * the given number of colors.
   * 
   * @param nrOfPins   Number of pins in the bank.
   * @param nrOfColors Range of colors, every pin can represent.
   */
  public AbstractBank(Integer nrOfPins, Integer nrOfColors) {
    if (nrOfPins < getMinimumNrOfPins()) {
      throw new IllegalArgumentException(String.format("Minimum number of pins is %s ", getMinimumNrOfPins()));
    }
    if (nrOfColors < getMinimumNrOfColors()) {
      throw new IllegalArgumentException(String.format("Minimum number of colors is %s ", getMinimumNrOfColors()));
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

  /**
   * Minimum number of pins, allowed in this bank.
   * 
   * @return The minimum number of pins;
   */
  protected abstract Integer getMinimumNrOfPins();

  /**
   * Minimum number of colors, allowed in this bank.
   * 
   * @return The minimum number of colors;
   */
  protected abstract Integer getMinimumNrOfColors();

}
