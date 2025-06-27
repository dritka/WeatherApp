package gui_example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // This is better than just instantiating
        // the object because it avoids thread related
        // issues that may occur in the future
        SwingUtilities.invokeLater(ExampleGui::new);
    }
}
