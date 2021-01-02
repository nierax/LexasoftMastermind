/**
 * 
 */
package de.lexasoft.mastermind.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import de.lexasoft.mastermind.core.AnswerBank;
import de.lexasoft.mastermind.core.GameBoard;
import de.lexasoft.mastermind.core.MMStrategy;
import de.lexasoft.mastermind.core.NrOfColors;
import de.lexasoft.mastermind.core.NrOfHoles;
import de.lexasoft.mastermind.core.NrOfMoves;
import de.lexasoft.mastermind.core.Pin;
import de.lexasoft.mastermind.core.QuestionBank;
import de.lexasoft.mastermind.core.QuestionPinColor;

/**
 * CLI application for master mind. Demo purpose only.
 * 
 * @author Axel
 *
 */
public class MMCLI {

  private MMInputValidator validator;

  private GameBoard mmBoard;
  private MMStrategy strategy;
  private String playersName;
  private Scanner scanner;

  /**
   * 
   */
  private MMCLI() {
    validator = new MMInputValidator();
    scanner = new Scanner(System.in);
  }

  /**
   * 
   * @param args
   */
  void runGame(String[] args) {
    mmBoard = askParameters();
    strategy = new MMStrategy(mmBoard.getNrOfColors(), mmBoard.getNrOfHoles());
    playPlayerGuess();
    System.out.println("Good bye.");
  }

  /**
   * The procedure, when the player has to guess.
   */
  private void playPlayerGuess() {
    mmBoard.setSolution(strategy.createSolution());
    System.out.println(String.format("I've got a combination. Your turn, %s.", playersName));
    for (int i = 0; i < mmBoard.getMaxNrOfMoves().getValue(); i++) {
      System.out.print(String.format("%s guess nr.%s (X, X, X, X): ", playersName, i + 1));
      List<Pin> guessedPins = readQuestionFromKeyboard();
      QuestionBank guess = new QuestionBank(mmBoard.getNrOfHoles(), mmBoard.getNrOfColors());
      guess.setPins(guessedPins);
      mmBoard.answer(guess);
      AnswerBank answer = mmBoard.getLastAnsweredMove().getAnswer();
      System.out.println(" Answer: " + answer.toString());
      if (answer.isCorrect()) {
        System.out.println(String.format("Correct. %s has won in %s moves.", playersName, i + 1));
        break;
      }
      if (i == mmBoard.getMaxNrOfMoves().getValue() - 1) {
        System.out.println(
            String.format("%s has lost, unfortunately. Right combination was: %", playersName, mmBoard.getSolution()));
      }
    }
  }

  /**
   * 
   */
  private GameBoard askParameters() {
    NrOfColors nrOfColors;
    NrOfHoles nrOfHoles;
    NrOfMoves nrOfMoves;
    System.out.print("Player's name: ");
    playersName = scanner.next();
    System.out.println("Ok, " + playersName);
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
    return new GameBoard(nrOfHoles, nrOfColors, nrOfMoves);
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
