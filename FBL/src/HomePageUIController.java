import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.bson.Document;

import java.util.Date;

public class HomePageUIController {

    GUIManager gui;
    FBLManager fbl;

    @FXML
    Label name;
    @FXML
    Label age;
    @FXML
    Label status;
    @FXML
    TextArea postTextArea;
    @FXML
    ScrollPane panePosts;

    public void initialize(GUIManager gui, FBLManager fbl) {
        this.gui = gui;
        this.fbl = fbl;
        buildSceneLayout();
        loadUserData();

        buildHomepagePosts();
    }

    // dynamically set setting of some panel, etc
    private void buildSceneLayout() {
        name.setWrapText(true);
        postTextArea.setWrapText(true);
        panePosts.setPadding(new Insets(5));
    }

    private void buildHomepagePosts() {
        fbl.getHomepagePosts();

        // VBox of holding all the nodes of posts
        VBox postsVBox = new VBox(5);

        for (Document postDocs: fbl.userPost.postDocs) {

            // TODO change to something else instead of just label?
            Label postLabel = new Label(
                    "@" + postDocs.getString("username") + "\n" +
                    new Date((long) postDocs.get("date")).toString() + "\n" +
                    postDocs.getString("post") + "\n" +
                    "-----"
            );

            postLabel.setWrapText(true);

            // TODO add double click or something to delete post?
            //postLabel.addEventHandler();

            postsVBox.getChildren().add(postLabel);
        }

        // add VBox to scrollPanel
        panePosts.setContent(postsVBox);
    }

    private void loadUserData(){
        name.setText(fbl.getFirstName() + " " + fbl.getLastName());
        name.setAlignment(Pos.CENTER);

        if(fbl.getHideAge() == false) {
            age.setText("Age: " + fbl.getAge());
            age.setAlignment(Pos.CENTER);
        }
        else
            age.setText("");

        if(fbl.getHideStatus() == false) {
            status.setText(fbl.getStatus());
            status.setAlignment(Pos.CENTER);
        }
        else
            status.setText("");
    }

    @FXML
    public void settings() throws Exception {
        gui.loadSettingUIPage();
    }

    public void logout() throws Exception {
        fbl.logout();
        gui.loadLoginPage();
    }

    public void post() throws Exception {
        if (!postTextArea.getText().equals("")) {
            // add post
            fbl.addNewPost(postTextArea.getText());
            // update scroll pane
            buildHomepagePosts();
        }
    }

    // when we clicked on our profile picture
    public void viewOwnProfile() throws Exception {
        gui.loadProfileUIPage();
    }
}
