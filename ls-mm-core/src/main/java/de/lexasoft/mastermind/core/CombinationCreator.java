/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Creates all combinations of number of colors and holes one by one by calling
 * the next() method.
 * <p>
 * Can be used to form a foreach loop. Thus the logic of scanning the possible
 * combinations against a question can be optimized, as the check can be done
 * directly after the creation of the comibination.
 * 
 * @author Axel
 */
public class CombinationCreator implements Iterable<List<Pin>>, Iterator<List<Pin>> {

  private NrOfColors nrOfColors;
  private NrOfHoles nrOfHoles;
  private int[] lastArray;

  /**
   * 
   */
  public CombinationCreator(NrOfColors nrOfColors, NrOfHoles nrOfHoles) {
    this.nrOfColors = nrOfColors;
    this.nrOfHoles = nrOfHoles;
    lastArray = new int[nrOfHoles.getValue()];
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
    for (int i = nrOfHoles.getValue() - 1; i >= 0; i--) {
      if (lastArray[i] < nrOfColors.getValue() - 1) {
        return true;
      }
    }
    return false;
  }

  /**
   * Increases the number arithmetically.
   * <p>
   * Does not check, whether there is new possibility. So the hasNext() method has
   * to be called before.
   * 
   * @param position The position to start from.
   */
  private void increasePosition(int position) {
    lastArray[position]++;
    if (lastArray[position] == nrOfColors.getValue()) {
      lastArray[position] = 0;
      increasePosition(position + 1);
    }
  }

  /**
   * Gets the next combination. Does not check, whether there is new possibility.
   * So the hasNext() method has to be called before.
   * 
   * @return A list of pins, which can be set in a question bank.
   */
  @Override
  public List<Pin> next() {
    List<Pin> combination = new ArrayList<>();
    increasePosition(0);
    for (int i = 0; i < lastArray.length; i++) {
      combination.add(new Pin(new QuestionPinColor(nrOfColors, lastArray[i])));
    }
    return combination;
  }

  /**
   * Added to be able, to put the creator into a foreach loop.
   */
  @Override
  public Iterator<List<Pin>> iterator() {
    return this;
  }

}
