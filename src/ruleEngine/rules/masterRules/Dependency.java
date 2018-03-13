package ruleEngine.rules.masterRules;

import ruleEngine.Rule;

public class Dependency {
    private Rule client;
    private Rule dependency;
    private boolean expectedState;

    Dependency(Rule client, Rule dependency, boolean expectedState){
        this.client = client;
        this.dependency = dependency;
        this.expectedState = expectedState;
    }

    public Rule getClient() {
        return client;
    }

    public Rule getDependency() {
        return dependency;
    }

    public boolean getExpectedState() {
        return expectedState;
    }
}
