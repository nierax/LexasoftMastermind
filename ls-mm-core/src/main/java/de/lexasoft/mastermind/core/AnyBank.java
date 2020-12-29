package de.lexasoft.mastermind.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for any bank in the game.
 * <p>
 * Allows to add pins and check the number of them.
 * <p>
 * Doesn't care about the number of colors, because they differ on the different
 * boards
 * 
 * @author Axel
 *
 */
public class AnyBank {

  private List<Hole> holes;
  private NrOfHoles nrOfHoles;

  /**
   * Creates a board with the given number of holes.
   * <p>
   * The board is kept empty, as no pins are added yet.
   * 
   * @param nrOfHoles Number of holes in the board.
   */
  public AnyBank(NrOfHoles nrOfHoles) {
    this.nrOfHoles = nrOfHoles;
    this.holes = new ArrayList<>();
    for (int i = 0; i < nrOfHoles.getValue(); i++) {
      holes.add(new Hole());
    }
  }

  /**
   * @return Get all pins in the bank.
   */
  List<Hole> getHoles() {
    return holes;
  }

  Hole getHole(int position) {
    if ((position < 0) || (position > nrOfHoles.getValue())) {
      throw new IndexOutOfBoundsException(
          String.format("Position %s does not exist. Must be between %s and %s", position, 0, nrOfHoles.getValue()));
    }
    return holes.get(position);
  }

  void checkPinBoundaries(int number) {
    if (number > nrOfHoles.getValue()) {
      throw new IndexOutOfBoundsException(
          String.format("List with %s pins not allowed, %s maximum.", number, nrOfHoles));
    }
  }

  /**
   * Copies the given source bank and returns the result.
   * <p>
   * This is just possible, if the number holes does not differ between the source
   * and this object.
   * 
   * @param source
   * @return
   */
  public AnyBank copy(AnyBank source) {
    if (!nrOfHoles.equals(source.nrOfHoles)) {
      throw new MasterMindValidationException("Banks to copy must have same number of holes.");
    }
    for (int i = 0; i < getNrOfHoles().getValue(); i++) {
      if (source.getHole(i).holdsAPin()) {
        this.getHole(i).setPin(source.getPin(i));
      } else {
        this.getHole(i).removePin();
      }
    }
    return this;
  }

  /**
   * Copies all pins in the given list to it's own list. The original list is not
   * reused.
   * 
   * @param pins The pins to get.
   */
  public void setPins(List<Pin> pins) {
    checkPinBoundaries(pins.size());
    for (Pin pin : pins) {
      addPin(pin);
    }
  }

  /**
   * 
   * @return The first hole, which is not used yet. Returns -1, if there is no
   *         free hole left.
   */
  private int findFirstFreePosition() {
    for (Hole hole : holes) {
      if (!hole.holdsAPin()) {
        return holes.indexOf(hole);
      }
    }
    return -1;
  }

  /**
   * Adds a pin to the given position.
   * <p>
   * If the position is used, the new pin will replace the old one.
   * 
   * @param pin      The pin to add
   * @param position The position, where the pin is to add.
   * @return The pin, added before.
   */
  Pin addPin(Pin pin, int position) {
    checkPinBoundaries(position);
    getHole(position).setValue(pin);
    return pin;
  }

  /**
   * Adds the given pin to the bank at the first free position.
   * 
   * @param pin Pin to be added.
   */
  Pin addPin(Pin pin) {
    int position = findFirstFreePosition();
    if (position < 0) {
      throw new MasterMindValidationException("Could not add a pin, because there was no free hole.");
    }
    return addPin(pin, position);
  }

  /**
   * Removes the pin from the given position. Returns the removed pin.
   * 
   * @param position The position, where the pin should be removed.
   * @return The removed pin, if there was one before. Null otherwise.
   */
  Pin removePin(int position) {
    return getHole(position).removePin();
  }

  /**
   * Gets the currently used number of pins.
   * 
   * @return
   */
  int currentNrOfPins() {
    int number = 0;
    for (Hole hole : holes) {
      if (hole.holdsAPin()) {
        number++;
      }
    }
    return number;
  }

  /**
   * Says, whether all holes in the board are filled with a pin.
   * 
   * @return True, if the board is filled, false otherwise.
   */
  boolean isCompletelyFilled() {
    return currentNrOfPins() == nrOfHoles.getValue();
  }

  /**
   * @return Number of holes, connected with this bank.
   */
  NrOfHoles getNrOfHoles() {
    return nrOfHoles;
  }

  /**
   * @param position
   * @return Pin at the given position
   */
  public Pin getPin(int position) {
    return holes.get(position).getPin();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof AnyBank)) {
      return false;
    }
    if (super.equals(obj)) {
      return true;
    }
    if (!nrOfHoles.equals(((AnyBank) obj).nrOfHoles)) {
      return false;
    }
    for (int i = 0; i < nrOfHoles.getValue(); i++) {
      if (!holes.get(i).equals(((AnyBank) obj).holes.get(i))) {
        return false;
      }
    }
    return true;
  }

}
