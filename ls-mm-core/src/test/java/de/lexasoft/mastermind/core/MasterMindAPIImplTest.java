package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.NrOfMoves;
import de.lexasoft.mastermind.core.api.Pin;

class MasterMindAPIImplTest {

  private MasterMindAPIImpl cut;

  private final static NrOfHoles NR_OF_HOLES = new NrOfHoles(4);
  private final static NrOfColors NR_OF_COLORS = new NrOfColors(6);
  private final static NrOfMoves NR_OF_MOVES = new NrOfMoves(6);

  @BeforeEach
  void prepareTestCase() {
    cut = new MasterMindAPIImpl(NR_OF_HOLES, NR_OF_COLORS, NR_OF_MOVES);
  }

  @Test
  final void testCreateSolution() {
    List<Pin> pins = cut.createSolution();
    assertNotNull(pins, "Solution must not be null.");
    assertEquals(NR_OF_HOLES.getValue(), pins.size(), "Must have a pin for every hole.");
  }

  @Test
  final void testAnswerQuestion() {
    cut.createSolution();
    List<Pin> question = BankFactory.createListFromArray(NR_OF_COLORS, new int[] { 0, 1, 2, 3 });
    List<Pin> answer = cut.answerQuestion(question);
    assertNotNull(answer, "Answer must not be null");
    for (Pin pin : answer) {
      Integer colorValue = pin.getColor().getValue();
      assertTrue((colorValue == 0) || (colorValue == 1));
    }
  }
}
