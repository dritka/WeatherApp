package gui;

import api.*;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;

public class WeatherApp extends JFrame {

    public JLabel cityLabel;
    public JTextField cityField;
    public JButton lookup;

    public JPanel infoPanel;

    public JLabel skyLabel;
    public JLabel skyValue;
    public JLabel tempLabel;
    public JLabel tempValue;
    public JLabel feelslikeTempLabel;
    public JLabel feelslikeValue;
    public JLabel windLabel;
    public JLabel windValue;
    public JLabel visibilityLabel;
    public JLabel visibilityValue;
    public ImageIcon icon;
    public JLabel image;

    public WeatherApp() {
        this.setTitle("\uD83C\uDF24\uFE0F Weather App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cityLabel = new JLabel("City: ");
        cityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cityField = new JTextField(15);
        lookup = new JButton("\uD83D\uDD0D Lookup");
        lookup.addActionListener(e -> {
            process();
        });

        JPanel componentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        componentPanel.add(cityLabel, 0);
        componentPanel.add(cityField, 1);

        componentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(lookup);

        infoPanel = new JPanel(new BorderLayout());

        skyLabel = new JLabel("Sky condition: ");
        skyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        skyValue = new JLabel();
        tempLabel = new JLabel("Temperate (in °C): ");
        tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tempValue = new JLabel();
        feelslikeTempLabel = new JLabel("Feels like temperate (°C): ");
        feelslikeTempLabel.setHorizontalAlignment(SwingConstants.CENTER);
        feelslikeValue = new JLabel();
        windLabel = new JLabel("Wind speed (kph): ");
        windLabel.setHorizontalAlignment(SwingConstants.CENTER);
        windValue = new JLabel();
        visibilityLabel = new JLabel("Visibility (km): ");
        visibilityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        visibilityValue = new JLabel();
        image = new JLabel();
        icon = new ImageIcon();

        infoPanel.setBackground(new Color(28, 97, 255));
        infoPanel.setSize(200, 200);
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(componentPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(infoPanel);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.add(mainPanel);

        // this.pack();
        this.setResizable(false);
        this.setFocusable(true);
        this.setSize(450, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void process() {
        String city = cityField.getText();
        cityField.setText(city);

        try {
            ApiHandler.readAllData(city);
            Map<Enum, String> data = ApiHandler.fetchWeatherData("data_" + city + ".json");
            ApiHandler.downloadIcon(data.get(Attributes.condition_json.icon));

            String sky = data.get(Attributes.condition_json.text);
            String temp_c = data.get(Attributes.current_json.temp_c);
            String feelslike_c = data.get(Attributes.current_json.feelslike_c);
            String wind_kph = data.get(Attributes.current_json.wind_kph);
            String vis_kph = data.get(Attributes.current_json.vis_km);

            skyValue.setText(sky);
            tempValue.setText(temp_c);
            feelslikeValue.setText(feelslike_c);
            windValue.setText(wind_kph);
            visibilityValue.setText(vis_kph);

            JPanel detailsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            detailsPanel.add(skyLabel, 0);
            detailsPanel.add(skyValue, 1);
            detailsPanel.add(tempLabel, 2);
            detailsPanel.add(tempValue, 3);
            detailsPanel.add(feelslikeTempLabel, 4);
            detailsPanel.add(feelslikeValue, 5);
            detailsPanel.add(windLabel, 6);
            detailsPanel.add(windValue, 7);
            detailsPanel.add(visibilityLabel, 8);
            detailsPanel.add(visibilityValue, 9);

            detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            BufferedImage bufferedImage = ImageIO.read(new File("icon.png"));
            icon.setImage(bufferedImage);
            image.setIcon(icon);

            infoPanel.removeAll();

            infoPanel.add(image, BorderLayout.WEST);
            infoPanel.add(detailsPanel, BorderLayout.CENTER);

            infoPanel.revalidate();
            infoPanel.repaint();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}
