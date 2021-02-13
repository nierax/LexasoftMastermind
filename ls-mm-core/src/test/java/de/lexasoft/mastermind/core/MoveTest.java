/**
 * 
 */
package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.PinColor;
import de.lexasoft.mastermind.core.api.QuestionPin;

/**
 * Tests the move class
 * 
 * @author nierax
 */
class MoveTest {

	private Move cut;
	private static final NrOfHoles NR_OF_HOLES = NrOfHoles.of(4);
	private static final NrOfColors NR_OF_COLORS = NrOfColors.of(6);

	@BeforeEach
	void prepareTextCase() {
		cut = new Move(NR_OF_HOLES, NR_OF_COLORS);
	}

	/**
	 * Test method for {@link de.lexasoft.mastermind.core.Move#isComplete()}.
	 */
	@Test
	final void testIsComplete() {
		// right after creation the move must be incomplete.
		assertFalse(cut.isComplete(), "Must be incomplete, right after creation");
		// QuestionBank is complete, but answer is not given => False
		for (Hole<QuestionPin> hole : cut.getQuestion().getHoles()) {
			hole.stickPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(0)));
		}
		assertFalse(cut.isComplete(), "Must be incomplete, if the question is complete, but the answer is not given.");
		// AnswerBank is set complete as well -> True
		cut.getAnswer().setGiven();
		assertTrue(cut.isComplete(), "Must be complete now");
		// Remove one pin from question. Now it should be considered false again
		cut.getQuestion().removePin(0);
		assertFalse(cut.isComplete(), "Must be incomplete, as the first hole in the question is empty.");
	}

}
