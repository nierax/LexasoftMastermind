/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.ArrayList;
import java.util.List;

import de.lexasoft.game.Dice;
import de.lexasoft.mastermind.core.api.MasterMindValidationException;
import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.Pin;

/**
 * Holds the strategy, the computer uses to find the right combination.
 * 
 * @author Axel
 */
public class MMStrategy {

  private List<List<Pin>> stillPossibleCombinations;
  private NrOfColors nrOfColors;
  private NrOfHoles nrOfHoles;

  /**
   * 
   */
  public MMStrategy(NrOfColors nrOfColors, NrOfHoles nrOfHoles) {
    stillPossibleCombinations = null;
    this.nrOfColors = nrOfColors;
    this.nrOfHoles = nrOfHoles;
  }

  List<List<Pin>> getStillPossibleCombinations() {
    return stillPossibleCombinations;
  }

  void setStillPossibleCombinations(List<List<Pin>> stillPossibleCombinations) {
    this.stillPossibleCombinations = stillPossibleCombinations;
  }

  private int indexOfNextGuess(List<List<Pin>> leftCombinations) {
    if (leftCombinations.size() == 1) {
      return 0;
    }
    Dice dice = new Dice(1, leftCombinations.size());
    return dice.roll() - 1;
  }

  /**
   * Gets the next guess. Needs the last guessed question and the answer to that
   * question.
   * 
   * @param lastGuess  The guess made before.
   * @param lastAnswer The answer to that guess.
   * @return The next guess.
   */
  public QuestionBank nextGuess(QuestionBank lastGuess, AnswerBank lastAnswer) {
    List<List<Pin>> leftCombinations = new ArrayList<>();
    Iterable<List<Pin>> availableCombinations;
    if (getStillPossibleCombinations() == null) {
      availableCombinations = new CombinationCreator(nrOfColors, nrOfHoles);
    } else {
      availableCombinations = getStillPossibleCombinations();
    }
    for (List<Pin> list : availableCombinations) {
      QuestionBank toCheck = new QuestionBank(nrOfHoles, nrOfColors);
      toCheck.setPins(list);
      AnswerBank answer = lastGuess.answer(toCheck);
      if (lastAnswer.equals(answer)) {
        leftCombinations.add(list);
      }
    }
    if (leftCombinations.size() == 0) {
      throw new MasterMindValidationException(
          "There was a mistake in the answers, as no possible combinations remain.");
    }
    setStillPossibleCombinations(leftCombinations);
    QuestionBank nextGuess = new QuestionBank(nrOfHoles, nrOfColors);
    nextGuess.setPins(leftCombinations.get(indexOfNextGuess(leftCombinations)));
    return nextGuess;
  }

  /**
   * The first guess is just a randomly filled bank.
   * 
   * @return A randomly filled bank as a first guess.
   */
  public QuestionBank firstGuess() {
    // Same logic as to create a solution. We reuse this method.
    return createSolution();
  }

  /**
   * Creates a solution to be guessed by the player. The values are set randomly.
   * 
   * @return A solution to guess.
   */
  public QuestionBank createSolution() {
    QuestionBank solution = new QuestionBank(nrOfHoles, nrOfColors);
    return solution.roll();
  }

}
