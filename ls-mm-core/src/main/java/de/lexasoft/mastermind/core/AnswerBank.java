/**
 * 
 */
package de.lexasoft.mastermind.core;

/**
 * Represents the bank for answers with only white and black pins.
 * 
 * @author Axel
 */
public class AnswerBank extends AnyBank {

  static final int WHITE_VALUE = 0;
  static final int BLACK_VALUE = 1;

  class WhitePin extends Pin {

    public WhitePin() {
      super(2);
      setValue(WHITE_VALUE);
    }

  }

  class BlackPin extends Pin {

    public BlackPin() {
      super(2);
      setValue(BLACK_VALUE);
    }

  }

  /**
   * Creates the answer bank with the given number of pins. The number of colors
   * is set to 2, because there is only black and white.
   * 
   * @param nrOfHoles
   */
  public AnswerBank(NrOfHoles nrOfHoles) {
    super(nrOfHoles);
  }

  /**
   * Adds a white pin to the bank.
   */
  public void addWhitePin() {
    addPin(new WhitePin());
  }

  /**
   * Adds the number of white pins given to the bank.
   * 
   * @param number The number of white pins to add.
   */
  public void addWhitePins(int number) {
    checkPinBoundaries(number);
    for (int i = 0; i < number; i++) {
      addWhitePin();
    }
  }

  /**
   * Adds a black pin to the bank.
   */
  public void addBlackPin() {
    addPin(new BlackPin());
  }

  /**
   * Adds the number of black pins given to the bank.
   * 
   * @param number The black of white pins to add.
   */
  public void addBlackPins(int number) {
    for (int i = 0; i < number; i++) {
      addBlackPin();
    }
  }

  /**
   * @param value Value in question
   * @return Number of pins with the given values.
   */
  private int countValues(Integer value) {
    int nrOfValues = 0;
    for (Hole hole : getHoles()) {
      if (hole.holdsAPin()) {
        if (hole.getPin().getValue().equals(value)) {
          nrOfValues++;
        }
      }
    }
    return nrOfValues;
  }

  /**
   * Counts the number of white pins in this bank.
   * 
   * @return The number of white pins in this bank.
   */
  public int getNrOfWhitePins() {
    return countValues(WHITE_VALUE);
  }

  /**
   * Counts the number of black pins in this bank.
   * 
   * @return The number of black pins in this bank.
   */
  public int getNrOfBlackPins() {
    return countValues(BLACK_VALUE);
  }

}
