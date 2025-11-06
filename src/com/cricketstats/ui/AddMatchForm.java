package com.cricketstats.ui;

import com.cricketstats.model.Match;
import com.cricketstats.service.MatchService;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Date;
import java.util.function.Consumer;

public class AddMatchForm extends JDialog {
    private final MatchService service = new MatchService();
    private final Runnable onSuccess;

    private final JTextField dateField = new JTextField();
    private final JTextField opponentField = new JTextField();
    private final JTextField runsField = new JTextField();
    private final JTextField wicketsField = new JTextField();
    private final JTextField oversField = new JTextField();
    private final JTextField inningsField = new JTextField();

    private Match editing;

    public AddMatchForm(java.awt.Frame owner, Match editing, Runnable onSuccess) {
        super(owner, true);
        this.onSuccess = onSuccess;
        this.editing = editing;
        setTitle(editing == null ? "Add Match" : "Edit Match");
        setSize(420, 320);
        setLocationRelativeTo(owner);

        JPanel form = new JPanel(new GridLayout(6, 2, 6, 6));
        form.add(new JLabel("Match Date (YYYY-MM-DD):"));
        form.add(dateField);
        form.add(new JLabel("Opponent Team:"));
        form.add(opponentField);
        form.add(new JLabel("Runs Scored:"));
        form.add(runsField);
        form.add(new JLabel("Wickets Taken:"));
        form.add(wicketsField);
        form.add(new JLabel("Overs Bowled:"));
        form.add(oversField);
        form.add(new JLabel("Innings Played:"));
        form.add(inningsField);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> onSave());

        JPanel bottom = new JPanel();
        bottom.add(saveBtn);

        add(form, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        if (editing != null) {
            prefill(editing);
        }
    }

    private void prefill(Match m) {
        dateField.setText(m.getMatchDate().toString());
        opponentField.setText(m.getOpponent());
        runsField.setText(String.valueOf(m.getRuns()));
        wicketsField.setText(String.valueOf(m.getWickets()));
        oversField.setText(String.valueOf(m.getOversBowled()));
        inningsField.setText(String.valueOf(m.getInnings()));
    }

    private void onSave() {
        try {
            Match m = (editing == null) ? new Match() : editing;
            m.setMatchDate(Date.valueOf(dateField.getText().trim()));
            m.setOpponent(opponentField.getText().trim());
            m.setRuns(Integer.parseInt(runsField.getText().trim()));
            m.setWickets(Integer.parseInt(wicketsField.getText().trim()));
            m.setOversBowled(Double.parseDouble(oversField.getText().trim()));
            m.setInnings(Integer.parseInt(inningsField.getText().trim()));

            if (editing == null) {
                service.addMatch(m);
            } else {
                service.updateMatch(m);
            }

            JOptionPane.showMessageDialog(this, "Match Saved Successfully!");
            if (onSuccess != null) {
                onSuccess.run();
            }
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


