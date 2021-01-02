/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.ArrayList;
import java.util.List;

import de.lexasoft.mastermind.core.api.GameState;
import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.NrOfMoves;

/**
 * Represents the board, on which the game is played, thus being the anchor
 * object for the whole game.
 * 
 * @author Axel
 */
public class GameBoard {

  private List<Move> moves;
  private NrOfMoves maxNrOfMoves;
  private NrOfHoles nrOfHoles;
  private NrOfColors nrOfColors;
  private QuestionBank solution;
  private GameState state;

  /**
   * Creates the game board with the given number of colors and pins and the
   * maximal number of moves allowed.
   * <p>
   * Die List of moves is kept empty at start, It will be filled with every new
   * move.
   * 
   * @param nrOfPins     The number of pins
   * @param nrOfColors   The number of colors
   * @param maxNrOfMoves the maximum number of pins allowed.
   */
  public GameBoard(NrOfHoles nrOfHoles, NrOfColors nrOfColors, NrOfMoves maxNrOfMoves) {
    moves = new ArrayList<Move>();
    this.maxNrOfMoves = maxNrOfMoves;
    this.nrOfColors = nrOfColors;
    this.nrOfHoles = nrOfHoles;
    createNextMove();
    state = GameState.MOVE_OPEN;
  }

  /**
   * Does the next move and delivers the object of this move.
   * 
   * @return The object of the newly created move.
   */
  public Move nextMove() {
    return createNextMove();
  }

  /**
   * @return
   */
  private Move createNextMove() {
    Move move = new Move(nrOfHoles, nrOfColors);
    moves.add(move);
    return move;
  }

  /**
   * Is there one more move allowed or is the board already filled with moves?
   * 
   * @return True, if at least one more move is allowed, false otherwise.
   */
  public boolean nextMoveAllowed() {
    return getMoveIndex() < getMaxNrOfMoves().getValue() - 1;
  }

  /**
   * Gets the current move without moving forward. If the {@link #nextMove()}
   * method was not called before, the method will return null.
   * 
   * @return The current move.
   */
  public Move currentMove() {
    int index = getMoveIndex();
    if (index < 0) {
      return null;
    }
    return getMove(index);
  }

  /**
   * The index of the current move, 0-based. Must be -1 directly after creation.
   * 
   * @return Index of the current move, 0-based.
   */
  public Integer getMoveIndex() {
    return moves.size() - 1;
  }

  /**
   * Gets the move on position index.
   * <p>
   * Not part of the official api of this class.
   * 
   * @param index The position of the move.
   * @return The move on position index
   */
  Move getMove(int index) {
    return this.moves.get(index);
  }

  List<Move> getMoves() {
    return moves;
  }

  public NrOfMoves getMaxNrOfMoves() {
    return maxNrOfMoves;
  }

  public NrOfHoles getNrOfHoles() {
    return nrOfHoles;
  }

  public NrOfColors getNrOfColors() {
    return nrOfColors;
  }

  /**
   * @return Gets the bank with the questioned solution.
   */
  public QuestionBank getSolution() {
    return solution;
  }

  /**
   * Sets the bank with the questioned solution
   * 
   * @param solution The solution, which should be guessed.
   */
  public QuestionBank setSolution(QuestionBank solution) {
    this.solution = solution;
    return this.solution;
  }

  /**
   * Checks, whether the solution is known to the question board.
   * <p>
   * In certain game situations it can make a difference, if the solution is known
   * to the board or not. If the person has to guess the combination, the solution
   * must be known to validate the persons answer.
   * <p>
   * If the computer has to guess the combination, the validation can be asked
   * from the person, thus the solution should not be necessarily known to the
   * game board.
   * 
   * @return True, if the solution is known, false otherwise.
   */
  public boolean isSolutionKnown() {
    return solution != null;
  }

  /**
   * Takes the questions and compares them to the solution. With the answer, given
   * a new move with an empty question will be initialized.
   * 
   * @param question
   * @return
   */
  public AnswerBank answer(QuestionBank question) {
    QuestionBank myQuestion = currentMove().getQuestion();
    myQuestion.copy(question);
    if (myQuestion.isCompletelyFilled() && isSolutionKnown()) {
      return answer(myQuestion.answer(getSolution()));
    }
    setState(GameState.MOVE_OPEN);
    return currentMove().getAnswer();
  }

  /**
   * The currentMove is the one, that's been guessed. That's why, the answer is
   * registered at the current move.
   * <p>
   * After that the next move is created.
   * 
   * @param answer The answer to register.
   * @return The state of the game (open / lost / won)
   */
  public AnswerBank answer(AnswerBank answer) {
    AnswerBank answer2Give = currentMove().getAnswer();
    answer2Give.copy(answer);
    if (answer.isCorrect()) {
      setState(GameState.WON);
    } else if (nextMoveAllowed()) {
      nextMove();
      setState(GameState.MOVE_OPEN);
    } else {
      setState(GameState.LOST);
    }
    return answer2Give;
  }

  /**
   * Gets the last move, that was answered or null, if there wasn't.
   * 
   * @return The last move with a given answer.
   */
  public Move getLastAnsweredMove() {
    for (int i = getMoveIndex(); i >= 0; i--) {
      Move move = getMove(i);
      AnswerBank answer = move.getAnswer();
      if (answer.isGiven()) {
        return move;
      }
    }
    return null;
  }

  /**
   * @return the state
   */
  public GameState getState() {
    return state;
  }

  /**
   * @param state the state to set
   */
  private GameState setState(GameState state) {
    this.state = state;
    return this.state;
  }

}
