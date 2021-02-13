/**
 * 
 */
package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mastermind.core.api.PinColor;

/**
 * Ensure, that color can not be changed after constructor.
 * 
 * @author Axel
 */
class PinColorTest {

	private PinColor cut;

	@BeforeEach
	void prepareTestCase() {
		cut = PinColor.of(1);
	}

	@Test
	void testEquals() {
		PinColor theOtherColor = PinColor.of(cut.value());
		assertTrue(cut.equals(theOtherColor));

		theOtherColor = PinColor.of(cut.value() + 1);
		assertFalse(cut.equals(theOtherColor));
	}

}
