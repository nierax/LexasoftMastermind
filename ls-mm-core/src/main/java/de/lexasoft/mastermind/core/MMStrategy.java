/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.List;

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

  public QuestionBank nextGuess(AnswerBank lastAnswer) {
    return null;
  }

}
