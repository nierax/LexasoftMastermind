/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Help class to easily create instances of banks in test situations.
 * 
 * @author Axel
 */
public class BankFactory {

  /**
   * Should not be instantiated.
   */
  private BankFactory() {
  }

  /**
   * Create a question bank with values form the given array.
   * 
   * @param nrOfColors Number of colors to use.
   * @param nrOfHoles  Number of holes to use
   * @param values     The values to set.
   * @return A questionBank filled with the given values.
   */
  final static QuestionBank createQuestionBank(NrOfColors nrOfColors, NrOfHoles nrOfHoles, int[] values) {
    List<Pin> pins = new ArrayList<>();
    for (int i = 0; i < values.length; i++) {
      pins.add(i, new Pin(new QuestionPinColor(nrOfColors, values[i])));
    }
    return createQuestionBank(nrOfColors, nrOfHoles, pins);
  }

  /**
   * Creates a question bank with the given pins.
   * 
   * @param nrOfColors Number of colors.
   * @param nrOfHoles  Number of holes.
   * @param pins       List with the pins to set.
   * @return
   */
  final static QuestionBank createQuestionBank(NrOfColors nrOfColors, NrOfHoles nrOfHoles, List<Pin> pins) {
    QuestionBank questionBank = new QuestionBank(nrOfHoles, nrOfColors);
    questionBank.setPins(pins);
    return questionBank;
  }

  /**
   * Helps to create a list form an integer array
   * 
   * @param array
   * @return
   */
  final static List<Pin> createListFromArray(NrOfColors nrOfColors, int[] array) {
    List<Pin> list = new ArrayList<>();
    for (int i = 0; i < array.length; i++) {
      list.add(new Pin(new QuestionPinColor(nrOfColors, array[i])));
    }
    return list;
  }

}
