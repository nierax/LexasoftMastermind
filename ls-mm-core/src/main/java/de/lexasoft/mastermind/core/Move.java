/**
 * 
 */
package de.lexasoft.mastermind.core;

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
   * @param nrOfPins   Number of pins in the game
   * @param nrOfColors Number of colors in the game
   */
  public Move(NrOfPins nrOfPins, NrOfColors nrOfColors) {
    question = new QuestionBank(nrOfPins, nrOfColors);
    answer = new AnswerBank(nrOfPins);
  }

  /**
   * @return The question, connected with this move
   */
  public QuestionBank getQuestion() {
    return question;
  }

  /**
   * @return The answe, connected with this move
   */
  public AnswerBank getAnswer() {
    return answer;
  }

}
