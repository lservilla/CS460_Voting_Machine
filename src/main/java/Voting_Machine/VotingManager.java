package Voting_Machine;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
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
//    Buttons for selecting options
    @FXML
    public Pane button_1;
    @FXML
    public Pane button_2;
    @FXML
    public Pane button_3;
    @FXML
    public Pane button_4;
    @FXML
    public Pane button_5;
    @FXML
    public Pane button_6;
    @FXML
    public Pane cancel;
    @FXML
    public Pane okay;
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

        Parent rootOpener = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Screen.fxml")));
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

            } else if (Objects.equals(n.getId(), "voterCard")){
                ID = 2;
            } else if (Objects.equals(n.getId(), "fakeCard")){
                ID = 0;
            }
        }
    }

    @FXML
    public void takeCardOut(MouseEvent mouseEvent){
        cardIn = false;
        insertedCard.setVisible(false);

        if (ID == 1){
            adminCard.setVisible(true);
        } else if (ID == 2){
            voterCard.setVisible(true);
        } else if (ID == 0){
            fakeCard.setVisible(true);
        }
    }
}
