/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lexasoft.common.model.Range;
import de.lexasoft.game.Dice;
import de.lexasoft.mastermind.core.api.MasterMindValidationException;
import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.QuestionPin;

/**
 * Holds the strategy, the computer uses to find the right combination.
 * 
 * @author nierax
 */
public class MMStrategy {

	private static Logger LOGGER = LoggerFactory.getLogger(MMStrategy.class);

	private PossibleCombinations possibleCombinations;
	private NrOfColors nrOfColors;
	private NrOfHoles nrOfHoles;

	/**
	 * 
	 */
	public MMStrategy(NrOfColors nrOfColors, NrOfHoles nrOfHoles) {
		possibleCombinations = new AllPossibleCombinations(nrOfColors, nrOfHoles);
		this.nrOfColors = nrOfColors;
		this.nrOfHoles = nrOfHoles;
	}

	PossibleCombinations getPossibleCombinations() {
		return possibleCombinations;
	}

	void setPossibleCombinations(PossibleCombinations stillPossibleCombinations) {
		this.possibleCombinations = stillPossibleCombinations;
	}

	private int indexOfNextGuess(List<List<QuestionPin>> leftCombinations) {
		if (leftCombinations.size() == 1) {
			return 0;
		}
		Dice dice = Dice.of(Range.of(1, leftCombinations.size()));
		return dice.roll().value() - 1;
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
		Long time = System.currentTimeMillis();
		List<List<QuestionPin>> leftCombinations = new ArrayList<>();
		QuestionBank toCheck = new QuestionBank(nrOfHoles, nrOfColors);
		AnswerBank answer = new AnswerBank(nrOfHoles);
		for (List<QuestionPin> combination2Check : getPossibleCombinations()) {
			toCheck.doSetPins(combination2Check);
			answer.removeAllPins();
			answer = lastGuess.doAnswer(toCheck, answer);
			if (lastAnswer.equals(answer)) {
				leftCombinations.add(combination2Check);
			}
		}
		if (leftCombinations.size() == 0) {
			throw new MasterMindValidationException(
			    "There was a mistake in the answers, as no possible combinations remain.");
		}
		setPossibleCombinations(LeftPossibleCombinations.fromList(leftCombinations));
		LOGGER.info(String.format("Left combinations: %s", nrOfLeftCombinations()));
		QuestionBank nextGuess = new QuestionBank(nrOfHoles, nrOfColors);
		nextGuess.setPins(leftCombinations.get(indexOfNextGuess(leftCombinations)));
		LOGGER.info(String.format("Time used 2 guess: %sms", System.currentTimeMillis() - time));
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

	public int nrOfLeftCombinations() {
		return possibleCombinations.nrOfCombinationsLeft();
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
