package earth.guardian.lrc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import earth.guardian.lrc.enums.MatchScore;

/**
 * Takes match as input and records each teams score.
 */
@Singleton
public class LeagueMatchRecorder {

	/**
	 * In memory record of all teams that have played a match.
	 */
	@Inject private TeamCache teamCache;

	private static final int MATCH_TEAM_A_INDEX = 0;
	private static final int MATCH_TEAM_B_INDEX = 1;
	private static final String MATCH_TEAM_SEPARATOR = ",";		
	
	/**
	 * Receives input for a single match.
	 * @param match For example "Lions 3, Snakes 3"
	 */
	public void recordMatchResult(String match) {
		final String[] matchTeams = match.split(MATCH_TEAM_SEPARATOR);
		final String teamMatchA = matchTeams[MATCH_TEAM_A_INDEX].trim();
		final String teamMatchB = matchTeams[MATCH_TEAM_B_INDEX].trim();
		recordMatchResult(TeamMatch.extract(teamMatchA), TeamMatch.extract(teamMatchB));
	}
	
	/**
	 * Receives input for a single match.
	 * @param teamMatchA
	 * @param teamMatchB
	 */
	private void recordMatchResult(TeamMatch teamMatchA, TeamMatch teamMatchB) {
		final int matchScoreA = teamMatchA.getMatchScore();
		final int matchScoreB = teamMatchB.getMatchScore();
		
		if (matchScoreA == matchScoreB) {
			teamCache.getOrCreateByTeamByName(teamMatchA.getTeamName()).recordMatchScore(MatchScore.DRAW);
			teamCache.getOrCreateByTeamByName(teamMatchB.getTeamName()).recordMatchScore(MatchScore.DRAW);	
		} else if (matchScoreA > matchScoreB) {
			teamCache.getOrCreateByTeamByName(teamMatchA.getTeamName()).recordMatchScore(MatchScore.WON);
			teamCache.getOrCreateByTeamByName(teamMatchB.getTeamName()).recordMatchScore(MatchScore.LOSS);	
		} else {
			teamCache.getOrCreateByTeamByName(teamMatchA.getTeamName()).recordMatchScore(MatchScore.LOSS);
			teamCache.getOrCreateByTeamByName(teamMatchB.getTeamName()).recordMatchScore(MatchScore.WON);
		}	
	}

	/**
	 * 
	 * @return
	 */
	public String getLeagueResults() {
		final StringBuilder builder = new StringBuilder();
		final List<Team> teams = new ArrayList<>(teamCache.getTeams());
		Collections.sort(teams, new TeamSorter());
		
		int place = 0;
		long currentTeamScore = -1;
		long previousTeamScore = -2;
		for (int i = 0; i < teams.size(); i++) {
			
			currentTeamScore = teams.get(i).getSeasonScore();			
			if (currentTeamScore != previousTeamScore) { // Only bump place if scores differ.
				place = (i + 1); // First loop this will get 1.
			}
			
			// Place. team name and points, platform newline character.
			builder.append(String.format("%d. %s %n", place, teams.get(i).toString()));
			
			// Next loop.
			previousTeamScore = currentTeamScore;
		}
		return builder.toString();
	}

}
