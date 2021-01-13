/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.List;

import de.lexasoft.mastermind.core.api.AnswerPin;
import de.lexasoft.mastermind.core.api.GameState;
import de.lexasoft.mastermind.core.api.MasterMindAPI;
import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.NrOfMoves;
import de.lexasoft.mastermind.core.api.QuestionPin;

/**
 * This is the default implementation of the master mind api.
 * 
 * @author Axel
 *
 */
public class MasterMindAPIImpl implements MasterMindAPI {

  private GameBoard gameBoard;
  private MMStrategy strategy;

  /**
   * 
   */
  public MasterMindAPIImpl(NrOfHoles nrOfHoles, NrOfColors nrOfColors, NrOfMoves nrOfMoves) {
    doInitialize(nrOfHoles, nrOfColors, nrOfMoves);
  }

  /**
   * @param nrOfHoles
   * @param nrOfColors
   * @param nrOfMoves
   */
  private void doInitialize(NrOfHoles nrOfHoles, NrOfColors nrOfColors, NrOfMoves nrOfMoves) {
    gameBoard = new GameBoard(nrOfHoles, nrOfColors, nrOfMoves);
    strategy = new MMStrategy(nrOfColors, nrOfHoles);
  }

  @Override
  public boolean nextMoveAllowed() {
    return gameBoard.nextMoveAllowed();
  }

  @Override
  public Integer getMoveIndex() {
    return gameBoard.getMoveIndex();
  }

  @Override
  public NrOfMoves getMaxNrOfMoves() {
    return gameBoard.getMaxNrOfMoves();
  }

  @Override
  public NrOfHoles getNrOfHoles() {
    return gameBoard.getNrOfHoles();
  }

  @Override
  public NrOfColors getNrOfColors() {
    return gameBoard.getNrOfColors();
  }

  @Override
  public boolean isSolutionKnown() {
    return gameBoard.isSolutionKnown();
  }

  @Override
  public GameState getState() {
    return gameBoard.getState();
  }

  @Override
  public List<QuestionPin> createSolution() {
    return gameBoard.setSolution(strategy.createSolution()).getPins();
  }

  @Override
  public List<AnswerPin> answerQuestion(List<QuestionPin> questionPins) {
    QuestionBank question = new QuestionBank(getNrOfHoles(), getNrOfColors());
    question.setPins(questionPins);
    return gameBoard.answer(question).getPins();
  }

  @Override
  public List<AnswerPin> provideAnswer(List<AnswerPin> pins) {
    AnswerBank answer = new AnswerBank(getNrOfHoles());
    answer.setPins(pins);
    return gameBoard.answer(answer).getPins();
  }

  @Override
  public List<QuestionPin> getSolution() {
    return gameBoard.getSolution().getPins();
  }

  @Override
  public List<QuestionPin> setSolution(List<QuestionPin> solution) {
    QuestionBank solutionBank = new QuestionBank(getNrOfHoles(), getNrOfColors());
    solutionBank.setPins(solution);
    return gameBoard.setSolution(solutionBank).getPins();
  }

  @Override
  public List<QuestionPin> nextComputerGuess(List<QuestionPin> lastGuess, List<AnswerPin> lastAnswer) {
    QuestionBank lastGuessBank = new QuestionBank(getNrOfHoles(), getNrOfColors());
    lastGuessBank.setPins(lastGuess);
    AnswerBank answerBank = new AnswerBank(getNrOfHoles());
    answerBank.setPins(lastAnswer);
    return strategy.nextGuess(lastGuessBank, answerBank).getPins();
  }

  @Override
  public List<QuestionPin> firstComputerGuess() {
    return strategy.firstGuess().getPins();
  }

  @Override
  public MasterMindAPI newGame() {
    doInitialize(getNrOfHoles(), getNrOfColors(), getMaxNrOfMoves());
    return this;
  }

}
