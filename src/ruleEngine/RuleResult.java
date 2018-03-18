package ruleEngine;

/**
 * Wrapper that contains the output of an action given to the RuleChecker.
 */
public class RuleResult {
    private boolean isValid;
    private StringBuilder logMessage;

    public RuleResult() {
        isValid = true;
        logMessage = new StringBuilder();
    }

    /**
     * Returns if a set of rule are valid.
     * @return true if rules allowed the action, false otherwise.
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Called by rules. Set the action as invalid and cannot be modified for the rest of the checks.
     */
    public void invalidate() {
        isValid = false;
    }

    /**
     * Return all of the logs recorded using addMessage() during the rule checking.
     * @return logs as a String.
     */
    public String getLogMessage() {
        return logMessage.toString();
    }

    /**
     * Record a log message to be retrieved among others using getLogMessage().
     * @param caster The calling rule.
     * @param message The message to be displayed.
     */
    public void addMessage(Rule caster, String message) {
        logMessage.append(caster.getClass().getSimpleName()).append(" : ").append(message).append("\n");
    }
}
