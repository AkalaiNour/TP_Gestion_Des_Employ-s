package Controller;

import DAO.EmployeeDAOImpl;
import DAO.SignUpViewDAOImpl;
import Model.EmployeeModel;
import Model.SignUpModel;
import javax.swing.*;
import view.LoginView;
import view.Vue;

public class LoginController {
    private final LoginView view;
    private final SignUpViewDAOImpl dao;

    public LoginController(LoginView view, SignUpViewDAOImpl dao) {
        this.view = view;
        this.dao = dao;

        // Attach listeners directly in the controller
        view.getLoginButton().addActionListener(e -> handleLogin());
        view.getCreateAccountButton().addActionListener(e -> handleCreateAccount());
    }

    private void handleLogin() {
        String username = view.getUsernameField().getText(); // Get username from the LoginView
        String password = new String(view.getPasswordField().getPassword()); // Get password from the LoginView

        // Validate credentials using DAO (checking against stored data)
        SignUpModel user = dao.getUserByUsernameAndPassword(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(view, "Login Successful!");
            view.dispose(); // Close the login window

            // Initialize the Vue (Main application page)
            Vue vueView = new Vue();

            // Initialize EmployeeDAO and EmployeeModel for managing employee actions
            EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
            EmployeeModel employeeModel = new EmployeeModel(employeeDAO);

            // Initialize and pass the model and view to the EmployeeController
            new EmployeeController(employeeModel, vueView);

            // Show the main Vue window
            vueView.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(view, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCreateAccount() {
        // Logic to redirect to the SignUp view
        JOptionPane.showMessageDialog(view, "Redirecting to Sign Up...");
        // Implement the SignUpView or open a new window for user registration
    }
}