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
            throw e;
        }
        return lines;
    }

    public static ArrayList<Question> parseBallot(List<String> ballotRaw) {

        ArrayList<Question> questionsList = new ArrayList<>();

        String category = "";
        String prompt = "";
        ArrayList<String> options = new ArrayList<>();

        for (String line : ballotRaw) {
            StringTokenizer tokens = new StringTokenizer(line, "<"); // tokens of one single line


            while (tokens.hasMoreTokens()) {
                String tok = tokens.nextToken();
                String str = tok.substring(0, 2);
                String trimOffTag = tok.substring(tok.indexOf('>') + 1).trim();

                switch (str) {
                    case "qu": // question GOOD
                        category = trimOffTag;
                        options.clear();
                        break;
                    case "pr": // Prompt case GOOD
                        prompt = trimOffTag;
                        break;
                    case "op": // options good
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