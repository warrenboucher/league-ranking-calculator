package earth.guardian.lrc.constants;

/**
 * Contains constants specific to a match.
 */
public class MatchConstants {
	
	/**
	 * Minimum score that 
	 */
	private static final int MINIMUM_SCORE = 0;
	
	private static final int WIN_POINTS = 3;
	private static final int DRAW_POINTS = 1;
	private static final int LOSS_POINTS = 0;	
	
	/**
	 * 
	 */
	private MatchConstants() {
		// Do not allow initialization.
	}

	public static int getMinimumScore() {
		return MINIMUM_SCORE;
	}

	public static int getWinPoints() {
		return WIN_POINTS;
	}

	public static int getDrawPoints() {
		return DRAW_POINTS;
	}

	public static int getLossPoints() {
		return LOSS_POINTS;
	}	
	
}
