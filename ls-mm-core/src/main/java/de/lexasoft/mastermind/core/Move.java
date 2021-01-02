/**
 * 
 */
package de.lexasoft.mastermind.core;

import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;

/**
 * Represents one move in the game board.
 * <p>
 * It combines a question with the associated answer.
 * 
 * @author Axel
 */
public class Move {

  private QuestionBank question;
  private AnswerBank answer;

  /**
   * Creates the move.
   * 
   * @param nrOfHoles  Number of pins in the game
   * @param nrOfColors Number of colors in the game
   */
  public Move(NrOfHoles nrOfHoles, NrOfColors nrOfColors) {
    question = new QuestionBank(nrOfHoles, nrOfColors);
    answer = new AnswerBank(nrOfHoles);
  }

  /**
   * @return The question, connected with this move
   */
  public QuestionBank getQuestion() {
    return question;
  }

  /**
   * @return The answer, connected with this move
   */
  public AnswerBank getAnswer() {
    return answer;
  }

  /**
   * The move is complete, when the question bank is completely filled out and the
   * answer is given.
   * 
   * @return True, if the move is complete, false otherwise
   */
  public boolean isComplete() {
    return question.isCompletelyFilled() && answer.isGiven();
  }

}
