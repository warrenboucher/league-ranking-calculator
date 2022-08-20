package earth.guardian.lrc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * In memory cache of all teams.
 */
@Singleton
public class TeamCache {

	private final Map<String, Team> leagueTeams = new HashMap<>();
	
	/**
	 * 
	 */
	@Inject
	public TeamCache() {
		// Inject constructor.
	}
	
	/**
	 * 
	 * @param teamName
	 * @return
	 */
	public Team getOrCreateByTeamByName(String teamName) {
		final String key = teamName.toLowerCase();
		if (leagueTeams.containsKey(key)) {
			return leagueTeams.get(key);
		}
		
		final Team team = new Team(teamName);
		leagueTeams.put(key, team);
		return team;
	}	
	
	/**
	 * 
	 * @return
	 */
	public Collection<Team> getTeams() {
		return leagueTeams.values();
	}
	
}
