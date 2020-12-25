/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.ArrayList;
import java.util.List;

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
  }

  /**
   * Does the next move and delivers the object of this move.
   * 
   * @return The object of the newly created move.
   */
  public Move nextMove() {
    Move move = new Move(nrOfHoles, nrOfColors);
    moves.add(move);
    return move;
  }

  /**
   * Gets the current move without moving forward. If the {@link #nextMove()}
   * method was not called before, the method will return null.
   * 
   * 
   * @return The current move.
   */
  public Move currentMove() {
    int index = getMoveIndex();
    if (index < 0) {
      return null;
    }
    return this.moves.get(index);
  }

  /**
   * The index of the current move, 0-based. Must be -1 directly after creation.
   * 
   * @return Index of the current move, 0-based.
   */
  public Integer getMoveIndex() {
    return moves.size() - 1;
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
  public void setSolution(QuestionBank solution) {
    this.solution = solution;
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

}
