package Voting_Machine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utils {
    public static ArrayList<String> readFile(String pathname) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();

        try {
            File file = new File(pathname);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine().trim());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
//            System.out.println("Ballot not found at path " + pathname);
//            e.printStackTrace();
            throw e;
        }
        return lines;
    }

    public static ArrayList<Question> parseBallot(List<String> ballotRaw) {

        ArrayList<Question> questionsList = new ArrayList<>();

        String category = "";
        String prompt = "";
        ArrayList<String> options = new ArrayList<>();


        if (!ballotRaw.get(0).equals("<beginBallot>")) {
            System.out.println("error in .bml file");
            return questionsList;
        }

        for (String line : ballotRaw) {
            StringTokenizer tokens = new StringTokenizer(line, "<"); // tokens of one single line


            while (tokens.hasMoreTokens()) {
                String tok = tokens.nextToken();
                String str = tok.substring(0, 2);
                String trimOffTag = tok.substring(tok.indexOf('>') + 1).trim();

                switch (str) {
                    case "be": // beginBallot case
                        break;
                    case "qu": // question GOOD
                        if (trimOffTag.length() > Constants.QUESTION_CHAR_LIMIT) {
                            System.out.printf("Error in given .bml file: question length exceeds %d characters\n",
                                    Constants.QUESTION_CHAR_LIMIT);
                            System.out.println("Given question was: " + trimOffTag);
                            return questionsList;
                        }
                        category = trimOffTag;
                        options.clear();
                        break;
                    case "pr": // Prompt case GOOD
                        if (trimOffTag.length() > Constants.PROMPT_CHAR_LIMIT) {
                            System.out.printf("Error in given .bml file: prompt length exceeds %d characters\n",
                                    Constants.PROMPT_CHAR_LIMIT);
                            System.out.println("Given prompt was: " + trimOffTag);
                            return questionsList;
                        }
                        prompt = trimOffTag;
                        break;
                    case "op": // options good
                        if (trimOffTag.length() > Constants.CHOICE_CHAR_LIMIT) {
                            System.out.printf("Error in given .bml file: choice length exceeds %d characters\n",
                                    Constants.CHOICE_CHAR_LIMIT);
                            System.out.println("Given choice was: " + trimOffTag);
                            return questionsList;
                        }
                        options.add(trimOffTag);
                        break;
                    case "\\q":
                        ArrayList<String> tmp = new ArrayList<>(options);
                        questionsList.add(new Question(category, prompt, tmp));

                        break;
                }
            }
        }

        return questionsList;
    }

}