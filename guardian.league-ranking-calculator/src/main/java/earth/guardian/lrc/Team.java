package earth.guardian.lrc;

import earth.guardian.lrc.enums.MatchScore;
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
	public void recordMatchScore(MatchScore matchScore) {
		seasonScore +=  matchScore.getPoints();
		log.logMatchResult(getTeamName(), matchScore.getPoints(), seasonScore);	
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
