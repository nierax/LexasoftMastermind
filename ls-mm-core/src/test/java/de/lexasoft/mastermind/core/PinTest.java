package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
		cut = new Pin(6);
	}

	/**
	 * Set a value within 0 and 5. Must be valid and set.
	 */
	@Test
	void testSetValueOk() {
		cut.setValue(2);
		assertEquals(2, cut.getValue());
	}

	/**
	 * Set value 0, which is equal to the min value. Must be valid and set.
	 */
	@Test
	void testSetValueEqualsMin() {
		cut.setValue(0);
		assertEquals(0, cut.getValue());
	}

	/**
	 * Set value 5, which is equal to the max value. Must be valid and set.
	 */
	@Test
	void testSetValueEqualsMax() {
		cut.setValue(5);
		assertEquals(5, cut.getValue());
	}

	/**
	 * Set value -1, which is below the min value. Must be invalid.
	 */
	@Test
	void testSetValueBelowMin() {
		assertThrows(IllegalArgumentException.class, () -> {
			cut.setValue(-1);
		});
	}

	/**
	 * Set value 6, which is above the max value. Must be invalid.
	 */
	@Test
	void testSetValueAboveMax() {
		assertThrows(IllegalArgumentException.class, () -> {
			cut.setValue(6);
		});
	}

}
