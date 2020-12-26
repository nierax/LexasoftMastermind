package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class AnswerBankTest {

  private AnswerBank cut;
  private static final NrOfHoles NR_OF_PINS = new NrOfHoles(4);

  @BeforeEach
  void prepareTestCase() {
    cut = new AnswerBank(NR_OF_PINS);
  }

  /**
   * Check, weather the number of white pins equals to the number of white pins
   * added
   * 
   * @param nrOfPins Number of white pins to add.
   */
  @ParameterizedTest
  @ValueSource(ints = { 1, 2, 3, 4 })
  void testAddWhitePinsOk(int nrOfPins) {
    cut.addWhitePins(nrOfPins);
    assertEquals(nrOfPins, cut.getNrOfWhitePins());
  }

  /**
   * Check, weather an IndexOutOfBoundsException is thrown, when the number of
   * white pins to add exceeds the maximum number of pins in the bank.
   * 
   * @param nrOfPins Number of white pins to add.
   */
  @ParameterizedTest
  @ValueSource(ints = { 5, 10 })
  void testAddWhitePinsTooMany(int nrOfPins) {
    assertThrows(IndexOutOfBoundsException.class, () -> {
      cut.addWhitePins(nrOfPins);
    });
  }

  /**
   * Check, weather the number of black pins equals to the number of black pins
   * added
   * 
   * @param nrOfPins Number of white pins to add.
   */
  @ParameterizedTest
  @ValueSource(ints = { 1, 2, 3, 4 })
  void testAddBlackPinsOk(int nrOfPins) {
    cut.addWhitePins(nrOfPins);
    assertEquals(nrOfPins, cut.getNrOfWhitePins());
  }

  /**
   * Check, weather an IndexOutOfBoundsException is thrown, when the number of
   * black pins to add exceeds the maximum number of pins in the bank.
   * 
   * @param nrOfPins Number of black pins to add.
   */
  @ParameterizedTest
  @ValueSource(ints = { 5, 10 })
  void testAddBlackPinsTooMany(int nrOfPins) {
    assertThrows(IndexOutOfBoundsException.class, () -> {
      cut.addWhitePins(nrOfPins);
    });
  }

  private static Stream<Arguments> testAddWhiteBlackPinsOk() {
    return Stream.of(Arguments.of(0, 1), Arguments.of(2, 2), Arguments.of(4, 0), Arguments.of(0, 4),
        Arguments.of(0, 0));
  }

  /**
   * Tests white and black pins in several combinations.
   * <p>
   * The number of added pins must correspond to the number of pins, declared by
   * the bank.
   * 
   * @param black Number of black pins in the bank
   * @param white Number of white pins in the bank
   */
  @ParameterizedTest
  @MethodSource
  void testAddWhiteBlackPinsOk(int black, int white) {
    cut.addBlackPins(black);
    cut.addWhitePins(white);
    assertEquals(black, cut.getNrOfBlackPins());
    assertEquals(white, cut.getNrOfWhitePins());
  }

  /**
   * When there are maximum number of pins in the bank and the next is added, an
   * IndexOutOfBoundsException is expected.
   */
  @Test
  void testAddWhitePin_OutOfBounds() {
    cut.addWhitePins(NR_OF_PINS.getValue());
    assertThrows(MasterMindValidationException.class, () -> {
      cut.addWhitePin();
    });
  }

  /**
   * When there are maximum number of pins in the bank and the next is added, an
   * IndexOutOfBoundsException is expected.
   */
  @Test
  void testAddBlackPin_OutOfBounds() {
    cut.addBlackPins(NR_OF_PINS.getValue());
    assertThrows(MasterMindValidationException.class, () -> {
      cut.addBlackPin();
    });
  }
}
