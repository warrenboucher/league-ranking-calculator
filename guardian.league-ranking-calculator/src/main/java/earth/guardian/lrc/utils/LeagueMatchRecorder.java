package earth.guardian.lrc.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import earth.guardian.lrc.model.Team;
import earth.guardian.lrc.model.TeamMatch;

/**
 * A draw (tie) is worth 1 point.
 * A win is worth 3 points. 
 * A loss is worth 0 points.
 */
@Singleton
public class LeagueMatchRecorder {

	/**
	 * In memory record of all teams that have played a match.
	 */
	@Inject private TeamCache teamCache;
	
	private static final int WIN_PTS = 3;
	private static final int DRAW_PTS = 1;
	private static final int LOSS_PTS = 0;	
	
	private static final int MATCH_TEAM_A_INDEX = 0;
	private static final int MATCH_TEAM_B_INDEX = 1;
	private static final String MATCH_TEAM_SEPARATOR = ",";		
	
	/**
	 * 
	 * @param match For example "Lions 3, Snakes 3"
	 */
	public void recordMatchResult(String match) {
		final String[] matchTeams = match.split(MATCH_TEAM_SEPARATOR);
		final String teamMatchA = matchTeams[MATCH_TEAM_A_INDEX].trim();
		final String teamMatchB = matchTeams[MATCH_TEAM_B_INDEX].trim();
		recordMatchResult(TeamMatch.extract(teamMatchA), TeamMatch.extract(teamMatchB));
	}
	
	/**
	 * 
	 * @param teamMatchA
	 * @param teamMatchB
	 */
	private void recordMatchResult(TeamMatch teamMatchA, TeamMatch teamMatchB) {
		final int matchScoreA = teamMatchA.getMatchScore();
		final int matchScoreB = teamMatchB.getMatchScore();
		
		if (matchScoreA == matchScoreB) {
			teamCache.getTeamByName(teamMatchA.getTeamName()).recordMatchScore(DRAW_PTS);
			teamCache.getTeamByName(teamMatchB.getTeamName()).recordMatchScore(DRAW_PTS);	
		} else if (matchScoreA > matchScoreB) {
			teamCache.getTeamByName(teamMatchA.getTeamName()).recordMatchScore(WIN_PTS);
			teamCache.getTeamByName(teamMatchB.getTeamName()).recordMatchScore(LOSS_PTS);	
		} else {
			teamCache.getTeamByName(teamMatchA.getTeamName()).recordMatchScore(LOSS_PTS);
			teamCache.getTeamByName(teamMatchB.getTeamName()).recordMatchScore(WIN_PTS);
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
		for (int i = 0; i < teams.size(); i++) {
			// Place. team name and points, platform newline character.
			builder.append(String.format("%d. %s %n", (i+1), teams.get(i).toString()));
		}
		return builder.toString();
	}

}
