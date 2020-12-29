/**
 * 
 */
package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @author admin
 *
 */
class CombinationCreatorTest {

  private CombinationCreator cut = new CombinationCreator(new NrOfColors(6), new NrOfHoles(4));

  /**
   * Test method for
   * {@link de.lexasoft.mastermind.core.CombinationCreator#next()}.
   */
  @Test
  final void testNext() {
    List<List<Pin>> combinations = new ArrayList<>();
    for (List<Pin> pin : cut) {
      combinations.add(pin);
      System.out.println(pin);
    }
    assertEquals(1296, combinations.size());
  }

}
