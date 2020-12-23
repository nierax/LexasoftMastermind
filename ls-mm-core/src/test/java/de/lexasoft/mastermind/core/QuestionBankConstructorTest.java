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
class QuestionBankConstructorTest {

  /**
   * Tests, whether the bank and the pins in it are initialized correctly after
   * constructor.
   */
  @Test
  void testConstructorOk() {
    QuestionBank cut = new QuestionBank(new NrOfPins(4), new NrOfColors(6));
    List<Pin> pins = cut.getPins();
    assertEquals(4, pins.size());
    for (Pin pin : pins) {
      assertEquals(6, pin.getNrOfColors());
    }
  }

  /**
   * If the minimum number of pins is too low, an IllegalArgumentException is
   * thrown.
   */
  @Test
  void testConstructorMinimumNrOfPins() {
    assertThrows(IllegalArgumentException.class, () -> {
      new QuestionBank(new NrOfPins(3), new NrOfColors(6));
    });
  }

  /**
   * If the minimum number of colors is too low, an IllegalArgumentException is
   * thrown.
   */
  @Test
  void testConstructorMinimumNrOfColors() {
    assertThrows(IllegalArgumentException.class, () -> {
      new QuestionBank(new NrOfPins(4), new NrOfColors(5));
    });
  }

}
