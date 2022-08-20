package earth.guardian.lrc.enums;

import earth.guardian.lrc.constants.MatchConstants;

/**
 * A match has points allocated based on Win Lose, Draw.
 */
public enum MatchScore {
	
	WON(MatchConstants.getWinPoints()),
	LOSS(MatchConstants.getLossPoints()),
	DRAW(MatchConstants.getDrawPoints());
	
	private int points;
	
	/**
	 * 
	 * @param points
	 */
	MatchScore(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return points;
	}
}
