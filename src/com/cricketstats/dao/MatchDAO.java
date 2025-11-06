package com.cricketstats.dao;

import com.cricketstats.model.Match;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO {

    public void addMatch(Match match) throws SQLException {
        final String sql = "INSERT INTO matches (match_date, opponent, runs, innings, wickets, overs_bowled) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, match.getMatchDate());
            ps.setString(2, match.getOpponent());
            ps.setInt(3, match.getRuns());
            ps.setInt(4, match.getInnings());
            ps.setInt(5, match.getWickets());
            ps.setBigDecimal(6, java.math.BigDecimal.valueOf(match.getOversBowled()));
            ps.executeUpdate();
        }
    }

    public void updateMatch(Match match) throws SQLException {
        final String sql = "UPDATE matches SET match_date=?, opponent=?, runs=?, innings=?, wickets=?, overs_bowled=? WHERE match_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, match.getMatchDate());
            ps.setString(2, match.getOpponent());
            ps.setInt(3, match.getRuns());
            ps.setInt(4, match.getInnings());
            ps.setInt(5, match.getWickets());
            ps.setBigDecimal(6, java.math.BigDecimal.valueOf(match.getOversBowled()));
            ps.setInt(7, match.getMatchId());
            ps.executeUpdate();
        }
    }

    public void deleteMatch(int matchId) throws SQLException {
        final String sql = "DELETE FROM matches WHERE match_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, matchId);
            ps.executeUpdate();
        }
    }

    public List<Match> getAllMatches() throws SQLException {
        final String sql = "SELECT match_id, match_date, opponent, runs, innings, wickets, overs_bowled FROM matches ORDER BY match_date DESC, match_id DESC";
        List<Match> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Match m = new Match();
                m.setMatchId(rs.getInt("match_id"));
                m.setMatchDate(rs.getDate("match_date"));
                m.setOpponent(rs.getString("opponent"));
                m.setRuns(rs.getInt("runs"));
                m.setInnings(rs.getInt("innings"));
                m.setWickets(rs.getInt("wickets"));
                java.math.BigDecimal ob = rs.getBigDecimal("overs_bowled");
                m.setOversBowled(ob == null ? 0.0 : ob.doubleValue());
                list.add(m);
            }
        }
        return list;
    }

    public int getTotalMatches() throws SQLException {
        final String sql = "SELECT COUNT(*) FROM matches";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int getTotalRuns() throws SQLException {
        return getIntAggregate("SELECT COALESCE(SUM(runs),0) FROM matches");
    }

    public int getTotalWickets() throws SQLException {
        return getIntAggregate("SELECT COALESCE(SUM(wickets),0) FROM matches");
    }

    public int getTotalInnings() throws SQLException {
        return getIntAggregate("SELECT COALESCE(SUM(innings),0) FROM matches");
    }

    public double getTotalOvers() throws SQLException {
        final String sql = "SELECT COALESCE(SUM(overs_bowled),0) FROM matches";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getBigDecimal(1).doubleValue() : 0.0;
        }
    }

    private int getIntAggregate(String sql) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}


