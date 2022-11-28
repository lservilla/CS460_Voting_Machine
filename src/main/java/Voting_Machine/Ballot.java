package Voting_Machine;

import java.util.ArrayList;

public class Ballot {

    private  final ArrayList<Question> questions;


    public Ballot(ArrayList<String> question) { questions = Utils.parseBallot(question); }

    public ArrayList<Question> getQuestions() { return  questions; }
    public Question getQuestion(int i) { return  questions.get(i); }
    public int getLength() { return  questions.size(); }

}


