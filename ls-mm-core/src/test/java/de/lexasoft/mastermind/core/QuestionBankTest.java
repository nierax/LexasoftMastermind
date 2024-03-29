package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.mastermind.core.QuestionBank.InternalQuestionPin;
import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.QuestionPin;

/**
 * Tests the important methods in question bank.
 * 
 * @author Axel
 */
class QuestionBankTest {

	private QuestionBank question;
	private QuestionBank solution;

	private static final NrOfColors NR_OF_COLORS = NrOfColors.of(6);
	private static final NrOfHoles NR_OF_HOLES = NrOfHoles.of(4);

	@BeforeEach
	void prepareTestCase() {
		question = new QuestionBank(NrOfHoles.of(4), NR_OF_COLORS);
		solution = new QuestionBank(NrOfHoles.of(4), NR_OF_COLORS);
	}

	/**
	 * Provides the arguments for the Method testAnswerForBank.
	 * 
	 * @return Arguments for test method testAnswerForBank
	 */
	private static Stream<Arguments> provideTestCases() {
		return Stream.of(Arguments.of(new int[] { 0, 1, 2, 3 }, new int[] { 0, 1, 2, 3, }, 4, 0),
		    Arguments.of(new int[] { 0, 1, 2, 3 }, new int[] { 4, 4, 5, 5 }, 0, 0),
		    Arguments.of(new int[] { 0, 1, 2, 3 }, new int[] { 4, 2, 3, 5 }, 0, 2),
		    Arguments.of(new int[] { 4, 1, 2, 3 }, new int[] { 4, 1, 3, 5 }, 2, 1),
		    Arguments.of(new int[] { 1, 2, 2, 3 }, new int[] { 2, 1, 2, 4 }, 1, 2),
		    Arguments.of(new int[] { 1, 1, 2, 3 }, new int[] { 1, 4, 1, 5 }, 1, 1),
		    Arguments.of(new int[] { 1, 1, 2, 3 }, new int[] { 1, 1, 2, 3 }, 4, 0),
		    Arguments.of(new int[] { 0, 1, 2, 3 }, new int[] { 0, 1, 2, 4 }, 3, 0),
		    Arguments.of(new int[] { 0, 1, 1, 2 }, new int[] { 0, 3, 0, 4 }, 1, 0),
		    Arguments.of(new int[] { 0, 1, 2, 3 }, new int[] { 4, 4, 4, 2 }, 0, 1),
		    Arguments.of(new int[] { 0, 1, 1, 1 }, new int[] { 4, 0, 4, 4 }, 0, 1),
		    Arguments.of(new int[] { 0, 1, 2, 3 }, new int[] { 3, 2, 1, 0 }, 0, 4));
	}

	@ParameterizedTest
	@MethodSource("provideTestCases")
	void testAnswerForBank(int[] question, int[] solution, int expectedBlack, int expectedWhite) {
		this.question.setPins(BankFactory.createListFromArray(NR_OF_COLORS, question));
		this.solution.setPins(BankFactory.createListFromArray(NR_OF_COLORS, solution));
		// Test
		AnswerBank answer = this.question.answer(this.solution);

		// Asserts
		assertEquals(expectedBlack, answer.getNrOfBlackPins());
		assertEquals(expectedWhite, answer.getNrOfWhitePins());
		// Both solution and question must be free for another answer
		for (Hole<QuestionPin> hole : this.solution.getHoles()) {
			assertFalse(((InternalQuestionPin) hole.pin()).isCounted());
		}
		for (Hole<QuestionPin> hole : this.solution.getHoles()) {
			assertFalse(((InternalQuestionPin) hole.pin()).isCounted());
		}
		assertTrue(answer.isGiven(), "The answer must be marked as given.");
	}

	/**
	 * @return Parameters to run
	 *         {@link #testAnswerForBank_BanksNotFilled(int[], int[])}
	 * 
	 */
	private static Stream<Arguments> testAnswerForBank_BanksNotFilled() {
		return Stream.of(Arguments.of(new int[] { 0, 1, 2 }, new int[] { 0, 1, 2 }),
		    Arguments.of(new int[] { 0, 1, 2, 3 }, new int[] { 0, 1, 2 }),
		    Arguments.of(new int[] { 0, 1, 2 }, new int[] { 0, 1, 2, 3 }));
	}

	/**
	 * Test must fail, if at least one of the banks is not completely filled.
	 * 
	 * @param question
	 * @param solution
	 */
	@ParameterizedTest
	@MethodSource
	void testAnswerForBank_BanksNotFilled(int[] question, int[] solution) {
		this.question.setPins(BankFactory.createListFromArray(NR_OF_COLORS, question));
		this.solution.setPins(BankFactory.createListFromArray(NR_OF_COLORS, solution));

		assertThrows(IllegalArgumentException.class, () -> {
			this.question.answer(this.solution);
		});
	}

	@Test
	void testAnswerForBank_SameNumberOfHoles() {
		// reset solution to an higher number holes.
		solution = new QuestionBank(NrOfHoles.of(5), NrOfColors.of(6));
		solution.setPins(BankFactory.createListFromArray(NR_OF_COLORS, new int[] { 0, 1, 2, 3, 4 }));
		question.setPins(BankFactory.createListFromArray(NR_OF_COLORS, new int[] { 0, 1, 2, 3 }));

		assertThrows(IllegalArgumentException.class, () -> {
			this.question.answer(this.solution);
		});
	}

	/**
	 * Tests, whether the setAllPinsCounted() and resetAllPinsCounted() methods set
	 * and reset all pins in the bank correctly.
	 */
	@Test
	void test_setResetAllPinsCounted() {
		solution.setPins(BankFactory.createListFromArray(NR_OF_COLORS, new int[] { 0, 1, 2, 3 }));
		solution.setAllPinsCounted();
		for (Hole<QuestionPin> hole : this.solution.getHoles()) {
			assertTrue(((InternalQuestionPin) hole.pin()).isCounted());
		}
		solution.resetAllPinsCounted();
		for (Hole<QuestionPin> hole : this.solution.getHoles()) {
			assertFalse(((InternalQuestionPin) hole.pin()).isCounted());
		}
	}

	@Test
	void testRoll() {
		assertFalse(question.isCompletelyFilled(), "After creation holes must be empty.");
		QuestionBank result = question.roll();
		assertNotNull(result, "The retruned bank must not be null.");
		assertSame(result, question, "The retruned bank must be the same object as the called.");
		assertTrue(result.isCompletelyFilled(), "After roll all pins mus have a value.");
	}

	@Test
	void testCopy() {
		// Prepare
		QuestionBank cut = new QuestionBank(new int[] { 0, 1, 2, 4 }, NR_OF_COLORS);
		// Run
		QuestionBank copy = cut.copy();
		// Assert
		assertTrue(cut.equals(copy));
	}

}
