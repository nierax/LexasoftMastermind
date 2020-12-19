package de.lexasoft.mastermind.core;

import de.lexasoft.common.model.Range;
import de.lexasoft.common.model.Value;

/**
 * Represents a single pin on the board.
 * <p>
 * A pin can have a value in a range from 0 to a maximum value, which depends on
 * the games difficulty. Attention: 0-based!
 * 
 * @author Axel
 */
public class Pin extends Value<Integer> {

  /**
   * Create a pin with number of colors.
   * 
   * @param nrOfColors
   */
  public Pin(Integer nrOfColors) {
    super(new Range<Integer>(0, nrOfColors - 1));
  }

  /**
   * @return Number of colors, this pin can range.
   */
  public Integer getNrOfColors() {
    return ((Range<Integer>) getValidator()).getMax() + 1;
  }
}
