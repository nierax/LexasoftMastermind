package de.lexasoft.mastermind.core.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests a pin as a component.
 * 
 * @author Axel
 */
public class PinTest {

	private Pin cut;

	@BeforeEach
	void prepare() {
		cut = Pin.of(PinColor.of(0));
	}

	/**
	 * Other pin has same value. True expected.
	 */
	@Test
	void testComparePinEqual() {
		Pin otherPin = Pin.of(PinColor.of(0));
		assertTrue(cut.comparePin(otherPin), "Both pins have same value. Must be true");
	}

	/**
	 * Other pin has a different value. False expected.
	 */
	@Test
	void testComparePinNotEqual() {
		Pin otherPin = Pin.of(PinColor.of(5));
		assertFalse(cut.comparePin(otherPin), "Pins have different values. Must be false");
	}

}
