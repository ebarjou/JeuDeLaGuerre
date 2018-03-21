package ruleEngine;

import ruleEngine.rules.newRules.IRule;

public class RuleResult {
    private boolean isValid;
    private StringBuilder logMessage;

    public RuleResult() {
        isValid = true;
        logMessage = new StringBuilder();
    }

    public boolean isValid() {
        return isValid;
    }

    public void invalidate() {
        isValid = false;
    }

    public String getLogMessage() {
        return logMessage.toString();
    }

    public void addMessage(IRule caster, String message) {
        logMessage.append(caster.getClass().getSimpleName()).append(" : ").append(message).append("\n");
    }

    public void addMessage(String casters, String message){
        logMessage.append(casters).append(" : ").append(message).append("\n");
    }

    public void addMessage(String message){
        logMessage.append(message).append("\n");
    }
}
