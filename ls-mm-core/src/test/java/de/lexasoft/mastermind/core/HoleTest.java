/**
 * 
 */
package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mastermind.core.api.Pin;
import de.lexasoft.mastermind.core.api.PinColor;

/**
 * @author nierax
 *
 */
class HoleTest {

	private Hole<Pin> cut;
	private Pin pin2Stuck;

	@BeforeEach
	void prepareTestCase() {
		cut = new Hole<Pin>();
		pin2Stuck = Pin.of(PinColor.of(5));
	}

	/**
	 * Test method for {@link de.lexasoft.mastermind.core.Hole#holdsAPin()}.
	 */
	@Test
	void testHoldsAPin() {
		assertFalse(cut.holdsAPin(), "Hole must be empty after creation");
		cut.stickPin(pin2Stuck);
		assertTrue(cut.holdsAPin(), "Hole must hold the pin after it was stuck.");
		cut.removePin();
		assertFalse(cut.holdsAPin(), "Hole must be empty after removing the pin");
	}

	@Test
	void testRemovePin() {
		assertNull(cut.removePin(), "Removing a none existing pin must result in null.");
		cut.stickPin(pin2Stuck);
		assertEquals(pin2Stuck, cut.removePin(), "The pin that was added before must be returned, when it was removed.");
	}

	/**
	 * Test method for {@link de.lexasoft.mastermind.core.Hole#toString()}.
	 */
	@Test
	void testToString() {
		cut.stickPin(pin2Stuck);
		assertEquals("5", cut.toString(), "Resulting string must be \"5\"");
	}

	@Test
	void testEquals() {
		Hole<Pin> other = new Hole<>();
		assertTrue(cut.equals(other), "Equals must be true, if both holes are empty");
		cut.stickPin(pin2Stuck);
		assertFalse(cut.equals(other), "Equals must be false, if the first hole holds a pin, but the other not.");
		other.stickPin(pin2Stuck);
		assertTrue(cut.equals(other), "Equals must be true, if both holes hold the same pin.");
		Pin otherPin = Pin.of(PinColor.of(5));
		other.stickPin(otherPin);
		assertTrue(cut.equals(other), "Equals must be true, if both holes hold a pin with the same color.");
		otherPin = Pin.of(PinColor.of(6));
		cut.stickPin(otherPin);
		assertFalse(cut.equals(other), "Equals must be false, if both holes hold pins with different colors.");
		cut.removePin();
		assertFalse(cut.equals(other), "Equals must be false, if I'm not holding a pin, but the other does.");
	}

}
