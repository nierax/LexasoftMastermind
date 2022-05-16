/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lexasoft.common.model.Range;
import de.lexasoft.game.Dice;
import de.lexasoft.mastermind.core.api.MasterMindValidationException;
import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;

/**
 * Holds the strategy, the computer uses to find the right combination.
 * 
 * @author nierax
 */
public class MMStrategy {

	private static Logger LOGGER = LoggerFactory.getLogger(MMStrategy.class);

	private PossibleCombinations leftoverCombinations;
	private NrOfColors nrOfColors;
	private NrOfHoles nrOfHoles;

	/**
	 * 
	 */
	public MMStrategy(NrOfColors nrOfColors, NrOfHoles nrOfHoles) {
		leftoverCombinations = null;
		this.nrOfColors = nrOfColors;
		this.nrOfHoles = nrOfHoles;
	}

	PossibleCombinations getLeftoverCombinations() {
		return leftoverCombinations;
	}

	void setLeftoverCombinations(PossibleCombinations stillPossibleCombinations) {
		this.leftoverCombinations = stillPossibleCombinations;
	}

	/**
	 * Randomly determines a possible next guess from the remaining list of
	 * combinations.
	 * 
	 * @param leftCombinations The list of combinations, left.
	 * @return A random element from the list of combinations.
	 */
	private QuestionBank findRandomGuessFrom(List<QuestionBank> leftCombinations) {
		if (leftCombinations.size() == 1) {
			return leftCombinations.get(0).copy();
		}
		var dice = Dice.of(Range.of(1, leftCombinations.size()));
		int idx = dice.roll().value() - 1;
		return leftCombinations.get(idx).copy();
	}

	private boolean sameAnswerAsGivenByUser(QuestionBank myGuess, QuestionBank possibleGuess, AnswerBank lastAnswer) {
		var answer = myGuess.doAnswer(possibleGuess);
		return lastAnswer.equals(answer);
	}

	private Stream<QuestionBank> asStream(PossibleCombinations leftOver) {
		return StreamSupport.stream(leftOver.spliterator(), false);
	}

	private PossibleCombinations getAllOrLeftoverCombinations() {
		return Optional.ofNullable(leftoverCombinations)//
		    .orElseGet(() -> new AllPossibleCombinations(nrOfColors, nrOfHoles));
	}

	/**
	 * Gets the next guess. Needs the last guessed question and the answer to that
	 * question.
	 * 
	 * @param myGuess     The guess made.
	 * @param usersAnswer The answer to that guess.
	 * @return The next guess.
	 */
	public QuestionBank nextGuess(QuestionBank myGuess, AnswerBank usersAnswer) {
		Long time = System.currentTimeMillis();

		List<QuestionBank> nowLeftoverCombinations = asStream(getAllOrLeftoverCombinations())//
		    .filter(possibleGuess -> sameAnswerAsGivenByUser(myGuess, possibleGuess, usersAnswer))
		    .collect(Collectors.toList());

		if (nowLeftoverCombinations.isEmpty()) {
			throw new MasterMindValidationException(
			    "There was a mistake in the answers, as no possible combinations remain.");
		}
		setLeftoverCombinations(LeftoverCombinations.fromList(nowLeftoverCombinations));

		LOGGER.info(String.format("Left %s combinations in %sms ", //
		    nrOfLeftCombinations(), //
		    System.currentTimeMillis() - time));
		return findRandomGuessFrom(nowLeftoverCombinations);
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
		return leftoverCombinations.nrOfCombinationsLeft();
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
