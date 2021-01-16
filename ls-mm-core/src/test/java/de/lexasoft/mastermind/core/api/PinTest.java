package de.lexasoft.mastermind.core.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.common.model.Validator;

/**
 * Tests a pin as a component.
 * 
 * @author Axel
 */
public class PinTest {

  private Pin cut;
  private final static Validator<PinColor> ALWAYS_TRUE = new Validator<PinColor>() {

    @Override
    public boolean validate(PinColor value) {
      return true;
    }
  };

  @BeforeEach
  void prepare() {
    cut = new Pin(ALWAYS_TRUE, new PinColor(0));
  }

  /**
   * Other pin has same value. True expected.
   */
  @Test
  void testComparePinEqual() {
    Pin otherPin = new Pin(ALWAYS_TRUE, new PinColor(0));
    assertTrue(cut.comparePin(otherPin), "Both pins have same value. Must be true");
  }

  /**
   * Other pin has a different value. False expected.
   */
  @Test
  void testComparePinNotEqual() {
    Pin otherPin = new Pin(ALWAYS_TRUE, new PinColor(5));
    assertFalse(cut.comparePin(otherPin), "Pins have different values. Must be false");
  }

}
