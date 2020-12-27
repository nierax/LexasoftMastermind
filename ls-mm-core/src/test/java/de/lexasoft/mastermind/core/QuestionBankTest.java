package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

/**
 * Tests the important methods in question bank.
 * 
 * @author Axel
 */
class QuestionBankTest {

  private QuestionBank question;
  private QuestionBank solution;

  private static final NrOfColors NR_OF_COLORS = new NrOfColors(6);

  @BeforeEach
  void prepareTestCase() {
    question = new QuestionBank(new NrOfHoles(4), new NrOfColors(6));
    solution = new QuestionBank(new NrOfHoles(4), new NrOfColors(6));
  }

  /**
   * Helps to create a list form an integer array
   * 
   * @param array
   * @return
   */
  private List<Pin> createListFromArray(int[] array) {
    List<Pin> list = new ArrayList<>();
    for (int i = 0; i < array.length; i++) {
      list.add(new Pin(new QuestionPinColor(NR_OF_COLORS, array[i])));
    }
    return list;
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
    this.question.setPins(createListFromArray(question));
    this.solution.setPins(createListFromArray(solution));
    // Test
    AnswerBank answer = this.question.answer(this.solution);

    // Asserts
    assertEquals(expectedBlack, answer.getNrOfBlackPins());
    assertEquals(expectedWhite, answer.getNrOfWhitePins());
    // Both solution and question must be free for another answer
    for (Hole hole : this.solution.getHoles()) {
      assertFalse(hole.getPin().isCounted());
    }
    for (Hole hole : this.solution.getHoles()) {
      assertFalse(hole.getPin().isCounted());
    }
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
    this.question.setPins(createListFromArray(question));
    this.solution.setPins(createListFromArray(solution));

    assertThrows(IllegalArgumentException.class, () -> {
      this.question.answer(this.solution);
    });
  }

  @Test
  void testAnswerForBank_SameNumberOfHoles() {
    // reset solution to an higher number holes.
    solution = new QuestionBank(new NrOfHoles(5), new NrOfColors(6));
    solution.setPins(createListFromArray(new int[] { 0, 1, 2, 3, 4 }));
    question.setPins(createListFromArray(new int[] { 0, 1, 2, 3 }));

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
    solution.setPins(createListFromArray(new int[] { 0, 1, 2, 3 }));
    solution.setAllPinsCounted();
    for (Hole hole : this.solution.getHoles()) {
      assertTrue(hole.getPin().isCounted());
    }
    solution.resetAllPinsCounted();
    for (Hole hole : this.solution.getHoles()) {
      assertFalse(hole.getPin().isCounted());
    }
  }

}
