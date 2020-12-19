/**
 * 
 */
package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests the bank class.
 * 
 * @author Axel
 */
class AnswerBankConstructorTest {

  /**
   * Tests, whether the bank and the pins in it are initialized correctly after
   * constructor.
   */
  @Test
  void testConstructorOk() {
    AnswerBank cut = new AnswerBank(4);
    List<Pin> pins = cut.getPins();
    assertEquals(4, pins.size());
    for (Pin pin : pins) {
      assertEquals(2, pin.getNrOfColors());
    }
  }

  /**
   * If the minimum number of pins is too low, an IllegalArgumentException is
   * thrown.
   */
  @Test
  void testConstructorMinimumNrOfPins() {
    assertThrows(IllegalArgumentException.class, () -> {
      new AnswerBank(3);
    });
  }

}
