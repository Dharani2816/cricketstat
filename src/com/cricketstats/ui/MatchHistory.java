package com.cricketstats.ui;

import com.cricketstats.model.Match;
import com.cricketstats.service.MatchService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.List;

public class MatchHistory extends JFrame {
    private final MatchService service = new MatchService();
    private final Runnable onBack;
    private final DefaultTableModel model;
    private final JTable table;

    public MatchHistory(Runnable onBack) {
        super("Match History");
        this.onBack = onBack;
        setSize(800, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new Object[]{"ID", "Date", "Opponent", "Runs", "Wickets", "Overs", "Inns"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);

        JButton editBtn = new JButton("✏ Edit");
        JButton deleteBtn = new JButton("🗑 Delete");
        JButton backBtn = new JButton("🔙 Back");

        editBtn.addActionListener(e -> onEdit());
        deleteBtn.addActionListener(e -> onDelete());
        backBtn.addActionListener(e -> {
            dispose();
            if (onBack != null) onBack.run();
        });

        JPanel top = new JPanel();
        top.add(editBtn);
        top.add(deleteBtn);
        top.add(backBtn);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        reload();
    }

    private void reload() {
        model.setRowCount(0);
        try {
            List<Match> matches = service.getAllMatches();
            for (Match m : matches) {
                model.addRow(new Object[]{
                        m.getMatchId(),
                        m.getMatchDate(),
                        m.getOpponent(),
                        m.getRuns(),
                        m.getWickets(),
                        m.getOversBowled(),
                        m.getInnings()
                });
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to edit");
            return;
        }
        Match m = new Match();
        m.setMatchId((int) model.getValueAt(row, 0));
        m.setMatchDate(java.sql.Date.valueOf(model.getValueAt(row, 1).toString()));
        m.setOpponent(model.getValueAt(row, 2).toString());
        m.setRuns((int) model.getValueAt(row, 3));
        m.setWickets((int) model.getValueAt(row, 4));
        m.setOversBowled(Double.parseDouble(model.getValueAt(row, 5).toString()));
        m.setInnings((int) model.getValueAt(row, 6));

        AddMatchForm form = new AddMatchForm(null, m, this::reload);
        form.setVisible(true);
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete match #" + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.deleteMatch(id);
                reload();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


