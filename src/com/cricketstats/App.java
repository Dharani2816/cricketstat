package com.cricketstats;

import com.cricketstats.ui.StatsDashboard;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StatsDashboard().setVisible(true));
    }
}


