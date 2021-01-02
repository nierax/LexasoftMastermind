/**
 * 
 */
package de.lexasoft.mastermind.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import de.lexasoft.mastermind.core.api.GameState;
import de.lexasoft.mastermind.core.api.MasterMindAPI;
import de.lexasoft.mastermind.core.api.MasterMindFactoryAPI;
import de.lexasoft.mastermind.core.api.Pin;
import de.lexasoft.mastermind.core.api.QuestionPinColor;

/**
 * CLI application for master mind. Demo purpose only.
 * 
 * @author Axel
 *
 */
public class MMCLI {

  private MMInputValidator validator;

  private MasterMindFactoryAPI mmApi;
  private MasterMindAPI mmBoard;
  private String playersName;
  private Scanner scanner;

  /**
   * 
   */
  private MMCLI() {
    validator = new MMInputValidator();
    mmApi = new MasterMindFactoryAPI();
    scanner = new Scanner(System.in);
  }

  /**
   * 
   * @param args
   */
  void runGame(String[] args) {
    mmBoard = askParameters();
    playPlayerGuess();
    System.out.println("Good bye.");
  }

  /**
   * The procedure, when the player has to guess.
   */
  private void playPlayerGuess() {
    mmBoard.createSolution();
    System.out.println(String.format("I've got a combination. Your turn, %s.", playersName));
    while (mmBoard.getState() == GameState.MOVE_OPEN) {
      int moveIdx = mmBoard.getMoveIndex();
      System.out.print(String.format("%s guess nr.%s (X, X, X, X): ", playersName, moveIdx + 1));
      List<Pin> answerPins = mmBoard.answerQuestion(readQuestionFromKeyboard());
      System.out.println(" Answer: " + answerPins.toString());
      if (mmBoard.getState() == GameState.WON) {
        System.out.println(String.format("Correct. %s has won in %s moves.", playersName, moveIdx + 1));
        return;
      }
    }
    System.out.println(
        String.format("%s has lost, unfortunately. Right combination was: %s", playersName, mmBoard.getSolution()));
  }

  /**
   * 
   */
  private MasterMindAPI askParameters() {
    int iNrOfColors;
    int iNrOfHoles;
    int iNrOfMoves;
    System.out.print("Player's name: ");
    playersName = scanner.next();
    System.out.println("Ok, " + playersName);
    while (true) {
      System.out.print("Number of colors to guess (at least 6): ");
      iNrOfColors = scanner.nextInt();
      if (validator.validateNrOfColors(iNrOfColors)) {
        break;
      }
    }
    while (true) {
      System.out.print("Number of positions in combination (at least 4): ");
      iNrOfHoles = scanner.nextInt();
      if (validator.validateNrOfHoles(iNrOfHoles)) {
        break;
      }
    }
    while (true) {
      System.out.print("Number of moves to guess (ar least 6): ");
      iNrOfMoves = scanner.nextInt();
      if (validator.validateNrOfMoves(iNrOfMoves)) {
        break;
      }
    }
    return mmApi.createBoard(iNrOfHoles, iNrOfColors, iNrOfMoves);
  }

  private List<Pin> readQuestionFromKeyboard() {
    String value = scanner.next();
    String[] values = value.split(",");
    List<Pin> pins = new ArrayList<Pin>();
    for (int i = 0; i < values.length; i++) {
      pins.add(new Pin(new QuestionPinColor(mmBoard.getNrOfColors(), Integer.valueOf(values[i]))));
    }
    return pins;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    MMCLI cli = new MMCLI();
    try {
      cli.runGame(args);
    } finally {
      cli.scanner.close();
    }
  }

}
