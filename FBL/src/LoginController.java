import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    GUIManager gui;
    FBLManager fbl;

    @FXML
    TextField username;
    @FXML
    PasswordField password;

    public void initialize(GUIManager gui, FBLManager fbl) {
        this.gui = gui;
        this.fbl = fbl;
    }

    public void forgotpassword() throws Exception{
        gui.loadForgotPasswordPage();
    }

    public void register() throws Exception {
        gui.loadRegisterPage();
    }

    public void login() throws Exception{
        //need to make sure both fields are filled and then validate with database
        //should display warning somewhere if field not filled or login doesn't work

        String un = username.getText();
        String pw = password.getText();

        if (!un.equals("") && !pw.equals("")) {
            DatabaseController dbc = new DatabaseController(); // connect to db
            dbc.LoginUser(un, pw); // find matching credential in db
            dbc.closeConnection();
        }
        //userName.getText();
        //password.getText();

        gui.loadHomePage();
    }
}