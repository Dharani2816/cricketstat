package com.cricketstats.service;

import com.cricketstats.dao.MatchDAO;
import com.cricketstats.model.Match;

import java.sql.SQLException;
import java.util.List;

public class MatchService {
    private final MatchDAO dao;

    public MatchService() {
        this.dao = new MatchDAO();
    }

    public MatchService(MatchDAO dao) {
        this.dao = dao;
    }

    public void addMatch(Match match) {
        validateMatch(match, false);
        try {
            dao.addMatch(match);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add match", e);
        }
    }

    public void updateMatch(Match match) {
        if (match.getMatchId() <= 0) {
            throw new IllegalArgumentException("matchId is required for update");
        }
        validateMatch(match, true);
        try {
            dao.updateMatch(match);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update match", e);
        }
    }

    public void deleteMatch(int matchId) {
        try {
            dao.deleteMatch(matchId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete match", e);
        }
    }

    public List<Match> getAllMatches() {
        try {
            return dao.getAllMatches();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch matches", e);
        }
    }

    public int getTotalMatches() {
        try {
            return dao.getTotalMatches();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get total matches", e);
        }
    }

    public int getTotalRuns() {
        try {
            return dao.getTotalRuns();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get total runs", e);
        }
    }

    public int getTotalWickets() {
        try {
            return dao.getTotalWickets();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get total wickets", e);
        }
    }

    public double getTotalOvers() {
        try {
            return dao.getTotalOvers();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get total overs", e);
        }
    }

    public int getTotalInnings() {
        try {
            return dao.getTotalInnings();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get total innings", e);
        }
    }

    public double getBattingAverage() {
        int innings = getTotalInnings();
        int runs = getTotalRuns();
        return innings == 0 ? 0.0 : (double) runs / innings;
    }

    private void validateMatch(Match m, boolean isUpdate) {
        if (m.getMatchDate() == null) {
            throw new IllegalArgumentException("Match date is required");
        }
        if (m.getOpponent() == null || m.getOpponent().trim().isEmpty()) {
            throw new IllegalArgumentException("Opponent is required");
        }
        if (m.getRuns() < 0) {
            throw new IllegalArgumentException("Runs cannot be negative");
        }
        if (m.getInnings() < 0) {
            throw new IllegalArgumentException("Innings cannot be negative");
        }
        if (m.getWickets() < 0) {
            throw new IllegalArgumentException("Wickets cannot be negative");
        }
        if (m.getOversBowled() < 0) {
            throw new IllegalArgumentException("Overs bowled cannot be negative");
        }
        // Normalize overs to one decimal place for consistent storage
        double normalizedOvers = Math.round(m.getOversBowled() * 10.0) / 10.0;
        m.setOversBowled(normalizedOvers);
    }
}


