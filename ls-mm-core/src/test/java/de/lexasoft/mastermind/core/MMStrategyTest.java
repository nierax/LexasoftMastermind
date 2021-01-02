/**
 * 
 */
package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.Pin;

/**
 * @author Axel
 *
 */
class MMStrategyTest {

  private MMStrategy cut;
  private final static NrOfColors NR_OF_COLORS = new NrOfColors(6);
  private final static NrOfHoles NR_OF_HOLES = new NrOfHoles(4);
  private List<List<Pin>> possibleCombinations;

  /**
   * Create the cut.
   */
  @BeforeEach
  void prepareTestCase() {
    cut = new MMStrategy(NR_OF_COLORS, NR_OF_HOLES);
    // We produce a set possible combinations
    possibleCombinations = new ArrayList<>();
    possibleCombinations.add(BankFactory.createListFromArray(NR_OF_COLORS, new int[] { 0, 1, 2, 3 }));
    possibleCombinations.add(BankFactory.createListFromArray(NR_OF_COLORS, new int[] { 0, 0, 0, 0 }));
    possibleCombinations.add(BankFactory.createListFromArray(NR_OF_COLORS, new int[] { 5, 5, 5, 5 }));
    possibleCombinations.add(BankFactory.createListFromArray(NR_OF_COLORS, new int[] { 0, 0, 1, 2 }));
    possibleCombinations.add(BankFactory.createListFromArray(NR_OF_COLORS, new int[] { 3, 2, 1, 0 }));
    possibleCombinations.add(BankFactory.createListFromArray(NR_OF_COLORS, new int[] { 4, 4, 5, 5 }));
    possibleCombinations.add(BankFactory.createListFromArray(NR_OF_COLORS, new int[] { 5, 4, 3, 3 }));
    cut.setStillPossibleCombinations(possibleCombinations);
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

  /**
   * Test method for {@link de.lexasoft.mastermind.core.MMStrategy#MMStrategy()}.
   */
  @Test
  final void testMMStrategy() {
    MMStrategy strategy = new MMStrategy(NR_OF_COLORS, NR_OF_HOLES);
    assertNull(strategy.getStillPossibleCombinations(), "List of combinations must be null after creation.");
  }

  private boolean hasEntry(List<List<Pin>> result, List<Pin> expected) {
    for (List<Pin> pins : result) {
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
    QuestionBank nextGuess = cut.nextGuess(lastGuess, answer);

    assertNextGuess(nextGuess);
    List<List<Pin>> result = cut.getStillPossibleCombinations();
    assertEquals(entriesLeft.length, result.size(), "Number of remaining combinations is not correct.");
    for (int i = 0; i < entriesLeft.length; i++) {
      assertTrue(hasEntry(result, possibleCombinations.get(entriesLeft[i])));
    }
  }

  /**
   * 
   */
  @Test
  void testNextGuess_FirstCall() {
    cut.setStillPossibleCombinations(null);
    QuestionBank nextGuess = cut.nextGuess(createQuestion(new int[] { 0, 1, 2, 3 }), createAnswer(0, 2));
    assertNotNull(cut.getStillPossibleCombinations(), "Possible combinations must be set after first call.");
    assertTrue(cut.getStillPossibleCombinations().size() > 1,
        "There must be possible combinations after first guess, as the result wasn't 4 blacks.");
    System.out.println("Number of combinationsleft: " + cut.getStillPossibleCombinations().size());
    assertNextGuess(nextGuess);
  }

}
