package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for the AnyBank class. Ensure the basis methods to work.
 * 
 * @author axel
 *
 */
class AnyBankTest {

  private AnyBank cut;
  private static final NrOfHoles NR_OF_HOLES = new NrOfHoles(4);

  @BeforeEach
  void prepareTestCase() {
    cut = new AnyBank(NR_OF_HOLES);
  }

  private List<Pin> createListOfPins(int nrOfPins) {
    List<Pin> pins = new ArrayList<>();
    for (int i = 0; i < nrOfPins; i++) {
      Pin pin = new Pin(2);
      pin.setValue(0);
      pins.add(pin);
    }
    return pins;
  }

  /**
   * Tests, whether the setPins() method does not reuse the given list, but has
   * it's own list.
   */
  @Test
  void testSetPinsOk() {
    List<Pin> source = createListOfPins(4);
    cut.setPins(source);
    assertNotSame(source, cut.getPins());
  }

  /**
   * Tests, whether the setPins() method does not accept a list with more elements
   * in it, than the bank is allowed to have. In this case an
   * IndexOutOfBoundsException is expected.
   */
  @Test
  void testSetPinsTooManyElements() {
    List<Pin> source = createListOfPins(5);
    assertThrows(IndexOutOfBoundsException.class, () -> {
      cut.setPins(source);
    });
  }

  /**
   * checks, whether the completely filled method works correctly.
   */
  @Test
  void testIsCompletelyFilled() {
    assertFalse(cut.isCompletelyFilled(), "Bank must not be filled after creation.");
    // Fill, but let one pin kept empty.
    for (int i = 0; i < cut.getNrOfHoles().getValue() - 1; i++) {
      cut.addPin(new Pin(6, 1));
    }
    assertFalse(cut.isCompletelyFilled(), "One hole not filled, so the bank must be confirmed not filled.");
    // Fill the last pin
    cut.addPin(new Pin(6, 3));
    assertTrue(cut.isCompletelyFilled(), "All holes filled, so the bank must be confirmed filled.");
  }

}
