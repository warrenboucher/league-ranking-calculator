package earth.guardian.lrc.model;

import earth.guardian.lrc.LrcRuntime;
import earth.guardian.lrc.MatchConstants;
import earth.guardian.lrc.utils.L;

/**
 * Encapsulates Team data.
 */
public class Team {
  
	private final L log = LrcRuntime.getInjector().getInstance(L.class);
	private final String teamName;
	private long seasonScore;
	
	/**
	 * 
	 * @param teamName
	 */
	public Team(String teamName) {
		this.teamName = teamName;
	}
	
	/**
	 * 
	 * @param matchScore
	 */
	public void recordMatchScore(int matchScore) {
		if (MatchConstants.MINIMUM_SCORE < matchScore) {
			seasonScore +=  matchScore;
			log.logMatchResult(getTeamName(), matchScore, seasonScore);
		}		
	}
		
	/**
	 * 
	 * @return
	 */
	public long getSeasonScore() {
		return seasonScore;
	}

	/**
	 * 
	 * @return
	 */
	public String getTeamName() {
		return teamName;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return String.format("%s, %d pts", getTeamName(), getSeasonScore());
	}
	
}
