package org.sportradar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryScoreBoardTest {

    private InMemoryScoreBoard scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new InMemoryScoreBoard();
    }

    @Test
    void shouldStartMatchSuccessfully() {
        scoreBoard.startMatch("Spain", "Brazil", LocalDateTime.now());

        assertMatchStarted("Spain", "Brazil", 0, 0);
    }

    @Test
    void shouldUpdateScoreSuccessfully() {
        scoreBoard.startMatch("Spain", "Brazil", LocalDateTime.now());
        scoreBoard.updateScore("Spain", "Brazil", 3, 2);

        assertMatchScore("Spain", "Brazil", 3, 2);
    }

    @Test
    void shouldFinishMatchSuccessfully() {
        scoreBoard.startMatch("Spain", "Brazil", LocalDateTime.now());
        scoreBoard.finishMatch("Spain", "Brazil");

        assertTrue(scoreBoard.getSummary().isEmpty());
    }

    @Test
    void shouldReturnCorrectSummaryOrder() {
        startAndUpdateMatches();

        List<Match> summary = scoreBoard.getSummary();
        assertMatchSummaryOrder(summary);
    }

    @Test
    void shouldThrowExceptionWhenStartingDuplicateMatch() {
        scoreBoard.startMatch("Spain", "Brazil", LocalDateTime.now());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startMatch("Spain", "Brazil", LocalDateTime.now()));

        assertEquals("Match already started", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFinishingNonexistentMatch() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> scoreBoard.finishMatch("Spain", "Brazil"));

        assertEquals("Match not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentMatch() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> scoreBoard.updateScore("Spain", "Brazil", 1, 1));

        assertEquals("Match not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSettingNegativeScore() {
        scoreBoard.startMatch("Spain", "Brazil", LocalDateTime.now());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.updateScore("Spain", "Brazil", -1, 2));

        assertEquals("Scores must be non-negative", exception.getMessage());
    }

    private void assertMatchStarted(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        List<Match> summary = scoreBoard.getSummary();
        assertEquals(1, summary.size());
        Match match = summary.get(0);
        assertEquals(homeTeam, match.getHomeTeam());
        assertEquals(awayTeam, match.getAwayTeam());
        assertEquals(homeScore, match.getHomeScore());
        assertEquals(awayScore, match.getAwayScore());
    }

    private void assertMatchScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        Match match = scoreBoard.getSummary().get(0);
        assertEquals(homeScore, match.getHomeScore());
        assertEquals(awayScore, match.getAwayScore());
    }

    private void startAndUpdateMatches() {
        scoreBoard.startMatch("Mexico", "Canada", LocalDateTime.now().minusMinutes(50));
        scoreBoard.updateScore("Mexico", "Canada", 0, 5);

        scoreBoard.startMatch("Spain", "Brazil", LocalDateTime.now().minusMinutes(40));
        scoreBoard.updateScore("Spain", "Brazil", 10, 2);

        scoreBoard.startMatch("Germany", "France", LocalDateTime.now().minusMinutes(30));
        scoreBoard.updateScore("Germany", "France", 2, 2);

        scoreBoard.startMatch("Uruguay", "Italy", LocalDateTime.now().minusMinutes(20));
        scoreBoard.updateScore("Uruguay", "Italy", 6, 6);

        scoreBoard.startMatch("Argentina", "Australia", LocalDateTime.now().minusMinutes(10));
        scoreBoard.updateScore("Argentina", "Australia", 3, 1);
    }

    private void assertMatchSummaryOrder(List<Match> summary) {
        assertEquals("Uruguay 6 - Italy 6", summary.get(0).toString());
        assertEquals("Spain 10 - Brazil 2", summary.get(1).toString());
        assertEquals("Mexico 0 - Canada 5", summary.get(2).toString());
        assertEquals("Argentina 3 - Australia 1", summary.get(3).toString());
        assertEquals("Germany 2 - France 2", summary.get(4).toString());
    }
}