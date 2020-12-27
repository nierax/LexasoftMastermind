package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Make sure, only valid value are accepted.
 * 
 * @author Axel
 */
class QuestionPinColorTest {

  private final static NrOfColors NR_OF_COLORS = new NrOfColors(6);

  /**
   * Valid value must be set as the color value
   */
  @ParameterizedTest
  @ValueSource(ints = { 0, 1, 2, 3, 4, 5 })
  final void testQuestionPinColor_Valid(int value) {
    QuestionPinColor cut = new QuestionPinColor(NR_OF_COLORS, value);
    assertEquals(value, cut.getValue(), "Value must be set, if valid.");
  }

  /**
   * Invalid value must be rejected.
   */
  @ParameterizedTest
  @ValueSource(ints = { -1, 6, 10, 100, 10000, -10 })
  final void testQuestionPinColor_Invalid(int value) {
    assertThrows(IllegalArgumentException.class, () -> {
      new QuestionPinColor(NR_OF_COLORS, value);
    });
  }

}
