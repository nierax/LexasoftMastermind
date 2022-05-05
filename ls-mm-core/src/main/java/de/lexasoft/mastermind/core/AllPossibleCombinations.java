/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.Iterator;

import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.PinColor;
import de.lexasoft.mastermind.core.api.QuestionPin;

/**
 * Represents all combinations of number of colors and holes one by one by
 * calling the next() method.
 * <p>
 * Thus not all possible combinations must exist in memory, but just those,
 * which are validated to belong to the solution set.
 * <p>
 * <code> Create -> check -> ok? -y-> add to list <br>
 *                     | <br>
 *                     -n-> throw away
 * </code>
 * 
 * @author nierax
 */
public class AllPossibleCombinations implements PossibleCombinations {

	private NrOfColors nrOfColors;
	private NrOfHoles nrOfHoles;
	private int[] lastArray;

	/**
	 * 
	 */
	public AllPossibleCombinations(NrOfColors nrOfColors, NrOfHoles nrOfHoles) {
		this.nrOfColors = nrOfColors;
		this.nrOfHoles = nrOfHoles;
		lastArray = new int[nrOfHoles.value()];
		// set array to start from to [-1,0,0,..,0]
		lastArray[0] = -1;
		for (int i = 1; i < lastArray.length; i++) {
			lastArray[i] = 0;
		}
	}

	/**
	 * @return False, if the combination would overflow next time, true otherwise.
	 */
	@Override
	public boolean hasNext() {
		for (int i = nrOfHoles.value() - 1; i >= 0; i--) {
			if (lastArray[i] < nrOfColors.value() - 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Increases the number arithmetically.
	 * <p>
	 * Does not check, whether there is a new possibility. So the hasNext() method
	 * has to be called before.
	 * 
	 * @param position The position to start from.
	 */
	private void increasePosition(int position) {
		lastArray[position]++;
		if (lastArray[position] == nrOfColors.value()) {
			lastArray[position] = 0;
			increasePosition(position + 1);
		}
	}

	/**
	 * Gets the next combination. Does not check, whether there are new
	 * possibilities. So the hasNext() method has to be called before.
	 * 
	 * @return A list of pins, which can be set in a question bank.
	 */
	@Override
	public QuestionBank next() {
		var question = new QuestionBank(nrOfHoles, nrOfColors);
		increasePosition(0);
		for (int i = 0; i < lastArray.length; i++) {
			question.addPin(QuestionPin.of(nrOfColors, PinColor.of(lastArray[i])));
		}
		return question;
	}

	/**
	 * Added to be able, to put the creator into a foreach loop.
	 */
	@Override
	public Iterator<QuestionBank> iterator() {
		return this;
	}

	@Override
	public int nrOfCombinationsLeft() {
		return (int) Math.pow(nrOfColors.value(), nrOfHoles.value());
	}

}
