package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
   * Adds the given number to the moves.
   * <p>
   * If the cut was just created, this corresponds to the moveIndex, the cut has
   * afterwards.
   * 
   * @param numberToAdd The number of moves to add.
   */
  private void fillCutWithMoves(int numberToAdd) {
    for (int i = 0; i < numberToAdd; i++) {
      cut.nextMove();
    }
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

  private static Stream<Arguments> testAnswer_AnswerBank_Ok() {
    return Stream.of(Arguments.of(0, 0, 0, GameState.MOVE_OPEN), Arguments.of(1, 2, 0, GameState.MOVE_OPEN),
        Arguments.of(2, 2, 5, GameState.LOST), Arguments.of(0, 4, 4, GameState.WON));
  }

  /**
   * Answer bank ok, so what is the right status?
   * 
   * @param nrOfWhites Number of white pins in the answer
   * @param nrOfBlacks Number of black pins in the answer
   * @param moveIndex  The index of the move in the start of the test
   * @param expected   The expected result
   */
  @ParameterizedTest
  @MethodSource
  void testAnswer_AnswerBank_Ok(int nrOfWhites, int nrOfBlacks, int moveIndex, GameState expected) {
    // Set moveIndex to be the current position of moves.
    fillCutWithMoves(moveIndex);

    AnswerBank answer = new AnswerBank(NR_OF_HOLES);
    answer.addWhitePins(nrOfWhites);
    answer.addBlackPins(nrOfBlacks);

    // Now call the answer method
    GameState received = cut.answer(answer);

    // Check
    Move move = cut.getMove(moveIndex);
    assertEquals(expected, received, "Not the expected GameState delivered.");
    assertEquals(nrOfWhites, move.getAnswer().getNrOfWhitePins(), "Number of white pins not set correctly.");
    assertEquals(nrOfBlacks, move.getAnswer().getNrOfBlackPins(), "Number of black pins not set correctly.");
    if (expected == GameState.MOVE_OPEN) {
      assertEquals(moveIndex + 1, cut.getMoveIndex(), "moveIndex was not increased.");
    }
  }

  private static Stream<Arguments> testNextMoveAllowed() {
    return Stream.of(Arguments.of(0, true), Arguments.of(1, true), Arguments.of(2, true), Arguments.of(3, true),
        Arguments.of(4, true), Arguments.of(5, false));
  }

  @ParameterizedTest
  @MethodSource
  void testNextMoveAllowed(int moveIndex, boolean expected) {
    fillCutWithMoves(moveIndex);
    assertEquals(expected, cut.nextMoveAllowed());
  }
}
