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

  private List<Pin> pins;
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
    this.pins = new ArrayList<>();
  }

  /**
   * @return Get all pins in the bank.
   */
  List<Pin> getPins() {
    return pins;
  }

  void checkPinBoundaries(int number) {
    if (number > nrOfHoles.getValue()) {
      throw new IndexOutOfBoundsException(
          String.format("List with %s pins not allowed, %s maximum.", number, nrOfHoles));
    }
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
   * Adds the given pin to the bank.
   * 
   * @param pin Pin to be added.
   */
  void addPin(Pin pin) {
    // One more element allowed?
    checkPinBoundaries(this.pins.size() + 1);
    this.pins.add(pin);
  }

  /**
   * Gets the currently used number of pins.
   * 
   * @return
   */
  int currentNrOfPins() {
    return this.pins.size();
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
    return pins.get(position);
  }

}
