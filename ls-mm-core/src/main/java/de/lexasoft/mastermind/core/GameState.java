/**
 * 
 */
package de.lexasoft.mastermind.core;

/**
 * Signalizes, whether the game is still running (a move is open to answer),
 * lost (no moves left, but not solved) or won (the right combination was
 * guessed).
 * 
 * @author Axel
 */
public enum GameState {

  MOVE_OPEN, LOST, WON

}
