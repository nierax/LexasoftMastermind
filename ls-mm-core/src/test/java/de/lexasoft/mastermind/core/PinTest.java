package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests a pin as a component.
 * 
 * @author Axel
 */
public class PinTest {

  private Pin cut;
  private final static NrOfColors NR_OF_COLORS = new NrOfColors(6);

  @BeforeEach
  void prepare() {
    cut = new Pin(new QuestionPinColor(NR_OF_COLORS, 0));
  }

  /**
   * Other pin has same value. True expected.
   */
  @Test
  void testComparePinEqual() {
    Pin otherPin = new Pin(new QuestionPinColor(NR_OF_COLORS, 0));
    assertTrue(cut.comparePin(otherPin), "Both pins have same value. Must be true");
  }

  /**
   * Other pin has a different value. False expected.
   */
  @Test
  void testComparePinNotEqual() {
    Pin otherPin = new Pin(new QuestionPinColor(NR_OF_COLORS, 5));
    assertFalse(cut.comparePin(otherPin), "Pins have different values. Must be false");
  }

}
