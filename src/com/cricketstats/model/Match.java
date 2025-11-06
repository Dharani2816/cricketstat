package com.cricketstats.model;

import java.sql.Date;

public class Match {
    private int matchId;
    private Date matchDate;
    private String opponent;
    private int runs;
    private int wickets;
    private double oversBowled;
    private int innings;

    public int getMatchId() { return matchId; }
    public void setMatchId(int matchId) { this.matchId = matchId; }

    public Date getMatchDate() { return matchDate; }
    public void setMatchDate(Date matchDate) { this.matchDate = matchDate; }

    public String getOpponent() { return opponent; }
    public void setOpponent(String opponent) { this.opponent = opponent; }

    public int getRuns() { return runs; }
    public void setRuns(int runs) { this.runs = runs; }

    public int getWickets() { return wickets; }
    public void setWickets(int wickets) { this.wickets = wickets; }

    public double getOversBowled() { return oversBowled; }
    public void setOversBowled(double oversBowled) { this.oversBowled = oversBowled; }

    public int getInnings() { return innings; }
    public void setInnings(int innings) { this.innings = innings; }

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
