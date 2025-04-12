package org.sportradar;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class InMemoryScoreBoard implements ScoreBoard {

    private final List<Match> matches = new LinkedList<>();

    @Override
    public void startMatch(String homeTeam, String awayTeam, LocalDateTime matchDateAndTime) {
        validateTeams(homeTeam, awayTeam);
        Match newMatch = createMatch(homeTeam, awayTeam, matchDateAndTime);
        ensureMatchNotStarted(newMatch);
        createMatch(newMatch);
    }

    private void validateTeams(String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Teams cannot be null");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Teams must be different");
        }
    }

    private Match createMatch(String homeTeam, String awayTeam, LocalDateTime matchDateAndTime) {
        return new Match(homeTeam, awayTeam, matchDateAndTime);
    }

    private void ensureMatchNotStarted(Match match) {
        if (matches.contains(match)) {
            throw new IllegalArgumentException("Match already started");
        }
    }

    private void createMatch(Match newMatch) {
        matches.add(newMatch);
    }

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        validateScores(homeScore, awayScore);
        Match match = findAndValidateMatch(homeTeam, awayTeam);
        updateMatchScore(match, homeScore, awayScore);
    }

    private void validateScores(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores must be non-negative");
        }
    }

    private Match findAndValidateMatch(String homeTeam, String awayTeam) {
        Match match = findMatch(homeTeam, awayTeam);
        if (match == null) {
            throw new IllegalArgumentException("Match not found");
        }
        return match;
    }

    private Match findMatch(String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam)
                        && m.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Match not found"));
    }

    private void updateMatchScore(Match match, int homeScore, int awayScore) {
        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        Match match = findAndValidateMatch(homeTeam, awayTeam);
        removeMatch(match);
    }

    private void removeMatch(Match match) {
        matches.remove(match);
    }

    @Override
    public List<Match> getSummary() {
        List<Match> summaryMatches = new LinkedList<>(matches);
        summaryMatches.sort(new MatchSummaryComparator());
        return summaryMatches;
    }
}
