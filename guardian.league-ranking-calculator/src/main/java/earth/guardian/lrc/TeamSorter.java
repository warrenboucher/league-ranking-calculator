package earth.guardian.lrc;

import java.util.Comparator;

/**
 * Order by score and then by name (Ignore case)
 */
public class TeamSorter implements Comparator<Team> {
	
	/**
	 * @param teamA
	 * @param teamB
	 */
	@Override
	public int compare(Team teamA, Team teamB) {
		if (teamA.getSeasonScore() == teamB.getSeasonScore()) {
			// Same score, sort alphabetically.
			return teamA.getTeamName().compareToIgnoreCase(teamB.getTeamName());
		} else if (teamA.getSeasonScore() > teamB.getSeasonScore()) {				
			return -1;
		} else {
			return 1;
		}  
	}

}
