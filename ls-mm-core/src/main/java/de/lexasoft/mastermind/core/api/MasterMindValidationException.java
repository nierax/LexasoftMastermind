/**
 * 
 */
package de.lexasoft.mastermind.core.api;

/**
 * Signalizes a situation in Master Kind, where a validation failed.
 * 
 * @author Axel
 *
 */
@SuppressWarnings("serial")
public class MasterMindValidationException extends RuntimeException {

  /**
   * @param message
   */
  public MasterMindValidationException(String message) {
    super(message);
  }

  /**
   * @param message
   * @param cause
   */
  public MasterMindValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public MasterMindValidationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
