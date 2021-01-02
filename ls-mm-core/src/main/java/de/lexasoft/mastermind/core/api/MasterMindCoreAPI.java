/**
 * 
 */
package de.lexasoft.mastermind.core.api;

import de.lexasoft.mastermind.core.MasterMindBoardImpl;

/**
 * This class holds an api for the master mind game for the use by clients.
 * <p>
 * Pattern: Facade
 * 
 * @author Axel
 *
 */
public class MasterMindCoreAPI {

  /**
   * 
   */
  public MasterMindCoreAPI() {
    // TODO Auto-generated constructor stub
  }

  /**
   * Creates a master mind board.
   * 
   * @param nrOfPositions Number of positions to guess
   * @param nrOfColors    Number of colors per pin.
   * @param nrOfMoves     Maximum number of moves to win the game.
   * @return
   */
  public MasterMindBoard createBoard(NrOfHoles nrOfPositions, NrOfColors nrOfColors, NrOfMoves nrOfMoves) {
    return new MasterMindBoardImpl(nrOfPositions, nrOfColors, nrOfMoves);
  }

  /**
   * Creates a master mind board.
   * 
   * @param nrOfPositions Number of positions to guess
   * @param nrOfColors    Number of colors per pin.
   * @param nrOfMoves     Maximum number of moves to win the game.
   * @return
   */
  public MasterMindBoard createBoard(int nrOfPositions, int nrOfColors, int nrOfMoves) {
    return createBoard(new NrOfHoles(nrOfPositions), new NrOfColors(nrOfColors), new NrOfMoves(nrOfMoves));
  }

}
