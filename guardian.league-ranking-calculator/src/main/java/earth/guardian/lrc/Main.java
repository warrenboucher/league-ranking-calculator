package earth.guardian.lrc;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Module;

import earth.guardian.lrc.guice.UtilityModule;
import earth.guardian.lrc.utils.L;

/**
 * This is the entry point.  The application itself is a different class.
 */
public class Main {
	private static final int EXPECTED_MODULES = 1;

	/**
	 * Setup dependency injection.
	 */
	private static void initInjector() {		
		final List<Module> modules = new ArrayList<>(EXPECTED_MODULES);
		modules.add(new UtilityModule());

		final Module[] moduleArray = new Module[modules.size()];
		modules.toArray(moduleArray);
		LrcRuntime.setInjector(Guice.createInjector(moduleArray));				
	}

	/**
	 * Application entry point.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			initInjector();
			final LeagueRankingCalculator application = LrcRuntime.getInjector().getInstance(LeagueRankingCalculator.class);
			application.run(args);
			// System.exit(0);	
		} catch (Exception e) {
			e.printStackTrace(); // Log to console to help debug startup issues.
			L.getLogger().error("{} {}", LrcRuntime.getCorrelationId(), e.getMessage(), e);
			System.exit(1);
		}
	}

}
