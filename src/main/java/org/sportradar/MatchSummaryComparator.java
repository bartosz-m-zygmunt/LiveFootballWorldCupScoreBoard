package org.sportradar;

import java.util.Comparator;

public class MatchSummaryComparator implements Comparator<Match> {
    @Override
    public int compare(Match firstMatch, Match secondMatch) {
        int scoreComparisonResult = compareScores(firstMatch, secondMatch);
        if (scoreComparisonResult != 0) {
            return scoreComparisonResult;
        }

        return compareStartDatesAndTimes(firstMatch, secondMatch);
    }

    private int compareScores(Match firstMatch, Match secondMatch) {
        return Integer.compare(secondMatch.getTotalScore(), firstMatch.getTotalScore());
    }

    private int compareStartDatesAndTimes(Match firstMatch, Match secondMatch) {
        return secondMatch.getStartDateAndTime().compareTo(firstMatch.getStartDateAndTime());
    }
}
