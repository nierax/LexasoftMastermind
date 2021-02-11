package de.lexasoft.mastermind.core.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests the constructor of the InternalQuestionPin class.
 * 
 * @author Axel
 *
 */
class QuestionPinTest {

	private final static NrOfColors NR_OF_COLORS = new NrOfColors(6);

	/**
	 * Must be valid for all values between 0 and 6 (because of the declaration of
	 * NrOfColors above.
	 * 
	 * @param colorValue
	 */
	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 2, 3, 4, 5 })
	final void testQuestionPin_Constructor_Ok(int colorValue) {
		QuestionPin cut = QuestionPin.of(NR_OF_COLORS, PinColor.of(colorValue));
		assertNotNull(cut, "InternalQuestionPin must not be null after constructor");
		assertEquals(colorValue, cut.color().value(), "Value of color is not correct.");
	}

	/**
	 * Must not be valid for values below 0 and above or equal NrOfColors.
	 * 
	 * @param colorValue
	 */
	@ParameterizedTest
	@ValueSource(ints = { -1, 6, 7 })
	final void testQuestionPin_Constructor_Fail(int colorValue) {
		assertThrows(IllegalArgumentException.class, () -> {
			QuestionPin.of(NR_OF_COLORS, PinColor.of(colorValue));
		});
	}

}
