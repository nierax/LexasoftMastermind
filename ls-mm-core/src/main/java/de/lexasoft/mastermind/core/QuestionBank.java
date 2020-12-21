/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one line of pins for question.
 * 
 * @author Axel
 */
public class QuestionBank {

  /**
   * Minimum number of pins.
   */
  public static final int MINIMUM_NUMBER_OF_PINS = 4;

  /**
   * Minimum number of colors.
   */
  public static final int MINIMUM_NUMBER_OF_COLORS = 6;

  private List<Pin> pins;

  /**
   * Creates a bank of pins with the given number of pins, each with a range of
   * the given number of colors.
   * 
   * @param nrOfPins   Number of pins in the bank.
   * @param nrOfColors Range of colors, every pin can represent.
   */
  public QuestionBank(Integer nrOfPins, Integer nrOfColors) {
    if (nrOfPins < MINIMUM_NUMBER_OF_PINS) {
      throw new IllegalArgumentException(String.format("Minimum number of pins is %s ", MINIMUM_NUMBER_OF_PINS));
    }
    if (nrOfColors < MINIMUM_NUMBER_OF_COLORS) {
      throw new IllegalArgumentException(String.format("Minimum number of colors is %s ", MINIMUM_NUMBER_OF_COLORS));
    }
    pins = new ArrayList<>();
    for (int i = 0; i < nrOfPins; i++) {
      pins.add(new Pin(nrOfColors));
    }
  }

  /**
   * @return List of the pins in the bank.
   */
  List<Pin> getPins() {
    return pins;
  }

  void setPinValue(int position, Integer value) {
    getPin(position).setValue(value);
  }

  /**
   * Sets the values of the pins to the entries in the given list.
   * 
   * @param pinValues The values of the pins.
   */
  void setPinValues(List<Integer> pinValues) {
    for (int i = 0; i < pinValues.size(); i++) {
      setPinValue(i, pinValues.get(i));
    }
  }

  /**
   * @param position
   * @return Pin at the given position
   */
  public Pin getPin(int position) {
    return pins.get(position);
  }

  /**
   * Sets all pins counted.
   */
  void setAllPinsCounted() {
    for (Pin pin : pins) {
      pin.setCounted();
    }
  }

  /**
   * Reset all pins counted.
   */
  void resetAllPinsCounted() {
    for (Pin pin : pins) {
      pin.resetCounted();
    }
  }

  /**
   * @param solution The bank to compare with
   * @return Number of black hits between these two banks.
   */
  private int countBlackHits(QuestionBank solution) {
    int blackHits = 0;
    for (int i = 0; i < pins.size(); i++) {
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
    for (int i = 0; i < pins.size(); i++) {
      Pin myPin = getPin(i);
      // If the pin had a black hit before, it must not be counted again.
      if (!myPin.isCounted()) {
        for (int j = 0; j < solution.getPins().size(); j++) {
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
   * Compares this bank to the solution.
   * 
   * @param solution The bank with the solution, given to guess.
   * @return The answer bank with the number of white and black pins
   */
  public AnswerBank answer(QuestionBank solution) {
    AnswerBank answer = new AnswerBank(this.pins.size());
    // Count black hits. Must be done first.
    answer.addBlackPins(countBlackHits(solution));
    // White hits second
    answer.addWhitePins(countWhiteHits(solution));
    // Solution must be ready for the next answer.
    solution.resetAllPinsCounted();
    resetAllPinsCounted();
    return answer;
  }

}
