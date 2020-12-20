package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class QuestionBankAnswerTest {

  private QuestionBank question;
  private QuestionBank solution;

  @BeforeEach
  void prepareTestCase() {
    question = new QuestionBank(4, 6);
    solution = new QuestionBank(4, 6);
  }

  /**
   * Helps to create a list form an integer array
   * 
   * @param array
   * @return
   */
  private List<Integer> createListFromArray(int[] array) {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < array.length; i++) {
      list.add(i, array[i]);
    }
    return list;
  }

  /**
   * Provides the arguments for the Method testAnswerForBank.
   * 
   * @return Arguments for test method testAnswerForBank
   */
  private static Stream<Arguments> provideTestCases() {
    return Stream.of(Arguments.of(new int[] { 0, 1, 2, 3 }, new int[] { 0, 1, 2, 3, }, 4, 0));
  }

  @ParameterizedTest
  @MethodSource("provideTestCases")
  void testAnswerForBank(int[] question, int[] solution, int expectedBlack, int expectedWhite) {
    this.question.setPinValues(createListFromArray(question));
    this.solution.setPinValues(createListFromArray(solution));
    fail("Not yet implemented");
  }

}
