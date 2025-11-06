package com.cricketstats.service;

import com.cricketstats.dao.MatchDAO;
import com.cricketstats.model.Match;

import java.sql.SQLException;
import java.util.List;

public class MatchService {
    private final MatchDAO dao = new MatchDAO();

    public void addMatch(Match m) {
        try {
            dao.addMatch(m);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add match: " + e.getMessage());
        }
    }

    public void updateMatch(Match m) {
        try {
            dao.updateMatch(m);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update match: " + e.getMessage());
        }
    }

    public void deleteMatch(int id) {
        try {
            dao.deleteMatch(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete match: " + e.getMessage());
        }
    }

    public List<Match> getAllMatches() {
        try {
            return dao.getAllMatches();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all matches: " + e.getMessage());
        }
    }

    // Aggregations
    public int getTotalMatches() {
        return getAllMatches().size();
    }

    public int getTotalRuns() {
        return getAllMatches().stream().mapToInt(Match::getRuns).sum();
    }

    public int getTotalWickets() {
        return getAllMatches().stream().mapToInt(Match::getWickets).sum();
    }

    public double getTotalOvers() {
        return getAllMatches().stream().mapToDouble(Match::getOversBowled).sum();
    }

    public int getTotalInnings() {
        return getAllMatches().stream().mapToInt(Match::getInnings).sum();
    }

    public double getBattingAverage() {
        int innings = getTotalInnings();
        return innings == 0 ? 0.0 : (double) getTotalRuns() / innings;
    }
}
