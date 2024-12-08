package view;
import Model.Employee.Role;
import java.awt.*;
import javax.swing.*;

public class SignUpView extends JFrame {
    private final JTextField nomField;
    private final JTextField prenomField;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JComboBox<Role> roleComboBox;
    private final JButton signUpButton;

    public SignUpView() {
        setTitle("Sign Up");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        JLabel nomLabel = new JLabel("Nom:");
        JLabel prenomLabel = new JLabel("Prénom:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JLabel roleLabel = new JLabel("Role:");

        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        roleComboBox = new JComboBox<>(Role.values());
        signUpButton = new JButton("Sign Up");

        add(nomLabel);
        add(nomField);
        add(prenomLabel);
        add(prenomField);
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(roleLabel);
        add(roleComboBox);
        add(new JLabel());
        add(signUpButton);

        setVisible(true);
    }

    public JTextField getNomField() {
        return nomField;
    }

    public JTextField getPrenomField() {
        return prenomField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JComboBox<Role> getRoleComboBox() {
        return roleComboBox;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }
}
