/**
 * 
 */
package de.lexasoft.mastermind.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lexasoft.mastermind.core.api.AnswerPinColor;
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

  private static Logger LOGGER = LoggerFactory.getLogger(MMCLI.class);

  private MMInputValidator validator;

  private MasterMindFactoryAPI mmFactory;
  private MasterMindAPI mmApi;
  private String playersName;
  private Scanner scanner;

  /**
   * 
   */
  private MMCLI() {
    validator = new MMInputValidator();
    mmFactory = new MasterMindFactoryAPI();
    scanner = new Scanner(System.in);
  }

  /**
   * 
   * @param args
   */
  void runGame(String[] args) {
    LOGGER.info("Starting game...");
    mmApi = askParameters();
    playPlayerGuess();
    mmApi = mmApi.newGame();
    playComputerGuess();
    System.out.println("Good bye.");
    LOGGER.info("Game over...");
  }

  /**
   * The procedure, when the player has to guess.
   */
  private void playPlayerGuess() {
    mmApi.createSolution();
    System.out.println(String.format("I've got a combination. Your turn, %s.", playersName));
    while (mmApi.getState() == GameState.MOVE_OPEN) {
      int moveIdx = mmApi.getMoveIndex();
      System.out.print(String.format("%s guess nr.%s (X, X, X, X): ", playersName, moveIdx + 1));
      List<Pin> answerPins = mmApi.answerQuestion(readQuestionFromKeyboard());
      System.out.println(" Answer: " + answerPins.toString());
      if (mmApi.getState() == GameState.WON) {
        System.out.println(String.format("Correct. %s has won in %s moves.", playersName, moveIdx + 1));
        return;
      }
    }
    System.out.println(
        String.format("%s has lost, unfortunately. Right combination was: %s", playersName, mmApi.getSolution()));
  }

  private void playComputerGuess() {
    System.out.println(String.format("Now it is Your turn to find a combination, %s.", playersName));
    System.out.print("Enter Your combination or just <ENTER>, if You want to answer Yourself: ");
    List<Pin> solution = readQuestionFromKeyboard();
    if (solution.size() == mmApi.getNrOfHoles().getValue()) {
      mmApi.setSolution(solution);
    }
    List<Pin> computerGuess = null;
    List<Pin> answer2Computer = null;
    while (mmApi.getState() == GameState.MOVE_OPEN) {
      int moveIdx = mmApi.getMoveIndex();
      if (moveIdx == 0) {
        computerGuess = mmApi.firstComputerGuess();
      } else {
        computerGuess = mmApi.nextComputerGuess(computerGuess, answer2Computer);
      }
      System.out.println(String.format("My guess nr %s is: %s", moveIdx + 1, computerGuess.toString()));
      if (mmApi.isSolutionKnown()) {
        answer2Computer = mmApi.answerQuestion(computerGuess);
      } else {
        System.out
            .print(String.format("%s, it is Your turn to give the answer (white pin: 0, black pin: 1): ", playersName));
        answer2Computer = readAnswerFromKeyboard();
        answer2Computer = mmApi.provideAnswer(answer2Computer);
      }
    }
    switch (mmApi.getState()) {
    case WON: {
      System.out.println(String.format("I've won in %s moves.", mmApi.getMoveIndex() + 1));
      break;
    }
    case LOST: {
      System.out.println(String.format("Ok, I've used alls % moves. I've lost.", mmApi.getMoveIndex() + 1));
      break;
    }
    default:
      break;
    }
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
    return mmFactory.createBoard(iNrOfHoles, iNrOfColors, iNrOfMoves);
  }

  private List<Pin> readQuestionFromKeyboard() {
    String value = scanner.next();
    String[] values = value.split(",");
    List<Pin> pins = new ArrayList<Pin>();
    for (int i = 0; i < values.length; i++) {
      pins.add(new Pin(new QuestionPinColor(mmApi.getNrOfColors(), Integer.valueOf(values[i]))));
    }
    return pins;
  }

  private List<Pin> readAnswerFromKeyboard() {
    List<Pin> pins = new ArrayList<Pin>();
    String value = scanner.next();
    String[] values = value.split(",");
    for (int i = 0; i < values.length; i++) {
      pins.add(new Pin(new AnswerPinColor(Integer.valueOf(values[i]))));
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
