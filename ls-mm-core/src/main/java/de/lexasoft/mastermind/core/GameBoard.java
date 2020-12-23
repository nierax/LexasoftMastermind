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
  private Integer maxNrOfMoves;
  private Integer nrOfPins;
  private Integer nrOfColors;

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
  public GameBoard(Integer nrOfPins, Integer nrOfColors, Integer maxNrOfMoves) {
    moves = new ArrayList<Move>();
    this.maxNrOfMoves = maxNrOfMoves;
    this.nrOfColors = nrOfColors;
    this.nrOfPins = nrOfPins;
  }

  /**
   * @return Index of the current move, 0-based.
   */
  public int getTurn() {
    return 0;
  }

  public List<Move> getMoves() {
    return moves;
  }

  public Integer getMaxNrOfMoves() {
    return maxNrOfMoves;
  }

  public Integer getNrOfPins() {
    return nrOfPins;
  }

  public Integer getNrOfColors() {
    return nrOfColors;
  }

}
