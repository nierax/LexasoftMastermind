/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.Iterator;
import java.util.List;

import de.lexasoft.mastermind.core.api.QuestionPin;

/**
 * Represents the combinations, that are currently left within the solution set.
 * 
 * @author nierax
 *
 */
public class LeftPossibleCombinations implements PossibleCombinations {

	/**
	 * Use a list to hold the combinations.
	 */
	private Iterator<List<QuestionPin>> leftCombinations;
	private int nrOfCombinationsLeft;

	/**
	 * 
	 * @param leftCombinations
	 */
	private LeftPossibleCombinations(List<List<QuestionPin>> leftCombinations) {
		this.leftCombinations = leftCombinations.iterator();
		this.nrOfCombinationsLeft = leftCombinations.size();
	}

	public final static PossibleCombinations fromList(List<List<QuestionPin>> leftCombinations) {
		return new LeftPossibleCombinations(leftCombinations);
	}

	@Override
	public Iterator<List<QuestionPin>> iterator() {
		return leftCombinations;
	}

	@Override
	public boolean hasNext() {
		return leftCombinations.hasNext();
	}

	@Override
	public List<QuestionPin> next() {
		return leftCombinations.next();
	}

	@Override
	public int nrOfCombinationsLeft() {
		return nrOfCombinationsLeft;
	}

}
