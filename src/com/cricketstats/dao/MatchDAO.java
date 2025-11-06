package com.cricketstats.dao;

import com.cricketstats.model.Match;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO {

    public void addMatch(Match m) throws SQLException {
        String sql = "INSERT INTO matches(match_date, opponent, runs, wickets, overs_bowled, innings) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, m.getMatchDate());
            ps.setString(2, m.getOpponent());
            ps.setInt(3, m.getRuns());
            ps.setInt(4, m.getWickets());
            ps.setDouble(5, m.getOversBowled());
            ps.setInt(6, m.getInnings());
            ps.executeUpdate();
        }
    }

    public List<Match> getAllMatches() throws SQLException {
        List<Match> list = new ArrayList<>();
        String sql = "SELECT * FROM matches ORDER BY match_date DESC";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Match m = new Match();
                m.setMatchId(rs.getInt("match_id"));
                m.setMatchDate(rs.getDate("match_date"));
                m.setOpponent(rs.getString("opponent"));
                m.setRuns(rs.getInt("runs"));
                m.setWickets(rs.getInt("wickets"));
                m.setOversBowled(rs.getDouble("overs_bowled"));
                m.setInnings(rs.getInt("innings"));
                list.add(m);
            }
        }
        return list;
    }

    public void updateMatch(Match m) throws SQLException {
        String sql = "UPDATE matches SET match_date=?, opponent=?, runs=?, wickets=?, overs_bowled=?, innings=? WHERE match_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, m.getMatchDate());
            ps.setString(2, m.getOpponent());
            ps.setInt(3, m.getRuns());
            ps.setInt(4, m.getWickets());
            ps.setDouble(5, m.getOversBowled());
            ps.setInt(6, m.getInnings());
            ps.setInt(7, m.getMatchId());
            ps.executeUpdate();
        }
    }

    public void deleteMatch(int id) throws SQLException {
        String sql = "DELETE FROM matches WHERE match_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
