/**
 * 
 */
package de.lexasoft.mastermind.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lexasoft.common.cli.ConsoleValidator;
import de.lexasoft.common.model.RangeValidator;
import de.lexasoft.mastermind.core.api.AnswerPin;
import de.lexasoft.mastermind.core.api.GameState;
import de.lexasoft.mastermind.core.api.MasterMindAPI;
import de.lexasoft.mastermind.core.api.MasterMindFactoryAPI;
import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.NrOfMoves;
import de.lexasoft.mastermind.core.api.PinColor;
import de.lexasoft.mastermind.core.api.QuestionPin;

/**
 * CLI application for master mind. Demo purpose only.
 * 
 * @author Axel
 *
 */
public class MMCLI {

  private static Logger LOGGER = LoggerFactory.getLogger(MMCLI.class);

  private MasterMindFactoryAPI mmFactory;
  private MasterMindAPI mmApi;
  private String playersName;
  private Scanner scanner;
  private ConsoleValidator console;
  private boolean playerGuess;
  private boolean computerGuess;

  /**
   * 
   */
  private MMCLI() {
    mmFactory = new MasterMindFactoryAPI();
    scanner = new Scanner(System.in);
    console = new ConsoleValidator();
  }

  /**
   * 
   * @param args
   */
  void runGame(String[] args) {
    LOGGER.info("Starting game...");
    mmApi = askParameters();
    if (playerGuess) {
      playPlayerGuess();
    }
    mmApi = mmApi.newGame();
    if (computerGuess) {
      playComputerGuess();
    }
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
      List<AnswerPin> answerPins = mmApi.answerQuestion(readQuestionFromKeyboard());
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
    System.out.println(String.format("Now it is Your turn to give me a combination to guess, %s.", playersName));
    System.out.print("Enter Your combination or just <ENTER>, if You want to answer Yourself: ");
    List<QuestionPin> solution = readQuestionFromKeyboard();
    if (solution.size() == mmApi.getNrOfHoles().getValue()) {
      mmApi.setSolution(solution);
    }
    List<QuestionPin> computerGuess = null;
    List<AnswerPin> answer2Computer = null;
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
        System.out.println(String.format("Answer: %s", answer2Computer.toString()));
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
   * Input the parameters for the game.
   */
  private MasterMindAPI askParameters() {
    System.out.print("Player's name: ");
    playersName = scanner.next();
    System.out.println("Ok, " + playersName);
    int iNrOfColors = console.fromConsole("Number of colors to guess (at least 6): ", () -> {
      return scanner.nextInt();
    }, NrOfColors.VALIDATOR);
    int iNrOfHoles = console.fromConsole("Number of positions in combination (at least 4): ", () -> {
      return scanner.nextInt();
    }, NrOfHoles.VALIDATOR);
    int iNrOfMoves = console.fromConsole("Number of moves to guess (ar least 6): ", () -> {
      return scanner.nextInt();
    }, NrOfMoves.VALIDATOR);
    int iModus = console.fromConsole("Modus (0: You play alone, 1: Computer plays alone, 2: Both play: ", () -> {
      return scanner.nextInt();
    }, new RangeValidator<Integer>(0, 2));
    playerGuess = (iModus == 0) || (iModus == 2);
    computerGuess = (iModus == 1) || (iModus == 2);
    return mmFactory.createBoard(iNrOfHoles, iNrOfColors, iNrOfMoves);
  }

  private List<QuestionPin> readQuestionFromKeyboard() {
    String value = scanner.next();
    String[] values = value.split(",");
    List<QuestionPin> pins = new ArrayList<>();
    for (int i = 0; i < values.length; i++) {
      pins.add(new QuestionPin(mmApi.getNrOfColors(), new PinColor(Integer.valueOf(values[i]))));
    }
    return pins;
  }

  private List<AnswerPin> readAnswerFromKeyboard() {
    List<AnswerPin> pins = new ArrayList<>();
    String value = scanner.next();
    String[] values = value.split(",");
    for (int i = 0; i < values.length; i++) {
      pins.add(new AnswerPin(new PinColor(Integer.valueOf(values[i]))));
    }
    return pins;

  }

  /**
   * Starts the game.
   * 
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
