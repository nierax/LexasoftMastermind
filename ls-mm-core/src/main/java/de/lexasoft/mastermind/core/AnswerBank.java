/**
 * 
 */
package de.lexasoft.mastermind.core;

import de.lexasoft.mastermind.core.api.AnswerPin;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.Pin;
import de.lexasoft.mastermind.core.api.PinColor;

/**
 * Represents the bank for answers with only white and black pins.
 * 
 * @author Axel
 */
public class AnswerBank extends AnyBank<AnswerPin> {

	static final int WHITE_VALUE = 0;
	static final int BLACK_VALUE = 1;

	private boolean answerGiven;

	/**
	 * Creates the answer bank with the given number of pins. The number of colors
	 * is set to 2, because there is only black and white.
	 * 
	 * @param nrOfHoles
	 */
	public AnswerBank(NrOfHoles nrOfHoles) {
		super(nrOfHoles);
		answerGiven = false;
	}

	/**
	 * Adds a white pin to the bank.
	 */
	public void addWhitePin() {
		addPin(AnswerPin.of(PinColor.of(WHITE_VALUE)));
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
		addPin(AnswerPin.of(PinColor.of(BLACK_VALUE)));
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
	private int countValues(Pin pin) {
		int nrOfValues = 0;
		for (Hole<AnswerPin> hole : getHoles()) {
			if (hole.holdsAPin()) {
				if (hole.getPin().equals(pin)) {
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
		return countValues(AnswerPin.of(PinColor.of(WHITE_VALUE)));
	}

	/**
	 * Counts the number of black pins in this bank.
	 * 
	 * @return The number of black pins in this bank.
	 */
	public int getNrOfBlackPins() {
		return countValues(AnswerPin.of(PinColor.of(BLACK_VALUE)));
	}

	/**
	 * @return True, when the answer is completely given, false otherwise.
	 */
	public boolean isGiven() {
		return answerGiven;
	}

	/**
	 * Sets the state of the bank to be completely answered.
	 */
	public void setGiven() {
		this.answerGiven = true;
	}

	/**
	 * Sets the state of the bank not to be completely answered.
	 */
	public void setNotGiven() {
		this.answerGiven = false;
	}

	/**
	 * Is the answer correctly given? This happens, when all holes are filled with
	 * black pins.
	 * 
	 * @return True, if the answer is correct, false if not.
	 */
	public boolean isCorrect() {
		return getNrOfBlackPins() == getNrOfHoles().value();
	}

	@Override
	public AnyBank<AnswerPin> copy(AnyBank<AnswerPin> source) {
		if (!(source instanceof AnswerBank)) {
			throw new IllegalArgumentException("Banks to copy must be comparable instances.");
		}
		this.answerGiven = ((AnswerBank) source).answerGiven;
		return super.copy(source);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AnswerBank)) {
			return false;
		}
		AnswerBank other = (AnswerBank) obj;
		return (this.getNrOfBlackPins() == other.getNrOfBlackPins())
		    && (this.getNrOfWhitePins() == other.getNrOfWhitePins());
	}

}
