/**
 * 
 */
package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.lexasoft.mastermind.core.api.AnswerPinColor;

/**
 * Make sure, only valid value are accepted.
 * 
 * @author Axel
 */
class AnswerPinColorTest {

  /**
   * Test method for
   * {@link de.lexasoft.mastermind.core.api.AnswerPinColor#AnswerPinColor(java.lang.Integer)}.
   * Should accept a valid value to the color.
   */
  @ParameterizedTest
  @ValueSource(ints = { 0, 1 })
  final void testAnswerPinColor_Valid(int value) {
    AnswerPinColor cut = new AnswerPinColor(value);
    assertEquals(value, cut.getValue(), "Valid value not accepted and set as value.");
  }

  /**
   * Test method for
   * {@link de.lexasoft.mastermind.core.api.AnswerPinColor#AnswerPinColor(java.lang.Integer)}.
   * Should reject an invalid value to the color.
   */
  @ParameterizedTest
  @ValueSource(ints = { -1, 2, 3 })
  final void testAnswerPinColor_Invalid(int value) {
    assertThrows(IllegalArgumentException.class, () -> {
      new AnswerPinColor(value);
    });
  }
}
