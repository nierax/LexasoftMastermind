/**
 * 
 */
package de.lexasoft.mastermind.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;

/**
 * @author nierax
 *
 */
class AllPossibleCombinationsTest {

	private final static NrOfColors NR_OF_COLORS = NrOfColors.of(6);
	private final static NrOfHoles NR_OF_HOLES = NrOfHoles.of(4);
	private AllPossibleCombinations cut;

	@BeforeEach
	final void prepareTestCase() {
		cut = new AllPossibleCombinations(NR_OF_COLORS, NR_OF_HOLES);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mastermind.core.AllPossibleCombinations#next()}.
	 */
	@Test
	final void testNext() {
		List<QuestionBank> combinations = new ArrayList<>();
		for (QuestionBank question : cut) {
			combinations.add(question);
			System.out.println(question);
		}
		assertEquals(1296, combinations.size());
		assertTrue(combinations.get(0).equals(new QuestionBank(new int[] { 0, 0, 0, 0 }, NR_OF_COLORS)));
		assertTrue(combinations.get(1).equals(new QuestionBank(new int[] { 1, 0, 0, 0 }, NR_OF_COLORS)));
		assertTrue(combinations.get(6).equals(new QuestionBank(new int[] { 0, 1, 0, 0 }, NR_OF_COLORS)));
		assertTrue(combinations.get(12).equals(new QuestionBank(new int[] { 0, 2, 0, 0 }, NR_OF_COLORS)));
		assertTrue(combinations.get(35).equals(new QuestionBank(new int[] { 5, 5, 0, 0 }, NR_OF_COLORS)));
		assertTrue(combinations.get(36).equals(new QuestionBank(new int[] { 0, 0, 1, 0 }, NR_OF_COLORS)));
		assertTrue(combinations.get(215).equals(new QuestionBank(new int[] { 5, 5, 5, 0 }, NR_OF_COLORS)));
		assertTrue(combinations.get(216).equals(new QuestionBank(new int[] { 0, 0, 0, 1 }, NR_OF_COLORS)));
		assertTrue(combinations.get(1295).equals(new QuestionBank(new int[] { 5, 5, 5, 5 }, NR_OF_COLORS)));
	}

	@Test
	final void testNrOfCombinationsLeft() {
		// Created size is 6 colors with 4 pins. That's why we check the right number of
		// combinations from this.
		assertEquals(1296, cut.nrOfCombinationsLeft());
	}

}
