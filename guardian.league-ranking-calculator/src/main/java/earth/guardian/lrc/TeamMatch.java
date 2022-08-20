package earth.guardian.lrc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Encapsulates a team match.
 */
public class TeamMatch {
	private static final String TEAM_MATCH_PARTS_SEPARATOR = " ";
	private final String teamName;
	private final int matchScore;

	/**
	 * 
	 * @param teamName
	 * @param matchScore
	 */
	public TeamMatch(String teamName, int matchScore) {
		this.teamName = teamName;
		this.matchScore = matchScore;
	}

	public String getTeamName() {
		return teamName;
	}

	public int getMatchScore() {
		return matchScore;
	}
	
	/**
	 * Create a TeamMatch object from part of the match input specific to a team.
	 * @param teamMatch For example "FC Awesome 0";
	 * @return
	 */
	public static TeamMatch extract(String teamMatch) {		
		final String[] parts = teamMatch.split(TEAM_MATCH_PARTS_SEPARATOR);
		final List<String> partsAsList = new ArrayList<>(Arrays.asList(parts));
		final String scoreAsString = partsAsList. remove(partsAsList.size() -1); // Score is always last index.
		final int score = Integer.parseInt(scoreAsString);
		final String teamName = String.join(TEAM_MATCH_PARTS_SEPARATOR, partsAsList);
		return new TeamMatch(teamName, score);
	}
}
