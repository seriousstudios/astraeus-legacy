package astraeus.game.model.widget.dialog;

import lombok.Data;

/**
 * The {@link Chainable} implementation that represents a dialogue with a single statement; which
 * has no models on the dialogue.
 *
 * @author Vult-R
 */
@Data
public class StatementDialogue implements Chainable {

  /**
   * The text for this dialogue.
   */
  private final String[] lines;

  /**
   * Creates a new {@link StatementDialogue}.
   *
   * @param lines The text for this dialogue.
   */
  public StatementDialogue(String... lines) {
    this.lines = lines;
  }


  @Override
  public void accept(DialogueFactory factory) {
    factory.sendStatement(this);
  }

}

