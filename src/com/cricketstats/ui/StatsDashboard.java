package com.cricketstats.ui;

import com.cricketstats.service.MatchService;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class StatsDashboard extends JFrame {
    private final MatchService service = new MatchService();

    private final JLabel totalMatchesLabel = new JLabel("Total Matches: 0", SwingConstants.CENTER);
    private final JLabel totalRunsLabel = new JLabel("Total Runs: 0", SwingConstants.CENTER);
    private final JLabel totalWicketsLabel = new JLabel("Total Wickets: 0", SwingConstants.CENTER);
    private final JLabel totalOversLabel = new JLabel("Total Overs Bowled: 0.0", SwingConstants.CENTER);
    private final JLabel totalInningsLabel = new JLabel("Total Innings: 0", SwingConstants.CENTER);
    private final JLabel battingAverageLabel = new JLabel("Batting Average: 0.00", SwingConstants.CENTER);

    private final DecimalFormat twoDp = new DecimalFormat("0.00");

    public StatsDashboard() {
        super("🏏 Cricket Stats Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 400);
        setLocationRelativeTo(null);

        // Use a nicer modern look
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        // ---------- HEADER ----------
        JLabel header = new JLabel(" Cricket Stats Manager", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBackground(new Color(46, 125, 50)); // Dark green header

        // ---------- CENTER PANEL ----------
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        centerPanel.setBackground(new Color(245, 255, 250));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        Font statFont = new Font("Segoe UI", Font.BOLD, 18);
        Color textColor = new Color(25, 25, 25);

        JLabel[] labels = {
                totalMatchesLabel, totalRunsLabel, totalWicketsLabel,
                totalOversLabel, totalInningsLabel, battingAverageLabel
        };

        for (JLabel label : labels) {
            label.setFont(statFont);
            label.setForeground(textColor);
            label.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
            label.setOpaque(true);
            label.setBackground(new Color(255, 255, 255));
            centerPanel.add(label);
        }

        // ---------- BOTTOM PANEL ----------
        JButton addMatchBtn = createButton(" Add Match", new Color(30, 136, 229));
        JButton viewHistoryBtn = createButton(" View Match History", new Color(67, 160, 71));
        JButton refreshBtn = createButton(" Refresh", new Color(255, 167, 38));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        bottomPanel.setBackground(new Color(240, 248, 255));
        bottomPanel.add(addMatchBtn);
        bottomPanel.add(viewHistoryBtn);
        bottomPanel.add(refreshBtn);

        // ---------- ACTIONS ----------
        addMatchBtn.addActionListener(e -> openAddMatchForm(null));
        viewHistoryBtn.addActionListener(e -> openMatchHistory());
        refreshBtn.addActionListener(e -> refreshStats());

        // ---------- FRAME LAYOUT ----------
        setLayout(new BorderLayout(10, 10));
        add(header, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshStats();
    }

    private JButton createButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setPreferredSize(new Dimension(180, 40));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
        return button;
    }

    private void refreshStats() {
        try {
            totalMatchesLabel.setText("Total Matches: " + service.getTotalMatches());
            totalRunsLabel.setText("Total Runs: " + service.getTotalRuns());
            totalWicketsLabel.setText("Total Wickets: " + service.getTotalWickets());
            totalOversLabel.setText("Overs Bowled: " + twoDp.format(service.getTotalOvers()));
            totalInningsLabel.setText("Total Innings: " + service.getTotalInnings());
            battingAverageLabel.setText("Batting Average: " + twoDp.format(service.getBattingAverage()));
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openAddMatchForm(com.cricketstats.model.Match existing) {
        AddMatchForm form = new AddMatchForm(this, existing, () -> SwingUtilities.invokeLater(this::refreshStats));
        form.setVisible(true);
    }

    private void openMatchHistory() {
        MatchHistory history = new MatchHistory(() -> SwingUtilities.invokeLater(this::refreshStats));
        history.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StatsDashboard().setVisible(true));
    }
}
