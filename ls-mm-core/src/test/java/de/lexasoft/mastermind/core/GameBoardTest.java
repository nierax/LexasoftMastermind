package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameBoardTest {

  private GameBoard cut;
  private static final NrOfHoles NR_OF_HOLES = new NrOfHoles(4);
  private static final NrOfColors NR_OF_COLORS = new NrOfColors(6);
  private static final NrOfMoves NR_OF_MOVES = new NrOfMoves(6);

  @BeforeEach
  void prepareTestCase() {
    cut = new GameBoard(NR_OF_HOLES, NR_OF_COLORS, NR_OF_MOVES);
  }

  /**
   * Constructor should instantiate list of moves. The current turn is 0;
   */
  @Test
  void testGameBoard() {
    assertNotNull(cut.getMoves(), "List must be created after constructor");
    assertEquals(0, cut.getMoveIndex(), "First move must be initialzed.");
    assertNotNull(cut.currentMove(), "Current move must be initialized.");
  }

  @Test
  void testNextMove() {
    Move move = cut.nextMove();
    assertNotNull(move, "The new move object must not be null.");
    assertEquals(1, cut.getMoveIndex(), "After the first move the move index should be 1.");
    assertEquals(NR_OF_HOLES.getValue(), move.getQuestion().getNrOfHoles().getValue(),
        "Question in move must have same number of holes as the board.");
    assertEquals(NR_OF_HOLES.getValue(), move.getAnswer().getNrOfHoles().getValue(),
        "Answer in move must have same number of holes as the board.");
    assertEquals(NR_OF_COLORS.getValue(), move.getQuestion().getNrOfColors().getValue(),
        "Question in move must have same number of colors as the board.");
    assertSame(move, cut.currentMove(), "Current move method mus return the same object as with next move created.");
  }

}
