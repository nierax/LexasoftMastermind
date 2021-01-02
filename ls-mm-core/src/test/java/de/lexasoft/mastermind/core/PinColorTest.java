/**
 * 
 */
package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mastermind.core.api.MasterMindValidationException;
import de.lexasoft.mastermind.core.api.PinColor;

/**
 * Ensure, that color can not be changed after constructor.
 * 
 * @author Axel
 */
class PinColorTest {

  private PinColor cut;

  @BeforeEach
  void prepareTestCase() {
    cut = createColor(1);
  }

  private PinColor createColor(int value) {
    return new PinColor((Integer) -> {
      return true;
    }, value);
  }

  /**
   * Test method for {@link de.lexasoft.mastermind.core.api.PinColor#unsetValue()}.
   */
  @Test
  final void testUnsetValue() {
    assertThrows(MasterMindValidationException.class, () -> {
      cut.unsetValue();
    });
  }

  /**
   * Test method for
   * {@link de.lexasoft.mastermind.core.api.PinColor#setValue(java.lang.Integer)}.
   */
  @Test
  final void testSetValueInteger() {
    assertThrows(MasterMindValidationException.class, () -> {
      cut.setValue(2);
    });
  }

  @Test
  void testEquals() {
    PinColor theOtherColor = createColor(cut.getValue());
    assertTrue(cut.equals(theOtherColor));

    theOtherColor = createColor(cut.getValue() + 1);
    assertFalse(cut.equals(theOtherColor));
  }

}
