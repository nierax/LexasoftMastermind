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
   * @return Index of the current move, 0-based.
   */
  public Integer getMove() {
    return 0;
  }

  public List<Move> getMoves() {
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

}
