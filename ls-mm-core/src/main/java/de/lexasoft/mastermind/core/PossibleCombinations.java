/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.Iterator;
import java.util.List;

import de.lexasoft.mastermind.core.api.QuestionPin;

/**
 * Represents possible combinations in the games. Either all (before first
 * answer) or those ones, still belonging to the solution set.
 * 
 * @author nierax
 *
 */
public interface PossibleCombinations extends Iterable<List<QuestionPin>>, Iterator<List<QuestionPin>> {

	/**
	 * The number of combinations left so far.
	 * 
	 * @return
	 */
	int nrOfCombinationsLeft();
}
