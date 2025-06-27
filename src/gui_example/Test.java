package gui_example;

import javax.swing.*;
import java.awt.*;

public class Test extends JFrame {

    public JButton button;

    public Test() {
        this.setTitle("Signup form");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button = new JButton("Click Me!");
        this.setLayout(new BorderLayout());
        button.addActionListener(e -> {
            // JOptionPane.showMessageDialog(this, "Hello World", "Info", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            SwingUtilities.invokeLater(ExampleGui::new);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(button);

        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.add(buttonPanel, BorderLayout.CENTER);

        this.pack();
        this.setSize(100, 100);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
