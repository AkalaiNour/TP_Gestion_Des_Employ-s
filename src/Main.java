import Controller.LoginController;
import DAO.SignUpViewDAOImpl;
import view.LoginView;

public class Main {
    public static void main(String[] args) {
        // Create instances of DAO and View
        SignUpViewDAOImpl dao = new SignUpViewDAOImpl(); 
        LoginView loginView = new LoginView(); 

        new LoginController(loginView, dao);
    }
}
