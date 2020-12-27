/**
 * 
 */
package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Ensure, that color can not be changed after constructor.
 * 
 * @author Axel
 */
class PinColorTest {

  private PinColor cut;

  @BeforeEach
  void prepareTestCase() {
    cut = new PinColor((Integer) -> {
      return true;
    }, 1);
  }

  /**
   * Test method for {@link de.lexasoft.mastermind.core.PinColor#unsetValue()}.
   */
  @Test
  final void testUnsetValue() {
    assertThrows(MasterMindValidationException.class, () -> {
      cut.unsetValue();
    });
  }

  /**
   * Test method for
   * {@link de.lexasoft.mastermind.core.PinColor#setValue(java.lang.Integer)}.
   */
  @Test
  final void testSetValueInteger() {
    assertThrows(MasterMindValidationException.class, () -> {
      cut.setValue(2);
    });
  }

}
