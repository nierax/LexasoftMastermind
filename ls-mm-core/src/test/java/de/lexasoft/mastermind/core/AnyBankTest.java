package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import de.lexasoft.mastermind.core.api.MasterMindValidationException;
import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.Pin;
import de.lexasoft.mastermind.core.api.PinColor;
import de.lexasoft.mastermind.core.api.QuestionPin;

/**
 * Test for the AnyBank class. Ensure the basis methods to work.
 * 
 * @author nierax
 *
 */
class AnyBankTest {

	private AnyBank<QuestionPin> cut;
	private static final NrOfHoles NR_OF_HOLES = NrOfHoles.of(4);
	private static final NrOfColors NR_OF_COLORS = NrOfColors.of(6);

	@BeforeEach
	void prepareTestCase() {
		cut = new AnyBank<>(NR_OF_HOLES);
	}

	private List<QuestionPin> createListOfPins(int nrOfPins) {
		List<QuestionPin> pins = new ArrayList<>();
		for (int i = 0; i < nrOfPins; i++) {
			QuestionPin pin = QuestionPin.of(NR_OF_COLORS, PinColor.of(0));
			pins.add(pin);
		}
		return pins;
	}

	/**
	 * Tests the constructor
	 */
	@Test
	void testAnyBank() {
		assertSame(NR_OF_HOLES, cut.getNrOfHoles());
		assertNotNull(cut.getHoles(), "List of holes not created.");
		assertEquals(NR_OF_HOLES.value(), cut.getHoles().size(), "The holes should be set to the number of holes");
	}

	/**
	 * Tests, whether the addPin() Method works correctly, if there is a free slot.
	 */
	@Test
	void testAddPin() {
		cut.addPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(0)));
		assertEquals(0, cut.getHole(0).pin().color().value(), "Value of pin must be equal to the given.");
		cut.addPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(1)));
		assertEquals(1, cut.getHole(1).pin().color().value(), "Value of pin must be equal to the given.");
		cut.addPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(2)));
		assertEquals(2, cut.getHole(2).pin().color().value(), "Value of pin must be equal to the given.");
		cut.addPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(3)));
		assertEquals(3, cut.getHole(3).pin().color().value(), "Value of pin must be equal to the given.");
	}

	/**
	 * There must be thrown an exception, if there is no
	 */
	@Test
	void testAddPin_NoFreeSlot() {
		List<QuestionPin> source = createListOfPins(4);
		cut.setPins(source);
		// All slots are used. The next add should generate an exception
		assertThrows(MasterMindValidationException.class, () -> {
			cut.addPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(4)));
		});
	}

	/**
	 * Tests, whether the addPin(pin, position) method replaces a pin when a new one
	 * is set to the same position.
	 */
	@Test
	void testAddPin_i_() {
		Pin firstPin = cut.addPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(0)), 0);
		assertSame(firstPin, cut.getPin(0), "The pin was not set at the given position.");
		// Set a new pin to the same position.
		Pin nextPin = cut.addPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(1)), 0);
		// Pin should be changed now.
		assertNotSame(firstPin, cut.getPin(0),
		    "After the next pin is set, the pin in the bank isn't the first one anymore.");
		assertSame(nextPin, cut.getPin(0), "After the next pin is set, it must be this one.");
	}

	@ParameterizedTest
	@ValueSource(ints = { -1, 4 })
	void testAddPin_i_IllegalPosition(int position) {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			cut.addPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(0)), position);
		});
	}

	/**
	 * Tests, whether the setPins() method does not reuse the given list, but has
	 * it's own list.
	 */
	@Test
	void testSetPinsOk() {
		List<QuestionPin> source = createListOfPins(4);
		cut.setPins(source);
		for (Hole<QuestionPin> hole : cut.getHoles()) {
			assertTrue(hole.holdsAPin(), "Each hole must hold a pin.");
			assertEquals(source.iterator().next().value(), hole.pin().value(), "Each value must be equal to the given.");
		}
	}

	/**
	 * Tests, whether the setPins() method does not accept a list with more elements
	 * in it, than the bank is allowed to have. In this case an
	 * IndexOutOfBoundsException is expected.
	 */
	@Test
	void testSetPinsTooManyElements() {
		List<QuestionPin> source = createListOfPins(5);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			cut.setPins(source);
		});
	}

	/**
	 * Tests whether the number of pins is correctly counted, also with not all
	 * holes filled.
	 */
	@Test
	void testCurrentNrOfPins() {
		// First the number is 0;
		assertEquals(0, cut.currentNrOfPins(), "Right after creation the number of pins is 0");
		List<QuestionPin> source = createListOfPins(2);
		cut.setPins(source);
		assertEquals(2, cut.currentNrOfPins(), "2 Pins added, so the number should be 2");
		// Adds one more pin in the hole at last position.
		cut.addPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(3)), 3);
		assertEquals(3, cut.currentNrOfPins(), "One more pin added, so the number should be 3");
		// Adds one more pin in the last free hole on position 2.
		cut.addPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(2)), 2);
		assertEquals(4, cut.currentNrOfPins(), "Now 4 pins added, so the number should be 4");
	}

	@Test
	void testRemovePin_i_() {
		// Fill the bank
		List<QuestionPin> pins = createListOfPins(NR_OF_HOLES.value());
		cut.setPins(pins);
		assertEquals(NR_OF_HOLES.value(), cut.currentNrOfPins());
		// Remove the first pin
		Pin pin = cut.removePin(0);
		assertNotNull(pin, "Pin expected to be returned, but wasn't.");
		assertEquals(NR_OF_HOLES.value() - 1, cut.currentNrOfPins(),
		    "After removing the pin, the number of pins should be reduced to one below the number of holes.");
	}

	/**
	 * checks, whether the completely filled method works correctly.
	 */
	@Test
	void testIsCompletelyFilled() {
		assertFalse(cut.isCompletelyFilled(), "Bank must not be filled after creation.");
		// Fill, but let one pin kept empty.
		for (int i = 0; i < cut.getNrOfHoles().value() - 1; i++) {
			cut.addPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(1)));
		}
		assertFalse(cut.isCompletelyFilled(), "One hole not filled, so the bank must be confirmed not filled.");
		// Fill the last pin
		cut.addPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(3)));
		assertTrue(cut.isCompletelyFilled(), "All holes filled, so the bank must be confirmed filled.");
	}

	/**
	 * Checks the copy method.
	 */
	@Test
	void testCopy_ok() {
		cut.setPins(createListOfPins(4));
		AnyBank<QuestionPin> source = new AnyBank<>(NR_OF_HOLES);
		QuestionPin pin0 = QuestionPin.of(NR_OF_COLORS, PinColor.of(1));
		QuestionPin pin2 = QuestionPin.of(NR_OF_COLORS, PinColor.of(2));
		source.addPin(pin0, 0);
		source.addPin(pin2, 2);

		cut.copy(source);

		assertEquals(pin0, cut.getPin(0), "Pin on position 0 not the expected");
		assertEquals(pin2, cut.getPin(2), "Pin on position 2 not the expected");
		assertFalse(cut.getHole(1).holdsAPin(), "The pin on position 1 was not removed.");
		assertFalse(cut.getHole(3).holdsAPin(), "The pin on position 3 was not removed.");
	}

	@Test
	void testCopy_DifferentNrOfHoles() {
		AnyBank<QuestionPin> source = new AnyBank<>(NrOfHoles.of(5));
		assertThrows(MasterMindValidationException.class, () -> {
			cut.copy(source);
		});
	}

	private static Stream<Arguments> testEquals() {
		return Stream.of(Arguments.of(new AnyBank<QuestionPin>(NR_OF_HOLES), new int[] { 0, 1, 2, 3 }, false),
		    Arguments.of(new AnyBank<QuestionPin>(NR_OF_HOLES), new int[] { 0, 0, 0, 0 }, true),
		    Arguments.of(new AnyBank<QuestionPin>(NrOfHoles.of(5)), new int[] { 0, 0, 0, 0, 0 }, false),
		    Arguments.of(new AnyBank<QuestionPin>(NrOfHoles.of(5)), new int[] { 0, 0, 0, 0 }, false));
	}

	/**
	 * Tests the equals() method
	 * 
	 * @param otherBank The bank to be compared to the cut.
	 * @param values    The values in the otherBank
	 * @param expResult The expected result.
	 */
	@ParameterizedTest
	@MethodSource
	void testEquals(AnyBank<QuestionPin> otherBank, int[] values, boolean expResult) {
		// Fill all holes with pins with color 0
		cut.setPins(createListOfPins(4));
		// Fill the other bank with the values given
		for (int i = 0; i < values.length; i++) {
			otherBank.addPin(QuestionPin.of(NR_OF_COLORS, PinColor.of(values[i])));
		}
		assertEquals(expResult, cut.equals(otherBank));
	}

	@Test
	void testRemoveAllPins() {
		// Fill all holes with pins with color 0
		cut.setPins(createListOfPins(4));
		// now remove all pins
		AnyBank<QuestionPin> result = cut.removeAllPins();
		assertNotNull(result, "removeAllPins() must not return null.");
		assertSame(result, cut, "removeAllPins() must return same object as cut.");
		for (Hole<QuestionPin> hole : result.getHoles()) {
			assertFalse(hole.holdsAPin(), "Hole mus be empty now.");
		}
	}

	private void assertPins(AnyBank<QuestionPin> cut, int[] expected) {
		int i = 0;
		for (Hole<QuestionPin> hole : cut.getHoles()) {
			assertEquals(expected[i], hole.pin().color().value(), String.format("Pin on position %s not as expected.", i++));
		}
	}

	/**
	 * Set the pins from a list must reset the list of holes.
	 */
	@Test
	void testSetPins() {
		int[] call1 = new int[] { 0, 1, 2, 3 };
		int[] call2 = new int[] { 2, 3, 4, 5 };

		// First call
		cut.setPins(BankFactory.createListFromArray(NR_OF_COLORS, call1));
		assertPins(cut, call1);

		// Second call
		cut.setPins(BankFactory.createListFromArray(NR_OF_COLORS, call2));
		assertPins(cut, call2);

	}

}
