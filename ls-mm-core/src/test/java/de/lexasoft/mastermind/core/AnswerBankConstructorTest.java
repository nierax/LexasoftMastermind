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
   * Tests, whether the bank is initialized correctly after constructor. The list
   * must be initially empty.
   */
  @Test
  void testConstructorOk() {
    AnswerBank cut = new AnswerBank(new NrOfHoles(4));
    List<Pin> pins = cut.getPins();
    assertEquals(0, pins.size());
  }

  /**
   * If the minimum number of pins is too low, an IllegalArgumentException is
   * thrown.
   */
  @Test
  void testConstructorMinimumNrOfPins() {
    assertThrows(IllegalArgumentException.class, () -> {
      new AnswerBank(new NrOfHoles(3));
    });
  }

}
