/**
 * 
 */
package de.lexasoft.mastermind.cli;

import java.util.Scanner;

import de.lexasoft.mastermind.core.GameBoard;
import de.lexasoft.mastermind.core.NrOfColors;
import de.lexasoft.mastermind.core.NrOfHoles;
import de.lexasoft.mastermind.core.NrOfMoves;

/**
 * @author admin
 *
 */
public class MMCLI {

  private MMInputValidator validator;

  private GameBoard mmBoard;

  /**
   * 
   */
  private MMCLI() {
    validator = new MMInputValidator();
  }

  void runGame(String[] args) {
    NrOfColors nrOfColors;
    NrOfHoles nrOfHoles;
    NrOfMoves nrOfMoves;
    Scanner scanner = new Scanner(System.in);
    System.out.print("Player's name: ");
    String playerName = scanner.next();
    System.out.println("Ok, " + playerName);
    while (true) {
      System.out.print("Number of colors to guess (at least 6): ");
      int iNrOfColors = scanner.nextInt();
      if (validator.validateNrOfColors(iNrOfColors)) {
        nrOfColors = new NrOfColors(iNrOfColors);
        break;
      }
    }
    while (true) {
      System.out.print("Number of positions in combination (at least 4): ");
      int iNrOfHoles = scanner.nextInt();
      if (validator.validateNrOfHoles(iNrOfHoles)) {
        nrOfHoles = new NrOfHoles(iNrOfHoles);
        break;
      }
    }
    while (true) {
      System.out.print("Number of moves to guess (ar least 6): ");
      int iNrOfMoves = scanner.nextInt();
      if (validator.validateNrOfMoves(iNrOfMoves)) {
        nrOfMoves = new NrOfMoves(iNrOfMoves);
        break;
      }
    }
    mmBoard = new GameBoard(nrOfHoles, nrOfColors, nrOfMoves);
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    MMCLI cli = new MMCLI();
    cli.runGame(args);
  }

}
