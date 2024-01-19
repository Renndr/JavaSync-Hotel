package com.hotel;

import com.formdev.flatlaf.FlatLightLaf;
import com.hotel.Views.MainWindow;

public class App {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
        FlatLightLaf.setup();
    }
}
