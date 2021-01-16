package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;

/**
 * Tests the performance of the strategy algorithm.
 * 
 * @author Axel
 *
 */
class MMStrategyPerformanceTest {

  private MMStrategy cut;
  private static final NrOfColors NR_OF_COLORS = new NrOfColors(10);
  private static final NrOfHoles NR_OF_HOLES = new NrOfHoles(6);

  @BeforeEach
  void prepareTestCase() {
    cut = new MMStrategy(NR_OF_COLORS, NR_OF_HOLES);
  }

  @Test
  final void testNextGuess() {
    QuestionBank lastGuess = BankFactory.createQuestionBank(NR_OF_COLORS, NR_OF_HOLES, new int[] { 2, 7, 1, 1, 6, 3 });
    AnswerBank lastAnswer = new AnswerBank(NR_OF_HOLES);
    lastAnswer.addWhitePins(2);
    lastAnswer.addBlackPins(2);

    long time = System.currentTimeMillis();
    // Call for the next step
    QuestionBank result = cut.nextGuess(lastGuess, lastAnswer);

    assertNotNull(result, "Result must not be null");
    assertEquals(6, result.getNrOfHoles().getValue(), "The result must have 6 holes.");
    time = System.currentTimeMillis() - time;
    System.out.println("Time needed (in ms): " + time);
  }

}
