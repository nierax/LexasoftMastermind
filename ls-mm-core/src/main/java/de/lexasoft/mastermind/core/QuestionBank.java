/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.List;

import de.lexasoft.common.model.Range;
import de.lexasoft.game.DiceCup;
import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.PinColor;
import de.lexasoft.mastermind.core.api.QuestionPin;

/**
 * Represents one line of pins for question.
 * 
 * @author Axel
 */
public class QuestionBank extends AnyBank<QuestionPin> {

	private NrOfColors nrOfColors;
	private DiceCup diceCup;

	/**
	 * Adds a counted attribute to the pin, which is needed for the internal
	 * answering process.
	 */
	class InternalQuestionPin extends QuestionPin {
		private boolean counted;

		public InternalQuestionPin(QuestionPin pin) {
			super(getNrOfColors(), pin.getColor());
			setValue(pin.getColor());
			counted = false;
		}

		/**
		 * 
		 * @return True, if the pin has already been counted, false otherwise.
		 */
		boolean isCounted() {
			return counted;
		}

		/**
		 * Marks the pin counted.
		 */
		void setCounted() {
			counted = true;
		}

		/**
		 * Removes the counted mark from the pin.
		 */
		void resetCounted() {
			counted = false;
		}

	}

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
		diceCup = DiceCup.of(getNrOfHoles().getValue(), Range.of(1, nrOfColors.getValue()));
	}

	NrOfColors getNrOfColors() {
		return nrOfColors;
	}

	/**
	 * Sets all pins counted.
	 */
	void setAllPinsCounted() {
		for (Hole<QuestionPin> hole : getHoles()) {
			if (hole.holdsAPin()) {
				((InternalQuestionPin) hole.getPin()).setCounted();
			}
		}
	}

	/**
	 * Reset all pins counted.
	 */
	void resetAllPinsCounted() {
		for (Hole<QuestionPin> hole : getHoles()) {
			if (hole.holdsAPin()) {
				((InternalQuestionPin) hole.getPin()).resetCounted();
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
			InternalQuestionPin myPin = (InternalQuestionPin) getPin(i);
			InternalQuestionPin solutionPin = (InternalQuestionPin) solution.getPin(i);
			// Hits at the same position
			if (solutionPin.comparePin(myPin)) {
				blackHits++;
				solutionPin.setCounted();
				myPin.setCounted();
			}
		}
		return blackHits;
	}

	/**
	 * @param solution The bank to compare with
	 * @return Number of white hits between these two banks.
	 */
	private int countWhiteHits(QuestionBank solution) {
		int whiteHits = 0;
		for (int i = 0; i < getNrOfHoles().getValue(); i++) {
			InternalQuestionPin myPin = (InternalQuestionPin) getPin(i);
			// If the pin had a black hit before, it must not be counted again.
			if (!myPin.isCounted()) {
				for (int j = 0; j < solution.getNrOfHoles().getValue(); j++) {
					InternalQuestionPin solutionPin = (InternalQuestionPin) solution.getPin(j);
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
		return doAnswer(solution, new AnswerBank(getNrOfHoles()));
	}

	/**
	 * Compares without checking, whether the banks are completely filled.
	 * <p>
	 * Can be used to save time, if the context makes sure, that the banks are
	 * filled.
	 * 
	 * @param solution The bank with the solution, given to guess.
	 * @param answer   The answer bank to use.
	 * @return The answer bank with the number of white and black pins
	 */
	AnswerBank doAnswer(QuestionBank solution, AnswerBank answer) {
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
			addPin(new QuestionPin(nrOfColors, new PinColor(value - 1)));
		}
		return this;
	}

	/**
	 * Replaces the pin by an instance of question pin.
	 * <p>
	 * This is necessary for being able to mark pins as counted under the answering
	 * process.
	 */
	@Override
	QuestionPin addPin(QuestionPin pin, int position) {
		InternalQuestionPin myPin = new InternalQuestionPin((QuestionPin) pin);
		return super.addPin(myPin, position);
	}

	/**
	 * Replaces the pin by an instance of question pin.
	 * <p>
	 * This is necessary for being able to mark pins as counted under the answering
	 * process.
	 */
	@Override
	QuestionPin addPin(QuestionPin pin) {
		InternalQuestionPin myPin = new InternalQuestionPin((QuestionPin) pin);
		return super.addPin(myPin);
	}

}
