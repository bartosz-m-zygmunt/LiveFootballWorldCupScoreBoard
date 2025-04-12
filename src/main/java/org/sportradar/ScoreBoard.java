package org.sportradar;

import java.time.LocalDateTime;
import java.util.List;

public interface ScoreBoard {
    void startMatch(String homeTeam, String awayTeam, LocalDateTime matchDateAndTime);

    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);

    void finishMatch(String homeTeam, String awayTeam);

    List<Match> getSummary();
}
