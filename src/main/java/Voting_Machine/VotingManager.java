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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
                ID = 2;
                if (state != Machine_State.VOTING_ACTIVE){
                    header.setText("Voting is inactive right now. Please alert a " +
                            "voting administrator to the machine's condition " +
                            "and move to a new machine. Thank you.");
                } else {
                    // Need to put code to activate voting sequence here
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

    @FXML
    public void buttonClick(MouseEvent mouseEvent){
        Node n = (Node)mouseEvent.getSource();

        switch (n.getId()){
            case "button_1" -> option_txt_1.setFill(Color.RED);

            case "button_2" -> option_txt_2.setFill(Color.RED);

            case "button_3" -> option_txt_3.setFill(Color.RED);

            case "button_4" -> option_txt_4.setFill(Color.RED);

            case "button_5" -> option_txt_5.setFill(Color.RED);

            case "button_6" -> option_txt_6.setFill(Color.RED);

            case "cancel" -> System.out.println("cancel!");

            case "okay" -> System.out.println("okay!");

            case "tamper" -> System.out.println("tamper!");
        }
    }
}
