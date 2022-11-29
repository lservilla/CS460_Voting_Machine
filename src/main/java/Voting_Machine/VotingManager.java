package Voting_Machine;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class VotingManager extends Application{
    enum Machine_State{
        WAITING,
        VOTING_ACTIVE,
        VOTING_PAUSED,
        VOTING_DISABLED,
        FAILURE

    }
    Machine_State state = Machine_State.WAITING;
    @FXML
    public Pane adminCard;
    @FXML
    public Pane voterCard;
    @FXML
    public Pane fakeCard;
    @FXML
    public Rectangle insertedCard;
    @FXML
    public Rectangle dropZone;
    @FXML
    public Pane tamper;
    @FXML
    public Circle color_9;
    //    Buttons for selecting options
    @FXML
    public Pane button_1;
    @FXML
    public Rectangle color_1;
    @FXML
    public Pane button_2;
    @FXML
    public Rectangle color_2;
    @FXML
    public Pane button_3;
    @FXML
    public Rectangle color_3;
    @FXML
    public Pane button_4;
    @FXML
    public Rectangle color_4;
    @FXML
    public Pane button_5;
    @FXML
    public Rectangle color_5;
    @FXML
    public Pane button_6;
    @FXML
    public Rectangle color_6;
    @FXML
    public Pane cancel;
    @FXML
    public Rectangle color_7;
    @FXML
    public Pane okay;
    @FXML
    public Rectangle color_8;

    //    Text that shows the heading, print_out, and options
    @FXML
    public Text header;
    @FXML
    public Text print_out;
    @FXML
    public Text option_txt_1;
    @FXML
    public Text option_txt_2;
    @FXML
    public Text option_txt_3;
    @FXML
    public Text option_txt_4;
    @FXML
    public Text option_txt_5;
    @FXML
    public Text option_txt_6;


    //    Temporary until Voter_ID has functions
    public boolean cardIn = false;
    public int ID = 0; //  0=invalid 1=admin 2=voter

    @Override
    public void start(Stage stage) throws Exception {
//      Starting watch dog thread used to catch failures
        Thread watchdog = new Thread(){
            public void run(){
                try {
                    FailureMonitor failure_monitor =
                            new FailureMonitor();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        watchdog.start();

        Parent rootOpener = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("Screen.fxml")));
        Scene sceneOpener = new Scene(rootOpener,1300,750);
        stage.setTitle("Voting Machine");
        stage.setScene(sceneOpener);
        stage.show();

    }

    @FXML
    public void cardDrag(MouseEvent mouseEvent){
        Node n = (Node)mouseEvent.getSource();
        n.setTranslateX(n.getTranslateX() + mouseEvent.getX());
        n.setTranslateY(n.getTranslateY() + mouseEvent.getY());

        dropZone.setVisible(true);
    }

    @FXML
    public void cardDrop(MouseEvent mouseEvent){
        dropZone.setVisible(false);
        Node n = (Node)mouseEvent.getSource();
        double x = n.getLayoutX() + n.getTranslateX();
        double y = n.getLayoutY() + n.getTranslateY();

        if ( 890 > x && x > 610 && 679 > y && y > 573 && !cardIn){
            n.setVisible(false);
            insertedCard.setVisible(true);
            cardIn = true;
            if(Objects.equals(n.getId(), "adminCard")){
                ID = 1;
                header.setText("Choose an option. Current machine state is: "+
                        state.toString());
                option_txt_1.setText("Check Ballot");
                option_txt_2.setText("Start Voting");
                option_txt_3.setText("Pause Voting");
                option_txt_4.setText("End Voting");
            } else if (Objects.equals(n.getId(), "voterCard")){
                steps = ballot.getLength()-1;
                ID = 2;
                option_txt_1.setFill(Color.BLACK);
                option_txt_2.setFill(Color.BLACK);
                option_txt_3.setFill(Color.BLACK);
                option_txt_4.setFill(Color.BLACK);
                option_txt_5.setFill(Color.BLACK);
                option_txt_6.setFill(Color.BLACK);
                if (state != Machine_State.VOTING_ACTIVE){
                    header.setText("Voting is inactive right now. Please alert a " +
                            "voting administrator to the machine's condition " +
                            "and move to a new machine. Thank you.");
                } else if (state == Machine_State.VOTING_ACTIVE){
                    header.setText("Push Ok Button to star your voting!");
                } else {
                    //add
                }

            } else if (Objects.equals(n.getId(), "fakeCard")){
                ID = 0;
            }
        }
    }

    @FXML
    public void takeCardOut(MouseEvent mouseEvent){
        cardIn = false;
        insertedCard.setVisible(false);
        header.setText("");
        print_out.setText("");
        option_txt_1.setText("");
        option_txt_2.setText("");
        option_txt_3.setText("");
        option_txt_4.setText("");
        option_txt_5.setText("");
        option_txt_6.setText("");

        if (ID == 1){
            adminCard.setVisible(true);
        } else if (ID == 2){
            voterCard.setVisible(true);
        } else if (ID == 0){
            fakeCard.setVisible(true);
        }
    }

    @FXML
    public void buttonHoverEnter(MouseEvent mouseEvent){
        Node n = (Node)mouseEvent.getSource();
        switch (n.getId()) {
            case "button_1" -> color_1.setFill(new
                    Color(0.3564, 0.6821, 0.9868, 1.0));
            case "button_2" -> color_2.setFill(new
                    Color(0.3564, 0.6821, 0.9868, 1.0));
            case "button_3" -> color_3.setFill(new
                    Color(0.3564, 0.6821, 0.9868, 1.0));
            case "button_4" -> color_4.setFill(new
                    Color(0.3564, 0.6821, 0.9868, 1.0));
            case "button_5" -> color_5.setFill(new
                    Color(0.3564, 0.6821, 0.9868, 1.0));
            case "button_6" -> color_6.setFill(new
                    Color(0.3564, 0.6821, 0.9868, 1.0));
            case "cancel" -> color_7.setFill(new
                    Color(0.9803, 0.6512, 0.6512, 1.0));
            case "okay" -> color_8.setFill(new
                    Color(0.4714, 0.9605, 0.561, 1.0));
            case "tamper" -> color_9.setFill(new
                    Color(1.0, 0.2269, 0.2269, 1.0));
        }

    }

    @FXML
    public void buttonHoverExit(MouseEvent mouseEvent){
        Node n = (Node)mouseEvent.getSource();
        switch (n.getId()) {
            case "button_1" -> color_1.setFill(new
                    Color(0.1176, 0.5647, 1.0, 1.0));
            case "button_2" -> color_2.setFill(new
                    Color(0.1176, 0.5647, 1.0, 1.0));
            case "button_3" -> color_3.setFill(new
                    Color(0.1176, 0.5647, 1.0, 1.0));
            case "button_4" -> color_4.setFill(new
                    Color(0.1176, 0.5647, 1.0, 1.0));
            case "button_5" -> color_5.setFill(new
                    Color(0.1176, 0.5647, 1.0, 1.0));
            case "button_6" -> color_6.setFill(new
                    Color(0.1176, 0.5647, 1.0, 1.0));
            case "cancel" -> color_7.setFill(new
                    Color(1.0, 0.3333, 0.3333, 1.0));
            case "okay" -> color_8.setFill(new
                    Color(0.2824, 0.8745, 0.4, 1.0));
            case "tamper" -> color_9.setFill(new
                    Color(0.8275, 0.0078, 0.0078, 1.0));
        }

    }


    private int curQuestion = 0;
    private Ballot ballot;
    private Path ballotPathPrefix;
    private int steps = -2;

    private String printOut = "";

    @FXML
    public void buttonClick(MouseEvent mouseEvent){
        Node n = (Node)mouseEvent.getSource();
        ArrayList<String> rawBallot = null;
        //add V1 and O1
        this.ballotPathPrefix = Paths.get("ballots");
        try {
            rawBallot = Utils.readFile(this.ballotPathPrefix.resolve(Paths.get("V1.txt")).toString());
        } catch (FileNotFoundException e) {

        }
        this.ballot = new Ballot(rawBallot);


        switch (n.getId()){
            case "button_1" -> {
                option_txt_1.setFill(Color.RED);
                option_txt_2.setFill(Color.BLACK);
                option_txt_3.setFill(Color.BLACK);
                option_txt_4.setFill(Color.BLACK);
                option_txt_5.setFill(Color.BLACK);
                option_txt_6.setFill(Color.BLACK);
            }

            case "button_2" -> {
                option_txt_1.setFill(Color.BLACK);
                option_txt_2.setFill(Color.RED);
                option_txt_3.setFill(Color.BLACK);
                option_txt_4.setFill(Color.BLACK);
                option_txt_5.setFill(Color.BLACK);
                option_txt_6.setFill(Color.BLACK);
            }

            case "button_3" -> {
                option_txt_1.setFill(Color.BLACK);
                option_txt_3.setFill(Color.RED);
                option_txt_2.setFill(Color.BLACK);
                option_txt_4.setFill(Color.BLACK);
                option_txt_5.setFill(Color.BLACK);
                option_txt_6.setFill(Color.BLACK);
            }

            case "button_4" -> {
                option_txt_1.setFill(Color.BLACK);
                option_txt_4.setFill(Color.RED);
                option_txt_3.setFill(Color.BLACK);
                option_txt_2.setFill(Color.BLACK);
                option_txt_5.setFill(Color.BLACK);
                option_txt_6.setFill(Color.BLACK);
            }

            case "button_5" -> {
                option_txt_1.setFill(Color.BLACK);
                option_txt_5.setFill(Color.RED);
                option_txt_3.setFill(Color.BLACK);
                option_txt_4.setFill(Color.BLACK);
                option_txt_5.setFill(Color.BLACK);
                option_txt_2.setFill(Color.BLACK);
            }

            case "button_6" -> {
                option_txt_1.setFill(Color.BLACK);
                option_txt_6.setFill(Color.RED);
                option_txt_3.setFill(Color.BLACK);
                option_txt_4.setFill(Color.BLACK);
                option_txt_5.setFill(Color.BLACK);
                option_txt_2.setFill(Color.BLACK);
            }

            case "cancel" -> {
                option_txt_1.setFill(Color.BLACK);
                option_txt_2.setFill(Color.BLACK);
                option_txt_3.setFill(Color.BLACK);
                option_txt_4.setFill(Color.BLACK);
                option_txt_5.setFill(Color.BLACK);
                option_txt_6.setFill(Color.BLACK);
                //System.out.println("cancel!");
            }


            case "okay" -> {
                //change state
                if (ID == 1) {
                    if (option_txt_1.getFill() == Color.RED) {
                        header.setText("Choose an option. Current machine state is: "+
                                state.toString() + "\n" + "Check Ballot!!!");
                    } else if (option_txt_2.getFill() == Color.RED) {
                        state = Machine_State.VOTING_ACTIVE;
                        header.setText("Choose an option. Current machine state is: "+
                                state.toString() + "\n" + "Start Voting!!!");
                    } else if (option_txt_3.getFill() == Color.RED) {
                        state = Machine_State.VOTING_PAUSED;
                        header.setText("Choose an option. Current machine state is: "+
                                state.toString() + "\n" + "Pause Voting!!!");
                    } else if (option_txt_4.getFill() == Color.RED)  {
                        state = Machine_State.VOTING_DISABLED;
                        header.setText("Choose an option. Current machine state is: "+
                                state.toString() + "\n" + "End Voting!!!");
                    } else {
                        header.setText("Choose an option. Current machine state is: "+
                                state.toString() + "\n" + "Please choose an option!!!");
                    }

                } else if (steps == 0 ) {
                    if (option_txt_1.getFill() == Color.RED) {
                        this.printOut = this.printOut + "\n" + option_txt_1.getText();
                    } else if (option_txt_2.getFill() == Color.RED) {
                        this.printOut = this.printOut + "\n" + option_txt_2.getText();
                    } else if (option_txt_3.getFill() == Color.RED) {
                        this.printOut = this.printOut + "\n" + option_txt_3.getText();
                    } else if (option_txt_4.getFill() == Color.RED)  {
                        this.printOut = this.printOut + "\n" + option_txt_4.getText();
                    } else if (option_txt_5.getFill() == Color.RED) {
                        this.printOut = this.printOut + "\n" + option_txt_5.getText();
                    } else if (option_txt_6.getFill() == Color.RED) {
                        this.printOut = this.printOut + "\n" + option_txt_6.getText();
                    } else {
                        steps++;
                        curQuestion--;
                    }
                    option_txt_1.setFill(Color.BLACK);
                    option_txt_2.setFill(Color.BLACK);
                    option_txt_3.setFill(Color.BLACK);
                    option_txt_4.setFill(Color.BLACK);
                    option_txt_5.setFill(Color.BLACK);
                    option_txt_6.setFill(Color.BLACK);
                    header.setText("Finish Voting!!!" + "\n" + "Do you want to print the vote?");
                    option_txt_1.setText("Yes");
                    option_txt_2.setText("No");
                    option_txt_3.setText("");
                    option_txt_4.setText("");
                    option_txt_5.setText("");
                    option_txt_6.setText("");
                } else if (steps == -1) {
                    if (option_txt_1.getFill() == Color.RED) {
                        print_out.setText(printOut);
                    } 
                    option_txt_1.setText("");
                    option_txt_2.setText("");
                    option_txt_3.setText("");
                    option_txt_4.setText("");
                    option_txt_5.setText("");
                    option_txt_6.setText("");
                    option_txt_1.setFill(Color.BLACK);
                    option_txt_2.setFill(Color.BLACK);
                    option_txt_3.setFill(Color.BLACK);
                    option_txt_4.setFill(Color.BLACK);
                    option_txt_5.setFill(Color.BLACK);
                    option_txt_6.setFill(Color.BLACK);
                    header.setText("Thank you for your voting!!");

                } else if (ID == 2 && state == Machine_State.VOTING_ACTIVE && steps > 0 )  {
                    if (option_txt_1.getFill() == Color.RED) {
                        this.printOut = this.printOut+ "\n" + option_txt_1.getText();
                    } else if (option_txt_2.getFill() == Color.RED) {
                        this.printOut = this.printOut + "\n" + option_txt_2.getText();
                    } else if (option_txt_3.getFill() == Color.RED) {
                        this.printOut = this.printOut + "\n" + option_txt_3.getText();
                    } else if (option_txt_4.getFill() == Color.RED)  {
                        this.printOut = this.printOut + "\n" + option_txt_4.getText();
                    } else if (option_txt_5.getFill() == Color.RED) {
                        this.printOut = this.printOut + "\n" + option_txt_5.getText();
                    } else if (option_txt_6.getFill() == Color.RED) {
                        this.printOut = this.printOut + "\n" + option_txt_6.getText();
                    } else {
                        steps++;
                        curQuestion--;
                    }

                    //System.out.println("okay!!!!!");
                    option_txt_1.setFill(Color.BLACK);
                    option_txt_2.setFill(Color.BLACK);
                    option_txt_3.setFill(Color.BLACK);
                    option_txt_4.setFill(Color.BLACK);
                    option_txt_5.setFill(Color.BLACK);
                    option_txt_6.setFill(Color.BLACK);
                    curQuestion++;
                    Question curQuestion = ballot.getQuestion(this.curQuestion);
                    List<String> curQuestionOptions = curQuestion.getOptions();
                    String promptText = curQuestion.getPrompt();
                    String questionCategory = curQuestion.getCategory();
                    String curQuestionText = questionCategory + "\n" + promptText;
                    //System.out.println(curQuestionOptions);
                    //System.out.println(promptText);
                    //System.out.println(questionCategory);
                    header.setText(curQuestionText);
                    //System.out.println(curQuestionOptions.size());
                    option_txt_1.setText("");
                    option_txt_2.setText("");
                    option_txt_3.setText("");
                    option_txt_4.setText("");
                    option_txt_5.setText("");
                    option_txt_6.setText("");
                    //System.out.println(ballot.getLength());
                    switch (curQuestionOptions.size()) {
                        case 6 :
                            option_txt_6.setText(curQuestionOptions.get(5));
                        case 5 :
                            option_txt_5.setText(curQuestionOptions.get(4));
                        case 4 :
                            option_txt_4.setText(curQuestionOptions.get(3));
                        case 3 :
                            option_txt_3.setText(curQuestionOptions.get(2));
                        case 2 :
                            option_txt_2.setText(curQuestionOptions.get(1));
                        case 1 :
                            option_txt_1.setText(curQuestionOptions.get(0));
                            break;
                    }
                    System.out.println(printOut);
                } else if(ID == 2 && state != Machine_State.VOTING_ACTIVE) {
                    header.setText("Choose an option. Current machine state is: "+
                            state.toString() + "\n" + "Ask Admin for help!");
                } else {
                    header.setText("Choose an option. Current machine state is: "+
                            state.toString() + "\n" + "Please Insert Card!");
                }
                steps--;
            }

            case "tamper" -> {
                System.out.println("tamper!");
                state = Machine_State.FAILURE;
                header.setText("Choose an option. Current machine state is: "+
                        state.toString() + "\n" + "Ask Admin for help!");
            }
        }
    }
}
