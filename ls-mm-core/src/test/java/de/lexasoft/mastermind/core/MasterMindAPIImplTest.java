package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mastermind.core.api.AnswerPin;
import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.NrOfMoves;
import de.lexasoft.mastermind.core.api.Pin;
import de.lexasoft.mastermind.core.api.QuestionPin;

class MasterMindAPIImplTest {

	private MasterMindAPIImpl cut;

	private final static NrOfHoles NR_OF_HOLES = NrOfHoles.of(4);
	private final static NrOfColors NR_OF_COLORS = NrOfColors.of(6);
	private final static NrOfMoves NR_OF_MOVES = NrOfMoves.of(6);

	@BeforeEach
	void prepareTestCase() {
		cut = new MasterMindAPIImpl(NR_OF_HOLES, NR_OF_COLORS, NR_OF_MOVES);
	}

	@Test
	final void testCreateSolution() {
		List<QuestionPin> pins = cut.createSolution();
		assertNotNull(pins, "Solution must not be null.");
		assertEquals(NR_OF_HOLES.value(), pins.size(), "Must have a pin for every hole.");
	}

	@Test
	final void testAnswerQuestion() {
		cut.createSolution();
		List<QuestionPin> question = BankFactory.createListFromArray(NR_OF_COLORS, new int[] { 0, 1, 2, 3 });
		List<AnswerPin> answer = cut.answerQuestion(question);
		assertNotNull(answer, "Answer must not be null");
		for (Pin pin : answer) {
			Integer colorValue = pin.color().value();
			assertTrue((colorValue == 0) || (colorValue == 1));
		}
	}
}
