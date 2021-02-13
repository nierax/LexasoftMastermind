package de.lexasoft.mastermind.core;

import java.util.ArrayList;
import java.util.List;

import de.lexasoft.mastermind.core.api.MasterMindValidationException;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.Pin;

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
public class AnyBank<T extends Pin> {

	private List<Hole<T>> holes;
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
		for (int i = 0; i < nrOfHoles.value(); i++) {
			holes.add(new Hole<T>());
		}
	}

	/**
	 * @return Get all pins in the bank.
	 */
	List<Hole<T>> getHoles() {
		return holes;
	}

	Hole<T> getHole(int position) {
		if ((position < 0) || (position > nrOfHoles.value())) {
			throw new IndexOutOfBoundsException(
			    String.format("Position %s does not exist. Must be between %s and %s", position, 0, nrOfHoles.value()));
		}
		return holes.get(position);
	}

	void checkPinBoundaries(int number) {
		if (number > nrOfHoles.value()) {
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
	public AnyBank<T> copy(AnyBank<T> source) {
		if (!nrOfHoles.equals(source.nrOfHoles)) {
			throw new MasterMindValidationException("Banks to copy must have same number of holes.");
		}
		for (int i = 0; i < getNrOfHoles().value(); i++) {
			if (source.getHole(i).holdsAPin()) {
				this.getHole(i).stickPin(source.getPin(i));
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
	public void setPins(List<T> pins) {
		checkPinBoundaries(pins.size());
		doSetPins(pins);
	}

	/**
	 * Does the copying without checking the boundaries, assuming, that this can't
	 * be wrong.
	 * <p>
	 * So the context must assure, that this check wopuldn't fail.
	 * <p>
	 * Used in strategy to save time.
	 * 
	 * @param pins
	 */
	void doSetPins(List<T> pins) {
		removeAllPins();
		for (int i = 0; i < pins.size(); i++) {
			addPin(pins.get(i), i);
		}
	}

	/**
	 * 
	 * @return The first hole, which is not used yet. Returns -1, if there is no
	 *         free hole left.
	 */
	private int findFirstFreePosition() {
		for (Hole<T> hole : holes) {
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
	T addPin(T pin, int position) {
		checkPinBoundaries(position);
		getHole(position).stickPin(pin);
		return pin;
	}

	/**
	 * Adds the given pin to the bank at the first free position.
	 * 
	 * @param pin Pin to be added.
	 */
	T addPin(T pin) {
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
	T removePin(int position) {
		return getHole(position).removePin();
	}

	/**
	 * Removes all pins from the holes.
	 * 
	 * @return Reference to this object.
	 */
	AnyBank<T> removeAllPins() {
		for (Hole<T> hole : holes) {
			hole.removePin();
		}
		return this;
	}

	/**
	 * Gets the currently used number of pins.
	 * 
	 * @return
	 */
	int currentNrOfPins() {
		int number = 0;
		for (Hole<T> hole : holes) {
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
		return currentNrOfPins() == nrOfHoles.value();
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
	public T getPin(int position) {
		return holes.get(position).pin();
	}

	/**
	 * This method returns a list of all pins, currently set. Holes, which are
	 * empty, are ignored.
	 * 
	 * @return List of pins.
	 */
	public List<T> getPins() {
		List<T> pins = new ArrayList<>();
		for (Hole<T> hole : holes) {
			if (hole.holdsAPin()) {
				pins.add(hole.pin());
			}
		}
		return pins;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AnyBank)) {
			return false;
		}
		if (super.equals(obj)) {
			return true;
		}
		if (!nrOfHoles.equals(((AnyBank<T>) obj).nrOfHoles)) {
			return false;
		}
		for (int i = 0; i < nrOfHoles.value(); i++) {
			if (!holes.get(i).equals(((AnyBank<T>) obj).holes.get(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Representing string is the list of holes with their values;
	 */
	@Override
	public String toString() {
		return getHoles().toString();
	}

}
