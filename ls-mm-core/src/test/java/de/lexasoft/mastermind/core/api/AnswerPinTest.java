/**
 * 
 */
package de.lexasoft.mastermind.core.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author admin
 *
 */
class AnswerPinTest {

	/**
	 * Test method for
	 * {@link de.lexasoft.mastermind.core.api.AnswerPin#AnswerPin(de.lexasoft.mastermind.core.api.PinColor)}.
	 */
	@ParameterizedTest
	@ValueSource(ints = { 0, 1 })
	final void testAnswerPin(int colorValue) {
		AnswerPin cut = AnswerPin.of(PinColor.of(colorValue));
		assertNotNull(cut, "InternalQuestionPin must not be null after constructor");
		assertEquals(colorValue, cut.color().value(), "Value of color is not correct.");
	}

	/**
	 * Must not be valid for values below 0 (white) and above 1 (black).
	 * 
	 * @param colorValue
	 */
	@ParameterizedTest
	@ValueSource(ints = { -1, 2, 3, 4, 5, 6, 7 })
	final void testQuestionPin_Constructor_Fail(int colorValue) {
		assertThrows(IllegalArgumentException.class, () -> {
			AnswerPin.of(PinColor.of(colorValue));
		});
	}

}
