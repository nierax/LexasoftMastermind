package de.lexasoft.mastermind.core.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NrOfMovesTest {

  private NrOfMoves cut;

  @BeforeEach
  void prepareTest() {
    cut = new NrOfMoves(6);
  }

  @ParameterizedTest
  @ValueSource(ints = { 0, 1, 2, 3, 4, 5 })
  final void testNrOfColors_BelowMinimum(int value) {
    assertThrows(IllegalArgumentException.class, () -> {
      new NrOfColors(value);
    });
  }

  @ParameterizedTest
  @ValueSource(ints = { 6, 7, 8, 9, 10, 1000, 100000 })
  final void testNrOfColors_Ok(int value) {
    cut = new NrOfMoves(value);
    assertEquals(value, cut.getValue());
  }

  @ParameterizedTest
  @ValueSource(ints = { 6, 7, 8, 9, 10, 1000, 100000 })
  final void testSetValueInteger_Ok(int value) {
    cut.setValue(value);
  }

  @ParameterizedTest
  @ValueSource(ints = { 0, 1, 2, 3, 4, 5, -1, -10 })
  final void testSetValueInteger_BelowMinimum(int value) {
    assertThrows(IllegalArgumentException.class, () -> {
      cut.setValue(value);
    });
  }

}
