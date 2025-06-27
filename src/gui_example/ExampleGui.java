package gui_example;

import java.awt.*;
import javax.swing.*;
import java.nio.file.*;

public class ExampleGui extends JFrame {

    public JLabel usernameLabel, passwordLabel;
    public JTextField usernameTextField;
    public JPasswordField passwordField;
    public JButton loginButton, signupButton;

    public ExampleGui() {
        this.setTitle("Management");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        usernameTextField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(75, 30));
        loginButton.addActionListener(e -> writeData());
        signupButton = new JButton("Sign up");
        signupButton.setPreferredSize(new Dimension(90, 30));
        signupButton.addActionListener(e -> {
            this.dispose();
            SwingUtilities.invokeLater(Test::new);
        });

        formPanel.add(usernameLabel, 0);
        formPanel.add(usernameTextField, 1);
        formPanel.add(passwordLabel, 2);
        formPanel.add(passwordField, 3);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(loginButton, 0);
        buttonPanel.add(signupButton, 1);

        JPanel outerButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        outerButtonPanel.add(buttonPanel);

        outerButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(2));
        mainPanel.add(outerButtonPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        this.add(mainPanel);

        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void writeData() {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        usernameTextField.setText("");
        passwordField.setText("");

        try {
            Path path = Paths.get("users.txt");

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            StringBuilder data = new StringBuilder();
            data.append(username).append(";").append(password).append("\n");
            Files.writeString(path, data.toString(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}