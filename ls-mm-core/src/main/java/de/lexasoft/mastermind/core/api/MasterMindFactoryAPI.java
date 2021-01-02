/**
 * 
 */
package de.lexasoft.mastermind.core.api;

import de.lexasoft.mastermind.core.MasterMindAPIImpl;

/**
 * This class creates a master mind api for use with a client.
 * <p>
 * Pattern: Factory
 * 
 * @author Axel
 *
 */
public class MasterMindFactoryAPI {

  /**
   * 
   */
  public MasterMindFactoryAPI() {
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
  public MasterMindAPI createBoard(NrOfHoles nrOfPositions, NrOfColors nrOfColors, NrOfMoves nrOfMoves) {
    return new MasterMindAPIImpl(nrOfPositions, nrOfColors, nrOfMoves);
  }

  /**
   * Creates a master mind board.
   * 
   * @param nrOfPositions Number of positions to guess
   * @param nrOfColors    Number of colors per pin.
   * @param nrOfMoves     Maximum number of moves to win the game.
   * @return
   */
  public MasterMindAPI createBoard(int nrOfPositions, int nrOfColors, int nrOfMoves) {
    return createBoard(new NrOfHoles(nrOfPositions), new NrOfColors(nrOfColors), new NrOfMoves(nrOfMoves));
  }

}
