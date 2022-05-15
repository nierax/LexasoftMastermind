/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.Iterator;
import java.util.List;

/**
 * Represents the combinations, that are currently left within the solution set.
 * 
 * @author nierax
 *
 */
public class LeftoverCombinations implements PossibleCombinations {

	/**
	 * Use a list to hold the combinations.
	 */
	private Iterator<QuestionBank> leftCombinations;
	private int nrOfCombinationsLeft;

	/**
	 * 
	 * @param leftCombinations
	 */
	private LeftoverCombinations(List<QuestionBank> leftCombinations) {
		this.leftCombinations = leftCombinations.iterator();
		this.nrOfCombinationsLeft = leftCombinations.size();
	}

	public final static PossibleCombinations fromList(List<QuestionBank> leftCombinations) {
		return new LeftoverCombinations(leftCombinations);
	}

	@Override
	public Iterator<QuestionBank> iterator() {
		return leftCombinations;
	}

	@Override
	public boolean hasNext() {
		return leftCombinations.hasNext();
	}

	@Override
	public QuestionBank next() {
		return leftCombinations.next();
	}

	@Override
	public int nrOfCombinationsLeft() {
		return nrOfCombinationsLeft;
	}

}
