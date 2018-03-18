package ruleEngine.exceptions;

/**
 * Used when a {@link ruleEngine.GameAction} is not recognized by {@link ruleEngine.RuleChecker}.
 *
 * @see ruleEngine.RuleChecker
 * @see ruleEngine.GameAction
 */
public class IncorrectGameActionException extends Exception {
    public IncorrectGameActionException(String message) {
        super(message);
    }
}
