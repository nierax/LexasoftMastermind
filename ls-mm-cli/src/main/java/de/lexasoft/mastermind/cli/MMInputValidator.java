/**
 * 
 */
package de.lexasoft.mastermind.cli;

import de.lexasoft.mastermind.core.NrOfColors;
import de.lexasoft.mastermind.core.NrOfHoles;
import de.lexasoft.mastermind.core.NrOfMoves;

/**
 * @author admin
 *
 */
public class MMInputValidator {

  /**
   * 
   */
  public MMInputValidator() {
  }

  public boolean validateNrOfColors(int nrOfColors) {
    return NrOfColors.MINIMUM_NUMBER_OF_COLORS <= nrOfColors;
  }

  public boolean validateNrOfHoles(int nrOfHoles) {
    return NrOfHoles.MINIMUM_NUMBER_OF_PINS <= nrOfHoles;
  }

  public boolean validateNrOfMoves(int nrOfMoves) {
    return NrOfMoves.MINIMUM_NUMBER_OF_MOVES <= nrOfMoves;
  }
}
