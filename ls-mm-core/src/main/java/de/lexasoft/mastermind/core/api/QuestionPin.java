package de.lexasoft.mastermind.core.api;

import de.lexasoft.common.model.Validator;

/**
 * Validator implementation for this pin.
 * 
 * @author Axel
 */
final class QPColorValidator implements Validator<PinColor> {

  private NrOfColors nrOfColors;

  public QPColorValidator(NrOfColors nrOfColors) {
    super();
    this.nrOfColors = nrOfColors;
  }

  @Override
  public boolean validate(PinColor value) {
    return (value.getValue() >= 0) && (value.getValue() < this.nrOfColors.getValue());
  }

}

/**
 * This pin represents a color to guess.
 * 
 * @author admin
 *
 */
public class QuestionPin extends Pin {

  public QuestionPin(NrOfColors nrOfColors, PinColor color) {
    super(new QPColorValidator(nrOfColors), color);
  }

}
