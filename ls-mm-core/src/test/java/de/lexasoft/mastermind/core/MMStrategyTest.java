/**
 * 
 */
package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Axel
 *
 */
class MMStrategyTest {

  private MMStrategy cut;
  private final static NrOfColors NR_OF_COLORS = new NrOfColors(6);
  private final static NrOfHoles NR_OF_HOLES = new NrOfHoles(4);

  /**
   * Create the cut.
   */
  @BeforeEach
  void prepareTestCase() {
    cut = new MMStrategy(NR_OF_COLORS, NR_OF_HOLES);
  }

  /**
   * Test method for {@link de.lexasoft.mastermind.core.MMStrategy#MMStrategy()}.
   */
  @Test
  final void testMMStrategy() {
    assertNull(cut.getStillPossibleCombinations(), "List of combinations must be null after creation.");
  }

}
