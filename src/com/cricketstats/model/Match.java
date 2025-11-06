package com.cricketstats.model;

import java.sql.Date;

/**
 * Represents a single match played by the player.
 * Each Match object stores batting and bowling stats for one game.
 */
public class Match {
    // Primary key (auto-increment from DB)
    private int matchId;

    // Basic match info
    private Date matchDate;
    private String opponent;

    // Batting performance
    private int runs;
    private int innings; // used to calculate batting average

    // Bowling performance
    private int wickets;
    private double oversBowled; // overs bowled (can be decimal like 4.5)

    // ---------------------------
    // Getters and Setters
    // ---------------------------

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getInnings() {
        return innings;
    }

    public void setInnings(int innings) {
        this.innings = innings;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public double getOversBowled() {
        return oversBowled;
    }

    public void setOversBowled(double oversBowled) {
        this.oversBowled = oversBowled;
    }

    // ---------------------------
    // Utility Methods (Optional)
    // ---------------------------

    @Override
    public String toString() {
        return "Match{" +
                "matchId=" + matchId +
                ", matchDate=" + matchDate +
                ", opponent='" + opponent + '\'' +
                ", runs=" + runs +
                ", wickets=" + wickets +
                ", oversBowled=" + oversBowled +
                ", innings=" + innings +
                '}';
    }
}


