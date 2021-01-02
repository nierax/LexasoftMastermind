/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.List;

import de.lexasoft.game.DiceCup;

/**
 * Represents one line of pins for question.
 * 
 * @author Axel
 */
public class QuestionBank extends AnyBank {

  private NrOfColors nrOfColors;
  private DiceCup diceCup;

  /**
   * Creates a bank of pins with the given number of pins, each with a range of
   * the given number of colors.
   * 
   * @param nrOfHoles  Number of pins in the bank.
   * @param nrOfColors Range of colors, every pin can represent.
   */
  public QuestionBank(NrOfHoles nrOfHoles, NrOfColors nrOfColors) {
    super(nrOfHoles);
    this.nrOfColors = nrOfColors;
    diceCup = new DiceCup(getNrOfHoles().getValue(), 1, nrOfColors.getValue());
  }

  NrOfColors getNrOfColors() {
    return nrOfColors;
  }

  /**
   * Sets all pins counted.
   */
  void setAllPinsCounted() {
    for (Hole hole : getHoles()) {
      if (hole.holdsAPin()) {
        hole.getPin().setCounted();
      }
    }
  }

  /**
   * Reset all pins counted.
   */
  void resetAllPinsCounted() {
    for (Hole hole : getHoles()) {
      if (hole.holdsAPin()) {
        hole.getPin().resetCounted();
      }
    }
  }

  /**
   * @param solution The bank to compare with
   * @return Number of black hits between these two banks.
   */
  private int countBlackHits(QuestionBank solution) {
    int blackHits = 0;
    for (int i = 0; i < getNrOfHoles().getValue(); i++) {
      Pin myPin = getPin(i);
      Pin solutionPin = solution.getPin(i);
      // Hits at the same position
      if (solutionPin.comparePin(myPin)) {
        blackHits++;
        solutionPin.setCounted();
        myPin.setCounted();
      }
    }
    System.out.println("Found black hits: " + blackHits);
    return blackHits;
  }

  /**
   * @param solution The bank to compare with
   * @return Number of white hits between these two banks.
   */
  private int countWhiteHits(QuestionBank solution) {
    int whiteHits = 0;
    for (int i = 0; i < getNrOfHoles().getValue(); i++) {
      Pin myPin = getPin(i);
      // If the pin had a black hit before, it must not be counted again.
      if (!myPin.isCounted()) {
        for (int j = 0; j < solution.getNrOfHoles().getValue(); j++) {
          Pin solutionPin = solution.getPin(j);
          // Same index would be black and must not be counted
          // If the solution pin was counted before, we must not count this one
          if ((i != j) && !solutionPin.isCounted()) {
            if (solutionPin.comparePin(myPin)) {
              // One more hit
              whiteHits++;
              // Mark solution as counted
              solutionPin.setCounted();
              // Just count once. Because we break here, it is not necessary to set this pin
              // as counted.
              break;
            }
          }
        }
      }
    }
    System.out.println("Found white hits: " + whiteHits);
    return whiteHits;
  }

  /**
   * Both the question and the solution must be filled completely.
   * 
   * @param question
   * @param solution
   */
  private void validateBanks(QuestionBank question, QuestionBank solution) {
    if (!question.isCompletelyFilled() || !solution.isCompletelyFilled()) {
      throw new IllegalArgumentException("Both question and solution must be filled completely");
    }
    if (question.getNrOfHoles().getValue() != solution.getNrOfHoles().getValue()) {
      throw new IllegalArgumentException("Both question and solution must have the same number of holes.");
    }
  }

  /**
   * Compares this bank to the solution.
   * 
   * @param solution The bank with the solution, given to guess.
   * @return The answer bank with the number of white and black pins
   */
  public AnswerBank answer(QuestionBank solution) {
    validateBanks(this, solution);
    AnswerBank answer = new AnswerBank(getNrOfHoles());
    // Count black hits. Must be done first.
    answer.addBlackPins(countBlackHits(solution));
    // White hits second
    answer.addWhitePins(countWhiteHits(solution));
    // Mark answer as given.
    answer.setGiven();
    // Solution must be ready for the next answer.
    solution.resetAllPinsCounted();
    resetAllPinsCounted();
    return answer;
  }

  /**
   * All pins are set with random values.
   * <p>
   * This happens for all pins, even if they have had a value before.
   * 
   * @return The question bank with the new values.
   */
  public QuestionBank roll() {
    removeAllPins();
    List<Integer> values = diceCup.roll();
    for (Integer value : values) {
      // Subtract 1 from the value, because dice is 1-based, but pins are 0-based.
      addPin(new Pin(new QuestionPinColor(nrOfColors, value - 1)));
    }
    return this;
  }

}
