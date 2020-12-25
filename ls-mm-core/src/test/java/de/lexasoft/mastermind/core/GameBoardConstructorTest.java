package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class GameBoardConstructorTest {

  /**
   * Constructor should instantiate list of moves. The current turn is 0;
   */
  @Test
  void testGameBoard() {
    GameBoard cut = new GameBoard(new NrOfHoles(4), new NrOfColors(6), new NrOfMoves(6));
    assertNotNull(cut.getMoves(), "List must be created after constructor");
    assertEquals(0, cut.getMoveIndex(), "Inital move number must be 0");
  }

}
