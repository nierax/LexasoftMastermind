/**
 * 
 */
package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.mastermind.core.api.MasterMindValidationException;
import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;

/**
 * @author nierax
 *
 */
class MMStrategyTest {

	private MMStrategy cut;
	private final static NrOfColors NR_OF_COLORS = NrOfColors.of(6);
	private final static NrOfHoles NR_OF_HOLES = NrOfHoles.of(4);
	private List<QuestionBank> possibleCombinations;

	/**
	 * Create the cut.
	 */
	@BeforeEach
	void prepareTestCase() {
		cut = new MMStrategy(NR_OF_COLORS, NR_OF_HOLES);
		// We produce a set possible combinations
		possibleCombinations = new ArrayList<>();
		possibleCombinations.add(new QuestionBank(new int[] { 0, 1, 2, 3 }, NR_OF_COLORS));
		possibleCombinations.add(new QuestionBank(new int[] { 0, 0, 0, 0 }, NR_OF_COLORS));
		possibleCombinations.add(new QuestionBank(new int[] { 5, 5, 5, 5 }, NR_OF_COLORS));
		possibleCombinations.add(new QuestionBank(new int[] { 0, 0, 1, 2 }, NR_OF_COLORS));
		possibleCombinations.add(new QuestionBank(new int[] { 3, 2, 1, 0 }, NR_OF_COLORS));
		possibleCombinations.add(new QuestionBank(new int[] { 4, 4, 5, 5 }, NR_OF_COLORS));
		possibleCombinations.add(new QuestionBank(new int[] { 5, 4, 3, 3 }, NR_OF_COLORS));
	}

	private static QuestionBank createQuestion(int[] values) {
		return BankFactory.createQuestionBank(NR_OF_COLORS, NR_OF_HOLES, values);
	}

	private static AnswerBank createAnswer(int nrOfWhite, int nrOfBlack) {
		AnswerBank answer = new AnswerBank(NR_OF_HOLES);
		answer.addWhitePins(nrOfWhite);
		answer.addBlackPins(nrOfBlack);
		return answer;
	}

	private boolean hasEntry(PossibleCombinations result, QuestionBank expected) {
		for (QuestionBank pins : result) {
			if (pins.equals(expected)) {
				return true;
			}
		}
		return false;
	}

	void assertNextGuess(QuestionBank nextGuess) {
		assertNotNull(nextGuess, "Next guess must not be null.");
		assertTrue(nextGuess.isCompletelyFilled(), "Next guess must have all holes filled.");
	}

	private static Stream<Arguments> testNextGuess() {
		return Stream.of(
		    // The combination on position 0 is found correctly -> Only 0 remains
		    Arguments.of(createQuestion(new int[] { 0, 1, 2, 3 }), createAnswer(0, 4), new int[] { 0 }),
		    Arguments.of(createQuestion(new int[] { 0, 0, 2, 3 }), createAnswer(0, 2), new int[] { 1 }),
		    Arguments.of(createQuestion(new int[] { 1, 2, 3, 4 }), createAnswer(2, 0), new int[] { 3 }),
		    Arguments.of(createQuestion(new int[] { 1, 1, 2, 2 }), createAnswer(0, 0), new int[] { 1, 2, 5, 6 }),
		    Arguments.of(createQuestion(new int[] { 0, 1, 2, 3 }), createAnswer(4, 0), new int[] { 4 }));
	}

	/**
	 * Tests, whether the nextGuess method is able to eliminate the answers, not
	 * suitable to the combination of last guess and answer.
	 * <p>
	 * The reference list is the one, set in {@link #prepareTestCase()}
	 * 
	 * @param lastGuess   The last guess made.
	 * @param answer      The answer to that
	 * @param entriesLeft The index numbers, left after next guess.
	 */
	@ParameterizedTest
	@MethodSource
	void testNextGuess(QuestionBank lastGuess, AnswerBank answer, int[] entriesLeft) {
		// Mock solution set from previous guesses.
		cut.setLeftoverCombinations(LeftoverCombinations.fromList(possibleCombinations));

		QuestionBank nextGuess = cut.nextGuess(lastGuess, answer);

		assertNextGuess(nextGuess);
		PossibleCombinations result = cut.getLeftoverCombinations();
		assertEquals(entriesLeft.length, result.nrOfCombinationsLeft(), "Number of remaining combinations is not correct.");
		for (int i = 0; i < entriesLeft.length; i++) {
			assertTrue(hasEntry(result, possibleCombinations.get(entriesLeft[i])));
		}
	}

	/**
	 * 
	 */
	@Test
	void testNextGuess_FirstCall() {

		QuestionBank nextGuess = cut.nextGuess(createQuestion(new int[] { 0, 1, 2, 3 }), createAnswer(0, 2));
		assertNotNull(cut.getLeftoverCombinations(), "Possible combinations must be set after first call.");
		int nrOfCombinationsLeft = cut.nrOfLeftCombinations();
		assertEquals(96, nrOfCombinationsLeft, "There must be 96 combinations left after first guess in this test case.");
		System.out.println("Number of combinationsleft: " + nrOfCombinationsLeft);
		assertNextGuess(nextGuess);
	}

	@Test
	void testNextGuess_NoMorePossibleCombinations() {
		// Mock solution set from previous guesses.
		cut.setLeftoverCombinations(LeftoverCombinations.fromList(possibleCombinations));
		assertThrows(MasterMindValidationException.class, () -> {
			// This should cause an exception, as there is no solution for this
			// combination of guess and answer.
			cut.nextGuess(createQuestion(new int[] { 5, 5, 3, 3 }), createAnswer(3, 0));
		});
	}

}
