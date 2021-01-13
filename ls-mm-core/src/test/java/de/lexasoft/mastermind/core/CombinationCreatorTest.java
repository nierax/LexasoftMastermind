/**
 * 
 */
package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.QuestionPin;

/**
 * @author admin
 *
 */
class CombinationCreatorTest {

  private final static NrOfColors NR_OF_COLORS = new NrOfColors(6);
  private final static NrOfHoles NR_OF_HOLES = new NrOfHoles(4);
  private CombinationCreator cut = new CombinationCreator(NR_OF_COLORS, NR_OF_HOLES);

  /**
   * Test method for
   * {@link de.lexasoft.mastermind.core.CombinationCreator#next()}.
   */
  @Test
  final void testNext() {
    List<List<QuestionPin>> combinations = new ArrayList<>();
    for (List<QuestionPin> pin : cut) {
      combinations.add(pin);
      System.out.println(pin);
    }
    assertEquals(1296, combinations.size());
    assertCombination(combinations.get(0), new int[] { 0, 0, 0, 0 });
    assertCombination(combinations.get(1), new int[] { 1, 0, 0, 0 });
    assertCombination(combinations.get(6), new int[] { 0, 1, 0, 0 });
    assertCombination(combinations.get(12), new int[] { 0, 2, 0, 0 });
    assertCombination(combinations.get(35), new int[] { 5, 5, 0, 0 });
    assertCombination(combinations.get(36), new int[] { 0, 0, 1, 0 });
    assertCombination(combinations.get(215), new int[] { 5, 5, 5, 0 });
    assertCombination(combinations.get(216), new int[] { 0, 0, 0, 1 });
    assertCombination(combinations.get(1295), new int[] { 5, 5, 5, 5 });
  }

  /**
   * @param resultInQuestion
   */
  private void assertCombination(List<QuestionPin> resultInQuestion, int[] expected) {
    for (int i = 0; i < resultInQuestion.size(); i++) {
      assertEquals(expected[i], resultInQuestion.get(i).getColor().getValue());
    }
  }

}
