package com.example.model;

public class OKRSet {
    private Objective objective;
    private KeyResult[] keyResults;

    public OKRSet(Objective objective,KeyResult... keyResults) {
        this.objective = objective;
        this.keyResults = new KeyResult[5];
        int minLength = Math.min(this.keyResults.length, keyResults.length);
        for (int i = 0; i < minLength; i++) {
            this.keyResults[i] = keyResults[i];
        }
    }

    public Objective getObjective() {
        return objective;
    }

    public KeyResult[] getKeyResults() {
        return keyResults;
    }

}
