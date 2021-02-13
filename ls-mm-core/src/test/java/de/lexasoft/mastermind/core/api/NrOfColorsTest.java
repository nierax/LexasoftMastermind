package de.lexasoft.mastermind.core.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NrOfColorsTest {

	private NrOfColors cut;

	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 2, 3, 4, 5 })
	final void testNrOfColors_BelowMinimum(int value) {
		assertThrows(IllegalArgumentException.class, () -> {
			NrOfColors.of(value);
		});
	}

	@ParameterizedTest
	@ValueSource(ints = { 6, 7, 8, 9, 10, 1000, 100000 })
	final void testNrOfColors_Ok(int value) {
		cut = NrOfColors.of(value);
		assertEquals(value, cut.value());
	}

}
