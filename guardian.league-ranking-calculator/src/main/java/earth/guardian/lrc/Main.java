package earth.guardian.lrc;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Module;

import earth.guardian.lrc.guice.UtilityModule;
import earth.guardian.lrc.utils.L;

/**
 * 
 */
public class Main {
	private static final int EXPECTED_MODULES = 2;
	
	/**
	 * 
	 */
	private static void initInjector() {		
		final List<Module> modules = new ArrayList<>(EXPECTED_MODULES);
		modules.add(new UtilityModule());
		
		final Module[] moduleArray = new Module[modules.size()];
		modules.toArray(moduleArray);
		LrcRuntime.setInjector(Guice.createInjector(moduleArray));
	}
	
	/**
	 * 
	 */
	private static void start() {
		initInjector();
		L.getLogger().info("Hello World");
	}
	
	/**
	 * Application entry point.
	 * @param args
	 */
	public static void main(String[] args) {
		try {			
			start();
			System.exit(0);	
		} catch (Exception e) {
			L.getLogger().error("{} {}", LrcRuntime.getCorrelationId(), e.getMessage(), e);
			System.exit(1);
		}
	}
	
}
