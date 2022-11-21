module Voting_Machine {
    requires javafx.controls;
    requires javafx.fxml;


    opens Voting_Machine to javafx.fxml;
    exports Voting_Machine;
}
