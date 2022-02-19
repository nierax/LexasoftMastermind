/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.Iterator;

/**
 * Represents possible combinations in the games. Either all (before first
 * answer) or those ones, still belonging to the solution set.
 * 
 * @author nierax
 *
 */
public interface PossibleCombinations extends Iterable<QuestionBank>, Iterator<QuestionBank> {

	/**
	 * The number of combinations left so far.
	 * 
	 * @return
	 */
	int nrOfCombinationsLeft();
}
