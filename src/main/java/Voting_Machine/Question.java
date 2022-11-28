package Voting_Machine;

import java.util.ArrayList;

public class Question {
    private final String category;
    private final String prompt;
    private final ArrayList<String> options;

    public Question(String category, String prompt, ArrayList<String> options) {
        this.prompt = prompt;
        this.options = options;
        this.category = category;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public String getCategory() {
        return category;
    }

    public String getPrompt() {
        return prompt;
    }
}
