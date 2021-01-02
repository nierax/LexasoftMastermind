/**
 * 
 */
package de.lexasoft.mastermind.core;

import java.util.List;

import de.lexasoft.mastermind.core.api.GameState;
import de.lexasoft.mastermind.core.api.MasterMindBoard;
import de.lexasoft.mastermind.core.api.NrOfColors;
import de.lexasoft.mastermind.core.api.NrOfHoles;
import de.lexasoft.mastermind.core.api.NrOfMoves;
import de.lexasoft.mastermind.core.api.Pin;

/**
 * @author admin
 *
 */
public class MasterMindBoardImpl implements MasterMindBoard {

  private GameBoard gameBoard;
  private MMStrategy strategy;

  /**
   * 
   */
  public MasterMindBoardImpl(NrOfHoles nrOfHoles, NrOfColors nrOfColors, NrOfMoves nrOfMoves) {
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
  public List<Pin> createSolution() {
    return gameBoard.setSolution(strategy.createSolution()).getPins();
  }

  @Override
  public List<Pin> answerQuestion(List<Pin> questionPins) {
    QuestionBank question = new QuestionBank(getNrOfHoles(), getNrOfColors());
    question.setPins(questionPins);
    return gameBoard.answer(question).getPins();
  }

  @Override
  public List<Pin> getSolution() {
    return gameBoard.getSolution().getPins();
  }

}
