package com.cricketstats.ui;

import com.cricketstats.service.MatchService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;

public class StatsDashboard extends JFrame {
    private final MatchService service = new MatchService();

    private final JLabel totalMatchesLabel = new JLabel("Total Matches: 0");
    private final JLabel totalRunsLabel = new JLabel("Total Runs: 0");
    private final JLabel totalWicketsLabel = new JLabel("Total Wickets: 0");
    private final JLabel totalOversLabel = new JLabel("Total Overs Bowled: 0.0");
    private final JLabel totalInningsLabel = new JLabel("Total Innings: 0");
    private final JLabel battingAverageLabel = new JLabel("Batting Average: 0.00");

    private final DecimalFormat twoDp = new DecimalFormat("0.00");

    public StatsDashboard() {
        super("\uD83C\uDFCF Player Statistics Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 300);
        setLocationRelativeTo(null);

        JLabel header = new JLabel("\uD83C\uDFCF Player Statistics Dashboard", SwingConstants.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(6, 1, 6, 6));
        center.add(totalMatchesLabel);
        center.add(totalRunsLabel);
        center.add(totalWicketsLabel);
        center.add(totalOversLabel);
        center.add(totalInningsLabel);
        center.add(battingAverageLabel);
        add(center, BorderLayout.CENTER);

        JButton addMatchBtn = new JButton("➕ Add Match");
        JButton viewHistoryBtn = new JButton("📋 View Match History");
        JButton refreshBtn = new JButton("⟳ Refresh");
        JPanel bottom = new JPanel();
        bottom.add(addMatchBtn);
        bottom.add(viewHistoryBtn);
        bottom.add(refreshBtn);
        add(bottom, BorderLayout.SOUTH);

        addMatchBtn.addActionListener(e -> openAddMatchForm(null));
        viewHistoryBtn.addActionListener(e -> openMatchHistory());
        refreshBtn.addActionListener(e -> refreshStats());

        refreshStats();
    }

    private void refreshStats() {
        try {
            totalMatchesLabel.setText("Total Matches: " + service.getTotalMatches());
            totalRunsLabel.setText("Total Runs: " + service.getTotalRuns());
            totalWicketsLabel.setText("Total Wickets: " + service.getTotalWickets());
            totalOversLabel.setText("Total Overs Bowled: " + twoDp.format(service.getTotalOvers()));
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
}


