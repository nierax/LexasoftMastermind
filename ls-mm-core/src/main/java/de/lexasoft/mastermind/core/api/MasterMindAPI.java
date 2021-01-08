package de.lexasoft.mastermind.core.api;

import java.util.List;

public interface MasterMindAPI {

  /**
   * Is there one more move allowed or is the board already filled with moves?
   * 
   * @return True, if at least one more move is allowed, false otherwise.
   */
  boolean nextMoveAllowed();

  /**
   * The index of the current move, 0-based. Must be -1 directly after creation.
   * 
   * @return Index of the current move, 0-based.
   */
  Integer getMoveIndex();

  NrOfMoves getMaxNrOfMoves();

  NrOfHoles getNrOfHoles();

  NrOfColors getNrOfColors();

  /**
   * Checks, whether the solution is known to the question board.
   * <p>
   * In certain game situations it can make a difference, if the solution is known
   * to the board or not. If the person has to guess the combination, the solution
   * must be known to validate the persons answer.
   * <p>
   * If the computer has to guess the combination, the validation can be asked
   * from the person, thus the solution should not be necessarily known to the
   * game board.
   * 
   * @return True, if the solution is known, false otherwise.
   */
  boolean isSolutionKnown();

  /**
   * Gets the solution to guess as list of pins.
   * 
   * @return
   */
  List<Pin> getSolution();

  /**
   * Sets a new solution to guess.
   * 
   * @param solution
   * @return The list will be returned.
   */
  List<Pin> setSolution(List<Pin> solution);

  /**
   * @return the state
   */
  GameState getState();

  /**
   * Creates a solution to be guessed.
   * 
   * @return A list of pins, representing the solution to guess.
   */
  List<Pin> createSolution();

  /**
   * Answers the question and returns a list of pins with the answers given.
   * <p>
   * The values in the answer mean 0 for white and 1 for black.
   * 
   * @param pins
   * @return
   */
  List<Pin> answerQuestion(List<Pin> pins);

  /**
   * Provides the answer to the current move.
   * <p>
   * Just needs to be called, if the solution is unknown.
   * 
   * @param pins List of answer pins (0,1)
   * @return The answer again
   */
  List<Pin> provideAnswer(List<Pin> pins);

  /**
   * Next guess, the computer does according to its strategy.
   * 
   * @return The list of pins, guessed by the computer.
   */
  List<Pin> nextComputerGuess(List<Pin> lastGuess, List<Pin> lastAnswer);

  /**
   * First guess, the computer does in the beginning of the game.
   * 
   * @return The list of pins, guessed by the computer.
   */
  List<Pin> firstComputerGuess();

  /**
   * Reinitialize for a new game.
   * 
   * @return Reference to a newly reinitialized API object.
   */
  MasterMindAPI newGame();

}