/**
 * 
 */
package de.lexasoft.mastermind.core;

/**
 * A move is the representation of a move in the game.
 * <p>
 * It describes the process of moving to the next step.
 * 
 * @author nierax
 *
 */
public interface Move {

	QuestionBank guess();

	AnswerBank answer(QuestionBank question);

}
